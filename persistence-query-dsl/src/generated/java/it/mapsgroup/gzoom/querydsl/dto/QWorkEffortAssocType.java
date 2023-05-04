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
 * QWorkEffortAssocType is a Querydsl query type for WorkEffortAssocType
 */
@Generated("com.querydsl.sql.codegen.MetaDataSerializer")
public class QWorkEffortAssocType extends com.querydsl.sql.RelationalPathBase<WorkEffortAssocType> {

    private static final long serialVersionUID = 1801248743;

    public static final QWorkEffortAssocType workEffortAssocType = new QWorkEffortAssocType("WORK_EFFORT_ASSOC_TYPE");

    public final StringPath createdByUserLogin = createString("createdByUserLogin");

    public final DateTimePath<java.time.LocalDateTime> createdStamp = createDateTime("createdStamp", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> createdTxStamp = createDateTime("createdTxStamp", java.time.LocalDateTime.class);

    public final StringPath description = createString("description");

    public final BooleanPath hasResponse = createBoolean("hasResponse");

    public final BooleanPath hasTable = createBoolean("hasTable");

    public final StringPath lastModifiedByUserLogin = createString("lastModifiedByUserLogin");

    public final DateTimePath<java.time.LocalDateTime> lastUpdatedStamp = createDateTime("lastUpdatedStamp", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> lastUpdatedTxStamp = createDateTime("lastUpdatedTxStamp", java.time.LocalDateTime.class);

    public final StringPath parentTypeId = createString("parentTypeId");

    public final StringPath workEffortAssocTypeId = createString("workEffortAssocTypeId");

    public final com.querydsl.sql.PrimaryKey<WorkEffortAssocType> primary = createPrimaryKey(workEffortAssocTypeId);

    public final com.querydsl.sql.ForeignKey<WorkEffortAssocType> wkEffrtasscTpar = createForeignKey(Arrays.asList(parentTypeId, parentTypeId), Arrays.asList("WORK_EFFORT_ASSOC_TYPE_ID", "WORK_EFFORT_ASSOC_TYPE_ID"));

    public final com.querydsl.sql.ForeignKey<WorkEffortAssocTypeAttr> _wkEffrtasscTatr = createInvForeignKey(workEffortAssocTypeId, "WORK_EFFORT_ASSOC_TYPE_ID");

    public final com.querydsl.sql.ForeignKey<WorkEffortAssocType> _wkEffrtasscTpar = createInvForeignKey(workEffortAssocTypeId, "PARENT_TYPE_ID");

    public QWorkEffortAssocType(String variable) {
        super(WorkEffortAssocType.class, forVariable(variable), "null", "WORK_EFFORT_ASSOC_TYPE");
        addMetadata();
    }

    public QWorkEffortAssocType(String variable, String schema, String table) {
        super(WorkEffortAssocType.class, forVariable(variable), schema, table);
        addMetadata();
    }

    public QWorkEffortAssocType(String variable, String schema) {
        super(WorkEffortAssocType.class, forVariable(variable), schema, "WORK_EFFORT_ASSOC_TYPE");
        addMetadata();
    }

    public QWorkEffortAssocType(Path<? extends WorkEffortAssocType> path) {
        super(path.getType(), path.getMetadata(), "null", "WORK_EFFORT_ASSOC_TYPE");
        addMetadata();
    }

    public QWorkEffortAssocType(PathMetadata metadata) {
        super(WorkEffortAssocType.class, metadata, "null", "WORK_EFFORT_ASSOC_TYPE");
        addMetadata();
    }

    public void addMetadata() {
        addMetadata(createdByUserLogin, ColumnMetadata.named("CREATED_BY_USER_LOGIN").withIndex(11).ofType(Types.VARCHAR).withSize(250));
        addMetadata(createdStamp, ColumnMetadata.named("CREATED_STAMP").withIndex(7).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(createdTxStamp, ColumnMetadata.named("CREATED_TX_STAMP").withIndex(8).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(description, ColumnMetadata.named("DESCRIPTION").withIndex(4).ofType(Types.VARCHAR).withSize(255));
        addMetadata(hasResponse, ColumnMetadata.named("HAS_RESPONSE").withIndex(9).ofType(Types.CHAR).withSize(1));
        addMetadata(hasTable, ColumnMetadata.named("HAS_TABLE").withIndex(3).ofType(Types.CHAR).withSize(1));
        addMetadata(lastModifiedByUserLogin, ColumnMetadata.named("LAST_MODIFIED_BY_USER_LOGIN").withIndex(10).ofType(Types.VARCHAR).withSize(250));
        addMetadata(lastUpdatedStamp, ColumnMetadata.named("LAST_UPDATED_STAMP").withIndex(5).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(lastUpdatedTxStamp, ColumnMetadata.named("LAST_UPDATED_TX_STAMP").withIndex(6).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(parentTypeId, ColumnMetadata.named("PARENT_TYPE_ID").withIndex(2).ofType(Types.VARCHAR).withSize(20));
        addMetadata(workEffortAssocTypeId, ColumnMetadata.named("WORK_EFFORT_ASSOC_TYPE_ID").withIndex(1).ofType(Types.VARCHAR).withSize(20).notNull());
    }

}

