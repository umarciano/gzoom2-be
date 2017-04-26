package it.memelabs.smartnebula.lmm.persistence.main.dao;

import it.memelabs.smartnebula.lmm.persistence.main.enumeration.EntityStateTag;

public class AntimafiaProcessFilter extends BaseFilter {

    private Long companyId;
    private Long prefectureId;
    private Long lotId;
    private Long stateId;
    private EntityStateTag stateTag;

    private DateRange expiryRange;

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Long getPrefectureId() {
        return prefectureId;
    }

    public void setPrefectureId(Long prefectureId) {
        this.prefectureId = prefectureId;
    }

    public Long getLotId() {
        return lotId;
    }

    public void setLotId(Long lotId) {
        this.lotId = lotId;
    }

    public Long getStateId() {
        return stateId;
    }

    public void setStateId(Long stateId) {
        this.stateId = stateId;
    }

    public DateRange getExpiryRange() {
        return expiryRange;
    }

    public void setExpiryRange(DateRange expiryRange) {
        this.expiryRange = expiryRange;
    }

    public EntityStateTag getStateTag() {
        return stateTag;
    }

    public void setStateTag(EntityStateTag stateTag) {
        this.stateTag = stateTag;
    }
}
