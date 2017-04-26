package it.memelabs.smartnebula.lmm.persistence.job.dao;

import it.memelabs.smartnebula.lmm.persistence.enumeration.JobStatus;
import it.memelabs.smartnebula.lmm.persistence.enumeration.JobType;
import it.memelabs.smartnebula.lmm.persistence.enumeration.ReferenceObject;
import it.memelabs.smartnebula.lmm.persistence.main.dto.*;
import it.memelabs.smartnebula.lmm.persistence.main.mapper.ActivityJobAttributeExMapper;
import it.memelabs.smartnebula.lmm.persistence.main.mapper.ActivityJobExMapper;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

/**
 * @author Andrea Fossi.
 */
@Service
public class ActivityJobDao {
    private final ActivityJobExMapper jobMapper;
    private final ActivityJobAttributeExMapper jobAttributeMapper;

    @Autowired
    public ActivityJobDao(ActivityJobExMapper jobMapper,
                          ActivityJobAttributeExMapper jobAttributeMapper) {
        this.jobMapper = jobMapper;
        this.jobAttributeMapper = jobAttributeMapper;
    }

    public Long insert(ActivityJobEx record, UserLogin user) {
        Date createdStamp = new Date();
        record.setModifiedStamp(createdStamp);
        record.setModifiedByUserId(user.getId());
        record.setCreatedStamp(createdStamp);
        record.setCreatedByUserId(user.getId());
        record.setOwnerNodeId(user.getActiveNode().getId());
        List<ActivityJobAttribute> attributes = record.getAttributes();
        int result = jobMapper.insert(record);
        if ((result > 0)) {
            long jobId = record.getId();
            attributes.forEach(att -> {
                att.setJobId(jobId);
            });
            if (!CollectionUtils.isEmpty(attributes)) {
                jobAttributeMapper.insertAttributes(attributes);
            }

            return record.getId();
        } else return null;
    }

    public List<ActivityJob> find(ActivityJobFilter filter) {
        return jobMapper.selectByFilter(filter, filter.makeRowBounds());
    }

    public ActivityJobEx findExById(Long id) {
        return jobMapper.selectByPrimaryKeyEx(id);
    }

    public ActivityJob findById(Long id) {
        return jobMapper.selectByPrimaryKey(id);
    }

    /**
     * @param record
     * @param currentStatus
     * @return
     */
    public boolean update(ActivityJob record, JobStatus currentStatus) {
        record.setModifiedStamp(new Date());
        if (currentStatus != null) {
            ActivityJobExample example = new ActivityJobExample();
            ActivityJobExample.Criteria criteria = example.createCriteria();
            criteria.andStatusEqualTo(currentStatus);
            criteria.andIdEqualTo(record.getId());
            return jobMapper.updateByExample(record, example) > 0;
        } else return jobMapper.updateConsumedByPrimaryKey(record) > 0;
    }

    public boolean delete(Long referenceObjectId, ReferenceObject referenceObject, JobType type) {
        jobAttributeMapper.deleteByJobReferenceId(referenceObjectId, referenceObject, type);
        int delete = jobMapper.deleteByReferenceId(referenceObjectId, referenceObject, type);
        return delete > 0;
    }

    public boolean update(ActivityJob record) {
        return jobMapper.updateByPrimaryKey(record) > 0;
    }

}
