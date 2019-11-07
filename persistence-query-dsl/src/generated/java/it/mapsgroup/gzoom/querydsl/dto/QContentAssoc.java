package it.mapsgroup.gzoom.querydsl.dto;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;

import com.querydsl.sql.ColumnMetadata;
import java.sql.Types;




/**
 * QContentAssoc is a Querydsl query type for ContentAssoc
 */
@Generated("com.querydsl.sql.codegen.MetaDataSerializer")
public class QContentAssoc extends com.querydsl.sql.RelationalPathBase<ContentAssoc> {

    private static final long serialVersionUID = 117420775;

    public static final QContentAssoc contentAssoc = new QContentAssoc("CONTENT_ASSOC");

    public final StringPath contentAssocPredicateId = createString("contentAssocPredicateId");

    public final StringPath contentAssocTypeId = createString("contentAssocTypeId");

    public final StringPath contentId = createString("contentId");

    public final StringPath contentIdTo = createString("contentIdTo");

    public final StringPath createdByUserLogin = createString("createdByUserLogin");

    public final DateTimePath<java.time.LocalDateTime> createdDate = createDateTime("createdDate", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> createdStamp = createDateTime("createdStamp", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> createdTxStamp = createDateTime("createdTxStamp", java.time.LocalDateTime.class);

    public final StringPath dataSourceId = createString("dataSourceId");

    public final DateTimePath<java.time.LocalDateTime> fromDate = createDateTime("fromDate", java.time.LocalDateTime.class);

    public final StringPath lastModifiedByUserLogin = createString("lastModifiedByUserLogin");

    public final DateTimePath<java.time.LocalDateTime> lastModifiedDate = createDateTime("lastModifiedDate", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> lastUpdatedStamp = createDateTime("lastUpdatedStamp", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> lastUpdatedTxStamp = createDateTime("lastUpdatedTxStamp", java.time.LocalDateTime.class);

    public final NumberPath<java.math.BigInteger> leftCoordinate = createNumber("leftCoordinate", java.math.BigInteger.class);

    public final StringPath mapKey = createString("mapKey");

    public final NumberPath<java.math.BigInteger> sequenceNum = createNumber("sequenceNum", java.math.BigInteger.class);

    public final DateTimePath<java.time.LocalDateTime> thruDate = createDateTime("thruDate", java.time.LocalDateTime.class);

    public final NumberPath<java.math.BigInteger> upperCoordinate = createNumber("upperCoordinate", java.math.BigInteger.class);

    public final com.querydsl.sql.PrimaryKey<ContentAssoc> primary = createPrimaryKey(contentAssocTypeId, contentId, contentIdTo, fromDate);

    public final com.querydsl.sql.ForeignKey<UserLoginPersistent> contentasscCbusr = createForeignKey(createdByUserLogin, "USER_LOGIN_ID");

    public final com.querydsl.sql.ForeignKey<Content> contentasscTo = createForeignKey(contentIdTo, "CONTENT_ID");

    public final com.querydsl.sql.ForeignKey<UserLoginPersistent> contentasscLmbur = createForeignKey(lastModifiedByUserLogin, "USER_LOGIN_ID");

    public final com.querydsl.sql.ForeignKey<Content> contentasscFrom = createForeignKey(contentId, "CONTENT_ID");

    public QContentAssoc(String variable) {
        super(ContentAssoc.class, forVariable(variable), "null", "CONTENT_ASSOC");
        addMetadata();
    }

    public QContentAssoc(String variable, String schema, String table) {
        super(ContentAssoc.class, forVariable(variable), schema, table);
        addMetadata();
    }

    public QContentAssoc(String variable, String schema) {
        super(ContentAssoc.class, forVariable(variable), schema, "CONTENT_ASSOC");
        addMetadata();
    }

    public QContentAssoc(Path<? extends ContentAssoc> path) {
        super(path.getType(), path.getMetadata(), "null", "CONTENT_ASSOC");
        addMetadata();
    }

    public QContentAssoc(PathMetadata metadata) {
        super(ContentAssoc.class, metadata, "null", "CONTENT_ASSOC");
        addMetadata();
    }

    public void addMetadata() {
        addMetadata(contentAssocPredicateId, ColumnMetadata.named("CONTENT_ASSOC_PREDICATE_ID").withIndex(6).ofType(Types.VARCHAR).withSize(20));
        addMetadata(contentAssocTypeId, ColumnMetadata.named("CONTENT_ASSOC_TYPE_ID").withIndex(3).ofType(Types.VARCHAR).withSize(20).notNull());
        addMetadata(contentId, ColumnMetadata.named("CONTENT_ID").withIndex(1).ofType(Types.VARCHAR).withSize(20).notNull());
        addMetadata(contentIdTo, ColumnMetadata.named("CONTENT_ID_TO").withIndex(2).ofType(Types.VARCHAR).withSize(20).notNull());
        addMetadata(createdByUserLogin, ColumnMetadata.named("CREATED_BY_USER_LOGIN").withIndex(13).ofType(Types.VARCHAR).withSize(250));
        addMetadata(createdDate, ColumnMetadata.named("CREATED_DATE").withIndex(12).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(createdStamp, ColumnMetadata.named("CREATED_STAMP").withIndex(18).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(createdTxStamp, ColumnMetadata.named("CREATED_TX_STAMP").withIndex(19).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(dataSourceId, ColumnMetadata.named("DATA_SOURCE_ID").withIndex(7).ofType(Types.VARCHAR).withSize(20));
        addMetadata(fromDate, ColumnMetadata.named("FROM_DATE").withIndex(4).ofType(Types.TIMESTAMP).withSize(26).notNull());
        addMetadata(lastModifiedByUserLogin, ColumnMetadata.named("LAST_MODIFIED_BY_USER_LOGIN").withIndex(15).ofType(Types.VARCHAR).withSize(250));
        addMetadata(lastModifiedDate, ColumnMetadata.named("LAST_MODIFIED_DATE").withIndex(14).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(lastUpdatedStamp, ColumnMetadata.named("LAST_UPDATED_STAMP").withIndex(16).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(lastUpdatedTxStamp, ColumnMetadata.named("LAST_UPDATED_TX_STAMP").withIndex(17).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(leftCoordinate, ColumnMetadata.named("LEFT_COORDINATE").withIndex(11).ofType(Types.DECIMAL).withSize(20));
        addMetadata(mapKey, ColumnMetadata.named("MAP_KEY").withIndex(9).ofType(Types.VARCHAR).withSize(100));
        addMetadata(sequenceNum, ColumnMetadata.named("SEQUENCE_NUM").withIndex(8).ofType(Types.DECIMAL).withSize(20));
        addMetadata(thruDate, ColumnMetadata.named("THRU_DATE").withIndex(5).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(upperCoordinate, ColumnMetadata.named("UPPER_COORDINATE").withIndex(10).ofType(Types.DECIMAL).withSize(20));
    }

}

