package it.mapsgroup.gzoom.querydsl.dto;

import javax.annotation.processing.Generated;
import com.querydsl.sql.Column;
import it.mapsgroup.gzoom.querydsl.AbstractIdentity;

/**
 * WorkEffortAnalysis is a Querydsl bean type
 */
@Generated("com.querydsl.codegen.BeanSerializer")
public class WorkEffortAnalysis implements AbstractIdentity {

    @Column("AVAILABILITY_ID")
    private String availabilityId;

    @Column("CREATED_STAMP")
    private java.time.LocalDateTime createdStamp;

    @Column("CREATED_TX_STAMP")
    private java.time.LocalDateTime createdTxStamp;

    @Column("DATA_VISIBILITY")
    private String dataVisibility;

    @Column("DESCRIPTION")
    private String description;

    @Column("DESCRIPTION1")
    private String description1;

    @Column("DESCRIPTION2")
    private String description2;

    @Column("DESCRIPTION3")
    private String description3;

    @Column("DESCRIPTION4")
    private String description4;

    @Column("DESCRIPTION5")
    private String description5;

    @Column("EXCLUDE_VALIDITY")
    private Boolean excludeValidity;

    @Column("IS_MONITOR")
    private Boolean isMonitor;

    @Column("LABEL_M1_PREV")
    private String labelM1Prev;

    @Column("LABEL_M1_REAL")
    private String labelM1Real;

    @Column("LABEL_M2_PREV")
    private String labelM2Prev;

    @Column("LABEL_M2_REAL")
    private String labelM2Real;

    @Column("LABEL_M3_PREV")
    private String labelM3Prev;

    @Column("LABEL_M3_REAL")
    private String labelM3Real;

    @Column("LABEL_M4_PREV")
    private String labelM4Prev;

    @Column("LABEL_M4_REAL")
    private String labelM4Real;

    @Column("LABEL_P1_PREV")
    private String labelP1Prev;

    @Column("LABEL_P1_REAL")
    private String labelP1Real;

    @Column("LABEL_P2_PREV")
    private String labelP2Prev;

    @Column("LABEL_P2_REAL")
    private String labelP2Real;

    @Column("LABEL_P3_PREV")
    private String labelP3Prev;

    @Column("LABEL_P3_REAL")
    private String labelP3Real;

    @Column("LABEL_P4_PREV")
    private String labelP4Prev;

    @Column("LABEL_P4_REAL")
    private String labelP4Real;

    @Column("LABEL_PREV")
    private String labelPrev;

    @Column("LABEL_REAL")
    private String labelReal;

    @Column("LAST_UPDATED_STAMP")
    private java.time.LocalDateTime lastUpdatedStamp;

    @Column("LAST_UPDATED_TX_STAMP")
    private java.time.LocalDateTime lastUpdatedTxStamp;

    @Column("REFERENCE_DATE")
    private java.time.LocalDateTime referenceDate;

    @Column("REPORT_ID")
    private String reportId;

    @Column("TYPE_BALANCE_CONS_IND_ID")
    private String typeBalanceConsIndId;

    @Column("TYPE_BALANCE_SCORE_CON_ID")
    private String typeBalanceScoreConId;

    @Column("TYPE_BALANCE_SCORE_TAR_ID")
    private String typeBalanceScoreTarId;

    @Column("TYPE_BALANCE_TAR_IND_ID")
    private String typeBalanceTarIndId;

    @Column("WORK_EFFORT_ANALYSIS_ID")
    private String workEffortAnalysisId;

    @Column("WORK_EFFORT_ID")
    private String workEffortId;

    @Column("WORK_EFFORT_PURPOSE_TYPE_ID")
    private String workEffortPurposeTypeId;

    @Column("WORK_EFFORT_TYPE_ID")
    private String workEffortTypeId;

    @Column("WORK_EFFORT_TYPE_ID_SEZ1")
    private String workEffortTypeIdSez1;

    @Column("WORK_EFFORT_TYPE_ID_SEZ2")
    private String workEffortTypeIdSez2;

    @Column("WORK_EFFORT_TYPE_ID_SEZ3")
    private String workEffortTypeIdSez3;

    @Column("WORK_EFFORT_TYPE_ID_SEZ4")
    private String workEffortTypeIdSez4;

    @Column("WORK_EFFORT_TYPE_ID_SEZ5")
    private String workEffortTypeIdSez5;

    @Column("WORK_EFFORT_TYPE_ID_SEZ6")
    private String workEffortTypeIdSez6;

    @Column("YEAR_M1_PREV")
    private java.time.LocalDateTime yearM1Prev;

