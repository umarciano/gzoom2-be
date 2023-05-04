package it.mapsgroup.gzoom.querydsl.dto;

import javax.annotation.Generated;
import com.querydsl.sql.Column;
import it.mapsgroup.gzoom.querydsl.AbstractIdentity;

/**
 * PortalPage is a Querydsl bean type
 */
@Generated("com.querydsl.codegen.BeanSerializer")
public class PortalPage implements AbstractIdentity {

    @Column("CREATED_STAMP")
    private java.time.LocalDateTime createdStamp;

    @Column("CREATED_TX_STAMP")
    private java.time.LocalDateTime createdTxStamp;

    @Column("DESCRIPTION")
    private String description;

    @Column("HELP_CONTENT_ID")
    private String helpContentId;

    @Column("LAST_UPDATED_STAMP")
    private java.time.LocalDateTime lastUpdatedStamp;

    @Column("LAST_UPDATED_TX_STAMP")
    private java.time.LocalDateTime lastUpdatedTxStamp;

    @Column("ORIGINAL_PORTAL_PAGE_ID")
    private String originalPortalPageId;

    @Column("OWNER_USER_LOGIN_ID")
    private String ownerUserLoginId;

    @Column("PARENT_PORTAL_PAGE_ID")
    private String parentPortalPageId;

    @Column("PORTAL_PAGE_ID")
    private String portalPageId;

    @Column("PORTAL_PAGE_NAME")
    private String portalPageName;

    @Column("SECURITY_GROUP_ID")
    private String securityGroupId;

    @Column("SEQUENCE_NUM")
    private java.math.BigInteger sequenceNum;

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

    public String getHelpContentId() {
        return helpContentId;
    }

    public void setHelpContentId(String helpContentId) {
        this.helpContentId = helpContentId;
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

    public String getOriginalPortalPageId() {
        return originalPortalPageId;
    }

    public void setOriginalPortalPageId(String originalPortalPageId) {
        this.originalPortalPageId = originalPortalPageId;
    }

    public String getOwnerUserLoginId() {
        return ownerUserLoginId;
    }

    public void setOwnerUserLoginId(String ownerUserLoginId) {
        this.ownerUserLoginId = ownerUserLoginId;
    }

    public String getParentPortalPageId() {
        return parentPortalPageId;
    }

    public void setParentPortalPageId(String parentPortalPageId) {
        this.parentPortalPageId = parentPortalPageId;
    }

    public String getPortalPageId() {
        return portalPageId;
    }

    public void setPortalPageId(String portalPageId) {
        this.portalPageId = portalPageId;
    }

    public String getPortalPageName() {
        return portalPageName;
    }

    public void setPortalPageName(String portalPageName) {
        this.portalPageName = portalPageName;
    }

    public String getSecurityGroupId() {
        return securityGroupId;
    }

    public void setSecurityGroupId(String securityGroupId) {
        this.securityGroupId = securityGroupId;
    }

    public java.math.BigInteger getSequenceNum() {
        return sequenceNum;
    }

    public void setSequenceNum(java.math.BigInteger sequenceNum) {
        this.sequenceNum = sequenceNum;
    }

}

