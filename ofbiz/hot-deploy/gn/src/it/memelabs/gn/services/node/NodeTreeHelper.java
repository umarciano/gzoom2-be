package it.memelabs.gn.services.node;

import it.memelabs.gn.services.AbstractServiceHelper;
import it.memelabs.gn.services.OfbizErrors;
import it.memelabs.gn.services.security.GnSecurity;
import it.memelabs.gn.services.security.PermissionsOfbiz;
import it.memelabs.gn.util.EntityConditionUtil;
import it.memelabs.gn.util.GnServiceException;
import it.memelabs.gn.util.PartyRelationshipIdUtil;
import javolution.util.FastList;
import javolution.util.FastMap;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilGenerics;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.entity.condition.EntityCondition;
import org.ofbiz.entity.condition.EntityConditionSubSelect;
import org.ofbiz.entity.condition.EntityConditionValue;
import org.ofbiz.entity.condition.EntityOperator;
import org.ofbiz.service.DispatchContext;
import org.ofbiz.service.GenericServiceException;
import org.ofbiz.service.ServiceUtil;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * 22/02/13
 *
 * @author Andrea Fossi
 */
public class NodeTreeHelper extends AbstractServiceHelper {
    protected static final String module = NodeTreeHelper.class.getName();

    /**
     * @param dctx    OFBiz DispatchContext
     * @param context Service Context
     */
    public NodeTreeHelper(DispatchContext dctx, Map<String, ? extends Object> context) {
        super(dctx, context);
    }

    /**
     * @param contextId
     * @param userLoginPartyId
     * @return
     * @throws GenericServiceException
     * @throws GenericEntityException
     */
    public List<Map<String, Object>> userProfileFindCompanies(String contextId, String userLoginPartyId) throws GenericServiceException, GenericEntityException {
        GnSecurity gnSecurity = new GnSecurity(dctx.getDelegator());
        List<Map<String, Object>> value;
        if (gnSecurity.hasPermission(PermissionsOfbiz.GN_ADMIN, userLogin)) {
            value = userProfileFindCompaniesGnAdmin();
        } else
            value = userProfileFindCompaniesOthers(contextId, userLoginPartyId);
        return value;
    }

    /**
     * Case 1
     *
     * @return
     * @throws org.ofbiz.entity.GenericEntityException
     *
     * @throws org.ofbiz.service.GenericServiceException
     *
     */
    private List<Map<String, Object>> userProfileFindCompaniesGnAdmin() throws GenericEntityException, GenericServiceException {
        //Find all companies that aren't owned by another Company or CompanyBase.

        //PartyRelationship condition
        EntityCondition relCond = EntityConditionUtil.makeRelationshipCondition(null, null, null, null, RelationshipTypeOfbiz.GN_OWNS.name(), true);
        //create a subSelect
        EntityConditionValue subSelCond = new EntityConditionSubSelect("PartyRelationship", "partyIdTo", relCond, true, delegator);
        EntityCondition companyCond = EntityCondition.makeCondition("partyId", EntityOperator.NOT_EQUAL, subSelCond);

        List<Map<String, Object>> ret = FastList.newInstance();
        List<GenericValue> companyIds = delegator.findList("GnCompany", companyCond, UtilMisc.toSet("partyId"), null, null, false);
        for (GenericValue companyId : companyIds) {
            ret.add(getTree(companyId.getString("partyId"), RelationshipTypeOfbiz.GN_OWNS.name()));
        }
        return ret;
    }

