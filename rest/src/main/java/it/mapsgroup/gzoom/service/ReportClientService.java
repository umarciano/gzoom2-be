package it.mapsgroup.gzoom.service;

import it.mapsgroup.gzoom.report.report.dto.CreateReport;
import it.mapsgroup.gzoom.report.report.dto.ReportStatus;
import it.memelabs.smartnebula.commons.DateUtil;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.HashMap;

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
        reportParameters.put("monitoringDate", date3112.getTime());
        reportParameters.put("date3112", date3112);

        reportParameters.put("excludeValidity", "N");
        reportParameters.put("exposeReleaseDate", "Y");
        reportParameters.put("exposePaginator", "Y");

        reportParameters.put("langLocale", "");
        reportParameters.put("outputFormat", "pdf");
        reportParameters.put("userLoginId", "admin");
        reportParameters.put("userProfile", "MGR_ADMIN");
        reportParameters.put("birtOutputFileName", "ValutazioniRischi");
        reportParameters.put("defaultOrganizationPartyId", "Company");


        CreateReport request = new CreateReport();
        request.setContentName("test.pdf");
        request.setCreatedByUserLogin("admin");
        request.setModifiedByUserLogin("admin");
        request.setReportLocale("it");
        request.setReportName("ValutazioniRischi/ValutazioniRischi");
        request.setParams(reportParameters);

        String reportId = restTemplate.postForObject("http://localhost:8081/rest/report/add", request, String.class);
        LOG.info("ReportId {}", reportId);
        return reportId;
    }

    public ResponseEntity<ReportStatus> getStatus(String id) {
        return restTemplate.getForEntity("http://localhost:8081/rest/report/{reportId}/status", ReportStatus.class,  id);
    }
}
