package it.mapsgroup.gzoom.rest;

import it.mapsgroup.gzoom.common.Exec;
import it.mapsgroup.gzoom.model.Result;
import it.mapsgroup.gzoom.model.Timesheet;
import it.mapsgroup.gzoom.querydsl.dto.TimesheetEx;
import it.mapsgroup.gzoom.querydsl.dto.UserPreference;
import it.mapsgroup.gzoom.querydsl.dto.WorkEffortTypeContentExt;
import it.mapsgroup.gzoom.service.TimesheetService;
import it.mapsgroup.gzoom.service.UserPreferenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import static it.mapsgroup.gzoom.security.Principals.principal;

/**
 */
@RestController
@RequestMapping(value = "timesheet", produces = { MediaType.APPLICATION_JSON_VALUE })
public class TimesheetController {

    private final TimesheetService timesheetService;
    private final UserPreferenceService userPreferenceService;

    @Autowired
    public TimesheetController(TimesheetService timesheetService, UserPreferenceService userPreferenceService) {
        this.timesheetService = timesheetService;
        this. userPreferenceService = userPreferenceService;
    }

    @RequestMapping(value = "/{context}", method = RequestMethod.GET)
    @ResponseBody
    public Result<TimesheetEx> getTimesheet(@PathVariable(value = "context")  String context) {
        UserPreference organization = userPreferenceService.getUserPreference("ORGANIZATION_PARTY");
        return Exec.exec("timesheet get", () -> timesheetService.getTimesheet(principal().getUserLoginId(),context, organization.getUserPrefValue()));
    }

    @RequestMapping(value = "/params/{context}", method = RequestMethod.GET)
    @ResponseBody
    public Result<WorkEffortTypeContentExt> getParamsTimesheet(@PathVariable(value = "context")  String context) {
        UserPreference organization = userPreferenceService.getUserPreference("ORGANIZATION_PARTY");
        return Exec.exec("timesheet-params get", () -> timesheetService.getParamsTimesheet(principal().getUserLoginId(),context, organization.getUserPrefValue()));
    }

    @RequestMapping(value = "/" , method = RequestMethod.POST)
    @ResponseBody
    public String createTimesheet(@RequestBody Timesheet req) {
        return Exec.exec( "timesheet-post", () -> timesheetService.createTimesheet(req));
    }

    @RequestMapping(value = "/", method = RequestMethod.DELETE)
    @ResponseBody
    public boolean deleteTimesheet(@PathVariable(value = "timesheets") String[] timesheets) {
        return Exec.exec("timesheet delete", () -> timesheetService.deleteTimesheet(timesheets));
    }

}
