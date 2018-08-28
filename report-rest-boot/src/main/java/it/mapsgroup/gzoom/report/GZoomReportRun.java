package it.mapsgroup.gzoom.report;


import com.querydsl.sql.Configuration;
import com.querydsl.sql.SQLQueryFactory;
import com.querydsl.sql.types.EnumByNameType;
import it.mapsgroup.gzoom.ReportModuleConfiguration;
import it.mapsgroup.gzoom.persistence.common.dto.enumeration.ReportActivityStatus;
import it.mapsgroup.gzoom.querydsl.persistence.service.QueryDslPersistenceConfiguration;
import it.mapsgroup.gzoom.rest.ReportJobController;
import it.mapsgroup.gzoom.service.ReportJobService;
import it.mapsgroup.gzoom.service.ReportTaskService;
import it.memelabs.smartnebula.spring.boot.config.ApplicationContextProvider;
import it.memelabs.smartnebula.spring.boot.config.PropertyApplicationContextInitializer;
import org.slf4j.Logger;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.util.StringUtils;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author Andrea Fossi.
 */
@SpringBootApplication(exclude = {MongoAutoConfiguration.class, MongoDataAutoConfiguration.class,})
@EnableConfigurationProperties
@ComponentScan(basePackageClasses = {ReportJobService.class, ReportJobController.class})
@Import({ReportModuleConfiguration.class})
public class GZoomReportRun {
    private static final Logger LOG = getLogger(GZoomReportRun.class);

    @Bean
    public ApplicationContextProvider applicationContextProvider(ApplicationContext ac){
        ApplicationContextProvider provider = new ApplicationContextProvider();
        provider.setApplicationContext(ac);
        return provider;
    }

    public static void main(String[] args) throws Exception {
        if (StringUtils.isEmpty(System.getProperty("logging.config")))
            System.setProperty("logging.config", System.getProperty("gzoom.conf.dir") + "/logback.xml");
        LOG.info("logging.config [{}])", System.getProperty("logging.config"));
        ConfigurableApplicationContext context = new SpringApplicationBuilder(GZoomReportRun.class)
                .initializers(new PropertyApplicationContextInitializer("file:" + System.getProperty("gzoom.conf.dir") + "/gzoom-report.properties"))
                .run(args);

        //fixme remove workaround
        Configuration configuration = context.getBean(SQLQueryFactory.class).getConfiguration();
        configuration.register("report_activity", "status", new EnumByNameType<>(ReportActivityStatus.class));
        configuration.register("REPORT_ACTIVITY", "status", new EnumByNameType<>(ReportActivityStatus.class));
        configuration.register("REPORT_ACTIVITY", "STATUS", new EnumByNameType<>(ReportActivityStatus.class));
        configuration.register("report_activity", "STATUS", new EnumByNameType<>(ReportActivityStatus.class));


        //resume suspended
        context.getBean(ReportTaskService.class).resume();
    }


}
