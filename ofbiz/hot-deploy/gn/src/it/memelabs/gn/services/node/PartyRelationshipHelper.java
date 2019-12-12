package it.memelabs.gn.services.node;

import it.memelabs.gn.services.AbstractServiceHelper;
import it.memelabs.gn.services.OfbizErrors;
import it.memelabs.gn.services.auditing.EntityTypeMap;
import it.memelabs.gn.services.auditing.EntityTypeOfbiz;
import it.memelabs.gn.services.authorization.AgreementTypesOfbiz;
import it.memelabs.gn.util.GnServiceException;
import it.memelabs.gn.util.PartyRelationshipIdUtil;
import it.memelabs.gn.webapp.event.AuditEvent;
import it.memelabs.gn.webapp.event.AuditEventSessionHelper;
import javolution.util.FastList;
import javolution.util.FastMap;
import org.ofbiz.base.util.*;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.entity.condition.EntityCondition;
import org.ofbiz.entity.condition.EntityExpr;
import org.ofbiz.entity.condition.EntityOperator;
import org.ofbiz.entity.util.EntityUtil;
import org.ofbiz.service.DispatchContext;
import org.ofbiz.service.GenericServiceException;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 02/04/13
 *
 * @author Andrea Fossi
 */
public class PartyRelationshipHelper extends AbstractServiceHelper {
    private static final String module = PartyRelationshipHelper.class.getName();

    /**
     * @param dctx    OFBiz DispatchContext
     * @param context Service Context
     */
    public PartyRelationshipHelper(DispatchContext dctx, Map<String, ? extends Object> context) {
        super(dctx, context);
    }

