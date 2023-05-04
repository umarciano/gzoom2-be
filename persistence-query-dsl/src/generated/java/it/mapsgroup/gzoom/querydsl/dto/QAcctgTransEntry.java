package it.mapsgroup.gzoom.querydsl.dto;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;

import com.querydsl.sql.ColumnMetadata;
import java.sql.Types;




/**
 * QAcctgTransEntry is a Querydsl query type for AcctgTransEntry
 */
@Generated("com.querydsl.sql.codegen.MetaDataSerializer")
public class QAcctgTransEntry extends com.querydsl.sql.RelationalPathBase<AcctgTransEntry> {

    private static final long serialVersionUID = 257877203;

    public static final QAcctgTransEntry acctgTransEntry = new QAcctgTransEntry("ACCTG_TRANS_ENTRY");

    public final StringPath acctgTransEntrySeqId = createString("acctgTransEntrySeqId");

    public final StringPath acctgTransEntryTypeId = createString("acctgTransEntryTypeId");

    public final StringPath acctgTransId = createString("acctgTransId");

    public final NumberPath<java.math.BigDecimal> amount = createNumber("amount", java.math.BigDecimal.class);

    public final BooleanPath amountLocked = createBoolean("amountLocked");

    public final BooleanPath checkAmount1 = createBoolean("checkAmount1");

    public final BooleanPath checkAmount2 = createBoolean("checkAmount2");

    public final BooleanPath checkAmount3 = createBoolean("checkAmount3");

    public final StringPath createdByUserLogin = createString("createdByUserLogin");

