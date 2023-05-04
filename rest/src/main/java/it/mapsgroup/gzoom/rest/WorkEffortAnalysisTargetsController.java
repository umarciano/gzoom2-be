package it.mapsgroup.gzoom.rest;


import it.mapsgroup.gzoom.common.Exec;
import it.mapsgroup.gzoom.model.Result;
import it.mapsgroup.gzoom.mybatis.dto.DetailKPI;
import it.mapsgroup.gzoom.mybatis.dto.WorkEffortAnalysisTarget;
import it.mapsgroup.gzoom.querydsl.dto.WorkEffortAnalysisTypeTypeExt;
import it.mapsgroup.gzoom.querydsl.dto.WorkEffortMeasuresExtAcctgTransAndEntry;
import it.mapsgroup.gzoom.service.WorkEffortAnalysisService;
import it.mapsgroup.gzoom.service.analysis.WorkEffortAnalysisTargetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import static it.mapsgroup.gzoom.security.Principals.principal;

@RestController
@RequestMapping(value = "", produces = { MediaType.APPLICATION_JSON_VALUE })
public class WorkEffortAnalysisTargetsController {
    private final WorkEffortAnalysisService workEffortAnalysisService;

    private final WorkEffortAnalysisTargetService workEffortAnalysisTargetService;

    @Autowired
    public WorkEffortAnalysisTargetsController(WorkEffortAnalysisService workEffortAnalysisService, WorkEffortAnalysisTargetService workEffortAnalysisTargetService) {
        this.workEffortAnalysisService = workEffortAnalysisService;
        this.workEffortAnalysisTargetService = workEffortAnalysisTargetService;
    }

    @RequestMapping(value = "work-effort-analysis-targets-header/{analysisId}/{workEffortId}", method = RequestMethod.GET)
    @ResponseBody
    public Result<WorkEffortAnalysisTypeTypeExt> getWorkEffortAnalysisTargetHeader(@PathVariable(value = "analysisId") String analysisId, @PathVariable(value = "workEffortId") String workEffortId) {
        return Exec.exec("get work-effort-analysis with id", () -> workEffortAnalysisService.getWorkEffortAnalysesTargetHeader(analysisId, workEffortId));
    }

    @RequestMapping(value = "work-effort-analysis-targets/{context}/{analysisId}", method = RequestMethod.GET)
    @ResponseBody
    public Result<WorkEffortAnalysisTypeTypeExt> getWorkEffortAnalysisTargetSummary(@PathVariable(value = "context") String context,@PathVariable(value = "analysisId") String analysisId) {
        return Exec.exec("get work-effort-analysis with id", () -> workEffortAnalysisService.getWorkEffortAnalysisTargetSummary(context, analysisId, principal().getUserLoginId()));
    }

    @RequestMapping(value = "work-effort-analysis-targets/header/{analysisId}/{workEffortId}", method = RequestMethod.GET)
    @ResponseBody
    public Result<WorkEffortAnalysisTarget> getWorkEffortAnalysisTargetHeaderOne(@PathVariable(value = "analysisId") String analysisId, @PathVariable(value = "workEffortId") String workEffortId) {
        return Exec.exec("get work-effort-analysis with id analysis and id work effort", () -> workEffortAnalysisTargetService.getWorkEffortAnalysisTargetHeaderOne(analysisId, workEffortId));
    }

    @RequestMapping(value = "work-effort-analysis-targets/header-more/{context}/{analysisId}", method = RequestMethod.GET)
    @ResponseBody
    public Result<WorkEffortAnalysisTarget> getWorkEffortAnalysisTargetHeaderMore(@PathVariable(value = "context") String context, @PathVariable(value = "analysisId") String analysisId) {
        return Exec.exec("get work-effort-analysis header more", () -> workEffortAnalysisTargetService.getWorkEffortAnalysisTargetHeaderMore(context, analysisId, principal().getUserLoginId()));
    }

    @RequestMapping(value = "work-effort-analysis-targets/list/{context}/{analysisId}/{dateControl}", method = RequestMethod.GET)
    @ResponseBody
    public Result<WorkEffortAnalysisTarget> getWorkEffortAnalysisTargetList(@PathVariable(value = "context") String context, @PathVariable(value = "analysisId") String analysisId, @PathVariable(value = "dateControl") String dateControl) {
        return Exec.exec("get work-effort-analysis target list", () -> workEffortAnalysisTargetService.getWorkEffortAnalysisTargetList(context, analysisId, principal().getUserLoginId(), dateControl));
    }

    @RequestMapping(value = "work-effort-analysis-targets/list-with-work-effort/{analysisId}/{workEffortId}/{dateControl}", method = RequestMethod.GET)
    @ResponseBody
    public Result<WorkEffortAnalysisTarget> getWorkEffortAnalysisTargetListWithWE( @PathVariable(value = "analysisId") String analysisId, @PathVariable(value = "workEffortId") String workEffortId, @PathVariable(value = "dateControl") String dateControl) {
        return Exec.exec("get work-effort-analysis target list with work-effort-id", () -> workEffortAnalysisTargetService.getWorkEffortAnalysisTargetListWithWE(analysisId, workEffortId, dateControl));
    }

    @RequestMapping(value = "work-effort-analysis-targets/detailKPIScore/{analysisId}/{workEffortId}/{dateControl}", method = RequestMethod.GET)
    @ResponseBody
    public Result<DetailKPI> getDetailKPIScore(@PathVariable(value = "analysisId") String analysisId, @PathVariable(value = "workEffortId") String workEffortId, @PathVariable(value = "dateControl") String dateControl) {
        return Exec.exec("get detail-KPI-score", () -> workEffortAnalysisTargetService.getDetailKPIScore(analysisId, workEffortId, dateControl));
    }

    @RequestMapping(value = "work-effort-analysis-targets/detailKPIPeriod/{analysisId}/{workEffortId}/{dateControl}", method = RequestMethod.GET)
    @ResponseBody
    public Result<DetailKPI> getDetailKPIPeriod(@PathVariable(value = "analysisId") String analysisId, @PathVariable(value = "workEffortId") String workEffortId, @PathVariable(value = "dateControl") String dateControl) {
        return Exec.exec("get detail-KPI-period", () -> workEffortAnalysisTargetService.getDetailKPIPeriod(analysisId, workEffortId, dateControl));
    }



}
