package it.memelabs.smartnebula.lmm.persistence.main.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.mapsgroup.commons.collect.Tuple2;
import it.memelabs.smartnebula.lmm.persistence.main.dto.ConstructionSiteLogEx;
import it.memelabs.smartnebula.lmm.persistence.main.dto.ConstructionSiteLogExample;
import it.memelabs.smartnebula.lmm.persistence.main.dto.UserLogin;
import it.memelabs.smartnebula.lmm.persistence.main.dto.ConstructionSiteLog;
import it.memelabs.smartnebula.lmm.persistence.main.mapper.ConstructionSiteLogExMapper;

/**
 * @author sivi.
 */
@Service
public class ConstructionSiteLogDao {
    private final ConstructionSiteLogExMapper constructionSiteLogExMapper;

    @Autowired
    public ConstructionSiteLogDao(ConstructionSiteLogExMapper constructionSiteLogExMapper) {
        this.constructionSiteLogExMapper = constructionSiteLogExMapper;
    }

    public ConstructionSiteLogEx findById(long id) {
        return constructionSiteLogExMapper.selectByPrimaryKeyEx(id);
    }

    public Tuple2<List<ConstructionSiteLogEx>, Integer> findByFilter(ConstructionSiteLogFilter filter, UserLogin user) {
        RowBounds rowBounds = new RowBounds((filter.getPage() - 1) * filter.getSize(), filter.getSize());
        ConstructionSiteLogExample constructionSiteLogExample = new ConstructionSiteLogExample();
        ConstructionSiteLogExample.Criteria criteria = constructionSiteLogExample.createCriteria();
        filter.setOwnerNodeId(user.getActiveNode().getId());
        criteria.andOwnerNodeIdEqualTo(filter.getOwnerNodeId());

        if (filter.getConstructionSiteId() != null) {
            criteria.andConstructionSiteIdEqualTo(filter.getConstructionSiteId());
        }
        if (filter.getLogDate() != null) {
            criteria.andLogDateEqualTo(filter.getLogDate());
        }
        if (filter.getStateId() != null) {
            criteria.andStateIdEqualTo(filter.getStateId());
        }
        constructionSiteLogExample.setOrderByClause("log_date DESC");
        int count = constructionSiteLogExMapper.countByExample(constructionSiteLogExample);
        List<ConstructionSiteLogEx> list = constructionSiteLogExMapper.selectByFilterWithRowboundsEx(constructionSiteLogExample, rowBounds);
        return new Tuple2<>(list, count);
    }

    public Long create(ConstructionSiteLog record, UserLogin user) {
        Date createdStamp = new Date();
        record.setModifiedStamp(createdStamp);
        record.setModifiedByUserId(user.getId());
        record.setCreatedStamp(createdStamp);
        record.setCreatedByUserId(user.getId());
        record.setOwnerNodeId(user.getActiveNode().getId());
        int result = constructionSiteLogExMapper.insert(record);
        return (result > 0) ? record.getId() : null;
    }

    public boolean update(ConstructionSiteLog record, UserLogin user) {
        record.setModifiedStamp(new Date());
        record.setModifiedByUserId(user.getId());
        record.setOwnerNodeId(user.getActiveNode().getId());
        int result = constructionSiteLogExMapper.updateByPrimaryKey(record);
        return result > 0;
    }

    public boolean delete(long id) {
        int result = constructionSiteLogExMapper.deleteByPrimaryKey(id);
        return result > 0;
    }

    public Tuple2<List<ConstructionSiteLogEx>, Integer> checkUniqueCode(ConstructionSiteLogFilter filter, UserLogin user) {
        RowBounds rowBounds = new RowBounds((filter.getPage() - 1) * filter.getSize(), filter.getSize());
        ConstructionSiteLogExample constructionSiteLogExample = new ConstructionSiteLogExample();
        ConstructionSiteLogExample.Criteria criteria = constructionSiteLogExample.createCriteria();
        filter.setOwnerNodeId(user.getActiveNode().getId());
        criteria.andOwnerNodeIdEqualTo(filter.getOwnerNodeId());

        if (filter.getLogDate() != null) {
            criteria.andLogDateEqualTo(filter.getLogDate());
        }
        if (filter.getConstructionSiteId() != null) {
            criteria.andConstructionSiteIdEqualTo(filter.getConstructionSiteId());
        }
        if (filter.getId() != null) {
            criteria.andIdNotEqualTo(filter.getId());
        }
        int count = constructionSiteLogExMapper.countByExample(constructionSiteLogExample);
        List<ConstructionSiteLogEx> list = constructionSiteLogExMapper.selectByFilterWithRowboundsEx(constructionSiteLogExample, rowBounds);
        return new Tuple2<>(list, count);
    }
}
