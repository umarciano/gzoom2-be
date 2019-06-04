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
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import it.mapsgroup.gzoom.model.Result;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;
import java.util.List;
import java.util.regex.Pattern;

import static it.mapsgroup.gzoom.security.Principals.principal;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * Node service.
 *
 */
@Service
public class NodeService {
    private static final Logger LOG = getLogger(NodeService.class);

    public static final String DEFAULT_PATH_LOGO = "/lmm/images/gzoom_logo.jpg";
    public static final String DEFAULT_PATH_LOGO_LOGIN = "/lmm/images/gzoom_logo_login.jpg";
    public static final String DEFAULT_PATH_ICON = "/lmm/images/gzoom.ico";

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
        try  {
            String imagePath = getDefaultImagePath(partyContentTypeId);
            LOG.info("stream imagePath: " + imagePath);
            File file = new ClassPathResource(imagePath).getFile();
            String fileName = file.getName();

            PartyContentEx partyContentEx = partyContentDao.getPartyContent(partyId, partyContentTypeId);

            if (partyContentEx != null) {
                LOG.info("stream path: " + partyContentEx.getDataResource().getObjectInfo());
                File partyContentFile = new File(partyContentEx.getDataResource().getObjectInfo());
                if (partyContentFile.exists() && partyContentFile.canRead()) {
                    file = partyContentFile;
                    response.setContentType(partyContentEx.getDataResource().getMimeTypeId());
                    fileName = partyContentEx.getContentName();
                }
            }

            InputStream bw = new BufferedInputStream(new FileInputStream(file));
            response.setContentLength((int) file.length());
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + fileName + "\"");

            IOUtils.copy(bw, response.getOutputStream());
            response.flushBuffer();
        } catch (IOException e) {
            LOG.error("error loading file", e);
            throw new ValidationException("error loading file");
        }
        return "";
    }

    private String getDefaultImagePath(String partyContentTypeId) {
        if ("LOGO".equals(partyContentTypeId)) {
            return DEFAULT_PATH_LOGO;
        } else if ("LOGO_LOGIN".equals(partyContentTypeId)) {
            return DEFAULT_PATH_LOGO_LOGIN;
        }
        return DEFAULT_PATH_ICON;
    }

}
