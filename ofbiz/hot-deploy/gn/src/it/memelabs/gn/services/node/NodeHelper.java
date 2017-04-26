package it.memelabs.gn.services.node;

import it.memelabs.gn.services.AbstractServiceHelper;
import it.memelabs.gn.services.OfbizErrors;
import it.memelabs.gn.services.auditing.EntityTypeMap;
import it.memelabs.gn.services.auditing.EntityTypeOfbiz;
import it.memelabs.gn.services.security.PermissionsOfbiz;
import it.memelabs.gn.util.EntityConditionUtil;
import it.memelabs.gn.util.GnServiceException;
import it.memelabs.gn.util.PartyNodeUtil;
import it.memelabs.gn.util.SysUtil;
import it.memelabs.gn.webapp.event.AuditEvent;
import it.memelabs.gn.webapp.event.AuditEventSessionHelper;
import javolution.util.FastList;
import javolution.util.FastMap;
import org.ofbiz.base.util.*;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.entity.condition.EntityCondition;
import org.ofbiz.entity.condition.EntityOperator;
import org.ofbiz.service.DispatchContext;
import org.ofbiz.service.GenericServiceException;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Pattern;

/**
 * 31/01/13
 *
 * @author Andrea Fossi
 */
public class NodeHelper extends AbstractServiceHelper {
    private static final String module = NodeHelper.class.getName();

    /**
     * @param dctx    OFBiz DispatchContext
     * @param context Service Context
     */
    public NodeHelper(DispatchContext dctx, Map<String, ? extends Object> context) {
        super(dctx, context);
    }

