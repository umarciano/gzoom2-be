package it.memelabs.smartnebula.lmm.persistence.main.dao;

import it.memelabs.smartnebula.lmm.persistence.AbstractDaoTestIT;
import it.memelabs.smartnebula.lmm.persistence.main.dto.NodeConfigurationEx;
import org.junit.Test;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author Andrea Fossi.
 */

public class NodeConfigurationDaoIT extends AbstractDaoTestIT {
    private static final Logger LOG = getLogger(NodeConfigurationDaoIT.class);

    @Autowired
    private NodeConfigurationDao nodeConfigurationDao;

    @Test
    public void testFindExById() throws Exception {
        NodeConfigurationEx ret = nodeConfigurationDao.findById(1L);
        System.out.println("id=" + (ret != null ? ret.getId() : null));
    }


}
