package it.mapsgroup.gzoom.querydsl.dto;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;

import com.querydsl.sql.ColumnMetadata;
import java.sql.Types;




/**
 * QPartyContent is a Querydsl query type for PartyContent
 */
@Generated("com.querydsl.sql.codegen.MetaDataSerializer")
public class QPartyContent extends com.querydsl.sql.RelationalPathBase<PartyContent> {

    private static final long serialVersionUID = 1283902078;

    public static final QPartyContent partyContent = new QPartyContent("PARTY_CONTENT");

    public final StringPath contentId = createString("contentId");

    public final StringPath createdByUserLogin = createString("createdByUserLogin");

    public final DateTimePath<java.time.LocalDateTime> createdStamp = createDateTime("createdStamp", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> createdTxStamp = createDateTime("createdTxStamp", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> fromDate = createDateTime("fromDate", java.time.LocalDateTime.class);

    public final StringPath lastModifiedByUserLogin = createString("lastModifiedByUserLogin");

    public final DateTimePath<java.time.LocalDateTime> lastUpdatedStamp = createDateTime("lastUpdatedStamp", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> lastUpdatedTxStamp = createDateTime("lastUpdatedTxStamp", java.time.LocalDateTime.class);

    public final StringPath partyContentTypeId = createString("partyContentTypeId");

    public final StringPath partyId = createString("partyId");

    public final DateTimePath<java.time.LocalDateTime> thruDate = createDateTime("thruDate", java.time.LocalDateTime.class);

    public final com.querydsl.sql.PrimaryKey<PartyContent> primary = createPrimaryKey(contentId, fromDate, partyContentTypeId, partyId);

    public final com.querydsl.sql.ForeignKey<Party> partyCntParty = createForeignKey(partyId, "PARTY_ID");

    public final com.querydsl.sql.ForeignKey<Content> partyCntCnt = createForeignKey(contentId, "CONTENT_ID");

    public QPartyContent(String variable) {
        super(PartyContent.class, forVariable(variable), "null", "PARTY_CONTENT");
        addMetadata();
    }

    public QPartyContent(String variable, String schema, String table) {
        super(PartyContent.class, forVariable(variable), schema, table);
        addMetadata();
    }

    public QPartyContent(String variable, String schema) {
        super(PartyContent.class, forVariable(variable), schema, "PARTY_CONTENT");
        addMetadata();
    }

    public QPartyContent(Path<? extends PartyContent> path) {
        super(path.getType(), path.getMetadata(), "null", "PARTY_CONTENT");
        addMetadata();
    }

    public QPartyContent(PathMetadata metadata) {
        super(PartyContent.class, metadata, "null", "PARTY_CONTENT");
        addMetadata();
    }

    public void addMetadata() {
        addMetadata(contentId, ColumnMetadata.named("CONTENT_ID").withIndex(2).ofType(Types.VARCHAR).withSize(20).notNull());
        addMetadata(createdByUserLogin, ColumnMetadata.named("CREATED_BY_USER_LOGIN").withIndex(11).ofType(Types.VARCHAR).withSize(250));
        addMetadata(createdStamp, ColumnMetadata.named("CREATED_STAMP").withIndex(8).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(createdTxStamp, ColumnMetadata.named("CREATED_TX_STAMP").withIndex(9).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(fromDate, ColumnMetadata.named("FROM_DATE").withIndex(4).ofType(Types.TIMESTAMP).withSize(26).notNull());
        addMetadata(lastModifiedByUserLogin, ColumnMetadata.named("LAST_MODIFIED_BY_USER_LOGIN").withIndex(10).ofType(Types.VARCHAR).withSize(250));
        addMetadata(lastUpdatedStamp, ColumnMetadata.named("LAST_UPDATED_STAMP").withIndex(6).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(lastUpdatedTxStamp, ColumnMetadata.named("LAST_UPDATED_TX_STAMP").withIndex(7).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(partyContentTypeId, ColumnMetadata.named("PARTY_CONTENT_TYPE_ID").withIndex(3).ofType(Types.VARCHAR).withSize(20).notNull());
        addMetadata(partyId, ColumnMetadata.named("PARTY_ID").withIndex(1).ofType(Types.VARCHAR).withSize(20).notNull());
        addMetadata(thruDate, ColumnMetadata.named("THRU_DATE").withIndex(5).ofType(Types.TIMESTAMP).withSize(26));
    }

}

