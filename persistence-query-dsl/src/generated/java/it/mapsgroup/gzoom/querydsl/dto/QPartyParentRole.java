package it.mapsgroup.gzoom.querydsl.dto;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;

import java.util.*;

import com.querydsl.sql.ColumnMetadata;
import java.sql.Types;




/**
 * QPartyParentRole is a Querydsl query type for PartyParentRole
 */
@Generated("com.querydsl.sql.codegen.MetaDataSerializer")
public class QPartyParentRole extends com.querydsl.sql.RelationalPathBase<PartyParentRole> {

    private static final long serialVersionUID = 1533839387;

    public static final QPartyParentRole partyParentRole = new QPartyParentRole("PARTY_PARENT_ROLE");

    public final StringPath createdByUserLogin = createString("createdByUserLogin");

    public final DateTimePath<java.time.LocalDateTime> createdStamp = createDateTime("createdStamp", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> createdTxStamp = createDateTime("createdTxStamp", java.time.LocalDateTime.class);

    public final StringPath lastModifiedByUserLogin = createString("lastModifiedByUserLogin");

    public final DateTimePath<java.time.LocalDateTime> lastUpdatedStamp = createDateTime("lastUpdatedStamp", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> lastUpdatedTxStamp = createDateTime("lastUpdatedTxStamp", java.time.LocalDateTime.class);

    public final StringPath parentRoleCode = createString("parentRoleCode");

    public final StringPath partyId = createString("partyId");

    public final StringPath roleTypeId = createString("roleTypeId");

    public final com.querydsl.sql.PrimaryKey<PartyParentRole> primary = createPrimaryKey(partyId, roleTypeId);

    public final com.querydsl.sql.ForeignKey<Party> pprPFk = createForeignKey(partyId, "PARTY_ID");

    public final com.querydsl.sql.ForeignKey<RoleType> pprRtFk = createForeignKey(roleTypeId, "ROLE_TYPE_ID");

    public final com.querydsl.sql.ForeignKey<PartyRole> _pprFk01 = createInvForeignKey(Arrays.asList(roleTypeId, partyId), Arrays.asList("PARENT_ROLE_TYPE_ID", "PARTY_ID"));

    public QPartyParentRole(String variable) {
        super(PartyParentRole.class, forVariable(variable), "null", "PARTY_PARENT_ROLE");
        addMetadata();
    }

    public QPartyParentRole(String variable, String schema, String table) {
        super(PartyParentRole.class, forVariable(variable), schema, table);
        addMetadata();
    }

    public QPartyParentRole(String variable, String schema) {
        super(PartyParentRole.class, forVariable(variable), schema, "PARTY_PARENT_ROLE");
        addMetadata();
    }

    public QPartyParentRole(Path<? extends PartyParentRole> path) {
        super(path.getType(), path.getMetadata(), "null", "PARTY_PARENT_ROLE");
        addMetadata();
    }

    public QPartyParentRole(PathMetadata metadata) {
        super(PartyParentRole.class, metadata, "null", "PARTY_PARENT_ROLE");
        addMetadata();
    }

    public void addMetadata() {
        addMetadata(createdByUserLogin, ColumnMetadata.named("CREATED_BY_USER_LOGIN").withIndex(5).ofType(Types.VARCHAR).withSize(250));
        addMetadata(createdStamp, ColumnMetadata.named("CREATED_STAMP").withIndex(8).ofType(Types.TIMESTAMP).withSize(19));
        addMetadata(createdTxStamp, ColumnMetadata.named("CREATED_TX_STAMP").withIndex(9).ofType(Types.TIMESTAMP).withSize(19));
        addMetadata(lastModifiedByUserLogin, ColumnMetadata.named("LAST_MODIFIED_BY_USER_LOGIN").withIndex(4).ofType(Types.VARCHAR).withSize(250));
        addMetadata(lastUpdatedStamp, ColumnMetadata.named("LAST_UPDATED_STAMP").withIndex(6).ofType(Types.TIMESTAMP).withSize(19));
        addMetadata(lastUpdatedTxStamp, ColumnMetadata.named("LAST_UPDATED_TX_STAMP").withIndex(7).ofType(Types.TIMESTAMP).withSize(19));
        addMetadata(parentRoleCode, ColumnMetadata.named("PARENT_ROLE_CODE").withIndex(3).ofType(Types.VARCHAR).withSize(20));
        addMetadata(partyId, ColumnMetadata.named("PARTY_ID").withIndex(1).ofType(Types.VARCHAR).withSize(20).notNull());
        addMetadata(roleTypeId, ColumnMetadata.named("ROLE_TYPE_ID").withIndex(2).ofType(Types.VARCHAR).withSize(20).notNull());
    }

}

