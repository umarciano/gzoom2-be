package it.memelabs.gn.services.node;

import it.intext.multifield.matcher.exception.ComparerException;
import it.intext.multifield.matcher.exception.TokenizerException;
import it.memelabs.gn.services.AbstractServiceHelper;
import it.memelabs.gn.services.OfbizErrors;
import it.memelabs.gn.services.authorization.AgreementTypesOfbiz;
import it.memelabs.gn.services.authorization.AuthorizationHelper;
import it.memelabs.gn.services.security.GnSecurity;
import it.memelabs.gn.services.security.PermissionsOfbiz;
import it.memelabs.gn.services.user.UserHelper;
import it.memelabs.gn.util.EntityConditionUtil;
import it.memelabs.gn.util.GnServiceException;
import it.memelabs.gn.util.PartyRelationshipIdUtil;
import it.memelabs.gn.util.TreeUtil;
import it.memelabs.gn.util.find.GnFindUtil;
import it.memelabs.greenebula.multifield.matcher.AddressMatcher;
import javolution.util.FastList;
import javolution.util.FastMap;
import javolution.util.FastSet;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.GeneralException;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.entity.condition.*;
import org.ofbiz.entity.model.DynamicViewEntity;
import org.ofbiz.entity.model.ModelKeyMap;
import org.ofbiz.entity.util.EntityListIterator;
import org.ofbiz.entity.util.EntityUtil;
import org.ofbiz.service.DispatchContext;
import org.ofbiz.service.GenericServiceException;
import org.ofbiz.service.LocalDispatcher;

import java.util.*;

/**
 * 04/04/13
 *
 * @author Andrea Fossi
 */
public class NodeSearchHelper extends AbstractServiceHelper {
    public NodeSearchHelper(DispatchContext dctx, Map<String, ? extends Object> context) {
        super(dctx, context);
        partyHelper = new PartyHelper(dctx, context);
    }

    private PartyHelper partyHelper;
    private static final String module = NodeSearchHelper.class.getName();


    @Deprecated
    public Map<String, Object> findPartyNodeById(String partyId, boolean loadRelationships) throws GenericEntityException, GenericServiceException {
        Map<String, Object> partyNode = UtilMisc.makeMapWritable(delegator.findOne("GnPartyGroupPartyNode", UtilMisc.toMap("partyId", partyId), false));
        //TODO: cache on view return error
        //GenericValue partyGroup = delegator.findOne("PartyGroup", UtilMisc.toMap("partyId", partyId), true);
        //partyGroup.size();
        Debug.logVerbose("called findPartyNodeById method with loadRelationships flag[" + loadRelationships + "]", module);
        //load modifier info.
        //partyNode.put("modifier", findUserByUserLoginId(dctx, context, (String) partyNode.get("lastModifiedByUserLogin")));

        // nodeType=PartyNodeTypeOfbiz.COMPANY_BASE.name()
        String nodeType = (String) partyNode.get("nodeType");
        if (PartyNodeTypeOfbiz.isACompanyBase(nodeType) ||
                PartyNodeTypeOfbiz.isAPrivateCompanyBase(nodeType)) {
            // get address
            partyNode.put("address", getCompanyBaseAddress(partyId));
            //get contact
            partyNode.put("contact", getPartyContact(partyId));
            //get reference to parent
        }

        if (loadRelationships) {
            partyNode.put("partyRelationships", findPartyNodeRelationships(partyId));
        } else {
            if (PartyNodeTypeOfbiz.isACompanyBase(nodeType)) {
                Map<String, Object> ownerCompany = getOwnerCompany(partyId);
                if (ownerCompany != null) UtilMisc.addToListInMap(ownerCompany, partyNode, "partyRelationships");
            } else {
                partyNode.put("partyRelationships", FastList.newInstance());
            }
        }
        // Debug.log("ZZZZZZZZ: "+partyNode.get("lastModifiedByUserLogin"));
        // Debug.log("ZZZZZZZZ: "+partyNode.get("lastModifiedDate"));
        return partyNode;
    }

