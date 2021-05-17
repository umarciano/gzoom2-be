package it.mapsgroup.gzoom.querydsl.dto;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;

import com.querydsl.sql.ColumnMetadata;
import java.sql.Types;




/**
 * QWorkEffortRevision is a Querydsl query type for WorkEffortRevision
 */
@Generated("com.querydsl.sql.codegen.MetaDataSerializer")
public class QWorkEffortRevision extends com.querydsl.sql.RelationalPathBase<WorkEffortRevision> {

    private static final long serialVersionUID = -808028701;

    public static final QWorkEffortRevision workEffortRevision = new QWorkEffortRevision("WORK_EFFORT_REVISION");

    public final StringPath comments = createString("comments");

    public final StringPath createdByUserLogin = createString("createdByUserLogin");

    public final DateTimePath<java.time.LocalDateTime> createdStamp = createDateTime("createdStamp", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> createdTxStamp = createDateTime("createdTxStamp", java.time.LocalDateTime.class);

    public final StringPath description = createString("description");

    public final DateTimePath<java.time.LocalDateTime> fromDate = createDateTime("fromDate", java.time.LocalDateTime.class);

    public final BooleanPath hasAcctgTrans = createBoolean("hasAcctgTrans");

    public final BooleanPath isAutomatic = createBoolean("isAutomatic");

    public final BooleanPath isClosed = createBoolean("isClosed");

    public final StringPath lastModifiedByUserLogin = createString("lastModifiedByUserLogin");

    public final DateTimePath<java.time.LocalDateTime> lastUpdatedStamp = createDateTime("lastUpdatedStamp", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> lastUpdatedTxStamp = createDateTime("lastUpdatedTxStamp", java.time.LocalDateTime.class);

    public final StringPath organizationId = createString("organizationId");

    public final DateTimePath<java.time.LocalDateTime> refDate = createDateTime("refDate", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> toDate = createDateTime("toDate", java.time.LocalDateTime.class);

    public final StringPath workEffortRevisionId = createString("workEffortRevisionId");

    public final StringPath workEffortTypeIdCtx = createString("workEffortTypeIdCtx");

    public final StringPath workEffortTypeIdFil = createString("workEffortTypeIdFil");

    public final com.querydsl.sql.PrimaryKey<WorkEffortRevision> primary = createPrimaryKey(workEffortRevisionId);

    public final com.querydsl.sql.ForeignKey<WorkEffortType> wefrevFilWeft = createForeignKey(workEffortTypeIdFil, "WORK_EFFORT_TYPE_ID");

    public final com.querydsl.sql.ForeignKey<WorkEffortType> wefrevCxtWeft = createForeignKey(workEffortTypeIdCtx, "WORK_EFFORT_TYPE_ID");

    public final com.querydsl.sql.ForeignKey<WorkEffort> _weRev = createInvForeignKey(workEffortRevisionId, "WORK_EFFORT_REVISION_ID");

    public final com.querydsl.sql.ForeignKey<WorkEffortAssoc> _wkassRev = createInvForeignKey(workEffortRevisionId, "WORK_EFFORT_REVISION_ID");

    public QWorkEffortRevision(String variable) {
        super(WorkEffortRevision.class, forVariable(variable), "null", "WORK_EFFORT_REVISION");
        addMetadata();
    }

    public QWorkEffortRevision(String variable, String schema, String table) {
        super(WorkEffortRevision.class, forVariable(variable), schema, table);
        addMetadata();
    }

    public QWorkEffortRevision(String variable, String schema) {
        super(WorkEffortRevision.class, forVariable(variable), schema, "WORK_EFFORT_REVISION");
        addMetadata();
    }

    public QWorkEffortRevision(Path<? extends WorkEffortRevision> path) {
        super(path.getType(), path.getMetadata(), "null", "WORK_EFFORT_REVISION");
        addMetadata();
    }

    public QWorkEffortRevision(PathMetadata metadata) {
        super(WorkEffortRevision.class, metadata, "null", "WORK_EFFORT_REVISION");
        addMetadata();
    }

    public void addMetadata() {
        addMetadata(comments, ColumnMetadata.named("COMMENTS").withIndex(6).ofType(Types.VARCHAR).withSize(2000));
        addMetadata(createdByUserLogin, ColumnMetadata.named("CREATED_BY_USER_LOGIN").withIndex(13).ofType(Types.VARCHAR).withSize(250));
        addMetadata(createdStamp, ColumnMetadata.named("CREATED_STAMP").withIndex(16).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(createdTxStamp, ColumnMetadata.named("CREATED_TX_STAMP").withIndex(17).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(description, ColumnMetadata.named("DESCRIPTION").withIndex(5).ofType(Types.VARCHAR).withSize(255).notNull());
        addMetadata(fromDate, ColumnMetadata.named("FROM_DATE").withIndex(10).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(hasAcctgTrans, ColumnMetadata.named("HAS_ACCTG_TRANS").withIndex(8).ofType(Types.CHAR).withSize(1).notNull());
        addMetadata(isAutomatic, ColumnMetadata.named("IS_AUTOMATIC").withIndex(9).ofType(Types.CHAR).withSize(1));
        addMetadata(isClosed, ColumnMetadata.named("IS_CLOSED").withIndex(7).ofType(Types.CHAR).withSize(1).notNull());
        addMetadata(lastModifiedByUserLogin, ColumnMetadata.named("LAST_MODIFIED_BY_USER_LOGIN").withIndex(12).ofType(Types.VARCHAR).withSize(250));
        addMetadata(lastUpdatedStamp, ColumnMetadata.named("LAST_UPDATED_STAMP").withIndex(14).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(lastUpdatedTxStamp, ColumnMetadata.named("LAST_UPDATED_TX_STAMP").withIndex(15).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(organizationId, ColumnMetadata.named("ORGANIZATION_ID").withIndex(18).ofType(Types.VARCHAR).withSize(20));
        addMetadata(refDate, ColumnMetadata.named("REF_DATE").withIndex(4).ofType(Types.TIMESTAMP).withSize(26).notNull());
        addMetadata(toDate, ColumnMetadata.named("TO_DATE").withIndex(11).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(workEffortRevisionId, ColumnMetadata.named("WORK_EFFORT_REVISION_ID").withIndex(1).ofType(Types.VARCHAR).withSize(20).notNull());
        addMetadata(workEffortTypeIdCtx, ColumnMetadata.named("WORK_EFFORT_TYPE_ID_CTX").withIndex(2).ofType(Types.VARCHAR).withSize(20).notNull());
        addMetadata(workEffortTypeIdFil, ColumnMetadata.named("WORK_EFFORT_TYPE_ID_FIL").withIndex(3).ofType(Types.VARCHAR).withSize(20).notNull());
    }

}

