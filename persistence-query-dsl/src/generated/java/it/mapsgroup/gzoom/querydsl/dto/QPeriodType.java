package it.mapsgroup.gzoom.querydsl.dto;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;

import com.querydsl.sql.ColumnMetadata;
import java.sql.Types;




/**
 * QPeriodType is a Querydsl query type for PeriodType
 */
@Generated("com.querydsl.sql.codegen.MetaDataSerializer")
public class QPeriodType extends com.querydsl.sql.RelationalPathBase<PeriodType> {

    private static final long serialVersionUID = -840945882;

    public static final QPeriodType periodType = new QPeriodType("PERIOD_TYPE");

    public final StringPath createdByUserLogin = createString("createdByUserLogin");

    public final DateTimePath<java.time.LocalDateTime> createdStamp = createDateTime("createdStamp", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> createdTxStamp = createDateTime("createdTxStamp", java.time.LocalDateTime.class);

    public final StringPath description = createString("description");

    public final StringPath lastModifiedByUserLogin = createString("lastModifiedByUserLogin");

    public final DateTimePath<java.time.LocalDateTime> lastUpdatedStamp = createDateTime("lastUpdatedStamp", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> lastUpdatedTxStamp = createDateTime("lastUpdatedTxStamp", java.time.LocalDateTime.class);

    public final NumberPath<java.math.BigInteger> periodLength = createNumber("periodLength", java.math.BigInteger.class);

    public final StringPath periodTypeId = createString("periodTypeId");

    public final StringPath uomId = createString("uomId");

    public final com.querydsl.sql.PrimaryKey<PeriodType> primary = createPrimaryKey(periodTypeId);

    public QPeriodType(String variable) {
        super(PeriodType.class, forVariable(variable), "null", "PERIOD_TYPE");
        addMetadata();
    }

    public QPeriodType(String variable, String schema, String table) {
        super(PeriodType.class, forVariable(variable), schema, table);
        addMetadata();
    }

    public QPeriodType(String variable, String schema) {
        super(PeriodType.class, forVariable(variable), schema, "PERIOD_TYPE");
        addMetadata();
    }

    public QPeriodType(Path<? extends PeriodType> path) {
        super(path.getType(), path.getMetadata(), "null", "PERIOD_TYPE");
        addMetadata();
    }

    public QPeriodType(PathMetadata metadata) {
        super(PeriodType.class, metadata, "null", "PERIOD_TYPE");
        addMetadata();
    }

    public void addMetadata() {
        addMetadata(createdByUserLogin, ColumnMetadata.named("CREATED_BY_USER_LOGIN").withIndex(10).ofType(Types.VARCHAR).withSize(250));
        addMetadata(createdStamp, ColumnMetadata.named("CREATED_STAMP").withIndex(7).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(createdTxStamp, ColumnMetadata.named("CREATED_TX_STAMP").withIndex(8).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(description, ColumnMetadata.named("DESCRIPTION").withIndex(2).ofType(Types.VARCHAR).withSize(255));
        addMetadata(lastModifiedByUserLogin, ColumnMetadata.named("LAST_MODIFIED_BY_USER_LOGIN").withIndex(9).ofType(Types.VARCHAR).withSize(250));
        addMetadata(lastUpdatedStamp, ColumnMetadata.named("LAST_UPDATED_STAMP").withIndex(5).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(lastUpdatedTxStamp, ColumnMetadata.named("LAST_UPDATED_TX_STAMP").withIndex(6).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(periodLength, ColumnMetadata.named("PERIOD_LENGTH").withIndex(3).ofType(Types.DECIMAL).withSize(20));
        addMetadata(periodTypeId, ColumnMetadata.named("PERIOD_TYPE_ID").withIndex(1).ofType(Types.VARCHAR).withSize(20).notNull());
        addMetadata(uomId, ColumnMetadata.named("UOM_ID").withIndex(4).ofType(Types.VARCHAR).withSize(20));
    }

}

