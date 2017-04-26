package it.memelabs.smartnebula.lmm.persistence.main.dto;

import it.memelabs.smartnebula.lmm.persistence.main.enumeration.EntityStateTag;

public class EquipmentEmploymentWwlExample extends EquipmentEmploymentExExample {

    private Long weeklyWorkLogId;
    private EntityStateTag companyStateTag;
    private Long workLogId;
    private Long companyId;

    public EntityStateTag getCompanyStateTag() {
        return companyStateTag;
    }

    public void setCompanyStateTag(EntityStateTag companyStateTag) {
        this.companyStateTag = companyStateTag;
    }

    public Long getWeeklyWorkLogId() {
        return weeklyWorkLogId;
    }

    public void setWeeklyWorkLogId(Long weeklyWorkLogId) {
        this.weeklyWorkLogId = weeklyWorkLogId;
    }

    public Long getWorkLogId() {
        return workLogId;
    }

    public void setWorkLogId(Long workLogId) {
        this.workLogId = workLogId;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }
}
