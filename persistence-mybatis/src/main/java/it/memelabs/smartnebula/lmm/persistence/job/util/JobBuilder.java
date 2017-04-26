package it.memelabs.smartnebula.lmm.persistence.job.util;

import it.memelabs.smartnebula.lmm.persistence.enumeration.AttributeType;
import it.memelabs.smartnebula.lmm.persistence.enumeration.JobStatus;
import it.memelabs.smartnebula.lmm.persistence.enumeration.JobType;
import it.memelabs.smartnebula.lmm.persistence.enumeration.ReferenceObject;
import it.memelabs.smartnebula.lmm.persistence.main.dto.ActivityJobAttribute;
import it.memelabs.smartnebula.lmm.persistence.main.dto.ActivityJobEx;

import java.util.Date;

/**
 * @author Andrea Fossi.
 */
public class JobBuilder {
    private final ActivityJobEx job;

    private JobBuilder(ActivityJobEx job) {
        this.job = job;
    }

    public static JobBuilder newInstance(JobType jobType, Date scheduledStamp, Long referenceId, ReferenceObject referenceObject, Long ownerNodeId, boolean commitRequired) {
        ActivityJobEx job = new ActivityJobEx();
        JobBuilder builder = new JobBuilder(job);
        job.setType(jobType);
        job.setReferenceObject(referenceObject);
        job.setReferenceId(referenceId);
        job.setCommitRequired(commitRequired);
        job.setScheduledStamp(scheduledStamp);
        job.setStatus(JobStatus.SCHEDULED);
        job.setOwnerNodeId(ownerNodeId);
        return builder;
    }

    public JobBuilder withAttribute(String attrName, String attrValue) {
        ActivityJobAttribute att = createAttribute(attrName, attrValue, AttributeType.STRING);
        job.getAttributes().add(att);
        return this;
    }

    public JobBuilder withAttribute(String attrName, Object attrValue) {
        String xml = JobUtil.toXml(attrValue);
        ActivityJobAttribute att = createAttribute(attrName, xml, AttributeType.XML);
        job.getAttributes().add(att);
        return this;
    }

    public ActivityJobEx build() {
        return job;
    }

    private ActivityJobAttribute createAttribute(String attrName, String attrValue, AttributeType attrTypeId) {
        ActivityJobAttribute ret = new ActivityJobAttribute();
        ret.setAttrValue(attrValue);
        ret.setAttrTypeId(attrTypeId);
        ret.setAttrName(attrName);
        return ret;
    }
}
