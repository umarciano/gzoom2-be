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
 * QJobLogLog is a Querydsl query type for JobLogLog
 */
@Generated("com.querydsl.sql.codegen.MetaDataSerializer")
public class QJobLogLog extends com.querydsl.sql.RelationalPathBase<JobLogLog> {

    private static final long serialVersionUID = 1161862034;

    public static final QJobLogLog jobLogLog = new QJobLogLog("JOB_LOG_LOG");

    public final StringPath createdByUserLogin = createString("createdByUserLogin");

    public final DateTimePath<java.time.LocalDateTime> createdStamp = createDateTime("createdStamp", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> createdTxStamp = createDateTime("createdTxStamp", java.time.LocalDateTime.class);

    public final StringPath jobLogId = createString("jobLogId");

    public final StringPath jobLogLogId = createString("jobLogLogId");

    public final StringPath lastModifiedByUserLogin = createString("lastModifiedByUserLogin");

    public final DateTimePath<java.time.LocalDateTime> lastUpdatedStamp = createDateTime("lastUpdatedStamp", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> lastUpdatedTxStamp = createDateTime("lastUpdatedTxStamp", java.time.LocalDateTime.class);

    public final StringPath logCode = createString("logCode");

    public final StringPath logMessage = createString("logMessage");

    public final StringPath logMessageLong = createString("logMessageLong");

    public final StringPath logTypeEnumId = createString("logTypeEnumId");

    public final StringPath valuePk1 = createString("valuePk1");

    public final StringPath valueRef1 = createString("valueRef1");

    public final StringPath valueRef2 = createString("valueRef2");

    public final StringPath valueRef3 = createString("valueRef3");

    public final com.querydsl.sql.PrimaryKey<JobLogLog> primary = createPrimaryKey(jobLogLogId);

    public final com.querydsl.sql.ForeignKey<JobLog> jllJlFk = createForeignKey(Arrays.asList(jobLogId, jobLogId), Arrays.asList("JOB_LOG_ID", "JOB_LOG_ID"));

    public QJobLogLog(String variable) {
        super(JobLogLog.class, forVariable(variable), "null", "JOB_LOG_LOG");
        addMetadata();
    }

    public QJobLogLog(String variable, String schema, String table) {
        super(JobLogLog.class, forVariable(variable), schema, table);
        addMetadata();
    }

    public QJobLogLog(String variable, String schema) {
        super(JobLogLog.class, forVariable(variable), schema, "JOB_LOG_LOG");
        addMetadata();
    }

    public QJobLogLog(Path<? extends JobLogLog> path) {
        super(path.getType(), path.getMetadata(), "null", "JOB_LOG_LOG");
        addMetadata();
    }

    public QJobLogLog(PathMetadata metadata) {
        super(JobLogLog.class, metadata, "null", "JOB_LOG_LOG");
        addMetadata();
    }

    public void addMetadata() {
        addMetadata(createdByUserLogin, ColumnMetadata.named("CREATED_BY_USER_LOGIN").withIndex(12).ofType(Types.VARCHAR).withSize(250));
        addMetadata(createdStamp, ColumnMetadata.named("CREATED_STAMP").withIndex(15).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(createdTxStamp, ColumnMetadata.named("CREATED_TX_STAMP").withIndex(16).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(jobLogId, ColumnMetadata.named("JOB_LOG_ID").withIndex(2).ofType(Types.VARCHAR).withSize(20));
        addMetadata(jobLogLogId, ColumnMetadata.named("JOB_LOG_LOG_ID").withIndex(1).ofType(Types.VARCHAR).withSize(20).notNull());
        addMetadata(lastModifiedByUserLogin, ColumnMetadata.named("LAST_MODIFIED_BY_USER_LOGIN").withIndex(11).ofType(Types.VARCHAR).withSize(250));
        addMetadata(lastUpdatedStamp, ColumnMetadata.named("LAST_UPDATED_STAMP").withIndex(13).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(lastUpdatedTxStamp, ColumnMetadata.named("LAST_UPDATED_TX_STAMP").withIndex(14).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(logCode, ColumnMetadata.named("LOG_CODE").withIndex(4).ofType(Types.VARCHAR).withSize(20));
        addMetadata(logMessage, ColumnMetadata.named("LOG_MESSAGE").withIndex(5).ofType(Types.VARCHAR).withSize(2000));
        addMetadata(logMessageLong, ColumnMetadata.named("LOG_MESSAGE_LONG").withIndex(9).ofType(Types.LONGVARCHAR).withSize(2147483647));
        addMetadata(logTypeEnumId, ColumnMetadata.named("LOG_TYPE_ENUM_ID").withIndex(3).ofType(Types.VARCHAR).withSize(20));
        addMetadata(valuePk1, ColumnMetadata.named("VALUE_PK1").withIndex(10).ofType(Types.LONGVARCHAR).withSize(2147483647));
        addMetadata(valueRef1, ColumnMetadata.named("VALUE_REF1").withIndex(6).ofType(Types.VARCHAR).withSize(255));
        addMetadata(valueRef2, ColumnMetadata.named("VALUE_REF2").withIndex(7).ofType(Types.VARCHAR).withSize(255));
        addMetadata(valueRef3, ColumnMetadata.named("VALUE_REF3").withIndex(8).ofType(Types.VARCHAR).withSize(255));
    }

}

