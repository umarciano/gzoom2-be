package it.mapsgroup.gzoom.querydsl.dto;

import javax.annotation.processing.Generated;
import com.querydsl.sql.Column;
import it.mapsgroup.gzoom.querydsl.AbstractIdentity;

/**
 * PartyRelationship is a Querydsl bean type
 */
@Generated("com.querydsl.codegen.BeanSerializer")
public class PartyRelationship implements AbstractIdentity {

    @Column("COMMENTS")
    private String comments;

    @Column("CREATED_BY_USER_LOGIN")
    private String createdByUserLogin;

    @Column("CREATED_STAMP")
    private java.time.LocalDateTime createdStamp;

    @Column("CREATED_TX_STAMP")
    private java.time.LocalDateTime createdTxStamp;

    @Column("FROM_DATE")
    private java.time.LocalDateTime fromDate;

    @Column("LAST_MODIFIED_BY_USER_LOGIN")
    private String lastModifiedByUserLogin;

    @Column("LAST_UPDATED_STAMP")
    private java.time.LocalDateTime lastUpdatedStamp;

    @Column("LAST_UPDATED_TX_STAMP")
    private java.time.LocalDateTime lastUpdatedTxStamp;

    @Column("PARTY_ID_FROM")
    private String partyIdFrom;

    @Column("PARTY_ID_TO")
    private String partyIdTo;

    @Column("PARTY_RELATIONSHIP_TYPE_ID")
    private String partyRelationshipTypeId;

    @Column("PERMISSIONS_ENUM_ID")
    private String permissionsEnumId;

    @Column("POSITION_TITLE")
    private String positionTitle;

    @Column("PRIORITY_TYPE_ID")
    private String priorityTypeId;

    @Column("RELATIONSHIP_NAME")
    private String relationshipName;

    @Column("RELATIONSHIP_VALUE")
    private java.math.BigDecimal relationshipValue;

    @Column("ROLE_TYPE_ID_FROM")
    private String roleTypeIdFrom;

    @Column("ROLE_TYPE_ID_TO")
    private String roleTypeIdTo;

    @Column("SECURITY_GROUP_ID")
    private String securityGroupId;

    @Column("STATUS_ID")
    private String statusId;

    @Column("THRU_DATE")
    private java.time.LocalDateTime thruDate;

    @Column("VALUE_UOM_ID")
    private String valueUomId;

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
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

    public String getPartyIdFrom() {
        return partyIdFrom;
    }

    public void setPartyIdFrom(String partyIdFrom) {
        this.partyIdFrom = partyIdFrom;
    }

    public String getPartyIdTo() {
        return partyIdTo;
    }

    public void setPartyIdTo(String partyIdTo) {
        this.partyIdTo = partyIdTo;
    }

    public String getPartyRelationshipTypeId() {
        return partyRelationshipTypeId;
    }

    public void setPartyRelationshipTypeId(String partyRelationshipTypeId) {
        this.partyRelationshipTypeId = partyRelationshipTypeId;
    }

    public String getPermissionsEnumId() {
        return permissionsEnumId;
    }

    public void setPermissionsEnumId(String permissionsEnumId) {
        this.permissionsEnumId = permissionsEnumId;
    }

    public String getPositionTitle() {
        return positionTitle;
    }

    public void setPositionTitle(String positionTitle) {
        this.positionTitle = positionTitle;
    }

    public String getPriorityTypeId() {
        return priorityTypeId;
    }

    public void setPriorityTypeId(String priorityTypeId) {
        this.priorityTypeId = priorityTypeId;
    }

    public String getRelationshipName() {
        return relationshipName;
    }

    public void setRelationshipName(String relationshipName) {
        this.relationshipName = relationshipName;
    }

    public java.math.BigDecimal getRelationshipValue() {
        return relationshipValue;
    }

    public void setRelationshipValue(java.math.BigDecimal relationshipValue) {
        this.relationshipValue = relationshipValue;
    }

    public String getRoleTypeIdFrom() {
        return roleTypeIdFrom;
    }

    public void setRoleTypeIdFrom(String roleTypeIdFrom) {
        this.roleTypeIdFrom = roleTypeIdFrom;
    }

    public String getRoleTypeIdTo() {
        return roleTypeIdTo;
    }

    public void setRoleTypeIdTo(String roleTypeIdTo) {
        this.roleTypeIdTo = roleTypeIdTo;
    }

    public String getSecurityGroupId() {
        return securityGroupId;
    }

    public void setSecurityGroupId(String securityGroupId) {
        this.securityGroupId = securityGroupId;
    }

    public String getStatusId() {
        return statusId;
    }

    public void setStatusId(String statusId) {
        this.statusId = statusId;
    }

    public java.time.LocalDateTime getThruDate() {
        return thruDate;
    }

    public void setThruDate(java.time.LocalDateTime thruDate) {
        this.thruDate = thruDate;
    }

    public String getValueUomId() {
        return valueUomId;
    }

    public void setValueUomId(String valueUomId) {
        this.valueUomId = valueUomId;
    }

}

