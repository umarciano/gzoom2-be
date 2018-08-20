package it.mapsgroup.gzoom.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.mapsgroup.gzoom.persistence.common.dto.enumeration.ReportActivityStatus;
import it.mapsgroup.gzoom.report.dto.CreateReport;
import it.mapsgroup.gzoom.report.querydsl.dao.ReportActivityDao;
import it.mapsgroup.gzoom.rest.ValidationException;
import it.mapsgroup.report.querydsl.dto.ReportActivity;
import org.apache.commons.lang3.LocaleUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Locale;

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
    public ReportActivity save(CreateReport report) {
        ReportActivity record = new ReportActivity();

        Locale locale = null;
        try {
            if (StringUtils.isNotEmpty(report.getReportLocale()))
                locale = LocaleUtils.toLocale(report.getReportLocale());
        } catch (Exception e) {
            LOG.error("Cannot parse locale", e);
        }
        Validators.assertNotNull(locale, "Locale cannot be null");

        record.setReportData("Primo ReportActivity " + System.currentTimeMillis());
        record.setStatus(ReportActivityStatus.QUEUED);
        record.setTemplateName(report.getReportName());
        record.setReportName(report.getReportName());
        record.setReportLocale(report.getReportLocale());
        try {
            if (report.getParams() != null)
                record.setReportData(objectMapper.writeValueAsString(report.getParams()));
            else
                record.setReportData(objectMapper.writeValueAsString(new HashMap<Object, String>()));
        } catch (JsonProcessingException e) {
            throw new ValidationException("Cannot serialize params");
        }

        record.setCompletedStamp(LocalDateTime.now());
        reportDao.create(record);
        return record;
    }

    public String cancel(String id, String reason) {
        return taskService.cancel(id, reason) + "";
    }

    public String getStatus(String id) {
        return taskService.getStatus(id);
    }
}
