package it.mapsgroup.gzoom.querydsl.dto;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;

import com.querydsl.sql.ColumnMetadata;
import java.sql.Types;




/**
 * QParty is a Querydsl query type for Party
 */
@Generated("com.querydsl.sql.codegen.MetaDataSerializer")
public class QParty extends com.querydsl.sql.RelationalPathBase<Party> {

    private static final long serialVersionUID = -664243845;

    public static final QParty party = new QParty("PARTY");

    public final StringPath createdByUserLogin = createString("createdByUserLogin");

    public final DateTimePath<java.time.LocalDateTime> createdDate = createDateTime("createdDate", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> createdStamp = createDateTime("createdStamp", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> createdTxStamp = createDateTime("createdTxStamp", java.time.LocalDateTime.class);

    public final StringPath dataSourceId = createString("dataSourceId");

    public final StringPath description = createString("description");

    public final DateTimePath<java.time.LocalDateTime> endDate = createDateTime("endDate", java.time.LocalDateTime.class);

    public final StringPath externalId = createString("externalId");

    public final StringPath fiscalCode = createString("fiscalCode");

    public final BooleanPath isUnread = createBoolean("isUnread");

    public final StringPath lastModifiedByUserLogin = createString("lastModifiedByUserLogin");

    public final DateTimePath<java.time.LocalDateTime> lastModifiedDate = createDateTime("lastModifiedDate", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> lastUpdatedStamp = createDateTime("lastUpdatedStamp", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> lastUpdatedTxStamp = createDateTime("lastUpdatedTxStamp", java.time.LocalDateTime.class);

    public final StringPath partyId = createString("partyId");

    public final StringPath partyName = createString("partyName");

    public final StringPath partyTypeId = createString("partyTypeId");

    public final StringPath preferredCurrencyUomId = createString("preferredCurrencyUomId");

    public final StringPath statusId = createString("statusId");

    public final StringPath vatCode = createString("vatCode");

    public final com.querydsl.sql.PrimaryKey<Party> primary = createPrimaryKey(partyId);

    public final com.querydsl.sql.ForeignKey<UserLogin> partyLmcul = createForeignKey(lastModifiedByUserLogin, "USER_LOGIN_ID");

    public final com.querydsl.sql.ForeignKey<UserLogin> partyCul = createForeignKey(createdByUserLogin, "USER_LOGIN_ID");

    public final com.querydsl.sql.ForeignKey<PartyContactMechPurpose> _partyCmprpParty = createInvForeignKey(partyId, "PARTY_ID");

    public final com.querydsl.sql.ForeignKey<Person> _personParty = createInvForeignKey(partyId, "PARTY_ID");

    public final com.querydsl.sql.ForeignKey<PartyContactMech> _partyCmechParty = createInvForeignKey(partyId, "PARTY_ID");

    public final com.querydsl.sql.ForeignKey<UserLogin> _userParty = createInvForeignKey(partyId, "PARTY_ID");

    public QParty(String variable) {
        super(Party.class, forVariable(variable), "null", "PARTY");
        addMetadata();
    }

    public QParty(String variable, String schema, String table) {
        super(Party.class, forVariable(variable), schema, table);
        addMetadata();
    }

    public QParty(String variable, String schema) {
        super(Party.class, forVariable(variable), schema, "PARTY");
        addMetadata();
    }

    public QParty(Path<? extends Party> path) {
        super(path.getType(), path.getMetadata(), "null", "PARTY");
        addMetadata();
    }

    public QParty(PathMetadata metadata) {
        super(Party.class, metadata, "null", "PARTY");
        addMetadata();
    }

    public void addMetadata() {
        addMetadata(createdByUserLogin, ColumnMetadata.named("CREATED_BY_USER_LOGIN").withIndex(8).ofType(Types.VARCHAR).withSize(250));
        addMetadata(createdDate, ColumnMetadata.named("CREATED_DATE").withIndex(7).ofType(Types.TIMESTAMP).withSize(19));
        addMetadata(createdStamp, ColumnMetadata.named("CREATED_STAMP").withIndex(15).ofType(Types.TIMESTAMP).withSize(19));
        addMetadata(createdTxStamp, ColumnMetadata.named("CREATED_TX_STAMP").withIndex(16).ofType(Types.TIMESTAMP).withSize(19));
        addMetadata(dataSourceId, ColumnMetadata.named("DATA_SOURCE_ID").withIndex(11).ofType(Types.VARCHAR).withSize(20));
        addMetadata(description, ColumnMetadata.named("DESCRIPTION").withIndex(5).ofType(Types.VARCHAR).withSize(255));
        addMetadata(endDate, ColumnMetadata.named("END_DATE").withIndex(20).ofType(Types.TIMESTAMP).withSize(19));
        addMetadata(externalId, ColumnMetadata.named("EXTERNAL_ID").withIndex(3).ofType(Types.VARCHAR).withSize(20));
        addMetadata(fiscalCode, ColumnMetadata.named("FISCAL_CODE").withIndex(17).ofType(Types.VARCHAR).withSize(20));
        addMetadata(isUnread, ColumnMetadata.named("IS_UNREAD").withIndex(12).ofType(Types.CHAR).withSize(1));
        addMetadata(lastModifiedByUserLogin, ColumnMetadata.named("LAST_MODIFIED_BY_USER_LOGIN").withIndex(10).ofType(Types.VARCHAR).withSize(250));
        addMetadata(lastModifiedDate, ColumnMetadata.named("LAST_MODIFIED_DATE").withIndex(9).ofType(Types.TIMESTAMP).withSize(19));
        addMetadata(lastUpdatedStamp, ColumnMetadata.named("LAST_UPDATED_STAMP").withIndex(13).ofType(Types.TIMESTAMP).withSize(19));
        addMetadata(lastUpdatedTxStamp, ColumnMetadata.named("LAST_UPDATED_TX_STAMP").withIndex(14).ofType(Types.TIMESTAMP).withSize(19));
        addMetadata(partyId, ColumnMetadata.named("PARTY_ID").withIndex(1).ofType(Types.VARCHAR).withSize(20).notNull());
        addMetadata(partyName, ColumnMetadata.named("PARTY_NAME").withIndex(19).ofType(Types.VARCHAR).withSize(255));
        addMetadata(partyTypeId, ColumnMetadata.named("PARTY_TYPE_ID").withIndex(2).ofType(Types.VARCHAR).withSize(20));
        addMetadata(preferredCurrencyUomId, ColumnMetadata.named("PREFERRED_CURRENCY_UOM_ID").withIndex(4).ofType(Types.VARCHAR).withSize(20));
        addMetadata(statusId, ColumnMetadata.named("STATUS_ID").withIndex(6).ofType(Types.VARCHAR).withSize(20));
        addMetadata(vatCode, ColumnMetadata.named("VAT_CODE").withIndex(18).ofType(Types.VARCHAR).withSize(20));
    }

}

