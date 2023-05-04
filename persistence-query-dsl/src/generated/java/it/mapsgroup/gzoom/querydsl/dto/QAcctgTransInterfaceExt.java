package it.mapsgroup.gzoom.querydsl.dto;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;

import com.querydsl.sql.ColumnMetadata;
import java.sql.Types;




/**
 * QAcctgTransInterfaceExt is a Querydsl query type for AcctgTransInterfaceExt
 */
@Generated("com.querydsl.sql.codegen.MetaDataSerializer")
public class QAcctgTransInterfaceExt extends com.querydsl.sql.RelationalPathBase<AcctgTransInterfaceExt> {

    private static final long serialVersionUID = 880349863;

    public static final QAcctgTransInterfaceExt acctgTransInterfaceExt = new QAcctgTransInterfaceExt("ACCTG_TRANS_INTERFACE_EXT");

    public final NumberPath<java.math.BigDecimal> amount = createNumber("amount", java.math.BigDecimal.class);

    public final StringPath amountCode = createString("amountCode");

    public final StringPath comments = createString("comments");

    public final StringPath commentsLang = createString("commentsLang");

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

    public final DateTimePath<java.time.LocalDateTime> fromDateCompetence = createDateTime("fromDateCompetence", java.time.LocalDateTime.class);

    public final StringPath glAccountCode = createString("glAccountCode");

    public final StringPath glAccountTypeEnumId = createString("glAccountTypeEnumId");

    public final StringPath glFiscalTypeId = createString("glFiscalTypeId");

    public final StringPath id = createString("id");

    public final StringPath note = createString("note");

    public final StringPath noteLang = createString("noteLang");

    public final StringPath partyCode = createString("partyCode");

    public final StringPath productCode = createString("productCode");

    public final DateTimePath<java.time.LocalDateTime> refDate = createDateTime("refDate", java.time.LocalDateTime.class);

    public final StringPath roleTypeId = createString("roleTypeId");

    public final NumberPath<java.math.BigInteger> seq = createNumber("seq", java.math.BigInteger.class);

    public final StringPath stato = createString("stato");

    public final DateTimePath<java.time.LocalDateTime> toDateCompetence = createDateTime("toDateCompetence", java.time.LocalDateTime.class);

    public final StringPath uomDescr = createString("uomDescr");

    public final StringPath uorgCode = createString("uorgCode");

    public final StringPath uorgRoleTypeId = createString("uorgRoleTypeId");

    public final StringPath voucherRef = createString("voucherRef");

    public final StringPath workEffortCode = createString("workEffortCode");

    public final com.querydsl.sql.PrimaryKey<AcctgTransInterfaceExt> primary = createPrimaryKey(id);

    public QAcctgTransInterfaceExt(String variable) {
        super(AcctgTransInterfaceExt.class, forVariable(variable), "null", "ACCTG_TRANS_INTERFACE_EXT");
        addMetadata();
    }

    public QAcctgTransInterfaceExt(String variable, String schema, String table) {
        super(AcctgTransInterfaceExt.class, forVariable(variable), schema, table);
        addMetadata();
    }

    public QAcctgTransInterfaceExt(String variable, String schema) {
        super(AcctgTransInterfaceExt.class, forVariable(variable), schema, "ACCTG_TRANS_INTERFACE_EXT");
        addMetadata();
    }

    public QAcctgTransInterfaceExt(Path<? extends AcctgTransInterfaceExt> path) {
        super(path.getType(), path.getMetadata(), "null", "ACCTG_TRANS_INTERFACE_EXT");
        addMetadata();
    }

    public QAcctgTransInterfaceExt(PathMetadata metadata) {
        super(AcctgTransInterfaceExt.class, metadata, "null", "ACCTG_TRANS_INTERFACE_EXT");
        addMetadata();
    }

