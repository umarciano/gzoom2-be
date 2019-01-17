package it.mapsgroup.gzoom.service;

import static it.mapsgroup.gzoom.security.Principals.principal;
import static org.slf4j.LoggerFactory.getLogger;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;



import it.mapsgroup.gzoom.model.Report;
import it.mapsgroup.gzoom.model.Result;
import it.mapsgroup.gzoom.querydsl.dao.ReportDao;
import it.mapsgroup.gzoom.querydsl.dao.WorkEffortTypeContentDao;
import it.mapsgroup.gzoom.querydsl.dto.ReportParam;
import it.mapsgroup.gzoom.querydsl.dto.ReportParams;
import it.mapsgroup.gzoom.querydsl.dto.ReportType;
import it.mapsgroup.gzoom.querydsl.dto.WorkEffortTypeExt;
import it.mapsgroup.gzoom.report.report.dto.CreateReport;
import it.mapsgroup.gzoom.report.report.dto.ReportStatus;
import it.mapsgroup.gzoom.rest.ValidationException;
import it.mapsgroup.report.querydsl.dto.ReportActivity;
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
    private final WorkEffortTypeContentDao workEffortTypeContentDao;
    private final DtoMapper dtoMapper;    

    @Autowired
    public ReportService(ReportClientService client, GzoomReportClientConfig config, ReportDao reportDao,  DtoMapper dtoMapper, 
    		WorkEffortTypeContentDao workEffortTypeContentDao) {
        this.config = config;
        this.client = new ReportClientService(new RestTemplate());
        this.reportDao = reportDao;
        this.workEffortTypeContentDao = workEffortTypeContentDao;
        this.dtoMapper = dtoMapper;
        // this.client = client;
        // client = new HttpClient(new SimpleHttpConnectionManager());
        // client.getHttpConnectionManager().getParams().setConnectionTimeout(30000);
    }

    public Result<Report> getReports(String parentTypeId) {
    	
    	List<it.mapsgroup.gzoom.querydsl.dto.Report> list = reportDao.getReports(parentTypeId);
    	List<Report> ret = list.stream().map(p -> dtoMapper.copy(p, new Report())).collect(Collectors.toList());
        
    	List<it.mapsgroup.gzoom.querydsl.dto.Report> listAnalysis =  reportDao.getAnalysisReports(parentTypeId);
        ret.addAll(listAnalysis.stream().map(p -> dtoMapper.copy(p, new Report())).collect(Collectors.toList()));
        
        return new Result<>(ret, ret.size());
    }

    public Report getReport(String parentTypeId, String reportContentId) {
        //TODO gestire caso analisi 
    	
        it.mapsgroup.gzoom.querydsl.dto.Report report = reportDao.getReport(reportContentId);
        Report ret = dtoMapper.copy(report, new Report());
        
        //carico la lista di formati
        List<ReportType> outputFormats = reportDao.getReportType(reportContentId);
        ret.setOutputFormats(outputFormats);
        
        
        //carico la lista dei workEffortType
        List<WorkEffortTypeExt> workEffortTypes = workEffortTypeContentDao.getWorkEffortTypeContents(reportContentId);
        ret.setWorkEffortTypes(workEffortTypes);        
        
        //TODO manca param
        ret.setParams(getParams(report.getContentName()));
        
        return ret;
    }
    
    /**
     *  Dato il noem del report  mi carico la lista dei parametri
     * @param reportName
     * @return
     */
	private List<ReportParam> getParams(String reportName) {		
		ResponseEntity<ReportParams> params = client.getReportParams(config.getServerReportUrl(), reportName);
        return params.getBody().getParams();
		
		//TODO
    	/*String path = "C:/Users/asma/workspaceNeon/ProvaBirt/StampaTimesheet/StampaTimesheet.json";
    	File file = new File(path);
    	if (file.exists()) {
    		return getParamsToFile(file);
    	} else {
    		//vado a prendere i parametri dal report
    		
    	}
        
        return null;*/
    	
    	/*
    	ReportParams param1111 = new ReportParams();
    	param1111.setParamType("LIST");
    	param1111.setMandatory(false);
    	param1111.setParamName("workEffortId");
    	list.add(param1111);
    	
    	ReportParams param111 = new ReportParams();
    	param111.setParamType("DATE");
    	param111.setMandatory(true);
    	param111.setParamName("monitoringDate");
    	list.add(param111);
    	
    	ReportParams param1111111 = new ReportParams();
    	param1111111.setParamType("LIST");
    	param1111111.setMandatory(true);
    	param1111111.setParamName("currentStatusName");
    	list.add(param1111111);
    	
    	
    	ReportParams param11111 = new ReportParams();
    	param11111.setParamType("INPUT");
    	param11111.setMandatory(true);
    	param11111.setParamDefault("PIPPO");
    	param11111.setParamName("workEffortTypeId_Obb");
    	list.add(param11111);
    	
    	ReportParams param = new ReportParams();
    	param.setParamType("BOOLEAN");
    	param.setMandatory(false);    
    	param.setParamDefault(false);    	
    	param.setParamName("exposePaginator"); 
    	list.add(param); 

    	*/
    	
    }
	
	

    /**     
     * 
     * @param req
     * @return
     */
    public String add(Report req) {
        // TODO controllo parametri se sono corretti

        HashMap<String, Object> reportParameters = new HashMap<>();
               
        reportParameters.put("workEffortTypeId", req.getWorkEffortTypeId());
        reportParameters.put("reportContentId", req.getReportContentId()); 
        reportParameters.put("userLoginId", principal().getUserLoginId());
        reportParameters.put("birtOutputFileName", req.getContentName()); 
        reportParameters.put("outputFormat", req.getOutputFormat()); 
        
        Map<String, Object> paramsValue = req.getParamsValue();
        /*for(String key: paramsValue.keySet()) {
        	reportParameters.put(key, paramsValue.get(key));
        }*/
        
        req.getParams().forEach(params -> {
        	Object obj = paramsValue.get(params.getParamName());
        	if (params.getParamType().equals("DATE")) {
        		reportParameters.put(params.getParamName(), DateUtil.parse((String)obj, "yyyy-MM-dd"));
        	} else if(params.getParamType().equals("BOOLEAN")) {        		
        		String value = "N";
        		if ((boolean)obj) {
        			value = "Y";
        		}
        		reportParameters.put(params.getParamName(), value);
        	} else {
        		reportParameters.put(params.getParamName(), obj);
        	}
        	
        });
        
        /*Date date3112 = DateUtil.parse("20171231", "yyyyMMdd");
        reportParameters.put("date3112", date3112);*/
        
        // TODO       
        
        reportParameters.put("langLocale", "");
        reportParameters.put("userProfile", "MGR_ADMIN");
        reportParameters.put("localDispatcherName", "corperf");
        reportParameters.put("defaultOrganizationPartyId", "Company");
        
        LOG.info("add  reportParameters-> "+ reportParameters);
        CreateReport request = new CreateReport();
        request.setCreatedByUserLogin(principal().getUserLoginId());
        request.setModifiedByUserLogin(principal().getUserLoginId());
        request.setReportLocale("it_IT");
        request.setReportName(req.getContentName());
        request.setParams(reportParameters);

        
        String id = client.createReport(config.getServerReportUrl(), request);
        ResponseEntity<ReportStatus> status = client.getStatus(config.getServerReportUrl(), id);
        LOG.info(status.getBody().toString());

        return id;
    }
    
    public ResponseEntity<ReportStatus> status(String activityId) {
        ResponseEntity<ReportStatus> status = client.getStatus(config.getServerReportUrl(), activityId);
        LOG.info(status.getBody().toString());
        return status;
    }
    
    /*public ByteArrayOutputStream stream(String activityId) {
    	try {
    		ByteArrayOutputStream baos = new ByteArrayOutputStream();
	    	File file = new File("C:\\data\\Gzoom_2\\birt\\logs\\report\\report_10100.pdf");
	    	FileInputStream fis = new FileInputStream(file);
	    	
	    	byte[] bytes = new byte[1024];
	        int length;
	        while((length = fis.read(bytes)) >= 0) {
	        	baos.write(bytes, 0, length);
	        }
	        fis.close();
	        baos.close();
	        return baos;
    	} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
    }*/
    
    public Boolean deleteReport(String contentId) {
        return false;
    }
    
    /**
     * 
     * @param activityId
     * @param request
     * @param response
     * @return
     */
    public String stream(String activityId, HttpServletRequest request, HttpServletResponse response) {
    	ReportActivity reportActivity = client.getReportActivity(config.getServerReportUrl(), activityId).getBody();
    	LOG.info("stream patch: "+reportActivity.getObjectInfo());
        File file = new File(reportActivity.getObjectInfo()); 
        
        try (InputStream bw = new BufferedInputStream(new FileInputStream(file))) {
            response.setContentType("application/pdf"); //TODO
            response.setContentLength((int) file.length());
            String fileName = reportActivity.getReportName() + ".pdf"; //TODO
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + fileName + "\"");
            IOUtils.copy(bw, response.getOutputStream());
            response.flushBuffer();
        } catch (IOException e) {
            LOG.error("error loading file", e);
            throw new ValidationException("error loading file");
        }
        return "";
    }

}
