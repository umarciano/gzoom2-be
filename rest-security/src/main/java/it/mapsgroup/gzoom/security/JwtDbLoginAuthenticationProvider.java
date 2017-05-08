package it.mapsgroup.gzoom.security;

import org.springframework.stereotype.Component;

/**
 * Database-based login authentication provider.
 * <p>
 * This provider attempts to validate username and password and creates a JWT token if succeeds.
 * </p>
 *
 * @author Andrea Fossi
 * @author Fabio G. Strozzi
 */
@Component("jwtDbLoginAuthenticationProvider")
public class JwtDbLoginAuthenticationProvider implements AuthenticationProvider {
    private static final Logger LOG = getLogger(JwtDbLoginAuthenticationProvider.class);

    private final JwtService jwtService;
    private final UserLoginDao userLoginDao;
    private final PermitsStorage permitsStorage;
    private final HashingStrategy hashingStrategy;

    @Autowired
    public JwtDbLoginAuthenticationProvider(JwtService jwtService,
                                            UserLoginDao userLoginDao,
                                            PermitsStorage permitsStorage,
                                            HashingStrategy hashingStrategy) {
        this.jwtService = jwtService;
        this.userLoginDao = userLoginDao;
        this.permitsStorage = permitsStorage;
        this.hashingStrategy = hashingStrategy;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UsernamePasswordAuthenticationToken userPwd = (UsernamePasswordAuthenticationToken) authentication;
        String username = (String) authentication.getPrincipal();
        try {
            UserLogin profile = userLoginDao.getSecurityProfile(username, null);

            if (profile == null || !profile.getEnabled()) {
                // no user or user not enabled, exit the chain of providers as
                // no other things are possible at all
                LOG.error("Attempting to login using a non-existing or disabled account [username={}]", username);
                throw new DisabledException(Messages.INVALID_ACCOUNT);
            }

            if (profile.getAuthenticationType() != DB) {
                // skips to next authentication provider as this does not support non-DB accounts
                return null;
            }

            // verifies the credentials
            boolean auth = hashingStrategy.verify(profile.getSalt(), (String) userPwd.getCredentials(), profile.getPassword());
            if (!auth)
                throw new BadCredentialsException(Messages.INVALID_USERNAME_OR_PASSWORD);

            // password matches
            String token = jwtService.generate(profile);
            permitsStorage.save(token, profile.getUsername());
            return new JwtAuthentication(token, profile);
        } catch (AuthenticationException e) {
            LOG.error("Invalid credentials [user={}]", username);
            throw e;
        } catch (Exception e) {
            LOG.error("Db-based authentication failed due to internal server error", e);
            throw new InternalAuthenticationServiceException("Invalid username or password", e);
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }

}
