package it.mapsgroup.gzoom;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;

/**
 * @author Andrea Fossi.
 */
@SpringBootApplication(exclude = {MongoAutoConfiguration.class, MongoDataAutoConfiguration.class})
public class BirtReportRunnerApplication {

    public static void main(String[] args) throws IOException {
        ConfigurableApplicationContext ctx = SpringApplication.run(BirtReportRunnerApplication.class, args);

        BIRTReportRunner reportRunner = ctx.getBean(BIRTReportRunner.class);

        //Report report = new BIRTReport("simple_report", "?null=null", reportRunner).runReport();
        HashMap<String, Object> reportParameters = new HashMap<>();
        //todo add parameters here

        Report report = new BIRTReport("simple_report", reportParameters, reportRunner, Locale.ITALIAN).runReport();

        report.getReportContent().writeTo(new FileOutputStream("/Users/anfo/projects/gzoom/report-designer/file.pdf"));
    }
}