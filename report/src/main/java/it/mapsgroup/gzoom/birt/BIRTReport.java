package it.mapsgroup.gzoom.birt;


import java.util.Locale;
import java.util.Map;

/**
 * @author Andrea Fossi.
 */
public class BIRTReport extends Report {

    public BIRTReport(String name, Map<String, Object> reportParameters, ReportRunner reportRunner, Locale reportLocale) {
        super(name, reportParameters, reportRunner, reportLocale);
    }

    @Override
    public Report runReport() {
        this.reportContent = reportRunner.runReport(this);
        return this;
    }
}