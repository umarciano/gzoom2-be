package it.memelabs.gn.services.user;

import it.memelabs.gn.services.AbstractServiceHelper;
import it.memelabs.gn.services.OfbizErrors;
import it.memelabs.gn.services.auditing.EntityTypeMap;
import it.memelabs.gn.services.auditing.EntityTypeOfbiz;
import it.memelabs.gn.services.login.LoginSourceOfbiz;
import it.memelabs.gn.services.node.PartyNodeTypeOfbiz;
import it.memelabs.gn.services.node.PartyRoleOfbiz;
import it.memelabs.gn.services.node.RelationshipTypeOfbiz;
import it.memelabs.gn.services.security.GnSecurity;
import it.memelabs.gn.services.security.PermissionsOfbiz;
import it.memelabs.gn.util.EntityConditionUtil;
import it.memelabs.gn.util.GnServiceException;
import it.memelabs.gn.util.GnServiceUtil;
import it.memelabs.gn.util.TreeUtil;
import it.memelabs.gn.util.find.GnFindUtil;
import it.memelabs.gn.webapp.event.AuditEvent;
import it.memelabs.gn.webapp.event.AuditEventSessionHelper;
import javolution.util.FastList;
import javolution.util.FastMap;
import org.ofbiz.base.util.*;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.entity.condition.EntityComparisonOperator;
import org.ofbiz.entity.condition.EntityCondition;
import org.ofbiz.entity.model.DynamicViewEntity;
import org.ofbiz.entity.model.ModelKeyMap;
import org.ofbiz.entity.util.EntityListIterator;
import org.ofbiz.entity.util.EntityUtil;
import org.ofbiz.service.DispatchContext;
import org.ofbiz.service.GenericServiceException;
import org.ofbiz.service.ServiceUtil;

import java.sql.Timestamp;
import java.util.*;

/**
 * 28/02/13
 *
 * @author Andrea Fossi
 */
public class GnContextHelper extends AbstractServiceHelper {
    private static final String module = GnContextHelper.class.getName();

    public GnContextHelper(DispatchContext dctx, Map<String, ? extends Object> context) {
        super(dctx, context);
    }

    /**
     * @param partyNodeIdToCheck
     * @return
     * @throws org.ofbiz.service.GenericServiceException
     */
    protected boolean createContextCheckPermission(String partyNodeIdToCheck) throws GenericServiceException {
        Map<String, Object> gnContext = dispatcher.runSync("gnFindContextById", UtilMisc.toMap("userLogin", userLogin, "partyId", (String) userLogin.get("activeContextId")));
        @SuppressWarnings("unchecked")
        Map<String, Object> partyNode = (Map<String, Object>) gnContext.get("partyNode");
        String activeContextPartyNodeId = (String) partyNode.get("partyId");

        GnSecurity gnSecurity = new GnSecurity(delegator);
        if (gnSecurity.hasPermission(PermissionsOfbiz.GN_ADMIN.name(), userLogin)) {
            return true;
        }
        if (gnSecurity.hasPermission(PermissionsOfbiz.GN_SYSTEM_ADMIN.name(), userLogin)) {
            return true;
        }

        if (gnSecurity.hasPermission(PermissionsOfbiz.GN_TREE_ADMIN.name(), userLogin)) {
            if (UtilValidate.areEqual(activeContextPartyNodeId, partyNodeIdToCheck)) {
                return true;
            } else {
                boolean hasPermission = false;
                Iterator<String> parentPartyId = TreeUtil.newBackwardNavigator(dctx, context, partyNodeIdToCheck, "GN_PROPAGATION_NODE", "GN_PROPAGATION_NODE", RelationshipTypeOfbiz.GN_PROPAGATES_TO.name());
                // Debug.log("* start Node[" + partyNodeIdToCheck + "] *", module);
                while (parentPartyId.hasNext() && !hasPermission) {
                    String parentPartyNodeId = parentPartyId.next();
                    //    Debug.log("parent Node[" + parentPartyNodeId + "]", module);
                    if (UtilValidate.areEqual(parentPartyNodeId, activeContextPartyNodeId)) hasPermission = true;
                }
                //Debug.log("* stop *", module);
                return hasPermission;
            }
        }

        if (gnSecurity.hasPermission(PermissionsOfbiz.GN_NODE_ADMIN.name(), userLogin)) {
            if (UtilValidate.areEqual(activeContextPartyNodeId, partyNodeIdToCheck)) {
                return true;
            } else return false;
        }

        return false;
    }

