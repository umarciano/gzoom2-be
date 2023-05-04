package it.mapsgroup.gzoom.querydsl.dto;

import javax.annotation.Generated;
import com.querydsl.sql.Column;
import it.mapsgroup.gzoom.querydsl.AbstractIdentity;

/**
 * GlFiscalType is a Querydsl bean type
 */
@Generated("com.querydsl.codegen.BeanSerializer")
public class GlFiscalType implements AbstractIdentity {

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

    @Column("GL_FISCAL_TYPE_ENUM_ID")
    private String glFiscalTypeEnumId;

    @Column("GL_FISCAL_TYPE_ID")
    private String glFiscalTypeId;

    @Column("IS_ACCOUNT_USED")
    private String isAccountUsed;

    @Column("IS_FINANCIAL_USED")
    private String isFinancialUsed;

    @Column("IS_INDICATOR_USED")
    private String isIndicatorUsed;

    @Column("LAST_MODIFIED_BY_USER_LOGIN")
    private String lastModifiedByUserLogin;

    @Column("LAST_UPDATED_STAMP")
    private java.time.LocalDateTime lastUpdatedStamp;

    @Column("LAST_UPDATED_TX_STAMP")
    private java.time.LocalDateTime lastUpdatedTxStamp;

    @Column("PERIODICAL_ABSOLUTE_ENUM_ID")
    private String periodicalAbsoluteEnumId;

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

    public String getGlFiscalTypeEnumId() {
        return glFiscalTypeEnumId;
    }

    public void setGlFiscalTypeEnumId(String glFiscalTypeEnumId) {
        this.glFiscalTypeEnumId = glFiscalTypeEnumId;
    }

    public String getGlFiscalTypeId() {
        return glFiscalTypeId;
    }

    public void setGlFiscalTypeId(String glFiscalTypeId) {
        this.glFiscalTypeId = glFiscalTypeId;
    }

    public String getIsAccountUsed() {
        return isAccountUsed;
    }

    public void setIsAccountUsed(String isAccountUsed) {
        this.isAccountUsed = isAccountUsed;
    }

    public String getIsFinancialUsed() {
        return isFinancialUsed;
    }

    public void setIsFinancialUsed(String isFinancialUsed) {
        this.isFinancialUsed = isFinancialUsed;
    }

    public String getIsIndicatorUsed() {
        return isIndicatorUsed;
    }

    public void setIsIndicatorUsed(String isIndicatorUsed) {
        this.isIndicatorUsed = isIndicatorUsed;
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

    public String getPeriodicalAbsoluteEnumId() {
        return periodicalAbsoluteEnumId;
    }

    public void setPeriodicalAbsoluteEnumId(String periodicalAbsoluteEnumId) {
        this.periodicalAbsoluteEnumId = periodicalAbsoluteEnumId;
    }

}

