package it.mapsgroup.gzoom.querydsl.dto;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;

import com.querydsl.sql.ColumnMetadata;
import java.sql.Types;




/**
 * QContactMech is a Querydsl query type for ContactMech
 */
@Generated("com.querydsl.sql.codegen.MetaDataSerializer")
public class QContactMech extends com.querydsl.sql.RelationalPathBase<ContactMech> {

    private static final long serialVersionUID = 711257874;

    public static final QContactMech contactMech = new QContactMech("CONTACT_MECH");

    public final StringPath contactMechId = createString("contactMechId");

    public final StringPath contactMechTypeId = createString("contactMechTypeId");

    public final StringPath createdByUserLogin = createString("createdByUserLogin");

    public final DateTimePath<java.time.LocalDateTime> createdStamp = createDateTime("createdStamp", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> createdTxStamp = createDateTime("createdTxStamp", java.time.LocalDateTime.class);

    public final StringPath infoString = createString("infoString");

    public final StringPath lastModifiedByUserLogin = createString("lastModifiedByUserLogin");

    public final DateTimePath<java.time.LocalDateTime> lastUpdatedStamp = createDateTime("lastUpdatedStamp", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> lastUpdatedTxStamp = createDateTime("lastUpdatedTxStamp", java.time.LocalDateTime.class);

    public final com.querydsl.sql.PrimaryKey<ContactMech> primary = createPrimaryKey(contactMechId);

    public final com.querydsl.sql.ForeignKey<PartyContactMech> _partyCmechCmech = createInvForeignKey(contactMechId, "CONTACT_MECH_ID");

    public final com.querydsl.sql.ForeignKey<CommunicationEvent> _comEvntTcm = createInvForeignKey(contactMechId, "CONTACT_MECH_ID_TO");

    public final com.querydsl.sql.ForeignKey<CommunicationEvent> _comEvntFcm = createInvForeignKey(contactMechId, "CONTACT_MECH_ID_FROM");

    public QContactMech(String variable) {
        super(ContactMech.class, forVariable(variable), "null", "CONTACT_MECH");
        addMetadata();
    }

    public QContactMech(String variable, String schema, String table) {
        super(ContactMech.class, forVariable(variable), schema, table);
        addMetadata();
    }

    public QContactMech(String variable, String schema) {
        super(ContactMech.class, forVariable(variable), schema, "CONTACT_MECH");
        addMetadata();
    }

    public QContactMech(Path<? extends ContactMech> path) {
        super(path.getType(), path.getMetadata(), "null", "CONTACT_MECH");
        addMetadata();
    }

    public QContactMech(PathMetadata metadata) {
        super(ContactMech.class, metadata, "null", "CONTACT_MECH");
        addMetadata();
    }

    public void addMetadata() {
        addMetadata(contactMechId, ColumnMetadata.named("CONTACT_MECH_ID").withIndex(1).ofType(Types.VARCHAR).withSize(20).notNull());
        addMetadata(contactMechTypeId, ColumnMetadata.named("CONTACT_MECH_TYPE_ID").withIndex(2).ofType(Types.VARCHAR).withSize(20));
        addMetadata(createdByUserLogin, ColumnMetadata.named("CREATED_BY_USER_LOGIN").withIndex(9).ofType(Types.VARCHAR).withSize(250));
        addMetadata(createdStamp, ColumnMetadata.named("CREATED_STAMP").withIndex(6).ofType(Types.TIMESTAMP).withSize(19));
        addMetadata(createdTxStamp, ColumnMetadata.named("CREATED_TX_STAMP").withIndex(7).ofType(Types.TIMESTAMP).withSize(19));
        addMetadata(infoString, ColumnMetadata.named("INFO_STRING").withIndex(3).ofType(Types.VARCHAR).withSize(255));
        addMetadata(lastModifiedByUserLogin, ColumnMetadata.named("LAST_MODIFIED_BY_USER_LOGIN").withIndex(8).ofType(Types.VARCHAR).withSize(250));
        addMetadata(lastUpdatedStamp, ColumnMetadata.named("LAST_UPDATED_STAMP").withIndex(4).ofType(Types.TIMESTAMP).withSize(19));
        addMetadata(lastUpdatedTxStamp, ColumnMetadata.named("LAST_UPDATED_TX_STAMP").withIndex(5).ofType(Types.TIMESTAMP).withSize(19));
    }

}

