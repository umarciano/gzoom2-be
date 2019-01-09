package it.mapsgroup.gzoom.rest;

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

	@RequestMapping(value = "work-effort/{workEffortTypeId}", method = RequestMethod.GET)
	@ResponseBody
	public Result<WorkEffort> getWorkEfforts(@PathVariable(value = "workEffortTypeId") String workEffortTypeId) {
		return Exec.exec("statusItem get", () -> workEffortService.getWorkEfforts(workEffortTypeId));
	}
	
	@RequestMapping(value = "work-effort", method = RequestMethod.GET)
	@ResponseBody
	public Result<WorkEffort> getWorkEfforts() {
		return Exec.exec("statusItem get", () -> workEffortService.getWorkEfforts());
	}
}
