package it.memelabs.gn.services.authorization;

import it.memelabs.gn.services.communication.EventTypeOfbiz;
import it.memelabs.gn.util.GnServiceUtil;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.GeneralException;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.service.DispatchContext;
import org.ofbiz.service.ServiceUtil;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

/**
 * 04/12/12
 *
 * @author Andrea Fossi
 */
public class AuthorizationItemServices {
    private static final String module = AuthorizationItemServices.class.getName();

    /**
     * Proxy to create/update authorizationItem method
     *
     * @param ctx
     * @param context
     * @return authorizationItemKey, agreementItemSeqId, agreementId
     */
    public static Map<String, Object> gnCreateUpdateAuthorizationItem(DispatchContext ctx, Map<String, ? extends Object> context) {
        try {
            Map<String, Object> srvContext = UtilMisc.makeMapWritable(context);
            AuthorizationHelper ah = new AuthorizationHelper(ctx, context);
            String agreementId = (String) context.get("agreementId");
            String authorizationKey = (String) context.get("authorizationKey");
            String agreementItemSeqId = (String) context.get("agreementItemSeqId");
            String authorizationItemKey = (String) context.get("authorizationItemKey");

            if (UtilValidate.isNotEmpty(authorizationItemKey)) {
                srvContext.putAll(new AuthorizationItemHelper(ctx, context).getAgreementItemId(authorizationItemKey));
            } else if (UtilValidate.isEmpty(agreementId) && UtilValidate.isNotEmpty(authorizationKey)) {
                srvContext.put("agreementId", ah.getAgreementId(authorizationKey));
            } else if (UtilValidate.isEmpty(authorizationKey) && UtilValidate.isNotEmpty(agreementId)) {
                srvContext.put("authorizationKey", ah.getAuthorizationKey(agreementId));
            }


            if (UtilValidate.isEmpty(agreementItemSeqId) && UtilValidate.isEmpty(authorizationItemKey))
                return ctx.getDispatcher().runSync("gnCreateAuthorizationItem", ctx.makeValidContext("gnCreateAuthorizationItem", "IN", srvContext));
            else {
                return ctx.getDispatcher().runSync("gnUpdateAuthorizationItem", ctx.makeValidContext("gnUpdateAuthorizationItem", "IN", srvContext));
            }
        } catch (Throwable e) {
            Debug.logError(e, module);
            return GnServiceUtil.returnError(e);
        }
    }

    /**
     * Creates an Authorization.
     *
     * @param ctx     The DispatchContext that this service is operating in.
     * @param context Map containing the input parameters.
     * @return authorizationItemKey, agreementItemSeqId, agreementId
     */

    public static Map<String, Object> gnCreateAuthorizationItem(DispatchContext ctx, Map<String, ? extends Object> context) {
        try {
            Map<String, Object> result = ServiceUtil.returnSuccess();
            String agreementText = (String) context.get("agreementText");
            String simplified = (String) context.get("simplified");
            String holderRoleId = (String) context.get("holderRoleId");
            String agreementId = (String) context.get("agreementId");
            String authorizationKey = (String) context.get("authorizationKey");
            String categoryClassEnumId = (String) context.get("categoryClassEnumId");
            @SuppressWarnings("unchecked")
            List<Map<String, Object>> agreementTerms = (List<Map<String, Object>>) context.get("agreementTerms");

            Timestamp validFromDate = (Timestamp) context.get("validFromDate");
            Timestamp validThruDate = (Timestamp) context.get("validThruDate");
            Timestamp suspendFromDate = (Timestamp) context.get("suspendFromDate");
            Timestamp suspendThruDate = (Timestamp) context.get("suspendThruDate");

            result.putAll(new AuthorizationItemHelper(ctx, context)
                    .gnCreateAuthorizationItem(agreementId, authorizationKey, null, agreementText,
                            simplified, holderRoleId, categoryClassEnumId,
                            validFromDate, validThruDate, suspendFromDate, suspendThruDate, agreementTerms));

            AuthorizationHelper authorizationHelper = new AuthorizationHelper(ctx, context);
            Map<String, Object> savedAuthorization = authorizationHelper.findAuthorizationById(agreementId, authorizationKey, true, false);
            authorizationHelper.gnSendCommunicationEventToContactList((String) savedAuthorization.get("ownerNodeId"), EventTypeOfbiz.GN_COM_EV_AUTH_MOD.name(), savedAuthorization);

            return result;
        } catch (GeneralException e) {
            Debug.logError(e, module);
            return GnServiceUtil.returnError(e);
        }
    }

