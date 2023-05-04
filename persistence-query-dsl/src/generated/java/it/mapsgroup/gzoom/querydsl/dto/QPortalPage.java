package it.mapsgroup.gzoom.querydsl.dto;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;

import com.querydsl.sql.ColumnMetadata;
import java.sql.Types;




/**
 * QPortalPage is a Querydsl query type for PortalPage
 */
@Generated("com.querydsl.sql.codegen.MetaDataSerializer")
public class QPortalPage extends com.querydsl.sql.RelationalPathBase<PortalPage> {

    private static final long serialVersionUID = -956577882;

    public static final QPortalPage portalPage = new QPortalPage("PORTAL_PAGE");

    public final DateTimePath<java.time.LocalDateTime> createdStamp = createDateTime("createdStamp", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> createdTxStamp = createDateTime("createdTxStamp", java.time.LocalDateTime.class);

    public final StringPath description = createString("description");

    public final StringPath helpContentId = createString("helpContentId");

    public final DateTimePath<java.time.LocalDateTime> lastUpdatedStamp = createDateTime("lastUpdatedStamp", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> lastUpdatedTxStamp = createDateTime("lastUpdatedTxStamp", java.time.LocalDateTime.class);

    public final StringPath originalPortalPageId = createString("originalPortalPageId");

    public final StringPath ownerUserLoginId = createString("ownerUserLoginId");

    public final StringPath parentPortalPageId = createString("parentPortalPageId");

    public final StringPath portalPageId = createString("portalPageId");

    public final StringPath portalPageName = createString("portalPageName");

    public final StringPath securityGroupId = createString("securityGroupId");

    public final NumberPath<java.math.BigInteger> sequenceNum = createNumber("sequenceNum", java.math.BigInteger.class);

    public final com.querydsl.sql.PrimaryKey<PortalPage> primary = createPrimaryKey(portalPageId);

    public final com.querydsl.sql.ForeignKey<SecurityGroup> portpageSecgrp = createForeignKey(securityGroupId, "GROUP_ID");

    public final com.querydsl.sql.ForeignKey<Content> portpalHelpCont = createForeignKey(helpContentId, "CONTENT_ID");

    public final com.querydsl.sql.ForeignKey<PortalPage> portPagePARENT = createForeignKey(parentPortalPageId, "PORTAL_PAGE_ID");

    public final com.querydsl.sql.ForeignKey<PortalPage> _portPagePARENT = createInvForeignKey(portalPageId, "PARENT_PORTAL_PAGE_ID");

    public final com.querydsl.sql.ForeignKey<SecurityGroup> _portalpage = createInvForeignKey(portalPageId, "DEFAULT_PORTAL_PAGE_ID");

    public QPortalPage(String variable) {
        super(PortalPage.class, forVariable(variable), "null", "PORTAL_PAGE");
        addMetadata();
    }

    public QPortalPage(String variable, String schema, String table) {
        super(PortalPage.class, forVariable(variable), schema, table);
        addMetadata();
    }

    public QPortalPage(String variable, String schema) {
        super(PortalPage.class, forVariable(variable), schema, "PORTAL_PAGE");
        addMetadata();
    }

    public QPortalPage(Path<? extends PortalPage> path) {
        super(path.getType(), path.getMetadata(), "null", "PORTAL_PAGE");
        addMetadata();
    }

    public QPortalPage(PathMetadata metadata) {
        super(PortalPage.class, metadata, "null", "PORTAL_PAGE");
        addMetadata();
    }

    public void addMetadata() {
        addMetadata(createdStamp, ColumnMetadata.named("CREATED_STAMP").withIndex(11).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(createdTxStamp, ColumnMetadata.named("CREATED_TX_STAMP").withIndex(12).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(description, ColumnMetadata.named("DESCRIPTION").withIndex(3).ofType(Types.VARCHAR).withSize(255));
        addMetadata(helpContentId, ColumnMetadata.named("HELP_CONTENT_ID").withIndex(13).ofType(Types.VARCHAR).withSize(20));
        addMetadata(lastUpdatedStamp, ColumnMetadata.named("LAST_UPDATED_STAMP").withIndex(9).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(lastUpdatedTxStamp, ColumnMetadata.named("LAST_UPDATED_TX_STAMP").withIndex(10).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(originalPortalPageId, ColumnMetadata.named("ORIGINAL_PORTAL_PAGE_ID").withIndex(5).ofType(Types.VARCHAR).withSize(20));
        addMetadata(ownerUserLoginId, ColumnMetadata.named("OWNER_USER_LOGIN_ID").withIndex(4).ofType(Types.VARCHAR).withSize(20));
        addMetadata(parentPortalPageId, ColumnMetadata.named("PARENT_PORTAL_PAGE_ID").withIndex(6).ofType(Types.VARCHAR).withSize(20));
        addMetadata(portalPageId, ColumnMetadata.named("PORTAL_PAGE_ID").withIndex(1).ofType(Types.VARCHAR).withSize(20).notNull());
        addMetadata(portalPageName, ColumnMetadata.named("PORTAL_PAGE_NAME").withIndex(2).ofType(Types.VARCHAR).withSize(100));
        addMetadata(securityGroupId, ColumnMetadata.named("SECURITY_GROUP_ID").withIndex(8).ofType(Types.VARCHAR).withSize(20));
        addMetadata(sequenceNum, ColumnMetadata.named("SEQUENCE_NUM").withIndex(7).ofType(Types.DECIMAL).withSize(20));
    }

}

