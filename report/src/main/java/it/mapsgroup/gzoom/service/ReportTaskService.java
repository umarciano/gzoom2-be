package it.mapsgroup.gzoom.service;

import it.mapsgroup.gzoom.persistence.common.dto.enumeration.ReportActivityStatus;
import it.mapsgroup.gzoom.report.querydsl.dao.ReportActivityDao;
import it.mapsgroup.report.querydsl.dto.ReportActivity;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;

import java.util.Random;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author Andrea Fossi.
 */
@Service
public class ReportTaskService {
    private static final Logger LOG = getLogger(ReportTaskService.class);


    private final TaskExecutor taskExecutor;
    private final ReportActivityDao reportDao;

    public ReportTaskService(@Qualifier("reportTaskExecutor") TaskExecutor taskExecutor,
                             ReportActivityDao reportDao) {
        this.taskExecutor = taskExecutor;
        this.reportDao = reportDao;
    }



    public void add(ReportTask reportTask) {
        taskExecutor.execute(() -> {
            LOG.info("Executing {}", reportTask.getId());

            ReportActivity record = reportDao.get(reportTask.getId());
            Boolean inCharge = reportDao.updateState(record.getActivityId(),
                    ReportActivityStatus.QUEUED,
                    ReportActivityStatus.RUNNING);
            if (inCharge) {
                try {
                    //execution time from 3 to 5 seconds
                    Thread.sleep(new Random().nextInt(3000) + 2000);
                } catch (InterruptedException e) {
                    LOG.error("Error", e);
                }
                reportDao.updateState(record.getActivityId(),
                        ReportActivityStatus.RUNNING,
                        ReportActivityStatus.DONE);
                LOG.info("Done {}", reportTask.getId());
            } else {
                LOG.info("Skipped {}", reportTask.getId());
            }
        });
    }

}
