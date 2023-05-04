package it.mapsgroup.gzoom.querydsl.dto;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;

import com.querydsl.sql.ColumnMetadata;
import java.sql.Types;




/**
 * QDataResourceType is a Querydsl query type for DataResourceType
 */
@Generated("com.querydsl.sql.codegen.MetaDataSerializer")
public class QDataResourceType extends com.querydsl.sql.RelationalPathBase<DataResourceType> {

    private static final long serialVersionUID = 1074286461;

    public static final QDataResourceType dataResourceType = new QDataResourceType("DATA_RESOURCE_TYPE");

    public final DateTimePath<java.time.LocalDateTime> createdStamp = createDateTime("createdStamp", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> createdTxStamp = createDateTime("createdTxStamp", java.time.LocalDateTime.class);

    public final StringPath dataResourceTypeId = createString("dataResourceTypeId");

    public final StringPath description = createString("description");

    public final BooleanPath hasTable = createBoolean("hasTable");

    public final DateTimePath<java.time.LocalDateTime> lastUpdatedStamp = createDateTime("lastUpdatedStamp", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> lastUpdatedTxStamp = createDateTime("lastUpdatedTxStamp", java.time.LocalDateTime.class);

    public final StringPath parentTypeId = createString("parentTypeId");

    public final com.querydsl.sql.PrimaryKey<DataResourceType> primary = createPrimaryKey(dataResourceTypeId);

    public QDataResourceType(String variable) {
        super(DataResourceType.class, forVariable(variable), "null", "DATA_RESOURCE_TYPE");
        addMetadata();
    }

    public QDataResourceType(String variable, String schema, String table) {
        super(DataResourceType.class, forVariable(variable), schema, table);
        addMetadata();
    }

    public QDataResourceType(String variable, String schema) {
        super(DataResourceType.class, forVariable(variable), schema, "DATA_RESOURCE_TYPE");
        addMetadata();
    }

    public QDataResourceType(Path<? extends DataResourceType> path) {
        super(path.getType(), path.getMetadata(), "null", "DATA_RESOURCE_TYPE");
        addMetadata();
    }

    public QDataResourceType(PathMetadata metadata) {
        super(DataResourceType.class, metadata, "null", "DATA_RESOURCE_TYPE");
        addMetadata();
    }

    public void addMetadata() {
        addMetadata(createdStamp, ColumnMetadata.named("CREATED_STAMP").withIndex(7).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(createdTxStamp, ColumnMetadata.named("CREATED_TX_STAMP").withIndex(8).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(dataResourceTypeId, ColumnMetadata.named("DATA_RESOURCE_TYPE_ID").withIndex(1).ofType(Types.VARCHAR).withSize(20).notNull());
        addMetadata(description, ColumnMetadata.named("DESCRIPTION").withIndex(4).ofType(Types.VARCHAR).withSize(255));
        addMetadata(hasTable, ColumnMetadata.named("HAS_TABLE").withIndex(3).ofType(Types.CHAR).withSize(1));
        addMetadata(lastUpdatedStamp, ColumnMetadata.named("LAST_UPDATED_STAMP").withIndex(5).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(lastUpdatedTxStamp, ColumnMetadata.named("LAST_UPDATED_TX_STAMP").withIndex(6).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(parentTypeId, ColumnMetadata.named("PARENT_TYPE_ID").withIndex(2).ofType(Types.VARCHAR).withSize(20));
    }

}

