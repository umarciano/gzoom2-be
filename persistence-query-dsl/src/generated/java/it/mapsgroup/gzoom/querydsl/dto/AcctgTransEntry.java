package it.mapsgroup.gzoom.querydsl.dto;

import javax.annotation.Generated;
import com.querydsl.sql.Column;
import it.mapsgroup.gzoom.querydsl.AbstractIdentity;

/**
 * AcctgTransEntry is a Querydsl bean type
 */
@Generated("com.querydsl.codegen.BeanSerializer")
public class AcctgTransEntry implements AbstractIdentity {

    @Column("ACCTG_TRANS_ENTRY_SEQ_ID")
    private String acctgTransEntrySeqId;

    @Column("ACCTG_TRANS_ENTRY_TYPE_ID")
    private String acctgTransEntryTypeId;

    @Column("ACCTG_TRANS_ID")
    private String acctgTransId;

    @Column("AMOUNT")
    private java.math.BigDecimal amount;

    @Column("AMOUNT_LOCKED")
    private Boolean amountLocked;

    @Column("CHECK_AMOUNT1")
    private Boolean checkAmount1;

    @Column("CHECK_AMOUNT2")
    private Boolean checkAmount2;

    @Column("CHECK_AMOUNT3")
    private Boolean checkAmount3;

    @Column("CREATED_BY_USER_LOGIN")
    private String createdByUserLogin;

    @Column("CREATED_STAMP")
    private java.time.LocalDateTime createdStamp;

    @Column("CREATED_TX_STAMP")
    private java.time.LocalDateTime createdTxStamp;

    @Column("CURRENCY_UOM_ID")
    private String currencyUomId;

    @Column("DEBIT_CREDIT_FLAG")
    private Boolean debitCreditFlag;

    @Column("DESCRIPTION")
    private String description;

    @Column("DESCRIPTION_LANG")
    private String descriptionLang;

    @Column("DUE_DATE")
    private java.time.LocalDate dueDate;

    @Column("EMPL_POSITION_TYPE_ID")
    private String emplPositionTypeId;

    @Column("FROM_DATE_COMPETENCE")
    private java.time.LocalDateTime fromDateCompetence;

    @Column("GL_ACCOUNT_FIN_ID")
    private String glAccountFinId;

    @Column("GL_ACCOUNT_ID")
    private String glAccountId;

    @Column("GL_ACCOUNT_TYPE_ID")
    private String glAccountTypeId;

    @Column("GL_FISCAL_TYPE_ID")
    private String glFiscalTypeId;

    @Column("GROUP_ID")
    private String groupId;

    @Column("HAS_SCORE_ALERT")
    private Boolean hasScoreAlert;

    @Column("INVENTORY_ITEM_ID")
    private String inventoryItemId;

    @Column("IS_SUMMARY")
    private Boolean isSummary;

    @Column("LAST_MODIFIED_BY_USER_LOGIN")
    private String lastModifiedByUserLogin;

    @Column("LAST_UPDATED_STAMP")
    private java.time.LocalDateTime lastUpdatedStamp;

    @Column("LAST_UPDATED_TX_STAMP")
    private java.time.LocalDateTime lastUpdatedTxStamp;

    @Column("ORGANIZATION_PARTY_ID")
    private String organizationPartyId;

    @Column("ORIG_AMOUNT")
    private java.math.BigDecimal origAmount;

    @Column("ORIG_CURRENCY_UOM_ID")
    private String origCurrencyUomId;

    @Column("PARTY_ID")
    private String partyId;

    @Column("PERF_AMOUNT_ACTUAL")
    private java.math.BigDecimal perfAmountActual;

    @Column("PERF_AMOUNT_CALC")
    private java.math.BigDecimal perfAmountCalc;

    @Column("PERF_AMOUNT_MAX")
    private java.math.BigDecimal perfAmountMax;

    @Column("PERF_AMOUNT_MIN")
    private java.math.BigDecimal perfAmountMin;

    @Column("PERF_AMOUNT_TARGET")
    private java.math.BigDecimal perfAmountTarget;

    @Column("PRODUCT_ID")
    private String productId;

    @Column("RECONCILE_STATUS_ID")
    private String reconcileStatusId;

