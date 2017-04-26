package it.memelabs.smartnebula.lmm.persistence.security.dao;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * jdbc storage of permits.
 *
 * @author Andrea Fossi
 */
@Service("jdbcKeyStore")
public class JdbcKeyStore {
    private static final Logger LOG = getLogger(JdbcKeyStore.class);
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcKeyStore(@Qualifier(value = "mainDataSource") DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private static final String INSERT = "INSERT INTO security_token_key (jwt_key, created_stamp) VALUES (?, ?)";
    private static final String SELECT = "SELECT jwt_key FROM security_token_key";

    /**
     * Save JWT public key
     *
     * @param key
     */
    public void saveKey(String key) {
        LocalDateTime exp = LocalDateTime.now();
        if (LOG.isDebugEnabled()) LOG.debug("====>  Preparing: {}", INSERT);
        int resultSet = jdbcTemplate.update(INSERT, key, Timestamp.valueOf(exp));
        if (resultSet == 0) {
            LOG.warn("Key not saved {}", "*******");
        }
    }

    /**
     * Loads JWT key.
     *
     * @return The public JWT key
     */
    public String loadKey() {
        String ret = null;
        //log format is similar to mybatis
        if (LOG.isDebugEnabled()) LOG.debug("====>  Preparing: {}", SELECT);
        List<String> keys = jdbcTemplate.queryForList(SELECT, String.class);
        if (keys.size() > 0) {
            if (LOG.isDebugEnabled()) LOG.debug("<====      Token found");
            ret = keys.get(0);
        } else {
            LOG.warn("Key not found");
        }
        return ret;
    }
}