    public Map<String, Object> gnCreatePartyNodeRelationship(String partyIdFrom, String roleTypeIdFrom,
                                                             String partyIdTo, String roleTypeIdTo,
                                                             String partyRelationshipTypeId, Timestamp fromDate,
                                                             String validationRequired, String synchronizationAllowed, String acceptAllIfEmptyFilters,
                                                             String relationshipName, String createReverse) throws GenericServiceException, GenericEntityException {
        PartyHelper partyHelper = new PartyHelper(dctx, context);
        if (UtilValidate.isEmpty(fromDate)) fromDate = UtilDateTime.nowTimestamp();

        if (RelationshipTypeOfbiz.GN_PROPAGATES_TO.name().equals(partyRelationshipTypeId) || RelationshipTypeOfbiz.GN_RECEIVES_FROM.name().equals(partyRelationshipTypeId)) {
            roleTypeIdFrom = PartyRoleOfbiz.GN_PROPAGATION_NODE.name();
            roleTypeIdTo = PartyRoleOfbiz.GN_PROPAGATION_NODE.name();
        }
        if (RelationshipTypeOfbiz.GN_PUBLISHES_TO_ROOT.name().equals(partyRelationshipTypeId)) {
            roleTypeIdFrom = PartyRoleOfbiz.GN_COMPANY.name();
            roleTypeIdTo = PartyRoleOfbiz.GN_ROOT.name();
        }

        if (RelationshipTypeOfbiz.GN_BELONGS_TO.name().equals(partyRelationshipTypeId) || RelationshipTypeOfbiz.GN_OWNS.name().equals(partyRelationshipTypeId)) {
            if (partyHelper.gnPartyRoleCheck(partyIdFrom, PartyRoleOfbiz.GN_COMPANY.name()))
                roleTypeIdFrom = PartyRoleOfbiz.GN_COMPANY.name();
            if (partyHelper.gnPartyRoleCheck(partyIdFrom, PartyRoleOfbiz.GN_COMPANY_BASE.name()))
                roleTypeIdFrom = PartyRoleOfbiz.GN_COMPANY_BASE.name();
            if (partyHelper.gnPartyRoleCheck(partyIdTo, PartyRoleOfbiz.GN_COMPANY.name()))
                roleTypeIdTo = PartyRoleOfbiz.GN_COMPANY.name();
            if (partyHelper.gnPartyRoleCheck(partyIdTo, PartyRoleOfbiz.GN_COMPANY_BASE.name()))
                roleTypeIdTo = PartyRoleOfbiz.GN_COMPANY_BASE.name();
            if (UtilValidate.isEmpty(roleTypeIdFrom)) partyIdFrom = PartyRoleOfbiz._NA_.name();
            if (UtilValidate.isEmpty(roleTypeIdTo)) partyIdTo = PartyRoleOfbiz._NA_.name();
        }

        if (RelationshipTypeOfbiz.GN_CBASE_DELEGATES.name().equals(partyRelationshipTypeId)) {
            roleTypeIdFrom = PartyRoleOfbiz.GN_COMPANY_BASE.name();
            roleTypeIdTo = PartyRoleOfbiz.GN_COMPANY_BASE.name();
        }
        Map<String, Object> rel = FastMap.newInstance();
        rel.put("partyIdTo", partyIdTo);
        rel.put("partyIdFrom", partyIdFrom);
        rel.put("fromDate", fromDate);
        rel.put("roleTypeIdFrom", roleTypeIdFrom);
        rel.put("roleTypeIdTo", roleTypeIdTo);
        rel.put("partyRelationshipTypeId", partyRelationshipTypeId);
        rel.put("validationRequired", validationRequired);

        if (RelationshipTypeOfbiz.GN_RECEIVES_FROM.name().equals(partyRelationshipTypeId)) {

            rel.put("acceptAllIfEmptyFilters", acceptAllIfEmptyFilters);

            synchronizationAllowed = "Y";
        } else {
            synchronizationAllowed = "N";
        }
        rel.put("synchronizationAllowed", synchronizationAllowed);

        rel.put("relationshipName", relationshipName);
        rel.put("userLogin", userLogin);
        Map<String, Object> ret = dispatcher.runSync("createPartyRelationship", rel);

        String partyRelationshipId = PartyRelationshipIdUtil.mapToKey(rel);
        Debug.log("Created relationship[" + partyRelationshipId + "]", module);

        Map<String, Object> result = FastMap.newInstance();

        //  create reverse relationship
        if ("Y".equals(createReverse)) {
            String rPartyRelationshipTypeId = PartyRoleOfbiz._NA_.name();
            if (RelationshipTypeOfbiz.GN_OWNS.name().equals(partyRelationshipTypeId))
                rPartyRelationshipTypeId = RelationshipTypeOfbiz.GN_BELONGS_TO.name();
            if (RelationshipTypeOfbiz.GN_BELONGS_TO.name().equals(partyRelationshipTypeId))
                rPartyRelationshipTypeId = RelationshipTypeOfbiz.GN_OWNS.name();
            if (RelationshipTypeOfbiz.GN_PROPAGATES_TO.name().equals(partyRelationshipTypeId))
                rPartyRelationshipTypeId = RelationshipTypeOfbiz.GN_RECEIVES_FROM.name();
            if (RelationshipTypeOfbiz.GN_RECEIVES_FROM.name().equals(partyRelationshipTypeId))
                rPartyRelationshipTypeId = RelationshipTypeOfbiz.GN_PROPAGATES_TO.name();

            if (RelationshipTypeOfbiz.GN_RECEIVES_FROM.name().equals(rPartyRelationshipTypeId)) {
                synchronizationAllowed = "Y";
            } else {
                synchronizationAllowed = "N";
            }

            Map<String, Object> _result = gnCreatePartyNodeRelationship(partyIdTo, roleTypeIdTo, partyIdFrom,
                    roleTypeIdFrom, rPartyRelationshipTypeId, fromDate, validationRequired, synchronizationAllowed,
                    acceptAllIfEmptyFilters, relationshipName + "-R", "N");
            if (UtilValidate.isNotEmpty(_result.get("partyRelationshipId")))
                result.put("reversePartyRelationshipId", _result.get("partyRelationshipId"));

        }

        //Audit data
        if (RelationshipTypeOfbiz.GN_RECEIVES_FROM.name().equals(partyRelationshipTypeId)) {
            AuditEvent event = new AuditEvent(EntityTypeOfbiz.AUTH_RECEIVES_FROM_REL);
            Map<String, Object> keyMap = PartyRelationshipIdUtil.getKeyMap(rel);
            Map<String, Object> pr = gnFindPartyRelationshipById(keyMap);
            event.setNewValue(pr);
            event.addFallback((String) pr.get("nodeKeyFrom"), getEntityType((String) pr.get("partyIdFrom")));
            event.addFallback((String) pr.get("nodeKeyTo"), getEntityType((String) pr.get("partyIdTo")));
            AuditEventSessionHelper.putAuditEvent(event);
        }

        result.put("partyRelationshipId", partyRelationshipId);
        return result;
    }