    public final DateTimePath<java.time.LocalDateTime> createdStamp = createDateTime("createdStamp", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> createdTxStamp = createDateTime("createdTxStamp", java.time.LocalDateTime.class);

    public final StringPath currencyUomId = createString("currencyUomId");

    public final BooleanPath debitCreditFlag = createBoolean("debitCreditFlag");

    public final StringPath description = createString("description");

    public final StringPath descriptionLang = createString("descriptionLang");

    public final DatePath<java.time.LocalDate> dueDate = createDate("dueDate", java.time.LocalDate.class);

    public final StringPath emplPositionTypeId = createString("emplPositionTypeId");

    public final DateTimePath<java.time.LocalDateTime> fromDateCompetence = createDateTime("fromDateCompetence", java.time.LocalDateTime.class);

    public final StringPath glAccountFinId = createString("glAccountFinId");

    public final StringPath glAccountId = createString("glAccountId");

    public final StringPath glAccountTypeId = createString("glAccountTypeId");

    public final StringPath glFiscalTypeId = createString("glFiscalTypeId");

    public final StringPath groupId = createString("groupId");

    public final BooleanPath hasScoreAlert = createBoolean("hasScoreAlert");

    public final StringPath inventoryItemId = createString("inventoryItemId");

    public final BooleanPath isSummary = createBoolean("isSummary");

    public final StringPath lastModifiedByUserLogin = createString("lastModifiedByUserLogin");

    public final DateTimePath<java.time.LocalDateTime> lastUpdatedStamp = createDateTime("lastUpdatedStamp", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> lastUpdatedTxStamp = createDateTime("lastUpdatedTxStamp", java.time.LocalDateTime.class);

    public final StringPath organizationPartyId = createString("organizationPartyId");

    public final NumberPath<java.math.BigDecimal> origAmount = createNumber("origAmount", java.math.BigDecimal.class);

    public final StringPath origCurrencyUomId = createString("origCurrencyUomId");

    public final StringPath partyId = createString("partyId");

    public final NumberPath<java.math.BigDecimal> perfAmountActual = createNumber("perfAmountActual", java.math.BigDecimal.class);

    public final NumberPath<java.math.BigDecimal> perfAmountCalc = createNumber("perfAmountCalc", java.math.BigDecimal.class);

    public final NumberPath<java.math.BigDecimal> perfAmountMax = createNumber("perfAmountMax", java.math.BigDecimal.class);

    public final NumberPath<java.math.BigDecimal> perfAmountMin = createNumber("perfAmountMin", java.math.BigDecimal.class);

    public final NumberPath<java.math.BigDecimal> perfAmountTarget = createNumber("perfAmountTarget", java.math.BigDecimal.class);

    public final StringPath productId = createString("productId");

    public final StringPath reconcileStatusId = createString("reconcileStatusId");

    public final StringPath roleTypeId = createString("roleTypeId");

    public final StringPath settlementTermId = createString("settlementTermId");

    public final DateTimePath<java.time.LocalDateTime> snapshotDate = createDateTime("snapshotDate", java.time.LocalDateTime.class);

    public final StringPath taxId = createString("taxId");

    public final StringPath textValue1 = createString("textValue1");

    public final StringPath theirPartyId = createString("theirPartyId");

    public final StringPath theirProductId = createString("theirProductId");

    public final DateTimePath<java.time.LocalDateTime> toDateCompetence = createDateTime("toDateCompetence", java.time.LocalDateTime.class);

    public final StringPath voucherRef = createString("voucherRef");

    public final StringPath workEffortRevisionId = createString("workEffortRevisionId");

    public final StringPath workEffortSnapshotId = createString("workEffortSnapshotId");

    public final com.querydsl.sql.PrimaryKey<AcctgTransEntry> primary = createPrimaryKey(acctgTransId, acctgTransEntrySeqId);

    public final com.querydsl.sql.ForeignKey<Uom> accttxentCurncy = createForeignKey(currencyUomId, "UOM_ID");

    public final com.querydsl.sql.ForeignKey<RoleType> accttxentRltyp = createForeignKey(roleTypeId, "ROLE_TYPE_ID");

    public final com.querydsl.sql.ForeignKey<EmplPositionType> ateEpt = createForeignKey(emplPositionTypeId, "EMPL_POSITION_TYPE_ID");

    public final com.querydsl.sql.ForeignKey<Party> accttxentParty = createForeignKey(partyId, "PARTY_ID");

    public final com.querydsl.sql.ForeignKey<StatusItem> accttxentRcsts = createForeignKey(reconcileStatusId, "STATUS_ID");

    public final com.querydsl.sql.ForeignKey<Uom> accttxentOcurncy = createForeignKey(origCurrencyUomId, "UOM_ID");

    public final com.querydsl.sql.ForeignKey<WorkEffortRevision> acctgWeRev = createForeignKey(workEffortRevisionId, "WORK_EFFORT_REVISION_ID");

    public final com.querydsl.sql.ForeignKey<AcctgTrans> accttxentActx = createForeignKey(acctgTransId, "ACCTG_TRANS_ID");

    public final com.querydsl.sql.ForeignKey<AcctgTransEntryType> accttxentAtet = createForeignKey(acctgTransEntryTypeId, "ACCTG_TRANS_ENTRY_TYPE_ID");

    public final com.querydsl.sql.ForeignKey<WorkEffortMeasure> acctgWeMeasure = createForeignKey(voucherRef, "WORK_EFFORT_MEASURE_ID");

    public QAcctgTransEntry(String variable) {
        super(AcctgTransEntry.class, forVariable(variable), "null", "ACCTG_TRANS_ENTRY");
        addMetadata();
    }

    public QAcctgTransEntry(String variable, String schema, String table) {
        super(AcctgTransEntry.class, forVariable(variable), schema, table);
        addMetadata();
    }

    public QAcctgTransEntry(String variable, String schema) {
        super(AcctgTransEntry.class, forVariable(variable), schema, "ACCTG_TRANS_ENTRY");
        addMetadata();
    }

    public QAcctgTransEntry(Path<? extends AcctgTransEntry> path) {
        super(path.getType(), path.getMetadata(), "null", "ACCTG_TRANS_ENTRY");
        addMetadata();
    }

    public QAcctgTransEntry(PathMetadata metadata) {
        super(AcctgTransEntry.class, metadata, "null", "ACCTG_TRANS_ENTRY");
        addMetadata();
    }

    public void addMetadata() {
        addMetadata(acctgTransEntrySeqId, ColumnMetadata.named("ACCTG_TRANS_ENTRY_SEQ_ID").withIndex(2).ofType(Types.VARCHAR).withSize(20).notNull());
        addMetadata(acctgTransEntryTypeId, ColumnMetadata.named("ACCTG_TRANS_ENTRY_TYPE_ID").withIndex(3).ofType(Types.VARCHAR).withSize(20));
        addMetadata(acctgTransId, ColumnMetadata.named("ACCTG_TRANS_ID").withIndex(1).ofType(Types.VARCHAR).withSize(20).notNull());
        addMetadata(amount, ColumnMetadata.named("AMOUNT").withIndex(15).ofType(Types.DECIMAL).withSize(18).withDigits(6));
        addMetadata(amountLocked, ColumnMetadata.named("AMOUNT_LOCKED").withIndex(31).ofType(Types.CHAR).withSize(1));
        addMetadata(checkAmount1, ColumnMetadata.named("CHECK_AMOUNT1").withIndex(48).ofType(Types.CHAR).withSize(1));
        addMetadata(checkAmount2, ColumnMetadata.named("CHECK_AMOUNT2").withIndex(49).ofType(Types.CHAR).withSize(1));
        addMetadata(checkAmount3, ColumnMetadata.named("CHECK_AMOUNT3").withIndex(50).ofType(Types.CHAR).withSize(1));
        addMetadata(createdByUserLogin, ColumnMetadata.named("CREATED_BY_USER_LOGIN").withIndex(37).ofType(Types.VARCHAR).withSize(250));
        addMetadata(createdStamp, ColumnMetadata.named("CREATED_STAMP").withIndex(28).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(createdTxStamp, ColumnMetadata.named("CREATED_TX_STAMP").withIndex(29).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(currencyUomId, ColumnMetadata.named("CURRENCY_UOM_ID").withIndex(16).ofType(Types.VARCHAR).withSize(20));
        addMetadata(debitCreditFlag, ColumnMetadata.named("DEBIT_CREDIT_FLAG").withIndex(19).ofType(Types.CHAR).withSize(1));
        addMetadata(description, ColumnMetadata.named("DESCRIPTION").withIndex(4).ofType(Types.VARCHAR).withSize(2000));
        addMetadata(descriptionLang, ColumnMetadata.named("DESCRIPTION_LANG").withIndex(35).ofType(Types.VARCHAR).withSize(2000));
        addMetadata(dueDate, ColumnMetadata.named("DUE_DATE").withIndex(20).ofType(Types.DATE).withSize(10));
        addMetadata(emplPositionTypeId, ColumnMetadata.named("EMPL_POSITION_TYPE_ID").withIndex(42).ofType(Types.VARCHAR).withSize(20));
        addMetadata(fromDateCompetence, ColumnMetadata.named("FROM_DATE_COMPETENCE").withIndex(39).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(glAccountFinId, ColumnMetadata.named("GL_ACCOUNT_FIN_ID").withIndex(41).ofType(Types.VARCHAR).withSize(20));
        addMetadata(glAccountId, ColumnMetadata.named("GL_ACCOUNT_ID").withIndex(13).ofType(Types.VARCHAR).withSize(20));
        addMetadata(glAccountTypeId, ColumnMetadata.named("GL_ACCOUNT_TYPE_ID").withIndex(12).ofType(Types.VARCHAR).withSize(20));
        addMetadata(glFiscalTypeId, ColumnMetadata.named("GL_FISCAL_TYPE_ID").withIndex(38).ofType(Types.VARCHAR).withSize(20));
        addMetadata(groupId, ColumnMetadata.named("GROUP_ID").withIndex(21).ofType(Types.VARCHAR).withSize(20));
        addMetadata(hasScoreAlert, ColumnMetadata.named("HAS_SCORE_ALERT").withIndex(43).ofType(Types.CHAR).withSize(1));
        addMetadata(inventoryItemId, ColumnMetadata.named("INVENTORY_ITEM_ID").withIndex(11).ofType(Types.VARCHAR).withSize(20));
        addMetadata(isSummary, ColumnMetadata.named("IS_SUMMARY").withIndex(25).ofType(Types.CHAR).withSize(1));
        addMetadata(lastModifiedByUserLogin, ColumnMetadata.named("LAST_MODIFIED_BY_USER_LOGIN").withIndex(36).ofType(Types.VARCHAR).withSize(250));
        addMetadata(lastUpdatedStamp, ColumnMetadata.named("LAST_UPDATED_STAMP").withIndex(26).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(lastUpdatedTxStamp, ColumnMetadata.named("LAST_UPDATED_TX_STAMP").withIndex(27).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(organizationPartyId, ColumnMetadata.named("ORGANIZATION_PARTY_ID").withIndex(14).ofType(Types.VARCHAR).withSize(20));
        addMetadata(origAmount, ColumnMetadata.named("ORIG_AMOUNT").withIndex(17).ofType(Types.DECIMAL).withSize(18).withDigits(6));
        addMetadata(origCurrencyUomId, ColumnMetadata.named("ORIG_CURRENCY_UOM_ID").withIndex(18).ofType(Types.VARCHAR).withSize(20));
        addMetadata(partyId, ColumnMetadata.named("PARTY_ID").withIndex(6).ofType(Types.VARCHAR).withSize(20));
        addMetadata(perfAmountActual, ColumnMetadata.named("PERF_AMOUNT_ACTUAL").withIndex(45).ofType(Types.DECIMAL).withSize(18).withDigits(6));
        addMetadata(perfAmountCalc, ColumnMetadata.named("PERF_AMOUNT_CALC").withIndex(30).ofType(Types.DECIMAL).withSize(18).withDigits(6));
        addMetadata(perfAmountMax, ColumnMetadata.named("PERF_AMOUNT_MAX").withIndex(47).ofType(Types.DECIMAL).withSize(18).withDigits(6));
        addMetadata(perfAmountMin, ColumnMetadata.named("PERF_AMOUNT_MIN").withIndex(46).ofType(Types.DECIMAL).withSize(18).withDigits(6));
        addMetadata(perfAmountTarget, ColumnMetadata.named("PERF_AMOUNT_TARGET").withIndex(44).ofType(Types.DECIMAL).withSize(18).withDigits(6));
        addMetadata(productId, ColumnMetadata.named("PRODUCT_ID").withIndex(9).ofType(Types.VARCHAR).withSize(20));
        addMetadata(reconcileStatusId, ColumnMetadata.named("RECONCILE_STATUS_ID").withIndex(23).ofType(Types.VARCHAR).withSize(20));
        addMetadata(roleTypeId, ColumnMetadata.named("ROLE_TYPE_ID").withIndex(7).ofType(Types.VARCHAR).withSize(20));
        addMetadata(settlementTermId, ColumnMetadata.named("SETTLEMENT_TERM_ID").withIndex(24).ofType(Types.VARCHAR).withSize(20));
        addMetadata(snapshotDate, ColumnMetadata.named("SNAPSHOT_DATE").withIndex(33).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(taxId, ColumnMetadata.named("TAX_ID").withIndex(22).ofType(Types.VARCHAR).withSize(20));
        addMetadata(textValue1, ColumnMetadata.named("TEXT_VALUE1").withIndex(51).ofType(Types.VARCHAR).withSize(4000));
        addMetadata(theirPartyId, ColumnMetadata.named("THEIR_PARTY_ID").withIndex(8).ofType(Types.VARCHAR).withSize(20));
        addMetadata(theirProductId, ColumnMetadata.named("THEIR_PRODUCT_ID").withIndex(10).ofType(Types.VARCHAR).withSize(20));
        addMetadata(toDateCompetence, ColumnMetadata.named("TO_DATE_COMPETENCE").withIndex(40).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(voucherRef, ColumnMetadata.named("VOUCHER_REF").withIndex(5).ofType(Types.VARCHAR).withSize(20));
        addMetadata(workEffortRevisionId, ColumnMetadata.named("WORK_EFFORT_REVISION_ID").withIndex(34).ofType(Types.VARCHAR).withSize(20));
        addMetadata(workEffortSnapshotId, ColumnMetadata.named("WORK_EFFORT_SNAPSHOT_ID").withIndex(32).ofType(Types.VARCHAR).withSize(20));
    }

}