    /**
     * @param companyBaseId
     * @return
     * @throws GenericEntityException
     * @throws GenericServiceException
     */
    private Map<String, Object> getOwnerCompany(String companyBaseId) throws GenericEntityException, GenericServiceException {
        if (!partyHelper.gnPartyRoleCheck(companyBaseId, PartyRoleOfbiz.GN_COMPANY_BASE.name())) {
            throw new GnServiceException("partyNode[" + companyBaseId + "] is not a companyBase");
        }

        EntityCondition cond = EntityConditionUtil.makeRelationshipCondition(companyBaseId, "GN_COMPANY_BASE", null, "GN_COMPANY", RelationshipTypeOfbiz.GN_BELONGS_TO.name(), true);
        List<GenericValue> rels = delegator.findList("GnPartyRelationship", cond, null, null, null, false);
        if (rels.size() != 1) {
            Debug.logError("Relationship error: found " + rels.size() + " relations (count<>1)) for node[" + companyBaseId + "]", module);
            return null;
        }
        Map<String, Object> rel = UtilMisc.makeMapWritable(rels.get(0));
        rel.put("partyRelationshipId", PartyRelationshipIdUtil.mapToKey(rel));
        rel.put("target", findPartyNodeById((String) rel.get("partyIdTo"), false));
        return rel;
    }

    public Map<String, Object> gnFindOwnerCompanyByCompanyBaseId(String companyBaseId) throws GenericEntityException, GenericServiceException {
        if (partyHelper.gnPartyRoleCheck(companyBaseId, PartyRoleOfbiz.GN_COMPANY_BASE.name())) {
            EntityCondition cond = EntityConditionUtil.makeRelationshipCondition(companyBaseId, PartyRoleOfbiz.GN_COMPANY_BASE.name(), null, PartyRoleOfbiz.GN_COMPANY.name(), RelationshipTypeOfbiz.GN_BELONGS_TO.name(), true);
            List<GenericValue> rels = delegator.findList("GnPartyRelationship", cond, null, null, null, false);
            if (rels.size() == 1) {
                Map<String, Object> result = FastMap.newInstance();
                result.put("companyBasePartyId", rels.get(0).get("partyIdFrom"));
                result.put("companyBaseNodeKey", rels.get(0).get("nodeKeyFrom"));
                result.put("companyPartyId", rels.get(0).get("partyIdTo"));
                result.put("companyNodeKey", rels.get(0).get("nodeKeyTo"));
                return result;
            } else if (rels.size() == 0) {
                throw new GnServiceException(OfbizErrors.ENTITY_EXCEPTION, "parent not found");
            } else {
                throw new GnServiceException(OfbizErrors.ENTITY_EXCEPTION, "more than one parents found");
            }
        } else {
            throw new GnServiceException(OfbizErrors.INVALID_PARAMETERS, "partyId doesn't belong to a companyBase");
        }
    }

    /**
     * Find node outgoing relationships
     * <p/>
     * Greennebula RelationKind:
     * DELEGATES_TO,
     * BELONGS_TO,
     * OWNS,
     * PROPAGATES_AUTHORIZATION_TO,
     * PUBLISHES_AUTHORIZATION_TO,
     * RECEIVES_AUTHORIZATION_FROM;
     *
     * @param partyIdFrom partyNodeId
     * @return relationships
     * @throws org.ofbiz.entity.GenericEntityException
     * @throws org.ofbiz.service.GenericServiceException
     */
    @SuppressWarnings("unchecked")
    private List<Map<String, Object>> findPartyNodeRelationships(String partyIdFrom) throws GenericServiceException, GenericEntityException {
        List<String> partyRelationshipTypeIds = Arrays.asList(RelationshipTypeOfbiz.GN_CBASE_DELEGATES.name(), RelationshipTypeOfbiz.GN_BELONGS_TO.name(),
                RelationshipTypeOfbiz.GN_OWNS.name(), RelationshipTypeOfbiz.GN_PROPAGATES_TO.name(), RelationshipTypeOfbiz.GN_PUBLISHES_TO_ROOT.name(), RelationshipTypeOfbiz.GN_RECEIVES_FROM.name());
        List<String> partyIdsFrom = Arrays.asList(partyIdFrom);
        Map<String, Object> res = PartyRelationshipService.gnFindPartyRelationships(dctx, UtilMisc.toMap("userLogin", userLogin, "partyRelationshipTypeIds", partyRelationshipTypeIds, "partyIdsFrom", partyIdsFrom));

        return (List<Map<String, Object>>) res.get("partyRelationships");
    }

