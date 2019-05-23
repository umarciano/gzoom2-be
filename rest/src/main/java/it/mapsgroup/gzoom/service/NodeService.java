package it.mapsgroup.gzoom.service;

import it.mapsgroup.gzoom.model.NodeConfiguration;
import it.mapsgroup.gzoom.querydsl.dao.PartyNoteDao;
import it.mapsgroup.gzoom.querydsl.dao.PartyContentDao;
import it.mapsgroup.gzoom.querydsl.dto.PartyNoteEx;
import it.mapsgroup.gzoom.querydsl.dto.PartyContentEx;
import it.mapsgroup.gzoom.rest.ValidationException;
import it.mapsgroup.report.querydsl.dto.ReportActivity;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import it.mapsgroup.gzoom.model.Result;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;
import java.util.regex.Pattern;

import static it.mapsgroup.gzoom.security.Principals.principal;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * Profile service.
 *
 */
@Service
public class NodeService {
    private static final Logger LOG = getLogger(NodeService.class);

    private final PartyNoteDao partyNoteDao;
    private final PartyContentDao partyContentDao;

    @Autowired
    public NodeService(PartyNoteDao partyNoteDao, PartyContentDao partyContentDao) {
        this.partyNoteDao = partyNoteDao;
        this.partyContentDao = partyContentDao;
    }

    public PartyNoteEx getNodeConfiguration(String partyId, String noteName) {
        PartyNoteEx ret = partyNoteDao.getPartyNote(partyId, noteName);
        return ret;
    }

    /**
     *
     * @param partyId
     * @param partyContentTypeId
     * @param request
     * @param response
     * @return
     */
    public String stream(String partyId, String partyContentTypeId, HttpServletRequest request, HttpServletResponse response) {
        PartyContentEx partyContentEx = partyContentDao.getPartyContent(partyId, partyContentTypeId);

        LOG.info("stream patch: "+partyContentEx.getDataResource().getObjectInfo());
        File file = new File(partyContentEx.getDataResource().getObjectInfo());

        try (InputStream bw = new BufferedInputStream(new FileInputStream(file))) {
            //String outputFormat = reportActivity.getObjectInfo().substring(reportActivity.getContentName().length() -3);
            //String contentType = BirtContentTypeEnum.getContentType(outputFormat);

            response.setContentType(partyContentEx.getDataResource().getMimeTypeId());
            response.setContentLength((int) file.length());
            String fileName = partyContentEx.getContentName();
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
