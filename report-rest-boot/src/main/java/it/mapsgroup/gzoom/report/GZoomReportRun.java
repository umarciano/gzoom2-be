package it.mapsgroup.gzoom.report;


import it.mapsgroup.gzoom.ReportConfiguration;
import it.mapsgroup.gzoom.rest.ReportJobController;
import it.mapsgroup.gzoom.service.ReportJobService;
import it.memelabs.smartnebula.spring.boot.config.PropertyApplicationContextInitializer;
import org.slf4j.Logger;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.util.StringUtils;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author Andrea Fossi.
 */
//@ImportResource("classpath:/bootstrap-context.xml")
@SpringBootApplication
@EnableConfigurationProperties
@ComponentScan(basePackageClasses = {ReportJobService.class, ReportJobController.class})
@Import({ReportConfiguration.class})
public class GZoomReportRun {
    private static final Logger LOG = getLogger(GZoomReportRun.class);

    public static void main(String[] args) throws Exception {
        if (StringUtils.isEmpty(System.getProperty("logging.config")))
            System.setProperty("logging.config", System.getProperty("gzoom.conf.dir") + "/logback.xml");
        LOG.info("logging.config [{}])", System.getProperty("logging.config"));
        //SpringApplication.run(LmmRun.class, args);
        new SpringApplicationBuilder(GZoomReportRun.class)
                .initializers(new PropertyApplicationContextInitializer("file:" + System.getProperty("gzoom.conf.dir") + "/gzoom.properties"))
                .run(args);
    }


}
