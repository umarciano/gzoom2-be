package it.mapsgroup.gzoom.rest;


import it.mapsgroup.gzoom.common.Exec;
import it.mapsgroup.gzoom.model.Result;
import it.mapsgroup.gzoom.querydsl.dto.WorkEffortAnalysis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import it.mapsgroup.gzoom.service.WorkEffortAnalysisService;
import static it.mapsgroup.gzoom.security.Principals.principal;

@RestController
@RequestMapping(value = "", produces = { MediaType.APPLICATION_JSON_VALUE })
public class WorkEffortAnalysisController {

    private final WorkEffortAnalysisService workEffortAnalysisService;

    @Autowired
    public WorkEffortAnalysisController(WorkEffortAnalysisService workEffortAnalysisService) {
        this.workEffortAnalysisService = workEffortAnalysisService;
    }

    @RequestMapping(value = "work-effort-analysis-id/{analysisId}", method = RequestMethod.GET)
    @ResponseBody
    public WorkEffortAnalysis getWorkEffortAnalysis(@PathVariable(value = "analysisId") String analysisId) {
        return Exec.exec("get work-effort-analysis with id", () -> workEffortAnalysisService.getWorkEffortAnalysis(analysisId));
    }

    @RequestMapping(value = "work-effort-analysis/{context}", method = RequestMethod.GET)
    @ResponseBody
    public Result<WorkEffortAnalysis> getWorkEffortAnalysisWithContext(@PathVariable(value = "context") String context) {
        return Exec.exec("get work-effort-analysis with id", () -> workEffortAnalysisService.getWorkEffortAnalysesWithContext(context, principal().getUserLoginId()));
    }
}
