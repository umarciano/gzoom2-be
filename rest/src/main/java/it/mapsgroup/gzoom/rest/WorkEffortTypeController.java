package it.mapsgroup.gzoom.rest;

import it.mapsgroup.gzoom.common.Exec;
import it.mapsgroup.gzoom.model.Result;
import it.mapsgroup.gzoom.querydsl.dto.WorkEffortType;
import it.mapsgroup.gzoom.service.WorkEffortTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "", produces = { MediaType.APPLICATION_JSON_VALUE })
public class WorkEffortTypeController {

    private final WorkEffortTypeService workEffortTypeService;

    @Autowired
    public WorkEffortTypeController(WorkEffortTypeService workEffortTypeService) {
        this.workEffortTypeService = workEffortTypeService;
    }

    @RequestMapping(value = "work-effort-type/{workEffortType}", method = RequestMethod.GET)
    @ResponseBody
    public WorkEffortType getWorkEffortType(@PathVariable(value = "workEffortType") String workEffortTypeId) {
        return Exec.exec("workEffortType get", () -> workEffortTypeService.getWorkEffortType(workEffortTypeId));
    }

    @RequestMapping(value = "work-effort-type/like/{workEffortType}", method = RequestMethod.GET)
    @ResponseBody
    public Result<WorkEffortType> getWorkEffortTypes(@PathVariable(value = "workEffortType") String workEffortTypeId) {
        return Exec.exec("workEffortType get", () -> workEffortTypeService.getWorkEffortTypes(workEffortTypeId));
    }
}
