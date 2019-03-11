package it.mapsgroup.gzoom.querydsl.dto;

import javax.annotation.processing.Generated;
import com.querydsl.sql.Column;
import it.mapsgroup.gzoom.querydsl.AbstractIdentity;

/**
 * WorkEffort is a Querydsl bean type
 */
@Generated("com.querydsl.codegen.BeanSerializer")
public class WorkEffort implements AbstractIdentity {

    @Column("ACCOMMODATION_MAP_ID")
    private String accommodationMapId;

    @Column("ACCOMMODATION_SPOT_ID")
    private String accommodationSpotId;

    @Column("ACTUAL_COMPLETION_DATE")
    private java.time.LocalDateTime actualCompletionDate;

    @Column("ACTUAL_MILLI_SECONDS")
    private java.math.BigDecimal actualMilliSeconds;

    @Column("ACTUAL_SETUP_MILLIS")
    private java.math.BigDecimal actualSetupMillis;

    @Column("ACTUAL_START_DATE")
    private java.time.LocalDateTime actualStartDate;

    @Column("CREATED_BY_USER_LOGIN")
    private String createdByUserLogin;

    @Column("CREATED_DATE")
    private java.time.LocalDateTime createdDate;

    @Column("CREATED_STAMP")
    private java.time.LocalDateTime createdStamp;

    @Column("CREATED_TX_STAMP")
    private java.time.LocalDateTime createdTxStamp;

    @Column("CURRENT_STATUS_ID")
    private String currentStatusId;

    @Column("DATA_SOLL")
    private java.time.LocalDateTime dataSoll;

    @Column("DESCRIPTION")
    private String description;

    @Column("DESCRIPTION_LANG")
    private String descriptionLang;

    @Column("EFFORT_UOM_ID")
    private String effortUomId;

    @Column("EMPL_POSITION_TYPE_ID")
    private String emplPositionTypeId;

    @Column("ESTIMATE_CALC_METHOD")
    private String estimateCalcMethod;

    @Column("ESTIMATED_COMPLETION_DATE")
    private java.time.LocalDateTime estimatedCompletionDate;

    @Column("ESTIMATED_MILLI_SECONDS")
    private java.math.BigDecimal estimatedMilliSeconds;

    @Column("ESTIMATED_SETUP_MILLIS")
    private java.math.BigDecimal estimatedSetupMillis;

    @Column("ESTIMATED_START_DATE")
    private java.time.LocalDateTime estimatedStartDate;

    @Column("ESTIMATED_TOTAL_EFFORT")
    private java.math.BigDecimal estimatedTotalEffort;

    @Column("ETCH")
    private String etch;

    @Column("FACILITY_ID")
    private String facilityId;

    @Column("FIXED_ASSET_ID")
    private String fixedAssetId;

    @Column("INFO_URL")
    private String infoUrl;

    @Column("IS_POSTED")
    private Boolean isPosted;

    @Column("LAST_CORRECT_SCORE_DATE")
    private java.time.LocalDateTime lastCorrectScoreDate;

    @Column("LAST_MODIFIED_BY_USER_LOGIN")
    private String lastModifiedByUserLogin;

    @Column("LAST_MODIFIED_DATE")
    private java.time.LocalDateTime lastModifiedDate;

    @Column("LAST_STATUS_UPDATE")
    private java.time.LocalDateTime lastStatusUpdate;

    @Column("LAST_UPDATED_STAMP")
    private java.time.LocalDateTime lastUpdatedStamp;

    @Column("LAST_UPDATED_TX_STAMP")
    private java.time.LocalDateTime lastUpdatedTxStamp;

    @Column("LOCAL_NAME_CONTENT_ID")
    private String localNameContentId;

    @Column("LOCATION_DESC")
    private String locationDesc;

    @Column("MONEY_UOM_ID")
    private String moneyUomId;

    @Column("NOTE_ID")
    private String noteId;

    @Column("ORGANIZATION_ID")
    private String organizationId;

    @Column("ORG_UNIT_ID")
    private String orgUnitId;

    @Column("ORG_UNIT_ROLE_TYPE_ID")
    private String orgUnitRoleTypeId;

    @Column("PERCENT_COMPLETE")
    private java.math.BigInteger percentComplete;

    @Column("PRIORITY")
    private java.math.BigInteger priority;

    @Column("PROCESS_ID")
    private String processId;

    @Column("PRODUCT_ID")
    private String productId;

    @Column("QUANTITY_PRODUCED")
    private java.math.BigDecimal quantityProduced;

