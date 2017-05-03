package it.mapsgroup.gzoom.querydsl.dto;

import javax.annotation.Generated;
import com.querydsl.sql.Column;

/**
 * PartyContactMech is a Querydsl bean type
 */
@Generated("com.querydsl.codegen.BeanSerializer")
public class PartyContactMech {

    @Column("ALLOW_SOLICITATION")
    private Boolean allowSolicitation;

    @Column("COMMENTS")
    private String comments;

    @Column("CONTACT_MECH_ID")
    private String contactMechId;

    @Column("CREATED_BY_USER_LOGIN")
    private String createdByUserLogin;

    @Column("CREATED_STAMP")
    private java.time.LocalDateTime createdStamp;

    @Column("CREATED_TX_STAMP")
    private java.time.LocalDateTime createdTxStamp;

    @Column("EXTENSION")
    private String extension;

    @Column("FROM_DATE")
    private java.time.LocalDateTime fromDate;

    @Column("LAST_MODIFIED_BY_USER_LOGIN")
    private String lastModifiedByUserLogin;

    @Column("LAST_UPDATED_STAMP")
    private java.time.LocalDateTime lastUpdatedStamp;

    @Column("LAST_UPDATED_TX_STAMP")
    private java.time.LocalDateTime lastUpdatedTxStamp;

    @Column("MONTHS_WITH_CONTACT_MECH")
    private java.math.BigInteger monthsWithContactMech;

    @Column("PARTY_ID")
    private String partyId;

    @Column("ROLE_TYPE_ID")
    private String roleTypeId;

    @Column("THRU_DATE")
    private java.time.LocalDateTime thruDate;

    @Column("VERIFIED")
    private Boolean verified;

    @Column("YEARS_WITH_CONTACT_MECH")
    private java.math.BigInteger yearsWithContactMech;

    public Boolean getAllowSolicitation() {
        return allowSolicitation;
    }

    public void setAllowSolicitation(Boolean allowSolicitation) {
        this.allowSolicitation = allowSolicitation;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getContactMechId() {
        return contactMechId;
    }

    public void setContactMechId(String contactMechId) {
        this.contactMechId = contactMechId;
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

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public java.time.LocalDateTime getFromDate() {
        return fromDate;
    }

    public void setFromDate(java.time.LocalDateTime fromDate) {
        this.fromDate = fromDate;
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

    public java.math.BigInteger getMonthsWithContactMech() {
        return monthsWithContactMech;
    }

    public void setMonthsWithContactMech(java.math.BigInteger monthsWithContactMech) {
        this.monthsWithContactMech = monthsWithContactMech;
    }

    public String getPartyId() {
        return partyId;
    }

    public void setPartyId(String partyId) {
        this.partyId = partyId;
    }

    public String getRoleTypeId() {
        return roleTypeId;
    }

    public void setRoleTypeId(String roleTypeId) {
        this.roleTypeId = roleTypeId;
    }

    public java.time.LocalDateTime getThruDate() {
        return thruDate;
    }

    public void setThruDate(java.time.LocalDateTime thruDate) {
        this.thruDate = thruDate;
    }

    public Boolean getVerified() {
        return verified;
    }

    public void setVerified(Boolean verified) {
        this.verified = verified;
    }

    public java.math.BigInteger getYearsWithContactMech() {
        return yearsWithContactMech;
    }

    public void setYearsWithContactMech(java.math.BigInteger yearsWithContactMech) {
        this.yearsWithContactMech = yearsWithContactMech;
    }

}

