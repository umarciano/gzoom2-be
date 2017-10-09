package it.mapsgroup.gzoom;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author Andrea Fossi.
 */
@SpringBootApplication
@EnableAutoConfiguration(exclude = {MongoAutoConfiguration.class, MongoDataAutoConfiguration.class})
public class BirtReportRunnerApplication {

    public static void main(String[] args) throws IOException {
        ConfigurableApplicationContext ctx = SpringApplication.run(BirtReportRunnerApplication.class, args);

        BIRTReportRunner reportRunner = ctx.getBean(BIRTReportRunner.class);

      //  Report report = new BIRTReport("simple_report", "?null=null", reportRunner).runReport();
        Report report = new BIRTReport("simple_report", "?null=null", reportRunner).runReport();

        report.getReportContent().writeTo(new FileOutputStream("/Users/anfo/projects/gzoom/report-designer/file.pdf"));
    }
}