    @SuppressWarnings("unchecked")
    private Map<String, Object> getCompanyBaseAddress(String partyId) throws GenericServiceException {
        List<Map<String, Object>> addresses = (List<Map<String, Object>>) dctx.getDispatcher().
                runSync("gnGetPostalAddressFromPartyId", UtilMisc.toMap("partyId", partyId, "userLogin", userLogin))
                .get("addresses");
        if (addresses.size() > 0) return addresses.get(0);
        else return null;
    }


    @SuppressWarnings("unchecked")
    private Map<String, Object> getPartyContact(String partyId) throws GenericServiceException {
        LocalDispatcher dispatcher = dctx.getDispatcher();
        Map<String, Object> srvContext = dispatcher.getDispatchContext().makeValidContext("gnGetPartyContact", "IN", context);
        srvContext.put("partyId", partyId);
        Map<String, Object> srvResult = dispatcher.runSync("gnGetPartyContact", srvContext);
        return (Map<String, Object>) srvResult.get("contact");
    }

    /**
     * @param partyId (companyId)
     * @return
     * @throws GenericServiceException
     */
    private List<Map<String, ? extends Object>> findCompanyBasesByCompanyId(String partyId) throws GenericServiceException, GenericEntityException {
        EntityCondition cond = EntityConditionUtil.makeRelationshipCondition(null, "GN_COMPANY_BASE", partyId, "GN_COMPANY", RelationshipTypeOfbiz.GN_BELONGS_TO.name(), true);
        List<Map<String, ? extends Object>> companyBases = FastList.newInstance();
        List<GenericValue> rels = delegator.findList("GnPartyRelationship", cond, null, null, null, false);
        for (GenericValue rel : rels) {
            Map<String, Object> companyBase = findPartyNodeById(rel.getString("partyIdFrom"), false);
           /* Map<String, Object> _rel = UtilMisc.makeMapWritable(rel);
            _rel.put("target", findPartyNodeById(rel.getString("partyIdTo"), false));
            UtilMisc.addToListInMap(_rel, companyBase, "partyRelationships");*/
            companyBases.add(companyBase);
        }
        return companyBases;

    }

    /**
     * @param companyBaseId
     * @return
     * @throws GenericServiceException
     */
    public Map<String, Object> findCompanyByCompanyBaseId(String companyBaseId) throws GenericServiceException, GenericEntityException {
        EntityCondition cond = EntityConditionUtil.makeRelationshipCondition(companyBaseId, PartyRoleOfbiz.GN_COMPANY_BASE, null, PartyRoleOfbiz.GN_COMPANY, RelationshipTypeOfbiz.GN_BELONGS_TO, true);
        List<GenericValue> rels = delegator.findList("GnPartyRelationship", cond, null, null, null, false);
        if (rels.size() == 0)
            throw new GnServiceException(OfbizErrors.ENTITY_EXCEPTION, "CompanyBase[" + companyBaseId + "] doesn't belong to any Company.");
        if (rels.size() > 1)
            throw new GnServiceException(OfbizErrors.ENTITY_EXCEPTION, "CompanyBase[" + companyBaseId + "] doesn't belong more than 1 Companies.");
        Map<String, Object> company = findPartyNodeById(rels.get(0).getString("partyIdTo"), false);
        return company;

    }

    public Map<String, Object> gnFindExtendedNodeById(String partyId, PartyNodeTypeOfbiz nodeType, boolean loadInfoHolderConstraint) throws GenericServiceException, GenericEntityException {
        Map<String, Object> result = gnFindExtendedNodeById(partyId, loadInfoHolderConstraint);
        if (UtilValidate.isNotEmpty(result.get("partyNode"))) {
            @SuppressWarnings("unchecked")
            Map<String, Object> partyNode = (Map<String, Object>) result.get("partyNode");
            if (nodeType.name().equals(partyNode.get("nodeType"))) return result;
        }
        return UtilMisc.toMap("partyNode", null);
    }