    @Column("QUANTITY_REJECTED")
    private java.math.BigDecimal quantityRejected;

    @Column("QUANTITY_TO_PRODUCE")
    private java.math.BigDecimal quantityToProduce;

    @Column("RECURRENCE_INFO_ID")
    private String recurrenceInfoId;

    @Column("RESERV2ND_P_P_PERC")
    private java.math.BigDecimal reserv2ndPPPerc;

    @Column("RESERV_NTH_P_P_PERC")
    private java.math.BigDecimal reservNthPPPerc;

    @Column("RESERV_PERSONS")
    private java.math.BigDecimal reservPersons;

    @Column("REVISION_NUMBER")
    private java.math.BigInteger revisionNumber;

    @Column("RUNTIME_DATA_ID")
    private String runtimeDataId;

    @Column("SCHEDULED_COMPLETION_DATE")
    private java.time.LocalDateTime scheduledCompletionDate;

    @Column("SCHEDULED_START_DATE")
    private java.time.LocalDateTime scheduledStartDate;

    @Column("SCOPE_ENUM_ID")
    private String scopeEnumId;

    @Column("SEND_NOTIFICATION_EMAIL")
    private Boolean sendNotificationEmail;

    @Column("SEQUENCE_NUM")
    private java.math.BigInteger sequenceNum;

    @Column("SERVICE_LOADER_NAME")
    private String serviceLoaderName;

    @Column("SHOW_AS_ENUM_ID")
    private String showAsEnumId;

    @Column("SNAP_SHOT_DATE")
    private java.time.LocalDateTime snapShotDate;

    @Column("SNAP_SHOT_DESCRIPTION")
    private String snapShotDescription;

    @Column("SOURCE_REFERENCE_ID")
    private String sourceReferenceId;

    @Column("SPECIAL_TERMS")
    private String specialTerms;

    @Column("TEMP_EXPR_ID")
    private String tempExprId;

    @Column("TIME_TRANSPARENCY")
    private java.math.BigInteger timeTransparency;

    @Column("TOTAL_ENUM_ID_ASSOC")
    private String totalEnumIdAssoc;

    @Column("TOTAL_ENUM_ID_KPI")
    private String totalEnumIdKpi;

    @Column("TOTAL_ENUM_ID_SONS")
    private String totalEnumIdSons;

    @Column("TOTAL_MILLI_SECONDS_ALLOWED")
    private java.math.BigDecimal totalMilliSecondsAllowed;

    @Column("TOTAL_MONEY_ALLOWED")
    private java.math.BigDecimal totalMoneyAllowed;

    @Column("UNIVERSAL_ID")
    private String universalId;

    @Column("UOM_RANGE_SCORE_ID")
    private String uomRangeScoreId;

    @Column("WEIGHT_ASSOC_WORK_EFFORT")
    private java.math.BigDecimal weightAssocWorkEffort;

    @Column("WEIGHT_KPI")
    private java.math.BigDecimal weightKpi;

    @Column("WEIGHT_REVIEW")
    private java.math.BigDecimal weightReview;

    @Column("WEIGHT_SONS")
    private java.math.BigDecimal weightSons;

    @Column("WORK_EFFORT_ASSOC_TYPE_ID")
    private String workEffortAssocTypeId;

    @Column("WORK_EFFORT_ID")
    private String workEffortId;

    @Column("WORK_EFFORT_NAME")
    private String workEffortName;

    @Column("WORK_EFFORT_NAME_LANG")
    private String workEffortNameLang;

    @Column("WORK_EFFORT_PARENT_ID")
    private String workEffortParentId;

    @Column("WORK_EFFORT_PURPOSE_TYPE_ID")
    private String workEffortPurposeTypeId;

    @Column("WORK_EFFORT_REVISION_ID")
    private String workEffortRevisionId;

    @Column("WORK_EFFORT_SNAPSHOT_ID")
    private String workEffortSnapshotId;

    @Column("WORK_EFFORT_TYPE_ID")
    private String workEffortTypeId;

    @Column("WORK_EFFORT_TYPE_PERIOD_ID")
    private String workEffortTypePeriodId;

    public String getAccommodationMapId() {
        return accommodationMapId;
    }

    public void setAccommodationMapId(String accommodationMapId) {
        this.accommodationMapId = accommodationMapId;
    }

    public String getAccommodationSpotId() {
        return accommodationSpotId;
    }

    public void setAccommodationSpotId(String accommodationSpotId) {
        this.accommodationSpotId = accommodationSpotId;
    }

    public java.time.LocalDateTime getActualCompletionDate() {
        return actualCompletionDate;
    }

