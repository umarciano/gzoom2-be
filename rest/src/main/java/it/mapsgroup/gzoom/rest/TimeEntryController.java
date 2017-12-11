package it.mapsgroup.gzoom.rest;

import it.mapsgroup.gzoom.common.Exec;
import it.mapsgroup.gzoom.model.Result;
import it.mapsgroup.gzoom.model.TimeEntry;
import it.mapsgroup.gzoom.model.Timesheet;
import it.mapsgroup.gzoom.querydsl.dto.TimeEntryEx;
import it.mapsgroup.gzoom.service.TimeEntryService;
import it.mapsgroup.gzoom.service.TimesheetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @RequestMapping(value = "timesheet/time-entry-workefforts", method = RequestMethod.GET)
    @ResponseBody
    public List<TimeEntryEx> getWorkEfforts() {
        return Exec.exec("timesheet/time-entry-workefforts get", () -> timeEntryService.getWorkEfforts());
    }

}
