package it.mapsgroup.gzoom.querydsl.dto;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;

import java.util.*;

import com.querydsl.sql.ColumnMetadata;
import java.sql.Types;




/**
 * QWorkEffort is a Querydsl query type for WorkEffort
 */
@Generated("com.querydsl.sql.codegen.MetaDataSerializer")
public class QWorkEffort extends com.querydsl.sql.RelationalPathBase<WorkEffort> {

    private static final long serialVersionUID = 1758708200;

    public static final QWorkEffort workEffort = new QWorkEffort("WORK_EFFORT");

    public final StringPath accommodationMapId = createString("accommodationMapId");

    public final StringPath accommodationSpotId = createString("accommodationSpotId");

    public final DateTimePath<java.time.LocalDateTime> actualCompletionDate = createDateTime("actualCompletionDate", java.time.LocalDateTime.class);

    public final NumberPath<java.math.BigDecimal> actualMilliSeconds = createNumber("actualMilliSeconds", java.math.BigDecimal.class);

    public final NumberPath<java.math.BigDecimal> actualSetupMillis = createNumber("actualSetupMillis", java.math.BigDecimal.class);

    public final DateTimePath<java.time.LocalDateTime> actualStartDate = createDateTime("actualStartDate", java.time.LocalDateTime.class);

    public final StringPath createdByUserLogin = createString("createdByUserLogin");

