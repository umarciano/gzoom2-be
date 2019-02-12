import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import it.mapsgroup.gzoom.persistence.common.CommonPersistenceConfiguration;
import it.mapsgroup.gzoom.quartz.ProbeSchedulerService;
import it.mapsgroup.gzoom.quartz.QuartzConfiguration;
import it.mapsgroup.gzoom.querydsl.dao.ReportDao;
import it.mapsgroup.gzoom.querydsl.persistence.service.QueryDslPersistenceConfiguration;
import it.mapsgroup.gzoom.service.ReportClientService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.net.MalformedURLException;
import java.util.HashMap;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author Andrea Fossi.
 */
@RunWith(SpringRunner.class)
//@RunWith(SpringJUnit4ClassRunner.class)
@ImportResource("classpath:/lmm/spring/backend-context.xml")
@TestPropertySource("classpath:test.properties")

public class ReportClientService2IT {
    private static final Logger LOG = getLogger(ReportClientService2IT.class);


    @Configuration
    @ComponentScan(basePackages = "it.mapsgroup.gzoom.querydsl.dao")
    @Import({QueryDslPersistenceConfiguration.class, CommonPersistenceConfiguration.class, QuartzConfiguration.class})
    public static class TestConfig {
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
    }

    @Autowired
    ReportDao reportDao;

    @Autowired
    ProbeSchedulerService probeSchedulerService;

    @Test
    public void name() throws InterruptedException, MalformedURLException {
        ReportClientService client = new ReportClientService(new RestTemplate());
        String id = client.createReport();
        //ResponseEntity<ReportStatus> status = client.getStatus(new URL("http://localhost:8081/rest/report/{reportId}/status"), id);

        probeSchedulerService.scheduleReportProbe(id, new HashMap<>());
        Thread.sleep(60 * 1000);
    }
}
