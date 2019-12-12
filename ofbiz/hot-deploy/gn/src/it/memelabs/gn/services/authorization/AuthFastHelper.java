package it.memelabs.gn.services.authorization;

import it.memelabs.gn.services.OfbizErrors;
import it.memelabs.gn.util.GnServiceException;
import it.memelabs.gn.util.find.GnFindUtil;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilGenerics;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.service.DispatchContext;
import org.ofbiz.service.GenericServiceException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * 26/05/2014
 *
 * @author Andrea Fossi
 */
@Deprecated
public class AuthFastHelper extends CommonAuthorizationHelper {
    private final TermAssocHelper termAssocHelper;

    public AuthFastHelper(DispatchContext dctx, Map<String, ? extends Object> context) {
        super(dctx, context);
        termAssocHelper = new TermAssocHelper(dctx, context);
    }

    protected Map<String, Object> findAuthorizationByIdFast(String agreementId, String authorizationKey, boolean loadNodes, boolean loadDetails) throws GenericEntityException, GenericServiceException {
        List<GenericValue> list = findAuthorizationByIdFast(agreementId, authorizationKey);

        Map<String, Map<String, Object>> authTree = new TreeMap<String, Map<String, Object>>();
        Map<String, Map<String, Object>> itemMap = new TreeMap<String, Map<String, Object>>();

        for (int i = 0; i < list.size(); i++) {
            GenericValue gv = list.get(i);
            String authKey = gv.getString("ITEM_" + "AuthorizationKey");

            //authKey/auth
            if (authTree.get(authKey) == null) {
                Map<String, Object> auth = extractItem("AUTH_", gv);
                authTree.put(authKey, auth);
            }
            //authorizationItems
            Map<String, Object> item = extractItem("ITEM_", gv);
            if (UtilValidate.isNotEmpty(item)) {
                UtilMisc.addToListInMap(item, authTree.get(authKey), "authorizationItems");
                String itemKey = item.get("agreementId") + "_" + item.get("agreementItemSeqId");
                itemMap.put(itemKey, item);
            }
            //authorizationTerms
            Map<String, Object> term = extractItem("TERM_", gv);
            if (UtilValidate.isNotEmpty(term)) {
                Debug.log("Term " + term.get("agreementTermId"));
                String itemKey = item.get("agreementId") + "_" + item.get("agreementItemSeqId");
                UtilMisc.addToListInMap(term, itemMap.get(itemKey), "agreementTerms");
                loadTermsAssoc(term);
            }

            //partyNodeFrom
            if (UtilValidate.isEmpty(authTree.get(authKey).get("partyNodeFrom")))
                authTree.get(authKey).put("partyNodeFrom", extractItem("PFROM_", gv));
            //partyNodeTo
            if (UtilValidate.isEmpty(authTree.get(authKey).get("partyNodeTo")))
                authTree.get(authKey).put("partyNodeTo", extractItem("PTO_", gv));
            //otherPartyNodeTo
            if (UtilValidate.isEmpty(authTree.get(authKey).get("otherPartyNodeTo")))
                authTree.get(authKey).put("otherPartyNodeTo", extractItem("OPTO_", gv));
        }

/*
        if (UtilValidate.isEmpty(auth))
            return auth;
        agreementId = (String) auth.get("agreementId");
        if (loadNodes) {
            String partyIdTo = (String) auth.get("partyIdTo");
            String otherPartyIdTo = (String) auth.get("otherPartyIdTo");
            String partyIdFrom = (String) auth.get("partyIdFrom");
            auth.put("partyNodeTo", findPartyNodeById(partyIdTo));
            auth.put("otherPartyNodeTo", findPartyNodeById(otherPartyIdTo));
            auth.put("partyNodeFrom", findPartyNodeById(partyIdFrom));
        }
        if (loadDetails) {
            AuthorizationItemHelper itemHlp = new AuthorizationItemHelper(dctx, context);
            AuthorizationAttachHelper attachHlp = new AuthorizationAttachHelper(dctx, context);
            auth.put("authorizationItems", itemHlp.findAuthorizationItems(agreementId, null, authorizationKey, null));
            auth.put("authorizationDocuments", attachHlp.aliasFields(attachHlp.find(agreementId, null)));
        } else {
            auth.put("authorizationItems", FastList.newInstance());
            auth.put("authorizationDocuments", FastList.newInstance());
        }
        return auth;*/
        return (Map<String, Object>) authTree.get(authTree.keySet().iterator().next());
    }

