package it.memelabs.smartnebula.lmm.persistence.main.dao;

import it.mapsgroup.commons.collect.Tuple2;
import it.memelabs.smartnebula.lmm.persistence.main.dto.*;
import it.memelabs.smartnebula.lmm.persistence.main.enumeration.ContractCatalogType;
import it.memelabs.smartnebula.lmm.persistence.main.enumeration.EntityStateReference;
import it.memelabs.smartnebula.lmm.persistence.main.mapper.ContractCatalogMapper;
import it.memelabs.smartnebula.lmm.persistence.main.mapper.ContractConstructionSiteMapper;
import it.memelabs.smartnebula.lmm.persistence.main.mapper.ContractExMapper;
import it.memelabs.smartnebula.lmm.persistence.main.mapper.EntityStateMapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

import static it.memelabs.smartnebula.commons.DateUtil.getEndOfDay;
import static it.memelabs.smartnebula.commons.DateUtil.getStartOfDay;

/**
 * @author Andrea Fossi.
 */
@Service
public class ContractDao {
    private final ContractExMapper contractMapper;
    private final ContractCatalogMapper ContractCatalogMapper;
    private final EntityStateMapper entityStateMapper;
    private final ContractConstructionSiteMapper contractConstructionSiteMapper;

    @Autowired
    public ContractDao(ContractExMapper contractMapper,
                       ContractCatalogMapper ContractCatalogMapper,
                       EntityStateMapper entityStateMapper,
                       ContractConstructionSiteMapper contractConstructionSiteMapper) {
        this.contractMapper = contractMapper;
        this.ContractCatalogMapper = ContractCatalogMapper;
        this.entityStateMapper = entityStateMapper;
        this.contractConstructionSiteMapper = contractConstructionSiteMapper;
    }

    public List<EntityState> selectStates(UserLogin user) {
        EntityStateExample entityStateExample = new EntityStateExample();
        entityStateExample.createCriteria() //
                .andOwnerNodeIdEqualTo(user.getActiveNode().getId()) //
                .andEntityEqualTo(EntityStateReference.CONTRACT.name());
        entityStateExample.setOrderByClause("id");
        return entityStateMapper.selectByExample(entityStateExample);
    }

    public List<ContractCatalog> selectCatalog(ContractCatalogType catalogType, UserLogin user) {
        ContractCatalogExample entityStateExample = new ContractCatalogExample();
        ContractCatalogExample.Criteria criteria = entityStateExample.createCriteria();
        criteria.andOwnerNodeIdEqualTo(user.getActiveNode().getId());
        if (catalogType != null)
            criteria.andTypeIdEqualTo(catalogType);
        entityStateExample.setOrderByClause("id");
        return ContractCatalogMapper.selectByExample(entityStateExample);
    }

    public Long create(Contract record, List<Long> constructionSiteIds, UserLogin user) {
        Date createdStamp = new Date();
        record.setModifiedStamp(createdStamp);
        record.setModifiedByUserId(user.getId());
        record.setCreatedStamp(createdStamp);
        record.setCreatedByUserId(user.getId());
        record.setOwnerNodeId(user.getActiveNode().getId());
        record.setModifiedStateStamp(createdStamp);
        int result = contractMapper.insert(record);
        if (result > 0) {
            constructionSiteIds.forEach(id -> {
                ContractConstructionSite ccs = new ContractConstructionSite();
                ccs.setCreatedByUserId(user.getId());
                ccs.setCreatedStamp(createdStamp);
                ccs.setConstructionSiteId(id);
                ccs.setContractId(record.getId());
                contractConstructionSiteMapper.insert(ccs);
            });
            return record.getId();
        } else
            return null;
    }

    public Contract findById(long id) {
        return contractMapper.selectByPrimaryKey(id);
    }

    public ContractEx findExById(long id) {
        return contractMapper.selectByPrimaryKeyEx(id);
    }

