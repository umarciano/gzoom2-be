package it.mapsgroup.gzoom.birt;

import org.apache.xmlbeans.impl.common.IOUtil;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author Andrea Fossi.
 */
@Service
public class BirtService {
    private static final Logger LOG = getLogger(BirtService.class);

    //private final BIRTReportRunner birtReportRunner;
    private final BirtConfig config;

    private final ConcurrentHashMap<String, BirtServiceProgress> reports;

    @Autowired
    public BirtService(BirtConfig config) {
        //this.birtReportRunner = birtReportRunner;
        this.config = config;
        reports = new ConcurrentHashMap<>();
    }

    @Autowired
    private ApplicationContext applicationContext;


    /**
     * Method that create a build report
     *
     * @param taskId
     * @param reportName
     * @param reportParams
     * @param outputFileName
     * @param locale
     */
    public void build(String taskId, String reportName, Map<String, Object> reportParams, String outputFileName, Locale locale) {
        ReportRunner reportRunner = applicationContext.getBean(BIRTReportRunner.class);
        Report report = new BIRTReport(reportName, reportParams, reportRunner, locale).runReport();
        reports.put(taskId, report.getBirtServiceProgress());
        //String namePath = "/Users/anfo/projects/gzoom/logs/file_" + Calendar.getInstance().getTimeInMillis() + ".pdf";
        File outputPath = new File(config.getBirtReportOutputDir(), outputFileName + ".pdf");
        try {
            FileOutputStream out = new FileOutputStream(outputPath);
            //report.getReportContent().writeTo(out);
            IOUtil.copyCompletely(report.getReportContent().getReportContent(), out);
            report.getReportContent().close();
        } catch (IOException e) {
            //FIXME
            LOG.error("Cannot save report", e);
        } finally {
            if (reports.remove(taskId) == null) LOG.error("Task {} not found", taskId);
        }
    }


}
