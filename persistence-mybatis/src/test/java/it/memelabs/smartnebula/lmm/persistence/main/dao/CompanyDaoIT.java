package it.memelabs.smartnebula.lmm.persistence.main.dao;

import it.mapsgroup.commons.collect.Tuple2;
import it.memelabs.smartnebula.commons.DateUtil;
import it.memelabs.smartnebula.lmm.persistence.AbstractDaoTestIT;
import it.memelabs.smartnebula.lmm.persistence.main.dto.CompanyEx;
import it.memelabs.smartnebula.lmm.persistence.main.dto.Node;
import it.memelabs.smartnebula.lmm.persistence.main.dto.UserLogin;
import org.junit.Test;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author Andrea Fossi.
 */

public class CompanyDaoIT  extends AbstractDaoTestIT {
    private static final Logger LOG = getLogger(CompanyDaoIT.class);

    @Autowired
    private CompanyDao companyDao;

    @Test
    public void testFindExById() throws Exception {
        CompanyEx ret = companyDao.findExById(101l);
        System.out.println("id=" + (ret != null ? ret.getId() : null));
    }

    @Test
    public void testFindByFilter() throws Exception {
        Node node = new Node();
        node.setId(1L);
        UserLogin userLogin = new UserLogin();
        userLogin.setActiveNode(node);
        CompanyFilter filter = new CompanyFilter();
        filter.setPage(1);
        filter.setSize(10);
        Tuple2<List<CompanyEx>, Integer> ret = companyDao.selectByFilter(filter, userLogin);
        System.out.println("size=" + (ret != null ? ret.first().size() : null));
    }

    @Test
    public void testAddress() throws Exception {
        CompanyEx ret = companyDao.findExById(101l);
        System.out.println("id=" + (ret != null ? ret.getId() : null));
        if (ret != null) {
            System.out.println("address=" + ret.getAddress());
        }
    }

    @Test
    public void testDaoWeeklyWorkLogQuery() throws Exception {
        Node node = new Node();
        node.setId(1L);
        UserLogin userLogin = new UserLogin();
        userLogin.getNodes().add(node);
        Tuple2<Date, Date> w = DateUtil.getDatesOfCalendarWeek(27, 2016);
        Tuple2<List<CompanyEx>, Integer> ret = companyDao.findForWeeklyWorkLog(1, Integer.MAX_VALUE, w.first(), w.second(),1002L,null, userLogin);
        LOG.info("Found {} items: {}", ret.second(), ret.first().stream().map(c -> c.getId() + " " + c.getBusinessName()).collect(Collectors.joining(", ")));
        assertTrue(ret.second() > 0);
        assertEquals(ret.first().size(), (int) ret.second());
    }
}
