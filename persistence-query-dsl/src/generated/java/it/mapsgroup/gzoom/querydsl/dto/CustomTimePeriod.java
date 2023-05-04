package it.mapsgroup.gzoom.querydsl.dto;

import javax.annotation.Generated;
import com.querydsl.sql.Column;
import it.mapsgroup.gzoom.querydsl.AbstractIdentity;

/**
 * CustomTimePeriod is a Querydsl bean type
 */
@Generated("com.querydsl.codegen.BeanSerializer")
public class CustomTimePeriod implements AbstractIdentity {

    @Column("CREATED_BY_USER_LOGIN")
    private String createdByUserLogin;

    @Column("CREATED_STAMP")
    private java.time.LocalDateTime createdStamp;

    @Column("CREATED_TX_STAMP")
    private java.time.LocalDateTime createdTxStamp;

    @Column("CUSTOM_TIME_PERIOD_CODE")
    private String customTimePeriodCode;

    @Column("CUSTOM_TIME_PERIOD_CODE_LANG")
    private String customTimePeriodCodeLang;

    @Column("CUSTOM_TIME_PERIOD_ID")
    private String customTimePeriodId;

    @Column("FROM_DATE")
    private java.time.LocalDateTime fromDate;

    @Column("IS_CLOSED")
    private Boolean isClosed;

    @Column("LAST_MODIFIED_BY_USER_LOGIN")
    private String lastModifiedByUserLogin;

    @Column("LAST_UPDATED_STAMP")
    private java.time.LocalDateTime lastUpdatedStamp;

    @Column("LAST_UPDATED_TX_STAMP")
    private java.time.LocalDateTime lastUpdatedTxStamp;

    @Column("PARENT_PERIOD_ID")
    private String parentPeriodId;

    @Column("PERIOD_NAME")
    private String periodName;

    @Column("PERIOD_NAME_LANG")
    private String periodNameLang;

    @Column("PERIOD_NUM")
    private java.math.BigInteger periodNum;

    @Column("PERIOD_TYPE_ID")
    private String periodTypeId;

    @Column("THRU_DATE")
    private java.time.LocalDateTime thruDate;

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

    public String getCustomTimePeriodCode() {
        return customTimePeriodCode;
    }

    public void setCustomTimePeriodCode(String customTimePeriodCode) {
        this.customTimePeriodCode = customTimePeriodCode;
    }

    public String getCustomTimePeriodCodeLang() {
        return customTimePeriodCodeLang;
    }

    public void setCustomTimePeriodCodeLang(String customTimePeriodCodeLang) {
        this.customTimePeriodCodeLang = customTimePeriodCodeLang;
    }

    public String getCustomTimePeriodId() {
        return customTimePeriodId;
    }

    public void setCustomTimePeriodId(String customTimePeriodId) {
        this.customTimePeriodId = customTimePeriodId;
    }

    public java.time.LocalDateTime getFromDate() {
        return fromDate;
    }

    public void setFromDate(java.time.LocalDateTime fromDate) {
        this.fromDate = fromDate;
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

    public String getParentPeriodId() {
        return parentPeriodId;
    }

    public void setParentPeriodId(String parentPeriodId) {
        this.parentPeriodId = parentPeriodId;
    }

    public String getPeriodName() {
        return periodName;
    }

    public void setPeriodName(String periodName) {
        this.periodName = periodName;
    }

    public String getPeriodNameLang() {
        return periodNameLang;
    }

    public void setPeriodNameLang(String periodNameLang) {
        this.periodNameLang = periodNameLang;
    }

    public java.math.BigInteger getPeriodNum() {
        return periodNum;
    }

    public void setPeriodNum(java.math.BigInteger periodNum) {
        this.periodNum = periodNum;
    }

    public String getPeriodTypeId() {
        return periodTypeId;
    }

    public void setPeriodTypeId(String periodTypeId) {
        this.periodTypeId = periodTypeId;
    }

    public java.time.LocalDateTime getThruDate() {
        return thruDate;
    }

    public void setThruDate(java.time.LocalDateTime thruDate) {
        this.thruDate = thruDate;
    }

}

