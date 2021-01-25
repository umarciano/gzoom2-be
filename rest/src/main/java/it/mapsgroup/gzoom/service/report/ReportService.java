package it.mapsgroup.gzoom.service.report;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import it.mapsgroup.gzoom.model.Report;
import it.mapsgroup.gzoom.model.Result;
import it.mapsgroup.gzoom.querydsl.dao.ReportDao;
import it.mapsgroup.gzoom.querydsl.dao.WorkEffortTypeContentDao;
import it.mapsgroup.gzoom.querydsl.dto.ReportParams;
import it.mapsgroup.gzoom.querydsl.dto.ReportType;
import it.mapsgroup.gzoom.querydsl.dto.WorkEffortTypeExt;
import it.mapsgroup.gzoom.service.DtoMapper;
import it.mapsgroup.gzoom.service.report.ReportClientService;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Profile service.
 *
 */
@Service
public class ReportService {
    private static final Logger LOG = getLogger(ReportService.class);

    private final ReportClientService client;
    
    private final ReportDao reportDao;
    private final WorkEffortTypeContentDao workEffortTypeContentDao;
    private final DtoMapper dtoMapper;

    @Autowired
    public ReportService(ReportClientService client, ReportDao reportDao,  DtoMapper dtoMapper,
    		WorkEffortTypeContentDao workEffortTypeContentDao) {
    	this.client = client;
        this.reportDao = reportDao;
        this.workEffortTypeContentDao = workEffortTypeContentDao;
        this.dtoMapper = dtoMapper;
        
    }

    public Result<Report> getReports(String parentTypeId) {
    	
    	List<it.mapsgroup.gzoom.querydsl.dto.Report> list = reportDao.getReports(parentTypeId);
    	List<Report> ret = list.stream().map(p -> dtoMapper.copy(p, new Report())).collect(Collectors.toList());
        
    	List<it.mapsgroup.gzoom.querydsl.dto.Report> listAnalysis =  reportDao.getAnalysisReports(parentTypeId);
        ret.addAll(listAnalysis.stream().map(p -> dtoMapper.copy(p, new Report())).collect(Collectors.toList()));

        LOG.info("getReports size="+ ret.size());
        return new Result<>(ret, ret.size());
    }

    public Report getReport(String parentTypeId, String reportContentId, String resourceName, String workEffortTypeId, boolean analysis) {
        LOG.info("Start getReport");
    	it.mapsgroup.gzoom.querydsl.dto.Report report = null;
    	List<WorkEffortTypeExt> workEffortTypes = null;
    	
    	if (analysis) {
    		report = reportDao.getAnalysisReport(parentTypeId, reportContentId, resourceName);
    		workEffortTypes = reportDao.getAnalysisWorkEffortTypeContents(parentTypeId, reportContentId, resourceName);
    		
    	} else {
    		report = reportDao.getReport(parentTypeId, reportContentId, resourceName, workEffortTypeId);
    		workEffortTypes = workEffortTypeContentDao.getWorkEffortTypeContents(parentTypeId, reportContentId, resourceName, workEffortTypeId);
    	}        
        Report ret = dtoMapper.copy(report, new Report());
        ret.setWorkEffortTypes(workEffortTypes);

        //carico la lista di formati
        List<ReportType> outputFormats = reportDao.getReportType(reportContentId);
        ret.setOutputFormats(outputFormats);
        LOG.info("outputFormats="+outputFormats);
        
        ReportParams params = getParams(parentTypeId, ret.getResourceName(), ret.getContentName());
        ret.setParams(params.getParams());
        ret.setServices(params.getServices());
        LOG.info("params="+params);

        LOG.info("End getReport=" + ret);
        return ret;
    }
    
    /**
     *  Dato il noem del report  mi carico la lista dei parametri
     * @param reportName
     * @return
     */
	private ReportParams getParams(String parentTypeId, String resourceName, String contentName) {
		ResponseEntity<ReportParams> params = client.getReportParams(parentTypeId, resourceName, contentName);
        return params.getBody();
    }	

}
