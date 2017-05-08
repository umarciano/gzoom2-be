package it.mapsgroup.gzoom.security;

import it.mapsgroup.gzoom.querydsl.dao.UserLoginDao;
import it.mapsgroup.gzoom.querydsl.dto.UserLogin;
import it.mapsgroup.gzoom.security.model.JwtAuthentication;
import it.mapsgroup.gzoom.security.model.Messages;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Token-based authentication provider.
 * <p>
 * This provider attempts to validate a JWT token.
 * </p>
 *
 * @author Andrea Fossi
 */
@Component("jwtTokenAuthenticationProvider")
public class JwtTokenAuthenticationProvider implements AuthenticationProvider {
    private static final Logger LOG = getLogger(JwtTokenAuthenticationProvider.class);

    private final JwtService jwtService;
    private final UserLoginDao userLoginDao;
    private final PermitsStorage permitsStorage;

    @Autowired
    public JwtTokenAuthenticationProvider(JwtService jwtService, UserLoginDao userLoginDao, PermitsStorage permitsStorage) {
        this.jwtService = jwtService;
        this.userLoginDao = userLoginDao;
        this.permitsStorage = permitsStorage;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        try {
            JwtAuthentication authToken = (JwtAuthentication) authentication;
            String token = authToken.getJwtToken();
            String username = jwtService.validate(token);
            boolean isValid = permitsStorage.isValid(token);

            // if token is not valid throw and exception
            if (username == null || !isValid) {
                LOG.error("Invalid authorization token");
                throw new BadCredentialsException(Messages.INVALID_AUTHORIZATION_TOKEN);
            }
            Long activeNodeId = jwtService.getActiveNode(token);

            //TODO
            //UserLogin profile = userLoginDao.getSecurityProfile(username, activeNodeId);
            UserLogin profile = userLoginDao.getUserLogin(username);

            // if no account is found, treat it as disabled
            // this in turn will exit the chain of providers
            if (profile == null) {
                LOG.error("No account for valid token [username={}]", username);
                throw new DisabledException(Messages.INVALID_ACCOUNT);
            }

            // all other cases, create an authenticated authentication
            return new JwtAuthentication(token, profile);
        } catch (AuthenticationException e) {
            LOG.error("Authentication failed: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            LOG.error("Unexpected error occurred while authenticating token", e);
            throw new InternalAuthenticationServiceException("Unexpected error occurred while authenticating token", e);
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return JwtAuthentication.class.isAssignableFrom(authentication);
    }
}