    public Map<String, Object> gnCreatePartyNode(Map<String, Object> partyMap) throws GenericServiceException, GenericEntityException {
        if (PartyNodeTypeOfbiz.isACompany(partyMap.get("nodeType")) && UtilValidate.isNotEmpty(findOtherGnCompany(null, (String) partyMap.get("taxIdentificationNumber"), (String) partyMap.get("VATnumber")))) {
            throw new GnServiceException(OfbizErrors.COMPANY_ALREADY_EXISTS, "The Company with taxIdentificationNumber " + partyMap.get("taxIdentificationNumber") + " or VATnumber " + partyMap.get("VATnumber") + " already exists");
        }

        Map<String, Object> srvCtx = dispatcher.getDispatchContext().makeValidContext("createPartyGroup", "IN", partyMap);
        Map<String, Object> srvResult = dispatcher.runSync("createPartyGroup", srvCtx);
        String partyId = (String) srvResult.get("partyId");

        String sourceNodeKey = (String) context.get("sourceNodeKey");

        String ownerNodeKey = (String) context.get("ownerNodeKey");
        if (UtilValidate.isEmpty(ownerNodeKey))
            ownerNodeKey = "GN_ROOT@memelabs";

        String nodeKey = UUID.randomUUID().toString() + "@" + SysUtil.getInstanceId();

        GenericValue partyNode = delegator.makeValue("GnPartyNode");
        partyNode.setNonPKFields(partyMap);
        partyNode.set("partyId", partyId);
        partyNode.set("nodeKey", nodeKey);
        partyNode.set("sourceNodeKey", sourceNodeKey);
        partyNode.set("ownerNodeKey", ownerNodeKey);

        delegator.create(partyNode);
        String nodeType = (String) partyMap.get("nodeType");

        //add partyRole to party
        //see class:  it.memelabs.greennebula.integration.api.model.organization.NodeKind
        String partyRoleId = NodeConst.typeToRole.get(nodeType);
        createPartyRole(partyId, partyRoleId);
        //add role: GN_PROPAGATION_NODE
        if (NodeConst.propagationNodes.contains(nodeType)) {
            createPartyRole(partyId, PartyRoleOfbiz.GN_PROPAGATION_NODE.name());
            createPartyRole(partyId, PartyRoleOfbiz.GN_NODE_CONTEXT.name());
        }
        //save party address info
        @SuppressWarnings("unchecked")
        Map<String, Object> address = (Map<String, Object>) partyMap.get("address");
        if (UtilValidate.isNotEmpty(address))
            createUpdatePostalAddress(partyId, address);
        //save party contact info
        @SuppressWarnings("unchecked")
        Map<String, Object> contact = (Map<String, Object>) partyMap.get("contact");
        if (UtilValidate.isNotEmpty(contact))
            gnCreateUpdatePartyContact(partyId, contact);
        //check owner relationship
        @SuppressWarnings("unchecked")
        List<Map<String, Object>> partyRelationships = (List<Map<String, Object>>) partyMap.get("partyRelationships");
        if (PartyNodeTypeOfbiz.isACompanyBase(nodeType)) {
            boolean hasParent = false;
            if (UtilValidate.isNotEmpty(partyRelationships)) {
                Iterator<Map<String, Object>> it = partyRelationships.iterator();
                while (it.hasNext() && !hasParent) {
                    hasParent = RelationshipTypeOfbiz.GN_BELONGS_TO.name().equals(it.next().get("partyRelationshipTypeId"));
                }
            }
            if (!hasParent)
                throw new GnServiceException("CompanyBase hasn't got relationship to owner.");

        }

        if (PartyNodeTypeOfbiz.isAOrganizzationNode(nodeType)) {
            boolean hasParent = false;
            if (UtilValidate.isNotEmpty(partyRelationships)) {
                Iterator<Map<String, Object>> it = partyRelationships.iterator();
                while (it.hasNext() && !hasParent) {
                    hasParent = RelationshipTypeOfbiz.GN_RECEIVES_FROM.name().equals(it.next().get("partyRelationshipTypeId"));
                }
            }
            if (!hasParent)
                throw new GnServiceException("Organization Node hasn't got GN_RECEIVES_FROM relationship.");

        }

        List<String> reversePartyRelationshipIds = FastList.newInstance();
        List<String> partyRelationshipIds = FastList.newInstance();
        if (UtilValidate.isNotEmpty(partyRelationships))
            for (Map<String, Object> rel : partyRelationships) {
                Map<String, Object> _result = createPartyNodeRelationship(partyId, rel);
                String reversePartyRelationshipId = (String) _result.get("reversePartyRelationshipId");
                if (UtilValidate.isNotEmpty(reversePartyRelationshipId))
                    reversePartyRelationshipIds.add(reversePartyRelationshipId);
                @SuppressWarnings("unchecked")
                String partyRelationshipId = (String) _result.get("partyRelationshipId");
                if (UtilValidate.isNotEmpty(partyRelationshipId))
                    partyRelationshipIds.add(partyRelationshipId);
            }

        Map<String, Object> result = FastMap.newInstance();
        result.put("partyRelationshipIds", partyRelationshipIds);
        result.put("reversePartyRelationshipIds", reversePartyRelationshipIds);
        result.put("partyId", partyId);
        result.put("nodeKey", nodeKey);
        manageInvitedCompany(partyId, nodeType);

        EntityTypeOfbiz nodeTypeEnumId = EntityTypeMap.getPartyNodeTypeFromEntityTypeId(nodeType);
        if (nodeTypeEnumId != null) {
            AuditEvent event = new AuditEvent(nodeTypeEnumId);
            Map<String, Object> savedNode = new NodeSearchHelper(dctx, context).findPartyNodeById(partyId, false);
            event.setNewValue(savedNode);
            if (PartyNodeTypeOfbiz.isACompanyBase(nodeType)) {
                event.addFallback((String) PartyNodeUtil.getOwner(savedNode).get("nodeKey"), EntityTypeOfbiz.COMPANY);
            }
            if (PartyNodeTypeOfbiz.isAOrganizzationNode(nodeType)) {
                boolean hasParent = false;
                if (UtilValidate.isNotEmpty(partyRelationships)) {
                    Iterator<Map<String, Object>> it = partyRelationships.iterator();
                    while (it.hasNext() && !hasParent) {
                        Map<String, Object> rel = it.next();
                        hasParent = RelationshipTypeOfbiz.GN_RECEIVES_FROM.name().equals(rel.get("partyRelationshipTypeId"));
                        if (hasParent)
                            event.addFallback((String) rel.get("nodeKeyTo"), getEntityType(getPartyId((String) rel.get("nodeKeyTo"))));
                    }
                }
            }
            AuditEventSessionHelper.putAuditEvent(event);
        } else {
            Debug.logWarning(String.format("NodeType[%s] is not manage in audit trail", nodeType), module);
        }
        return result;
    }

