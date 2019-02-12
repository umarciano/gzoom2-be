package it.mapsgroup.report.querydsl.dto;

import javax.annotation.Generated;
import com.querydsl.sql.Column;
import it.mapsgroup.gzoom.querydsl.AbstractIdentity;

/**
 * ReportActivity is a Querydsl bean type
 */
@Generated("com.querydsl.codegen.BeanSerializer")
public class ReportActivity implements AbstractIdentity {

    @Column("ACTIVITY_ID")
    private String activityId;

    @Column("COMPLETED_STAMP")
    private java.time.LocalDateTime completedStamp;

    @Column("CONTENT_NAME")
    private String contentName;

    @Column("CREATED_BY_USER_LOGIN")
    private String createdByUserLogin;

    @Column("CREATED_STAMP")
    private java.time.LocalDateTime createdStamp;

    @Column("CREATED_TX_STAMP")
    private java.time.LocalDateTime createdTxStamp;

    @Column("ERROR")
    private String error;

    @Column("LAST_MODIFIED_BY_USER_LOGIN")
    private String lastModifiedByUserLogin;

    @Column("LAST_UPDATED_STAMP")
    private java.time.LocalDateTime lastUpdatedStamp;

    @Column("LAST_UPDATED_TX_STAMP")
    private java.time.LocalDateTime lastUpdatedTxStamp;

    @Column("MIME_TYPE_ID")
    private String mimeTypeId;

    @Column("OBJECT_INFO")
    private String objectInfo;

    @Column("REPORT_DATA")
    private String reportData;

    @Column("REPORT_LOCALE")
    private String reportLocale;

    @Column("REPORT_NAME")
    private String reportName;

    @Column("RESUMED")
    private Boolean resumed;

    @Column("STATUS")
    private it.mapsgroup.gzoom.persistence.common.dto.enumeration.ReportActivityStatus status;

    @Column("TEMPLATE_NAME")
    private String templateName;

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public java.time.LocalDateTime getCompletedStamp() {
        return completedStamp;
    }

    public void setCompletedStamp(java.time.LocalDateTime completedStamp) {
        this.completedStamp = completedStamp;
    }

    public String getContentName() {
        return contentName;
    }

    public void setContentName(String contentName) {
        this.contentName = contentName;
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

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
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

    public String getReportData() {
        return reportData;
    }

    public void setReportData(String reportData) {
        this.reportData = reportData;
    }

    public String getReportLocale() {
        return reportLocale;
    }

    public void setReportLocale(String reportLocale) {
        this.reportLocale = reportLocale;
    }

    public String getReportName() {
        return reportName;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName;
    }

    public Boolean getResumed() {
        return resumed;
    }

    public void setResumed(Boolean resumed) {
        this.resumed = resumed;
    }

    public it.mapsgroup.gzoom.persistence.common.dto.enumeration.ReportActivityStatus getStatus() {
        return status;
    }

    public void setStatus(it.mapsgroup.gzoom.persistence.common.dto.enumeration.ReportActivityStatus status) {
        this.status = status;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

}

