package it.memelabs.smartnebula.lmm.persistence.main.dao;

import it.mapsgroup.commons.collect.Tuple2;
import it.memelabs.smartnebula.lmm.persistence.AbstractDaoTestIT;
import it.memelabs.smartnebula.lmm.persistence.main.dto.SecurityRoleEx;
import org.junit.Test;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author Andrea Fossi.
 */

public class RoleDaoIT extends AbstractDaoTestIT {
    private static final Logger LOG = getLogger(RoleDaoIT.class);

    @Autowired
    private SecurityRoleDao roleDao;

    @Test
    public void testFindByFilter() throws Exception {
        RoleFilter filter = new RoleFilter();
        filter.setPage(1);
        filter.setSize(10);
        Tuple2<List<SecurityRoleEx>, Integer> ret = roleDao.find(filter);
        System.out.println("id=" + (ret != null ? ret.second() : null));
    }
    @Test
    public void testFindId() throws Exception {
        SecurityRoleEx ret = roleDao.find(1L);
        System.out.println("id=" + (ret != null ? ret.getId() : null));
    }


}
