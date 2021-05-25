package it.mapsgroup.gzoom.querydsl.dto;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;

import com.querydsl.sql.ColumnMetadata;
import java.sql.Types;




/**
 * QWorkEffortTypeRole is a Querydsl query type for WorkEffortTypeRole
 */
@Generated("com.querydsl.sql.codegen.MetaDataSerializer")
public class QWorkEffortTypeRole extends com.querydsl.sql.RelationalPathBase<WorkEffortTypeRole> {

    private static final long serialVersionUID = -1223617320;

    public static final QWorkEffortTypeRole workEffortTypeRole = new QWorkEffortTypeRole("WORK_EFFORT_TYPE_ROLE");

    public final StringPath comments = createString("comments");

    public final StringPath createdByUserLogin = createString("createdByUserLogin");

    public final DateTimePath<java.time.LocalDateTime> createdStamp = createDateTime("createdStamp", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> createdTxStamp = createDateTime("createdTxStamp", java.time.LocalDateTime.class);

    public final BooleanPath isMandatory = createBoolean("isMandatory");

    public final StringPath lastModifiedByUserLogin = createString("lastModifiedByUserLogin");

    public final DateTimePath<java.time.LocalDateTime> lastUpdatedStamp = createDateTime("lastUpdatedStamp", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> lastUpdatedTxStamp = createDateTime("lastUpdatedTxStamp", java.time.LocalDateTime.class);

    public final StringPath roleTypeId = createString("roleTypeId");

    public final StringPath workEffortTypeId = createString("workEffortTypeId");

    public final com.querydsl.sql.PrimaryKey<WorkEffortTypeRole> primary = createPrimaryKey(roleTypeId, workEffortTypeId);

    public final com.querydsl.sql.ForeignKey<WorkEffortType> wetrWtFk = createForeignKey(workEffortTypeId, "WORK_EFFORT_TYPE_ID");

    public final com.querydsl.sql.ForeignKey<RoleType> wetrRtFk = createForeignKey(roleTypeId, "ROLE_TYPE_ID");

    public QWorkEffortTypeRole(String variable) {
        super(WorkEffortTypeRole.class, forVariable(variable), "null", "WORK_EFFORT_TYPE_ROLE");
        addMetadata();
    }

    public QWorkEffortTypeRole(String variable, String schema, String table) {
        super(WorkEffortTypeRole.class, forVariable(variable), schema, table);
        addMetadata();
    }

    public QWorkEffortTypeRole(String variable, String schema) {
        super(WorkEffortTypeRole.class, forVariable(variable), schema, "WORK_EFFORT_TYPE_ROLE");
        addMetadata();
    }

    public QWorkEffortTypeRole(Path<? extends WorkEffortTypeRole> path) {
        super(path.getType(), path.getMetadata(), "null", "WORK_EFFORT_TYPE_ROLE");
        addMetadata();
    }

    public QWorkEffortTypeRole(PathMetadata metadata) {
        super(WorkEffortTypeRole.class, metadata, "null", "WORK_EFFORT_TYPE_ROLE");
        addMetadata();
    }

    public void addMetadata() {
        addMetadata(comments, ColumnMetadata.named("COMMENTS").withIndex(3).ofType(Types.VARCHAR).withSize(255));
        addMetadata(createdByUserLogin, ColumnMetadata.named("CREATED_BY_USER_LOGIN").withIndex(5).ofType(Types.VARCHAR).withSize(250));
        addMetadata(createdStamp, ColumnMetadata.named("CREATED_STAMP").withIndex(8).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(createdTxStamp, ColumnMetadata.named("CREATED_TX_STAMP").withIndex(9).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(isMandatory, ColumnMetadata.named("IS_MANDATORY").withIndex(10).ofType(Types.CHAR).withSize(1));
        addMetadata(lastModifiedByUserLogin, ColumnMetadata.named("LAST_MODIFIED_BY_USER_LOGIN").withIndex(4).ofType(Types.VARCHAR).withSize(250));
        addMetadata(lastUpdatedStamp, ColumnMetadata.named("LAST_UPDATED_STAMP").withIndex(6).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(lastUpdatedTxStamp, ColumnMetadata.named("LAST_UPDATED_TX_STAMP").withIndex(7).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(roleTypeId, ColumnMetadata.named("ROLE_TYPE_ID").withIndex(2).ofType(Types.VARCHAR).withSize(20).notNull());
        addMetadata(workEffortTypeId, ColumnMetadata.named("WORK_EFFORT_TYPE_ID").withIndex(1).ofType(Types.VARCHAR).withSize(20).notNull());
    }

}

