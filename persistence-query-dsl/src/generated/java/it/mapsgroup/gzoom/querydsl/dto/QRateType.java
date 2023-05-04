package it.mapsgroup.gzoom.querydsl.dto;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;

import com.querydsl.sql.ColumnMetadata;
import java.sql.Types;




/**
 * QRateType is a Querydsl query type for RateType
 */
@Generated("com.querydsl.sql.codegen.MetaDataSerializer")
public class QRateType extends com.querydsl.sql.RelationalPathBase<RateType> {

    private static final long serialVersionUID = 1953990021;

    public static final QRateType rateType = new QRateType("RATE_TYPE");

    public final DateTimePath<java.time.LocalDateTime> createdStamp = createDateTime("createdStamp", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> createdTxStamp = createDateTime("createdTxStamp", java.time.LocalDateTime.class);

    public final StringPath description = createString("description");

    public final DateTimePath<java.time.LocalDateTime> lastUpdatedStamp = createDateTime("lastUpdatedStamp", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> lastUpdatedTxStamp = createDateTime("lastUpdatedTxStamp", java.time.LocalDateTime.class);

    public final StringPath rateTypeId = createString("rateTypeId");

    public final com.querydsl.sql.PrimaryKey<RateType> primary = createPrimaryKey(rateTypeId);

    public QRateType(String variable) {
        super(RateType.class, forVariable(variable), "null", "RATE_TYPE");
        addMetadata();
    }

    public QRateType(String variable, String schema, String table) {
        super(RateType.class, forVariable(variable), schema, table);
        addMetadata();
    }

    public QRateType(String variable, String schema) {
        super(RateType.class, forVariable(variable), schema, "RATE_TYPE");
        addMetadata();
    }

    public QRateType(Path<? extends RateType> path) {
        super(path.getType(), path.getMetadata(), "null", "RATE_TYPE");
        addMetadata();
    }

    public QRateType(PathMetadata metadata) {
        super(RateType.class, metadata, "null", "RATE_TYPE");
        addMetadata();
    }

    public void addMetadata() {
        addMetadata(createdStamp, ColumnMetadata.named("CREATED_STAMP").withIndex(5).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(createdTxStamp, ColumnMetadata.named("CREATED_TX_STAMP").withIndex(6).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(description, ColumnMetadata.named("DESCRIPTION").withIndex(2).ofType(Types.VARCHAR).withSize(255));
        addMetadata(lastUpdatedStamp, ColumnMetadata.named("LAST_UPDATED_STAMP").withIndex(3).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(lastUpdatedTxStamp, ColumnMetadata.named("LAST_UPDATED_TX_STAMP").withIndex(4).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(rateTypeId, ColumnMetadata.named("RATE_TYPE_ID").withIndex(1).ofType(Types.VARCHAR).withSize(20).notNull());
    }

}

