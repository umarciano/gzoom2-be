package it.mapsgroup.gzoom.querydsl.dto;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;

import com.querydsl.sql.ColumnMetadata;
import java.sql.Types;




/**
 * QCustomTimePeriod is a Querydsl query type for CustomTimePeriod
 */
@Generated("com.querydsl.sql.codegen.MetaDataSerializer")
public class QCustomTimePeriod extends com.querydsl.sql.RelationalPathBase<CustomTimePeriod> {

    private static final long serialVersionUID = -1641848950;

    public static final QCustomTimePeriod customTimePeriod = new QCustomTimePeriod("CUSTOM_TIME_PERIOD");

    public final StringPath createdByUserLogin = createString("createdByUserLogin");

    public final DateTimePath<java.time.LocalDateTime> createdStamp = createDateTime("createdStamp", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> createdTxStamp = createDateTime("createdTxStamp", java.time.LocalDateTime.class);

    public final StringPath customTimePeriodCode = createString("customTimePeriodCode");

    public final StringPath customTimePeriodCodeLang = createString("customTimePeriodCodeLang");

    public final StringPath customTimePeriodId = createString("customTimePeriodId");

    public final DateTimePath<java.time.LocalDateTime> fromDate = createDateTime("fromDate", java.time.LocalDateTime.class);

    public final BooleanPath isClosed = createBoolean("isClosed");

    public final StringPath lastModifiedByUserLogin = createString("lastModifiedByUserLogin");

    public final DateTimePath<java.time.LocalDateTime> lastUpdatedStamp = createDateTime("lastUpdatedStamp", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> lastUpdatedTxStamp = createDateTime("lastUpdatedTxStamp", java.time.LocalDateTime.class);

    public final StringPath parentPeriodId = createString("parentPeriodId");

    public final StringPath periodName = createString("periodName");

    public final StringPath periodNameLang = createString("periodNameLang");

    public final NumberPath<java.math.BigInteger> periodNum = createNumber("periodNum", java.math.BigInteger.class);

    public final StringPath periodTypeId = createString("periodTypeId");

    public final DateTimePath<java.time.LocalDateTime> thruDate = createDateTime("thruDate", java.time.LocalDateTime.class);

    public final com.querydsl.sql.PrimaryKey<CustomTimePeriod> primary = createPrimaryKey(customTimePeriodId);

    public QCustomTimePeriod(String variable) {
        super(CustomTimePeriod.class, forVariable(variable), "null", "CUSTOM_TIME_PERIOD");
        addMetadata();
    }

    public QCustomTimePeriod(String variable, String schema, String table) {
        super(CustomTimePeriod.class, forVariable(variable), schema, table);
        addMetadata();
    }

    public QCustomTimePeriod(String variable, String schema) {
        super(CustomTimePeriod.class, forVariable(variable), schema, "CUSTOM_TIME_PERIOD");
        addMetadata();
    }

    public QCustomTimePeriod(Path<? extends CustomTimePeriod> path) {
        super(path.getType(), path.getMetadata(), "null", "CUSTOM_TIME_PERIOD");
        addMetadata();
    }

    public QCustomTimePeriod(PathMetadata metadata) {
        super(CustomTimePeriod.class, metadata, "null", "CUSTOM_TIME_PERIOD");
        addMetadata();
    }

    public void addMetadata() {
        addMetadata(createdByUserLogin, ColumnMetadata.named("CREATED_BY_USER_LOGIN").withIndex(17).ofType(Types.VARCHAR).withSize(250));
        addMetadata(createdStamp, ColumnMetadata.named("CREATED_STAMP").withIndex(11).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(createdTxStamp, ColumnMetadata.named("CREATED_TX_STAMP").withIndex(12).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(customTimePeriodCode, ColumnMetadata.named("CUSTOM_TIME_PERIOD_CODE").withIndex(13).ofType(Types.VARCHAR).withSize(60));
        addMetadata(customTimePeriodCodeLang, ColumnMetadata.named("CUSTOM_TIME_PERIOD_CODE_LANG").withIndex(14).ofType(Types.VARCHAR).withSize(60));
        addMetadata(customTimePeriodId, ColumnMetadata.named("CUSTOM_TIME_PERIOD_ID").withIndex(1).ofType(Types.VARCHAR).withSize(20).notNull());
        addMetadata(fromDate, ColumnMetadata.named("FROM_DATE").withIndex(6).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(isClosed, ColumnMetadata.named("IS_CLOSED").withIndex(8).ofType(Types.CHAR).withSize(1));
        addMetadata(lastModifiedByUserLogin, ColumnMetadata.named("LAST_MODIFIED_BY_USER_LOGIN").withIndex(16).ofType(Types.VARCHAR).withSize(250));
        addMetadata(lastUpdatedStamp, ColumnMetadata.named("LAST_UPDATED_STAMP").withIndex(9).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(lastUpdatedTxStamp, ColumnMetadata.named("LAST_UPDATED_TX_STAMP").withIndex(10).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(parentPeriodId, ColumnMetadata.named("PARENT_PERIOD_ID").withIndex(2).ofType(Types.VARCHAR).withSize(20));
        addMetadata(periodName, ColumnMetadata.named("PERIOD_NAME").withIndex(5).ofType(Types.VARCHAR).withSize(100));
        addMetadata(periodNameLang, ColumnMetadata.named("PERIOD_NAME_LANG").withIndex(15).ofType(Types.VARCHAR).withSize(100));
        addMetadata(periodNum, ColumnMetadata.named("PERIOD_NUM").withIndex(4).ofType(Types.DECIMAL).withSize(20));
        addMetadata(periodTypeId, ColumnMetadata.named("PERIOD_TYPE_ID").withIndex(3).ofType(Types.VARCHAR).withSize(20));
        addMetadata(thruDate, ColumnMetadata.named("THRU_DATE").withIndex(7).ofType(Types.TIMESTAMP).withSize(26));
    }

}

