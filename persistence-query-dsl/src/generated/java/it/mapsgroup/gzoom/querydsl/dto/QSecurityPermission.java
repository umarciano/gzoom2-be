package it.mapsgroup.gzoom.querydsl.dto;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;

import com.querydsl.sql.ColumnMetadata;
import java.sql.Types;




/**
 * QSecurityPermission is a Querydsl query type for SecurityPermission
 */
@Generated("com.querydsl.sql.codegen.MetaDataSerializer")
public class QSecurityPermission extends com.querydsl.sql.RelationalPathBase<SecurityPermission> {

    private static final long serialVersionUID = 168807610;

    public static final QSecurityPermission securityPermission = new QSecurityPermission("SECURITY_PERMISSION");

    public final StringPath createdByUserLogin = createString("createdByUserLogin");

    public final DateTimePath<java.time.LocalDateTime> createdStamp = createDateTime("createdStamp", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> createdTxStamp = createDateTime("createdTxStamp", java.time.LocalDateTime.class);

    public final StringPath description = createString("description");

    public final StringPath dynamicAccess = createString("dynamicAccess");

    public final BooleanPath enabled = createBoolean("enabled");

    public final StringPath lastModifiedByUserLogin = createString("lastModifiedByUserLogin");

    public final DateTimePath<java.time.LocalDateTime> lastUpdatedStamp = createDateTime("lastUpdatedStamp", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> lastUpdatedTxStamp = createDateTime("lastUpdatedTxStamp", java.time.LocalDateTime.class);

    public final StringPath permissionId = createString("permissionId");

    public final com.querydsl.sql.PrimaryKey<SecurityPermission> primary = createPrimaryKey(permissionId);

    public QSecurityPermission(String variable) {
        super(SecurityPermission.class, forVariable(variable), "null", "SECURITY_PERMISSION");
        addMetadata();
    }

    public QSecurityPermission(String variable, String schema, String table) {
        super(SecurityPermission.class, forVariable(variable), schema, table);
        addMetadata();
    }

    public QSecurityPermission(String variable, String schema) {
        super(SecurityPermission.class, forVariable(variable), schema, "SECURITY_PERMISSION");
        addMetadata();
    }

    public QSecurityPermission(Path<? extends SecurityPermission> path) {
        super(path.getType(), path.getMetadata(), "null", "SECURITY_PERMISSION");
        addMetadata();
    }

    public QSecurityPermission(PathMetadata metadata) {
        super(SecurityPermission.class, metadata, "null", "SECURITY_PERMISSION");
        addMetadata();
    }

    public void addMetadata() {
        addMetadata(createdByUserLogin, ColumnMetadata.named("CREATED_BY_USER_LOGIN").withIndex(10).ofType(Types.VARCHAR).withSize(250));
        addMetadata(createdStamp, ColumnMetadata.named("CREATED_STAMP").withIndex(6).ofType(Types.TIMESTAMP).withSize(19));
        addMetadata(createdTxStamp, ColumnMetadata.named("CREATED_TX_STAMP").withIndex(7).ofType(Types.TIMESTAMP).withSize(19));
        addMetadata(description, ColumnMetadata.named("DESCRIPTION").withIndex(2).ofType(Types.VARCHAR).withSize(255));
        addMetadata(dynamicAccess, ColumnMetadata.named("DYNAMIC_ACCESS").withIndex(3).ofType(Types.VARCHAR).withSize(255));
        addMetadata(enabled, ColumnMetadata.named("ENABLED").withIndex(8).ofType(Types.CHAR).withSize(1));
        addMetadata(lastModifiedByUserLogin, ColumnMetadata.named("LAST_MODIFIED_BY_USER_LOGIN").withIndex(9).ofType(Types.VARCHAR).withSize(250));
        addMetadata(lastUpdatedStamp, ColumnMetadata.named("LAST_UPDATED_STAMP").withIndex(4).ofType(Types.TIMESTAMP).withSize(19));
        addMetadata(lastUpdatedTxStamp, ColumnMetadata.named("LAST_UPDATED_TX_STAMP").withIndex(5).ofType(Types.TIMESTAMP).withSize(19));
        addMetadata(permissionId, ColumnMetadata.named("PERMISSION_ID").withIndex(1).ofType(Types.VARCHAR).withSize(60).notNull());
    }

}

