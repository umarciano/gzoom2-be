package it.memelabs.smartnebula.lmm.persistence.main.dao;

import it.mapsgroup.commons.collect.Tuple2;
import it.memelabs.smartnebula.commons.DateUtil;
import it.memelabs.smartnebula.lmm.persistence.AbstractDaoTestIT;
import it.memelabs.smartnebula.lmm.persistence.main.dto.*;
import it.memelabs.smartnebula.lmm.persistence.main.enumeration.EntityStateTag;
import it.memelabs.smartnebula.lmm.persistence.main.util.Dummies;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertNotNull;

/**
 * @author Andrea Fossi.
 */

public class ContractDaoIT  extends AbstractDaoTestIT {
    @Autowired
    private ContractDao contractDao;

    @Test
    public void testFindById() throws Exception {
        ContractEx ret = contractDao.findExById(1001L);
        if (ret != null) {
            AntimafiaProcessEx antimafiaProcess = ret.getAntimafiaProcess();
            if (antimafiaProcess != null) {
                System.out.println("AP.lot = " + (antimafiaProcess.getLot() != null ? antimafiaProcess.getLot().getId() : null));
                System.out.println("AP.prefecture = " + (antimafiaProcess.getPrefecture() != null ? antimafiaProcess.getPrefecture().getId() : null));
            }
        }
        ret.getId();
    }

    @Test
    public void testFindByFilter() throws Exception {
        ContractFilter filter = new ContractFilter();
        filter.setCup("cup");
        Tuple2<List<ContractEx>, Integer> ret = contractDao.findExByFilter(filter, Dummies.getUserLogin());
        ret.first().size();
    }

    @Test
    public void testFindByFilterMgo() throws Exception {
        Node node = new Node();
        node.setId(1L);
        UserLogin userLogin = new UserLogin();
        userLogin.getNodes().add(node);
        ContractFilter filter = new ContractFilter();
        //filter.setCup("cup");
        Tuple2<List<ContractExMgo>, Integer> ret = contractDao.findExByFilterMgo(filter, userLogin);
        ret.first().size();
    }

    @Test
    public void testSelectMissingMgoDataIT() throws Exception {
        List<ContractEx> ret = contractDao.findMissingMgoData(1L, DateUtil.addDays(new Date(), -10), new Date());
        assertNotNull(ret);
    }

    @Test
    public void testFindByFilterTag() throws Exception {
        ContractFilter filter = new ContractFilter();
        filter.setStateTag(EntityStateTag.INTEGRATION_NEEDED);
        Tuple2<List<ContractEx>, Integer> ret = contractDao.findExByFilter(filter, Dummies.getUserLogin());
        ret.first().size();
    }

}
