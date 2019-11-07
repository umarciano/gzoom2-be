package it.mapsgroup.gzoom.querydsl.dto;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;

import com.querydsl.sql.ColumnMetadata;
import java.sql.Types;




/**
 * QWorkEffortAssoc is a Querydsl query type for WorkEffortAssoc
 */
@Generated("com.querydsl.sql.codegen.MetaDataSerializer")
public class QWorkEffortAssoc extends com.querydsl.sql.RelationalPathBase<WorkEffortAssoc> {

    private static final long serialVersionUID = 168504077;

    public static final QWorkEffortAssoc workEffortAssoc = new QWorkEffortAssoc("WORK_EFFORT_ASSOC");

    public final NumberPath<java.math.BigDecimal> assocWeight = createNumber("assocWeight", java.math.BigDecimal.class);

    public final StringPath comments = createString("comments");

    public final StringPath createdByUserLogin = createString("createdByUserLogin");

    public final DateTimePath<java.time.LocalDateTime> createdStamp = createDateTime("createdStamp", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> createdTxStamp = createDateTime("createdTxStamp", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> fromDate = createDateTime("fromDate", java.time.LocalDateTime.class);

    public final BooleanPath isPosted = createBoolean("isPosted");

    public final StringPath lastModifiedByUserLogin = createString("lastModifiedByUserLogin");

    public final DateTimePath<java.time.LocalDateTime> lastUpdatedStamp = createDateTime("lastUpdatedStamp", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> lastUpdatedTxStamp = createDateTime("lastUpdatedTxStamp", java.time.LocalDateTime.class);

    public final StringPath note = createString("note");

    public final StringPath responseEnumId = createString("responseEnumId");

    public final NumberPath<java.math.BigInteger> sequenceNum = createNumber("sequenceNum", java.math.BigInteger.class);

    public final DateTimePath<java.time.LocalDateTime> thruDate = createDateTime("thruDate", java.time.LocalDateTime.class);

    public final StringPath weMeasureEvalId = createString("weMeasureEvalId");

    public final StringPath workEffortAssocTypeId = createString("workEffortAssocTypeId");

    public final StringPath workEffortIdFrom = createString("workEffortIdFrom");

    public final StringPath workEffortIdTo = createString("workEffortIdTo");

    public final StringPath workEffortRevisionId = createString("workEffortRevisionId");

    public final com.querydsl.sql.PrimaryKey<WorkEffortAssoc> primary = createPrimaryKey(fromDate, workEffortAssocTypeId, workEffortIdFrom, workEffortIdTo);

    public final com.querydsl.sql.ForeignKey<WorkEffort> wkEffrtasscFwe = createForeignKey(workEffortIdFrom, "WORK_EFFORT_ID");

    public final com.querydsl.sql.ForeignKey<WorkEffort> wkEffrtasscTwe = createForeignKey(workEffortIdTo, "WORK_EFFORT_ID");

    public QWorkEffortAssoc(String variable) {
        super(WorkEffortAssoc.class, forVariable(variable), "null", "WORK_EFFORT_ASSOC");
        addMetadata();
    }

    public QWorkEffortAssoc(String variable, String schema, String table) {
        super(WorkEffortAssoc.class, forVariable(variable), schema, table);
        addMetadata();
    }

    public QWorkEffortAssoc(String variable, String schema) {
        super(WorkEffortAssoc.class, forVariable(variable), schema, "WORK_EFFORT_ASSOC");
        addMetadata();
    }

    public QWorkEffortAssoc(Path<? extends WorkEffortAssoc> path) {
        super(path.getType(), path.getMetadata(), "null", "WORK_EFFORT_ASSOC");
        addMetadata();
    }

    public QWorkEffortAssoc(PathMetadata metadata) {
        super(WorkEffortAssoc.class, metadata, "null", "WORK_EFFORT_ASSOC");
        addMetadata();
    }

    public void addMetadata() {
        addMetadata(assocWeight, ColumnMetadata.named("ASSOC_WEIGHT").withIndex(11).ofType(Types.DECIMAL).withSize(18).withDigits(6));
        addMetadata(comments, ColumnMetadata.named("COMMENTS").withIndex(12).ofType(Types.VARCHAR).withSize(2000));
        addMetadata(createdByUserLogin, ColumnMetadata.named("CREATED_BY_USER_LOGIN").withIndex(18).ofType(Types.VARCHAR).withSize(250));
        addMetadata(createdStamp, ColumnMetadata.named("CREATED_STAMP").withIndex(9).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(createdTxStamp, ColumnMetadata.named("CREATED_TX_STAMP").withIndex(10).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(fromDate, ColumnMetadata.named("FROM_DATE").withIndex(5).ofType(Types.TIMESTAMP).withSize(26).notNull());
        addMetadata(isPosted, ColumnMetadata.named("IS_POSTED").withIndex(14).ofType(Types.CHAR).withSize(1));
        addMetadata(lastModifiedByUserLogin, ColumnMetadata.named("LAST_MODIFIED_BY_USER_LOGIN").withIndex(17).ofType(Types.VARCHAR).withSize(250));
        addMetadata(lastUpdatedStamp, ColumnMetadata.named("LAST_UPDATED_STAMP").withIndex(7).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(lastUpdatedTxStamp, ColumnMetadata.named("LAST_UPDATED_TX_STAMP").withIndex(8).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(note, ColumnMetadata.named("NOTE").withIndex(13).ofType(Types.VARCHAR).withSize(2000));
        addMetadata(responseEnumId, ColumnMetadata.named("RESPONSE_ENUM_ID").withIndex(15).ofType(Types.VARCHAR).withSize(20));
        addMetadata(sequenceNum, ColumnMetadata.named("SEQUENCE_NUM").withIndex(4).ofType(Types.DECIMAL).withSize(20));
        addMetadata(thruDate, ColumnMetadata.named("THRU_DATE").withIndex(6).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(weMeasureEvalId, ColumnMetadata.named("WE_MEASURE_EVAL_ID").withIndex(16).ofType(Types.VARCHAR).withSize(20));
        addMetadata(workEffortAssocTypeId, ColumnMetadata.named("WORK_EFFORT_ASSOC_TYPE_ID").withIndex(3).ofType(Types.VARCHAR).withSize(20).notNull());
        addMetadata(workEffortIdFrom, ColumnMetadata.named("WORK_EFFORT_ID_FROM").withIndex(1).ofType(Types.VARCHAR).withSize(20).notNull());
        addMetadata(workEffortIdTo, ColumnMetadata.named("WORK_EFFORT_ID_TO").withIndex(2).ofType(Types.VARCHAR).withSize(20).notNull());
        addMetadata(workEffortRevisionId, ColumnMetadata.named("WORK_EFFORT_REVISION_ID").withIndex(19).ofType(Types.VARCHAR).withSize(20));
    }

}

