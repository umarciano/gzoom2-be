package it.memelabs.smartnebula.lmm.querydsl.generated;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;

import com.querydsl.sql.ColumnMetadata;
import java.sql.Types;




/**
 * QContactMechPurposeType is a Querydsl query type for ContactMechPurposeType
 */
@Generated("com.querydsl.sql.codegen.MetaDataSerializer")
public class QContactMechPurposeType extends com.querydsl.sql.RelationalPathBase<ContactMechPurposeType> {

    private static final long serialVersionUID = -2120984810;

    public static final QContactMechPurposeType contactMechPurposeType = new QContactMechPurposeType("CONTACT_MECH_PURPOSE_TYPE");

    public final StringPath contactMechPurposeTypeId = createString("contactMechPurposeTypeId");

    public final StringPath createdByUserLogin = createString("createdByUserLogin");

    public final DateTimePath<java.time.LocalDateTime> createdStamp = createDateTime("createdStamp", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> createdTxStamp = createDateTime("createdTxStamp", java.time.LocalDateTime.class);

    public final StringPath description = createString("description");

    public final StringPath lastModifiedByUserLogin = createString("lastModifiedByUserLogin");

    public final DateTimePath<java.time.LocalDateTime> lastUpdatedStamp = createDateTime("lastUpdatedStamp", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> lastUpdatedTxStamp = createDateTime("lastUpdatedTxStamp", java.time.LocalDateTime.class);

    public final com.querydsl.sql.PrimaryKey<ContactMechPurposeType> primary = createPrimaryKey(contactMechPurposeTypeId);

    public final com.querydsl.sql.ForeignKey<PartyContactMechPurpose> _partyCmprpType = createInvForeignKey(contactMechPurposeTypeId, "CONTACT_MECH_PURPOSE_TYPE_ID");

    public QContactMechPurposeType(String variable) {
        super(ContactMechPurposeType.class, forVariable(variable), "null", "CONTACT_MECH_PURPOSE_TYPE");
        addMetadata();
    }

    public QContactMechPurposeType(String variable, String schema, String table) {
        super(ContactMechPurposeType.class, forVariable(variable), schema, table);
        addMetadata();
    }

    public QContactMechPurposeType(String variable, String schema) {
        super(ContactMechPurposeType.class, forVariable(variable), schema, "CONTACT_MECH_PURPOSE_TYPE");
        addMetadata();
    }

    public QContactMechPurposeType(Path<? extends ContactMechPurposeType> path) {
        super(path.getType(), path.getMetadata(), "null", "CONTACT_MECH_PURPOSE_TYPE");
        addMetadata();
    }

    public QContactMechPurposeType(PathMetadata metadata) {
        super(ContactMechPurposeType.class, metadata, "null", "CONTACT_MECH_PURPOSE_TYPE");
        addMetadata();
    }

    public void addMetadata() {
        addMetadata(contactMechPurposeTypeId, ColumnMetadata.named("CONTACT_MECH_PURPOSE_TYPE_ID").withIndex(1).ofType(Types.VARCHAR).withSize(20).notNull());
        addMetadata(createdByUserLogin, ColumnMetadata.named("CREATED_BY_USER_LOGIN").withIndex(8).ofType(Types.VARCHAR).withSize(250));
        addMetadata(createdStamp, ColumnMetadata.named("CREATED_STAMP").withIndex(5).ofType(Types.TIMESTAMP).withSize(19));
        addMetadata(createdTxStamp, ColumnMetadata.named("CREATED_TX_STAMP").withIndex(6).ofType(Types.TIMESTAMP).withSize(19));
        addMetadata(description, ColumnMetadata.named("DESCRIPTION").withIndex(2).ofType(Types.VARCHAR).withSize(255));
        addMetadata(lastModifiedByUserLogin, ColumnMetadata.named("LAST_MODIFIED_BY_USER_LOGIN").withIndex(7).ofType(Types.VARCHAR).withSize(250));
        addMetadata(lastUpdatedStamp, ColumnMetadata.named("LAST_UPDATED_STAMP").withIndex(3).ofType(Types.TIMESTAMP).withSize(19));
        addMetadata(lastUpdatedTxStamp, ColumnMetadata.named("LAST_UPDATED_TX_STAMP").withIndex(4).ofType(Types.TIMESTAMP).withSize(19));
    }

}

