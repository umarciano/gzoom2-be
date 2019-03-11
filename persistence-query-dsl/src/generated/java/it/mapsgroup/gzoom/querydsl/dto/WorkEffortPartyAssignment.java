package it.mapsgroup.gzoom.querydsl.dto;

import javax.annotation.processing.Generated;
import com.querydsl.sql.Column;
import it.mapsgroup.gzoom.querydsl.AbstractIdentity;

/**
 * WorkEffortPartyAssignment is a Querydsl bean type
 */
@Generated("com.querydsl.codegen.BeanSerializer")
public class WorkEffortPartyAssignment implements AbstractIdentity {

    @Column("ASSIGNED_BY_USER_LOGIN_ID")
    private String assignedByUserLoginId;

    @Column("AVAILABILITY_STATUS_ID")
    private String availabilityStatusId;

    @Column("COMMENTS")
    private String comments;

    @Column("CREATED_BY_USER_LOGIN")
    private String createdByUserLogin;

    @Column("CREATED_STAMP")
    private java.time.LocalDateTime createdStamp;

    @Column("CREATED_TX_STAMP")
    private java.time.LocalDateTime createdTxStamp;

    @Column("DELEGATE_REASON_ENUM_ID")
    private String delegateReasonEnumId;

    @Column("EXPECTATION_ENUM_ID")
    private String expectationEnumId;

    @Column("FACILITY_ID")
    private String facilityId;

    @Column("FROM_DATE")
    private java.time.LocalDateTime fromDate;

    @Column("FROM_DATE_FROM")
    private java.time.LocalDateTime fromDateFrom;

    @Column("IS_POSTED")
    private Boolean isPosted;

    @Column("LAST_MODIFIED_BY_USER_LOGIN")
    private String lastModifiedByUserLogin;

    @Column("LAST_UPDATED_STAMP")
    private java.time.LocalDateTime lastUpdatedStamp;

    @Column("LAST_UPDATED_TX_STAMP")
    private java.time.LocalDateTime lastUpdatedTxStamp;

    @Column("MUST_RSVP")
    private Boolean mustRsvp;

    @Column("PARTY_ID")
    private String partyId;

    @Column("PARTY_ID_FROM")
    private String partyIdFrom;

    @Column("PLANNED_HOURS")
    private java.math.BigDecimal plannedHours;

    @Column("ROLE_TYPE_ID")
    private String roleTypeId;

    @Column("ROLE_TYPE_ID_FROM")
    private String roleTypeIdFrom;

    @Column("ROLE_TYPE_WEIGHT")
    private java.math.BigDecimal roleTypeWeight;

    @Column("ROLE_TYPE_WEIGHT_ACTUAL")
    private java.math.BigDecimal roleTypeWeightActual;

    @Column("STATUS_DATE_TIME")
    private java.time.LocalDateTime statusDateTime;

    @Column("STATUS_ID")
    private String statusId;

    @Column("THRU_DATE")
    private java.time.LocalDateTime thruDate;

    @Column("WORK_EFFORT_ID")
    private String workEffortId;

    @Column("WORK_EFFORT_ID_FROM")
    private String workEffortIdFrom;

    @Column("WORK_EFFORT_MEASURE_ID")
    private String workEffortMeasureId;

    public String getAssignedByUserLoginId() {
        return assignedByUserLoginId;
    }

    public void setAssignedByUserLoginId(String assignedByUserLoginId) {
        this.assignedByUserLoginId = assignedByUserLoginId;
    }

    public String getAvailabilityStatusId() {
        return availabilityStatusId;
    }

