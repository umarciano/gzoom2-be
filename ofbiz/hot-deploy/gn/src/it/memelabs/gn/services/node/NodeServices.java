package it.memelabs.gn.services.node;

import it.memelabs.gn.services.security.GnSecurity;
import it.memelabs.gn.util.GnServiceUtil;
import it.memelabs.gn.util.PartyNodeUtil;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.GeneralException;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.service.DispatchContext;
import org.ofbiz.service.LocalDispatcher;
import org.ofbiz.service.ServiceUtil;

import java.util.List;
import java.util.Map;

/**
 * 18/01/13
 *
 * @author Andrea Fossi
 */
public class NodeServices {

    private static final String module = NodeServices.class.getName();

    public Map<String, Object> gnCreateUpdatePartyNode(DispatchContext ctx, Map<String, Object> context) {
        Map<String, Object> result = GnServiceUtil.returnSuccess();
        Map<String, Object> serviceMap = null;
        try {
            String partyId = (String) context.get("partyId");
            String nodeKey = (String) context.get("nodeKey");
            NodeHelper nodeHelper = new NodeHelper(ctx, context);
            if (nodeHelper.syntacticVerifyCompanyTaxAndFiscalCode(context)) {
                if (UtilValidate.isEmpty(partyId) && UtilValidate.isEmpty(nodeKey)) {
                    Map<String, Object> createMap = ctx.makeValidContext("gnCreatePartyNode", "IN", context);
                    serviceMap = gnCreatePartyNode(ctx, createMap);
                    result.put("partyRelationshipIds", serviceMap.get("partyRelationshipIds"));
                    result.put("reversePartyRelationshipIds", serviceMap.get("reversePartyRelationshipIds"));
                    partyId = (String) serviceMap.get("partyId");
                    nodeKey = (String) serviceMap.get("nodeKey");
                    Debug.log("GnPartyNode created, partyId is " + partyId, module);
                } else {
                    Map<String, Object> updateMap = ctx.makeValidContext("gnUpdatePartyNode", "IN", context);
                    serviceMap = gnUpdatePartyNode(ctx, updateMap);
                    partyId = (String) serviceMap.get("partyId");
                    nodeKey = (String) serviceMap.get("nodeKey");
                    Debug.log("GnPartyNode updated, partyId is " + partyId, module);
                }
            }
            if (GnServiceUtil.isError(serviceMap)) {
                return serviceMap;
            }
            result.put("partyId", partyId);
            result.put("nodeKey", nodeKey);

            String nodeType = (String) context.get("nodeType");
            LocalDispatcher dispatcher = ctx.getDispatcher();
            if (PartyNodeTypeOfbiz.isACompanyBase(nodeType)) {
                Map<String, Object> companyBaseMap = dispatcher.runSync("gnInternalFindPartyNodeById", UtilMisc.toMap("userLogin", context.get("userLogin"), "findRelationships", "N", "partyId", partyId));
                result.put("companyBase", companyBaseMap.get("partyNode"));
            }

            return result;
        } catch (Exception e) {
            Debug.logError(e, module);
            return GnServiceUtil.returnError(e);
        }
    }

    @SuppressWarnings("unchecked")
    public static Map<String, Object> gnCreatePartyNode(DispatchContext ctx, Map<String, Object> context) {
        try {
            String nodeType = (String) context.get("nodeType");
            GnSecurity gnSecurity = new GnSecurity(ctx.getDelegator());
            boolean hasPermission = gnSecurity.hasPermission("GN_ADMIN", (GenericValue) context.get("userLogin"));
            hasPermission |= gnSecurity.hasEntityPermission("GN_TREE", "UPDATE", (GenericValue) context.get("userLogin"));
            hasPermission |= gnSecurity.hasEntityPermission("GN_NODE", "UPDATE", (GenericValue) context.get("userLogin"));
            if (PartyNodeTypeOfbiz.isACompany(nodeType) || PartyNodeTypeOfbiz.isACompanyBase(nodeType)) {
                hasPermission |= gnSecurity.hasEntityPermission("GN_COMPANY", "UPDATE", (GenericValue) context.get("userLogin"));
                hasPermission |= gnSecurity.hasEntityPermission("GN_COMPANY_TREE", "UPDATE", (GenericValue) context.get("userLogin"));
            }
            if (!hasPermission && !(Boolean) context.get("internal")) {
                return GnServiceUtil.returnInvalidPermissionError("gnCreatePartyNode");
            } else {
                Map<String, Object> result = ServiceUtil.returnSuccess();
                result.putAll(new NodeHelper(ctx, context).gnCreatePartyNode(context));
                return result;
            }
        } catch (Throwable t) {
            Debug.logError(t, module);
            return GnServiceUtil.returnError(t);
        }
    }

