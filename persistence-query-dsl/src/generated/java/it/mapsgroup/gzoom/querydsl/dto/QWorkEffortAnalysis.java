package it.mapsgroup.gzoom.querydsl.dto;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;

import com.querydsl.sql.ColumnMetadata;
import java.sql.Types;




/**
 * QWorkEffortAnalysis is a Querydsl query type for WorkEffortAnalysis
 */
@Generated("com.querydsl.sql.codegen.MetaDataSerializer")
public class QWorkEffortAnalysis extends com.querydsl.sql.RelationalPathBase<WorkEffortAnalysis> {

    private static final long serialVersionUID = -1571688220;

    public static final QWorkEffortAnalysis workEffortAnalysis = new QWorkEffortAnalysis("WORK_EFFORT_ANALYSIS");

    public final StringPath availabilityId = createString("availabilityId");

    public final DateTimePath<java.time.LocalDateTime> createdStamp = createDateTime("createdStamp", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> createdTxStamp = createDateTime("createdTxStamp", java.time.LocalDateTime.class);

    public final StringPath dataVisibility = createString("dataVisibility");

    public final StringPath description = createString("description");

    public final StringPath description1 = createString("description1");

    public final StringPath description2 = createString("description2");

    public final StringPath description3 = createString("description3");

    public final StringPath description4 = createString("description4");

    public final StringPath description5 = createString("description5");

    public final BooleanPath excludeValidity = createBoolean("excludeValidity");

    public final BooleanPath isMonitor = createBoolean("isMonitor");

    public final StringPath labelM1Prev = createString("labelM1Prev");

    public final StringPath labelM1Real = createString("labelM1Real");

    public final StringPath labelM2Prev = createString("labelM2Prev");

    public final StringPath labelM2Real = createString("labelM2Real");

    public final StringPath labelM3Prev = createString("labelM3Prev");

    public final StringPath labelM3Real = createString("labelM3Real");

    public final StringPath labelM4Prev = createString("labelM4Prev");

    public final StringPath labelM4Real = createString("labelM4Real");

    public final StringPath labelP1Prev = createString("labelP1Prev");

    public final StringPath labelP1Real = createString("labelP1Real");

    public final StringPath labelP2Prev = createString("labelP2Prev");

    public final StringPath labelP2Real = createString("labelP2Real");

    public final StringPath labelP3Prev = createString("labelP3Prev");

    public final StringPath labelP3Real = createString("labelP3Real");

    public final StringPath labelP4Prev = createString("labelP4Prev");

    public final StringPath labelP4Real = createString("labelP4Real");

    public final StringPath labelPrev = createString("labelPrev");

    public final StringPath labelReal = createString("labelReal");

    public final DateTimePath<java.time.LocalDateTime> lastUpdatedStamp = createDateTime("lastUpdatedStamp", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> lastUpdatedTxStamp = createDateTime("lastUpdatedTxStamp", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> referenceDate = createDateTime("referenceDate", java.time.LocalDateTime.class);

    public final StringPath reportId = createString("reportId");

    public final StringPath typeBalanceConsIndId = createString("typeBalanceConsIndId");

    public final StringPath typeBalanceScoreConId = createString("typeBalanceScoreConId");

    public final StringPath typeBalanceScoreTarId = createString("typeBalanceScoreTarId");

    public final StringPath typeBalanceTarIndId = createString("typeBalanceTarIndId");

    public final StringPath workEffortAnalysisId = createString("workEffortAnalysisId");

    public final StringPath workEffortId = createString("workEffortId");

    public final StringPath workEffortPurposeTypeId = createString("workEffortPurposeTypeId");

    public final StringPath workEffortTypeId = createString("workEffortTypeId");

    public final StringPath workEffortTypeIdSez1 = createString("workEffortTypeIdSez1");

    public final StringPath workEffortTypeIdSez2 = createString("workEffortTypeIdSez2");

    public final StringPath workEffortTypeIdSez3 = createString("workEffortTypeIdSez3");

    public final StringPath workEffortTypeIdSez4 = createString("workEffortTypeIdSez4");

    public final StringPath workEffortTypeIdSez5 = createString("workEffortTypeIdSez5");

    public final StringPath workEffortTypeIdSez6 = createString("workEffortTypeIdSez6");

    public final DateTimePath<java.time.LocalDateTime> yearM1Prev = createDateTime("yearM1Prev", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> yearM1Real = createDateTime("yearM1Real", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> yearM2Prev = createDateTime("yearM2Prev", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> yearM2Real = createDateTime("yearM2Real", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> yearM3Prev = createDateTime("yearM3Prev", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> yearM3Real = createDateTime("yearM3Real", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> yearM4Prev = createDateTime("yearM4Prev", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> yearM4Real = createDateTime("yearM4Real", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> yearP1Prev = createDateTime("yearP1Prev", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> yearP1Real = createDateTime("yearP1Real", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> yearP2Prev = createDateTime("yearP2Prev", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> yearP2Real = createDateTime("yearP2Real", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> yearP3Prev = createDateTime("yearP3Prev", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> yearP3Real = createDateTime("yearP3Real", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> yearP4Prev = createDateTime("yearP4Prev", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> yearP4Real = createDateTime("yearP4Real", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> yearPrev = createDateTime("yearPrev", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> yearReal = createDateTime("yearReal", java.time.LocalDateTime.class);

    public final com.querydsl.sql.PrimaryKey<WorkEffortAnalysis> primary = createPrimaryKey(workEffortAnalysisId);

    public final com.querydsl.sql.ForeignKey<WorkEffortType> weaWet1Fk = createForeignKey(workEffortTypeIdSez1, "WORK_EFFORT_TYPE_ID");

    public final com.querydsl.sql.ForeignKey<WorkEffortType> weaWet6Fk = createForeignKey(workEffortTypeIdSez6, "WORK_EFFORT_TYPE_ID");

    public final com.querydsl.sql.ForeignKey<WorkEffort> weaWeFk = createForeignKey(workEffortId, "WORK_EFFORT_ID");

    public final com.querydsl.sql.ForeignKey<WorkEffortType> weaWet4Fk = createForeignKey(workEffortTypeIdSez4, "WORK_EFFORT_TYPE_ID");

    public final com.querydsl.sql.ForeignKey<WorkEffortType> weaWetFk = createForeignKey(workEffortTypeId, "WORK_EFFORT_TYPE_ID");

    public final com.querydsl.sql.ForeignKey<WorkEffortType> weaWet3Fk = createForeignKey(workEffortTypeIdSez3, "WORK_EFFORT_TYPE_ID");

    public final com.querydsl.sql.ForeignKey<WorkEffortType> weaWet5Fk = createForeignKey(workEffortTypeIdSez5, "WORK_EFFORT_TYPE_ID");

    public final com.querydsl.sql.ForeignKey<WorkEffortType> weaWet2Fk = createForeignKey(workEffortTypeIdSez2, "WORK_EFFORT_TYPE_ID");

    public final com.querydsl.sql.ForeignKey<Content> weaCntFk = createForeignKey(reportId, "CONTENT_ID");

    public QWorkEffortAnalysis(String variable) {
        super(WorkEffortAnalysis.class, forVariable(variable), "null", "WORK_EFFORT_ANALYSIS");
        addMetadata();
    }

    public QWorkEffortAnalysis(String variable, String schema, String table) {
        super(WorkEffortAnalysis.class, forVariable(variable), schema, table);
        addMetadata();
    }

    public QWorkEffortAnalysis(String variable, String schema) {
        super(WorkEffortAnalysis.class, forVariable(variable), schema, "WORK_EFFORT_ANALYSIS");
        addMetadata();
    }

    public QWorkEffortAnalysis(Path<? extends WorkEffortAnalysis> path) {
        super(path.getType(), path.getMetadata(), "null", "WORK_EFFORT_ANALYSIS");
        addMetadata();
    }

    public QWorkEffortAnalysis(PathMetadata metadata) {
        super(WorkEffortAnalysis.class, metadata, "null", "WORK_EFFORT_ANALYSIS");
        addMetadata();
    }

    public void addMetadata() {
        addMetadata(availabilityId, ColumnMetadata.named("AVAILABILITY_ID").withIndex(44).ofType(Types.VARCHAR).withSize(20));
        addMetadata(createdStamp, ColumnMetadata.named("CREATED_STAMP").withIndex(65).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(createdTxStamp, ColumnMetadata.named("CREATED_TX_STAMP").withIndex(66).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(dataVisibility, ColumnMetadata.named("DATA_VISIBILITY").withIndex(5).ofType(Types.VARCHAR).withSize(20));
        addMetadata(description, ColumnMetadata.named("DESCRIPTION").withIndex(2).ofType(Types.VARCHAR).withSize(255));
        addMetadata(description1, ColumnMetadata.named("DESCRIPTION1").withIndex(57).ofType(Types.VARCHAR).withSize(255));
        addMetadata(description2, ColumnMetadata.named("DESCRIPTION2").withIndex(58).ofType(Types.VARCHAR).withSize(255));
        addMetadata(description3, ColumnMetadata.named("DESCRIPTION3").withIndex(59).ofType(Types.VARCHAR).withSize(255));
        addMetadata(description4, ColumnMetadata.named("DESCRIPTION4").withIndex(60).ofType(Types.VARCHAR).withSize(255));
        addMetadata(description5, ColumnMetadata.named("DESCRIPTION5").withIndex(61).ofType(Types.VARCHAR).withSize(255));
        addMetadata(excludeValidity, ColumnMetadata.named("EXCLUDE_VALIDITY").withIndex(62).ofType(Types.CHAR).withSize(1));
        addMetadata(isMonitor, ColumnMetadata.named("IS_MONITOR").withIndex(7).ofType(Types.CHAR).withSize(1));
        addMetadata(labelM1Prev, ColumnMetadata.named("LABEL_M1_PREV").withIndex(27).ofType(Types.VARCHAR).withSize(255));
        addMetadata(labelM1Real, ColumnMetadata.named("LABEL_M1_REAL").withIndex(36).ofType(Types.VARCHAR).withSize(255));
        addMetadata(labelM2Prev, ColumnMetadata.named("LABEL_M2_PREV").withIndex(28).ofType(Types.VARCHAR).withSize(255));
        addMetadata(labelM2Real, ColumnMetadata.named("LABEL_M2_REAL").withIndex(37).ofType(Types.VARCHAR).withSize(255));
        addMetadata(labelM3Prev, ColumnMetadata.named("LABEL_M3_PREV").withIndex(29).ofType(Types.VARCHAR).withSize(255));
        addMetadata(labelM3Real, ColumnMetadata.named("LABEL_M3_REAL").withIndex(38).ofType(Types.VARCHAR).withSize(255));
        addMetadata(labelM4Prev, ColumnMetadata.named("LABEL_M4_PREV").withIndex(30).ofType(Types.VARCHAR).withSize(255));
        addMetadata(labelM4Real, ColumnMetadata.named("LABEL_M4_REAL").withIndex(39).ofType(Types.VARCHAR).withSize(255));
        addMetadata(labelP1Prev, ColumnMetadata.named("LABEL_P1_PREV").withIndex(31).ofType(Types.VARCHAR).withSize(255));
        addMetadata(labelP1Real, ColumnMetadata.named("LABEL_P1_REAL").withIndex(40).ofType(Types.VARCHAR).withSize(255));
        addMetadata(labelP2Prev, ColumnMetadata.named("LABEL_P2_PREV").withIndex(32).ofType(Types.VARCHAR).withSize(255));
        addMetadata(labelP2Real, ColumnMetadata.named("LABEL_P2_REAL").withIndex(41).ofType(Types.VARCHAR).withSize(255));
        addMetadata(labelP3Prev, ColumnMetadata.named("LABEL_P3_PREV").withIndex(33).ofType(Types.VARCHAR).withSize(255));
        addMetadata(labelP3Real, ColumnMetadata.named("LABEL_P3_REAL").withIndex(42).ofType(Types.VARCHAR).withSize(255));
        addMetadata(labelP4Prev, ColumnMetadata.named("LABEL_P4_PREV").withIndex(34).ofType(Types.VARCHAR).withSize(255));
        addMetadata(labelP4Real, ColumnMetadata.named("LABEL_P4_REAL").withIndex(43).ofType(Types.VARCHAR).withSize(255));
        addMetadata(labelPrev, ColumnMetadata.named("LABEL_PREV").withIndex(26).ofType(Types.VARCHAR).withSize(255));
        addMetadata(labelReal, ColumnMetadata.named("LABEL_REAL").withIndex(35).ofType(Types.VARCHAR).withSize(255));
        addMetadata(lastUpdatedStamp, ColumnMetadata.named("LAST_UPDATED_STAMP").withIndex(63).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(lastUpdatedTxStamp, ColumnMetadata.named("LAST_UPDATED_TX_STAMP").withIndex(64).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(referenceDate, ColumnMetadata.named("REFERENCE_DATE").withIndex(6).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(reportId, ColumnMetadata.named("REPORT_ID").withIndex(45).ofType(Types.VARCHAR).withSize(20));
        addMetadata(typeBalanceConsIndId, ColumnMetadata.named("TYPE_BALANCE_CONS_IND_ID").withIndex(56).ofType(Types.VARCHAR).withSize(20));
        addMetadata(typeBalanceScoreConId, ColumnMetadata.named("TYPE_BALANCE_SCORE_CON_ID").withIndex(53).ofType(Types.VARCHAR).withSize(20));
        addMetadata(typeBalanceScoreTarId, ColumnMetadata.named("TYPE_BALANCE_SCORE_TAR_ID").withIndex(54).ofType(Types.VARCHAR).withSize(20));
        addMetadata(typeBalanceTarIndId, ColumnMetadata.named("TYPE_BALANCE_TAR_IND_ID").withIndex(55).ofType(Types.VARCHAR).withSize(20));
        addMetadata(workEffortAnalysisId, ColumnMetadata.named("WORK_EFFORT_ANALYSIS_ID").withIndex(1).ofType(Types.VARCHAR).withSize(20).notNull());
        addMetadata(workEffortId, ColumnMetadata.named("WORK_EFFORT_ID").withIndex(4).ofType(Types.VARCHAR).withSize(20));
        addMetadata(workEffortPurposeTypeId, ColumnMetadata.named("WORK_EFFORT_PURPOSE_TYPE_ID").withIndex(46).ofType(Types.VARCHAR).withSize(20));
        addMetadata(workEffortTypeId, ColumnMetadata.named("WORK_EFFORT_TYPE_ID").withIndex(3).ofType(Types.VARCHAR).withSize(20));
        addMetadata(workEffortTypeIdSez1, ColumnMetadata.named("WORK_EFFORT_TYPE_ID_SEZ1").withIndex(47).ofType(Types.VARCHAR).withSize(20));
        addMetadata(workEffortTypeIdSez2, ColumnMetadata.named("WORK_EFFORT_TYPE_ID_SEZ2").withIndex(48).ofType(Types.VARCHAR).withSize(20));
        addMetadata(workEffortTypeIdSez3, ColumnMetadata.named("WORK_EFFORT_TYPE_ID_SEZ3").withIndex(49).ofType(Types.VARCHAR).withSize(20));
        addMetadata(workEffortTypeIdSez4, ColumnMetadata.named("WORK_EFFORT_TYPE_ID_SEZ4").withIndex(50).ofType(Types.VARCHAR).withSize(20));
        addMetadata(workEffortTypeIdSez5, ColumnMetadata.named("WORK_EFFORT_TYPE_ID_SEZ5").withIndex(51).ofType(Types.VARCHAR).withSize(20));
        addMetadata(workEffortTypeIdSez6, ColumnMetadata.named("WORK_EFFORT_TYPE_ID_SEZ6").withIndex(52).ofType(Types.VARCHAR).withSize(20));
        addMetadata(yearM1Prev, ColumnMetadata.named("YEAR_M1_PREV").withIndex(9).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(yearM1Real, ColumnMetadata.named("YEAR_M1_REAL").withIndex(18).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(yearM2Prev, ColumnMetadata.named("YEAR_M2_PREV").withIndex(10).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(yearM2Real, ColumnMetadata.named("YEAR_M2_REAL").withIndex(19).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(yearM3Prev, ColumnMetadata.named("YEAR_M3_PREV").withIndex(11).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(yearM3Real, ColumnMetadata.named("YEAR_M3_REAL").withIndex(20).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(yearM4Prev, ColumnMetadata.named("YEAR_M4_PREV").withIndex(12).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(yearM4Real, ColumnMetadata.named("YEAR_M4_REAL").withIndex(21).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(yearP1Prev, ColumnMetadata.named("YEAR_P1_PREV").withIndex(13).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(yearP1Real, ColumnMetadata.named("YEAR_P1_REAL").withIndex(22).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(yearP2Prev, ColumnMetadata.named("YEAR_P2_PREV").withIndex(14).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(yearP2Real, ColumnMetadata.named("YEAR_P2_REAL").withIndex(23).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(yearP3Prev, ColumnMetadata.named("YEAR_P3_PREV").withIndex(15).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(yearP3Real, ColumnMetadata.named("YEAR_P3_REAL").withIndex(24).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(yearP4Prev, ColumnMetadata.named("YEAR_P4_PREV").withIndex(16).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(yearP4Real, ColumnMetadata.named("YEAR_P4_REAL").withIndex(25).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(yearPrev, ColumnMetadata.named("YEAR_PREV").withIndex(8).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(yearReal, ColumnMetadata.named("YEAR_REAL").withIndex(17).ofType(Types.TIMESTAMP).withSize(26));
    }

}