    public Map<String, Object> gnFindExtendedNodeById(String partyId, boolean loadInfoHolderConstraint) throws GenericServiceException, GenericEntityException {
        Map<String, Object> result = FastMap.newInstance();
        if (UtilValidate.isNotEmpty(partyId)) {
            Map<String, Object> node = findPartyNodeById(partyId, true);
            Map<String, Object> filterMap = UtilMisc.toMap("nodeId", (Object) partyId, "roleTypeIdFrom", "GN_CONTEXT", "roleTypeIdTo", "GN_NODE_CONTEXT");
            List<EntityCondition> filterConditions = UtilMisc.toList(EntityCondition.makeCondition(filterMap),
                    EntityUtil.getFilterByDateExpr(), EntityCondition.makeCondition("contextId", EntityComparisonOperator.NOT_EQUAL, "GN_SYSTEM_CTX"));
            List<GenericValue> findResponse = delegator.findList("GnContextPartyNode", EntityCondition.makeCondition(filterConditions), null, null, null, false);
            result.put("contexts", FastList.newInstance());
            for (GenericValue gv : findResponse) {
                String contextId = gv.getString("contextId");
                Map<String, Object> gnContext = dctx.getDispatcher().runSync("gnFindContextById", UtilMisc.toMap("userLogin", userLogin, "partyId", contextId));
                UtilMisc.addToListInMap(gnContext, result, "contexts");
            }
            @SuppressWarnings("unchecked")
            Collection<Map<String, Object>> partyRelationships = (Collection<Map<String, Object>>) node.get("partyRelationships");
            for (Map<String, Object> rel : partyRelationships) {
                String partyNodeIdTo = (String) rel.get("partyIdTo");
                Map<String, Object> partyNodeTo = findPartyNodeById(partyNodeIdTo, false);
                rel.put("target", partyNodeTo);

                String partyRelationshipTypeId = (String) rel.get("partyRelationshipTypeId");
                if (RelationshipTypeOfbiz.GN_RECEIVES_FROM.is(partyRelationshipTypeId)) {
                    Map<String, Object> relKey = PartyRelationshipIdUtil.getKeyMap(rel);
                    List<Map<String, Object>> filters = findFilters(relKey, AgreementTypesOfbiz.GN_AUTH_FILTER, loadInfoHolderConstraint);
                    rel.put("filters", filters);
                    List<Map<String, Object>> slicingFilters = findFilters(relKey, AgreementTypesOfbiz.GN_AUTH_SLI_FILTER, loadInfoHolderConstraint);
                    rel.put("slicingFilters", slicingFilters);
                }
            }

            result.put("contactLists", findContactLists(partyId));
            result.put("partyNode", node);
        } else result.put("partyNode", null);

        return result;
    }


    /**
     * @param partyRelationshipTypeId
     * @param userLoginPartyId
     * @param contextId
     * @return
     * @throws org.ofbiz.entity.GenericEntityException
     * @throws org.ofbiz.service.GenericServiceException
     */
    public List<Map<String, Object>> gnFindFreeCompaniesAndBases(String partyRelationshipTypeId, String userLoginPartyId, String contextId) throws GeneralException {
        GnSecurity gnSecurity = new GnSecurity(dctx.getDelegator());
        Set<String> partyIds = FastSet.newInstance();

        if (gnSecurity.hasPermission(PermissionsOfbiz.GN_ADMIN.name(), userLogin)) {
            partyIds.addAll(findFreeCompaniesGnAdmin(partyRelationshipTypeId));
        } else
            partyIds.addAll(findFreeCompaniesOthers(contextId, userLoginPartyId, partyRelationshipTypeId));

        //load nodes
        List<Map<String, Object>> value = FastList.newInstance();
        for (String nodeId : partyIds) {
            Map<String, Object> partyNode = findPartyNodeById(nodeId, false);
           /* if (UtilValidate.areEqual(partyNode.get("nodeType"), PartyNodeTypeOfbiz.COMPANY_BASE)) {.name()
                EntityCondition cond = EntityConditionUtil.makeRelationshipCondition((String) partyNode.get("partyId"), "GN_COMPANY_BASE", null, "GN_COMPANY", "GN_BELONGS_TO", true);
                List<GenericValue> rels = delegator.findList("GnPartyRelationship", cond, null, null, null, false);
                if (rels.size() > 1) throw new GeneralException("CompanyBase has more than one owner company.");
                if (rels.size() == 0) {
                    Debug.logError("companyBase[" + partyNode.get("partyId") + "] hasn't got parent node", module);
                } else {
                    Map<String, Object> rel = rels.get(0);
                    rel = new HashMap<String, Object>(rel);
                    rel.put("target", findPartyNodeById((String) rel.get("partyIdTo"), false));
                    UtilMisc.addToListInMap(rel, partyNode, "partyRelationships");
                }
            }*/
            value.add(partyNode);
        }
        return value;
    }

