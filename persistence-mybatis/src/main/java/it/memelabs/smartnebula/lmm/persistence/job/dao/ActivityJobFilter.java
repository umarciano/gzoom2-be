package it.memelabs.smartnebula.lmm.persistence.job.dao;

import it.memelabs.smartnebula.lmm.persistence.enumeration.JobStatus;
import it.memelabs.smartnebula.lmm.persistence.enumeration.JobType;
import it.memelabs.smartnebula.lmm.persistence.main.dao.BaseFilter;

import java.util.Date;

/**
 * @author Andrea Fossi.
 */
public class ActivityJobFilter extends BaseFilter {
    private JobStatus status;
    private JobType type;
    private Long referenceId;
    private Date scheduledSince;
    private Date scheduledUntil;

    public JobStatus getStatus() {
        return status;
    }

    public void setStatus(JobStatus status) {
        this.status = status;
    }

    public JobType getType() {
        return type;
    }

    public void setType(JobType type) {
        this.type = type;
    }

    public Long getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(Long referenceId) {
        this.referenceId = referenceId;
    }

    public Date getScheduledSince() {
        return scheduledSince;
    }

    public void setScheduledSince(Date scheduledSince) {
        this.scheduledSince = scheduledSince;
    }

    public Date getScheduledUntil() {
        return scheduledUntil;
    }

    public void setScheduledUntil(Date scheduledUntil) {
        this.scheduledUntil = scheduledUntil;
    }


}
