package it.mapsgroup.gzoom.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.mapsgroup.gzoom.callback.ReportCallback;
import it.mapsgroup.gzoom.callback.ReportCallbackManager;
import it.mapsgroup.gzoom.dto.JsonTypeMap;
import it.mapsgroup.gzoom.report.querydsl.dao.ReportActivityDao;
import it.mapsgroup.report.querydsl.dto.ReportActivity;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author Andrea Fossi.
 */
@Service
public class ReportCallbackService {
    private static final Logger LOG = getLogger(ReportCallbackService.class);

    private final ReportCallbackManager callBackManager;
    private final ReportActivityDao reportActivityDao;
    private final ObjectMapper objectMapper;


    @Autowired
    public ReportCallbackService(ReportCallbackManager callBackManager,
                                 ReportActivityDao reportActivityDao,
                                 ObjectMapper objectMapper) {
        this.callBackManager = callBackManager;
        this.reportActivityDao = reportActivityDao;
        this.objectMapper = objectMapper;
    }

    public void reportDone(String reportActivityId) {
        ReportActivity reportActivity = reportActivityDao.get(reportActivityId);
        if (reportActivity.getCallbackType() != null) {
            Map<String, Object> params = Collections.emptyMap();
            if (StringUtils.isNotEmpty(reportActivity.getCallbackData())) {
                try {
                    JsonTypeMap<String, Object> jsonMap =
                            objectMapper.readValue(reportActivity.getCallbackData(), new TypeReference<JsonTypeMap<String, Object>>() {
                            });
                    params = jsonMap.get();
                } catch (IOException e) {
                    LOG.error("Cannot parse callback data. ReportActivity[{}]", reportActivity.getActivityId());
                    return;
                }
            }
            ReportCallback reportCallback = this.callBackManager.get(reportActivity.getCallbackType());
            if (reportCallback == null) {
                LOG.error("Callback not found. ReportActivity[{}], Callback[{}]", reportActivity.getActivityId(), reportActivity.getCallbackType());
                return;
            }
            reportCallback.run(params);
        }
    }

}
