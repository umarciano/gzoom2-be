package it.mapsgroup.gzoom.querydsl.dto;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;

import com.querydsl.sql.ColumnMetadata;
import java.sql.Types;




/**
 * QUserLoginPersistent is a Querydsl query type for UserLoginPersistent
 */
@Generated("com.querydsl.sql.codegen.MetaDataSerializer")
public class QUserLoginPersistent extends com.querydsl.sql.RelationalPathBase<UserLoginPersistent> {

    private static final long serialVersionUID = -715393686;

    public static final QUserLoginPersistent userLogin = new QUserLoginPersistent("USER_LOGIN");

    public final DateTimePath<java.time.LocalDateTime> createdStamp = createDateTime("createdStamp", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> createdTxStamp = createDateTime("createdTxStamp", java.time.LocalDateTime.class);

    public final StringPath currentPassword = createString("currentPassword");

    public final StringPath description = createString("description");

    public final DateTimePath<java.time.LocalDateTime> disabledDateTime = createDateTime("disabledDateTime", java.time.LocalDateTime.class);

    public final BooleanPath enabled = createBoolean("enabled");

    public final StringPath externalAuthId = createString("externalAuthId");

    public final StringPath externalSystem = createString("externalSystem");

    public final BooleanPath hasLoggedOut = createBoolean("hasLoggedOut");

    public final BooleanPath isSystem = createBoolean("isSystem");

    public final StringPath lastCurrencyUom = createString("lastCurrencyUom");

    public final StringPath lastLocale = createString("lastLocale");

    public final StringPath lastTimeZone = createString("lastTimeZone");

    public final DateTimePath<java.time.LocalDateTime> lastUpdatedStamp = createDateTime("lastUpdatedStamp", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> lastUpdatedTxStamp = createDateTime("lastUpdatedTxStamp", java.time.LocalDateTime.class);

    public final StringPath partyId = createString("partyId");

    public final StringPath passwordHint = createString("passwordHint");

    public final BooleanPath requirePasswordChange = createBoolean("requirePasswordChange");

    public final NumberPath<java.math.BigInteger> successiveFailedLogins = createNumber("successiveFailedLogins", java.math.BigInteger.class);

    public final StringPath userLdapDn = createString("userLdapDn");

    public final StringPath userLoginId = createString("userLoginId");

    public final com.querydsl.sql.PrimaryKey<UserLoginPersistent> primary = createPrimaryKey(userLoginId);

    public final com.querydsl.sql.ForeignKey<Party> userParty = createForeignKey(partyId, "PARTY_ID");

    public final com.querydsl.sql.ForeignKey<DataResource> _dataRecCbUlgn = createInvForeignKey(userLoginId, "CREATED_BY_USER_LOGIN");

    public final com.querydsl.sql.ForeignKey<Party> _partyCul = createInvForeignKey(userLoginId, "CREATED_BY_USER_LOGIN");

    public final com.querydsl.sql.ForeignKey<DataResource> _dataRecLmbUlgn = createInvForeignKey(userLoginId, "LAST_MODIFIED_BY_USER_LOGIN");

    public final com.querydsl.sql.ForeignKey<Content> _contentLmbUlgn = createInvForeignKey(userLoginId, "LAST_MODIFIED_BY_USER_LOGIN");

    public final com.querydsl.sql.ForeignKey<WorkEffortPartyAssignment> _wkeffPaAbusrlog = createInvForeignKey(userLoginId, "ASSIGNED_BY_USER_LOGIN_ID");

    public final com.querydsl.sql.ForeignKey<Party> _partyLmcul = createInvForeignKey(userLoginId, "LAST_MODIFIED_BY_USER_LOGIN");

    public final com.querydsl.sql.ForeignKey<UserLoginValidPartyRole> _ulvprUlFk = createInvForeignKey(userLoginId, "USER_LOGIN_ID");

    public final com.querydsl.sql.ForeignKey<ContentAssoc> _contentasscLmbur = createInvForeignKey(userLoginId, "LAST_MODIFIED_BY_USER_LOGIN");

    public final com.querydsl.sql.ForeignKey<ContentAssoc> _contentasscCbusr = createInvForeignKey(userLoginId, "CREATED_BY_USER_LOGIN");

    public final com.querydsl.sql.ForeignKey<Content> _contentCbUlgn = createInvForeignKey(userLoginId, "CREATED_BY_USER_LOGIN");

    public final com.querydsl.sql.ForeignKey<Timesheet> _timesheetAbUl = createInvForeignKey(userLoginId, "APPROVED_BY_USER_LOGIN_ID");

    public final com.querydsl.sql.ForeignKey<UserLoginSecurityGroup> _userSecgrpUser = createInvForeignKey(userLoginId, "USER_LOGIN_ID");

    public QUserLoginPersistent(String variable) {
        super(UserLoginPersistent.class, forVariable(variable), "null", "USER_LOGIN");
        addMetadata();
    }

    public QUserLoginPersistent(String variable, String schema, String table) {
        super(UserLoginPersistent.class, forVariable(variable), schema, table);
        addMetadata();
    }

    public QUserLoginPersistent(String variable, String schema) {
        super(UserLoginPersistent.class, forVariable(variable), schema, "USER_LOGIN");
        addMetadata();
    }

    public QUserLoginPersistent(Path<? extends UserLoginPersistent> path) {
        super(path.getType(), path.getMetadata(), "null", "USER_LOGIN");
        addMetadata();
    }

    public QUserLoginPersistent(PathMetadata metadata) {
        super(UserLoginPersistent.class, metadata, "null", "USER_LOGIN");
        addMetadata();
    }

    public void addMetadata() {
        addMetadata(createdStamp, ColumnMetadata.named("CREATED_STAMP").withIndex(17).ofType(Types.TIMESTAMP).withSize(19));
        addMetadata(createdTxStamp, ColumnMetadata.named("CREATED_TX_STAMP").withIndex(18).ofType(Types.TIMESTAMP).withSize(19));
        addMetadata(currentPassword, ColumnMetadata.named("CURRENT_PASSWORD").withIndex(2).ofType(Types.VARCHAR).withSize(60));
        addMetadata(description, ColumnMetadata.named("DESCRIPTION").withIndex(21).ofType(Types.VARCHAR).withSize(255));
        addMetadata(disabledDateTime, ColumnMetadata.named("DISABLED_DATE_TIME").withIndex(11).ofType(Types.TIMESTAMP).withSize(19));
        addMetadata(enabled, ColumnMetadata.named("ENABLED").withIndex(5).ofType(Types.CHAR).withSize(1));
        addMetadata(externalAuthId, ColumnMetadata.named("EXTERNAL_AUTH_ID").withIndex(13).ofType(Types.VARCHAR).withSize(250));
        addMetadata(externalSystem, ColumnMetadata.named("EXTERNAL_SYSTEM").withIndex(20).ofType(Types.VARCHAR).withSize(255));
        addMetadata(hasLoggedOut, ColumnMetadata.named("HAS_LOGGED_OUT").withIndex(6).ofType(Types.CHAR).withSize(1));
        addMetadata(isSystem, ColumnMetadata.named("IS_SYSTEM").withIndex(4).ofType(Types.CHAR).withSize(1));
        addMetadata(lastCurrencyUom, ColumnMetadata.named("LAST_CURRENCY_UOM").withIndex(8).ofType(Types.VARCHAR).withSize(20));
        addMetadata(lastLocale, ColumnMetadata.named("LAST_LOCALE").withIndex(9).ofType(Types.VARCHAR).withSize(10));
        addMetadata(lastTimeZone, ColumnMetadata.named("LAST_TIME_ZONE").withIndex(10).ofType(Types.VARCHAR).withSize(60));
        addMetadata(lastUpdatedStamp, ColumnMetadata.named("LAST_UPDATED_STAMP").withIndex(15).ofType(Types.TIMESTAMP).withSize(19));
        addMetadata(lastUpdatedTxStamp, ColumnMetadata.named("LAST_UPDATED_TX_STAMP").withIndex(16).ofType(Types.TIMESTAMP).withSize(19));
        addMetadata(partyId, ColumnMetadata.named("PARTY_ID").withIndex(19).ofType(Types.VARCHAR).withSize(20));
        addMetadata(passwordHint, ColumnMetadata.named("PASSWORD_HINT").withIndex(3).ofType(Types.VARCHAR).withSize(255));
        addMetadata(requirePasswordChange, ColumnMetadata.named("REQUIRE_PASSWORD_CHANGE").withIndex(7).ofType(Types.CHAR).withSize(1));
        addMetadata(successiveFailedLogins, ColumnMetadata.named("SUCCESSIVE_FAILED_LOGINS").withIndex(12).ofType(Types.DECIMAL).withSize(20));
        addMetadata(userLdapDn, ColumnMetadata.named("USER_LDAP_DN").withIndex(14).ofType(Types.VARCHAR).withSize(250));
        addMetadata(userLoginId, ColumnMetadata.named("USER_LOGIN_ID").withIndex(1).ofType(Types.VARCHAR).withSize(250).notNull());
    }

}

