package it.mapsgroup.gzoom.querydsl.dto;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;

import com.querydsl.sql.ColumnMetadata;
import java.sql.Types;




/**
 * QVisitor is a Querydsl query type for Visitor
 */
@Generated("com.querydsl.sql.codegen.MetaDataSerializer")
public class QVisitor extends com.querydsl.sql.RelationalPathBase<Visitor> {

    private static final long serialVersionUID = -1423492669;

    public static final QVisitor visitor = new QVisitor("VISITOR");

    public final DateTimePath<java.time.LocalDateTime> createdStamp = createDateTime("createdStamp", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> createdTxStamp = createDateTime("createdTxStamp", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> lastUpdatedStamp = createDateTime("lastUpdatedStamp", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> lastUpdatedTxStamp = createDateTime("lastUpdatedTxStamp", java.time.LocalDateTime.class);

    public final StringPath partyId = createString("partyId");

    public final StringPath userLoginId = createString("userLoginId");

    public final StringPath visitorId = createString("visitorId");

    public final com.querydsl.sql.PrimaryKey<Visitor> primary = createPrimaryKey(visitorId);

    public final com.querydsl.sql.ForeignKey<Party> visitorParty = createForeignKey(partyId, "PARTY_ID");

    public final com.querydsl.sql.ForeignKey<UserLoginPersistent> visitorUsrlgn = createForeignKey(userLoginId, "USER_LOGIN_ID");

    public final com.querydsl.sql.ForeignKey<Visit> _visitVisitor = createInvForeignKey(visitorId, "VISITOR_ID");

    public QVisitor(String variable) {
        super(Visitor.class, forVariable(variable), "null", "VISITOR");
        addMetadata();
    }

    public QVisitor(String variable, String schema, String table) {
        super(Visitor.class, forVariable(variable), schema, table);
        addMetadata();
    }

    public QVisitor(String variable, String schema) {
        super(Visitor.class, forVariable(variable), schema, "VISITOR");
        addMetadata();
    }

    public QVisitor(Path<? extends Visitor> path) {
        super(path.getType(), path.getMetadata(), "null", "VISITOR");
        addMetadata();
    }

    public QVisitor(PathMetadata metadata) {
        super(Visitor.class, metadata, "null", "VISITOR");
        addMetadata();
    }

    public void addMetadata() {
        addMetadata(createdStamp, ColumnMetadata.named("CREATED_STAMP").withIndex(5).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(createdTxStamp, ColumnMetadata.named("CREATED_TX_STAMP").withIndex(6).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(lastUpdatedStamp, ColumnMetadata.named("LAST_UPDATED_STAMP").withIndex(3).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(lastUpdatedTxStamp, ColumnMetadata.named("LAST_UPDATED_TX_STAMP").withIndex(4).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(partyId, ColumnMetadata.named("PARTY_ID").withIndex(7).ofType(Types.VARCHAR).withSize(20));
        addMetadata(userLoginId, ColumnMetadata.named("USER_LOGIN_ID").withIndex(2).ofType(Types.VARCHAR).withSize(250));
        addMetadata(visitorId, ColumnMetadata.named("VISITOR_ID").withIndex(1).ofType(Types.VARCHAR).withSize(20).notNull());
    }

}

