package it.memelabs.smartnebula.lmm.persistence.main.dao;

import it.mapsgroup.commons.collect.Tuple2;
import it.memelabs.smartnebula.lmm.persistence.main.dto.*;
import it.memelabs.smartnebula.lmm.persistence.main.enumeration.CompanyType;
import it.memelabs.smartnebula.lmm.persistence.main.enumeration.EntityStateReference;
import it.memelabs.smartnebula.lmm.persistence.main.enumeration.EntityStateTag;
import it.memelabs.smartnebula.lmm.persistence.main.mapper.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author Andrea Fossi.
 */
@Service
public class CompanyDao {
    // private static final Logger LOG = getLogger(CompanyDao.class);

    private final CompanyMapper companyMapper;
    private final CompanyExMapper companyExMapper;
    private final CompanyClassificationMapper companyClassificationMapper;
    private final CompanyCategoryMapper companyCategoryMapper;
    private final EntityStateMapper entityStateMapper;
    private final CompanyCompositionExMapper companyCompositionExMapper;

    @Autowired
    public CompanyDao(CompanyMapper companyMapper, CompanyExMapper companyExMapper, CompanyClassificationMapper companyClassificationMapper, CompanyCategoryMapper companyCategoryMapper, EntityStateMapper entityStateMapper,
                      CompanyCompositionExMapper companyCompositionExMapper) {
        this.companyMapper = companyMapper;
        this.companyExMapper = companyExMapper;
        this.companyClassificationMapper = companyClassificationMapper;
        this.companyCategoryMapper = companyCategoryMapper;
        this.entityStateMapper = entityStateMapper;
        this.companyCompositionExMapper = companyCompositionExMapper;
    }

    public Company findById(long id) {
        return companyMapper.selectByPrimaryKey(id);
    }

    public CompanyEx findExById(long id) {
        return companyExMapper.selectByPrimaryKey(id);
    }

    public List<CompanyEx> findByTaxOrVatNumber(int size, String companyType, Long id, String taxIdentificationNumber, String vatNumber, UserLogin user) {
        RowBounds rowBounds = new RowBounds(0, size);
        CompanyExExample companyExample = new CompanyExExample();
        CompanyExExample.Criteria criteria = companyExample.createCriteria();
        criteria.andOwnerNodeIdEqualTo(user.getActiveNode().getId());
        if (StringUtils.isNotBlank(companyType)) {
            criteria.andCompanyTypeEqualTo(companyType);
        }
        companyExample.setFilterTaxOrVatNumber(true);
        companyExample.setTaxIdentificationNumber(taxIdentificationNumber);
        companyExample.setVatNumber(vatNumber);
        if (id != null) {
            criteria.andIdNotEqualTo(id);
        }
        companyExample.setOrderByClause("business_name, id");
        return companyExMapper.selectByExampleWithRowbounds(companyExample, rowBounds);
    }

    public List<CompanyEx> findByBusinessName(int size, String companyType, Long id, String businessName, UserLogin user) {
        RowBounds rowBounds = new RowBounds(0, size);
        CompanyExExample companyExample = new CompanyExExample();
        CompanyExExample.Criteria criteria = companyExample.createCriteria();
        criteria.andOwnerNodeIdEqualTo(user.getActiveNode().getId());
        if (StringUtils.isNotBlank(companyType)) {
            criteria.andCompanyTypeEqualTo(companyType);
        }
        criteria.andBusinessNameEqualToInsensitive(businessName);
        if (id != null) {
            criteria.andIdNotEqualTo(id);
        }
        companyExample.setOrderByClause("business_name, id");
        return companyExMapper.selectByExampleWithRowbounds(companyExample, rowBounds);
    }

    public Tuple2<List<CompanyEx>, Integer> selectByFilter(CompanyFilter filter, UserLogin user) {
        RowBounds rowBounds = new RowBounds((filter.getPage() - 1) * filter.getSize(), filter.getSize());
        filter.setOwnerNodeId(user.getActiveNode().getId());

        CompanyExExample companyExample = new CompanyExExample();
        CompanyExExample.Criteria criteria = companyExample.createCriteria();
        criteria.andOwnerNodeIdEqualTo(filter.getOwnerNodeId());
        if (StringUtils.isNotBlank(filter.getCompanyType())) {
            if (!CompanyType.ANY.name().equalsIgnoreCase(filter.getCompanyType())) {
                criteria.andCompanyTypeEqualTo(filter.getCompanyType());
            }
        }
        if (StringUtils.isNotBlank(filter.getVatNumber())) {
            criteria.andVatNumberLikeInsensitive(filter.makeLike(filter.getVatNumber()));
        }
        if (StringUtils.isNotBlank(filter.getTaxIdentificationNumber())) {
            criteria.andTaxIdentificationNumberLikeInsensitive(filter.makeLike(filter.getTaxIdentificationNumber()));
        }
        if (StringUtils.isNotBlank(filter.getBusinessName())) {
            criteria.andBusinessNameLikeInsensitive(filter.makeLike(filter.getBusinessName()));
        }
        if (filter.getStateId() != null) {
            criteria.andStateIdEqualTo(filter.getStateId());
        }
        if (filter.getClassificationId() != null) {
            criteria.andClassificationIdEqualTo(filter.getClassificationId());
        }
        if (filter.getCategoryId() != null) {
            criteria.andCategoryIdEqualTo(filter.getCategoryId());
        }
        if (filter.getFilterText() != null) {
            companyExample.setFilterText(filter.makeLike(filter.getFilterText()));
        }
        if (filter.getCompanyId() != null) {
            criteria.andIdEqualTo(filter.getCompanyId());
        }
        companyExample.setOrderByClause("business_name, id");

        int count = companyExMapper.countByExample(companyExample);
        List<CompanyEx> list = companyExMapper.selectByExampleWithRowbounds(companyExample, rowBounds);
        return new Tuple2<>(list, count);
    }