    public void setActualCompletionDate(java.time.LocalDateTime actualCompletionDate) {
        this.actualCompletionDate = actualCompletionDate;
    }

    public java.math.BigDecimal getActualMilliSeconds() {
        return actualMilliSeconds;
    }

    public void setActualMilliSeconds(java.math.BigDecimal actualMilliSeconds) {
        this.actualMilliSeconds = actualMilliSeconds;
    }

    public java.math.BigDecimal getActualSetupMillis() {
        return actualSetupMillis;
    }

    public void setActualSetupMillis(java.math.BigDecimal actualSetupMillis) {
        this.actualSetupMillis = actualSetupMillis;
    }

    public java.time.LocalDateTime getActualStartDate() {
        return actualStartDate;
    }

    public void setActualStartDate(java.time.LocalDateTime actualStartDate) {
        this.actualStartDate = actualStartDate;
    }

    public String getCreatedByUserLogin() {
        return createdByUserLogin;
    }

    public void setCreatedByUserLogin(String createdByUserLogin) {
        this.createdByUserLogin = createdByUserLogin;
    }

    public java.time.LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(java.time.LocalDateTime createdDate) {
        this.createdDate = createdDate;
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

    public java.time.LocalDateTime getDataSoll() {
        return dataSoll;
    }

    public void setDataSoll(java.time.LocalDateTime dataSoll) {
        this.dataSoll = dataSoll;
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

    public String getEmplPositionTypeId() {
        return emplPositionTypeId;
    }

    public void setEmplPositionTypeId(String emplPositionTypeId) {
        this.emplPositionTypeId = emplPositionTypeId;
    }

    public String getEstimateCalcMethod() {
        return estimateCalcMethod;
    }

    public void setEstimateCalcMethod(String estimateCalcMethod) {
        this.estimateCalcMethod = estimateCalcMethod;
    }

    public java.time.LocalDateTime getEstimatedCompletionDate() {
        return estimatedCompletionDate;
    }

    public void setEstimatedCompletionDate(java.time.LocalDateTime estimatedCompletionDate) {
        this.estimatedCompletionDate = estimatedCompletionDate;
    }

    public java.math.BigDecimal getEstimatedMilliSeconds() {
        return estimatedMilliSeconds;
    }

    public void setEstimatedMilliSeconds(java.math.BigDecimal estimatedMilliSeconds) {
        this.estimatedMilliSeconds = estimatedMilliSeconds;
    }

    public java.math.BigDecimal getEstimatedSetupMillis() {
        return estimatedSetupMillis;
    }

    public void setEstimatedSetupMillis(java.math.BigDecimal estimatedSetupMillis) {
        this.estimatedSetupMillis = estimatedSetupMillis;
    }

    public java.time.LocalDateTime getEstimatedStartDate() {
        return estimatedStartDate;
    }

    public void setEstimatedStartDate(java.time.LocalDateTime estimatedStartDate) {
        this.estimatedStartDate = estimatedStartDate;
    }

    public java.math.BigDecimal getEstimatedTotalEffort() {
        return estimatedTotalEffort;
    }

    public void setEstimatedTotalEffort(java.math.BigDecimal estimatedTotalEffort) {
        this.estimatedTotalEffort = estimatedTotalEffort;
    }

    public String getEtch() {
        return etch;
    }

    public void setEtch(String etch) {
        this.etch = etch;
    }

    public String getFacilityId() {
        return facilityId;
    }

    public void setFacilityId(String facilityId) {
        this.facilityId = facilityId;
    }

    public String getFixedAssetId() {
        return fixedAssetId;
    }

    public void setFixedAssetId(String fixedAssetId) {
        this.fixedAssetId = fixedAssetId;
    }

    public String getInfoUrl() {
        return infoUrl;
    }

    public void setInfoUrl(String infoUrl) {
        this.infoUrl = infoUrl;
    }

    public Boolean getIsPosted() {
        return isPosted;
    }

    public void setIsPosted(Boolean isPosted) {
        this.isPosted = isPosted;
    }

    public java.time.LocalDateTime getLastCorrectScoreDate() {
        return lastCorrectScoreDate;
    }

    public void setLastCorrectScoreDate(java.time.LocalDateTime lastCorrectScoreDate) {
        this.lastCorrectScoreDate = lastCorrectScoreDate;
    }

    public String getLastModifiedByUserLogin() {
        return lastModifiedByUserLogin;
    }

    public void setLastModifiedByUserLogin(String lastModifiedByUserLogin) {
        this.lastModifiedByUserLogin = lastModifiedByUserLogin;
    }

    public java.time.LocalDateTime getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(java.time.LocalDateTime lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public java.time.LocalDateTime getLastStatusUpdate() {
        return lastStatusUpdate;
    }

    public void setLastStatusUpdate(java.time.LocalDateTime lastStatusUpdate) {
        this.lastStatusUpdate = lastStatusUpdate;
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

    public String getLocationDesc() {
        return locationDesc;
    }

    public void setLocationDesc(String locationDesc) {
        this.locationDesc = locationDesc;
    }

    public String getMoneyUomId() {
        return moneyUomId;
    }

    public void setMoneyUomId(String moneyUomId) {
        this.moneyUomId = moneyUomId;
    }

    public String getNoteId() {
        return noteId;
    }

    public void setNoteId(String noteId) {
        this.noteId = noteId;
    }

    public String getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
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

    public java.math.BigInteger getPercentComplete() {
        return percentComplete;
    }

    public void setPercentComplete(java.math.BigInteger percentComplete) {
        this.percentComplete = percentComplete;
    }

    public java.math.BigInteger getPriority() {
        return priority;
    }

    public void setPriority(java.math.BigInteger priority) {
        this.priority = priority;
    }

    public String getProcessId() {
        return processId;
    }

    public void setProcessId(String processId) {
        this.processId = processId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public java.math.BigDecimal getQuantityProduced() {
        return quantityProduced;
    }

    public void setQuantityProduced(java.math.BigDecimal quantityProduced) {
        this.quantityProduced = quantityProduced;
    }

    public java.math.BigDecimal getQuantityRejected() {
        return quantityRejected;
    }

    public void setQuantityRejected(java.math.BigDecimal quantityRejected) {
        this.quantityRejected = quantityRejected;
    }

    public java.math.BigDecimal getQuantityToProduce() {
        return quantityToProduce;
    }

    public void setQuantityToProduce(java.math.BigDecimal quantityToProduce) {
        this.quantityToProduce = quantityToProduce;
    }

    public String getRecurrenceInfoId() {
        return recurrenceInfoId;
    }

    public void setRecurrenceInfoId(String recurrenceInfoId) {
        this.recurrenceInfoId = recurrenceInfoId;
    }

    public java.math.BigDecimal getReserv2ndPPPerc() {
        return reserv2ndPPPerc;
    }

    public void setReserv2ndPPPerc(java.math.BigDecimal reserv2ndPPPerc) {
        this.reserv2ndPPPerc = reserv2ndPPPerc;
    }

    public java.math.BigDecimal getReservNthPPPerc() {
        return reservNthPPPerc;
    }

    public void setReservNthPPPerc(java.math.BigDecimal reservNthPPPerc) {
        this.reservNthPPPerc = reservNthPPPerc;
    }

    public java.math.BigDecimal getReservPersons() {
        return reservPersons;
    }

    public void setReservPersons(java.math.BigDecimal reservPersons) {
        this.reservPersons = reservPersons;
    }

    public java.math.BigInteger getRevisionNumber() {
        return revisionNumber;
    }

    public void setRevisionNumber(java.math.BigInteger revisionNumber) {
        this.revisionNumber = revisionNumber;
    }

    public String getRuntimeDataId() {
        return runtimeDataId;
    }

    public void setRuntimeDataId(String runtimeDataId) {
        this.runtimeDataId = runtimeDataId;
    }

    public java.time.LocalDateTime getScheduledCompletionDate() {
        return scheduledCompletionDate;
    }

    public void setScheduledCompletionDate(java.time.LocalDateTime scheduledCompletionDate) {
        this.scheduledCompletionDate = scheduledCompletionDate;
    }

    public java.time.LocalDateTime getScheduledStartDate() {
        return scheduledStartDate;
    }

    public void setScheduledStartDate(java.time.LocalDateTime scheduledStartDate) {
        this.scheduledStartDate = scheduledStartDate;
    }

    public String getScopeEnumId() {
        return scopeEnumId;
    }

    public void setScopeEnumId(String scopeEnumId) {
        this.scopeEnumId = scopeEnumId;
    }

    public Boolean getSendNotificationEmail() {
        return sendNotificationEmail;
    }

    public void setSendNotificationEmail(Boolean sendNotificationEmail) {
        this.sendNotificationEmail = sendNotificationEmail;
    }

    public java.math.BigInteger getSequenceNum() {
        return sequenceNum;
    }

    public void setSequenceNum(java.math.BigInteger sequenceNum) {
        this.sequenceNum = sequenceNum;
    }

    public String getServiceLoaderName() {
        return serviceLoaderName;
    }

    public void setServiceLoaderName(String serviceLoaderName) {
        this.serviceLoaderName = serviceLoaderName;
    }

    public String getShowAsEnumId() {
        return showAsEnumId;
    }

    public void setShowAsEnumId(String showAsEnumId) {
        this.showAsEnumId = showAsEnumId;
    }

    public java.time.LocalDateTime getSnapShotDate() {
        return snapShotDate;
    }

    public void setSnapShotDate(java.time.LocalDateTime snapShotDate) {
        this.snapShotDate = snapShotDate;
    }

    public String getSnapShotDescription() {
        return snapShotDescription;
    }

    public void setSnapShotDescription(String snapShotDescription) {
        this.snapShotDescription = snapShotDescription;
    }

    public String getSourceReferenceId() {
        return sourceReferenceId;
    }

    public void setSourceReferenceId(String sourceReferenceId) {
        this.sourceReferenceId = sourceReferenceId;
    }

    public String getSpecialTerms() {
        return specialTerms;
    }

    public void setSpecialTerms(String specialTerms) {
        this.specialTerms = specialTerms;
    }

    public String getTempExprId() {
        return tempExprId;
    }

    public void setTempExprId(String tempExprId) {
        this.tempExprId = tempExprId;
    }

    public java.math.BigInteger getTimeTransparency() {
        return timeTransparency;
    }

    public void setTimeTransparency(java.math.BigInteger timeTransparency) {
        this.timeTransparency = timeTransparency;
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

    public java.math.BigDecimal getTotalMilliSecondsAllowed() {
        return totalMilliSecondsAllowed;
    }

    public void setTotalMilliSecondsAllowed(java.math.BigDecimal totalMilliSecondsAllowed) {
        this.totalMilliSecondsAllowed = totalMilliSecondsAllowed;
    }

    public java.math.BigDecimal getTotalMoneyAllowed() {
        return totalMoneyAllowed;
    }

    public void setTotalMoneyAllowed(java.math.BigDecimal totalMoneyAllowed) {
        this.totalMoneyAllowed = totalMoneyAllowed;
    }

    public String getUniversalId() {
        return universalId;
    }

    public void setUniversalId(String universalId) {
        this.universalId = universalId;
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

    public java.math.BigDecimal getWeightKpi() {
        return weightKpi;
    }

    public void setWeightKpi(java.math.BigDecimal weightKpi) {
        this.weightKpi = weightKpi;
    }

    public java.math.BigDecimal getWeightReview() {
        return weightReview;
    }

    public void setWeightReview(java.math.BigDecimal weightReview) {
        this.weightReview = weightReview;
    }

    public java.math.BigDecimal getWeightSons() {
        return weightSons;
    }

    public void setWeightSons(java.math.BigDecimal weightSons) {
        this.weightSons = weightSons;
    }

    public String getWorkEffortAssocTypeId() {
        return workEffortAssocTypeId;
    }

    public void setWorkEffortAssocTypeId(String workEffortAssocTypeId) {
        this.workEffortAssocTypeId = workEffortAssocTypeId;
    }

    public String getWorkEffortId() {
        return workEffortId;
    }

    public void setWorkEffortId(String workEffortId) {
        this.workEffortId = workEffortId;
    }

    public String getWorkEffortName() {
        return workEffortName;
    }

    public void setWorkEffortName(String workEffortName) {
        this.workEffortName = workEffortName;
    }

    public String getWorkEffortNameLang() {
        return workEffortNameLang;
    }

    public void setWorkEffortNameLang(String workEffortNameLang) {
        this.workEffortNameLang = workEffortNameLang;
    }

    public String getWorkEffortParentId() {
        return workEffortParentId;
    }

    public void setWorkEffortParentId(String workEffortParentId) {
        this.workEffortParentId = workEffortParentId;
    }

    public String getWorkEffortPurposeTypeId() {
        return workEffortPurposeTypeId;
    }

    public void setWorkEffortPurposeTypeId(String workEffortPurposeTypeId) {
        this.workEffortPurposeTypeId = workEffortPurposeTypeId;
    }

    public String getWorkEffortRevisionId() {
        return workEffortRevisionId;
    }

    public void setWorkEffortRevisionId(String workEffortRevisionId) {
        this.workEffortRevisionId = workEffortRevisionId;
    }

    public String getWorkEffortSnapshotId() {
        return workEffortSnapshotId;
    }

    public void setWorkEffortSnapshotId(String workEffortSnapshotId) {
        this.workEffortSnapshotId = workEffortSnapshotId;
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

