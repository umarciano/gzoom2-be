package it.memelabs.gn.services.security;

import it.memelabs.gn.services.OfbizErrors;
import it.memelabs.gn.services.node.CompanyAttributeOfbiz;
import it.memelabs.gn.services.node.PartyRoleOfbiz;
import it.memelabs.gn.util.GnServiceException;
import it.memelabs.gn.util.GnServiceUtil;
import it.memelabs.gn.webapp.event.ErrorEvent;
import javolution.util.FastList;
import org.ofbiz.base.util.*;
import org.ofbiz.entity.Delegator;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.entity.condition.EntityComparisonOperator;
import org.ofbiz.entity.condition.EntityCondition;
import org.ofbiz.entity.condition.EntityOperator;
import org.ofbiz.entity.util.EntityUtil;
import org.ofbiz.security.Security;
import org.ofbiz.security.SecurityConfigurationException;
import org.ofbiz.security.SecurityFactory;
import org.ofbiz.service.DispatchContext;
import org.ofbiz.service.GenericServiceException;
import org.ofbiz.service.LocalDispatcher;
import org.ofbiz.service.ServiceUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 15/02/13
 *
 * @author Andrea Fossi
 */
public class SecurityService {
    private static final String module = SecurityService.class.getName();

    public static Map<String, Object> gnGenericPermissionCheck(DispatchContext ctx, Map<String, ? extends Object> context) {
        Map<String, Object> result = ServiceUtil.returnSuccess();
        Delegator delegator = ctx.getDelegator();
        GnSecurity gnSecurity = new GnSecurity(delegator);
        GenericValue userLogin = (GenericValue) context.get("userLogin");
        String permission = (String) context.get("permission");
        try {
            if (isUserLoginContextValid(ctx, context)) {
                boolean hasPermission = gnSecurity.hasPermission(permission, userLogin);
                if (hasPermission) Debug.log("user has " + permission + " permission ", module);
                result.put("hasPermission", hasPermission);
                result.put("failMessage", OfbizErrors.ACCESS_DENIED.toString());
                ErrorEvent.addError(OfbizErrors.ACCESS_DENIED, null);
            } else {
                result.put("hasPermission", false);
                result.put("failMessage", OfbizErrors.ACCESS_DENIED.toString());
                ErrorEvent.addError(OfbizErrors.ACCESS_DENIED, null);
            }
        } catch (GeneralException e) {
            Debug.logError(e, module);
            result = GnServiceUtil.returnError(e.getMessage());
            result.put("hasPermission", false);
            result.put("failMessage", OfbizErrors.ACCESS_DENIED.toString());
            ErrorEvent.addError(OfbizErrors.ACCESS_DENIED, null);
        }
        return result;
    }

    public static Map<String, Object> gnUserRolePermissionCheck(DispatchContext ctx, Map<String, ? extends Object> context) {

        LocalDispatcher dispatcher = ctx.getDispatcher();
        Delegator delegator = ctx.getDelegator();
        Map<String, Object> result = ServiceUtil.returnSuccess();
        String entity = (String) context.get("entity");
        String mainAction = (String) context.get("mainAction");
        String permission = entity + "_" + mainAction;

        try {
            if (isUserLoginContextValid(ctx, context)) {
                Debug.log("check permission: " + permission, module);

                GenericValue userLogin = (GenericValue) context.get("userLogin");
                GnSecurity gnSecurity = new GnSecurity(delegator);
                boolean hasPermission = gnSecurity.hasEntityPermission(entity, "_" + mainAction, userLogin);
                if (hasPermission) Debug.log("user has " + entity + "_" + mainAction + " permission ", module);

                if (!hasPermission) {//code is executed only if previous control fail
                    Debug.log("check context permission fail, checking userLogin permission", module);
                    Security security = SecurityFactory.getInstance(delegator);
                    hasPermission = security.hasEntityPermission(entity, "_" + mainAction, userLogin);
                    if (hasPermission) Debug.log("user has " + entity + "_" + mainAction + " permission ", module);
                }

                if (!hasPermission) {
                    hasPermission = gnSecurity.hasPermission(PermissionsOfbiz.GN_ADMIN.name(), userLogin);
                    if (hasPermission)
                        Debug.logWarning("user has GN_ADMIN permission, but not checked in gnAdminRolePermissionCheck method.", module);
                }
                if (!hasPermission) {
                    result.put("failMessage", OfbizErrors.ACCESS_DENIED.toString());
                    ErrorEvent.addError(OfbizErrors.ACCESS_DENIED, null);
                }
                result.put("hasPermission", hasPermission);
            } else {
                result.put("hasPermission", false);
                result.put("failMessage", OfbizErrors.ACCESS_DENIED.toString());
                ErrorEvent.addError(OfbizErrors.ACCESS_DENIED, null);
            }

        } catch (GeneralException e) {
            Debug.logError(e, module);
            result = GnServiceUtil.returnError(e.getMessage());
            result.put("hasPermission", false);
            result.put("failMessage", OfbizErrors.ACCESS_DENIED.toString());
            ErrorEvent.addError(OfbizErrors.ACCESS_DENIED, null);
        }
        return result;
    }


