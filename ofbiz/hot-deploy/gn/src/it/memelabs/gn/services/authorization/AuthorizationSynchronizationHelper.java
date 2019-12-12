package it.memelabs.gn.services.authorization;

import it.memelabs.gn.services.node.RelationshipTypeOfbiz;
import it.memelabs.gn.util.*;
import it.memelabs.gn.util.find.GnFindUtil;
import javolution.util.FastList;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilGenerics;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.entity.condition.EntityCondition;
import org.ofbiz.entity.transaction.GenericTransactionException;
import org.ofbiz.entity.transaction.TransactionUtil;
import org.ofbiz.service.DispatchContext;
import org.ofbiz.service.GenericServiceException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 07/02/14
 *
 * @author Andrea Fossi
 */
public class AuthorizationSynchronizationHelper extends CommonAuthorizationHelper {

    private AuthorizationHelper authorizationHelper;
    private AuthorizationPublicationHelper authorizationPublicationHelper;

    public AuthorizationSynchronizationHelper(DispatchContext dctx, Map<String, ? extends Object> context) {
        super(dctx, context);
        authorizationHelper = new AuthorizationHelper(dctx, context);
        authorizationPublicationHelper = new AuthorizationPublicationHelper(dctx, context);
    }

    public final static String module = AuthorizationSynchronizationHelper.class.getName();

    public boolean gnSynchronizeNode(String nodeKey) throws GenericServiceException, GenericEntityException {
        int status = TransactionUtil.getStatus();
        Debug.log("" + status);

        String partyId = PartyNodeUtil.getPartyId(dctx, context, null, nodeKey, "NodeKey cannot be resolved to a partyId.");
        GenericValue receiveRel = findReceiveFromRelationship(partyId);

        List<Map<String, Object>> filters = findRelationshipFilters(receiveRel, AgreementTypesOfbiz.GN_AUTH_FILTER);
        List<Map<String, Object>> slicingFilters = findRelationshipFilters(receiveRel, AgreementTypesOfbiz.GN_AUTH_SLI_FILTER);
        boolean slicingFilterPresent = (UtilValidate.isNotEmpty(slicingFilters) && UtilValidate.isNotEmpty(slicingFilters.get(0)));

        String sourcePartyId = receiveRel.getString("partyIdTo");
        String targetPartyId = receiveRel.getString("partyIdFrom");

        List<String> sourceAuthIds = findAuthorizationByNode(sourcePartyId, null,
                AuthorizationStatusOfbiz.GN_AUTH_PUBLISHED.name());
        List<String> targetAuthIds = findAuthorizationByNode(targetPartyId, null,
                AuthorizationStatusOfbiz.GN_AUTH_PUBLISHED.name(),
                AuthorizationStatusOfbiz.GN_AUTH_TO_BE_VALID.name(),
                AuthorizationStatusOfbiz.GN_AUTH_CONFLICTING.name());

        final List<String> filteredSourceAuthIds = new ArrayList<String>(sourceAuthIds.size());

        if (filters.size() == 0) {
            Debug.log("No filter found", module);
            if ("Y".equals(receiveRel.get("acceptAllIfEmptyFilters"))) {
                filteredSourceAuthIds.addAll(sourceAuthIds);
            } // else --> no auth pass --> empty filteredSourceAuthIds
        } else {
            //apply filter
            for (int i = 0; i < sourceAuthIds.size(); i++) {
                String authorizationKey = sourceAuthIds.get(i);
                boolean passFilter = checkFilter(filters, authorizationKey);
                if (passFilter) filteredSourceAuthIds.add(sourceAuthIds.get(i));
                Debug.log(String.format("Processed %s of %s authorization", i + 1, sourceAuthIds.size()), module);
            }
        }

        CollectionDiff<String, String> diff = new CollectionDiff<String, String>(filteredSourceAuthIds, targetAuthIds) { //parent,this
            @Override
            protected boolean isItemEqual(String left, String right) {
                return AuthorizationKey.fromString(left).getUuid().equals(AuthorizationKey.fromString(right).getUuid());
            }
        };

        diff.compare();
        List<String> authIdsToPropagate = diff.getLeftOnly();
        List<String> authIdsToDelete = diff.getRightOnly();

        // NOTA: In teoria non ci sarebbe bisogno di verificare che tutti gli authIdsToPropagate passino gli slicingFilters,
        // perchè sono verificati dalla receive, che eventualmente taglia e/o blocca e cancella la vecchia versione.
        // (Dal momento dell'introduzione degli slicingFilters c'è bisogno di ripropagare tutte le aut. già presenti sul nodo,
        // perchè potrebbero essere da tagliare in modo diverso.)
        //TODO: (ottimizzazione) farlo solo se gli slicingFilters sono effettivamente cambiati (modificati/aggiunti/tolti)

        List<CollectionDiff.Pair<String, String>> both = diff.getBoth();
        for (CollectionDiff.Pair<String, String> pair : both) {
            authIdsToPropagate.add(pair.getLeft());
            //propagazione per aggiornamento, la delete dei vecchi la invocherà la receive
            //va fatta anche se !slicingFilterPresent perchè potrebbero esser stati rimossi
        }

        // Si avvia qui la propagate delete di quelle che sicuramente non passano più gli slicing filters
        // e si rimanda alla receive la cancellazione delle aut. da aggiornare. Questo evita annodamenti tra messaggi propagate/delete.
        if (slicingFilterPresent) {
            //it.memelabs.gn.services.authorization.AuthorizationPublicationHelper.receiveAuthorizationEM
            for (String authorizationKey : authIdsToPropagate) {
                Map<String, Object> authorization = authorizationHelper.findAuthorizationById(null, authorizationKey, true, true);
                Map<String, Object> slicedAuth = applySlicingFilter(receiveRel, authorization, nodeKey); //apply slicing filter
                List<Map<String, Object>> items = UtilGenerics.checkList(slicedAuth.get("authorizationItems"));
                if (items.size() == 0) { //se non passa
                    authIdsToPropagate.remove(authorizationKey);
                    authIdsToDelete.add(authorizationKey);
                }
            }
        }

        generatePropagationEvents(nodeKey, partyId, authIdsToPropagate, authIdsToDelete);
        return false;
    }

