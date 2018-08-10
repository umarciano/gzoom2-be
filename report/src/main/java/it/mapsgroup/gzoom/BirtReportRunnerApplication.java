package it.mapsgroup.gzoom;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

/**
 * @author Andrea Fossi.
 */
@SpringBootApplication(exclude = {MongoAutoConfiguration.class, MongoDataAutoConfiguration.class,})
public class BirtReportRunnerApplication {

    public static void main(String[] args) throws IOException {
        ConfigurableApplicationContext ctx = SpringApplication.run(BirtReportRunnerApplication.class, args);

        BIRTReportRunner reportRunner = ctx.getBean(BIRTReportRunner.class);

        //Report report = new BIRTReport("simple_report", "?null=null", reportRunner).runReport();
        HashMap<String, Object> reportParameters = new HashMap<>();
        //todo add parameters here
        reportParameters.put("langLocale", "");
        reportParameters.put("outputFormat", "pdf");
        reportParameters.put("workEffortTypeId", "15AP0PPC");
        // reportParameters.put("workEffortId", "E10211");
        reportParameters.put("exposeReleaseDate", "Y");
        reportParameters.put("exposePaginator", "Y");
        reportParameters.put("reportContentId", "REPORT_CATALOGO");
        reportParameters.put("userLoginId", "admin");
        reportParameters.put("userProfile", "MGR_ADMIN");
        reportParameters.put("birtOutputFileName", "CatalogoTreLivelli");
        reportParameters.put("localDispatcherName", "corperf");
        reportParameters.put("defaultOrganizationPartyId", "Company");


        Report report = new BIRTReport("CatalogoTreLivelli_ORI", reportParameters, reportRunner, Locale.ITALIAN).runReport();
        //Report report = new BIRTReport("simple_report", reportParameters, reportRunner, Locale.ITALIAN).runReport();

        String namePath = "/Users/anfo/projects/gzoom/logs/file_" + Calendar.getInstance().getTimeInMillis() + ".pdf";
        report.getReportContent().writeTo(new FileOutputStream(namePath));

    }

}