    /**
     * Case 2,3,4
     *
     * @return
     * @throws org.ofbiz.entity.GenericEntityException
     *
     * @throws org.ofbiz.service.GenericServiceException
     *
     */
    private List<Map<String, Object>> userProfileFindCompaniesOthers(String contextId, String userLoginPartyId) throws GenericEntityException, GenericServiceException {
        // memeadmin -     GN_C_AMTREE_MEMETOSC -- (GN_AMTREE)
        GnSecurity gnSecurity = new GnSecurity(delegator);

        Map<String, Object> gnContext = dispatcher.runSync("gnFindContextById", UtilMisc.toMap("userLogin", userLogin, "partyId", contextId));
        @SuppressWarnings("unchecked")
        Map<String, Object> company = (Map<String, Object>) dispatcher.runSync("gnFindCompanyWhereUserIsEmployed", UtilMisc.toMap("userLogin", userLogin, "userLoginPartyId", userLoginPartyId)).get("partyNode");
        @SuppressWarnings("unchecked")
        Map<String, Object> gnContextNode = (Map<String, Object>) gnContext.get("partyNode");


        //2
        if (gnSecurity.hasPermission(PermissionsOfbiz.GN_COMPANY_TREE_ADMIN.name(), userLogin)) {
            List<Map<String, Object>> ret = FastList.newInstance();
            if (UtilValidate.areEqual(company.get("partyId"), gnContextNode.get("partyId"))) {
                //company.put("partyRelationships",FastList.newInstance());
                ret.add(getTree((String) gnContextNode.get("partyId"), RelationshipTypeOfbiz.GN_OWNS.name()));
                //ret.add(company);
            } else {
                company.put("partyRelationships", FastList.newInstance());
                ret.add(company);
                if (PartyNodeTypeOfbiz.isACompany(gnContextNode.get("nodeType"))) {
                    ret.add(getTree((String) gnContextNode.get("partyId"), RelationshipTypeOfbiz.GN_OWNS.name()));
                }
            }
            return ret;
        } else
            //3
            if (gnSecurity.hasPermission(PermissionsOfbiz.GN_COMPANY_ADMIN.name(), userLogin)) {
                List<Map<String, Object>> ret = FastList.newInstance();
                if (UtilValidate.areEqual(company.get("partyId"), gnContextNode.get("partyId"))) {
                    company.put("partyRelationships", FastList.newInstance());
                    ret.add(company);
                } else {
                    company.put("partyRelationships", FastList.newInstance());
                    ret.add(company);
                    if (PartyNodeTypeOfbiz.isACompany(gnContextNode.get("nodeType"))) {
                        gnContextNode.put("partyRelationships", FastList.newInstance());
                        ret.add(gnContextNode);
                    }
                }
                return ret;
            } else {
                //4
                List<Map<String, Object>> ret = FastList.newInstance();
                company.put("partyRelationships", FastList.newInstance());
                ret.add(company);
                return ret;
            }
    }

    /**
     * @param partyNodeIdFrom
     * @param partyRelationshipTypeId
     * @return
     * @throws GenericEntityException
     * @throws GenericServiceException
     */
    protected Map<String, Object> getTree(String partyNodeIdFrom, String partyRelationshipTypeId) throws GenericEntityException, GenericServiceException {

        Map<String, Object> result = FastMap.newInstance();
        //List<GenericValue> ret = delegator.findByAnd("GnPartyGroupPartyNode", UtilMisc.toMap("partyId", partyNodeIdFrom));
        Map<String, Object> response = dispatcher.runSync("gnInternalFindPartyNodeById", UtilMisc.toMap("partyId", partyNodeIdFrom, "userLogin", userLogin, "findRelationships", "N"));
        @SuppressWarnings("unchecked")
        Map<String, Object> partyNode = (Map<String, Object>) response.get("partyNode");
        if (UtilValidate.isEmpty(partyNode)) {
            return result;
        } else {
            result.putAll(partyNode);
            result.remove("partyRelationships");
            getChildren(partyNodeIdFrom, partyRelationshipTypeId, result);
            return result;
        }
    }

