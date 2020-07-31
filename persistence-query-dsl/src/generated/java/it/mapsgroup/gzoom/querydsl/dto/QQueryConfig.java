package it.mapsgroup.gzoom.querydsl.dto;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;

import com.querydsl.sql.ColumnMetadata;
import java.sql.Types;




/**
 * QQueryConfig is a Querydsl query type for QueryConfig
 */
@Generated("com.querydsl.sql.codegen.MetaDataSerializer")
public class QQueryConfig extends com.querydsl.sql.RelationalPathBase<QueryConfig> {

    private static final long serialVersionUID = -1053285121;

    public static final QQueryConfig queryConfig = new QQueryConfig("QUERY_CONFIG");

    public final StringPath cond0Comm = createString("cond0Comm");

    public final StringPath cond0Info = createString("cond0Info");

    public final StringPath cond0Name = createString("cond0Name");

    public final StringPath cond1Comm = createString("cond1Comm");

    public final StringPath cond1Info = createString("cond1Info");

    public final StringPath cond1Name = createString("cond1Name");

    public final StringPath cond2Comm = createString("cond2Comm");

    public final StringPath cond2Info = createString("cond2Info");

    public final StringPath cond2Name = createString("cond2Name");

    public final StringPath cond3Comm = createString("cond3Comm");

    public final StringPath cond3Info = createString("cond3Info");

    public final StringPath cond3Name = createString("cond3Name");

    public final StringPath cond4Comm = createString("cond4Comm");

    public final StringPath cond4Info = createString("cond4Info");

    public final StringPath cond4Name = createString("cond4Name");

    public final StringPath cond5Comm = createString("cond5Comm");

    public final StringPath cond5Info = createString("cond5Info");

    public final StringPath cond5Name = createString("cond5Name");

    public final StringPath cond6Comm = createString("cond6Comm");

    public final StringPath cond6Info = createString("cond6Info");

    public final StringPath cond6Name = createString("cond6Name");

    public final StringPath cond7Comm = createString("cond7Comm");

    public final StringPath cond7Info = createString("cond7Info");

    public final StringPath cond7Name = createString("cond7Name");

    public final StringPath cond8Comm = createString("cond8Comm");

    public final StringPath cond8Info = createString("cond8Info");

    public final StringPath cond8Name = createString("cond8Name");

