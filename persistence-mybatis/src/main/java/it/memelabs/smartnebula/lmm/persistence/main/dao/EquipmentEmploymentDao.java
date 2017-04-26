package it.memelabs.smartnebula.lmm.persistence.main.dao;

import it.mapsgroup.commons.collect.Tuple2;
import it.memelabs.smartnebula.lmm.persistence.main.dto.*;
import it.memelabs.smartnebula.lmm.persistence.main.enumeration.EntityStateTag;
import it.memelabs.smartnebula.lmm.persistence.main.mapper.EntityStateMapper;
import it.memelabs.smartnebula.lmm.persistence.main.mapper.EquipmentEmploymentExMapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

import static it.memelabs.smartnebula.commons.DateUtil.getEndOfDay;
import static it.memelabs.smartnebula.commons.DateUtil.getStartOfDay;

/**
 * @author Andrea Fossi.
 */
@Service
public class EquipmentEmploymentDao {
    // private static final Logger LOG = getLogger(EquipmentEmploymentDao.class);

    private final EquipmentEmploymentExMapper equipmentEmploymentExMapper;
    private final EntityStateMapper entityStateMapper;
    private final CommonEmploymentDao commonEmploymentDao;


    @Autowired
    public EquipmentEmploymentDao(EquipmentEmploymentExMapper equipmentEmploymentExMapper,
                                  EntityStateMapper entityStateMapper,
                                  CommonEmploymentDao commonEmploymentDao) {
        this.equipmentEmploymentExMapper = equipmentEmploymentExMapper;
        this.entityStateMapper = entityStateMapper;
        this.commonEmploymentDao = commonEmploymentDao;
    }

    public EquipmentEmploymentEx findById(long id) {
        return equipmentEmploymentExMapper.selectByPrimaryKey(id);
    }

    public Tuple2<List<EquipmentEmploymentEx>, Integer> findByEquipmentId(int page, int size, long equipmentId, UserLogin user) {
        RowBounds rowBounds = new RowBounds((page - 1) * size, size);

        EquipmentEmploymentExample equipmentEmploymentExample = new EquipmentEmploymentExample();
        EquipmentEmploymentExample.Criteria criteria = equipmentEmploymentExample.createCriteria();
        criteria.andOwnerNodeIdEqualTo(user.getActiveNode().getId());
        criteria.andEquipmentIdEqualTo(equipmentId);
        equipmentEmploymentExample.setOrderByClause("start_date desc, end_date desc, id");

        int count = equipmentEmploymentExMapper.countByExample(equipmentEmploymentExample);
        List<EquipmentEmploymentEx> list = equipmentEmploymentExMapper.selectExByExampleWithRowbounds(equipmentEmploymentExample, rowBounds);
        return new Tuple2<>(list, count);
    }

    public Long create(EquipmentEmployment record, UserLogin user) {
        Date createdStamp = new Date();
        record.setModifiedStamp(createdStamp);
        record.setModifiedByUserId(user.getId());
        record.setCreatedStamp(createdStamp);
        record.setCreatedByUserId(user.getId());
        record.setOwnerNodeId(user.getActiveNode().getId());
        record.setExternalId(commonEmploymentDao.uniqueIdentifier(CommonEmploymentDao.Type.EE, user.getActiveNode().getId()));
        if (record.getStateId() != null)
            record.setStateTag(entityStateMapper.selectByPrimaryKey(record.getStateId()).getTag());
        int result = equipmentEmploymentExMapper.insert(record);
        return (result > 0) ? record.getId() : null;
    }

    public boolean update(EquipmentEmployment record, UserLogin user) {
        record.setModifiedStamp(new Date());
        record.setModifiedByUserId(user.getId());
        if (record.getStateId() != null)
            record.setStateTag(entityStateMapper.selectByPrimaryKey(record.getStateId()).getTag());
        int result = equipmentEmploymentExMapper.updateByPrimaryKey(record);
        return result > 0;
    }

    public boolean delete(long id) {
        EquipmentEmploymentExample example = new EquipmentEmploymentExample();
        EquipmentEmploymentExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(id);
        criteria.andExportedStampIsNull();
        int result = equipmentEmploymentExMapper.deleteByExample(example);
        return result > 0;
    }

    public boolean checkDateOverlap(EquipmentEmploymentExExample equipmentExExample) {
        if (equipmentEmploymentExMapper.countDateOverlap(equipmentExExample) > 0) {
            return true;
        }
        return false;
    }

