package it.mapsgroup.gzoom.querydsl.dto;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;

import com.querydsl.sql.ColumnMetadata;
import java.sql.Types;




/**
 * QUom is a Querydsl query type for Uom
 */
@Generated("com.querydsl.sql.codegen.MetaDataSerializer")
public class QUom extends com.querydsl.sql.RelationalPathBase<Uom> {

    private static final long serialVersionUID = -1846493976;

    public static final QUom uom = new QUom("UOM");

    public final StringPath abbreviation = createString("abbreviation");

    public final StringPath abbreviationLang = createString("abbreviationLang");

    public final StringPath createdByUserLogin = createString("createdByUserLogin");

    public final DateTimePath<java.time.LocalDateTime> createdStamp = createDateTime("createdStamp", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> createdTxStamp = createDateTime("createdTxStamp", java.time.LocalDateTime.class);

    public final NumberPath<java.math.BigInteger> decimalScale = createNumber("decimalScale", java.math.BigInteger.class);

    public final StringPath description = createString("description");

    public final StringPath descriptionLang = createString("descriptionLang");

    public final StringPath lastModifiedByUserLogin = createString("lastModifiedByUserLogin");

    public final DateTimePath<java.time.LocalDateTime> lastUpdatedStamp = createDateTime("lastUpdatedStamp", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> lastUpdatedTxStamp = createDateTime("lastUpdatedTxStamp", java.time.LocalDateTime.class);

    public final NumberPath<java.math.BigDecimal> maxValue = createNumber("maxValue", java.math.BigDecimal.class);

    public final NumberPath<java.math.BigDecimal> minValue = createNumber("minValue", java.math.BigDecimal.class);

    public final StringPath uomId = createString("uomId");

    public final StringPath uomTypeId = createString("uomTypeId");

    public final com.querydsl.sql.PrimaryKey<Uom> primary = createPrimaryKey(uomId);

    public final com.querydsl.sql.ForeignKey<UomType> uomToType = createForeignKey(uomTypeId, "UOM_TYPE_ID");

    public final com.querydsl.sql.ForeignKey<WorkEffort> _weEffuom = createInvForeignKey(uomId, "EFFORT_UOM_ID");

    public final com.querydsl.sql.ForeignKey<Party> _partyPrefCrncy = createInvForeignKey(uomId, "PREFERRED_CURRENCY_UOM_ID");

    public final com.querydsl.sql.ForeignKey<UomRatingScale> _ratingToUom = createInvForeignKey(uomId, "UOM_ID");

    public final com.querydsl.sql.ForeignKey<WorkEffortType> _wetEffuom = createInvForeignKey(uomId, "EFFORT_UOM_ID");

    public final com.querydsl.sql.ForeignKey<WorkEffort> _wkEffrtMonUom = createInvForeignKey(uomId, "MONEY_UOM_ID");

    public final com.querydsl.sql.ForeignKey<PartyRelationship> _uomFk01 = createInvForeignKey(uomId, "VALUE_UOM_ID");

    public QUom(String variable) {
        super(Uom.class, forVariable(variable), "null", "UOM");
        addMetadata();
    }

    public QUom(String variable, String schema, String table) {
        super(Uom.class, forVariable(variable), schema, table);
        addMetadata();
    }

    public QUom(String variable, String schema) {
        super(Uom.class, forVariable(variable), schema, "UOM");
        addMetadata();
    }

    public QUom(Path<? extends Uom> path) {
        super(path.getType(), path.getMetadata(), "null", "UOM");
        addMetadata();
    }

    public QUom(PathMetadata metadata) {
        super(Uom.class, metadata, "null", "UOM");
        addMetadata();
    }

    public void addMetadata() {
        addMetadata(abbreviation, ColumnMetadata.named("ABBREVIATION").withIndex(3).ofType(Types.VARCHAR).withSize(60));
        addMetadata(abbreviationLang, ColumnMetadata.named("ABBREVIATION_LANG").withIndex(14).ofType(Types.VARCHAR).withSize(60));
        addMetadata(createdByUserLogin, ColumnMetadata.named("CREATED_BY_USER_LOGIN").withIndex(13).ofType(Types.VARCHAR).withSize(250));
        addMetadata(createdStamp, ColumnMetadata.named("CREATED_STAMP").withIndex(7).ofType(Types.TIMESTAMP).withSize(19));
        addMetadata(createdTxStamp, ColumnMetadata.named("CREATED_TX_STAMP").withIndex(8).ofType(Types.TIMESTAMP).withSize(19));
        addMetadata(decimalScale, ColumnMetadata.named("DECIMAL_SCALE").withIndex(9).ofType(Types.DECIMAL).withSize(20));
        addMetadata(description, ColumnMetadata.named("DESCRIPTION").withIndex(4).ofType(Types.VARCHAR).withSize(255));
        addMetadata(descriptionLang, ColumnMetadata.named("DESCRIPTION_LANG").withIndex(15).ofType(Types.VARCHAR).withSize(255));
        addMetadata(lastModifiedByUserLogin, ColumnMetadata.named("LAST_MODIFIED_BY_USER_LOGIN").withIndex(12).ofType(Types.VARCHAR).withSize(250));
        addMetadata(lastUpdatedStamp, ColumnMetadata.named("LAST_UPDATED_STAMP").withIndex(5).ofType(Types.TIMESTAMP).withSize(19));
        addMetadata(lastUpdatedTxStamp, ColumnMetadata.named("LAST_UPDATED_TX_STAMP").withIndex(6).ofType(Types.TIMESTAMP).withSize(19));
        addMetadata(maxValue, ColumnMetadata.named("MAX_VALUE").withIndex(11).ofType(Types.DECIMAL).withSize(18).withDigits(6));
        addMetadata(minValue, ColumnMetadata.named("MIN_VALUE").withIndex(10).ofType(Types.DECIMAL).withSize(18).withDigits(6));
        addMetadata(uomId, ColumnMetadata.named("UOM_ID").withIndex(1).ofType(Types.VARCHAR).withSize(20).notNull());
        addMetadata(uomTypeId, ColumnMetadata.named("UOM_TYPE_ID").withIndex(2).ofType(Types.VARCHAR).withSize(20));
    }

}