    /**
     * @param partyNodeId
     * @param description
     * @param companyBaseIds
     * @param securityGroupIds
     * @param userLoginIds
     * @return
     * @throws GeneralException
     */
    public String gnCreateContext(String partyNodeId, String description, List<String> companyBaseIds,
                                  List<String> securityGroupIds, List<String> userLoginIds) throws GeneralException {


        //validation
        if (UtilValidate.isNotEmpty(partyNodeId)) {
            if (!createContextCheckPermission(partyNodeId))
                throw new GnServiceException(OfbizErrors.ACCESS_DENIED, "User doesn't have permission.");
        } else {
            throw new GnServiceException(OfbizErrors.INVALID_PARAMETERS, "partyNodeId is empty");
        }

        if (UtilValidate.isEmpty(userLoginIds)) {
            throw new GnServiceException(OfbizErrors.INVALID_PARAMETERS, "userLoginIds collection is empty or null");
        }

        checkAllowedSecurityGroupPermission(partyNodeId, securityGroupIds, userLoginIds);

        if (securityGroupIds.contains(PermissionsOfbiz.GN_AUTH_PUBLISH.name()) && UtilValidate.isEmpty(companyBaseIds))
            throw new GnServiceException(OfbizErrors.ACCESS_DENIED, "user has GN_AUTH_PUBLISH role and companyBaseIds collection is empty or null");

        //create PartyGroup
        Map<String, Object> pgResult = dispatcher.runSync("createPartyGroup", UtilMisc.toMap("userLogin", userLogin,
                "description", description, "groupName", description));
        if (pgResult != null && ServiceUtil.isError(pgResult)) {
            throw new GnServiceException(OfbizErrors.SERVICE_EXCEPTION, ServiceUtil.getErrorMessage(pgResult));
        }
        String contextPartyId = (String) pgResult.get("partyId");

        //add PartyRole
        Map<String, Object> prResult = dispatcher.runSync("createPartyRole", UtilMisc.toMap("userLogin", userLogin,
                "roleTypeId", "GN_CONTEXT", "partyId", contextPartyId));
        if (prResult != null && ServiceUtil.isError(prResult)) {
            throw new GnServiceException(OfbizErrors.SERVICE_EXCEPTION, ServiceUtil.getErrorMessage(prResult));
        }

        Timestamp fromDate = UtilDateTime.nowTimestamp();

        //add SecurityGroups
        if (UtilValidate.isNotEmpty(securityGroupIds)) {
            for (String groupId : securityGroupIds) {
                dispatcher.runSync("gnAddPartyToSecurityGroup", UtilMisc.toMap("userLogin", userLogin,
                        "groupId", groupId, "partyId", contextPartyId, "fromDate", fromDate));
            }
        }

        //add partyNode relationship
        if (UtilValidate.isNotEmpty(partyNodeId)) {
            createPartyRelationship(contextPartyId, PartyRoleOfbiz.GN_CONTEXT, partyNodeId, PartyRoleOfbiz.GN_NODE_CONTEXT, fromDate, RelationshipTypeOfbiz.GN_CONTEXT_NODE);
        }

        //add companyBase relationship
        if (UtilValidate.isNotEmpty(companyBaseIds)) {
            @SuppressWarnings("unchecked")
            List<String> companyBases = (List<String>) companyBaseIds;
            for (String companyBasePartyId : companyBases) {
                createPartyRelationship(contextPartyId, PartyRoleOfbiz.GN_CONTEXT, companyBasePartyId, PartyRoleOfbiz.GN_COMPANY_BASE, fromDate, RelationshipTypeOfbiz.GN_CONTEXT_CBASE);
            }
        }

        //add UserLogin-Party-Person relationship
        if (UtilValidate.isNotEmpty(userLoginIds)) {
            for (String userLoginId : userLoginIds) {
                GenericValue _userLogin = delegator.findByPrimaryKey("UserLogin", UtilMisc.toMap("userLoginId", userLoginId));
                if (UtilValidate.isEmpty(_userLogin))
                    throw new GnServiceException(OfbizErrors.ENTITY_EXCEPTION, "UserLogin with id[" + userLoginId + "] not exist.");
                String ulPartyId = (String) _userLogin.get("partyId");
                createPartyRelationship(contextPartyId, PartyRoleOfbiz.GN_CONTEXT, ulPartyId, PartyRoleOfbiz.GN_USER, fromDate, RelationshipTypeOfbiz.GN_CONTEXT_USER);
            }
        }

        //audit
        AuditEvent event = new AuditEvent(EntityTypeOfbiz.CONTEXT);
        Map<String, Object> savedContext = gnFindContextById(contextPartyId, false, false, false, false);
        Map<String, Object> savedContextPartyNode = UtilGenerics.checkMap(savedContext.get("partyNode"));
        savedContext.remove("partyNode");
        event.setNewValue(savedContext);
        if (UtilValidate.isNotEmpty(savedContextPartyNode))
            event.addFallback((String) savedContextPartyNode.get("nodeKey"), getEntityType((String) savedContextPartyNode.get("partyId")));
        AuditEventSessionHelper.putAuditEvent(event);
        return contextPartyId;
    }

