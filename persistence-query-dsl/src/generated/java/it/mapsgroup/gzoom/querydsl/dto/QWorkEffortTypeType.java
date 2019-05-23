package it.mapsgroup.gzoom.querydsl.dto;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;

import com.querydsl.sql.ColumnMetadata;
import java.sql.Types;




/**
 * QWorkEffortTypeType is a Querydsl query type for WorkEffortTypeType
 */
@Generated("com.querydsl.sql.codegen.MetaDataSerializer")
public class QWorkEffortTypeType extends com.querydsl.sql.RelationalPathBase<WorkEffortTypeType> {

    private static final long serialVersionUID = -1223548004;

    public static final QWorkEffortTypeType workEffortTypeType = new QWorkEffortTypeType("WORK_EFFORT_TYPE_TYPE");

    public final StringPath comments = createString("comments");

    public final StringPath createdByUserLogin = createString("createdByUserLogin");

    public final DateTimePath<java.time.LocalDateTime> createdStamp = createDateTime("createdStamp", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> createdTxStamp = createDateTime("createdTxStamp", java.time.LocalDateTime.class);

    public final StringPath lastModifiedByUserLogin = createString("lastModifiedByUserLogin");

    public final DateTimePath<java.time.LocalDateTime> lastUpdatedStamp = createDateTime("lastUpdatedStamp", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> lastUpdatedTxStamp = createDateTime("lastUpdatedTxStamp", java.time.LocalDateTime.class);

    public final NumberPath<java.math.BigInteger> sequenceNum = createNumber("sequenceNum", java.math.BigInteger.class);

    public final StringPath workEffortTypeIdFrom = createString("workEffortTypeIdFrom");

    public final StringPath workEffortTypeIdRoot = createString("workEffortTypeIdRoot");

    public final StringPath workEffortTypeIdTo = createString("workEffortTypeIdTo");

    public final com.querydsl.sql.PrimaryKey<WorkEffortTypeType> primary = createPrimaryKey(workEffortTypeIdFrom, workEffortTypeIdRoot, workEffortTypeIdTo);

    public final com.querydsl.sql.ForeignKey<WorkEffortType> wttRtFk = createForeignKey(workEffortTypeIdRoot, "WORK_EFFORT_TYPE_ID");

    public final com.querydsl.sql.ForeignKey<WorkEffortType> wttFrFk = createForeignKey(workEffortTypeIdFrom, "WORK_EFFORT_TYPE_ID");

    public final com.querydsl.sql.ForeignKey<WorkEffortType> wttToFk = createForeignKey(workEffortTypeIdTo, "WORK_EFFORT_TYPE_ID");

    public QWorkEffortTypeType(String variable) {
        super(WorkEffortTypeType.class, forVariable(variable), "null", "WORK_EFFORT_TYPE_TYPE");
        addMetadata();
    }

    public QWorkEffortTypeType(String variable, String schema, String table) {
        super(WorkEffortTypeType.class, forVariable(variable), schema, table);
        addMetadata();
    }

    public QWorkEffortTypeType(String variable, String schema) {
        super(WorkEffortTypeType.class, forVariable(variable), schema, "WORK_EFFORT_TYPE_TYPE");
        addMetadata();
    }

    public QWorkEffortTypeType(Path<? extends WorkEffortTypeType> path) {
        super(path.getType(), path.getMetadata(), "null", "WORK_EFFORT_TYPE_TYPE");
        addMetadata();
    }

    public QWorkEffortTypeType(PathMetadata metadata) {
        super(WorkEffortTypeType.class, metadata, "null", "WORK_EFFORT_TYPE_TYPE");
        addMetadata();
    }

    public void addMetadata() {
        addMetadata(comments, ColumnMetadata.named("COMMENTS").withIndex(5).ofType(Types.VARCHAR).withSize(255));
        addMetadata(createdByUserLogin, ColumnMetadata.named("CREATED_BY_USER_LOGIN").withIndex(7).ofType(Types.VARCHAR).withSize(250));
        addMetadata(createdStamp, ColumnMetadata.named("CREATED_STAMP").withIndex(10).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(createdTxStamp, ColumnMetadata.named("CREATED_TX_STAMP").withIndex(11).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(lastModifiedByUserLogin, ColumnMetadata.named("LAST_MODIFIED_BY_USER_LOGIN").withIndex(6).ofType(Types.VARCHAR).withSize(250));
        addMetadata(lastUpdatedStamp, ColumnMetadata.named("LAST_UPDATED_STAMP").withIndex(8).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(lastUpdatedTxStamp, ColumnMetadata.named("LAST_UPDATED_TX_STAMP").withIndex(9).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(sequenceNum, ColumnMetadata.named("SEQUENCE_NUM").withIndex(4).ofType(Types.DECIMAL).withSize(20));
        addMetadata(workEffortTypeIdFrom, ColumnMetadata.named("WORK_EFFORT_TYPE_ID_FROM").withIndex(2).ofType(Types.VARCHAR).withSize(20).notNull());
        addMetadata(workEffortTypeIdRoot, ColumnMetadata.named("WORK_EFFORT_TYPE_ID_ROOT").withIndex(1).ofType(Types.VARCHAR).withSize(20).notNull());
        addMetadata(workEffortTypeIdTo, ColumnMetadata.named("WORK_EFFORT_TYPE_ID_TO").withIndex(3).ofType(Types.VARCHAR).withSize(20).notNull());
    }

}

