package it.mapsgroup.gzoom.querydsl.dto;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;

import com.querydsl.sql.ColumnMetadata;
import java.sql.Types;




/**
 * QUomType is a Querydsl query type for UomType
 */
@Generated("com.querydsl.sql.codegen.MetaDataSerializer")
public class QUomType extends com.querydsl.sql.RelationalPathBase<UomType> {

    private static final long serialVersionUID = -2145383358;

    public static final QUomType uomType = new QUomType("UOM_TYPE");

    public final StringPath createdByUserLogin = createString("createdByUserLogin");

    public final DateTimePath<java.time.LocalDateTime> createdStamp = createDateTime("createdStamp", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> createdTxStamp = createDateTime("createdTxStamp", java.time.LocalDateTime.class);

    public final StringPath description = createString("description");

    public final BooleanPath hasTable = createBoolean("hasTable");

    public final BooleanPath isReserved = createBoolean("isReserved");

    public final StringPath lastModifiedByUserLogin = createString("lastModifiedByUserLogin");

    public final DateTimePath<java.time.LocalDateTime> lastUpdatedStamp = createDateTime("lastUpdatedStamp", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> lastUpdatedTxStamp = createDateTime("lastUpdatedTxStamp", java.time.LocalDateTime.class);

    public final StringPath parentTypeId = createString("parentTypeId");

    public final StringPath uomTypeId = createString("uomTypeId");

    public final com.querydsl.sql.PrimaryKey<UomType> primary = createPrimaryKey(uomTypeId);

    public final com.querydsl.sql.ForeignKey<UomType> uomTypeParent = createForeignKey(parentTypeId, "UOM_TYPE_ID");

    public final com.querydsl.sql.ForeignKey<UomType> _uomTypeParent = createInvForeignKey(uomTypeId, "PARENT_TYPE_ID");

    public final com.querydsl.sql.ForeignKey<Uom> _uomToType = createInvForeignKey(uomTypeId, "UOM_TYPE_ID");

    public QUomType(String variable) {
        super(UomType.class, forVariable(variable), "null", "UOM_TYPE");
        addMetadata();
    }

    public QUomType(String variable, String schema, String table) {
        super(UomType.class, forVariable(variable), schema, table);
        addMetadata();
    }

    public QUomType(String variable, String schema) {
        super(UomType.class, forVariable(variable), schema, "UOM_TYPE");
        addMetadata();
    }

    public QUomType(Path<? extends UomType> path) {
        super(path.getType(), path.getMetadata(), "null", "UOM_TYPE");
        addMetadata();
    }

    public QUomType(PathMetadata metadata) {
        super(UomType.class, metadata, "null", "UOM_TYPE");
        addMetadata();
    }

    public void addMetadata() {
        addMetadata(createdByUserLogin, ColumnMetadata.named("CREATED_BY_USER_LOGIN").withIndex(11).ofType(Types.VARCHAR).withSize(250));
        addMetadata(createdStamp, ColumnMetadata.named("CREATED_STAMP").withIndex(7).ofType(Types.TIMESTAMP).withSize(19));
        addMetadata(createdTxStamp, ColumnMetadata.named("CREATED_TX_STAMP").withIndex(8).ofType(Types.TIMESTAMP).withSize(19));
        addMetadata(description, ColumnMetadata.named("DESCRIPTION").withIndex(4).ofType(Types.VARCHAR).withSize(255));
        addMetadata(hasTable, ColumnMetadata.named("HAS_TABLE").withIndex(3).ofType(Types.CHAR).withSize(1));
        addMetadata(isReserved, ColumnMetadata.named("IS_RESERVED").withIndex(9).ofType(Types.CHAR).withSize(1));
        addMetadata(lastModifiedByUserLogin, ColumnMetadata.named("LAST_MODIFIED_BY_USER_LOGIN").withIndex(10).ofType(Types.VARCHAR).withSize(250));
        addMetadata(lastUpdatedStamp, ColumnMetadata.named("LAST_UPDATED_STAMP").withIndex(5).ofType(Types.TIMESTAMP).withSize(19));
        addMetadata(lastUpdatedTxStamp, ColumnMetadata.named("LAST_UPDATED_TX_STAMP").withIndex(6).ofType(Types.TIMESTAMP).withSize(19));
        addMetadata(parentTypeId, ColumnMetadata.named("PARENT_TYPE_ID").withIndex(2).ofType(Types.VARCHAR).withSize(20));
        addMetadata(uomTypeId, ColumnMetadata.named("UOM_TYPE_ID").withIndex(1).ofType(Types.VARCHAR).withSize(20).notNull());
    }

}

