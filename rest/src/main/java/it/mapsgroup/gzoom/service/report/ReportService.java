package it.mapsgroup.gzoom.service.report;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
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
import it.mapsgroup.gzoom.service.DtoMapper;
import it.mapsgroup.gzoom.service.GzoomReportClientConfig;
import it.mapsgroup.gzoom.service.ReportClientService;

/**
 * Profile service.
 *
 */
@Service
public class ReportService {

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

}