    public void setAvailabilityStatusId(String availabilityStatusId) {
        this.availabilityStatusId = availabilityStatusId;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getCreatedByUserLogin() {
        return createdByUserLogin;
    }

    public void setCreatedByUserLogin(String createdByUserLogin) {
        this.createdByUserLogin = createdByUserLogin;
    }

    public java.time.LocalDateTime getCreatedStamp() {
        return createdStamp;
    }

    public void setCreatedStamp(java.time.LocalDateTime createdStamp) {
        this.createdStamp = createdStamp;
    }

    public java.time.LocalDateTime getCreatedTxStamp() {
        return createdTxStamp;
    }

    public void setCreatedTxStamp(java.time.LocalDateTime createdTxStamp) {
        this.createdTxStamp = createdTxStamp;
    }

    public String getDelegateReasonEnumId() {
        return delegateReasonEnumId;
    }

    public void setDelegateReasonEnumId(String delegateReasonEnumId) {
        this.delegateReasonEnumId = delegateReasonEnumId;
    }

    public String getExpectationEnumId() {
        return expectationEnumId;
    }

    public void setExpectationEnumId(String expectationEnumId) {
        this.expectationEnumId = expectationEnumId;
    }

    public String getFacilityId() {
        return facilityId;
    }

    public void setFacilityId(String facilityId) {
        this.facilityId = facilityId;
    }

    public java.time.LocalDateTime getFromDate() {
        return fromDate;
    }

    public void setFromDate(java.time.LocalDateTime fromDate) {
        this.fromDate = fromDate;
    }

    public java.time.LocalDateTime getFromDateFrom() {
        return fromDateFrom;
    }

    public void setFromDateFrom(java.time.LocalDateTime fromDateFrom) {
        this.fromDateFrom = fromDateFrom;
    }

    public Boolean getIsPosted() {
        return isPosted;
    }

    public void setIsPosted(Boolean isPosted) {
        this.isPosted = isPosted;
    }

    public String getLastModifiedByUserLogin() {
        return lastModifiedByUserLogin;
    }

    public void setLastModifiedByUserLogin(String lastModifiedByUserLogin) {
        this.lastModifiedByUserLogin = lastModifiedByUserLogin;
    }

    public java.time.LocalDateTime getLastUpdatedStamp() {
        return lastUpdatedStamp;
    }

    public void setLastUpdatedStamp(java.time.LocalDateTime lastUpdatedStamp) {
        this.lastUpdatedStamp = lastUpdatedStamp;
    }

    public java.time.LocalDateTime getLastUpdatedTxStamp() {
        return lastUpdatedTxStamp;
    }

    public void setLastUpdatedTxStamp(java.time.LocalDateTime lastUpdatedTxStamp) {
        this.lastUpdatedTxStamp = lastUpdatedTxStamp;
    }

    public Boolean getMustRsvp() {
        return mustRsvp;
    }

    public void setMustRsvp(Boolean mustRsvp) {
        this.mustRsvp = mustRsvp;
    }

    public String getPartyId() {
        return partyId;
    }

    public void setPartyId(String partyId) {
        this.partyId = partyId;
    }

    public String getPartyIdFrom() {
        return partyIdFrom;
    }

    public void setPartyIdFrom(String partyIdFrom) {
        this.partyIdFrom = partyIdFrom;
    }

    public java.math.BigDecimal getPlannedHours() {
        return plannedHours;
    }

    public void setPlannedHours(java.math.BigDecimal plannedHours) {
        this.plannedHours = plannedHours;
    }

    public String getRoleTypeId() {
        return roleTypeId;
    }

    public void setRoleTypeId(String roleTypeId) {
        this.roleTypeId = roleTypeId;
    }

    public String getRoleTypeIdFrom() {
        return roleTypeIdFrom;
    }

    public void setRoleTypeIdFrom(String roleTypeIdFrom) {
        this.roleTypeIdFrom = roleTypeIdFrom;
    }

    public java.math.BigDecimal getRoleTypeWeight() {
        return roleTypeWeight;
    }

    public void setRoleTypeWeight(java.math.BigDecimal roleTypeWeight) {
        this.roleTypeWeight = roleTypeWeight;
    }

    public java.math.BigDecimal getRoleTypeWeightActual() {
        return roleTypeWeightActual;
    }

    public void setRoleTypeWeightActual(java.math.BigDecimal roleTypeWeightActual) {
        this.roleTypeWeightActual = roleTypeWeightActual;
    }

    public java.time.LocalDateTime getStatusDateTime() {
        return statusDateTime;
    }

    public void setStatusDateTime(java.time.LocalDateTime statusDateTime) {
        this.statusDateTime = statusDateTime;
    }

    public String getStatusId() {
        return statusId;
    }

    public void setStatusId(String statusId) {
        this.statusId = statusId;
    }

    public java.time.LocalDateTime getThruDate() {
        return thruDate;
    }

    public void setThruDate(java.time.LocalDateTime thruDate) {
        this.thruDate = thruDate;
    }

    public String getWorkEffortId() {
        return workEffortId;
    }

    public void setWorkEffortId(String workEffortId) {
        this.workEffortId = workEffortId;
    }

    public String getWorkEffortIdFrom() {
        return workEffortIdFrom;
    }

    public void setWorkEffortIdFrom(String workEffortIdFrom) {
        this.workEffortIdFrom = workEffortIdFrom;
    }

    public String getWorkEffortMeasureId() {
        return workEffortMeasureId;
    }

    public void setWorkEffortMeasureId(String workEffortMeasureId) {
        this.workEffortMeasureId = workEffortMeasureId;
    }

}

