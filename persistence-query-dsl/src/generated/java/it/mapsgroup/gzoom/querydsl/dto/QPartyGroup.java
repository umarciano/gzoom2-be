package it.mapsgroup.gzoom.querydsl.dto;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;

import com.querydsl.sql.ColumnMetadata;
import java.sql.Types;




/**
 * QPartyGroup is a Querydsl query type for PartyGroup
 */
@Generated("com.querydsl.sql.codegen.MetaDataSerializer")
public class QPartyGroup extends com.querydsl.sql.RelationalPathBase<PartyGroup> {

    private static final long serialVersionUID = -768063036;

    public static final QPartyGroup partyGroup = new QPartyGroup("PARTY_GROUP");

    public final NumberPath<java.math.BigDecimal> annualRevenue = createNumber("annualRevenue", java.math.BigDecimal.class);

    public final NumberPath<java.math.BigDecimal> annualTurnover = createNumber("annualTurnover", java.math.BigDecimal.class);

    public final StringPath comments = createString("comments");

    public final StringPath createdByUserLogin = createString("createdByUserLogin");

    public final DateTimePath<java.time.LocalDateTime> createdStamp = createDateTime("createdStamp", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> createdTxStamp = createDateTime("createdTxStamp", java.time.LocalDateTime.class);

    public final StringPath groupName = createString("groupName");

    public final StringPath groupNameLang = createString("groupNameLang");

    public final StringPath groupNameLocal = createString("groupNameLocal");

    public final StringPath lastModifiedByUserLogin = createString("lastModifiedByUserLogin");

    public final DateTimePath<java.time.LocalDateTime> lastUpdatedStamp = createDateTime("lastUpdatedStamp", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> lastUpdatedTxStamp = createDateTime("lastUpdatedTxStamp", java.time.LocalDateTime.class);

    public final StringPath logoImageUrl = createString("logoImageUrl");

    public final NumberPath<java.math.BigInteger> numEmployees = createNumber("numEmployees", java.math.BigInteger.class);

    public final StringPath officeSiteName = createString("officeSiteName");

    public final StringPath partyId = createString("partyId");

    public final StringPath tickerSymbol = createString("tickerSymbol");

    public final NumberPath<java.math.BigInteger> yearStatisticData = createNumber("yearStatisticData", java.math.BigInteger.class);

    public final com.querydsl.sql.PrimaryKey<PartyGroup> primary = createPrimaryKey(partyId);

    public final com.querydsl.sql.ForeignKey<Party> partyGrpParty = createForeignKey(partyId, "PARTY_ID");

    public QPartyGroup(String variable) {
        super(PartyGroup.class, forVariable(variable), "null", "PARTY_GROUP");
        addMetadata();
    }

    public QPartyGroup(String variable, String schema, String table) {
        super(PartyGroup.class, forVariable(variable), schema, table);
        addMetadata();
    }

    public QPartyGroup(String variable, String schema) {
        super(PartyGroup.class, forVariable(variable), schema, "PARTY_GROUP");
        addMetadata();
    }

    public QPartyGroup(Path<? extends PartyGroup> path) {
        super(path.getType(), path.getMetadata(), "null", "PARTY_GROUP");
        addMetadata();
    }

    public QPartyGroup(PathMetadata metadata) {
        super(PartyGroup.class, metadata, "null", "PARTY_GROUP");
        addMetadata();
    }

    public void addMetadata() {
        addMetadata(annualRevenue, ColumnMetadata.named("ANNUAL_REVENUE").withIndex(5).ofType(Types.DECIMAL).withSize(18).withDigits(2));
        addMetadata(annualTurnover, ColumnMetadata.named("ANNUAL_TURNOVER").withIndex(15).ofType(Types.DECIMAL).withSize(18).withDigits(2));
        addMetadata(comments, ColumnMetadata.named("COMMENTS").withIndex(8).ofType(Types.VARCHAR).withSize(255));
        addMetadata(createdByUserLogin, ColumnMetadata.named("CREATED_BY_USER_LOGIN").withIndex(17).ofType(Types.VARCHAR).withSize(250));
        addMetadata(createdStamp, ColumnMetadata.named("CREATED_STAMP").withIndex(12).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(createdTxStamp, ColumnMetadata.named("CREATED_TX_STAMP").withIndex(13).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(groupName, ColumnMetadata.named("GROUP_NAME").withIndex(2).ofType(Types.VARCHAR).withSize(255));
        addMetadata(groupNameLang, ColumnMetadata.named("GROUP_NAME_LANG").withIndex(18).ofType(Types.VARCHAR).withSize(255));
        addMetadata(groupNameLocal, ColumnMetadata.named("GROUP_NAME_LOCAL").withIndex(3).ofType(Types.VARCHAR).withSize(100));
        addMetadata(lastModifiedByUserLogin, ColumnMetadata.named("LAST_MODIFIED_BY_USER_LOGIN").withIndex(16).ofType(Types.VARCHAR).withSize(250));
        addMetadata(lastUpdatedStamp, ColumnMetadata.named("LAST_UPDATED_STAMP").withIndex(10).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(lastUpdatedTxStamp, ColumnMetadata.named("LAST_UPDATED_TX_STAMP").withIndex(11).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(logoImageUrl, ColumnMetadata.named("LOGO_IMAGE_URL").withIndex(9).ofType(Types.VARCHAR).withSize(255));
        addMetadata(numEmployees, ColumnMetadata.named("NUM_EMPLOYEES").withIndex(6).ofType(Types.DECIMAL).withSize(20));
        addMetadata(officeSiteName, ColumnMetadata.named("OFFICE_SITE_NAME").withIndex(4).ofType(Types.VARCHAR).withSize(100));
        addMetadata(partyId, ColumnMetadata.named("PARTY_ID").withIndex(1).ofType(Types.VARCHAR).withSize(20).notNull());
        addMetadata(tickerSymbol, ColumnMetadata.named("TICKER_SYMBOL").withIndex(7).ofType(Types.VARCHAR).withSize(10));
        addMetadata(yearStatisticData, ColumnMetadata.named("YEAR_STATISTIC_DATA").withIndex(14).ofType(Types.DECIMAL).withSize(20));
    }

}