    private void generatePropagationEvents(String nodeKey, String partyId, List<String> authIdsToPropagate, List<String> authIdsToDelete) throws GenericTransactionException, GnServiceException {
        if (TransactionUtil.getStatus() != TransactionUtil.STATUS_NO_TRANSACTION)
            throw new GnServiceException("A transaction alreadyExist");
        else {
            TransactionUtil.begin(60 * 10 * 1000);
        }

        try {
            for (String srcKey : authIdsToPropagate) {
                Map<String, Object> result = authorizationPublicationHelper.gnPropagateAuthorizationToEM(partyId, nodeKey, srcKey, true);
            }
            for (String targetKey : authIdsToDelete) {
                authorizationHelper.gnPropagateDeleteAuthorizationToEM(targetKey, nodeKey, partyId);
            }
            TransactionUtil.commit();
        } catch (Exception e) {
            TransactionUtil.rollback();
            throw new GnServiceException(e);
        }
    }

    private List<String> findAuthorizationByNode(String ownerNodeId, String holderPartyNodeId, String... statusIds) throws GenericEntityException, GenericServiceException {
        List<EntityCondition> conds = new ArrayList<EntityCondition>();
        conds.add(EntityCondition.makeCondition("ownerNodeId", ownerNodeId));
        if (statusIds != null && statusIds.length > 0)
            conds.add(GnFindUtil.makeOrConditionById("statusId",
                    Arrays.asList(statusIds)));
        conds.add(GnFindUtil.makeIsEmptyCondition("internalStatusId"));

        if (holderPartyNodeId != null && !holderPartyNodeId.isEmpty()) {
            List<String> companyBaseIds = findCompanyBaseIdsByCompanyId(holderPartyNodeId);
            conds.add(GnFindUtil.makeOrConditionById("originPartyIdTo", companyBaseIds));
        }

        List<GenericValue> list = delegator.findList("GnAuthorizationAndAgreement", EntityCondition.makeCondition(conds), null, UtilMisc.toList("agreementId"), null, false);
        List<String> ids = new ArrayList<String>();
        for (GenericValue gv : list) {
            ids.add(gv.getString("authorizationKey"));
        }
        return ids;
    }