    @SuppressWarnings("unchecked")
    public static Map<String, Object> gnUpdatePartyNode(DispatchContext ctx, Map<String, Object> context) {
        try {
            NodeHelper nodeHelper = new NodeHelper(ctx, context);
            String partyId = (String) context.get("partyId");
            String nodeKey = (String) context.get("nodeKey");
            String nodeType = (String) context.get("nodeType");
            partyId = PartyNodeUtil.getPartyId(ctx, context, partyId, nodeKey, "PartyId and NodeKey cannot be empty both.");

            context.put("partyId", partyId);
            context.put("nodeKey", nodeKey);

            GnSecurity gnSecurity = new GnSecurity(ctx.getDelegator());
            boolean hasPermission = gnSecurity.hasPermission("GN_ADMIN", (GenericValue) context.get("userLogin"));
            hasPermission |= gnSecurity.hasEntityPermission("GN_TREE", "UPDATE", (GenericValue) context.get("userLogin"));
            hasPermission |= gnSecurity.hasEntityPermission("GN_NODE", "UPDATE", (GenericValue) context.get("userLogin"));
            if (PartyNodeTypeOfbiz.isACompany(nodeType) || PartyNodeTypeOfbiz.isACompanyBase(nodeType)) {
                hasPermission |= gnSecurity.hasEntityPermission("GN_COMPANY", "UPDATE", (GenericValue) context.get("userLogin"));
                hasPermission |= gnSecurity.hasEntityPermission("GN_COMPANY_TREE", "UPDATE", (GenericValue) context.get("userLogin"));
            }
            if (!hasPermission && !(Boolean) context.get("internal")) {
                return GnServiceUtil.returnInvalidPermissionError("gnUpdatePartyNode");
            } else {
                Map<String, Object> result = ServiceUtil.returnSuccess();
                result.putAll(nodeHelper.gnUpdatePartyNode(context));
                return result;
            }
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
    @SuppressWarnings("unchecked")
    public static Map<String, Object> gnFindPartyNodeById(DispatchContext ctx, Map<String, ? extends Object> context) {
        Map<String, Object> result = ServiceUtil.returnSuccess();
        try {
            String partyId = (String) context.get("partyId");
            String nodeKey = (String) context.get("nodeKey");
            partyId = PartyNodeUtil.getPartyId(ctx, (Map<String, Object>) context, partyId, nodeKey, "PartyId and NodeKey cannot be empty both.");

            boolean findRelationships = "Y".equals(context.get("findRelationships"));
            if (UtilValidate.isNotEmpty(partyId)) {
                Map<String, Object> node = new NodeSearchHelper(ctx, context).findPartyNodeById(partyId, findRelationships);
                result.put("partyNode", node);
            } else
                result.put("partyNode", null);
        } catch (Throwable t) {
            Debug.logError(t, module);
            return GnServiceUtil.returnError(t);
        }
        return result;
    }

    /**
     * @param ctx
     * @param context
     * @return
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> gnFindExtendedNodeById(DispatchContext ctx, Map<String, ? extends Object> context) {
        NodeHelper nodeHelper = new NodeHelper(ctx, context);
        try {
            Map<String, Object> result = ServiceUtil.returnSuccess();
            String partyId = (String) context.get("partyId");
            String nodeKey = (String) context.get("nodeKey");
            partyId = PartyNodeUtil.getPartyId(ctx, context, partyId, nodeKey, "PartyId and NodeKey cannot be empty both.");

            result.putAll(new NodeSearchHelper(ctx, context).gnFindExtendedNodeById(partyId, true));
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
    @SuppressWarnings("unchecked")
    public static Map<String, Object> gnFindCompanyNodeById(DispatchContext ctx, Map<String, ? extends Object> context) {
        NodeHelper nodeHelper = new NodeHelper(ctx, context);
        try {
            Map<String, Object> result = ServiceUtil.returnSuccess();
            String partyId = (String) context.get("partyId");
            String nodeKey = (String) context.get("nodeKey");
            partyId = PartyNodeUtil.getPartyId(ctx, context, partyId, nodeKey, "PartyId and NodeKey cannot be empty both.");

            NodeSearchHelper nodeSearchHelper = new NodeSearchHelper(ctx, context);
            result.putAll(nodeSearchHelper.gnFindExtendedNodeById(partyId, PartyNodeTypeOfbiz.COMPANY, true));
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
    @SuppressWarnings("unchecked")
    public static Map<String, Object> gnFindCompanyNodeByTaxIdentificationNumber(DispatchContext ctx, Map<String, ? extends Object> context) {
        try {
            Map<String, Object> result = ServiceUtil.returnSuccess();
            String taxIdentificationNumber = (String) context.get("taxIdentificationNumber");
            NodeSearchHelper nodeSearchHelper = new NodeSearchHelper(ctx, context);
            result.put("partyNode", nodeSearchHelper.gnFindCompanyNodeByTaxIdentificationNumber(taxIdentificationNumber));
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
    @SuppressWarnings("unchecked")
    public static Map<String, Object> gnFindCompanyBaseNodeById(DispatchContext ctx, Map<String, ? extends Object> context) {
        NodeHelper nodeHelper = new NodeHelper(ctx, context);
        try {
            Map<String, Object> result = ServiceUtil.returnSuccess();
            String partyId = (String) context.get("partyId");
            String nodeKey = (String) context.get("nodeKey");
            partyId = PartyNodeUtil.getPartyId(ctx, context, partyId, nodeKey, "PartyId and NodeKey cannot be empty both.");

            NodeSearchHelper nodeSearchHelper = new NodeSearchHelper(ctx, context);
            result.putAll(nodeSearchHelper.gnFindExtendedNodeById(partyId, PartyNodeTypeOfbiz.COMPANY_BASE, true));
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

    public static Map<String, Object> gnFindFreeCompaniesAndBases(DispatchContext ctx, Map<String, ? extends Object> context) {
        try {
            Map<String, Object> result = ServiceUtil.returnSuccess();
            String partyRelationshipTypeId = (String) context.get("partyRelationshipTypeId");
            GenericValue userLogin = (GenericValue) context.get("userLogin");
            String userLoginId = userLogin.getString("userLoginId");
            String userLoginPartyId = userLogin.getString("partyId");
            String activeContextId = userLogin.getString("activeContextId");
            result.put("partyNodes", new NodeSearchHelper(ctx, context).gnFindFreeCompaniesAndBases(partyRelationshipTypeId, userLoginPartyId, activeContextId));
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
    @SuppressWarnings("unchecked")
    public static Map<String, Object> gnUserProfileFindCompanyBases(DispatchContext ctx, Map<String, ? extends Object> context) {
        try {
            Map<String, Object> result = ServiceUtil.returnSuccess();
            String companyPartyId = (String) context.get("partyId");
            String companyNodeKey = (String) context.get("nodeKey");
            companyPartyId = PartyNodeUtil.getPartyId(ctx, context, companyPartyId, companyNodeKey, "PartyId and NodeKey cannot be empty both.");

            result.put("partyNodes", new NodeSearchHelper(ctx, context).userProfileFindCompanyBases(companyPartyId));
            return result;
        } catch (GeneralException e) {
            Debug.logError(e, module);
            return GnServiceUtil.returnError(e.getMessage());
        }
    }

    /**
     * Retrieve partyId from nodeKey.
     * If nodeKey is null return null;
     *
     * @param ctx
     * @param context
     * @return
     */
    public static Map<String, Object> gnGetPartyIdFromNodeKey(DispatchContext ctx, Map<String, ? extends Object> context) {
        try {
            Map<String, Object> result = ServiceUtil.returnSuccess();
            String nodeKey = (String) context.get("nodeKey");
            result.put("partyId", new NodeHelper(ctx, context).getPartyId(nodeKey));
            return result;
        } catch (GeneralException e) {
            Debug.logError(e, module);
            return GnServiceUtil.returnError(e.getMessage());
        }
    }

    /**
     * Retrieve nodeKey from partyId.
     * If nodeKey is null return null;
     *
     * @param ctx
     * @param context
     * @return
     */
    public static Map<String, Object> gnGetNodeKeyFromPartyId(DispatchContext ctx, Map<String, ? extends Object> context) {
        try {
            Map<String, Object> result = ServiceUtil.returnSuccess();
            String partyId = (String) context.get("partyId");
            result.put("nodeKey", new NodeHelper(ctx, context).getNodeKey(partyId));
            return result;
        } catch (GeneralException e) {
            Debug.logError(e, module);
            return GnServiceUtil.returnError(e.getMessage());
        }
    }

    /**
     * Find belonging to company/privateCompany of a companyBase/privateCompanyBase
     *
     * @param ctx
     * @param context
     * @return
     */
    public static Map<String, Object> gnFindOwnerCompanyByCompanyBaseId(DispatchContext ctx, Map<String, ? extends Object> context) {
        try {
            Map<String, Object> result = ServiceUtil.returnSuccess();
            String partyId = (String) context.get("companyBasePartyId");
            result.putAll(new NodeSearchHelper(ctx, context).gnFindOwnerCompanyByCompanyBaseId(partyId));
            return result;
        } catch (GeneralException e) {
            Debug.logError(e, module);
            return GnServiceUtil.returnError(e.getMessage());
        }
    }

    public static Map<String, Object> gnDeletePrivateCompanyOrCompanyBase(DispatchContext ctx, Map<String, ? extends Object> context) {
        try {
            Map<String, Object> result = ServiceUtil.returnSuccess();
            String partyId = (String) context.get("partyId");
            new NodeHelper(ctx, context).gnDeletePrivateCompanyOrCompanyBase(partyId);
            return result;
        } catch (GeneralException e) {
            Debug.logError(e, module);
            return GnServiceUtil.returnError(e.getMessage());
        }
    }

    public static Map<String, Object> gnFindPublicCompanyBases(DispatchContext ctx, Map<String, Object> context) {
        try {
            @SuppressWarnings("unchecked")
            Map<String, Object> userLogin = (Map<String, Object>) context.get("userLogin");

            NodeSearchHelper nodeSearchHelper = new NodeSearchHelper(ctx, context);

            String taxIdentificationNumber = (String) context.get("taxIdentificationNumber");

            List<Map<String, Object>> partyNodeList = nodeSearchHelper.findPublicCompanyBases(taxIdentificationNumber);

            Map<String, Object> result = ServiceUtil.returnSuccess();
            result.put("partyNodes", partyNodeList);
            return result;
        } catch (GeneralException e) {
            Debug.logError(e, module);
            return GnServiceUtil.returnError(e);
        }
    }

    @SuppressWarnings("unchecked")
    public static Map<String, Object> gnFindCompanyBasesByAddress(DispatchContext ctx, Map<String, Object> context) {
        try {
            Map<String, Object> address = (Map<String, Object>) context.get("address");
            String nodeKeyToExclude = (String) context.get("nodeKeyToExclude");
            String parentNodeKey = (String) context.get("parentNodeKey");
            List<Map<String, Object>> companyBases = new NodeSearchHelper(ctx, context).gnFindCompanyBasesByAddress(address, nodeKeyToExclude, parentNodeKey);
            Map<String, Object> result = ServiceUtil.returnSuccess();
            result.put("companyBases", companyBases);
            return result;
        } catch (GeneralException e) {
            Debug.logError(e, module);
            return GnServiceUtil.returnError(e);
        }
    }
}
