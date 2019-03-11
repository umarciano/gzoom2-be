package it.mapsgroup.gzoom.querydsl.dto;

import javax.annotation.processing.Generated;
import com.querydsl.sql.Column;
import it.mapsgroup.gzoom.querydsl.AbstractIdentity;

/**
 * WorkEffortTypePeriod is a Querydsl bean type
 */
@Generated("com.querydsl.codegen.BeanSerializer")
public class WorkEffortTypePeriod implements AbstractIdentity {

    @Column("CREATED_STAMP")
    private java.time.LocalDateTime createdStamp;

    @Column("CREATED_TX_STAMP")
    private java.time.LocalDateTime createdTxStamp;

    @Column("CUSTOM_TIME_PERIOD_ID")
    private String customTimePeriodId;

    @Column("DES_PROC")
    private String desProc;

    @Column("GL_FISCAL_TYPE_ENUM_ID")
    private String glFiscalTypeEnumId;

    @Column("LAST_UPDATED_STAMP")
    private java.time.LocalDateTime lastUpdatedStamp;

    @Column("LAST_UPDATED_TX_STAMP")
    private java.time.LocalDateTime lastUpdatedTxStamp;

    @Column("PER_LAV_FROM")
    private java.time.LocalDateTime perLavFrom;

    @Column("PER_LAV_THRU")
    private java.time.LocalDateTime perLavThru;

    @Column("STATUS_ENUM_ID")
    private String statusEnumId;

    @Column("STATUS_TYPE_ID")
    private String statusTypeId;

    @Column("WORK_EFFORT_TYPE_ID")
    private String workEffortTypeId;

    @Column("WORK_EFFORT_TYPE_PERIOD_ID")
    private String workEffortTypePeriodId;

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

    public String getCustomTimePeriodId() {
        return customTimePeriodId;
    }

    public void setCustomTimePeriodId(String customTimePeriodId) {
        this.customTimePeriodId = customTimePeriodId;
    }

    public String getDesProc() {
        return desProc;
    }

    public void setDesProc(String desProc) {
        this.desProc = desProc;
    }

    public String getGlFiscalTypeEnumId() {
        return glFiscalTypeEnumId;
    }

    public void setGlFiscalTypeEnumId(String glFiscalTypeEnumId) {
        this.glFiscalTypeEnumId = glFiscalTypeEnumId;
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

    public java.time.LocalDateTime getPerLavFrom() {
        return perLavFrom;
    }

    public void setPerLavFrom(java.time.LocalDateTime perLavFrom) {
        this.perLavFrom = perLavFrom;
    }

    public java.time.LocalDateTime getPerLavThru() {
        return perLavThru;
    }

    public void setPerLavThru(java.time.LocalDateTime perLavThru) {
        this.perLavThru = perLavThru;
    }

    public String getStatusEnumId() {
        return statusEnumId;
    }

    public void setStatusEnumId(String statusEnumId) {
        this.statusEnumId = statusEnumId;
    }

    public String getStatusTypeId() {
        return statusTypeId;
    }

    public void setStatusTypeId(String statusTypeId) {
        this.statusTypeId = statusTypeId;
    }

    public String getWorkEffortTypeId() {
        return workEffortTypeId;
    }

    public void setWorkEffortTypeId(String workEffortTypeId) {
        this.workEffortTypeId = workEffortTypeId;
    }

    public String getWorkEffortTypePeriodId() {
        return workEffortTypePeriodId;
    }

    public void setWorkEffortTypePeriodId(String workEffortTypePeriodId) {
        this.workEffortTypePeriodId = workEffortTypePeriodId;
    }

}