    public final DateTimePath<java.time.LocalDateTime> createdDate = createDateTime("createdDate", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> createdStamp = createDateTime("createdStamp", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> createdTxStamp = createDateTime("createdTxStamp", java.time.LocalDateTime.class);

    public final StringPath currentStatusId = createString("currentStatusId");

    public final DateTimePath<java.time.LocalDateTime> dataSoll = createDateTime("dataSoll", java.time.LocalDateTime.class);

    public final StringPath description = createString("description");

    public final StringPath descriptionLang = createString("descriptionLang");

    public final StringPath effortUomId = createString("effortUomId");

    public final StringPath emplPositionTypeId = createString("emplPositionTypeId");

    public final StringPath estimateCalcMethod = createString("estimateCalcMethod");

    public final DateTimePath<java.time.LocalDateTime> estimatedCompletionDate = createDateTime("estimatedCompletionDate", java.time.LocalDateTime.class);

    public final NumberPath<java.math.BigDecimal> estimatedMilliSeconds = createNumber("estimatedMilliSeconds", java.math.BigDecimal.class);

    public final NumberPath<java.math.BigDecimal> estimatedSetupMillis = createNumber("estimatedSetupMillis", java.math.BigDecimal.class);

    public final DateTimePath<java.time.LocalDateTime> estimatedStartDate = createDateTime("estimatedStartDate", java.time.LocalDateTime.class);

    public final NumberPath<java.math.BigDecimal> estimatedTotalEffort = createNumber("estimatedTotalEffort", java.math.BigDecimal.class);

    public final StringPath etch = createString("etch");

    public final StringPath facilityId = createString("facilityId");

    public final StringPath fixedAssetId = createString("fixedAssetId");

    public final StringPath infoUrl = createString("infoUrl");

    public final BooleanPath isPosted = createBoolean("isPosted");

    public final DateTimePath<java.time.LocalDateTime> lastCorrectScoreDate = createDateTime("lastCorrectScoreDate", java.time.LocalDateTime.class);

    public final StringPath lastModifiedByUserLogin = createString("lastModifiedByUserLogin");

    public final DateTimePath<java.time.LocalDateTime> lastModifiedDate = createDateTime("lastModifiedDate", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> lastStatusUpdate = createDateTime("lastStatusUpdate", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> lastUpdatedStamp = createDateTime("lastUpdatedStamp", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> lastUpdatedTxStamp = createDateTime("lastUpdatedTxStamp", java.time.LocalDateTime.class);

    public final StringPath localNameContentId = createString("localNameContentId");

    public final StringPath locationDesc = createString("locationDesc");

    public final StringPath moneyUomId = createString("moneyUomId");

    public final StringPath noteId = createString("noteId");

    public final StringPath organizationId = createString("organizationId");

    public final StringPath orgUnitId = createString("orgUnitId");

    public final StringPath orgUnitRoleTypeId = createString("orgUnitRoleTypeId");

    public final NumberPath<java.math.BigInteger> percentComplete = createNumber("percentComplete", java.math.BigInteger.class);

    public final NumberPath<java.math.BigInteger> priority = createNumber("priority", java.math.BigInteger.class);

    public final StringPath processId = createString("processId");

    public final StringPath productId = createString("productId");

    public final NumberPath<java.math.BigDecimal> quantityProduced = createNumber("quantityProduced", java.math.BigDecimal.class);

    public final NumberPath<java.math.BigDecimal> quantityRejected = createNumber("quantityRejected", java.math.BigDecimal.class);

    public final NumberPath<java.math.BigDecimal> quantityToProduce = createNumber("quantityToProduce", java.math.BigDecimal.class);

    public final StringPath recurrenceInfoId = createString("recurrenceInfoId");

    public final NumberPath<java.math.BigDecimal> reserv2ndPPPerc = createNumber("reserv2ndPPPerc", java.math.BigDecimal.class);

    public final NumberPath<java.math.BigDecimal> reservNthPPPerc = createNumber("reservNthPPPerc", java.math.BigDecimal.class);

    public final NumberPath<java.math.BigDecimal> reservPersons = createNumber("reservPersons", java.math.BigDecimal.class);

    public final NumberPath<java.math.BigInteger> revisionNumber = createNumber("revisionNumber", java.math.BigInteger.class);

    public final StringPath runtimeDataId = createString("runtimeDataId");

    public final DateTimePath<java.time.LocalDateTime> scheduledCompletionDate = createDateTime("scheduledCompletionDate", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> scheduledStartDate = createDateTime("scheduledStartDate", java.time.LocalDateTime.class);

    public final StringPath scopeEnumId = createString("scopeEnumId");

    public final BooleanPath sendNotificationEmail = createBoolean("sendNotificationEmail");

    public final NumberPath<java.math.BigInteger> sequenceNum = createNumber("sequenceNum", java.math.BigInteger.class);

    public final StringPath serviceLoaderName = createString("serviceLoaderName");

    public final StringPath showAsEnumId = createString("showAsEnumId");

    public final DateTimePath<java.time.LocalDateTime> snapShotDate = createDateTime("snapShotDate", java.time.LocalDateTime.class);

    public final StringPath snapShotDescription = createString("snapShotDescription");

    public final StringPath sourceReferenceId = createString("sourceReferenceId");

    public final StringPath specialTerms = createString("specialTerms");

    public final StringPath tempExprId = createString("tempExprId");

    public final NumberPath<java.math.BigInteger> timeTransparency = createNumber("timeTransparency", java.math.BigInteger.class);

    public final StringPath totalEnumIdAssoc = createString("totalEnumIdAssoc");

    public final StringPath totalEnumIdKpi = createString("totalEnumIdKpi");

    public final StringPath totalEnumIdSons = createString("totalEnumIdSons");

    public final NumberPath<java.math.BigDecimal> totalMilliSecondsAllowed = createNumber("totalMilliSecondsAllowed", java.math.BigDecimal.class);

    public final NumberPath<java.math.BigDecimal> totalMoneyAllowed = createNumber("totalMoneyAllowed", java.math.BigDecimal.class);

    public final StringPath universalId = createString("universalId");

    public final StringPath uomRangeScoreId = createString("uomRangeScoreId");

    public final NumberPath<java.math.BigDecimal> weightAssocWorkEffort = createNumber("weightAssocWorkEffort", java.math.BigDecimal.class);

    public final NumberPath<java.math.BigDecimal> weightKpi = createNumber("weightKpi", java.math.BigDecimal.class);

    public final NumberPath<java.math.BigDecimal> weightReview = createNumber("weightReview", java.math.BigDecimal.class);

    public final NumberPath<java.math.BigDecimal> weightSons = createNumber("weightSons", java.math.BigDecimal.class);

    public final StringPath workEffortAssocTypeId = createString("workEffortAssocTypeId");

    public final StringPath workEffortId = createString("workEffortId");

    public final StringPath workEffortName = createString("workEffortName");

    public final StringPath workEffortNameLang = createString("workEffortNameLang");

    public final StringPath workEffortParentId = createString("workEffortParentId");

    public final StringPath workEffortPurposeTypeId = createString("workEffortPurposeTypeId");

    public final StringPath workEffortRevisionId = createString("workEffortRevisionId");

    public final StringPath workEffortSnapshotId = createString("workEffortSnapshotId");

    public final StringPath workEffortTypeId = createString("workEffortTypeId");

    public final StringPath workEffortTypePeriodId = createString("workEffortTypePeriodId");

    public final com.querydsl.sql.PrimaryKey<WorkEffort> primary = createPrimaryKey(workEffortId);

    public final com.querydsl.sql.ForeignKey<Uom> wkEffrtMonUom = createForeignKey(moneyUomId, "UOM_ID");

    public final com.querydsl.sql.ForeignKey<PartyRole> weOrgUnit = createForeignKey(Arrays.asList(orgUnitId, orgUnitRoleTypeId), Arrays.asList("PARTY_ID", "ROLE_TYPE_ID"));

    public final com.querydsl.sql.ForeignKey<Content> weLnamecnt = createForeignKey(localNameContentId, "CONTENT_ID");

    public final com.querydsl.sql.ForeignKey<WorkEffort> wkEffrtParent = createForeignKey(workEffortParentId, "WORK_EFFORT_ID");

    public final com.querydsl.sql.ForeignKey<Uom> weEffuom = createForeignKey(effortUomId, "UOM_ID");

    public final com.querydsl.sql.ForeignKey<WorkEffortType> wkEffrtType = createForeignKey(workEffortTypeId, "WORK_EFFORT_TYPE_ID");

    public final com.querydsl.sql.ForeignKey<TimeEntry> _timeEntWeff = createInvForeignKey(workEffortId, "WORK_EFFORT_ID");

    public final com.querydsl.sql.ForeignKey<WorkEffort> _wkEffrtParent = createInvForeignKey(workEffortId, "WORK_EFFORT_PARENT_ID");

    public final com.querydsl.sql.ForeignKey<WorkEffortAssoc> _wkEffrtasscFwe = createInvForeignKey(workEffortId, "WORK_EFFORT_ID_FROM");

    public final com.querydsl.sql.ForeignKey<WorkEffortPartyAssignment> _wkeffPaWe = createInvForeignKey(workEffortId, "WORK_EFFORT_ID");

    public final com.querydsl.sql.ForeignKey<WorkEffortAssoc> _wkEffrtasscTwe = createInvForeignKey(workEffortId, "WORK_EFFORT_ID_TO");

    public QWorkEffort(String variable) {
        super(WorkEffort.class, forVariable(variable), "null", "WORK_EFFORT");
        addMetadata();
    }

    public QWorkEffort(String variable, String schema, String table) {
        super(WorkEffort.class, forVariable(variable), schema, table);
        addMetadata();
    }

    public QWorkEffort(String variable, String schema) {
        super(WorkEffort.class, forVariable(variable), schema, "WORK_EFFORT");
        addMetadata();
    }

    public QWorkEffort(Path<? extends WorkEffort> path) {
        super(path.getType(), path.getMetadata(), "null", "WORK_EFFORT");
        addMetadata();
    }

    public QWorkEffort(PathMetadata metadata) {
        super(WorkEffort.class, metadata, "null", "WORK_EFFORT");
        addMetadata();
    }

    public void addMetadata() {
        addMetadata(accommodationMapId, ColumnMetadata.named("ACCOMMODATION_MAP_ID").withIndex(45).ofType(Types.VARCHAR).withSize(20));
        addMetadata(accommodationSpotId, ColumnMetadata.named("ACCOMMODATION_SPOT_ID").withIndex(46).ofType(Types.VARCHAR).withSize(20));
        addMetadata(actualCompletionDate, ColumnMetadata.named("ACTUAL_COMPLETION_DATE").withIndex(18).ofType(Types.TIMESTAMP).withSize(19));
        addMetadata(actualMilliSeconds, ColumnMetadata.named("ACTUAL_MILLI_SECONDS").withIndex(22).ofType(Types.DECIMAL).withSize(18).withDigits(6));
        addMetadata(actualSetupMillis, ColumnMetadata.named("ACTUAL_SETUP_MILLIS").withIndex(23).ofType(Types.DECIMAL).withSize(18).withDigits(6));
        addMetadata(actualStartDate, ColumnMetadata.named("ACTUAL_START_DATE").withIndex(17).ofType(Types.TIMESTAMP).withSize(19));
        addMetadata(createdByUserLogin, ColumnMetadata.named("CREATED_BY_USER_LOGIN").withIndex(49).ofType(Types.VARCHAR).withSize(250));
        addMetadata(createdDate, ColumnMetadata.named("CREATED_DATE").withIndex(48).ofType(Types.TIMESTAMP).withSize(19));
        addMetadata(createdStamp, ColumnMetadata.named("CREATED_STAMP").withIndex(54).ofType(Types.TIMESTAMP).withSize(19));
        addMetadata(createdTxStamp, ColumnMetadata.named("CREATED_TX_STAMP").withIndex(55).ofType(Types.TIMESTAMP).withSize(19));
        addMetadata(currentStatusId, ColumnMetadata.named("CURRENT_STATUS_ID").withIndex(3).ofType(Types.VARCHAR).withSize(20));
        addMetadata(dataSoll, ColumnMetadata.named("DATA_SOLL").withIndex(87).ofType(Types.TIMESTAMP).withSize(19));
        addMetadata(description, ColumnMetadata.named("DESCRIPTION").withIndex(13).ofType(Types.VARCHAR).withSize(2000));
        addMetadata(descriptionLang, ColumnMetadata.named("DESCRIPTION_LANG").withIndex(86).ofType(Types.VARCHAR).withSize(2000));
        addMetadata(effortUomId, ColumnMetadata.named("EFFORT_UOM_ID").withIndex(58).ofType(Types.VARCHAR).withSize(20));
        addMetadata(emplPositionTypeId, ColumnMetadata.named("EMPL_POSITION_TYPE_ID").withIndex(60).ofType(Types.VARCHAR).withSize(20));
        addMetadata(estimateCalcMethod, ColumnMetadata.named("ESTIMATE_CALC_METHOD").withIndex(21).ofType(Types.VARCHAR).withSize(20));
        addMetadata(estimatedCompletionDate, ColumnMetadata.named("ESTIMATED_COMPLETION_DATE").withIndex(16).ofType(Types.TIMESTAMP).withSize(19));
        addMetadata(estimatedMilliSeconds, ColumnMetadata.named("ESTIMATED_MILLI_SECONDS").withIndex(19).ofType(Types.DECIMAL).withSize(18).withDigits(6));
        addMetadata(estimatedSetupMillis, ColumnMetadata.named("ESTIMATED_SETUP_MILLIS").withIndex(20).ofType(Types.DECIMAL).withSize(18).withDigits(6));
        addMetadata(estimatedStartDate, ColumnMetadata.named("ESTIMATED_START_DATE").withIndex(15).ofType(Types.TIMESTAMP).withSize(19));
        addMetadata(estimatedTotalEffort, ColumnMetadata.named("ESTIMATED_TOTAL_EFFORT").withIndex(62).ofType(Types.DECIMAL).withSize(18).withDigits(6));
        addMetadata(etch, ColumnMetadata.named("ETCH").withIndex(76).ofType(Types.VARCHAR).withSize(20));
        addMetadata(facilityId, ColumnMetadata.named("FACILITY_ID").withIndex(32).ofType(Types.VARCHAR).withSize(20));
        addMetadata(fixedAssetId, ColumnMetadata.named("FIXED_ASSET_ID").withIndex(31).ofType(Types.VARCHAR).withSize(20));
        addMetadata(infoUrl, ColumnMetadata.named("INFO_URL").withIndex(33).ofType(Types.VARCHAR).withSize(255));
        addMetadata(isPosted, ColumnMetadata.named("IS_POSTED").withIndex(75).ofType(Types.CHAR).withSize(1));
        addMetadata(lastCorrectScoreDate, ColumnMetadata.named("LAST_CORRECT_SCORE_DATE").withIndex(70).ofType(Types.TIMESTAMP).withSize(19));
        addMetadata(lastModifiedByUserLogin, ColumnMetadata.named("LAST_MODIFIED_BY_USER_LOGIN").withIndex(51).ofType(Types.VARCHAR).withSize(250));
        addMetadata(lastModifiedDate, ColumnMetadata.named("LAST_MODIFIED_DATE").withIndex(50).ofType(Types.TIMESTAMP).withSize(19));
        addMetadata(lastStatusUpdate, ColumnMetadata.named("LAST_STATUS_UPDATE").withIndex(4).ofType(Types.TIMESTAMP).withSize(19));
        addMetadata(lastUpdatedStamp, ColumnMetadata.named("LAST_UPDATED_STAMP").withIndex(52).ofType(Types.TIMESTAMP).withSize(19));
        addMetadata(lastUpdatedTxStamp, ColumnMetadata.named("LAST_UPDATED_TX_STAMP").withIndex(53).ofType(Types.TIMESTAMP).withSize(19));
        addMetadata(localNameContentId, ColumnMetadata.named("LOCAL_NAME_CONTENT_ID").withIndex(61).ofType(Types.VARCHAR).withSize(20));
        addMetadata(locationDesc, ColumnMetadata.named("LOCATION_DESC").withIndex(14).ofType(Types.VARCHAR).withSize(255));
        addMetadata(moneyUomId, ColumnMetadata.named("MONEY_UOM_ID").withIndex(26).ofType(Types.VARCHAR).withSize(20));
        addMetadata(noteId, ColumnMetadata.named("NOTE_ID").withIndex(37).ofType(Types.VARCHAR).withSize(20));
        addMetadata(organizationId, ColumnMetadata.named("ORGANIZATION_ID").withIndex(57).ofType(Types.VARCHAR).withSize(20));
        addMetadata(orgUnitId, ColumnMetadata.named("ORG_UNIT_ID").withIndex(67).ofType(Types.VARCHAR).withSize(20));
        addMetadata(orgUnitRoleTypeId, ColumnMetadata.named("ORG_UNIT_ROLE_TYPE_ID").withIndex(66).ofType(Types.VARCHAR).withSize(20));
        addMetadata(percentComplete, ColumnMetadata.named("PERCENT_COMPLETE").withIndex(9).ofType(Types.DECIMAL).withSize(20));
        addMetadata(priority, ColumnMetadata.named("PRIORITY").withIndex(8).ofType(Types.DECIMAL).withSize(20));
        addMetadata(processId, ColumnMetadata.named("PROCESS_ID").withIndex(74).ofType(Types.VARCHAR).withSize(60));
        addMetadata(productId, ColumnMetadata.named("PRODUCT_ID").withIndex(59).ofType(Types.VARCHAR).withSize(20));
        addMetadata(quantityProduced, ColumnMetadata.named("QUANTITY_PRODUCED").withIndex(40).ofType(Types.DECIMAL).withSize(18).withDigits(6));
        addMetadata(quantityRejected, ColumnMetadata.named("QUANTITY_REJECTED").withIndex(41).ofType(Types.DECIMAL).withSize(18).withDigits(6));
        addMetadata(quantityToProduce, ColumnMetadata.named("QUANTITY_TO_PRODUCE").withIndex(39).ofType(Types.DECIMAL).withSize(18).withDigits(6));
        addMetadata(recurrenceInfoId, ColumnMetadata.named("RECURRENCE_INFO_ID").withIndex(34).ofType(Types.VARCHAR).withSize(20));
        addMetadata(reserv2ndPPPerc, ColumnMetadata.named("RESERV2ND_P_P_PERC").withIndex(43).ofType(Types.DECIMAL).withSize(18).withDigits(6));
        addMetadata(reservNthPPPerc, ColumnMetadata.named("RESERV_NTH_P_P_PERC").withIndex(44).ofType(Types.DECIMAL).withSize(18).withDigits(6));
        addMetadata(reservPersons, ColumnMetadata.named("RESERV_PERSONS").withIndex(42).ofType(Types.DECIMAL).withSize(18).withDigits(6));
        addMetadata(revisionNumber, ColumnMetadata.named("REVISION_NUMBER").withIndex(47).ofType(Types.DECIMAL).withSize(20));
        addMetadata(runtimeDataId, ColumnMetadata.named("RUNTIME_DATA_ID").withIndex(36).ofType(Types.VARCHAR).withSize(20));
        addMetadata(scheduledCompletionDate, ColumnMetadata.named("SCHEDULED_COMPLETION_DATE").withIndex(84).ofType(Types.TIMESTAMP).withSize(19));
        addMetadata(scheduledStartDate, ColumnMetadata.named("SCHEDULED_START_DATE").withIndex(83).ofType(Types.TIMESTAMP).withSize(19));
        addMetadata(scopeEnumId, ColumnMetadata.named("SCOPE_ENUM_ID").withIndex(7).ofType(Types.VARCHAR).withSize(20));
        addMetadata(sendNotificationEmail, ColumnMetadata.named("SEND_NOTIFICATION_EMAIL").withIndex(12).ofType(Types.CHAR).withSize(1));
        addMetadata(sequenceNum, ColumnMetadata.named("SEQUENCE_NUM").withIndex(56).ofType(Types.DECIMAL).withSize(20));
        addMetadata(serviceLoaderName, ColumnMetadata.named("SERVICE_LOADER_NAME").withIndex(38).ofType(Types.VARCHAR).withSize(100));
        addMetadata(showAsEnumId, ColumnMetadata.named("SHOW_AS_ENUM_ID").withIndex(11).ofType(Types.VARCHAR).withSize(20));
        addMetadata(snapShotDate, ColumnMetadata.named("SNAP_SHOT_DATE").withIndex(78).ofType(Types.TIMESTAMP).withSize(19));
        addMetadata(snapShotDescription, ColumnMetadata.named("SNAP_SHOT_DESCRIPTION").withIndex(79).ofType(Types.VARCHAR).withSize(2000));
        addMetadata(sourceReferenceId, ColumnMetadata.named("SOURCE_REFERENCE_ID").withIndex(30).ofType(Types.VARCHAR).withSize(60));
        addMetadata(specialTerms, ColumnMetadata.named("SPECIAL_TERMS").withIndex(27).ofType(Types.VARCHAR).withSize(4000));
        addMetadata(tempExprId, ColumnMetadata.named("TEMP_EXPR_ID").withIndex(35).ofType(Types.VARCHAR).withSize(20));
        addMetadata(timeTransparency, ColumnMetadata.named("TIME_TRANSPARENCY").withIndex(28).ofType(Types.DECIMAL).withSize(20));
        addMetadata(totalEnumIdAssoc, ColumnMetadata.named("TOTAL_ENUM_ID_ASSOC").withIndex(73).ofType(Types.VARCHAR).withSize(20));
        addMetadata(totalEnumIdKpi, ColumnMetadata.named("TOTAL_ENUM_ID_KPI").withIndex(71).ofType(Types.VARCHAR).withSize(20));
        addMetadata(totalEnumIdSons, ColumnMetadata.named("TOTAL_ENUM_ID_SONS").withIndex(72).ofType(Types.VARCHAR).withSize(20));
        addMetadata(totalMilliSecondsAllowed, ColumnMetadata.named("TOTAL_MILLI_SECONDS_ALLOWED").withIndex(24).ofType(Types.DECIMAL).withSize(18).withDigits(6));
        addMetadata(totalMoneyAllowed, ColumnMetadata.named("TOTAL_MONEY_ALLOWED").withIndex(25).ofType(Types.DECIMAL).withSize(18).withDigits(2));
        addMetadata(universalId, ColumnMetadata.named("UNIVERSAL_ID").withIndex(29).ofType(Types.VARCHAR).withSize(60));
        addMetadata(uomRangeScoreId, ColumnMetadata.named("UOM_RANGE_SCORE_ID").withIndex(81).ofType(Types.VARCHAR).withSize(20));
        addMetadata(weightAssocWorkEffort, ColumnMetadata.named("WEIGHT_ASSOC_WORK_EFFORT").withIndex(68).ofType(Types.DECIMAL).withSize(18).withDigits(6));
        addMetadata(weightKpi, ColumnMetadata.named("WEIGHT_KPI").withIndex(63).ofType(Types.DECIMAL).withSize(18).withDigits(6));
        addMetadata(weightReview, ColumnMetadata.named("WEIGHT_REVIEW").withIndex(64).ofType(Types.DECIMAL).withSize(18).withDigits(6));
        addMetadata(weightSons, ColumnMetadata.named("WEIGHT_SONS").withIndex(65).ofType(Types.DECIMAL).withSize(18).withDigits(6));
        addMetadata(workEffortAssocTypeId, ColumnMetadata.named("WORK_EFFORT_ASSOC_TYPE_ID").withIndex(69).ofType(Types.VARCHAR).withSize(20));
        addMetadata(workEffortId, ColumnMetadata.named("WORK_EFFORT_ID").withIndex(1).ofType(Types.VARCHAR).withSize(20).notNull());
        addMetadata(workEffortName, ColumnMetadata.named("WORK_EFFORT_NAME").withIndex(10).ofType(Types.VARCHAR).withSize(255));
        addMetadata(workEffortNameLang, ColumnMetadata.named("WORK_EFFORT_NAME_LANG").withIndex(85).ofType(Types.VARCHAR).withSize(255));
        addMetadata(workEffortParentId, ColumnMetadata.named("WORK_EFFORT_PARENT_ID").withIndex(6).ofType(Types.VARCHAR).withSize(20));
        addMetadata(workEffortPurposeTypeId, ColumnMetadata.named("WORK_EFFORT_PURPOSE_TYPE_ID").withIndex(5).ofType(Types.VARCHAR).withSize(20));
        addMetadata(workEffortRevisionId, ColumnMetadata.named("WORK_EFFORT_REVISION_ID").withIndex(82).ofType(Types.VARCHAR).withSize(20));
        addMetadata(workEffortSnapshotId, ColumnMetadata.named("WORK_EFFORT_SNAPSHOT_ID").withIndex(80).ofType(Types.VARCHAR).withSize(20));
        addMetadata(workEffortTypeId, ColumnMetadata.named("WORK_EFFORT_TYPE_ID").withIndex(2).ofType(Types.VARCHAR).withSize(20));
        addMetadata(workEffortTypePeriodId, ColumnMetadata.named("WORK_EFFORT_TYPE_PERIOD_ID").withIndex(77).ofType(Types.VARCHAR).withSize(20));
    }

}

