package it.mapsgroup.gzoom.quartz;

import it.mapsgroup.gzoom.persistence.common.dto.enumeration.ReportActivityStatus;
import it.mapsgroup.gzoom.report.report.dto.ReportStatus;
import it.mapsgroup.gzoom.service.ReportClientService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.MalformedURLException;
import java.net.URL;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author Andrea Fossi.
 */
@Service
public class ProbeService {
    private static final Logger LOG = getLogger(ProbeService.class);

    private final ReportClientService reportClientService = new ReportClientService(new RestTemplate());
    private final ProbeSchedulerService schedulerService;

    @Autowired
    public ProbeService(ProbeSchedulerService schedulerService) {
        this.schedulerService = schedulerService;
    }

    public void probeReport(String id) {
        ResponseEntity<ReportStatus> status = null;
        try {
            status = reportClientService.getStatus(new URL("http://localhost:8081/rest/report"), id);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        LOG.info("Report status: {}", status.getBody().getActivityStatus());
        LOG.info("******************* {} ******************* ", status.getBody().getActivityStatus());
        if (StringUtils.equals(ReportActivityStatus.DONE.name(), status.getBody().getActivityStatus())) {
            schedulerService.unScheduleReportProbe(id);
        }
        if (
                StringUtils.equals(ReportActivityStatus.QUEUED.name(), status.getBody().getActivityStatus()) ||
                        StringUtils.equals(ReportActivityStatus.RUNNING.name(), status.getBody().getActivityStatus())
        ) {
            //LOG.info("******************* RUNNING ******************* ");
            schedulerService.updateReportProbe(id, false);
        }
        if (
                StringUtils.equals(ReportActivityStatus.FAILED.name(), status.getBody().getActivityStatus()) ||
                        StringUtils.equals(ReportActivityStatus.CANCELLED.name(), status.getBody().getActivityStatus())
        ) {
            //LOG.info("******************* STOPPED ******************* ");
            schedulerService.unScheduleReportProbe(id);
        }


    }
}
