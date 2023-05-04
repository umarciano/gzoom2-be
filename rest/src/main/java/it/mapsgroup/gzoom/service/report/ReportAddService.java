package it.mapsgroup.gzoom.service.report;

import it.mapsgroup.gzoom.model.Report;

import it.mapsgroup.gzoom.querydsl.util.ContextPermissionPrefixEnum;
import it.mapsgroup.gzoom.report.report.dto.CreateReport;
import it.mapsgroup.gzoom.report.report.dto.ReportStatus;

import it.mapsgroup.gzoom.util.BirtContentTypeEnum;
import it.mapsgroup.gzoom.util.BirtUtil;
import it.mapsgroup.gzoom.service.Configuration;
import it.memelabs.smartnebula.commons.DateUtil;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Locale;

import static it.mapsgroup.gzoom.security.Principals.principal;
import static org.slf4j.LoggerFactory.getLogger;

@Service
public class ReportAddService {
    private static final Logger LOG = getLogger(ReportAddService.class);

    private final ReportClientService client;
    private final Configuration config;
    private final BirtUtil birtUtil;

    private static final String DEFAULT_REPORT_OUTPUT_FORMAT = "pdf";
    private static final String DEFAULT_ORGANIZATION_UNIT_ID = "Company";
    private static final String DEFAULT_LANG = "_LANG";

    @Autowired
    public ReportAddService(BirtUtil birtUtil, ReportClientService client, Configuration config) {
        this.birtUtil = birtUtil;
        this.client = client;
        this.config = config;
    }

    public HashMap<String, Object> getReportParameters(Report req) {
        HashMap<String, Object> reportParameters = new HashMap<>();

        reportParameters.put("workEffortTypeId", req.getWorkEffortTypeId());
        reportParameters.put("reportContentId", req.getReportContentId());
        reportParameters.put("reportContentTypeId", req.getReportContentTypeId());
        reportParameters.put("userLoginId", principal().getUserLoginId());
        reportParameters.put("birtOutputFileName", req.getEtch()!=null?req.getEtch():req.getDescription()!=null?req.getDescription():req.getContentName());
        reportParameters.put("outputFormat", (req.getOutputFormat() == null ? DEFAULT_REPORT_OUTPUT_FORMAT : req.getOutputFormat()));
        reportParameters.put("localDispatcherName", ContextPermissionPrefixEnum.getPermissionPrefix(req.getParentTypeId()));
        reportParameters.put("parentTypeId", req.getParentTypeId());
        reportParameters.put("defaultOrganizationPartyId", req.getDefaultOrganizationUnitId() == null ? DEFAULT_ORGANIZATION_UNIT_ID : req.getDefaultOrganizationUnitId());
        List<String> avaibledLocale = config.getLanguages();
        LOG.info("getReportParameters: avaibledLocale -> " + avaibledLocale + " , birtLocale" + req.getBirtLocale());
        reportParameters.put("birtLocale", req.getBirtLocale());
        List<String> localeAvailable = config.getLanguages();
        int pos = localeAvailable.indexOf(req.getBirtLocale().toString());
        reportParameters.put("langLocale", "");
        if (pos > 0) {
            reportParameters.put("birtOutputFileName", req.getEtchLang() ); //this row is needed because when we control that birtOuputFileName != null the reportName is overwrite with primaryLanguage label string
            reportParameters.put("langLocale", DEFAULT_LANG);
        }
        LOG.info("getReportParameters: langLocale()-> " + req.getLangLocale());

        Map<String, Object> paramsValue = req.getParamsValue();
        for (String key : paramsValue.keySet()) {
            reportParameters.put(key, paramsValue.get(key) == null ? "" : paramsValue.get(key));
        }

        //devo convertire la data in Date
        if(req.getParams()!=null) {
            req.getParams().forEach(params -> {
                Object obj = paramsValue.get(params.getParamName());
                if (params.getParamType().equals("DATE")) {
                    reportParameters.put(params.getParamName(), DateUtil.parse((String) obj, "yyyy-MM-dd"));
                } else if (params.getParamType().equals("BOOLEAN") && reportParameters.containsKey(params.getParamName())) {
                    boolean value = (boolean) reportParameters.get(params.getParamName());
                    if (value) {
                        reportParameters.put(params.getParamName(), "Y");
                    } else {
                        reportParameters.put(params.getParamName(), "N");
                    }
                }

                //Ripeto l'operazione inserendo i parametri che mancano ed hanno un default per le stampe lanciate da scheda obiettivo
                if(!reportParameters.containsKey(params.getParamName())
                        && params.getParamDefault()!=null && !params.getParamDefault().equals("")) {
                    if (params.getParamType().equals("DATE")) {
                        reportParameters.put(params.getParamName(), DateUtil.parse((String) params.getParamDefault(), "yyyy-MM-dd"));
                    } else if (params.getParamType().equals("BOOLEAN")) {
                        boolean value = (boolean) params.getParamDefault();
                        if (value) {
                            reportParameters.put(params.getParamName(), "Y");
                        } else {
                            reportParameters.put(params.getParamName(), "N");
                        }
                    } else {
                        reportParameters.put(params.getParamName(), params.getParamDefault());
                    }
                }
            });
        }

        LOG.info("add  before call all service reportParameters-> " + reportParameters);
        if(req.getServices()!=null) {
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
        }
        return reportParameters;
    }

    /**
     * @param req
     * @return
     */
    public String add(Report req) {
        HashMap<String, Object> reportParameters = getReportParameters(req);
        return add(reportParameters, req.getContentName(), req.getResourceName(), principal().getUserLoginId());
    }


    /**
     * @param reportParameters
     * @param contentName
     * @return
     */
    public String add(HashMap<String, Object> reportParameters, String contentName,String resourceName, String userLoginId) {
        LOG.info("add  reportParameters-> " + reportParameters);
        CreateReport request = new CreateReport();
        request.setCreatedByUserLogin(userLoginId);
        request.setModifiedByUserLogin(userLoginId);
        LOG.info("add  reportParameters.birtLocale-> " + reportParameters.get("birtLocale"));
        LOG.info("add  reportParameters.langLocale-> " + reportParameters.get("langLocale"));
        Locale birtLocale = (Locale) reportParameters.get("birtLocale");
        request.setReportLocale(birtLocale.toString());

        request.setMimeTypeId(BirtContentTypeEnum.getContentType((String) reportParameters.get("outputFormat")));
        request.setReportName(contentName + "." + reportParameters.get("outputFormat"));
        if (reportParameters.get("birtOutputFileName") != null) {
            request.setReportName(reportParameters.get("birtOutputFileName") + "." + reportParameters.get("outputFormat")); // nome del file pdf, xls, ecc...
        }
        request.setContentName(contentName ); // nome del json
        request.setParams(reportParameters);
        request.setResourceName(resourceName);
        request.setContentTypeId(reportParameters.get("reportContentTypeId").toString());
        String id = client.createReport(request);
        ResponseEntity<ReportStatus> status = client.getStatus(id);
        LOG.info(status.getBody().toString());

        return id;
    }


}
