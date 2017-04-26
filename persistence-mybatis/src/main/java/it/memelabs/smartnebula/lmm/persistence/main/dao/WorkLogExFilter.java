package it.memelabs.smartnebula.lmm.persistence.main.dao;

import it.memelabs.smartnebula.lmm.persistence.main.enumeration.EntityStateTag;

/**
 * Advanced filter to generate {@link it.memelabs.smartnebula.lmm.persistence.main.dto.WorkLogExExample}
 *
 * Filter contained joined where clause
 */
public class WorkLogExFilter extends WorkLogFilter {
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
}
