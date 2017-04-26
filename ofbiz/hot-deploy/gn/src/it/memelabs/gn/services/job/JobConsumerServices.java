package it.memelabs.gn.services.job;

import it.memelabs.gn.services.event.EventMessageContainer;
import it.memelabs.gn.util.GnServiceUtil;
import org.ofbiz.base.util.Debug;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.service.DispatchContext;
import org.ofbiz.service.GenericServiceException;

import java.util.Map;

/**
 * 06/05/2014
 *
 * @author Andrea Fossi
 */
public class JobConsumerServices {
    private static final String module = JobConsumerServices.class.getName();


    public static Map<String, Object> gnConsumeJob(DispatchContext ctx, Map<String, ? extends Object> context) throws GenericEntityException, GenericServiceException {
        try {
            EventMessageContainer.clean();
            Map<String, Object> result = GnServiceUtil.returnSuccess();

            JobConsumerHelper jobHelper = new JobConsumerHelper(ctx, context);
            result.putAll(jobHelper.gnConsumeJob((String) context.get("jobId")));
            return result;
        } catch (Exception e) {
            Debug.logError(e, module);
            return GnServiceUtil.returnError(e);
        } finally {
            EventMessageContainer.clean();
        }
    }


}
