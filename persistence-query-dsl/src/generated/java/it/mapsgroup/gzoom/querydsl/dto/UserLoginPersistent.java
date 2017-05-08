package it.mapsgroup.gzoom.querydsl.dto;

import javax.annotation.Generated;
import com.querydsl.sql.Column;

/**
 * UserLoginPersistent is a Querydsl bean type
 */
@Generated("com.querydsl.codegen.BeanSerializer")
public class UserLoginPersistent {

    @Column("CREATED_STAMP")
    private java.time.LocalDateTime createdStamp;

    @Column("CREATED_TX_STAMP")
    private java.time.LocalDateTime createdTxStamp;

    @Column("CURRENT_PASSWORD")
    private String currentPassword;

    @Column("DESCRIPTION")
    private String description;

    @Column("DISABLED_DATE_TIME")
    private java.time.LocalDateTime disabledDateTime;

    @Column("ENABLED")
    private Boolean enabled;

    @Column("EXTERNAL_AUTH_ID")
    private String externalAuthId;

    @Column("EXTERNAL_SYSTEM")
    private String externalSystem;

    @Column("HAS_LOGGED_OUT")
    private Boolean hasLoggedOut;

    @Column("IS_SYSTEM")
    private Boolean isSystem;

    @Column("LAST_CURRENCY_UOM")
    private String lastCurrencyUom;

    @Column("LAST_LOCALE")
    private String lastLocale;

    @Column("LAST_TIME_ZONE")
    private String lastTimeZone;

    @Column("LAST_UPDATED_STAMP")
    private java.time.LocalDateTime lastUpdatedStamp;

    @Column("LAST_UPDATED_TX_STAMP")
    private java.time.LocalDateTime lastUpdatedTxStamp;

    @Column("PARTY_ID")
    private String partyId;

    @Column("PASSWORD_HINT")
    private String passwordHint;

    @Column("REQUIRE_PASSWORD_CHANGE")
    private Boolean requirePasswordChange;

    @Column("SUCCESSIVE_FAILED_LOGINS")
    private java.math.BigInteger successiveFailedLogins;

    @Column("USER_LDAP_DN")
    private String userLdapDn;

    @Column("USER_LOGIN_ID")
    private String userLoginId;

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

    public String getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public java.time.LocalDateTime getDisabledDateTime() {
        return disabledDateTime;
    }

    public void setDisabledDateTime(java.time.LocalDateTime disabledDateTime) {
        this.disabledDateTime = disabledDateTime;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getExternalAuthId() {
        return externalAuthId;
    }

    public void setExternalAuthId(String externalAuthId) {
        this.externalAuthId = externalAuthId;
    }

    public String getExternalSystem() {
        return externalSystem;
    }

    public void setExternalSystem(String externalSystem) {
        this.externalSystem = externalSystem;
    }

    public Boolean getHasLoggedOut() {
        return hasLoggedOut;
    }

    public void setHasLoggedOut(Boolean hasLoggedOut) {
        this.hasLoggedOut = hasLoggedOut;
    }

    public Boolean getIsSystem() {
        return isSystem;
    }

    public void setIsSystem(Boolean isSystem) {
        this.isSystem = isSystem;
    }

    public String getLastCurrencyUom() {
        return lastCurrencyUom;
    }

    public void setLastCurrencyUom(String lastCurrencyUom) {
        this.lastCurrencyUom = lastCurrencyUom;
    }

    public String getLastLocale() {
        return lastLocale;
    }

    public void setLastLocale(String lastLocale) {
        this.lastLocale = lastLocale;
    }

    public String getLastTimeZone() {
        return lastTimeZone;
    }

    public void setLastTimeZone(String lastTimeZone) {
        this.lastTimeZone = lastTimeZone;
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

    public String getPasswordHint() {
        return passwordHint;
    }

    public void setPasswordHint(String passwordHint) {
        this.passwordHint = passwordHint;
    }

    public Boolean getRequirePasswordChange() {
        return requirePasswordChange;
    }

    public void setRequirePasswordChange(Boolean requirePasswordChange) {
        this.requirePasswordChange = requirePasswordChange;
    }

    public java.math.BigInteger getSuccessiveFailedLogins() {
        return successiveFailedLogins;
    }

    public void setSuccessiveFailedLogins(java.math.BigInteger successiveFailedLogins) {
        this.successiveFailedLogins = successiveFailedLogins;
    }

    public String getUserLdapDn() {
        return userLdapDn;
    }

    public void setUserLdapDn(String userLdapDn) {
        this.userLdapDn = userLdapDn;
    }

    public String getUserLoginId() {
        return userLoginId;
    }

    public void setUserLoginId(String userLoginId) {
        this.userLoginId = userLoginId;
    }

}

