package it.mapsgroup.gzoom.rest;

import static it.mapsgroup.gzoom.security.Principals.principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import it.mapsgroup.gzoom.common.Exec;
import it.mapsgroup.gzoom.model.Result;
import it.mapsgroup.gzoom.querydsl.dto.WorkEffort;
import it.mapsgroup.gzoom.service.WorkEffortService;

@RestController
@RequestMapping(value = "", produces = { MediaType.APPLICATION_JSON_VALUE })
public class WorkEffortController {
	private final WorkEffortService workEffortService;

	@Autowired
	public WorkEffortController(WorkEffortService workEffortService) {
		this.workEffortService = workEffortService;
	}

	@RequestMapping(value = "work-effort/{parentTypeId}/{workEffortTypeId}/{useFilter}", method = RequestMethod.GET)
	@ResponseBody
	public Result<WorkEffort> getWorkEfforts(@PathVariable(value = "parentTypeId") String parentTypeId, @PathVariable(value = "workEffortTypeId") String workEffortTypeId, @PathVariable(value = "useFilter") boolean useFilter) {
		return Exec.exec("workEffortTypeId get", () -> workEffortService.getWorkEfforts(principal().getUserLoginId(), parentTypeId, workEffortTypeId, useFilter));
	}
	
	@RequestMapping(value = "work-effort", method = RequestMethod.GET)
	@ResponseBody
	public Result<WorkEffort> getWorkEfforts() {
		return Exec.exec("workEfforts get", () -> workEffortService.getWorkEfforts());
	}
	
	
	@RequestMapping(value = "work-effort/work-effort-parent/{workEffortParentId}", method = RequestMethod.GET)
	@ResponseBody
	public Result<WorkEffort> getWorkEffortParents(@PathVariable(value = "workEffortParentId") String workEffortParentId) {
		return Exec.exec("workEffortParents get", () -> workEffortService.getWorkEffortParents(workEffortParentId));
	}

}
