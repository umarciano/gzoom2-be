package it.mapsgroup.gzoom.querydsl.dto;

import javax.annotation.Generated;
import com.querydsl.sql.Column;
import it.mapsgroup.gzoom.querydsl.AbstractIdentity;

/**
 * WorkEffortAssoc is a Querydsl bean type
 */
@Generated("com.querydsl.codegen.BeanSerializer")
public class WorkEffortAssoc implements AbstractIdentity {

    @Column("ASSOC_WEIGHT")
    private java.math.BigDecimal assocWeight;

    @Column("COMMENTS")
    private String comments;

    @Column("CREATED_BY_USER_LOGIN")
    private String createdByUserLogin;

    @Column("CREATED_STAMP")
    private java.time.LocalDateTime createdStamp;

    @Column("CREATED_TX_STAMP")
    private java.time.LocalDateTime createdTxStamp;

    @Column("FROM_DATE")
    private java.time.LocalDateTime fromDate;

    @Column("IS_POSTED")
    private Boolean isPosted;

    @Column("LAST_MODIFIED_BY_USER_LOGIN")
    private String lastModifiedByUserLogin;

    @Column("LAST_UPDATED_STAMP")
    private java.time.LocalDateTime lastUpdatedStamp;

    @Column("LAST_UPDATED_TX_STAMP")
    private java.time.LocalDateTime lastUpdatedTxStamp;

    @Column("NOTE")
    private String note;

    @Column("RESPONSE_ENUM_ID")
    private String responseEnumId;

    @Column("SEQUENCE_NUM")
    private java.math.BigInteger sequenceNum;

    @Column("THRU_DATE")
    private java.time.LocalDateTime thruDate;

    @Column("WE_MEASURE_EVAL_ID")
    private String weMeasureEvalId;

    @Column("WORK_EFFORT_ASSOC_TYPE_ID")
    private String workEffortAssocTypeId;

    @Column("WORK_EFFORT_ID_FROM")
    private String workEffortIdFrom;

    @Column("WORK_EFFORT_ID_TO")
    private String workEffortIdTo;

    @Column("WORK_EFFORT_REVISION_ID")
    private String workEffortRevisionId;

    public java.math.BigDecimal getAssocWeight() {
        return assocWeight;
    }

    public void setAssocWeight(java.math.BigDecimal assocWeight) {
        this.assocWeight = assocWeight;
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

    public java.time.LocalDateTime getFromDate() {
        return fromDate;
    }

    public void setFromDate(java.time.LocalDateTime fromDate) {
        this.fromDate = fromDate;
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

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getResponseEnumId() {
        return responseEnumId;
    }

    public void setResponseEnumId(String responseEnumId) {
        this.responseEnumId = responseEnumId;
    }

    public java.math.BigInteger getSequenceNum() {
        return sequenceNum;
    }

    public void setSequenceNum(java.math.BigInteger sequenceNum) {
        this.sequenceNum = sequenceNum;
    }

    public java.time.LocalDateTime getThruDate() {
        return thruDate;
    }

    public void setThruDate(java.time.LocalDateTime thruDate) {
        this.thruDate = thruDate;
    }

    public String getWeMeasureEvalId() {
        return weMeasureEvalId;
    }

    public void setWeMeasureEvalId(String weMeasureEvalId) {
        this.weMeasureEvalId = weMeasureEvalId;
    }

    public String getWorkEffortAssocTypeId() {
        return workEffortAssocTypeId;
    }

    public void setWorkEffortAssocTypeId(String workEffortAssocTypeId) {
        this.workEffortAssocTypeId = workEffortAssocTypeId;
    }

    public String getWorkEffortIdFrom() {
        return workEffortIdFrom;
    }

    public void setWorkEffortIdFrom(String workEffortIdFrom) {
        this.workEffortIdFrom = workEffortIdFrom;
    }

    public String getWorkEffortIdTo() {
        return workEffortIdTo;
    }

    public void setWorkEffortIdTo(String workEffortIdTo) {
        this.workEffortIdTo = workEffortIdTo;
    }

    public String getWorkEffortRevisionId() {
        return workEffortRevisionId;
    }

    public void setWorkEffortRevisionId(String workEffortRevisionId) {
        this.workEffortRevisionId = workEffortRevisionId;
    }

}

