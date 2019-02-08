package it.mapsgroup.gzoom.querydsl.dto;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;

import com.querydsl.sql.ColumnMetadata;
import java.sql.Types;




/**
 * QCommEventContentAssoc is a Querydsl query type for CommEventContentAssoc
 */
@Generated("com.querydsl.sql.codegen.MetaDataSerializer")
public class QCommEventContentAssoc extends com.querydsl.sql.RelationalPathBase<CommEventContentAssoc> {

    private static final long serialVersionUID = -864489025;

    public static final QCommEventContentAssoc commEventContentAssoc = new QCommEventContentAssoc("COMM_EVENT_CONTENT_ASSOC");

    public final StringPath commContentAssocTypeId = createString("commContentAssocTypeId");

    public final StringPath communicationEventId = createString("communicationEventId");

    public final StringPath contentId = createString("contentId");

    public final DateTimePath<java.time.LocalDateTime> createdStamp = createDateTime("createdStamp", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> createdTxStamp = createDateTime("createdTxStamp", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> fromDate = createDateTime("fromDate", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> lastUpdatedStamp = createDateTime("lastUpdatedStamp", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> lastUpdatedTxStamp = createDateTime("lastUpdatedTxStamp", java.time.LocalDateTime.class);

    public final NumberPath<java.math.BigInteger> sequenceNum = createNumber("sequenceNum", java.math.BigInteger.class);

    public final DateTimePath<java.time.LocalDateTime> thruDate = createDateTime("thruDate", java.time.LocalDateTime.class);

    public final com.querydsl.sql.PrimaryKey<CommEventContentAssoc> primary = createPrimaryKey(communicationEventId, contentId, fromDate);

    public final com.querydsl.sql.ForeignKey<CommunicationEvent> commevCaCommev = createForeignKey(communicationEventId, "COMMUNICATION_EVENT_ID");

    public final com.querydsl.sql.ForeignKey<Content> commevCaFrom = createForeignKey(contentId, "CONTENT_ID");

    public QCommEventContentAssoc(String variable) {
        super(CommEventContentAssoc.class, forVariable(variable), "null", "COMM_EVENT_CONTENT_ASSOC");
        addMetadata();
    }

    public QCommEventContentAssoc(String variable, String schema, String table) {
        super(CommEventContentAssoc.class, forVariable(variable), schema, table);
        addMetadata();
    }

    public QCommEventContentAssoc(String variable, String schema) {
        super(CommEventContentAssoc.class, forVariable(variable), schema, "COMM_EVENT_CONTENT_ASSOC");
        addMetadata();
    }

    public QCommEventContentAssoc(Path<? extends CommEventContentAssoc> path) {
        super(path.getType(), path.getMetadata(), "null", "COMM_EVENT_CONTENT_ASSOC");
        addMetadata();
    }

    public QCommEventContentAssoc(PathMetadata metadata) {
        super(CommEventContentAssoc.class, metadata, "null", "COMM_EVENT_CONTENT_ASSOC");
        addMetadata();
    }

    public void addMetadata() {
        addMetadata(commContentAssocTypeId, ColumnMetadata.named("COMM_CONTENT_ASSOC_TYPE_ID").withIndex(3).ofType(Types.VARCHAR).withSize(20));
        addMetadata(communicationEventId, ColumnMetadata.named("COMMUNICATION_EVENT_ID").withIndex(2).ofType(Types.VARCHAR).withSize(20).notNull());
        addMetadata(contentId, ColumnMetadata.named("CONTENT_ID").withIndex(1).ofType(Types.VARCHAR).withSize(20).notNull());
        addMetadata(createdStamp, ColumnMetadata.named("CREATED_STAMP").withIndex(9).ofType(Types.TIMESTAMP).withSize(19));
        addMetadata(createdTxStamp, ColumnMetadata.named("CREATED_TX_STAMP").withIndex(10).ofType(Types.TIMESTAMP).withSize(19));
        addMetadata(fromDate, ColumnMetadata.named("FROM_DATE").withIndex(4).ofType(Types.TIMESTAMP).withSize(19).notNull());
        addMetadata(lastUpdatedStamp, ColumnMetadata.named("LAST_UPDATED_STAMP").withIndex(7).ofType(Types.TIMESTAMP).withSize(19));
        addMetadata(lastUpdatedTxStamp, ColumnMetadata.named("LAST_UPDATED_TX_STAMP").withIndex(8).ofType(Types.TIMESTAMP).withSize(19));
        addMetadata(sequenceNum, ColumnMetadata.named("SEQUENCE_NUM").withIndex(6).ofType(Types.DECIMAL).withSize(20));
        addMetadata(thruDate, ColumnMetadata.named("THRU_DATE").withIndex(5).ofType(Types.TIMESTAMP).withSize(19));
    }

}