    public void gnUpdatePartyNodeRelationship(Map<String, Object> relationshipKey, String partyRelationshipTypeId,
                                              String validationRequired, String acceptAllIfEmptyFilters, String relationshipName,
                                              List<Map<String, Object>> filters, List<Map<String, Object>> slicingFilters) throws GenericServiceException, GenericEntityException {
        Map<String, Object> oldPr = null;
        if (RelationshipTypeOfbiz.GN_RECEIVES_FROM.name().equals(partyRelationshipTypeId)) {
            oldPr = gnFindPartyRelationshipById(relationshipKey);
        }

        GenericValue rel = delegator.findOne("PartyRelationship", relationshipKey, false);
        rel.set("partyRelationshipTypeId", partyRelationshipTypeId, false);
        rel.set("validationRequired", validationRequired);

        String synchronizationAllowed;
        if (RelationshipTypeOfbiz.GN_RECEIVES_FROM.name().equals(partyRelationshipTypeId)) {

            rel.set("acceptAllIfEmptyFilters", acceptAllIfEmptyFilters);

            synchronizationAllowed = "Y";
        } else {
            synchronizationAllowed = "N";
        }
        rel.set("synchronizationAllowed", synchronizationAllowed);

        rel.set("relationshipName", relationshipName);
        delegator.store(rel);
        Debug.log("Updated relationship[" + relationshipKey + "]", module);
        createUpdateFilters(relationshipKey, filters, AgreementTypesOfbiz.GN_AUTH_FILTER.name());
        createUpdateFilters(relationshipKey, slicingFilters, AgreementTypesOfbiz.GN_AUTH_SLI_FILTER.name());

        if ("N".equals(validationRequired)) {
            Map<String, Object> result = dispatcher.runSync("gnHasToBeValidatedAuthorization", UtilMisc.toMap("userLogin", userLogin, "partyId", rel.get("partyIdFrom")));
            if ("Y".equals(result.get("hasToBeValidatedAuthorization")))
                throw new GnServiceException(OfbizErrors.CANNOT_DISABLE_VALIDATION_FLAG, "Recipient node contains auth to be validated", module);
        }

        //Audit data
        if (RelationshipTypeOfbiz.GN_RECEIVES_FROM.name().equals(partyRelationshipTypeId)) {
            AuditEvent event = new AuditEvent(EntityTypeOfbiz.AUTH_RECEIVES_FROM_REL);
            Map<String, Object> pr = gnFindPartyRelationshipById(relationshipKey);
            event.setNewValue(pr);
            event.setOldValue(oldPr);
            event.addFallback((String) pr.get("nodeKeyFrom"), getEntityType((String) pr.get("partyIdFrom")));
            event.addFallback((String) pr.get("nodeKeyTo"), getEntityType((String) pr.get("partyIdTo")));
            AuditEventSessionHelper.putAuditEvent(event);
        }
    }

    /**
     * @param relationshipKey
     * @param filters
     * @return
     */
    private Map<String, Object> createUpdateFilters(Map<String, Object> relationshipKey, List<Map<String, Object>> filters, String agreementTypeId) throws GenericServiceException {
        if (filters == null) filters = FastList.newInstance();
        return dispatcher.runSync("gnCreateUpdatedAuthorizationFilters",
                UtilMisc.toMap("userLogin", userLogin, "relationshipKey", relationshipKey, "filters", filters, "agreementTypeId", agreementTypeId));
    }

