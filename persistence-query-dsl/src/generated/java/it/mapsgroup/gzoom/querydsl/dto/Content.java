package it.mapsgroup.gzoom.querydsl.dto;

import javax.annotation.Generated;
import com.querydsl.sql.Column;
import it.mapsgroup.gzoom.querydsl.AbstractIdentity;

/**
 * Content is a Querydsl bean type
 */
@Generated("com.querydsl.codegen.BeanSerializer")
public class Content implements AbstractIdentity {

    @Column("CHARACTER_SET_ID")
    private String characterSetId;

    @Column("CHILD_BRANCH_COUNT")
    private java.math.BigInteger childBranchCount;

    @Column("CHILD_LEAF_COUNT")
    private java.math.BigInteger childLeafCount;

    @Column("CONTENT_ID")
    private String contentId;

    @Column("CONTENT_NAME")
    private String contentName;

    @Column("CONTENT_TYPE_ID")
    private String contentTypeId;

    @Column("CREATED_BY_USER_LOGIN")
    private String createdByUserLogin;

    @Column("CREATED_DATE")
    private java.time.LocalDateTime createdDate;

    @Column("CREATED_STAMP")
    private java.time.LocalDateTime createdStamp;

    @Column("CREATED_TX_STAMP")
    private java.time.LocalDateTime createdTxStamp;

    @Column("DATA_RESOURCE_ID")
    private String dataResourceId;

    @Column("DATA_SOURCE_ID")
    private String dataSourceId;

    @Column("DECORATOR_CONTENT_ID")
    private String decoratorContentId;

    @Column("DESCRIPTION")
    private String description;

    @Column("DESCRIPTION_LANG")
    private String descriptionLang;

    @Column("INSTANCE_OF_CONTENT_ID")
    private String instanceOfContentId;

    @Column("LAST_MODIFIED_BY_USER_LOGIN")
    private String lastModifiedByUserLogin;

    @Column("LAST_MODIFIED_DATE")
    private java.time.LocalDateTime lastModifiedDate;

    @Column("LAST_UPDATED_STAMP")
    private java.time.LocalDateTime lastUpdatedStamp;

    @Column("LAST_UPDATED_TX_STAMP")
    private java.time.LocalDateTime lastUpdatedTxStamp;

    @Column("LOCALE_STRING")
    private String localeString;

    @Column("MIME_TYPE_ID")
    private String mimeTypeId;

    @Column("OWNER_CONTENT_ID")
    private String ownerContentId;

    @Column("PRIVILEGE_ENUM_ID")
    private String privilegeEnumId;

    @Column("SERVICE_NAME")
    private String serviceName;

    @Column("STATUS_ID")
    private String statusId;

    @Column("TEMPLATE_DATA_RESOURCE_ID")
    private String templateDataResourceId;

    public String getCharacterSetId() {
        return characterSetId;
    }

    public void setCharacterSetId(String characterSetId) {
        this.characterSetId = characterSetId;
    }

    public java.math.BigInteger getChildBranchCount() {
        return childBranchCount;
    }

    public void setChildBranchCount(java.math.BigInteger childBranchCount) {
        this.childBranchCount = childBranchCount;
    }

    public java.math.BigInteger getChildLeafCount() {
        return childLeafCount;
    }

    public void setChildLeafCount(java.math.BigInteger childLeafCount) {
        this.childLeafCount = childLeafCount;
    }

    public String getContentId() {
        return contentId;
    }

    public void setContentId(String contentId) {
        this.contentId = contentId;
    }

    public String getContentName() {
        return contentName;
    }

    public void setContentName(String contentName) {
        this.contentName = contentName;
    }

    public String getContentTypeId() {
        return contentTypeId;
    }

    public void setContentTypeId(String contentTypeId) {
        this.contentTypeId = contentTypeId;
    }

    public String getCreatedByUserLogin() {
        return createdByUserLogin;
    }

    public void setCreatedByUserLogin(String createdByUserLogin) {
        this.createdByUserLogin = createdByUserLogin;
    }

    public java.time.LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(java.time.LocalDateTime createdDate) {
        this.createdDate = createdDate;
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

    public String getDataResourceId() {
        return dataResourceId;
    }

    public void setDataResourceId(String dataResourceId) {
        this.dataResourceId = dataResourceId;
    }

    public String getDataSourceId() {
        return dataSourceId;
    }

    public void setDataSourceId(String dataSourceId) {
        this.dataSourceId = dataSourceId;
    }

    public String getDecoratorContentId() {
        return decoratorContentId;
    }

    public void setDecoratorContentId(String decoratorContentId) {
        this.decoratorContentId = decoratorContentId;
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

    public String getInstanceOfContentId() {
        return instanceOfContentId;
    }

    public void setInstanceOfContentId(String instanceOfContentId) {
        this.instanceOfContentId = instanceOfContentId;
    }

    public String getLastModifiedByUserLogin() {
        return lastModifiedByUserLogin;
    }

    public void setLastModifiedByUserLogin(String lastModifiedByUserLogin) {
        this.lastModifiedByUserLogin = lastModifiedByUserLogin;
    }

    public java.time.LocalDateTime getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(java.time.LocalDateTime lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
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

    public String getLocaleString() {
        return localeString;
    }

    public void setLocaleString(String localeString) {
        this.localeString = localeString;
    }

    public String getMimeTypeId() {
        return mimeTypeId;
    }

    public void setMimeTypeId(String mimeTypeId) {
        this.mimeTypeId = mimeTypeId;
    }

    public String getOwnerContentId() {
        return ownerContentId;
    }

    public void setOwnerContentId(String ownerContentId) {
        this.ownerContentId = ownerContentId;
    }

    public String getPrivilegeEnumId() {
        return privilegeEnumId;
    }

    public void setPrivilegeEnumId(String privilegeEnumId) {
        this.privilegeEnumId = privilegeEnumId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getStatusId() {
        return statusId;
    }

    public void setStatusId(String statusId) {
        this.statusId = statusId;
    }

    public String getTemplateDataResourceId() {
        return templateDataResourceId;
    }

    public void setTemplateDataResourceId(String templateDataResourceId) {
        this.templateDataResourceId = templateDataResourceId;
    }

}

