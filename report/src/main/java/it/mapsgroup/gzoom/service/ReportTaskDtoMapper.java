package it.mapsgroup.gzoom.service;

import it.mapsgroup.gzoom.querydsl.dto.Content;
import it.mapsgroup.gzoom.querydsl.dto.DataResource;
import it.mapsgroup.report.querydsl.dto.ReportActivity;
import org.springframework.stereotype.Component;

/**
 * @author Andrea Fossi.
 */
@Component
public class ReportTaskDtoMapper {

    public Content getContent(ReportActivity from, String dataResourceId) {
        Content to = new Content();
        to.setContentTypeId("TMP_ENCLOSE");
        to.setDataResourceId(dataResourceId);//data resource id
        to.setStatusId("CTNT_INITIAL_DRAFT");
        to.setServiceName("RP" + from.getActivityId());//scheduled job id
        to.setContentName(from.getContentName());//report file name (when downloaded)
        to.setMimeTypeId("application/pdf");
        to.setCreatedByUserLogin(from.getCreatedByUserLogin());
        to.setLastModifiedByUserLogin(from.getLastModifiedByUserLogin());
        return to;
    }

    public DataResource getDataResource(ReportActivity from, String reportPath) {
        DataResource to = new DataResource();
        to.setDataResourceTypeId("LOCAL_FILE");
        to.setDataTemplateTypeId("NONE");
        to.setStatusId("CTNT_IN_PROGRESS");
        to.setDataResourceName(from.getContentName());
        to.setMimeTypeId("application/pdf");
        to.setObjectInfo(reportPath);
        to.setIsPublic(false);
        to.setCreatedByUserLogin(from.getCreatedByUserLogin());
        to.setLastModifiedByUserLogin(from.getLastModifiedByUserLogin());
        return to;
    }
}
