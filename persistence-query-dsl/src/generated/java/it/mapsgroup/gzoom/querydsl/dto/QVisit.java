package it.mapsgroup.gzoom.querydsl.dto;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;

import java.util.*;

import com.querydsl.sql.ColumnMetadata;
import java.sql.Types;




/**
 * QVisit is a Querydsl query type for Visit
 */
@Generated("com.querydsl.sql.codegen.MetaDataSerializer")
public class QVisit extends com.querydsl.sql.RelationalPathBase<Visit> {

    private static final long serialVersionUID = -658463776;

    public static final QVisit visit = new QVisit("VISIT");

    public final StringPath clientHostName = createString("clientHostName");

    public final StringPath clientIpAddress = createString("clientIpAddress");

    public final StringPath clientIpCountryGeoId = createString("clientIpCountryGeoId");

    public final StringPath clientIpIspName = createString("clientIpIspName");

    public final StringPath clientIpPostalCode = createString("clientIpPostalCode");

    public final StringPath clientIpStateProvGeoId = createString("clientIpStateProvGeoId");

    public final StringPath clientUser = createString("clientUser");

    public final StringPath contactMechId = createString("contactMechId");

    public final StringPath cookie = createString("cookie");

    public final DateTimePath<java.time.LocalDateTime> createdStamp = createDateTime("createdStamp", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> createdTxStamp = createDateTime("createdTxStamp", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> fromDate = createDateTime("fromDate", java.time.LocalDateTime.class);

    public final StringPath initialLocale = createString("initialLocale");

    public final StringPath initialReferrer = createString("initialReferrer");

    public final StringPath initialRequest = createString("initialRequest");

    public final StringPath initialUserAgent = createString("initialUserAgent");

    public final DateTimePath<java.time.LocalDateTime> lastUpdatedStamp = createDateTime("lastUpdatedStamp", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> lastUpdatedTxStamp = createDateTime("lastUpdatedTxStamp", java.time.LocalDateTime.class);

    public final StringPath partyId = createString("partyId");

    public final StringPath roleTypeId = createString("roleTypeId");

    public final StringPath serverHostName = createString("serverHostName");

    public final StringPath serverIpAddress = createString("serverIpAddress");

    public final StringPath sessionId = createString("sessionId");

    public final DateTimePath<java.time.LocalDateTime> thruDate = createDateTime("thruDate", java.time.LocalDateTime.class);

    public final StringPath userAgentId = createString("userAgentId");

    public final BooleanPath userCreated = createBoolean("userCreated");

    public final StringPath userLoginId = createString("userLoginId");

    public final StringPath visitId = createString("visitId");

    public final StringPath visitorId = createString("visitorId");

    public final StringPath webappName = createString("webappName");

    public final com.querydsl.sql.PrimaryKey<Visit> primary = createPrimaryKey(visitId);

    public final com.querydsl.sql.ForeignKey<PartyRole> visitPartyRole = createForeignKey(Arrays.asList(partyId, roleTypeId), Arrays.asList("PARTY_ID", "ROLE_TYPE_ID"));

    public final com.querydsl.sql.ForeignKey<RoleType> visitRoleType = createForeignKey(roleTypeId, "ROLE_TYPE_ID");

    public final com.querydsl.sql.ForeignKey<ContactMech> visitContMech = createForeignKey(contactMechId, "CONTACT_MECH_ID");

    public final com.querydsl.sql.ForeignKey<Party> visitParty = createForeignKey(partyId, "PARTY_ID");

    public final com.querydsl.sql.ForeignKey<Visitor> visitVisitor = createForeignKey(visitorId, "VISITOR_ID");

    public QVisit(String variable) {
        super(Visit.class, forVariable(variable), "null", "VISIT");
        addMetadata();
    }

    public QVisit(String variable, String schema, String table) {
        super(Visit.class, forVariable(variable), schema, table);
        addMetadata();
    }

    public QVisit(String variable, String schema) {
        super(Visit.class, forVariable(variable), schema, "VISIT");
        addMetadata();
    }

    public QVisit(Path<? extends Visit> path) {
        super(path.getType(), path.getMetadata(), "null", "VISIT");
        addMetadata();
    }

    public QVisit(PathMetadata metadata) {
        super(Visit.class, metadata, "null", "VISIT");
        addMetadata();
    }

    public void addMetadata() {
        addMetadata(clientHostName, ColumnMetadata.named("CLIENT_HOST_NAME").withIndex(15).ofType(Types.VARCHAR).withSize(255));
        addMetadata(clientIpAddress, ColumnMetadata.named("CLIENT_IP_ADDRESS").withIndex(14).ofType(Types.VARCHAR).withSize(60));
        addMetadata(clientIpCountryGeoId, ColumnMetadata.named("CLIENT_IP_COUNTRY_GEO_ID").withIndex(20).ofType(Types.VARCHAR).withSize(20));
        addMetadata(clientIpIspName, ColumnMetadata.named("CLIENT_IP_ISP_NAME").withIndex(17).ofType(Types.VARCHAR).withSize(60));
        addMetadata(clientIpPostalCode, ColumnMetadata.named("CLIENT_IP_POSTAL_CODE").withIndex(18).ofType(Types.VARCHAR).withSize(60));
        addMetadata(clientIpStateProvGeoId, ColumnMetadata.named("CLIENT_IP_STATE_PROV_GEO_ID").withIndex(19).ofType(Types.VARCHAR).withSize(20));
        addMetadata(clientUser, ColumnMetadata.named("CLIENT_USER").withIndex(16).ofType(Types.VARCHAR).withSize(60));
        addMetadata(contactMechId, ColumnMetadata.named("CONTACT_MECH_ID").withIndex(28).ofType(Types.VARCHAR).withSize(20));
        addMetadata(cookie, ColumnMetadata.named("COOKIE").withIndex(21).ofType(Types.VARCHAR).withSize(60));
        addMetadata(createdStamp, ColumnMetadata.named("CREATED_STAMP").withIndex(26).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(createdTxStamp, ColumnMetadata.named("CREATED_TX_STAMP").withIndex(27).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(fromDate, ColumnMetadata.named("FROM_DATE").withIndex(22).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(initialLocale, ColumnMetadata.named("INITIAL_LOCALE").withIndex(9).ofType(Types.VARCHAR).withSize(60));
        addMetadata(initialReferrer, ColumnMetadata.named("INITIAL_REFERRER").withIndex(11).ofType(Types.VARCHAR).withSize(255));
        addMetadata(initialRequest, ColumnMetadata.named("INITIAL_REQUEST").withIndex(10).ofType(Types.VARCHAR).withSize(255));
        addMetadata(initialUserAgent, ColumnMetadata.named("INITIAL_USER_AGENT").withIndex(12).ofType(Types.VARCHAR).withSize(255));
        addMetadata(lastUpdatedStamp, ColumnMetadata.named("LAST_UPDATED_STAMP").withIndex(24).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(lastUpdatedTxStamp, ColumnMetadata.named("LAST_UPDATED_TX_STAMP").withIndex(25).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(partyId, ColumnMetadata.named("PARTY_ID").withIndex(29).ofType(Types.VARCHAR).withSize(20));
        addMetadata(roleTypeId, ColumnMetadata.named("ROLE_TYPE_ID").withIndex(30).ofType(Types.VARCHAR).withSize(20));
        addMetadata(serverHostName, ColumnMetadata.named("SERVER_HOST_NAME").withIndex(7).ofType(Types.VARCHAR).withSize(255));
        addMetadata(serverIpAddress, ColumnMetadata.named("SERVER_IP_ADDRESS").withIndex(6).ofType(Types.VARCHAR).withSize(20));
        addMetadata(sessionId, ColumnMetadata.named("SESSION_ID").withIndex(5).ofType(Types.VARCHAR).withSize(250));
        addMetadata(thruDate, ColumnMetadata.named("THRU_DATE").withIndex(23).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(userAgentId, ColumnMetadata.named("USER_AGENT_ID").withIndex(13).ofType(Types.VARCHAR).withSize(20));
        addMetadata(userCreated, ColumnMetadata.named("USER_CREATED").withIndex(4).ofType(Types.CHAR).withSize(1));
        addMetadata(userLoginId, ColumnMetadata.named("USER_LOGIN_ID").withIndex(3).ofType(Types.VARCHAR).withSize(250));
        addMetadata(visitId, ColumnMetadata.named("VISIT_ID").withIndex(1).ofType(Types.VARCHAR).withSize(20).notNull());
        addMetadata(visitorId, ColumnMetadata.named("VISITOR_ID").withIndex(2).ofType(Types.VARCHAR).withSize(20));
        addMetadata(webappName, ColumnMetadata.named("WEBAPP_NAME").withIndex(8).ofType(Types.VARCHAR).withSize(60));
    }

}