    public void addMetadata() {
        addMetadata(amount, ColumnMetadata.named("AMOUNT").withIndex(9).ofType(Types.DECIMAL).withSize(18).withDigits(6));
        addMetadata(amountCode, ColumnMetadata.named("AMOUNT_CODE").withIndex(10).ofType(Types.VARCHAR).withSize(255));
        addMetadata(comments, ColumnMetadata.named("COMMENTS").withIndex(15).ofType(Types.VARCHAR).withSize(2000));
        addMetadata(commentsLang, ColumnMetadata.named("COMMENTS_LANG").withIndex(23).ofType(Types.VARCHAR).withSize(2000));
        addMetadata(customDate01, ColumnMetadata.named("CUSTOM_DATE01").withIndex(29).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(customDate02, ColumnMetadata.named("CUSTOM_DATE02").withIndex(30).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(customDate03, ColumnMetadata.named("CUSTOM_DATE03").withIndex(31).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(customDate04, ColumnMetadata.named("CUSTOM_DATE04").withIndex(32).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(customDate05, ColumnMetadata.named("CUSTOM_DATE05").withIndex(33).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(customText01, ColumnMetadata.named("CUSTOM_TEXT01").withIndex(24).ofType(Types.VARCHAR).withSize(255));
        addMetadata(customText02, ColumnMetadata.named("CUSTOM_TEXT02").withIndex(25).ofType(Types.VARCHAR).withSize(255));
        addMetadata(customText03, ColumnMetadata.named("CUSTOM_TEXT03").withIndex(26).ofType(Types.VARCHAR).withSize(255));
        addMetadata(customText04, ColumnMetadata.named("CUSTOM_TEXT04").withIndex(27).ofType(Types.VARCHAR).withSize(255));
        addMetadata(customText05, ColumnMetadata.named("CUSTOM_TEXT05").withIndex(28).ofType(Types.VARCHAR).withSize(255));
        addMetadata(customValue01, ColumnMetadata.named("CUSTOM_VALUE01").withIndex(34).ofType(Types.DECIMAL).withSize(18).withDigits(6));
        addMetadata(customValue02, ColumnMetadata.named("CUSTOM_VALUE02").withIndex(35).ofType(Types.DECIMAL).withSize(18).withDigits(6));
        addMetadata(customValue03, ColumnMetadata.named("CUSTOM_VALUE03").withIndex(36).ofType(Types.DECIMAL).withSize(18).withDigits(6));
        addMetadata(customValue04, ColumnMetadata.named("CUSTOM_VALUE04").withIndex(37).ofType(Types.DECIMAL).withSize(18).withDigits(6));
        addMetadata(customValue05, ColumnMetadata.named("CUSTOM_VALUE05").withIndex(38).ofType(Types.DECIMAL).withSize(18).withDigits(6));
        addMetadata(dataSource, ColumnMetadata.named("DATA_SOURCE").withIndex(3).ofType(Types.VARCHAR).withSize(20).notNull());
        addMetadata(fromDateCompetence, ColumnMetadata.named("FROM_DATE_COMPETENCE").withIndex(20).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(glAccountCode, ColumnMetadata.named("GL_ACCOUNT_CODE").withIndex(8).ofType(Types.VARCHAR).withSize(100).notNull());
        addMetadata(glAccountTypeEnumId, ColumnMetadata.named("GL_ACCOUNT_TYPE_ENUM_ID").withIndex(7).ofType(Types.VARCHAR).withSize(20).notNull());
        addMetadata(glFiscalTypeId, ColumnMetadata.named("GL_FISCAL_TYPE_ID").withIndex(11).ofType(Types.VARCHAR).withSize(20).notNull());
        addMetadata(id, ColumnMetadata.named("ID").withIndex(1).ofType(Types.VARCHAR).withSize(20).notNull());
        addMetadata(note, ColumnMetadata.named("NOTE").withIndex(14).ofType(Types.VARCHAR).withSize(2000));
        addMetadata(noteLang, ColumnMetadata.named("NOTE_LANG").withIndex(22).ofType(Types.VARCHAR).withSize(2000));
        addMetadata(partyCode, ColumnMetadata.named("PARTY_CODE").withIndex(18).ofType(Types.VARCHAR).withSize(20).notNull());
        addMetadata(productCode, ColumnMetadata.named("PRODUCT_CODE").withIndex(16).ofType(Types.VARCHAR).withSize(20).notNull());
        addMetadata(refDate, ColumnMetadata.named("REF_DATE").withIndex(6).ofType(Types.TIMESTAMP).withSize(26).notNull());
        addMetadata(roleTypeId, ColumnMetadata.named("ROLE_TYPE_ID").withIndex(19).ofType(Types.VARCHAR).withSize(20));
        addMetadata(seq, ColumnMetadata.named("SEQ").withIndex(39).ofType(Types.DECIMAL).withSize(20));
        addMetadata(stato, ColumnMetadata.named("STATO").withIndex(2).ofType(Types.VARCHAR).withSize(20));
        addMetadata(toDateCompetence, ColumnMetadata.named("TO_DATE_COMPETENCE").withIndex(21).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(uomDescr, ColumnMetadata.named("UOM_DESCR").withIndex(5).ofType(Types.VARCHAR).withSize(255).notNull());
        addMetadata(uorgCode, ColumnMetadata.named("UORG_CODE").withIndex(12).ofType(Types.VARCHAR).withSize(20).notNull());
        addMetadata(uorgRoleTypeId, ColumnMetadata.named("UORG_ROLE_TYPE_ID").withIndex(13).ofType(Types.VARCHAR).withSize(20));
        addMetadata(voucherRef, ColumnMetadata.named("VOUCHER_REF").withIndex(4).ofType(Types.VARCHAR).withSize(60).notNull());
        addMetadata(workEffortCode, ColumnMetadata.named("WORK_EFFORT_CODE").withIndex(17).ofType(Types.VARCHAR).withSize(60).notNull());
    }

}