    /**
     * @param contextPartyId
     * @param description
     * @param companyBaseIds
     * @param securityGroupIds
     * @param userLoginIds
     * @return
     * @throws GeneralException
     */
    public String gnUpdateContext(String contextPartyId, String description, List<String> companyBaseIds,
                                  List<String> securityGroupIds, List<String> userLoginIds) throws GeneralException {
        //validation
        String contextNodePartyId = getContextNodePartyId(contextPartyId);
        if (!createContextCheckPermission(contextNodePartyId))
            throw new GnServiceException(OfbizErrors.ACCESS_DENIED, "User doesn't have permission.");


        Timestamp fromDate = UtilDateTime.nowTimestamp();

        if (UtilValidate.isEmpty(userLogin)) {
            throw new GnServiceException(OfbizErrors.INVALID_PARAMETERS, "userLoginIds collection is empty or null");
        }

        checkAllowedSecurityGroupPermission(contextNodePartyId, securityGroupIds, userLoginIds);

        Map<String, Object> oldSavedContext = gnFindContextById(contextPartyId, false, false, true, false);

        GenericValue party = delegator.findOne("Party", false, "partyId", contextPartyId);
        party.set("description", description);
        delegator.store(party);

        GenericValue partyGroup = delegator.findOne("PartyGroup", false, "partyId", contextPartyId);
        partyGroup.set("groupName", description);
        delegator.store(partyGroup);

        //delete security group
        int result = 0;
        result = delegator.removeByAnd("GnPartySecurityGroup", UtilMisc.toMap("partyId", contextPartyId));
        Debug.log("Removed " + result + " security group associations", module);

        //add SecurityGroups
        if (UtilValidate.isNotEmpty(securityGroupIds)) {
            for (String groupId : securityGroupIds) {
                dispatcher.runSync("gnAddPartyToSecurityGroup", UtilMisc.toMap("userLogin", userLogin,
                        "groupId", groupId, "partyId", contextPartyId, "fromDate", fromDate));
            }
        }

        //company bases
        result = delegator.removeByCondition("PartyRelationship",
                EntityConditionUtil.makeRelationshipCondition(contextPartyId, "GN_CONTEXT", null, "GN_COMPANY_BASE", RelationshipTypeOfbiz.GN_CONTEXT_CBASE.name(), false));
        Debug.log("Removed " + result + " security relations with companyBases", module);
        //add companyBase relationship
        if (UtilValidate.isNotEmpty(companyBaseIds)) {
            @SuppressWarnings("unchecked")
            List<String> companyBases = (List<String>) companyBaseIds;
            for (String companyBasePartyId : companyBases) {
                createPartyRelationship(contextPartyId, PartyRoleOfbiz.GN_CONTEXT, companyBasePartyId, PartyRoleOfbiz.GN_COMPANY_BASE, fromDate, RelationshipTypeOfbiz.GN_CONTEXT_CBASE);
            }
        }

        //users
        result = delegator.removeByCondition("PartyRelationship",
                EntityConditionUtil.makeRelationshipCondition(contextPartyId, "GN_CONTEXT", null, "GN_USER", RelationshipTypeOfbiz.GN_CONTEXT_USER.name(), false));
        Debug.log("Removed " + result + " security relations with users", module);
        //add UserLogin-Party-Person relationship
        if (UtilValidate.isNotEmpty(userLoginIds)) {
            for (String userLoginId : userLoginIds) {
                GenericValue _userLogin = delegator.findByPrimaryKey("UserLogin", UtilMisc.toMap("userLoginId", userLoginId));
                if (UtilValidate.isEmpty(_userLogin))
                    throw new GnServiceException(OfbizErrors.ENTITY_EXCEPTION, "UserLogin with id[" + userLoginId + "] not exist.");
                String ulPartyId = (String) _userLogin.get("partyId");
                createPartyRelationship(contextPartyId, PartyRoleOfbiz.GN_CONTEXT, ulPartyId, PartyRoleOfbiz.GN_USER, fromDate, RelationshipTypeOfbiz.GN_CONTEXT_USER);
            }
        }

        //audit
        AuditEvent event = new AuditEvent(EntityTypeOfbiz.CONTEXT);
        Map<String, Object> savedContext = gnFindContextById(contextPartyId, false, false, false, false);
        Map<String, Object> savedContextPartyNode = UtilGenerics.checkMap(savedContext.get("partyNode"));
        savedContext.remove("partyNode");
        event.setNewValue(savedContext);
        event.setOldValue(oldSavedContext);
        if (UtilValidate.isNotEmpty(savedContextPartyNode))
            event.addFallback((String) savedContextPartyNode.get("nodeKey"), getEntityType((String) savedContextPartyNode.get("partyId")));
        AuditEventSessionHelper.putAuditEvent(event);

        return contextPartyId;
    }

