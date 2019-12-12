package it.memelabs.gn.services.job;

import it.memelabs.gn.services.AbstractServiceHelper;
import it.memelabs.gn.services.AttributeTypeOfbiz;
import it.memelabs.gn.util.GnServiceException;
import it.memelabs.gn.util.XmlUtil;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilDateTime;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.entity.condition.EntityComparisonOperator;
import org.ofbiz.entity.condition.EntityCondition;
import org.ofbiz.service.DispatchContext;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 06/05/2014
 *
 * @author Andrea Fossi
 */
public class JobHelper extends AbstractServiceHelper {
    private static final String module = JobHelper.class.getName();

    /**
     * @param dctx
     * @param context
     */
    public JobHelper(DispatchContext dctx, Map<String, ? extends Object> context) {
        super(dctx, context);
    }

    /**
     * @param jobStatusId
     * @param jobTypeId
     * @param commitRequired
     * @param referenceKey
     * @param expectedAck
     * @param scheduled
     * @param attributes
     * @return
     * @throws GenericEntityException
     */
    public String gnCreateJob(JobStatusOfbiz jobStatusId, JobTypeOfbiz jobTypeId,
                              String commitRequired, String referenceKey,
                              long expectedAck, Timestamp scheduled,
                              Map<String, Object> attributes) throws GenericEntityException, GnServiceException {
        GenericValue job = delegator.makeValue("GnActivityJob");
        job.setNextSeqId();
        job.set("jobStatusId", jobStatusId.name());
        job.set("jobTypeId", jobTypeId.name());
        job.set("commitRequired", commitRequired);
        job.set("referenceKey", referenceKey);
        job.set("expectedAck", expectedAck);
        job.set("fail", 0l);
        job.set("success", 0l);
        job.set("failMessage", null);
        job.set("consumed", null);
        job.set("scheduled", scheduled);
        delegator.create(job);
        String jobId = job.getString("jobId");
        if (UtilValidate.isNotEmpty(attributes))
            createAttributes(jobId, attributes);
        return jobId;
    }

    /**
     * @param jobId
     * @param jobStatusId
     * @param failMessage
     * @throws GenericEntityException
     */
    public void gnUpdateJobStatus(String jobId, JobStatusOfbiz jobStatusId, String failMessage) throws GenericEntityException {
        GenericValue job = delegator.findByPrimaryKey("GnActivityJob", UtilMisc.toMap("jobId", jobId));
        job.set("jobStatusId", jobStatusId.name());
        if (UtilValidate.isNotEmpty(failMessage))
            job.set("failMessage", failMessage);
        delegator.store(job);
    }

    /**
     * @param referenceKey
     * @param jobStatusId
     * @param failMessage
     * @param scheduledOnly
     * @throws GenericEntityException
     */
    public void gnUpdateJobStatusByReferenceKey(String referenceKey, JobStatusOfbiz jobStatusId, String failMessage, String scheduledOnly) throws GenericEntityException {
        Map<String, String> condition = UtilMisc.toMap("referenceKey", referenceKey);
        if ("Y".equals(scheduledOnly)) condition.put("jobStatusId", JobStatusOfbiz.GN_ACT_JOB_SCHEDULED.name());
        List<GenericValue> jobs = delegator.findByAnd("GnActivityJob", condition);
        for (GenericValue job : jobs) {
            job.set("jobStatusId", jobStatusId.name());
            if (UtilValidate.isNotEmpty(failMessage))
                job.set("failMessage", failMessage);
            delegator.store(job);
        }
    }

