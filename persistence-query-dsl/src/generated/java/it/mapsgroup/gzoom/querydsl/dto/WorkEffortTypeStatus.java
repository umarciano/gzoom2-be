package it.mapsgroup.gzoom.querydsl.dto;

import javax.annotation.Generated;
import com.querydsl.sql.Column;
import it.mapsgroup.gzoom.querydsl.AbstractIdentity;

/**
 * WorkEffortTypeStatus is a Querydsl bean type
 */
@Generated("com.querydsl.codegen.BeanSerializer")
public class WorkEffortTypeStatus implements AbstractIdentity {

    @Column("CHECK_IS_MANDATORY")
    private Boolean checkIsMandatory;

    @Column("CREATED_BY_USER_LOGIN")
    private String createdByUserLogin;

    @Column("CREATED_STAMP")
    private java.time.LocalDateTime createdStamp;

    @Column("CREATED_TX_STAMP")
    private java.time.LocalDateTime createdTxStamp;

    @Column("CTRL_SCORE_ENUM_ID")
    private String ctrlScoreEnumId;

    @Column("CURRENT_STATUS_ID")
    private String currentStatusId;

    @Column("FREQ_SOLL")
    private java.math.BigInteger freqSoll;

    @Column("GL_FISCAL_TYPE_ID")
    private String glFiscalTypeId;

    @Column("HAS_MANDATORY_ATTR")
    private Boolean hasMandatoryAttr;

    @Column("LAST_MODIFIED_BY_USER_LOGIN")
    private String lastModifiedByUserLogin;

    @Column("LAST_UPDATED_STAMP")
    private java.time.LocalDateTime lastUpdatedStamp;

    @Column("LAST_UPDATED_TX_STAMP")
    private java.time.LocalDateTime lastUpdatedTxStamp;

    @Column("LAT_SOLL")
    private java.math.BigInteger latSoll;

    @Column("MANAGEMENT_ROLE_TYPE_ID")
    private String managementRoleTypeId;

    @Column("MANAG_WE_STATUS_ENUM_ID")
    private String managWeStatusEnumId;

    @Column("NEXT_STATUS_ID")
    private String nextStatusId;

    @Column("ONLY_RESPONSIBLE")
    private Boolean onlyResponsible;

    @Column("START_SOLL")
    private java.math.BigInteger startSoll;

    @Column("SUM_VERIFY")
    private Boolean sumVerify;

    @Column("WORK_EFFORT_TYPE_ROOT_ID")
    private String workEffortTypeRootId;

    public Boolean getCheckIsMandatory() {
        return checkIsMandatory;
    }

    public void setCheckIsMandatory(Boolean checkIsMandatory) {
        this.checkIsMandatory = checkIsMandatory;
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

    public String getCtrlScoreEnumId() {
        return ctrlScoreEnumId;
    }

    public void setCtrlScoreEnumId(String ctrlScoreEnumId) {
        this.ctrlScoreEnumId = ctrlScoreEnumId;
    }

    public String getCurrentStatusId() {
        return currentStatusId;
    }

    public void setCurrentStatusId(String currentStatusId) {
        this.currentStatusId = currentStatusId;
    }

    public java.math.BigInteger getFreqSoll() {
        return freqSoll;
    }

    public void setFreqSoll(java.math.BigInteger freqSoll) {
        this.freqSoll = freqSoll;
    }

    public String getGlFiscalTypeId() {
        return glFiscalTypeId;
    }

    public void setGlFiscalTypeId(String glFiscalTypeId) {
        this.glFiscalTypeId = glFiscalTypeId;
    }

    public Boolean getHasMandatoryAttr() {
        return hasMandatoryAttr;
    }

    public void setHasMandatoryAttr(Boolean hasMandatoryAttr) {
        this.hasMandatoryAttr = hasMandatoryAttr;
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

    public java.math.BigInteger getLatSoll() {
        return latSoll;
    }

    public void setLatSoll(java.math.BigInteger latSoll) {
        this.latSoll = latSoll;
    }

    public String getManagementRoleTypeId() {
        return managementRoleTypeId;
    }

    public void setManagementRoleTypeId(String managementRoleTypeId) {
        this.managementRoleTypeId = managementRoleTypeId;
    }

    public String getManagWeStatusEnumId() {
        return managWeStatusEnumId;
    }

    public void setManagWeStatusEnumId(String managWeStatusEnumId) {
        this.managWeStatusEnumId = managWeStatusEnumId;
    }

    public String getNextStatusId() {
        return nextStatusId;
    }

    public void setNextStatusId(String nextStatusId) {
        this.nextStatusId = nextStatusId;
    }

    public Boolean getOnlyResponsible() {
        return onlyResponsible;
    }

    public void setOnlyResponsible(Boolean onlyResponsible) {
        this.onlyResponsible = onlyResponsible;
    }

    public java.math.BigInteger getStartSoll() {
        return startSoll;
    }

    public void setStartSoll(java.math.BigInteger startSoll) {
        this.startSoll = startSoll;
    }

    public Boolean getSumVerify() {
        return sumVerify;
    }

    public void setSumVerify(Boolean sumVerify) {
        this.sumVerify = sumVerify;
    }

    public String getWorkEffortTypeRootId() {
        return workEffortTypeRootId;
    }

    public void setWorkEffortTypeRootId(String workEffortTypeRootId) {
        this.workEffortTypeRootId = workEffortTypeRootId;
    }

}

