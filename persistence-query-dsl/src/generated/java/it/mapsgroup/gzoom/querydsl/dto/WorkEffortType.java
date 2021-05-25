package it.mapsgroup.gzoom.querydsl.dto;

import javax.annotation.Generated;
import com.querydsl.sql.Column;
import it.mapsgroup.gzoom.querydsl.AbstractIdentity;

/**
 * WorkEffortType is a Querydsl bean type
 */
@Generated("com.querydsl.codegen.BeanSerializer")
public class WorkEffortType implements AbstractIdentity {

    @Column("ALL_ORG_ASSIGNED")
    private Boolean allOrgAssigned;

    @Column("ALL_ROLES_ASSIGNED")
    private Boolean allRolesAssigned;

    @Column("APPLY_SCORE_RANGE")
    private Boolean applyScoreRange;

    @Column("BATCH_STATUS_ACTIVE")
    private Boolean batchStatusActive;

    @Column("CHILD_TEMPLATE_ID")
    private String childTemplateId;

    @Column("CODE_COUNTER")
    private String codeCounter;

    @Column("CODE_PREFIX")
    private String codePrefix;

    @Column("COPY")
    private Boolean copy;

    @Column("CREATED_BY_USER_LOGIN")
    private String createdByUserLogin;

    @Column("CREATED_STAMP")
    private java.time.LocalDateTime createdStamp;

    @Column("CREATED_TX_STAMP")
    private java.time.LocalDateTime createdTxStamp;

    @Column("DESCRIPTION")
    private String description;

    @Column("DESCRIPTION_LANG")
    private String descriptionLang;

    @Column("EFFORT_UOM_ID")
    private String effortUomId;

    @Column("ENABLE_MULTI_YEAR_FLAG")
    private Boolean enableMultiYearFlag;

    @Column("ENABLE_SNAPSHOT")
    private Boolean enableSnapshot;

    @Column("ETCH")
    private String etch;

    @Column("ETCH_LANG")
    private String etchLang;

    @Column("EVAL_ENUM_ID")
    private String evalEnumId;

    @Column("FOR_ALL_USERS")
    private Boolean forAllUsers;

    @Column("FRAME_ENUM_ID")
    private String frameEnumId;

    @Column("FROM_DATE")
    private java.time.LocalDateTime fromDate;

    @Column("GP_MENU_ENUM_ID")
    private String gpMenuEnumId;

    @Column("HAS_PERSON_FILTER")
    private Boolean hasPersonFilter;

    @Column("HAS_PRODUCT")
    private Boolean hasProduct;

    @Column("HAS_TABLE")
    private Boolean hasTable;

    @Column("HIERARCHY_ASSOC_TYPE_ID")
    private String hierarchyAssocTypeId;

    @Column("ICON_CONTENT_ID")
    private String iconContentId;

    @Column("INFLUENCE_CATALOG_ID")
    private String influenceCatalogId;

    @Column("INITIALLY_COLLAPSED")
    private String initiallyCollapsed;

    @Column("IS_INDIC_AUTO")
    private Boolean isIndicAuto;

    @Column("IS_IN_ONLY_ONE_CARD")
    private Boolean isInOnlyOneCard;

    @Column("IS_ROLE_TYPE_ID_AUTO")
    private Boolean isRoleTypeIdAuto;

    @Column("IS_ROOT")
    private Boolean isRoot;

    @Column("IS_ROOT_ACTIVE")
    private Boolean isRootActive;

    @Column("IS_TEMPLATE")
    private Boolean isTemplate;

    @Column("LAST_MODIFIED_BY_USER_LOGIN")
    private String lastModifiedByUserLogin;

    @Column("LAST_UPDATED_STAMP")
    private java.time.LocalDateTime lastUpdatedStamp;

    @Column("LAST_UPDATED_TX_STAMP")
    private java.time.LocalDateTime lastUpdatedTxStamp;

    @Column("LOCAL_NAME_CONTENT_ID")
    private String localNameContentId;

    @Column("MAX1")
    private java.math.BigInteger max1;

    @Column("MAX2")
    private java.math.BigInteger max2;

    @Column("MAX3")
    private java.math.BigInteger max3;

    @Column("MAX4")
    private java.math.BigInteger max4;

    @Column("MAX5")
    private java.math.BigInteger max5;

    @Column("MIN1")
    private java.math.BigInteger min1;

    @Column("MIN2")
    private java.math.BigInteger min2;

