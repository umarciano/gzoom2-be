package it.mapsgroup.gzoom.report.dto;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Andrea Fossi.
 */
/*@JsonTypeInfo(
        use = JsonTypeInfo.Id.MINIMAL_CLASS,
        include = JsonTypeInfo.As.PROPERTY,
        property = "@class")*/
public class CreateReport {
    /**
     * {@see https://github.com/FasterXML/jackson-docs/wiki/JacksonPolymorphicDeserialization}
     */
    @JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@class")
    private Map<String, Object> params;
    private String reportName;
    private String reportLocale;

    public CreateReport() {
        this.params = new HashMap<>();
    }

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

    public String getReportLocale() {
        return reportLocale;
    }

    public void setReportLocale(String reportLocale) {
        this.reportLocale = reportLocale;
    }
}
