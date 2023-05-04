package it.mapsgroup.gzoom.querydsl.dto;

import javax.annotation.Generated;
import com.querydsl.sql.Column;
import it.mapsgroup.gzoom.querydsl.AbstractIdentity;

/**
 * WorkEffortMeasure is a Querydsl bean type
 */
@Generated("com.querydsl.codegen.BeanSerializer")
public class WorkEffortMeasure implements AbstractIdentity {

    @Column("COMMENTS")
    private String comments;

    @Column("COMMENTS_LANG")
    private String commentsLang;

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

    @Column("DETAIL_ENUM_ID")
    private String detailEnumId;

    @Column("EMPL_POSITION_TYPE_ID")
    private String emplPositionTypeId;

    @Column("FROM_DATE")
    private java.time.LocalDateTime fromDate;

    @Column("GL_ACCOUNT_ID")
    private String glAccountId;

    @Column("GL_FISCAL_TYPE_ENUM_ID")
    private String glFiscalTypeEnumId;

    @Column("IS_INVISIBLE")
    private Boolean isInvisible;

    @Column("IS_POSTED")
    private Boolean isPosted;

    @Column("KPI_OTHER_WEIGHT")
    private java.math.BigDecimal kpiOtherWeight;

    @Column("KPI_SCORE_WEIGHT")
    private java.math.BigDecimal kpiScoreWeight;

    @Column("LAST_MODIFIED_BY_USER_LOGIN")
    private String lastModifiedByUserLogin;

    @Column("LAST_UPDATED_STAMP")
    private java.time.LocalDateTime lastUpdatedStamp;

    @Column("LAST_UPDATED_TX_STAMP")
    private java.time.LocalDateTime lastUpdatedTxStamp;

    @Column("ORG_UNIT_ID")
    private String orgUnitId;

    @Column("ORG_UNIT_ROLE_TYPE_ID")
    private String orgUnitRoleTypeId;

    @Column("OTHER_WORK_EFFORT_ID")
    private String otherWorkEffortId;

    @Column("PARTY_ID")
    private String partyId;

    @Column("PERIOD_TYPE_ID")
    private String periodTypeId;

    @Column("PRODUCT_ID")
    private String productId;

    @Column("ROLE_TYPE_ID")
    private String roleTypeId;

    @Column("SEQUENCE_ID")
    private java.math.BigInteger sequenceId;

    @Column("SOURCE")
    private String source;

    @Column("THRU_DATE")
    private java.time.LocalDateTime thruDate;

    @Column("UOM_DESCR")
    private String uomDescr;

    @Column("UOM_DESCR_LANG")
    private String uomDescrLang;

    @Column("UOM_RANGE_ID")
    private String uomRangeId;

    @Column("WE_ALERT_RULE_ENUM_ID")
    private String weAlertRuleEnumId;

    @Column("WE_MEASURE_TYPE_ENUM_ID")
    private String weMeasureTypeEnumId;

    @Column("WE_OTHER_GOAL_ENUM_ID")
    private String weOtherGoalEnumId;

    @Column("WE_SCORE_CONV_ENUM_ID")
    private String weScoreConvEnumId;

    @Column("WE_SCORE_RANGE_ENUM_ID")
    private String weScoreRangeEnumId;

    @Column("WE_WITHOUT_PERF")
    private String weWithoutPerf;

    @Column("WORK_EFFORT_ID")
    private String workEffortId;

    @Column("WORK_EFFORT_INFLUENCE_ID")
    private String workEffortInfluenceId;

    @Column("WORK_EFFORT_MEASURE_ID")
    private String workEffortMeasureId;

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getCommentsLang() {
        return commentsLang;
    }

