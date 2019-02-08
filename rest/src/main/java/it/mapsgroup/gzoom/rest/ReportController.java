package it.mapsgroup.gzoom.rest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import it.mapsgroup.gzoom.common.Exec;
import it.mapsgroup.gzoom.model.Report;
import it.mapsgroup.gzoom.model.Result;
import it.mapsgroup.gzoom.report.report.dto.ReportStatus;
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

    @RequestMapping(value = "report/{activityId}/status", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<ReportStatus> update(@PathVariable(value = "activityId") String activityId) {
        return Exec.exec("report status", () -> reportService.status(activityId));
    }
    
    @RequestMapping(method = RequestMethod.GET, value = "report/{activityId}/stream")
    @ResponseBody
    public String stream(@PathVariable(value = "activityId") String activityId, HttpServletRequest req, HttpServletResponse response) {
    	return Exec.exec("report stream", () -> reportService.stream(activityId, req, response));
    }
    
    @RequestMapping(value = "report/{contentId}", method = RequestMethod.DELETE)
    @ResponseBody
    public Boolean delete(@PathVariable(value = "contentId") String contentId) {
        return Exec.exec("report delete", () -> reportService.deleteReport(contentId));
    }    
    
    @RequestMapping(value = "report/mail", method = RequestMethod.POST)
    @ResponseBody
    public String sendmail(@RequestBody Report req) {
        return Exec.exec("report/sendmail post", () -> reminderService.sendMail(req));
    }
    
}
