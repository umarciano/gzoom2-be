package it.memelabs.gn.services.user;

import it.memelabs.gn.services.OfbizErrors;
import it.memelabs.gn.services.communication.CommunicationEventAttributeKeyOfbiz;
import it.memelabs.gn.services.communication.EventTypeOfbiz;
import it.memelabs.gn.services.login.LoginSourceOfbiz;
import it.memelabs.gn.services.node.PartyNodeTypeOfbiz;
import it.memelabs.gn.services.node.RelationshipTypeOfbiz;
import it.memelabs.gn.services.security.GnSecurity;
import it.memelabs.gn.services.security.PermissionsOfbiz;
import it.memelabs.gn.util.GnServiceException;
import it.memelabs.gn.util.GnServiceUtil;
import it.memelabs.gn.util.PartyNodeUtil;
import it.memelabs.gn.util.TreeUtil;
import javolution.util.FastList;
import javolution.util.FastMap;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.GeneralException;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.entity.Delegator;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.entity.condition.EntityCondition;
import org.ofbiz.service.DispatchContext;
import org.ofbiz.service.GenericServiceException;
import org.ofbiz.service.LocalDispatcher;
import org.ofbiz.service.ServiceUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.ofbiz.service.ServiceUtil.returnSuccess;

/**
 * 11/01/13
 *
 * @author Andrea Fossi
 */
public class UserServices {
    private static final String module = UserServices.class.getName();

    /**
     * @param ctx
     * @param context
     * @return
     */
    public static Map<String, Object> gnGetUserProfile(DispatchContext ctx, Map<String, ? extends Object> context) {
        try {
            Map<String, Object> result = returnSuccess();
            result.putAll(new UserHelper(ctx, context).findUserByUserLoginId((String) context.get("userLoginId"), true, true));
            return result;
        } catch (GeneralException e) {
            Debug.logError(e, module);
            return GnServiceUtil.returnError(e);
        }
    }

    /**
     * @param ctx
     * @param context (userLoginId)
     * @return
     * @throws GenericEntityException
     */
    public static Map<String, Object> gnGetUser(DispatchContext ctx, Map<String, ? extends Object> context) {
        try {
            Map<String, Object> result = returnSuccess();
            result.putAll(new UserHelper(ctx, context).findUserByUserLoginId((String) context.get("userLoginId"), false, false));
            return result;
        } catch (GeneralException e) {
            Debug.logError(e, module);
            return GnServiceUtil.returnError(e.getMessageList());
        }
    }

    /**
     * @param ctx
     * @param context
     * @return
     */
    public static Map<String, Object> gnFindUsers(DispatchContext ctx, Map<String, ? extends Object> context) {
        try {
            List<EntityCondition> conds = FastList.newInstance();
            if (UtilValidate.isNotEmpty(context.get("userLoginId")))
                conds.add(EntityCondition.makeCondition("userLoginId", context.get("userLoginId")));
            if (UtilValidate.isNotEmpty(context.get("partyId")))
                conds.add(EntityCondition.makeCondition("partyId", context.get("partyId")));
            List<GenericValue> ret = ctx.getDelegator().findList("GnPartyAndUserLoginAndPerson", EntityCondition.makeCondition(conds), null, UtilMisc.toList("userLoginId"), null, false);
            Map<String, Object> result = returnSuccess();
            List<Map<String, Object>> users = new ArrayList<Map<String, Object>>(ret.size());
            for (GenericValue gv : ret) {
                Map<String, Object> item = UtilMisc.makeMapWritable(gv);
                List<String> loginSourceIds = new GnLoginHelper(ctx, context).findLoginSourceId(gv.getString("userLoginId"));
                item.put("loginSourceIds", loginSourceIds);
                if (loginSourceIds.size() > 0) {
                    if (loginSourceIds.contains(LoginSourceOfbiz.GN_LOG_SRC_WEB_UI.name())) {
                        item.put("loginSourceId", LoginSourceOfbiz.GN_LOG_SRC_WEB_UI.name());
                    } else {
                        item.put("loginSourceId", loginSourceIds.get(0));
                    }
                }
                users.add(item);
            }
            //ret.add(ret.get(0));
            result.put("users", users);
            //result.put("userListSize", ret.size());
            return result;
        } catch (Exception e) {
            Debug.logError(e, module);
            return GnServiceUtil.returnError(e.getMessage());
        }
    }

