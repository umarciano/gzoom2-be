package it.memelabs.gn.services.system;

import it.memelabs.gn.util.GnServiceUtil;
import org.ofbiz.base.util.Debug;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.service.DispatchContext;
import org.ofbiz.service.ServiceUtil;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

/**
 * 22/10/13
 *
 * @author Andrea Fossi
 */
public class GnSystemServices {
    private static final String module = GnSystemServices.class.getName();

    /**
     * @param ctx
     * @param context
     * @return
     */
    public static Map<String, Object> gnFindApplicationProperty(DispatchContext ctx, Map<String, ?> context) {
        try {
            Map<String, Object> result = ServiceUtil.returnSuccess();
            result.putAll(new GnSystemHelper(ctx, context).gnFindApplicationProperty((String) context.get("name")));
            return result;
        } catch (Exception t) {
            Debug.logError(t, module);
            return GnServiceUtil.returnError(t);
        }
    }

    /**
     * @param ctx
     * @param context
     * @return
     */
    public static Map<String, Object> gnCreateApplicationProperty(DispatchContext ctx, Map<String, ?> context) {
        try {
            Map<String, Object> result = ServiceUtil.returnSuccess();
            new GnSystemHelper(ctx, context).gnCreateApplicationProperty((String) context.get("name"), (String) context.get("value"), (String) context.get("comment"));
            return result;
        } catch (Exception t) {
            Debug.logError(t, module);
            return GnServiceUtil.returnError(t);
        }
    }

    /**
     * @param ctx
     * @param context
     * @return
     */
    public static Map<String, Object> gnUpdateApplicationProperty(DispatchContext ctx, Map<String, ?> context) {
        try {
            Map<String, Object> result = ServiceUtil.returnSuccess();
            new GnSystemHelper(ctx, context).gnUpdateApplicationProperty((String) context.get("name"), (String) context.get("value"), (String) context.get("comment"));
            return result;
        } catch (Exception t) {
            Debug.logError(t, module);
            return GnServiceUtil.returnError(t);
        }
    }

    /**
     * @param ctx
     * @param context
     * @return
     */
    public static Map<String, Object> gnRemoveApplicationProperty(DispatchContext ctx, Map<String, ?> context) {
        try {
            Map<String, Object> result = ServiceUtil.returnSuccess();
            result.putAll(new GnSystemHelper(ctx, context).gnRemoveApplicationProperty((String) context.get("name")));
            return result;
        } catch (Exception t) {
            Debug.logError(t, module);
            return GnServiceUtil.returnError(t);
        }
    }

    /**
     * @param ctx
     * @param context
     * @return
     */
    public static Map<String, Object> gnFindServiceInvocationLog(DispatchContext ctx, Map<String, ?> context) {
        try {
            Map<String, Object> result = ServiceUtil.returnSuccess();
            List<GenericValue> invocationLogs = new GnSystemHelper(ctx, context).gnFindServiceInvocationLog((String) context.get("serviceName"),
                    (String) context.get("userLoginId"), (String) context.get("contextId"));
            result.put("list", invocationLogs);
            result.put("listSize", invocationLogs.size());
            return result;
        } catch (Exception t) {
            Debug.logError(t, module);
            return GnServiceUtil.returnError(t);
        }
    }

    /**
     * @param ctx
     * @param context
     * @return
     */
    public static Map<String, Object> gnCreateServiceInvocationLog(DispatchContext ctx, Map<String, ?> context) {
        try {
            Map<String, Object> result = ServiceUtil.returnSuccess();
            result.putAll(new GnSystemHelper(ctx, context).gnCreateServiceInvocationLog((String) context.get("serviceName"),
                    (String) context.get("userLoginId"), (String) context.get("contextId"), (Timestamp) context.get("invocationDate")));
            return result;
        } catch (Exception t) {
            Debug.logError(t, module);
            return GnServiceUtil.returnError(t);
        }
    }

}
