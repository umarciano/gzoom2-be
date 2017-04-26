package it.memelabs.smartnebula.lmm.persistence.security.dao;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author Andrea Fossi.
 *         Execute a query that check if object id and ownerNodeId are choerent
 */
@Service
public class SecurityDao {
    private static final Logger LOG = getLogger(SecurityDao.class);

    private final DataSource dataSource;

    @Autowired
    public SecurityDao(@Qualifier(value = "mainDataSource") DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * Return true if record with passed id belongs to ownerNodeId
     *
     * @param query
     * @param id
     * @param nodeId
     * @return
     */
    public boolean check(String query, Long id, Long nodeId) {
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            //log format is similar to mybatis
            if (LOG.isDebugEnabled()) {
                LOG.debug("====>  Preparing: {}", query);
                LOG.debug("====> Parameters: {}(long), {}(long)", id, nodeId);
            }
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, id);
            statement.setLong(2, nodeId);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            int count = resultSet.getInt(1);
            if (LOG.isDebugEnabled()) LOG.debug("<====      Total: {}", count);
            resultSet.close();
            statement.close();
            if (count > 0) return true;
        } catch (SQLException e) {
            LOG.error("Error execution query", e);
        } finally {
            if (connection != null) try {
                connection.close();
            } catch (SQLException e) {
                LOG.error("Error closing connection", e);
            }
        }
        return false;
    }


}
