package it.mapsgroup.gzoom.birt;

/**
 * @author Andrea Fossi.
 */


import java.util.Locale;
import java.util.Map;

/**
 * A Report object has a byte representation of the report output that can be
 * used to write to any output stream. This class is designed around the concept
 * of using ByteArrayOutputStreams to write PDFs to an output stream.
 */
public abstract class Report {

    protected String name;
    protected Map<String, Object> parameters;
    protected Locale reportLocale;
    protected BirtServiceProgress birtServiceProgress;
    protected String taskId;

    public Report(String taskId, String name, Map<String, Object> parameters, Locale reportLocale) {
        this.name = name;
        this.parameters = parameters;
        this.reportLocale = reportLocale;
        this.taskId = taskId;
        this.birtServiceProgress = new BirtServiceProgress();
    }


    public String getName() {
        return name;
    }

    public Map<String, Object> getParameters() {
        return parameters;
    }

    public Locale getReportLocale() {
        return reportLocale;
    }

    public BirtServiceProgress getBirtServiceProgress() {
        return birtServiceProgress;
    }

    public String getTaskId() {
        return taskId;
    }
}