    @Column("YEAR_M1_REAL")
    private java.time.LocalDateTime yearM1Real;

    @Column("YEAR_M2_PREV")
    private java.time.LocalDateTime yearM2Prev;

    @Column("YEAR_M2_REAL")
    private java.time.LocalDateTime yearM2Real;

    @Column("YEAR_M3_PREV")
    private java.time.LocalDateTime yearM3Prev;

    @Column("YEAR_M3_REAL")
    private java.time.LocalDateTime yearM3Real;

    @Column("YEAR_M4_PREV")
    private java.time.LocalDateTime yearM4Prev;

    @Column("YEAR_M4_REAL")
    private java.time.LocalDateTime yearM4Real;

    @Column("YEAR_P1_PREV")
    private java.time.LocalDateTime yearP1Prev;

    @Column("YEAR_P1_REAL")
    private java.time.LocalDateTime yearP1Real;

    @Column("YEAR_P2_PREV")
    private java.time.LocalDateTime yearP2Prev;

    @Column("YEAR_P2_REAL")
    private java.time.LocalDateTime yearP2Real;

    @Column("YEAR_P3_PREV")
    private java.time.LocalDateTime yearP3Prev;

    @Column("YEAR_P3_REAL")
    private java.time.LocalDateTime yearP3Real;

    @Column("YEAR_P4_PREV")
    private java.time.LocalDateTime yearP4Prev;

    @Column("YEAR_P4_REAL")
    private java.time.LocalDateTime yearP4Real;

    @Column("YEAR_PREV")
    private java.time.LocalDateTime yearPrev;

    @Column("YEAR_REAL")
    private java.time.LocalDateTime yearReal;

    public String getAvailabilityId() {
        return availabilityId;
    }

