package it.memelabs.gn.services.node.filter;

import it.memelabs.gn.services.OfbizErrors;
import it.memelabs.gn.services.auditing.EntityTypeMap;
import it.memelabs.gn.services.auditing.EntityTypeOfbiz;
import it.memelabs.gn.services.authorization.AgreementTermTypeOfbiz;
import it.memelabs.gn.services.authorization.AgreementTypesOfbiz;
import it.memelabs.gn.services.authorization.CommonAuthorizationHelper;
import it.memelabs.gn.services.event.EventMessageContainer;
import it.memelabs.gn.services.event.EventMessageDirectionOfbiz;
import it.memelabs.gn.services.event.EventMessageFactory;
import it.memelabs.gn.services.event.type.EventMessage;
import it.memelabs.gn.services.event.type.EventMessageEntityAudit;
import it.memelabs.gn.services.event.type.EventMessageFilterAperture;
import it.memelabs.gn.services.node.RelationshipTypeOfbiz;
import it.memelabs.gn.util.*;
import it.memelabs.gn.webapp.event.AuditContext;
import it.memelabs.gn.webapp.event.AuditEvent;
import javolution.util.FastMap;
import org.ofbiz.base.util.*;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.entity.condition.EntityCondition;
import org.ofbiz.service.DispatchContext;
import org.ofbiz.service.GenericServiceException;

import java.util.*;

/**
 * 03/04/14
 *
 * @author Elisa Spada
 */
public class FilterHelper extends CommonAuthorizationHelper {

    public static final String resource = "GnUiLabels.xml";
    private static final String module = FilterHelper.class.getName();

    public FilterHelper(DispatchContext dctx, Map<String, ? extends Object> context) {
        super(dctx, context);
    }

    public Map<String, Object> gnFilterApertureManage(Map<String, Object> partyRelationship, String taxIdentificationNumber) throws GenericServiceException, GenericEntityException {
        Map<String, Object> result = GnServiceUtil.returnSuccess();
        if (partyRelationship != null) {
            String partyRelationshipId = (String) partyRelationship.get("partyRelationshipId");

            Map<String, Object> relationshipKey;
            if (UtilValidate.isNotEmpty(partyRelationshipId))
                relationshipKey = PartyRelationshipIdUtil.keyToMap(partyRelationshipId);
            else relationshipKey = PartyRelationshipIdUtil.getKeyMap(partyRelationship);
            if (!PartyRelationshipIdUtil.isValid(relationshipKey))
                throw new GnServiceException(OfbizErrors.INVALID_PARAMETERS, "PartyRelationship key is not valid");

            Map<String, Object> oldPartyRelationship = gnFindPartyRelationshipById(relationshipKey);

            List<Map<String, Object>> filters = findFilters(relationshipKey,AgreementTypesOfbiz.GN_AUTH_FILTER);
            boolean acceptAllIfEmptyFilters = ("Y".equals(partyRelationship.get("acceptAllIfEmptyFilters")));
            boolean filterChanged = false;
            if (filters == null || filters.isEmpty()) {
                if (!acceptAllIfEmptyFilters) { //create filter with holder term
                    Map<String, Object> filterMap = gnCreateEmptyFilter(relationshipKey, AgreementTypesOfbiz.GN_AUTH_FILTER.name());
                    createHolderTerm((String) filterMap.get("agreementId"), taxIdentificationNumber);
                    filterChanged = true;
                }// else do nothing
            } else {
                for (Map<String, Object> filter : filters) {
                    String termTypeId = AgreementTermTypeOfbiz.GN_CNS_HOLDER.name();
                    Map<String, Object> term = getTerm(termTypeId, filter);
                    if (term == null) { //create holder term
                        createHolderTerm((String) filter.get("agreementId"), taxIdentificationNumber);
                        filterChanged = true;
                    } else {
                        boolean inclusive = "Y".equals(term.get("inclusive"));
                        if (inclusive) {
                            String agreementTermId = (String) term.get("agreementTermId");
                            List<GenericValue> registrationNumbers = delegator.findByAnd("GnRegistrationNumberAgreementTerm",
                                    "agreementTermId", agreementTermId, "termTypeId", termTypeId);
                            boolean found = false;
                            for (GenericValue regNum : registrationNumbers) {
                                String regNumId = (String) regNum.get("registrationNumber");
                                if (UtilValidate.areEqual(regNumId, taxIdentificationNumber.replaceAll(" ", ""))) {
                                    found = true;
                                    break;
                                }
                            }
                            if (!found) { //update holder term
                                createRegistrationLicenseTerm(agreementTermId, termTypeId, taxIdentificationNumber);
                                filterChanged = true;
                            }
                        }// else do nothing
                    }
                }
            }
            if (filterChanged) {
                makeAuditEventBecauseOfChangingFilter(oldPartyRelationship, relationshipKey);
                makeReceiveEventBecauseOfChangingFilter(relationshipKey, taxIdentificationNumber);
            }
        }
        return result;
    }

