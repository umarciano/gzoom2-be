package it.mapsgroup.gzoom.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.mapsgroup.gzoom.birt.BirtService;
import it.mapsgroup.gzoom.persistence.common.dto.enumeration.ReportActivityStatus;
import it.mapsgroup.gzoom.report.querydsl.dao.ReportActivityDao;
import it.mapsgroup.gzoom.report.querydsl.dao.ReportActvityFilter;
import it.mapsgroup.report.querydsl.dto.ReportActivity;
import org.apache.commons.lang3.LocaleUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author Andrea Fossi.
 */
@Service
public class ReportTaskService {
    private static final Logger LOG = getLogger(ReportTaskService.class);


    private final TaskExecutor taskExecutor;
    private final ReportActivityDao reportDao;
    private final BirtService birtService;
    private final ObjectMapper objectMapper;

    public ReportTaskService(@Qualifier("reportTaskExecutor") TaskExecutor taskExecutor,
                             ReportActivityDao reportDao,
                             BirtService birtService) {
        this.taskExecutor = taskExecutor;
        this.reportDao = reportDao;
        this.birtService = birtService;
        this.objectMapper = new ObjectMapper();
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
                    Map<String, Object> params =
                            objectMapper.readValue(record.getReportData(), new TypeReference<Map<String, Object>>() {
                            });
                    Locale locale=null;
                    try {
                        if (StringUtils.isNotEmpty(record.getReportLocale()))
                            locale = LocaleUtils.toLocale(record.getReportLocale());
                    } catch (Exception e) {
                        LOG.error("Cannot parse locale", e);
                    }
                    if(locale==null){
                        LOG.warn("invalid report locale, setting default");
                        locale=Locale.ITALIAN;
                    }
                    birtService.build(record.getActivityId(),
                            record.getReportName(),
                            params,
                            "report_" + record.getActivityId(),
                            locale);
                } catch (Exception e) {
                    LOG.error("Cannot generate report", e);
                    reportDao.updateState(record.getActivityId(),
                            ReportActivityStatus.RUNNING,
                            ReportActivityStatus.FAILED,
                            e.toString());
                    return;
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

    @Transactional
    public void resume() {
        long resumed = reportDao.resumeRunning();
        LOG.info("Resumed {} reports", resumed);

        ReportActvityFilter filter = new ReportActvityFilter();
        filter.getStates().add(ReportActivityStatus.QUEUED);
        List<ReportActivity> actvities = reportDao.getActvities(filter);
        actvities.forEach(a -> add(new ReportTask(a.getActivityId())));
        LOG.info("Added to queue {} reports", actvities.size());
    }
}