    /**
     * @param contextId
     * @param partyRelationshipTypeId
     * @return
     * @throws GenericEntityException
     * @throws GenericServiceException
     */
    protected Map<String, Object> getTreeFromContext(String contextId, String partyRelationshipTypeId) throws GenericEntityException, GenericServiceException {
        GnSecurity gnSecurity = new GnSecurity(dctx.getDelegator());
        if (RelationshipTypeOfbiz.GN_OWNS.name().equals(partyRelationshipTypeId)) {
            if (!gnSecurity.hasPermission(PermissionsOfbiz.GN_ADMIN.name(), userLogin) &&
                    !gnSecurity.hasPermission(PermissionsOfbiz.GN_COMPANY_ADMIN.name(), userLogin) &&
                    !gnSecurity.hasPermission(PermissionsOfbiz.GN_COMPANY_TREE_ADMIN.name(), userLogin)) {
                throw new GnServiceException(OfbizErrors.ACCESS_DENIED, "UserLogin [" + userLogin.getString("userLoginId") + "] doesn't have permission to call service [getTreeFromContext]");
            }
        } else if (RelationshipTypeOfbiz.GN_PROPAGATES_TO.name().equals(partyRelationshipTypeId)) {
            if (!gnSecurity.hasPermission(PermissionsOfbiz.GN_ADMIN.name(), userLogin) &&
                    !gnSecurity.hasPermission(PermissionsOfbiz.GN_TREE_ADMIN.name(), userLogin)) {
                throw new GnServiceException(OfbizErrors.ACCESS_DENIED, "UserLogin [" + userLogin.getString("userLoginId") + "] doesn't have permission to call service [getTreeFromContext]");
            }
        } else {
            throw new GnServiceException(OfbizErrors.INVALID_PARAMETERS, "partyRelationship[" + partyRelationshipTypeId + "] is not allowed.");
        }
        EntityCondition relCondition = EntityConditionUtil.makeRelationshipCondition(contextId, "GN_CONTEXT", null, "GN_NODE_CONTEXT", RelationshipTypeOfbiz.GN_CONTEXT_NODE.name(), true);
        List<GenericValue> ret = delegator.findList("PartyRelationship", relCondition, null, null, null, false);
        if (ret.isEmpty()) {
            Debug.logError("No party relationship between the selected context and a node.", module);
            return FastMap.newInstance();
        } else {
            GenericValue partyRelationship = ret.get(0);
            String partyIdTo = (String) partyRelationship.get("partyIdTo");

            return getTree(partyIdTo, partyRelationshipTypeId);

        }
    }

    @SuppressWarnings("unchecked")
    public void getChildren(String partyId, String partyRelationshipTypeId, Map<String, Object> parentNode) throws GenericServiceException, GenericEntityException {
        EntityCondition entityCondition = EntityConditionUtil.makeRelationshipCondition(partyId, null, null, null, partyRelationshipTypeId, true);
        List<GenericValue> partyIdsTo = delegator.findList("PartyRelationship", entityCondition, null, null, null, false);
        if (!UtilValidate.isEmpty(partyIdsTo)) {
            for (GenericValue rel : partyIdsTo) {
                String partyNodeId = (String) rel.get("partyIdTo");
                String relationshipId = PartyRelationshipIdUtil.mapToKey(rel);
                String relationshipName = (String) rel.get("relationshipName");
                Map<String, Object> node = dispatcher.runSync("gnInternalFindPartyNodeById", UtilMisc.toMap("partyId", partyNodeId, "userLogin", userLogin, "findRelationships", "N"));
                node = (Map<String, Object>) node.get("partyNode");
                //remove generic relationships
                node.remove("partyRelationships");
                Map<String, Object> relation = UtilMisc.toMap("partyRelationshipTypeId", partyRelationshipTypeId, "target", node, "partyRelationshipId", relationshipId, "relationshipName", relationshipName);
//                Debug.logInfo(" ", module);
//                Debug.logInfo("parent: " + partyId, module);
//                Debug.logInfo("partyRelationshipTypeId: " + partyRelationshipTypeId, module);
//                Debug.logInfo("target: " + node.values().toString(), module);
//                Debug.logInfo(" ", module);
                UtilMisc.addToListInMap(relation, parentNode, "partyRelationships");
                getChildren(partyNodeId, partyRelationshipTypeId, node);
            }
        } else {
            parentNode.put("partyRelationships", Collections.emptyList());
        }
    }

    /**
     * @param partyId
     * @return
     * @throws GenericServiceException
     * @throws GenericEntityException
     */
    @SuppressWarnings("unchecked")
    protected Map<String, Object> findParentWhoPropagatesToNode(String partyId) throws GenericServiceException, GenericEntityException {
        EntityCondition cond = EntityConditionUtil.makeRelationshipCondition(partyId, "GN_PROPAGATION_NODE", null, "GN_PROPAGATION_NODE", RelationshipTypeOfbiz.GN_RECEIVES_FROM.name(), true);
        List<GenericValue> rels = delegator.findList("GnPartyRelationship", cond, null, null, null, false);
        if (rels.size() != 1) {
            Debug.logError("Relationship error: found " + rels.size() + " relations (count<>1)) for node[" + partyId + "]", module);
            return null;
        }

        Map<String, Object> rel = UtilMisc.makeMapWritable(rels.get(0));
        String parentPartyId = (String) rel.get("partyIdTo");

        Map<String, Object> result = ServiceUtil.returnSuccess();
        result.put("partyId", parentPartyId);
        return result;
    }

