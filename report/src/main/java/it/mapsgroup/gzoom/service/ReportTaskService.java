package it.mapsgroup.gzoom.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.mapsgroup.gzoom.birt.BirtService;
import it.mapsgroup.gzoom.birt.BirtServiceProgress;
import it.mapsgroup.gzoom.birt.Report;
import it.mapsgroup.gzoom.dto.JsonTypeMap;
import it.mapsgroup.gzoom.dto.ReportStatus;
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
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

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

    private final ConcurrentHashMap<String, ReportTask> tasks;


    public ReportTaskService(@Qualifier("reportTaskExecutor") TaskExecutor taskExecutor,
                             ReportActivityDao reportDao,
                             BirtService birtService) {
        this.taskExecutor = taskExecutor;
        this.reportDao = reportDao;
        this.birtService = birtService;
        this.objectMapper = new ObjectMapper();
        this.tasks = new ConcurrentHashMap<>();
    }


    public void add(ReportTask reportTask) {
        tasks.put(reportTask.getId(), reportTask);
        taskExecutor.execute(() -> {
            LOG.info("Executing {}", reportTask.getId());

            ReportActivity record = reportDao.get(reportTask.getId());
            Boolean inCharge = reportDao.updateState(record.getActivityId(),
                    ReportActivityStatus.QUEUED,
                    ReportActivityStatus.RUNNING);
            if (inCharge) {
                try {
                    JsonTypeMap<String, Object> params =
                            objectMapper.readValue(record.getReportData(), new TypeReference<JsonTypeMap<String, Object>>() {
                            });
                    Locale locale = null;
                    try {
                        if (StringUtils.isNotEmpty(record.getReportLocale()))
                            locale = LocaleUtils.toLocale(record.getReportLocale());
                    } catch (Exception e) {
                        LOG.error("Cannot parse locale", e);
                    }
                    if (locale == null) {
                        LOG.warn("invalid report locale, setting default");
                        locale = Locale.ITALIAN;
                    }
                    Report report = birtService.build(record.getActivityId(),
                            record.getReportName(),
                            params.get(),
                            locale);
                    reportTask.setReport(report);
                    birtService.run("report_" + record.getActivityId(), report);
                    reportDao.updateState(record.getActivityId(),
                            ReportActivityStatus.RUNNING,
                            ReportActivityStatus.DONE);
                    LOG.info("Done {}", reportTask.getId());
                } catch (Exception e) {
                    LOG.error("Failed: cannot generate report", e);
                    reportDao.updateState(record.getActivityId(),
                            ReportActivityStatus.RUNNING,
                            ReportActivityStatus.FAILED,
                            e.toString());
                } finally {
                    tasks.remove(reportTask.getId());
                }
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

    public boolean cancel(String id, String reason) {
        Optional<BirtServiceProgress> reportProgress = getReportProgress(id);
        reportProgress.ifPresent(ie -> {
            ie.cancel(reason);
            reportDao.updateState(id, ReportActivityStatus.RUNNING, ReportActivityStatus.CANCELLED, reason);
        });
        return reportProgress.isPresent();
    }

    public ReportStatus getStatus(String id) {
        Optional<BirtServiceProgress> iEngineTask = getReportProgress(id);
        ReportStatus reportStatus = iEngineTask.map(BirtServiceProgress::getStatus).orElse(new ReportStatus());
        ReportActivity reportActivity = reportDao.get(id);
        if (reportActivity != null) {
            reportStatus.setActivityStatus(reportActivity.getStatus());
        }
        return reportStatus;
    }


    private Optional<BirtServiceProgress> getReportProgress(String id) {
        if (tasks.get(id) != null
                && tasks.get(id).getReport() != null)
            return Optional.of(tasks.get(id).getReport().getBirtServiceProgress());
        else return Optional.empty();
    }
}