    public void gnDeleteContext(String contextPartyId) throws GenericEntityException, GenericServiceException {
        //validation
        String contextNodePartyId = getContextNodePartyId(contextPartyId);
        if (!createContextCheckPermission(contextNodePartyId))
            throw new GnServiceException(OfbizErrors.ACCESS_DENIED, "User doesn't have permission.");
        int result = 0;

        Map<String, Object> oldSavedContext = gnFindContextById(contextPartyId, false, false, false, false);

        result = delegator.removeByAnd("GnPartySecurityGroup", UtilMisc.toMap("partyId", contextPartyId));
        Debug.log("Removed " + result + " security group associations", module);
        //company bases
        result = delegator.removeByCondition("PartyRelationship",
                EntityConditionUtil.makeRelationshipCondition(contextPartyId, "GN_CONTEXT", null, "GN_COMPANY_BASE", RelationshipTypeOfbiz.GN_CONTEXT_CBASE.name(), false));
        Debug.log("Removed " + result + " security relations with companyBases", module);
        //users
        result = delegator.removeByCondition("PartyRelationship",
                EntityConditionUtil.makeRelationshipCondition(contextPartyId, "GN_CONTEXT", null, "GN_USER", RelationshipTypeOfbiz.GN_CONTEXT_USER.name(), false));
        Debug.log("Removed " + result + " security relations with users", module);
        //partyNode
        result = delegator.removeByCondition("PartyRelationship",
                EntityConditionUtil.makeRelationshipCondition(contextPartyId, "GN_CONTEXT", null, "GN_NODE_CONTEXT", RelationshipTypeOfbiz.GN_CONTEXT_NODE.name(), false));
        Debug.log("Removed " + result + " security relation with partyNode", module);
        dispatcher.runSync("deletePartyRole", UtilMisc.toMap("userLogin", userLogin, "roleTypeId", "GN_CONTEXT", "partyId", contextPartyId));
        dispatcher.runSync("deletePartyRole", UtilMisc.toMap("userLogin", userLogin, "roleTypeId", "_NA_", "partyId", contextPartyId));
        Debug.log("Deleted partyRoles", module);

        result = delegator.removeByAnd("PartyStatus", "partyId", contextPartyId);
        Debug.log("Removed " + result + "PartyStatus", module);
        delegator.removeValue(delegator.findOne("PartyGroup", false, "partyId", contextPartyId));
        Debug.log("Deleted PartyGroup[" + contextPartyId + "]", module);
        delegator.removeValue(delegator.findOne("Party", false, "partyId", contextPartyId));
        Debug.log("Deleted Party[" + contextPartyId + "]", module);

        //audit
        AuditEvent event = new AuditEvent(EntityTypeOfbiz.CONTEXT);
        event.setOldValue(oldSavedContext);
        Map<String, Object> savedContextPartyNode = UtilGenerics.checkMap(oldSavedContext.get("partyNode"));
        if (UtilValidate.isNotEmpty(savedContextPartyNode))
            event.addFallback((String) savedContextPartyNode.get("nodeKey"), getEntityType((String) savedContextPartyNode.get("partyId")));
        AuditEventSessionHelper.putAuditEvent(event);
    }

    /**
     * Find partyNode id of partyNode associated to context
     *
     * @param contextPartyId
     * @return partyNodeId
     * @throws GenericEntityException
     * @throws GnServiceException
     */
    private String getContextNodePartyId(String contextPartyId) throws GenericEntityException, GnServiceException {
        EntityCondition filterConditions = GnFindUtil.concat(EntityCondition.makeCondition("contextId", contextPartyId), EntityUtil.getFilterByDateExpr());
        List<GenericValue> view = delegator.findList("GnContextPartyNode", filterConditions, null, null, null, false);
        if (view.size() == 0) {
            throw new GnServiceException(OfbizErrors.ENTITY_EXCEPTION, "No PartyNode associated to the context.");
        } else if (view.size() > 1) {
            throw new GnServiceException(OfbizErrors.ENTITY_EXCEPTION, "More than one PartyNodes associated to the context.");
        }

        return view.get(0).getString("nodeId");
    }

    /**
     * @param userLoginId
     * @return
     * @throws org.ofbiz.entity.GenericEntityException
     */
    protected List<Map<String, Object>> findContextByUserLoginId(String userLoginId) throws GenericEntityException, GenericServiceException {
        List<Map<String, Object>> ret = new ArrayList<Map<String, Object>>();
        List<GenericValue> contexts = delegator.findByAnd("GnContextUser", UtilMisc.toMap("userLoginId", userLoginId), UtilMisc.toList("contextPartyId ASC"));
        for (GenericValue gnContext : contexts) {
            Map<String, Object> map = UtilMisc.toMap("description", gnContext.get("contextDescription"), "partyId", gnContext.get("contextPartyId"));
            map.put("companyBases", getGnContextCompanyBase(gnContext.get("contextPartyId")));
            map.put("securityGroups", getUserSecurityGroupsAndPermissions((String) gnContext.get("contextPartyId")));
            map.put("partyNode", getGnPartyGroupPartyNode((String) gnContext.get("contextPartyId")));
            ret.add(map);
        }

        return ret;
    }

    /**
     * Find context (and load userLogin) that with contextNode=partyNodeId and has a SecurityGroup that contains permissionId
     *
     * @param partyNodeId
     * @param permissionId
     * @return
     * @throws GenericEntityException
     * @throws GenericServiceException
     */
    protected List<Map<String, Object>> gnFindContextWithUserLoginByPartyNodeAndPermission(String partyNodeId, String permissionId) throws GenericEntityException, GenericServiceException {
        return gnInternalFindContextByUserLoginPartyNodeAndPermission(null, partyNodeId, permissionId, true, true);
    }

    /**
     * Find context that with member userLoginId, contextNode=partyNodeId and has a SecurityGroup that contains permissionId
     *
     * @param userLoginId
     * @param partyNodeId
     * @param permissionId
     * @param loadContext
     * @return
     * @throws GenericEntityException
     * @throws GenericServiceException
     */
    protected List<Map<String, Object>> gnFindContextByUserLoginPartyNodeAndPermission(String userLoginId, String partyNodeId, String permissionId, boolean loadContext) throws GenericEntityException, GenericServiceException {
        return gnInternalFindContextByUserLoginPartyNodeAndPermission(userLoginId, partyNodeId, permissionId, loadContext, false);
    }

