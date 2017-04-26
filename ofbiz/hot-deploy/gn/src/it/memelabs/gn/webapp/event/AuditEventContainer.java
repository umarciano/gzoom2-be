package it.memelabs.gn.webapp.event;

import org.ofbiz.base.util.UtilDateTime;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Do not use this class AuditEventContainer directly!!!
 * Use AuditEventSessionHelper or migrate to EventMessageEntityAudit and use EventMessageContainer.
 */
public class AuditEventContainer {

    private Timestamp timestamp;
    private String userLoginId;
    private String contextId;
    private String contextDescription;
    private List<AuditEvent> events;

    public AuditEventContainer(String userLoginId, String contextId, String contextDescription) {
        this.timestamp = UtilDateTime.nowTimestamp();
        this.userLoginId = userLoginId;
        this.contextId = contextId;
        this.contextDescription = contextDescription;
        this.events = new ArrayList<AuditEvent>();
    }

    protected void addEvent(AuditEvent auditEvent) {
        events.add(auditEvent);
    }

    protected void addEvent(String type, Map<String, Object> oldValue, Map<String, Object> newValue, List<Map<String, Object>> fallback) {
        AuditEvent event = new AuditEvent(type, oldValue, newValue, fallback);
        events.add(event);
    }

    protected Map<String, Object> toMap() {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("timestamp", timestamp);

        Map<String, Object> auditContext = new AuditContext(contextDescription, contextId, userLoginId).toMap();
        result.put("context", auditContext);

        List<Map<String, Object>> entries = new ArrayList<Map<String, Object>>();
        for (AuditEvent event : events) {
            entries.add(event.toMap());
        }

        result.put("entries", entries);

        return result;
    }

}