    private Collection<String> findCompanyBase(String companyId) throws GenericEntityException {
        List<GenericValue> aaa = delegator.findList("GnPartyRelationship", EntityConditionUtil.makeRelationshipCondition(companyId, "GN_COMPANY", null, "GN_COMPANY_BASE", RelationshipTypeOfbiz.GN_OWNS.name(), true),
                UtilMisc.toSet("partyIdTo"), null, null, false);
        Set<String> companyBaseIds = FastSet.newInstance();
        for (GenericValue companyBaseId : aaa) {
            companyBaseIds.add(companyBaseId.getString("partyIdTo"));
        }
        return companyBaseIds;
    }

    /**
     * Case 1,2,4,5
     *
     * @return
     * @throws org.ofbiz.entity.GenericEntityException
     * @throws org.ofbiz.service.GenericServiceException
     */
    private Set<String> findFreeCompaniesOthers(String contextId, String userLoginPartyId, String partyRelationshipTypeId) throws GeneralException {
        // memeadmin -     GN_C_AMTREE_MEMETOSC -- (GN_AMTREE)
        GnSecurity gnSecurity = new GnSecurity(delegator);
        Set<String> partyIds = FastSet.newInstance();

        Map<String, Object> gnContext = dispatcher.runSync("gnFindContextById", UtilMisc.toMap("userLogin", userLogin, "partyId", contextId));
        @SuppressWarnings("unchecked")
        Map<String, Object> companyUserBelongTo = (Map<String, Object>) dispatcher.runSync("gnFindCompanyWhereUserIsEmployed", UtilMisc.toMap("userLogin", userLogin, "userLoginPartyId", userLoginPartyId)).get("partyNode");
        //find companyBase
        String companyIdUserBelongTo = (String) companyUserBelongTo.get("partyId");
        //1
        partyIds.add(companyIdUserBelongTo);//company where user is employed
        //2
        partyIds.addAll(findCompanyBase(companyIdUserBelongTo));//companyBase belong to company where user is employed


        @SuppressWarnings("unchecked")
        Map<String, Object> gnContextNode = (Map<String, Object>) gnContext.get("partyNode");
        if (UtilValidate.areEqual(gnContextNode.get("nodeType"), PartyNodeTypeOfbiz.COMPANY.name())) {
            //4
            if (gnSecurity.hasPermission(PermissionsOfbiz.GN_COMPANY_TREE_ADMIN.name(), userLogin)) {
                String contextNodeId = (String) gnContextNode.get("partyId");
                final List<String> nodes = FastList.newInstance();
                TreeUtil.NodeManager nodeManeger = new TreeUtil.NodeManager() {
                    @Override
                    public boolean visit(String currentNodeId, String validationRequired) {
                        nodes.add(currentNodeId);
                        return false;
                    }
                };
                TreeUtil.visitTree(dctx, nodeManeger, contextNodeId, null, null, RelationshipTypeOfbiz.GN_OWNS.name());
                partyIds.addAll(nodes);
            } else
                //5
                if (gnSecurity.hasPermission(PermissionsOfbiz.GN_COMPANY_ADMIN.name(), userLogin)) {
                    if (!UtilValidate.areEqual(companyUserBelongTo.get("partyId"), gnContextNode.get("partyId"))) {
                        partyIds.add((String) gnContextNode.get("partyId"));//id of company referred from  context
                        partyIds.addAll(findCompanyBase((String) gnContextNode.get("partyId")));//companyBase belong to company referred from  context
                    }
                }
        }
        ///
        Set<String> freePartyIds = FastSet.newInstance();

        //PartyRelationship condition
        EntityCondition relCond = EntityConditionUtil.makeRelationshipCondition(null, null, null, null, partyRelationshipTypeId, true);
        //create a subSelect
        EntityConditionValue subSelCond = new EntityConditionSubSelect("PartyRelationship", "partyIdTo", relCond, true, delegator);
        EntityCondition companyCond1 = EntityCondition.makeCondition("partyId", EntityOperator.NOT_EQUAL, subSelCond);
        EntityCondition companyCond2 = EntityCondition.makeCondition("partyId", EntityOperator.IN, partyIds);

        EntityCondition companyCond = EntityCondition.makeCondition(companyCond1, companyCond2,
                EntityCondition.makeCondition("nodeType", PartyNodeTypeOfbiz.COMPANY.name()));

        List<GenericValue> companyIds = delegator.findList("GnPropagationNode", companyCond, UtilMisc.toSet("partyId"), null, null, false);
        for (GenericValue companyId : companyIds) {
            freePartyIds.add(companyId.getString("partyId"));
        }

        companyCond = EntityCondition.makeCondition(companyCond1, companyCond2,
                EntityCondition.makeCondition("nodeType", PartyNodeTypeOfbiz.COMPANY_BASE.name()));
        List<GenericValue> companyBaseIds = delegator.findList("GnPropagationNode", companyCond, UtilMisc.toSet("partyId"), null, null, false);
        for (GenericValue companyId : companyBaseIds) {
            freePartyIds.add(companyId.getString("partyId"));
        }
        return freePartyIds;
    }

