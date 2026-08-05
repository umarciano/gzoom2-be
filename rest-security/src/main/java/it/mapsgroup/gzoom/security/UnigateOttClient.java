package it.mapsgroup.gzoom.security;

import it.mapsgroup.gzoom.security.dto.models.OttValidationResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * Client HTTP verso UNIGATE per la validazione dei One-Time Token (OTT).
 * Chiama GET {unigate.url}/api/portal/ott/validate?token=xxx
 * passando l'API key nell'header X-Api-Key.
 */
@Component
public class UnigateOttClient {

    private static final Logger LOG = LoggerFactory.getLogger(UnigateOttClient.class);

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${unigate.url:http://localhost:8092}")
    private String unigateUrl;

    @Value("${unigate.ott.api-key:dev-api-key-change-in-prod}")
    private String apiKey;

    public OttValidationResponse validate(String token) {
        String url = unigateUrl + "/api/portal/ott/validate?token=" + token;
        LOG.info("UnigateOttClient - Calling UNIGATE validate: " + url);

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Api-Key", apiKey);
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<OttValidationResponse> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                OttValidationResponse.class
        );

        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            LOG.info("UnigateOttClient - validation result: valid=" + response.getBody().isValid()
                    + ", appUsername=" + response.getBody().getAppUsername());
            return response.getBody();
        }

        throw new RuntimeException("UNIGATE OTT validation failed with HTTP " + response.getStatusCodeValue());
    }
}
