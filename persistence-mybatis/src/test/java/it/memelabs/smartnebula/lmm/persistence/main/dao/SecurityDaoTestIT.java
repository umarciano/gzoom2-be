package it.memelabs.smartnebula.lmm.persistence.main.dao;

import it.memelabs.smartnebula.lmm.persistence.AbstractDaoTestIT;
import it.memelabs.smartnebula.lmm.persistence.security.dao.SecurityDao;
import org.junit.Test;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import javax.sql.DataSource;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author Andrea Fossi.
 */

public class SecurityDaoTestIT extends AbstractDaoTestIT {
    private static final Logger LOG = getLogger(SecurityDaoTestIT.class);
    @Autowired
    private DataSource ds;


    @Test
    public void name() throws Exception {
        boolean ret = new SecurityDao(ds).check("company", 101L, 1L);
        LOG.info("" + ret);
    }
}
