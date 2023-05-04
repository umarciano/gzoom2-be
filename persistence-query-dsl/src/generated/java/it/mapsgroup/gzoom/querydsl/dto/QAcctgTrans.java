package it.mapsgroup.gzoom.querydsl.dto;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;

import com.querydsl.sql.ColumnMetadata;
import java.sql.Types;




/**
 * QAcctgTrans is a Querydsl query type for AcctgTrans
 */
@Generated("com.querydsl.sql.codegen.MetaDataSerializer")
public class QAcctgTrans extends com.querydsl.sql.RelationalPathBase<AcctgTrans> {

    private static final long serialVersionUID = 1984014047;

    public static final QAcctgTrans acctgTrans = new QAcctgTrans("ACCTG_TRANS");

    public final StringPath acctgTransId = createString("acctgTransId");

    public final StringPath acctgTransTypeId = createString("acctgTransTypeId");

    public final StringPath createdByUserLogin = createString("createdByUserLogin");

    public final DateTimePath<java.time.LocalDateTime> createdStamp = createDateTime("createdStamp", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> createdTxStamp = createDateTime("createdTxStamp", java.time.LocalDateTime.class);

    public final StringPath description = createString("description");

    public final StringPath descriptionLang = createString("descriptionLang");

    public final StringPath finAccountTransId = createString("finAccountTransId");

    public final StringPath fixedAssetId = createString("fixedAssetId");

    public final StringPath glFiscalTypeId = createString("glFiscalTypeId");

    public final StringPath glJournalId = createString("glJournalId");

    public final StringPath groupStatusId = createString("groupStatusId");

    public final StringPath inventoryItemId = createString("inventoryItemId");

    public final StringPath invoiceId = createString("invoiceId");

    public final BooleanPath isPosted = createBoolean("isPosted");

    public final StringPath lastModifiedByUserLogin = createString("lastModifiedByUserLogin");

    public final DateTimePath<java.time.LocalDateTime> lastUpdatedStamp = createDateTime("lastUpdatedStamp", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> lastUpdatedTxStamp = createDateTime("lastUpdatedTxStamp", java.time.LocalDateTime.class);

    public final StringPath partyId = createString("partyId");

    public final StringPath paymentId = createString("paymentId");

    public final StringPath physicalInventoryId = createString("physicalInventoryId");

    public final DateTimePath<java.time.LocalDateTime> postedDate = createDateTime("postedDate", java.time.LocalDateTime.class);

    public final StringPath receiptId = createString("receiptId");

    public final StringPath roleTypeId = createString("roleTypeId");

    public final DateTimePath<java.time.LocalDateTime> scheduledPostingDate = createDateTime("scheduledPostingDate", java.time.LocalDateTime.class);

    public final StringPath shipmentId = createString("shipmentId");

    public final DateTimePath<java.time.LocalDateTime> snapshotDate = createDateTime("snapshotDate", java.time.LocalDateTime.class);

    public final StringPath theirAcctgTransId = createString("theirAcctgTransId");

    public final DateTimePath<java.time.LocalDateTime> transactionDate = createDateTime("transactionDate", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> voucherDate = createDateTime("voucherDate", java.time.LocalDateTime.class);

    public final StringPath voucherRef = createString("voucherRef");

    public final StringPath workEffortId = createString("workEffortId");

    public final StringPath workEffortRevisionId = createString("workEffortRevisionId");

    public final StringPath workEffortSnapshotId = createString("workEffortSnapshotId");

    public final com.querydsl.sql.PrimaryKey<AcctgTrans> primary = createPrimaryKey(acctgTransId);

    public final com.querydsl.sql.ForeignKey<WorkEffort> accttxWeff = createForeignKey(workEffortId, "WORK_EFFORT_ID");

    public final com.querydsl.sql.ForeignKey<StatusItem> accttxGrpstts = createForeignKey(groupStatusId, "STATUS_ID");

    public final com.querydsl.sql.ForeignKey<WorkEffortRevision> accttxWeRev = createForeignKey(workEffortRevisionId, "WORK_EFFORT_REVISION_ID");

    public final com.querydsl.sql.ForeignKey<RoleType> accttxRoletyp = createForeignKey(roleTypeId, "ROLE_TYPE_ID");

    public final com.querydsl.sql.ForeignKey<Party> accttxParty = createForeignKey(partyId, "PARTY_ID");

    public final com.querydsl.sql.ForeignKey<WorkEffortMeasure> accttxWemeasure = createForeignKey(voucherRef, "WORK_EFFORT_MEASURE_ID");

    public QAcctgTrans(String variable) {
        super(AcctgTrans.class, forVariable(variable), "null", "ACCTG_TRANS");
        addMetadata();
    }

    public QAcctgTrans(String variable, String schema, String table) {
        super(AcctgTrans.class, forVariable(variable), schema, table);
        addMetadata();
    }

    public QAcctgTrans(String variable, String schema) {
        super(AcctgTrans.class, forVariable(variable), schema, "ACCTG_TRANS");
        addMetadata();
    }

    public QAcctgTrans(Path<? extends AcctgTrans> path) {
        super(path.getType(), path.getMetadata(), "null", "ACCTG_TRANS");
        addMetadata();
    }

    public QAcctgTrans(PathMetadata metadata) {
        super(AcctgTrans.class, metadata, "null", "ACCTG_TRANS");
        addMetadata();
    }

    public void addMetadata() {
        addMetadata(acctgTransId, ColumnMetadata.named("ACCTG_TRANS_ID").withIndex(1).ofType(Types.VARCHAR).withSize(20).notNull());
        addMetadata(acctgTransTypeId, ColumnMetadata.named("ACCTG_TRANS_TYPE_ID").withIndex(2).ofType(Types.VARCHAR).withSize(20));
        addMetadata(createdByUserLogin, ColumnMetadata.named("CREATED_BY_USER_LOGIN").withIndex(25).ofType(Types.VARCHAR).withSize(250));
        addMetadata(createdStamp, ColumnMetadata.named("CREATED_STAMP").withIndex(29).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(createdTxStamp, ColumnMetadata.named("CREATED_TX_STAMP").withIndex(30).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(description, ColumnMetadata.named("DESCRIPTION").withIndex(3).ofType(Types.VARCHAR).withSize(2000));
        addMetadata(descriptionLang, ColumnMetadata.named("DESCRIPTION_LANG").withIndex(34).ofType(Types.VARCHAR).withSize(2000));
        addMetadata(finAccountTransId, ColumnMetadata.named("FIN_ACCOUNT_TRANS_ID").withIndex(20).ofType(Types.VARCHAR).withSize(20));
        addMetadata(fixedAssetId, ColumnMetadata.named("FIXED_ASSET_ID").withIndex(13).ofType(Types.VARCHAR).withSize(20));
        addMetadata(glFiscalTypeId, ColumnMetadata.named("GL_FISCAL_TYPE_ID").withIndex(9).ofType(Types.VARCHAR).withSize(20));
        addMetadata(glJournalId, ColumnMetadata.named("GL_JOURNAL_ID").withIndex(8).ofType(Types.VARCHAR).withSize(20));
        addMetadata(groupStatusId, ColumnMetadata.named("GROUP_STATUS_ID").withIndex(12).ofType(Types.VARCHAR).withSize(20));
        addMetadata(inventoryItemId, ColumnMetadata.named("INVENTORY_ITEM_ID").withIndex(14).ofType(Types.VARCHAR).withSize(20));
        addMetadata(invoiceId, ColumnMetadata.named("INVOICE_ID").withIndex(18).ofType(Types.VARCHAR).withSize(20));
        addMetadata(isPosted, ColumnMetadata.named("IS_POSTED").withIndex(5).ofType(Types.CHAR).withSize(1));
        addMetadata(lastModifiedByUserLogin, ColumnMetadata.named("LAST_MODIFIED_BY_USER_LOGIN").withIndex(26).ofType(Types.VARCHAR).withSize(250));
        addMetadata(lastUpdatedStamp, ColumnMetadata.named("LAST_UPDATED_STAMP").withIndex(27).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(lastUpdatedTxStamp, ColumnMetadata.named("LAST_UPDATED_TX_STAMP").withIndex(28).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(partyId, ColumnMetadata.named("PARTY_ID").withIndex(16).ofType(Types.VARCHAR).withSize(20));
        addMetadata(paymentId, ColumnMetadata.named("PAYMENT_ID").withIndex(19).ofType(Types.VARCHAR).withSize(20));
        addMetadata(physicalInventoryId, ColumnMetadata.named("PHYSICAL_INVENTORY_ID").withIndex(15).ofType(Types.VARCHAR).withSize(20));
        addMetadata(postedDate, ColumnMetadata.named("POSTED_DATE").withIndex(6).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(receiptId, ColumnMetadata.named("RECEIPT_ID").withIndex(22).ofType(Types.VARCHAR).withSize(20));
        addMetadata(roleTypeId, ColumnMetadata.named("ROLE_TYPE_ID").withIndex(17).ofType(Types.VARCHAR).withSize(20));
        addMetadata(scheduledPostingDate, ColumnMetadata.named("SCHEDULED_POSTING_DATE").withIndex(7).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(shipmentId, ColumnMetadata.named("SHIPMENT_ID").withIndex(21).ofType(Types.VARCHAR).withSize(20));
        addMetadata(snapshotDate, ColumnMetadata.named("SNAPSHOT_DATE").withIndex(31).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(theirAcctgTransId, ColumnMetadata.named("THEIR_ACCTG_TRANS_ID").withIndex(24).ofType(Types.VARCHAR).withSize(60));
        addMetadata(transactionDate, ColumnMetadata.named("TRANSACTION_DATE").withIndex(4).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(voucherDate, ColumnMetadata.named("VOUCHER_DATE").withIndex(11).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(voucherRef, ColumnMetadata.named("VOUCHER_REF").withIndex(10).ofType(Types.VARCHAR).withSize(20));
        addMetadata(workEffortId, ColumnMetadata.named("WORK_EFFORT_ID").withIndex(23).ofType(Types.VARCHAR).withSize(20));
        addMetadata(workEffortRevisionId, ColumnMetadata.named("WORK_EFFORT_REVISION_ID").withIndex(33).ofType(Types.VARCHAR).withSize(20));
        addMetadata(workEffortSnapshotId, ColumnMetadata.named("WORK_EFFORT_SNAPSHOT_ID").withIndex(32).ofType(Types.VARCHAR).withSize(20));
    }

}

