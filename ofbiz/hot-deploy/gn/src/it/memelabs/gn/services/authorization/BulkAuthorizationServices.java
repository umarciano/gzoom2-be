package it.memelabs.gn.services.authorization;

import it.memelabs.gn.services.OfbizErrors;
import it.memelabs.gn.services.login.LoginSourceOfbiz;
import it.memelabs.gn.util.GnServiceException;
import it.memelabs.gn.util.GnServiceUtil;
import javolution.util.FastMap;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.service.DispatchContext;

import java.util.List;
import java.util.Map;

/**
 * 28/10/13
 *
 * @author Andrea Fossi
 */
public class BulkAuthorizationServices {
    private static final String module = BulkAuthorizationServices.class.getName();

    /**
     * Proxy method to import an authorization with items
     * Creates an Authorization in draft with related AuthorizationItems.
     *
     * @param ctx
     * @param context
     * @return
     */
    public static Map<String, Object> gnBulkImportAuthorization(DispatchContext ctx, Map<String, Object> context) {
        try {
            Map<String, Object> result = FastMap.newInstance();
            @SuppressWarnings("unchecked")
            GenericValue userLogin = (GenericValue) context.get("userLogin");


            String userLoginId = (String) context.get("userLoginId");
            String contextId = (String) context.get("contextId");


            String ownerNodeKey = (String) context.get("nodeKey");
            String ownerPartyId = (String) context.get("partyId");

            @SuppressWarnings("unchecked")
            Map<String, Object> authorization = (Map<String, Object>) context.get("authorization");

            if (authorization.get("isPrivate") == null) {
                throw new GnServiceException(OfbizErrors.INVALID_PARAMETERS,
                        "isPrivate must be specified in authorization to import.");
            }
            boolean isPrivate = "Y".equals(authorization.get("isPrivate"));

            BulkAuthorizationHelper bulkAuthorizationHelper = new BulkAuthorizationHelper(ctx, context);
            List<String> userLoginSourceIds = bulkAuthorizationHelper.findLoginSourceIds(userLogin);

            if (userLoginSourceIds.contains(LoginSourceOfbiz.GN_LOG_SRC_INTERNAL.name())) {
                result.putAll(bulkAuthorizationHelper.bulkCreateFromScraping(userLoginId, contextId, ownerNodeKey, ownerPartyId, authorization, isPrivate));
            } else {
                throw new GnServiceException(OfbizErrors.ACCESS_DENIED, "You cannot import an authorization without '" + LoginSourceOfbiz.GN_LOG_SRC_INTERNAL.name() + "' login.");
            }


            return result;
        } catch (Throwable e) {
            Debug.logError(e, module);
            return GnServiceUtil.returnError(e);
        }
    }


    public static Map<String, Object> gnBulkCreateUpdateAuthorization(DispatchContext ctx, Map<String, Object> context) {
        try {
            Map<String, Object> result = FastMap.newInstance();
            @SuppressWarnings("unchecked")
            Map<String, Object> authorization = (Map<String, Object>) context.get("authorization");

            if (UtilValidate.isEmpty(authorization.get("authorizationKey")) && UtilValidate.isEmpty(authorization.get("agreementId"))) {
                result.putAll(gnBulkCreateAuthorization(ctx, context));
            } else {
                result.putAll(gnBulkUpdateAuthorization(ctx, context));
            }


            return result;
        } catch (Throwable e) {
            Debug.logError(e, module);
            return GnServiceUtil.returnError(e);
        }

    }

    public static Map<String, Object> gnBulkCreateAuthorization(DispatchContext ctx, Map<String, Object> context) {
        try {
            Map<String, Object> result = FastMap.newInstance();
            @SuppressWarnings("unchecked")
            GenericValue userLogin = (GenericValue) context.get("userLogin");


            @SuppressWarnings("unchecked")
            Map<String, Object> authorization = (Map<String, Object>) context.get("authorization");

            if (authorization.get("isPrivate") == null) {
                throw new GnServiceException(OfbizErrors.INVALID_PARAMETERS,
                        "isPrivate must be specified in authorization to import.");
            }

            BulkAuthorizationHelper bulkAuthorizationHelper = new BulkAuthorizationHelper(ctx, context);
            List<String> userLoginSourceIds = bulkAuthorizationHelper.findLoginSourceIds(userLogin);
            if (userLoginSourceIds.contains(LoginSourceOfbiz.GN_LOG_SRC_WEB_API.name())) {
                result.putAll(bulkAuthorizationHelper.bulkCreateFromWebApi(authorization));
            } else {
                throw new GnServiceException(OfbizErrors.ACCESS_DENIED, "You create import an authorization without '" + LoginSourceOfbiz.GN_LOG_SRC_WEB_API.name() + "' login.");
            }


            return result;
        } catch (Throwable e) {
            Debug.logError(e, module);
            return GnServiceUtil.returnError(e);
        }
    }

    /**
     * @param ctx
     * @param context
     * @return
     */
    public static Map<String, Object> gnBulkUpdateAuthorization(DispatchContext ctx, Map<String, Object> context) {
        try {
            Map<String, Object> result = FastMap.newInstance();
            @SuppressWarnings("unchecked")
            GenericValue userLogin = (GenericValue) context.get("userLogin");

            @SuppressWarnings("unchecked")
            Map<String, Object> authorization = (Map<String, Object>) context.get("authorization");

            if (authorization.get("isPrivate") == null) {
                throw new GnServiceException(OfbizErrors.INVALID_PARAMETERS,
                        "isPrivate must be specified in authorization to import.");
            }

            BulkAuthorizationHelper bulkAuthorizationHelper = new BulkAuthorizationHelper(ctx, context);
            List<String> userLoginSourceIds = bulkAuthorizationHelper.findLoginSourceIds(userLogin);
            if (userLoginSourceIds.contains(LoginSourceOfbiz.GN_LOG_SRC_WEB_API.name())) {
                result.putAll(bulkAuthorizationHelper.bulkUpdateFromWebApi(authorization));
            } else {
                throw new GnServiceException(OfbizErrors.ACCESS_DENIED, "You create import an authorization without '" + LoginSourceOfbiz.GN_LOG_SRC_WEB_API.name() + "' login.");
            }


            return result;
        } catch (Throwable e) {
            Debug.logError(e, module);
            return GnServiceUtil.returnError(e);
        }
    }


}