    /**
     * If a companyBase is added to invited company, first is added to company publication context also.
     * <p/>
     * Invited company can be found because cannot be a propagation node.
     *
     * @param companyBaseId
     * @param nodeType
     * @throws GenericServiceException
     * @throws GenericEntityException
     */
    protected void manageInvitedCompany(String companyBaseId, String nodeType) throws GenericServiceException, GenericEntityException {
        NodeSearchHelper nodeSearchHelper = new NodeSearchHelper(dctx, context);
        if ("COMPANY_BASE".equals(nodeType)) {
            Debug.log("Node[" + companyBaseId + "] is a CompanyBase.", module);
            Map<String, Object> company = nodeSearchHelper.findCompanyByCompanyBaseId(companyBaseId);
            if ("GN_ROOT@memelabs".equals(company.get("ownerNodeKey"))) {
                String companyId = (String) company.get("partyId");
                EntityCondition cond = EntityConditionUtil.makeRelationshipCondition(null, PartyRoleOfbiz.GN_PROPAGATION_NODE, companyId, PartyRoleOfbiz.GN_PROPAGATION_NODE, RelationshipTypeOfbiz.GN_PROPAGATES_TO, true);
                List<GenericValue> result = delegator.findList("GnPartyRelationship", cond, null, null, null, false);
                //invited company cannot be a propagation node.
                if (result.size() == 0) {
                    Debug.log("Company[" + companyId + "] isn't a propagation node.");
                    Map<String, Object> srvRequest = FastMap.newInstance();
                    srvRequest.put("userLoginId", userLogin.getString("userLoginId"));
                    srvRequest.put("partyNodeId", companyId);
                    srvRequest.put("permissionId", PermissionsOfbiz.GN_AUTH_PUBLISH.name());
                    srvRequest.put("loadContext", "N");
                    srvRequest.put("userLogin", userLogin);
                    @SuppressWarnings("unchecked")
                    List<Map<String, Object>> ret = (List<Map<String, Object>>) dispatcher.runSync("gnFindContextByUserLoginPartyNodeAndPermission", srvRequest).get("contextList");
                    if (ret.size() == 0) {
                        Debug.log("Has not been found any publication GnContext", module);
                    } else {
                        for (Map<String, Object> gnContext : ret) {
                            String contextId = (String) gnContext.get("partyId");
                            Debug.log("Found publication GnContext[" + contextId + "]", module);
                            Map<String, Object> srvRequest2 = FastMap.newInstance();
                            srvRequest2.put("partyId", companyBaseId);
                            srvRequest2.put("contextId", contextId);
                            srvRequest2.put("userLogin", userLogin);
                            dispatcher.runSync("gnAddCompanyBaseToContext", srvRequest2);
                        }
                    }
                } else {
                    Debug.log("Company[" + companyId + "] is a propagation node.", module);
                }
            }
        }
    }

    public Map<String, Object> gnUpdatePartyNode(Map<String, Object> partyMap) throws GenericServiceException, GenericEntityException {
        String partyId = (String) partyMap.get("partyId");
        Map<String, Object> oldPartyNode = new NodeSearchHelper(dctx, context).findPartyNodeById(partyId, false);

        if (PartyNodeTypeOfbiz.isACompany(partyMap.get("nodeType")) && UtilValidate.isNotEmpty(findOtherGnCompany(partyId, (String) partyMap.get("taxIdentificationNumber"), (String) partyMap.get("VATnumber")))) {
            throw new GnServiceException(OfbizErrors.COMPANY_ALREADY_EXISTS, "The Company with taxIdentificationNumber " + partyMap.get("taxIdentificationNumber") + " or VATnumber " + partyMap.get("VATnumber") + " already exists");
        }

        Map<String, Object> srvCtx = dispatcher.getDispatchContext().makeValidContext("updatePartyGroup", "IN", partyMap);
        Map<String, Object> srvResult = dispatcher.runSync("updatePartyGroup", srvCtx);
        GenericValue partyNode = delegator.findOne("GnPartyNode", false, "partyId", partyId);
        String nodeKey = partyNode.getString("nodeKey");
        partyNode.setNonPKFields(partyMap);
        partyNode.set("nodeKey", nodeKey);
        delegator.store(partyNode);

        //save party address info
        @SuppressWarnings("unchecked")
        Map<String, Object> address = (Map<String, Object>) partyMap.get("address");

        if (UtilValidate.isNotEmpty(address) && UtilValidate.isEmpty(address.get("contactMechId"))) {
            Map<String, Object> _tmpAddress = getCompanyBaseAddress(partyId);
            if (UtilValidate.isNotEmpty(_tmpAddress) && UtilValidate.isNotEmpty(_tmpAddress.get("contactMechId"))) {
                Debug.log("Update current address with contactMechId[" + _tmpAddress.get("contactMechId") + "]");
                address.put("contactMechId", _tmpAddress.get("contactMechId"));
            }
        }
        if (UtilValidate.isNotEmpty(address))
            createUpdatePostalAddress(partyId, address);

        //save party contact info
        @SuppressWarnings("unchecked")
        Map<String, Object> contact = (Map<String, Object>) partyMap.get("contact");
        if (UtilValidate.isNotEmpty(contact))
            gnCreateUpdatePartyContact(partyId, contact);

        Map<String, Object> result = FastMap.newInstance();
        result.put("partyId", partyId);
        result.put("nodeKey", getNodeKey(partyId));

        EntityTypeOfbiz nodeTypeEnumId = EntityTypeMap.getPartyNodeTypeFromEntityTypeId((String) oldPartyNode.get("nodeType"));
        if (nodeTypeEnumId != null) {
            AuditEvent event = new AuditEvent(nodeTypeEnumId);
            Map<String, Object> updatedPartyNode = new NodeSearchHelper(dctx, context).findPartyNodeById(partyId, false);
            event.setNewValue(updatedPartyNode);
            event.setOldValue(oldPartyNode);
            if (PartyNodeTypeOfbiz.isACompanyBase(oldPartyNode.get("nodeType"))) {
                event.addFallback((String) PartyNodeUtil.getOwner(updatedPartyNode).get("nodeKey"), EntityTypeOfbiz.COMPANY);
            }
            AuditEventSessionHelper.putAuditEvent(event);
        } else {
            Debug.logWarning(String.format("NodeType[%s] is not manage in audit trail", (String) oldPartyNode.get("nodeType")), module);
        }
        return result;
    }

