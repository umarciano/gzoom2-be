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
 * QWorkEffortTypeAssoc is a Querydsl query type for WorkEffortTypeAssoc
 */
@Generated("com.querydsl.sql.codegen.MetaDataSerializer")
public class QWorkEffortTypeAssoc extends com.querydsl.sql.RelationalPathBase<WorkEffortTypeAssoc> {

    private static final long serialVersionUID = 706995187;

    public static final QWorkEffortTypeAssoc workEffortTypeAssoc = new QWorkEffortTypeAssoc("WORK_EFFORT_TYPE_ASSOC");

    public final StringPath comments = createString("comments");

    public final StringPath commentsLang = createString("commentsLang");

    public final StringPath contentId = createString("contentId");

    public final StringPath createdByUserLogin = createString("createdByUserLogin");

    public final DateTimePath<java.time.LocalDateTime> createdStamp = createDateTime("createdStamp", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> createdTxStamp = createDateTime("createdTxStamp", java.time.LocalDateTime.class);

    public final BooleanPath hasResp = createBoolean("hasResp");

    public final BooleanPath isMandatory = createBoolean("isMandatory");

    public final BooleanPath isParentRel = createBoolean("isParentRel");

    public final BooleanPath isUnique = createBoolean("isUnique");

    public final StringPath lastModifiedByUserLogin = createString("lastModifiedByUserLogin");

    public final DateTimePath<java.time.LocalDateTime> lastUpdatedStamp = createDateTime("lastUpdatedStamp", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> lastUpdatedTxStamp = createDateTime("lastUpdatedTxStamp", java.time.LocalDateTime.class);

    public final StringPath wefromWetoEnumId = createString("wefromWetoEnumId");

    public final StringPath workEffortAssocTypeId = createString("workEffortAssocTypeId");

    public final StringPath workEffortTypeId = createString("workEffortTypeId");

    public final StringPath workEffortTypeIdRef = createString("workEffortTypeIdRef");

    public final com.querydsl.sql.PrimaryKey<WorkEffortTypeAssoc> primary = createPrimaryKey(workEffortTypeId, workEffortAssocTypeId, wefromWetoEnumId, workEffortTypeIdRef);

    public final com.querydsl.sql.ForeignKey<WorkEffortType> wetaWetFk = createForeignKey(Arrays.asList(workEffortTypeId, workEffortTypeId), Arrays.asList("WORK_EFFORT_TYPE_ID", "WORK_EFFORT_TYPE_ID"));

    public final com.querydsl.sql.ForeignKey<WorkEffortType> wetaWetrFk = createForeignKey(Arrays.asList(workEffortTypeIdRef, workEffortTypeIdRef), Arrays.asList("WORK_EFFORT_TYPE_ID", "WORK_EFFORT_TYPE_ID"));

    public QWorkEffortTypeAssoc(String variable) {
        super(WorkEffortTypeAssoc.class, forVariable(variable), "null", "WORK_EFFORT_TYPE_ASSOC");
        addMetadata();
    }

    public QWorkEffortTypeAssoc(String variable, String schema, String table) {
        super(WorkEffortTypeAssoc.class, forVariable(variable), schema, table);
        addMetadata();
    }

    public QWorkEffortTypeAssoc(String variable, String schema) {
        super(WorkEffortTypeAssoc.class, forVariable(variable), schema, "WORK_EFFORT_TYPE_ASSOC");
        addMetadata();
    }

    public QWorkEffortTypeAssoc(Path<? extends WorkEffortTypeAssoc> path) {
        super(path.getType(), path.getMetadata(), "null", "WORK_EFFORT_TYPE_ASSOC");
        addMetadata();
    }

    public QWorkEffortTypeAssoc(PathMetadata metadata) {
        super(WorkEffortTypeAssoc.class, metadata, "null", "WORK_EFFORT_TYPE_ASSOC");
        addMetadata();
    }

    public void addMetadata() {
        addMetadata(comments, ColumnMetadata.named("COMMENTS").withIndex(5).ofType(Types.VARCHAR).withSize(255));
        addMetadata(commentsLang, ColumnMetadata.named("COMMENTS_LANG").withIndex(11).ofType(Types.VARCHAR).withSize(255));
        addMetadata(contentId, ColumnMetadata.named("CONTENT_ID").withIndex(6).ofType(Types.VARCHAR).withSize(20));
        addMetadata(createdByUserLogin, ColumnMetadata.named("CREATED_BY_USER_LOGIN").withIndex(13).ofType(Types.VARCHAR).withSize(250));
        addMetadata(createdStamp, ColumnMetadata.named("CREATED_STAMP").withIndex(16).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(createdTxStamp, ColumnMetadata.named("CREATED_TX_STAMP").withIndex(17).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(hasResp, ColumnMetadata.named("HAS_RESP").withIndex(8).ofType(Types.CHAR).withSize(1));
        addMetadata(isMandatory, ColumnMetadata.named("IS_MANDATORY").withIndex(10).ofType(Types.CHAR).withSize(1));
        addMetadata(isParentRel, ColumnMetadata.named("IS_PARENT_REL").withIndex(9).ofType(Types.CHAR).withSize(1));
        addMetadata(isUnique, ColumnMetadata.named("IS_UNIQUE").withIndex(7).ofType(Types.CHAR).withSize(1));
        addMetadata(lastModifiedByUserLogin, ColumnMetadata.named("LAST_MODIFIED_BY_USER_LOGIN").withIndex(12).ofType(Types.VARCHAR).withSize(250));
        addMetadata(lastUpdatedStamp, ColumnMetadata.named("LAST_UPDATED_STAMP").withIndex(14).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(lastUpdatedTxStamp, ColumnMetadata.named("LAST_UPDATED_TX_STAMP").withIndex(15).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(wefromWetoEnumId, ColumnMetadata.named("WEFROM_WETO_ENUM_ID").withIndex(3).ofType(Types.VARCHAR).withSize(20).notNull());
        addMetadata(workEffortAssocTypeId, ColumnMetadata.named("WORK_EFFORT_ASSOC_TYPE_ID").withIndex(2).ofType(Types.VARCHAR).withSize(20).notNull());
        addMetadata(workEffortTypeId, ColumnMetadata.named("WORK_EFFORT_TYPE_ID").withIndex(1).ofType(Types.VARCHAR).withSize(20).notNull());
        addMetadata(workEffortTypeIdRef, ColumnMetadata.named("WORK_EFFORT_TYPE_ID_REF").withIndex(4).ofType(Types.VARCHAR).withSize(20).notNull());
    }

}

