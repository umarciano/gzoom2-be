package it.memelabs.smartnebula.lmm.querydsl.generated;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;

import com.querydsl.sql.ColumnMetadata;
import java.sql.Types;




/**
 * QUserLoginSession is a Querydsl query type for UserLoginSession
 */
@Generated("com.querydsl.sql.codegen.MetaDataSerializer")
public class QUserLoginSession extends com.querydsl.sql.RelationalPathBase<UserLoginSession> {

    private static final long serialVersionUID = -732287821;

    public static final QUserLoginSession userLoginSession = new QUserLoginSession("USER_LOGIN_SESSION");

    public final DateTimePath<java.time.LocalDateTime> createdStamp = createDateTime("createdStamp", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> createdTxStamp = createDateTime("createdTxStamp", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> lastUpdatedStamp = createDateTime("lastUpdatedStamp", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> lastUpdatedTxStamp = createDateTime("lastUpdatedTxStamp", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> savedDate = createDateTime("savedDate", java.time.LocalDateTime.class);

    public final StringPath sessionData = createString("sessionData");

    public final StringPath userLoginId = createString("userLoginId");

    public final com.querydsl.sql.PrimaryKey<UserLoginSession> primary = createPrimaryKey(userLoginId);

    public final com.querydsl.sql.ForeignKey<UserLogin> userSessionUser = createForeignKey(userLoginId, "USER_LOGIN_ID");

    public QUserLoginSession(String variable) {
        super(UserLoginSession.class, forVariable(variable), "null", "USER_LOGIN_SESSION");
        addMetadata();
    }

    public QUserLoginSession(String variable, String schema, String table) {
        super(UserLoginSession.class, forVariable(variable), schema, table);
        addMetadata();
    }

    public QUserLoginSession(String variable, String schema) {
        super(UserLoginSession.class, forVariable(variable), schema, "USER_LOGIN_SESSION");
        addMetadata();
    }

    public QUserLoginSession(Path<? extends UserLoginSession> path) {
        super(path.getType(), path.getMetadata(), "null", "USER_LOGIN_SESSION");
        addMetadata();
    }

    public QUserLoginSession(PathMetadata metadata) {
        super(UserLoginSession.class, metadata, "null", "USER_LOGIN_SESSION");
        addMetadata();
    }

    public void addMetadata() {
        addMetadata(createdStamp, ColumnMetadata.named("CREATED_STAMP").withIndex(6).ofType(Types.TIMESTAMP).withSize(19));
        addMetadata(createdTxStamp, ColumnMetadata.named("CREATED_TX_STAMP").withIndex(7).ofType(Types.TIMESTAMP).withSize(19));
        addMetadata(lastUpdatedStamp, ColumnMetadata.named("LAST_UPDATED_STAMP").withIndex(4).ofType(Types.TIMESTAMP).withSize(19));
        addMetadata(lastUpdatedTxStamp, ColumnMetadata.named("LAST_UPDATED_TX_STAMP").withIndex(5).ofType(Types.TIMESTAMP).withSize(19));
        addMetadata(savedDate, ColumnMetadata.named("SAVED_DATE").withIndex(2).ofType(Types.TIMESTAMP).withSize(19));
        addMetadata(sessionData, ColumnMetadata.named("SESSION_DATA").withIndex(3).ofType(Types.LONGVARCHAR).withSize(2147483647));
        addMetadata(userLoginId, ColumnMetadata.named("USER_LOGIN_ID").withIndex(1).ofType(Types.VARCHAR).withSize(250).notNull());
    }

}

