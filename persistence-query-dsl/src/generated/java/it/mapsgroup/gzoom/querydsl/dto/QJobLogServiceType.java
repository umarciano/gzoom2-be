package it.mapsgroup.gzoom.querydsl.dto;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;

import com.querydsl.sql.ColumnMetadata;
import java.sql.Types;




/**
 * QJobLogServiceType is a Querydsl query type for JobLogServiceType
 */
@Generated("com.querydsl.sql.codegen.MetaDataSerializer")
public class QJobLogServiceType extends com.querydsl.sql.RelationalPathBase<JobLogServiceType> {

    private static final long serialVersionUID = 749744605;

    public static final QJobLogServiceType jobLogServiceType = new QJobLogServiceType("JOB_LOG_SERVICE_TYPE");

    public final StringPath createdByUserLogin = createString("createdByUserLogin");

    public final DateTimePath<java.time.LocalDateTime> createdStamp = createDateTime("createdStamp", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> createdTxStamp = createDateTime("createdTxStamp", java.time.LocalDateTime.class);

    public final StringPath description = createString("description");

    public final StringPath lastModifiedByUserLogin = createString("lastModifiedByUserLogin");

    public final DateTimePath<java.time.LocalDateTime> lastUpdatedStamp = createDateTime("lastUpdatedStamp", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> lastUpdatedTxStamp = createDateTime("lastUpdatedTxStamp", java.time.LocalDateTime.class);

    public final BooleanPath logInfo = createBoolean("logInfo");

    public final StringPath serviceTypeId = createString("serviceTypeId");

    public final com.querydsl.sql.PrimaryKey<JobLogServiceType> primary = createPrimaryKey(serviceTypeId);

    public final com.querydsl.sql.ForeignKey<JobLog> _jlstFk = createInvForeignKey(serviceTypeId, "SERVICE_TYPE_ID");

    public QJobLogServiceType(String variable) {
        super(JobLogServiceType.class, forVariable(variable), "null", "JOB_LOG_SERVICE_TYPE");
        addMetadata();
    }

    public QJobLogServiceType(String variable, String schema, String table) {
        super(JobLogServiceType.class, forVariable(variable), schema, table);
        addMetadata();
    }

    public QJobLogServiceType(String variable, String schema) {
        super(JobLogServiceType.class, forVariable(variable), schema, "JOB_LOG_SERVICE_TYPE");
        addMetadata();
    }

    public QJobLogServiceType(Path<? extends JobLogServiceType> path) {
        super(path.getType(), path.getMetadata(), "null", "JOB_LOG_SERVICE_TYPE");
        addMetadata();
    }

    public QJobLogServiceType(PathMetadata metadata) {
        super(JobLogServiceType.class, metadata, "null", "JOB_LOG_SERVICE_TYPE");
        addMetadata();
    }

    public void addMetadata() {
        addMetadata(createdByUserLogin, ColumnMetadata.named("CREATED_BY_USER_LOGIN").withIndex(5).ofType(Types.VARCHAR).withSize(250));
        addMetadata(createdStamp, ColumnMetadata.named("CREATED_STAMP").withIndex(8).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(createdTxStamp, ColumnMetadata.named("CREATED_TX_STAMP").withIndex(9).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(description, ColumnMetadata.named("DESCRIPTION").withIndex(2).ofType(Types.VARCHAR).withSize(255));
        addMetadata(lastModifiedByUserLogin, ColumnMetadata.named("LAST_MODIFIED_BY_USER_LOGIN").withIndex(4).ofType(Types.VARCHAR).withSize(250));
        addMetadata(lastUpdatedStamp, ColumnMetadata.named("LAST_UPDATED_STAMP").withIndex(6).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(lastUpdatedTxStamp, ColumnMetadata.named("LAST_UPDATED_TX_STAMP").withIndex(7).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(logInfo, ColumnMetadata.named("LOG_INFO").withIndex(3).ofType(Types.CHAR).withSize(1));
        addMetadata(serviceTypeId, ColumnMetadata.named("SERVICE_TYPE_ID").withIndex(1).ofType(Types.VARCHAR).withSize(20).notNull());
    }

}

