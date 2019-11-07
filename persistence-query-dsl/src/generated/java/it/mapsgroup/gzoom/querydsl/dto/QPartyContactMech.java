package it.mapsgroup.gzoom.querydsl.dto;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;

import com.querydsl.sql.ColumnMetadata;
import java.sql.Types;




/**
 * QPartyContactMech is a Querydsl query type for PartyContactMech
 */
@Generated("com.querydsl.sql.codegen.MetaDataSerializer")
public class QPartyContactMech extends com.querydsl.sql.RelationalPathBase<PartyContactMech> {

    private static final long serialVersionUID = -658004030;

    public static final QPartyContactMech partyContactMech = new QPartyContactMech("PARTY_CONTACT_MECH");

    public final BooleanPath allowSolicitation = createBoolean("allowSolicitation");

    public final StringPath comments = createString("comments");

    public final StringPath contactMechId = createString("contactMechId");

    public final StringPath createdByUserLogin = createString("createdByUserLogin");

    public final DateTimePath<java.time.LocalDateTime> createdStamp = createDateTime("createdStamp", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> createdTxStamp = createDateTime("createdTxStamp", java.time.LocalDateTime.class);

    public final StringPath extension = createString("extension");

    public final DateTimePath<java.time.LocalDateTime> fromDate = createDateTime("fromDate", java.time.LocalDateTime.class);

    public final StringPath lastModifiedByUserLogin = createString("lastModifiedByUserLogin");

    public final DateTimePath<java.time.LocalDateTime> lastUpdatedStamp = createDateTime("lastUpdatedStamp", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> lastUpdatedTxStamp = createDateTime("lastUpdatedTxStamp", java.time.LocalDateTime.class);

    public final NumberPath<java.math.BigInteger> monthsWithContactMech = createNumber("monthsWithContactMech", java.math.BigInteger.class);

    public final StringPath partyId = createString("partyId");

    public final StringPath roleTypeId = createString("roleTypeId");

    public final DateTimePath<java.time.LocalDateTime> thruDate = createDateTime("thruDate", java.time.LocalDateTime.class);

    public final BooleanPath verified = createBoolean("verified");

    public final NumberPath<java.math.BigInteger> yearsWithContactMech = createNumber("yearsWithContactMech", java.math.BigInteger.class);

    public final com.querydsl.sql.PrimaryKey<PartyContactMech> primary = createPrimaryKey(contactMechId, fromDate, partyId);

    public final com.querydsl.sql.ForeignKey<ContactMech> partyCmechCmech = createForeignKey(contactMechId, "CONTACT_MECH_ID");

    public final com.querydsl.sql.ForeignKey<Party> partyCmechParty = createForeignKey(partyId, "PARTY_ID");

    public final com.querydsl.sql.ForeignKey<RoleType> partyCmechRole = createForeignKey(roleTypeId, "ROLE_TYPE_ID");

    public QPartyContactMech(String variable) {
        super(PartyContactMech.class, forVariable(variable), "null", "PARTY_CONTACT_MECH");
        addMetadata();
    }

    public QPartyContactMech(String variable, String schema, String table) {
        super(PartyContactMech.class, forVariable(variable), schema, table);
        addMetadata();
    }

    public QPartyContactMech(String variable, String schema) {
        super(PartyContactMech.class, forVariable(variable), schema, "PARTY_CONTACT_MECH");
        addMetadata();
    }

    public QPartyContactMech(Path<? extends PartyContactMech> path) {
        super(path.getType(), path.getMetadata(), "null", "PARTY_CONTACT_MECH");
        addMetadata();
    }

    public QPartyContactMech(PathMetadata metadata) {
        super(PartyContactMech.class, metadata, "null", "PARTY_CONTACT_MECH");
        addMetadata();
    }

    public void addMetadata() {
        addMetadata(allowSolicitation, ColumnMetadata.named("ALLOW_SOLICITATION").withIndex(6).ofType(Types.CHAR).withSize(1));
        addMetadata(comments, ColumnMetadata.named("COMMENTS").withIndex(9).ofType(Types.VARCHAR).withSize(255));
        addMetadata(contactMechId, ColumnMetadata.named("CONTACT_MECH_ID").withIndex(2).ofType(Types.VARCHAR).withSize(20).notNull());
        addMetadata(createdByUserLogin, ColumnMetadata.named("CREATED_BY_USER_LOGIN").withIndex(17).ofType(Types.VARCHAR).withSize(250));
        addMetadata(createdStamp, ColumnMetadata.named("CREATED_STAMP").withIndex(14).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(createdTxStamp, ColumnMetadata.named("CREATED_TX_STAMP").withIndex(15).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(extension, ColumnMetadata.named("EXTENSION").withIndex(7).ofType(Types.VARCHAR).withSize(255));
        addMetadata(fromDate, ColumnMetadata.named("FROM_DATE").withIndex(3).ofType(Types.TIMESTAMP).withSize(26).notNull());
        addMetadata(lastModifiedByUserLogin, ColumnMetadata.named("LAST_MODIFIED_BY_USER_LOGIN").withIndex(16).ofType(Types.VARCHAR).withSize(250));
        addMetadata(lastUpdatedStamp, ColumnMetadata.named("LAST_UPDATED_STAMP").withIndex(12).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(lastUpdatedTxStamp, ColumnMetadata.named("LAST_UPDATED_TX_STAMP").withIndex(13).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(monthsWithContactMech, ColumnMetadata.named("MONTHS_WITH_CONTACT_MECH").withIndex(11).ofType(Types.DECIMAL).withSize(20));
        addMetadata(partyId, ColumnMetadata.named("PARTY_ID").withIndex(1).ofType(Types.VARCHAR).withSize(20).notNull());
        addMetadata(roleTypeId, ColumnMetadata.named("ROLE_TYPE_ID").withIndex(5).ofType(Types.VARCHAR).withSize(20));
        addMetadata(thruDate, ColumnMetadata.named("THRU_DATE").withIndex(4).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(verified, ColumnMetadata.named("VERIFIED").withIndex(8).ofType(Types.CHAR).withSize(1));
        addMetadata(yearsWithContactMech, ColumnMetadata.named("YEARS_WITH_CONTACT_MECH").withIndex(10).ofType(Types.DECIMAL).withSize(20));
    }

}

