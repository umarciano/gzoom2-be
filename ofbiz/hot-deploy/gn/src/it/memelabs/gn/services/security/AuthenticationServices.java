package it.memelabs.gn.services.security;

import it.memelabs.gn.services.OfbizErrors;
import it.memelabs.gn.services.login.LoginSourceOfbiz;
import it.memelabs.gn.services.system.GnAuthenticator;
import it.memelabs.gn.services.user.UserHelper;
import it.memelabs.gn.util.GnServiceException;
import it.memelabs.gn.util.GnServiceUtil;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.GeneralException;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.base.util.cache.UtilCache;
import org.ofbiz.entity.Delegator;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.service.DispatchContext;
import org.ofbiz.service.GenericServiceException;
import org.ofbiz.service.LocalDispatcher;
import org.ofbiz.service.ServiceUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 11/12/12
 *
 * @author Andrea Fossi
 */
public class AuthenticationServices {
    private static final String module = AuthenticationServices.class.getName();

    /**
     * function like {@link #gnSimpleLogin(org.ofbiz.service.DispatchContext, java.util.Map)} but
     * return user profile information if available also.
     *
     * @param ctx
     * @param context
     * @return sessionId and user profile
     * @throws GenericServiceException
     */
    public static Map<String, Object> gnLogin(DispatchContext ctx, Map<String, ? extends Object> context) throws GenericServiceException {
        try {

            //  generateCode(ctx,context);
            LocalDispatcher dispatcher = ctx.getDispatcher();
            GenericValue userLogin = (GenericValue) context.get("userLogin");
            checkLoginSourceId(ctx.getDispatcher(), (String) context.get("loginSourceId"), userLogin);
            checkMobile(ctx.getDispatcher(), userLogin, (String) context.get("loginSourceId"),
                    (String) context.get("deviceId"), (String) context.get("deviceDescription"), (String) context.get("appId"), (String) context.get("appVersion"));

            userLogin = checkSaltedPassword(userLogin, (String) context.get("login_password"), ctx.getDelegator());
            HttpServletRequest req = (HttpServletRequest) context.get("request");
            req.setAttribute("userLogin", userLogin);
            req.getSession().setAttribute("userLogin", userLogin);
            String sessionId = req.getSession().getId();
            String userLoginId = (String) userLogin.get("userLoginId");
            Map<String, Object> userProfile = dispatcher.runSync("gnGetUserProfile",
                    UtilMisc.toMap("userLogin", userLogin, "userLoginId", userLoginId));

            Map<String, Object> result = ServiceUtil.returnSuccess();
            result.putAll(userProfile);
            result.put("sessionId", sessionId);
            Integer passwordExpirationDays = new UserHelper(ctx, context).getPasswordExpirationDays(userLoginId);//expose a service
            result.put("passwordExpirationDays", passwordExpirationDays);

            return result;
        } catch (GeneralException e) {
            return GnServiceUtil.returnError(e);
        }
    }

    private static GenericValue checkSaltedPassword(GenericValue userLogin, String password, Delegator delegator) throws GnServiceException {
        String salt = userLogin.getString("salt");
        if (UtilValidate.isEmpty(salt)) {
            if (UtilValidate.isEmpty(password)) throw new GnServiceException(OfbizErrors.GENERIC, "password is empty");
            return GnAuthenticator.updateSaltedPassword(userLogin, password, password, password, delegator, false);
        } else {
            return userLogin;
        }
    }

/*    private static boolean hasPublishesToRootRelationship(DispatchContext ctx, GenericValue userLogin, String partyIdFrom) throws GenericServiceException, GenericEntityException {
        Map<String, Object> params = UtilMisc.toMap("userLogin", userLogin, "partyRelationshipTypeIds", Arrays.asList(RelationshipTypeOfbiz.GN_PUBLISHES_TO_ROOT.name()), "partyIdsFrom", Arrays.asList(partyIdFrom));
        Map<String, Object> res = PartyRelationshipService.gnFindPartyRelationships(ctx, params);
        List<Map<String, Object>> partyRelationships = (List<Map<String, Object>>) res.get("partyRelationships");
        return !partyRelationships.isEmpty();
    }*/

    /**
     * If user is logged in, return http session id.
     *
     * @param ctx
     * @param context
     * @return sessionId
     */
    public static Map<String, Object> gnSimpleLogin(DispatchContext ctx, Map<String, ? extends Object> context) {
        try {
            GenericValue userLogin = (GenericValue) context.get("userLogin");
            checkLoginSourceId(ctx.getDispatcher(), (String) context.get("loginSourceId"), userLogin);
            checkMobile(ctx.getDispatcher(), userLogin, (String) context.get("loginSourceId"), (String) context.get("deviceId"), (String) context.get("deviceDescription"), (String) context.get("appId"), (String) context.get("appVersion"));
            HttpServletRequest req = (HttpServletRequest) context.get("request");
            req.setAttribute("userLogin", userLogin);
            req.getSession().setAttribute("userLogin", userLogin);
            String sessionId = req.getSession().getId();
            Map<String, Object> result = ServiceUtil.returnSuccess();
            result.put("sessionId", sessionId);
            return result;
        } catch (GeneralException e) {
            return GnServiceUtil.returnError(e);
        }
    }

    /**
     * Logout service: signal to system that http session should be close.
     *
     * @param ctx
     * @param context
     * @return success message
     */
    public static Map<String, Object> gnLogout(DispatchContext ctx, Map<String, ? extends Object> context) {
        HttpServletRequest request = (HttpServletRequest) context.get("request");
        GenericValue userLogin = (GenericValue) context.get("userLogin");
        userLogin.set("hasLoggedOut", "Y");
        GenericValue currentUserLogin = (GenericValue) request.getSession().getAttribute("userLogin");
        currentUserLogin.set("hasLoggedOut", "Y");
        Map<String, Object> result = ServiceUtil.returnSuccess();
        result.put("result", "success");
        return result;
    }

