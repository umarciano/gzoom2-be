package it.memelabs.gn.webapp.event;

import it.memelabs.gn.services.auditing.EntityTypeOfbiz;
import org.ofbiz.base.util.UtilMisc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 12/07/13
 *
 * @author Andrea Fossi
 */
public class AuditEvent {

    private String type;
    private Map<String, Object> oldValue;
    private Map<String, Object> newValue;
    private List<Map<String, Object>> fallback;

    public AuditEvent(String type, Map<String, Object> oldValue, Map<String, Object> newValue, List<Map<String, Object>> fallback) {
        this.fallback = fallback;
        this.newValue = newValue;
        this.oldValue = oldValue;
        this.type = type;
    }

    public AuditEvent(EntityTypeOfbiz type) {
        this.type = type.name();
    }

    public AuditEvent(String type) {
        this.type = type;
    }

    public AuditEvent(String type, Map<String, Object> oldValue) {
        this.oldValue = oldValue;
        this.type = type;
    }

    public List<Map<String, Object>> getFallback() {
        if (fallback == null) return new ArrayList<Map<String, Object>>();
        else
            return fallback;
    }

    public void setFallback(List<Map<String, Object>> fallback) {
        this.fallback = fallback;
    }

    public void addFallback(String id, EntityTypeOfbiz type) {
        if (fallback == null)
            fallback = new ArrayList<Map<String, Object>>();
        fallback.add(UtilMisc.toMap("id", id, "type", (Object) type.name()));
    }

    public void addFallback(String id, EntityTypeOfbiz type, String description) {
        if (fallback == null)
            fallback = new ArrayList<Map<String, Object>>();
        fallback.add(UtilMisc.toMap("id", id, "type", (Object) type.name(), "description", description));
    }

    public Map<String, Object> getNewValue() {
        if (newValue == null) return new HashMap<String, Object>();
        else
            return newValue;
    }

    public void setNewValue(Map<String, Object> newValue) {
        this.newValue = newValue;
    }

    public Map<String, Object> getOldValue() {
        if (oldValue == null) return new HashMap<String, Object>();
        else
            return oldValue;
    }

    public void setOldValue(Map<String, Object> oldValue) {
        this.oldValue = oldValue;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Map<String, Object> toMap() {
        Map<String, Object> entry = new HashMap<String, Object>();
        entry.put("type", this.getType());
        entry.put("old", this.getOldValue());
        entry.put("new", this.getNewValue());
        entry.put("fallback", this.getFallback());
        return entry;
    }

}
