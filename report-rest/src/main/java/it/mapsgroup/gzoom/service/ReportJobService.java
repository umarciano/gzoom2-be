package it.mapsgroup.gzoom.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.mapsgroup.gzoom.persistence.common.dto.enumeration.ReportActivityStatus;
import it.mapsgroup.gzoom.report.dto.CreateReport;
import it.mapsgroup.gzoom.report.querydsl.dao.ReportActivityDao;
import it.mapsgroup.gzoom.rest.ValidationException;
import it.mapsgroup.report.querydsl.dto.ReportActivity;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Profile service.
 */
@Service
public class ReportJobService {
    private static final Logger LOG = getLogger(ReportJobService.class);

    private final ReportActivityDao reportDao;
    private final ObjectMapper objectMapper;
    private final ReportTaskService taskService;

    @Autowired
    public ReportJobService(ReportActivityDao reportDao, ObjectMapper objectMapper, ReportTaskService taskService) {
        this.reportDao = reportDao;
        this.objectMapper = objectMapper;
        this.taskService = taskService;
    }

    public String add(CreateReport report) {
        ReportActivity record = save(report);
        taskService.add(new ReportTask(record.getActivityId()));
        return record.getActivityId();
    }

    @Transactional
    private ReportActivity save(CreateReport report) {
        ReportActivity record = new ReportActivity();
        record.setReportData("Primo ReportActivity " + System.currentTimeMillis());
        record.setStatus(ReportActivityStatus.QUEUED);
        record.setTemplateName(report.getReportName());
        record.setReportName(report.getReportName());
        try {
            if (report.getParams() != null)
                record.setReportData(objectMapper.writeValueAsString(report.getParams()));
            else
                record.setReportData(objectMapper.writeValueAsString(new HashMap<Object, String>()));
        } catch (JsonProcessingException e) {
            throw new ValidationException("Canno serialize params");
        }

        record.setCompletedStamp(LocalDateTime.now());
        reportDao.create(record);
        return record;
    }
}