    public Long create(Company record, UserLogin user) {
        Date createdStamp = new Date();
        record.setModifiedStamp(createdStamp);
        record.setModifiedByUserId(user.getId());
        record.setCreatedStamp(createdStamp);
        record.setCreatedByUserId(user.getId());
        if (record.getStateId() != null)
            record.setStateTag(entityStateMapper.selectByPrimaryKey(record.getStateId()).getTag());
        // record.setDocumentationStatus(CompanyDocumentationStatus.DOCUMENTATION_INCOMPLETE);
        record.setOwnerNodeId(user.getActiveNode().getId());
        int result = companyMapper.insert(record);
        return (result > 0) ? record.getId() : null;
    }

    public boolean update(Company record, UserLogin user) {
        record.setModifiedStamp(new Date());
        record.setModifiedByUserId(user.getId());
        if (record.getStateId() != null)
            record.setStateTag(entityStateMapper.selectByPrimaryKey(record.getStateId()).getTag());
        int result = companyMapper.updateByPrimaryKey(record);
        return result > 0;
    }

    public boolean delete(long id) {
        int result = companyMapper.deleteByPrimaryKey(id);
        return result > 0;
    }

    public List<CompanyClassification> selectClassifications(UserLogin user) {
        CompanyClassificationExample companyClassificationExample = new CompanyClassificationExample();
        companyClassificationExample.createCriteria().andOwnerNodeIdEqualTo(user.getActiveNode().getId());
        companyClassificationExample.setOrderByClause("id");
        return companyClassificationMapper.selectByExample(companyClassificationExample);
    }

    public List<CompanyCategory> selectCategories(UserLogin user) {
        CompanyCategoryExample companyCategoryExample = new CompanyCategoryExample();
        companyCategoryExample.createCriteria().andOwnerNodeIdEqualTo(user.getActiveNode().getId());
        companyCategoryExample.setOrderByClause("id");
        return companyCategoryMapper.selectByExample(companyCategoryExample);
    }

    public List<EntityState> selectStates(UserLogin user) {
        EntityStateExample entityStateExample = new EntityStateExample();
        entityStateExample.createCriteria() //
                .andOwnerNodeIdEqualTo(user.getActiveNode().getId()) //
                .andEntityEqualTo(EntityStateReference.COMPANY.name());
        entityStateExample.setOrderByClause("id");
        return entityStateMapper.selectByExample(entityStateExample);
    }

    public List<CompanyCompositionEx> selectCompanyComposition(long parentCompanyId) {
        CompanyCompositionExample companyCompositionExample = new CompanyCompositionExample();
        companyCompositionExample.createCriteria().andParentCompanyIdEqualTo(parentCompanyId);
        companyCompositionExample.setOrderByClause("business_name, company_id");
        return companyCompositionExMapper.selectExByExample(companyCompositionExample);
    }

    public void deleteCompositions(long parentCompanyId) {
        CompanyCompositionExample companyCompositionExample = new CompanyCompositionExample();
        companyCompositionExample.createCriteria().andParentCompanyIdEqualTo(parentCompanyId);
        companyCompositionExMapper.deleteByExample(companyCompositionExample);
    }

    public void insertComposition(CompanyComposition composition, UserLogin user) {
        Date nowStamp = new Date();
        composition.setCreatedStamp(nowStamp);
        composition.setCreatedByUserId(user.getId());
        companyCompositionExMapper.insert(composition);
    }

    /**
     * @param page
     * @param size
     * @param start
     * @param end
     * @param wwlId weekly work log id
     * @param user
     * @return
     */
    public Tuple2<List<CompanyEx>, Integer> findForWeeklyWorkLog(int page, int size, Date start, Date end, Long wwlId, Long wlId, UserLogin user) {
        RowBounds rowBounds = new RowBounds((page - 1) * size, size);
        CompanyWwlExample companyExample = new CompanyWwlExample();
        CompanyWwlExample.Criteria criteria = companyExample.createCriteria();
        criteria.andOwnerNodeIdEqualTo(user.getActiveNode().getId());
        //criteria.andCompanyIdEqualTo(companyId);
        companyExample.setEmploymentStartDate(start);
        companyExample.setEmploymentEndDate(end);
        companyExample.setOrderByClause("business_name, id");
        companyExample.setDistinct(true);
        companyExample.setWeeklyWorkLogId(wwlId);
        companyExample.setWorkLogId(wlId);
        companyExample.setEmploymentStateTag(EntityStateTag.AUTHORIZED);
        criteria.andStateTagEqualTo(EntityStateTag.AUTHORIZED);
        int count = companyExMapper.countWeekyWorkLogByExample(companyExample);
        List<CompanyEx> list = companyExMapper.selectWeekyWorkLogByExampleWithRowbounds(companyExample, rowBounds);
        return new Tuple2<>(list, count);
    }
}
