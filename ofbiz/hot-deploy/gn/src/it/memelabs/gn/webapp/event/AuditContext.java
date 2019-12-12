package it.memelabs.gn.webapp.event;

import java.util.HashMap;
import java.util.Map;

/**
 * 16/04/14
 *
 * @author Elisa Spada
 */
public class AuditContext {

    private String userLoginId;
    private String contextId;
    private String contextDescription;

    public AuditContext(String contextDescription, String contextId, String userLoginId) {
        this.contextDescription = contextDescription;
        this.contextId = contextId;
        this.userLoginId = userLoginId;
    }

    public Map<String, Object> toMap() {
        Map<String, Object> auditContext = new HashMap<String, Object>();
        auditContext.put("userLoginId", userLoginId);
        auditContext.put("contextId", contextId);
        auditContext.put("contextDescription", contextDescription);
        return auditContext;
    }

}
