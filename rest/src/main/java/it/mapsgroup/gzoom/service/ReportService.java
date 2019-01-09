package it.mapsgroup.gzoom.service;

import static it.mapsgroup.gzoom.security.Principals.principal;
import static org.slf4j.LoggerFactory.getLogger;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
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
import it.mapsgroup.gzoom.querydsl.dto.WorkEffort;
import it.mapsgroup.gzoom.querydsl.dto.WorkEffortTypeExt;
import it.mapsgroup.gzoom.report.report.dto.CreateReport;
import it.mapsgroup.gzoom.report.report.dto.ReportStatus;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

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
        ret.setParams(getParams(reportContentId));
        
        return ret;
    }
    
    /**
     * La lista dei parametri vado a prenderla nella cartella del report,
     * se non esiste prendo i valori dal report
     * (i vecchi report i valori non sono settati in modo corretto ed è meglio metterlil
     * in file di configurazione, mentre si può pensare nei nuovi di andarlia pescare dal report)
     * @param reportContentId
     * @return
     */
	private List<ReportParam> getParams(String reportContentId) {
		//TODO
    	String path = "C:/Users/asma/workspaceNeon/ProvaBirt/StampaTimesheet/StampaTimesheet.json";
    	File file = new File(path);
    	if (file.exists()) {
    		return getParamsToFile(file);
    	} else {
    		//vado a prendere i parametri dal report
    		
    	}
        
        return null;
    	
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
	
	private List<ReportParam> getParamsToFile(File file) {
		ObjectMapper mapper = new ObjectMapper();
        try {
        	ReportParams params = mapper.readValue(file, ReportParams.class);
			LOG.info("PIPPO value -> "+ params.getParams().toString());
			 return params.getParams();
			 
		} catch (JsonParseException e) {			
			e.printStackTrace();
		} catch (JsonMappingException e) {			
			e.printStackTrace();
		} catch (IOException e) {			
			e.printStackTrace();
		}
        
        return null;
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
    
    public ByteArrayOutputStream stream(String activityId) {
    	try {
    		ByteArrayOutputStream baos = new ByteArrayOutputStream();
	    	File file = new File("C:\\data\\Gzoom_2\\birt\\logs\\report\\report_10070.pdf");
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
    }
    
    public Boolean deleteReport(String contentId) {
        return false;
    }
    
    public Result<WorkEffort> getWorkEfforts(String workEffortTypeId) {
       List<WorkEffort> ret = reportDao.getWorkEfforts(workEffortTypeId);
       
       return new Result<>(ret, ret.size());
    }

}