    /**
     * Retrieve partyId from nodeKey.
     * If nodeKey is null return null;
     *
     * @param nodeKey
     * @return partyId
     * @throws GnServiceException
     * @throws GenericEntityException
     */
    public String getPartyId(String nodeKey) throws GnServiceException, GenericEntityException {
        if (UtilValidate.isEmpty(nodeKey))
            return null;
        else {
            List<GenericValue> result = delegator.findList("GnPartyNode", EntityCondition.makeCondition("nodeKey", nodeKey), UtilMisc.toSet("partyId"), null, null, true);
            if (result.size() > 1)
                throw new GnServiceException("Many GnPartyNodes found with key[" + nodeKey + "]");
            else if (result.size() == 0) {
                Debug.logWarning("Unable to find a partyNode with nodeKey[" + nodeKey + "]", module);
                return null;
            } else
                return result.get(0).getString("partyId");
        }
    }

    /**
     * Retrieve nodeKey from partyId.
     * If partyId is null return null.
     *
     * @param partyId
     * @return nodeKey
     * @throws GnServiceException
     * @throws GenericEntityException
     */
    public String getNodeKey(String partyId) throws GnServiceException, GenericEntityException {
        if (UtilValidate.isEmpty(partyId)) {
            return null;
        } else {
            GenericValue result = delegator.findOne("GnPartyNode", UtilMisc.toMap("partyId", partyId), true);
            String nodeKey = result.getString("nodeKey");
            return nodeKey;
        }
    }

    private String createUpdatePostalAddress(String partyId, Map<String, Object> address) throws GenericServiceException {
        Map<String, Object> srvParams = dispatcher.getDispatchContext().makeValidContext("gnCreateUpdatePartyPostalAddress", "IN", address);
        srvParams.put("partyId", partyId);
        srvParams.put("userLogin", userLogin);
        Map<String, Object> result = dispatcher.runSync("gnCreateUpdatePartyPostalAddress", srvParams);
        return (String) result.get("contactMechId");
    }

    private Map<String, Object> gnCreateUpdatePartyContact(String partyId, Map<String, Object> address) throws GenericServiceException {
        Map<String, Object> srvParams = dispatcher.getDispatchContext().makeValidContext("gnCreateUpdatePartyContact", "IN", address);
        srvParams.put("partyId", partyId);
        srvParams.put("userLogin", userLogin);
        return dispatcher.runSync("gnCreateUpdatePartyContact", srvParams);
    }

