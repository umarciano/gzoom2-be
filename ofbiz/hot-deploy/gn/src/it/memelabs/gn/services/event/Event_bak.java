package it.memelabs.gn.services.event;

import java.util.HashMap;
import java.util.Map;

/**
 * 10/01/14
 *
 * @author Andrea Fossi
 */
public class Event_bak extends HashMap<String, Object> {


    private Map<String, Object> attributes;

    public Event_bak init(EventMessageTypeOfBiz eventType) {
        setType(eventType);
        return init();
    }

    public Event_bak init() {
        setId(EventMessageUtil.getSingleton().getNextId());
        setTimestamp(System.currentTimeMillis());
        return this;
    }

    public Event_bak() {
        attributes = new HashMap<String, Object>();
        attributes.put("timestamp", null);
    }

    public Event_bak(Map<String, Object> attributes) {
        this.attributes.putAll(attributes);
    }

    public void setId(long id) {
        attributes.put("id", id);
    }

    public void setType(EventMessageTypeOfBiz kind) {
        attributes.put("eventType", (kind != null) ? kind.toString() : null);
    }

    public void setSender(String sender) {
        attributes.put("sender", sender);
    }

    public void setTimestamp(long timestamp) {
        attributes.put("timestamp", timestamp);
    }

    public EventMessageTypeOfBiz getKind() {
        String eventKind = (String) attributes.get("eventType");
        if (eventKind != null) return EventMessageTypeOfBiz.valueOf(eventKind);
        else return null;
    }

    public String getId() {
        return (String) attributes.get("id");
    }

    public String getSender() {
        return (String) attributes.get("sender");
    }

    public Long getTimestamp() {
        return (Long) attributes.get("timestamp");
    }


}
