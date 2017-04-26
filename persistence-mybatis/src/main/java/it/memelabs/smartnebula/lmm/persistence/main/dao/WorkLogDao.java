package it.memelabs.smartnebula.lmm.persistence.main.dao;

import it.mapsgroup.commons.collect.Tuple2;
import it.memelabs.smartnebula.lmm.persistence.main.dto.*;
import it.memelabs.smartnebula.lmm.persistence.main.mapper.WorkLogExMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author sivi.
 */
@Service
public class WorkLogDao {
    // private static final Logger LOG = getLogger(WorkLogDao.class);

    private final WorkLogExMapper mapper;

    @Autowired
    public WorkLogDao(WorkLogExMapper mapper) {
        this.mapper = mapper;
    }

    public WorkLog findById(long id) {
        return mapper.selectByPrimaryKey(id);
    }

    public WorkLogEx findExById(long id) {
        return mapper.selectExByPrimaryKey(id);
    }

    public List<CompanyEmployment> findCompanyPersonEmpl(long id) {
        return mapper.selectCompanyPersonEmpl(id);
    }

    public List<CompanyEmployment> findCompanyEquipmentEmpl(long id) {
        return mapper.selectCompanyEquipmentEmpl(id);
    }

    public Tuple2<List<WorkLog>, Integer> findByFilter(WorkLogFilter filter, UserLogin user) {
        WorkLogExample example = makeWorkLogExample(filter, user);
        int count = mapper.countByExample(example);
        List<WorkLog> list = mapper.selectByExampleWithRowbounds(example, filter.makeRowBounds());
        return new Tuple2<>(list, count);
    }

    public Tuple2<List<WorkLogEx>, Integer> findExByFilter(WorkLogExFilter filter, UserLogin user) {
        WorkLogExExample example = makeWorkLogExExample(filter, user);
        int count = mapper.countExByExample(example);
        List<WorkLogEx> list = mapper.selectExByExampleWithRowbounds(example, filter.makeRowBounds());
        return new Tuple2<>(list, count);
    }

    public Long create(WorkLog record, UserLogin user) {
        Date createdStamp = new Date();
        record.setModifiedStamp(createdStamp);
        record.setModifiedByUserId(user.getId());
        record.setCreatedStamp(createdStamp);
        record.setCreatedByUserId(user.getId());
        record.setOwnerNodeId(user.getActiveNode().getId());
        int result = mapper.insert(record);
        return (result > 0) ? record.getId() : null;
    }


    public boolean update(WorkLog record, UserLogin user) {
        record.setModifiedStamp(new Date());
        record.setModifiedByUserId(user.getId());
        record.setOwnerNodeId(user.getActiveNode().getId());
        int result = mapper.updateByPrimaryKey(record);
        return result > 0;
    }

    public boolean update(WorkLog record, WorkLogExample example, UserLogin user) {
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

    /**
     * @param workLogId
     * @param logDate
     * @return invalid personEmploymentIds
     */
    public List<Long> validatePersonEmployment(long workLogId, Date logDate) {
        return mapper.validatePersonEmployment(workLogId, logDate);
    }

    /**
     * @param workLogId
     * @param logDate
     * @return equipmentEmploymentIds
     */
    public List<Long> validateEquipmentEmployment(long workLogId, Date logDate) {
        return mapper.validateEquipmentEmployment(workLogId, logDate);
    }

    private WorkLogExample makeWorkLogExample(WorkLogFilter filter, UserLogin user) {
        return makeWorkLogExample(filter, user, new WorkLogExExample());
    }

    private WorkLogExample makeWorkLogExample(WorkLogFilter filter, UserLogin user, WorkLogExample example) {
        filter.setOwnerNodeId(user.getActiveNode().getId());

        WorkLogExample.Criteria criteria = example.createCriteria();
        criteria.andOwnerNodeIdEqualTo(filter.getOwnerNodeId());

        if (filter.getIdToExclude() != null) {
            criteria.andIdNotEqualTo(filter.getIdToExclude());
        }
        if (filter.getConstructionSiteId() != null) {
            criteria.andConstructionSiteIdEqualTo(filter.getConstructionSiteId());
        }
        if (filter.getFromDate() != null) {
            criteria.andLogDateGreaterThanOrEqualTo(filter.getFromDate());
        }
        if (filter.getToDate() != null) {
            criteria.andLogDateLessThanOrEqualTo(filter.getToDate());
        }
        if (filter.getConstructionSiteNull() != null) {
            if (filter.getConstructionSiteNull())
                criteria.andConstructionSiteIdIsNull();
            else
                criteria.andConstructionSiteIdIsNotNull();
        }


        example.setOrderByClause("log_date desc, id desc");
        return example;
    }

    private WorkLogExExample makeWorkLogExExample(WorkLogExFilter filter, UserLogin user) {
        filter.setOwnerNodeId(user.getActiveNode().getId());
        WorkLogExExample example = new WorkLogExExample();
        makeWorkLogExample(filter, user, example);
        if (filter.getStateTag() != null)
            example.setStateTag(filter.getStateTag());

        if (filter.getCompanyId() != null) {
            example.setCompanyId(filter.getCompanyId());
            example.setDistinct(true);
        }
        example.setOrderByClause("log_date desc, id desc");
        return example;
    }

    public List<Map<String, Object>> countEventsByWorkLogId(long workLogId) {
        return mapper.countEventsByWorkLogId(workLogId);
    }

    public WorkLog findByConstructionSiteLogId( long constructionSiteLogId) {
        return mapper.selectByConstructionSiteLogId(constructionSiteLogId);
    }
}
