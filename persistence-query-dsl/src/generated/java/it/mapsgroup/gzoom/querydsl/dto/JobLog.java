package it.mapsgroup.gzoom.querydsl.dto;

import javax.annotation.Generated;
import com.querydsl.sql.Column;
import it.mapsgroup.gzoom.querydsl.AbstractIdentity;

/**
 * JobLog is a Querydsl bean type
 */
@Generated("com.querydsl.codegen.BeanSerializer")
public class JobLog implements AbstractIdentity {

    @Column("BLOCKING_ERRORS")
    private java.math.BigInteger blockingErrors;

    @Column("CREATED_BY_USER_LOGIN")
    private String createdByUserLogin;

    @Column("CREATED_STAMP")
    private java.time.LocalDateTime createdStamp;

    @Column("CREATED_TX_STAMP")
    private java.time.LocalDateTime createdTxStamp;

    @Column("DESCRIPTION")
    private String description;

    @Column("ELAB_REF1")
    private String elabRef1;

    @Column("JOB_ID")
    private String jobId;

    @Column("JOB_LOG_ID")
    private String jobLogId;

    @Column("LAST_MODIFIED_BY_USER_LOGIN")
    private String lastModifiedByUserLogin;

    @Column("LAST_UPDATED_STAMP")
    private java.time.LocalDateTime lastUpdatedStamp;

    @Column("LAST_UPDATED_TX_STAMP")
    private java.time.LocalDateTime lastUpdatedTxStamp;

    @Column("LOG_DATE")
    private java.time.LocalDateTime logDate;

    @Column("LOG_END_DATE")
    private java.time.LocalDateTime logEndDate;

    @Column("RECORD_ELABORATED")
    private java.math.BigInteger recordElaborated;

    @Column("SERVICE_NAME")
    private String serviceName;

    @Column("SERVICE_TYPE_ID")
    private String serviceTypeId;

    @Column("SESSION_ID")
    private String sessionId;

    @Column("USER_LOGIN_ID")
    private String userLoginId;

    @Column("WARNING_MESSAGES")
    private java.math.BigInteger warningMessages;

    public java.math.BigInteger getBlockingErrors() {
        return blockingErrors;
    }

    public void setBlockingErrors(java.math.BigInteger blockingErrors) {
        this.blockingErrors = blockingErrors;
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

    public String getElabRef1() {
        return elabRef1;
    }

    public void setElabRef1(String elabRef1) {
        this.elabRef1 = elabRef1;
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getJobLogId() {
        return jobLogId;
    }

    public void setJobLogId(String jobLogId) {
        this.jobLogId = jobLogId;
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

    public java.time.LocalDateTime getLogDate() {
        return logDate;
    }

    public void setLogDate(java.time.LocalDateTime logDate) {
        this.logDate = logDate;
    }

    public java.time.LocalDateTime getLogEndDate() {
        return logEndDate;
    }

    public void setLogEndDate(java.time.LocalDateTime logEndDate) {
        this.logEndDate = logEndDate;
    }

    public java.math.BigInteger getRecordElaborated() {
        return recordElaborated;
    }

    public void setRecordElaborated(java.math.BigInteger recordElaborated) {
        this.recordElaborated = recordElaborated;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceTypeId() {
        return serviceTypeId;
    }

    public void setServiceTypeId(String serviceTypeId) {
        this.serviceTypeId = serviceTypeId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getUserLoginId() {
        return userLoginId;
    }

    public void setUserLoginId(String userLoginId) {
        this.userLoginId = userLoginId;
    }

    public java.math.BigInteger getWarningMessages() {
        return warningMessages;
    }

    public void setWarningMessages(java.math.BigInteger warningMessages) {
        this.warningMessages = warningMessages;
    }

}

