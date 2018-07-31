package it.mapsgroup.gzoom.querydsl.dto;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;

import com.querydsl.sql.ColumnMetadata;
import java.sql.Types;




/**
 * QWorkEffortType is a Querydsl query type for WorkEffortType
 */
@Generated("com.querydsl.sql.codegen.MetaDataSerializer")
public class QWorkEffortType extends com.querydsl.sql.RelationalPathBase<WorkEffortType> {

    private static final long serialVersionUID = 1945669954;

    public static final QWorkEffortType workEffortType = new QWorkEffortType("WORK_EFFORT_TYPE");

    public final BooleanPath allOrgAssigned = createBoolean("allOrgAssigned");

    public final BooleanPath allRolesAssigned = createBoolean("allRolesAssigned");

    public final BooleanPath applyScoreRange = createBoolean("applyScoreRange");

    public final StringPath childTemplateId = createString("childTemplateId");

    public final StringPath codeCounter = createString("codeCounter");

    public final StringPath codePrefix = createString("codePrefix");

    public final BooleanPath copy = createBoolean("copy");

    public final StringPath createdByUserLogin = createString("createdByUserLogin");

    public final DateTimePath<java.time.LocalDateTime> createdStamp = createDateTime("createdStamp", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> createdTxStamp = createDateTime("createdTxStamp", java.time.LocalDateTime.class);

    public final StringPath description = createString("description");

    public final StringPath descriptionLang = createString("descriptionLang");

    public final StringPath effortUomId = createString("effortUomId");

    public final BooleanPath enableMultiYearFlag = createBoolean("enableMultiYearFlag");

    public final BooleanPath enableSnapshot = createBoolean("enableSnapshot");

    public final StringPath etch = createString("etch");

    public final StringPath etchLang = createString("etchLang");

    public final StringPath evalEnumId = createString("evalEnumId");

    public final BooleanPath forAllUsers = createBoolean("forAllUsers");

    public final StringPath frameEnumId = createString("frameEnumId");

    public final DateTimePath<java.time.LocalDateTime> fromDate = createDateTime("fromDate", java.time.LocalDateTime.class);

    public final BooleanPath hasPersonFilter = createBoolean("hasPersonFilter");

    public final BooleanPath hasProduct = createBoolean("hasProduct");

    public final BooleanPath hasTable = createBoolean("hasTable");

    public final StringPath hierarchyAssocTypeId = createString("hierarchyAssocTypeId");

    public final StringPath iconContentId = createString("iconContentId");

    public final StringPath influenceCatalogId = createString("influenceCatalogId");

    public final BooleanPath isIndicAuto = createBoolean("isIndicAuto");

    public final BooleanPath isInOnlyOneCard = createBoolean("isInOnlyOneCard");

    public final BooleanPath isRoleTypeIdAuto = createBoolean("isRoleTypeIdAuto");

    public final BooleanPath isRoot = createBoolean("isRoot");

    public final BooleanPath isRootActive = createBoolean("isRootActive");

    public final BooleanPath isTemplate = createBoolean("isTemplate");

    public final StringPath lastModifiedByUserLogin = createString("lastModifiedByUserLogin");

    public final DateTimePath<java.time.LocalDateTime> lastUpdatedStamp = createDateTime("lastUpdatedStamp", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> lastUpdatedTxStamp = createDateTime("lastUpdatedTxStamp", java.time.LocalDateTime.class);

    public final StringPath localNameContentId = createString("localNameContentId");

    public final NumberPath<java.math.BigInteger> max1 = createNumber("max1", java.math.BigInteger.class);

    public final NumberPath<java.math.BigInteger> max2 = createNumber("max2", java.math.BigInteger.class);

    public final NumberPath<java.math.BigInteger> max3 = createNumber("max3", java.math.BigInteger.class);

    public final NumberPath<java.math.BigInteger> max4 = createNumber("max4", java.math.BigInteger.class);

    public final NumberPath<java.math.BigInteger> max5 = createNumber("max5", java.math.BigInteger.class);

    public final NumberPath<java.math.BigInteger> min1 = createNumber("min1", java.math.BigInteger.class);

    public final NumberPath<java.math.BigInteger> min2 = createNumber("min2", java.math.BigInteger.class);

    public final NumberPath<java.math.BigInteger> min3 = createNumber("min3", java.math.BigInteger.class);

    public final NumberPath<java.math.BigInteger> min4 = createNumber("min4", java.math.BigInteger.class);

    public final NumberPath<java.math.BigInteger> min5 = createNumber("min5", java.math.BigInteger.class);

    public final StringPath note = createString("note");

    public final StringPath noteLang = createString("noteLang");

    public final StringPath orgUnitRoleTypeId = createString("orgUnitRoleTypeId");

    public final BooleanPath parentPeriodFilter = createBoolean("parentPeriodFilter");

    public final StringPath parentTypeId = createString("parentTypeId");

    public final StringPath partyRelTypeIdAuto = createString("partyRelTypeIdAuto");

    public final StringPath periodOpenEnumId = createString("periodOpenEnumId");

    public final StringPath periodTypeId = createString("periodTypeId");

    public final StringPath purposeEtch = createString("purposeEtch");

    public final StringPath purposeEtchLang = createString("purposeEtchLang");

    public final BooleanPath reminderActive = createBoolean("reminderActive");

    public final StringPath roleTypeIdAuto = createString("roleTypeIdAuto");

    public final StringPath ruleTypeId1 = createString("ruleTypeId1");

    public final StringPath ruleTypeId2 = createString("ruleTypeId2");

    public final StringPath ruleTypeId3 = createString("ruleTypeId3");

    public final StringPath ruleTypeId4 = createString("ruleTypeId4");

    public final StringPath ruleTypeId5 = createString("ruleTypeId5");

    public final StringPath scorePeriodEnumId = createString("scorePeriodEnumId");

    public final NumberPath<java.math.BigInteger> seqDigit = createNumber("seqDigit", java.math.BigInteger.class);

    public final BooleanPath seqOnlyId = createBoolean("seqOnlyId");

    public final StringPath shortLabel = createString("shortLabel");

    public final BooleanPath showHierarchy = createBoolean("showHierarchy");

    public final BooleanPath showScorekpi = createBoolean("showScorekpi");

    public final DateTimePath<java.time.LocalDateTime> thruDate = createDateTime("thruDate", java.time.LocalDateTime.class);

    public final StringPath totalEnumIdAssoc = createString("totalEnumIdAssoc");

    public final StringPath totalEnumIdKpi = createString("totalEnumIdKpi");

    public final StringPath totalEnumIdSons = createString("totalEnumIdSons");

    public final StringPath typeId1 = createString("typeId1");

    public final StringPath typeId2 = createString("typeId2");

    public final StringPath typeId3 = createString("typeId3");

    public final StringPath typeId4 = createString("typeId4");

    public final StringPath uomRangeScoreId = createString("uomRangeScoreId");

    public final NumberPath<java.math.BigDecimal> weightAssocWorkEffort = createNumber("weightAssocWorkEffort", java.math.BigDecimal.class);

    public final NumberPath<java.math.BigDecimal> weightControlSum = createNumber("weightControlSum", java.math.BigDecimal.class);

    public final NumberPath<java.math.BigDecimal> weightKpi = createNumber("weightKpi", java.math.BigDecimal.class);

    public final NumberPath<java.math.BigDecimal> weightKpiControlSum = createNumber("weightKpiControlSum", java.math.BigDecimal.class);

    public final NumberPath<java.math.BigDecimal> weightSons = createNumber("weightSons", java.math.BigDecimal.class);

    public final StringPath weLayoutTypeEnumId = createString("weLayoutTypeEnumId");

    public final StringPath wePurposeTypeIdInd = createString("wePurposeTypeIdInd");

    public final StringPath wePurposeTypeIdRes = createString("wePurposeTypeIdRes");

    public final StringPath wePurposeTypeIdWe = createString("wePurposeTypeIdWe");

    public final StringPath workEffortAssocTypeId = createString("workEffortAssocTypeId");

    public final StringPath workEffortTypeHierarchyId = createString("workEffortTypeHierarchyId");

    public final StringPath workEffortTypeId = createString("workEffortTypeId");

    public final com.querydsl.sql.PrimaryKey<WorkEffortType> primary = createPrimaryKey(workEffortTypeId);

    public final com.querydsl.sql.ForeignKey<WorkEffortType> wetRult4wet = createForeignKey(ruleTypeId4, "WORK_EFFORT_TYPE_ID");

    public final com.querydsl.sql.ForeignKey<Content> wetIconcnt = createForeignKey(iconContentId, "CONTENT_ID");

    public final com.querydsl.sql.ForeignKey<WorkEffortType> wetRult2wet = createForeignKey(ruleTypeId2, "WORK_EFFORT_TYPE_ID");

    public final com.querydsl.sql.ForeignKey<Content> wetLnamecnt = createForeignKey(localNameContentId, "CONTENT_ID");

    public final com.querydsl.sql.ForeignKey<WorkEffortType> wetRult1wet = createForeignKey(ruleTypeId1, "WORK_EFFORT_TYPE_ID");

    public final com.querydsl.sql.ForeignKey<WorkEffortType> wetChldtmplwet = createForeignKey(childTemplateId, "WORK_EFFORT_TYPE_ID");

    public final com.querydsl.sql.ForeignKey<WorkEffortType> wetRult5wet = createForeignKey(ruleTypeId5, "WORK_EFFORT_TYPE_ID");

    public final com.querydsl.sql.ForeignKey<WorkEffortType> wetRult3wet = createForeignKey(ruleTypeId3, "WORK_EFFORT_TYPE_ID");

    public final com.querydsl.sql.ForeignKey<WorkEffortType> wkEffrtTypePar = createForeignKey(parentTypeId, "WORK_EFFORT_TYPE_ID");

    public final com.querydsl.sql.ForeignKey<Uom> wetEffuom = createForeignKey(effortUomId, "UOM_ID");

    public final com.querydsl.sql.ForeignKey<WorkEffort> _wkEffrtType = createInvForeignKey(workEffortTypeId, "WORK_EFFORT_TYPE_ID");

    public final com.querydsl.sql.ForeignKey<WorkEffortType> _wetRult2wet = createInvForeignKey(workEffortTypeId, "RULE_TYPE_ID2");

    public final com.querydsl.sql.ForeignKey<WorkEffortType> _wkEffrtTypePar = createInvForeignKey(workEffortTypeId, "PARENT_TYPE_ID");

    public final com.querydsl.sql.ForeignKey<WorkEffortTypeType> _wttToFk = createInvForeignKey(workEffortTypeId, "WORK_EFFORT_TYPE_ID_TO");

    public final com.querydsl.sql.ForeignKey<WorkEffortType> _wetRult4wet = createInvForeignKey(workEffortTypeId, "RULE_TYPE_ID4");

    public final com.querydsl.sql.ForeignKey<WorkEffortType> _wetChldtmplwet = createInvForeignKey(workEffortTypeId, "CHILD_TEMPLATE_ID");

    public final com.querydsl.sql.ForeignKey<WorkEffortType> _wetRult1wet = createInvForeignKey(workEffortTypeId, "RULE_TYPE_ID1");

    public final com.querydsl.sql.ForeignKey<WorkEffortType> _wetRult5wet = createInvForeignKey(workEffortTypeId, "RULE_TYPE_ID5");

    public final com.querydsl.sql.ForeignKey<WorkEffortType> _wetRult3wet = createInvForeignKey(workEffortTypeId, "RULE_TYPE_ID3");

    public final com.querydsl.sql.ForeignKey<WorkEffortTypeType> _wttFrFk = createInvForeignKey(workEffortTypeId, "WORK_EFFORT_TYPE_ID_FROM");

    public final com.querydsl.sql.ForeignKey<WorkEffortTypeType> _wttRtFk = createInvForeignKey(workEffortTypeId, "WORK_EFFORT_TYPE_ID_ROOT");

    public QWorkEffortType(String variable) {
        super(WorkEffortType.class, forVariable(variable), "null", "WORK_EFFORT_TYPE");
        addMetadata();
    }

    public QWorkEffortType(String variable, String schema, String table) {
        super(WorkEffortType.class, forVariable(variable), schema, table);
        addMetadata();
    }

    public QWorkEffortType(String variable, String schema) {
        super(WorkEffortType.class, forVariable(variable), schema, "WORK_EFFORT_TYPE");
        addMetadata();
    }

    public QWorkEffortType(Path<? extends WorkEffortType> path) {
        super(path.getType(), path.getMetadata(), "null", "WORK_EFFORT_TYPE");
        addMetadata();
    }

    public QWorkEffortType(PathMetadata metadata) {
        super(WorkEffortType.class, metadata, "null", "WORK_EFFORT_TYPE");
        addMetadata();
    }

    public void addMetadata() {
        addMetadata(allOrgAssigned, ColumnMetadata.named("ALL_ORG_ASSIGNED").withIndex(20).ofType(Types.CHAR).withSize(1));
        addMetadata(allRolesAssigned, ColumnMetadata.named("ALL_ROLES_ASSIGNED").withIndex(19).ofType(Types.CHAR).withSize(1));
        addMetadata(applyScoreRange, ColumnMetadata.named("APPLY_SCORE_RANGE").withIndex(73).ofType(Types.CHAR).withSize(1));
        addMetadata(childTemplateId, ColumnMetadata.named("CHILD_TEMPLATE_ID").withIndex(21).ofType(Types.VARCHAR).withSize(20));
        addMetadata(codeCounter, ColumnMetadata.named("CODE_COUNTER").withIndex(14).ofType(Types.VARCHAR).withSize(20));
        addMetadata(codePrefix, ColumnMetadata.named("CODE_PREFIX").withIndex(13).ofType(Types.VARCHAR).withSize(20));
        addMetadata(copy, ColumnMetadata.named("COPY").withIndex(70).ofType(Types.CHAR).withSize(1));
        addMetadata(createdByUserLogin, ColumnMetadata.named("CREATED_BY_USER_LOGIN").withIndex(76).ofType(Types.VARCHAR).withSize(250));
        addMetadata(createdStamp, ColumnMetadata.named("CREATED_STAMP").withIndex(7).ofType(Types.TIMESTAMP).withSize(19));
        addMetadata(createdTxStamp, ColumnMetadata.named("CREATED_TX_STAMP").withIndex(8).ofType(Types.TIMESTAMP).withSize(19));
        addMetadata(description, ColumnMetadata.named("DESCRIPTION").withIndex(4).ofType(Types.VARCHAR).withSize(255));
        addMetadata(descriptionLang, ColumnMetadata.named("DESCRIPTION_LANG").withIndex(86).ofType(Types.VARCHAR).withSize(255));
        addMetadata(effortUomId, ColumnMetadata.named("EFFORT_UOM_ID").withIndex(11).ofType(Types.VARCHAR).withSize(20));
        addMetadata(enableMultiYearFlag, ColumnMetadata.named("ENABLE_MULTI_YEAR_FLAG").withIndex(74).ofType(Types.CHAR).withSize(1));
        addMetadata(enableSnapshot, ColumnMetadata.named("ENABLE_SNAPSHOT").withIndex(69).ofType(Types.CHAR).withSize(1));
        addMetadata(etch, ColumnMetadata.named("ETCH").withIndex(57).ofType(Types.VARCHAR).withSize(255));
        addMetadata(etchLang, ColumnMetadata.named("ETCH_LANG").withIndex(87).ofType(Types.VARCHAR).withSize(255));
        addMetadata(evalEnumId, ColumnMetadata.named("EVAL_ENUM_ID").withIndex(72).ofType(Types.VARCHAR).withSize(20));
        addMetadata(forAllUsers, ColumnMetadata.named("FOR_ALL_USERS").withIndex(78).ofType(Types.CHAR).withSize(1));
        addMetadata(frameEnumId, ColumnMetadata.named("FRAME_ENUM_ID").withIndex(60).ofType(Types.VARCHAR).withSize(20));
        addMetadata(fromDate, ColumnMetadata.named("FROM_DATE").withIndex(22).ofType(Types.TIMESTAMP).withSize(19));
        addMetadata(hasPersonFilter, ColumnMetadata.named("HAS_PERSON_FILTER").withIndex(65).ofType(Types.CHAR).withSize(1));
        addMetadata(hasProduct, ColumnMetadata.named("HAS_PRODUCT").withIndex(64).ofType(Types.CHAR).withSize(1));
        addMetadata(hasTable, ColumnMetadata.named("HAS_TABLE").withIndex(3).ofType(Types.CHAR).withSize(1));
        addMetadata(hierarchyAssocTypeId, ColumnMetadata.named("HIERARCHY_ASSOC_TYPE_ID").withIndex(24).ofType(Types.VARCHAR).withSize(20));
        addMetadata(iconContentId, ColumnMetadata.named("ICON_CONTENT_ID").withIndex(10).ofType(Types.VARCHAR).withSize(20));
        addMetadata(influenceCatalogId, ColumnMetadata.named("INFLUENCE_CATALOG_ID").withIndex(9).ofType(Types.VARCHAR).withSize(20));
        addMetadata(isIndicAuto, ColumnMetadata.named("IS_INDIC_AUTO").withIndex(59).ofType(Types.CHAR).withSize(1));
        addMetadata(isInOnlyOneCard, ColumnMetadata.named("IS_IN_ONLY_ONE_CARD").withIndex(18).ofType(Types.CHAR).withSize(1));
        addMetadata(isRoleTypeIdAuto, ColumnMetadata.named("IS_ROLE_TYPE_ID_AUTO").withIndex(79).ofType(Types.CHAR).withSize(1));
        addMetadata(isRoot, ColumnMetadata.named("IS_ROOT").withIndex(16).ofType(Types.CHAR).withSize(1));
        addMetadata(isRootActive, ColumnMetadata.named("IS_ROOT_ACTIVE").withIndex(90).ofType(Types.CHAR).withSize(1));
        addMetadata(isTemplate, ColumnMetadata.named("IS_TEMPLATE").withIndex(15).ofType(Types.CHAR).withSize(1));
        addMetadata(lastModifiedByUserLogin, ColumnMetadata.named("LAST_MODIFIED_BY_USER_LOGIN").withIndex(75).ofType(Types.VARCHAR).withSize(250));
        addMetadata(lastUpdatedStamp, ColumnMetadata.named("LAST_UPDATED_STAMP").withIndex(5).ofType(Types.TIMESTAMP).withSize(19));
        addMetadata(lastUpdatedTxStamp, ColumnMetadata.named("LAST_UPDATED_TX_STAMP").withIndex(6).ofType(Types.TIMESTAMP).withSize(19));
        addMetadata(localNameContentId, ColumnMetadata.named("LOCAL_NAME_CONTENT_ID").withIndex(17).ofType(Types.VARCHAR).withSize(20));
        addMetadata(max1, ColumnMetadata.named("MAX1").withIndex(43).ofType(Types.DECIMAL).withSize(20));
        addMetadata(max2, ColumnMetadata.named("MAX2").withIndex(44).ofType(Types.DECIMAL).withSize(20));
        addMetadata(max3, ColumnMetadata.named("MAX3").withIndex(45).ofType(Types.DECIMAL).withSize(20));
        addMetadata(max4, ColumnMetadata.named("MAX4").withIndex(46).ofType(Types.DECIMAL).withSize(20));
        addMetadata(max5, ColumnMetadata.named("MAX5").withIndex(47).ofType(Types.DECIMAL).withSize(20));
        addMetadata(min1, ColumnMetadata.named("MIN1").withIndex(38).ofType(Types.DECIMAL).withSize(20));
        addMetadata(min2, ColumnMetadata.named("MIN2").withIndex(39).ofType(Types.DECIMAL).withSize(20));
        addMetadata(min3, ColumnMetadata.named("MIN3").withIndex(40).ofType(Types.DECIMAL).withSize(20));
        addMetadata(min4, ColumnMetadata.named("MIN4").withIndex(41).ofType(Types.DECIMAL).withSize(20));
        addMetadata(min5, ColumnMetadata.named("MIN5").withIndex(42).ofType(Types.DECIMAL).withSize(20));
        addMetadata(note, ColumnMetadata.named("NOTE").withIndex(12).ofType(Types.LONGVARCHAR).withSize(2147483647));
        addMetadata(noteLang, ColumnMetadata.named("NOTE_LANG").withIndex(88).ofType(Types.LONGVARCHAR).withSize(2147483647));
        addMetadata(orgUnitRoleTypeId, ColumnMetadata.named("ORG_UNIT_ROLE_TYPE_ID").withIndex(77).ofType(Types.VARCHAR).withSize(20));
        addMetadata(parentPeriodFilter, ColumnMetadata.named("PARENT_PERIOD_FILTER").withIndex(82).ofType(Types.CHAR).withSize(1));
        addMetadata(parentTypeId, ColumnMetadata.named("PARENT_TYPE_ID").withIndex(2).ofType(Types.VARCHAR).withSize(20));
        addMetadata(partyRelTypeIdAuto, ColumnMetadata.named("PARTY_REL_TYPE_ID_AUTO").withIndex(81).ofType(Types.VARCHAR).withSize(20));
        addMetadata(periodOpenEnumId, ColumnMetadata.named("PERIOD_OPEN_ENUM_ID").withIndex(83).ofType(Types.VARCHAR).withSize(20));
        addMetadata(periodTypeId, ColumnMetadata.named("PERIOD_TYPE_ID").withIndex(28).ofType(Types.VARCHAR).withSize(20));
        addMetadata(purposeEtch, ColumnMetadata.named("PURPOSE_ETCH").withIndex(71).ofType(Types.VARCHAR).withSize(255));
        addMetadata(purposeEtchLang, ColumnMetadata.named("PURPOSE_ETCH_LANG").withIndex(89).ofType(Types.VARCHAR).withSize(255));
        addMetadata(reminderActive, ColumnMetadata.named("REMINDER_ACTIVE").withIndex(91).ofType(Types.CHAR).withSize(1));
        addMetadata(roleTypeIdAuto, ColumnMetadata.named("ROLE_TYPE_ID_AUTO").withIndex(80).ofType(Types.VARCHAR).withSize(20));
        addMetadata(ruleTypeId1, ColumnMetadata.named("RULE_TYPE_ID1").withIndex(33).ofType(Types.VARCHAR).withSize(20));
        addMetadata(ruleTypeId2, ColumnMetadata.named("RULE_TYPE_ID2").withIndex(34).ofType(Types.VARCHAR).withSize(20));
        addMetadata(ruleTypeId3, ColumnMetadata.named("RULE_TYPE_ID3").withIndex(35).ofType(Types.VARCHAR).withSize(20));
        addMetadata(ruleTypeId4, ColumnMetadata.named("RULE_TYPE_ID4").withIndex(36).ofType(Types.VARCHAR).withSize(20));
        addMetadata(ruleTypeId5, ColumnMetadata.named("RULE_TYPE_ID5").withIndex(37).ofType(Types.VARCHAR).withSize(20));
        addMetadata(scorePeriodEnumId, ColumnMetadata.named("SCORE_PERIOD_ENUM_ID").withIndex(85).ofType(Types.VARCHAR).withSize(20));
        addMetadata(seqDigit, ColumnMetadata.named("SEQ_DIGIT").withIndex(61).ofType(Types.DECIMAL).withSize(20));
        addMetadata(seqOnlyId, ColumnMetadata.named("SEQ_ONLY_ID").withIndex(62).ofType(Types.CHAR).withSize(1));
        addMetadata(shortLabel, ColumnMetadata.named("SHORT_LABEL").withIndex(58).ofType(Types.VARCHAR).withSize(20));
        addMetadata(showHierarchy, ColumnMetadata.named("SHOW_HIERARCHY").withIndex(84).ofType(Types.CHAR).withSize(1));
        addMetadata(showScorekpi, ColumnMetadata.named("SHOW_SCOREKPI").withIndex(68).ofType(Types.CHAR).withSize(1));
        addMetadata(thruDate, ColumnMetadata.named("THRU_DATE").withIndex(23).ofType(Types.TIMESTAMP).withSize(19));
        addMetadata(totalEnumIdAssoc, ColumnMetadata.named("TOTAL_ENUM_ID_ASSOC").withIndex(53).ofType(Types.VARCHAR).withSize(20));
        addMetadata(totalEnumIdKpi, ColumnMetadata.named("TOTAL_ENUM_ID_KPI").withIndex(51).ofType(Types.VARCHAR).withSize(20));
        addMetadata(totalEnumIdSons, ColumnMetadata.named("TOTAL_ENUM_ID_SONS").withIndex(52).ofType(Types.VARCHAR).withSize(20));
        addMetadata(typeId1, ColumnMetadata.named("TYPE_ID1").withIndex(29).ofType(Types.VARCHAR).withSize(20));
        addMetadata(typeId2, ColumnMetadata.named("TYPE_ID2").withIndex(30).ofType(Types.VARCHAR).withSize(20));
        addMetadata(typeId3, ColumnMetadata.named("TYPE_ID3").withIndex(31).ofType(Types.VARCHAR).withSize(20));
        addMetadata(typeId4, ColumnMetadata.named("TYPE_ID4").withIndex(32).ofType(Types.VARCHAR).withSize(20));
        addMetadata(uomRangeScoreId, ColumnMetadata.named("UOM_RANGE_SCORE_ID").withIndex(26).ofType(Types.VARCHAR).withSize(20));
        addMetadata(weightAssocWorkEffort, ColumnMetadata.named("WEIGHT_ASSOC_WORK_EFFORT").withIndex(50).ofType(Types.DECIMAL).withSize(18).withDigits(6));
        addMetadata(weightControlSum, ColumnMetadata.named("WEIGHT_CONTROL_SUM").withIndex(25).ofType(Types.DECIMAL).withSize(18).withDigits(6));
        addMetadata(weightKpi, ColumnMetadata.named("WEIGHT_KPI").withIndex(48).ofType(Types.DECIMAL).withSize(18).withDigits(6));
        addMetadata(weightKpiControlSum, ColumnMetadata.named("WEIGHT_KPI_CONTROL_SUM").withIndex(66).ofType(Types.DECIMAL).withSize(18).withDigits(6));
        addMetadata(weightSons, ColumnMetadata.named("WEIGHT_SONS").withIndex(49).ofType(Types.DECIMAL).withSize(18).withDigits(6));
        addMetadata(weLayoutTypeEnumId, ColumnMetadata.named("WE_LAYOUT_TYPE_ENUM_ID").withIndex(27).ofType(Types.VARCHAR).withSize(20));
        addMetadata(wePurposeTypeIdInd, ColumnMetadata.named("WE_PURPOSE_TYPE_ID_IND").withIndex(56).ofType(Types.VARCHAR).withSize(20));
        addMetadata(wePurposeTypeIdRes, ColumnMetadata.named("WE_PURPOSE_TYPE_ID_RES").withIndex(55).ofType(Types.VARCHAR).withSize(20));
        addMetadata(wePurposeTypeIdWe, ColumnMetadata.named("WE_PURPOSE_TYPE_ID_WE").withIndex(63).ofType(Types.VARCHAR).withSize(20));
        addMetadata(workEffortAssocTypeId, ColumnMetadata.named("WORK_EFFORT_ASSOC_TYPE_ID").withIndex(54).ofType(Types.VARCHAR).withSize(20));
        addMetadata(workEffortTypeHierarchyId, ColumnMetadata.named("WORK_EFFORT_TYPE_HIERARCHY_ID").withIndex(67).ofType(Types.VARCHAR).withSize(20));
        addMetadata(workEffortTypeId, ColumnMetadata.named("WORK_EFFORT_TYPE_ID").withIndex(1).ofType(Types.VARCHAR).withSize(20).notNull());
    }

}