    public static Map<String, Object> gnCheckUserLoginContextValidity(DispatchContext ctx, Map<String, ?> context) {
        try {
            Map<String, Object> result = ServiceUtil.returnSuccess();
            boolean isContextValid = isUserLoginContextValid(ctx, context);
            result.put("hasPermission", isContextValid);
            if (!isContextValid) {
                result.put("failMessage", OfbizErrors.ACCESS_DENIED.toString());
                ErrorEvent.addError(OfbizErrors.ACCESS_DENIED, null);
            }
            return result;
        } catch (GeneralException e) {
            Debug.logError(e, module);
            return GnServiceUtil.returnError(e);
        }
    }

    private static boolean isUserLoginContextValid(DispatchContext ctx, Map<String, ?> context) throws GenericEntityException, GnServiceException {
        Delegator delegator = ctx.getDelegator();
        @SuppressWarnings("unchecked")
        Map<String, Object> userLogin = (Map<String, Object>) context.get("userLogin");
        if (UtilValidate.isEmpty(userLogin))
            throw new GnServiceException(OfbizErrors.USER_NOT_LOGGED, "UserLogin is empty, maybe user is not logged");
        String userLoginPartyId = (String) userLogin.get("partyId");
        //<PartyRelationshipType description="" hasTable="N" parentTypeId="" partyRelationshipName="Context-User" partyRelationshipTypeId="GN_CONTEXT_USER" roleTypeIdValidFrom="GN_CONTEXT" roleTypeIdValidTo="GN_USER"/>
        String contextId = (String) userLogin.get("activeContextId");
        if (!UtilValidate.isEmpty(contextId)) {
            List<EntityCondition> conditions = new ArrayList<EntityCondition>();
            conditions.add(EntityCondition.makeCondition(UtilMisc.toMap("partyIdFrom", contextId,
                    "roleTypeIdFrom", PartyRoleOfbiz.GN_CONTEXT.name(), "roleTypeIdTo", PartyRoleOfbiz.GN_USER.name(), "partyIdTo", userLoginPartyId)));
            conditions.add(EntityUtil.getFilterByDateExpr());
            List<GenericValue> ret = delegator.findList("PartyRelationship", EntityCondition.makeCondition(conditions), null, null, null, true);

            if (ret.size() > 1)
                Debug.logWarning("context[" + contextId + "] and user[" + userLoginPartyId + "] as more than 1 relation", module);
            if (ret.size() == 1) return true;
            else return false;
        } else {
            Debug.logWarning("contextId is empty", module);
            return false;
        }
    }

    /**
     * Returned SecurityGroup objects must include the set of Permissions they are associated to.
     *
     * @param ctx
     * @param context
     * @return SecurityGroup List
     */
    public static Map<String, Object> gnFindSecurityGroups(DispatchContext ctx, Map<String, ?> context) {
        Delegator delegator = ctx.getDelegator();

        try {
            List<EntityCondition> conds = FastList.newInstance();
            conds.add(EntityCondition.makeCondition("groupId", EntityOperator.LIKE, "GN_%"));
            conds.add(EntityCondition.makeCondition("groupId", EntityComparisonOperator.NOT_EQUAL, "GN_INTERNAL"));
            List<GenericValue> securityGroupsGV = delegator.findList("SecurityGroup", EntityCondition.makeCondition(conds), null, null, null, true);
            List<Map<String, Object>> securityGroups = FastList.newInstance();

            for (GenericValue securityGroup : securityGroupsGV) {
                String groupId = (String) securityGroup.get("groupId");
                if (!groupId.equalsIgnoreCase("GN_UNIT_TEST_SC")) {
                    Map<String, Object> sg = UtilMisc.toMap("groupId", groupId, "description", securityGroup.get("description"));
                    sg.put("permissions", FastList.newInstance());
                    //use cache...
                    List<GenericValue> permissions = delegator.findByAnd("GnSecurityGroupPermission", "groupId", groupId);
                    for (GenericValue gv : permissions) {
                        Map<String, Object> permission = UtilMisc.toMap("permissionId", gv.get("permissionId"),
                                "description", "permissionDescription", "dynamicAccess", gv.get("dynamicAccess"));
                        UtilMisc.addToListInMap(permission, sg, "permissions");
                    }
                    securityGroups.add(sg);
                }
            }
            Map<String, Object> result = ServiceUtil.returnSuccess();
            result.put("securityGroups", securityGroups);
            return result;
        } catch (GenericEntityException e) {
            return GnServiceUtil.returnError(e);
        }
    }

