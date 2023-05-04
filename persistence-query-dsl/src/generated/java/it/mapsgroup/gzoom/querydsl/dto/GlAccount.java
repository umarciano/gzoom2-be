package it.mapsgroup.gzoom.querydsl.dto;

import javax.annotation.Generated;
import com.querydsl.sql.Column;
import it.mapsgroup.gzoom.querydsl.AbstractIdentity;

/**
 * GlAccount is a Querydsl bean type
 */
@Generated("com.querydsl.codegen.BeanSerializer")
public class GlAccount implements AbstractIdentity {

    @Column("ACCOUNT_CODE")
    private String accountCode;

    @Column("ACCOUNT_NAME")
    private String accountName;

    @Column("ACCOUNT_NAME_LANG")
    private String accountNameLang;

    @Column("ACCOUNT_TYPE_ENUM_ID")
    private String accountTypeEnumId;

    @Column("CALC_CUSTOM_METHOD_ID")
    private String calcCustomMethodId;

    @Column("CHILD_FOLDER_FILE")
    private String childFolderFile;

    @Column("CREATED_BY_USER_LOGIN")
    private String createdByUserLogin;

    @Column("CREATED_STAMP")
    private java.time.LocalDateTime createdStamp;

    @Column("CREATED_TX_STAMP")
    private java.time.LocalDateTime createdTxStamp;

    @Column("CURRENT_STATUS_ID")
    private String currentStatusId;

    @Column("DATA_SOURCE_ID")
    private String dataSourceId;

    @Column("DEBIT_CREDIT_DEFAULT")
    private String debitCreditDefault;

    @Column("DEFAULT_UOM_ID")
    private String defaultUomId;

    @Column("DESCRIPTION")
    private String description;

    @Column("DESCRIPTION_LANG")
    private String descriptionLang;

    @Column("DETAIL_ENUM_ID")
    private String detailEnumId;

    @Column("DETECT_ORG_UNIT_ID_FLAG")
    private Boolean detectOrgUnitIdFlag;

    @Column("EMPL_POSITION_TYPE_ID")
    private String emplPositionTypeId;

    @Column("EXTERNAL_ID")
    private String externalId;

    @Column("FROM_DATE")
    private java.time.LocalDateTime fromDate;

    @Column("GL_ACCOUNT_CLASS_ID")
    private String glAccountClassId;

    @Column("GL_ACCOUNT_ID")
    private String glAccountId;

    @Column("GL_ACCOUNT_TYPE_ID")
    private String glAccountTypeId;

    @Column("GL_RESOURCE_TYPE_ID")
    private String glResourceTypeId;

    @Column("GL_XBRL_CLASS_ID")
    private String glXbrlClassId;

    @Column("HAS_COMPETENCE")
    private Boolean hasCompetence;

    @Column("INPUT_ENUM_ID")
    private String inputEnumId;

    @Column("LAST_MODIFIED_BY_USER_LOGIN")
    private String lastModifiedByUserLogin;

    @Column("LAST_UPDATED_STAMP")
    private java.time.LocalDateTime lastUpdatedStamp;

    @Column("LAST_UPDATED_TX_STAMP")
    private java.time.LocalDateTime lastUpdatedTxStamp;

    @Column("LOCAL_NAME_CONTENT_ID")
    private String localNameContentId;

    @Column("PARENT_GL_ACCOUNT_ID")
    private String parentGlAccountId;

    @Column("PERIODICAL_ABSOLUTE_ENUM_ID")
    private String periodicalAbsoluteEnumId;

    @Column("PERIOD_TYPE_ID")
    private String periodTypeId;

    @Column("POSTED_BALANCE")
    private java.math.BigDecimal postedBalance;

    @Column("PRIO_CALC")
    private java.math.BigInteger prioCalc;

    @Column("PRODUCT_ID")
    private String productId;

    @Column("REFERENCED_ACCOUNT_ID")
    private String referencedAccountId;

    @Column("RESP_CENTER_ID")
    private String respCenterId;

    @Column("RESP_CENTER_ROLE_TYPE_ID")
    private String respCenterRoleTypeId;

    @Column("ROLE_TYPE_ID")
    private String roleTypeId;

    @Column("SEQUENCE_ID")
    private java.math.BigInteger sequenceId;

    @Column("SOURCE")
    private String source;

    @Column("SOURCE_LANG")
    private String sourceLang;

    @Column("TARGET_PERIOD_ENUM_ID")
    private String targetPeriodEnumId;

    @Column("THRU_DATE")
    private java.time.LocalDateTime thruDate;

    @Column("TREND_ENUM_ID")
    private String trendEnumId;

