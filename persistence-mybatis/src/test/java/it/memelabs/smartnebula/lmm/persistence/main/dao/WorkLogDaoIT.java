package it.memelabs.smartnebula.lmm.persistence.main.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import it.memelabs.smartnebula.lmm.persistence.AbstractDaoTestIT;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import it.mapsgroup.commons.collect.Tuple2;
import it.memelabs.smartnebula.lmm.persistence.main.dto.*;

/**
 * @author Andrea Fossi.
 */

public class WorkLogDaoIT extends AbstractDaoTestIT {

    @Autowired
    private WorkLogDao workLogDao;

    @Test
    public void testFindExById() throws Exception {
        WorkLogEx ret = workLogDao.findExById(101L);
        System.out.println("id=" + (ret != null ? ret.getId() : null));
    }

    @Test
    public void testFindByFilter() throws Exception {
        Node node = new Node();
        node.setId(1L);
        UserLogin userLogin = new UserLogin();
        userLogin.getNodes().add(node);
        WorkLogFilter filter = new WorkLogFilter();
        filter.setPage(1);
        filter.setSize(10);
        Tuple2<List<WorkLog>, Integer> ret = workLogDao.findByFilter(filter, userLogin);
        System.out.println("size=" + (ret != null ? ret.first().size() : null));
    }

    @Test
    public void testCompanyPersonEmpl() {
        List<CompanyEmployment> ret = workLogDao.findCompanyPersonEmpl(101L);
        System.out.println("size=" + (ret != null ? ret.size() : null));
    }

    @Test
    public void testCompanyEquipmentEmpl() {
        List<CompanyEmployment> ret = workLogDao.findCompanyEquipmentEmpl(101L);
        System.out.println("size=" + (ret != null ? ret.size() : null));
    }

    @Test
    public void testValidatePersonEmployment() {
        List<Long> ret = workLogDao.validatePersonEmployment(1004L, new Date());
        System.out.println("size=" + (ret != null ? ret.size() : null));
    }

    @Test
    public void testValidateEquipmentEmployment() {
        List<Long> ret = workLogDao.validateEquipmentEmployment(1004L, new Date());
        System.out.println("size=" + (ret != null ? ret.size() : null));
    }
    @Test
    public void testCountEventsByWorkLogId() {
        List<Map<String, Object>> ret = workLogDao.countEventsByWorkLogId(1100L);
        System.out.println("size=" + (ret != null ? ret.size() : null));
    }
}
