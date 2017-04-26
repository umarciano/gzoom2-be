package it.memelabs.smartnebula.lmm.persistence.security.dao;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.sql.DataSource;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * jdbc storage of permits.
 *
 * @author Andrea Fossi
 */
@Service("jdbcTokenStore")
public class JdbcTokenStore {

    private static final Logger LOG = getLogger(JdbcTokenStore.class);

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcTokenStore(@Qualifier(value = "mainDataSource") DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private static final String INSERT = "INSERT INTO security_token (token, username, expiration, created_stamp, updated_stamp) VALUES (?, ?, ?,?,?)";
    private static final String DELETE = "DELETE FROM security_token WHERE token = ?";
    private static final String SELECT = "UPDATE  security_token SET updated_stamp = ? WHERE token = ?  RETURNING token, username, expiration, created_stamp, updated_stamp";
    //private static final String SELECT = "SELECT token, username, expiration, created_stamp, updated_stamp  FROM security_token WHERE token = ?";


    /**
     * Saves the JWT and other user details.
     *
     * @param jwt      The JWT
     * @param userName Username
     */
    public void save(String jwt, String userName, int tokenExpiryMinutes) {
        int min = tokenExpiryMinutes;
        if (min <= 0) {
            min = 60 * 24 * 100; // minutes in 100 days
        }
        LocalDateTime exp = LocalDateTime.now().plus(min, ChronoUnit.MINUTES);
        //permits.put(jwt, new Permit(jwt, userName, exp));
        if (LOG.isDebugEnabled()) LOG.debug("====>  Preparing: {}", INSERT);
        if (LOG.isDebugEnabled()) LOG.debug("====> Parameters: {}(String)", jwt);
        Timestamp timestamp = Timestamp.valueOf(exp);
        Timestamp now = Timestamp.valueOf(LocalDateTime.now());
        int resultSet = jdbcTemplate.update(INSERT, jwt, userName, timestamp, now, now);
        if (resultSet == 0) {
            LOG.warn("Token not saved {}", jwt);
        }
    }

    /**
     * Removes a permit associated to a certain JWT.
     *
     * @param jwt The JWT
     * @return The pruned permit or null if no one is associated to the JWT
     */
    public Permit prune(String jwt) {
        Permit ret = null;
        ret = load(jwt);
        //log format is similar to mybatis
        if (LOG.isDebugEnabled()) LOG.debug("====>  Preparing: {}", DELETE);
        if (LOG.isDebugEnabled()) LOG.debug("====> Parameters: {}(String)", jwt);
        int resultSet = jdbcTemplate.update(DELETE, jwt);
        if (resultSet == 0) {
            ret = null;
            LOG.warn("Token not found {}", jwt);
        }
        return ret;
    }

    /**
     * Loads a permit associated to a certain JWT.
     *
     * @param jwt The JWT
     * @return The permit
     */
    public Permit load(String jwt) {
        Permit ret;
        //log format is similar to mybatis
        if (LOG.isDebugEnabled()) LOG.debug("====>  Preparing: {}", SELECT);
        if (LOG.isDebugEnabled()) LOG.debug("====> Parameters: {}(String)", jwt);
        try {
            ret = jdbcTemplate.queryForObject(SELECT, (rs, rowNum) -> {
                String userName = rs.getString(2);
                LocalDateTime expiration = rs.getTimestamp(3).toLocalDateTime();
                return new Permit(jwt, userName, expiration);
            }, Timestamp.valueOf(LocalDateTime.now()), jwt);
            //catch exception given when no record found
        } catch (EmptyResultDataAccessException e) {
            ret = null;
        }
        if (ret != null) {
            if (LOG.isDebugEnabled()) LOG.debug("<====      Token found");
        }
        return ret;
    }


    /**
     * Graces the current permit of a specified amount of time.
     *
     * @param jwt    The JWT
     * @param amount The amount of time of the grace
     * @param unit   The unit of time to be added to the expiration date
     */

    public void grace(String jwt, int amount, ChronoUnit unit) {
        throw new NotImplementedException();
       /* permits.computeIfPresent(jwt, (t, permit) -> {
            LocalDateTime e = permit.expiration.plus(amount, unit);
            return new Permit(permit.token, permit.username, e);
        });*/
    }

    public class Permit {
        public final String token;
        public final String username;
        public final LocalDateTime expiration;

        public Permit(String token, String username, LocalDateTime expiration) {
            this.token = token;
            this.username = username;
            this.expiration = expiration;
        }
    }
}