    /**
     * @param partyId (companyId)
     * @return
     * @see it.memelabs.gn.services.node.NodeSearchHelper#findCompanyBasesByCompanyId(java.lang.String)
     */
    private List<String> findCompanyBaseIdsByCompanyId(String partyId) throws GenericEntityException, GenericServiceException {
        EntityCondition cond = EntityConditionUtil.makeRelationshipCondition(null, "GN_COMPANY_BASE", partyId, "GN_COMPANY", RelationshipTypeOfbiz.GN_BELONGS_TO.name(), true);
        List<String> companyBaseIds = FastList.newInstance();
        List<GenericValue> rels = delegator.findList("GnPartyRelationship", cond, null, null, null, false);
        for (GenericValue rel : rels) {
            companyBaseIds.add(rel.getString("partyIdFrom"));
        }
        return companyBaseIds;
    }

    private List<Map<String, Object>> findRelationshipFilters(GenericValue receiveRel, AgreementTypesOfbiz agreementTypesOfbiz) throws GenericEntityException, GenericServiceException {
        FilterHelper filterHelper = new FilterHelper(dctx, context);
        Map<String, Object> relationshipKey = PartyRelationshipIdUtil.getKeyMap(receiveRel);
        return filterHelper.gnFindFilterByRelationship(relationshipKey, agreementTypesOfbiz.name(), false);
    }

    /**
     * @param filters
     * @param authorizationKey
     * @return
     * @throws GenericServiceException
     */
    private boolean checkFilter(List<Map<String, Object>> filters, String authorizationKey) throws GenericServiceException, GenericEntityException {
        if (filters.size() == 0) {
            return false; //default of acceptAllIfEmptyFilters
        } else {
            Map<String, Object> authorization = authorizationHelper.findAuthorizationById(null, authorizationKey, true, true);
            for (Map<String, Object> filter : filters) {
                Debug.log("Matching filter[" + filter.get("agreementId") + "," + filter.get("filterKey") + "] - " + filter.get("description"), module);
                Map<String, Object> result = dispatcher.runSync("gnAuthorizationMatchFilter", UtilMisc.toMap("authorization", authorization, "filter", filter, "mode", "IN", "userLogin", userLogin));
                if ("Y".equals(result.get("match"))) {
                    Debug.log("Filter match", module);
                    return true;
                } else {
                    Debug.log("Filter doesn't match", module);
                }
            }
            return false;
        }
    }

    /**
     * @param relationship
     * @param authorization
     * @param nodeKey
     * @return
     * @throws GenericServiceException AuthorizationPublicationHelper#applySlicingFilter(org.ofbiz.entity.GenericValue, java.util.Map<java.lang.String,java.lang.Object>, java.lang.String)
     */
    public Map<String, Object> applySlicingFilter(GenericValue relationship, Map<String, Object> authorization, String nodeKey) throws GenericServiceException {
        authorization = CloneUtil.deepClone(authorization);
        Debug.log("Applying filter Authorization[" + authorization.get("authorizationKey") + "] on incoming relationship of node [" + nodeKey + "]", module);
        Map<String, Object> srvRequest = PartyRelationshipIdUtil.getKeyMap(relationship);
        srvRequest.put("userLogin", userLogin);
        srvRequest.put("agreementTypeId", AgreementTypesOfbiz.GN_AUTH_SLI_FILTER.name());
        Map<String, Object> srvResult = dispatcher.runSync("gnFindAuthorizationFilterByRelationship", srvRequest);
        List<Map<String, Object>> filters = UtilMisc.getListFromMap(srvResult, "filters");
        if (filters.size() == 0) {
            Debug.log("No filter found", module);
            return authorization;
        } else {
            Map<String, Object> result = dispatcher.runSync("gnAuthorizationApplySlicingFilters", UtilMisc.toMap("authorization", authorization, "filters", filters, "userLogin", userLogin));
            return UtilMisc.getMapFromMap(result, "authorization");
        }
    }

