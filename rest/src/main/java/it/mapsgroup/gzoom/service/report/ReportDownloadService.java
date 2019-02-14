package it.mapsgroup.gzoom.service.report;

import static it.mapsgroup.gzoom.security.Principals.principal;
import static org.slf4j.LoggerFactory.getLogger;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;



import it.mapsgroup.gzoom.model.Result;
import it.mapsgroup.gzoom.querydsl.dao.ReportDao;
import it.mapsgroup.gzoom.report.report.dto.ReportStatus;
import it.mapsgroup.gzoom.rest.ValidationException;
import it.mapsgroup.gzoom.service.GzoomReportClientConfig;
import it.mapsgroup.gzoom.service.ReportClientService;
import it.mapsgroup.report.querydsl.dto.ReportActivity;

/**
 * Profile service.
 *
 */
@Service
public class ReportDownloadService {
    private static final Logger LOG = getLogger(ReportDownloadService.class);

    // private final GzoomReportClient client;
    private final GzoomReportClientConfig config;

    private final ReportClientService client;
    
   
    @Autowired
    public ReportDownloadService(ReportClientService client, GzoomReportClientConfig config, ReportDao reportDao) {
        this.config = config;
        this.client = new ReportClientService(new RestTemplate());
    }
    
    public Result<ReportActivity> getReportDownloads() {
    	return client.getReportDownloads(config.getServerReportUrl(), principal().getUserLoginId()); 
   
//    	ReportActivity reportActivity = client.getReportActivity(config.getServerReportUrl(), "10070").getBody();
//    	List<ReportActivity> rest = new ArrayList<>();
//    	rest.add(reportActivity);
 //   	return new Result<>(rest, rest.size());
    	
    }
    
    public ResponseEntity<ReportStatus> status(String activityId) {
        ResponseEntity<ReportStatus> status = client.getStatus(config.getServerReportUrl(), activityId);
        LOG.info(status.getBody().toString());
        return status;
    }
    
    public Boolean deleteReport(String activityId) {
    	client.cancel(config.getServerReportUrl(), activityId);
        return true;
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
        	//String outputFormat = reportActivity.getObjectInfo().substring(reportActivity.getContentName().length() -3);
        	//String contentType = BirtContentTypeEnum.getContentType(outputFormat);
        	
            response.setContentType(reportActivity.getMimeTypeId()); 
            response.setContentLength((int) file.length());
            String fileName = reportActivity.getContentName(); 
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
