package it.mapsgroup.gzoom.querydsl.dto;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;

import com.querydsl.sql.ColumnMetadata;
import java.sql.Types;




/**
 * QSecurityGroupPermission is a Querydsl query type for SecurityGroupPermission
 */
@Generated("com.querydsl.sql.codegen.MetaDataSerializer")
public class QSecurityGroupPermission extends com.querydsl.sql.RelationalPathBase<SecurityGroupPermission> {

    private static final long serialVersionUID = -830580317;

    public static final QSecurityGroupPermission securityGroupPermission = new QSecurityGroupPermission("SECURITY_GROUP_PERMISSION");

    public final DateTimePath<java.time.LocalDateTime> createdStamp = createDateTime("createdStamp", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> createdTxStamp = createDateTime("createdTxStamp", java.time.LocalDateTime.class);

    public final StringPath groupId = createString("groupId");

    public final DateTimePath<java.time.LocalDateTime> lastUpdatedStamp = createDateTime("lastUpdatedStamp", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> lastUpdatedTxStamp = createDateTime("lastUpdatedTxStamp", java.time.LocalDateTime.class);

    public final StringPath permissionId = createString("permissionId");

    public final com.querydsl.sql.PrimaryKey<SecurityGroupPermission> primary = createPrimaryKey(groupId, permissionId);

    public final com.querydsl.sql.ForeignKey<SecurityGroup> secGrpPermGrp = createForeignKey(groupId, "GROUP_ID");

    public QSecurityGroupPermission(String variable) {
        super(SecurityGroupPermission.class, forVariable(variable), "null", "SECURITY_GROUP_PERMISSION");
        addMetadata();
    }

    public QSecurityGroupPermission(String variable, String schema, String table) {
        super(SecurityGroupPermission.class, forVariable(variable), schema, table);
        addMetadata();
    }

    public QSecurityGroupPermission(String variable, String schema) {
        super(SecurityGroupPermission.class, forVariable(variable), schema, "SECURITY_GROUP_PERMISSION");
        addMetadata();
    }

    public QSecurityGroupPermission(Path<? extends SecurityGroupPermission> path) {
        super(path.getType(), path.getMetadata(), "null", "SECURITY_GROUP_PERMISSION");
        addMetadata();
    }

    public QSecurityGroupPermission(PathMetadata metadata) {
        super(SecurityGroupPermission.class, metadata, "null", "SECURITY_GROUP_PERMISSION");
        addMetadata();
    }

    public void addMetadata() {
        addMetadata(createdStamp, ColumnMetadata.named("CREATED_STAMP").withIndex(5).ofType(Types.TIMESTAMP).withSize(19));
        addMetadata(createdTxStamp, ColumnMetadata.named("CREATED_TX_STAMP").withIndex(6).ofType(Types.TIMESTAMP).withSize(19));
        addMetadata(groupId, ColumnMetadata.named("GROUP_ID").withIndex(1).ofType(Types.VARCHAR).withSize(20).notNull());
        addMetadata(lastUpdatedStamp, ColumnMetadata.named("LAST_UPDATED_STAMP").withIndex(3).ofType(Types.TIMESTAMP).withSize(19));
        addMetadata(lastUpdatedTxStamp, ColumnMetadata.named("LAST_UPDATED_TX_STAMP").withIndex(4).ofType(Types.TIMESTAMP).withSize(19));
        addMetadata(permissionId, ColumnMetadata.named("PERMISSION_ID").withIndex(2).ofType(Types.VARCHAR).withSize(60).notNull());
    }

}

