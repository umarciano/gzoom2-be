package it.mapsgroup.gzoom.querydsl.dto;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;

import com.querydsl.sql.ColumnMetadata;
import java.sql.Types;




/**
 * QUomRangeValues is a Querydsl query type for UomRangeValues
 */
@Generated("com.querydsl.sql.codegen.MetaDataSerializer")
public class QUomRangeValues extends com.querydsl.sql.RelationalPathBase<UomRangeValues> {

    private static final long serialVersionUID = 2142595767;

    public static final QUomRangeValues uomRangeValues = new QUomRangeValues("UOM_RANGE_VALUES");

    public final BooleanPath alert = createBoolean("alert");

    public final StringPath colorEnumId = createString("colorEnumId");

    public final StringPath comments = createString("comments");

    public final StringPath commentsLang = createString("commentsLang");

    public final StringPath createdByUserLogin = createString("createdByUserLogin");

    public final DateTimePath<java.time.LocalDateTime> createdStamp = createDateTime("createdStamp", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> createdTxStamp = createDateTime("createdTxStamp", java.time.LocalDateTime.class);

    public final NumberPath<java.math.BigDecimal> fromValue = createNumber("fromValue", java.math.BigDecimal.class);

    public final StringPath iconContentId = createString("iconContentId");

    public final BooleanPath isPositive = createBoolean("isPositive");

    public final StringPath lastModifiedByUserLogin = createString("lastModifiedByUserLogin");

    public final DateTimePath<java.time.LocalDateTime> lastUpdatedStamp = createDateTime("lastUpdatedStamp", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> lastUpdatedTxStamp = createDateTime("lastUpdatedTxStamp", java.time.LocalDateTime.class);

    public final BooleanPath prorateRange = createBoolean("prorateRange");

    public final NumberPath<java.math.BigDecimal> rangeValuesFactor = createNumber("rangeValuesFactor", java.math.BigDecimal.class);

    public final NumberPath<java.math.BigDecimal> rangeValuesFactorMin = createNumber("rangeValuesFactorMin", java.math.BigDecimal.class);

    public final NumberPath<java.math.BigDecimal> thruValue = createNumber("thruValue", java.math.BigDecimal.class);

    public final StringPath uomRangeId = createString("uomRangeId");

    public final StringPath uomRangeValuesId = createString("uomRangeValuesId");

    public final com.querydsl.sql.PrimaryKey<UomRangeValues> primary = createPrimaryKey(uomRangeValuesId);

    public final com.querydsl.sql.ForeignKey<Content> rangevlToCont = createForeignKey(iconContentId, "CONTENT_ID");

    public final com.querydsl.sql.ForeignKey<Enumeration> color = createForeignKey(colorEnumId, "ENUM_ID");

    public QUomRangeValues(String variable) {
        super(UomRangeValues.class, forVariable(variable), "null", "UOM_RANGE_VALUES");
        addMetadata();
    }

    public QUomRangeValues(String variable, String schema, String table) {
        super(UomRangeValues.class, forVariable(variable), schema, table);
        addMetadata();
    }

    public QUomRangeValues(String variable, String schema) {
        super(UomRangeValues.class, forVariable(variable), schema, "UOM_RANGE_VALUES");
        addMetadata();
    }

    public QUomRangeValues(Path<? extends UomRangeValues> path) {
        super(path.getType(), path.getMetadata(), "null", "UOM_RANGE_VALUES");
        addMetadata();
    }

    public QUomRangeValues(PathMetadata metadata) {
        super(UomRangeValues.class, metadata, "null", "UOM_RANGE_VALUES");
        addMetadata();
    }

    public void addMetadata() {
        addMetadata(alert, ColumnMetadata.named("ALERT").withIndex(8).ofType(Types.CHAR).withSize(1));
        addMetadata(colorEnumId, ColumnMetadata.named("COLOR_ENUM_ID").withIndex(10).ofType(Types.VARCHAR).withSize(20));
        addMetadata(comments, ColumnMetadata.named("COMMENTS").withIndex(3).ofType(Types.VARCHAR).withSize(255));
        addMetadata(commentsLang, ColumnMetadata.named("COMMENTS_LANG").withIndex(19).ofType(Types.VARCHAR).withSize(255));
        addMetadata(createdByUserLogin, ColumnMetadata.named("CREATED_BY_USER_LOGIN").withIndex(13).ofType(Types.VARCHAR).withSize(250));
        addMetadata(createdStamp, ColumnMetadata.named("CREATED_STAMP").withIndex(16).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(createdTxStamp, ColumnMetadata.named("CREATED_TX_STAMP").withIndex(17).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(fromValue, ColumnMetadata.named("FROM_VALUE").withIndex(5).ofType(Types.DECIMAL).withSize(18).withDigits(6));
        addMetadata(iconContentId, ColumnMetadata.named("ICON_CONTENT_ID").withIndex(7).ofType(Types.VARCHAR).withSize(20));
        addMetadata(isPositive, ColumnMetadata.named("IS_POSITIVE").withIndex(4).ofType(Types.CHAR).withSize(1));
        addMetadata(lastModifiedByUserLogin, ColumnMetadata.named("LAST_MODIFIED_BY_USER_LOGIN").withIndex(12).ofType(Types.VARCHAR).withSize(250));
        addMetadata(lastUpdatedStamp, ColumnMetadata.named("LAST_UPDATED_STAMP").withIndex(14).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(lastUpdatedTxStamp, ColumnMetadata.named("LAST_UPDATED_TX_STAMP").withIndex(15).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(prorateRange, ColumnMetadata.named("PRORATE_RANGE").withIndex(11).ofType(Types.CHAR).withSize(1));
        addMetadata(rangeValuesFactor, ColumnMetadata.named("RANGE_VALUES_FACTOR").withIndex(9).ofType(Types.DECIMAL).withSize(18).withDigits(6));
        addMetadata(rangeValuesFactorMin, ColumnMetadata.named("RANGE_VALUES_FACTOR_MIN").withIndex(18).ofType(Types.DECIMAL).withSize(18).withDigits(6));
        addMetadata(thruValue, ColumnMetadata.named("THRU_VALUE").withIndex(6).ofType(Types.DECIMAL).withSize(18).withDigits(6));
        addMetadata(uomRangeId, ColumnMetadata.named("UOM_RANGE_ID").withIndex(1).ofType(Types.VARCHAR).withSize(20));
        addMetadata(uomRangeValuesId, ColumnMetadata.named("UOM_RANGE_VALUES_ID").withIndex(2).ofType(Types.VARCHAR).withSize(20).notNull());
    }

}

