package it.mapsgroup.gzoom.querydsl.dto;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;

import com.querydsl.sql.ColumnMetadata;
import java.sql.Types;




/**
 * QDataResource is a Querydsl query type for DataResource
 */
@Generated("com.querydsl.sql.codegen.MetaDataSerializer")
public class QDataResource extends com.querydsl.sql.RelationalPathBase<DataResource> {

    private static final long serialVersionUID = 299488675;

    public static final QDataResource dataResource = new QDataResource("DATA_RESOURCE");

    public final StringPath characterSetId = createString("characterSetId");

    public final StringPath createdByUserLogin = createString("createdByUserLogin");

    public final DateTimePath<java.time.LocalDateTime> createdDate = createDateTime("createdDate", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> createdStamp = createDateTime("createdStamp", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> createdTxStamp = createDateTime("createdTxStamp", java.time.LocalDateTime.class);

    public final StringPath dataCategoryId = createString("dataCategoryId");

    public final StringPath dataResourceId = createString("dataResourceId");

    public final StringPath dataResourceName = createString("dataResourceName");

    public final StringPath dataResourceTypeId = createString("dataResourceTypeId");

    public final StringPath dataSourceId = createString("dataSourceId");

    public final StringPath dataTemplateTypeId = createString("dataTemplateTypeId");

    public final BooleanPath isPublic = createBoolean("isPublic");

    public final StringPath lastModifiedByUserLogin = createString("lastModifiedByUserLogin");

    public final DateTimePath<java.time.LocalDateTime> lastModifiedDate = createDateTime("lastModifiedDate", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> lastUpdatedStamp = createDateTime("lastUpdatedStamp", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> lastUpdatedTxStamp = createDateTime("lastUpdatedTxStamp", java.time.LocalDateTime.class);

    public final StringPath localeString = createString("localeString");

    public final StringPath mimeTypeId = createString("mimeTypeId");

    public final StringPath objectInfo = createString("objectInfo");

    public final StringPath relatedDetailId = createString("relatedDetailId");

    public final StringPath statusId = createString("statusId");

    public final StringPath surveyId = createString("surveyId");

    public final StringPath surveyResponseId = createString("surveyResponseId");

    public final com.querydsl.sql.PrimaryKey<DataResource> primary = createPrimaryKey(dataResourceId);

    public final com.querydsl.sql.ForeignKey<UserLoginPersistent> dataRecLmbUlgn = createForeignKey(lastModifiedByUserLogin, "USER_LOGIN_ID");

    public final com.querydsl.sql.ForeignKey<UserLoginPersistent> dataRecCbUlgn = createForeignKey(createdByUserLogin, "USER_LOGIN_ID");

    public final com.querydsl.sql.ForeignKey<Content> _contentToTmpdata = createInvForeignKey(dataResourceId, "TEMPLATE_DATA_RESOURCE_ID");

    public final com.querydsl.sql.ForeignKey<Content> _contentToData = createInvForeignKey(dataResourceId, "DATA_RESOURCE_ID");

    public QDataResource(String variable) {
        super(DataResource.class, forVariable(variable), "null", "DATA_RESOURCE");
        addMetadata();
    }

    public QDataResource(String variable, String schema, String table) {
        super(DataResource.class, forVariable(variable), schema, table);
        addMetadata();
    }

    public QDataResource(String variable, String schema) {
        super(DataResource.class, forVariable(variable), schema, "DATA_RESOURCE");
        addMetadata();
    }

    public QDataResource(Path<? extends DataResource> path) {
        super(path.getType(), path.getMetadata(), "null", "DATA_RESOURCE");
        addMetadata();
    }

    public QDataResource(PathMetadata metadata) {
        super(DataResource.class, metadata, "null", "DATA_RESOURCE");
        addMetadata();
    }

    public void addMetadata() {
        addMetadata(characterSetId, ColumnMetadata.named("CHARACTER_SET_ID").withIndex(10).ofType(Types.VARCHAR).withSize(60));
        addMetadata(createdByUserLogin, ColumnMetadata.named("CREATED_BY_USER_LOGIN").withIndex(17).ofType(Types.VARCHAR).withSize(250));
        addMetadata(createdDate, ColumnMetadata.named("CREATED_DATE").withIndex(16).ofType(Types.TIMESTAMP).withSize(19));
        addMetadata(createdStamp, ColumnMetadata.named("CREATED_STAMP").withIndex(22).ofType(Types.TIMESTAMP).withSize(19));
        addMetadata(createdTxStamp, ColumnMetadata.named("CREATED_TX_STAMP").withIndex(23).ofType(Types.TIMESTAMP).withSize(19));
        addMetadata(dataCategoryId, ColumnMetadata.named("DATA_CATEGORY_ID").withIndex(4).ofType(Types.VARCHAR).withSize(20));
        addMetadata(dataResourceId, ColumnMetadata.named("DATA_RESOURCE_ID").withIndex(1).ofType(Types.VARCHAR).withSize(20).notNull());
        addMetadata(dataResourceName, ColumnMetadata.named("DATA_RESOURCE_NAME").withIndex(7).ofType(Types.VARCHAR).withSize(100));
        addMetadata(dataResourceTypeId, ColumnMetadata.named("DATA_RESOURCE_TYPE_ID").withIndex(2).ofType(Types.VARCHAR).withSize(20));
        addMetadata(dataSourceId, ColumnMetadata.named("DATA_SOURCE_ID").withIndex(5).ofType(Types.VARCHAR).withSize(20));
        addMetadata(dataTemplateTypeId, ColumnMetadata.named("DATA_TEMPLATE_TYPE_ID").withIndex(3).ofType(Types.VARCHAR).withSize(20));
        addMetadata(isPublic, ColumnMetadata.named("IS_PUBLIC").withIndex(15).ofType(Types.CHAR).withSize(1));
        addMetadata(lastModifiedByUserLogin, ColumnMetadata.named("LAST_MODIFIED_BY_USER_LOGIN").withIndex(19).ofType(Types.VARCHAR).withSize(250));
        addMetadata(lastModifiedDate, ColumnMetadata.named("LAST_MODIFIED_DATE").withIndex(18).ofType(Types.TIMESTAMP).withSize(19));
        addMetadata(lastUpdatedStamp, ColumnMetadata.named("LAST_UPDATED_STAMP").withIndex(20).ofType(Types.TIMESTAMP).withSize(19));
        addMetadata(lastUpdatedTxStamp, ColumnMetadata.named("LAST_UPDATED_TX_STAMP").withIndex(21).ofType(Types.TIMESTAMP).withSize(19));
        addMetadata(localeString, ColumnMetadata.named("LOCALE_STRING").withIndex(8).ofType(Types.VARCHAR).withSize(10));
        addMetadata(mimeTypeId, ColumnMetadata.named("MIME_TYPE_ID").withIndex(9).ofType(Types.VARCHAR).withSize(60));
        addMetadata(objectInfo, ColumnMetadata.named("OBJECT_INFO").withIndex(11).ofType(Types.VARCHAR).withSize(255));
        addMetadata(relatedDetailId, ColumnMetadata.named("RELATED_DETAIL_ID").withIndex(14).ofType(Types.VARCHAR).withSize(20));
        addMetadata(statusId, ColumnMetadata.named("STATUS_ID").withIndex(6).ofType(Types.VARCHAR).withSize(20));
        addMetadata(surveyId, ColumnMetadata.named("SURVEY_ID").withIndex(12).ofType(Types.VARCHAR).withSize(20));
        addMetadata(surveyResponseId, ColumnMetadata.named("SURVEY_RESPONSE_ID").withIndex(13).ofType(Types.VARCHAR).withSize(20));
    }

}

