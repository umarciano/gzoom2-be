package it.memelabs.smartnebula.lmm.persistence.main.dao;

import it.memelabs.smartnebula.lmm.persistence.main.enumeration.EntityStateTag;

import java.util.Date;
import java.util.List;

/**
 * @author Andrea Fossi.
 */
public class WorkLogEventUpdateFilter {
    private Long workLogId;
    private Long destinationWorkLogId;
    private Long wbsId;
    private Long stateId;
    private EntityStateTag stateTag;
    private boolean wbsIdNull;
    private List<Long> events;
    private Date modifiedStamp;
    private Long modifiedByUserId;

    public Long getWorkLogId() {
        return workLogId;
    }

    public void setWorkLogId(Long workLogId) {
        this.workLogId = workLogId;
    }

    public Long getWbsId() {
        return wbsId;
    }

    public void setWbsId(Long wbsId) {
        this.wbsId = wbsId;
    }

    public Long getStateId() {
        return stateId;
    }

    public void setStateId(Long stateId) {
        this.stateId = stateId;
    }

    public List<Long> getEvents() {
        return events;
    }

    public void setEvents(List<Long> events) {
        this.events = events;
    }

    public boolean isWbsIdNull() {
        return wbsIdNull;
    }

    public void setWbsIdNull(boolean wbsIdNull) {
        this.wbsIdNull = wbsIdNull;
    }

    public EntityStateTag getStateTag() {
        return stateTag;
    }

    public void setStateTag(EntityStateTag stateTag) {
        this.stateTag = stateTag;
    }

    public Date getModifiedStamp() {
        return modifiedStamp;
    }

    public void setModifiedStamp(Date modifiedStamp) {
        this.modifiedStamp = modifiedStamp;
    }

    public Long getModifiedByUserId() {
        return modifiedByUserId;
    }

    public void setModifiedByUserId(Long modifiedByUserId) {
        this.modifiedByUserId = modifiedByUserId;
    }

    public Long getDestinationWorkLogId() {
        return destinationWorkLogId;
    }

    public void setDestinationWorkLogId(Long destinationWorkLogId) {
        this.destinationWorkLogId = destinationWorkLogId;
    }
}