    public static Map<String, Object> gnFindCompanyWhereUserIsEmployed(DispatchContext ctx, Map<String, ? extends Object> context) {
        try {
            Map<String, Object> result = ServiceUtil.returnSuccess();
            Map<String, Object> partyNode = new UserHelper(ctx, context).findCompanyWhereUserIsEmployed((String) context.get("userLoginPartyId"));
            result.put("partyNode", partyNode);
            return result;
        } catch (GenericEntityException e) {
            Debug.logError(e, module);
            return GnServiceUtil.returnError(e.getMessage());
        } catch (GenericServiceException e) {
            Debug.logError(e, module);
            return GnServiceUtil.returnError(e.getMessage());
        }
    }

    /**
     * @param ctx
     * @param context
     * @return
     */
    public static Map<String, Object> gnGetPasswordConstraints(DispatchContext ctx, Map<String, Object> context) {
        try {
            GenericValue userLogin = (GenericValue) context.get("userLogin");
            String ret = new UserHelper(ctx, context).getPasswordConstraints(userLogin.getString("userLoginId"));
            Map<String, Object> result = returnSuccess();
            result.put("passwordConstraints", ret);
            return result;
        } catch (GeneralException e) {
            Debug.logError(e, module);
            return GnServiceUtil.returnError(e.getMessage());
        }
    }

    /**
     * @param ctx
     * @param context
     * @return
     */
    public static Map<String, Object> gnInternalGetPasswordConstraints(DispatchContext ctx, Map<String, Object> context) {
        try {
            String ret = new UserHelper(ctx, context).getPasswordConstraints((String) context.get("userLoginId"));
            Map<String, Object> result = returnSuccess();
            result.put("passwordConstraints", ret);
            return result;
        } catch (GeneralException e) {
            Debug.logError(e, module);
            return GnServiceUtil.returnError(e.getMessage());
        }
    }

    /**
     * @param ctx
     * @param context
     * @return
     */
    public static Map<String, Object> gnUserProfileFindUsers(DispatchContext ctx, Map<String, Object> context) {
        try {
            String companyPartyId = (String) context.get("partyId");
            String companyNodeKey = (String) context.get("nodeKey");

            companyPartyId = PartyNodeUtil.getPartyId(ctx, context, companyPartyId, companyNodeKey, "PartyId and NodeKey cannot be empty both.");

            GenericValue userLogin = (GenericValue) context.get("userLogin");
            Delegator delegator = ctx.getDelegator();
            LocalDispatcher dispatcher = ctx.getDispatcher();
            GnSecurity security = new GnSecurity(delegator);
            //boolean hasPermission = false;
            boolean hasPermission = new UserHelper(ctx, context).isUserIsEmployedInCompany(userLogin.getString("partyId"), companyPartyId);

            if (!hasPermission && security.hasPermission(PermissionsOfbiz.GN_ADMIN.name(), userLogin)) {
                hasPermission = true;
            }

            if (!hasPermission && security.hasPermission(PermissionsOfbiz.GN_COMPANY_TREE_ADMIN.name(), userLogin)) {
                Map<String, Object> gnContext = dispatcher.runSync("gnFindContextById", UtilMisc.toMap("userLogin", userLogin, "partyId", userLogin.get("activeContextId")));
                @SuppressWarnings("unchecked")
                Map<String, Object> contextNode = (Map<String, Object>) gnContext.get("partyNode");
                if (PartyNodeTypeOfbiz.isACompany(contextNode.get("nodeType"))) {
                    hasPermission = TreeUtil.isPartyNodeBelongToTree(ctx, (String) contextNode.get("partyId"), null, null, RelationshipTypeOfbiz.GN_OWNS.name(), companyPartyId);
                }
            }

            if (!hasPermission && security.hasPermission(PermissionsOfbiz.GN_COMPANY_ADMIN.name(), userLogin)) {
                Map<String, Object> gnContext = dispatcher.runSync("gnFindContextById", UtilMisc.toMap("userLogin", userLogin, "partyId", userLogin.get("activeContextId")));
                @SuppressWarnings("unchecked")
                Map<String, Object> contextNode = (Map<String, Object>) gnContext.get("partyNode");
                if (PartyNodeTypeOfbiz.isACompany(contextNode.get("nodeType"))) {
                    hasPermission = UtilValidate.areEqual(contextNode.get("partyId"), companyPartyId);
                }
            }

            if (!hasPermission)
                throw new GnServiceException(OfbizErrors.ACCESS_DENIED, "User hasn't got permission.");

            List<Map<String, ? extends Object>> users = new UserHelper(ctx, context).findUsersByEmployingCompanyId(companyPartyId);
            Map<String, Object> result = returnSuccess();
            result.put("users", users);
            //result.put("userListSize", users.size());
            return result;
        } catch (GeneralException e) {
            Debug.logError(e, module);
            return GnServiceUtil.returnError(e.getMessage());
        }
    }

