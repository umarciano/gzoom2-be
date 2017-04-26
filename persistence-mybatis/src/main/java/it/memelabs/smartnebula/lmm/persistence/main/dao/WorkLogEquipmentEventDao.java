package it.memelabs.smartnebula.lmm.persistence.main.dao;

import it.mapsgroup.commons.collect.Tuple2;
import it.memelabs.smartnebula.commons.DateUtil;
import it.memelabs.smartnebula.lmm.persistence.main.dto.*;
import it.memelabs.smartnebula.lmm.persistence.main.enumeration.EntityStateReference;
import it.memelabs.smartnebula.lmm.persistence.main.enumeration.WorkLogEventOrigin;
import it.memelabs.smartnebula.lmm.persistence.main.mapper.EntityStateMapper;
import it.memelabs.smartnebula.lmm.persistence.main.mapper.WorkLogEquipmentEventExMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @author anfo.
 */
@Service
public class WorkLogEquipmentEventDao {
    // private static final Logger LOG = getLogger(WorkLogDao.class);

    private final WorkLogEquipmentEventExMapper equipmentEventMapper;
    private final EntityStateMapper entityStateMapper;


    @Autowired
    public WorkLogEquipmentEventDao(WorkLogEquipmentEventExMapper equipmentEventMapper,
                                    EntityStateMapper entityStateMapper) {
        this.equipmentEventMapper = equipmentEventMapper;
        this.entityStateMapper = entityStateMapper;
    }

    public List<EntityState> selectStates(UserLogin user) {
        EntityStateExample entityStateExample = new EntityStateExample();
        entityStateExample.createCriteria() //
                .andOwnerNodeIdEqualTo(user.getActiveNode().getId()) //
                .andEntityEqualTo(EntityStateReference.WORK_LOG_EVENT.name());
        entityStateExample.setOrderByClause("id");
        return entityStateMapper.selectByExample(entityStateExample);
    }

    public boolean create(WorkLogEquipmentEvent record, UserLogin user) {
        Date createdStamp = new Date();
        record.setCreatedStamp(createdStamp);
        record.setModifiedStamp(createdStamp);
        record.setCreatedByUserId(user.getId());
        record.setModifiedByUserId(user.getId());
        return equipmentEventMapper.insert(record) > 0;
    }

    public boolean update(WorkLogEquipmentEvent record, UserLogin user) {
        record.setModifiedStamp(new Date());
        record.setModifiedByUserId(user.getId());
        int result = equipmentEventMapper.updateByPrimaryKey(record);
        return result > 0;
    }


    public WorkLogEquipmentEvent findEquipmentEventByPrimaryKey(Long workLogId, Long eqEmplId, Date eventTimeStamp) {
        WorkLogEquipmentEventExample example = new WorkLogEquipmentEventExample();
        WorkLogEquipmentEventExample.Criteria criteria = example.createCriteria();
        criteria.andWorkLogIdEqualTo(workLogId);
        criteria.andEquipmentEmplIdEqualTo(eqEmplId);
        criteria.andEventTimestampEqualTo(eventTimeStamp);
        List<WorkLogEquipmentEvent> result = equipmentEventMapper.selectByExample(example);
        return (result.size() > 0) ? result.get(0) : null;
    }


    public WorkLogEquipmentEventEx findEquipmentEventExById(Long id) {
        return equipmentEventMapper.selectExByPrimaryKey(id);
    }


    public WorkLogEquipmentEvent findEquipmentEventById(Long id) {
        return equipmentEventMapper.selectByPrimaryKey(id);
    }

    public Set<Long> findEquipmentEmploymentIds(Long workLogId) {
        return equipmentEventMapper.selectEquipmentEmploymentIds(workLogId);
    }

    public int countEquipmentByFilter(WorkLogEventFilter filter) {
        WorkLogEquipmentEventExExample example = equipmentCriteria(filter);
        return equipmentEventMapper.countExByExample(example);
    }

    public Tuple2<List<WorkLogEquipmentEventEx>, Integer> findEquipmentByFilter(WorkLogEventFilter filter) {
        WorkLogEquipmentEventExExample example = equipmentCriteria(filter);
        int count = equipmentEventMapper.countExByExample(example);
        List<WorkLogEquipmentEventEx> events = equipmentEventMapper.selectExByExample(example, filter.makeRowBounds());
        return new Tuple2<>(events, count);
    }