    private List<Map<String, Object>> gnInternalFindContextByUserLoginPartyNodeAndPermission(String userLoginId, String partyNodeId, String permissionId, boolean loadContext, boolean loadUsers) throws GenericEntityException, GenericServiceException {
        //GnPartySecurityGroupPermission
        //GnContextUser
        DynamicViewEntity entity = new DynamicViewEntity();
        entity.addMemberEntity("CTX", "GnContext");
        entity.addMemberEntity("PREL2", "PartyRelationship");
        entity.addMemberEntity("PSG", "GnPartySecurityGroup");
        entity.addMemberEntity("SGP", "SecurityGroupPermission");
        entity.addMemberEntity("SP", "SecurityPermission");


        entity.addViewLink("CTX", "PREL2", false, ModelKeyMap.makeKeyMapList("partyId", "partyIdFrom"));


        entity.addViewLink("CTX", "PSG", false, ModelKeyMap.makeKeyMapList("partyId", "partyId"));

        entity.addViewLink("PSG", "SGP", false, ModelKeyMap.makeKeyMapList("groupId", "groupId"));
        entity.addViewLink("SGP", "SP", false, ModelKeyMap.makeKeyMapList("permissionId", "permissionId"));

        entity.addAlias("CTX", "contextId", "partyId", null, null, null, null);
        entity.addAlias("PREL2", "partyNodeId", "partyIdTo", null, null, null, null);

        //used to filter party
        entity.addAlias("CTX", "roleTypeId", "roleTypeId", null, null, null, null);
        //permissionId
        entity.addAlias("SP", "permissionId", "permissionId", null, null, null, null);
        //GN_CONTEXT_USER
        entity.addAlias("PREL2", "partyRelationshipTypeId2", "partyRelationshipTypeId", null, null, null, null);
        //GN_USER
        entity.addAlias("PREL2", "roleTypeIdTo2", "roleTypeIdTo", null, null, null, null);
        //GN_CONTEXT
        entity.addAlias("PREL2", "roleTypeIdFrom2", "roleTypeIdFrom", null, null, null, null);

        List<EntityCondition> conds = new ArrayList<EntityCondition>();
        conds.add(EntityCondition.makeCondition("partyRelationshipTypeId2", "GN_CONTEXT_NODE"));
        conds.add(EntityCondition.makeCondition("roleTypeIdFrom2", "GN_CONTEXT"));
        conds.add(EntityCondition.makeCondition("roleTypeIdTo2", "GN_NODE_CONTEXT"));

        if (userLoginId != null && !userLoginId.isEmpty()) {
            entity.addMemberEntity("PREL", "PartyRelationship");
            entity.addMemberEntity("UL", "PartyAndUserLoginAndPerson");
            entity.addViewLink("CTX", "PREL", false, ModelKeyMap.makeKeyMapList("partyId", "partyIdFrom"));
            entity.addViewLink("PREL", "UL", false, ModelKeyMap.makeKeyMapList("partyIdTo", "partyId"));

            //GN_CONTEXT_USER rel
            entity.addAlias("PREL", "partyRelationshipTypeId", "partyRelationshipTypeId", null, null, null, null);
            //GN_CONTEXT role
            entity.addAlias("PREL", "roleTypeIdFrom", "roleTypeIdFrom", null, null, null, null);
            //GN_USER role
            entity.addAlias("PREL", "roleTypeIdTo", "roleTypeIdTo", null, null, null, null);
            //userlogin
            entity.addAlias("UL", "userLoginId", "userLoginId", null, null, null, null);

            conds.add(EntityCondition.makeCondition("partyRelationshipTypeId", "GN_CONTEXT_USER"));
            conds.add(EntityCondition.makeCondition("roleTypeIdFrom", "GN_CONTEXT"));
            conds.add(EntityCondition.makeCondition("roleTypeIdTo", "GN_USER"));
            conds.add(EntityCondition.makeCondition("userLoginId", userLoginId));
        }

        conds.add(EntityCondition.makeCondition("partyNodeId", partyNodeId));
        conds.add(EntityCondition.makeCondition("permissionId", permissionId));


        EntityListIterator listIteratorByCondition = delegator.findListIteratorByCondition(entity, EntityCondition.makeCondition(conds), null, null, null, null);
        List<GenericValue> completeList = listIteratorByCondition.getCompleteList();
        listIteratorByCondition.close();

        List<Map<String, Object>> ret = FastList.newInstance();
        for (GenericValue gv : completeList) {
            if (loadContext) {
                ret.add(gnFindContextById(gv.getString("contextId"), true, true, true, !loadUsers));
            } else {
                ret.add(UtilMisc.toMap("partyId", gv.get("contextId")));
            }
        }
        return ret;
    }