    @Column("MIN3")
    private java.math.BigInteger min3;

    @Column("MIN4")
    private java.math.BigInteger min4;

    @Column("MIN5")
    private java.math.BigInteger min5;

    @Column("NOTE")
    private String note;

    @Column("NOTE_LANG")
    private String noteLang;

    @Column("ORG_UNIT_ROLE_TYPE_ID")
    private String orgUnitRoleTypeId;

    @Column("ORG_UNIT_ROLE_TYPE_ID2")
    private String orgUnitRoleTypeId2;

    @Column("ORG_UNIT_ROLE_TYPE_ID3")
    private String orgUnitRoleTypeId3;

    @Column("PARENT_PERIOD_FILTER")
    private Boolean parentPeriodFilter;

    @Column("PARENT_TYPE_ID")
    private String parentTypeId;

    @Column("PARTY_REL_TYPE_ID_AUTO")
    private String partyRelTypeIdAuto;

    @Column("PERIOD_OPEN_ENUM_ID")
    private String periodOpenEnumId;

    @Column("PERIOD_TYPE_ID")
    private String periodTypeId;

    @Column("PURPOSE_ETCH")
    private String purposeEtch;

    @Column("PURPOSE_ETCH_LANG")
    private String purposeEtchLang;

    @Column("REMINDER_ACTIVE")
    private Boolean reminderActive;

    @Column("ROLE_TYPE_ID_AUTO")
    private String roleTypeIdAuto;

    @Column("RULE_TYPE_ID1")
    private String ruleTypeId1;

    @Column("RULE_TYPE_ID2")
    private String ruleTypeId2;

    @Column("RULE_TYPE_ID3")
    private String ruleTypeId3;

    @Column("RULE_TYPE_ID4")
    private String ruleTypeId4;

    @Column("RULE_TYPE_ID5")
    private String ruleTypeId5;

    @Column("SCORE_PERIOD_ENUM_ID")
    private String scorePeriodEnumId;

    @Column("SEQ_DIGIT")
    private java.math.BigInteger seqDigit;

    @Column("SEQ_ESP")
    private java.math.BigInteger seqEsp;

    @Column("SEQ_ONLY_ID")
    private Boolean seqOnlyId;

    @Column("SHORT_LABEL")
    private String shortLabel;

    @Column("SHOW_HIERARCHY")
    private Boolean showHierarchy;

    @Column("SHOW_SCOREKPI")
    private Boolean showScorekpi;

    @Column("THRU_DATE")
    private java.time.LocalDateTime thruDate;

    @Column("TOTAL_ENUM_ID_ASSOC")
    private String totalEnumIdAssoc;

    @Column("TOTAL_ENUM_ID_KPI")
    private String totalEnumIdKpi;

    @Column("TOTAL_ENUM_ID_SONS")
    private String totalEnumIdSons;

    @Column("TYPE_ID1")
    private String typeId1;

    @Column("TYPE_ID2")
    private String typeId2;

    @Column("TYPE_ID3")
    private String typeId3;

    @Column("TYPE_ID4")
    private String typeId4;

    @Column("UOM_RANGE_SCORE_ID")
    private String uomRangeScoreId;

    @Column("WEIGHT_ASSOC_WORK_EFFORT")
    private java.math.BigDecimal weightAssocWorkEffort;

    @Column("WEIGHT_CONTROL_SUM")
    private java.math.BigDecimal weightControlSum;

    @Column("WEIGHT_KPI")
    private java.math.BigDecimal weightKpi;

    @Column("WEIGHT_KPI_CONTROL_SUM")
    private java.math.BigDecimal weightKpiControlSum;

    @Column("WEIGHT_SONS")
    private java.math.BigDecimal weightSons;

    @Column("WE_LAYOUT_TYPE_ENUM_ID")
    private String weLayoutTypeEnumId;

    @Column("WE_PURPOSE_TYPE_ID_IND")
    private String wePurposeTypeIdInd;

    @Column("WE_PURPOSE_TYPE_ID_RES")
    private String wePurposeTypeIdRes;

    @Column("WE_PURPOSE_TYPE_ID_WE")
    private String wePurposeTypeIdWe;

    @Column("WORK_EFFORT_ASSOC_TYPE_ID")
    private String workEffortAssocTypeId;

    @Column("WORK_EFFORT_TYPE_HIERARCHY_ID")
    private String workEffortTypeHierarchyId;

