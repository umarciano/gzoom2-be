package it.memelabs.smartnebula.lmm.persistence.main.dao;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.stream.Collectors;

import it.memelabs.smartnebula.lmm.persistence.AbstractDaoTestIT;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import it.mapsgroup.commons.collect.Tuple2;
import it.memelabs.smartnebula.commons.DateUtil;
import it.memelabs.smartnebula.lmm.persistence.main.dto.CompanyEmployment;
import it.memelabs.smartnebula.lmm.persistence.main.dto.UserLogin;
import it.memelabs.smartnebula.lmm.persistence.main.dto.WeeklyWorkLog;
import it.memelabs.smartnebula.lmm.persistence.main.dto.WeeklyWorkLogEx;
import it.memelabs.smartnebula.lmm.persistence.main.mapper.EquipmentEmploymentExMapper;
import it.memelabs.smartnebula.lmm.persistence.main.mapper.PersonEmploymentExMapper;

/**
 * @author Andrea Fossi.
 */

public class WeeklyWorkLogDaoIT extends AbstractDaoTestIT {

    private static final Long CONSTRUCTION_SITE_ID = 101L;
    private static final Long STATE_ID_DRAFT = 201L;

    @Autowired
    private UserLoginDao userLoginDao;
    @Autowired
    private WeeklyWorkLogDao weeklyWorkLogDao;
    @Autowired
    private PersonEmploymentExMapper personEmplMapper;
    @Autowired
    private EquipmentEmploymentExMapper equipmentEmplMapper;

    @Test
    public void testFindExById() throws Exception {
        WeeklyWorkLogEx ret = weeklyWorkLogDao.findExById(101L);
        System.out.println("id=" + (ret != null ? ret.getId() : null));
    }

    @Test
    public void testFindByFilter() throws Exception {
        UserLogin user = loadAdminUser();
        WeeklyWorkLogFilter filter = new WeeklyWorkLogFilter();
        filter.setPage(1);
        filter.setSize(10);
        Tuple2<List<WeeklyWorkLog>, Integer> ret = weeklyWorkLogDao.findByFilter(filter, user);
        System.out.println("size=" + (ret != null ? ret.first().size() : null));
    }

    @Test
    public void testCompanyPersonEmpl() {
        List<CompanyEmployment> ret = weeklyWorkLogDao.findCompanyPersonEmpl(101L);
        System.out.println("size=" + (ret != null ? ret.size() : null));
    }

    @Test
    public void testCompanyEquipmentEmpl() {
        List<CompanyEmployment> ret = weeklyWorkLogDao.findCompanyEquipmentEmpl(101L);
        System.out.println("size=" + (ret != null ? ret.size() : null));
    }

    @Test
    public void testValidatePersonEmployment() {
        List<Long> ret = weeklyWorkLogDao.validatePersonEmployment(1004L, new Date(), new Date());
        System.out.println("size=" + (ret != null ? ret.size() : null));
    }

    @Test
    public void testValidateEquipmentEmployment() {
        List<Long> ret = weeklyWorkLogDao.validateEquipmentEmployment(1004L, new Date(), new Date());
        System.out.println("size=" + (ret != null ? ret.size() : null));
    }

    @Test
    public void testDeleteEmplByIdList() {
        UserLogin user = loadAdminUser();
        // Create WWL and employments
        WeeklyWorkLog wwl = recreateWWL(makeWeekDate(2000, 1, 1), null, user);
        insertAllPersonEmpl(wwl.getId(), user);
        insertAllEquipmentEmpl(wwl.getId(), user);
        // Delete all person employments by id list
        List<CompanyEmployment> emplList = weeklyWorkLogDao.findCompanyPersonEmpl(wwl.getId());
        List<Long> emplIdList = emplList.stream().map(CompanyEmployment::getEmplId).collect(Collectors.toList());
        int deletedCount = weeklyWorkLogDao.deletePersonsEmpl(wwl.getId(), emplIdList);
        Assert.assertEquals("All person empl deleted", emplList.size(), deletedCount);
        // Delete all equipment employments by id list
        emplList = weeklyWorkLogDao.findCompanyEquipmentEmpl(wwl.getId());
        emplIdList = emplList.stream().map(CompanyEmployment::getEmplId).collect(Collectors.toList());
        deletedCount = weeklyWorkLogDao.deleteEquipmentsEmpl(wwl.getId(), emplIdList);
        Assert.assertEquals("All equipment empl deleted", emplList.size(), deletedCount);
    }