    public Tuple2<List<EquipmentEmploymentEx>, Integer> findForWeeklyWorkLog(int page, int size, long companyId, Long wwlId, Long wlId, Date start, Date end, UserLogin user) {
        RowBounds rowBounds = new RowBounds((page - 1) * size, size);
        EquipmentEmploymentWwlExample example = new EquipmentEmploymentWwlExample();
        EquipmentEmploymentWwlExample.Criteria criteria = example.createCriteria();
        criteria.andOwnerNodeIdEqualTo(user.getActiveNode().getId());
        criteria.andCompanyIdEqualTo(companyId);
        example.setEmploymentStartDate(start);
        example.setEmploymentEndDate(end);
        example.setOrderByClause("equipment_registration_number asc, start_date desc, end_date desc, id");
        example.setCompanyStateTag(EntityStateTag.AUTHORIZED);
        example.setWeeklyWorkLogId(wwlId);
        example.setWorkLogId(wlId);
        example.setCompanyId(companyId);
        criteria.andStateTagEqualTo(EntityStateTag.AUTHORIZED);
        int count = equipmentEmploymentExMapper.countWeekyWorkLogByExample(example);
        List<EquipmentEmploymentEx> list = equipmentEmploymentExMapper.selectWeekyWorkLogByExampleWithRowbounds(example, rowBounds);
        return new Tuple2<>(list, count);
    }

    public Tuple2<List<EquipmentEmploymentEx>, Integer> findByIds(int page, int size, List<Long> ids, UserLogin user) {
        RowBounds rowBounds = new RowBounds((page - 1) * size, size);

        EquipmentEmploymentExample equipmentEmploymentExample = new EquipmentEmploymentExample();
        EquipmentEmploymentExample.Criteria criteria = equipmentEmploymentExample.createCriteria();
        criteria.andOwnerNodeIdEqualTo(user.getActiveNode().getId());
        criteria.andIdIn(ids);
        equipmentEmploymentExample.setOrderByClause("start_date desc, end_date desc, id");

        int count = equipmentEmploymentExMapper.countByExample(equipmentEmploymentExample);
        List<EquipmentEmploymentEx> list = equipmentEmploymentExMapper.selectExByExampleWithRowbounds(equipmentEmploymentExample, rowBounds);
        return new Tuple2<>(list, count);
    }

    /**
     * Find {@link EquipmentEmployment} with endDate contained in interval
     *
     * @param endDateFrom
     * @param endDateTo
     * @param stateTag
     * @param user
     * @return
     */
    public List<EquipmentEmploymentEx> findByEndDate(Date endDateFrom, Date endDateTo, EntityStateTag stateTag, UserLogin user) {
        EquipmentEmploymentExample example = new EquipmentEmploymentExample();
        EquipmentEmploymentExample.Criteria criteria = example.createCriteria();
        criteria.andOwnerNodeIdEqualTo(user.getActiveNode().getId());
        criteria.andEndDateBetween(getStartOfDay(endDateFrom), getEndOfDay(endDateTo));
        if (stateTag != null)
            criteria.andStateTagEqualTo(stateTag);
        example.setOrderByClause("end_date ASC");
        return equipmentEmploymentExMapper.selectExByExampleWithRowbounds(example, new RowBounds());
    }

    public EquipmentEmploymentEx findByExternalId(String externalId, UserLogin user) {
        if (StringUtils.isNotBlank(externalId)) {
            EquipmentEmploymentExample example = new EquipmentEmploymentExample();
            EquipmentEmploymentExample.Criteria criteria = example.createCriteria();
            criteria.andExternalIdEqualTo(externalId);
            if (user != null) criteria.andOwnerNodeIdEqualTo(user.getActiveNode().getId());
            List<EquipmentEmploymentEx> equipmentEmployments = equipmentEmploymentExMapper.selectExByExampleWithRowbounds(example, new RowBounds());
            if (equipmentEmployments.size() > 0) return equipmentEmployments.get(0);
        }
        return null;
    }

    public Tuple2<List<EquipmentEmploymentEx>, Integer> findByFreeText(EquipmentEmploymentFilter filter, UserLogin user) {
        RowBounds rowBounds = new RowBounds((filter.getPage() - 1) * filter.getSize(), filter.getSize());
        filter.setOwnerNodeId(user.getActiveNode().getId());
        List<EquipmentEmploymentEx> list = equipmentEmploymentExMapper.selectByFreeTextWithRowbounds(filter, rowBounds);
        return new Tuple2<>(list, list.size());
    }
}
