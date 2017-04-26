package it.memelabs.smartnebula.lmm.persistence.main.dao;

import it.mapsgroup.commons.collect.Tuple2;
import it.memelabs.smartnebula.lmm.persistence.main.dto.*;
import it.memelabs.smartnebula.lmm.persistence.main.mapper.WeeklyWorkLogEquipmentExMapper;
import it.memelabs.smartnebula.lmm.persistence.main.mapper.WeeklyWorkLogExMapper;
import it.memelabs.smartnebula.lmm.persistence.main.mapper.WeeklyWorkLogPersonExMapper;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @author sivi.
 */
@Service
public class WeeklyWorkLogDao {
    // private static final Logger LOG = getLogger(PersonDao.class);

    private final WeeklyWorkLogExMapper mapper;
    private final WeeklyWorkLogPersonExMapper personMapper;
    private final WeeklyWorkLogEquipmentExMapper equipmentMapper;

    @Autowired
    public WeeklyWorkLogDao(WeeklyWorkLogExMapper mapper, WeeklyWorkLogPersonExMapper personMapper, WeeklyWorkLogEquipmentExMapper equipmentMapper) {
        this.mapper = mapper;
        this.personMapper = personMapper;
        this.equipmentMapper = equipmentMapper;
    }

    public WeeklyWorkLog findById(long id) {
        return mapper.selectByPrimaryKey(id);
    }

    public WeeklyWorkLogEx findExById(long id) {
        return mapper.selectExByPrimaryKey(id);
    }

    public List<CompanyEmployment> findCompanyPersonEmpl(long id) {
        return mapper.selectCompanyPersonEmpl(id);
    }

    public List<CompanyEmployment> findCompanyEquipmentEmpl(long id) {
        return mapper.selectCompanyEquipmentEmpl(id);
    }

    public Tuple2<List<WeeklyWorkLog>, Integer> findByFilter(WeeklyWorkLogFilter filter, UserLogin user) {
        WeeklyWorkLogExample example = makeWeeklyWorkLogExample(filter, user);
        int count = mapper.countByExample(example);
        List<WeeklyWorkLog> list = mapper.selectByExampleWithRowbounds(example, filter.makeRowBounds());
        return new Tuple2<>(list, count);
    }

    public Tuple2<List<WeeklyWorkLogEx>, Integer> findExByFilter(WeeklyWorkLogFilter filter, UserLogin user) {
        WeeklyWorkLogExample example = makeWeeklyWorkLogExample(filter, user);
        int count = mapper.countByExample(example);
        List<WeeklyWorkLogEx> list = mapper.selectExByExampleWithRowbounds(example, filter.makeRowBounds());
        return new Tuple2<>(list, count);
    }

    public WeeklyWorkLog findPrevious(long constructionSiteId, Date logDate, UserLogin user) {
        WeeklyWorkLogExample example = new WeeklyWorkLogExample();
        example.setOrderByClause("log_date desc");
        WeeklyWorkLogExample.Criteria criteria = example.createCriteria();
        criteria.andOwnerNodeIdEqualTo(user.getActiveNode().getId());
        criteria.andConstructionSiteIdEqualTo(constructionSiteId);
        criteria.andLogDateLessThan(logDate);
        // first result only
        List<WeeklyWorkLog> list = mapper.selectByExampleWithRowbounds(example, new RowBounds(0, 1));
        return list != null && !list.isEmpty() ? list.get(0) : null;
    }

    public Long create(WeeklyWorkLog record, UserLogin user) {
        Date createdStamp = new Date();
        record.setModifiedStamp(createdStamp);
        record.setModifiedByUserId(user.getId());
        record.setCreatedStamp(createdStamp);
        record.setCreatedByUserId(user.getId());
        record.setOwnerNodeId(user.getActiveNode().getId());
        int result = mapper.insert(record);
        return (result > 0) ? record.getId() : null;
    }

