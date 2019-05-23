package it.mapsgroup.gzoom.querydsl.dto;

import javax.annotation.Generated;
import com.querydsl.sql.Column;
import it.mapsgroup.gzoom.querydsl.AbstractIdentity;

/**
 * ContactMechPurposeType is a Querydsl bean type
 */
@Generated("com.querydsl.codegen.BeanSerializer")
public class ContactMechPurposeType implements AbstractIdentity {

    @Column("CONTACT_MECH_PURPOSE_TYPE_ID")
    private String contactMechPurposeTypeId;

    @Column("CREATED_BY_USER_LOGIN")
    private String createdByUserLogin;

    @Column("CREATED_STAMP")
    private java.time.LocalDateTime createdStamp;

    @Column("CREATED_TX_STAMP")
    private java.time.LocalDateTime createdTxStamp;

    @Column("DESCRIPTION")
    private String description;

    @Column("LAST_MODIFIED_BY_USER_LOGIN")
    private String lastModifiedByUserLogin;

    @Column("LAST_UPDATED_STAMP")
    private java.time.LocalDateTime lastUpdatedStamp;

    @Column("LAST_UPDATED_TX_STAMP")
    private java.time.LocalDateTime lastUpdatedTxStamp;

    public String getContactMechPurposeTypeId() {
        return contactMechPurposeTypeId;
    }

    public void setContactMechPurposeTypeId(String contactMechPurposeTypeId) {
        this.contactMechPurposeTypeId = contactMechPurposeTypeId;
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

}

