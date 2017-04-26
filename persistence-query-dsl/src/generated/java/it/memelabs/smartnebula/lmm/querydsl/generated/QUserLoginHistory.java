package it.memelabs.smartnebula.lmm.querydsl.generated;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;

import com.querydsl.sql.ColumnMetadata;
import java.sql.Types;




/**
 * QUserLoginHistory is a Querydsl query type for UserLoginHistory
 */
@Generated("com.querydsl.sql.codegen.MetaDataSerializer")
public class QUserLoginHistory extends com.querydsl.sql.RelationalPathBase<UserLoginHistory> {

    private static final long serialVersionUID = -1790341455;

    public static final QUserLoginHistory userLoginHistory = new QUserLoginHistory("USER_LOGIN_HISTORY");

    public final DateTimePath<java.time.LocalDateTime> createdStamp = createDateTime("createdStamp", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> createdTxStamp = createDateTime("createdTxStamp", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> fromDate = createDateTime("fromDate", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> lastUpdatedStamp = createDateTime("lastUpdatedStamp", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> lastUpdatedTxStamp = createDateTime("lastUpdatedTxStamp", java.time.LocalDateTime.class);

    public final StringPath partyId = createString("partyId");

    public final StringPath passwordUsed = createString("passwordUsed");

    public final BooleanPath successfulLogin = createBoolean("successfulLogin");

    public final DateTimePath<java.time.LocalDateTime> thruDate = createDateTime("thruDate", java.time.LocalDateTime.class);

    public final StringPath userLoginId = createString("userLoginId");

    public final StringPath visitId = createString("visitId");

    public final com.querydsl.sql.PrimaryKey<UserLoginHistory> primary = createPrimaryKey(fromDate, userLoginId);

    public final com.querydsl.sql.ForeignKey<UserLogin> userLhUser = createForeignKey(userLoginId, "USER_LOGIN_ID");

    public QUserLoginHistory(String variable) {
        super(UserLoginHistory.class, forVariable(variable), "null", "USER_LOGIN_HISTORY");
        addMetadata();
    }

    public QUserLoginHistory(String variable, String schema, String table) {
        super(UserLoginHistory.class, forVariable(variable), schema, table);
        addMetadata();
    }

    public QUserLoginHistory(String variable, String schema) {
        super(UserLoginHistory.class, forVariable(variable), schema, "USER_LOGIN_HISTORY");
        addMetadata();
    }

    public QUserLoginHistory(Path<? extends UserLoginHistory> path) {
        super(path.getType(), path.getMetadata(), "null", "USER_LOGIN_HISTORY");
        addMetadata();
    }

    public QUserLoginHistory(PathMetadata metadata) {
        super(UserLoginHistory.class, metadata, "null", "USER_LOGIN_HISTORY");
        addMetadata();
    }

    public void addMetadata() {
        addMetadata(createdStamp, ColumnMetadata.named("CREATED_STAMP").withIndex(9).ofType(Types.TIMESTAMP).withSize(19));
        addMetadata(createdTxStamp, ColumnMetadata.named("CREATED_TX_STAMP").withIndex(10).ofType(Types.TIMESTAMP).withSize(19));
        addMetadata(fromDate, ColumnMetadata.named("FROM_DATE").withIndex(3).ofType(Types.TIMESTAMP).withSize(19).notNull());
        addMetadata(lastUpdatedStamp, ColumnMetadata.named("LAST_UPDATED_STAMP").withIndex(7).ofType(Types.TIMESTAMP).withSize(19));
        addMetadata(lastUpdatedTxStamp, ColumnMetadata.named("LAST_UPDATED_TX_STAMP").withIndex(8).ofType(Types.TIMESTAMP).withSize(19));
        addMetadata(partyId, ColumnMetadata.named("PARTY_ID").withIndex(11).ofType(Types.VARCHAR).withSize(20));
        addMetadata(passwordUsed, ColumnMetadata.named("PASSWORD_USED").withIndex(5).ofType(Types.VARCHAR).withSize(60));
        addMetadata(successfulLogin, ColumnMetadata.named("SUCCESSFUL_LOGIN").withIndex(6).ofType(Types.CHAR).withSize(1));
        addMetadata(thruDate, ColumnMetadata.named("THRU_DATE").withIndex(4).ofType(Types.TIMESTAMP).withSize(19));
        addMetadata(userLoginId, ColumnMetadata.named("USER_LOGIN_ID").withIndex(1).ofType(Types.VARCHAR).withSize(250).notNull());
        addMetadata(visitId, ColumnMetadata.named("VISIT_ID").withIndex(2).ofType(Types.VARCHAR).withSize(20));
    }

}