    /**
     * @param jobId
     * @param jobResultId
     * @param failMessage
     * @throws GenericEntityException
     */
    public void gnNotifyResult(String jobId, JobResultOfbiz jobResultId, String failMessage) throws GenericEntityException {
        GenericValue job = delegator.findByPrimaryKey("GnActivityJob", UtilMisc.toMap("jobId", jobId));
        if (UtilValidate.isNotEmpty(failMessage))
            job.set("failMessage", failMessage);
        if (jobResultId == JobResultOfbiz.SUCCESS) {
            job.set("success", (Long) job.get("success") + 1);
        } else { //JobResultOfbiz.FAILED
            job.set("fail", (Long) job.get("fail") + 1);
        }
        long success = (Long) job.get("success");
        long fail = (Long) job.get("fail");
        long expectedAck = (Long) job.get("expectedAck");

        if ((success + fail) >= expectedAck) {
            if (fail > 0l)
                job.set("jobStatusId", JobStatusOfbiz.GN_ACT_JOB_FAILED.name());
            else
                job.set("jobStatusId", JobStatusOfbiz.GN_ACT_JOB_COMMITTED.name());
        } else {
            job.set("jobStatusId", JobStatusOfbiz.GN_ACT_JOB_INCOMPLETE.name());
        }

        delegator.store(job);
    }

    /**
     * @param n
     * @return
     * @throws GenericEntityException
     */
    public List<Map<String, Object>> gnConsumeJobs(long n) throws GenericEntityException {
        List<EntityCondition> conds = new ArrayList<EntityCondition>();
        conds.add(EntityCondition.makeCondition("jobStatusId", JobStatusOfbiz.GN_ACT_JOB_SCHEDULED.name()));
        Timestamp dayEnd = UtilDateTime.getDayEnd(UtilDateTime.nowTimestamp());
        conds.add(EntityCondition.makeCondition("scheduled", EntityComparisonOperator.LESS_THAN_EQUAL_TO, dayEnd));

        List<GenericValue> jobs = delegator.findList("GnActivityJob", EntityCondition.makeCondition(conds),
                null, UtilMisc.toList("-scheduled"), null, false);
        int i = 0;
        List<Map<String, Object>> ret = new ArrayList<Map<String, Object>>();
        while (i < n && i < jobs.size()) {
            Map<String, Object> jobResult = consumeJob(jobs.get(i).getString("jobId"));
            if (UtilValidate.isNotEmpty(jobResult)) ret.add(jobResult);
            i++;
        }
        return ret;
    }

    /**
     * @param n
     * @return
     * @throws GenericEntityException
     */
    public List<Map<String, Object>> gnTestConsumeJobs(long n, Timestamp dateToConsume) throws GenericEntityException {
        List<EntityCondition> conds = new ArrayList<EntityCondition>();
        conds.add(EntityCondition.makeCondition("jobStatusId", JobStatusOfbiz.GN_ACT_JOB_SCHEDULED.name()));
        Timestamp dayEnd = UtilDateTime.getDayEnd(dateToConsume);
        conds.add(EntityCondition.makeCondition("scheduled", EntityComparisonOperator.LESS_THAN_EQUAL_TO, dayEnd));

        List<GenericValue> jobs = delegator.findList("GnActivityJob", EntityCondition.makeCondition(conds),
                null, UtilMisc.toList("-scheduled"), null, false);
        int i = 0;
        List<Map<String, Object>> ret = new ArrayList<Map<String, Object>>();
        while (i < n && i < jobs.size()) {
            Map<String, Object> jobResult = consumeJob(jobs.get(i).getString("jobId"));
            if (UtilValidate.isNotEmpty(jobResult)) ret.add(jobResult);
            i++;
        }
        return ret;
    }


    /**
     * @param jobId
     * @return
     * @throws org.ofbiz.entity.GenericEntityException
     */
    public Map<String, Object> consumeJob(String jobId) throws GenericEntityException {
      /*  GenericValue job = delegator.findOne("GnActivityJob", false, "jobId", jobId);
        Map<String, String> jobAttributes = findAttributes(job.getString("jobId"));*/
        Debug.log(String.format("Consuming job[%s] ", jobId));
        Map<String, Object> result;
        try {
            result = dispatcher.runSync("gnConsumeJob", UtilMisc.toMap("userLogin", userLogin, "jobId", jobId), 3000, true);
        } catch (Exception e) {
            // mange error    and update status to fail
            Debug.logError(e, "Error on consuming job", module);
            String message = "[] " + e.getMessage();
            if (e instanceof GnServiceException)
                message = "[" + ((GnServiceException) e).getCode() + "] " + e.getMessage();
            gnUpdateJobStatus(jobId, JobStatusOfbiz.GN_ACT_JOB_FAILED, message);
            result = UtilMisc.makeMapWritable(findJobById(jobId));
        }
        return result;
    }


