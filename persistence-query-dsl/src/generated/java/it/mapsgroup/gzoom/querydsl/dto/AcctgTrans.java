package it.mapsgroup.gzoom.querydsl.dto;

import javax.annotation.Generated;
import com.querydsl.sql.Column;
import it.mapsgroup.gzoom.querydsl.AbstractIdentity;

/**
 * AcctgTrans is a Querydsl bean type
 */
@Generated("com.querydsl.codegen.BeanSerializer")
public class AcctgTrans implements AbstractIdentity {

    @Column("ACCTG_TRANS_ID")
    private String acctgTransId;

    @Column("ACCTG_TRANS_TYPE_ID")
    private String acctgTransTypeId;

    @Column("CREATED_BY_USER_LOGIN")
    private String createdByUserLogin;

    @Column("CREATED_STAMP")
    private java.time.LocalDateTime createdStamp;

    @Column("CREATED_TX_STAMP")
    private java.time.LocalDateTime createdTxStamp;

    @Column("DESCRIPTION")
    private String description;

    @Column("DESCRIPTION_LANG")
    private String descriptionLang;

    @Column("FIN_ACCOUNT_TRANS_ID")
    private String finAccountTransId;

    @Column("FIXED_ASSET_ID")
    private String fixedAssetId;

    @Column("GL_FISCAL_TYPE_ID")
    private String glFiscalTypeId;

    @Column("GL_JOURNAL_ID")
    private String glJournalId;

    @Column("GROUP_STATUS_ID")
    private String groupStatusId;

    @Column("INVENTORY_ITEM_ID")
    private String inventoryItemId;

    @Column("INVOICE_ID")
    private String invoiceId;

    @Column("IS_POSTED")
    private Boolean isPosted;

    @Column("LAST_MODIFIED_BY_USER_LOGIN")
    private String lastModifiedByUserLogin;

    @Column("LAST_UPDATED_STAMP")
    private java.time.LocalDateTime lastUpdatedStamp;

    @Column("LAST_UPDATED_TX_STAMP")
    private java.time.LocalDateTime lastUpdatedTxStamp;

    @Column("PARTY_ID")
    private String partyId;

    @Column("PAYMENT_ID")
    private String paymentId;

    @Column("PHYSICAL_INVENTORY_ID")
    private String physicalInventoryId;

    @Column("POSTED_DATE")
    private java.time.LocalDateTime postedDate;

    @Column("RECEIPT_ID")
    private String receiptId;

    @Column("ROLE_TYPE_ID")
    private String roleTypeId;

    @Column("SCHEDULED_POSTING_DATE")
    private java.time.LocalDateTime scheduledPostingDate;

    @Column("SHIPMENT_ID")
    private String shipmentId;

    @Column("SNAPSHOT_DATE")
    private java.time.LocalDateTime snapshotDate;

    @Column("THEIR_ACCTG_TRANS_ID")
    private String theirAcctgTransId;

    @Column("TRANSACTION_DATE")
    private java.time.LocalDateTime transactionDate;

    @Column("VOUCHER_DATE")
    private java.time.LocalDateTime voucherDate;

    @Column("VOUCHER_REF")
    private String voucherRef;

    @Column("WORK_EFFORT_ID")
    private String workEffortId;

    @Column("WORK_EFFORT_REVISION_ID")
    private String workEffortRevisionId;

    @Column("WORK_EFFORT_SNAPSHOT_ID")
    private String workEffortSnapshotId;

    public String getAcctgTransId() {
        return acctgTransId;
    }

    public void setAcctgTransId(String acctgTransId) {
        this.acctgTransId = acctgTransId;
    }

    public String getAcctgTransTypeId() {
        return acctgTransTypeId;
    }

    public void setAcctgTransTypeId(String acctgTransTypeId) {
        this.acctgTransTypeId = acctgTransTypeId;
    }

    public String getCreatedByUserLogin() {
        return createdByUserLogin;
    }

    public void setCreatedByUserLogin(String createdByUserLogin) {
        this.createdByUserLogin = createdByUserLogin;
    }

    public java.time.LocalDateTime getCreatedStamp() {
        return createdStamp;
    }

    public void setCreatedStamp(java.time.LocalDateTime createdStamp) {
        this.createdStamp = createdStamp;
    }

    public java.time.LocalDateTime getCreatedTxStamp() {
        return createdTxStamp;
    }

