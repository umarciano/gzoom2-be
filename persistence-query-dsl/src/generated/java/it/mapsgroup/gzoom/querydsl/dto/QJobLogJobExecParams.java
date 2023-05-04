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
 * QJobLogJobExecParams is a Querydsl query type for JobLogJobExecParams
 */
@Generated("com.querydsl.sql.codegen.MetaDataSerializer")
public class QJobLogJobExecParams extends com.querydsl.sql.RelationalPathBase<JobLogJobExecParams> {

    private static final long serialVersionUID = -1455797278;

    public static final QJobLogJobExecParams jobLogJobExecParams = new QJobLogJobExecParams("JOB_LOG_JOB_EXEC_PARAMS");

    public final StringPath createdByUserLogin = createString("createdByUserLogin");

    public final DateTimePath<java.time.LocalDateTime> createdStamp = createDateTime("createdStamp", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> createdTxStamp = createDateTime("createdTxStamp", java.time.LocalDateTime.class);

    public final StringPath jobLogId = createString("jobLogId");

    public final StringPath lastModifiedByUserLogin = createString("lastModifiedByUserLogin");

    public final DateTimePath<java.time.LocalDateTime> lastUpdatedStamp = createDateTime("lastUpdatedStamp", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> lastUpdatedTxStamp = createDateTime("lastUpdatedTxStamp", java.time.LocalDateTime.class);

    public final StringPath parameterDescription = createString("parameterDescription");

    public final StringPath parameterName = createString("parameterName");

    public final StringPath parameterType = createString("parameterType");

    public final StringPath parameterValue = createString("parameterValue");

    public final com.querydsl.sql.PrimaryKey<JobLogJobExecParams> primary = createPrimaryKey(jobLogId, parameterName);

    public final com.querydsl.sql.ForeignKey<JobLog> jljepJlFk = createForeignKey(Arrays.asList(jobLogId, jobLogId), Arrays.asList("JOB_LOG_ID", "JOB_LOG_ID"));

    public QJobLogJobExecParams(String variable) {
        super(JobLogJobExecParams.class, forVariable(variable), "null", "JOB_LOG_JOB_EXEC_PARAMS");
        addMetadata();
    }

    public QJobLogJobExecParams(String variable, String schema, String table) {
        super(JobLogJobExecParams.class, forVariable(variable), schema, table);
        addMetadata();
    }

    public QJobLogJobExecParams(String variable, String schema) {
        super(JobLogJobExecParams.class, forVariable(variable), schema, "JOB_LOG_JOB_EXEC_PARAMS");
        addMetadata();
    }

    public QJobLogJobExecParams(Path<? extends JobLogJobExecParams> path) {
        super(path.getType(), path.getMetadata(), "null", "JOB_LOG_JOB_EXEC_PARAMS");
        addMetadata();
    }

    public QJobLogJobExecParams(PathMetadata metadata) {
        super(JobLogJobExecParams.class, metadata, "null", "JOB_LOG_JOB_EXEC_PARAMS");
        addMetadata();
    }

    public void addMetadata() {
        addMetadata(createdByUserLogin, ColumnMetadata.named("CREATED_BY_USER_LOGIN").withIndex(7).ofType(Types.VARCHAR).withSize(250));
        addMetadata(createdStamp, ColumnMetadata.named("CREATED_STAMP").withIndex(10).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(createdTxStamp, ColumnMetadata.named("CREATED_TX_STAMP").withIndex(11).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(jobLogId, ColumnMetadata.named("JOB_LOG_ID").withIndex(1).ofType(Types.VARCHAR).withSize(20).notNull());
        addMetadata(lastModifiedByUserLogin, ColumnMetadata.named("LAST_MODIFIED_BY_USER_LOGIN").withIndex(6).ofType(Types.VARCHAR).withSize(250));
        addMetadata(lastUpdatedStamp, ColumnMetadata.named("LAST_UPDATED_STAMP").withIndex(8).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(lastUpdatedTxStamp, ColumnMetadata.named("LAST_UPDATED_TX_STAMP").withIndex(9).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(parameterDescription, ColumnMetadata.named("PARAMETER_DESCRIPTION").withIndex(3).ofType(Types.VARCHAR).withSize(255));
        addMetadata(parameterName, ColumnMetadata.named("PARAMETER_NAME").withIndex(2).ofType(Types.VARCHAR).withSize(100).notNull());
        addMetadata(parameterType, ColumnMetadata.named("PARAMETER_TYPE").withIndex(4).ofType(Types.VARCHAR).withSize(255));
        addMetadata(parameterValue, ColumnMetadata.named("PARAMETER_VALUE").withIndex(5).ofType(Types.VARCHAR).withSize(255));
    }

}

