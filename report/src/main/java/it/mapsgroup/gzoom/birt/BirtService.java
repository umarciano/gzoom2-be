package it.mapsgroup.gzoom.birt;

import org.apache.xmlbeans.impl.common.IOUtil;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Locale;
import java.util.Map;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author Andrea Fossi.
 */
@Service
public class BirtService {
    private static final Logger LOG = getLogger(BirtService.class);

    //private final BIRTReportRunner birtReportRunner;
    private final BirtConfig config;
    private final ReportRunner reportRunner;


    @Autowired
    public BirtService(BirtConfig config, ReportRunner reportRunner) {
        //this.birtReportRunner = birtReportRunner;
        this.config = config;
        this.reportRunner = reportRunner;
    }


    /**
     * Method that create a build report
     *
     * @param taskId
     * @param reportName
     * @param reportParams
     * @param locale
     */
    public Report build(String taskId, String reportName, Map<String, Object> reportParams, Locale locale) {
        Report report = new BIRTReport(reportName, reportParams, locale);
        return report;
    }

    public void run(String outputFileName, Report report) {
        ReportHandler reportHandler = reportRunner.runReport(report);
        File outputPath = new File(config.getBirtReportOutputDir(), outputFileName + ".pdf");
        try {
            FileOutputStream out = new FileOutputStream(outputPath);
            //report.getReportContent().writeTo(out);
            IOUtil.copyCompletely(reportHandler.getReportContent(), out);
            reportHandler.close();
        } catch (IOException e) {
            LOG.error("Cannot save report", e);
            throw new RuntimeException(e);//fixme manage exception
        }
    }

}
