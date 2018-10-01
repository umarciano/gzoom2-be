package it.mapsgroup.gzoom.querydsl.dto;

import java.util.List;

public class Report extends Content {
    private String reportName;
    private String reportContentId;
    private String parentTypeId;
    private List<ReportParams> params;
    private List<ReportType> outputFormats;

    /**
     * @return the parentTypeId
     */
    public String getParentTypeId() {
        return parentTypeId;
    }

    /**
     * @param parentTypeId the parentTypeId to set
     */
    public void setParentTypeId(String parentTypeId) {
        this.parentTypeId = parentTypeId;
    }

    /**
     * @return the reportContentId
     */
    public String getReportContentId() {
        return reportContentId;
    }

    /**
     * @param reportContentId the reportContentId to set
     */
    public void setReportContentId(String reportContentId) {
        this.reportContentId = reportContentId;
    }

    /**
     * @return the reportName
     */
    public String getReportName() {
        return reportName;
    }

    /**
     * @param reportName the reportName to set
     */
    public void setReportName(String reportName) {
        this.reportName = reportName;
    }

    /**
     * @return the params
     */
    public List<ReportParams> getParams() {
        return params;
    }

    /**
     * @param params the params to set
     */
    public void setParams(List<ReportParams> params) {
        this.params = params;
    }

    /**
     * @return the outputFormats
     */
    public List<ReportType> getOutputFormats() {
        return outputFormats;
    }

    /**
     * @param outputFormats the outputFormats to set
     */
    public void setOutputFormats(List<ReportType> outputFormats) {
        this.outputFormats = outputFormats;
    }
}
