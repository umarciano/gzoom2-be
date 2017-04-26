package it.memelabs.smartnebula.lmm.persistence.main.dao;


import it.memelabs.smartnebula.lmm.persistence.enumeration.EventStatus;

import java.util.Date;

public class NotificationEventFilter extends BaseFilter {
    private Date promiseDateEnd;
    private Boolean enabled;
    private EventStatus status;

    public Date getPromiseDateEnd() {
        return promiseDateEnd;
    }

    public void setPromiseDateEnd(Date promiseDateEnd) {
        this.promiseDateEnd = promiseDateEnd;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public EventStatus getStatus() {
        return status;
    }

    public void setStatus(EventStatus status) {
        this.status = status;
    }
}
