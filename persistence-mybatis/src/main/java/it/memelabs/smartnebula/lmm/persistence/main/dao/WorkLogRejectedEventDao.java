package it.memelabs.smartnebula.lmm.persistence.main.dao;

import it.memelabs.smartnebula.lmm.persistence.main.dto.UserLogin;
import it.memelabs.smartnebula.lmm.persistence.main.dto.WorkLogRejectedEvent;
import it.memelabs.smartnebula.lmm.persistence.main.dto.WorkLogRejectedEventExample;
import it.memelabs.smartnebula.lmm.persistence.main.mapper.WorkLogRejectedEventMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author Andrea Fossi.
 */
@Service
public class WorkLogRejectedEventDao {

    private final WorkLogRejectedEventMapper mapper;

    @Autowired
    public WorkLogRejectedEventDao(WorkLogRejectedEventMapper mapper) {
        this.mapper = mapper;
    }

    /**
     *
     * @param record
     * @param user
     * @return
     */
    public boolean create(WorkLogRejectedEvent record, UserLogin user) {
        Date createdStamp = new Date();
        record.setModifiedStamp(createdStamp);
        record.setModifiedByUserId(user.getId());
        record.setCreatedStamp(createdStamp);
        record.setCreatedByUserId(user.getId());
        record.setOwnerNodeId(user.getActiveNode().getId());
        int result = mapper.insert(record);
        return (result > 0);
    }

    /**
     *
     * @param record
     * @param user
     * @return
     */
    public boolean update(WorkLogRejectedEvent record, UserLogin user) {
        record.setModifiedStamp(new Date());
        record.setModifiedByUserId(user.getId());
        record.setOwnerNodeId(user.getActiveNode().getId());
        int result = mapper.updateByPrimaryKey(record);
        return result > 0;
    }

    public WorkLogRejectedEvent findById(long id) {
        return mapper.selectByPrimaryKey(id);
    }

    public WorkLogRejectedEvent find(String identificationCode, Date timestamp) {
        WorkLogRejectedEventExample example = new WorkLogRejectedEventExample();
        WorkLogRejectedEventExample.Criteria criteria = example.createCriteria();
        criteria.andTimestampEqualTo(timestamp);
        criteria.andIdentificationCodeEqualTo(identificationCode);
        List<WorkLogRejectedEvent> result = mapper.selectByExample(example);
        if (result.size() == 0) return null;
        else return result.get(0);
    }
}