    /**
     * Update an AuthorizationItem
     *
     * @param ctx
     * @param context
     * @return authorizationItemKey, agreementItemSeqId, agreementId
     */
    public static Map<String, Object> gnUpdateAuthorizationItem(DispatchContext ctx, Map<String, ? extends Object> context) {
        try {
            Map<String, Object> result = ServiceUtil.returnSuccess();
            String agreementText = (String) context.get("agreementText");
            String simplified = (String) context.get("simplified");
            String holderRoleId = (String) context.get("holderRoleId");
            String agreementId = (String) context.get("agreementId");
            String authorizationKey = (String) context.get("authorizationKey");
            String agreementItemSeqId = (String) context.get("agreementItemSeqId");
            String authorizationItemKey = (String) context.get("authorizationItemKey");
            String categoryClassEnumId = (String) context.get("categoryClassEnumId");
            @SuppressWarnings("unchecked")
            List<Map<String, Object>> agreementTerms = (List<Map<String, Object>>) context.get("agreementTerms");

            Timestamp validFromDate = (Timestamp) context.get("validFromDate");
            Timestamp validThruDate = (Timestamp) context.get("validThruDate");
            Timestamp suspendFromDate = (Timestamp) context.get("suspendFromDate");
            Timestamp suspendThruDate = (Timestamp) context.get("suspendThruDate");

            result.putAll(new AuthorizationItemHelper(ctx, context)
                    .gnUpdateAuthorizationItem(agreementId, agreementItemSeqId, authorizationKey,
                            authorizationItemKey, agreementText, simplified, holderRoleId, categoryClassEnumId,
                            validFromDate, validThruDate, suspendFromDate, suspendThruDate, agreementTerms));

            AuthorizationHelper authorizationHelper = new AuthorizationHelper(ctx, context);
            Map<String, Object> savedAuthorization = authorizationHelper.findAuthorizationById(agreementId, authorizationKey, true, false);
            authorizationHelper.gnSendCommunicationEventToContactList((String) savedAuthorization.get("ownerNodeId"), EventTypeOfbiz.GN_COM_EV_AUTH_MOD.name(), savedAuthorization);

            return result;
        } catch (GeneralException e) {
            Debug.logError(e, module);
            return GnServiceUtil.returnError(e);
        }
    }

    /**
     * Update an AuthorizationItem
     *
     * @param ctx
     * @param context
     * @return authorizationItemKey, agreementItemSeqId, agreementId
     */
    public static Map<String, Object> gnDeleteAuthorizationItem(DispatchContext ctx, Map<String, ? extends Object> context) {
        try {
            Map<String, Object> result = ServiceUtil.returnSuccess();

            String authorizationItemKey = (String) context.get("authorizationItemKey");
            new AuthorizationItemHelper(ctx, context).gnDeleteAuthorizationItem(authorizationItemKey);
            return result;
        } catch (GeneralException e) {
            Debug.logError(e, module);
            return GnServiceUtil.returnError(e);
        }
    }

    /**
     * Find authorizationKey by agreementId, authorizationKey, agreementItemSeqId, authorizationItemKey
     *
     * @param ctx
     * @param context
     * @return List of agreementTerms
     */
    public static Map<String, Object> gnFindAuthorizationItems(DispatchContext ctx, Map<String, ? extends Object> context) {
        try {
            Map<String, Object> result = ServiceUtil.returnSuccess();
            String agreementId = (String) context.get("agreementId");
            String authorizationKey = (String) context.get("authorizationKey");
            String agreementItemSeqId = (String) context.get("agreementItemSeqId");
            String authorizationItemKey = (String) context.get("authorizationItemKey");
            result.put("authorizationItems", new AuthorizationItemHelper(ctx, context).gnFindAuthorizationItems(agreementId, agreementItemSeqId, authorizationKey, authorizationItemKey));
            return result;
        } catch (GeneralException e) {
            Debug.logError(e, module);
            return GnServiceUtil.returnError(e);
        }
    }
}
