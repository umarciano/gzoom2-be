package it.mapsgroup.gzoom.service;

import com.querydsl.core.Tuple;
import it.mapsgroup.gzoom.model.Result;

import it.mapsgroup.gzoom.querydsl.dao.WorkEffortAnalysisDao;
import it.mapsgroup.gzoom.querydsl.dto.*;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

@Service
public class WorkEffortAnalysisService {
    private static final Logger LOG = getLogger(WorkEffortAnalysisService.class);

    private final WorkEffortAnalysisDao workEffortAnalysisDao;

    @Autowired
    public WorkEffortAnalysisService(WorkEffortAnalysisDao workEffortAnalysisDao) {
        this.workEffortAnalysisDao = workEffortAnalysisDao;
    }

    public WorkEffortAnalysis getWorkEffortAnalysis(String workEffortAnalysisId) {
        return workEffortAnalysisDao.getWorkEffortAnalysis(workEffortAnalysisId);
    }

    public Result<WorkEffortAnalysis> getWorkEffortAnalysesWithContext(String context, String userLoginId) {
        List<WorkEffortAnalysis> list = workEffortAnalysisDao.getWorkEffortAnalysesWithContext(context, userLoginId);
        return new Result<>(list, list.size());
    }

    public Result<WorkEffortAnalysisTypeTypeExt> getWorkEffortAnalysesTargetHeader(String analysisId, String workEffortId) {
        List<WorkEffortAnalysisTypeTypeExt> row = workEffortAnalysisDao.getWorkEffortAnalysisTargetHeader(analysisId, workEffortId);
        return new Result<>(row, row.size());
    }

    public Result<WorkEffortAnalysisTypeTypeExt> getWorkEffortAnalysisTargetSummary(String context, String analysisId, String userLoginId) {
        List<WorkEffortAnalysisTypeTypeExt> list = workEffortAnalysisDao.getWorkEffortAnalysisTargetSummary(context, analysisId, userLoginId);
        return new Result<>(list, list.size());
    }


}
