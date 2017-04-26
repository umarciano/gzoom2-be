package it.memelabs.smartnebula.lmm.persistence.main.dao;

import it.mapsgroup.commons.collect.Tuple2;
import it.memelabs.smartnebula.commons.DateUtil;
import it.memelabs.smartnebula.lmm.persistence.main.dto.*;
import it.memelabs.smartnebula.lmm.persistence.main.enumeration.EntityStateReference;
import it.memelabs.smartnebula.lmm.persistence.main.enumeration.WorkLogEventOrigin;
import it.memelabs.smartnebula.lmm.persistence.main.mapper.EntityStateMapper;
import it.memelabs.smartnebula.lmm.persistence.main.mapper.WorkLogPersonEventExMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @author anfo.
 */
@Service
public class WorkLogPersonEventDao {
    // private static final Logger LOG = getLogger(WorkLogDao.class);

    private final WorkLogPersonEventExMapper personEventMapper;
    private final EntityStateMapper entityStateMapper;


    @Autowired
    public WorkLogPersonEventDao(WorkLogPersonEventExMapper personEventMapper,
                                 EntityStateMapper entityStateMapper) {
        this.personEventMapper = personEventMapper;
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


    public boolean create(WorkLogPersonEvent record, UserLogin user) {
        Date createdStamp = new Date();
        record.setCreatedStamp(createdStamp);
        record.setModifiedStamp(createdStamp);
        record.setCreatedByUserId(user.getId());
        record.setModifiedByUserId(user.getId());
        return personEventMapper.insert(record) > 0;
    }

    public boolean update(WorkLogPersonEvent record, UserLogin user) {
        record.setModifiedStamp(new Date());
        record.setModifiedByUserId(user.getId());
        int result = personEventMapper.updateByPrimaryKey(record);
        return result > 0;
    }


    public WorkLogPersonEvent findPersonEventByPrimaryKey(Long workLogId, Long peEmplId, Date eventTimeStamp) {
        WorkLogPersonEventExample example = new WorkLogPersonEventExample();
        WorkLogPersonEventExample.Criteria criteria = example.createCriteria();
        criteria.andWorkLogIdEqualTo(workLogId);
        criteria.andPersonEmplIdEqualTo(peEmplId);
        criteria.andEventTimestampEqualTo(eventTimeStamp);
        List<WorkLogPersonEvent> result = personEventMapper.selectByExample(example);
        return (result.size() > 0) ? result.get(0) : null;
    }


    public Tuple2<List<WorkLogPersonEventEx>, Integer> findPersonByFilter(WorkLogEventFilter filter) {
        WorkLogPersonEventExExample example = personCriteria(filter);

        example.setOrderByClause("event_timestamp ASC, pe_person_last_name ASC");
        int count = personEventMapper.countExByExample(example);
        List<WorkLogPersonEventEx> events = personEventMapper.selectExByExample(example, filter.makeRowBounds());
        return new Tuple2<>(events, count);
    }


    public int countPersonByFilter(WorkLogEventFilter filter) {
        WorkLogPersonEventExExample example = personCriteria(filter);

        return personEventMapper.countExByExample(example);
    }


    private WorkLogPersonEventExExample personCriteria(WorkLogEventFilter filter) {
        WorkLogPersonEventExExample example = new WorkLogPersonEventExExample();
        WorkLogPersonEventExample.Criteria criteria = example.createCriteria();
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
        if (filter.getPersonEmploymentId() != null)
            criteria.andPersonEmplIdEqualTo(filter.getPersonEmploymentId());
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
        if (filter.getPersonId() != null)
            example.andPersonIdEqualTo(criteria, filter.getPersonId());
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

    public WorkLogPersonEventEx findPersonEventExById(Long id) {
        return personEventMapper.selectExByPrimaryKey(id);
    }


    public WorkLogPersonEvent findPersonEventById(Long id) {
        return personEventMapper.selectByPrimaryKey(id);
    }

    public Set<Long> findPersonEmploymentIds(Long workLogId) {
        return personEventMapper.selectPersonEmploymentIds(workLogId);
    }


    public int updatePersonEvents(WorkLogEventUpdateFilter req, UserLogin user) {
        if (user != null) req.setModifiedByUserId(user.getId());
        req.setModifiedStamp(new Date());
        return personEventMapper.update(req);
    }

    public int countNotValidPersonEvents(WorkLogEventUpdateFilter req) {
        return personEventMapper.countNotValid(req.getWorkLogId(), req.getEvents());
    }

    public int countDuplicatePersonEvents(WorkLogEventUpdateFilter req) {
        return personEventMapper.countDuplicate(req.getWorkLogId(), req.getDestinationWorkLogId(), req.getEvents());
    }


    public boolean deletePersonEvent(Long eventId) {
        WorkLogPersonEventExExample example = new WorkLogPersonEventExExample();
        WorkLogPersonEventExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(eventId);
        criteria.andOriginEqualTo(WorkLogEventOrigin.INTERNAL);
        return personEventMapper.deleteByExample(example) > 0;
    }


    public int deletePersonEvents(Long workLogId) {
        WorkLogPersonEventExExample example = new WorkLogPersonEventExExample();
        WorkLogPersonEventExample.Criteria criteria = example.createCriteria();
        criteria.andWorkLogIdEqualTo(workLogId);
        criteria.andOriginEqualTo(WorkLogEventOrigin.INTERNAL);
        return personEventMapper.deleteByExample(example);
    }


    public List<WorkLogPersonEventExport> findForExportByFilter(WorkLogEventFilter filter, UserLogin principal) {
        filter.setOwnerNodeId(principal.getActiveNode().getId());
        WorkLogPersonEventExExample example = personCriteria(filter);
        example.setOrderByClause("work_log_construction_site_code ASC, event_timestamp ASC, pe_company_business_name ASC, pe_person_last_name ASC, pe_person_first_name ASC");
        List<WorkLogPersonEventExport> events = personEventMapper.findForExportByFilter(example, filter.makeRowBounds());
        return events;
    }
}