    /**
     * Find terms by  {@code agreementId} and {@code agreementItemSeqId}
     * Method load collections of term additional infos also.
     *
     * @return
     * @throws GenericEntityException
     */
    public Map<String, Object> loadTermsAssoc(Map<String, Object> term) throws GenericEntityException, GenericServiceException {
        String termTypeId = (String) term.get("termTypeId");
        if (AgreementTermTypeOfbiz.GN_CNS_PACKAGING.is(termTypeId))
            term.put("packagings", termAssocHelper.findPackagings((String) term.get("agreementTermId")));
        if (AgreementTermTypeOfbiz.GN_CNS_OPERATION.is(termTypeId))
            term.put("operations", termAssocHelper.findOperations((String) term.get("agreementTermId")));
        if (AgreementTermTypeOfbiz.GN_CNS_WASTE.is(termTypeId))
            term.put("wasteCerCodes", termAssocHelper.findWasteCerCodes((String) term.get("agreementTermId")));
        if (AgreementTermTypeOfbiz.GN_CNS_VEHICLE_REG_N.is(termTypeId))
            term.put("registrationNumbers", termAssocHelper.findRegistrationNumbers((String) term.get("agreementTermId"), termTypeId));
        if (AgreementTermTypeOfbiz.GN_CNS_SUBJECT_KIND.is(termTypeId))
            term.put("subjectTypes", termAssocHelper.findSubjectTypes((String) term.get("agreementTermId")));
        if (AgreementTermTypeOfbiz.GN_CNS_SUBJ_COMPANY.is(termTypeId))
            term.put("partyNodes", termAssocHelper.findSubjectPartyNodes((String) term.get("agreementTermId")));
        //filter
        if (AgreementTermTypeOfbiz.GN_CNS_HOLDER.is(termTypeId)) {
            term.put("partyNodes", termAssocHelper.findHolderPartyNodes((String) term.get("agreementTermId"), termTypeId, false));
            term.put("geos", termAssocHelper.findGeos((String) term.get("agreementTermId")));
        }
        if (AgreementTermTypeOfbiz.GN_CNS_SUBJ_ROLE.is(termTypeId))
            term.put("subjectRoles", termAssocHelper.findSubjectTypes((String) term.get("agreementTermId")));
        if (AgreementTermTypeOfbiz.GN_CNS_AUTH_TYPE.is(termTypeId))
            term.put("authorizationTypes", termAssocHelper.findSubjectTypes((String) term.get("agreementTermId")));

        //  if (AgreementTermTypeOfbiz.GN_CNS_WASTE_ENTRY.is(term.get("termTypeId"))) ;
        // if (AgreementTermTypeOfbiz.GN_CNS_WASTE_STOCK.is(term.get("termTypeId"))) ;
        // if (AgreementTermTypeOfbiz.GN_CNS_SUBJECT_KIND.is(term.get("termTypeId"))) ;

        return term;
    }


    /**
     * @param prefix
     * @param source
     * @return
     */
    private Map<String, Object> extractItem(String prefix, Map<String, Object> source) {
        Map<String, Object> ret = new HashMap<String, Object>();
        for (String fieldName : source.keySet()) {
            if (fieldName.startsWith(prefix))
                ret.put(GnFindUtil.removePrefix(prefix, fieldName), source.get(fieldName));
        }
        return ret;
    }

    /**
     * Finds authorization by id and it doesn't  load any associated entity
     *
     * @param agreementId
     * @param authorizationKey
     * @return authorization Map or null is not exist
     * @throws GenericEntityException
     */
    public List<GenericValue> findAuthorizationByIdFast(String agreementId, String authorizationKey) throws GenericEntityException, GenericServiceException {
        if (UtilValidate.isEmpty(agreementId) && UtilValidate.isEmpty(authorizationKey))
            throw new GnServiceException(OfbizErrors.INVALID_PARAMETERS, "agreementId and authorizationKey cannot be empty both");
        List<GenericValue> l;
        if (UtilValidate.isNotEmpty(agreementId))
            l = delegator.findByAnd("GnAuthTest", UtilMisc.toMap("AUTH_AgreementId", agreementId),
                    UtilMisc.toList("AUTH_AgreementId", "ITEM_AgreementItemSeqId", "TERM_AgreementTermId"));
        else {
            l = delegator.findByAnd("GnAuthTest", UtilMisc.toMap("AUTH_AuthorizationKey", authorizationKey),
                    UtilMisc.toList("AUTH_AgreementId", "ITEM_AgreementItemSeqId", "TERM_AgreementTermId"));
        }
        return l;
    }

    /**
     * Find party by primary key
     *
     * @param partyNodeId
     * @return
     * @throws GenericServiceException
     */
    protected Map<String, Object> findPartyNodeById(String partyNodeId) throws GenericServiceException {
        return UtilGenerics.checkMap(dispatcher.runSync("gnInternalFindPartyNodeById", UtilMisc.toMap("partyId", partyNodeId, "userLogin", userLogin, "findRelationships", "N")).get("partyNode"));
    }

}
