package it.mapsgroup.gzoom.report.dto;

import java.util.Map;

/**
 * @author Andrea Fossi.
 */
public class CreateReport {
    private Map<String, Object> params;
    private String reportName;

    public Map<String, Object> getParams() {
        return params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }

    public String getReportName() {
        return reportName;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName;
    }
}