    /**
     * Returned SecurityGroup objects must include the set of Permissions they are associated to.
     *
     * @param ctx
     * @param context
     * @return SecurityGroup List
     */
    public static Map<String, Object> gnHasPermission(DispatchContext ctx, Map<String, ?> context) {
        Delegator delegator = ctx.getDelegator();

        try {
            //identifier
            String entityId = (String) context.get("entityId");
            String entityTypeId = (String) context.get("entityTypeId");
            String permissionId = (String) context.get("permissionId");
            Map<String, Object> result = GnServiceUtil.returnSuccess();
            SecurityServiceHelper helper = new SecurityServiceHelper(ctx, context);
            result.put("partyNodeKey", helper.gnHasPermission(entityId, entityTypeId, permissionId));
            return result;
        } catch (GeneralException e) {
            return GnServiceUtil.returnError(e);
        }
    }

    public static Map<String, Object> gnRootPublishProfileCheck(DispatchContext ctx, Map<String, ?> context) {
        try {
            LocalDispatcher dispatcher = ctx.getDispatcher();
            return partyAttributeCheck(context, dispatcher, CompanyAttributeOfbiz.ROOT_PUBLISH);
        } catch (GeneralException e) {
            Debug.logError(e, module);
            return GnServiceUtil.returnError(e);
        }
    }

    public static Map<String, Object> gnPrivateAuthPublishProfileCheck(DispatchContext ctx, Map<String, ?> context) {
        try {
            LocalDispatcher dispatcher = ctx.getDispatcher();
            return partyAttributeCheck(context, dispatcher, CompanyAttributeOfbiz.PRIVATE_AUTH_PUBLISH);
        } catch (GeneralException e) {
            Debug.logError(e, module);
            return GnServiceUtil.returnError(e);
        }
    }

    public static Map<String, Object> gnPrivateAuthCheckProfileCheck(DispatchContext ctx, Map<String, ?> context) {
        Map<String, Object> result = ServiceUtil.returnSuccess();
        result.put("hasPermission", "Y");
        return result;

/*      //At the moment, we can disable the permission check.

        LocalDispatcher dispatcher = ctx.getDispatcher();
       try {
            return partyAttributeCheck(context, dispatcher, CompanyAttributeOfbiz.PRIVATE_AUTH_CHECK);
        } catch (GeneralException e) {
            Debug.logError(e, module);
            return GnServiceUtil.returnError(e);
        }*/
    }

    private static Map<String, Object> partyAttributeCheck(Map<String, ?> context, LocalDispatcher dispatcher, CompanyAttributeOfbiz attribute) throws GenericServiceException {
        String forcedCompanyPartyId = (String) context.get("forcedCompanyPartyId");
        String partyId;
        if (forcedCompanyPartyId == null || forcedCompanyPartyId.isEmpty()) {
            @SuppressWarnings("unchecked")
            Map<String, Object> userLogin = (Map<String, Object>) context.get("userLogin");
            Map<String, Object> companyMap = dispatcher.runSync("gnFindCompanyWhereUserIsEmployed",
                    UtilMisc.toMap("userLogin", userLogin, "userLoginPartyId", userLogin.get("partyId")));
            if (companyMap == null || companyMap.get("partyNode") == null)
                throw new GnServiceException(OfbizErrors.INVALID_PARAMETERS, "User is not employed in a company.");
            companyMap = UtilGenerics.checkMap(companyMap.get("partyNode"));
            partyId = (String) companyMap.get("partyId");
        } else {
            partyId = forcedCompanyPartyId;
        }
        Map<String, Object> partyAttribute = dispatcher.runSync("gnFindPartyAttributeById",
                UtilMisc.toMap("userLogin", context.get("userLogin"), "partyId", partyId, "attrName", attribute.name()));
        Map<String, Object> result = ServiceUtil.returnSuccess();
        if (partyAttribute == null || !"Y".equals(partyAttribute.get("attrValue"))) {
            result.put("hasPermission", "N");
            result.put("failMessage", OfbizErrors.ACCESS_DENIED.toString());
            ErrorEvent.addError(OfbizErrors.ACCESS_DENIED, null);
        } else {
            result.put("hasPermission", "Y");
        }

        return result;
    }

    /**
     * Used only for tests
     *
     * @param ctx
     * @param context
     * @return
     */
    public static Map<String, Object> gnTestUserPermissionCheck(DispatchContext ctx, Map<String, ? extends Object> context) throws SecurityConfigurationException {
        Map<String, Object> result = ServiceUtil.returnSuccess();
        Delegator delegator = ctx.getDelegator();
        Security gnSecurity = SecurityFactory.getInstance(delegator);
        GenericValue userLogin = (GenericValue) context.get("userLogin");
        String permission = (String) context.get("permission");

        boolean hasPermission = gnSecurity.hasPermission(permission, userLogin);
        if (hasPermission) Debug.log("user has " + permission + " permission ", module);
        result.put("hasPermission", hasPermission);
        if (!hasPermission) {
            result.put("failMessage", OfbizErrors.ACCESS_DENIED.toString());
            ErrorEvent.addError(OfbizErrors.ACCESS_DENIED, null);
        }
        return result;
    }


}