    public Map<String, Object> gnFilterApertureSendToEM(String nodePartyId, String taxIdentificationNumber, EventMessageDirectionOfbiz direction, String senderNodeKey, String senderPartyId) throws GenericEntityException, GenericServiceException {
        Map<String, Object> result = GnServiceUtil.returnSuccess();
        if (nodePartyId.equals("GN_ROOT")) return result;
        EntityCondition parentCondition = EntityConditionUtil.makeRelationshipCondition(nodePartyId, null, null, null, RelationshipTypeOfbiz.GN_RECEIVES_FROM.name(), true);
        List<GenericValue> parentRels = delegator.findList("GnPartyRelationship", parentCondition, null, null, null, false);
        GenericValue parentRel = parentRels.isEmpty() ? null : parentRels.get(0);

        String parentPartyId = null;
        if (parentRel != null) {
            gnFilterApertureManage(parentRel, taxIdentificationNumber);
            parentPartyId = (String) parentRel.get("partyIdTo");
        }

        generateFilterApertureEvents(nodePartyId, parentPartyId, taxIdentificationNumber, direction);

        return result;
    }

    private void generateFilterApertureEvents(String nodePartyId, String parentPartyId, String taxIdentificationNumber, EventMessageDirectionOfbiz direction) throws GenericServiceException, GenericEntityException {
        String senderNodeKey = getNodeKey(nodePartyId, null);
        if (direction != null) {
            List<EventMessage> messages = new ArrayList<EventMessage>();
            if (direction == EventMessageDirectionOfbiz.UPWARD) {
                // *** propagate filter aperture to parent *** //
                if (parentPartyId != null && !parentPartyId.equals("GN_ROOT")) {
                    Map<String, Object> partyNode = UtilGenerics.checkMap(dispatcher.runSync("gnInternalFindPartyNodeById", UtilMisc.toMap("partyId", parentPartyId, "userLogin", userLogin, "findRelationships", "N")).get("partyNode"));
                    EventMessageFilterAperture emParent = EventMessageFactory.make().makeEventMessageFilterAperture(
                            (String) partyNode.get("nodeKey"),
                            (String) partyNode.get("partyId"),
                            (String) partyNode.get("nodeType"),
                            EventMessageDirectionOfbiz.UPWARD,
                            senderNodeKey,
                            nodePartyId,
                            taxIdentificationNumber);
                    messages.add(emParent);
                }
            } else if (direction == EventMessageDirectionOfbiz.DOWNWARD) {
                // *** propagate filter aperture to children *** //
                EntityCondition childrenCondition = EntityConditionUtil.makeRelationshipCondition(nodePartyId, null, null, null, RelationshipTypeOfbiz.GN_PROPAGATES_TO.name(), true);
                List<GenericValue> childrenRels = delegator.findList("GnPartyRelationship", childrenCondition, null, null, null, false);
                for (GenericValue rel : childrenRels) {
                    String childPartyId = rel.getString("partyIdTo");
                    Map<String, Object> partyNode = UtilGenerics.checkMap(dispatcher.runSync("gnInternalFindPartyNodeById", UtilMisc.toMap("partyId", childPartyId, "userLogin", userLogin, "findRelationships", "N")).get("partyNode"));
                    EventMessageFilterAperture emC = EventMessageFactory.make().makeEventMessageFilterAperture(
                            (String) partyNode.get("nodeKey"),
                            (String) partyNode.get("partyId"),
                            (String) partyNode.get("nodeType"),
                            EventMessageDirectionOfbiz.DOWNWARD,
                            senderNodeKey,
                            nodePartyId,
                            taxIdentificationNumber);
                    messages.add(emC);
                }
            }
            if (!messages.isEmpty())
                EventMessageContainer.addAll(messages);
        }
    }

