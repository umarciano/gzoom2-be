package it.memelabs.smartnebula.lmm.persistence.main.dao;

import it.mapsgroup.commons.collect.Tuple2;
import it.memelabs.smartnebula.lmm.persistence.main.dto.ConstructionSiteLogActivity;
import it.memelabs.smartnebula.lmm.persistence.main.dto.ConstructionSiteLogActivityEx;
import it.memelabs.smartnebula.lmm.persistence.main.dto.ConstructionSiteLogActivityExample;
import it.memelabs.smartnebula.lmm.persistence.main.dto.UserLogin;
import it.memelabs.smartnebula.lmm.persistence.main.mapper.ConstructionSiteLogActivityExMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author Andrea Fossi.
 */
@Service
public class CslActivityDao {
    private final ConstructionSiteLogActivityExMapper constructionSiteLogActivityExMapper;

    @Autowired
    public CslActivityDao(ConstructionSiteLogActivityExMapper constructionSiteLogActivityExMapper) {
        this.constructionSiteLogActivityExMapper = constructionSiteLogActivityExMapper;
    }

    public Long create(ConstructionSiteLogActivity record, UserLogin user) {
        Date createdStamp = new Date();
        record.setModifiedStamp(createdStamp);
        record.setModifiedByUserId(user.getId());
        record.setCreatedStamp(createdStamp);
        record.setCreatedByUserId(user.getId());
        record.setOwnerNodeId(user.getActiveNode().getId());
        int result = constructionSiteLogActivityExMapper.insert(record);
        if (result > 0)
            return record.getId();
        else return null;
    }

    public boolean update(ConstructionSiteLogActivity record, UserLogin user) {
        record.setModifiedStamp(new Date());
        record.setModifiedByUserId(user.getId());
        record.setOwnerNodeId(user.getActiveNode().getId());
        int result = constructionSiteLogActivityExMapper.updateByPrimaryKey(record);
        return result > 0;
    }

    public boolean delete(long id) {
        int result = constructionSiteLogActivityExMapper.deleteByPrimaryKey(id);
        return result > 0;
    }

    public ConstructionSiteLogActivity findById(long id) {
        return constructionSiteLogActivityExMapper.selectByPrimaryKey(id);
    }


    public boolean deleteByConstructionSiteLogId(long constructionSiteLogId, UserLogin user) {
        ConstructionSiteLogActivityExample example = new ConstructionSiteLogActivityExample();
        ConstructionSiteLogActivityExample.Criteria criteria = example.createCriteria();
        criteria.andConstructionSiteLogIdEqualTo(constructionSiteLogId);
        criteria.andOwnerNodeIdEqualTo(user.getActiveNode().getId());
        int count = constructionSiteLogActivityExMapper.deleteByExample(example);
        return count > 0;
    }


    public ConstructionSiteLogActivityEx findExById(long id) {
        return constructionSiteLogActivityExMapper.selectExByPrimaryKey(id);
    }

    public int count(long cslId, Long wbsId, Long companyId, UserLogin user) {
        ConstructionSiteLogActivityExample example = new ConstructionSiteLogActivityExample();
        ConstructionSiteLogActivityExample.Criteria criteria = example.createCriteria();
        criteria.andOwnerNodeIdEqualTo(user.getActiveNode().getId());
        criteria.andConstructionSiteLogIdEqualTo(cslId);
        if (companyId != null) criteria.andCompanyIdEqualTo(companyId);
        if (wbsId != null) criteria.andWbsIdEqualTo(wbsId);
        int count = constructionSiteLogActivityExMapper.countByExample(example);
        return count;
    }

    public Tuple2<List<ConstructionSiteLogActivityEx>, Integer> find(long cslId, Long wbsId, Long companyId, UserLogin user) {
        ConstructionSiteLogActivityExample example = new ConstructionSiteLogActivityExample();
        ConstructionSiteLogActivityExample.Criteria criteria = example.createCriteria();
        criteria.andOwnerNodeIdEqualTo(user.getActiveNode().getId());
        criteria.andConstructionSiteLogIdEqualTo(cslId);
        if (companyId != null) criteria.andCompanyIdEqualTo(companyId);
        if (wbsId != null) criteria.andWbsIdEqualTo(wbsId);
        int count = constructionSiteLogActivityExMapper.countByExample(example);
        example.setOrderByClause("c_business_name ASC, wbs_code ASC, wbs_description ASC");
        List<ConstructionSiteLogActivityEx> list = constructionSiteLogActivityExMapper.selectExByExample(example);
        return new Tuple2<>(list, count);
    }
}
