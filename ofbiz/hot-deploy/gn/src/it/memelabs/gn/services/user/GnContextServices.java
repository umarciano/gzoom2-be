package it.memelabs.gn.services.user;

import it.memelabs.gn.util.GnServiceUtil;
import it.memelabs.gn.util.PartyNodeUtil;
import javolution.util.FastList;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.GeneralException;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.service.DispatchContext;
import org.ofbiz.service.GenericServiceException;
import org.ofbiz.service.ServiceUtil;

import java.util.List;
import java.util.Map;

/**
 * 26/02/13
 *
 * @author Andrea Fossi
 */
public class GnContextServices {
    private static final String module = GnContextServices.class.getName();

    /**
     * @param ctx
     * @param context
     * @return
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> gnCreateUpdateContext(DispatchContext ctx, Map<String, Object> context) {
        try {
            List<String> companyBaseIds = (List<String>) context.get("companyBaseIds");
            List<String> companyBaseKeys = (List<String>) context.get("companyBaseKeys");
            String partyNodeId = (String) context.get("partyNodeId");
            String partyNodeKey = (String) context.get("partyNodeKey");

            //companyBaseKeys-->companyBaseIds
            if (UtilValidate.isNotEmpty(companyBaseKeys)) {
                companyBaseIds = FastList.newInstance();
                for (String partyKey : companyBaseKeys) {
                    String partyId = PartyNodeUtil.getPartyId(ctx, context, null, partyKey);
                    if (UtilValidate.isNotEmpty(partyId)) companyBaseIds.add(partyId);
                }
            } else if (UtilValidate.isEmpty(companyBaseIds)) companyBaseIds = FastList.newInstance();
            //partyNodeKey-->partyNodeId

            partyNodeId = PartyNodeUtil.getPartyId(ctx, context, partyNodeId, partyNodeKey);

            context.put("partyNodeId", partyNodeId);
            context.put("companyBaseIds", companyBaseIds);

            if (UtilValidate.isEmpty(context.get("partyId"))) {
                return ctx.getDispatcher().runSync("gnCreateContext", ctx.getDispatcher().getDispatchContext().makeValidContext("gnCreateContext", "IN", context));
            } else {
                return ctx.getDispatcher().runSync("gnUpdateContext", ctx.getDispatcher().getDispatchContext().makeValidContext("gnUpdateContext", "IN", context));
            }
        } catch (GeneralException e) {
            Debug.logError(e, module);
            return GnServiceUtil.returnError(e);
        }
    }


    /**
     * @param ctx
     * @param context
     * @return
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> gnCreateContext(DispatchContext ctx, Map<String, ? extends Object> context) {
        try {
            Map<String, Object> result = ServiceUtil.returnSuccess();
            GnContextHelper contextHelper = new GnContextHelper(ctx, context);
            result.put("partyId", contextHelper.gnCreateContext((String) context.get("partyNodeId"), (String) context.get("description"),
                    (List<String>) context.get("companyBaseIds"), (List<String>) context.get("securityGroupIds"), (List<String>) context.get("userLoginIds")));
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
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> gnUpdateContext(DispatchContext ctx, Map<String, ? extends Object> context) {
        try {
            Map<String, Object> result = ServiceUtil.returnSuccess();
            GnContextHelper contextHelper = new GnContextHelper(ctx, context);
            result.put("partyId", contextHelper.gnUpdateContext((String) context.get("partyId"), (String) context.get("description"),
                    (List<String>) context.get("companyBaseIds"), (List<String>) context.get("securityGroupIds"), (List<String>) context.get("userLoginIds")));
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
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> gnDeleteContext(DispatchContext ctx, Map<String, ? extends Object> context) {
        try {
            GnContextHelper contextHelper = new GnContextHelper(ctx, context);
            contextHelper.gnDeleteContext((String) context.get("partyId"));
            Map<String, Object> result = GnServiceUtil.returnSuccess();
            return result;
        } catch (GeneralException e) {
            Debug.logError(e, module);
            return GnServiceUtil.returnError(e);
        }
    }

    /**
     * @param ctx
     * @param context (partyId-->contextId)
     * @return
     * @throws org.ofbiz.entity.GenericEntityException
     *
     * @throws org.ofbiz.service.GenericServiceException
     *
     */
    public static Map<String, Object> gnFindContextById(DispatchContext ctx, Map<String, ? extends Object> context) throws GenericEntityException, GenericServiceException {
        try {
            String contextPartyId = (String) context.get("partyId");
            GnContextHelper gnContextHelper = new GnContextHelper(ctx, context);
            boolean excludeCompanyBases = "Y".equals(context.get("excludeCompanyBases"));
            boolean excludeSecurityGroups = "Y".equals(context.get("excludeSecurityGroups"));
            boolean excludePartyNode = "Y".equals(context.get("excludePartyNode"));
            boolean excludeUsers = "Y".equals(context.get("excludeUsers"));
            Map<String, Object> result = GnServiceUtil.returnSuccess();
            result.putAll(gnContextHelper.gnFindContextById(contextPartyId, excludeCompanyBases, excludeSecurityGroups, excludePartyNode, excludeUsers));
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
    public static Map<String, Object> gnFindContextByUserLoginPartyNodeAndPermission(DispatchContext ctx, Map<String, ? extends Object> context) throws GenericEntityException, GenericServiceException {
        try {
            String userLoginId = (String) context.get("userLoginId");
            String partyNodeId = (String) context.get("partyNodeId");
            String permissionId = (String) context.get("permissionId");
            String loadContext = (String) context.get("loadContext");
            GnContextHelper gnContextHelper = new GnContextHelper(ctx, context);
            Map<String, Object> result = GnServiceUtil.returnSuccess();
            result.put("contextList", gnContextHelper.gnFindContextByUserLoginPartyNodeAndPermission(userLoginId, partyNodeId, permissionId, "Y".equals(loadContext)));
            return result;
        } catch (GeneralException e) {
            Debug.logError(e, module);
            return GnServiceUtil.returnError(e);
        }
    }

    public static Map<String, Object> gnFindContextWithUserLoginByPartyNodeAndPermission(DispatchContext ctx, Map<String, ? extends Object> context) throws GenericEntityException, GenericServiceException {
        try {
            String partyNodeId = (String) context.get("partyNodeId");
            String permissionId = (String) context.get("permissionId");
            GnContextHelper gnContextHelper = new GnContextHelper(ctx, context);
            Map<String, Object> result = GnServiceUtil.returnSuccess();
            result.put("contextList", gnContextHelper.gnFindContextWithUserLoginByPartyNodeAndPermission(partyNodeId, permissionId));
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
    public static Map<String, Object> gnAddCompanyBaseToContext(DispatchContext ctx, Map<String, ? extends Object> context) throws GenericEntityException, GenericServiceException {
        try {
            String gnContextId = (String) context.get("contextId");
            String companyBaseId = (String) context.get("partyId");

            GnContextHelper gnContextHelper = new GnContextHelper(ctx, context);
            gnContextHelper.gnAddCompanyBaseToContext(gnContextId, companyBaseId);
            return GnServiceUtil.returnSuccess();
        } catch (GeneralException e) {
            Debug.logError(e, module);
            return GnServiceUtil.returnError(e);
        }
    }


}
