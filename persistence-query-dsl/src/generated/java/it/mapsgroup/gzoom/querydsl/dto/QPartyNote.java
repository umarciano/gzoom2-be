package it.mapsgroup.gzoom.querydsl.dto;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;

import com.querydsl.sql.ColumnMetadata;
import java.sql.Types;




/**
 * QPartyNote is a Querydsl query type for PartyNote
 */
@Generated("com.querydsl.sql.codegen.MetaDataSerializer")
public class QPartyNote extends com.querydsl.sql.RelationalPathBase<PartyNote> {

    private static final long serialVersionUID = -1548591091;

    public static final QPartyNote partyNote = new QPartyNote("PARTY_NOTE");

    public final StringPath createdByUserLogin = createString("createdByUserLogin");

    public final DateTimePath<java.time.LocalDateTime> createdStamp = createDateTime("createdStamp", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> createdTxStamp = createDateTime("createdTxStamp", java.time.LocalDateTime.class);

    public final StringPath lastModifiedByUserLogin = createString("lastModifiedByUserLogin");

    public final DateTimePath<java.time.LocalDateTime> lastUpdatedStamp = createDateTime("lastUpdatedStamp", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> lastUpdatedTxStamp = createDateTime("lastUpdatedTxStamp", java.time.LocalDateTime.class);

    public final StringPath noteId = createString("noteId");

    public final StringPath partyId = createString("partyId");

    public final com.querydsl.sql.PrimaryKey<PartyNote> primary = createPrimaryKey(noteId, partyId);

    public final com.querydsl.sql.ForeignKey<Party> partyNoteParty = createForeignKey(partyId, "PARTY_ID");

    public final com.querydsl.sql.ForeignKey<NoteData> partyNoteNote = createForeignKey(noteId, "NOTE_ID");

    public QPartyNote(String variable) {
        super(PartyNote.class, forVariable(variable), "null", "PARTY_NOTE");
        addMetadata();
    }

    public QPartyNote(String variable, String schema, String table) {
        super(PartyNote.class, forVariable(variable), schema, table);
        addMetadata();
    }

    public QPartyNote(String variable, String schema) {
        super(PartyNote.class, forVariable(variable), schema, "PARTY_NOTE");
        addMetadata();
    }

    public QPartyNote(Path<? extends PartyNote> path) {
        super(path.getType(), path.getMetadata(), "null", "PARTY_NOTE");
        addMetadata();
    }

    public QPartyNote(PathMetadata metadata) {
        super(PartyNote.class, metadata, "null", "PARTY_NOTE");
        addMetadata();
    }

    public void addMetadata() {
        addMetadata(createdByUserLogin, ColumnMetadata.named("CREATED_BY_USER_LOGIN").withIndex(8).ofType(Types.VARCHAR).withSize(250));
        addMetadata(createdStamp, ColumnMetadata.named("CREATED_STAMP").withIndex(5).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(createdTxStamp, ColumnMetadata.named("CREATED_TX_STAMP").withIndex(6).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(lastModifiedByUserLogin, ColumnMetadata.named("LAST_MODIFIED_BY_USER_LOGIN").withIndex(7).ofType(Types.VARCHAR).withSize(250));
        addMetadata(lastUpdatedStamp, ColumnMetadata.named("LAST_UPDATED_STAMP").withIndex(3).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(lastUpdatedTxStamp, ColumnMetadata.named("LAST_UPDATED_TX_STAMP").withIndex(4).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(noteId, ColumnMetadata.named("NOTE_ID").withIndex(2).ofType(Types.VARCHAR).withSize(20).notNull());
        addMetadata(partyId, ColumnMetadata.named("PARTY_ID").withIndex(1).ofType(Types.VARCHAR).withSize(20).notNull());
    }

}

