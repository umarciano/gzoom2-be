package it.memelabs.smartnebula.lmm.persistence.main.dto;

import it.memelabs.smartnebula.lmm.persistence.main.enumeration.EntityStateTag;

/**
 * Used with {@link it.memelabs.smartnebula.lmm.persistence.main.dao.WorkLogExFilter}
 * <p>
 * parameters in this class usually used to filter joined tables
 */
public class WorkLogExExample extends WorkLogExample {
    private Long companyId;
    private EntityStateTag stateTag;

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public EntityStateTag getStateTag() {
        return stateTag;
    }

    public void setStateTag(EntityStateTag stateTag) {
        this.stateTag = stateTag;
    }

    /* public void setStateTag(EntityStateTag stateTag) {
        this.stateTag = stateTag;
    }

    public Criteria andStateTag(Criteria criteria, EntityStateTag value) {
        criteria.addCriterion("state_tag =", value.name(), "stateTag");
        return criteria;
    }*/
}