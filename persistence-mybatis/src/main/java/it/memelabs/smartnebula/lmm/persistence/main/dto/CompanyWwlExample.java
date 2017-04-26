package it.memelabs.smartnebula.lmm.persistence.main.dto;

import it.memelabs.smartnebula.lmm.persistence.main.enumeration.EntityStateTag;

import java.util.Date;

/**
 * Used only for WeeklyWorkLog query
 */
public class CompanyWwlExample extends CompanyExExample {

    private Date employmentStartDate;
    private Date employmentEndDate;
    private Long weeklyWorkLogId;
    private EntityStateTag employmentStateTag;

    private Long workLogId;

    public Date getEmploymentEndDate() {
        return employmentEndDate;
    }


    public void setEmploymentEndDate(Date employmentEndDate) {
        this.employmentEndDate = employmentEndDate;
    }

    public Date getEmploymentStartDate() {
        return employmentStartDate;
    }

    public void setEmploymentStartDate(Date employmentStartDate) {
        this.employmentStartDate = employmentStartDate;
    }

    public Long getWeeklyWorkLogId() {
        return weeklyWorkLogId;
    }

    public void setWeeklyWorkLogId(Long weeklyWorkLogId) {
        this.weeklyWorkLogId = weeklyWorkLogId;
    }

    public EntityStateTag getEmploymentStateTag() {
        return employmentStateTag;
    }

    public void setEmploymentStateTag(EntityStateTag employmentStateTag) {
        this.employmentStateTag = employmentStateTag;
    }

    public Long getWorkLogId() {
        return workLogId;
    }

    public void setWorkLogId(Long workLogId) {
        this.workLogId = workLogId;
    }
}