    /**
     * Used during filter aperture (invitation) by it.memelabs.gn.services.node.filter.FilterHelper#makeReceiveEventBecauseOfChangingFilter(java.util.Map<java.lang.String,java.lang.Object>, java.lang.String)
     *
     * @param nodePartyId
     * @param taxIdentificationNumber
     * @throws GenericServiceException
     * @throws GenericEntityException
     */
    public void gnSynchronizeNodeForHolderCompany(String nodePartyId, String taxIdentificationNumber) throws GenericServiceException, GenericEntityException {
        Debug.log("gnSynchronizeNodeForHolderCompany on node " + nodePartyId + " for taxIdentificationNumber " + taxIdentificationNumber);
        String nodeKey = getNodeKey(nodePartyId, null);
        @SuppressWarnings("unchecked")
        Map<String, Object> holderPartyNode = (Map<String, Object>) dispatcher.runSync("gnFindCompanyNodeByTaxIdentificationNumber", UtilMisc.toMap("userLogin", userLogin, "taxIdentificationNumber", taxIdentificationNumber)).get("partyNode");
        if (holderPartyNode != null) {
            GenericValue receiveRel = findReceiveFromRelationship(nodePartyId);
            List<Map<String, Object>> filters = findRelationshipFilters(receiveRel, AgreementTypesOfbiz.GN_AUTH_FILTER);

            String sourcePartyId = receiveRel.getString("partyIdTo");
            String targetPartyId = receiveRel.getString("partyIdFrom"); //=nodePartyId --> do not find auths on this node (expected empty list)

            List<String> sourceAuthIds = findAuthorizationByNode(sourcePartyId, (String) holderPartyNode.get("partyId"),
                    AuthorizationStatusOfbiz.GN_AUTH_PUBLISHED.name()); //see gnSynchronizeNode()

            List<String> filteredSourceAuthIds = new ArrayList<String>(sourceAuthIds.size());

            if (filters.size() == 0) {
                Debug.log("No filter found", module);
                if ("Y".equals(receiveRel.get("acceptAllIfEmptyFilters"))) {
                    filteredSourceAuthIds.addAll(sourceAuthIds);
                } // else --> no auth pass --> empty filteredSourceAuthIds
            } else {
                //apply filter
                for (int i = 0; i < sourceAuthIds.size(); i++) {
                    String authorizationKey = sourceAuthIds.get(i);
                    boolean passFilter = checkFilter(filters, authorizationKey);
                    if (passFilter) filteredSourceAuthIds.add(sourceAuthIds.get(i));
                    Debug.log(String.format("Processed %s of %s authorization", i + 1, sourceAuthIds.size()), module);
                }
            }

            Debug.log("There're " + filteredSourceAuthIds.size() + " authorizations to receive.");
            for (String authIdToPropagate : filteredSourceAuthIds) {
                Map<String, Object> result = authorizationPublicationHelper.gnPropagateAuthorizationToEM(nodePartyId, nodeKey, authIdToPropagate, false);
            } //see generatePropagationEvents() --> it creates temporary authorization and EventMessageAuthReceived
        } //else: it's an invited company not yet registred --> no auths
    }

}
