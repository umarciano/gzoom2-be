package it.mapsgroup.gzoom.querydsl.dto;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;

import com.querydsl.sql.ColumnMetadata;
import java.sql.Types;




/**
 * QSecurityGroup is a Querydsl query type for SecurityGroup
 */
@Generated("com.querydsl.sql.codegen.MetaDataSerializer")
public class QSecurityGroup extends com.querydsl.sql.RelationalPathBase<SecurityGroup> {

    private static final long serialVersionUID = -538769228;

    public static final QSecurityGroup securityGroup = new QSecurityGroup("security_group");

    public final StringPath createdByUserLogin = createString("createdByUserLogin");

    public final DateTimePath<java.time.LocalDateTime> createdStamp = createDateTime("createdStamp", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> createdTxStamp = createDateTime("createdTxStamp", java.time.LocalDateTime.class);

    public final StringPath defaultPortalPageId = createString("defaultPortalPageId");

    public final StringPath description = createString("description");

    public final StringPath groupId = createString("groupId");

    public final StringPath lastModifiedByUserLogin = createString("lastModifiedByUserLogin");

    public final DateTimePath<java.time.LocalDateTime> lastUpdatedStamp = createDateTime("lastUpdatedStamp", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> lastUpdatedTxStamp = createDateTime("lastUpdatedTxStamp", java.time.LocalDateTime.class);

    public final com.querydsl.sql.PrimaryKey<SecurityGroup> primary = createPrimaryKey(groupId);

    public final com.querydsl.sql.ForeignKey<SecurityGroupContent> _secgrpCntGrp = createInvForeignKey(groupId, "GROUP_ID");

    public final com.querydsl.sql.ForeignKey<SecurityGroupPermission> _secGrpPermGrp = createInvForeignKey(groupId, "GROUP_ID");

    public final com.querydsl.sql.ForeignKey<UserLoginSecurityGroup> _userSecgrpGrp = createInvForeignKey(groupId, "GROUP_ID");

    public QSecurityGroup(String variable) {
        super(SecurityGroup.class, forVariable(variable), "null", "security_group");
        addMetadata();
    }

    public QSecurityGroup(String variable, String schema, String table) {
        super(SecurityGroup.class, forVariable(variable), schema, table);
        addMetadata();
    }

    public QSecurityGroup(String variable, String schema) {
        super(SecurityGroup.class, forVariable(variable), schema, "security_group");
        addMetadata();
    }

    public QSecurityGroup(Path<? extends SecurityGroup> path) {
        super(path.getType(), path.getMetadata(), "null", "security_group");
        addMetadata();
    }

    public QSecurityGroup(PathMetadata metadata) {
        super(SecurityGroup.class, metadata, "null", "security_group");
        addMetadata();
    }

    public void addMetadata() {
        addMetadata(createdByUserLogin, ColumnMetadata.named("CREATED_BY_USER_LOGIN").withIndex(9).ofType(Types.VARCHAR).withSize(250));
        addMetadata(createdStamp, ColumnMetadata.named("CREATED_STAMP").withIndex(5).ofType(Types.TIMESTAMP).withSize(19));
        addMetadata(createdTxStamp, ColumnMetadata.named("CREATED_TX_STAMP").withIndex(6).ofType(Types.TIMESTAMP).withSize(19));
        addMetadata(defaultPortalPageId, ColumnMetadata.named("DEFAULT_PORTAL_PAGE_ID").withIndex(7).ofType(Types.VARCHAR).withSize(20));
        addMetadata(description, ColumnMetadata.named("DESCRIPTION").withIndex(2).ofType(Types.VARCHAR).withSize(255));
        addMetadata(groupId, ColumnMetadata.named("GROUP_ID").withIndex(1).ofType(Types.VARCHAR).withSize(20).notNull());
        addMetadata(lastModifiedByUserLogin, ColumnMetadata.named("LAST_MODIFIED_BY_USER_LOGIN").withIndex(8).ofType(Types.VARCHAR).withSize(250));
        addMetadata(lastUpdatedStamp, ColumnMetadata.named("LAST_UPDATED_STAMP").withIndex(3).ofType(Types.TIMESTAMP).withSize(19));
        addMetadata(lastUpdatedTxStamp, ColumnMetadata.named("LAST_UPDATED_TX_STAMP").withIndex(4).ofType(Types.TIMESTAMP).withSize(19));
    }

}

