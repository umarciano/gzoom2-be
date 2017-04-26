package it.memelabs.smartnebula.lmm.security;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import it.memelabs.smartnebula.lmm.persistence.main.dao.UserLoginDao;
import it.memelabs.smartnebula.lmm.persistence.main.dto.UserLogin;
import it.memelabs.smartnebula.lmm.persistence.main.enumeration.AuthenticationType;
import it.memelabs.smartnebula.lmm.security.model.JwtAuthentication;
import it.memelabs.smartnebula.lmm.security.model.Messages;
import it.memelabs.smartnebula.lmm.security.model.SecurityConfiguration;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import javax.naming.*;
import javax.naming.directory.*;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;
import java.text.MessageFormat;
import java.util.*;

import static com.google.common.collect.Iterables.*;
import static org.slf4j.LoggerFactory.*;
import static org.springframework.security.ldap.LdapUtils.*;

/**
 * An authentication provider that logs users into an AD server.
 *
 * @author Fabio G. Strozzi
 */
@Component("jwtADLoginAuthenticationProvider")
public class JwtADLoginAuthenticationProvider implements AuthenticationProvider, PluggableAuthenticationProvider {
    private static final Logger LOG = getLogger(JwtADLoginAuthenticationProvider.class);
    private static final String LDAP_CTX_FACTORY = "com.sun.jndi.ldap.LdapCtxFactory";

    private final JwtService jwtService;
    private final UserLoginDao userLoginDao;
    private final PermitsStorage permitsStorage;
    private final SecurityConfiguration config;
    private final String url;

    @Autowired
    public JwtADLoginAuthenticationProvider(JwtService jwtService,
                                            UserLoginDao userLoginDao,
                                            PermitsStorage permitsStorage,
                                            SecurityConfiguration config) throws Exception {
        this.jwtService = jwtService;
        this.userLoginDao = userLoginDao;
        this.permitsStorage = permitsStorage;
        this.config = config;
        this.url = new MessageFormat("ldap://{0}:{1}").format(new Object[]{config.getADHost(), config.getADPort()});
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = (String) authentication.getPrincipal();
        try {
            UserLogin profile = userLoginDao.getUserLogin(username);

            if (profile == null || !profile.getEnabled()) {
                // no user or user not enabled, exits the chain of providers as
                // no other things are possible at all
                LOG.error("Attempting to login using a non-existing or disabled account [username={}]", username);
                throw new DisabledException(Messages.INVALID_ACCOUNT);
            }

            if (profile.getAuthenticationType() != AuthenticationType.LDAP) {
                // skips to next authentication provider as this does not support non-DB accounts
                return null;
            }

            // attempts login with ldap
            login(username, (String) authentication.getCredentials());

            // generates the JWT token, store it and return the authenticated authentication
            String token = jwtService.generate(profile);
            permitsStorage.save(token, profile.getUsername());
            return new JwtAuthentication(token, profile);
        } catch (AuthenticationException e) {
            LOG.error("Invalid credentials [user={}]", username);
            throw e;
        } catch (Exception e) {
            LOG.error("Active directory authentication failed due to internal server error", e);
            throw new InternalAuthenticationServiceException("Invalid username or password", e);
        }
    }

    private void login(String username, String password) {
        Object[] usrParams = new Object[]{username, config.getADDomain()};
        LdapContext ctx = null;
        try {
            ctx = openContext(password, usrParams);
            findUser(ctx, usrParams);
        } catch (javax.naming.AuthenticationException e) {
            LOG.error("User failed to authenticate to AD [user={}]", username);
            throw new BadCredentialsException(Messages.INVALID_USERNAME_OR_PASSWORD);
        } catch (NamingException e) {
            LOG.error("Exception occurred while logging into AD", e);
            throw new AuthenticationServiceException(Messages.AD_LOGIN_FAILED);
        } finally {
            closeContext(ctx);
        }
    }

    private LdapContext openContext(String password, Object[] usrParams) throws NamingException {
        // context properties
        Hashtable<String, String> props = new Hashtable<>();
        props.put(Context.SECURITY_PRINCIPAL, config.getADUser().format(usrParams));
        props.put(Context.SECURITY_CREDENTIALS, password);
        props.put(Context.INITIAL_CONTEXT_FACTORY, LDAP_CTX_FACTORY);
        props.put(Context.PROVIDER_URL, url);

        return new InitialLdapContext(props, null);
    }

    private Attributes findUser(LdapContext ctx, Object[] usrParams) throws NamingException {
        SearchControls controls = new SearchControls();
        controls.setSearchScope(SearchControls.SUBTREE_SCOPE);
        controls.setCountLimit(2);
        controls.setTimeLimit(config.getADTimeout());
        controls.setReturningAttributes(config.getADUserAttributes());

        String usrFilter = config.getADUserFilter().format(usrParams);
        String name = config.getADUserRoot() != null ? config.getADUserRoot() : toDC(config.getADDomain());
        NamingEnumeration<SearchResult> resultEnum = ctx.search(name, usrFilter, controls);
        List<SearchResult> resultList = new ArrayList<>();

        try {
            while (resultEnum.hasMore()) {
                resultList.add(resultEnum.next());
            }
        } catch (PartialResultException e) {
            closeEnumeration(resultEnum);
            LOG.info("Partial result ignored while looking up user [username={}, domain={}]", usrParams[0], usrParams[1]);
        }

        if (resultList.size() == 0) {
            LOG.warn("No user found [username={}, domain={}]", usrParams[0], usrParams[1]);
            throw new BadCredentialsException(Messages.INVALID_ACCOUNT);
        }

        if (resultList.size() > 1) {
            LOG.warn("No unique user found [username={}, domain={}]", usrParams[0], usrParams[1]);
            throw new BadCredentialsException(Messages.INVALID_ACCOUNT);
        }

        SearchResult res = resultList.get(0);
        return res.getAttributes();
    }

    private static String toDC(String domainName) {
        Iterable<String> tokens = Splitter.on('.').omitEmptyStrings().trimResults().split(domainName);
        return Joiner.on(',').join(transform(tokens, t -> "DC=" + t));
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }

    @Override
    public boolean isEnabled() {
        return config.isADEnabled();
    }

}
