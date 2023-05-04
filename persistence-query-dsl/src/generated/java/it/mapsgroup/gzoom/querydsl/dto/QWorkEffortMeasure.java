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
 * QWorkEffortMeasure is a Querydsl query type for WorkEffortMeasure
 */
@Generated("com.querydsl.sql.codegen.MetaDataSerializer")
public class QWorkEffortMeasure extends com.querydsl.sql.RelationalPathBase<WorkEffortMeasure> {

    private static final long serialVersionUID = 366478934;

    public static final QWorkEffortMeasure workEffortMeasure = new QWorkEffortMeasure("WORK_EFFORT_MEASURE");

    public final StringPath comments = createString("comments");

    public final StringPath commentsLang = createString("commentsLang");

    public final StringPath createdByUserLogin = createString("createdByUserLogin");

    public final DateTimePath<java.time.LocalDateTime> createdStamp = createDateTime("createdStamp", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> createdTxStamp = createDateTime("createdTxStamp", java.time.LocalDateTime.class);

    public final StringPath currentStatusId = createString("currentStatusId");

    public final StringPath dataSourceId = createString("dataSourceId");

    public final StringPath detailEnumId = createString("detailEnumId");

    public final StringPath emplPositionTypeId = createString("emplPositionTypeId");

    public final DateTimePath<java.time.LocalDateTime> fromDate = createDateTime("fromDate", java.time.LocalDateTime.class);

    public final StringPath glAccountId = createString("glAccountId");

    public final StringPath glFiscalTypeEnumId = createString("glFiscalTypeEnumId");

    public final BooleanPath isInvisible = createBoolean("isInvisible");

    public final BooleanPath isPosted = createBoolean("isPosted");

    public final NumberPath<java.math.BigDecimal> kpiOtherWeight = createNumber("kpiOtherWeight", java.math.BigDecimal.class);

    public final NumberPath<java.math.BigDecimal> kpiScoreWeight = createNumber("kpiScoreWeight", java.math.BigDecimal.class);

    public final StringPath lastModifiedByUserLogin = createString("lastModifiedByUserLogin");

    public final DateTimePath<java.time.LocalDateTime> lastUpdatedStamp = createDateTime("lastUpdatedStamp", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> lastUpdatedTxStamp = createDateTime("lastUpdatedTxStamp", java.time.LocalDateTime.class);

    public final StringPath orgUnitId = createString("orgUnitId");

    public final StringPath orgUnitRoleTypeId = createString("orgUnitRoleTypeId");

    public final StringPath otherWorkEffortId = createString("otherWorkEffortId");

    public final StringPath partyId = createString("partyId");

    public final StringPath periodTypeId = createString("periodTypeId");

    public final StringPath productId = createString("productId");

    public final StringPath roleTypeId = createString("roleTypeId");

    public final NumberPath<java.math.BigInteger> sequenceId = createNumber("sequenceId", java.math.BigInteger.class);

    public final StringPath source = createString("source");

    public final DateTimePath<java.time.LocalDateTime> thruDate = createDateTime("thruDate", java.time.LocalDateTime.class);

    public final StringPath uomDescr = createString("uomDescr");

    public final StringPath uomDescrLang = createString("uomDescrLang");

    public final StringPath uomRangeId = createString("uomRangeId");

    public final StringPath weAlertRuleEnumId = createString("weAlertRuleEnumId");

    public final StringPath weMeasureTypeEnumId = createString("weMeasureTypeEnumId");

    public final StringPath weOtherGoalEnumId = createString("weOtherGoalEnumId");

    public final StringPath weScoreConvEnumId = createString("weScoreConvEnumId");

    public final StringPath weScoreRangeEnumId = createString("weScoreRangeEnumId");

    public final StringPath weWithoutPerf = createString("weWithoutPerf");

    public final StringPath workEffortId = createString("workEffortId");

    public final StringPath workEffortInfluenceId = createString("workEffortInfluenceId");

    public final StringPath workEffortMeasureId = createString("workEffortMeasureId");

    public final com.querydsl.sql.PrimaryKey<WorkEffortMeasure> primary = createPrimaryKey(workEffortMeasureId);

    public final com.querydsl.sql.ForeignKey<Enumeration> wemOmg = createForeignKey(weOtherGoalEnumId, "ENUM_ID");

    public final com.querydsl.sql.ForeignKey<PartyRole> wmOuFk = createForeignKey(Arrays.asList(orgUnitId, orgUnitRoleTypeId), Arrays.asList("PARTY_ID", "ROLE_TYPE_ID"));

    public final com.querydsl.sql.ForeignKey<Enumeration> wemDeFk = createForeignKey(detailEnumId, "ENUM_ID");

    public final com.querydsl.sql.ForeignKey<PartyRole> wemParrl = createForeignKey(Arrays.asList(partyId, roleTypeId), Arrays.asList("PARTY_ID", "ROLE_TYPE_ID"));

    public final com.querydsl.sql.ForeignKey<EmplPositionType> wemEmppt = createForeignKey(emplPositionTypeId, "EMPL_POSITION_TYPE_ID");

    public final com.querydsl.sql.ForeignKey<WorkEffort> wemWkefft = createForeignKey(workEffortId, "WORK_EFFORT_ID");

    public final com.querydsl.sql.ForeignKey<WorkEffort> wemOther = createForeignKey(otherWorkEffortId, "WORK_EFFORT_ID");

    public final com.querydsl.sql.ForeignKey<WorkEffortPartyAssignment> _wepaWem = createInvForeignKey(workEffortMeasureId, "WORK_EFFORT_MEASURE_ID");

    public final com.querydsl.sql.ForeignKey<WorkEffortAssoc> _wkassMea = createInvForeignKey(workEffortMeasureId, "WE_MEASURE_EVAL_ID");

    public QWorkEffortMeasure(String variable) {
        super(WorkEffortMeasure.class, forVariable(variable), "null", "WORK_EFFORT_MEASURE");
        addMetadata();
    }

    public QWorkEffortMeasure(String variable, String schema, String table) {
        super(WorkEffortMeasure.class, forVariable(variable), schema, table);
        addMetadata();
    }

    public QWorkEffortMeasure(String variable, String schema) {
        super(WorkEffortMeasure.class, forVariable(variable), schema, "WORK_EFFORT_MEASURE");
        addMetadata();
    }

    public QWorkEffortMeasure(Path<? extends WorkEffortMeasure> path) {
        super(path.getType(), path.getMetadata(), "null", "WORK_EFFORT_MEASURE");
        addMetadata();
    }

    public QWorkEffortMeasure(PathMetadata metadata) {
        super(WorkEffortMeasure.class, metadata, "null", "WORK_EFFORT_MEASURE");
        addMetadata();
    }

    public void addMetadata() {
        addMetadata(comments, ColumnMetadata.named("COMMENTS").withIndex(19).ofType(Types.VARCHAR).withSize(2000));
        addMetadata(commentsLang, ColumnMetadata.named("COMMENTS_LANG").withIndex(41).ofType(Types.VARCHAR).withSize(2000));
        addMetadata(createdByUserLogin, ColumnMetadata.named("CREATED_BY_USER_LOGIN").withIndex(36).ofType(Types.VARCHAR).withSize(250));
        addMetadata(createdStamp, ColumnMetadata.named("CREATED_STAMP").withIndex(39).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(createdTxStamp, ColumnMetadata.named("CREATED_TX_STAMP").withIndex(40).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(currentStatusId, ColumnMetadata.named("CURRENT_STATUS_ID").withIndex(28).ofType(Types.VARCHAR).withSize(20));
        addMetadata(dataSourceId, ColumnMetadata.named("DATA_SOURCE_ID").withIndex(27).ofType(Types.VARCHAR).withSize(20));
        addMetadata(detailEnumId, ColumnMetadata.named("DETAIL_ENUM_ID").withIndex(26).ofType(Types.VARCHAR).withSize(20));
        addMetadata(emplPositionTypeId, ColumnMetadata.named("EMPL_POSITION_TYPE_ID").withIndex(3).ofType(Types.VARCHAR).withSize(20));
        addMetadata(fromDate, ColumnMetadata.named("FROM_DATE").withIndex(16).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(glAccountId, ColumnMetadata.named("GL_ACCOUNT_ID").withIndex(15).ofType(Types.VARCHAR).withSize(20));
        addMetadata(glFiscalTypeEnumId, ColumnMetadata.named("GL_FISCAL_TYPE_ENUM_ID").withIndex(4).ofType(Types.VARCHAR).withSize(20));
        addMetadata(isInvisible, ColumnMetadata.named("IS_INVISIBLE").withIndex(34).ofType(Types.CHAR).withSize(1));
        addMetadata(isPosted, ColumnMetadata.named("IS_POSTED").withIndex(29).ofType(Types.CHAR).withSize(1));
        addMetadata(kpiOtherWeight, ColumnMetadata.named("KPI_OTHER_WEIGHT").withIndex(33).ofType(Types.DECIMAL).withSize(18).withDigits(6));
        addMetadata(kpiScoreWeight, ColumnMetadata.named("KPI_SCORE_WEIGHT").withIndex(20).ofType(Types.DECIMAL).withSize(18).withDigits(6));
        addMetadata(lastModifiedByUserLogin, ColumnMetadata.named("LAST_MODIFIED_BY_USER_LOGIN").withIndex(35).ofType(Types.VARCHAR).withSize(250));
        addMetadata(lastUpdatedStamp, ColumnMetadata.named("LAST_UPDATED_STAMP").withIndex(37).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(lastUpdatedTxStamp, ColumnMetadata.named("LAST_UPDATED_TX_STAMP").withIndex(38).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(orgUnitId, ColumnMetadata.named("ORG_UNIT_ID").withIndex(25).ofType(Types.VARCHAR).withSize(20));
        addMetadata(orgUnitRoleTypeId, ColumnMetadata.named("ORG_UNIT_ROLE_TYPE_ID").withIndex(24).ofType(Types.VARCHAR).withSize(20));
        addMetadata(otherWorkEffortId, ColumnMetadata.named("OTHER_WORK_EFFORT_ID").withIndex(21).ofType(Types.VARCHAR).withSize(20));
        addMetadata(partyId, ColumnMetadata.named("PARTY_ID").withIndex(5).ofType(Types.VARCHAR).withSize(20));
        addMetadata(periodTypeId, ColumnMetadata.named("PERIOD_TYPE_ID").withIndex(22).ofType(Types.VARCHAR).withSize(20));
        addMetadata(productId, ColumnMetadata.named("PRODUCT_ID").withIndex(2).ofType(Types.VARCHAR).withSize(20));
        addMetadata(roleTypeId, ColumnMetadata.named("ROLE_TYPE_ID").withIndex(6).ofType(Types.VARCHAR).withSize(20));
        addMetadata(sequenceId, ColumnMetadata.named("SEQUENCE_ID").withIndex(31).ofType(Types.DECIMAL).withSize(20));
        addMetadata(source, ColumnMetadata.named("SOURCE").withIndex(30).ofType(Types.VARCHAR).withSize(255));
        addMetadata(thruDate, ColumnMetadata.named("THRU_DATE").withIndex(17).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(uomDescr, ColumnMetadata.named("UOM_DESCR").withIndex(18).ofType(Types.VARCHAR).withSize(2000));
        addMetadata(uomDescrLang, ColumnMetadata.named("UOM_DESCR_LANG").withIndex(32).ofType(Types.VARCHAR).withSize(2000));
        addMetadata(uomRangeId, ColumnMetadata.named("UOM_RANGE_ID").withIndex(12).ofType(Types.VARCHAR).withSize(20));
        addMetadata(weAlertRuleEnumId, ColumnMetadata.named("WE_ALERT_RULE_ENUM_ID").withIndex(10).ofType(Types.VARCHAR).withSize(20));
        addMetadata(weMeasureTypeEnumId, ColumnMetadata.named("WE_MEASURE_TYPE_ENUM_ID").withIndex(7).ofType(Types.VARCHAR).withSize(20));
        addMetadata(weOtherGoalEnumId, ColumnMetadata.named("WE_OTHER_GOAL_ENUM_ID").withIndex(13).ofType(Types.VARCHAR).withSize(20));
        addMetadata(weScoreConvEnumId, ColumnMetadata.named("WE_SCORE_CONV_ENUM_ID").withIndex(9).ofType(Types.VARCHAR).withSize(20));
        addMetadata(weScoreRangeEnumId, ColumnMetadata.named("WE_SCORE_RANGE_ENUM_ID").withIndex(8).ofType(Types.VARCHAR).withSize(20));
        addMetadata(weWithoutPerf, ColumnMetadata.named("WE_WITHOUT_PERF").withIndex(23).ofType(Types.VARCHAR).withSize(20));
        addMetadata(workEffortId, ColumnMetadata.named("WORK_EFFORT_ID").withIndex(14).ofType(Types.VARCHAR).withSize(20));
        addMetadata(workEffortInfluenceId, ColumnMetadata.named("WORK_EFFORT_INFLUENCE_ID").withIndex(11).ofType(Types.VARCHAR).withSize(20));
        addMetadata(workEffortMeasureId, ColumnMetadata.named("WORK_EFFORT_MEASURE_ID").withIndex(1).ofType(Types.VARCHAR).withSize(20).notNull());
    }

}

