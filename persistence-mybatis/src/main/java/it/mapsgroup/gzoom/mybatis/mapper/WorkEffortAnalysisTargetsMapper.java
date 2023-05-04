package it.mapsgroup.gzoom.mybatis.mapper;

import it.mapsgroup.gzoom.mybatis.dto.DetailKPI;
import it.mapsgroup.gzoom.mybatis.dto.WorkEffortAnalysisTarget;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface WorkEffortAnalysisTargetsMapper {

    List<WorkEffortAnalysisTarget> getWorkEffortAnalysisTargetHeaderOne(@Param("analysisId") String analysisId, @Param("workEffortId") String workEffortId);

    List<WorkEffortAnalysisTarget> getWorkEffortAnalysisTargetHeaderMore(@Param("analysisId") String analysisId, @Param("userLoginId") String userLoginId, @Param("permissionMGR_ADMIN") String permissionMGR_ADMIN, @Param("permissionORG_ADMIN") String permissionORG_ADMIN);

    List<WorkEffortAnalysisTarget> getWorkEffortAnalysisTargetList(@Param("analysisId") String analysisId, @Param("userLoginId") String userLoginId, @Param("dateControl") String dateControl, @Param("permissionMGR_ADMIN") String permissionMGR_ADMIN, @Param("permissionORG_ADMIN") String permissionORG_ADMIN);

    List<WorkEffortAnalysisTarget> getWorkEffortAnalysisTargetListWithWE(@Param("analysisId") String analysisId, @Param("workEffortId") String workEffortId, @Param("dateControl") String dateControl);

    List<DetailKPI> getDetailKPIScore(@Param("analysisId") String analysisId, @Param("workEffortId") String workEffortId, @Param("dateControl") String dateControl);

    List<DetailKPI> getDetailKPIPeriod (@Param("analysisId") String analysisId, @Param("workEffortId") String workEffortId, @Param("dateControl") String dateControl);

}