    public final DateTimePath<java.time.LocalDateTime> createdStamp = createDateTime("createdStamp", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> createdTxStamp = createDateTime("createdTxStamp", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> lastUpdatedStamp = createDateTime("lastUpdatedStamp", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> lastUpdatedTxStamp = createDateTime("lastUpdatedTxStamp", java.time.LocalDateTime.class);

    public final BooleanPath queryActive = createBoolean("queryActive");

    public final StringPath queryCode = createString("queryCode");

    public final StringPath queryComm = createString("queryComm");

    public final StringPath queryCtx = createString("queryCtx");

    public final StringPath queryId = createString("queryId");

    public final StringPath queryInfo = createString("queryInfo");

    public final StringPath queryName = createString("queryName");

    public final BooleanPath queryPublic = createBoolean("queryPublic");

    public final StringPath queryType = createString("queryType");

    public final com.querydsl.sql.PrimaryKey<QueryConfig> queryConfigPk = createPrimaryKey(queryId);

    public QQueryConfig(String variable) {
        super(QueryConfig.class, forVariable(variable), "DBO", "QUERY_CONFIG");
        addMetadata();
    }

    public QQueryConfig(String variable, String schema, String table) {
        super(QueryConfig.class, forVariable(variable), schema, table);
        addMetadata();
    }

    public QQueryConfig(String variable, String schema) {
        super(QueryConfig.class, forVariable(variable), schema, "QUERY_CONFIG");
        addMetadata();
    }

    public QQueryConfig(Path<? extends QueryConfig> path) {
        super(path.getType(), path.getMetadata(), "DBO", "QUERY_CONFIG");
        addMetadata();
    }

    public QQueryConfig(PathMetadata metadata) {
        super(QueryConfig.class, metadata, "DBO", "QUERY_CONFIG");
        addMetadata();
    }

    public void addMetadata() {
        addMetadata(cond0Comm, ColumnMetadata.named("COND0_COMM").withIndex(11).ofType(Types.VARCHAR).withSize(2000));
        addMetadata(cond0Info, ColumnMetadata.named("COND0_INFO").withIndex(12).ofType(Types.VARCHAR).withSize(2000));
        addMetadata(cond0Name, ColumnMetadata.named("COND0_NAME").withIndex(10).ofType(Types.VARCHAR).withSize(255));
        addMetadata(cond1Comm, ColumnMetadata.named("COND1_COMM").withIndex(14).ofType(Types.VARCHAR).withSize(2000));
        addMetadata(cond1Info, ColumnMetadata.named("COND1_INFO").withIndex(15).ofType(Types.VARCHAR).withSize(2000));
        addMetadata(cond1Name, ColumnMetadata.named("COND1_NAME").withIndex(13).ofType(Types.VARCHAR).withSize(255));
        addMetadata(cond2Comm, ColumnMetadata.named("COND2_COMM").withIndex(17).ofType(Types.VARCHAR).withSize(2000));
        addMetadata(cond2Info, ColumnMetadata.named("COND2_INFO").withIndex(18).ofType(Types.VARCHAR).withSize(2000));
        addMetadata(cond2Name, ColumnMetadata.named("COND2_NAME").withIndex(16).ofType(Types.VARCHAR).withSize(255));
        addMetadata(cond3Comm, ColumnMetadata.named("COND3_COMM").withIndex(20).ofType(Types.VARCHAR).withSize(2000));
        addMetadata(cond3Info, ColumnMetadata.named("COND3_INFO").withIndex(21).ofType(Types.VARCHAR).withSize(2000));
        addMetadata(cond3Name, ColumnMetadata.named("COND3_NAME").withIndex(19).ofType(Types.VARCHAR).withSize(255));
        addMetadata(cond4Comm, ColumnMetadata.named("COND4_COMM").withIndex(23).ofType(Types.VARCHAR).withSize(2000));
        addMetadata(cond4Info, ColumnMetadata.named("COND4_INFO").withIndex(24).ofType(Types.VARCHAR).withSize(2000));
        addMetadata(cond4Name, ColumnMetadata.named("COND4_NAME").withIndex(22).ofType(Types.VARCHAR).withSize(255));
        addMetadata(cond5Comm, ColumnMetadata.named("COND5_COMM").withIndex(26).ofType(Types.VARCHAR).withSize(2000));
        addMetadata(cond5Info, ColumnMetadata.named("COND5_INFO").withIndex(27).ofType(Types.VARCHAR).withSize(2000));
        addMetadata(cond5Name, ColumnMetadata.named("COND5_NAME").withIndex(25).ofType(Types.VARCHAR).withSize(255));
        addMetadata(cond6Comm, ColumnMetadata.named("COND6_COMM").withIndex(29).ofType(Types.VARCHAR).withSize(2000));
        addMetadata(cond6Info, ColumnMetadata.named("COND6_INFO").withIndex(30).ofType(Types.VARCHAR).withSize(2000));
        addMetadata(cond6Name, ColumnMetadata.named("COND6_NAME").withIndex(28).ofType(Types.VARCHAR).withSize(255));
        addMetadata(cond7Comm, ColumnMetadata.named("COND7_COMM").withIndex(32).ofType(Types.VARCHAR).withSize(2000));
        addMetadata(cond7Info, ColumnMetadata.named("COND7_INFO").withIndex(33).ofType(Types.VARCHAR).withSize(2000));
        addMetadata(cond7Name, ColumnMetadata.named("COND7_NAME").withIndex(31).ofType(Types.VARCHAR).withSize(255));
        addMetadata(cond8Comm, ColumnMetadata.named("COND8_COMM").withIndex(35).ofType(Types.VARCHAR).withSize(2000));
        addMetadata(cond8Info, ColumnMetadata.named("COND8_INFO").withIndex(36).ofType(Types.VARCHAR).withSize(2000));
        addMetadata(cond8Name, ColumnMetadata.named("COND8_NAME").withIndex(34).ofType(Types.VARCHAR).withSize(255));
        addMetadata(createdStamp, ColumnMetadata.named("CREATED_STAMP").withIndex(42).ofType(Types.TIMESTAMP).withSize(23).withDigits(3));
        addMetadata(createdTxStamp, ColumnMetadata.named("CREATED_TX_STAMP").withIndex(43).ofType(Types.TIMESTAMP).withSize(23).withDigits(3));
        addMetadata(lastUpdatedStamp, ColumnMetadata.named("LAST_UPDATED_STAMP").withIndex(40).ofType(Types.TIMESTAMP).withSize(23).withDigits(3));
        addMetadata(lastUpdatedTxStamp, ColumnMetadata.named("LAST_UPDATED_TX_STAMP").withIndex(41).ofType(Types.TIMESTAMP).withSize(23).withDigits(3));
        addMetadata(queryActive, ColumnMetadata.named("QUERY_ACTIVE").withIndex(8).ofType(Types.CHAR).withSize(1));
        addMetadata(queryCode, ColumnMetadata.named("QUERY_CODE").withIndex(2).ofType(Types.VARCHAR).withSize(20));
        addMetadata(queryComm, ColumnMetadata.named("QUERY_COMM").withIndex(4).ofType(Types.VARCHAR).withSize(2000));
        addMetadata(queryCtx, ColumnMetadata.named("QUERY_CTX").withIndex(6).ofType(Types.VARCHAR).withSize(20));
        addMetadata(queryId, ColumnMetadata.named("QUERY_ID").withIndex(1).ofType(Types.VARCHAR).withSize(20).notNull());
        addMetadata(queryInfo, ColumnMetadata.named("QUERY_INFO").withIndex(9).ofType(Types.LONGVARCHAR).withSize(2147483647));
        addMetadata(queryName, ColumnMetadata.named("QUERY_NAME").withIndex(3).ofType(Types.VARCHAR).withSize(255));
        addMetadata(queryPublic, ColumnMetadata.named("QUERY_PUBLIC").withIndex(7).ofType(Types.CHAR).withSize(1));
        addMetadata(queryType, ColumnMetadata.named("QUERY_TYPE").withIndex(5).ofType(Types.CHAR).withSize(1));
    }

}

