package it.mapsgroup.gzoom.querydsl.dto;

import javax.annotation.Generated;
import com.querydsl.sql.Column;
import it.mapsgroup.gzoom.querydsl.AbstractIdentity;

/**
 * WorkEffortRevision is a Querydsl bean type
 */
@Generated("com.querydsl.codegen.BeanSerializer")
public class WorkEffortRevision implements AbstractIdentity {

    @Column("COMMENTS")
    private String comments;

    @Column("CREATED_BY_USER_LOGIN")
    private String createdByUserLogin;

    @Column("CREATED_STAMP")
    private java.time.LocalDateTime createdStamp;

    @Column("CREATED_TX_STAMP")
    private java.time.LocalDateTime createdTxStamp;

    @Column("DESCRIPTION")
    private String description;

    @Column("FROM_DATE")
    private java.time.LocalDateTime fromDate;

    @Column("HAS_ACCTG_TRANS")
    private Boolean hasAcctgTrans;

    @Column("IS_AUTOMATIC")
    private Boolean isAutomatic;

    @Column("IS_CLOSED")
    private Boolean isClosed;

    @Column("LAST_MODIFIED_BY_USER_LOGIN")
    private String lastModifiedByUserLogin;

    @Column("LAST_UPDATED_STAMP")
    private java.time.LocalDateTime lastUpdatedStamp;

    @Column("LAST_UPDATED_TX_STAMP")
    private java.time.LocalDateTime lastUpdatedTxStamp;

    @Column("ORGANIZATION_ID")
    private String organizationId;

    @Column("REF_DATE")
    private java.time.LocalDateTime refDate;

    @Column("TO_DATE")
    private java.time.LocalDateTime toDate;

    @Column("WORK_EFFORT_REVISION_ID")
    private String workEffortRevisionId;

    @Column("WORK_EFFORT_TYPE_ID_CTX")
    private String workEffortTypeIdCtx;

    @Column("WORK_EFFORT_TYPE_ID_FIL")
    private String workEffortTypeIdFil;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public java.time.LocalDateTime getFromDate() {
        return fromDate;
    }

    public void setFromDate(java.time.LocalDateTime fromDate) {
        this.fromDate = fromDate;
    }

    public Boolean getHasAcctgTrans() {
        return hasAcctgTrans;
    }

    public void setHasAcctgTrans(Boolean hasAcctgTrans) {
        this.hasAcctgTrans = hasAcctgTrans;
    }

    public Boolean getIsAutomatic() {
        return isAutomatic;
    }

    public void setIsAutomatic(Boolean isAutomatic) {
        this.isAutomatic = isAutomatic;
    }

    public Boolean getIsClosed() {
        return isClosed;
    }

    public void setIsClosed(Boolean isClosed) {
        this.isClosed = isClosed;
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

    public String getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }

    public java.time.LocalDateTime getRefDate() {
        return refDate;
    }

    public void setRefDate(java.time.LocalDateTime refDate) {
        this.refDate = refDate;
    }

    public java.time.LocalDateTime getToDate() {
        return toDate;
    }

    public void setToDate(java.time.LocalDateTime toDate) {
        this.toDate = toDate;
    }

    public String getWorkEffortRevisionId() {
        return workEffortRevisionId;
    }

    public void setWorkEffortRevisionId(String workEffortRevisionId) {
        this.workEffortRevisionId = workEffortRevisionId;
    }

    public String getWorkEffortTypeIdCtx() {
        return workEffortTypeIdCtx;
    }

    public void setWorkEffortTypeIdCtx(String workEffortTypeIdCtx) {
        this.workEffortTypeIdCtx = workEffortTypeIdCtx;
    }

    public String getWorkEffortTypeIdFil() {
        return workEffortTypeIdFil;
    }

    public void setWorkEffortTypeIdFil(String workEffortTypeIdFil) {
        this.workEffortTypeIdFil = workEffortTypeIdFil;
    }

}

