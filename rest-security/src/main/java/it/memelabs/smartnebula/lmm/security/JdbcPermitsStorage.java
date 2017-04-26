package it.memelabs.smartnebula.lmm.security;

import it.memelabs.smartnebula.lmm.persistence.security.dao.JdbcTokenStore;
import it.memelabs.smartnebula.lmm.security.model.SecurityConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * In memory storage of permits.
 *
 * @author Fabio G. Strozzi
 */
@Service("permitsStorage")
@Profile("persistJwt")
public class JdbcPermitsStorage extends PermitsStorage {
    private final SecurityConfiguration config;
    private final JdbcTokenStore permitsStorage;

    @Autowired
    public JdbcPermitsStorage(SecurityConfiguration config, JdbcTokenStore permitsStorage) {
        super(config);
        this.config = config;
        this.permitsStorage = permitsStorage;
    }

    /**
     * Saves the JWT and other user details.
     *
     * @param jwt      The JWT
     * @param userName Username
     */
    @Override
    public void save(String jwt, String userName) {
        int min = config.getTokenExpiryMinutes();
        permitsStorage.save(jwt, userName, min);
    }

    /**
     * Removes a permit associated to a certain JWT.
     *
     * @param jwt The JWT
     * @return The pruned permit or null if no one is associated to the JWT
     */
    @Override
    public Permit prune(String jwt) {
        return copy(permitsStorage.prune(jwt));
    }

    /**
     * Loads a permit associated to a certain JWT.
     *
     * @param jwt The JWT
     * @return The permit
     */
    @Override
    public Permit load(String jwt) {
        return copy(permitsStorage.load(jwt));
    }

    /**
     * Loads and validates a permit associated to a certain JWT.
     *
     * @param jwt The JWT
     * @return The permit
     */
    @Override
    public boolean isValid(String jwt) {
        Permit permit = load(jwt);
        // makes sure permit expiration is after the current date
        return permit != null && permit.expiration.isAfter(LocalDateTime.now());
    }

    /**
     * Graces the current permit of a specified amount of time.
     *
     * @param jwt    The JWT
     * @param amount The amount of time of the grace
     * @param unit   The unit of time to be added to the expiration date
     */
    @Override
    public void grace(String jwt, int amount, ChronoUnit unit) {
        permitsStorage.grace(jwt, amount, unit);
    }

    private Permit copy(JdbcTokenStore.Permit from) {
        return new Permit(from.token, from.username, from.expiration);
    }


}
