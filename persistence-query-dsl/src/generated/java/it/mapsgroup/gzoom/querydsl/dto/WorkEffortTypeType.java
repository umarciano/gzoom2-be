package it.mapsgroup.gzoom.querydsl.dto;

import javax.annotation.Generated;
import com.querydsl.sql.Column;
import it.mapsgroup.gzoom.querydsl.AbstractIdentity;

/**
 * WorkEffortTypeType is a Querydsl bean type
 */
@Generated("com.querydsl.codegen.BeanSerializer")
public class WorkEffortTypeType implements AbstractIdentity {

    @Column("COMMENTS")
    private String comments;

    @Column("CREATED_BY_USER_LOGIN")
    private String createdByUserLogin;

    @Column("CREATED_STAMP")
    private java.time.LocalDateTime createdStamp;

    @Column("CREATED_TX_STAMP")
    private java.time.LocalDateTime createdTxStamp;

    @Column("LAST_MODIFIED_BY_USER_LOGIN")
    private String lastModifiedByUserLogin;

    @Column("LAST_UPDATED_STAMP")
    private java.time.LocalDateTime lastUpdatedStamp;

    @Column("LAST_UPDATED_TX_STAMP")
    private java.time.LocalDateTime lastUpdatedTxStamp;

    @Column("SEQUENCE_NUM")
    private java.math.BigInteger sequenceNum;

    @Column("WORK_EFFORT_TYPE_ID_FROM")
    private String workEffortTypeIdFrom;

    @Column("WORK_EFFORT_TYPE_ID_ROOT")
    private String workEffortTypeIdRoot;

    @Column("WORK_EFFORT_TYPE_ID_TO")
    private String workEffortTypeIdTo;

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

    public java.math.BigInteger getSequenceNum() {
        return sequenceNum;
    }

    public void setSequenceNum(java.math.BigInteger sequenceNum) {
        this.sequenceNum = sequenceNum;
    }

    public String getWorkEffortTypeIdFrom() {
        return workEffortTypeIdFrom;
    }

    public void setWorkEffortTypeIdFrom(String workEffortTypeIdFrom) {
        this.workEffortTypeIdFrom = workEffortTypeIdFrom;
    }

    public String getWorkEffortTypeIdRoot() {
        return workEffortTypeIdRoot;
    }

    public void setWorkEffortTypeIdRoot(String workEffortTypeIdRoot) {
        this.workEffortTypeIdRoot = workEffortTypeIdRoot;
    }

    public String getWorkEffortTypeIdTo() {
        return workEffortTypeIdTo;
    }

    public void setWorkEffortTypeIdTo(String workEffortTypeIdTo) {
        this.workEffortTypeIdTo = workEffortTypeIdTo;
    }

}