    private Map<String, Object> getHolderTermMap(String taxIdentificationNumber) throws GenericServiceException, GenericEntityException {
        Map<String, Object> holderTerm = new HashMap<String, Object>();
        holderTerm.put("modifier", null);
        holderTerm.put("modified", null);
        holderTerm.put("geoIds", new ArrayList<Map>(0));
        holderTerm.put("authorizationTermKey", null);
        holderTerm.put("description", null);
        holderTerm.put("inclusive", "Y");
        holderTerm.put("termTypeId", AgreementTermTypeOfbiz.GN_CNS_HOLDER.name());
        ArrayList<String> taxIdeNumList = new ArrayList<String>(1);
        taxIdeNumList.add(taxIdentificationNumber);
        holderTerm.put("partyNodeTaxidentificationNumbers", taxIdeNumList);
        return holderTerm;
    }

    private void createHolderTerm(String filterId, String taxIdentificationNumber) throws GenericServiceException, GenericEntityException {
        String agreementTermId = createAgreementTerm(getHolderTermMap(taxIdentificationNumber), filterId, null, null);
        createRegistrationLicenseTerm(agreementTermId, AgreementTermTypeOfbiz.GN_CNS_HOLDER.name(), taxIdentificationNumber);
    }

    /**
     * Create a GnStringValueAgreementTerm for each RegistrationNumber of AgreementTerm
     *
     * @param agreementTermId
     * @param registrationNumber
     * @throws GenericEntityException
     * @see it.memelabs.gn.services.authorization.TermAssocHelper#createRegistrationLicenseTerm(java.lang.String, java.lang.String, java.util.List<java.lang.String>)
     */
    private void createRegistrationLicenseTerm(String agreementTermId, String termTypeId, String registrationNumber) throws GenericEntityException {
        String registrationNumberTrimmed = registrationNumber.replaceAll(" ", "");
        GenericValue gv = delegator.makeValue("GnRegistrationNumberAgreementTerm",
                UtilMisc.toMap("agreementTermId", agreementTermId, "registrationNumber", registrationNumberTrimmed, "termTypeId", termTypeId));
        delegator.create(gv);
    }

    /**
     * @param term
     * @param filterId
     * @param filterItemSeqId
     * @param filterItemKey
     * @return
     * @throws GenericServiceException
     * @throws GenericEntityException
     * @see it.memelabs.gn.services.authorization.TermHelper#createAgreementTerm(java.util.Map, String, String, String)
     */
    private String createAgreementTerm(Map<String, Object> term, String filterId, String filterItemSeqId, String filterItemKey) throws GenericServiceException, GenericEntityException {

        GenericValue agreementTerm = delegator.makeValue("AgreementTerm");
        String agreementTermId = delegator.getNextSeqId("AgreementTerm");
        if (term.get("termDays") != null) term.put("termDays", Long.parseLong(term.get("termDays").toString()));
        agreementTerm.setNonPKFields(term);
        agreementTerm.set("agreementId", filterId);
        agreementTerm.set("agreementItemSeqId", filterItemSeqId);
        agreementTerm.set("agreementTermId", agreementTermId);
        agreementTerm.create();

        String filterTermKey = createFilterTermKey(filterItemKey);
        GenericValue authTerm = delegator.makeValue("GnAuthorizationTerm", "agreementTermId", agreementTermId);
        term.remove("lastModifiedDate");
        authTerm.setNonPKFields(term);
        authTerm.set("authorizationTermKey", filterTermKey);
        authTerm.set("lastModifiedByUserLogin", userLogin.get("userLoginId"));
        authTerm.set("lastModifiedDate", UtilDateTime.nowTimestamp());
        GenericValue at = delegator.create(authTerm);

        return agreementTermId;
    }

    /**
     * Create new filterTermKey and check that not exist already.
     *
     * @param filterItemKey
     * @return authorizationTermKey value
     * @throws GenericEntityException
     * @see it.memelabs.gn.services.authorization.TermHelper#createAuthorizationTermKey(java.lang.String, java.lang.String)
     */
    private String createFilterTermKey(String filterItemKey) throws GenericEntityException {
        String uuid = UUID.randomUUID().toString();
        String termKey = String.format("%s#%s", filterItemKey, uuid);
        if (delegator.findByAnd("GnAuthorizationTerm", UtilMisc.toMap("authorizationTermKey", termKey)).size() > 0) {
            Debug.logWarning("An authorizationTerm with key[" + termKey + "] already exist.", module);
            return createFilterTermKey(filterItemKey);
        } else
            return termKey;
    }

