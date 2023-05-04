package it.mapsgroup.gzoom.querydsl.dto;

import javax.annotation.Generated;
import com.querydsl.sql.Column;
import it.mapsgroup.gzoom.querydsl.AbstractIdentity;

/**
 * GlAccountType is a Querydsl bean type
 */
@Generated("com.querydsl.codegen.BeanSerializer")
public class GlAccountType implements AbstractIdentity {

    @Column("ACCOUNT_TYPE_ENUM_ID")
    private String accountTypeEnumId;

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

    @Column("GL_ACCOUNT_TYPE_ID")
    private String glAccountTypeId;

    @Column("HAS_TABLE")
    private Boolean hasTable;

    @Column("IS_RESERVED_ACCOUNT")
    private Boolean isReservedAccount;

    @Column("LAST_MODIFIED_BY_USER_LOGIN")
    private String lastModifiedByUserLogin;

    @Column("LAST_UPDATED_STAMP")
    private java.time.LocalDateTime lastUpdatedStamp;

    @Column("LAST_UPDATED_TX_STAMP")
    private java.time.LocalDateTime lastUpdatedTxStamp;

    @Column("PARENT_TYPE_ID")
    private String parentTypeId;

    public String getAccountTypeEnumId() {
        return accountTypeEnumId;
    }

    public void setAccountTypeEnumId(String accountTypeEnumId) {
        this.accountTypeEnumId = accountTypeEnumId;
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

    public String getGlAccountTypeId() {
        return glAccountTypeId;
    }

    public void setGlAccountTypeId(String glAccountTypeId) {
        this.glAccountTypeId = glAccountTypeId;
    }

    public Boolean getHasTable() {
        return hasTable;
    }

    public void setHasTable(Boolean hasTable) {
        this.hasTable = hasTable;
    }

    public Boolean getIsReservedAccount() {
        return isReservedAccount;
    }

    public void setIsReservedAccount(Boolean isReservedAccount) {
        this.isReservedAccount = isReservedAccount;
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

    public String getParentTypeId() {
        return parentTypeId;
    }

    public void setParentTypeId(String parentTypeId) {
        this.parentTypeId = parentTypeId;
    }

}

