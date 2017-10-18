package it.mapsgroup.gzoom.querydsl.dto;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;

import com.querydsl.sql.ColumnMetadata;
import java.sql.Types;




/**
 * QUomRatingScale is a Querydsl query type for UomRatingScale
 */
@Generated("com.querydsl.sql.codegen.MetaDataSerializer")
public class QUomRatingScale extends com.querydsl.sql.RelationalPathBase<UomRatingScale> {

    private static final long serialVersionUID = -1050465371;

    public static final QUomRatingScale uomRatingScale = new QUomRatingScale("UOM_RATING_SCALE");

    public final StringPath createdByUserLogin = createString("createdByUserLogin");

    public final DateTimePath<java.time.LocalDateTime> createdStamp = createDateTime("createdStamp", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> createdTxStamp = createDateTime("createdTxStamp", java.time.LocalDateTime.class);

    public final StringPath description = createString("description");

    public final StringPath descriptionLang = createString("descriptionLang");

    public final StringPath iconContentId = createString("iconContentId");

    public final StringPath lastModifiedByUserLogin = createString("lastModifiedByUserLogin");

    public final DateTimePath<java.time.LocalDateTime> lastUpdatedStamp = createDateTime("lastUpdatedStamp", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> lastUpdatedTxStamp = createDateTime("lastUpdatedTxStamp", java.time.LocalDateTime.class);

    public final StringPath uomId = createString("uomId");

    public final NumberPath<java.math.BigDecimal> uomRatingValue = createNumber("uomRatingValue", java.math.BigDecimal.class);

    public final com.querydsl.sql.PrimaryKey<UomRatingScale> primary = createPrimaryKey(uomId, uomRatingValue);

    public final com.querydsl.sql.ForeignKey<Uom> ratingToUom = createForeignKey(uomId, "UOM_ID");

    public final com.querydsl.sql.ForeignKey<Content> ratingToCont = createForeignKey(iconContentId, "CONTENT_ID");

    public QUomRatingScale(String variable) {
        super(UomRatingScale.class, forVariable(variable), "null", "UOM_RATING_SCALE");
        addMetadata();
    }

    public QUomRatingScale(String variable, String schema, String table) {
        super(UomRatingScale.class, forVariable(variable), schema, table);
        addMetadata();
    }

    public QUomRatingScale(String variable, String schema) {
        super(UomRatingScale.class, forVariable(variable), schema, "UOM_RATING_SCALE");
        addMetadata();
    }

    public QUomRatingScale(Path<? extends UomRatingScale> path) {
        super(path.getType(), path.getMetadata(), "null", "UOM_RATING_SCALE");
        addMetadata();
    }

    public QUomRatingScale(PathMetadata metadata) {
        super(UomRatingScale.class, metadata, "null", "UOM_RATING_SCALE");
        addMetadata();
    }

    public void addMetadata() {
        addMetadata(createdByUserLogin, ColumnMetadata.named("CREATED_BY_USER_LOGIN").withIndex(6).ofType(Types.VARCHAR).withSize(250));
        addMetadata(createdStamp, ColumnMetadata.named("CREATED_STAMP").withIndex(9).ofType(Types.TIMESTAMP).withSize(19));
        addMetadata(createdTxStamp, ColumnMetadata.named("CREATED_TX_STAMP").withIndex(10).ofType(Types.TIMESTAMP).withSize(19));
        addMetadata(description, ColumnMetadata.named("DESCRIPTION").withIndex(3).ofType(Types.VARCHAR).withSize(255));
        addMetadata(descriptionLang, ColumnMetadata.named("DESCRIPTION_LANG").withIndex(11).ofType(Types.VARCHAR).withSize(255));
        addMetadata(iconContentId, ColumnMetadata.named("ICON_CONTENT_ID").withIndex(4).ofType(Types.VARCHAR).withSize(20));
        addMetadata(lastModifiedByUserLogin, ColumnMetadata.named("LAST_MODIFIED_BY_USER_LOGIN").withIndex(5).ofType(Types.VARCHAR).withSize(250));
        addMetadata(lastUpdatedStamp, ColumnMetadata.named("LAST_UPDATED_STAMP").withIndex(7).ofType(Types.TIMESTAMP).withSize(19));
        addMetadata(lastUpdatedTxStamp, ColumnMetadata.named("LAST_UPDATED_TX_STAMP").withIndex(8).ofType(Types.TIMESTAMP).withSize(19));
        addMetadata(uomId, ColumnMetadata.named("UOM_ID").withIndex(1).ofType(Types.VARCHAR).withSize(20).notNull());
        addMetadata(uomRatingValue, ColumnMetadata.named("UOM_RATING_VALUE").withIndex(2).ofType(Types.DECIMAL).withSize(18).withDigits(6).notNull());
    }

}

