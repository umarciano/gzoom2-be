package it.mapsgroup.gzoom.querydsl.dto;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;

import com.querydsl.sql.ColumnMetadata;
import java.sql.Types;




/**
 * QGlAccountType is a Querydsl query type for GlAccountType
 */
@Generated("com.querydsl.sql.codegen.MetaDataSerializer")
public class QGlAccountType extends com.querydsl.sql.RelationalPathBase<GlAccountType> {

    private static final long serialVersionUID = -673011081;

    public static final QGlAccountType glAccountType = new QGlAccountType("GL_ACCOUNT_TYPE");

    public final StringPath accountTypeEnumId = createString("accountTypeEnumId");

    public final StringPath createdByUserLogin = createString("createdByUserLogin");

    public final DateTimePath<java.time.LocalDateTime> createdStamp = createDateTime("createdStamp", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> createdTxStamp = createDateTime("createdTxStamp", java.time.LocalDateTime.class);

    public final StringPath description = createString("description");

    public final StringPath descriptionLang = createString("descriptionLang");

    public final StringPath glAccountTypeId = createString("glAccountTypeId");

    public final BooleanPath hasTable = createBoolean("hasTable");

    public final BooleanPath isReservedAccount = createBoolean("isReservedAccount");

    public final StringPath lastModifiedByUserLogin = createString("lastModifiedByUserLogin");

    public final DateTimePath<java.time.LocalDateTime> lastUpdatedStamp = createDateTime("lastUpdatedStamp", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> lastUpdatedTxStamp = createDateTime("lastUpdatedTxStamp", java.time.LocalDateTime.class);

    public final StringPath parentTypeId = createString("parentTypeId");

    public final com.querydsl.sql.PrimaryKey<GlAccountType> primary = createPrimaryKey(glAccountTypeId);

    public final com.querydsl.sql.ForeignKey<Enumeration> gatTypeenum = createForeignKey(accountTypeEnumId, "ENUM_ID");

    public final com.querydsl.sql.ForeignKey<GlAccountType> glacttyPar = createForeignKey(parentTypeId, "GL_ACCOUNT_TYPE_ID");

    public final com.querydsl.sql.ForeignKey<GlAccount> _glacctType = createInvForeignKey(glAccountTypeId, "GL_ACCOUNT_TYPE_ID");

    public final com.querydsl.sql.ForeignKey<AcctgTransEntry> _accttxentGlactt = createInvForeignKey(glAccountTypeId, "GL_ACCOUNT_TYPE_ID");

    public final com.querydsl.sql.ForeignKey<GlAccountType> _glacttyPar = createInvForeignKey(glAccountTypeId, "PARENT_TYPE_ID");

    public QGlAccountType(String variable) {
        super(GlAccountType.class, forVariable(variable), "null", "GL_ACCOUNT_TYPE");
        addMetadata();
    }

    public QGlAccountType(String variable, String schema, String table) {
        super(GlAccountType.class, forVariable(variable), schema, table);
        addMetadata();
    }

    public QGlAccountType(String variable, String schema) {
        super(GlAccountType.class, forVariable(variable), schema, "GL_ACCOUNT_TYPE");
        addMetadata();
    }

    public QGlAccountType(Path<? extends GlAccountType> path) {
        super(path.getType(), path.getMetadata(), "null", "GL_ACCOUNT_TYPE");
        addMetadata();
    }

    public QGlAccountType(PathMetadata metadata) {
        super(GlAccountType.class, metadata, "null", "GL_ACCOUNT_TYPE");
        addMetadata();
    }

    public void addMetadata() {
        addMetadata(accountTypeEnumId, ColumnMetadata.named("ACCOUNT_TYPE_ENUM_ID").withIndex(9).ofType(Types.VARCHAR).withSize(20));
        addMetadata(createdByUserLogin, ColumnMetadata.named("CREATED_BY_USER_LOGIN").withIndex(13).ofType(Types.VARCHAR).withSize(250));
        addMetadata(createdStamp, ColumnMetadata.named("CREATED_STAMP").withIndex(7).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(createdTxStamp, ColumnMetadata.named("CREATED_TX_STAMP").withIndex(8).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(description, ColumnMetadata.named("DESCRIPTION").withIndex(4).ofType(Types.VARCHAR).withSize(255));
        addMetadata(descriptionLang, ColumnMetadata.named("DESCRIPTION_LANG").withIndex(11).ofType(Types.VARCHAR).withSize(255));
        addMetadata(glAccountTypeId, ColumnMetadata.named("GL_ACCOUNT_TYPE_ID").withIndex(1).ofType(Types.VARCHAR).withSize(20).notNull());
        addMetadata(hasTable, ColumnMetadata.named("HAS_TABLE").withIndex(3).ofType(Types.CHAR).withSize(1));
        addMetadata(isReservedAccount, ColumnMetadata.named("IS_RESERVED_ACCOUNT").withIndex(10).ofType(Types.CHAR).withSize(1));
        addMetadata(lastModifiedByUserLogin, ColumnMetadata.named("LAST_MODIFIED_BY_USER_LOGIN").withIndex(12).ofType(Types.VARCHAR).withSize(250));
        addMetadata(lastUpdatedStamp, ColumnMetadata.named("LAST_UPDATED_STAMP").withIndex(5).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(lastUpdatedTxStamp, ColumnMetadata.named("LAST_UPDATED_TX_STAMP").withIndex(6).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(parentTypeId, ColumnMetadata.named("PARENT_TYPE_ID").withIndex(2).ofType(Types.VARCHAR).withSize(20));
    }

}