    public boolean createPersonEmpl(long weeklyWorkLogId, long personEmplId, UserLogin user) {
        Date createdStamp = new Date();
        WeeklyWorkLogPerson record = new WeeklyWorkLogPerson();
        record.setCreatedStamp(createdStamp);
        record.setCreatedByUserId(user.getId());
        record.setWeeklyWorkLogId(weeklyWorkLogId);
        record.setPersonEmplId(personEmplId);
        int result = personMapper.insert(record);
        return result > 0;
    }

    public boolean createEquipmentEmpl(long weeklyWorkLogId, long equipmentEmplId, UserLogin user) {
        Date createdStamp = new Date();
        WeeklyWorkLogEquipment record = new WeeklyWorkLogEquipment();
        record.setCreatedStamp(createdStamp);
        record.setCreatedByUserId(user.getId());
        record.setWeeklyWorkLogId(weeklyWorkLogId);
        record.setEquipmentEmplId(equipmentEmplId);
        int result = equipmentMapper.insert(record);
        return result > 0;
    }

    public boolean update(WeeklyWorkLog record, UserLogin user) {
        record.setModifiedStamp(new Date());
        record.setModifiedByUserId(user.getId());
        record.setOwnerNodeId(user.getActiveNode().getId());
        int result = mapper.updateByPrimaryKey(record);
        return result > 0;
    }

    public boolean update(WeeklyWorkLog record, WeeklyWorkLogExample example, UserLogin user) {
        record.setModifiedStamp(new Date());
        record.setModifiedByUserId(user.getId());
        record.setOwnerNodeId(user.getActiveNode().getId());
        int result = mapper.updateByExample(record, example);
        return result > 0;
    }

    public boolean delete(long id) {
        int result = mapper.deleteByPrimaryKey(id);
        return result > 0;
    }

    public int deletePersonsEmpl(long weeklyWorkLogId, List<Long> emplIdList) {
        WeeklyWorkLogPersonExample example = new WeeklyWorkLogPersonExample();
        WeeklyWorkLogPersonExample.Criteria criteria = example.createCriteria();
        criteria.andWeeklyWorkLogIdEqualTo(weeklyWorkLogId);
        if (emplIdList != null && !emplIdList.isEmpty()) {
            criteria.andPersonEmplIdIn(emplIdList);
        }
        return personMapper.deleteByExample(example);
    }

    public int deleteEquipmentsEmpl(long weeklyWorkLogId, List<Long> emplIdList) {
        WeeklyWorkLogEquipmentExample example = new WeeklyWorkLogEquipmentExample();
        WeeklyWorkLogEquipmentExample.Criteria criteria = example.createCriteria();
        criteria.andWeeklyWorkLogIdEqualTo(weeklyWorkLogId);
        if (emplIdList != null && !emplIdList.isEmpty()) {
            criteria.andEquipmentEmplIdIn(emplIdList);
        }
        return equipmentMapper.deleteByExample(example);
    }

    /**
     * @param weeklyWorkLogId
     * @param employmentStartDate
     * @param employmentEndDate
     * @return invalid personEmploymentIds
     */
    public List<Long> validatePersonEmployment(long weeklyWorkLogId, Date employmentStartDate, Date employmentEndDate) {
        return mapper.validatePersonEmployment(weeklyWorkLogId, employmentStartDate, employmentEndDate);
    }

    /**
     * @param weeklyWorkLogId
     * @param employmentStartDate
     * @param employmentEndDate
     * @return equipmentEmploymentIds
     */
    public List<Long> validateEquipmentEmployment(long weeklyWorkLogId, Date employmentStartDate, Date employmentEndDate) {
        return mapper.validateEquipmentEmployment(weeklyWorkLogId, employmentStartDate, employmentEndDate);
    }