    /**
     * Add a company base to context
     *
     * @param gnContextId
     * @param companyBaseId
     * @throws GenericEntityException
     * @throws GenericServiceException
     */
    public void gnAddCompanyBaseToContext(String gnContextId, String companyBaseId) throws GenericEntityException, GenericServiceException {
        Map<String, Object> companyBase = findPartyNodeById(companyBaseId, false);
        if (!PartyNodeTypeOfbiz.isACompanyBase(companyBase.get("nodeType"))) {
            throw new GnServiceException(OfbizErrors.INVALID_PARAMETERS, "Party[" + companyBaseId + "] is not a CompanyBase");
        }
        EntityCondition cond = EntityConditionUtil.makeRelationshipCondition(gnContextId, PartyRoleOfbiz.GN_CONTEXT,
                companyBaseId, PartyRoleOfbiz.GN_NODE_CONTEXT, RelationshipTypeOfbiz.GN_CONTEXT_CBASE, true);
        if (delegator.findList("PartyRelationship", cond, null, null, null, false).size() > 0) {
            throw new GnServiceException(OfbizErrors.BUSINESS_LOGIC_EXCEPTION, "CompanyBase[" + companyBaseId + "] has a relaction with Context[" + gnContextId + "] already.");
        }
        createPartyRelationship(gnContextId, PartyRoleOfbiz.GN_CONTEXT, companyBaseId, PartyRoleOfbiz.GN_COMPANY_BASE, UtilDateTime.nowTimestamp(), RelationshipTypeOfbiz.GN_CONTEXT_CBASE);
        Debug.log("Added CompanyBase[" + companyBaseId + "] to GnContext[" + gnContextId + "]", module);
    }

    /**
     * @param contextId
     * @return
     */
    List<Map<String, Object>> getGnContextCompanyBase(Object contextId) throws GenericEntityException, GenericServiceException {
        List<Map<String, Object>> ret = new ArrayList<Map<String, Object>>();
        List<GenericValue> view = delegator.findByAnd("GnContextCompanyBase", UtilMisc.toMap("contextId", contextId));
        for (GenericValue item : view) {
            String partyId = (String) item.get("nodeId");
            Map<String, Object> gnCompanyBase = findPartyNodeById(partyId, true);

            List<Map<String, Object>> partyRelationships = (List<Map<String, Object>>) gnCompanyBase.get("partyRelationships");
            if (partyRelationships != null && partyRelationships.size() > 0) {
                for (Map<String, Object> rel : partyRelationships) {
                    Map<String, Object> targetNode = findPartyNodeById((String) rel.get("partyIdTo"), false);
                    rel.put("target", targetNode);
                }
            }

            ret.add(gnCompanyBase);
        }
        return ret;
    }


    /**
     * Retrieve information of PartyNode associated to context
     *
     * @param gnContextId
     * @return
     * @throws GenericServiceException
     * @throws GenericEntityException
     */
    protected Map<String, Object> getGnPartyGroupPartyNode(String gnContextId) throws GenericServiceException, GenericEntityException {
        Map<String, Object> partyNode = findPartyNodeById(getContextNodePartyId(gnContextId), true);
        return partyNode;
    }

    protected List<Map<String, Object>> getUserSecurityGroupsAndPermissions(String contextId) throws GenericEntityException {
        Map<String, Map<String, Object>> securityGroup = FastMap.newInstance();
        List<EntityCondition> conds = FastList.newInstance();
        conds.add(EntityCondition.makeCondition("partyId", contextId));
        conds.add(EntityCondition.makeCondition("securityGroupId", EntityComparisonOperator.NOT_EQUAL, "GN_INTERNAL"));

        List<GenericValue> view = delegator.findList("GnPartySecurityGroupPermission", EntityCondition.makeCondition(conds), null, UtilMisc.toList("securityGroupId", "permissionId"), null, false);
        //List<GenericValue> view = delegator.findByAnd("GnPartySecurityGroupPermission", UtilMisc.toMap("partyId", contextId), UtilMisc.toList("securityGroupId", "permissionId"));
        for (GenericValue item : view) {
            String groupId = item.getString("securityGroupId");
            String groupDescription = item.getString("securityGroupDescription");
            if (!securityGroup.containsKey(groupId))
                securityGroup.put(groupId, UtilMisc.toMap("groupId", groupId, "description", (Object) groupDescription));
            Map<String, Object> permission = UtilMisc.toMap("permissionId", item.get("permissionId"), "description", item.get("permissionDescription"));
            UtilMisc.addToListInMap(permission, securityGroup.get(groupId), "permissions");
        }


        return new ArrayList<Map<String, Object>>(securityGroup.values());
    }

    private void createPartyRelationship(String partyIdFrom, PartyRoleOfbiz roleTypeIdFrom, String partyIdTo, PartyRoleOfbiz roleTypeIdTo, Timestamp fromDate, RelationshipTypeOfbiz partyRelationshipTypeId) throws GenericServiceException {
        dispatcher.runSync("createPartyRelationship", UtilMisc.toMap("userLogin", userLogin,
                "partyIdFrom", partyIdFrom, "roleTypeIdFrom", roleTypeIdFrom.name(),
                "partyIdTo", partyIdTo, "roleTypeIdTo", roleTypeIdTo.name(),
                "relationshipName", roleTypeIdFrom + "_" + roleTypeIdTo + " relation from [" + partyIdFrom + "] to [" + partyIdTo + "]",
                "partyRelationshipTypeId", partyRelationshipTypeId.name(),
                "fromDate", fromDate));
    }

    /**
     * call service that doesn't require permissions
     *
     * @param partyId
     * @return
     * @throws GenericEntityException
     * @throws GenericServiceException
     */
    @SuppressWarnings("unchecked")
    protected Map<String, Object> findPartyNodeById(String partyId, boolean findRelationships) throws GenericEntityException, GenericServiceException {
        Map<String, Object> result = dispatcher.runSync("gnInternalFindPartyNodeById", UtilMisc.toMap("userLogin", userLogin, "partyId", partyId, "findRelationships", (findRelationships) ? "Y" : "N"));
        return (Map<String, Object>) result.get("partyNode");
    }