    @Column("WORK_EFFORT_TYPE_ID")
    private String workEffortTypeId;

    public Boolean getAllOrgAssigned() {
        return allOrgAssigned;
    }

    public void setAllOrgAssigned(Boolean allOrgAssigned) {
        this.allOrgAssigned = allOrgAssigned;
    }

    public Boolean getAllRolesAssigned() {
        return allRolesAssigned;
    }

    public void setAllRolesAssigned(Boolean allRolesAssigned) {
        this.allRolesAssigned = allRolesAssigned;
    }

    public Boolean getApplyScoreRange() {
        return applyScoreRange;
    }

    public void setApplyScoreRange(Boolean applyScoreRange) {
        this.applyScoreRange = applyScoreRange;
    }

    public Boolean getBatchStatusActive() {
        return batchStatusActive;
    }

    public void setBatchStatusActive(Boolean batchStatusActive) {
        this.batchStatusActive = batchStatusActive;
    }

    public String getChildTemplateId() {
        return childTemplateId;
    }

    public void setChildTemplateId(String childTemplateId) {
        this.childTemplateId = childTemplateId;
    }

    public String getCodeCounter() {
        return codeCounter;
    }

    public void setCodeCounter(String codeCounter) {
        this.codeCounter = codeCounter;
    }

    public String getCodePrefix() {
        return codePrefix;
    }

    public void setCodePrefix(String codePrefix) {
        this.codePrefix = codePrefix;
    }

    public Boolean getCopy() {
        return copy;
    }