    /**
     * Check that user can loginIn from the input source
     *
     * @param serviceLoginSourceId
     * @param userLogin
     * @throws GnServiceException
     */
    private static void checkLoginSourceId(LocalDispatcher dispatcher, String serviceLoginSourceId, GenericValue userLogin) throws GenericServiceException {
        Map<String, Object> result = dispatcher.runSync("gnFindLoginSourceId", UtilMisc.toMap("userLogin", userLogin, "userLoginId", userLogin.getString("userLoginId")));
        List<String> loginSourceIds = UtilMisc.getListFromMap(result, "loginSourceIds");
        String userLoginId = userLogin.getString("userLoginId");
/*
        if (UtilValidate.isEmpty(serviceLoginSourceId)) {
            serviceLoginSourceId = LoginSourceOfbiz.GN_LOG_SRC_WEB_UI.name();
            Debug.logWarning("serviceLoginSourceId is empty for User[" + userLoginId + "]", module);
        }*/
      /*  if (UtilValidate.isEmpty(loginSourceIds)) {
            loginSourceIds.add(LoginSourceOfbiz.GN_LOG_SRC_WEB_UI.name());
            Debug.logWarning("userLoginSourceId is empty for User[" + userLoginId + "]", module);
        }*/
        if (!loginSourceIds.contains(serviceLoginSourceId)) {
            throw new GnServiceException(OfbizErrors.ACCESS_DENIED, "User[" + userLoginId + "] cannot login from " + serviceLoginSourceId);
        }
    }

    private static void checkMobile(LocalDispatcher dispatcher, GenericValue userLogin, String loginSourceId, String deviceId, String deviceDescription, String appId, String appVersion) throws GenericServiceException {
        boolean isMobile = loginSourceId.equals(LoginSourceOfbiz.GN_LOG_SRC_MOBILE.name());
        if (isMobile) {
            if (UtilValidate.isEmpty(deviceId))
                throw new GnServiceException(OfbizErrors.INVALID_PARAMETERS, "DeviceId cannot be empty.");
            String userLoginId = userLogin.getString("userLoginId");
            Map<String, Object> result = dispatcher.runSync("gnFindUserLoginDeviceUniqueIds", UtilMisc.toMap("userLogin", userLogin, "userLoginId", userLoginId, "deviceId", deviceId));
            List<Map<String, Object>> devices = UtilMisc.getListFromMap(result, "list");
            if (devices.size() == 0) {//ADD
                dispatcher.runSync("gnAddActiveUserLoginDeviceUniqueId", UtilMisc.toMap("userLogin", userLogin, "userLoginId", userLoginId,
                        "deviceId", deviceId, "deviceDescription", deviceDescription, "appId", appId, "appVersion", appVersion));
            } else {
                Map<String, Object> device = devices.get(0);
                if ("Y".equals(device.get("isActive"))) {//ok
                    //to update timestamp
                    dispatcher.runSync("gnUpdateUserLoginDeviceUniqueId", UtilMisc.toMap("userLogin", userLogin, "userLoginId", userLoginId, "deviceId", deviceId,
                            "deviceDescription", deviceDescription, "appId", appId, "appVersion", appVersion,
                            "isActive", "Y"));
                } else {//error
                    throw new GnServiceException(OfbizErrors.EXPIRED_DEVICE,
                            String.format("You cannot use device[%s] because is already used. ", deviceId));
                }
            }
        }
    }

    /**
     * Logout service: signal to system that http session should be close.
     *
     * @param ctx
     * @param context
     * @return success message
     */
    public static Map<String, Object> gnRollbackDb(DispatchContext ctx, Map<String, ? extends Object> context) {
        long start = System.currentTimeMillis();
        ctx.getDelegator().rollback();
        long stop = System.currentTimeMillis();
        Debug.log("Roll back time " + (stop - start) / 1000d + "s");
        for (String key : UtilCache.getUtilCacheTableKeySet()) {
            /*if (!key.startsWith("service.")&&
                    !key.startsWith("entity.") &&
                    !key.startsWith("webapp.") &&
                    !key.startsWith("resource.") &&
                    !key.startsWith("properties.")&&
                    !key.startsWith("template.")&&
                    !key.startsWith("template.")&&
                    !key.startsWith("flexibleStringExpander.")
                    )*/
            if (key.startsWith("entitycache.") ||
                    key.startsWith("gn.entitycache.")
                    ) {
                Debug.log("Clear cache[" + key + "]");
                UtilCache.clearCache(key);
            }
        }
        //UtilCache.clearAllCaches();
        Map<String, Object> result = ServiceUtil.returnSuccess();
        return result;
    }


   /* public static Map<String, Object> generateCode(DispatchContext ctx, Map<String, ? extends Object> context) throws GenericEntityException {
        Delegator delegator = ctx.getDelegator();
        Set<String> entities = delegator.getModelReader().getEntityNames();
        for (String entityName : entities) {
            List<String> fields = delegator.getModelEntity(entityName).getAllFieldNames();
            for (String fieldName : fields) {
                ModelField modelField = delegator.getModelEntity(entityName).getField(fieldName);
                ModelFieldType mft = delegator.getModelFieldTypeReader(delegator.getModelEntity(entityName)).getModelFieldType(modelField.getType());
                mft.getJavaType();
            }
        }
        return GnServiceUtil.returnSuccess();
    }*/
}
