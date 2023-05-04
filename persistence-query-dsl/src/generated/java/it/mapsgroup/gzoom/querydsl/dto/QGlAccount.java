package it.mapsgroup.gzoom.querydsl.dto;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;

import com.querydsl.sql.ColumnMetadata;
import java.sql.Types;




/**
 * QGlAccount is a Querydsl query type for GlAccount
 */
@Generated("com.querydsl.sql.codegen.MetaDataSerializer")
public class QGlAccount extends com.querydsl.sql.RelationalPathBase<GlAccount> {

    private static final long serialVersionUID = -1514357347;

    public static final QGlAccount glAccount = new QGlAccount("GL_ACCOUNT");

    public final StringPath accountCode = createString("accountCode");

    public final StringPath accountName = createString("accountName");

    public final StringPath accountNameLang = createString("accountNameLang");

    public final StringPath accountTypeEnumId = createString("accountTypeEnumId");

    public final StringPath calcCustomMethodId = createString("calcCustomMethodId");

    public final StringPath childFolderFile = createString("childFolderFile");

    public final StringPath createdByUserLogin = createString("createdByUserLogin");

    public final DateTimePath<java.time.LocalDateTime> createdStamp = createDateTime("createdStamp", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> createdTxStamp = createDateTime("createdTxStamp", java.time.LocalDateTime.class);

    public final StringPath currentStatusId = createString("currentStatusId");

    public final StringPath dataSourceId = createString("dataSourceId");

    public final StringPath debitCreditDefault = createString("debitCreditDefault");

    public final StringPath defaultUomId = createString("defaultUomId");

    public final StringPath description = createString("description");

    public final StringPath descriptionLang = createString("descriptionLang");

    public final StringPath detailEnumId = createString("detailEnumId");

    public final BooleanPath detectOrgUnitIdFlag = createBoolean("detectOrgUnitIdFlag");

    public final StringPath emplPositionTypeId = createString("emplPositionTypeId");

    public final StringPath externalId = createString("externalId");

    public final DateTimePath<java.time.LocalDateTime> fromDate = createDateTime("fromDate", java.time.LocalDateTime.class);

    public final StringPath glAccountClassId = createString("glAccountClassId");

    public final StringPath glAccountId = createString("glAccountId");

    public final StringPath glAccountTypeId = createString("glAccountTypeId");

    public final StringPath glResourceTypeId = createString("glResourceTypeId");

    public final StringPath glXbrlClassId = createString("glXbrlClassId");

    public final BooleanPath hasCompetence = createBoolean("hasCompetence");

    public final StringPath inputEnumId = createString("inputEnumId");

    public final StringPath lastModifiedByUserLogin = createString("lastModifiedByUserLogin");

    public final DateTimePath<java.time.LocalDateTime> lastUpdatedStamp = createDateTime("lastUpdatedStamp", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> lastUpdatedTxStamp = createDateTime("lastUpdatedTxStamp", java.time.LocalDateTime.class);

    public final StringPath localNameContentId = createString("localNameContentId");

    public final StringPath parentGlAccountId = createString("parentGlAccountId");

    public final StringPath periodicalAbsoluteEnumId = createString("periodicalAbsoluteEnumId");

    public final StringPath periodTypeId = createString("periodTypeId");

    public final NumberPath<java.math.BigDecimal> postedBalance = createNumber("postedBalance", java.math.BigDecimal.class);

    public final NumberPath<java.math.BigInteger> prioCalc = createNumber("prioCalc", java.math.BigInteger.class);

    public final StringPath productId = createString("productId");

    public final StringPath referencedAccountId = createString("referencedAccountId");

    public final StringPath respCenterId = createString("respCenterId");

    public final StringPath respCenterRoleTypeId = createString("respCenterRoleTypeId");

    public final StringPath roleTypeId = createString("roleTypeId");

    public final NumberPath<java.math.BigInteger> sequenceId = createNumber("sequenceId", java.math.BigInteger.class);

    public final StringPath source = createString("source");

    public final StringPath sourceLang = createString("sourceLang");

    public final StringPath targetPeriodEnumId = createString("targetPeriodEnumId");

    public final DateTimePath<java.time.LocalDateTime> thruDate = createDateTime("thruDate", java.time.LocalDateTime.class);

    public final StringPath trendEnumId = createString("trendEnumId");

    public final StringPath uomRangeId = createString("uomRangeId");

    public final StringPath weAlertRuleEnumId = createString("weAlertRuleEnumId");

    public final StringPath weMeasureTypeEnumId = createString("weMeasureTypeEnumId");

    public final StringPath weScoreConvEnumId = createString("weScoreConvEnumId");

    public final StringPath weScoreRangeEnumId = createString("weScoreRangeEnumId");

    public final StringPath weWithoutPerf = createString("weWithoutPerf");

    public final StringPath weWithoutTarget = createString("weWithoutTarget");

    public final com.querydsl.sql.PrimaryKey<GlAccount> primary = createPrimaryKey(glAccountId);

    public final com.querydsl.sql.ForeignKey<Enumeration> wemWmt = createForeignKey(weMeasureTypeEnumId, "ENUM_ID");

    public final com.querydsl.sql.ForeignKey<Enumeration> wemAlru = createForeignKey(weAlertRuleEnumId, "ENUM_ID");

    public final com.querydsl.sql.ForeignKey<Enumeration> glaEieFk = createForeignKey(inputEnumId, "ENUM_ID");

    public final com.querydsl.sql.ForeignKey<EmplPositionType> gaEmpl = createForeignKey(emplPositionTypeId, "EMPL_POSITION_TYPE_ID");

    public final com.querydsl.sql.ForeignKey<Enumeration> wemScrn = createForeignKey(weScoreRangeEnumId, "ENUM_ID");

    public final com.querydsl.sql.ForeignKey<Enumeration> glaWotaFk = createForeignKey(weWithoutTarget, "ENUM_ID");

    public final com.querydsl.sql.ForeignKey<Enumeration> wemNoperf = createForeignKey(weWithoutPerf, "ENUM_ID");

    public final com.querydsl.sql.ForeignKey<Enumeration> gaPerabsenum = createForeignKey(periodicalAbsoluteEnumId, "ENUM_ID");

    public final com.querydsl.sql.ForeignKey<Enumeration> gaTrend = createForeignKey(trendEnumId, "ENUM_ID");

    public final com.querydsl.sql.ForeignKey<RoleType> rtCrrFk = createForeignKey(respCenterRoleTypeId, "ROLE_TYPE_ID");

    public final com.querydsl.sql.ForeignKey<Enumeration> glaEFk = createForeignKey(detailEnumId, "ENUM_ID");

    public final com.querydsl.sql.ForeignKey<Uom> gaDftuom = createForeignKey(defaultUomId, "UOM_ID");

    public final com.querydsl.sql.ForeignKey<Party> ptyCrFk = createForeignKey(respCenterId, "PARTY_ID");

    public final com.querydsl.sql.ForeignKey<Enumeration> gaDbtcrd = createForeignKey(debitCreditDefault, "ENUM_ID");

    public final com.querydsl.sql.ForeignKey<GlAccount> glacctPar = createForeignKey(parentGlAccountId, "GL_ACCOUNT_ID");

    public final com.querydsl.sql.ForeignKey<Enumeration> gaTypeenum = createForeignKey(accountTypeEnumId, "ENUM_ID");

    public final com.querydsl.sql.ForeignKey<Enumeration> wemCvn = createForeignKey(weScoreConvEnumId, "ENUM_ID");

    public final com.querydsl.sql.ForeignKey<Enumeration> glaTaperFk = createForeignKey(targetPeriodEnumId, "ENUM_ID");

    public final com.querydsl.sql.ForeignKey<GlAccount> _glacctPar = createInvForeignKey(glAccountId, "PARENT_GL_ACCOUNT_ID");

    public final com.querydsl.sql.ForeignKey<WorkEffortMeasure> _wemAccount = createInvForeignKey(glAccountId, "GL_ACCOUNT_ID");

    public final com.querydsl.sql.ForeignKey<AcctgTransEntry> _accttxentGlact = createInvForeignKey(glAccountId, "GL_ACCOUNT_ID");

    public final com.querydsl.sql.ForeignKey<AcctgTransEntry> _ateGlacc = createInvForeignKey(glAccountId, "GL_ACCOUNT_FIN_ID");

    public QGlAccount(String variable) {
        super(GlAccount.class, forVariable(variable), "null", "GL_ACCOUNT");
        addMetadata();
    }

    public QGlAccount(String variable, String schema, String table) {
        super(GlAccount.class, forVariable(variable), schema, table);
        addMetadata();
    }

    public QGlAccount(String variable, String schema) {
        super(GlAccount.class, forVariable(variable), schema, "GL_ACCOUNT");
        addMetadata();
    }

    public QGlAccount(Path<? extends GlAccount> path) {
        super(path.getType(), path.getMetadata(), "null", "GL_ACCOUNT");
        addMetadata();
    }

    public QGlAccount(PathMetadata metadata) {
        super(GlAccount.class, metadata, "null", "GL_ACCOUNT");
        addMetadata();
    }

    public void addMetadata() {
        addMetadata(accountCode, ColumnMetadata.named("ACCOUNT_CODE").withIndex(7).ofType(Types.VARCHAR).withSize(100));
        addMetadata(accountName, ColumnMetadata.named("ACCOUNT_NAME").withIndex(8).ofType(Types.VARCHAR).withSize(2000));
        addMetadata(accountNameLang, ColumnMetadata.named("ACCOUNT_NAME_LANG").withIndex(46).ofType(Types.VARCHAR).withSize(2000));
        addMetadata(accountTypeEnumId, ColumnMetadata.named("ACCOUNT_TYPE_ENUM_ID").withIndex(21).ofType(Types.VARCHAR).withSize(20));
        addMetadata(calcCustomMethodId, ColumnMetadata.named("CALC_CUSTOM_METHOD_ID").withIndex(33).ofType(Types.VARCHAR).withSize(20));
        addMetadata(childFolderFile, ColumnMetadata.named("CHILD_FOLDER_FILE").withIndex(23).ofType(Types.VARCHAR).withSize(10));
        addMetadata(createdByUserLogin, ColumnMetadata.named("CREATED_BY_USER_LOGIN").withIndex(54).ofType(Types.VARCHAR).withSize(250));
        addMetadata(createdStamp, ColumnMetadata.named("CREATED_STAMP").withIndex(15).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(createdTxStamp, ColumnMetadata.named("CREATED_TX_STAMP").withIndex(16).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(currentStatusId, ColumnMetadata.named("CURRENT_STATUS_ID").withIndex(26).ofType(Types.VARCHAR).withSize(20));
        addMetadata(dataSourceId, ColumnMetadata.named("DATA_SOURCE_ID").withIndex(31).ofType(Types.VARCHAR).withSize(20));
        addMetadata(debitCreditDefault, ColumnMetadata.named("DEBIT_CREDIT_DEFAULT").withIndex(17).ofType(Types.VARCHAR).withSize(20));
        addMetadata(defaultUomId, ColumnMetadata.named("DEFAULT_UOM_ID").withIndex(18).ofType(Types.VARCHAR).withSize(20));
        addMetadata(description, ColumnMetadata.named("DESCRIPTION").withIndex(9).ofType(Types.VARCHAR).withSize(2000));
        addMetadata(descriptionLang, ColumnMetadata.named("DESCRIPTION_LANG").withIndex(47).ofType(Types.VARCHAR).withSize(2000));
        addMetadata(detailEnumId, ColumnMetadata.named("DETAIL_ENUM_ID").withIndex(32).ofType(Types.VARCHAR).withSize(20));
        addMetadata(detectOrgUnitIdFlag, ColumnMetadata.named("DETECT_ORG_UNIT_ID_FLAG").withIndex(25).ofType(Types.CHAR).withSize(1));
        addMetadata(emplPositionTypeId, ColumnMetadata.named("EMPL_POSITION_TYPE_ID").withIndex(45).ofType(Types.VARCHAR).withSize(20));
        addMetadata(externalId, ColumnMetadata.named("EXTERNAL_ID").withIndex(11).ofType(Types.VARCHAR).withSize(20));
        addMetadata(fromDate, ColumnMetadata.named("FROM_DATE").withIndex(29).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(glAccountClassId, ColumnMetadata.named("GL_ACCOUNT_CLASS_ID").withIndex(3).ofType(Types.VARCHAR).withSize(20));
        addMetadata(glAccountId, ColumnMetadata.named("GL_ACCOUNT_ID").withIndex(1).ofType(Types.VARCHAR).withSize(20).notNull());
        addMetadata(glAccountTypeId, ColumnMetadata.named("GL_ACCOUNT_TYPE_ID").withIndex(2).ofType(Types.VARCHAR).withSize(20));
        addMetadata(glResourceTypeId, ColumnMetadata.named("GL_RESOURCE_TYPE_ID").withIndex(4).ofType(Types.VARCHAR).withSize(20));
        addMetadata(glXbrlClassId, ColumnMetadata.named("GL_XBRL_CLASS_ID").withIndex(5).ofType(Types.VARCHAR).withSize(20));
        addMetadata(hasCompetence, ColumnMetadata.named("HAS_COMPETENCE").withIndex(24).ofType(Types.CHAR).withSize(1));
        addMetadata(inputEnumId, ColumnMetadata.named("INPUT_ENUM_ID").withIndex(27).ofType(Types.VARCHAR).withSize(20));
        addMetadata(lastModifiedByUserLogin, ColumnMetadata.named("LAST_MODIFIED_BY_USER_LOGIN").withIndex(53).ofType(Types.VARCHAR).withSize(250));
        addMetadata(lastUpdatedStamp, ColumnMetadata.named("LAST_UPDATED_STAMP").withIndex(13).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(lastUpdatedTxStamp, ColumnMetadata.named("LAST_UPDATED_TX_STAMP").withIndex(14).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(localNameContentId, ColumnMetadata.named("LOCAL_NAME_CONTENT_ID").withIndex(22).ofType(Types.VARCHAR).withSize(20));
        addMetadata(parentGlAccountId, ColumnMetadata.named("PARENT_GL_ACCOUNT_ID").withIndex(6).ofType(Types.VARCHAR).withSize(20));
        addMetadata(periodicalAbsoluteEnumId, ColumnMetadata.named("PERIODICAL_ABSOLUTE_ENUM_ID").withIndex(19).ofType(Types.VARCHAR).withSize(20));
        addMetadata(periodTypeId, ColumnMetadata.named("PERIOD_TYPE_ID").withIndex(28).ofType(Types.VARCHAR).withSize(20));
        addMetadata(postedBalance, ColumnMetadata.named("POSTED_BALANCE").withIndex(12).ofType(Types.DECIMAL).withSize(18).withDigits(2));
        addMetadata(prioCalc, ColumnMetadata.named("PRIO_CALC").withIndex(34).ofType(Types.DECIMAL).withSize(20));
        addMetadata(productId, ColumnMetadata.named("PRODUCT_ID").withIndex(10).ofType(Types.VARCHAR).withSize(20));
        addMetadata(referencedAccountId, ColumnMetadata.named("REFERENCED_ACCOUNT_ID").withIndex(20).ofType(Types.VARCHAR).withSize(20));
        addMetadata(respCenterId, ColumnMetadata.named("RESP_CENTER_ID").withIndex(43).ofType(Types.VARCHAR).withSize(20));
        addMetadata(respCenterRoleTypeId, ColumnMetadata.named("RESP_CENTER_ROLE_TYPE_ID").withIndex(44).ofType(Types.VARCHAR).withSize(20));
        addMetadata(roleTypeId, ColumnMetadata.named("ROLE_TYPE_ID").withIndex(49).ofType(Types.VARCHAR).withSize(20));
        addMetadata(sequenceId, ColumnMetadata.named("SEQUENCE_ID").withIndex(50).ofType(Types.DECIMAL).withSize(20));
        addMetadata(source, ColumnMetadata.named("SOURCE").withIndex(41).ofType(Types.VARCHAR).withSize(255));
        addMetadata(sourceLang, ColumnMetadata.named("SOURCE_LANG").withIndex(48).ofType(Types.VARCHAR).withSize(255));
        addMetadata(targetPeriodEnumId, ColumnMetadata.named("TARGET_PERIOD_ENUM_ID").withIndex(51).ofType(Types.VARCHAR).withSize(20));
        addMetadata(thruDate, ColumnMetadata.named("THRU_DATE").withIndex(30).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(trendEnumId, ColumnMetadata.named("TREND_ENUM_ID").withIndex(42).ofType(Types.VARCHAR).withSize(20));
        addMetadata(uomRangeId, ColumnMetadata.named("UOM_RANGE_ID").withIndex(39).ofType(Types.VARCHAR).withSize(20));
        addMetadata(weAlertRuleEnumId, ColumnMetadata.named("WE_ALERT_RULE_ENUM_ID").withIndex(38).ofType(Types.VARCHAR).withSize(20));
        addMetadata(weMeasureTypeEnumId, ColumnMetadata.named("WE_MEASURE_TYPE_ENUM_ID").withIndex(35).ofType(Types.VARCHAR).withSize(20));
        addMetadata(weScoreConvEnumId, ColumnMetadata.named("WE_SCORE_CONV_ENUM_ID").withIndex(37).ofType(Types.VARCHAR).withSize(20));
        addMetadata(weScoreRangeEnumId, ColumnMetadata.named("WE_SCORE_RANGE_ENUM_ID").withIndex(36).ofType(Types.VARCHAR).withSize(20));
        addMetadata(weWithoutPerf, ColumnMetadata.named("WE_WITHOUT_PERF").withIndex(40).ofType(Types.VARCHAR).withSize(20));
        addMetadata(weWithoutTarget, ColumnMetadata.named("WE_WITHOUT_TARGET").withIndex(52).ofType(Types.VARCHAR).withSize(20));
    }

}

