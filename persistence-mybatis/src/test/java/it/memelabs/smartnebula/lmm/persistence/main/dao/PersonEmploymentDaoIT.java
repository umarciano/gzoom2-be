package it.memelabs.smartnebula.lmm.persistence.main.dao;

import it.mapsgroup.commons.collect.Tuple2;
import it.memelabs.smartnebula.commons.DateUtil;
import it.memelabs.smartnebula.lmm.persistence.AbstractDaoTestIT;
import it.memelabs.smartnebula.lmm.persistence.main.dto.*;
import it.memelabs.smartnebula.lmm.persistence.main.enumeration.EntityStateTag;
import it.memelabs.smartnebula.lmm.persistence.main.mapper.PersonEmploymentExMapper;
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

public class PersonEmploymentDaoIT extends AbstractDaoTestIT {
    private static final Logger LOG = getLogger(PersonEmploymentDaoIT.class);

    @Autowired
    private PersonEmploymentDao dao;
    @Autowired
    private PersonEmploymentExMapper mapper;

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
        PersonEmploymentWwlExample example = new PersonEmploymentWwlExample();
        PersonEmploymentWwlExample.Criteria criteria = example.createCriteria();
        RowBounds rowBounds = new RowBounds(0, 100);
        Tuple2<Date, Date> w = DateUtil.getDatesOfCalendarWeek(23, 2016);
        criteria.andCompanyIdEqualTo(103L);
        criteria.andOwnerNodeIdEqualTo(1L);
        example.setFilterEmploymentStartDate(w.first());
        example.setFilterEmploymentEndDate(w.second());
        criteria.andStateTagEqualTo(EntityStateTag.AUTHORIZED);
        example.setWeeklyWorkLogId(1000L);
        List<PersonEmploymentEx> ret = mapper.selectWeekyWorkLogByExampleWithRowbounds(example, rowBounds);
        int c = mapper.countWeekyWorkLogByExample(example);
        ret.size();
        String str = ret.stream().map(PersonEmploymentEx::getPerson).map(p -> p.getFirstName() + " " + p.getLastName() + " " + p.getId()).collect(Collectors.joining(","));
        LOG.debug(str);
        LOG.debug("" + c);
    }

    @Test
    public void testDaoWeeklyWorkLogQuery() throws Exception {
        Node node = new Node();
        node.setId(1L);
        UserLogin userLogin = new UserLogin();
        userLogin.getNodes().add(node);
        Tuple2<Date, Date> w = DateUtil.getDatesOfCalendarWeek(23, 2016);
        Tuple2<List<PersonEmploymentEx>, Integer> ret = dao.findForWeeklyWorkLog(1, 200, 103L, null,null,w.first(), w.second(), userLogin);
        ret.first().size();
    }
}
