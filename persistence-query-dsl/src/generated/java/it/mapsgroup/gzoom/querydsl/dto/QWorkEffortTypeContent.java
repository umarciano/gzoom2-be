package it.mapsgroup.gzoom.querydsl.dto;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;

import com.querydsl.sql.ColumnMetadata;
import java.sql.Types;




/**
 * QWorkEffortTypeContent is a Querydsl query type for WorkEffortTypeContent
 */
@Generated("com.querydsl.sql.codegen.MetaDataSerializer")
public class QWorkEffortTypeContent extends com.querydsl.sql.RelationalPathBase<WorkEffortTypeContent> {

    private static final long serialVersionUID = -1821397801;

    public static final QWorkEffortTypeContent workEffortTypeContent = new QWorkEffortTypeContent("WORK_EFFORT_TYPE_CONTENT");

    public final StringPath contentId = createString("contentId");

    public final StringPath createdByUserLogin = createString("createdByUserLogin");

    public final DateTimePath<java.time.LocalDateTime> createdStamp = createDateTime("createdStamp", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> createdTxStamp = createDateTime("createdTxStamp", java.time.LocalDateTime.class);

    public final StringPath etch = createString("etch");

    public final StringPath etchLang = createString("etchLang");

    public final BooleanPath isVisible = createBoolean("isVisible");

    public final StringPath lastModifiedByUserLogin = createString("lastModifiedByUserLogin");

    public final DateTimePath<java.time.LocalDateTime> lastUpdatedStamp = createDateTime("lastUpdatedStamp", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> lastUpdatedTxStamp = createDateTime("lastUpdatedTxStamp", java.time.LocalDateTime.class);

    public final StringPath params = createString("params");

    public final NumberPath<java.math.BigInteger> sequenceNum = createNumber("sequenceNum", java.math.BigInteger.class);

    public final BooleanPath useFilter = createBoolean("useFilter");

    public final StringPath weTypeContentTypeId = createString("weTypeContentTypeId");

    public final StringPath workEffortPurposeTypeId = createString("workEffortPurposeTypeId");

    public final StringPath workEffortTypeId = createString("workEffortTypeId");

    public final com.querydsl.sql.PrimaryKey<WorkEffortTypeContent> primary = createPrimaryKey(contentId, workEffortTypeId);

    public final com.querydsl.sql.ForeignKey<Content> wtfCnFk = createForeignKey(contentId, "CONTENT_ID");

    public final com.querydsl.sql.ForeignKey<WorkEffortType> wtfWtFk = createForeignKey(workEffortTypeId, "WORK_EFFORT_TYPE_ID");

    public QWorkEffortTypeContent(String variable) {
        super(WorkEffortTypeContent.class, forVariable(variable), "null", "WORK_EFFORT_TYPE_CONTENT");
        addMetadata();
    }

    public QWorkEffortTypeContent(String variable, String schema, String table) {
        super(WorkEffortTypeContent.class, forVariable(variable), schema, table);
        addMetadata();
    }

    public QWorkEffortTypeContent(String variable, String schema) {
        super(WorkEffortTypeContent.class, forVariable(variable), schema, "WORK_EFFORT_TYPE_CONTENT");
        addMetadata();
    }

    public QWorkEffortTypeContent(Path<? extends WorkEffortTypeContent> path) {
        super(path.getType(), path.getMetadata(), "null", "WORK_EFFORT_TYPE_CONTENT");
        addMetadata();
    }

    public QWorkEffortTypeContent(PathMetadata metadata) {
        super(WorkEffortTypeContent.class, metadata, "null", "WORK_EFFORT_TYPE_CONTENT");
        addMetadata();
    }

    public void addMetadata() {
        addMetadata(contentId, ColumnMetadata.named("CONTENT_ID").withIndex(3).ofType(Types.VARCHAR).withSize(20).notNull());
        addMetadata(createdByUserLogin, ColumnMetadata.named("CREATED_BY_USER_LOGIN").withIndex(10).ofType(Types.VARCHAR).withSize(250));
        addMetadata(createdStamp, ColumnMetadata.named("CREATED_STAMP").withIndex(13).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(createdTxStamp, ColumnMetadata.named("CREATED_TX_STAMP").withIndex(14).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(etch, ColumnMetadata.named("ETCH").withIndex(5).ofType(Types.VARCHAR).withSize(255));
        addMetadata(etchLang, ColumnMetadata.named("ETCH_LANG").withIndex(15).ofType(Types.VARCHAR).withSize(255));
        addMetadata(isVisible, ColumnMetadata.named("IS_VISIBLE").withIndex(8).ofType(Types.CHAR).withSize(1));
        addMetadata(lastModifiedByUserLogin, ColumnMetadata.named("LAST_MODIFIED_BY_USER_LOGIN").withIndex(9).ofType(Types.VARCHAR).withSize(250));
        addMetadata(lastUpdatedStamp, ColumnMetadata.named("LAST_UPDATED_STAMP").withIndex(11).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(lastUpdatedTxStamp, ColumnMetadata.named("LAST_UPDATED_TX_STAMP").withIndex(12).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(params, ColumnMetadata.named("PARAMS").withIndex(7).ofType(Types.VARCHAR).withSize(2000));
        addMetadata(sequenceNum, ColumnMetadata.named("SEQUENCE_NUM").withIndex(4).ofType(Types.DECIMAL).withSize(20));
        addMetadata(useFilter, ColumnMetadata.named("USE_FILTER").withIndex(16).ofType(Types.CHAR).withSize(1));
        addMetadata(weTypeContentTypeId, ColumnMetadata.named("WE_TYPE_CONTENT_TYPE_ID").withIndex(2).ofType(Types.VARCHAR).withSize(20));
        addMetadata(workEffortPurposeTypeId, ColumnMetadata.named("WORK_EFFORT_PURPOSE_TYPE_ID").withIndex(6).ofType(Types.VARCHAR).withSize(20));
        addMetadata(workEffortTypeId, ColumnMetadata.named("WORK_EFFORT_TYPE_ID").withIndex(1).ofType(Types.VARCHAR).withSize(20).notNull());
    }

}

