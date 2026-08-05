package it.mapsgroup.gzoom.security.dto.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Risposta dell'endpoint UNIGATE GET /api/portal/ott/validate
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class OttValidationResponse {

    private String appUsername;
    private boolean valid;

    public String getAppUsername() {
        return appUsername;
    }

    public void setAppUsername(String appUsername) {
        this.appUsername = appUsername;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }
}
