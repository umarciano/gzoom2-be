package it.memelabs.smartnebula.lmm.persistence.main.dao;

import it.memelabs.smartnebula.lmm.persistence.main.enumeration.EntityStateTag;

public class ContractFilter extends BaseFilter {
    private Long antimafiaProcessId;
    private Long companyId;
    private Long performingCompanyId;
    private Long performingCompanyIdForRefContracts;
    private Long contractTypeId;
    private Long stateId;
    private String contractNumber;
    private String filterText;
    private Boolean subjectToMgo;

    private String cup;
    private Long referenceContractId;
    private Long contractExtensionId;
    private EntityStateTag stateTag;

    private DateRange endDateRange;

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Long getPerformingCompanyId() {
        return performingCompanyId;
    }

    public void setPerformingCompanyId(Long performingCompanyId) {
        this.performingCompanyId = performingCompanyId;
    }

    public Long getPerformingCompanyIdForRefContracts() {
        return performingCompanyIdForRefContracts;
    }

    public void setPerformingCompanyIdForRefContracts(Long performingCompanyIdForRefContracts) {
        this.performingCompanyIdForRefContracts = performingCompanyIdForRefContracts;
    }

    public Long getContractTypeId() {
        return contractTypeId;
    }

    public void setContractTypeId(Long contractTypeId) {
        this.contractTypeId = contractTypeId;
    }

    public Long getStateId() {
        return stateId;
    }

    public void setStateId(Long stateId) {
        this.stateId = stateId;
    }

    public String getContractNumber() {
        return contractNumber;
    }

    public void setContractNumber(String contractNumber) {
        this.contractNumber = contractNumber;
    }

    public String getFilterText() {
        return filterText;
    }

    public void setFilterText(String filterText) {
        this.filterText = filterText;
    }

    public Long getReferenceContractId() {
        return referenceContractId;
    }

    public void setReferenceContractId(Long referenceContractId) {
        this.referenceContractId = referenceContractId;
    }

    public Long getAntimafiaProcessId() {
        return antimafiaProcessId;
    }

    public void setAntimafiaProcessId(Long antimafiaProcessId) {
        this.antimafiaProcessId = antimafiaProcessId;
    }

    public String getCup() {
        return cup;
    }

    public void setCup(String cup) {
        this.cup = cup;
    }

    public Boolean getSubjectToMgo() {
        return subjectToMgo;
    }

    public void setSubjectToMgo(Boolean subjectToMgo) {
        this.subjectToMgo = subjectToMgo;
    }

    public Long getContractExtensionId() {
        return contractExtensionId;
    }

    public void setContractExtensionId(Long contractExtensionId) {
        this.contractExtensionId = contractExtensionId;
    }

    public DateRange getEndDateRange() {
        return endDateRange;
    }

    public void setEndDateRange(DateRange endDateRange) {
        this.endDateRange = endDateRange;
    }

    public EntityStateTag getStateTag() {
        return stateTag;
    }

    public void setStateTag(EntityStateTag stateTag) {
        this.stateTag = stateTag;
    }
}
