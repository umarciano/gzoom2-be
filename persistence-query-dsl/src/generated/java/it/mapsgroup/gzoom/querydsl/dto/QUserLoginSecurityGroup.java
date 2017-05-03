package it.mapsgroup.gzoom.querydsl.dto;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;

import com.querydsl.sql.ColumnMetadata;
import java.sql.Types;




/**
 * QUserLoginSecurityGroup is a Querydsl query type for UserLoginSecurityGroup
 */
@Generated("com.querydsl.sql.codegen.MetaDataSerializer")
public class QUserLoginSecurityGroup extends com.querydsl.sql.RelationalPathBase<UserLoginSecurityGroup> {

    private static final long serialVersionUID = 446815084;

    public static final QUserLoginSecurityGroup userLoginSecurityGroup = new QUserLoginSecurityGroup("USER_LOGIN_SECURITY_GROUP");

    public final DateTimePath<java.time.LocalDateTime> createdStamp = createDateTime("createdStamp", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> createdTxStamp = createDateTime("createdTxStamp", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> fromDate = createDateTime("fromDate", java.time.LocalDateTime.class);

    public final StringPath groupId = createString("groupId");

    public final DateTimePath<java.time.LocalDateTime> lastUpdatedStamp = createDateTime("lastUpdatedStamp", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> lastUpdatedTxStamp = createDateTime("lastUpdatedTxStamp", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> thruDate = createDateTime("thruDate", java.time.LocalDateTime.class);

    public final StringPath userLoginId = createString("userLoginId");

    public final com.querydsl.sql.PrimaryKey<UserLoginSecurityGroup> primary = createPrimaryKey(fromDate, groupId, userLoginId);

    public final com.querydsl.sql.ForeignKey<UserLogin> userSecgrpUser = createForeignKey(userLoginId, "USER_LOGIN_ID");

    public final com.querydsl.sql.ForeignKey<SecurityGroup> userSecgrpGrp = createForeignKey(groupId, "GROUP_ID");

    public QUserLoginSecurityGroup(String variable) {
        super(UserLoginSecurityGroup.class, forVariable(variable), "null", "USER_LOGIN_SECURITY_GROUP");
        addMetadata();
    }

    public QUserLoginSecurityGroup(String variable, String schema, String table) {
        super(UserLoginSecurityGroup.class, forVariable(variable), schema, table);
        addMetadata();
    }

    public QUserLoginSecurityGroup(String variable, String schema) {
        super(UserLoginSecurityGroup.class, forVariable(variable), schema, "USER_LOGIN_SECURITY_GROUP");
        addMetadata();
    }

    public QUserLoginSecurityGroup(Path<? extends UserLoginSecurityGroup> path) {
        super(path.getType(), path.getMetadata(), "null", "USER_LOGIN_SECURITY_GROUP");
        addMetadata();
    }

    public QUserLoginSecurityGroup(PathMetadata metadata) {
        super(UserLoginSecurityGroup.class, metadata, "null", "USER_LOGIN_SECURITY_GROUP");
        addMetadata();
    }

    public void addMetadata() {
        addMetadata(createdStamp, ColumnMetadata.named("CREATED_STAMP").withIndex(7).ofType(Types.TIMESTAMP).withSize(19));
        addMetadata(createdTxStamp, ColumnMetadata.named("CREATED_TX_STAMP").withIndex(8).ofType(Types.TIMESTAMP).withSize(19));
        addMetadata(fromDate, ColumnMetadata.named("FROM_DATE").withIndex(3).ofType(Types.TIMESTAMP).withSize(19).notNull());
        addMetadata(groupId, ColumnMetadata.named("GROUP_ID").withIndex(2).ofType(Types.VARCHAR).withSize(20).notNull());
        addMetadata(lastUpdatedStamp, ColumnMetadata.named("LAST_UPDATED_STAMP").withIndex(5).ofType(Types.TIMESTAMP).withSize(19));
        addMetadata(lastUpdatedTxStamp, ColumnMetadata.named("LAST_UPDATED_TX_STAMP").withIndex(6).ofType(Types.TIMESTAMP).withSize(19));
        addMetadata(thruDate, ColumnMetadata.named("THRU_DATE").withIndex(4).ofType(Types.TIMESTAMP).withSize(19));
        addMetadata(userLoginId, ColumnMetadata.named("USER_LOGIN_ID").withIndex(1).ofType(Types.VARCHAR).withSize(250).notNull());
    }

}

