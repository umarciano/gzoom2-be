package it.memelabs.smartnebula.lmm.persistence.main.dao;

import it.memelabs.smartnebula.lmm.persistence.main.enumeration.EntityStateTag;
import it.memelabs.smartnebula.lmm.persistence.main.enumeration.WorkLogEventOrigin;

import java.util.Date;
import java.util.List;

public class WorkLogEventFilter extends BaseFilter {
    private Long workLogId;
    private Long wbsId;
    private Boolean wbsNull;
    private Long stateId;
    private Long personEmploymentId;
    private Long equipmentEmploymentId;
    private EntityStateTag stateTag;
    private WorkLogEventOrigin origin;
    private Date eventTimestamp;
    private DateRange eventTimestampBetween;
    private List<Long> ids;

    //joined attributes
    private Long companyId;
    private Long constructionSiteId;
    private Boolean constructionSiteNull;
    private Long personId;
    private Long equipmentId;

    /**
     * if value !=0 query return events with personEmploymentId is
     */
    private Long notInWeeklyWorkLogId;


    public WorkLogEventFilter() {
        //ids = new ArrayList<>();
    }

    public Long getWorkLogId() {
        return workLogId;
    }

    public void setWorkLogId(Long workLogId) {
        this.workLogId = workLogId;
    }

    public Long getPersonId() {
        return personId;
    }

    public void setPersonId(Long personId) {
        this.personId = personId;
    }

    public Long getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(Long equipmentId) {
        this.equipmentId = equipmentId;
    }

    public Long getWbsId() {
        return wbsId;
    }

    public void setWbsId(Long wbsId) {
        this.wbsId = wbsId;
    }

    public Boolean getWbsNull() {
        return wbsNull;
    }

    public void setWbsNull(Boolean wbsNull) {
        this.wbsNull = wbsNull;
    }

    public Long getStateId() {
        return stateId;
    }

    public void setStateId(Long stateId) {
        this.stateId = stateId;
    }

    public Long getPersonEmploymentId() {
        return personEmploymentId;
    }

    public void setPersonEmploymentId(Long personEmploymentId) {
        this.personEmploymentId = personEmploymentId;
    }

    public Long getEquipmentEmploymentId() {
        return equipmentEmploymentId;
    }

    public void setEquipmentEmploymentId(Long equipmentEmploymentId) {
        this.equipmentEmploymentId = equipmentEmploymentId;
    }

    public EntityStateTag getStateTag() {
        return stateTag;
    }

    public void setStateTag(EntityStateTag stateTag) {
        this.stateTag = stateTag;
    }

    public WorkLogEventOrigin getOrigin() {
        return origin;
    }

    public void setOrigin(WorkLogEventOrigin origin) {
        this.origin = origin;
    }

    public List<Long> getIds() {
        return ids;
    }

    public void setIds(List<Long> ids) {
        this.ids = ids;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Long getConstructionSiteId() {
        return constructionSiteId;
    }

    public void setConstructionSiteId(Long constructionSiteId) {
        this.constructionSiteId = constructionSiteId;
    }

    public Date getEventTimestamp() {
        return eventTimestamp;
    }

    public void setEventTimestamp(Date eventTimestamp) {
        this.eventTimestamp = eventTimestamp;
    }

    public DateRange getEventTimestampBetween() {
        return eventTimestampBetween;
    }

    public void setEventTimestampBetween(DateRange eventTimestampBetween) {
        this.eventTimestampBetween = eventTimestampBetween;
    }

    public Boolean getConstructionSiteNull() {
        return constructionSiteNull;
    }

    public void setConstructionSiteNull(Boolean constructionSiteNull) {
        this.constructionSiteNull = constructionSiteNull;
    }

    public Long getNotInWeeklyWorkLogId() {
        return notInWeeklyWorkLogId;
    }

    public void setNotInWeeklyWorkLogId(Long notInWeeklyWorkLogId) {
        this.notInWeeklyWorkLogId = notInWeeklyWorkLogId;
    }
}
