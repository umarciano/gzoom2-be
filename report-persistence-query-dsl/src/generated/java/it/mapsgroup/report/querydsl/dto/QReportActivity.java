package it.mapsgroup.report.querydsl.dto;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;

import com.querydsl.sql.ColumnMetadata;
import java.sql.Types;




/**
 * QReportActivity is a Querydsl query type for ReportActivity
 */
@Generated("com.querydsl.sql.codegen.MetaDataSerializer")
public class QReportActivity extends com.querydsl.sql.RelationalPathBase<ReportActivity> {

    private static final long serialVersionUID = -458835302;

    public static final QReportActivity reportActivity = new QReportActivity("REPORT_ACTIVITY");

    public final StringPath activityId = createString("activityId");

    public final StringPath callbackData = createString("callbackData");

    public final EnumPath<it.mapsgroup.gzoom.persistence.common.dto.enumeration.ReportCallbackType> callbackType = createEnum("callbackType", it.mapsgroup.gzoom.persistence.common.dto.enumeration.ReportCallbackType.class);

    public final DateTimePath<java.time.LocalDateTime> completedStamp = createDateTime("completedStamp", java.time.LocalDateTime.class);

    public final StringPath contentName = createString("contentName");

    public final StringPath createdByUserLogin = createString("createdByUserLogin");

    public final DateTimePath<java.time.LocalDateTime> createdStamp = createDateTime("createdStamp", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> createdTxStamp = createDateTime("createdTxStamp", java.time.LocalDateTime.class);

    public final StringPath error = createString("error");

    public final StringPath lastModifiedByUserLogin = createString("lastModifiedByUserLogin");

    public final DateTimePath<java.time.LocalDateTime> lastUpdatedStamp = createDateTime("lastUpdatedStamp", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> lastUpdatedTxStamp = createDateTime("lastUpdatedTxStamp", java.time.LocalDateTime.class);

    public final StringPath mimeTypeId = createString("mimeTypeId");

    public final StringPath objectInfo = createString("objectInfo");

    public final StringPath reportData = createString("reportData");

    public final StringPath reportLocale = createString("reportLocale");

    public final StringPath reportName = createString("reportName");

    public final BooleanPath resumed = createBoolean("resumed");

    public final EnumPath<it.mapsgroup.gzoom.persistence.common.dto.enumeration.ReportActivityStatus> status = createEnum("status", it.mapsgroup.gzoom.persistence.common.dto.enumeration.ReportActivityStatus.class);

    public final StringPath templateName = createString("templateName");

    public final com.querydsl.sql.PrimaryKey<ReportActivity> primary = createPrimaryKey(activityId);

    public QReportActivity(String variable) {
        super(ReportActivity.class, forVariable(variable), "null", "REPORT_ACTIVITY");
        addMetadata();
    }

    public QReportActivity(String variable, String schema, String table) {
        super(ReportActivity.class, forVariable(variable), schema, table);
        addMetadata();
    }

    public QReportActivity(String variable, String schema) {
        super(ReportActivity.class, forVariable(variable), schema, "REPORT_ACTIVITY");
        addMetadata();
    }

    public QReportActivity(Path<? extends ReportActivity> path) {
        super(path.getType(), path.getMetadata(), "null", "REPORT_ACTIVITY");
        addMetadata();
    }

    public QReportActivity(PathMetadata metadata) {
        super(ReportActivity.class, metadata, "null", "REPORT_ACTIVITY");
        addMetadata();
    }

    public void addMetadata() {
        addMetadata(activityId, ColumnMetadata.named("ACTIVITY_ID").withIndex(1).ofType(Types.VARCHAR).withSize(255).notNull());
        addMetadata(callbackData, ColumnMetadata.named("CALLBACK_DATA").withIndex(19).ofType(Types.LONGVARCHAR).withSize(65535));
        addMetadata(callbackType, ColumnMetadata.named("CALLBACK_TYPE").withIndex(20).ofType(Types.VARCHAR).withSize(255));
        addMetadata(completedStamp, ColumnMetadata.named("COMPLETED_STAMP").withIndex(11).ofType(Types.TIMESTAMP).withSize(19));
        addMetadata(contentName, ColumnMetadata.named("CONTENT_NAME").withIndex(18).ofType(Types.VARCHAR).withSize(100));
        addMetadata(createdByUserLogin, ColumnMetadata.named("CREATED_BY_USER_LOGIN").withIndex(16).ofType(Types.VARCHAR).withSize(250));
        addMetadata(createdStamp, ColumnMetadata.named("CREATED_STAMP").withIndex(14).ofType(Types.TIMESTAMP).withSize(19));
        addMetadata(createdTxStamp, ColumnMetadata.named("CREATED_TX_STAMP").withIndex(15).ofType(Types.TIMESTAMP).withSize(19));
        addMetadata(error, ColumnMetadata.named("ERROR").withIndex(5).ofType(Types.LONGVARCHAR).withSize(65535));
        addMetadata(lastModifiedByUserLogin, ColumnMetadata.named("LAST_MODIFIED_BY_USER_LOGIN").withIndex(17).ofType(Types.VARCHAR).withSize(250));
        addMetadata(lastUpdatedStamp, ColumnMetadata.named("LAST_UPDATED_STAMP").withIndex(12).ofType(Types.TIMESTAMP).withSize(19));
        addMetadata(lastUpdatedTxStamp, ColumnMetadata.named("LAST_UPDATED_TX_STAMP").withIndex(13).ofType(Types.TIMESTAMP).withSize(19));
        addMetadata(mimeTypeId, ColumnMetadata.named("MIME_TYPE_ID").withIndex(10).ofType(Types.VARCHAR).withSize(60));
        addMetadata(objectInfo, ColumnMetadata.named("OBJECT_INFO").withIndex(9).ofType(Types.VARCHAR).withSize(255));
        addMetadata(reportData, ColumnMetadata.named("REPORT_DATA").withIndex(4).ofType(Types.LONGVARCHAR).withSize(65535));
        addMetadata(reportLocale, ColumnMetadata.named("REPORT_LOCALE").withIndex(8).ofType(Types.VARCHAR).withSize(255));
        addMetadata(reportName, ColumnMetadata.named("REPORT_NAME").withIndex(7).ofType(Types.VARCHAR).withSize(255).notNull());
        addMetadata(resumed, ColumnMetadata.named("RESUMED").withIndex(3).ofType(Types.CHAR).withSize(1));
        addMetadata(status, ColumnMetadata.named("STATUS").withIndex(2).ofType(Types.VARCHAR).withSize(255).notNull());
        addMetadata(templateName, ColumnMetadata.named("TEMPLATE_NAME").withIndex(6).ofType(Types.VARCHAR).withSize(255).notNull());
    }

}

