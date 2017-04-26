package it.memelabs.smartnebula.lmm.persistence.main.dao;

import it.mapsgroup.commons.collect.Tuple2;
import it.memelabs.smartnebula.lmm.persistence.main.dto.*;
import it.memelabs.smartnebula.lmm.persistence.main.mapper.ConstructionSiteExMapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ConstructionSiteDao {

    private final ConstructionSiteExMapper constructionSiteExMapper;

    @Autowired
    public ConstructionSiteDao(ConstructionSiteExMapper constructionSiteExMapper) {
        this.constructionSiteExMapper = constructionSiteExMapper;
    }

    public ConstructionSite findById(long id) {
        return constructionSiteExMapper.selectByPrimaryKey(id);
    }


    public ConstructionSiteEx findExById(long id) {
        return constructionSiteExMapper.selectByPrimaryKeyEx(id);
    }

    public Tuple2<List<ConstructionSiteEx>, Integer> findByFilter(ConstructionSiteFilter filter, UserLogin user) {
        RowBounds rowBounds = filter.makeRowBounds();
        filter.setOwnerNodeId(user.getActiveNode().getId());

        ConstructionSiteExExample constructionSiteExample = new ConstructionSiteExExample();
        ConstructionSiteExExample.Criteria criteria = constructionSiteExample.createCriteria();
        criteria.andOwnerNodeIdEqualTo(filter.getOwnerNodeId());
        if (StringUtils.isNotBlank(filter.getFreeText())) {
            constructionSiteExample.setFilterText(filter.getFreeText());
        }
        if (StringUtils.isNotBlank(filter.getCode())) {
            criteria.andCodeLikeInsensitive(filter.getCode());
        }
        if (filter.getJobOrder() != null) {
            criteria.andJobOrderIdEqualTo(filter.getJobOrder());
        }
        if (filter.getAssignedCompany() != null) {
            criteria.andAssignedCompanyIdEqualTo(filter.getAssignedCompany());
        }
        if (filter.getConstructionSiteId() != null) {
            criteria.andIdEqualTo(filter.getConstructionSiteId());
        }
        constructionSiteExample.setOrderByClause("code, description, id");
        int count = constructionSiteExMapper.countByFilterEx(constructionSiteExample);
        List<ConstructionSiteEx> list = constructionSiteExMapper.selectByFilterWithRowboundsEx(constructionSiteExample, rowBounds);
        return new Tuple2<>(list, count);

    }

    public Long create(ConstructionSite record, UserLogin user) {
        Date createdStamp = new Date();
        record.setModifiedStamp(createdStamp);
        record.setModifiedByUserId(user.getId());
        record.setCreatedStamp(createdStamp);
        record.setCreatedByUserId(user.getId());
        record.setOwnerNodeId(user.getActiveNode().getId());
        int result = constructionSiteExMapper.insert(record);
        return result > 0 ? record.getId() : null;
    }

    public boolean update(ConstructionSite record, UserLogin user) {
        record.setModifiedStamp(new Date());
        record.setModifiedByUserId(user.getId());
        int result = constructionSiteExMapper.updateByPrimaryKey(record);
        return result > 0;
    }

    public boolean delete(long id) {
        int result = constructionSiteExMapper.deleteByPrimaryKey(id);
        return result > 0;
    }

    public List<ConstructionSite> findByIds(List<Long> ids) {
        ConstructionSiteExample example = new ConstructionSiteExample();
        ConstructionSiteExample.Criteria criteria = example.createCriteria();
        criteria.andIdIn(ids);
        example.setOrderByClause("code asc");
        return constructionSiteExMapper.selectByExample(example);
    }
}
