package it.mapsgroup.gzoom.rest;

import it.mapsgroup.gzoom.common.Exec;
import it.mapsgroup.gzoom.model.Result;
import it.mapsgroup.gzoom.model.Timesheet;
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

    @Autowired
    public TimeEntryController(TimesheetService timesheetService) {
        this.timesheetService = timesheetService;
    }

    @RequestMapping(value = "timesheet/time-entry", method = RequestMethod.GET)
    @ResponseBody
    public Result<Timesheet> getTimesheets() {
        return Exec.exec("timesheet/timesheet get", () -> timesheetService.getTimesheets());
    }

}