    public Map<String, Object> gnFindContextById(String contextPartyId, boolean excludeCompanyBases,
                                                 boolean excludeSecurityGroups, boolean excludePartyNode,
                                                 boolean excludeUsers) throws GenericEntityException, GenericServiceException {
        GenericValue gnContext = delegator.findOne("GnContext", UtilMisc.toMap("partyId", contextPartyId, "roleTypeId", "GN_CONTEXT"), false);
        if (UtilValidate.isEmpty(gnContext))
            return GnServiceUtil.returnError(OfbizErrors.ENTITY_EXCEPTION, "Context[" + contextPartyId + "] not found.");
        else {
            Debug.log("Load GnContext[" + contextPartyId + "]", module);
            Map<String, Object> result = FastMap.newInstance();
            result.put("partyId", contextPartyId);
            UserHelper userHelper = new UserHelper(dctx, context);
            result.put("description", gnContext.get("description"));
            if (!excludeCompanyBases)
                result.put("companyBases", getGnContextCompanyBase(contextPartyId));
            if (!excludeSecurityGroups)
                result.put("securityGroups", getUserSecurityGroupsAndPermissions(contextPartyId));
            if (!excludePartyNode)
                result.put("partyNode", getGnPartyGroupPartyNode(contextPartyId));
            if (!excludeUsers) {
                List<GenericValue> contexts = delegator.findByAnd("GnContextUser", UtilMisc.toMap("contextPartyId", contextPartyId));
                result.put("users", new ArrayList<Map<String, Object>>());
                for (GenericValue cu : contexts) {
                    Map<String, Object> user = userHelper.findUserByUserLoginId((String) cu.get("userLoginId"), false, false);
                    UtilMisc.addToListInMap(user, result, "users");
                }
            }
            return result;
        }
    }