    public void setAvailabilityId(String availabilityId) {
        this.availabilityId = availabilityId;
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

    public String getDataVisibility() {
        return dataVisibility;
    }

    public void setDataVisibility(String dataVisibility) {
        this.dataVisibility = dataVisibility;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription1() {
        return description1;
    }

    public void setDescription1(String description1) {
        this.description1 = description1;
    }

    public String getDescription2() {
        return description2;
    }

    public void setDescription2(String description2) {
        this.description2 = description2;
    }

    public String getDescription3() {
        return description3;
    }

    public void setDescription3(String description3) {
        this.description3 = description3;
    }

    public String getDescription4() {
        return description4;
    }

    public void setDescription4(String description4) {
        this.description4 = description4;
    }

    public String getDescription5() {
        return description5;
    }

    public void setDescription5(String description5) {
        this.description5 = description5;
    }

    public Boolean getExcludeValidity() {
        return excludeValidity;
    }

    public void setExcludeValidity(Boolean excludeValidity) {
        this.excludeValidity = excludeValidity;
    }

    public Boolean getIsMonitor() {
        return isMonitor;
    }

    public void setIsMonitor(Boolean isMonitor) {
        this.isMonitor = isMonitor;
    }

    public String getLabelM1Prev() {
        return labelM1Prev;
    }

    public void setLabelM1Prev(String labelM1Prev) {
        this.labelM1Prev = labelM1Prev;
    }

    public String getLabelM1Real() {
        return labelM1Real;
    }

    public void setLabelM1Real(String labelM1Real) {
        this.labelM1Real = labelM1Real;
    }

    public String getLabelM2Prev() {
        return labelM2Prev;
    }

    public void setLabelM2Prev(String labelM2Prev) {
        this.labelM2Prev = labelM2Prev;
    }

    public String getLabelM2Real() {
        return labelM2Real;
    }

    public void setLabelM2Real(String labelM2Real) {
        this.labelM2Real = labelM2Real;
    }

    public String getLabelM3Prev() {
        return labelM3Prev;
    }

    public void setLabelM3Prev(String labelM3Prev) {
        this.labelM3Prev = labelM3Prev;
    }

    public String getLabelM3Real() {
        return labelM3Real;
    }

    public void setLabelM3Real(String labelM3Real) {
        this.labelM3Real = labelM3Real;
    }

    public String getLabelM4Prev() {
        return labelM4Prev;
    }

    public void setLabelM4Prev(String labelM4Prev) {
        this.labelM4Prev = labelM4Prev;
    }

    public String getLabelM4Real() {
        return labelM4Real;
    }

    public void setLabelM4Real(String labelM4Real) {
        this.labelM4Real = labelM4Real;
    }

    public String getLabelP1Prev() {
        return labelP1Prev;
    }

    public void setLabelP1Prev(String labelP1Prev) {
        this.labelP1Prev = labelP1Prev;
    }

    public String getLabelP1Real() {
        return labelP1Real;
    }

    public void setLabelP1Real(String labelP1Real) {
        this.labelP1Real = labelP1Real;
    }

    public String getLabelP2Prev() {
        return labelP2Prev;
    }

    public void setLabelP2Prev(String labelP2Prev) {
        this.labelP2Prev = labelP2Prev;
    }

    public String getLabelP2Real() {
        return labelP2Real;
    }

    public void setLabelP2Real(String labelP2Real) {
        this.labelP2Real = labelP2Real;
    }

    public String getLabelP3Prev() {
        return labelP3Prev;
    }

    public void setLabelP3Prev(String labelP3Prev) {
        this.labelP3Prev = labelP3Prev;
    }

    public String getLabelP3Real() {
        return labelP3Real;
    }

    public void setLabelP3Real(String labelP3Real) {
        this.labelP3Real = labelP3Real;
    }

    public String getLabelP4Prev() {
        return labelP4Prev;
    }

    public void setLabelP4Prev(String labelP4Prev) {
        this.labelP4Prev = labelP4Prev;
    }

    public String getLabelP4Real() {
        return labelP4Real;
    }

    public void setLabelP4Real(String labelP4Real) {
        this.labelP4Real = labelP4Real;
    }

    public String getLabelPrev() {
        return labelPrev;
    }

    public void setLabelPrev(String labelPrev) {
        this.labelPrev = labelPrev;
    }

    public String getLabelReal() {
        return labelReal;
    }

    public void setLabelReal(String labelReal) {
        this.labelReal = labelReal;
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

    public java.time.LocalDateTime getReferenceDate() {
        return referenceDate;
    }

    public void setReferenceDate(java.time.LocalDateTime referenceDate) {
        this.referenceDate = referenceDate;
    }

    public String getReportId() {
        return reportId;
    }

    public void setReportId(String reportId) {
        this.reportId = reportId;
    }

    public String getTypeBalanceConsIndId() {
        return typeBalanceConsIndId;
    }

    public void setTypeBalanceConsIndId(String typeBalanceConsIndId) {
        this.typeBalanceConsIndId = typeBalanceConsIndId;
    }

    public String getTypeBalanceScoreConId() {
        return typeBalanceScoreConId;
    }

    public void setTypeBalanceScoreConId(String typeBalanceScoreConId) {
        this.typeBalanceScoreConId = typeBalanceScoreConId;
    }

    public String getTypeBalanceScoreTarId() {
        return typeBalanceScoreTarId;
    }

    public void setTypeBalanceScoreTarId(String typeBalanceScoreTarId) {
        this.typeBalanceScoreTarId = typeBalanceScoreTarId;
    }

    public String getTypeBalanceTarIndId() {
        return typeBalanceTarIndId;
    }

    public void setTypeBalanceTarIndId(String typeBalanceTarIndId) {
        this.typeBalanceTarIndId = typeBalanceTarIndId;
    }

    public String getWorkEffortAnalysisId() {
        return workEffortAnalysisId;
    }

    public void setWorkEffortAnalysisId(String workEffortAnalysisId) {
        this.workEffortAnalysisId = workEffortAnalysisId;
    }

    public String getWorkEffortId() {
        return workEffortId;
    }

    public void setWorkEffortId(String workEffortId) {
        this.workEffortId = workEffortId;
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

    public String getWorkEffortTypeIdSez1() {
        return workEffortTypeIdSez1;
    }

    public void setWorkEffortTypeIdSez1(String workEffortTypeIdSez1) {
        this.workEffortTypeIdSez1 = workEffortTypeIdSez1;
    }

    public String getWorkEffortTypeIdSez2() {
        return workEffortTypeIdSez2;
    }

    public void setWorkEffortTypeIdSez2(String workEffortTypeIdSez2) {
        this.workEffortTypeIdSez2 = workEffortTypeIdSez2;
    }

    public String getWorkEffortTypeIdSez3() {
        return workEffortTypeIdSez3;
    }

    public void setWorkEffortTypeIdSez3(String workEffortTypeIdSez3) {
        this.workEffortTypeIdSez3 = workEffortTypeIdSez3;
    }

    public String getWorkEffortTypeIdSez4() {
        return workEffortTypeIdSez4;
    }

    public void setWorkEffortTypeIdSez4(String workEffortTypeIdSez4) {
        this.workEffortTypeIdSez4 = workEffortTypeIdSez4;
    }

    public String getWorkEffortTypeIdSez5() {
        return workEffortTypeIdSez5;
    }

    public void setWorkEffortTypeIdSez5(String workEffortTypeIdSez5) {
        this.workEffortTypeIdSez5 = workEffortTypeIdSez5;
    }

    public String getWorkEffortTypeIdSez6() {
        return workEffortTypeIdSez6;
    }

    public void setWorkEffortTypeIdSez6(String workEffortTypeIdSez6) {
        this.workEffortTypeIdSez6 = workEffortTypeIdSez6;
    }

    public java.time.LocalDateTime getYearM1Prev() {
        return yearM1Prev;
    }

    public void setYearM1Prev(java.time.LocalDateTime yearM1Prev) {
        this.yearM1Prev = yearM1Prev;
    }

    public java.time.LocalDateTime getYearM1Real() {
        return yearM1Real;
    }

    public void setYearM1Real(java.time.LocalDateTime yearM1Real) {
        this.yearM1Real = yearM1Real;
    }

    public java.time.LocalDateTime getYearM2Prev() {
        return yearM2Prev;
    }

    public void setYearM2Prev(java.time.LocalDateTime yearM2Prev) {
        this.yearM2Prev = yearM2Prev;
    }

    public java.time.LocalDateTime getYearM2Real() {
        return yearM2Real;
    }

    public void setYearM2Real(java.time.LocalDateTime yearM2Real) {
        this.yearM2Real = yearM2Real;
    }

    public java.time.LocalDateTime getYearM3Prev() {
        return yearM3Prev;
    }

    public void setYearM3Prev(java.time.LocalDateTime yearM3Prev) {
        this.yearM3Prev = yearM3Prev;
    }

    public java.time.LocalDateTime getYearM3Real() {
        return yearM3Real;
    }

    public void setYearM3Real(java.time.LocalDateTime yearM3Real) {
        this.yearM3Real = yearM3Real;
    }

    public java.time.LocalDateTime getYearM4Prev() {
        return yearM4Prev;
    }

    public void setYearM4Prev(java.time.LocalDateTime yearM4Prev) {
        this.yearM4Prev = yearM4Prev;
    }

    public java.time.LocalDateTime getYearM4Real() {
        return yearM4Real;
    }

    public void setYearM4Real(java.time.LocalDateTime yearM4Real) {
        this.yearM4Real = yearM4Real;
    }

    public java.time.LocalDateTime getYearP1Prev() {
        return yearP1Prev;
    }

    public void setYearP1Prev(java.time.LocalDateTime yearP1Prev) {
        this.yearP1Prev = yearP1Prev;
    }

    public java.time.LocalDateTime getYearP1Real() {
        return yearP1Real;
    }

    public void setYearP1Real(java.time.LocalDateTime yearP1Real) {
        this.yearP1Real = yearP1Real;
    }

    public java.time.LocalDateTime getYearP2Prev() {
        return yearP2Prev;
    }

    public void setYearP2Prev(java.time.LocalDateTime yearP2Prev) {
        this.yearP2Prev = yearP2Prev;
    }

    public java.time.LocalDateTime getYearP2Real() {
        return yearP2Real;
    }

    public void setYearP2Real(java.time.LocalDateTime yearP2Real) {
        this.yearP2Real = yearP2Real;
    }

    public java.time.LocalDateTime getYearP3Prev() {
        return yearP3Prev;
    }

    public void setYearP3Prev(java.time.LocalDateTime yearP3Prev) {
        this.yearP3Prev = yearP3Prev;
    }

    public java.time.LocalDateTime getYearP3Real() {
        return yearP3Real;
    }

    public void setYearP3Real(java.time.LocalDateTime yearP3Real) {
        this.yearP3Real = yearP3Real;
    }

    public java.time.LocalDateTime getYearP4Prev() {
        return yearP4Prev;
    }

    public void setYearP4Prev(java.time.LocalDateTime yearP4Prev) {
        this.yearP4Prev = yearP4Prev;
    }

    public java.time.LocalDateTime getYearP4Real() {
        return yearP4Real;
    }

    public void setYearP4Real(java.time.LocalDateTime yearP4Real) {
        this.yearP4Real = yearP4Real;
    }

    public java.time.LocalDateTime getYearPrev() {
        return yearPrev;
    }

    public void setYearPrev(java.time.LocalDateTime yearPrev) {
        this.yearPrev = yearPrev;
    }

    public java.time.LocalDateTime getYearReal() {
        return yearReal;
    }

    public void setYearReal(java.time.LocalDateTime yearReal) {
        this.yearReal = yearReal;
    }

}

