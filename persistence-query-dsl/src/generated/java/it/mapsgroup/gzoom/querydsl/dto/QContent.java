package it.mapsgroup.gzoom.querydsl.dto;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;

import com.querydsl.sql.ColumnMetadata;
import java.sql.Types;




/**
 * QContent is a Querydsl query type for Content
 */
@Generated("com.querydsl.sql.codegen.MetaDataSerializer")
public class QContent extends com.querydsl.sql.RelationalPathBase<Content> {

    private static final long serialVersionUID = -938722866;

    public static final QContent content = new QContent("content");

    public final StringPath characterSetId = createString("characterSetId");

    public final NumberPath<java.math.BigInteger> childBranchCount = createNumber("childBranchCount", java.math.BigInteger.class);

    public final NumberPath<java.math.BigInteger> childLeafCount = createNumber("childLeafCount", java.math.BigInteger.class);

    public final StringPath contentId = createString("contentId");

    public final StringPath contentName = createString("contentName");

    public final StringPath contentTypeId = createString("contentTypeId");

    public final StringPath createdByUserLogin = createString("createdByUserLogin");

    public final DateTimePath<java.time.LocalDateTime> createdDate = createDateTime("createdDate", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> createdStamp = createDateTime("createdStamp", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> createdTxStamp = createDateTime("createdTxStamp", java.time.LocalDateTime.class);

    public final StringPath dataResourceId = createString("dataResourceId");

    public final StringPath dataSourceId = createString("dataSourceId");

    public final StringPath decoratorContentId = createString("decoratorContentId");

    public final StringPath description = createString("description");

    public final StringPath instanceOfContentId = createString("instanceOfContentId");

    public final StringPath lastModifiedByUserLogin = createString("lastModifiedByUserLogin");

    public final DateTimePath<java.time.LocalDateTime> lastModifiedDate = createDateTime("lastModifiedDate", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> lastUpdatedStamp = createDateTime("lastUpdatedStamp", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> lastUpdatedTxStamp = createDateTime("lastUpdatedTxStamp", java.time.LocalDateTime.class);

    public final StringPath localeString = createString("localeString");

    public final StringPath mimeTypeId = createString("mimeTypeId");

    public final StringPath ownerContentId = createString("ownerContentId");

    public final StringPath privilegeEnumId = createString("privilegeEnumId");

    public final StringPath serviceName = createString("serviceName");

    public final StringPath statusId = createString("statusId");

    public final StringPath templateDataResourceId = createString("templateDataResourceId");

    public final com.querydsl.sql.PrimaryKey<Content> primary = createPrimaryKey(contentId);

    public final com.querydsl.sql.ForeignKey<UserLoginPersistent> contentLmbUlgn = createForeignKey(lastModifiedByUserLogin, "USER_LOGIN_ID");

    public final com.querydsl.sql.ForeignKey<UserLoginPersistent> contentCbUlgn = createForeignKey(createdByUserLogin, "USER_LOGIN_ID");

    public final com.querydsl.sql.ForeignKey<Content> contentIofcnt = createForeignKey(instanceOfContentId, "CONTENT_ID");

    public final com.querydsl.sql.ForeignKey<Content> contentPcntnt = createForeignKey(ownerContentId, "CONTENT_ID");

    public final com.querydsl.sql.ForeignKey<Content> contentDcntnt = createForeignKey(decoratorContentId, "CONTENT_ID");

    public final com.querydsl.sql.ForeignKey<SecurityGroupContent> _secgrpCntCnt = createInvForeignKey(contentId, "CONTENT_ID");

    public final com.querydsl.sql.ForeignKey<Content> _contentDcntnt = createInvForeignKey(contentId, "DECORATOR_CONTENT_ID");

    public final com.querydsl.sql.ForeignKey<Content> _contentPcntnt = createInvForeignKey(contentId, "OWNER_CONTENT_ID");

    public final com.querydsl.sql.ForeignKey<ContentAssoc> _contentasscFrom = createInvForeignKey(contentId, "CONTENT_ID");

    public final com.querydsl.sql.ForeignKey<Content> _contentIofcnt = createInvForeignKey(contentId, "INSTANCE_OF_CONTENT_ID");

    public final com.querydsl.sql.ForeignKey<ContentAssoc> _contentasscTo = createInvForeignKey(contentId, "CONTENT_ID_TO");

    public final com.querydsl.sql.ForeignKey<ContentAttribute> _contentAttr = createInvForeignKey(contentId, "CONTENT_ID");

    public QContent(String variable) {
        super(Content.class, forVariable(variable), "null", "content");
        addMetadata();
    }

    public QContent(String variable, String schema, String table) {
        super(Content.class, forVariable(variable), schema, table);
        addMetadata();
    }

    public QContent(String variable, String schema) {
        super(Content.class, forVariable(variable), schema, "content");
        addMetadata();
    }

    public QContent(Path<? extends Content> path) {
        super(path.getType(), path.getMetadata(), "null", "content");
        addMetadata();
    }

    public QContent(PathMetadata metadata) {
        super(Content.class, metadata, "null", "content");
        addMetadata();
    }

    public void addMetadata() {
        addMetadata(characterSetId, ColumnMetadata.named("CHARACTER_SET_ID").withIndex(16).ofType(Types.VARCHAR).withSize(60));
        addMetadata(childBranchCount, ColumnMetadata.named("CHILD_BRANCH_COUNT").withIndex(18).ofType(Types.DECIMAL).withSize(20));
        addMetadata(childLeafCount, ColumnMetadata.named("CHILD_LEAF_COUNT").withIndex(17).ofType(Types.DECIMAL).withSize(20));
        addMetadata(contentId, ColumnMetadata.named("CONTENT_ID").withIndex(1).ofType(Types.VARCHAR).withSize(20).notNull());
        addMetadata(contentName, ColumnMetadata.named("CONTENT_NAME").withIndex(12).ofType(Types.VARCHAR).withSize(100));
        addMetadata(contentTypeId, ColumnMetadata.named("CONTENT_TYPE_ID").withIndex(2).ofType(Types.VARCHAR).withSize(20));
        addMetadata(createdByUserLogin, ColumnMetadata.named("CREATED_BY_USER_LOGIN").withIndex(20).ofType(Types.VARCHAR).withSize(250));
        addMetadata(createdDate, ColumnMetadata.named("CREATED_DATE").withIndex(19).ofType(Types.TIMESTAMP).withSize(19));
        addMetadata(createdStamp, ColumnMetadata.named("CREATED_STAMP").withIndex(25).ofType(Types.TIMESTAMP).withSize(19));
        addMetadata(createdTxStamp, ColumnMetadata.named("CREATED_TX_STAMP").withIndex(26).ofType(Types.TIMESTAMP).withSize(19));
        addMetadata(dataResourceId, ColumnMetadata.named("DATA_RESOURCE_ID").withIndex(6).ofType(Types.VARCHAR).withSize(20));
        addMetadata(dataSourceId, ColumnMetadata.named("DATA_SOURCE_ID").withIndex(8).ofType(Types.VARCHAR).withSize(20));
        addMetadata(decoratorContentId, ColumnMetadata.named("DECORATOR_CONTENT_ID").withIndex(4).ofType(Types.VARCHAR).withSize(20));
        addMetadata(description, ColumnMetadata.named("DESCRIPTION").withIndex(13).ofType(Types.VARCHAR).withSize(255));
        addMetadata(instanceOfContentId, ColumnMetadata.named("INSTANCE_OF_CONTENT_ID").withIndex(5).ofType(Types.VARCHAR).withSize(20));
        addMetadata(lastModifiedByUserLogin, ColumnMetadata.named("LAST_MODIFIED_BY_USER_LOGIN").withIndex(22).ofType(Types.VARCHAR).withSize(250));
        addMetadata(lastModifiedDate, ColumnMetadata.named("LAST_MODIFIED_DATE").withIndex(21).ofType(Types.TIMESTAMP).withSize(19));
        addMetadata(lastUpdatedStamp, ColumnMetadata.named("LAST_UPDATED_STAMP").withIndex(23).ofType(Types.TIMESTAMP).withSize(19));
        addMetadata(lastUpdatedTxStamp, ColumnMetadata.named("LAST_UPDATED_TX_STAMP").withIndex(24).ofType(Types.TIMESTAMP).withSize(19));
        addMetadata(localeString, ColumnMetadata.named("LOCALE_STRING").withIndex(14).ofType(Types.VARCHAR).withSize(10));
        addMetadata(mimeTypeId, ColumnMetadata.named("MIME_TYPE_ID").withIndex(15).ofType(Types.VARCHAR).withSize(60));
        addMetadata(ownerContentId, ColumnMetadata.named("OWNER_CONTENT_ID").withIndex(3).ofType(Types.VARCHAR).withSize(20));
        addMetadata(privilegeEnumId, ColumnMetadata.named("PRIVILEGE_ENUM_ID").withIndex(10).ofType(Types.VARCHAR).withSize(20));
        addMetadata(serviceName, ColumnMetadata.named("SERVICE_NAME").withIndex(11).ofType(Types.VARCHAR).withSize(255));
        addMetadata(statusId, ColumnMetadata.named("STATUS_ID").withIndex(9).ofType(Types.VARCHAR).withSize(20));
        addMetadata(templateDataResourceId, ColumnMetadata.named("TEMPLATE_DATA_RESOURCE_ID").withIndex(7).ofType(Types.VARCHAR).withSize(20));
    }

}

