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
 * QJobLog is a Querydsl query type for JobLog
 */
@Generated("com.querydsl.sql.codegen.MetaDataSerializer")
public class QJobLog extends com.querydsl.sql.RelationalPathBase<JobLog> {

    private static final long serialVersionUID = 723916370;

    public static final QJobLog jobLog = new QJobLog("JOB_LOG");

    public final NumberPath<java.math.BigInteger> blockingErrors = createNumber("blockingErrors", java.math.BigInteger.class);

    public final StringPath createdByUserLogin = createString("createdByUserLogin");

    public final DateTimePath<java.time.LocalDateTime> createdStamp = createDateTime("createdStamp", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> createdTxStamp = createDateTime("createdTxStamp", java.time.LocalDateTime.class);

    public final StringPath description = createString("description");

    public final StringPath elabRef1 = createString("elabRef1");

    public final StringPath jobId = createString("jobId");

    public final StringPath jobLogId = createString("jobLogId");

    public final StringPath lastModifiedByUserLogin = createString("lastModifiedByUserLogin");

    public final DateTimePath<java.time.LocalDateTime> lastUpdatedStamp = createDateTime("lastUpdatedStamp", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> lastUpdatedTxStamp = createDateTime("lastUpdatedTxStamp", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> logDate = createDateTime("logDate", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> logEndDate = createDateTime("logEndDate", java.time.LocalDateTime.class);

    public final NumberPath<java.math.BigInteger> recordElaborated = createNumber("recordElaborated", java.math.BigInteger.class);

    public final StringPath serviceName = createString("serviceName");

    public final StringPath serviceTypeId = createString("serviceTypeId");

    public final StringPath sessionId = createString("sessionId");

    public final StringPath userLoginId = createString("userLoginId");

    public final NumberPath<java.math.BigInteger> warningMessages = createNumber("warningMessages", java.math.BigInteger.class);

    public final com.querydsl.sql.PrimaryKey<JobLog> primary = createPrimaryKey(jobLogId);

    public final com.querydsl.sql.ForeignKey<JobLogServiceType> jlstFk = createForeignKey(Arrays.asList(serviceTypeId, serviceTypeId), Arrays.asList("SERVICE_TYPE_ID", "SERVICE_TYPE_ID"));

    public final com.querydsl.sql.ForeignKey<JobLogJobExecParams> _jljepJlFk = createInvForeignKey(jobLogId, "JOB_LOG_ID");

    public final com.querydsl.sql.ForeignKey<JobLogLog> _jllJlFk = createInvForeignKey(jobLogId, "JOB_LOG_ID");

    public QJobLog(String variable) {
        super(JobLog.class, forVariable(variable), "null", "JOB_LOG");
        addMetadata();
    }

    public QJobLog(String variable, String schema, String table) {
        super(JobLog.class, forVariable(variable), schema, table);
        addMetadata();
    }

    public QJobLog(String variable, String schema) {
        super(JobLog.class, forVariable(variable), schema, "JOB_LOG");
        addMetadata();
    }

    public QJobLog(Path<? extends JobLog> path) {
        super(path.getType(), path.getMetadata(), "null", "JOB_LOG");
        addMetadata();
    }

    public QJobLog(PathMetadata metadata) {
        super(JobLog.class, metadata, "null", "JOB_LOG");
        addMetadata();
    }

    public void addMetadata() {
        addMetadata(blockingErrors, ColumnMetadata.named("BLOCKING_ERRORS").withIndex(11).ofType(Types.DECIMAL).withSize(20));
        addMetadata(createdByUserLogin, ColumnMetadata.named("CREATED_BY_USER_LOGIN").withIndex(15).ofType(Types.VARCHAR).withSize(250));
        addMetadata(createdStamp, ColumnMetadata.named("CREATED_STAMP").withIndex(18).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(createdTxStamp, ColumnMetadata.named("CREATED_TX_STAMP").withIndex(19).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(description, ColumnMetadata.named("DESCRIPTION").withIndex(6).ofType(Types.VARCHAR).withSize(255));
        addMetadata(elabRef1, ColumnMetadata.named("ELAB_REF1").withIndex(12).ofType(Types.VARCHAR).withSize(255));
        addMetadata(jobId, ColumnMetadata.named("JOB_ID").withIndex(2).ofType(Types.VARCHAR).withSize(20));
        addMetadata(jobLogId, ColumnMetadata.named("JOB_LOG_ID").withIndex(1).ofType(Types.VARCHAR).withSize(20).notNull());
        addMetadata(lastModifiedByUserLogin, ColumnMetadata.named("LAST_MODIFIED_BY_USER_LOGIN").withIndex(14).ofType(Types.VARCHAR).withSize(250));
        addMetadata(lastUpdatedStamp, ColumnMetadata.named("LAST_UPDATED_STAMP").withIndex(16).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(lastUpdatedTxStamp, ColumnMetadata.named("LAST_UPDATED_TX_STAMP").withIndex(17).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(logDate, ColumnMetadata.named("LOG_DATE").withIndex(7).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(logEndDate, ColumnMetadata.named("LOG_END_DATE").withIndex(8).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(recordElaborated, ColumnMetadata.named("RECORD_ELABORATED").withIndex(9).ofType(Types.DECIMAL).withSize(20));
        addMetadata(serviceName, ColumnMetadata.named("SERVICE_NAME").withIndex(5).ofType(Types.VARCHAR).withSize(255));
        addMetadata(serviceTypeId, ColumnMetadata.named("SERVICE_TYPE_ID").withIndex(4).ofType(Types.VARCHAR).withSize(20));
        addMetadata(sessionId, ColumnMetadata.named("SESSION_ID").withIndex(13).ofType(Types.VARCHAR).withSize(20));
        addMetadata(userLoginId, ColumnMetadata.named("USER_LOGIN_ID").withIndex(3).ofType(Types.VARCHAR).withSize(250));
        addMetadata(warningMessages, ColumnMetadata.named("WARNING_MESSAGES").withIndex(10).ofType(Types.DECIMAL).withSize(20));
    }

}

