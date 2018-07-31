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
 * QPartyRole is a Querydsl query type for PartyRole
 */
@Generated("com.querydsl.sql.codegen.MetaDataSerializer")
public class QPartyRole extends com.querydsl.sql.RelationalPathBase<PartyRole> {

    private static final long serialVersionUID = -1548472175;

    public static final QPartyRole partyRole = new QPartyRole("PARTY_ROLE");

    public final StringPath createdByUserLogin = createString("createdByUserLogin");

    public final DateTimePath<java.time.LocalDateTime> createdStamp = createDateTime("createdStamp", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> createdTxStamp = createDateTime("createdTxStamp", java.time.LocalDateTime.class);

    public final StringPath lastModifiedByUserLogin = createString("lastModifiedByUserLogin");

    public final DateTimePath<java.time.LocalDateTime> lastUpdatedStamp = createDateTime("lastUpdatedStamp", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> lastUpdatedTxStamp = createDateTime("lastUpdatedTxStamp", java.time.LocalDateTime.class);

    public final StringPath parentRoleTypeId = createString("parentRoleTypeId");

    public final StringPath partyId = createString("partyId");

    public final StringPath roleTypeId = createString("roleTypeId");

    public final com.querydsl.sql.PrimaryKey<PartyRole> primary = createPrimaryKey(partyId, roleTypeId);

    public final com.querydsl.sql.ForeignKey<Party> partyRleParty = createForeignKey(partyId, "PARTY_ID");

    public final com.querydsl.sql.ForeignKey<RoleType> partyRleRole = createForeignKey(roleTypeId, "ROLE_TYPE_ID");

    public final com.querydsl.sql.ForeignKey<PartyRelationship> _partyRelTprole = createInvForeignKey(Arrays.asList(partyId, roleTypeId), Arrays.asList("PARTY_ID_TO", "ROLE_TYPE_ID_TO"));

    public final com.querydsl.sql.ForeignKey<WorkEffort> _weOrgUnit = createInvForeignKey(Arrays.asList(partyId, roleTypeId), Arrays.asList("ORG_UNIT_ID", "ORG_UNIT_ROLE_TYPE_ID"));

    public final com.querydsl.sql.ForeignKey<PartyRelationship> _partyRelFprole = createInvForeignKey(Arrays.asList(partyId, roleTypeId), Arrays.asList("PARTY_ID_FROM", "ROLE_TYPE_ID_FROM"));

    public QPartyRole(String variable) {
        super(PartyRole.class, forVariable(variable), "null", "PARTY_ROLE");
        addMetadata();
    }

    public QPartyRole(String variable, String schema, String table) {
        super(PartyRole.class, forVariable(variable), schema, table);
        addMetadata();
    }

    public QPartyRole(String variable, String schema) {
        super(PartyRole.class, forVariable(variable), schema, "PARTY_ROLE");
        addMetadata();
    }

    public QPartyRole(Path<? extends PartyRole> path) {
        super(path.getType(), path.getMetadata(), "null", "PARTY_ROLE");
        addMetadata();
    }

    public QPartyRole(PathMetadata metadata) {
        super(PartyRole.class, metadata, "null", "PARTY_ROLE");
        addMetadata();
    }

    public void addMetadata() {
        addMetadata(createdByUserLogin, ColumnMetadata.named("CREATED_BY_USER_LOGIN").withIndex(9).ofType(Types.VARCHAR).withSize(250));
        addMetadata(createdStamp, ColumnMetadata.named("CREATED_STAMP").withIndex(5).ofType(Types.TIMESTAMP).withSize(19));
        addMetadata(createdTxStamp, ColumnMetadata.named("CREATED_TX_STAMP").withIndex(6).ofType(Types.TIMESTAMP).withSize(19));
        addMetadata(lastModifiedByUserLogin, ColumnMetadata.named("LAST_MODIFIED_BY_USER_LOGIN").withIndex(8).ofType(Types.VARCHAR).withSize(250));
        addMetadata(lastUpdatedStamp, ColumnMetadata.named("LAST_UPDATED_STAMP").withIndex(3).ofType(Types.TIMESTAMP).withSize(19));
        addMetadata(lastUpdatedTxStamp, ColumnMetadata.named("LAST_UPDATED_TX_STAMP").withIndex(4).ofType(Types.TIMESTAMP).withSize(19));
        addMetadata(parentRoleTypeId, ColumnMetadata.named("PARENT_ROLE_TYPE_ID").withIndex(7).ofType(Types.VARCHAR).withSize(20));
        addMetadata(partyId, ColumnMetadata.named("PARTY_ID").withIndex(1).ofType(Types.VARCHAR).withSize(20).notNull());
        addMetadata(roleTypeId, ColumnMetadata.named("ROLE_TYPE_ID").withIndex(2).ofType(Types.VARCHAR).withSize(20).notNull());
    }

}

