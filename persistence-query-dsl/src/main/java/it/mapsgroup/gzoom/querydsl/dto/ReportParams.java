package it.mapsgroup.gzoom.querydsl.dto;

public class ReportParams {
    private String reportParamName;
    private String reportParamType;
    private Boolean mandatory;

    /**
     * @return the reportParamType
     */
    public String getReportParamType() {
        return reportParamType;
    }

    /**
     * @param reportParamType
     *            the reportParamType to set
     */
    public void setReportParamType(String reportParamType) {
        this.reportParamType = reportParamType;
    }

    /**
     * @return the reportParamName
     */
    public String getReportParamName() {
        return reportParamName;
    }

    /**
     * @param reportParamName
     *            the reportParamName to set
     */
    public void setReportParamName(String reportParamName) {
        this.reportParamName = reportParamName;
    }

    /**
     * @return the mandatory
     */
    public Boolean getMandatory() {
        return mandatory;
    }

    /**
     * @param mandatory
     *            the mandatory to set
     */
    public void setMandatory(Boolean mandatory) {
        this.mandatory = mandatory;
    }
}
