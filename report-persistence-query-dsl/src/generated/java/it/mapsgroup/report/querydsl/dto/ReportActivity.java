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

    @Column("CREATED_STAMP")
    private java.time.LocalDateTime createdStamp;

    @Column("CREATED_TX_STAMP")
    private java.time.LocalDateTime createdTxStamp;

    @Column("LAST_UPDATED_STAMP")
    private java.time.LocalDateTime lastUpdatedStamp;

    @Column("LAST_UPDATED_TX_STAMP")
    private java.time.LocalDateTime lastUpdatedTxStamp;

    @Column("REPORT_DATA")
    private String reportData;

    @Column("REPORT_NAME")
    private String reportName;

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

    public String getReportData() {
        return reportData;
    }

    public void setReportData(String reportData) {
        this.reportData = reportData;
    }

    public String getReportName() {
        return reportName;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName;
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

