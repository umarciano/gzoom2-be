package it.mapsgroup.gzoom.quartz;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import it.mapsgroup.gzoom.persistence.common.CommonPersistenceConfiguration;
import it.mapsgroup.gzoom.querydsl.dao.AbstractDao;
import it.mapsgroup.gzoom.querydsl.persistence.service.QueryDslPersistenceConfiguration;
import it.mapsgroup.gzoom.report.service.ReportCallbackService;
import it.mapsgroup.gzoom.service.GzoomReportClientConfig;
import it.mapsgroup.gzoom.service.ReportClientService;
import org.slf4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;
import org.springframework.web.client.RestTemplate;

import java.net.MalformedURLException;
import java.net.URL;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author Andrea Fossi.
 */
@Configuration
@ComponentScan(basePackageClasses = {AbstractDao.class})
@ComponentScan(basePackageClasses = ReportCallbackService.class)
@Import({QueryDslPersistenceConfiguration.class,
        CommonPersistenceConfiguration.class,
        QuartzConfiguration.class,
})
public class SchedulerTestConfig {
    private static final Logger LOG = getLogger(SchedulerTestConfig.class);

    @Bean
    public ObjectMapper getObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper
                //.registerModule(new ParameterNamesModule())
                //.registerModule(new Jdk8Module())
                .registerModule(new JavaTimeModule());
        objectMapper.setDateFormat(new ISO8601DateFormat());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return objectMapper;
    }

    @Bean
    public ReportClientService reportClientService() {
        return new ReportClientService(new RestTemplate());
    }
/*
    @Bean
    GzoomReportClientConfig gzoomReportClientConfig(Environment env) {
        return () -> {
            try {
                String url = env.getProperty("gzoom.server.report.url");
                LOG.info("gzoom.server.report.url=" + url);
                return new URL(url);
            } catch (MalformedURLException e) {
                LOG.error("URL parsing error", e);
                throw new RuntimeException(e);
            }
        };
    }*/

   /* @Bean
    SchedulerConfig schedulerConfig() {
        return new SchedulerConfig() {
            @Override
            public int getReportProbeDelay() {
                return 10;
            }

            @Override
            public int getReportProbeRetries() {
                return 240;
            }
        };
    }*/

}
