package it.mapsgroup.gzoom.querydsl.dto;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;

import com.querydsl.sql.ColumnMetadata;
import java.sql.Types;




/**
 * QWorkEffortTypePeriod is a Querydsl query type for WorkEffortTypePeriod
 */
@Generated("com.querydsl.sql.codegen.MetaDataSerializer")
public class QWorkEffortTypePeriod extends com.querydsl.sql.RelationalPathBase<WorkEffortTypePeriod> {

    private static final long serialVersionUID = 858487203;

    public static final QWorkEffortTypePeriod workEffortTypePeriod = new QWorkEffortTypePeriod("WORK_EFFORT_TYPE_PERIOD");

    public final DateTimePath<java.time.LocalDateTime> createdStamp = createDateTime("createdStamp", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> createdTxStamp = createDateTime("createdTxStamp", java.time.LocalDateTime.class);

    public final StringPath customTimePeriodId = createString("customTimePeriodId");

    public final StringPath desProc = createString("desProc");

    public final StringPath glFiscalTypeEnumId = createString("glFiscalTypeEnumId");

    public final DateTimePath<java.time.LocalDateTime> lastUpdatedStamp = createDateTime("lastUpdatedStamp", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> lastUpdatedTxStamp = createDateTime("lastUpdatedTxStamp", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> perLavFrom = createDateTime("perLavFrom", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> perLavThru = createDateTime("perLavThru", java.time.LocalDateTime.class);

    public final StringPath statusEnumId = createString("statusEnumId");

    public final StringPath statusTypeId = createString("statusTypeId");

    public final StringPath workEffortTypeId = createString("workEffortTypeId");

    public final StringPath workEffortTypePeriodId = createString("workEffortTypePeriodId");

    public final com.querydsl.sql.PrimaryKey<WorkEffortTypePeriod> primary = createPrimaryKey(workEffortTypePeriodId);

    public final com.querydsl.sql.ForeignKey<WorkEffortType> wtpWtype = createForeignKey(workEffortTypeId, "WORK_EFFORT_TYPE_ID");

    public QWorkEffortTypePeriod(String variable) {
        super(WorkEffortTypePeriod.class, forVariable(variable), "null", "WORK_EFFORT_TYPE_PERIOD");
        addMetadata();
    }

    public QWorkEffortTypePeriod(String variable, String schema, String table) {
        super(WorkEffortTypePeriod.class, forVariable(variable), schema, table);
        addMetadata();
    }

    public QWorkEffortTypePeriod(String variable, String schema) {
        super(WorkEffortTypePeriod.class, forVariable(variable), schema, "WORK_EFFORT_TYPE_PERIOD");
        addMetadata();
    }

    public QWorkEffortTypePeriod(Path<? extends WorkEffortTypePeriod> path) {
        super(path.getType(), path.getMetadata(), "null", "WORK_EFFORT_TYPE_PERIOD");
        addMetadata();
    }

    public QWorkEffortTypePeriod(PathMetadata metadata) {
        super(WorkEffortTypePeriod.class, metadata, "null", "WORK_EFFORT_TYPE_PERIOD");
        addMetadata();
    }

    public void addMetadata() {
        addMetadata(createdStamp, ColumnMetadata.named("CREATED_STAMP").withIndex(12).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(createdTxStamp, ColumnMetadata.named("CREATED_TX_STAMP").withIndex(13).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(customTimePeriodId, ColumnMetadata.named("CUSTOM_TIME_PERIOD_ID").withIndex(3).ofType(Types.VARCHAR).withSize(20));
        addMetadata(desProc, ColumnMetadata.named("DES_PROC").withIndex(8).ofType(Types.VARCHAR).withSize(255));
        addMetadata(glFiscalTypeEnumId, ColumnMetadata.named("GL_FISCAL_TYPE_ENUM_ID").withIndex(4).ofType(Types.VARCHAR).withSize(20));
        addMetadata(lastUpdatedStamp, ColumnMetadata.named("LAST_UPDATED_STAMP").withIndex(10).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(lastUpdatedTxStamp, ColumnMetadata.named("LAST_UPDATED_TX_STAMP").withIndex(11).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(perLavFrom, ColumnMetadata.named("PER_LAV_FROM").withIndex(6).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(perLavThru, ColumnMetadata.named("PER_LAV_THRU").withIndex(7).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(statusEnumId, ColumnMetadata.named("STATUS_ENUM_ID").withIndex(9).ofType(Types.VARCHAR).withSize(20));
        addMetadata(statusTypeId, ColumnMetadata.named("STATUS_TYPE_ID").withIndex(5).ofType(Types.VARCHAR).withSize(20));
        addMetadata(workEffortTypeId, ColumnMetadata.named("WORK_EFFORT_TYPE_ID").withIndex(2).ofType(Types.VARCHAR).withSize(20));
        addMetadata(workEffortTypePeriodId, ColumnMetadata.named("WORK_EFFORT_TYPE_PERIOD_ID").withIndex(1).ofType(Types.VARCHAR).withSize(20).notNull());
    }

}

