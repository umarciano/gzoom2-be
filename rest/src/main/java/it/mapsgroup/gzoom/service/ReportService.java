package it.mapsgroup.gzoom.service;

import static it.mapsgroup.gzoom.security.Principals.principal;
import static org.slf4j.LoggerFactory.getLogger;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
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
import it.mapsgroup.gzoom.querydsl.dto.ReportParams;
import it.mapsgroup.gzoom.querydsl.dto.ReportType;
import it.mapsgroup.gzoom.querydsl.dto.WorkEffortTypeExt;
import it.mapsgroup.gzoom.report.report.dto.CreateReport;
import it.mapsgroup.gzoom.report.report.dto.ReportStatus;
import it.mapsgroup.gzoom.rest.ValidationException;
import it.mapsgroup.gzoom.util.BirtUtil;
import it.mapsgroup.report.querydsl.dto.ReportActivity;

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
    private final BirtUtil birtUtil;

    @Autowired
    public ReportService(ReportClientService client, GzoomReportClientConfig config, ReportDao reportDao,  DtoMapper dtoMapper, 
    		WorkEffortTypeContentDao workEffortTypeContentDao, BirtUtil birtUtil) {
        this.config = config;
        this.client = new ReportClientService(new RestTemplate());
        this.reportDao = reportDao;
        this.workEffortTypeContentDao = workEffortTypeContentDao;
        this.dtoMapper = dtoMapper;
        this.birtUtil = birtUtil;
    }

    public Result<Report> getReports(String parentTypeId) {
    	
    	List<it.mapsgroup.gzoom.querydsl.dto.Report> list = reportDao.getReports(parentTypeId);
    	List<Report> ret = list.stream().map(p -> dtoMapper.copy(p, new Report())).collect(Collectors.toList());
        
    	List<it.mapsgroup.gzoom.querydsl.dto.Report> listAnalysis =  reportDao.getAnalysisReports(parentTypeId);
        ret.addAll(listAnalysis.stream().map(p -> dtoMapper.copy(p, new Report())).collect(Collectors.toList()));
        
        return new Result<>(ret, ret.size());
    }

    public Report getReport(String parentTypeId, String reportContentId, String reportName, boolean analysis) {
        
    	it.mapsgroup.gzoom.querydsl.dto.Report report = null;
    	List<WorkEffortTypeExt> workEffortTypes = null;
    	
    	if (analysis) {
    		report = reportDao.getAnalysisReport(parentTypeId, reportContentId, reportName);
    		workEffortTypes = reportDao.getAnalysisWorkEffortTypeContents(parentTypeId, reportContentId, reportName); 
    		
    	} else {
    		report = reportDao.getReport(parentTypeId, reportContentId, reportName);
    		workEffortTypes = workEffortTypeContentDao.getWorkEffortTypeContents(parentTypeId, reportContentId, reportName); 
    	}        
        Report ret = dtoMapper.copy(report, new Report());
        ret.setWorkEffortTypes(workEffortTypes);  
        
        
        //carico la lista di formati
        List<ReportType> outputFormats = reportDao.getReportType(reportContentId);
        ret.setOutputFormats(outputFormats);
                
        
        ReportParams params = getParams(report.getContentName());
        ret.setParams(params.getParams());
        ret.setServices(params.getServices());
        
        return ret;
    }
    
    /**
     *  Dato il noem del report  mi carico la lista dei parametri
     * @param reportName
     * @return
     */
	private ReportParams getParams(String reportName) {		
		ResponseEntity<ReportParams> params = client.getReportParams(config.getServerReportUrl(), reportName);
        return params.getBody();
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
        for(String key: paramsValue.keySet()) {
        	reportParameters.put(key, paramsValue.get(key));
        }
        
        //TODO
        reportParameters.put("langLocale", "");
        reportParameters.put("userProfile", "MGR_ADMIN");
        reportParameters.put("localDispatcherName", "corperf"); //non serve più
        reportParameters.put("defaultOrganizationPartyId", "Company");
        
        LOG.info("add  before call all service reportParameters-> "+ reportParameters);
        req.getServices().forEach(services -> {
        	String serviceName = services.getServiceName();
			try {
				Method setNameMethod = BirtUtil.class.getMethod(serviceName, Map.class);
				setNameMethod.invoke(birtUtil, reportParameters);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            
	    	LOG.info("add call serviceName="+serviceName);
	    });
        
       /* req.getParams().forEach(params -> {
        	Object obj = paramsValue.get(params.getParamName());
        	
        	if (params.getParamType().equals("DATE")) {
        		//reportParameters.put(params.getParamName(), DateUtil.parse((String)obj, "YYYY-MM-DD"));
        	} else if(params.getParamType().equals("BOOLEAN")) {        		
        		String value = "N";
        		if ((boolean)obj) {
        			value = "Y";
        		}
        		reportParameters.put(params.getParamName(), value);
        	} else {
        		reportParameters.put(params.getParamName(), obj);
        	}
        	
        });*/

		//TODO nel caso che ho un servizio da chiamare al posto della creazione dell stampa
        //unico caso creazioen di uno zip in formato xls
        //come si fa?
        
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