    /**
     * @param relationshipKey
     * @return filters
     */
    @SuppressWarnings("unchecked")
    private List<Map<String, Object>> findFilters(Map<String, Object> relationshipKey, AgreementTypesOfbiz agreementTypeId) throws GenericServiceException {
        Map<String, Object> request = UtilMisc.toMap("userLogin", (Object) userLogin);
        request.putAll(relationshipKey);
        request.put("agreementTypeId", agreementTypeId.name());
        Map<String, Object> result = dispatcher.runSync("gnFindAuthorizationFilterByRelationship", request);
        return (List<Map<String, Object>>) result.get("filters");
    }


    @SuppressWarnings("unchecked")
    public Map<String, Object> gnFindPartyRelationshipById(Map<String, Object> relationshipKey) throws GenericServiceException, GenericEntityException {
        GenericValue _partyRelationship = delegator.findOne("GnPartyRelationship", relationshipKey, false);
        Map<String, Object> partyRelationship = UtilMisc.makeMapWritable(_partyRelationship);
        String partyRelationshipId = PartyRelationshipIdUtil.mapToKey(partyRelationship);
        partyRelationship.put("partyRelationshipId", partyRelationshipId);
        partyRelationship.put("filters", findFilters(relationshipKey, AgreementTypesOfbiz.GN_AUTH_FILTER));
        partyRelationship.put("slicingFilters", findFilters(relationshipKey, AgreementTypesOfbiz.GN_AUTH_SLI_FILTER));
        partyRelationship.put("target", findPartyNodeById((String) partyRelationship.get("partyIdTo")));
        return partyRelationship;
    }

    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> gnFindPartyRelationships(Collection<String> partyIdsFrom, Collection<String> roleTypeIdsFrom, Collection<String> partyRelationshipTypeIds,
                                                              Collection<String> partyIdsTo, Collection<String> roleTypeIdsTo, boolean validOnly) throws GenericServiceException, GenericEntityException {
        //Map<String, Object> result = ServiceUtil.returnSuccess();
        List<Map<String, Object>> ret = FastList.newInstance();
        List<EntityCondition> conditions = new ArrayList<EntityCondition>();

        //partyIdsFrom = UtilMisc.toList("GN_MEMELABS", "GN_ON_TOSC");//****** only for test
        if (partyIdsFrom != null && !partyIdsFrom.isEmpty()) {
            List<EntityExpr> partyIdsFromCondition = new ArrayList<EntityExpr>();
            for (String partyIdFrom : partyIdsFrom) {
                partyIdsFromCondition.add(EntityCondition.makeCondition("partyIdFrom", EntityOperator.EQUALS, partyIdFrom.trim()));
            }
            conditions.add(EntityCondition.makeCondition(partyIdsFromCondition, EntityOperator.OR));
        }


        //roleTypeIdsFrom = UtilMisc.toList( "GN_PROPAGATION_NODE");//****** only for test
        if (roleTypeIdsFrom != null && !roleTypeIdsFrom.isEmpty()) {
            List<EntityExpr> roleTypeIdsFromCondition = new ArrayList<EntityExpr>();
            for (String partyIdFrom : roleTypeIdsFrom) {
                roleTypeIdsFromCondition.add(EntityCondition.makeCondition("roleTypeIdFrom", EntityOperator.EQUALS, partyIdFrom.trim()));
            }
            conditions.add(EntityCondition.makeCondition(roleTypeIdsFromCondition, EntityOperator.OR));
        }

        //partyRelationshipTypeIds = UtilMisc.toList(RelationshipTypeOfbiz.GN_PROPAGATES_TO.name(), RelationshipTypeOfbiz.GN_RECEIVES_FROM.name());//****** only for test
        if (partyRelationshipTypeIds != null && !partyRelationshipTypeIds.isEmpty()) {
            List<EntityExpr> partyRelationshipTypeIdsCondition = new ArrayList<EntityExpr>();
            for (String partyIdFrom : partyRelationshipTypeIds) {
                partyRelationshipTypeIdsCondition.add(EntityCondition.makeCondition("partyRelationshipTypeId", EntityOperator.EQUALS, partyIdFrom.trim()));
            }
            conditions.add(EntityCondition.makeCondition(partyRelationshipTypeIdsCondition, EntityOperator.OR));
        }

        //partyIdsTo = UtilMisc.toList(RelationshipTypeOfbiz.GN_PROPAGATES_TO.name(), RelationshipTypeOfbiz.GN_RECEIVES_FROM.name());//****** only for test
        if (partyIdsTo != null && !partyIdsTo.isEmpty()) {
            List<EntityExpr> partyIdsToCondition = new ArrayList<EntityExpr>();
            for (String partyIdFrom : partyIdsTo) {
                partyIdsToCondition.add(EntityCondition.makeCondition("partyIdTo", EntityOperator.EQUALS, partyIdFrom.trim()));
            }
            conditions.add(EntityCondition.makeCondition(partyIdsToCondition, EntityOperator.OR));
        }

        //roleTypeIdsTo = UtilMisc.toList(RelationshipTypeOfbiz.GN_PROPAGATES_TO.name(), RelationshipTypeOfbiz.GN_RECEIVES_FROM.name());//****** only for test
        if (roleTypeIdsTo != null && !roleTypeIdsTo.isEmpty()) {
            List<EntityExpr> roleTypeIdsToCondition = new ArrayList<EntityExpr>();
            for (String partyIdFrom : roleTypeIdsTo) {
                roleTypeIdsToCondition.add(EntityCondition.makeCondition("roleTypeIdTo", EntityOperator.EQUALS, partyIdFrom.trim()));
            }
            conditions.add(EntityCondition.makeCondition(roleTypeIdsToCondition, EntityOperator.OR));
        }

        if (validOnly) conditions.add(EntityUtil.getFilterByDateExpr());

        EntityCondition condition = (conditions.isEmpty()) ? null : EntityCondition.makeCondition(conditions);

        List<GenericValue> partyRelationships = delegator.findList("GnPartyRelationship", condition, null, null, null, false);

        //result.put("partyRelationships", FastList.newInstance());

        /*
        Composite primary key:
        <prim-key field="partyIdFrom"/>
        <prim-key field="partyIdTo"/>
        <prim-key field="roleTypeIdFrom"/>
        <prim-key field="roleTypeIdTo"/>
        <prim-key field="fromDate"/>
         */

        for (GenericValue _partyRelationship : partyRelationships) {
            Map<String, Object> partyRelationship = UtilMisc.makeMapWritable(_partyRelationship);
            String partyRelationshipId = PartyRelationshipIdUtil.mapToKey(partyRelationship);
            partyRelationship.put("partyRelationshipId", partyRelationshipId);
            ret.add(partyRelationship);
        }

        return ret;
    }

