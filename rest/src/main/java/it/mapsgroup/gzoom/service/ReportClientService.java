package it.mapsgroup.gzoom.service;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author Andrea Fossi.
 */
@Service
public class ReportClientService {
    private final RestTemplate restTemplate;

    private static final Logger LOG = getLogger(ReportClientService.class);

    @Autowired
    public ReportClientService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    //todo add arguments
    public String createReport() {
        //restTemplate.postForObject()
        return null;
    }
}
