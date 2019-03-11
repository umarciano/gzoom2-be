package it.mapsgroup.gzoom.rest;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import it.mapsgroup.gzoom.common.Exec;
import it.mapsgroup.gzoom.model.Report;
import it.mapsgroup.gzoom.model.Result;
import it.mapsgroup.gzoom.service.reminder.ReminderService;
import it.mapsgroup.gzoom.service.report.ReportAddService;
import it.mapsgroup.gzoom.service.report.ReportService;

/**
 */
@RestController
@RequestMapping(value = "", produces = { MediaType.APPLICATION_JSON_VALUE })
public class ReportController {

    private final ReportService reportService;
    private final ReportAddService reportAddService;
    private final ReminderService reminderService;

    @Autowired
    public ReportController(ReportService reportService, ReportAddService reportAddService, ReminderService reminderService) {
        this.reportService = reportService;
        this.reportAddService = reportAddService;
        this.reminderService = reminderService;
    }

    @RequestMapping(value = "report/{parentTypeId}", method = RequestMethod.GET)
    @ResponseBody
    public Result<Report> getReports(@PathVariable(value = "parentTypeId") String parentTypeId) {
        return Exec.exec("report get", () -> reportService.getReports(parentTypeId));
    }

    @RequestMapping(value = "report/{parentTypeId}/{reportContentId}/{reportName}/{analysis}", method = RequestMethod.GET)
    @ResponseBody
    public Report getReport(@PathVariable(value = "parentTypeId") String parentTypeId, 
    		@PathVariable(value = "reportContentId") String reportContentId, 
    		@PathVariable(value = "reportName") String reportName,
    		@PathVariable(value = "analysis") boolean analysis) {
        return Exec.exec("report get", () -> reportService.getReport(parentTypeId, reportContentId, reportName, analysis));
    }
    
    @RequestMapping(value = "report/add", method = RequestMethod.POST)
    @ResponseBody
    public String createReport(@RequestBody Report req) {
        return Exec.exec("report/add post", () -> reportAddService.add(req));
    }
    
    @RequestMapping(value = "report/mail", method = RequestMethod.POST)
    @ResponseBody
    public String sendmail(@RequestBody Report req) {
        return Exec.exec("report/sendmail post", () -> reminderService.sendMail(req));
    }

}
