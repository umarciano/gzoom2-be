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
 * QWorkEffortAssocTypeAttr is a Querydsl query type for WorkEffortAssocTypeAttr
 */
@Generated("com.querydsl.sql.codegen.MetaDataSerializer")
public class QWorkEffortAssocTypeAttr extends com.querydsl.sql.RelationalPathBase<WorkEffortAssocTypeAttr> {

    private static final long serialVersionUID = -1330912648;

    public static final QWorkEffortAssocTypeAttr workEffortAssocTypeAttr = new QWorkEffortAssocTypeAttr("WORK_EFFORT_ASSOC_TYPE_ATTR");

    public final StringPath attrName = createString("attrName");

    public final StringPath createdByUserLogin = createString("createdByUserLogin");

    public final DateTimePath<java.time.LocalDateTime> createdStamp = createDateTime("createdStamp", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> createdTxStamp = createDateTime("createdTxStamp", java.time.LocalDateTime.class);

    public final StringPath lastModifiedByUserLogin = createString("lastModifiedByUserLogin");

    public final DateTimePath<java.time.LocalDateTime> lastUpdatedStamp = createDateTime("lastUpdatedStamp", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> lastUpdatedTxStamp = createDateTime("lastUpdatedTxStamp", java.time.LocalDateTime.class);

    public final StringPath workEffortAssocTypeId = createString("workEffortAssocTypeId");

    public final com.querydsl.sql.PrimaryKey<WorkEffortAssocTypeAttr> primary = createPrimaryKey(workEffortAssocTypeId, attrName);

    public final com.querydsl.sql.ForeignKey<WorkEffortAssocType> wkEffrtasscTatr = createForeignKey(Arrays.asList(workEffortAssocTypeId, workEffortAssocTypeId), Arrays.asList("WORK_EFFORT_ASSOC_TYPE_ID", "WORK_EFFORT_ASSOC_TYPE_ID"));

    public QWorkEffortAssocTypeAttr(String variable) {
        super(WorkEffortAssocTypeAttr.class, forVariable(variable), "null", "WORK_EFFORT_ASSOC_TYPE_ATTR");
        addMetadata();
    }

    public QWorkEffortAssocTypeAttr(String variable, String schema, String table) {
        super(WorkEffortAssocTypeAttr.class, forVariable(variable), schema, table);
        addMetadata();
    }

    public QWorkEffortAssocTypeAttr(String variable, String schema) {
        super(WorkEffortAssocTypeAttr.class, forVariable(variable), schema, "WORK_EFFORT_ASSOC_TYPE_ATTR");
        addMetadata();
    }

    public QWorkEffortAssocTypeAttr(Path<? extends WorkEffortAssocTypeAttr> path) {
        super(path.getType(), path.getMetadata(), "null", "WORK_EFFORT_ASSOC_TYPE_ATTR");
        addMetadata();
    }

    public QWorkEffortAssocTypeAttr(PathMetadata metadata) {
        super(WorkEffortAssocTypeAttr.class, metadata, "null", "WORK_EFFORT_ASSOC_TYPE_ATTR");
        addMetadata();
    }

    public void addMetadata() {
        addMetadata(attrName, ColumnMetadata.named("ATTR_NAME").withIndex(2).ofType(Types.VARCHAR).withSize(60).notNull());
        addMetadata(createdByUserLogin, ColumnMetadata.named("CREATED_BY_USER_LOGIN").withIndex(8).ofType(Types.VARCHAR).withSize(250));
        addMetadata(createdStamp, ColumnMetadata.named("CREATED_STAMP").withIndex(5).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(createdTxStamp, ColumnMetadata.named("CREATED_TX_STAMP").withIndex(6).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(lastModifiedByUserLogin, ColumnMetadata.named("LAST_MODIFIED_BY_USER_LOGIN").withIndex(7).ofType(Types.VARCHAR).withSize(250));
        addMetadata(lastUpdatedStamp, ColumnMetadata.named("LAST_UPDATED_STAMP").withIndex(3).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(lastUpdatedTxStamp, ColumnMetadata.named("LAST_UPDATED_TX_STAMP").withIndex(4).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(workEffortAssocTypeId, ColumnMetadata.named("WORK_EFFORT_ASSOC_TYPE_ID").withIndex(1).ofType(Types.VARCHAR).withSize(20).notNull());
    }

}