    private Map<String, Object> createPartyRole(String partyId, String roleTypeId) throws GenericServiceException, GenericEntityException {
    /*    <simple-method method-name="createPartyRole" short-description="Create Party Role">
        <entity-one entity-name="PartyRole" value-field="partyRole"/>
        <if-empty field="partyRole">
        <make-value entity-name="PartyRole" value-field="newEntity"/>
        <set-pk-fields map="parameters" value-field="newEntity"/>
        <create-value value-field="newEntity"/>
        </if-empty>
        </simple-method>*/
        GenericValue partyRole = delegator.findOne("PartyRole", false, "partyId", partyId, "roleTypeId", roleTypeId);
        if (UtilValidate.isEmpty(partyRole)) {
            partyRole = delegator.create("PartyRole", "partyId", partyId, "roleTypeId", roleTypeId);

        }
        return partyRole;
        //return dispatcher.runSync("createPartyRole", UtilMisc.toMap("userLogin", userLogin, "partyId", partyId, "roleTypeId", roleTypeId));
    }

    private Map<String, Object> createPartyNodeRelationship(String partyId, Map<String, Object> rel) throws GenericServiceException {
        Map<String, Object> srvParams = dispatcher.getDispatchContext().makeValidContext("gnCreatePartyNodeRelationship", "IN", rel);
        srvParams.put("partyIdFrom", partyId);
        srvParams.put("userLogin", userLogin);
        srvParams.put("fromDate", UtilDateTime.nowTimestamp());
        return dispatcher.runSync("gnInternalCreatePartyNodeRelationship", srvParams);
    }

    @SuppressWarnings("unchecked")
    protected Map<String, Object> getCompanyBaseAddress(String partyId) throws GenericServiceException {
        List<Map<String, Object>> addresses = (List<Map<String, Object>>) dctx.getDispatcher().runSync("gnGetPostalAddressFromPartyId", UtilMisc.toMap("partyId", partyId, "userLogin", userLogin)).get("addresses");
        if (addresses.size() > 0)
            return addresses.get(0);
        else
            return null;
    }

    private List<GenericValue> findOtherGnCompany(String partyId, String taxIdentificationNumber, String VATnumber) throws GenericEntityException {
        List<EntityCondition> conditionList = FastList.newInstance();
        if (UtilValidate.isNotEmpty(partyId)) {
            conditionList.add(EntityCondition.makeCondition("partyId", EntityOperator.NOT_EQUAL, partyId));
        }

        conditionList.add(EntityCondition.makeCondition(EntityCondition.makeCondition("taxIdentificationNumber", taxIdentificationNumber), EntityOperator.OR, EntityCondition.makeCondition("VATnumber", VATnumber)));
        conditionList.add(EntityCondition.makeCondition("nodeType", PartyNodeTypeOfbiz.COMPANY.name()));
        return delegator.findList("GnCompany", EntityCondition.makeCondition(conditionList), null, null, null, false);
    }

    public boolean syntacticVerifyCompanyTaxAndFiscalCode(Map<String, Object> partyMap) throws GnServiceException {
        String tax = (String) partyMap.get("taxIdentificationNumber");
        String vat = (String) partyMap.get("VATnumber");
        if (partyMap.get("nodeType").equals("COMPANY") || partyMap.get("nodeType").equals("INVITED_COMPANY")) {
            if (UtilValidate.isEmpty(tax) || UtilValidate.isEmpty(vat)) {
                return false;
            }
            final String taxRegExp = "^([a-zA-Z]{6}[0-9]{2}[abcdehlmprstABCDEHLMPRST]{1}[0-9]{2}([a-zA-Z]{1}[0-9]{3})[a-zA-Z]{1}|[0-9]{11}){1}$";
            final String vatRegExp = "^[0-9]{11}$";

            Pattern taxPattern = Pattern.compile(taxRegExp);
            Pattern vatPattern = Pattern.compile(vatRegExp);
            if (!taxPattern.matcher(tax).matches()) {
                throw new GnServiceException(OfbizErrors.TAX_NUMBER_SYNTACTICALLY_INCORRECT, "The tax number " + tax + " isn't syntactically correct!");
            }
            if (!vatPattern.matcher(vat).matches()) {
                throw new GnServiceException(OfbizErrors.VAT_NUMBER_SYNTACTICALLY_INCORRECT, "The vat number " + vat + " isn't syntactically correct!");
            }
        }
        return true;
    }

