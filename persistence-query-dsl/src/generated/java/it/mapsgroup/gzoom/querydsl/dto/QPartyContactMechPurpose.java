package it.mapsgroup.gzoom.querydsl.dto;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;

import com.querydsl.sql.ColumnMetadata;
import java.sql.Types;




/**
 * QPartyContactMechPurpose is a Querydsl query type for PartyContactMechPurpose
 */
@Generated("com.querydsl.sql.codegen.MetaDataSerializer")
public class QPartyContactMechPurpose extends com.querydsl.sql.RelationalPathBase<PartyContactMechPurpose> {

    private static final long serialVersionUID = -61403396;

    public static final QPartyContactMechPurpose partyContactMechPurpose = new QPartyContactMechPurpose("PARTY_CONTACT_MECH_PURPOSE");

    public final StringPath contactMechId = createString("contactMechId");

    public final StringPath contactMechPurposeTypeId = createString("contactMechPurposeTypeId");

    public final StringPath createdByUserLogin = createString("createdByUserLogin");

    public final DateTimePath<java.time.LocalDateTime> createdStamp = createDateTime("createdStamp", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> createdTxStamp = createDateTime("createdTxStamp", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> fromDate = createDateTime("fromDate", java.time.LocalDateTime.class);

    public final StringPath lastModifiedByUserLogin = createString("lastModifiedByUserLogin");

    public final DateTimePath<java.time.LocalDateTime> lastUpdatedStamp = createDateTime("lastUpdatedStamp", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> lastUpdatedTxStamp = createDateTime("lastUpdatedTxStamp", java.time.LocalDateTime.class);

    public final StringPath partyId = createString("partyId");

    public final DateTimePath<java.time.LocalDateTime> thruDate = createDateTime("thruDate", java.time.LocalDateTime.class);

    public final com.querydsl.sql.PrimaryKey<PartyContactMechPurpose> primary = createPrimaryKey(contactMechId, contactMechPurposeTypeId, fromDate, partyId);

    public final com.querydsl.sql.ForeignKey<Party> partyCmprpParty = createForeignKey(partyId, "PARTY_ID");

    public final com.querydsl.sql.ForeignKey<ContactMechPurposeType> partyCmprpType = createForeignKey(contactMechPurposeTypeId, "CONTACT_MECH_PURPOSE_TYPE_ID");

    public final com.querydsl.sql.ForeignKey<ContactMech> partyCmprpCmech = createForeignKey(contactMechId, "CONTACT_MECH_ID");

    public QPartyContactMechPurpose(String variable) {
        super(PartyContactMechPurpose.class, forVariable(variable), "null", "PARTY_CONTACT_MECH_PURPOSE");
        addMetadata();
    }

    public QPartyContactMechPurpose(String variable, String schema, String table) {
        super(PartyContactMechPurpose.class, forVariable(variable), schema, table);
        addMetadata();
    }

    public QPartyContactMechPurpose(String variable, String schema) {
        super(PartyContactMechPurpose.class, forVariable(variable), schema, "PARTY_CONTACT_MECH_PURPOSE");
        addMetadata();
    }

    public QPartyContactMechPurpose(Path<? extends PartyContactMechPurpose> path) {
        super(path.getType(), path.getMetadata(), "null", "PARTY_CONTACT_MECH_PURPOSE");
        addMetadata();
    }

    public QPartyContactMechPurpose(PathMetadata metadata) {
        super(PartyContactMechPurpose.class, metadata, "null", "PARTY_CONTACT_MECH_PURPOSE");
        addMetadata();
    }

    public void addMetadata() {
        addMetadata(contactMechId, ColumnMetadata.named("CONTACT_MECH_ID").withIndex(2).ofType(Types.VARCHAR).withSize(20).notNull());
        addMetadata(contactMechPurposeTypeId, ColumnMetadata.named("CONTACT_MECH_PURPOSE_TYPE_ID").withIndex(3).ofType(Types.VARCHAR).withSize(20).notNull());
        addMetadata(createdByUserLogin, ColumnMetadata.named("CREATED_BY_USER_LOGIN").withIndex(11).ofType(Types.VARCHAR).withSize(250));
        addMetadata(createdStamp, ColumnMetadata.named("CREATED_STAMP").withIndex(8).ofType(Types.TIMESTAMP).withSize(19));
        addMetadata(createdTxStamp, ColumnMetadata.named("CREATED_TX_STAMP").withIndex(9).ofType(Types.TIMESTAMP).withSize(19));
        addMetadata(fromDate, ColumnMetadata.named("FROM_DATE").withIndex(4).ofType(Types.TIMESTAMP).withSize(19).notNull());
        addMetadata(lastModifiedByUserLogin, ColumnMetadata.named("LAST_MODIFIED_BY_USER_LOGIN").withIndex(10).ofType(Types.VARCHAR).withSize(250));
        addMetadata(lastUpdatedStamp, ColumnMetadata.named("LAST_UPDATED_STAMP").withIndex(6).ofType(Types.TIMESTAMP).withSize(19));
        addMetadata(lastUpdatedTxStamp, ColumnMetadata.named("LAST_UPDATED_TX_STAMP").withIndex(7).ofType(Types.TIMESTAMP).withSize(19));
        addMetadata(partyId, ColumnMetadata.named("PARTY_ID").withIndex(1).ofType(Types.VARCHAR).withSize(20).notNull());
        addMetadata(thruDate, ColumnMetadata.named("THRU_DATE").withIndex(5).ofType(Types.TIMESTAMP).withSize(19));
    }

}

