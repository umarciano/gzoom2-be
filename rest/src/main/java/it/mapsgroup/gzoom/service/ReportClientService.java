package it.mapsgroup.gzoom.service;

import it.mapsgroup.gzoom.model.Result;
import it.mapsgroup.gzoom.querydsl.dto.ReportParams;
import it.mapsgroup.gzoom.report.report.dto.CreateReport;
import it.mapsgroup.gzoom.report.report.dto.ReportStatus;
import it.mapsgroup.gzoom.report.service.ReportCallbackType;
import it.mapsgroup.report.querydsl.dto.ReportActivity;
import it.memelabs.smartnebula.commons.DateUtil;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author Andrea Fossi.
 */
@Service
public class ReportClientService {
    private final RestTemplate restTemplate;

    private static final Logger LOG = getLogger(ReportClientService.class);

    @Autowired
    public ReportClientService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

  //todo sample rest call
    public String createReport() {
        HashMap<String, Object> reportParameters = new HashMap<>();
        //TODO add parameters here

        reportParameters.put("workEffortTypeId", "15AP0PPC");
        reportParameters.put("workEffortId", "E12144");
        reportParameters.put("reportContentId", "REPO_VALUT_RISC"); // REPO_VALUT_RISC - REPO_PRI_VALUT_RISC


        Date date3112 = DateUtil.parse("20171231", "yyyyMMdd");
        reportParameters.put("langLocale", "");
        reportParameters.put("outputFormat", "pdf");
        reportParameters.put("workEffortTypeId", "15AP0PPC");
        reportParameters.put("exposeReleaseDate", "Y");
        reportParameters.put("exposePaginator", "Y");
        reportParameters.put("reportContentId", "REPORT_CATALOGO");
        reportParameters.put("userLoginId", "admin");
        reportParameters.put("userProfile", "MGR_ADMIN");
        reportParameters.put("birtOutputFileName", "CatalogoTreLivelli");
        reportParameters.put("localDispatcherName", "corperf");
        reportParameters.put("defaultOrganizationPartyId", "Company");
        reportParameters.put("date3112", date3112);


        CreateReport request = new CreateReport();
        request.setContentName("test.pdf");
        request.setCreatedByUserLogin("admin");
        request.setModifiedByUserLogin("admin");
        request.setReportLocale("it_IT");
        request.setReportName("CatalogoTreLivelli");
        request.setParams(reportParameters);

        // 
        String reportId = restTemplate.postForObject("http://localhost:8081/rest/report/add", request, String.class);
        LOG.info("ReportId {}", reportId);
        return reportId;
    }

    //TODO sample rest call
    public String createReport(URL url, CreateReport request) {
        // "http://localhost:8081/rest/report/add"
        String reportId = restTemplate.postForObject(url.toString() + "/add", request, String.class);
        LOG.info("ReportId {}", reportId);
        return reportId;
    }

    public ResponseEntity<ReportParams> getReportParams(URL url, String reportName) {
        return restTemplate.getForEntity(url.toString() + "/params/" + reportName, ReportParams.class, reportName);
    }

    /*public ResponseEntity<ReportStatus> getStatus(String id) {
        return restTemplate.getForEntity("http://localhost:8081/rest/report/{reportId}/status", ReportStatus.class, id);
    }*/

    public ResponseEntity<ReportStatus> getStatus(URL url, String id) {
        return restTemplate.getForEntity(url.toString() + "/" + id + "/status", ReportStatus.class, id);
    }

    public ResponseEntity<ReportActivity> getReportActivity(URL url, String id) {
        return restTemplate.getForEntity(url.toString() + "/" + id, ReportActivity.class, id);
    }

	public Result<ReportActivity> getReportDownloads(URL url, String userLoginId) {
		List rest = (List) ((HashMap) restTemplate.getForObject(url.toString()+ "/report-download/" + userLoginId, Object.class, userLoginId)).get("results");
		return new Result<>(rest, rest.size());            }


    public String cancel(URL url, String id) {
    	restTemplate.delete(url.toString() + "/" + id, id);
        return "";
    }
	
}
