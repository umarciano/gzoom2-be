package it.mapsgroup.gzoom.querydsl.dto;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;

import com.querydsl.sql.ColumnMetadata;
import java.sql.Types;




/**
 * QEnumerationType is a Querydsl query type for EnumerationType
 */
@Generated("com.querydsl.sql.codegen.MetaDataSerializer")
public class QEnumerationType extends com.querydsl.sql.RelationalPathBase<EnumerationType> {

    private static final long serialVersionUID = 26178166;

    public static final QEnumerationType enumerationType = new QEnumerationType("ENUMERATION_TYPE");

    public final DateTimePath<java.time.LocalDateTime> createdStamp = createDateTime("createdStamp", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> createdTxStamp = createDateTime("createdTxStamp", java.time.LocalDateTime.class);

    public final StringPath description = createString("description");

    public final StringPath enumTypeId = createString("enumTypeId");

    public final BooleanPath hasTable = createBoolean("hasTable");

    public final DateTimePath<java.time.LocalDateTime> lastUpdatedStamp = createDateTime("lastUpdatedStamp", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> lastUpdatedTxStamp = createDateTime("lastUpdatedTxStamp", java.time.LocalDateTime.class);

    public final StringPath parentTypeId = createString("parentTypeId");

    public final com.querydsl.sql.PrimaryKey<EnumerationType> enumerationTypePk = createPrimaryKey(enumTypeId);

    public final com.querydsl.sql.ForeignKey<EnumerationType> enumTypeParent = createForeignKey(parentTypeId, "ENUM_TYPE_ID");

    public final com.querydsl.sql.ForeignKey<Enumeration> _enumToType = createInvForeignKey(enumTypeId, "ENUM_TYPE_ID");

    public final com.querydsl.sql.ForeignKey<EnumerationType> _enumTypeParent = createInvForeignKey(enumTypeId, "PARENT_TYPE_ID");

    public QEnumerationType(String variable) {
        super(EnumerationType.class, forVariable(variable), "DBO", "ENUMERATION_TYPE");
        addMetadata();
    }

    public QEnumerationType(String variable, String schema, String table) {
        super(EnumerationType.class, forVariable(variable), schema, table);
        addMetadata();
    }

    public QEnumerationType(String variable, String schema) {
        super(EnumerationType.class, forVariable(variable), schema, "ENUMERATION_TYPE");
        addMetadata();
    }

    public QEnumerationType(Path<? extends EnumerationType> path) {
        super(path.getType(), path.getMetadata(), "DBO", "ENUMERATION_TYPE");
        addMetadata();
    }

    public QEnumerationType(PathMetadata metadata) {
        super(EnumerationType.class, metadata, "DBO", "ENUMERATION_TYPE");
        addMetadata();
    }

    public void addMetadata() {
        addMetadata(createdStamp, ColumnMetadata.named("CREATED_STAMP").withIndex(7).ofType(Types.TIMESTAMP).withSize(23).withDigits(3));
        addMetadata(createdTxStamp, ColumnMetadata.named("CREATED_TX_STAMP").withIndex(8).ofType(Types.TIMESTAMP).withSize(23).withDigits(3));
        addMetadata(description, ColumnMetadata.named("DESCRIPTION").withIndex(4).ofType(Types.VARCHAR).withSize(255));
        addMetadata(enumTypeId, ColumnMetadata.named("ENUM_TYPE_ID").withIndex(1).ofType(Types.VARCHAR).withSize(20).notNull());
        addMetadata(hasTable, ColumnMetadata.named("HAS_TABLE").withIndex(3).ofType(Types.CHAR).withSize(1));
        addMetadata(lastUpdatedStamp, ColumnMetadata.named("LAST_UPDATED_STAMP").withIndex(5).ofType(Types.TIMESTAMP).withSize(23).withDigits(3));
        addMetadata(lastUpdatedTxStamp, ColumnMetadata.named("LAST_UPDATED_TX_STAMP").withIndex(6).ofType(Types.TIMESTAMP).withSize(23).withDigits(3));
        addMetadata(parentTypeId, ColumnMetadata.named("PARENT_TYPE_ID").withIndex(2).ofType(Types.VARCHAR).withSize(20));
    }

}

