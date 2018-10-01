package it.mapsgroup.gzoom.service;

import static it.mapsgroup.gzoom.security.Principals.principal;
import static org.slf4j.LoggerFactory.getLogger;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import it.mapsgroup.gzoom.model.Messages;
import it.mapsgroup.gzoom.model.Result;
import it.mapsgroup.gzoom.querydsl.dao.ContentAndAttributesDao;
import it.mapsgroup.gzoom.querydsl.dao.ReportDao;
import it.mapsgroup.gzoom.querydsl.dto.Report;
import it.mapsgroup.gzoom.querydsl.dto.ReportParams;
import it.mapsgroup.gzoom.querydsl.dto.ReportType;
import it.mapsgroup.gzoom.report.report.dto.CreateReport;
import it.mapsgroup.gzoom.report.report.dto.ReportStatus;
import it.memelabs.smartnebula.commons.DateUtil;

/**
 * Profile service.
 *
 */
@Service
public class ReportService {
    private static final Logger LOG = getLogger(ReportService.class);

    // private final GzoomReportClient client;
    private final GzoomReportClientConfig config;

    private final ReportClientService client;
    
    private final ReportDao reportDao;
    

    @Autowired
    public ReportService(ReportClientService client, GzoomReportClientConfig config, ReportDao reportDao) {
        this.config = config;
        this.client = new ReportClientService(new RestTemplate());
        this.reportDao = reportDao;
        // this.client = client;
        // client = new HttpClient(new SimpleHttpConnectionManager());
        // client.getHttpConnectionManager().getParams().setConnectionTimeout(30000);
    }

    public Result<Report> getReports(String parentTypeId) {
        Report c = new Report();
        c.setContentId("10000");
        c.setContentName("StampaTimesheet");
        c.setParentTypeId("CTX_PR");
        c.setReportContentId("REP_TIMESHEET");
        c.setReportName("Stampa Timesheet");
        List<Report> list = new ArrayList<>();
        list.add(c);
        
        Report c2 = new Report();
        c2.setContentId("10001");
        c2.setContentName("StampaMensileMacroAttivita");
        c2.setParentTypeId("CTX_PR");
        c2.setReportContentId("REP_MENS_MACATT");
        c2.setReportName("Stampa mensile Macro-attività");
        list.add(c2);
        return new Result<>(list, list.size());
    }

    public Report getReport(String reportContentId) {
        Report c = new Report();
        if ("REP_TIMESHEET".equals(reportContentId)) {
            c.setContentId("10000");
            c.setContentName("StampaTimesheet");
            c.setParentTypeId("CTX_PR");
            c.setReportContentId("REP_TIMESHEET");
            c.setReportName("Stampa Timesheet");
            ReportParams param1 = new ReportParams();
            param1.setReportParamName("exposeReleaseDate");
            param1.setReportParamType("boolean");
            ReportParams param2 = new ReportParams();
            param2.setReportParamName("date1231");
            param2.setReportParamType("date");
            
            List<ReportType> pippo = reportDao.getReportTypes("REP_TIMESHEET");
            c.setOutputFormats(pippo);
        } else {
            c.setContentId("10001");
            c.setContentName("StampaMensileMacroAttivita");
            c.setParentTypeId("CTX_PR");
            c.setReportContentId("REP_MENS_MACATT");
            c.setReportName("Stampa mensile Macro-attività");
            ReportParams param1 = new ReportParams();
            param1.setReportParamName("date1231");
            param1.setReportParamType("date");
            
            List<ReportType> pippo = reportDao.getReportTypes("REP_MENS_MACATT");
            c.setOutputFormats(pippo);
        }
        return c;
    }

    /**
     * Validation and Create TODO Manage db Exception, like integrity Violetion, Duplicate Key or create a error message
     * with detail
     * @param req 
     * 
     * @param req
     * @return
     */
    public String add(Report req) {
        // TODO attenzione xke nella req i lreportName e' sbagliato
        
        // TODO Validators.assertNotNull(req, Messages.UOM_TYPE_REQUIRED);
        HashMap<String, Object> reportParameters = new HashMap<>();
        //TODO add parameters here
        
        // TODO 
        reportParameters.put("workEffortTypeId", "15AP0PPC");
        // TODO 
        reportParameters.put("workEffortId", "E12144");
        reportParameters.put("reportContentId", req.getReportContentId()); // REPO_VALUT_RISC - REPO_PRI_VALUT_RISC

        // TODO
        Date date3112 = DateUtil.parse("20171231", "yyyyMMdd");
        reportParameters.put("langLocale", "");
        reportParameters.put("outputFormat", "pdf");
        reportParameters.put("workEffortTypeId", "15AP0PPC");
        reportParameters.put("exposeReleaseDate", "Y");
        reportParameters.put("exposePaginator", "Y");
        reportParameters.put("reportContentId", req.getReportContentId()); // anche qui?
        reportParameters.put("userLoginId", principal().getUserLoginId());
        reportParameters.put("userProfile", "MGR_ADMIN");
        reportParameters.put("birtOutputFileName", req.getContentName()); // anche qui?
        reportParameters.put("localDispatcherName", "corperf");
        reportParameters.put("defaultOrganizationPartyId", "Company");
        reportParameters.put("date3112", date3112);

        CreateReport request = new CreateReport();
        request.setContentName(req.getContentName());
        request.setCreatedByUserLogin(principal().getUserLoginId());
        request.setModifiedByUserLogin(principal().getUserLoginId());
        request.setReportLocale("it_IT");
        request.setReportName(req.getReportName() + "/" + req.getReportName());
        request.setParams(reportParameters);

        
        String id = client.createReport(config.getServerReportUrl(), request);
        ResponseEntity<ReportStatus> status = client.getStatus(config.getServerReportUrl(), id);
        LOG.info(status.getBody().toString());

        return id;
    }
    
    public ResponseEntity<ReportStatus> status(String contentId) {
        ResponseEntity<ReportStatus> status = client.getStatus(config.getServerReportUrl(), contentId);
        LOG.info(status.getBody().toString());
        return status;
    }
    
    public Boolean deleteReport(String contentId) {
        return false;
    }
}
