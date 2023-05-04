package it.mapsgroup.gzoom.querydsl.dto;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;

import com.querydsl.sql.ColumnMetadata;
import java.sql.Types;




/**
 * QGlFiscalType is a Querydsl query type for GlFiscalType
 */
@Generated("com.querydsl.sql.codegen.MetaDataSerializer")
public class QGlFiscalType extends com.querydsl.sql.RelationalPathBase<GlFiscalType> {

    private static final long serialVersionUID = 654148776;

    public static final QGlFiscalType glFiscalType = new QGlFiscalType("GL_FISCAL_TYPE");

    public final StringPath createdByUserLogin = createString("createdByUserLogin");

    public final DateTimePath<java.time.LocalDateTime> createdStamp = createDateTime("createdStamp", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> createdTxStamp = createDateTime("createdTxStamp", java.time.LocalDateTime.class);

    public final StringPath description = createString("description");

    public final StringPath descriptionLang = createString("descriptionLang");

    public final StringPath glFiscalTypeEnumId = createString("glFiscalTypeEnumId");

    public final StringPath glFiscalTypeId = createString("glFiscalTypeId");

    public final StringPath isAccountUsed = createString("isAccountUsed");

    public final StringPath isFinancialUsed = createString("isFinancialUsed");

    public final StringPath isIndicatorUsed = createString("isIndicatorUsed");

    public final StringPath lastModifiedByUserLogin = createString("lastModifiedByUserLogin");

    public final DateTimePath<java.time.LocalDateTime> lastUpdatedStamp = createDateTime("lastUpdatedStamp", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> lastUpdatedTxStamp = createDateTime("lastUpdatedTxStamp", java.time.LocalDateTime.class);

    public final StringPath periodicalAbsoluteEnumId = createString("periodicalAbsoluteEnumId");

    public final com.querydsl.sql.PrimaryKey<GlFiscalType> primary = createPrimaryKey(glFiscalTypeId);

    public QGlFiscalType(String variable) {
        super(GlFiscalType.class, forVariable(variable), "null", "GL_FISCAL_TYPE");
        addMetadata();
    }

    public QGlFiscalType(String variable, String schema, String table) {
        super(GlFiscalType.class, forVariable(variable), schema, table);
        addMetadata();
    }

    public QGlFiscalType(String variable, String schema) {
        super(GlFiscalType.class, forVariable(variable), schema, "GL_FISCAL_TYPE");
        addMetadata();
    }

    public QGlFiscalType(Path<? extends GlFiscalType> path) {
        super(path.getType(), path.getMetadata(), "null", "GL_FISCAL_TYPE");
        addMetadata();
    }

    public QGlFiscalType(PathMetadata metadata) {
        super(GlFiscalType.class, metadata, "null", "GL_FISCAL_TYPE");
        addMetadata();
    }

    public void addMetadata() {
        addMetadata(createdByUserLogin, ColumnMetadata.named("CREATED_BY_USER_LOGIN").withIndex(14).ofType(Types.VARCHAR).withSize(250));
        addMetadata(createdStamp, ColumnMetadata.named("CREATED_STAMP").withIndex(5).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(createdTxStamp, ColumnMetadata.named("CREATED_TX_STAMP").withIndex(6).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(description, ColumnMetadata.named("DESCRIPTION").withIndex(2).ofType(Types.VARCHAR).withSize(255));
        addMetadata(descriptionLang, ColumnMetadata.named("DESCRIPTION_LANG").withIndex(12).ofType(Types.VARCHAR).withSize(255));
        addMetadata(glFiscalTypeEnumId, ColumnMetadata.named("GL_FISCAL_TYPE_ENUM_ID").withIndex(8).ofType(Types.VARCHAR).withSize(20));
        addMetadata(glFiscalTypeId, ColumnMetadata.named("GL_FISCAL_TYPE_ID").withIndex(1).ofType(Types.VARCHAR).withSize(20).notNull());
        addMetadata(isAccountUsed, ColumnMetadata.named("IS_ACCOUNT_USED").withIndex(10).ofType(Types.CHAR).withSize(1));
        addMetadata(isFinancialUsed, ColumnMetadata.named("IS_FINANCIAL_USED").withIndex(9).ofType(Types.CHAR).withSize(1));
        addMetadata(isIndicatorUsed, ColumnMetadata.named("IS_INDICATOR_USED").withIndex(11).ofType(Types.CHAR).withSize(1));
        addMetadata(lastModifiedByUserLogin, ColumnMetadata.named("LAST_MODIFIED_BY_USER_LOGIN").withIndex(13).ofType(Types.VARCHAR).withSize(250));
        addMetadata(lastUpdatedStamp, ColumnMetadata.named("LAST_UPDATED_STAMP").withIndex(3).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(lastUpdatedTxStamp, ColumnMetadata.named("LAST_UPDATED_TX_STAMP").withIndex(4).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(periodicalAbsoluteEnumId, ColumnMetadata.named("PERIODICAL_ABSOLUTE_ENUM_ID").withIndex(7).ofType(Types.VARCHAR).withSize(20));
    }

}