    /**
     * Case 1,2,3
     *
     * @return
     * @throws org.ofbiz.entity.GenericEntityException
     * @throws org.ofbiz.service.GenericServiceException
     */
    private Set<String> findFreeCompaniesGnAdmin(String partyRelationshipTypeId) throws GenericEntityException, GenericServiceException {
        Set<String> partyIds = FastSet.newInstance();

        //PartyRelationship condition
        EntityCondition relCond = EntityConditionUtil.makeRelationshipCondition(null, null, null, null, partyRelationshipTypeId, true);
        //create a subSelect
        EntityConditionValue subSelCond1 = new EntityConditionSubSelect("PartyRelationship", "partyIdTo", relCond, true, delegator);
        EntityCondition companyCond1 = EntityCondition.makeCondition("partyId", EntityOperator.NOT_EQUAL, subSelCond1);

//        EntityCondition roleCond = EntityCondition.makeCondition(UtilMisc.toMap("roleTypeId","GN_PROPAGATION_NODE"));
//        EntityConditionValue subSelCond2 = new EntityConditionSubSelect("PartyRole", "partyId", roleCond, true, delegator);
//        EntityCondition companyCond2 = EntityCondition.makeCondition("partyId", EntityOperator.NOT_EQUAL, subSelCond2);

        EntityCondition companyCond = EntityCondition.makeCondition(companyCond1, EntityCondition.makeCondition("nodeType", PartyNodeTypeOfbiz.COMPANY.name()));

        List<GenericValue> companyIds = delegator.findList("GnPropagationNode", companyCond, UtilMisc.toSet("partyId"), null, null, false);
        for (GenericValue companyId : companyIds) {
            partyIds.add(companyId.getString("partyId"));
        }
        companyCond = EntityCondition.makeCondition(companyCond1, EntityCondition.makeCondition("nodeType", PartyNodeTypeOfbiz.COMPANY_BASE.name()));
        List<GenericValue> companyBaseIds = delegator.findList("GnPropagationNode", companyCond, UtilMisc.toSet("partyId"), null, null, false);
        for (GenericValue companyId : companyBaseIds) {
            partyIds.add(companyId.getString("partyId"));
        }
        return partyIds;
    }

