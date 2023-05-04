package it.mapsgroup.gzoom.querydsl.dto;

import javax.annotation.Generated;
import com.querydsl.sql.Column;

/**
 * AcctgTransInterface is a Querydsl bean type
 */
@Generated("com.querydsl.codegen.BeanSerializer")
public class AcctgTransInterface {

    @Column("AMOUNT")
    private java.math.BigDecimal amount;

    @Column("AMOUNT_CODE")
    private String amountCode;

    @Column("COMMENTS")
    private String comments;

    @Column("COMMENTS_LANG")
    private String commentsLang;

    @Column("CUSTOM_DATE01")
    private java.time.LocalDateTime customDate01;

    @Column("CUSTOM_DATE02")
    private java.time.LocalDateTime customDate02;

    @Column("CUSTOM_DATE03")
    private java.time.LocalDateTime customDate03;

    @Column("CUSTOM_DATE04")
    private java.time.LocalDateTime customDate04;

    @Column("CUSTOM_DATE05")
    private java.time.LocalDateTime customDate05;

    @Column("CUSTOM_TEXT01")
    private String customText01;

    @Column("CUSTOM_TEXT02")
    private String customText02;

    @Column("CUSTOM_TEXT03")
    private String customText03;

    @Column("CUSTOM_TEXT04")
    private String customText04;

    @Column("CUSTOM_TEXT05")
    private String customText05;

    @Column("CUSTOM_VALUE01")
    private java.math.BigDecimal customValue01;

    @Column("CUSTOM_VALUE02")
    private java.math.BigDecimal customValue02;

    @Column("CUSTOM_VALUE03")
    private java.math.BigDecimal customValue03;

    @Column("CUSTOM_VALUE04")
    private java.math.BigDecimal customValue04;

    @Column("CUSTOM_VALUE05")
    private java.math.BigDecimal customValue05;

    @Column("DATA_SOURCE")
    private String dataSource;

    @Column("FROM_DATE_COMPETENCE")
    private java.time.LocalDateTime fromDateCompetence;

    @Column("GL_ACCOUNT_CODE")
    private String glAccountCode;

    @Column("GL_ACCOUNT_TYPE_ENUM_ID")
    private String glAccountTypeEnumId;

    @Column("GL_FISCAL_TYPE_ID")
    private String glFiscalTypeId;

    @Column("ID")
    private String id;

    @Column("NOTE")
    private String note;

    @Column("NOTE_LANG")
    private String noteLang;

    @Column("PARTY_CODE")
    private String partyCode;

    @Column("PRODUCT_CODE")
    private String productCode;

    @Column("REF_DATE")
    private java.time.LocalDateTime refDate;

    @Column("ROLE_TYPE_ID")
    private String roleTypeId;

    @Column("SEQ")
    private java.math.BigInteger seq;

    @Column("STATO")
    private String stato;

    @Column("TO_DATE_COMPETENCE")
    private java.time.LocalDateTime toDateCompetence;

    @Column("UOM_DESCR")
    private String uomDescr;

    @Column("UORG_CODE")
    private String uorgCode;

    @Column("UORG_ROLE_TYPE_ID")
    private String uorgRoleTypeId;

    @Column("VOUCHER_REF")
    private String voucherRef;

    @Column("WORK_EFFORT_CODE")
    private String workEffortCode;

    public java.math.BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(java.math.BigDecimal amount) {
        this.amount = amount;
    }

    public String getAmountCode() {
        return amountCode;
    }

