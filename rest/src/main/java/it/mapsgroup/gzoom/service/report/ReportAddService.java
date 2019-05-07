package it.mapsgroup.gzoom.service.report;

import it.mapsgroup.gzoom.model.Report;
import it.mapsgroup.gzoom.querydsl.util.ContextPermissionPrefixEnum;
import it.mapsgroup.gzoom.report.report.dto.CreateReport;
import it.mapsgroup.gzoom.report.report.dto.ReportStatus;
import it.mapsgroup.gzoom.service.report.ReportClientService;
import it.mapsgroup.gzoom.util.BirtContentTypeEnum;
import it.mapsgroup.gzoom.util.BirtUtil;
import it.memelabs.smartnebula.commons.DateUtil;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import static it.mapsgroup.gzoom.security.Principals.principal;
import static org.slf4j.LoggerFactory.getLogger;

@Service
public class ReportAddService {
    private static final Logger LOG = getLogger(ReportAddService.class);

    private final ReportClientService client;
    private final BirtUtil birtUtil;

    private static final String DEFAULT_REPORT_OUTPUT_FORMAT = "pdf";

    @Autowired
    public ReportAddService(BirtUtil birtUtil, ReportClientService client) {
        this.birtUtil = birtUtil;
        this.client = client;
    }

    public HashMap<String, Object> getReportParameters(Report req) {
        HashMap<String, Object> reportParameters = new HashMap<>();

        reportParameters.put("workEffortTypeId", req.getWorkEffortTypeId());
        reportParameters.put("reportContentId", req.getReportContentId());
        reportParameters.put("userLoginId", principal().getUserLoginId());
        reportParameters.put("birtOutputFileName", req.getContentName());
        reportParameters.put("outputFormat", (req.getOutputFormat() == null ? DEFAULT_REPORT_OUTPUT_FORMAT : req.getOutputFormat()));
        reportParameters.put("localDispatcherName", ContextPermissionPrefixEnum.getPermissionPrefix(req.getParentTypeId())); //non serve piu
        reportParameters.put("parentTypeId", req.getParentTypeId());

        Map<String, Object> paramsValue = req.getParamsValue();
        for (String key : paramsValue.keySet()) {
            reportParameters.put(key, paramsValue.get(key) == null ? "" : paramsValue.get(key) == null);
        }

        //devo convertire la data in Date 
        req.getParams().forEach(params -> {
            Object obj = paramsValue.get(params.getParamName());
            if (params.getParamType().equals("DATE")) {
                reportParameters.put(params.getParamName(), DateUtil.parse((String) obj, "yyyy-MM-dd"));
            } else if (params.getParamType().equals("BOOLEAN")) {
                boolean value = (boolean) reportParameters.get(params.getParamName());
                if (value) {
                    reportParameters.put(params.getParamName(), "Y");
                } else {
                    reportParameters.put(params.getParamName(), "N");
                }
            }
        });

        //TODO
        reportParameters.put("langLocale", "");
        //reportParameters.put("userProfile", "MGR_ADMIN"); //nnon serve piu
        reportParameters.put("defaultOrganizationPartyId", "Company");

        LOG.info("add  before call all service reportParameters-> " + reportParameters);
        req.getServices().forEach(services -> {
            String serviceName = services.getServiceName();
            try {
                Method setNameMethod = BirtUtil.class.getMethod(serviceName, Map.class);
                setNameMethod.invoke(birtUtil, reportParameters);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            LOG.info("add call serviceName=" + serviceName);
        });

        return reportParameters;
    }

    /**
     * @param req
     * @return
     */
    public String add(Report req) {
        HashMap<String, Object> reportParameters = getReportParameters(req);
        return add(reportParameters, req.getContentName(), principal().getUserLoginId());
    }


    /**
     * @param reportParameters
     * @param contentName 
     * @return
     */
    public String add(HashMap<String, Object> reportParameters, String contentName, String userLoginId) {
        LOG.info("add  reportParameters-> " + reportParameters);
        CreateReport request = new CreateReport();
        request.setCreatedByUserLogin(userLoginId);
        request.setModifiedByUserLogin(userLoginId);
        request.setReportLocale("it_IT");
        request.setMimeTypeId(BirtContentTypeEnum.getContentType((String) reportParameters.get("outputFormat")));
        request.setReportName(contentName);
        request.setContentName(contentName + "." + reportParameters.get("outputFormat"));
        request.setParams(reportParameters);

        String id = client.createReport(request);
        ResponseEntity<ReportStatus> status = client.getStatus(id);
        LOG.info(status.getBody().toString());

        return id;
    }


}