    private Map<String, Object> getFilterMap() throws GenericServiceException, GenericEntityException {
        Map<String, Object> filter = new HashMap<String, Object>();
        String filterDescription = UtilProperties.getMessage(resource, "FilterHelper.filterHolderTermDescription", Locale.ITALY);
        filter.put("description", filterDescription);
        filter.put("filterKey", null);
        filter.put("agreementTerms", new ArrayList<Map>(0));
        return filter;
    }

    /**
     * @param relationshipKey
     * @param agreementTypeId
     * @return
     * @throws GenericEntityException
     * @throws GenericServiceException
     * @see it.memelabs.gn.services.authorization.FilterService#gnCreateUpdatedAuthorizationFilters(org.ofbiz.service.DispatchContext, java.util.Map) (java.util.Map, java.util.Map, String)
     */
    public Map<String, Object> gnCreateEmptyFilter(Map<String, Object> relationshipKey, String agreementTypeId) throws GenericEntityException, GenericServiceException {
        Map<String, Object> filter = getFilterMap();
        String filterKey = UUID.randomUUID().toString() + "@" + SysUtil.getInstanceId();
        String agreementId = delegator.getNextSeqId("Agreement");
        Debug.log("Creating filter[" + agreementId + "," + filterKey + "]");
        Map<String, Object> agreementMap = FastMap.newInstance();
        agreementMap.put("agreementTypeId", agreementTypeId);
        agreementMap.put("description", filter.get("description"));
        agreementMap.put("agreementId", agreementId);
        agreementMap.putAll(relationshipKey);
        GenericValue agr = delegator.makeValue("Agreement", agreementMap);
        delegator.create(agr);

        GenericValue authFilter = delegator.makeValue("GnAuthorizationFilter");
        authFilter.set("agreementId", agreementId);
        authFilter.set("filterKey", filterKey);

        authFilter.setNonPKFields(relationshipKey);
        delegator.create(authFilter);

        return UtilMisc.toMap("agreementId", agreementId, "filterKey", (Object) filterKey);
    }

    /**
     * Manage receive event for receive relationship of this inviter node (similar to synchronization, but only for invited party)
     *
     * @param receiveRelationshipKey
     * @throws GenericServiceException
     * @throws GenericEntityException
     */
    private void makeReceiveEventBecauseOfChangingFilter(Map<String, Object> receiveRelationshipKey, String taxIdentificationNumber) throws GenericServiceException, GenericEntityException {
        Map<String, Object> result = dispatcher.runSync("gnSynchronizeNodeForHolderCompany", UtilMisc.toMap("userLogin", userLogin,
                "nodePartyId", receiveRelationshipKey.get("partyIdFrom"), "taxIdentificationNumber", taxIdentificationNumber));
    }

    /**
     * Manage audit event for receive relationship of this inviter node
     *
     * @param oldReceiveRel
     * @param relationshipKey
     * @throws GenericServiceException
     * @throws GenericEntityException
     */
    private void makeAuditEventBecauseOfChangingFilter(Map<String, Object> oldReceiveRel, Map<String, Object> relationshipKey) throws GenericServiceException, GenericEntityException {
        AuditEvent event = new AuditEvent(EntityTypeOfbiz.AUTH_RECEIVES_FROM_REL);
        event.setOldValue(oldReceiveRel);
        Map<String, Object> newReceiveRel = gnFindPartyRelationshipById(relationshipKey);
        event.setNewValue(newReceiveRel);
        event.addFallback((String) newReceiveRel.get("nodeKeyFrom"), getEntityType((String) newReceiveRel.get("partyIdFrom")));
        event.addFallback((String) newReceiveRel.get("nodeKeyTo"), getEntityType((String) newReceiveRel.get("partyIdTo")));

        String userLoginId = null;
        String contextId = null;
        String contextDescription = null;
        if (UtilValidate.isNotEmpty(userLogin)) {
            userLoginId = userLogin.getString("userLoginId");
            contextId = userLogin.getString("activeContextId");
            if (UtilValidate.isNotEmpty(contextId)) {
                Map<String, Object> gnContext = dispatcher.runSync("gnFindContextById",
                        UtilMisc.toMap("partyId", contextId, "userLogin", userLogin,
                                "excludeCompanyBases", "Y",
                                "excludeSecurityGroups", "Y",
                                "excludePartyNode", "Y",
                                "excludeUsers", "Y"));
                contextDescription = (String) gnContext.get("description");
            }
        }
        AuditContext auditContext = new AuditContext(userLoginId, contextId, contextDescription);

        List<Map<String, Object>> entries = new ArrayList<Map<String, Object>>(1);
        entries.add(event.toMap());
        EventMessageEntityAudit message = EventMessageFactory.make().makeEventMessageEntityAudit(
                auditContext.toMap(),
                UtilDateTime.nowDate(),
                entries);
        EventMessageContainer.add(message);
        Debug.log("EventMessageEntityAudit created.");
    }

