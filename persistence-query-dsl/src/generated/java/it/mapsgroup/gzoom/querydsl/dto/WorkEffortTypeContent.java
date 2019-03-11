package it.mapsgroup.gzoom.querydsl.dto;

import javax.annotation.processing.Generated;
import com.querydsl.sql.Column;
import it.mapsgroup.gzoom.querydsl.AbstractIdentity;

/**
 * WorkEffortTypeContent is a Querydsl bean type
 */
@Generated("com.querydsl.codegen.BeanSerializer")
public class WorkEffortTypeContent implements AbstractIdentity {

    @Column("CONTENT_ID")
    private String contentId;

    @Column("CREATED_BY_USER_LOGIN")
    private String createdByUserLogin;

    @Column("CREATED_STAMP")
    private java.time.LocalDateTime createdStamp;

    @Column("CREATED_TX_STAMP")
    private java.time.LocalDateTime createdTxStamp;

    @Column("ETCH")
    private String etch;

    @Column("ETCH_LANG")
    private String etchLang;

    @Column("IS_VISIBLE")
    private Boolean isVisible;

    @Column("LAST_MODIFIED_BY_USER_LOGIN")
    private String lastModifiedByUserLogin;

    @Column("LAST_UPDATED_STAMP")
    private java.time.LocalDateTime lastUpdatedStamp;

    @Column("LAST_UPDATED_TX_STAMP")
    private java.time.LocalDateTime lastUpdatedTxStamp;

    @Column("PARAMS")
    private String params;

    @Column("SEQUENCE_NUM")
    private java.math.BigInteger sequenceNum;

    @Column("USE_FILTER")
    private Boolean useFilter;

    @Column("WE_TYPE_CONTENT_TYPE_ID")
    private String weTypeContentTypeId;

    @Column("WORK_EFFORT_PURPOSE_TYPE_ID")
    private String workEffortPurposeTypeId;

    @Column("WORK_EFFORT_TYPE_ID")
    private String workEffortTypeId;

    public String getContentId() {
        return contentId;
    }

    public void setContentId(String contentId) {
        this.contentId = contentId;
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

    public String getEtch() {
        return etch;
    }

    public void setEtch(String etch) {
        this.etch = etch;
    }

    public String getEtchLang() {
        return etchLang;
    }

    public void setEtchLang(String etchLang) {
        this.etchLang = etchLang;
    }

    public Boolean getIsVisible() {
        return isVisible;
    }

    public void setIsVisible(Boolean isVisible) {
        this.isVisible = isVisible;
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

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public java.math.BigInteger getSequenceNum() {
        return sequenceNum;
    }

    public void setSequenceNum(java.math.BigInteger sequenceNum) {
        this.sequenceNum = sequenceNum;
    }

    public Boolean getUseFilter() {
        return useFilter;
    }

    public void setUseFilter(Boolean useFilter) {
        this.useFilter = useFilter;
    }

    public String getWeTypeContentTypeId() {
        return weTypeContentTypeId;
    }

    public void setWeTypeContentTypeId(String weTypeContentTypeId) {
        this.weTypeContentTypeId = weTypeContentTypeId;
    }

    public String getWorkEffortPurposeTypeId() {
        return workEffortPurposeTypeId;
    }

    public void setWorkEffortPurposeTypeId(String workEffortPurposeTypeId) {
        this.workEffortPurposeTypeId = workEffortPurposeTypeId;
    }

    public String getWorkEffortTypeId() {
        return workEffortTypeId;
    }

    public void setWorkEffortTypeId(String workEffortTypeId) {
        this.workEffortTypeId = workEffortTypeId;
    }

}

