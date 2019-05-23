package it.mapsgroup.gzoom.querydsl.dto;

import javax.annotation.Generated;
import com.querydsl.sql.Column;
import it.mapsgroup.gzoom.querydsl.AbstractIdentity;

/**
 * UserPreference is a Querydsl bean type
 */
@Generated("com.querydsl.codegen.BeanSerializer")
public class UserPreference implements AbstractIdentity {

    @Column("CREATED_STAMP")
    private java.time.LocalDateTime createdStamp;

    @Column("CREATED_TX_STAMP")
    private java.time.LocalDateTime createdTxStamp;

    @Column("LAST_UPDATED_STAMP")
    private java.time.LocalDateTime lastUpdatedStamp;

    @Column("LAST_UPDATED_TX_STAMP")
    private java.time.LocalDateTime lastUpdatedTxStamp;

    @Column("USER_LOGIN_ID")
    private String userLoginId;

    @Column("USER_PREF_DATA_TYPE")
    private String userPrefDataType;

    @Column("USER_PREF_GROUP_TYPE_ID")
    private String userPrefGroupTypeId;

    @Column("USER_PREF_TYPE_ID")
    private String userPrefTypeId;

    @Column("USER_PREF_VALUE")
    private String userPrefValue;

    @Column("XML_USER_PREF")
    private String xmlUserPref;

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

    public String getUserLoginId() {
        return userLoginId;
    }

    public void setUserLoginId(String userLoginId) {
        this.userLoginId = userLoginId;
    }

    public String getUserPrefDataType() {
        return userPrefDataType;
    }

    public void setUserPrefDataType(String userPrefDataType) {
        this.userPrefDataType = userPrefDataType;
    }

    public String getUserPrefGroupTypeId() {
        return userPrefGroupTypeId;
    }

    public void setUserPrefGroupTypeId(String userPrefGroupTypeId) {
        this.userPrefGroupTypeId = userPrefGroupTypeId;
    }

    public String getUserPrefTypeId() {
        return userPrefTypeId;
    }

    public void setUserPrefTypeId(String userPrefTypeId) {
        this.userPrefTypeId = userPrefTypeId;
    }

    public String getUserPrefValue() {
        return userPrefValue;
    }

    public void setUserPrefValue(String userPrefValue) {
        this.userPrefValue = userPrefValue;
    }

    public String getXmlUserPref() {
        return xmlUserPref;
    }

    public void setXmlUserPref(String xmlUserPref) {
        this.xmlUserPref = xmlUserPref;
    }

}

