package it.mapsgroup.gzoom.mybatis.dao;

import it.mapsgroup.gzoom.mybatis.dto.DetailKPI;
import it.mapsgroup.gzoom.mybatis.dto.WorkEffortAnalysisTarget;
import it.mapsgroup.gzoom.mybatis.mapper.WorkEffortAnalysisTargetsMapper;
import it.mapsgroup.gzoom.mybatis.service.FilterService;
import it.mapsgroup.gzoom.querydsl.util.ContextPermissionPrefixEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class WorkEffortAnalysisTargetDao extends AbstractDao{

    private final WorkEffortAnalysisTargetsMapper workEffortAnalysisTargetsMapper;

    @Autowired
    public WorkEffortAnalysisTargetDao(WorkEffortAnalysisTargetsMapper workEffortAnalysisTargetsMapper) {
        this.workEffortAnalysisTargetsMapper = workEffortAnalysisTargetsMapper;

    }

    /**
     * This function gets the data for header.
     *
     * @param analysisId Analysis id.
     * @param workEffortId Work effort id.
     * @return List of WorkEffortAnalysisTarget
     */
    public List<WorkEffortAnalysisTarget> getWorkEffortAnalysisTargetHeaderOne(String analysisId, String workEffortId) {
        return workEffortAnalysisTargetsMapper.getWorkEffortAnalysisTargetHeaderOne(analysisId, workEffortId);
    }

    /**
     * This function gets the data for header.
     *
     * @param context Context.
     * @param analysisId Analysis id.
     * @param userLoginId User login id.
     * @return List of WorkEffortAnalysisTarget.
     */
    public List<WorkEffortAnalysisTarget> getWorkEffortAnalysisTargetHeaderMore(String context, String analysisId, String userLoginId) {
        String permission = ContextPermissionPrefixEnum.getPermissionPrefix(context);
        String permissionMGR_ADMIN = permission + "MGR_ADMIN";
        String permissionORG_ADMIN = permission + "ORG_ADMIN";
        return workEffortAnalysisTargetsMapper.getWorkEffortAnalysisTargetHeaderMore(analysisId, userLoginId, permissionMGR_ADMIN, permissionORG_ADMIN);
    }

    /**
     * This function gets list of work efforts associated with the analysis and the context.
     *
     * @param context Context.
     * @param analysisId Analysis id.
     * @param userLoginId User login id.
     * @param dateControl dateControl from comments.
     * @return List of WorkEffortAnalysisTarget.
     */
    public List<WorkEffortAnalysisTarget> getWorkEffortAnalysisTargetList(String context, String analysisId, String userLoginId, String dateControl) {
        String permission = ContextPermissionPrefixEnum.getPermissionPrefix(context);
        String permissionMGR_ADMIN = permission + "MGR_ADMIN";
        String permissionORG_ADMIN = permission + "ORG_ADMIN";
        return workEffortAnalysisTargetsMapper.getWorkEffortAnalysisTargetList(analysisId, userLoginId, dateControl, permissionMGR_ADMIN, permissionORG_ADMIN);
    }

    /**
     * This function gets list of work efforts associated with the analysis and the work effort id.
     *
     * @param analysisId Analysis id.
     * @param workEffortId Work effort id.
     * @param dateControl dateControl from comments.
     * @return List of WorkEffortAnalysisTarget.
     */
    public List<WorkEffortAnalysisTarget> getWorkEffortAnalysisTargetListWithWE(String analysisId, String workEffortId, String dateControl) {
        return workEffortAnalysisTargetsMapper.getWorkEffortAnalysisTargetListWithWE(analysisId, workEffortId, dateControl);
    }

    /**
     * This function gets the indicators when detailKPI from params equals SCORE.
     *
     * @param analysisId Analysis id.
     * @param workEffortId Work effort id.
     * @param dateControl dateControl from comments.
     * @return List of DetailKPI.
     */
    public List<DetailKPI> getDetailKPIScore(String analysisId, String workEffortId, String dateControl) {
        return workEffortAnalysisTargetsMapper.getDetailKPIScore(analysisId, workEffortId, dateControl);
    }

    /**
     * This function gets the indicators when detailKPI from params equals PERIOD.
     *
     * @param analysisId Analysis id.
     * @param workEffortId Work effort id.
     * @param dateControl dateControl from comments.
     * @return List of DetailKPI.
     */
    public List<DetailKPI> getDetailKPIPeriod(String analysisId, String workEffortId, String dateControl) {
        return workEffortAnalysisTargetsMapper.getDetailKPIPeriod(analysisId, workEffortId, dateControl);
    }
}