    public void setCopy(Boolean copy) {
        this.copy = copy;
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

    public String getDescriptionLang() {
        return descriptionLang;
    }

    public void setDescriptionLang(String descriptionLang) {
        this.descriptionLang = descriptionLang;
    }

    public String getEffortUomId() {
        return effortUomId;
    }

    public void setEffortUomId(String effortUomId) {
        this.effortUomId = effortUomId;
    }

    public Boolean getEnableMultiYearFlag() {
        return enableMultiYearFlag;
    }

    public void setEnableMultiYearFlag(Boolean enableMultiYearFlag) {
        this.enableMultiYearFlag = enableMultiYearFlag;
    }

    public Boolean getEnableSnapshot() {
        return enableSnapshot;
    }

    public void setEnableSnapshot(Boolean enableSnapshot) {
        this.enableSnapshot = enableSnapshot;
    }

    public String getEtch() {
        return etch;
    }

    public void setEtch(String etch) {
        this.etch = etch;
    }

    public String getEtchLang() {
        return etchLang;
    }

    public void setEtchLang(String etchLang) {
        this.etchLang = etchLang;
    }

    public String getEvalEnumId() {
        return evalEnumId;
    }

    public void setEvalEnumId(String evalEnumId) {
        this.evalEnumId = evalEnumId;
    }

    public Boolean getForAllUsers() {
        return forAllUsers;
    }

    public void setForAllUsers(Boolean forAllUsers) {
        this.forAllUsers = forAllUsers;
    }

    public String getFrameEnumId() {
        return frameEnumId;
    }

    public void setFrameEnumId(String frameEnumId) {
        this.frameEnumId = frameEnumId;
    }

    public java.time.LocalDateTime getFromDate() {
        return fromDate;
    }

    public void setFromDate(java.time.LocalDateTime fromDate) {
        this.fromDate = fromDate;
    }

    public String getGpMenuEnumId() {
        return gpMenuEnumId;
    }

    public void setGpMenuEnumId(String gpMenuEnumId) {
        this.gpMenuEnumId = gpMenuEnumId;
    }

    public Boolean getHasPersonFilter() {
        return hasPersonFilter;
    }

    public void setHasPersonFilter(Boolean hasPersonFilter) {
        this.hasPersonFilter = hasPersonFilter;
    }

    public Boolean getHasProduct() {
        return hasProduct;
    }

    public void setHasProduct(Boolean hasProduct) {
        this.hasProduct = hasProduct;
    }

    public Boolean getHasTable() {
        return hasTable;
    }

    public void setHasTable(Boolean hasTable) {
        this.hasTable = hasTable;
    }

    public String getHierarchyAssocTypeId() {
        return hierarchyAssocTypeId;
    }

    public void setHierarchyAssocTypeId(String hierarchyAssocTypeId) {
        this.hierarchyAssocTypeId = hierarchyAssocTypeId;
    }

    public String getIconContentId() {
        return iconContentId;
    }

    public void setIconContentId(String iconContentId) {
        this.iconContentId = iconContentId;
    }

    public String getInfluenceCatalogId() {
        return influenceCatalogId;
    }

    public void setInfluenceCatalogId(String influenceCatalogId) {
        this.influenceCatalogId = influenceCatalogId;
    }

    public String getInitiallyCollapsed() {
        return initiallyCollapsed;
    }

    public void setInitiallyCollapsed(String initiallyCollapsed) {
        this.initiallyCollapsed = initiallyCollapsed;
    }

    public Boolean getIsIndicAuto() {
        return isIndicAuto;
    }

    public void setIsIndicAuto(Boolean isIndicAuto) {
        this.isIndicAuto = isIndicAuto;
    }

    public Boolean getIsInOnlyOneCard() {
        return isInOnlyOneCard;
    }

    public void setIsInOnlyOneCard(Boolean isInOnlyOneCard) {
        this.isInOnlyOneCard = isInOnlyOneCard;
    }

    public Boolean getIsRoleTypeIdAuto() {
        return isRoleTypeIdAuto;
    }

    public void setIsRoleTypeIdAuto(Boolean isRoleTypeIdAuto) {
        this.isRoleTypeIdAuto = isRoleTypeIdAuto;
    }

    public Boolean getIsRoot() {
        return isRoot;
    }

    public void setIsRoot(Boolean isRoot) {
        this.isRoot = isRoot;
    }

    public Boolean getIsRootActive() {
        return isRootActive;
    }

    public void setIsRootActive(Boolean isRootActive) {
        this.isRootActive = isRootActive;
    }

    public Boolean getIsTemplate() {
        return isTemplate;
    }

    public void setIsTemplate(Boolean isTemplate) {
        this.isTemplate = isTemplate;
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

    public java.math.BigInteger getMax1() {
        return max1;
    }

    public void setMax1(java.math.BigInteger max1) {
        this.max1 = max1;
    }

    public java.math.BigInteger getMax2() {
        return max2;
    }

    public void setMax2(java.math.BigInteger max2) {
        this.max2 = max2;
    }

    public java.math.BigInteger getMax3() {
        return max3;
    }

    public void setMax3(java.math.BigInteger max3) {
        this.max3 = max3;
    }

    public java.math.BigInteger getMax4() {
        return max4;
    }

    public void setMax4(java.math.BigInteger max4) {
        this.max4 = max4;
    }

    public java.math.BigInteger getMax5() {
        return max5;
    }

    public void setMax5(java.math.BigInteger max5) {
        this.max5 = max5;
    }

    public java.math.BigInteger getMin1() {
        return min1;
    }

    public void setMin1(java.math.BigInteger min1) {
        this.min1 = min1;
    }

    public java.math.BigInteger getMin2() {
        return min2;
    }

    public void setMin2(java.math.BigInteger min2) {
        this.min2 = min2;
    }

    public java.math.BigInteger getMin3() {
        return min3;
    }

    public void setMin3(java.math.BigInteger min3) {
        this.min3 = min3;
    }

    public java.math.BigInteger getMin4() {
        return min4;
    }

    public void setMin4(java.math.BigInteger min4) {
        this.min4 = min4;
    }

    public java.math.BigInteger getMin5() {
        return min5;
    }

    public void setMin5(java.math.BigInteger min5) {
        this.min5 = min5;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getNoteLang() {
        return noteLang;
    }

    public void setNoteLang(String noteLang) {
        this.noteLang = noteLang;
    }

    public String getOrgUnitRoleTypeId() {
        return orgUnitRoleTypeId;
    }

    public void setOrgUnitRoleTypeId(String orgUnitRoleTypeId) {
        this.orgUnitRoleTypeId = orgUnitRoleTypeId;
    }

    public String getOrgUnitRoleTypeId2() {
        return orgUnitRoleTypeId2;
    }

    public void setOrgUnitRoleTypeId2(String orgUnitRoleTypeId2) {
        this.orgUnitRoleTypeId2 = orgUnitRoleTypeId2;
    }

    public String getOrgUnitRoleTypeId3() {
        return orgUnitRoleTypeId3;
    }

    public void setOrgUnitRoleTypeId3(String orgUnitRoleTypeId3) {
        this.orgUnitRoleTypeId3 = orgUnitRoleTypeId3;
    }

    public Boolean getParentPeriodFilter() {
        return parentPeriodFilter;
    }

    public void setParentPeriodFilter(Boolean parentPeriodFilter) {
        this.parentPeriodFilter = parentPeriodFilter;
    }

    public String getParentTypeId() {
        return parentTypeId;
    }

    public void setParentTypeId(String parentTypeId) {
        this.parentTypeId = parentTypeId;
    }

    public String getPartyRelTypeIdAuto() {
        return partyRelTypeIdAuto;
    }

    public void setPartyRelTypeIdAuto(String partyRelTypeIdAuto) {
        this.partyRelTypeIdAuto = partyRelTypeIdAuto;
    }

    public String getPeriodOpenEnumId() {
        return periodOpenEnumId;
    }

    public void setPeriodOpenEnumId(String periodOpenEnumId) {
        this.periodOpenEnumId = periodOpenEnumId;
    }

    public String getPeriodTypeId() {
        return periodTypeId;
    }

    public void setPeriodTypeId(String periodTypeId) {
        this.periodTypeId = periodTypeId;
    }

    public String getPurposeEtch() {
        return purposeEtch;
    }

    public void setPurposeEtch(String purposeEtch) {
        this.purposeEtch = purposeEtch;
    }

    public String getPurposeEtchLang() {
        return purposeEtchLang;
    }

    public void setPurposeEtchLang(String purposeEtchLang) {
        this.purposeEtchLang = purposeEtchLang;
    }

    public Boolean getReminderActive() {
        return reminderActive;
    }

    public void setReminderActive(Boolean reminderActive) {
        this.reminderActive = reminderActive;
    }

    public String getRoleTypeIdAuto() {
        return roleTypeIdAuto;
    }

    public void setRoleTypeIdAuto(String roleTypeIdAuto) {
        this.roleTypeIdAuto = roleTypeIdAuto;
    }

    public String getRuleTypeId1() {
        return ruleTypeId1;
    }

    public void setRuleTypeId1(String ruleTypeId1) {
        this.ruleTypeId1 = ruleTypeId1;
    }

    public String getRuleTypeId2() {
        return ruleTypeId2;
    }

    public void setRuleTypeId2(String ruleTypeId2) {
        this.ruleTypeId2 = ruleTypeId2;
    }

    public String getRuleTypeId3() {
        return ruleTypeId3;
    }

    public void setRuleTypeId3(String ruleTypeId3) {
        this.ruleTypeId3 = ruleTypeId3;
    }

    public String getRuleTypeId4() {
        return ruleTypeId4;
    }

    public void setRuleTypeId4(String ruleTypeId4) {
        this.ruleTypeId4 = ruleTypeId4;
    }

    public String getRuleTypeId5() {
        return ruleTypeId5;
    }

    public void setRuleTypeId5(String ruleTypeId5) {
        this.ruleTypeId5 = ruleTypeId5;
    }

    public String getScorePeriodEnumId() {
        return scorePeriodEnumId;
    }

    public void setScorePeriodEnumId(String scorePeriodEnumId) {
        this.scorePeriodEnumId = scorePeriodEnumId;
    }

    public java.math.BigInteger getSeqDigit() {
        return seqDigit;
    }

    public void setSeqDigit(java.math.BigInteger seqDigit) {
        this.seqDigit = seqDigit;
    }

    public java.math.BigInteger getSeqEsp() {
        return seqEsp;
    }

    public void setSeqEsp(java.math.BigInteger seqEsp) {
        this.seqEsp = seqEsp;
    }

    public Boolean getSeqOnlyId() {
        return seqOnlyId;
    }

    public void setSeqOnlyId(Boolean seqOnlyId) {
        this.seqOnlyId = seqOnlyId;
    }

    public String getShortLabel() {
        return shortLabel;
    }

    public void setShortLabel(String shortLabel) {
        this.shortLabel = shortLabel;
    }

    public Boolean getShowHierarchy() {
        return showHierarchy;
    }

    public void setShowHierarchy(Boolean showHierarchy) {
        this.showHierarchy = showHierarchy;
    }

    public Boolean getShowScorekpi() {
        return showScorekpi;
    }

    public void setShowScorekpi(Boolean showScorekpi) {
        this.showScorekpi = showScorekpi;
    }

    public java.time.LocalDateTime getThruDate() {
        return thruDate;
    }

    public void setThruDate(java.time.LocalDateTime thruDate) {
        this.thruDate = thruDate;
    }

    public String getTotalEnumIdAssoc() {
        return totalEnumIdAssoc;
    }

    public void setTotalEnumIdAssoc(String totalEnumIdAssoc) {
        this.totalEnumIdAssoc = totalEnumIdAssoc;
    }

    public String getTotalEnumIdKpi() {
        return totalEnumIdKpi;
    }

    public void setTotalEnumIdKpi(String totalEnumIdKpi) {
        this.totalEnumIdKpi = totalEnumIdKpi;
    }

    public String getTotalEnumIdSons() {
        return totalEnumIdSons;
    }

    public void setTotalEnumIdSons(String totalEnumIdSons) {
        this.totalEnumIdSons = totalEnumIdSons;
    }

    public String getTypeId1() {
        return typeId1;
    }

    public void setTypeId1(String typeId1) {
        this.typeId1 = typeId1;
    }

    public String getTypeId2() {
        return typeId2;
    }

    public void setTypeId2(String typeId2) {
        this.typeId2 = typeId2;
    }

    public String getTypeId3() {
        return typeId3;
    }

    public void setTypeId3(String typeId3) {
        this.typeId3 = typeId3;
    }

    public String getTypeId4() {
        return typeId4;
    }

    public void setTypeId4(String typeId4) {
        this.typeId4 = typeId4;
    }

    public String getUomRangeScoreId() {
        return uomRangeScoreId;
    }

    public void setUomRangeScoreId(String uomRangeScoreId) {
        this.uomRangeScoreId = uomRangeScoreId;
    }

    public java.math.BigDecimal getWeightAssocWorkEffort() {
        return weightAssocWorkEffort;
    }

    public void setWeightAssocWorkEffort(java.math.BigDecimal weightAssocWorkEffort) {
        this.weightAssocWorkEffort = weightAssocWorkEffort;
    }

    public java.math.BigDecimal getWeightControlSum() {
        return weightControlSum;
    }

    public void setWeightControlSum(java.math.BigDecimal weightControlSum) {
        this.weightControlSum = weightControlSum;
    }

    public java.math.BigDecimal getWeightKpi() {
        return weightKpi;
    }

    public void setWeightKpi(java.math.BigDecimal weightKpi) {
        this.weightKpi = weightKpi;
    }

    public java.math.BigDecimal getWeightKpiControlSum() {
        return weightKpiControlSum;
    }

    public void setWeightKpiControlSum(java.math.BigDecimal weightKpiControlSum) {
        this.weightKpiControlSum = weightKpiControlSum;
    }

    public java.math.BigDecimal getWeightSons() {
        return weightSons;
    }

    public void setWeightSons(java.math.BigDecimal weightSons) {
        this.weightSons = weightSons;
    }

    public String getWeLayoutTypeEnumId() {
        return weLayoutTypeEnumId;
    }

    public void setWeLayoutTypeEnumId(String weLayoutTypeEnumId) {
        this.weLayoutTypeEnumId = weLayoutTypeEnumId;
    }

    public String getWePurposeTypeIdInd() {
        return wePurposeTypeIdInd;
    }

    public void setWePurposeTypeIdInd(String wePurposeTypeIdInd) {
        this.wePurposeTypeIdInd = wePurposeTypeIdInd;
    }

    public String getWePurposeTypeIdRes() {
        return wePurposeTypeIdRes;
    }

    public void setWePurposeTypeIdRes(String wePurposeTypeIdRes) {
        this.wePurposeTypeIdRes = wePurposeTypeIdRes;
    }

    public String getWePurposeTypeIdWe() {
        return wePurposeTypeIdWe;
    }

    public void setWePurposeTypeIdWe(String wePurposeTypeIdWe) {
        this.wePurposeTypeIdWe = wePurposeTypeIdWe;
    }

    public String getWorkEffortAssocTypeId() {
        return workEffortAssocTypeId;
    }

    public void setWorkEffortAssocTypeId(String workEffortAssocTypeId) {
        this.workEffortAssocTypeId = workEffortAssocTypeId;
    }

    public String getWorkEffortTypeHierarchyId() {
        return workEffortTypeHierarchyId;
    }

    public void setWorkEffortTypeHierarchyId(String workEffortTypeHierarchyId) {
        this.workEffortTypeHierarchyId = workEffortTypeHierarchyId;
    }

    public String getWorkEffortTypeId() {
        return workEffortTypeId;
    }

    public void setWorkEffortTypeId(String workEffortTypeId) {
        this.workEffortTypeId = workEffortTypeId;
    }

}