    public Tuple2<List<Contract>, Integer> findByFilter(ContractFilter filter, UserLogin user) {
        ContractExExample contractExample = makeContractExExample(filter, user);
        int count = contractMapper.countByExample(contractExample);
        List<Contract> list = contractMapper.selectByExampleWithRowbounds(contractExample, filter.makeRowBounds());
        return new Tuple2<>(list, count);
    }

    public Tuple2<List<ContractEx>, Integer> findExByFilter(ContractFilter filter, UserLogin user) {
        ContractExExample contractExample = makeContractExExample(filter, user);
        int count = contractMapper.countByExample(contractExample);
        List<ContractEx> list = contractMapper.selectByExampleWithRowboundsEx(contractExample, filter.makeRowBounds());
        return new Tuple2<>(list, count);
    }

    public Tuple2<List<ContractExMgo>, Integer> findExByFilterMgo(ContractFilter filter, UserLogin user) {
        ContractExExample contractExample = makeContractExExample(filter, user);
        int count = contractMapper.countByExample(contractExample);
        List<ContractExMgo> list = contractMapper.selectByExampleWithRowboundsExMgo(contractExample, filter.makeRowBounds());
        return new Tuple2<>(list, count);
    }

    public Tuple2<List<Contract>, Integer> lookForReferenceContracts(ContractFilter filter, UserLogin user) {
        ContractExExample contractExample = makeContractExExample(filter, user);
        int count = contractMapper.countForReferenceContracts(contractExample);
        List<Contract> list = contractMapper.lookForReferenceContracts(contractExample, filter.makeRowBounds());
        return new Tuple2<>(list, count);
    }

    /**
     * Update Contract record
     *
     * @param record
     * @param user
     * @return
     */
    public boolean update(Contract record, UserLogin user) {
        return update(record, new Date(), user) > 0;
    }

    /**
     * Internal Contract record update
     *
     * @param record
     * @param modifiedStamp
     * @param user
     * @return
     */
    private int update(Contract record, Date modifiedStamp, UserLogin user) {
        record.setModifiedStamp(modifiedStamp);
        record.setModifiedByUserId(user.getId());
        record.setOwnerNodeId(user.getActiveNode().getId());
        int result = contractMapper.updateByPrimaryKey(record);
        return result;
    }

    /**
     * Update Contract and create/delete contractConstructionSite records
     *
     * @param record
     * @param constructionSiteIds
     * @param user
     * @return
     */
    public boolean update(Contract record, List<Long> constructionSiteIds, UserLogin user) {
        Date modifiedStamp = new Date();
        int result = update(record, modifiedStamp, user);
        ContractConstructionSiteExample example = new ContractConstructionSiteExample();
        ContractConstructionSiteExample.Criteria criteria = example.createCriteria();
        criteria.andContractIdEqualTo(record.getId());
        contractConstructionSiteMapper.deleteByExample(example);
        constructionSiteIds.forEach(id -> {
            ContractConstructionSite ccs = new ContractConstructionSite();
            ccs.setCreatedByUserId(user.getId());
            ccs.setCreatedStamp(modifiedStamp);
            ccs.setConstructionSiteId(id);
            ccs.setContractId(record.getId());
            contractConstructionSiteMapper.insert(ccs);
        });
        return result > 0;
    }

    public boolean updateState(long id, long newStateId, long oldStateId, UserLogin user) {
        EntityState newState = entityStateMapper.selectByPrimaryKey(newStateId);
        Contract record = contractMapper.selectByPrimaryKey(id);
        Date modifiedStamp = new Date();
        record.setModifiedStamp(modifiedStamp);
        record.setModifiedByUserId(user.getId());
        record.setOwnerNodeId(user.getActiveNode().getId());
        record.setStateId(newStateId);
        record.setStateTag(newState.getTag());
        record.setModifiedStateStamp(modifiedStamp);
        ContractExample example = new ContractExample();
        ContractExample.Criteria criteria = example.createCriteria();
        criteria.andStateIdEqualTo(oldStateId);
        criteria.andIdEqualTo(id);
        criteria.andOwnerNodeIdEqualTo(user.getActiveNode().getId());
        int result = contractMapper.updateByExample(record, example);
        return result > 0;
    }