    public void gnDeletePrivateCompanyOrCompanyBase(String partyId) throws GenericEntityException, GenericServiceException {
        //String privateCompanyId = (String) new NodeSearchHelper(dctx, context).gnFindOwnerCompanyByCompanyBaseId(privateCompanyBaseId).get("companyPartyId");

        //delete companyBase contactMechs

        int result = 0;
        PartyHelper partyHelper = new PartyHelper(dctx, context);
        if (partyHelper.gnPartyRoleCheck(partyId, PartyRoleOfbiz.GN_PVT_COMPANY_BASE.name())) {
            String privateCompanyBaseId = partyId;
            dispatcher.runSync("gnDeleteAllPartyContactMechanisms", UtilMisc.toMap("userLogin", userLogin, "partyId", privateCompanyBaseId));
            //** companyBase
            //partyRole
            dispatcher.runSync("deletePartyRole", UtilMisc.toMap("userLogin", userLogin, "roleTypeId", PartyRoleOfbiz.GN_PVT_COMPANY_BASE.name(), "partyId", privateCompanyBaseId));
            dispatcher.runSync("deletePartyRole", UtilMisc.toMap("userLogin", userLogin, "roleTypeId", PartyRoleOfbiz._NA_.name(), "partyId", privateCompanyBaseId));
            Debug.log("Deleted partyRoles", module);

            result = delegator.removeByAnd("PartyStatus", "partyId", privateCompanyBaseId);
            Debug.log("Deleted PartyNameHistory[" + privateCompanyBaseId + "]", module);
            delegator.removeByAnd("PartyNameHistory", "partyId", privateCompanyBaseId);
            Debug.log("Removed " + result + "PartyStatus", module);
            delegator.removeValue(delegator.findOne("GnPartyNode", false, "partyId", privateCompanyBaseId));
            Debug.log("Deleted GnPartyNode[" + privateCompanyBaseId + "]", module);
            delegator.removeValue(delegator.findOne("PartyGroup", false, "partyId", privateCompanyBaseId));
            Debug.log("Deleted PartyGroup[" + privateCompanyBaseId + "]", module);
            delegator.removeValue(delegator.findOne("Party", false, "partyId", privateCompanyBaseId));
            Debug.log("Deleted Party[" + privateCompanyBaseId + "]", module);
        } else {
            if (partyHelper.gnPartyRoleCheck(partyId, PartyRoleOfbiz.GN_PVT_COMPANY.name())) {
                //** Company
                //partyRole
                String privateCompanyId = partyId;
                dispatcher.runSync("deletePartyRole", UtilMisc.toMap("userLogin", userLogin, "roleTypeId", PartyRoleOfbiz.GN_PVT_COMPANY.name(), "partyId", privateCompanyId));
                dispatcher.runSync("deletePartyRole", UtilMisc.toMap("userLogin", userLogin, "roleTypeId", PartyRoleOfbiz._NA_.name(), "partyId", privateCompanyId));
                Debug.log("Deleted partyRoles", module);

                result = delegator.removeByAnd("PartyStatus", "partyId", privateCompanyId);
                Debug.log("Removed " + result + "PartyStatus", module);
                delegator.removeValue(delegator.findOne("GnPartyNode", false, "partyId", privateCompanyId));
                Debug.log("Deleted PartyNameHistory[" + privateCompanyId + "]", module);
                delegator.removeByAnd("PartyNameHistory", "partyId", privateCompanyId);
                Debug.log("Deleted GnPartyNode[" + privateCompanyId + "]", module);
                delegator.removeValue(delegator.findOne("PartyGroup", false, "partyId", privateCompanyId));
                Debug.log("Deleted PartyGroup[" + privateCompanyId + "]", module);
                delegator.removeValue(delegator.findOne("Party", false, "partyId", privateCompanyId));
                Debug.log("Deleted Party[" + privateCompanyId + "]", module);
            } else {
                throw new GnServiceException(OfbizErrors.INVALID_PARAMETERS, String.format("Cannot delete party[%s]. Party doesn't exist or is not a GN_PVT_COMPANY or GN_PVT_COMPANY_BASE.", partyId));
            }
        }
    }

    private EntityTypeOfbiz getEntityType(String partyId) throws GenericServiceException {
        Map<String, Object> result = dispatcher.runSync("gnInternalFindPartyNodeById", UtilMisc.toMap("userLogin", context.get("userLogin"), "findRelationships", "N", "partyId", partyId));
        String nodeType = (String) UtilGenerics.checkMap(result.get("partyNode")).get("nodeType");
        EntityTypeOfbiz ret = EntityTypeMap.getPartyNodeTypeFromEntityTypeId(nodeType);
        if ("GN_ROOT".equals(partyId)) ret = EntityTypeOfbiz.PROPAGATION_NODE;
        if (ret == null)
            throw new GnServiceException(OfbizErrors.INVALID_PARAMETERS, String.format("Node[%s] is not a propagation node.", partyId));
        return ret;


    }

}
