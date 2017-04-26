package it.memelabs.smartnebula.lmm.persistence.main.dao;

import it.mapsgroup.commons.collect.Tuple2;
import it.memelabs.smartnebula.lmm.persistence.AbstractDaoTestIT;
import it.memelabs.smartnebula.lmm.persistence.main.dto.EquipmentEx;
import it.memelabs.smartnebula.lmm.persistence.main.dto.Node;
import it.memelabs.smartnebula.lmm.persistence.main.dto.UserLogin;
import it.memelabs.smartnebula.lmm.persistence.main.util.Dummies;
import org.junit.Test;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author Andrea Fossi.
 */

public class EquipmentDaoIT extends AbstractDaoTestIT {
    private static final Logger LOG = getLogger(EquipmentDaoIT.class);

    @Autowired
    private EquipmentDao equipmentDao;

    @Test
    public void testFindById() throws Exception {
        EquipmentEx ret = equipmentDao.findExById(1001l);
        ret.getId();
    }

    @Test
    public void testCount() throws Exception {
        Node node = new Node();
        node.setId(1L);
        UserLogin userLogin = new UserLogin();
        userLogin.getNodes().add(node);
        EquipmentFilter filter = new EquipmentFilter();
        filter.setPage(1);
        filter.setSize(1000);
        filter.setCompanyId(12L);
        Tuple2<List<EquipmentEx>, Integer> ret = equipmentDao.findByFilter(filter, userLogin);
        LOG.info("size:" + ret.first().size());
    }

    @Test
    public void testFindByFilter() throws Exception {
        EquipmentFilter filter = new EquipmentFilter();
        filter.setFilterCompanyStatusId(2102L);
        filter.setFilterType("equipments");
        Tuple2<List<EquipmentEx>, Integer> ret = equipmentDao.findByFilter(filter, Dummies.getUserLogin(2L));
        ret.first().size();
    }
}