    private EntityTypeOfbiz getEntityType(String partyId) throws GenericServiceException {
        Map<String, Object> result = dispatcher.runSync("gnInternalFindPartyNodeById", UtilMisc.toMap("userLogin", userLogin, "findRelationships", "N", "partyId", partyId));
        String nodeType = (String) UtilGenerics.checkMap(result.get("partyNode")).get("nodeType");
        EntityTypeOfbiz ret = EntityTypeMap.getPartyNodeTypeFromEntityTypeId(nodeType);
        if ("GN_ROOT".equals(partyId)) ret = EntityTypeOfbiz.PROPAGATION_NODE;
        if (ret == null)
            throw new GnServiceException(OfbizErrors.INVALID_PARAMETERS, String.format("Node[%s] is not a propagation node.", partyId));
        return ret;
    }

    //-------------------------------------------- utilities

    @SuppressWarnings("unchecked")
    protected Map<String, Object> gnFindPartyRelationshipById(Map<String, Object> relationshipKey) throws GenericServiceException, GenericEntityException {
        GenericValue _partyRelationship = delegator.findOne("GnPartyRelationship", relationshipKey, false);
        Map<String, Object> partyRelationship = UtilMisc.makeMapWritable(_partyRelationship);
        String partyRelationshipId = PartyRelationshipIdUtil.mapToKey(partyRelationship);
        partyRelationship.put("partyRelationshipId", partyRelationshipId);
        partyRelationship.put("filters", findFilters(relationshipKey,AgreementTypesOfbiz.GN_AUTH_FILTER));
        partyRelationship.put("target", findPartyNodeById((String) partyRelationship.get("partyIdTo")));
        return partyRelationship;
    }

    /**
     * @param relationshipKey
     * @param  agreementTypeId
     * @return filters
     */
    @SuppressWarnings("unchecked")
    protected List<Map<String, Object>> findFilters(Map<String, Object> relationshipKey,AgreementTypesOfbiz agreementTypeId) throws GenericServiceException {
        Map<String, Object> request = UtilMisc.toMap("userLogin", (Object) userLogin);
        request.putAll(relationshipKey);
        request.put("agreementTypeId",agreementTypeId.name());
        Map<String, Object> result = dispatcher.runSync("gnFindAuthorizationFilterByRelationship", request);
        return (List<Map<String, Object>>) result.get("filters");
    }

    protected Map<String, Object> getTerm(String termTypeId, Map<String, Object> filter) {
        @SuppressWarnings("unchecked")
        List<Map<String, Object>> terms = (List<Map<String, Object>>) filter.get("agreementTerms");
        for (Map<String, Object> term : terms)
            if (UtilValidate.areEqual(term.get("termTypeId"), termTypeId)) return term;
        return null;
    }

    /**
     * Find party by primary key
     *
     * @param partyNodeId
     * @return
     * @throws GenericServiceException
     */
    @SuppressWarnings("unchecked")
    public Map<String, Object> findPartyNodeById(String partyNodeId) throws GenericServiceException {
        return (Map<String, Object>) dispatcher.runSync("gnInternalFindPartyNodeById", UtilMisc.toMap("partyId", partyNodeId, "userLogin", userLogin, "findRelationships", "N")).get("partyNode");
    }

}