package it.memelabs.gn.services.user;

import it.memelabs.gn.util.GnServiceUtil;
import org.ofbiz.base.util.Debug;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.service.DispatchContext;
import org.ofbiz.service.ServiceUtil;

import java.util.List;
import java.util.Map;

/**
 * 17/05/13
 *
 * @author Andrea Fossi
 */
public class GnLoginServices {
    private static final String module = GnLoginServices.class.getName();

    /**
     * @param ctx
     * @param context
     * @return
     */
    public static Map<String, Object> gnUpdateUserLoginOriginId(DispatchContext ctx, Map<String, ?> context) {
        try {
            Map<String, Object> result = ServiceUtil.returnSuccess();
            new GnLoginHelper(ctx, context).updateUserLoginOriginId((String) context.get("userLoginId"), (String) context.get("originId"));
            return result;
        } catch (Throwable t) {
            Debug.logError(t, module);
            return GnServiceUtil.returnError(t);
        }
    }


    /**
     * @param ctx
     * @param context
     * @return
     */
    public static Map<String, Object> gnFindLoginSourceId(DispatchContext ctx, Map<String, ?> context) {
        try {
            Map<String, Object> result = ServiceUtil.returnSuccess();
            String userLoginId = (String) context.get("userLoginId");
            List<String> ret = new GnLoginHelper(ctx, context).findLoginSourceId(userLoginId);
            result.put("loginSourceIds", ret);
            return result;
        } catch (Throwable t) {
            Debug.logError(t, module);
            return GnServiceUtil.returnError(t);
        }
    }

    /**
     * @param ctx
     * @param context
     * @return
     */
    public static Map<String, Object> gnCheckLoginSourceId(DispatchContext ctx, Map<String, ?> context) {
        try {
            Map<String, Object> result = ServiceUtil.returnSuccess();
            String userLoginId = (String) context.get("userLoginId");
            String loginSourceId = (String) context.get("loginSourceId");
            boolean isSuccess = new GnLoginHelper(ctx, context).checkLoginSourceId(userLoginId, loginSourceId);
            result.put("success", (isSuccess) ? "Y" : "N");
            return result;
        } catch (Throwable t) {
            Debug.logError(t, module);
            return GnServiceUtil.returnError(t);
        }
    }

    /**
     * @param ctx
     * @param context
     * @return
     */
    public static Map<String, Object> gnAddUserLoginLoginSourceId(DispatchContext ctx, Map<String, ?> context) {
        try {
            Map<String, Object> result = ServiceUtil.returnSuccess();
            String userLoginId = (String) context.get("userLoginId");
            String loginSourceId = (String) context.get("loginSourceId");
            new GnLoginHelper(ctx, context).addUserLoginLoginSourceId(userLoginId, loginSourceId);
            return result;
        } catch (Throwable t) {
            Debug.logError(t, module);
            return GnServiceUtil.returnError(t);
        }
    }

    /**
     * @param ctx
     * @param context
     * @return
     */
    public static Map<String, Object> gnCreateUserLoginDeviceUniqueId(DispatchContext ctx, Map<String, ?> context) {
        try {
            Map<String, Object> result = ServiceUtil.returnSuccess();
            String userLoginId = (String) context.get("userLoginId");
            String deviceId = (String) context.get("deviceId");
            String isActive = (String) context.get("isActive");
            String deviceDescription = (String) context.get("deviceDescription");
            String appId = (String) context.get("appId");
            String appVersion = (String) context.get("appVersion");
            new GnLoginHelper(ctx, context).createUserLoginDeviceUniqueId(userLoginId, deviceId, isActive,deviceDescription,appId,appVersion);
            return result;
        } catch (Throwable t) {
            Debug.logError(t, module);
            return GnServiceUtil.returnError(t);
        }
    }

    /**
     * @param ctx
     * @param context
     * @return
     */
    public static Map<String, Object> gnAddActiveUserLoginDeviceUniqueId(DispatchContext ctx, Map<String, ?> context) {
        try {
            Map<String, Object> result = ServiceUtil.returnSuccess();
            String userLoginId = (String) context.get("userLoginId");
            String deviceId = (String) context.get("deviceId");
            String deviceDescription = (String) context.get("deviceDescription");
            String appId = (String) context.get("appId");
            String appVersion = (String) context.get("appVersion");
            new GnLoginHelper(ctx, context).addActiveUserLoginDeviceUniqueId(userLoginId, deviceId,deviceDescription,appId,appVersion);
            return result;
        } catch (Throwable t) {
            Debug.logError(t, module);
            return GnServiceUtil.returnError(t);
        }
    }

    /**
     * @param ctx
     * @param context
     * @return
     */
    public static Map<String, Object> gnUpdateUserLoginDeviceUniqueId(DispatchContext ctx, Map<String, ?> context) {
        try {
            Map<String, Object> result = ServiceUtil.returnSuccess();
            String userLoginId = (String) context.get("userLoginId");
            String deviceId = (String) context.get("deviceId");
            String isActive = (String) context.get("isActive");

            String deviceDescription = (String) context.get("deviceDescription");
            String appId = (String) context.get("appId");
            String appVersion = (String) context.get("appVersion");

            new GnLoginHelper(ctx, context).updateUserLoginDeviceUniqueId(userLoginId, deviceId,
                    deviceDescription, appId, appVersion,
                    isActive);
            return result;
        } catch (Throwable t) {
            Debug.logError(t, module);
            return GnServiceUtil.returnError(t);
        }
    }

    /**
     * @param ctx
     * @param context
     * @return
     */
    public static Map<String, Object> gnFindUserLoginDeviceUniqueIds(DispatchContext ctx, Map<String, ?> context) {
        try {
            Map<String, Object> result = ServiceUtil.returnSuccess();
            String userLoginId = (String) context.get("userLoginId");
            String deviceId = (String) context.get("deviceId");
            String isActive = (String) context.get("isActive");
            List<GenericValue> ret = new GnLoginHelper(ctx, context).findUserLoginDeviceUniqueIds(userLoginId, deviceId, isActive);
            result.put("list", ret);
            result.put("listSize", ret.size());
            return result;
        } catch (Throwable t) {
            Debug.logError(t, module);
            return GnServiceUtil.returnError(t);
        }
    }

}
