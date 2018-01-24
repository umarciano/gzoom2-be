package it.mapsgroup.gzoom.rest;

import it.mapsgroup.gzoom.common.Exec;
import it.mapsgroup.gzoom.model.Result;
import it.mapsgroup.gzoom.model.TimeEntry;
import it.mapsgroup.gzoom.model.Timesheet;
import it.mapsgroup.gzoom.service.TimeEntryService;
import it.mapsgroup.gzoom.service.TimesheetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 */
@RestController
@RequestMapping(value = "", produces = { MediaType.APPLICATION_JSON_VALUE })
public class TimeEntryController {

    private final TimesheetService timesheetService;
    private final TimeEntryService timeEntryService;


    @Autowired
    public TimeEntryController(TimesheetService timesheetService, TimeEntryService timeEntryService) {
        this.timesheetService = timesheetService;
        this.timeEntryService = timeEntryService;
    }

    @RequestMapping(value = "timesheet/time-entry", method = RequestMethod.GET)
    @ResponseBody
    public Result<Timesheet> getTimesheets() {
        return Exec.exec("timesheet/timesheet get", () -> timesheetService.getTimesheets());
    }

    @RequestMapping(value = "timesheet/time-entry/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Result<TimeEntry> getTimeEntries(@PathVariable(value = "id") String id){
        return Exec.exec("timesheet/time-entry get", () -> timeEntryService.getTimeEntries(id));
    }

    @RequestMapping(value = "timesheet/time-entry-work-efforts", method = RequestMethod.GET)
    @ResponseBody
    public Result<TimeEntry> getWorkEfforts() {
        return Exec.exec("timesheet/time-entry-workefforts get", () -> timeEntryService.getWorkEfforts());
    }

    @RequestMapping(value = "timesheet/time-entry-create" , method = RequestMethod.POST)
    @ResponseBody
    public String createTimeEntry(@RequestBody TimeEntry req) {
        return Exec.exec( "timesheet/time-entry post", () -> timeEntryService.createTimeEntry(req));
    }

    @RequestMapping(value = "timesheet/time-entry-update/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public String updateTimeEntry(@PathVariable(value = "id") String id, @RequestBody TimeEntry req) {
        return Exec.exec("timesheet/time-entry put", () -> timeEntryService.updateTimeEntry(id, req));
    }

    @RequestMapping(value = "timesheet/time-entry-delete/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public String deleteTimeEntry(@PathVariable(value = "id") String id) {
        return Exec.exec("timesheet/time-entry delete", () -> timeEntryService.deleteTimeEntry(id));
    }

}
