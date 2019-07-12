package it.mapsgroup.gzoom.querydsl.dto;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;

import com.querydsl.sql.ColumnMetadata;
import java.sql.Types;




/**
 * QCommunicationEvent is a Querydsl query type for CommunicationEvent
 */
@Generated("com.querydsl.sql.codegen.MetaDataSerializer")
public class QCommunicationEvent extends com.querydsl.sql.RelationalPathBase<CommunicationEvent> {

    private static final long serialVersionUID = -938415121;

    public static final QCommunicationEvent communicationEvent = new QCommunicationEvent("COMMUNICATION_EVENT");

    public final StringPath bccString = createString("bccString");

    public final StringPath ccString = createString("ccString");

    public final StringPath communicationEventId = createString("communicationEventId");

    public final StringPath communicationEventTypeId = createString("communicationEventTypeId");

    public final StringPath contactListId = createString("contactListId");

    public final StringPath contactMechIdFrom = createString("contactMechIdFrom");

    public final StringPath contactMechIdTo = createString("contactMechIdTo");

    public final StringPath contactMechTypeId = createString("contactMechTypeId");

    public final StringPath content = createString("content");

    public final StringPath contentMimeTypeId = createString("contentMimeTypeId");

    public final DateTimePath<java.time.LocalDateTime> createdStamp = createDateTime("createdStamp", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> createdTxStamp = createDateTime("createdTxStamp", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> datetimeEnded = createDateTime("datetimeEnded", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> datetimeStarted = createDateTime("datetimeStarted", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> entryDate = createDateTime("entryDate", java.time.LocalDateTime.class);

    public final StringPath fromString = createString("fromString");

    public final StringPath headerString = createString("headerString");

    public final DateTimePath<java.time.LocalDateTime> lastUpdatedStamp = createDateTime("lastUpdatedStamp", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> lastUpdatedTxStamp = createDateTime("lastUpdatedTxStamp", java.time.LocalDateTime.class);

    public final StringPath messageId = createString("messageId");

    public final StringPath note = createString("note");

    public final StringPath origCommEventId = createString("origCommEventId");

    public final StringPath parentCommEventId = createString("parentCommEventId");

    public final StringPath partyIdFrom = createString("partyIdFrom");

    public final StringPath partyIdTo = createString("partyIdTo");

    public final StringPath reasonEnumId = createString("reasonEnumId");

    public final StringPath roleTypeIdFrom = createString("roleTypeIdFrom");

    public final StringPath roleTypeIdTo = createString("roleTypeIdTo");

    public final StringPath statusId = createString("statusId");

    public final StringPath subject = createString("subject");

    public final StringPath toString = createString("toString");

    public final com.querydsl.sql.PrimaryKey<CommunicationEvent> primary = createPrimaryKey(communicationEventId);

    public final com.querydsl.sql.ForeignKey<ContactMech> comEvntTcm = createForeignKey(contactMechIdTo, "CONTACT_MECH_ID");

    public final com.querydsl.sql.ForeignKey<RoleType> comEvntTrtyp = createForeignKey(roleTypeIdTo, "ROLE_TYPE_ID");

    public final com.querydsl.sql.ForeignKey<Party> comEvntFpty = createForeignKey(partyIdFrom, "PARTY_ID");

    public final com.querydsl.sql.ForeignKey<ContactMech> comEvntFcm = createForeignKey(contactMechIdFrom, "CONTACT_MECH_ID");

    public final com.querydsl.sql.ForeignKey<RoleType> comEvntFrtyp = createForeignKey(roleTypeIdFrom, "ROLE_TYPE_ID");

    public final com.querydsl.sql.ForeignKey<StatusItem> comEvntStts = createForeignKey(statusId, "STATUS_ID");

    public final com.querydsl.sql.ForeignKey<Party> comEvntTpty = createForeignKey(partyIdTo, "PARTY_ID");

    public final com.querydsl.sql.ForeignKey<CommEventContentAssoc> _commevCaCommev = createInvForeignKey(communicationEventId, "COMMUNICATION_EVENT_ID");

    public QCommunicationEvent(String variable) {
        super(CommunicationEvent.class, forVariable(variable), "null", "COMMUNICATION_EVENT");
        addMetadata();
    }

    public QCommunicationEvent(String variable, String schema, String table) {
        super(CommunicationEvent.class, forVariable(variable), schema, table);
        addMetadata();
    }

    public QCommunicationEvent(String variable, String schema) {
        super(CommunicationEvent.class, forVariable(variable), schema, "COMMUNICATION_EVENT");
        addMetadata();
    }

    public QCommunicationEvent(Path<? extends CommunicationEvent> path) {
        super(path.getType(), path.getMetadata(), "null", "COMMUNICATION_EVENT");
        addMetadata();
    }

    public QCommunicationEvent(PathMetadata metadata) {
        super(CommunicationEvent.class, metadata, "null", "COMMUNICATION_EVENT");
        addMetadata();
    }

    public void addMetadata() {
        addMetadata(bccString, ColumnMetadata.named("BCC_STRING").withIndex(26).ofType(Types.LONGVARCHAR).withSize(2147483647));
        addMetadata(ccString, ColumnMetadata.named("CC_STRING").withIndex(25).ofType(Types.LONGVARCHAR).withSize(2147483647));
        addMetadata(communicationEventId, ColumnMetadata.named("COMMUNICATION_EVENT_ID").withIndex(1).ofType(Types.VARCHAR).withSize(20).notNull());
        addMetadata(communicationEventTypeId, ColumnMetadata.named("COMMUNICATION_EVENT_TYPE_ID").withIndex(2).ofType(Types.VARCHAR).withSize(20));
        addMetadata(contactListId, ColumnMetadata.named("CONTACT_LIST_ID").withIndex(21).ofType(Types.VARCHAR).withSize(20));
        addMetadata(contactMechIdFrom, ColumnMetadata.named("CONTACT_MECH_ID_FROM").withIndex(7).ofType(Types.VARCHAR).withSize(20));
        addMetadata(contactMechIdTo, ColumnMetadata.named("CONTACT_MECH_ID_TO").withIndex(8).ofType(Types.VARCHAR).withSize(20));
        addMetadata(contactMechTypeId, ColumnMetadata.named("CONTACT_MECH_TYPE_ID").withIndex(6).ofType(Types.VARCHAR).withSize(20));
        addMetadata(content, ColumnMetadata.named("CONTENT").withIndex(18).ofType(Types.LONGVARCHAR).withSize(2147483647));
        addMetadata(contentMimeTypeId, ColumnMetadata.named("CONTENT_MIME_TYPE_ID").withIndex(17).ofType(Types.VARCHAR).withSize(60));
        addMetadata(createdStamp, ColumnMetadata.named("CREATED_STAMP").withIndex(30).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(createdTxStamp, ColumnMetadata.named("CREATED_TX_STAMP").withIndex(31).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(datetimeEnded, ColumnMetadata.named("DATETIME_ENDED").withIndex(15).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(datetimeStarted, ColumnMetadata.named("DATETIME_STARTED").withIndex(14).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(entryDate, ColumnMetadata.named("ENTRY_DATE").withIndex(13).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(fromString, ColumnMetadata.named("FROM_STRING").withIndex(23).ofType(Types.LONGVARCHAR).withSize(2147483647));
        addMetadata(headerString, ColumnMetadata.named("HEADER_STRING").withIndex(22).ofType(Types.LONGVARCHAR).withSize(2147483647));
        addMetadata(lastUpdatedStamp, ColumnMetadata.named("LAST_UPDATED_STAMP").withIndex(28).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(lastUpdatedTxStamp, ColumnMetadata.named("LAST_UPDATED_TX_STAMP").withIndex(29).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(messageId, ColumnMetadata.named("MESSAGE_ID").withIndex(27).ofType(Types.VARCHAR).withSize(255));
        addMetadata(note, ColumnMetadata.named("NOTE").withIndex(19).ofType(Types.VARCHAR).withSize(255));
        addMetadata(origCommEventId, ColumnMetadata.named("ORIG_COMM_EVENT_ID").withIndex(3).ofType(Types.VARCHAR).withSize(20));
        addMetadata(parentCommEventId, ColumnMetadata.named("PARENT_COMM_EVENT_ID").withIndex(4).ofType(Types.VARCHAR).withSize(20));
        addMetadata(partyIdFrom, ColumnMetadata.named("PARTY_ID_FROM").withIndex(11).ofType(Types.VARCHAR).withSize(20));
        addMetadata(partyIdTo, ColumnMetadata.named("PARTY_ID_TO").withIndex(12).ofType(Types.VARCHAR).withSize(20));
        addMetadata(reasonEnumId, ColumnMetadata.named("REASON_ENUM_ID").withIndex(20).ofType(Types.VARCHAR).withSize(20));
        addMetadata(roleTypeIdFrom, ColumnMetadata.named("ROLE_TYPE_ID_FROM").withIndex(9).ofType(Types.VARCHAR).withSize(20));
        addMetadata(roleTypeIdTo, ColumnMetadata.named("ROLE_TYPE_ID_TO").withIndex(10).ofType(Types.VARCHAR).withSize(20));
        addMetadata(statusId, ColumnMetadata.named("STATUS_ID").withIndex(5).ofType(Types.VARCHAR).withSize(20));
        addMetadata(subject, ColumnMetadata.named("SUBJECT").withIndex(16).ofType(Types.VARCHAR).withSize(255));
        addMetadata(toString, ColumnMetadata.named("TO_STRING").withIndex(24).ofType(Types.LONGVARCHAR).withSize(2147483647));
    }

}

