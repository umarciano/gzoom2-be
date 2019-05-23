package it.mapsgroup.gzoom.querydsl.dto;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;

import com.querydsl.sql.ColumnMetadata;
import java.sql.Types;




/**
 * QNoteData is a Querydsl query type for NoteData
 */
@Generated("com.querydsl.sql.codegen.MetaDataSerializer")
public class QNoteData extends com.querydsl.sql.RelationalPathBase<NoteData> {

    private static final long serialVersionUID = -1182633977;

    public static final QNoteData noteData = new QNoteData("NOTE_DATA");

    public final StringPath createdByUserLogin = createString("createdByUserLogin");

    public final DateTimePath<java.time.LocalDateTime> createdStamp = createDateTime("createdStamp", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> createdTxStamp = createDateTime("createdTxStamp", java.time.LocalDateTime.class);

    public final BooleanPath isPublic = createBoolean("isPublic");

    public final StringPath lastModifiedByUserLogin = createString("lastModifiedByUserLogin");

    public final DateTimePath<java.time.LocalDateTime> lastUpdatedStamp = createDateTime("lastUpdatedStamp", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> lastUpdatedTxStamp = createDateTime("lastUpdatedTxStamp", java.time.LocalDateTime.class);

    public final StringPath moreInfoItemId = createString("moreInfoItemId");

    public final StringPath moreInfoPortletId = createString("moreInfoPortletId");

    public final DateTimePath<java.time.LocalDateTime> noteDateTime = createDateTime("noteDateTime", java.time.LocalDateTime.class);

    public final StringPath noteId = createString("noteId");

    public final StringPath noteInfo = createString("noteInfo");

    public final StringPath noteInfoLang = createString("noteInfoLang");

    public final StringPath noteName = createString("noteName");

    public final StringPath noteNameLang = createString("noteNameLang");

    public final StringPath noteParty = createString("noteParty");

    public final com.querydsl.sql.PrimaryKey<NoteData> primary = createPrimaryKey(noteId);

    public final com.querydsl.sql.ForeignKey<Party> noteDataPty = createForeignKey(noteParty, "PARTY_ID");

    public final com.querydsl.sql.ForeignKey<PartyNote> _partyNoteNote = createInvForeignKey(noteId, "NOTE_ID");

    public final com.querydsl.sql.ForeignKey<WorkEffort> _wkEffrtNote = createInvForeignKey(noteId, "NOTE_ID");

    public QNoteData(String variable) {
        super(NoteData.class, forVariable(variable), "null", "NOTE_DATA");
        addMetadata();
    }

    public QNoteData(String variable, String schema, String table) {
        super(NoteData.class, forVariable(variable), schema, table);
        addMetadata();
    }

    public QNoteData(String variable, String schema) {
        super(NoteData.class, forVariable(variable), schema, "NOTE_DATA");
        addMetadata();
    }

    public QNoteData(Path<? extends NoteData> path) {
        super(path.getType(), path.getMetadata(), "null", "NOTE_DATA");
        addMetadata();
    }

    public QNoteData(PathMetadata metadata) {
        super(NoteData.class, metadata, "null", "NOTE_DATA");
        addMetadata();
    }

    public void addMetadata() {
        addMetadata(createdByUserLogin, ColumnMetadata.named("CREATED_BY_USER_LOGIN").withIndex(14).ofType(Types.VARCHAR).withSize(250));
        addMetadata(createdStamp, ColumnMetadata.named("CREATED_STAMP").withIndex(7).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(createdTxStamp, ColumnMetadata.named("CREATED_TX_STAMP").withIndex(8).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(isPublic, ColumnMetadata.named("IS_PUBLIC").withIndex(12).ofType(Types.CHAR).withSize(1));
        addMetadata(lastModifiedByUserLogin, ColumnMetadata.named("LAST_MODIFIED_BY_USER_LOGIN").withIndex(13).ofType(Types.VARCHAR).withSize(250));
        addMetadata(lastUpdatedStamp, ColumnMetadata.named("LAST_UPDATED_STAMP").withIndex(5).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(lastUpdatedTxStamp, ColumnMetadata.named("LAST_UPDATED_TX_STAMP").withIndex(6).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(moreInfoItemId, ColumnMetadata.named("MORE_INFO_ITEM_ID").withIndex(11).ofType(Types.VARCHAR).withSize(255));
        addMetadata(moreInfoPortletId, ColumnMetadata.named("MORE_INFO_PORTLET_ID").withIndex(10).ofType(Types.VARCHAR).withSize(255));
        addMetadata(noteDateTime, ColumnMetadata.named("NOTE_DATE_TIME").withIndex(4).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(noteId, ColumnMetadata.named("NOTE_ID").withIndex(1).ofType(Types.VARCHAR).withSize(20).notNull());
        addMetadata(noteInfo, ColumnMetadata.named("NOTE_INFO").withIndex(3).ofType(Types.LONGVARCHAR).withSize(2147483647));
        addMetadata(noteInfoLang, ColumnMetadata.named("NOTE_INFO_LANG").withIndex(16).ofType(Types.LONGVARCHAR).withSize(2147483647));
        addMetadata(noteName, ColumnMetadata.named("NOTE_NAME").withIndex(2).ofType(Types.VARCHAR).withSize(100));
        addMetadata(noteNameLang, ColumnMetadata.named("NOTE_NAME_LANG").withIndex(15).ofType(Types.VARCHAR).withSize(100));
        addMetadata(noteParty, ColumnMetadata.named("NOTE_PARTY").withIndex(9).ofType(Types.VARCHAR).withSize(20));
    }

}

