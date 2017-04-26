package it.memelabs.smartnebula.lmm.persistence.main.dao;

import it.mapsgroup.commons.collect.Tuple2;
import it.memelabs.smartnebula.commons.DateUtil;
import it.memelabs.smartnebula.lmm.persistence.AbstractDaoTestIT;
import it.memelabs.smartnebula.lmm.persistence.main.dto.*;
import it.memelabs.smartnebula.lmm.persistence.main.enumeration.EntityStateTag;
import it.memelabs.smartnebula.lmm.persistence.main.mapper.EquipmentEmploymentExMapper;
import org.apache.ibatis.session.RowBounds;
import org.junit.Test;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author Andrea Fossi.
 */

public class EquipmentEmploymentDaoIT extends AbstractDaoTestIT {
    private static final Logger LOG = getLogger(EquipmentEmploymentDaoIT.class);

    @Autowired
    private EquipmentEmploymentDao dao;
    @Autowired
    private EquipmentEmploymentExMapper mapper;

    @Test
    public void testFindById() throws Exception {
        /*EquipmentEx ret = dao.findExById(1001l);
        ret.getId();*/
    }

    @Test
    public void testMapperWeeklyWorkLogQuery() throws Exception {
        Node node = new Node();
        node.setId(1L);
        UserLogin userLogin = new UserLogin();
        userLogin.getNodes().add(node);
        EquipmentEmploymentWwlExample example = new EquipmentEmploymentWwlExample();
        EquipmentEmploymentWwlExample.Criteria criteria = example.createCriteria();
        RowBounds rowBounds = new RowBounds(0, 100);
        Tuple2<Date, Date> w = DateUtil.getDatesOfCalendarWeek(27, 2016);
        criteria.andCompanyIdEqualTo(102L);
        criteria.andOwnerNodeIdEqualTo(1L);
        example.setEmploymentStartDate(w.first());
        example.setEmploymentEndDate(w.second());
        example.setCompanyStateTag(EntityStateTag.AUTHORIZED);
        criteria.andStateTagEqualTo(EntityStateTag.AUTHORIZED);
        example.setWeeklyWorkLogId(1002L);
        List<EquipmentEmploymentEx> ret = mapper.selectWeekyWorkLogByExampleWithRowbounds(example, rowBounds);
        int c = mapper.countWeekyWorkLogByExample(example);
        String str = ret.stream().map(EquipmentEmploymentEx::getEquipment).map(p -> p.getRegistrationNumber() + " " + p.getId()).collect(Collectors.joining(","));
        LOG.info(str);
        ret.size();
        LOG.debug("" + c);
    }

    @Test
    public void testDaoWeeklyWorkLogQuery() throws Exception {
        Node node = new Node();
        node.setId(1L);
        UserLogin userLogin = new UserLogin();
        userLogin.getNodes().add(node);
        Tuple2<Date, Date> w = DateUtil.getDatesOfCalendarWeek(23, 2016);
        Tuple2<List<EquipmentEmploymentEx>, Integer> ret = dao.findForWeeklyWorkLog(1, 200, 103L, 1002L,null,w.first(), w.second(), userLogin);
        ret.first().size();
    }
}
