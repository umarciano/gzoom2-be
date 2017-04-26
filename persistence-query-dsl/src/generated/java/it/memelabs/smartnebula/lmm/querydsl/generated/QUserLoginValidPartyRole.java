package it.memelabs.smartnebula.lmm.querydsl.generated;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;

import com.querydsl.sql.ColumnMetadata;
import java.sql.Types;




/**
 * QUserLoginValidPartyRole is a Querydsl query type for UserLoginValidPartyRole
 */
@Generated("com.querydsl.sql.codegen.MetaDataSerializer")
public class QUserLoginValidPartyRole extends com.querydsl.sql.RelationalPathBase<UserLoginValidPartyRole> {

    private static final long serialVersionUID = 141697827;

    public static final QUserLoginValidPartyRole userLoginValidPartyRole = new QUserLoginValidPartyRole("USER_LOGIN_VALID_PARTY_ROLE");

    public final StringPath comments = createString("comments");

    public final StringPath createdByUserLogin = createString("createdByUserLogin");

    public final DateTimePath<java.time.LocalDateTime> createdStamp = createDateTime("createdStamp", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> createdTxStamp = createDateTime("createdTxStamp", java.time.LocalDateTime.class);

    public final StringPath lastModifiedByUserLogin = createString("lastModifiedByUserLogin");

    public final DateTimePath<java.time.LocalDateTime> lastUpdatedStamp = createDateTime("lastUpdatedStamp", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> lastUpdatedTxStamp = createDateTime("lastUpdatedTxStamp", java.time.LocalDateTime.class);

    public final StringPath partyId = createString("partyId");

    public final StringPath roleTypeId = createString("roleTypeId");

    public final StringPath userLoginId = createString("userLoginId");

    public final com.querydsl.sql.PrimaryKey<UserLoginValidPartyRole> primary = createPrimaryKey(partyId, roleTypeId, userLoginId);

    public final com.querydsl.sql.ForeignKey<UserLogin> ulvprUlFk = createForeignKey(userLoginId, "USER_LOGIN_ID");

    public QUserLoginValidPartyRole(String variable) {
        super(UserLoginValidPartyRole.class, forVariable(variable), "null", "USER_LOGIN_VALID_PARTY_ROLE");
        addMetadata();
    }

    public QUserLoginValidPartyRole(String variable, String schema, String table) {
        super(UserLoginValidPartyRole.class, forVariable(variable), schema, table);
        addMetadata();
    }

    public QUserLoginValidPartyRole(String variable, String schema) {
        super(UserLoginValidPartyRole.class, forVariable(variable), schema, "USER_LOGIN_VALID_PARTY_ROLE");
        addMetadata();
    }

    public QUserLoginValidPartyRole(Path<? extends UserLoginValidPartyRole> path) {
        super(path.getType(), path.getMetadata(), "null", "USER_LOGIN_VALID_PARTY_ROLE");
        addMetadata();
    }

    public QUserLoginValidPartyRole(PathMetadata metadata) {
        super(UserLoginValidPartyRole.class, metadata, "null", "USER_LOGIN_VALID_PARTY_ROLE");
        addMetadata();
    }

    public void addMetadata() {
        addMetadata(comments, ColumnMetadata.named("COMMENTS").withIndex(4).ofType(Types.VARCHAR).withSize(255));
        addMetadata(createdByUserLogin, ColumnMetadata.named("CREATED_BY_USER_LOGIN").withIndex(6).ofType(Types.VARCHAR).withSize(250));
        addMetadata(createdStamp, ColumnMetadata.named("CREATED_STAMP").withIndex(9).ofType(Types.TIMESTAMP).withSize(19));
        addMetadata(createdTxStamp, ColumnMetadata.named("CREATED_TX_STAMP").withIndex(10).ofType(Types.TIMESTAMP).withSize(19));
        addMetadata(lastModifiedByUserLogin, ColumnMetadata.named("LAST_MODIFIED_BY_USER_LOGIN").withIndex(5).ofType(Types.VARCHAR).withSize(250));
        addMetadata(lastUpdatedStamp, ColumnMetadata.named("LAST_UPDATED_STAMP").withIndex(7).ofType(Types.TIMESTAMP).withSize(19));
        addMetadata(lastUpdatedTxStamp, ColumnMetadata.named("LAST_UPDATED_TX_STAMP").withIndex(8).ofType(Types.TIMESTAMP).withSize(19));
        addMetadata(partyId, ColumnMetadata.named("PARTY_ID").withIndex(2).ofType(Types.VARCHAR).withSize(20).notNull());
        addMetadata(roleTypeId, ColumnMetadata.named("ROLE_TYPE_ID").withIndex(3).ofType(Types.VARCHAR).withSize(20).notNull());
        addMetadata(userLoginId, ColumnMetadata.named("USER_LOGIN_ID").withIndex(1).ofType(Types.VARCHAR).withSize(250).notNull());
    }

}