    /**
     * Only GnContexts that refer a Company Node can have security groups with follow permissions
     * <p/>
     * <ul>
     * <li> PermissionsOfbiz#GN_AUTH_DRAFT</li>
     * <li> PermissionsOfbiz#GN_AUTH_PUBLISH</li>
     * </ul>
     * <p/>
     *
     * @param securityGroupIds
     */
    private void checkAllowedSecurityGroupPermission(String partyNodeId, List<String> securityGroupIds, List<String> userLoginIds) throws GenericServiceException, GenericEntityException {

        for (String userLoginId : userLoginIds) {
            GenericValue userLoginToCheck = delegator.findOne("UserLogin", false, "userLoginId", userLoginId);
            boolean userWebApi = UtilValidate.isNotEmpty(delegator.findOne("GnUserLoginSource", false, "userLoginId", userLoginId, "enumId", LoginSourceOfbiz.GN_LOG_SRC_WEB_API.name()));
            boolean rootPublish;
            boolean privatePublish;
            boolean authCheck;
            Map<String, Object> publishProfileResponse = dispatcher.runSync("gnRootPublishProfileCheck", UtilMisc.toMap("userLogin", userLoginToCheck));
            rootPublish = "Y".equals(publishProfileResponse.get("hasPermission"));
            Map<String, Object> privateProfileResponse = dispatcher.runSync("gnPrivateAuthPublishProfileCheck", UtilMisc.toMap("userLogin", userLoginToCheck));
            privatePublish = "Y".equals(privateProfileResponse.get("hasPermission"));
            Map<String, Object> authCheckProfileResponse = dispatcher.runSync("gnPrivateAuthCheckProfileCheck", UtilMisc.toMap("userLogin", userLoginToCheck));
            authCheck = "Y".equals(authCheckProfileResponse.get("hasPermission"));


            for (String secGroupId : securityGroupIds) {
                List<GenericValue> secGroupList = delegator.findByAnd("SecurityGroupPermission", UtilMisc.toMap("groupId", secGroupId));

                for (GenericValue secGroup : secGroupList) {
                    String permission = secGroup.getString("permissionId");
                    if (permission.equals(PermissionsOfbiz.GN_AUTH_DRAFT.name()) ||
                            permission.equals(PermissionsOfbiz.GN_AUTH_PUBLISH.name()) ||
                            permission.equals(PermissionsOfbiz.GN_AUTH_CANCEL.name())) {
                        if (!rootPublish) {
                            Debug.logError(OfbizErrors.NOT_ENOUGH_PERMISSIONS.name() + ": Permissions assigned to users conflicting with company's license.", module);
                            throw new GnServiceException(OfbizErrors.NOT_ENOUGH_PERMISSIONS,
                                    "Permissions assigned to users conflicting with company's license.");
                        }
                    }
                    if (permission.equals(PermissionsOfbiz.GN_AUTH_PVT_DRAFT.name()) ||
                            permission.equals(PermissionsOfbiz.GN_AUTH_PVT_PUBLISH.name()) ||
                            permission.equals(PermissionsOfbiz.GN_AUTH_PVT_CANCEL.name())) {
                        if (!privatePublish) {
                            Debug.logError(OfbizErrors.NOT_ENOUGH_PERMISSIONS.name() + ": Permissions assigned to users conflicting with company's license.", module);
                            throw new GnServiceException(OfbizErrors.NOT_ENOUGH_PERMISSIONS,
                                    "Permissions assigned to users conflicting with company's license.");
                        }
                    }
                    if (permission.equals(PermissionsOfbiz.GN_AUTH_CHECK.name())) {
                        if (!authCheck) {
                            Debug.logError(OfbizErrors.NOT_ENOUGH_PERMISSIONS.name() + ": Permissions assigned to users conflicting with company's license.", module);
                            throw new GnServiceException(OfbizErrors.NOT_ENOUGH_PERMISSIONS,
                                    "Permissions assigned to users conflicting with company's license.");
                        }
                    }
                    if (userWebApi &&
                            (!(permission.equals(PermissionsOfbiz.GN_AUTH_VIEW.name()) ||
                                    permission.equals(PermissionsOfbiz.GN_AUTH_DRAFT.name()) ||
                                    permission.equals(PermissionsOfbiz.GN_AUTH_CANCEL.name()) ||
                                    permission.equals(PermissionsOfbiz.GN_AUTH_PUBLISH.name()) ||
                                    permission.equals(PermissionsOfbiz.GN_AUTH_PVT_DRAFT.name()) ||
                                    permission.equals(PermissionsOfbiz.GN_AUTH_PVT_CANCEL.name()) ||
                                    permission.equals(PermissionsOfbiz.GN_AUTH_PVT_PUBLISH.name())
                            ))) {
                        Debug.logError(OfbizErrors.INVALID_PARAMETERS.name() + ": WEB_API user can be associated with a limited set of permissions.", module);
                        throw new GnServiceException(OfbizErrors.INVALID_PARAMETERS,
                                "WEB_API user can be associated with a limited set of permissions.");
                    }
                }
            }
        }

        Map<String, Object> partyNode = findPartyNodeById(partyNodeId, false);
        String nodeType = (String) partyNode.get("nodeType");
        if (!PartyNodeTypeOfbiz.isACompany(nodeType)) {
            List<PermissionsOfbiz> companyBlackList = Arrays.asList(PermissionsOfbiz.GN_AUTH_DRAFT, PermissionsOfbiz.GN_AUTH_PUBLISH,
                    PermissionsOfbiz.GN_COMPANY_ADMIN, PermissionsOfbiz.GN_COMPANY_WRITE, PermissionsOfbiz.GN_COMPANY_BASE_WRITE, PermissionsOfbiz.GN_COMPANY_TREE_ADMIN);
//            List<PermissionsOfbiz> companyBlackList = Arrays.asList(PermissionsOfbiz.GN_AUTH_DRAFT, PermissionsOfbiz.GN_AUTH_PUBLISH, PermissionsOfbiz.GN_AUTH_WRITE);
            List<PermissionsOfbiz> treeBlackList = UtilMisc.toList(PermissionsOfbiz.GN_TREE_ADMIN, PermissionsOfbiz.GN_COMPANY_TREE_ADMIN);
            GnSecurity gnSecurity = new GnSecurity(delegator);
            for (String groupId : securityGroupIds) {
                if (gnSecurity.securityGroupPermissionExists(groupId, PermissionsOfbiz.GN_ADMIN.name())) {
                    Debug.logError("The GN_ADMIN permission can never be assigned!", module);
                    throw new GnServiceException(OfbizErrors.NOT_ENOUGH_PERMISSIONS, "The GN_ADMIN permission can never be assigned!");
                }

                if (!gnSecurity.hasPermission(PermissionsOfbiz.GN_ADMIN.name(), userLogin) && !gnSecurity.hasPermission(PermissionsOfbiz.GN_TREE_ADMIN.name(), userLogin)) {
                    for (PermissionsOfbiz permission : treeBlackList) {
                        if (gnSecurity.securityGroupPermissionExists(groupId, permission.name())) {
                            Debug.logError("You cannot assign the permission " + permission.name() + " contained in group " + groupId, module);
                            throw new GnServiceException(OfbizErrors.NOT_ENOUGH_PERMISSIONS, "You cannot assign the permission " + permission.name() + " contained in group " + groupId);
                        }
                    }
                }

                for (PermissionsOfbiz permission : companyBlackList) {
                    if (gnSecurity.securityGroupPermissionExists(groupId, permission.name())) {
                        Debug.logError("Only GnContexts that refer Company nodes can have SecurityGroup with " + permission.name() + " permission", module);
                        throw new GnServiceException(OfbizErrors.NOT_ENOUGH_PERMISSIONS, "Only GnContexts that refer Company nodes can have SecurityGroup with " + permission.name() + " permission");
                    }
                }
            }
        }
    }

    private EntityTypeOfbiz getEntityType(String partyId) throws GenericServiceException {
        Map<String, Object> result = dispatcher.runSync("gnInternalFindPartyNodeById", UtilMisc.toMap("userLogin", context.get("userLogin"), "findRelationships", "N", "partyId", partyId));
        String nodeType = (String) UtilGenerics.checkMap(result.get("partyNode")).get("nodeType");
        EntityTypeOfbiz ret = EntityTypeMap.getPartyNodeTypeFromEntityTypeId(nodeType);
        if (ret == null)
            throw new GnServiceException(OfbizErrors.INVALID_PARAMETERS, String.format("Node[%s] is not a propagation node.", partyId));
        return ret;


    }
}
