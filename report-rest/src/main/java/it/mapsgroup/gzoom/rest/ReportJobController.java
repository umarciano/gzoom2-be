package it.mapsgroup.gzoom.rest;

import it.mapsgroup.gzoom.common.Exec;
import it.mapsgroup.gzoom.report.dto.CreateReport;
import it.mapsgroup.gzoom.service.ReportJobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * @author Fabio G. Strozzi
 */
@RestController
@RequestMapping(value = "", produces = {MediaType.APPLICATION_JSON_VALUE})
public class ReportJobController {

    private final ReportJobService reportJobService;

    @Autowired
    public ReportJobController(ReportJobService reportJobService) {
        this.reportJobService = reportJobService;
    }

    @RequestMapping(value = "/report/add", method = RequestMethod.POST)
    @ResponseBody
    public String add(@RequestBody CreateReport report) {
        return Exec.exec("add-report", () -> reportJobService.add(report));
    }
}
