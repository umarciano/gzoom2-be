package it.memelabs.smartnebula.lmm.persistence.main.dao;

/**
 * @author Andrea Fossi.
 */
public class EmploymentToExportFilter extends BaseFilter {
    private Long sessionId;

    public Long getSessionId() {
        return sessionId;
    }

    public void setSessionId(Long sessionId) {
        this.sessionId = sessionId;
    }
}