    /**
     * Find party by primary key
     *
     * @param partyNodeId
     * @return
     * @throws GenericServiceException
     */
    @SuppressWarnings("unchecked")
    private Map<String, Object> findPartyNodeById(String partyNodeId) throws GenericServiceException {
        return (Map<String, Object>) dispatcher.runSync("gnInternalFindPartyNodeById", UtilMisc.toMap("partyId", partyNodeId, "userLogin", userLogin, "findRelationships", "N")).get("partyNode");
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

    protected boolean isSynchronizationAllowed(Map<String, Object> receivesFromRelation) throws GnServiceException {
        if (RelationshipTypeOfbiz.GN_RECEIVES_FROM.toString().equals(receivesFromRelation.get("partyRelationshipTypeId"))) {
            return !"N".equalsIgnoreCase((String) receivesFromRelation.get("synchronizationAllowed"));
        }
        throw new GnServiceException(OfbizErrors.INVALID_PARAMETERS, String.format("The argument is not a valid %s relationship", RelationshipTypeOfbiz.GN_RECEIVES_FROM.toString()));
    }

    protected boolean isSynchronizationActive(Map<String, Object> receivesFromRelation) throws GnServiceException {
        if (RelationshipTypeOfbiz.GN_RECEIVES_FROM.toString().equals(receivesFromRelation.get("partyRelationshipTypeId"))) {
            return "Y".equalsIgnoreCase((String) receivesFromRelation.get("synchronizationActive"));
        }
        throw new GnServiceException(OfbizErrors.INVALID_PARAMETERS, String.format("The argument is not a valid %s relationship", RelationshipTypeOfbiz.GN_RECEIVES_FROM.toString()));
    }

}
