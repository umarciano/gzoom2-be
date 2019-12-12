package it.memelabs.gn.services.authorization;

import it.memelabs.gn.util.GnServiceUtil;
import org.ofbiz.base.util.Debug;
import org.ofbiz.service.DispatchContext;
import org.ofbiz.service.ServiceUtil;

import java.util.Map;

/**
 * 16/05/2014
 *
 * @author Andrea Fossi
 */
public class AuthorizationJobService {
    private static final String module = AuthorizationJobService.class.getName();

    /**
     * Retrieves a list of companies identified by the input taxIdentificationNumber, candidated for
     * holder company in a draft authorization.
     *
     * @param ctx
     * @param context (taxIdentificationNumber)
     * @return partyNodes
     */
    public static Map<String, Object> gnScheduleAuthorizationExpirationRemindJobsForPreviousAuthorization(DispatchContext ctx, Map<String, Object> context) {
        try {
            AuthorizationJobHelper authorizationJobHelper = new AuthorizationJobHelper(ctx, context);
            authorizationJobHelper.gnScheduleAuthorizationExpirationRemindJobsForPreviousAuthorization();
            Map<String, Object> result = ServiceUtil.returnSuccess();
            return result;
        } catch (Throwable e) {
            Debug.logError(e, module);
            return GnServiceUtil.returnError(e);
        }
    }

    public static Map<String, Object> gnCheckInvitedCompanyHasPublishedAuthorization(DispatchContext ctx, Map<String, Object> context) {
        try {
            AuthorizationJobHelper authorizationJobHelper = new AuthorizationJobHelper(ctx, context);
            boolean ret = authorizationJobHelper.gnCheckInvitedCompanyHasPublishedAuthorization((String) context.get("ownerNodeId"));
            Map<String, Object> result = ServiceUtil.returnSuccess();
            result.put("hasPublished", (ret) ? "Y" : "N");
            return result;
        } catch (Throwable e) {
            Debug.logError(e, module);
            return GnServiceUtil.returnError(e);
        }
    }

    public static Map<String, Object> gnConsumeValidateAuthorizationRemindJob(DispatchContext ctx, Map<String, Object> context) {
        try {
            AuthorizationJobHelper authorizationJobHelper = new AuthorizationJobHelper(ctx, context);
            String jobId = (String) context.get("jobId");
            authorizationJobHelper.gnConsumeValidateAuthorizationRemindJob(jobId);
            Map<String, Object> result = ServiceUtil.returnSuccess();
            return result;
        } catch (Throwable e) {
            Debug.logError(e, module);
            return GnServiceUtil.returnError(e);
        }
    }

    public static Map<String, Object> gnSchedulePreviousAuthorizationValidateRemindJobs(DispatchContext ctx, Map<String, Object> context) {
        try {
            AuthorizationJobHelper authorizationJobHelper = new AuthorizationJobHelper(ctx, context);
            authorizationJobHelper.gnSchedulePreviousAuthorizationValidateRemindJobs();
            Map<String, Object> result = ServiceUtil.returnSuccess();
            return result;
        } catch (Throwable e) {
            Debug.logError(e, module);
            return GnServiceUtil.returnError(e);
        }
    }


}
