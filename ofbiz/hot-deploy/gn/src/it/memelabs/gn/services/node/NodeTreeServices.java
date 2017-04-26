package it.memelabs.gn.services.node;

import it.memelabs.gn.services.OfbizErrors;
import it.memelabs.gn.services.authorization.AuthorizationHelper;
import it.memelabs.gn.services.authorization.AuthorizationSynchronizationHelper;
import it.memelabs.gn.util.GnServiceException;
import it.memelabs.gn.util.GnServiceUtil;
import it.memelabs.gn.util.PartyNodeUtil;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.GeneralException;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.service.DispatchContext;
import org.ofbiz.service.LocalDispatcher;
import org.ofbiz.service.ServiceUtil;

import java.util.List;
import java.util.Map;

import static org.ofbiz.service.ServiceUtil.returnSuccess;


/**
 * 25/02/13
 *
 * @author Andrea Fossi
 */
public class NodeTreeServices {
    private static final String module = NodeTreeServices.class.getName();

    /**
     * @param ctx
     * @param context
     * @return
     */
    public static Map<String, Object> gnUserProfileFindCompanies(DispatchContext ctx, Map<String, ? extends Object> context) {
        try {
            Map<String, Object> result = returnSuccess();
            GenericValue userLogin = (GenericValue) context.get("userLogin");
            String contextId = userLogin.getString("activeContextId");
            String userLoginPartyId = userLogin.getString("partyId");
            List<Map<String, Object>> value = new NodeTreeHelper(ctx, context).userProfileFindCompanies(contextId, userLoginPartyId);
            result.put("partyNodes", value);
            return result;
        } catch (GeneralException e) {
            Debug.logError(e, module);
            return GnServiceUtil.returnError(e.getMessage());
        }
    }


    public static Map<String, Object> gnGetTreeFromContext(DispatchContext ctx, Map<String, ?> context) {
        try {
            String contextId = (String) context.get("contextId");
            String partyRelationshipTypeId = (String) context.get("partyRelationshipTypeId");
            Map<String, Object> resultMap = new NodeTreeHelper(ctx, context).getTreeFromContext(contextId, partyRelationshipTypeId);
            return resultMap;
        } catch (GeneralException e) {
            Debug.logError(e, module);
            return GnServiceUtil.returnError(e);
        }
    }

    /**
     * Retrieve node tree starting from partyNode[partyId] with relationship of type [partyRelationshipTypeId]
     *
     * @param ctx     (partyId, partyRelationshipTypeId)
     * @param context
     * @return partyNodes tree
     */
    public static Map<String, Object> gnGetTree(DispatchContext ctx, Map<String, ?> context) {
        try {
            String partyId = (String) context.get("partyId");
            String partyRelationshipTypeId = (String) context.get("partyRelationshipTypeId");
            Map<String, Object> result = ServiceUtil.returnSuccess();
            result.putAll(new NodeTreeHelper(ctx, context).getTree(partyId, partyRelationshipTypeId));
            return result;
        } catch (GeneralException e) {
            Debug.logError(e, module);
            return GnServiceUtil.returnError(e);
        }
    }

    /**
     * @param ctx     (partyId)
     * @param context
     * @return partyId parentPartyId
     */
    public static Map<String, Object> gnFindParentWhoPropagatesToNode(DispatchContext ctx, Map<String, ? extends Object> context) {
        try {
            String partyId = (String) context.get("partyId");
            Map<String, Object> result = ServiceUtil.returnSuccess();
            result.putAll(new NodeTreeHelper(ctx, context).findParentWhoPropagatesToNode(partyId));
            return result;
        } catch (GeneralException e) {
            Debug.logError(e, module);
            return GnServiceUtil.returnError(e);
        }
    }

    public static Map<String, Object> gnSynchronizeNode(DispatchContext ctx, Map<String, ? extends Object> context) {
        LocalDispatcher dispatcher = ctx.getDispatcher();
        GenericValue userLogin = (GenericValue) context.get("userLogin");

        Map<String, Object> result = ServiceUtil.returnSuccess();
        GenericValue receiveRel = null;
        AuthorizationHelper ch = new AuthorizationHelper(ctx, context);
        String partyId = (String) context.get("partyId");
        String nodeKey = (String) context.get("nodeKey");
        try {
            partyId = ch.getPartyId(partyId, nodeKey, String.format("Cannot convert the nodeKey \"%s\" to a partyId", nodeKey));
            receiveRel = ch.findReceiveFromRelationship(partyId);
        } catch (GeneralException e) {
            Debug.logError(e, module);
            return GnServiceUtil.returnError(e);
        }

        Map<String, Object> params = UtilMisc.toMap("userLogin", userLogin, "partyId", partyId, "nodeKey", (Object) nodeKey);

        //precondition check
        boolean synchronizationAllowed = !"N".equalsIgnoreCase(receiveRel.getString("synchronizationAllowed"));
        boolean synchronizationActive = "Y".equalsIgnoreCase(receiveRel.getString("synchronizationActive"));
        if (synchronizationAllowed && !synchronizationActive) {
            //set flags
            try {
                Map<String, Object> servRes1 = dispatcher.runSync("gnSetSynchronizationActive", ctx.makeValidContext("gnSetSynchronizationActive", "IN", params));
                Map<String, Object> servRes2 = dispatcher.runSync("gnSetFalseSynchroAllowed", ctx.makeValidContext("gnSetFalseSynchroAllowed", "IN", params));

                //synchronization
                Map<String, Object> servRes3 = dispatcher.runSync("gnInternalSynchronizeNode", ctx.makeValidContext("gnInternalSynchronizeNode", "IN", params));

            } catch (Exception e) {
                Debug.logError(e, module);
                return GnServiceUtil.returnError(e);
            } finally {
                try {
                    dispatcher.runSync("gnSetSynchronizationDisactive", ctx.makeValidContext("gnSetSynchronizationDisactive", "IN", params));
                } catch (Exception e) {
                    Debug.logError(e, module);
                    return GnServiceUtil.returnError(e);
                }
            }

        } else {
            //precondition fail
            Exception e = new GnServiceException(OfbizErrors.BUSINESS_LOGIC_EXCEPTION, String.format("Precondition check fails trying to synchronize node \"%s\"", nodeKey));
            Debug.logError(e, module);
            return GnServiceUtil.returnError(e);
        }
        return result;
    }