    public static Map<String, Object> gnFindUsersByEmployingCompany(DispatchContext ctx, Map<String, Object> context) {
        try {
            String companyPartyId = (String) context.get("partyId");
            List<Map<String, ? extends Object>> users = new UserHelper(ctx, context).findUsersByEmployingCompanyId(companyPartyId);
            Map<String, Object> result = returnSuccess();
            result.put("users", users);
            //result.put("userListSize", users.size());
            return result;
        } catch (GeneralException e) {
            Debug.logError(e, module);
            return GnServiceUtil.returnError(e.getMessage());
        }

    }

    public static Map<String, Object> gnUpdatePassword(DispatchContext ctx, Map<String, Object> context) {
        try {
            String currentPassword = (String) context.get("currentPassword");
            String newPassword = (String) context.get("newPassword");
            String newPasswordVerify = (String) context.get("newPasswordVerify");
            return new UserHelper(ctx, context).updatePassword(currentPassword, newPassword, newPasswordVerify);
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
    public static Map<String, Object> gnResetPassword(DispatchContext ctx, Map<String, Object> context) {
        try {
            String userLoginId = (String) context.get("userLoginId");
            String uuid = (String) context.get("uuid");
            String password = (String) context.get("password");
            UserHelper helper = new UserHelper(ctx, context);
            if (helper.hasWebLoginSource(userLoginId) && helper.canResetPassword(userLoginId, uuid)) {
                helper.resetPassword(userLoginId, password);
                helper.deleteUUID(uuid);
            }
            return returnSuccess();
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
    public static Map<String, Object> gnRecoverPassword(DispatchContext ctx, Map<String, Object> context) {
        Map<String, Object> result = returnSuccess();
        result.put("uuid", "");
        try {
            UserHelper userHelper = new UserHelper(ctx, context);
            String userLoginId = (String) context.get("userLoginId");
            if (userHelper.hasWebLoginSource(userLoginId) && !userHelper.hasGnAdminPermission(userLoginId)) {
                Map<String, Object> helperMap = userHelper.createPasswordResetUUID(userLoginId);
                result.put("email", helperMap.get("email"));
                result.put("uuid", helperMap.get("uuid"));

                Map<String, Object> attributes = FastMap.newInstance();
                attributes.put(CommunicationEventAttributeKeyOfbiz.TEMPORARY_UUID.name(), helperMap.get("uuid"));
                Map<String, Object> commRes = userHelper.sendCommunicationEventToUser(userLoginId, EventTypeOfbiz.GN_COM_EV_RST_PWD.name(), attributes);
                result.put("communicationEventToSendList", commRes.get("communicationEventToSendList"));
                result.put("communicationEventToSendListSize", commRes.get("communicationEventToSendListSize"));
            }
            return result;
        } catch (GeneralException e) {
            Debug.logError(e, module);
            return GnServiceUtil.returnError(e);
        }
    }

    public static Map<String, Object> gnIsValidResetPassword(DispatchContext ctx, Map<String, Object> context) {
        String uuid = (String) context.get("uuid");
        Map<String, Object> result = returnSuccess();
        try {
            result.putAll(new UserHelper(ctx, context).verifyPasswordResetUUID(uuid));
        } catch (GeneralException e) {
            Debug.logError(e, module);
            return GnServiceUtil.returnError(e);
        }
        return result;
    }

    public static Map<String, Object> gnReenableUserLogin(DispatchContext ctx, Map<String, Object> context) {
        try {
            String userLoginId = (String) context.get("userLoginId");

            UserHelper userHelper = new UserHelper(ctx, context);
            GnLoginHelper loginHelper = new GnLoginHelper(ctx, context);
            Map<String, Object> result = userHelper.reenableUserLogin(userLoginId);

            if (((String) result.get("previousDisabled")).equalsIgnoreCase("Y")) {
                Map<String, Object> attributes = FastMap.newInstance();
                attributes.put(CommunicationEventAttributeKeyOfbiz.USER_ENABLED.name(), userLoginId);
                Map<String, Object> commRes = FastMap.newInstance();
                if (loginHelper.hasPreviouslyLogged(userLoginId)) {
                    commRes = userHelper.sendCommunicationEventToUser(userLoginId, EventTypeOfbiz.GN_COM_EV_USR_RNBL.name(), attributes);
                } else {
                    commRes = userHelper.sendCommunicationEventToUser(userLoginId, EventTypeOfbiz.GN_COM_EV_USR_FSTLGN.name(), attributes);
                }

                result.put("communicationEventToSendList", commRes.get("communicationEventToSendList"));
                result.put("communicationEventToSendListSize", commRes.get("communicationEventToSendListSize"));
            }

            return result;

        } catch (GeneralException e) {
            Debug.logError(e, module);
            return GnServiceUtil.returnError(e.getMessage());
        }
    }

}