    /**
     * @param partyId
     * @return
     * @throws GenericServiceException
     * @throws GenericEntityException
     */
    @SuppressWarnings("unchecked")
    protected void setFalseSynchroAllowed(String partyId) throws GenericServiceException, GenericEntityException {
        EntityCondition cond = EntityConditionUtil.makeRelationshipCondition(partyId, "GN_PROPAGATION_NODE", null, "GN_PROPAGATION_NODE", RelationshipTypeOfbiz.GN_RECEIVES_FROM.name(), true);
        List<GenericValue> rels = delegator.findList("PartyRelationship", cond, null, null, null, false);
        if (rels.size() != 1) {
            Debug.logError("Relationship error: found " + rels.size() + " relations (count<>1)) for node[" + partyId + "]", module);
        }

        GenericValue genericValue = rels.get(0);
        genericValue.put("synchronizationAllowed", "N");
        delegator.store(genericValue);
    }

    protected void setSynchronizationActive(String partyId) throws GenericServiceException, GenericEntityException {
        EntityCondition cond = EntityConditionUtil.makeRelationshipCondition(partyId, "GN_PROPAGATION_NODE", null, "GN_PROPAGATION_NODE", RelationshipTypeOfbiz.GN_RECEIVES_FROM.name(), true);
        List<GenericValue> rels = delegator.findList("PartyRelationship", cond, null, null, null, false);
        if (rels.size() != 1) {
            Debug.logError("Relationship error: found " + rels.size() + " relations (count<>1)) for node[" + partyId + "]", module);
        }

        GenericValue genericValue = rels.get(0);
        genericValue.put("synchronizationActive", "Y");
        delegator.store(genericValue);
    }

    protected void setSynchronizationDisactive(String partyId) throws GenericServiceException, GenericEntityException {
        EntityCondition cond = EntityConditionUtil.makeRelationshipCondition(partyId, "GN_PROPAGATION_NODE", null, "GN_PROPAGATION_NODE", RelationshipTypeOfbiz.GN_RECEIVES_FROM.name(), true);
        List<GenericValue> rels = delegator.findList("PartyRelationship", cond, null, null, null, false);
        if (rels.size() != 1) {
            Debug.logError("Relationship error: found " + rels.size() + " relations (count<>1)) for node[" + partyId + "]", module);
        }

        GenericValue genericValue = rels.get(0);
        genericValue.put("synchronizationActive", "N");
        delegator.store(genericValue);
    }

    public boolean gnCanSynchronize(String partyId) throws GenericServiceException, GenericEntityException {
        boolean canSynchronize = false;
        @SuppressWarnings("unchecked")
        Map<String, Object> node = new NodeSearchHelper(dctx, context).findPartyNodeById(partyId, true);

        List<Map<String, Object>> relations = UtilGenerics.checkList(node.get("partyRelationships"));
        if (UtilValidate.isEmpty(relations)) {
            throw new GnServiceException(OfbizErrors.BUSINESS_LOGIC_EXCEPTION, String.format("Unable to find relationships for node with id \"%s\"", partyId));
        }
        boolean foundReceivesFrom = false;
        for (Map<String, Object> relation : relations) {
            if (RelationshipTypeOfbiz.GN_RECEIVES_FROM.toString().equals(relation.get("partyRelationshipTypeId"))) {
                foundReceivesFrom = true;
                PartyRelationshipHelper partyRelationshipHelper = new PartyRelationshipHelper(dctx, context);
                canSynchronize = partyRelationshipHelper.isSynchronizationAllowed(relation)
                        && !partyRelationshipHelper.isSynchronizationActive(relation);
                break;
            }
        }
        if (!foundReceivesFrom) {
            throw new GnServiceException(OfbizErrors.BUSINESS_LOGIC_EXCEPTION, String.format("Unable to find a %s relationship for node \"%s\"", RelationshipTypeOfbiz.GN_RECEIVES_FROM, partyId));
        }
        return canSynchronize;
    }
}
