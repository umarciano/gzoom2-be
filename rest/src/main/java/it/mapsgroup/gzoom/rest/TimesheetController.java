package it.mapsgroup.gzoom.rest;

import it.mapsgroup.gzoom.common.Exec;
import it.mapsgroup.gzoom.model.Result;
import it.mapsgroup.gzoom.querydsl.dto.Timesheet;
import it.mapsgroup.gzoom.querydsl.dto.Uom;
import it.mapsgroup.gzoom.querydsl.dto.UomEx;
import it.mapsgroup.gzoom.service.TimesheetService;
import it.mapsgroup.gzoom.service.UomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 */
@RestController
@RequestMapping(value = "", produces = { MediaType.APPLICATION_JSON_VALUE })
public class TimesheetController {

    private final TimesheetService timesheetService;

    @Autowired
    public TimesheetController(TimesheetService timesheetService) {
        this.timesheetService = timesheetService;
    }

    @RequestMapping(value = "timesheet/timesheet", method = RequestMethod.GET)
    @ResponseBody
    public Result<Timesheet> getTimesheets() {
        return Exec.exec("timesheet/timesheet get", () -> timesheetService.getTimesheets());
    }

    @RequestMapping(value = "timesheet/timesheet" , method = RequestMethod.POST)
    @ResponseBody
    public String createTimesheet(@RequestBody Timesheet req) {
        return Exec.exec( "timesheet/timesheet-create", () -> timesheetService.createTimesheet(req));
    }

}