    @Column("UOM_RANGE_ID")
    private String uomRangeId;

    @Column("WE_ALERT_RULE_ENUM_ID")
    private String weAlertRuleEnumId;

    @Column("WE_MEASURE_TYPE_ENUM_ID")
    private String weMeasureTypeEnumId;

    @Column("WE_SCORE_CONV_ENUM_ID")
    private String weScoreConvEnumId;

    @Column("WE_SCORE_RANGE_ENUM_ID")
    private String weScoreRangeEnumId;

    @Column("WE_WITHOUT_PERF")
    private String weWithoutPerf;

    @Column("WE_WITHOUT_TARGET")
    private String weWithoutTarget;

    public String getAccountCode() {
        return accountCode;
    }

    public void setAccountCode(String accountCode) {
        this.accountCode = accountCode;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAccountNameLang() {
        return accountNameLang;
    }

    public void setAccountNameLang(String accountNameLang) {
        this.accountNameLang = accountNameLang;
    }

    public String getAccountTypeEnumId() {
        return accountTypeEnumId;
    }

    public void setAccountTypeEnumId(String accountTypeEnumId) {
        this.accountTypeEnumId = accountTypeEnumId;
    }

    public String getCalcCustomMethodId() {
        return calcCustomMethodId;
    }

    public void setCalcCustomMethodId(String calcCustomMethodId) {
        this.calcCustomMethodId = calcCustomMethodId;
    }

    public String getChildFolderFile() {
        return childFolderFile;
    }

    public void setChildFolderFile(String childFolderFile) {
        this.childFolderFile = childFolderFile;
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

    public String getCurrentStatusId() {
        return currentStatusId;
    }

    public void setCurrentStatusId(String currentStatusId) {
        this.currentStatusId = currentStatusId;
    }

    public String getDataSourceId() {
        return dataSourceId;
    }

    public void setDataSourceId(String dataSourceId) {
        this.dataSourceId = dataSourceId;
    }

    public String getDebitCreditDefault() {
        return debitCreditDefault;
    }

    public void setDebitCreditDefault(String debitCreditDefault) {
        this.debitCreditDefault = debitCreditDefault;
    }

    public String getDefaultUomId() {
        return defaultUomId;
    }

    public void setDefaultUomId(String defaultUomId) {
        this.defaultUomId = defaultUomId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescriptionLang() {
        return descriptionLang;
    }

    public void setDescriptionLang(String descriptionLang) {
        this.descriptionLang = descriptionLang;
    }

    public String getDetailEnumId() {
        return detailEnumId;
    }

    public void setDetailEnumId(String detailEnumId) {
        this.detailEnumId = detailEnumId;
    }

    public Boolean getDetectOrgUnitIdFlag() {
        return detectOrgUnitIdFlag;
    }

    public void setDetectOrgUnitIdFlag(Boolean detectOrgUnitIdFlag) {
        this.detectOrgUnitIdFlag = detectOrgUnitIdFlag;
    }

    public String getEmplPositionTypeId() {
        return emplPositionTypeId;
    }

    public void setEmplPositionTypeId(String emplPositionTypeId) {
        this.emplPositionTypeId = emplPositionTypeId;
    }

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public java.time.LocalDateTime getFromDate() {
        return fromDate;
    }

    public void setFromDate(java.time.LocalDateTime fromDate) {
        this.fromDate = fromDate;
    }

    public String getGlAccountClassId() {
        return glAccountClassId;
    }

    public void setGlAccountClassId(String glAccountClassId) {
        this.glAccountClassId = glAccountClassId;
    }

    public String getGlAccountId() {
        return glAccountId;
    }

    public void setGlAccountId(String glAccountId) {
        this.glAccountId = glAccountId;
    }

    public String getGlAccountTypeId() {
        return glAccountTypeId;
    }

    public void setGlAccountTypeId(String glAccountTypeId) {
        this.glAccountTypeId = glAccountTypeId;
    }

    public String getGlResourceTypeId() {
        return glResourceTypeId;
    }

    public void setGlResourceTypeId(String glResourceTypeId) {
        this.glResourceTypeId = glResourceTypeId;
    }

    public String getGlXbrlClassId() {
        return glXbrlClassId;
    }

    public void setGlXbrlClassId(String glXbrlClassId) {
        this.glXbrlClassId = glXbrlClassId;
    }

    public Boolean getHasCompetence() {
        return hasCompetence;
    }

    public void setHasCompetence(Boolean hasCompetence) {
        this.hasCompetence = hasCompetence;
    }

    public String getInputEnumId() {
        return inputEnumId;
    }

    public void setInputEnumId(String inputEnumId) {
        this.inputEnumId = inputEnumId;
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

    public String getLocalNameContentId() {
        return localNameContentId;
    }

    public void setLocalNameContentId(String localNameContentId) {
        this.localNameContentId = localNameContentId;
    }

    public String getParentGlAccountId() {
        return parentGlAccountId;
    }

    public void setParentGlAccountId(String parentGlAccountId) {
        this.parentGlAccountId = parentGlAccountId;
    }

    public String getPeriodicalAbsoluteEnumId() {
        return periodicalAbsoluteEnumId;
    }

    public void setPeriodicalAbsoluteEnumId(String periodicalAbsoluteEnumId) {
        this.periodicalAbsoluteEnumId = periodicalAbsoluteEnumId;
    }

    public String getPeriodTypeId() {
        return periodTypeId;
    }

    public void setPeriodTypeId(String periodTypeId) {
        this.periodTypeId = periodTypeId;
    }

    public java.math.BigDecimal getPostedBalance() {
        return postedBalance;
    }

    public void setPostedBalance(java.math.BigDecimal postedBalance) {
        this.postedBalance = postedBalance;
    }

    public java.math.BigInteger getPrioCalc() {
        return prioCalc;
    }

    public void setPrioCalc(java.math.BigInteger prioCalc) {
        this.prioCalc = prioCalc;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getReferencedAccountId() {
        return referencedAccountId;
    }

    public void setReferencedAccountId(String referencedAccountId) {
        this.referencedAccountId = referencedAccountId;
    }

    public String getRespCenterId() {
        return respCenterId;
    }

    public void setRespCenterId(String respCenterId) {
        this.respCenterId = respCenterId;
    }

    public String getRespCenterRoleTypeId() {
        return respCenterRoleTypeId;
    }

    public void setRespCenterRoleTypeId(String respCenterRoleTypeId) {
        this.respCenterRoleTypeId = respCenterRoleTypeId;
    }

    public String getRoleTypeId() {
        return roleTypeId;
    }

    public void setRoleTypeId(String roleTypeId) {
        this.roleTypeId = roleTypeId;
    }

    public java.math.BigInteger getSequenceId() {
        return sequenceId;
    }

    public void setSequenceId(java.math.BigInteger sequenceId) {
        this.sequenceId = sequenceId;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getSourceLang() {
        return sourceLang;
    }

    public void setSourceLang(String sourceLang) {
        this.sourceLang = sourceLang;
    }

    public String getTargetPeriodEnumId() {
        return targetPeriodEnumId;
    }

    public void setTargetPeriodEnumId(String targetPeriodEnumId) {
        this.targetPeriodEnumId = targetPeriodEnumId;
    }

    public java.time.LocalDateTime getThruDate() {
        return thruDate;
    }

    public void setThruDate(java.time.LocalDateTime thruDate) {
        this.thruDate = thruDate;
    }

    public String getTrendEnumId() {
        return trendEnumId;
    }

    public void setTrendEnumId(String trendEnumId) {
        this.trendEnumId = trendEnumId;
    }

    public String getUomRangeId() {
        return uomRangeId;
    }

    public void setUomRangeId(String uomRangeId) {
        this.uomRangeId = uomRangeId;
    }

    public String getWeAlertRuleEnumId() {
        return weAlertRuleEnumId;
    }

    public void setWeAlertRuleEnumId(String weAlertRuleEnumId) {
        this.weAlertRuleEnumId = weAlertRuleEnumId;
    }

    public String getWeMeasureTypeEnumId() {
        return weMeasureTypeEnumId;
    }

    public void setWeMeasureTypeEnumId(String weMeasureTypeEnumId) {
        this.weMeasureTypeEnumId = weMeasureTypeEnumId;
    }

    public String getWeScoreConvEnumId() {
        return weScoreConvEnumId;
    }

    public void setWeScoreConvEnumId(String weScoreConvEnumId) {
        this.weScoreConvEnumId = weScoreConvEnumId;
    }

    public String getWeScoreRangeEnumId() {
        return weScoreRangeEnumId;
    }

    public void setWeScoreRangeEnumId(String weScoreRangeEnumId) {
        this.weScoreRangeEnumId = weScoreRangeEnumId;
    }

    public String getWeWithoutPerf() {
        return weWithoutPerf;
    }

    public void setWeWithoutPerf(String weWithoutPerf) {
        this.weWithoutPerf = weWithoutPerf;
    }

    public String getWeWithoutTarget() {
        return weWithoutTarget;
    }

    public void setWeWithoutTarget(String weWithoutTarget) {
        this.weWithoutTarget = weWithoutTarget;
    }

}