    public int duplicatePersonEmployment(long weeklyWorkLogId, long weeklyWorkLogIdOrig, UserLogin user) {
        Date createdStamp = new Date();
        WeeklyWorkLogPersonEx record = new WeeklyWorkLogPersonEx();
        record.setCreatedStamp(createdStamp);
        record.setCreatedByUserId(user.getId());
        record.setWeeklyWorkLogId(weeklyWorkLogId);
        record.setWeeklyWorkLogIdOrig(weeklyWorkLogIdOrig);
        return mapper.duplicatePersonEmployment(record);
    }

    public int duplicateEquipmentEmployment(long weeklyWorkLogId, long weeklyWorkLogIdOrig, UserLogin user) {
        Date createdStamp = new Date();
        WeeklyWorkLogEquipmentEx record = new WeeklyWorkLogEquipmentEx();
        record.setCreatedStamp(createdStamp);
        record.setCreatedByUserId(user.getId());
        record.setWeeklyWorkLogId(weeklyWorkLogId);
        record.setWeeklyWorkLogIdOrig(weeklyWorkLogIdOrig);
        return mapper.duplicateEquipmentEmployment(record);
    }

    private WeeklyWorkLogExample makeWeeklyWorkLogExample(WeeklyWorkLogFilter filter, UserLogin user) {
        filter.setOwnerNodeId(user.getActiveNode().getId());

        WeeklyWorkLogExample example = new WeeklyWorkLogExample();
        WeeklyWorkLogExample.Criteria criteria = example.createCriteria();
        criteria.andOwnerNodeIdEqualTo(filter.getOwnerNodeId());

        if (filter.getIdToExclude() != null) {
            criteria.andIdNotEqualTo(filter.getIdToExclude());
        }
        if (filter.getConstructionSiteId() != null) {
            criteria.andConstructionSiteIdEqualTo(filter.getConstructionSiteId());
        }
        if (filter.getFromWeekDate() != null) {
            criteria.andLogDateGreaterThanOrEqualTo(filter.getFromWeekDate());
        }
        if (filter.getToWeekDate() != null) {
            criteria.andLogDateLessThanOrEqualTo(filter.getToWeekDate());
        }

        example.setOrderByClause("log_date desc, id desc");
        return example;
    }

    public Set<Long> findPersonEmploymentIds(Long workLogId) {
        return personMapper.selectPersonEmploymentIds(workLogId);
    }

    public Set<Long> findEquipmentEmploymentIds(Long workLogId) {
        return equipmentMapper.selectEquipmentEmploymentIds(workLogId);
    }

    /**
     * @param wwlId weeklyWorkLogId
     * @return
     */
    public List<WeeklyWorkLogPersonExport> findPersonForExport(Long wwlId, List<Long> personEmpIds) {
        WeeklyWorkLogPersonExample example = new WeeklyWorkLogPersonExample();
        WeeklyWorkLogPersonExample.Criteria criteria = example.createCriteria();
        if (wwlId != null)
            criteria.andWeeklyWorkLogIdEqualTo(wwlId);
        if (personEmpIds != null)
            criteria.andPersonEmplIdIn(personEmpIds);
        example.setOrderByClause("pe_company_business_name ASC, pe_person_last_name ASC, pe_person_first_name ASC");
        return personMapper.findForExportByExample(example);
    }

    /**
     * @param wwlId weeklyWorkLogId
     * @return
     */
    public List<WeeklyWorkLogEquipmentExport> findEquipmentForExport(Long wwlId, List<Long> equipmentEmpIds) {
        WeeklyWorkLogEquipmentExample example = new WeeklyWorkLogEquipmentExample();
        WeeklyWorkLogEquipmentExample.Criteria criteria = example.createCriteria();
        if (wwlId != null)
            criteria.andWeeklyWorkLogIdEqualTo(wwlId);
        if (equipmentEmpIds != null)
            criteria.andEquipmentEmplIdIn(equipmentEmpIds);
        example.setOrderByClause("ee_company_business_name ASC, ee_equipment_registration_number ASC");
        return equipmentMapper.findForExportByExample(example);
    }
}
