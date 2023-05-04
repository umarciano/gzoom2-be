package it.mapsgroup.gzoom.querydsl.dto;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;

import com.querydsl.sql.ColumnMetadata;
import java.sql.Types;




/**
 * QGlAccountInterfaceHist is a Querydsl query type for GlAccountInterfaceHist
 */
@Generated("com.querydsl.sql.codegen.MetaDataSerializer")
public class QGlAccountInterfaceHist extends com.querydsl.sql.RelationalPathBase<GlAccountInterfaceHist> {

    private static final long serialVersionUID = 486454622;

    public static final QGlAccountInterfaceHist glAccountInterfaceHist = new QGlAccountInterfaceHist("GL_ACCOUNT_INTERFACE_HIST");

    public final StringPath accountCode = createString("accountCode");

    public final StringPath accountName = createString("accountName");

    public final StringPath accountNameLang = createString("accountNameLang");

    public final StringPath accountTypeEnumId = createString("accountTypeEnumId");

    public final StringPath accountTypeId = createString("accountTypeId");

    public final StringPath calcCustomMethodId = createString("calcCustomMethodId");

    public final DateTimePath<java.time.LocalDateTime> createdStamp = createDateTime("createdStamp", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> createdTxStamp = createDateTime("createdTxStamp", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> customDate01 = createDateTime("customDate01", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> customDate02 = createDateTime("customDate02", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> customDate03 = createDateTime("customDate03", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> customDate04 = createDateTime("customDate04", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> customDate05 = createDateTime("customDate05", java.time.LocalDateTime.class);

    public final StringPath customText01 = createString("customText01");

    public final StringPath customText02 = createString("customText02");

    public final StringPath customText03 = createString("customText03");

    public final StringPath customText04 = createString("customText04");

    public final StringPath customText05 = createString("customText05");

    public final NumberPath<java.math.BigDecimal> customValue01 = createNumber("customValue01", java.math.BigDecimal.class);

    public final NumberPath<java.math.BigDecimal> customValue02 = createNumber("customValue02", java.math.BigDecimal.class);

    public final NumberPath<java.math.BigDecimal> customValue03 = createNumber("customValue03", java.math.BigDecimal.class);

    public final NumberPath<java.math.BigDecimal> customValue04 = createNumber("customValue04", java.math.BigDecimal.class);

    public final NumberPath<java.math.BigDecimal> customValue05 = createNumber("customValue05", java.math.BigDecimal.class);

    public final StringPath dataSource = createString("dataSource");

    public final BooleanPath debitCreditDefault = createBoolean("debitCreditDefault");

    public final StringPath defaultUomCode = createString("defaultUomCode");

    public final StringPath description = createString("description");

    public final StringPath descriptionLang = createString("descriptionLang");

    public final BooleanPath detectOrgUnitIdFlag = createBoolean("detectOrgUnitIdFlag");

    public final DateTimePath<java.time.LocalDateTime> fromDate = createDateTime("fromDate", java.time.LocalDateTime.class);

    public final StringPath glAccountClassCode = createString("glAccountClassCode");

    public final StringPath glResourceTypeId = createString("glResourceTypeId");

    public final StringPath histJobLogId = createString("histJobLogId");

    public final StringPath id = createString("id");

    public final StringPath inputEnumId = createString("inputEnumId");

    public final DateTimePath<java.time.LocalDateTime> lastUpdatedStamp = createDateTime("lastUpdatedStamp", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> lastUpdatedTxStamp = createDateTime("lastUpdatedTxStamp", java.time.LocalDateTime.class);

    public final StringPath partyIdCdc = createString("partyIdCdc");

    public final StringPath partyIdCdr = createString("partyIdCdr");

    public final StringPath periodTypeDesc = createString("periodTypeDesc");

    public final NumberPath<java.math.BigInteger> prioCalc = createNumber("prioCalc", java.math.BigInteger.class);

    public final StringPath productId = createString("productId");

    public final StringPath purposeTypeId = createString("purposeTypeId");

    public final DateTimePath<java.time.LocalDateTime> refDate = createDateTime("refDate", java.time.LocalDateTime.class);

    public final StringPath referencedAccountCode = createString("referencedAccountCode");

    public final StringPath roleTypeIdCdc = createString("roleTypeIdCdc");

    public final StringPath roleTypeIdCdr = createString("roleTypeIdCdr");

    public final NumberPath<java.math.BigInteger> seq = createNumber("seq", java.math.BigInteger.class);

    public final StringPath source = createString("source");

    public final StringPath sourceLang = createString("sourceLang");

    public final StringPath stato = createString("stato");

    public final DateTimePath<java.time.LocalDateTime> thruDate = createDateTime("thruDate", java.time.LocalDateTime.class);

    public final StringPath uomRangeId = createString("uomRangeId");

    public final StringPath weMeasureTypeEnumId = createString("weMeasureTypeEnumId");

    public final com.querydsl.sql.PrimaryKey<GlAccountInterfaceHist> primary = createPrimaryKey(id);

    public QGlAccountInterfaceHist(String variable) {
        super(GlAccountInterfaceHist.class, forVariable(variable), "null", "GL_ACCOUNT_INTERFACE_HIST");
        addMetadata();
    }

    public QGlAccountInterfaceHist(String variable, String schema, String table) {
        super(GlAccountInterfaceHist.class, forVariable(variable), schema, table);
        addMetadata();
    }

    public QGlAccountInterfaceHist(String variable, String schema) {
        super(GlAccountInterfaceHist.class, forVariable(variable), schema, "GL_ACCOUNT_INTERFACE_HIST");
        addMetadata();
    }

    public QGlAccountInterfaceHist(Path<? extends GlAccountInterfaceHist> path) {
        super(path.getType(), path.getMetadata(), "null", "GL_ACCOUNT_INTERFACE_HIST");
        addMetadata();
    }

    public QGlAccountInterfaceHist(PathMetadata metadata) {
        super(GlAccountInterfaceHist.class, metadata, "null", "GL_ACCOUNT_INTERFACE_HIST");
        addMetadata();
    }

    public void addMetadata() {
        addMetadata(accountCode, ColumnMetadata.named("ACCOUNT_CODE").withIndex(5).ofType(Types.VARCHAR).withSize(100).notNull());
        addMetadata(accountName, ColumnMetadata.named("ACCOUNT_NAME").withIndex(7).ofType(Types.VARCHAR).withSize(2000).notNull());
        addMetadata(accountNameLang, ColumnMetadata.named("ACCOUNT_NAME_LANG").withIndex(25).ofType(Types.VARCHAR).withSize(2000));
        addMetadata(accountTypeEnumId, ColumnMetadata.named("ACCOUNT_TYPE_ENUM_ID").withIndex(19).ofType(Types.VARCHAR).withSize(20));
        addMetadata(accountTypeId, ColumnMetadata.named("ACCOUNT_TYPE_ID").withIndex(6).ofType(Types.VARCHAR).withSize(20).notNull());
        addMetadata(calcCustomMethodId, ColumnMetadata.named("CALC_CUSTOM_METHOD_ID").withIndex(29).ofType(Types.VARCHAR).withSize(20));
        addMetadata(createdStamp, ColumnMetadata.named("CREATED_STAMP").withIndex(37).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(createdTxStamp, ColumnMetadata.named("CREATED_TX_STAMP").withIndex(38).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(customDate01, ColumnMetadata.named("CUSTOM_DATE01").withIndex(44).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(customDate02, ColumnMetadata.named("CUSTOM_DATE02").withIndex(45).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(customDate03, ColumnMetadata.named("CUSTOM_DATE03").withIndex(46).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(customDate04, ColumnMetadata.named("CUSTOM_DATE04").withIndex(47).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(customDate05, ColumnMetadata.named("CUSTOM_DATE05").withIndex(48).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(customText01, ColumnMetadata.named("CUSTOM_TEXT01").withIndex(39).ofType(Types.VARCHAR).withSize(255));
        addMetadata(customText02, ColumnMetadata.named("CUSTOM_TEXT02").withIndex(40).ofType(Types.VARCHAR).withSize(255));
        addMetadata(customText03, ColumnMetadata.named("CUSTOM_TEXT03").withIndex(41).ofType(Types.VARCHAR).withSize(255));
        addMetadata(customText04, ColumnMetadata.named("CUSTOM_TEXT04").withIndex(42).ofType(Types.VARCHAR).withSize(255));
        addMetadata(customText05, ColumnMetadata.named("CUSTOM_TEXT05").withIndex(43).ofType(Types.VARCHAR).withSize(255));
        addMetadata(customValue01, ColumnMetadata.named("CUSTOM_VALUE01").withIndex(49).ofType(Types.DECIMAL).withSize(18).withDigits(6));
        addMetadata(customValue02, ColumnMetadata.named("CUSTOM_VALUE02").withIndex(50).ofType(Types.DECIMAL).withSize(18).withDigits(6));
        addMetadata(customValue03, ColumnMetadata.named("CUSTOM_VALUE03").withIndex(51).ofType(Types.DECIMAL).withSize(18).withDigits(6));
        addMetadata(customValue04, ColumnMetadata.named("CUSTOM_VALUE04").withIndex(52).ofType(Types.DECIMAL).withSize(18).withDigits(6));
        addMetadata(customValue05, ColumnMetadata.named("CUSTOM_VALUE05").withIndex(53).ofType(Types.DECIMAL).withSize(18).withDigits(6));
        addMetadata(dataSource, ColumnMetadata.named("DATA_SOURCE").withIndex(3).ofType(Types.VARCHAR).withSize(20).notNull());
        addMetadata(debitCreditDefault, ColumnMetadata.named("DEBIT_CREDIT_DEFAULT").withIndex(16).ofType(Types.CHAR).withSize(1));
        addMetadata(defaultUomCode, ColumnMetadata.named("DEFAULT_UOM_CODE").withIndex(20).ofType(Types.VARCHAR).withSize(20));
        addMetadata(description, ColumnMetadata.named("DESCRIPTION").withIndex(8).ofType(Types.VARCHAR).withSize(255));
        addMetadata(descriptionLang, ColumnMetadata.named("DESCRIPTION_LANG").withIndex(26).ofType(Types.VARCHAR).withSize(255));
        addMetadata(detectOrgUnitIdFlag, ColumnMetadata.named("DETECT_ORG_UNIT_ID_FLAG").withIndex(31).ofType(Types.CHAR).withSize(1));
        addMetadata(fromDate, ColumnMetadata.named("FROM_DATE").withIndex(17).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(glAccountClassCode, ColumnMetadata.named("GL_ACCOUNT_CLASS_CODE").withIndex(33).ofType(Types.VARCHAR).withSize(20));
        addMetadata(glResourceTypeId, ColumnMetadata.named("GL_RESOURCE_TYPE_ID").withIndex(22).ofType(Types.VARCHAR).withSize(20));
        addMetadata(histJobLogId, ColumnMetadata.named("HIST_JOB_LOG_ID").withIndex(34).ofType(Types.VARCHAR).withSize(20));
        addMetadata(id, ColumnMetadata.named("ID").withIndex(1).ofType(Types.VARCHAR).withSize(20).notNull());
        addMetadata(inputEnumId, ColumnMetadata.named("INPUT_ENUM_ID").withIndex(28).ofType(Types.VARCHAR).withSize(20));
        addMetadata(lastUpdatedStamp, ColumnMetadata.named("LAST_UPDATED_STAMP").withIndex(35).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(lastUpdatedTxStamp, ColumnMetadata.named("LAST_UPDATED_TX_STAMP").withIndex(36).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(partyIdCdc, ColumnMetadata.named("PARTY_ID_CDC").withIndex(13).ofType(Types.VARCHAR).withSize(20));
        addMetadata(partyIdCdr, ColumnMetadata.named("PARTY_ID_CDR").withIndex(11).ofType(Types.VARCHAR).withSize(20));
        addMetadata(periodTypeDesc, ColumnMetadata.named("PERIOD_TYPE_DESC").withIndex(21).ofType(Types.VARCHAR).withSize(255));
        addMetadata(prioCalc, ColumnMetadata.named("PRIO_CALC").withIndex(30).ofType(Types.DECIMAL).withSize(20));
        addMetadata(productId, ColumnMetadata.named("PRODUCT_ID").withIndex(10).ofType(Types.VARCHAR).withSize(20));
        addMetadata(purposeTypeId, ColumnMetadata.named("PURPOSE_TYPE_ID").withIndex(15).ofType(Types.VARCHAR).withSize(20));
        addMetadata(refDate, ColumnMetadata.named("REF_DATE").withIndex(4).ofType(Types.TIMESTAMP).withSize(26).notNull());
        addMetadata(referencedAccountCode, ColumnMetadata.named("REFERENCED_ACCOUNT_CODE").withIndex(9).ofType(Types.VARCHAR).withSize(20));
        addMetadata(roleTypeIdCdc, ColumnMetadata.named("ROLE_TYPE_ID_CDC").withIndex(14).ofType(Types.VARCHAR).withSize(20));
        addMetadata(roleTypeIdCdr, ColumnMetadata.named("ROLE_TYPE_ID_CDR").withIndex(12).ofType(Types.VARCHAR).withSize(20));
        addMetadata(seq, ColumnMetadata.named("SEQ").withIndex(54).ofType(Types.DECIMAL).withSize(20));
        addMetadata(source, ColumnMetadata.named("SOURCE").withIndex(23).ofType(Types.VARCHAR).withSize(255));
        addMetadata(sourceLang, ColumnMetadata.named("SOURCE_LANG").withIndex(27).ofType(Types.VARCHAR).withSize(255));
        addMetadata(stato, ColumnMetadata.named("STATO").withIndex(2).ofType(Types.VARCHAR).withSize(20));
        addMetadata(thruDate, ColumnMetadata.named("THRU_DATE").withIndex(18).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(uomRangeId, ColumnMetadata.named("UOM_RANGE_ID").withIndex(32).ofType(Types.VARCHAR).withSize(20));
        addMetadata(weMeasureTypeEnumId, ColumnMetadata.named("WE_MEASURE_TYPE_ENUM_ID").withIndex(24).ofType(Types.VARCHAR).withSize(20));
    }

}

