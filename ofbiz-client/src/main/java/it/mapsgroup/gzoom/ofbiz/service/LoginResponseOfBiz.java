package it.mapsgroup.gzoom.ofbiz.service;

/**
 * @author Andrea Fossi.
 */
public class LoginResponseOfBiz {
    private String sessionId;
    private String firstName;
    private String lastName;

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
