package it.mapsgroup.gzoom.service.analysis;

import it.mapsgroup.gzoom.mybatis.dao.WorkEffortAnalysisTargetDao;
import it.mapsgroup.gzoom.mybatis.dto.DetailKPI;
import it.mapsgroup.gzoom.mybatis.dto.WorkEffortAnalysisTarget;
import it.mapsgroup.gzoom.querydsl.dto.PartyContactMech;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import it.mapsgroup.gzoom.model.Result;

import java.util.List;

@Service
public class WorkEffortAnalysisTargetService {
    private WorkEffortAnalysisTargetDao workEffortAnalysisTargetDao;

    @Autowired
    public WorkEffortAnalysisTargetService(WorkEffortAnalysisTargetDao workEffortAnalysisTargetDao) {
        this.workEffortAnalysisTargetDao = workEffortAnalysisTargetDao;
    }


    public Result<WorkEffortAnalysisTarget> getWorkEffortAnalysisTargetHeaderOne(String analysisId, String workEffortId) {
        List<WorkEffortAnalysisTarget> list = workEffortAnalysisTargetDao.getWorkEffortAnalysisTargetHeaderOne(analysisId, workEffortId);
        return  new Result<>(list, list.size());
    }

    public Result<WorkEffortAnalysisTarget> getWorkEffortAnalysisTargetHeaderMore(String context, String analysisId, String userLoginId) {
        List<WorkEffortAnalysisTarget> list = workEffortAnalysisTargetDao.getWorkEffortAnalysisTargetHeaderMore(context, analysisId, userLoginId);
        return  new Result<>(list, list.size());
    }

    public Result<WorkEffortAnalysisTarget> getWorkEffortAnalysisTargetList(String context, String analysisId, String userLoginId, String dateControl) {
        List<WorkEffortAnalysisTarget> list = workEffortAnalysisTargetDao.getWorkEffortAnalysisTargetList(context, analysisId, userLoginId, dateControl);
        return  new Result<>(list, list.size());
    }

    public Result<WorkEffortAnalysisTarget> getWorkEffortAnalysisTargetListWithWE(String analysisId, String workEffortId, String dateControl) {
        List<WorkEffortAnalysisTarget> list = workEffortAnalysisTargetDao.getWorkEffortAnalysisTargetListWithWE( analysisId, workEffortId, dateControl);
        return  new Result<>(list, list.size());
    }

    public Result<DetailKPI> getDetailKPIScore(String analysisId, String workEffortId, String dateControl) {
        List<DetailKPI> list = workEffortAnalysisTargetDao.getDetailKPIScore( analysisId, workEffortId, dateControl);
        return  new Result<>(list, list.size());
    }

    public Result<DetailKPI> getDetailKPIPeriod(String analysisId, String workEffortId, String dateControl) {
        List<DetailKPI> list = workEffortAnalysisTargetDao.getDetailKPIPeriod( analysisId, workEffortId, dateControl);
        return  new Result<>(list, list.size());
    }

}
