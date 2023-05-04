package it.mapsgroup.gzoom.querydsl.dto;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;

import com.querydsl.sql.ColumnMetadata;
import java.sql.Types;




/**
 * QDataSourceType is a Querydsl query type for DataSourceType
 */
@Generated("com.querydsl.sql.codegen.MetaDataSerializer")
public class QDataSourceType extends com.querydsl.sql.RelationalPathBase<DataSourceType> {

    private static final long serialVersionUID = 280028746;

    public static final QDataSourceType dataSourceType = new QDataSourceType("DATA_SOURCE_TYPE");

    public final StringPath createdByUserLogin = createString("createdByUserLogin");

    public final DateTimePath<java.time.LocalDateTime> createdStamp = createDateTime("createdStamp", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> createdTxStamp = createDateTime("createdTxStamp", java.time.LocalDateTime.class);

    public final StringPath dataSourceTypeId = createString("dataSourceTypeId");

    public final StringPath description = createString("description");

    public final StringPath lastModifiedByUserLogin = createString("lastModifiedByUserLogin");

    public final DateTimePath<java.time.LocalDateTime> lastUpdatedStamp = createDateTime("lastUpdatedStamp", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> lastUpdatedTxStamp = createDateTime("lastUpdatedTxStamp", java.time.LocalDateTime.class);

    public final com.querydsl.sql.PrimaryKey<DataSourceType> primary = createPrimaryKey(dataSourceTypeId);

    public QDataSourceType(String variable) {
        super(DataSourceType.class, forVariable(variable), "null", "DATA_SOURCE_TYPE");
        addMetadata();
    }

    public QDataSourceType(String variable, String schema, String table) {
        super(DataSourceType.class, forVariable(variable), schema, table);
        addMetadata();
    }

    public QDataSourceType(String variable, String schema) {
        super(DataSourceType.class, forVariable(variable), schema, "DATA_SOURCE_TYPE");
        addMetadata();
    }

    public QDataSourceType(Path<? extends DataSourceType> path) {
        super(path.getType(), path.getMetadata(), "null", "DATA_SOURCE_TYPE");
        addMetadata();
    }

    public QDataSourceType(PathMetadata metadata) {
        super(DataSourceType.class, metadata, "null", "DATA_SOURCE_TYPE");
        addMetadata();
    }

    public void addMetadata() {
        addMetadata(createdByUserLogin, ColumnMetadata.named("CREATED_BY_USER_LOGIN").withIndex(8).ofType(Types.VARCHAR).withSize(250));
        addMetadata(createdStamp, ColumnMetadata.named("CREATED_STAMP").withIndex(5).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(createdTxStamp, ColumnMetadata.named("CREATED_TX_STAMP").withIndex(6).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(dataSourceTypeId, ColumnMetadata.named("DATA_SOURCE_TYPE_ID").withIndex(1).ofType(Types.VARCHAR).withSize(20).notNull());
        addMetadata(description, ColumnMetadata.named("DESCRIPTION").withIndex(2).ofType(Types.VARCHAR).withSize(255));
        addMetadata(lastModifiedByUserLogin, ColumnMetadata.named("LAST_MODIFIED_BY_USER_LOGIN").withIndex(7).ofType(Types.VARCHAR).withSize(250));
        addMetadata(lastUpdatedStamp, ColumnMetadata.named("LAST_UPDATED_STAMP").withIndex(3).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(lastUpdatedTxStamp, ColumnMetadata.named("LAST_UPDATED_TX_STAMP").withIndex(4).ofType(Types.TIMESTAMP).withSize(26));
    }

}

