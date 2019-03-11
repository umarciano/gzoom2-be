package it.mapsgroup.gzoom.querydsl.dto;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;

import com.querydsl.sql.ColumnMetadata;
import java.sql.Types;




/**
 * QContentAttribute is a Querydsl query type for ContentAttribute
 */
@Generated("com.querydsl.sql.codegen.MetaDataSerializer")
public class QContentAttribute extends com.querydsl.sql.RelationalPathBase<ContentAttribute> {

    private static final long serialVersionUID = -352929682;

    public static final QContentAttribute contentAttribute = new QContentAttribute("CONTENT_ATTRIBUTE");

    public final StringPath attrName = createString("attrName");

    public final StringPath attrValue = createString("attrValue");

    public final StringPath contentId = createString("contentId");

    public final DateTimePath<java.time.LocalDateTime> createdStamp = createDateTime("createdStamp", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> createdTxStamp = createDateTime("createdTxStamp", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> lastUpdatedStamp = createDateTime("lastUpdatedStamp", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> lastUpdatedTxStamp = createDateTime("lastUpdatedTxStamp", java.time.LocalDateTime.class);

    public final com.querydsl.sql.PrimaryKey<ContentAttribute> primary = createPrimaryKey(attrName, contentId);

    public final com.querydsl.sql.ForeignKey<Content> contentAttr = createForeignKey(contentId, "CONTENT_ID");

    public QContentAttribute(String variable) {
        super(ContentAttribute.class, forVariable(variable), "null", "CONTENT_ATTRIBUTE");
        addMetadata();
    }

    public QContentAttribute(String variable, String schema, String table) {
        super(ContentAttribute.class, forVariable(variable), schema, table);
        addMetadata();
    }

    public QContentAttribute(String variable, String schema) {
        super(ContentAttribute.class, forVariable(variable), schema, "CONTENT_ATTRIBUTE");
        addMetadata();
    }

    public QContentAttribute(Path<? extends ContentAttribute> path) {
        super(path.getType(), path.getMetadata(), "null", "CONTENT_ATTRIBUTE");
        addMetadata();
    }

    public QContentAttribute(PathMetadata metadata) {
        super(ContentAttribute.class, metadata, "null", "CONTENT_ATTRIBUTE");
        addMetadata();
    }

    public void addMetadata() {
        addMetadata(attrName, ColumnMetadata.named("ATTR_NAME").withIndex(2).ofType(Types.VARCHAR).withSize(60).notNull());
        addMetadata(attrValue, ColumnMetadata.named("ATTR_VALUE").withIndex(3).ofType(Types.VARCHAR).withSize(255));
        addMetadata(contentId, ColumnMetadata.named("CONTENT_ID").withIndex(1).ofType(Types.VARCHAR).withSize(20).notNull());
        addMetadata(createdStamp, ColumnMetadata.named("CREATED_STAMP").withIndex(6).ofType(Types.TIMESTAMP).withSize(19));
        addMetadata(createdTxStamp, ColumnMetadata.named("CREATED_TX_STAMP").withIndex(7).ofType(Types.TIMESTAMP).withSize(19));
        addMetadata(lastUpdatedStamp, ColumnMetadata.named("LAST_UPDATED_STAMP").withIndex(4).ofType(Types.TIMESTAMP).withSize(19));
        addMetadata(lastUpdatedTxStamp, ColumnMetadata.named("LAST_UPDATED_TX_STAMP").withIndex(5).ofType(Types.TIMESTAMP).withSize(19));
    }

}