    /**
     * @param companyPartyId
     * @return
     * @throws GeneralException
     */
    public List<Map<String, ? extends Object>> userProfileFindCompanyBases(String companyPartyId) throws GeneralException {
        GnSecurity security = new GnSecurity(delegator);
        //boolean hasPermission = false;
        //todo: pending in the future call the service
        boolean hasPermission = new UserHelper(dctx, context).isUserIsEmployedInCompany(userLogin.getString("partyId"), companyPartyId);

        if (!hasPermission && security.hasPermission(PermissionsOfbiz.GN_ADMIN.name(), userLogin)) {
            hasPermission = true;
        }

        if (!hasPermission && security.hasPermission(PermissionsOfbiz.GN_COMPANY_TREE_ADMIN.name(), userLogin)) {
            Map<String, Object> gnContext = dispatcher.runSync("gnFindContextById", UtilMisc.toMap("userLogin", userLogin, "partyId", userLogin.get("activeContextId")));
            @SuppressWarnings("unchecked")
            Map<String, Object> contextNode = (Map<String, Object>) gnContext.get("partyNode");
            if (PartyNodeTypeOfbiz.isACompany(contextNode.get("nodeType"))) {
                hasPermission = TreeUtil.isPartyNodeBelongToTree(dctx, (String) contextNode.get("partyId"), null, null, RelationshipTypeOfbiz.GN_OWNS.name(), companyPartyId);
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

        if (!hasPermission) throw new GnServiceException(OfbizErrors.ACCESS_DENIED, "User hasn't got permission.");

        List<Map<String, ? extends Object>> companyBaseList = findCompanyBasesByCompanyId(companyPartyId);
        return companyBaseList;
        //   Map<String, Object> result = FastMap.newInstance();
        //   result.put("partyNodes", companyBaseList);
        //result.put("companyBaseListSize", companyBaseList.size());
        //   return result;

    }

    /**
     * @param relationshipKey
     * @return filters
     */
    @SuppressWarnings("unchecked")
    private List<Map<String, Object>> findFilters(Map<String, Object> relationshipKey, AgreementTypesOfbiz agreementTypeId, boolean loadInfoHolderConstraint) throws GenericServiceException {
        Map<String, Object> request = UtilMisc.toMap("userLogin", (Object) userLogin);
        request.putAll(relationshipKey);
        request.put("loadInfoHolderConstraint", loadInfoHolderConstraint ? "Y" : "N");
        request.put("agreementTypeId", agreementTypeId.name());
        Map<String, Object> result = dispatcher.runSync("gnFindAuthorizationFilterByRelationship", request);
        return (List<Map<String, Object>>) result.get("filters");
    }

    /**
     * @param ownerPartyId
     * @return contactLists
     */
    @SuppressWarnings("unchecked")
    private List<Map<String, Object>> findContactLists(String ownerPartyId) throws GenericServiceException {
        Map<String, Object> request = UtilMisc.toMap("userLogin", (Object) userLogin, "ownerPartyIds", UtilMisc.toList(ownerPartyId));
        Map<String, Object> result = dispatcher.runSync("gnFindContactListWithoutPermission", request);
        return (List<Map<String, Object>>) result.get("contactLists");
    }

    /**
     * @param taxIdentificationNumber
     * @return
     * @throws org.ofbiz.service.GenericServiceException
     */
    public List<Map<String, Object>> findPublicCompanyBases(String taxIdentificationNumber) throws GenericServiceException, GenericEntityException {
        DynamicViewEntity view = new DynamicViewEntity();
        view.addMemberEntity("C", "GnPartyGroupPartyNode");
        view.addMemberEntity("R", "GnPartyRelationship");
        view.addViewLink("C", "R", false, UtilMisc.toList(new ModelKeyMap("partyId", "partyIdTo")));

        view.addAlias("C", "nodeType");
        view.addAlias("C", "taxIdentificationNumber");
        view.addAlias("R", "partyRelationshipTypeId");
        view.addAlias("R", "roleTypeIdFrom");
        view.addAlias("R", "roleTypeIdTo");
        view.addAlias("R", "partyIdFrom");

        EntityCondition cond = EntityCondition.makeCondition("partyRelationshipTypeId", "GN_BELONGS_TO");
        cond = GnFindUtil.concat(cond, EntityCondition.makeCondition("roleTypeIdFrom", "GN_COMPANY_BASE"));
        cond = GnFindUtil.concat(cond, EntityCondition.makeCondition("roleTypeIdTo", "GN_COMPANY"));
        cond = GnFindUtil.concat(cond, EntityCondition.makeCondition("nodeType", "COMPANY"));
        cond = GnFindUtil.concat(cond, EntityCondition.makeCondition("taxIdentificationNumber", taxIdentificationNumber));

        EntityListIterator iterator = delegator.findListIteratorByCondition(view, cond, null, Arrays.asList("partyIdFrom"), null, GnFindUtil.getFindOptDistinct());
        List<GenericValue> gvs = iterator.getCompleteList();
        iterator.close();
        FastList<Map<String, Object>> parties = FastList.newInstance();
        Map<String, Object> companyBase;
        for (GenericValue gn : gvs) {
            companyBase = findPartyNodeById((String) gn.get("partyIdFrom"), false);
            parties.add(companyBase);
        }
        return parties;
    }


    public Map<String, Object> gnFindCompanyNodeByTaxIdentificationNumber(String taxIdentificationNumber) throws GenericEntityException {
        List<GenericValue> genericValues = delegator.findByAnd("GnPartyGroupPartyNode", UtilMisc.toMap("taxIdentificationNumber", taxIdentificationNumber, "nodeType", PartyNodeTypeOfbiz.COMPANY.name()));
        if (genericValues.isEmpty())
            return null;
        else
            return genericValues.get(0);
    }

    public List<Map<String, Object>> gnFindCompanyBasesByAddress(Map<String, Object> address, String nodeKeyToExclude, String parentNodeKey) throws GenericEntityException, GenericServiceException {
        List<Map<String, Object>> companyBases = FastList.newInstance();

        String countryGeoName = (String) address.get("countryGeoName");
        String municipalityGeoName = (String) address.get("municipalityGeoName");
        List<EntityCondition> conditionList = FastList.newInstance();

        DynamicViewEntity dv = new DynamicViewEntity();
        dv.addMemberEntity("PPA", "GnPartyAndPostalAddress");
        dv.addAliasAll("PPA", null);

        if (UtilValidate.isNotEmpty(parentNodeKey)) {
            //roleTypeIdTo
            String parentNodePartyId = new AuthorizationHelper(dctx, context).getPartyId(null, parentNodeKey, String.format("Cannot convert nodeKey \"%s\" in a partyId", parentNodeKey));
            dv.addMemberEntity("REL", "PartyRelationship");
            dv.addAlias("REL", "partyIdTo");
            dv.addAlias("REL", "partyRelationshipTypeId");
            dv.addViewLink("PPA", "REL", false, UtilMisc.toList(new ModelKeyMap("partyId", "partyIdFrom")));
            conditionList.add(EntityCondition.makeCondition("partyRelationshipTypeId", RelationshipTypeOfbiz.GN_BELONGS_TO.name()));
            conditionList.add(EntityCondition.makeCondition("partyIdTo", parentNodePartyId));
        }

        if (UtilValidate.isNotEmpty(nodeKeyToExclude)) {
            String partyIdToExclude = new AuthorizationHelper(dctx, context).getPartyId(null, nodeKeyToExclude, String.format("Cannot convert nodeKey \"%s\" in a partyId", nodeKeyToExclude));
            conditionList.add(EntityCondition.makeCondition("partyId", EntityOperator.NOT_EQUAL, partyIdToExclude));
        }
        if (countryGeoName.equals("Italy")) {
            //Filter by municipality (use join)
            conditionList.add(EntityCondition.makeCondition(EntityFunction.UPPER_FIELD("municipalityGeoName"), EntityComparisonOperator.EQUALS, EntityFunction.UPPER(municipalityGeoName)));
        } else {
            conditionList.add(EntityCondition.makeCondition(EntityFunction.UPPER_FIELD("countryGeoName"), EntityComparisonOperator.EQUALS, EntityFunction.UPPER(countryGeoName)));
            conditionList.add(EntityCondition.makeCondition(EntityFunction.UPPER_FIELD("city"), EntityComparisonOperator.EQUALS, EntityFunction.UPPER(municipalityGeoName)));
            conditionList.add(EntityCondition.makeCondition(EntityFunction.UPPER_FIELD("postalCode"), EntityComparisonOperator.EQUALS, EntityFunction.UPPER(address.get("postalCode"))));
        }
        Set<String> fieldsToSelect = UtilMisc.toSet("partyId", "postalCodeGeoId", "streetNumber");
        List<GenericValue> contacts = delegator.findListIteratorByCondition(dv, EntityCondition.makeCondition(conditionList), null,
                fieldsToSelect, null, null).getCompleteList();

        for (GenericValue contact : contacts) {
            Map<String, Object> nodeEx = gnFindExtendedNodeById(contact.getString("partyId"), PartyNodeTypeOfbiz.COMPANY_BASE, true);
            Map<String, Object> companyBase = UtilMisc.getMapFromMap(nodeEx, "partyNode");

            AddressMatcher addressMatcher = new AddressMatcher((String) address.get("address1"),
                    (String) address.get("streetNumber"), (String) address.get("village"));

            if (PartyNodeTypeOfbiz.COMPANY_BASE.name().equals(companyBase.get("nodeType"))) {
                Map<String, Object> companyBaseAddress = UtilMisc.getMapFromMap(companyBase, "address");
                try {
                    if (addressMatcher.match((String) companyBaseAddress.get("address1"), (String) companyBaseAddress.get("streetNumber"),
                            (String) companyBaseAddress.get("village"))) {
                        companyBases.add(companyBase);
                    }
                } catch (TokenizerException e) {
                    throw new GnServiceException(OfbizErrors.GENERIC, "Unable to evaluate address", e);
                } catch (ComparerException e) {
                    throw new GnServiceException(OfbizErrors.GENERIC, "Unable to evaluate address", e);
                }
            }
        }
        return companyBases;
    }
}
