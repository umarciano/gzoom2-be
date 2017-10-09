package it.mapsgroup.gzoom;


/**
 * @author Andrea Fossi.
 */
public class BIRTReport extends Report {

    public BIRTReport(String name, String reportParameters, ReportRunner reportRunner) {
        super(name, reportParameters, reportRunner);
    }

    @Override
    public Report runReport() {
        this.reportContent = reportRunner.runReport(this);
        return this;
    }
}