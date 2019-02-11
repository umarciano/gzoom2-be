package it.mapsgroup.gzoom.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.mapsgroup.gzoom.birt.BirtService;
import it.mapsgroup.gzoom.birt.BirtServiceProgress;
import it.mapsgroup.gzoom.dto.ReportStatus;
import it.mapsgroup.gzoom.persistence.common.dto.enumeration.ReportActivityStatus;
import it.mapsgroup.gzoom.querydsl.dao.ContentDao;
import it.mapsgroup.gzoom.querydsl.dao.DataResourceDao;
import it.mapsgroup.gzoom.querydsl.dto.Content;
import it.mapsgroup.gzoom.querydsl.dto.DataResource;
import it.mapsgroup.gzoom.report.querydsl.dao.ReportActivityDao;
import it.mapsgroup.gzoom.report.querydsl.dao.ReportActvityFilter;
import it.mapsgroup.report.querydsl.dto.ReportActivity;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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
    private final ContentDao contentDao;
    private final DataResourceDao dataResourceDao;
    private final ReportTaskDtoMapper dtoMapper;

    private final ReportCallbackService callbackService;

    private final ConcurrentHashMap<String, ReportTaskInfo> tasks;


    public ReportTaskService(@Qualifier("reportTaskExecutor") TaskExecutor taskExecutor,
                             ReportActivityDao reportDao,
                             BirtService birtService, ContentDao contentDao,
                             DataResourceDao dataResourceDao, ReportTaskDtoMapper dtoMapper,
                             ReportCallbackService callbackService) {
        this.taskExecutor = taskExecutor;
        this.reportDao = reportDao;
        this.birtService = birtService;
        this.contentDao = contentDao;
        this.dataResourceDao = dataResourceDao;
        this.dtoMapper = dtoMapper;
        this.callbackService = callbackService;
        this.objectMapper = new ObjectMapper();
        this.tasks = new ConcurrentHashMap<>();
    }


    /**
     * @param reportTask
     */
    public void addToQueue(ReportTaskInfo reportTask) {
        tasks.put(reportTask.getId(), reportTask);
        taskExecutor.execute(new ReportRunnableTask(reportTask, reportDao, birtService, objectMapper, reportTaskInfo -> {
            tasks.remove(reportTaskInfo.getId());
            createOfbizRecords(reportTask.getId());
            callbackService.reportDone(reportTask.getId());
        }));

    }

    @Transactional
    protected void createOfbizRecords(String reportTaskId) {
        ReportActivity record = reportDao.get(reportTaskId);
        if (record.getStatus() == ReportActivityStatus.DONE) {
            DataResource dataResource = dtoMapper.getDataResource(record);
            dataResourceDao.create(dataResource);
            Content content = dtoMapper.getContent(record, dataResource.getDataResourceId());
            contentDao.create(content);
        }
        LOG.debug("Ofbiz records created");
    }


    @Transactional
    public void resume() {
        long resumed = reportDao.resumeRunning();
        LOG.info("Resumed {} reports", resumed);

        ReportActvityFilter filter = new ReportActvityFilter();
        filter.getStates().add(ReportActivityStatus.QUEUED);
        List<ReportActivity> actvities = reportDao.getActvities(filter);
        actvities.forEach(a -> addToQueue(new ReportTaskInfo(a.getActivityId())));
        LOG.info("Added to queue {} reports", actvities.size());
    }

    public boolean cancel(String id, String reason) {
        Optional<BirtServiceProgress> reportProgress = getReportProgress(id);
        reportProgress.ifPresent(ie -> {
            ie.cancel(reason);
            reportDao.updateState(id, ReportActivityStatus.RUNNING, ReportActivityStatus.CANCELLED, reason, null);
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
