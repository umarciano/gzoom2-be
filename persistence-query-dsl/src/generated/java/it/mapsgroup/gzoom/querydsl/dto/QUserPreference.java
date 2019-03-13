package it.mapsgroup.gzoom.querydsl.dto;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;

import com.querydsl.sql.ColumnMetadata;
import java.sql.Types;




/**
 * QUserPreference is a Querydsl query type for UserPreference
 */
@Generated("com.querydsl.sql.codegen.MetaDataSerializer")
public class QUserPreference extends com.querydsl.sql.RelationalPathBase<UserPreference> {

    private static final long serialVersionUID = 816104465;

    public static final QUserPreference userPreference = new QUserPreference("USER_PREFERENCE");

    public final DateTimePath<java.time.LocalDateTime> createdStamp = createDateTime("createdStamp", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> createdTxStamp = createDateTime("createdTxStamp", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> lastUpdatedStamp = createDateTime("lastUpdatedStamp", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> lastUpdatedTxStamp = createDateTime("lastUpdatedTxStamp", java.time.LocalDateTime.class);

    public final StringPath userLoginId = createString("userLoginId");

    public final StringPath userPrefDataType = createString("userPrefDataType");

    public final StringPath userPrefGroupTypeId = createString("userPrefGroupTypeId");

    public final StringPath userPrefTypeId = createString("userPrefTypeId");

    public final StringPath userPrefValue = createString("userPrefValue");

    public final StringPath xmlUserPref = createString("xmlUserPref");

    public final com.querydsl.sql.PrimaryKey<UserPreference> primary = createPrimaryKey(userLoginId, userPrefTypeId);

    public QUserPreference(String variable) {
        super(UserPreference.class, forVariable(variable), "null", "USER_PREFERENCE");
        addMetadata();
    }

    public QUserPreference(String variable, String schema, String table) {
        super(UserPreference.class, forVariable(variable), schema, table);
        addMetadata();
    }

    public QUserPreference(String variable, String schema) {
        super(UserPreference.class, forVariable(variable), schema, "USER_PREFERENCE");
        addMetadata();
    }

    public QUserPreference(Path<? extends UserPreference> path) {
        super(path.getType(), path.getMetadata(), "null", "USER_PREFERENCE");
        addMetadata();
    }

    public QUserPreference(PathMetadata metadata) {
        super(UserPreference.class, metadata, "null", "USER_PREFERENCE");
        addMetadata();
    }

    public void addMetadata() {
        addMetadata(createdStamp, ColumnMetadata.named("CREATED_STAMP").withIndex(9).ofType(Types.TIMESTAMP).withSize(19));
        addMetadata(createdTxStamp, ColumnMetadata.named("CREATED_TX_STAMP").withIndex(10).ofType(Types.TIMESTAMP).withSize(19));
        addMetadata(lastUpdatedStamp, ColumnMetadata.named("LAST_UPDATED_STAMP").withIndex(7).ofType(Types.TIMESTAMP).withSize(19));
        addMetadata(lastUpdatedTxStamp, ColumnMetadata.named("LAST_UPDATED_TX_STAMP").withIndex(8).ofType(Types.TIMESTAMP).withSize(19));
        addMetadata(userLoginId, ColumnMetadata.named("USER_LOGIN_ID").withIndex(1).ofType(Types.VARCHAR).withSize(250).notNull());
        addMetadata(userPrefDataType, ColumnMetadata.named("USER_PREF_DATA_TYPE").withIndex(5).ofType(Types.VARCHAR).withSize(60));
        addMetadata(userPrefGroupTypeId, ColumnMetadata.named("USER_PREF_GROUP_TYPE_ID").withIndex(3).ofType(Types.VARCHAR).withSize(60));
        addMetadata(userPrefTypeId, ColumnMetadata.named("USER_PREF_TYPE_ID").withIndex(2).ofType(Types.VARCHAR).withSize(60).notNull());
        addMetadata(userPrefValue, ColumnMetadata.named("USER_PREF_VALUE").withIndex(4).ofType(Types.VARCHAR).withSize(255));
        addMetadata(xmlUserPref, ColumnMetadata.named("XML_USER_PREF").withIndex(6).ofType(Types.LONGVARCHAR).withSize(2147483647));
    }

}