    public static Map<String, Object> gnSetFalseSynchroAllowed(DispatchContext ctx, Map<String, ? extends Object> context) {
        try {
            String partyId = (String) context.get("partyId");
            new NodeTreeHelper(ctx, context).setFalseSynchroAllowed(partyId);
            return ServiceUtil.returnSuccess();
        } catch (GeneralException e) {
            Debug.logError(e, module);
            return GnServiceUtil.returnError(e);
        }
    }

    public static Map<String, Object> gnSetSynchronizationActive(DispatchContext ctx, Map<String, ? extends Object> context) {
        try {
            String partyId = (String) context.get("partyId");
            String nodeKey = (String) context.get("nodeKey");
            partyId = PartyNodeUtil.getPartyId(ctx, context, partyId, nodeKey, "PartyId and NodeKey cannot be empty both.");
            new NodeTreeHelper(ctx, context).setSynchronizationActive(partyId);
            return ServiceUtil.returnSuccess();
        } catch (GeneralException ge) {
            Debug.logError(ge, module);
            return GnServiceUtil.returnError(ge);
        }
    }

    public static Map<String, Object> gnSetSynchronizationDisactive(DispatchContext ctx, Map<String, ? extends Object> context) {
        try {
            String partyId = (String) context.get("partyId");
            String nodeKey = (String) context.get("nodeKey");
            partyId = PartyNodeUtil.getPartyId(ctx, context, partyId, nodeKey, "PartyId and NodeKey cannot be empty both.");
            new NodeTreeHelper(ctx, context).setSynchronizationDisactive(partyId);
            return ServiceUtil.returnSuccess();
        } catch (GeneralException ge) {
            Debug.logError(ge, module);
            return GnServiceUtil.returnError(ge);
        }
    }

    public static Map<String, Object> gnCanSynchronize(DispatchContext ctx, Map<String, ? extends Object> context) {
        try {
            Map<String, Object> result = ServiceUtil.returnSuccess();
            String partyId = (String) context.get("partyId");
            String nodeKey = (String) context.get("nodeKey");
            partyId = PartyNodeUtil.getPartyId(ctx, context, partyId, nodeKey, "PartyId and NodeKey cannot be empty both.");
            boolean canSynchronize = new NodeTreeHelper(ctx, context).gnCanSynchronize(partyId);
            result.put("canSynchronize", canSynchronize);
            return result;
        } catch (GeneralException ge) {
            Debug.logError(ge, module);
            return GnServiceUtil.returnError(ge);
        }
    }

    public static Map<String, Object> gnInternalSynchronizeNode(DispatchContext ctx, Map<String, ? extends Object> context) {
        try {
            Map<String, Object> result = ServiceUtil.returnSuccess();
            String partyId = (String) context.get("partyId");
            String nodeKey = (String) context.get("nodeKey");
            partyId = PartyNodeUtil.getPartyId(ctx, context, partyId, nodeKey, "PartyId and NodeKey cannot be empty both.");
            boolean canSynchronize = new AuthorizationSynchronizationHelper(ctx, context).gnSynchronizeNode(nodeKey);
            //result.put("canSynchronize", canSynchronize);
            return result;
        } catch (GeneralException ge) {
            Debug.logError(ge, module);
            return GnServiceUtil.returnError(ge);
        }
    }

    public static Map<String, Object> gnSynchronizeNodeForHolderCompany(DispatchContext ctx, Map<String, ? extends Object> context) {
        try {
            Map<String, Object> result = ServiceUtil.returnSuccess();
            String nodePartyId = (String) context.get("nodePartyId");
            String taxIdentificationNumber = (String) context.get("taxIdentificationNumber");
            new AuthorizationSynchronizationHelper(ctx, context).gnSynchronizeNodeForHolderCompany(nodePartyId, taxIdentificationNumber);
            return result;
        } catch (GeneralException ge) {
            Debug.logError(ge, module);
            return GnServiceUtil.returnError(ge);
        }
    }

}
