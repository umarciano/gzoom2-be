package it.mapsgroup.gzoom.querydsl.dto;

import javax.annotation.Generated;
import com.querydsl.sql.Column;
import it.mapsgroup.gzoom.querydsl.AbstractIdentity;

/**
 * JobLogLog is a Querydsl bean type
 */
@Generated("com.querydsl.codegen.BeanSerializer")
public class JobLogLog implements AbstractIdentity {

    @Column("CREATED_BY_USER_LOGIN")
    private String createdByUserLogin;

    @Column("CREATED_STAMP")
    private java.time.LocalDateTime createdStamp;

    @Column("CREATED_TX_STAMP")
    private java.time.LocalDateTime createdTxStamp;

    @Column("JOB_LOG_ID")
    private String jobLogId;

    @Column("JOB_LOG_LOG_ID")
    private String jobLogLogId;

    @Column("LAST_MODIFIED_BY_USER_LOGIN")
    private String lastModifiedByUserLogin;

    @Column("LAST_UPDATED_STAMP")
    private java.time.LocalDateTime lastUpdatedStamp;

    @Column("LAST_UPDATED_TX_STAMP")
    private java.time.LocalDateTime lastUpdatedTxStamp;

    @Column("LOG_CODE")
    private String logCode;

    @Column("LOG_MESSAGE")
    private String logMessage;

    @Column("LOG_MESSAGE_LONG")
    private String logMessageLong;

    @Column("LOG_TYPE_ENUM_ID")
    private String logTypeEnumId;

    @Column("VALUE_PK1")
    private String valuePk1;

    @Column("VALUE_REF1")
    private String valueRef1;

    @Column("VALUE_REF2")
    private String valueRef2;

    @Column("VALUE_REF3")
    private String valueRef3;

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

    public String getJobLogId() {
        return jobLogId;
    }

    public void setJobLogId(String jobLogId) {
        this.jobLogId = jobLogId;
    }

    public String getJobLogLogId() {
        return jobLogLogId;
    }

    public void setJobLogLogId(String jobLogLogId) {
        this.jobLogLogId = jobLogLogId;
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

    public String getLogCode() {
        return logCode;
    }

    public void setLogCode(String logCode) {
        this.logCode = logCode;
    }

    public String getLogMessage() {
        return logMessage;
    }

    public void setLogMessage(String logMessage) {
        this.logMessage = logMessage;
    }

    public String getLogMessageLong() {
        return logMessageLong;
    }

    public void setLogMessageLong(String logMessageLong) {
        this.logMessageLong = logMessageLong;
    }

    public String getLogTypeEnumId() {
        return logTypeEnumId;
    }

    public void setLogTypeEnumId(String logTypeEnumId) {
        this.logTypeEnumId = logTypeEnumId;
    }

    public String getValuePk1() {
        return valuePk1;
    }

    public void setValuePk1(String valuePk1) {
        this.valuePk1 = valuePk1;
    }

    public String getValueRef1() {
        return valueRef1;
    }

    public void setValueRef1(String valueRef1) {
        this.valueRef1 = valueRef1;
    }

    public String getValueRef2() {
        return valueRef2;
    }

    public void setValueRef2(String valueRef2) {
        this.valueRef2 = valueRef2;
    }

    public String getValueRef3() {
        return valueRef3;
    }

    public void setValueRef3(String valueRef3) {
        this.valueRef3 = valueRef3;
    }

}

