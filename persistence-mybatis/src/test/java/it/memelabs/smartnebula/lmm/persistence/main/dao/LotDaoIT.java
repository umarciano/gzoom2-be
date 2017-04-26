package it.memelabs.smartnebula.lmm.persistence.main.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import it.memelabs.smartnebula.lmm.persistence.AbstractDaoTestIT;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import it.mapsgroup.commons.collect.Tuple2;
import it.memelabs.smartnebula.lmm.persistence.main.dto.LotEx;
import it.memelabs.smartnebula.lmm.persistence.main.dto.Node;
import it.memelabs.smartnebula.lmm.persistence.main.dto.UserLogin;

/**
 * @author Andrea Fossi.
 */

public class LotDaoIT extends AbstractDaoTestIT {
    @Autowired
    private LotDao lotDao;

    @Test
    public void testFindById() throws Exception {
        LotEx ret = lotDao.findExById(101);
        assertNotNull(ret);
        assertNotNull(ret.getJobOrder());
        assertNotNull(ret.getWorksManager());
        assertNotNull(ret.getAssignedCompany());
        assertNotNull(ret.getDtl());
        assertNotNull(ret.getLocations());
        assertEquals(ret.getLocations().size(), 1);
    }

    @Test
    public void testFindByFilter() throws Exception {
        Node node = new Node();
        node.setId(1L);
        UserLogin userLogin = new UserLogin();
        userLogin.getNodes().add(node);
        LotFilter filter = new LotFilter();
        filter.setPage(1);
        filter.setSize(10);
        Tuple2<List<LotEx>, Integer> ret = lotDao.findByFilter(filter, userLogin);
        assertTrue(ret.first().size()>0);
        LotEx lot=ret.first().get(0);
        assertNotNull(lot.getJobOrder());
        assertNotNull(lot.getWorksManager());
        assertNotNull(lot.getAssignedCompany());
        assertNotNull(lot.getDtl());
        assertNotNull(lot.getLocations());
        assertEquals(lot.getLocations().size(),0);
    }

}