    public boolean delete(long id) {
        // Delete contract associations
        ContractConstructionSiteExample contractConstructionSiteExample = new ContractConstructionSiteExample();
        ContractConstructionSiteExample.Criteria contractConstructionSiteCriteria = contractConstructionSiteExample.createCriteria();
        contractConstructionSiteCriteria.andContractIdEqualTo(id);
        contractConstructionSiteMapper.deleteByExample(contractConstructionSiteExample);
        // Delete contract
        int result = contractMapper.deleteByPrimaryKey(id);
        return result > 0;
    }

    private ContractExExample makeContractExExample(ContractFilter filter, UserLogin user) {
        filter.setOwnerNodeId(user.getActiveNode().getId());

        ContractExExample contractExample = new ContractExExample();
        ContractExExample.Criteria criteria = contractExample.createCriteria();
        criteria.andOwnerNodeIdEqualTo(filter.getOwnerNodeId());

        if (filter.getAntimafiaProcessId() != null) {
            criteria.andAntimafiaProcessIdEqualTo(filter.getAntimafiaProcessId());
        }
        if (filter.getCompanyId() != null) {
            criteria.andCompanyIdEqualTo(filter.getCompanyId());
        }
        if (filter.getPerformingCompanyId() != null) {
            criteria.andPerformingCompanyIdEqualTo(filter.getPerformingCompanyId());
        }
        if (filter.getContractTypeId() != null) {
            criteria.andContractTypeIdEqualTo(filter.getContractTypeId());
        }
        if (filter.getStateId() != null) {
            criteria.andStateIdEqualTo(filter.getStateId());
        }
        if (StringUtils.isNotBlank(filter.getContractNumber())) {
            criteria.andContractNumberLikeInsensitive(filter.makeLike(filter.getContractNumber()));
        }
        if (filter.getFilterText() != null) {
            contractExample.setFilterText(filter.makeLike(filter.getFilterText()));
        }
        if (filter.getPerformingCompanyIdForRefContracts() != null) {
            contractExample.setPerformingCompanyId(filter.getPerformingCompanyIdForRefContracts());
        }
        if (filter.getReferenceContractId() != null) {
            criteria.andReferenceContractIdEqualTo(filter.getReferenceContractId());
        }
        if (StringUtils.isNotBlank(filter.getCup())) {
            contractExample.setCup(filter.getCup());
        }
        if (filter.getSubjectToMgo() != null) {
            criteria.andSubjectToMgoEqualTo(filter.getSubjectToMgo());
        }
        if (filter.getContractExtensionId() != null) {
            criteria.andContractExtensionIdEqualTo(filter.getContractExtensionId());
        }
        if (filter.getEndDateRange() != null) {
            criteria.andEndDateBetween(getStartOfDay(filter.getEndDateRange().getFrom()), getEndOfDay(filter.getEndDateRange().getTo()));
        }
        if (filter.getStateTag() != null)
            criteria.andStateTagEqualTo(filter.getStateTag());

        contractExample.setOrderByClause("contract_number, id");
        return contractExample;
    }

    public List<ContractEx> findForTree(ContractFilter filter, UserLogin user) {
        ContractExExample example = makeContractExExample(filter, user);
        /*ContractExExample contractExExample = new ContractExExample();
        ContractExample.Criteria criteria = contractExExample.createCriteria();
        criteria.andOwnerNodeIdEqualTo(user.getActiveNode().getId());
        ContractExExample example = contractExExample;*/
        List<ContractEx> test = contractMapper.selectByExampleWithRowboundsExForTree(example, new RowBounds(0, Integer.MAX_VALUE));
        return test;
    }

    public List<ContractEx> findMissingMgoData(Long ownerNodeId,
                                               Date modifiedStateStampFrom,
                                               Date modifiedStateStampTo) {
        return contractMapper.selectMissingMgoData(ownerNodeId, modifiedStateStampFrom, modifiedStateStampTo);
    }
}
