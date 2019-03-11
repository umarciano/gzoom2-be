package it.mapsgroup.gzoom.querydsl.dto;

import javax.annotation.processing.Generated;
import com.querydsl.sql.Column;
import it.mapsgroup.gzoom.querydsl.AbstractIdentity;

/**
 * DataResource is a Querydsl bean type
 */
@Generated("com.querydsl.codegen.BeanSerializer")
public class DataResource implements AbstractIdentity {

    @Column("CHARACTER_SET_ID")
    private String characterSetId;

    @Column("CREATED_BY_USER_LOGIN")
    private String createdByUserLogin;

    @Column("CREATED_DATE")
    private java.time.LocalDateTime createdDate;

    @Column("CREATED_STAMP")
    private java.time.LocalDateTime createdStamp;

    @Column("CREATED_TX_STAMP")
    private java.time.LocalDateTime createdTxStamp;

    @Column("DATA_CATEGORY_ID")
    private String dataCategoryId;

    @Column("DATA_RESOURCE_ID")
    private String dataResourceId;

    @Column("DATA_RESOURCE_NAME")
    private String dataResourceName;

    @Column("DATA_RESOURCE_TYPE_ID")
    private String dataResourceTypeId;

    @Column("DATA_SOURCE_ID")
    private String dataSourceId;

    @Column("DATA_TEMPLATE_TYPE_ID")
    private String dataTemplateTypeId;

    @Column("IS_PUBLIC")
    private Boolean isPublic;

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

    @Column("OBJECT_INFO")
    private String objectInfo;

    @Column("RELATED_DETAIL_ID")
    private String relatedDetailId;

    @Column("STATUS_ID")
    private String statusId;

    @Column("SURVEY_ID")
    private String surveyId;

    @Column("SURVEY_RESPONSE_ID")
    private String surveyResponseId;

    public String getCharacterSetId() {
        return characterSetId;
    }

    public void setCharacterSetId(String characterSetId) {
        this.characterSetId = characterSetId;
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

    public String getDataCategoryId() {
        return dataCategoryId;
    }

    public void setDataCategoryId(String dataCategoryId) {
        this.dataCategoryId = dataCategoryId;
    }

    public String getDataResourceId() {
        return dataResourceId;
    }

    public void setDataResourceId(String dataResourceId) {
        this.dataResourceId = dataResourceId;
    }

    public String getDataResourceName() {
        return dataResourceName;
    }

    public void setDataResourceName(String dataResourceName) {
        this.dataResourceName = dataResourceName;
    }

    public String getDataResourceTypeId() {
        return dataResourceTypeId;
    }

    public void setDataResourceTypeId(String dataResourceTypeId) {
        this.dataResourceTypeId = dataResourceTypeId;
    }

    public String getDataSourceId() {
        return dataSourceId;
    }

    public void setDataSourceId(String dataSourceId) {
        this.dataSourceId = dataSourceId;
    }

    public String getDataTemplateTypeId() {
        return dataTemplateTypeId;
    }

    public void setDataTemplateTypeId(String dataTemplateTypeId) {
        this.dataTemplateTypeId = dataTemplateTypeId;
    }

    public Boolean getIsPublic() {
        return isPublic;
    }

    public void setIsPublic(Boolean isPublic) {
        this.isPublic = isPublic;
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

    public String getObjectInfo() {
        return objectInfo;
    }

    public void setObjectInfo(String objectInfo) {
        this.objectInfo = objectInfo;
    }

    public String getRelatedDetailId() {
        return relatedDetailId;
    }

    public void setRelatedDetailId(String relatedDetailId) {
        this.relatedDetailId = relatedDetailId;
    }

    public String getStatusId() {
        return statusId;
    }

    public void setStatusId(String statusId) {
        this.statusId = statusId;
    }

    public String getSurveyId() {
        return surveyId;
    }

    public void setSurveyId(String surveyId) {
        this.surveyId = surveyId;
    }

    public String getSurveyResponseId() {
        return surveyResponseId;
    }

    public void setSurveyResponseId(String surveyResponseId) {
        this.surveyResponseId = surveyResponseId;
    }

}