    public void setCreatedTxStamp(java.time.LocalDateTime createdTxStamp) {
        this.createdTxStamp = createdTxStamp;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescriptionLang() {
        return descriptionLang;
    }

    public void setDescriptionLang(String descriptionLang) {
        this.descriptionLang = descriptionLang;
    }

    public String getFinAccountTransId() {
        return finAccountTransId;
    }

    public void setFinAccountTransId(String finAccountTransId) {
        this.finAccountTransId = finAccountTransId;
    }

    public String getFixedAssetId() {
        return fixedAssetId;
    }

    public void setFixedAssetId(String fixedAssetId) {
        this.fixedAssetId = fixedAssetId;
    }

    public String getGlFiscalTypeId() {
        return glFiscalTypeId;
    }

    public void setGlFiscalTypeId(String glFiscalTypeId) {
        this.glFiscalTypeId = glFiscalTypeId;
    }

    public String getGlJournalId() {
        return glJournalId;
    }

    public void setGlJournalId(String glJournalId) {
        this.glJournalId = glJournalId;
    }

    public String getGroupStatusId() {
        return groupStatusId;
    }

    public void setGroupStatusId(String groupStatusId) {
        this.groupStatusId = groupStatusId;
    }

    public String getInventoryItemId() {
        return inventoryItemId;
    }

    public void setInventoryItemId(String inventoryItemId) {
        this.inventoryItemId = inventoryItemId;
    }

    public String getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(String invoiceId) {
        this.invoiceId = invoiceId;
    }

    public Boolean getIsPosted() {
        return isPosted;
    }

    public void setIsPosted(Boolean isPosted) {
        this.isPosted = isPosted;
    }

    public String getLastModifiedByUserLogin() {
        return lastModifiedByUserLogin;
    }

    public void setLastModifiedByUserLogin(String lastModifiedByUserLogin) {
        this.lastModifiedByUserLogin = lastModifiedByUserLogin;
    }

    public java.time.LocalDateTime getLastUpdatedStamp() {
        return lastUpdatedStamp;
    }

    public void setLastUpdatedStamp(java.time.LocalDateTime lastUpdatedStamp) {
        this.lastUpdatedStamp = lastUpdatedStamp;
    }

    public java.time.LocalDateTime getLastUpdatedTxStamp() {
        return lastUpdatedTxStamp;
    }

    public void setLastUpdatedTxStamp(java.time.LocalDateTime lastUpdatedTxStamp) {
        this.lastUpdatedTxStamp = lastUpdatedTxStamp;
    }

    public String getPartyId() {
        return partyId;
    }

    public void setPartyId(String partyId) {
        this.partyId = partyId;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getPhysicalInventoryId() {
        return physicalInventoryId;
    }

    public void setPhysicalInventoryId(String physicalInventoryId) {
        this.physicalInventoryId = physicalInventoryId;
    }

    public java.time.LocalDateTime getPostedDate() {
        return postedDate;
    }

    public void setPostedDate(java.time.LocalDateTime postedDate) {
        this.postedDate = postedDate;
    }

    public String getReceiptId() {
        return receiptId;
    }

    public void setReceiptId(String receiptId) {
        this.receiptId = receiptId;
    }

    public String getRoleTypeId() {
        return roleTypeId;
    }

    public void setRoleTypeId(String roleTypeId) {
        this.roleTypeId = roleTypeId;
    }

    public java.time.LocalDateTime getScheduledPostingDate() {
        return scheduledPostingDate;
    }

    public void setScheduledPostingDate(java.time.LocalDateTime scheduledPostingDate) {
        this.scheduledPostingDate = scheduledPostingDate;
    }

    public String getShipmentId() {
        return shipmentId;
    }

    public void setShipmentId(String shipmentId) {
        this.shipmentId = shipmentId;
    }

    public java.time.LocalDateTime getSnapshotDate() {
        return snapshotDate;
    }

    public void setSnapshotDate(java.time.LocalDateTime snapshotDate) {
        this.snapshotDate = snapshotDate;
    }

    public String getTheirAcctgTransId() {
        return theirAcctgTransId;
    }

    public void setTheirAcctgTransId(String theirAcctgTransId) {
        this.theirAcctgTransId = theirAcctgTransId;
    }

    public java.time.LocalDateTime getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(java.time.LocalDateTime transactionDate) {
        this.transactionDate = transactionDate;
    }

    public java.time.LocalDateTime getVoucherDate() {
        return voucherDate;
    }

    public void setVoucherDate(java.time.LocalDateTime voucherDate) {
        this.voucherDate = voucherDate;
    }

    public String getVoucherRef() {
        return voucherRef;
    }

    public void setVoucherRef(String voucherRef) {
        this.voucherRef = voucherRef;
    }

    public String getWorkEffortId() {
        return workEffortId;
    }

    public void setWorkEffortId(String workEffortId) {
        this.workEffortId = workEffortId;
    }

    public String getWorkEffortRevisionId() {
        return workEffortRevisionId;
    }

    public void setWorkEffortRevisionId(String workEffortRevisionId) {
        this.workEffortRevisionId = workEffortRevisionId;
    }

    public String getWorkEffortSnapshotId() {
        return workEffortSnapshotId;
    }

    public void setWorkEffortSnapshotId(String workEffortSnapshotId) {
        this.workEffortSnapshotId = workEffortSnapshotId;
    }

}