    @Test
    public void testDuplicate() {
        UserLogin user = loadAdminUser();
        //
        WeeklyWorkLog wwl1 = recreateWWL(makeWeekDate(2001, 1, 1), null, user);
        insertAllPersonEmpl(wwl1.getId(), user);
        insertAllEquipmentEmpl(wwl1.getId(), user);
        WeeklyWorkLog wwl2 = recreateWWL(makeWeekDate(2001, 2, 1), null, user);
        //
        int result = weeklyWorkLogDao.duplicatePersonEmployment(wwl2.getId(), wwl1.getId(), user);
        System.out.println("duplicated person empl count: " + result);
        result = weeklyWorkLogDao.duplicateEquipmentEmployment(wwl2.getId(), wwl1.getId(), user);
        System.out.println("duplicated equipment empl count: " + result);
        //
        List<CompanyEmployment> listEmpl1 = weeklyWorkLogDao.findCompanyPersonEmpl(wwl1.getId());
        Assert.assertNotNull(listEmpl1);
        List<CompanyEmployment> listEmpl2 = weeklyWorkLogDao.findCompanyPersonEmpl(wwl2.getId());
        Assert.assertNotNull(listEmpl2);
        Assert.assertTrue("person list equals", listMatch(listEmpl1, listEmpl2, this::compareCompanyEmployment));
        //
        listEmpl1 = weeklyWorkLogDao.findCompanyEquipmentEmpl(wwl1.getId());
        Assert.assertNotNull(listEmpl1);
        listEmpl2 = weeklyWorkLogDao.findCompanyEquipmentEmpl(wwl2.getId());
        Assert.assertNotNull(listEmpl2);
        Assert.assertTrue("equipment list equals", listMatch(listEmpl1, listEmpl2, this::compareCompanyEmployment));
    }

    // Private methods

    private Date makeDate(int year, int month, int day) {
        return new GregorianCalendar(year, month - 1, day).getTime();
    }

    private Date makeWeekDate(int year, int month, int day) {
        return DateUtil.getFirstDateOfCalendarWeek(makeDate(year, month, day));
    }

    private <A, B> boolean listMatch(List<A> list1, List<B> list2, BiPredicate<A, B> predicate) {
        return list1.stream().allMatch(item1 -> list2.stream().anyMatch(item2 -> predicate.test(item1, item2)));
    }

    private UserLogin loadAdminUser() {
        return userLoginDao.getUserLogin(66L);
    }

    private List<WeeklyWorkLog> findByDate(Date logDate, UserLogin user) {
        WeeklyWorkLogFilter filter = new WeeklyWorkLogFilter();
        filter.setConstructionSiteId(CONSTRUCTION_SITE_ID);
        filter.setFromDate(logDate);
        filter.setToDate(logDate);
        Tuple2<List<WeeklyWorkLog>, Integer> list = weeklyWorkLogDao.findByFilter(filter, user);
        return list.first();
    }

    private WeeklyWorkLog createWWL(Date logDate, Long stateId, UserLogin user) {
        WeeklyWorkLog wwl = new WeeklyWorkLog();
        wwl.setLogDate(logDate);
        wwl.setConstructionSiteId(CONSTRUCTION_SITE_ID);
        wwl.setStateId(STATE_ID_DRAFT);
        Long wwlId = weeklyWorkLogDao.create(wwl, user);
        Assert.assertNotNull(wwlId);
        return wwl;
    }

    private WeeklyWorkLog recreateWWL(Date logDate, Long stateId, UserLogin user) {
        deleteAll(findByDate(logDate, user));
        return createWWL(logDate, stateId, user);
    }

    private void deleteAll(List<WeeklyWorkLog> list) {
        list.forEach(item -> {
            weeklyWorkLogDao.deletePersonsEmpl(item.getId(), null);
            weeklyWorkLogDao.deleteEquipmentsEmpl(item.getId(), null);
            weeklyWorkLogDao.delete(item.getId());
        });
    }

    private void insertAllPersonEmpl(long wwlId, UserLogin user) {
        personEmplMapper.selectByExample(null).forEach(empl -> weeklyWorkLogDao.createPersonEmpl(wwlId, empl.getId(), user));
    }

    private void insertAllEquipmentEmpl(long wwlId, UserLogin user) {
        equipmentEmplMapper.selectByExample(null).forEach(empl -> weeklyWorkLogDao.createEquipmentEmpl(wwlId, empl.getId(), user));
    }

    private boolean compareCompanyEmployment(CompanyEmployment empl1, CompanyEmployment empl2) {
        return empl2.getCompanyId() == empl1.getCompanyId() //
                && empl2.getEmplId() == empl1.getEmplId();
    }
}