    private WorkLogEquipmentEventExExample equipmentCriteria(WorkLogEventFilter filter) {
        WorkLogEquipmentEventExExample example = new WorkLogEquipmentEventExExample();
        WorkLogEquipmentEventExample.Criteria criteria = example.createCriteria();
        if (filter.getWorkLogId() != null)
            criteria.andWorkLogIdEqualTo(filter.getWorkLogId());
        if (filter.getWbsId() != null)
            criteria.andWbsIdEqualTo(filter.getWbsId());
        if (filter.getWbsNull() != null) {
            if (filter.getWbsNull())
                criteria.andWbsIdIsNull();
            else
                criteria.andWbsIdIsNotNull();
        }
        if (filter.getStateId() != null)
            criteria.andStateIdEqualTo(filter.getStateId());
        if (filter.getStateTag() != null)
            criteria.andStateTagEqualTo(filter.getStateTag());
        if (filter.getEquipmentId() != null)
            example.andEquipmentIdEqualTo(criteria, filter.getEquipmentId());
        if (filter.getOrigin() != null)
            criteria.andOriginEqualTo(filter.getOrigin());
        if (filter.getEventTimestamp() != null)
            criteria.andEventTimestampEqualTo(filter.getEventTimestamp());
        if (filter.getEventTimestampBetween() != null)
            criteria.andEventTimestampBetween(filter.getEventTimestampBetween().getFrom(), DateUtil.getEndOfDay(filter.getEventTimestampBetween().getTo()));
        if (filter.getIds() != null)
            criteria.andIdIn(filter.getIds());

        if (filter.getCompanyId() != null)
            example.andCompanyIdEqualTo(criteria, filter.getCompanyId());
        if (filter.getEquipmentEmploymentId() != null)
            criteria.andEquipmentEmplIdEqualTo(filter.getEquipmentEmploymentId());
        if (filter.getConstructionSiteId() != null)
            example.andConstructionSiteIdEqualTo(criteria, filter.getConstructionSiteId());
        if (filter.getOwnerNodeId() != null)
            example.andOwnerNodeIdEqualTo(criteria, filter.getOwnerNodeId());
        if (filter.getConstructionSiteNull() != null && filter.getConstructionSiteNull())
            example.andConstructionSiteIdIsNull(criteria);
        if(filter.getNotInWeeklyWorkLogId()!=null)
            example.setNotInWeeklyWorkLogId(filter.getNotInWeeklyWorkLogId());
        return example;
    }


    public int updateEquipmentEvents(WorkLogEventUpdateFilter req, UserLogin user) {
        if (user != null) req.setModifiedByUserId(user.getId());
        req.setModifiedStamp(new Date());
        return equipmentEventMapper.update(req);
    }

    public int countNotValidEquipmentEvents(WorkLogEventUpdateFilter req) {
        return equipmentEventMapper.countNotValid(req.getWorkLogId(), req.getEvents());
    }

    public int countDuplicateEquipmentEvents(WorkLogEventUpdateFilter req) {
        return equipmentEventMapper.countDuplicate(req.getWorkLogId(), req.getDestinationWorkLogId(), req.getEvents());
    }


    public boolean deleteEquipmentEvent(Long eventId) {
        WorkLogEquipmentEventExExample example = new WorkLogEquipmentEventExExample();
        WorkLogEquipmentEventExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(eventId);
        criteria.andOriginEqualTo(WorkLogEventOrigin.INTERNAL);
        return equipmentEventMapper.deleteByExample(example) > 0;
    }


    public int deleteEquipmentEvents(Long workLogId) {
        WorkLogEquipmentEventExExample example = new WorkLogEquipmentEventExExample();
        WorkLogEquipmentEventExample.Criteria criteria = example.createCriteria();
        criteria.andWorkLogIdEqualTo(workLogId);
        criteria.andOriginEqualTo(WorkLogEventOrigin.INTERNAL);
        return equipmentEventMapper.deleteByExample(example);
    }

    public List<WorkLogEquipmentEventExport> findForExportByFilter(WorkLogEventFilter filter, UserLogin principal) {
        filter.setOwnerNodeId(principal.getActiveNode().getId());
        WorkLogEquipmentEventExExample example = equipmentCriteria(filter);
        example.setOrderByClause("work_log_construction_site_code ASC, event_timestamp ASC, ee_company_business_name ASC, ee_equipment_registration_number ASC");
        List<WorkLogEquipmentEventExport> events = equipmentEventMapper.findForExportByFilter(example, filter.makeRowBounds());
        return events;
    }
}
