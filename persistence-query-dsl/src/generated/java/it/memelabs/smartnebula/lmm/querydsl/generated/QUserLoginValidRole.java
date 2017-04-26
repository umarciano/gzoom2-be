package it.memelabs.smartnebula.lmm.querydsl.generated;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;

import com.querydsl.sql.ColumnMetadata;
import java.sql.Types;




/**
 * QUserLoginValidRole is a Querydsl query type for UserLoginValidRole
 */
@Generated("com.querydsl.sql.codegen.MetaDataSerializer")
public class QUserLoginValidRole extends com.querydsl.sql.RelationalPathBase<UserLoginValidRole> {

    private static final long serialVersionUID = -1071989393;

    public static final QUserLoginValidRole userLoginValidRole = new QUserLoginValidRole("USER_LOGIN_VALID_ROLE");

    public final StringPath comments = createString("comments");

    public final StringPath createdByUserLogin = createString("createdByUserLogin");

    public final DateTimePath<java.time.LocalDateTime> createdStamp = createDateTime("createdStamp", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> createdTxStamp = createDateTime("createdTxStamp", java.time.LocalDateTime.class);

    public final StringPath lastModifiedByUserLogin = createString("lastModifiedByUserLogin");

    public final DateTimePath<java.time.LocalDateTime> lastUpdatedStamp = createDateTime("lastUpdatedStamp", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> lastUpdatedTxStamp = createDateTime("lastUpdatedTxStamp", java.time.LocalDateTime.class);

    public final StringPath roleTypeId = createString("roleTypeId");

    public final StringPath userLoginId = createString("userLoginId");

    public final com.querydsl.sql.PrimaryKey<UserLoginValidRole> primary = createPrimaryKey(roleTypeId, userLoginId);

    public final com.querydsl.sql.ForeignKey<UserLogin> ulvrUlFk = createForeignKey(userLoginId, "USER_LOGIN_ID");

    public QUserLoginValidRole(String variable) {
        super(UserLoginValidRole.class, forVariable(variable), "null", "USER_LOGIN_VALID_ROLE");
        addMetadata();
    }

    public QUserLoginValidRole(String variable, String schema, String table) {
        super(UserLoginValidRole.class, forVariable(variable), schema, table);
        addMetadata();
    }

    public QUserLoginValidRole(String variable, String schema) {
        super(UserLoginValidRole.class, forVariable(variable), schema, "USER_LOGIN_VALID_ROLE");
        addMetadata();
    }

    public QUserLoginValidRole(Path<? extends UserLoginValidRole> path) {
        super(path.getType(), path.getMetadata(), "null", "USER_LOGIN_VALID_ROLE");
        addMetadata();
    }

    public QUserLoginValidRole(PathMetadata metadata) {
        super(UserLoginValidRole.class, metadata, "null", "USER_LOGIN_VALID_ROLE");
        addMetadata();
    }

    public void addMetadata() {
        addMetadata(comments, ColumnMetadata.named("COMMENTS").withIndex(3).ofType(Types.VARCHAR).withSize(255));
        addMetadata(createdByUserLogin, ColumnMetadata.named("CREATED_BY_USER_LOGIN").withIndex(5).ofType(Types.VARCHAR).withSize(250));
        addMetadata(createdStamp, ColumnMetadata.named("CREATED_STAMP").withIndex(8).ofType(Types.TIMESTAMP).withSize(19));
        addMetadata(createdTxStamp, ColumnMetadata.named("CREATED_TX_STAMP").withIndex(9).ofType(Types.TIMESTAMP).withSize(19));
        addMetadata(lastModifiedByUserLogin, ColumnMetadata.named("LAST_MODIFIED_BY_USER_LOGIN").withIndex(4).ofType(Types.VARCHAR).withSize(250));
        addMetadata(lastUpdatedStamp, ColumnMetadata.named("LAST_UPDATED_STAMP").withIndex(6).ofType(Types.TIMESTAMP).withSize(19));
        addMetadata(lastUpdatedTxStamp, ColumnMetadata.named("LAST_UPDATED_TX_STAMP").withIndex(7).ofType(Types.TIMESTAMP).withSize(19));
        addMetadata(roleTypeId, ColumnMetadata.named("ROLE_TYPE_ID").withIndex(2).ofType(Types.VARCHAR).withSize(20).notNull());
        addMetadata(userLoginId, ColumnMetadata.named("USER_LOGIN_ID").withIndex(1).ofType(Types.VARCHAR).withSize(250).notNull());
    }

}

