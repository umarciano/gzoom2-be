package it.memelabs.gn.services.job;

import it.memelabs.gn.util.GnServiceUtil;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.GeneralException;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.service.DispatchContext;
import org.ofbiz.service.GenericServiceException;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

/**
 * 06/05/2014
 *
 * @author Andrea Fossi
 */
public class JobServices {
    private static final String module = JobServices.class.getName();

    public static Map<String, Object> gnCreateJob(DispatchContext ctx, Map<String, ? extends Object> context) throws GenericEntityException, GenericServiceException {
        try {
            JobHelper jobHelper = new JobHelper(ctx, context);
            JobStatusOfbiz jobStatusId = JobStatusOfbiz.GN_ACT_JOB_SCHEDULED;

            if (UtilValidate.isNotEmpty(context.get("jobStatusId")))
                jobStatusId = JobStatusOfbiz.valueOf((String) context.get("jobStatusId"));

            JobTypeOfbiz jobTypeId = JobTypeOfbiz.valueOf((String) context.get("jobTypeId"));
            String commitRequired = (String) context.get("commitRequired");
            String referenceKey = (String) context.get("referenceKey");
            Long expectedAck = (Long) context.get("expectedAck");
            Timestamp scheduled = (Timestamp) context.get("scheduled");
            @SuppressWarnings("unchecked")
            Map<String, Object> attributes = UtilMisc.getMapFromMap((Map<String, Object>) context, "attributes");
            String jobId = jobHelper.gnCreateJob(jobStatusId, jobTypeId, commitRequired, referenceKey, expectedAck, scheduled, attributes);
            Map<String, Object> result = GnServiceUtil.returnSuccess();
            result.put("jobId", jobId);
            return result;
        } catch (GeneralException e) {
            Debug.logError(e, module);
            return GnServiceUtil.returnError(e);
        }
    }

    public static Map<String, Object> gnUpdateJobStatus(DispatchContext ctx, Map<String, ? extends Object> context) throws GenericEntityException, GenericServiceException {
        try {
            JobHelper jobHelper = new JobHelper(ctx, context);
            JobStatusOfbiz jobStatusId = JobStatusOfbiz.valueOf((String) context.get("jobStatusId"));
            String jobId = (String) context.get("jobId");
            String failMessage = (String) context.get("failMessage");
            jobHelper.gnUpdateJobStatus(jobId, jobStatusId, failMessage);
            Map<String, Object> result = GnServiceUtil.returnSuccess();
            return result;
        } catch (GeneralException e) {
            Debug.logError(e, module);
            return GnServiceUtil.returnError(e);
        }
    }

    public static Map<String, Object> gnUpdateJobStatusByReferenceKey(DispatchContext ctx, Map<String, ? extends Object> context) {
        try {
            JobHelper jobHelper = new JobHelper(ctx, context);
            JobStatusOfbiz jobStatusId = JobStatusOfbiz.valueOf((String) context.get("jobStatusId"));
            String referenceKey = (String) context.get("referenceKey");
            String failMessage = (String) context.get("failMessage");
            String scheduledOnly = (String) context.get("scheduledOnly");
            jobHelper.gnUpdateJobStatusByReferenceKey(referenceKey, jobStatusId, failMessage, scheduledOnly);
            Map<String, Object> result = GnServiceUtil.returnSuccess();
            return result;
        } catch (GeneralException e) {
            Debug.logError(e, module);
            return GnServiceUtil.returnError(e);
        }
    }

    public static Map<String, Object> gnNotifyResult(DispatchContext ctx, Map<String, ? extends Object> context) {
        try {
            JobHelper jobHelper = new JobHelper(ctx, context);
            JobResultOfbiz jobResultId = JobResultOfbiz.valueOf((String) context.get("jobResultId"));
            String jobId = (String) context.get("jobId");
            String failMessage = (String) context.get("failMessage");
            jobHelper.gnNotifyResult(jobId, jobResultId, failMessage);
            Map<String, Object> result = GnServiceUtil.returnSuccess();
            return result;
        } catch (GeneralException e) {
            Debug.logError(e, module);
            return GnServiceUtil.returnError(e);
        }
    }

    /**
     * @param ctx
     * @param context
     * @return
     * @throws GenericEntityException
     * @throws GenericServiceException
     */
    public static Map<String, Object> gnConsumeJobs(DispatchContext ctx, Map<String, ? extends Object> context) {
        try {
            JobHelper jobHelper = new JobHelper(ctx, context);
            Long jobToConsume = (Long) context.get("jobToConsume");
            List<Map<String, Object>> ret = jobHelper.gnConsumeJobs(jobToConsume);
            Map<String, Object> result = GnServiceUtil.returnSuccess();
            result.put("consumedJobList", ret);
            result.put("consumedJobListSize", ret.size());
            return result;
        } catch (GeneralException e) {
            Debug.logError(e, module);
            return GnServiceUtil.returnError(e);
        }
    }

    /**
     * @param ctx
     * @param context
     * @return
     * @throws GenericEntityException
     * @throws GenericServiceException
     */
    public static Map<String, Object> gnTestConsumeJobs(DispatchContext ctx, Map<String, ? extends Object> context) {
        try {
            JobHelper jobHelper = new JobHelper(ctx, context);
            Long jobToConsume = (Long) context.get("jobToConsume");
            Timestamp dateToConsume = (Timestamp) context.get("dateToConsume");
            List<Map<String, Object>> ret = jobHelper.gnTestConsumeJobs(jobToConsume, dateToConsume);
            Map<String, Object> result = GnServiceUtil.returnSuccess();
            result.put("consumedJobList", ret);
            result.put("consumedJobListSize", ret.size());
            return result;
        } catch (GeneralException e) {
            Debug.logError(e, module);
            return GnServiceUtil.returnError(e);
        }
    }

    /**
     * @param ctx
     * @param context
     * @return
     * @throws GenericEntityException
     * @throws GenericServiceException
     */
    public static Map<String, Object> gnFindJobs(DispatchContext ctx, Map<String, ? extends Object> context) {
        try {
            JobHelper jobHelper = new JobHelper(ctx, context);
            String jobStatusId = (String) context.get("jobStatusId");
            String referenceKey = (String) context.get("referenceKey");
            String jobId = (String) context.get("jobId");
            String jobTypeId = (String) context.get("jobTypeId");
            String fetchJobAttributes = (String) context.get("fetchJobAttributes");
            List<Map<String, Object>> ret = jobHelper.gnFindJobs(jobId, jobStatusId, jobTypeId, referenceKey, fetchJobAttributes);
            Map<String, Object> result = GnServiceUtil.returnSuccess();
            result.put("jobList", ret);
            result.put("jobListSize", ret.size());
            return result;
        } catch (GeneralException e) {
            Debug.logError(e, module);
            return GnServiceUtil.returnError(e);
        }
    }


}
