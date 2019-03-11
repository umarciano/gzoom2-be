package it.mapsgroup.gzoom.querydsl.dto;

import javax.annotation.processing.Generated;
import com.querydsl.sql.Column;
import it.mapsgroup.gzoom.querydsl.AbstractIdentity;

/**
 * RoleType is a Querydsl bean type
 */
@Generated("com.querydsl.codegen.BeanSerializer")
public class RoleType implements AbstractIdentity {

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

    @Column("HAS_TABLE")
    private Boolean hasTable;

    @Column("ICON_CONTENT_ID")
    private String iconContentId;

    @Column("LAST_MODIFIED_BY_USER_LOGIN")
    private String lastModifiedByUserLogin;

    @Column("LAST_UPDATED_STAMP")
    private java.time.LocalDateTime lastUpdatedStamp;

    @Column("LAST_UPDATED_TX_STAMP")
    private java.time.LocalDateTime lastUpdatedTxStamp;

    @Column("PARENT_TYPE_ID")
    private String parentTypeId;

    @Column("PREV_PARTY_TYPE_ID")
    private String prevPartyTypeId;

    @Column("ROLE_TYPE_ID")
    private String roleTypeId;

    @Column("SHORT_LABEL")
    private String shortLabel;

    @Column("SHORT_LABEL_LANG")
    private String shortLabelLang;

    @Column("WORK_EFFORT_ASSOC_TYPE_ID")
    private String workEffortAssocTypeId;

    @Column("WORK_EFFORT_PERIOD_ID")
    private String workEffortPeriodId;

    @Column("WORK_EFFORT_TYPE_ID")
    private String workEffortTypeId;

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

    public Boolean getHasTable() {
        return hasTable;
    }

    public void setHasTable(Boolean hasTable) {
        this.hasTable = hasTable;
    }

    public String getIconContentId() {
        return iconContentId;
    }

    public void setIconContentId(String iconContentId) {
        this.iconContentId = iconContentId;
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

    public String getPrevPartyTypeId() {
        return prevPartyTypeId;
    }

    public void setPrevPartyTypeId(String prevPartyTypeId) {
        this.prevPartyTypeId = prevPartyTypeId;
    }

    public String getRoleTypeId() {
        return roleTypeId;
    }

    public void setRoleTypeId(String roleTypeId) {
        this.roleTypeId = roleTypeId;
    }

    public String getShortLabel() {
        return shortLabel;
    }

    public void setShortLabel(String shortLabel) {
        this.shortLabel = shortLabel;
    }

    public String getShortLabelLang() {
        return shortLabelLang;
    }

    public void setShortLabelLang(String shortLabelLang) {
        this.shortLabelLang = shortLabelLang;
    }

    public String getWorkEffortAssocTypeId() {
        return workEffortAssocTypeId;
    }

    public void setWorkEffortAssocTypeId(String workEffortAssocTypeId) {
        this.workEffortAssocTypeId = workEffortAssocTypeId;
    }

    public String getWorkEffortPeriodId() {
        return workEffortPeriodId;
    }

    public void setWorkEffortPeriodId(String workEffortPeriodId) {
        this.workEffortPeriodId = workEffortPeriodId;
    }

    public String getWorkEffortTypeId() {
        return workEffortTypeId;
    }

    public void setWorkEffortTypeId(String workEffortTypeId) {
        this.workEffortTypeId = workEffortTypeId;
    }

}

