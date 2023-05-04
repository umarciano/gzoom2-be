package it.mapsgroup.gzoom.rest;

import it.mapsgroup.gzoom.common.Exec;
import it.mapsgroup.gzoom.model.Result;
import it.mapsgroup.gzoom.model.TimeEntry;
import it.mapsgroup.gzoom.querydsl.dto.TimeEntryEx;
import it.mapsgroup.gzoom.querydsl.dto.TimesheetEx;
import it.mapsgroup.gzoom.querydsl.dto.WorkEffort;
import it.mapsgroup.gzoom.service.TimeEntryService;
import it.mapsgroup.gzoom.service.TimesheetService;
import it.mapsgroup.gzoom.service.UserPreferenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static it.mapsgroup.gzoom.security.Principals.principal;

/**
 */
@RestController
@RequestMapping(value = "time-entry", produces = { MediaType.APPLICATION_JSON_VALUE })
public class TimeEntryController {

    private final TimesheetService timesheetService;
    private final TimeEntryService timeEntryService;
    private final UserPreferenceService userPreferenceService;


    @Autowired
    public TimeEntryController(TimesheetService timesheetService, TimeEntryService timeEntryService, UserPreferenceService userPreferenceService) {
        this.timesheetService = timesheetService;
        this.timeEntryService = timeEntryService;
        this.userPreferenceService = userPreferenceService;
    }

    @RequestMapping(value = "/time-entry-timesheet/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Result<TimesheetEx> getTimesheet(@PathVariable(value = "id") String id) {
        return Exec.exec("timesheet get", () -> timeEntryService.getTimesheet(id,principal().getUserLoginId()));
    }

    @RequestMapping(value = "/time-entry/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Result<TimeEntryEx> getTimeEntries(@PathVariable(value = "id") String id){
        return Exec.exec("time-entry get", () -> timeEntryService.getTimeEntries(id));
    }

    @RequestMapping(value = "/time-entry-work-efforts/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Result<WorkEffort> getWorkEfforts(@PathVariable(value = "id") String id) {
        return Exec.exec("timesheet/time-entry-workefforts get", () -> timeEntryService.getWorkEfforts(id));
    }

    @RequestMapping(value = "/", method = RequestMethod.PUT)
    @ResponseBody
    public Boolean updateTimeEntry(@RequestBody List<TimeEntry> array) {
        return Exec.exec("time-entry put", () -> timeEntryService.updateTimeEntry(array));
    }

    @RequestMapping(value = "/{timeEntrys}", method = RequestMethod.DELETE)
    @ResponseBody
    public Boolean deleteTimeEntry(@PathVariable(value = "timeEntrys") String[] timeEntrys) {
        return Exec.exec("time-entry delete", () -> timeEntryService.deleteTimeEntry(timeEntrys));
    }

}