    @Column("ROLE_TYPE_ID")
    private String roleTypeId;

    @Column("SETTLEMENT_TERM_ID")
    private String settlementTermId;

    @Column("SNAPSHOT_DATE")
    private java.time.LocalDateTime snapshotDate;

    @Column("TAX_ID")
    private String taxId;

    @Column("TEXT_VALUE1")
    private String textValue1;

    @Column("THEIR_PARTY_ID")
    private String theirPartyId;

    @Column("THEIR_PRODUCT_ID")
    private String theirProductId;

    @Column("TO_DATE_COMPETENCE")
    private java.time.LocalDateTime toDateCompetence;

    @Column("VOUCHER_REF")
    private String voucherRef;

    @Column("WORK_EFFORT_REVISION_ID")
    private String workEffortRevisionId;

    @Column("WORK_EFFORT_SNAPSHOT_ID")
    private String workEffortSnapshotId;

    public String getAcctgTransEntrySeqId() {
        return acctgTransEntrySeqId;
    }

    public void setAcctgTransEntrySeqId(String acctgTransEntrySeqId) {
        this.acctgTransEntrySeqId = acctgTransEntrySeqId;
    }

    public String getAcctgTransEntryTypeId() {
        return acctgTransEntryTypeId;
    }

    public void setAcctgTransEntryTypeId(String acctgTransEntryTypeId) {
        this.acctgTransEntryTypeId = acctgTransEntryTypeId;
    }

    public String getAcctgTransId() {
        return acctgTransId;
    }

    public void setAcctgTransId(String acctgTransId) {
        this.acctgTransId = acctgTransId;
    }

    public java.math.BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(java.math.BigDecimal amount) {
        this.amount = amount;
    }

    public Boolean getAmountLocked() {
        return amountLocked;
    }

    public void setAmountLocked(Boolean amountLocked) {
        this.amountLocked = amountLocked;
    }

    public Boolean getCheckAmount1() {
        return checkAmount1;
    }

    public void setCheckAmount1(Boolean checkAmount1) {
        this.checkAmount1 = checkAmount1;
    }

    public Boolean getCheckAmount2() {
        return checkAmount2;
    }

    public void setCheckAmount2(Boolean checkAmount2) {
        this.checkAmount2 = checkAmount2;
    }

    public Boolean getCheckAmount3() {
        return checkAmount3;
    }

