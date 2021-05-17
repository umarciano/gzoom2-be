package it.mapsgroup.gzoom.querydsl.dto;

import javax.annotation.Generated;
import com.querydsl.sql.Column;

/**
 * OrganizationInterfaceExt is a Querydsl bean type
 */
@Generated("com.querydsl.codegen.BeanSerializer")
public class OrganizationInterfaceExt {

    @Column("DATA_SOURCE")
    private String dataSource;

    @Column("DESCRIPTION")
    private String description;

    @Column("DESCRIPTION_LANG")
    private String descriptionLang;

    @Column("LONG_DESCRIPTION")
    private String longDescription;

    @Column("LONG_DESCRIPTION_LANG")
    private String longDescriptionLang;

    @Column("ORG_CODE")
    private String orgCode;

    @Column("ORG_ROLE_TYPE_ID")
    private String orgRoleTypeId;

    @Column("PARENT_FROM_DATE")
    private java.time.LocalDateTime parentFromDate;

    @Column("PARENT_ORG_CODE")
    private String parentOrgCode;

    @Column("PARENT_ROLE_TYPE_ID")
    private String parentRoleTypeId;

    @Column("REF_DATE")
    private java.time.LocalDateTime refDate;

    @Column("RESPONSIBLE_CODE")
    private String responsibleCode;

    @Column("RESPONSIBLE_COMMENTS")
    private String responsibleComments;

    @Column("RESPONSIBLE_FROM_DATE")
    private java.time.LocalDateTime responsibleFromDate;

    @Column("RESPONSIBLE_ROLE_TYPE_ID")
    private String responsibleRoleTypeId;

    @Column("RESPONSIBLE_THRU_DATE")
    private java.time.LocalDateTime responsibleThruDate;

    @Column("THRU_DATE")
    private java.time.LocalDateTime thruDate;

    @Column("VAT_CODE")
    private String vatCode;

    public String getDataSource() {
        return dataSource;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
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

    public String getLongDescription() {
        return longDescription;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }

    public String getLongDescriptionLang() {
        return longDescriptionLang;
    }

    public void setLongDescriptionLang(String longDescriptionLang) {
        this.longDescriptionLang = longDescriptionLang;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getOrgRoleTypeId() {
        return orgRoleTypeId;
    }

    public void setOrgRoleTypeId(String orgRoleTypeId) {
        this.orgRoleTypeId = orgRoleTypeId;
    }

    public java.time.LocalDateTime getParentFromDate() {
        return parentFromDate;
    }

    public void setParentFromDate(java.time.LocalDateTime parentFromDate) {
        this.parentFromDate = parentFromDate;
    }

    public String getParentOrgCode() {
        return parentOrgCode;
    }

    public void setParentOrgCode(String parentOrgCode) {
        this.parentOrgCode = parentOrgCode;
    }

    public String getParentRoleTypeId() {
        return parentRoleTypeId;
    }

    public void setParentRoleTypeId(String parentRoleTypeId) {
        this.parentRoleTypeId = parentRoleTypeId;
    }

    public java.time.LocalDateTime getRefDate() {
        return refDate;
    }

    public void setRefDate(java.time.LocalDateTime refDate) {
        this.refDate = refDate;
    }

    public String getResponsibleCode() {
        return responsibleCode;
    }

    public void setResponsibleCode(String responsibleCode) {
        this.responsibleCode = responsibleCode;
    }

    public String getResponsibleComments() {
        return responsibleComments;
    }

    public void setResponsibleComments(String responsibleComments) {
        this.responsibleComments = responsibleComments;
    }

    public java.time.LocalDateTime getResponsibleFromDate() {
        return responsibleFromDate;
    }

    public void setResponsibleFromDate(java.time.LocalDateTime responsibleFromDate) {
        this.responsibleFromDate = responsibleFromDate;
    }

    public String getResponsibleRoleTypeId() {
        return responsibleRoleTypeId;
    }

    public void setResponsibleRoleTypeId(String responsibleRoleTypeId) {
        this.responsibleRoleTypeId = responsibleRoleTypeId;
    }

    public java.time.LocalDateTime getResponsibleThruDate() {
        return responsibleThruDate;
    }

    public void setResponsibleThruDate(java.time.LocalDateTime responsibleThruDate) {
        this.responsibleThruDate = responsibleThruDate;
    }

    public java.time.LocalDateTime getThruDate() {
        return thruDate;
    }

    public void setThruDate(java.time.LocalDateTime thruDate) {
        this.thruDate = thruDate;
    }

    public String getVatCode() {
        return vatCode;
    }

    public void setVatCode(String vatCode) {
        this.vatCode = vatCode;
    }

}

