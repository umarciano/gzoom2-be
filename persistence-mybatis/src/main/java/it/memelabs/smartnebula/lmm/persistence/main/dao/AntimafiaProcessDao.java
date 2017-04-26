package it.memelabs.smartnebula.lmm.persistence.main.dao;

import it.mapsgroup.commons.collect.Tuple2;
import it.memelabs.smartnebula.lmm.persistence.main.dto.*;
import it.memelabs.smartnebula.lmm.persistence.main.mapper.AntimafiaProcessExMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

import static it.memelabs.smartnebula.commons.DateUtil.getEndOfDay;
import static it.memelabs.smartnebula.commons.DateUtil.getStartOfDay;

@Service
public class AntimafiaProcessDao {

    private final AntimafiaProcessExMapper antimafiaProcessExMapper;

    @Autowired
    public AntimafiaProcessDao(AntimafiaProcessExMapper antimafiaProcessExMapper) {
        this.antimafiaProcessExMapper = antimafiaProcessExMapper;
    }

    public AntimafiaProcess findById(long id) {
        return antimafiaProcessExMapper.selectByPrimaryKey(id);
    }

    public AntimafiaProcessEx findExById(long id) {
        return antimafiaProcessExMapper.selectExByPrimaryKey(id);
    }

    public Tuple2<List<AntimafiaProcess>, Integer> findByFilter(AntimafiaProcessFilter filter, UserLogin user) {
        AntimafiaProcessExExample contractExample = makeAntimafiaProcessExample(filter, user);
        int count = antimafiaProcessExMapper.countByExample(contractExample);
        List<AntimafiaProcess> list = antimafiaProcessExMapper.selectByExampleWithRowbounds(contractExample, filter.makeRowBounds());
        return new Tuple2<>(list, count);
    }

    public Tuple2<List<AntimafiaProcessEx>, Integer> findExByFilter(AntimafiaProcessFilter filter, UserLogin user) {
        AntimafiaProcessExExample contractExample = makeAntimafiaProcessExample(filter, user);
        int count = antimafiaProcessExMapper.countByExample(contractExample);
        List<AntimafiaProcessEx> list = antimafiaProcessExMapper.selectExByExampleWithRowbounds(contractExample, filter.makeRowBounds());
        return new Tuple2<>(list, count);
    }

    public Long create(AntimafiaProcess record, UserLogin user) {
        Date createdStamp = new Date();
        record.setModifiedStamp(createdStamp);
        record.setModifiedByUserId(user.getId());
        record.setCreatedStamp(createdStamp);
        record.setCreatedByUserId(user.getId());
        record.setOwnerNodeId(user.getActiveNode().getId());
        int result = antimafiaProcessExMapper.insert(record);
        return (result > 0) ? record.getId() : null;
    }

    public boolean update(AntimafiaProcess record, UserLogin user) {
        record.setModifiedStamp(new Date());
        record.setModifiedByUserId(user.getId());
        record.setOwnerNodeId(user.getActiveNode().getId());
        int result = antimafiaProcessExMapper.updateByPrimaryKey(record);
        return result > 0;
    }

    public boolean updateByExample(AntimafiaProcess record, UserLogin user, AntimafiaProcessExample example) {
        record.setModifiedStamp(new Date());
        record.setModifiedByUserId(user.getId());
        record.setOwnerNodeId(user.getActiveNode().getId());
        int result = antimafiaProcessExMapper.updateByExample(record, example);
        return result > 0;
    }

    public boolean delete(long id) {
        int result = antimafiaProcessExMapper.deleteByPrimaryKey(id);
        return result > 0;
    }

    private AntimafiaProcessExExample makeAntimafiaProcessExample(AntimafiaProcessFilter filter, UserLogin user) {
        filter.setOwnerNodeId(user.getActiveNode().getId());

        AntimafiaProcessExExample example = new AntimafiaProcessExExample();
        AntimafiaProcessExExample.Criteria criteria = example.createCriteria();
        criteria.andOwnerNodeIdEqualTo(filter.getOwnerNodeId());

        if (filter.getCompanyId() != null) {
            criteria.andCompanyIdEqualTo(filter.getCompanyId());
        }
        if (filter.getPrefectureId() != null) {
            criteria.andPrefectureIdEqualTo(filter.getPrefectureId());
        }
        if (filter.getLotId() != null) {
            criteria.andLotIdEqualTo(filter.getLotId());
        }
        if (filter.getStateId() != null) {
            criteria.andStateIdEqualTo(filter.getStateId());
        }
        if (filter.getStateTag() != null) {
            criteria.andStateTagEqualTo(filter.getStateTag());
        }
        if (filter.getExpiryRange() != null) {
            criteria.andExpiryDateBetween(getStartOfDay(filter.getExpiryRange().getFrom()), getEndOfDay(filter.getExpiryRange().getTo()));
        }
        example.setOrderByClause("id");
        return example;
    }
}
