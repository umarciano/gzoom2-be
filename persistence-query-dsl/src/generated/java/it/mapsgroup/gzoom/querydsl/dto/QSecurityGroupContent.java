package it.mapsgroup.gzoom.querydsl.dto;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;

import com.querydsl.sql.ColumnMetadata;
import java.sql.Types;




/**
 * QSecurityGroupContent is a Querydsl query type for SecurityGroupContent
 */
@Generated("com.querydsl.sql.codegen.MetaDataSerializer")
public class QSecurityGroupContent extends com.querydsl.sql.RelationalPathBase<SecurityGroupContent> {

    private static final long serialVersionUID = -1167634139;

    public static final QSecurityGroupContent securityGroupContent = new QSecurityGroupContent("SECURITY_GROUP_CONTENT");

    public final StringPath contentId = createString("contentId");

    public final DateTimePath<java.time.LocalDateTime> createdStamp = createDateTime("createdStamp", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> createdTxStamp = createDateTime("createdTxStamp", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> fromDate = createDateTime("fromDate", java.time.LocalDateTime.class);

    public final StringPath groupId = createString("groupId");

    public final DateTimePath<java.time.LocalDateTime> lastUpdatedStamp = createDateTime("lastUpdatedStamp", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> lastUpdatedTxStamp = createDateTime("lastUpdatedTxStamp", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> thruDate = createDateTime("thruDate", java.time.LocalDateTime.class);

    public final com.querydsl.sql.PrimaryKey<SecurityGroupContent> primary = createPrimaryKey(contentId, fromDate, groupId);

    public final com.querydsl.sql.ForeignKey<SecurityGroup> secgrpCntGrp = createForeignKey(groupId, "GROUP_ID");

    public final com.querydsl.sql.ForeignKey<Content> secgrpCntCnt = createForeignKey(contentId, "CONTENT_ID");

    public QSecurityGroupContent(String variable) {
        super(SecurityGroupContent.class, forVariable(variable), "null", "SECURITY_GROUP_CONTENT");
        addMetadata();
    }

    public QSecurityGroupContent(String variable, String schema, String table) {
        super(SecurityGroupContent.class, forVariable(variable), schema, table);
        addMetadata();
    }

    public QSecurityGroupContent(String variable, String schema) {
        super(SecurityGroupContent.class, forVariable(variable), schema, "SECURITY_GROUP_CONTENT");
        addMetadata();
    }

    public QSecurityGroupContent(Path<? extends SecurityGroupContent> path) {
        super(path.getType(), path.getMetadata(), "null", "SECURITY_GROUP_CONTENT");
        addMetadata();
    }

    public QSecurityGroupContent(PathMetadata metadata) {
        super(SecurityGroupContent.class, metadata, "null", "SECURITY_GROUP_CONTENT");
        addMetadata();
    }

    public void addMetadata() {
        addMetadata(contentId, ColumnMetadata.named("CONTENT_ID").withIndex(2).ofType(Types.VARCHAR).withSize(20).notNull());
        addMetadata(createdStamp, ColumnMetadata.named("CREATED_STAMP").withIndex(7).ofType(Types.TIMESTAMP).withSize(19));
        addMetadata(createdTxStamp, ColumnMetadata.named("CREATED_TX_STAMP").withIndex(8).ofType(Types.TIMESTAMP).withSize(19));
        addMetadata(fromDate, ColumnMetadata.named("FROM_DATE").withIndex(3).ofType(Types.TIMESTAMP).withSize(19).notNull());
        addMetadata(groupId, ColumnMetadata.named("GROUP_ID").withIndex(1).ofType(Types.VARCHAR).withSize(20).notNull());
        addMetadata(lastUpdatedStamp, ColumnMetadata.named("LAST_UPDATED_STAMP").withIndex(5).ofType(Types.TIMESTAMP).withSize(19));
        addMetadata(lastUpdatedTxStamp, ColumnMetadata.named("LAST_UPDATED_TX_STAMP").withIndex(6).ofType(Types.TIMESTAMP).withSize(19));
        addMetadata(thruDate, ColumnMetadata.named("THRU_DATE").withIndex(4).ofType(Types.TIMESTAMP).withSize(19));
    }

}

