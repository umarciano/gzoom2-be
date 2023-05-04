package it.mapsgroup.gzoom.querydsl.dto;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;

import com.querydsl.sql.ColumnMetadata;
import java.sql.Types;




/**
 * QWorkEffortSequence is a Querydsl query type for WorkEffortSequence
 */
@Generated("com.querydsl.sql.codegen.MetaDataSerializer")
public class QWorkEffortSequence extends com.querydsl.sql.RelationalPathBase<WorkEffortSequence> {

    private static final long serialVersionUID = 802305481;

    public static final QWorkEffortSequence workEffortSequence = new QWorkEffortSequence("WORK_EFFORT_SEQUENCE");

    public final StringPath createdByUserLogin = createString("createdByUserLogin");

    public final DateTimePath<java.time.LocalDateTime> createdStamp = createDateTime("createdStamp", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> createdTxStamp = createDateTime("createdTxStamp", java.time.LocalDateTime.class);

    public final StringPath lastModifiedByUserLogin = createString("lastModifiedByUserLogin");

    public final DateTimePath<java.time.LocalDateTime> lastUpdatedStamp = createDateTime("lastUpdatedStamp", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> lastUpdatedTxStamp = createDateTime("lastUpdatedTxStamp", java.time.LocalDateTime.class);

    public final NumberPath<java.math.BigInteger> seqId = createNumber("seqId", java.math.BigInteger.class);

    public final StringPath seqName = createString("seqName");

    public final com.querydsl.sql.PrimaryKey<WorkEffortSequence> primary = createPrimaryKey(seqName);

    public QWorkEffortSequence(String variable) {
        super(WorkEffortSequence.class, forVariable(variable), "null", "WORK_EFFORT_SEQUENCE");
        addMetadata();
    }

    public QWorkEffortSequence(String variable, String schema, String table) {
        super(WorkEffortSequence.class, forVariable(variable), schema, table);
        addMetadata();
    }

    public QWorkEffortSequence(String variable, String schema) {
        super(WorkEffortSequence.class, forVariable(variable), schema, "WORK_EFFORT_SEQUENCE");
        addMetadata();
    }

    public QWorkEffortSequence(Path<? extends WorkEffortSequence> path) {
        super(path.getType(), path.getMetadata(), "null", "WORK_EFFORT_SEQUENCE");
        addMetadata();
    }

    public QWorkEffortSequence(PathMetadata metadata) {
        super(WorkEffortSequence.class, metadata, "null", "WORK_EFFORT_SEQUENCE");
        addMetadata();
    }

    public void addMetadata() {
        addMetadata(createdByUserLogin, ColumnMetadata.named("CREATED_BY_USER_LOGIN").withIndex(4).ofType(Types.VARCHAR).withSize(250));
        addMetadata(createdStamp, ColumnMetadata.named("CREATED_STAMP").withIndex(7).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(createdTxStamp, ColumnMetadata.named("CREATED_TX_STAMP").withIndex(8).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(lastModifiedByUserLogin, ColumnMetadata.named("LAST_MODIFIED_BY_USER_LOGIN").withIndex(3).ofType(Types.VARCHAR).withSize(250));
        addMetadata(lastUpdatedStamp, ColumnMetadata.named("LAST_UPDATED_STAMP").withIndex(5).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(lastUpdatedTxStamp, ColumnMetadata.named("LAST_UPDATED_TX_STAMP").withIndex(6).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(seqId, ColumnMetadata.named("SEQ_ID").withIndex(2).ofType(Types.DECIMAL).withSize(20));
        addMetadata(seqName, ColumnMetadata.named("SEQ_NAME").withIndex(1).ofType(Types.VARCHAR).withSize(60).notNull());
    }

}