    /**
     * @param jobId
     * @return
     * @throws GenericEntityException
     */
    protected GenericValue findJobById(String jobId) throws GenericEntityException {
        GenericValue job = delegator.findOne("GnActivityJob", false, "jobId", jobId);
        return job;
    }

    /**
     * @param jobId
     * @return
     * @throws GenericEntityException
     */
    protected Map<String, Object> findAttributes(String jobId) throws GenericEntityException, GnServiceException {
        List<GenericValue> attributes = delegator.findList("GnActivityJobAttribute", EntityCondition.makeCondition("jobId", jobId),
                UtilMisc.toSet("attrName", "attrValue", "attrTypeId"), UtilMisc.toList("attrName"), null, false);
        Map<String, Object> ret = new HashMap<String, Object>(attributes.size());
        for (GenericValue attr : attributes) {
            String attrValue = attr.getString("attrValue");
            String attrTypeId = attr.getString("attrTypeId");
            Object value;
            if (AttributeTypeOfbiz.GN_ATTR_XML.name().equals(attrTypeId) && UtilValidate.isNotEmpty(attrValue)) {
                value = XmlUtil.fromXml(attrValue);
            } else {
                value = attrValue;
            }
            ret.put(attr.getString("attrName"), value);
        }

        return ret;
    }

    private void createAttributes(String jobId, Map<String, Object> attributes) throws GenericEntityException, GnServiceException {
        for (String name : attributes.keySet()) {
            createAttribute(jobId, name, attributes.get(name));
        }
    }

    private void createAttribute(String jobId, String name, Object _value) throws GenericEntityException, GnServiceException {
        GenericValue gv = delegator.makeValue("GnActivityJobAttribute");
        String value;
        String attrTypeId;
        if (_value instanceof String) {
            value = (String) _value;
            attrTypeId = AttributeTypeOfbiz.GN_ATTR_STRING.name();
        } else {
            value = XmlUtil.toXml(_value);
            attrTypeId = AttributeTypeOfbiz.GN_ATTR_XML.name();
        }
        gv.put("jobId", jobId);//pk
        gv.put("attrName", name);//pk
        gv.put("attrValue", value);
        gv.put("attrTypeId", attrTypeId);
        delegator.create(gv);
    }

    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> gnFindJobs(String jobId, String jobStatusId, String jobTypeId, String referenceKey, String fetchJobAttributes) throws GenericEntityException, GnServiceException {
        List<EntityCondition> conds = new ArrayList<EntityCondition>();
        if (UtilValidate.isNotEmpty(jobId))
            conds.add(EntityCondition.makeCondition("jobId", jobId));
        if (UtilValidate.isNotEmpty(jobStatusId))
            conds.add(EntityCondition.makeCondition("jobStatusId", jobStatusId));
        if (UtilValidate.isNotEmpty(referenceKey))
            conds.add(EntityCondition.makeCondition("referenceKey", referenceKey));
        if (UtilValidate.isNotEmpty(jobTypeId))
            conds.add(EntityCondition.makeCondition("jobTypeId", jobTypeId));
        List<? extends Map<String, Object>> jobs = delegator.findList("GnActivityJob", EntityCondition.makeCondition(conds), null, null, null, false);
        if ("Y".equals(fetchJobAttributes)) {
            List<Map<String, Object>> ret = new ArrayList<Map<String, Object>>(jobs.size());
            for (int i = 0; i < jobs.size(); i++) {
                Map<String, Object> job = UtilMisc.makeMapWritable(jobs.get(i));
                job.put("attributes", findAttributes((String) job.get("jobId")));
                ret.add(job);
            }
            return ret;
        } else {
            return (List<Map<String, Object>>) jobs;
        }
    }
}