    public void setCommentsLang(String commentsLang) {
        this.commentsLang = commentsLang;
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

    public String getDetailEnumId() {
        return detailEnumId;
    }

    public void setDetailEnumId(String detailEnumId) {
        this.detailEnumId = detailEnumId;
    }

    public String getEmplPositionTypeId() {
        return emplPositionTypeId;
    }

    public void setEmplPositionTypeId(String emplPositionTypeId) {
        this.emplPositionTypeId = emplPositionTypeId;
    }

    public java.time.LocalDateTime getFromDate() {
        return fromDate;
    }

    public void setFromDate(java.time.LocalDateTime fromDate) {
        this.fromDate = fromDate;
    }

    public String getGlAccountId() {
        return glAccountId;
    }

    public void setGlAccountId(String glAccountId) {
        this.glAccountId = glAccountId;
    }

    public String getGlFiscalTypeEnumId() {
        return glFiscalTypeEnumId;
    }

    public void setGlFiscalTypeEnumId(String glFiscalTypeEnumId) {
        this.glFiscalTypeEnumId = glFiscalTypeEnumId;
    }

    public Boolean getIsInvisible() {
        return isInvisible;
    }

    public void setIsInvisible(Boolean isInvisible) {
        this.isInvisible = isInvisible;
    }

    public Boolean getIsPosted() {
        return isPosted;
    }

    public void setIsPosted(Boolean isPosted) {
        this.isPosted = isPosted;
    }

    public java.math.BigDecimal getKpiOtherWeight() {
        return kpiOtherWeight;
    }

    public void setKpiOtherWeight(java.math.BigDecimal kpiOtherWeight) {
        this.kpiOtherWeight = kpiOtherWeight;
    }

    public java.math.BigDecimal getKpiScoreWeight() {
        return kpiScoreWeight;
    }

    public void setKpiScoreWeight(java.math.BigDecimal kpiScoreWeight) {
        this.kpiScoreWeight = kpiScoreWeight;
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

    public String getOrgUnitId() {
        return orgUnitId;
    }

    public void setOrgUnitId(String orgUnitId) {
        this.orgUnitId = orgUnitId;
    }

    public String getOrgUnitRoleTypeId() {
        return orgUnitRoleTypeId;
    }

    public void setOrgUnitRoleTypeId(String orgUnitRoleTypeId) {
        this.orgUnitRoleTypeId = orgUnitRoleTypeId;
    }

    public String getOtherWorkEffortId() {
        return otherWorkEffortId;
    }

    public void setOtherWorkEffortId(String otherWorkEffortId) {
        this.otherWorkEffortId = otherWorkEffortId;
    }

    public String getPartyId() {
        return partyId;
    }

    public void setPartyId(String partyId) {
        this.partyId = partyId;
    }

    public String getPeriodTypeId() {
        return periodTypeId;
    }

    public void setPeriodTypeId(String periodTypeId) {
        this.periodTypeId = periodTypeId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
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

    public java.time.LocalDateTime getThruDate() {
        return thruDate;
    }

    public void setThruDate(java.time.LocalDateTime thruDate) {
        this.thruDate = thruDate;
    }

    public String getUomDescr() {
        return uomDescr;
    }

    public void setUomDescr(String uomDescr) {
        this.uomDescr = uomDescr;
    }

    public String getUomDescrLang() {
        return uomDescrLang;
    }

    public void setUomDescrLang(String uomDescrLang) {
        this.uomDescrLang = uomDescrLang;
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

    public String getWeOtherGoalEnumId() {
        return weOtherGoalEnumId;
    }

    public void setWeOtherGoalEnumId(String weOtherGoalEnumId) {
        this.weOtherGoalEnumId = weOtherGoalEnumId;
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

    public String getWorkEffortId() {
        return workEffortId;
    }

    public void setWorkEffortId(String workEffortId) {
        this.workEffortId = workEffortId;
    }

    public String getWorkEffortInfluenceId() {
        return workEffortInfluenceId;
    }

    public void setWorkEffortInfluenceId(String workEffortInfluenceId) {
        this.workEffortInfluenceId = workEffortInfluenceId;
    }

    public String getWorkEffortMeasureId() {
        return workEffortMeasureId;
    }

    public void setWorkEffortMeasureId(String workEffortMeasureId) {
        this.workEffortMeasureId = workEffortMeasureId;
    }

}

