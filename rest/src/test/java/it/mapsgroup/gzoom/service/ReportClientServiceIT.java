package it.mapsgroup.gzoom.service;

import it.mapsgroup.gzoom.report.report.dto.ReportStatus;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author Andrea Fossi.
 */
public class ReportClientServiceIT {
    private static final Logger LOG = getLogger(ReportClientServiceIT.class);

    @Test
    @Ignore
    public void sampleTest() {
        ReportClientService client = new ReportClientService(new RestTemplate());
        String id = client.createReport();
        ResponseEntity<ReportStatus> status = client.getStatus(id);
        LOG.info(status.getBody().toString());
    }
}