    public void setAmountCode(String amountCode) {
        this.amountCode = amountCode;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getCommentsLang() {
        return commentsLang;
    }

    public void setCommentsLang(String commentsLang) {
        this.commentsLang = commentsLang;
    }

    public java.time.LocalDateTime getCustomDate01() {
        return customDate01;
    }

    public void setCustomDate01(java.time.LocalDateTime customDate01) {
        this.customDate01 = customDate01;
    }

    public java.time.LocalDateTime getCustomDate02() {
        return customDate02;
    }

    public void setCustomDate02(java.time.LocalDateTime customDate02) {
        this.customDate02 = customDate02;
    }

    public java.time.LocalDateTime getCustomDate03() {
        return customDate03;
    }

    public void setCustomDate03(java.time.LocalDateTime customDate03) {
        this.customDate03 = customDate03;
    }

    public java.time.LocalDateTime getCustomDate04() {
        return customDate04;
    }

    public void setCustomDate04(java.time.LocalDateTime customDate04) {
        this.customDate04 = customDate04;
    }

    public java.time.LocalDateTime getCustomDate05() {
        return customDate05;
    }

    public void setCustomDate05(java.time.LocalDateTime customDate05) {
        this.customDate05 = customDate05;
    }

    public String getCustomText01() {
        return customText01;
    }

    public void setCustomText01(String customText01) {
        this.customText01 = customText01;
    }

    public String getCustomText02() {
        return customText02;
    }

    public void setCustomText02(String customText02) {
        this.customText02 = customText02;
    }

    public String getCustomText03() {
        return customText03;
    }

    public void setCustomText03(String customText03) {
        this.customText03 = customText03;
    }

    public String getCustomText04() {
        return customText04;
    }

    public void setCustomText04(String customText04) {
        this.customText04 = customText04;
    }

    public String getCustomText05() {
        return customText05;
    }

    public void setCustomText05(String customText05) {
        this.customText05 = customText05;
    }

    public java.math.BigDecimal getCustomValue01() {
        return customValue01;
    }

    public void setCustomValue01(java.math.BigDecimal customValue01) {
        this.customValue01 = customValue01;
    }

    public java.math.BigDecimal getCustomValue02() {
        return customValue02;
    }

    public void setCustomValue02(java.math.BigDecimal customValue02) {
        this.customValue02 = customValue02;
    }

    public java.math.BigDecimal getCustomValue03() {
        return customValue03;
    }

    public void setCustomValue03(java.math.BigDecimal customValue03) {
        this.customValue03 = customValue03;
    }

    public java.math.BigDecimal getCustomValue04() {
        return customValue04;
    }

    public void setCustomValue04(java.math.BigDecimal customValue04) {
        this.customValue04 = customValue04;
    }

    public java.math.BigDecimal getCustomValue05() {
        return customValue05;
    }

    public void setCustomValue05(java.math.BigDecimal customValue05) {
        this.customValue05 = customValue05;
    }

    public String getDataSource() {
        return dataSource;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }

    public java.time.LocalDateTime getFromDateCompetence() {
        return fromDateCompetence;
    }

    public void setFromDateCompetence(java.time.LocalDateTime fromDateCompetence) {
        this.fromDateCompetence = fromDateCompetence;
    }

    public String getGlAccountCode() {
        return glAccountCode;
    }

    public void setGlAccountCode(String glAccountCode) {
        this.glAccountCode = glAccountCode;
    }

    public String getGlAccountTypeEnumId() {
        return glAccountTypeEnumId;
    }

    public void setGlAccountTypeEnumId(String glAccountTypeEnumId) {
        this.glAccountTypeEnumId = glAccountTypeEnumId;
    }

    public String getGlFiscalTypeId() {
        return glFiscalTypeId;
    }

    public void setGlFiscalTypeId(String glFiscalTypeId) {
        this.glFiscalTypeId = glFiscalTypeId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getNoteLang() {
        return noteLang;
    }

    public void setNoteLang(String noteLang) {
        this.noteLang = noteLang;
    }

    public String getPartyCode() {
        return partyCode;
    }

    public void setPartyCode(String partyCode) {
        this.partyCode = partyCode;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public java.time.LocalDateTime getRefDate() {
        return refDate;
    }

    public void setRefDate(java.time.LocalDateTime refDate) {
        this.refDate = refDate;
    }

    public String getRoleTypeId() {
        return roleTypeId;
    }

    public void setRoleTypeId(String roleTypeId) {
        this.roleTypeId = roleTypeId;
    }

    public java.math.BigInteger getSeq() {
        return seq;
    }

    public void setSeq(java.math.BigInteger seq) {
        this.seq = seq;
    }

    public String getStato() {
        return stato;
    }

    public void setStato(String stato) {
        this.stato = stato;
    }

    public java.time.LocalDateTime getToDateCompetence() {
        return toDateCompetence;
    }

    public void setToDateCompetence(java.time.LocalDateTime toDateCompetence) {
        this.toDateCompetence = toDateCompetence;
    }

    public String getUomDescr() {
        return uomDescr;
    }

    public void setUomDescr(String uomDescr) {
        this.uomDescr = uomDescr;
    }

    public String getUorgCode() {
        return uorgCode;
    }

    public void setUorgCode(String uorgCode) {
        this.uorgCode = uorgCode;
    }

    public String getUorgRoleTypeId() {
        return uorgRoleTypeId;
    }

    public void setUorgRoleTypeId(String uorgRoleTypeId) {
        this.uorgRoleTypeId = uorgRoleTypeId;
    }

    public String getVoucherRef() {
        return voucherRef;
    }

    public void setVoucherRef(String voucherRef) {
        this.voucherRef = voucherRef;
    }

    public String getWorkEffortCode() {
        return workEffortCode;
    }

    public void setWorkEffortCode(String workEffortCode) {
        this.workEffortCode = workEffortCode;
    }

}

