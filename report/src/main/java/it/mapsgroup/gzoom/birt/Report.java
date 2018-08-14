package it.mapsgroup.gzoom.birt;

/**
 * @author Andrea Fossi.
 */


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.OutputStream;
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
    protected ReportHandler reportContent;
    protected ReportRunner reportRunner;
    protected Locale reportLocale;
    protected BirtServiceProgress birtServiceProgress;

    public Report(String name, Map<String, Object> parameters, ReportRunner reportRunner, Locale reportLocale) {
        this.name = name;
        this.parameters = parameters;
        this.reportRunner = reportRunner;
        this.reportLocale = reportLocale;
        this.birtServiceProgress = new BirtServiceProgress();
    }

    /**
     * This is the processing method for a Report. Once the report is ran it
     * populates an internal field with a ByteArrayOutputStream of the
     * report content generated during the run process.
     *
     * @return Returns itself with the report content output stream created.
     */
    public abstract Report runReport();

    public ReportHandler getReportContent() {
        return this.reportContent;
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
}