    public void setCheckAmount3(Boolean checkAmount3) {
        this.checkAmount3 = checkAmount3;
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

    public String getCurrencyUomId() {
        return currencyUomId;
    }

    public void setCurrencyUomId(String currencyUomId) {
        this.currencyUomId = currencyUomId;
    }

    public Boolean getDebitCreditFlag() {
        return debitCreditFlag;
    }

    public void setDebitCreditFlag(Boolean debitCreditFlag) {
        this.debitCreditFlag = debitCreditFlag;
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

    public java.time.LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(java.time.LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public String getEmplPositionTypeId() {
        return emplPositionTypeId;
    }

    public void setEmplPositionTypeId(String emplPositionTypeId) {
        this.emplPositionTypeId = emplPositionTypeId;
    }

    public java.time.LocalDateTime getFromDateCompetence() {
        return fromDateCompetence;
    }

    public void setFromDateCompetence(java.time.LocalDateTime fromDateCompetence) {
        this.fromDateCompetence = fromDateCompetence;
    }

    public String getGlAccountFinId() {
        return glAccountFinId;
    }

    public void setGlAccountFinId(String glAccountFinId) {
        this.glAccountFinId = glAccountFinId;
    }

    public String getGlAccountId() {
        return glAccountId;
    }

    public void setGlAccountId(String glAccountId) {
        this.glAccountId = glAccountId;
    }

    public String getGlAccountTypeId() {
        return glAccountTypeId;
    }

    public void setGlAccountTypeId(String glAccountTypeId) {
        this.glAccountTypeId = glAccountTypeId;
    }

    public String getGlFiscalTypeId() {
        return glFiscalTypeId;
    }

    public void setGlFiscalTypeId(String glFiscalTypeId) {
        this.glFiscalTypeId = glFiscalTypeId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public Boolean getHasScoreAlert() {
        return hasScoreAlert;
    }

    public void setHasScoreAlert(Boolean hasScoreAlert) {
        this.hasScoreAlert = hasScoreAlert;
    }

    public String getInventoryItemId() {
        return inventoryItemId;
    }

    public void setInventoryItemId(String inventoryItemId) {
        this.inventoryItemId = inventoryItemId;
    }

    public Boolean getIsSummary() {
        return isSummary;
    }

    public void setIsSummary(Boolean isSummary) {
        this.isSummary = isSummary;
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

    public String getOrganizationPartyId() {
        return organizationPartyId;
    }

    public void setOrganizationPartyId(String organizationPartyId) {
        this.organizationPartyId = organizationPartyId;
    }

    public java.math.BigDecimal getOrigAmount() {
        return origAmount;
    }

    public void setOrigAmount(java.math.BigDecimal origAmount) {
        this.origAmount = origAmount;
    }

    public String getOrigCurrencyUomId() {
        return origCurrencyUomId;
    }

    public void setOrigCurrencyUomId(String origCurrencyUomId) {
        this.origCurrencyUomId = origCurrencyUomId;
    }

    public String getPartyId() {
        return partyId;
    }

    public void setPartyId(String partyId) {
        this.partyId = partyId;
    }

    public java.math.BigDecimal getPerfAmountActual() {
        return perfAmountActual;
    }

    public void setPerfAmountActual(java.math.BigDecimal perfAmountActual) {
        this.perfAmountActual = perfAmountActual;
    }

    public java.math.BigDecimal getPerfAmountCalc() {
        return perfAmountCalc;
    }

    public void setPerfAmountCalc(java.math.BigDecimal perfAmountCalc) {
        this.perfAmountCalc = perfAmountCalc;
    }

    public java.math.BigDecimal getPerfAmountMax() {
        return perfAmountMax;
    }

    public void setPerfAmountMax(java.math.BigDecimal perfAmountMax) {
        this.perfAmountMax = perfAmountMax;
    }

    public java.math.BigDecimal getPerfAmountMin() {
        return perfAmountMin;
    }

    public void setPerfAmountMin(java.math.BigDecimal perfAmountMin) {
        this.perfAmountMin = perfAmountMin;
    }

    public java.math.BigDecimal getPerfAmountTarget() {
        return perfAmountTarget;
    }

    public void setPerfAmountTarget(java.math.BigDecimal perfAmountTarget) {
        this.perfAmountTarget = perfAmountTarget;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getReconcileStatusId() {
        return reconcileStatusId;
    }

    public void setReconcileStatusId(String reconcileStatusId) {
        this.reconcileStatusId = reconcileStatusId;
    }

    public String getRoleTypeId() {
        return roleTypeId;
    }

    public void setRoleTypeId(String roleTypeId) {
        this.roleTypeId = roleTypeId;
    }

    public String getSettlementTermId() {
        return settlementTermId;
    }

    public void setSettlementTermId(String settlementTermId) {
        this.settlementTermId = settlementTermId;
    }

    public java.time.LocalDateTime getSnapshotDate() {
        return snapshotDate;
    }

    public void setSnapshotDate(java.time.LocalDateTime snapshotDate) {
        this.snapshotDate = snapshotDate;
    }

    public String getTaxId() {
        return taxId;
    }

    public void setTaxId(String taxId) {
        this.taxId = taxId;
    }

    public String getTextValue1() {
        return textValue1;
    }

    public void setTextValue1(String textValue1) {
        this.textValue1 = textValue1;
    }

    public String getTheirPartyId() {
        return theirPartyId;
    }

    public void setTheirPartyId(String theirPartyId) {
        this.theirPartyId = theirPartyId;
    }

    public String getTheirProductId() {
        return theirProductId;
    }

    public void setTheirProductId(String theirProductId) {
        this.theirProductId = theirProductId;
    }

    public java.time.LocalDateTime getToDateCompetence() {
        return toDateCompetence;
    }

    public void setToDateCompetence(java.time.LocalDateTime toDateCompetence) {
        this.toDateCompetence = toDateCompetence;
    }

    public String getVoucherRef() {
        return voucherRef;
    }

    public void setVoucherRef(String voucherRef) {
        this.voucherRef = voucherRef;
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

