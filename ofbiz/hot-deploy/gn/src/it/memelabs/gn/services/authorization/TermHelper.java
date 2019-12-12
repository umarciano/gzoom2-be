package it.memelabs.gn.services.authorization;

import it.memelabs.gn.services.OfbizErrors;
import it.memelabs.gn.util.CollectionDiff;
import it.memelabs.gn.util.GnServiceException;
import javolution.util.FastList;
import javolution.util.FastSet;
import org.ofbiz.base.util.*;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.entity.condition.EntityCondition;
import org.ofbiz.service.DispatchContext;
import org.ofbiz.service.GenericServiceException;

import java.util.*;

/**
 * 19/03/13
 *
 * @author Andrea Fossi
 */
public class TermHelper extends CommonAuthorizationHelper {
    public TermHelper(DispatchContext dctx, Map<String, ? extends Object> context) {
        super(dctx, context);
        termAssocHelper = new TermAssocHelper(dctx, context);
    }

    public final static String module = AuthorizationHelper.class.getName();
    private final TermAssocHelper termAssocHelper;

    /**
     * Create agreementTerms
     * <p/>
     * Depending on termType system call the right create method
     *
     * @param agreementTerms
     * @param agreementId
     * @param agreementItemSeqId @return agreementTermIds
     */
    public List<String> createAgreementTerms(List<Map<String, Object>> agreementTerms, String agreementId, String agreementItemSeqId, String authorizationItemKey) throws GenericServiceException, GenericEntityException {
        if (agreementTerms == null)
            return null;
        List<String> agreementTermIds = FastList.newInstance();

        checkTermTypeUnivocity(agreementTerms);
        for (Map<String, Object> term : agreementTerms) {
            String agreementTermId = createAgreementTerm(term, agreementId, agreementItemSeqId, authorizationItemKey);
            agreementTermIds.add(agreementTermId);
            Debug.log("Saved authorizationTerm with type[" + term.get("termTypeId") + "] agreementId[" + agreementId + "] and agreementItemSeqId " + agreementItemSeqId + "]", module);
        }
        return agreementTermIds;
    }


    /**
     * Method explores terms in {@code agreementTerms} will do one of follow action:
     * <ul>
     * <li>If term exist  update them</li>
     * <li>If term doesn't exist on db create them</li>
     * <li>If term exits on db and not in {@code agreementTerms} delete them</li>
     * </ul>
     * <p/>
     * Depending on termType system call the right update method
     *
     * @param agreementTerms
     * @return agreementTermIds
     * @throws GenericEntityException
     */
    public List<Map<String, Object>> updateAgreementTerms(String agreementId, String agreementItemSeqId, List<Map<String, Object>> agreementTerms, String authorizationItemKey) throws GenericServiceException, GenericEntityException {
        checkTermTypeUnivocity(agreementTerms);

        if (agreementTerms != null) {
            List<Map<String, Object>> agreementTermsOld = findAuthorizationAndAgreementTerms(agreementId, agreementItemSeqId, false);
            CollectionDiff<Map<String, Object>, Map<String, Object>> diff = new CollectionDiff<Map<String, Object>, Map<String, Object>>(agreementTermsOld, agreementTerms) {
                @Override
                protected boolean isItemEqual(Map<String, Object> left, Map<String, Object> right) {
                    //String agreementTermIdLeft = left != null ? (String) left.get("agreementTermId") : null;
                    //String agreementTermIdRight = right != null ? (String) right.get("agreementTermId") : null;
                    //return agreementTermIdLeft != null ? agreementTermIdLeft.equals(agreementTermIdRight) : agreementTermIdRight == null;
                    String leftTermKey = (String) left.get("authorizationTermKey");
                    String rightTermKey = (String) right.get("authorizationTermKey");
                    if (UtilValidate.isEmpty(leftTermKey) || UtilValidate.isEmpty(rightTermKey))
                        return UtilValidate.areEqual(left.get("agreementTermId"), right.get("agreementTermId"));
                    else
                        return UtilValidate.areEqual(leftTermKey, rightTermKey);

                }
            };
            diff.compare();
            for (Map<String, Object> term : diff.getLeftOnly())
                deleteAgreementTerm(term);
            for (CollectionDiff.Pair<Map<String, Object>, Map<String, Object>> termPair : diff.getBoth())
                updateAgreementTerm(termPair.getRight(), agreementId, agreementItemSeqId);
            for (Map<String, Object> term : diff.getRightOnly())
                createAgreementTerm(term, agreementId, agreementItemSeqId, authorizationItemKey);
        }
        return null;
    }

    /**
     * Delete agreement terms
     *
     * @param term
     * @return
     * @throws GenericServiceException
     * @throws GenericEntityException
     */
    public Map<String, Object> deleteAgreementTerm(Map<String, Object> term) throws GenericServiceException, GenericEntityException {
        term.put("agreementTermId", getAgreementTermId((String) term.get("agreementTermId"), (String) term.get("authorizationTermKey")));
        String agreementTermId = (String) term.get("agreementTermId");

        deleteAgreementTermAssoc(term);
        deleteGnAuthorizationTerm(agreementTermId);

        String srvName = "deleteAgreementTerm";
        Map<String, Object> srvRequest = UtilMisc.toMap("userLogin", (Object) userLogin, "agreementTermId", agreementTermId);
        srvRequest = dispatcher.getDispatchContext().makeValidContext(srvName, "IN", srvRequest);
        return dispatcher.runSync(srvName, srvRequest);
    }

    private String createAgreementTerm(Map<String, Object> term, String agreementId, String agreementItemSeqId, String authorizationItemKey) throws GenericServiceException, GenericEntityException {
        GenericValue agreementTerm = delegator.makeValue("AgreementTerm");
        String agreementTermId = delegator.getNextSeqId("AgreementTerm");
        if (term.get("termDays") != null) term.put("termDays", Long.parseLong(term.get("termDays").toString()));
        agreementTerm.setNonPKFields(term);
        agreementTerm.set("agreementId", agreementId);
        agreementTerm.set("agreementItemSeqId", agreementItemSeqId);
        agreementTerm.set("agreementTermId", agreementTermId);

        agreementTerm.create();

        createGnAuthorizationTerm(agreementTermId, term, authorizationItemKey);
        createAgreementTermAssoc(agreementTermId, term);

        return agreementTermId;
    }

    private GenericValue createGnAuthorizationTerm(String agreementTermId, Map<String, Object> term, String authorizationItemKey) throws GenericEntityException {
        String oldAuthorizationTermKey = (String) term.get("authorizationTermKey");
        String authorizationTermKey = createAuthorizationTermKey(authorizationItemKey, oldAuthorizationTermKey);
        GenericValue authTerm = delegator.makeValue("GnAuthorizationTerm", "agreementTermId", agreementTermId);
        term.remove("lastModifiedDate");
        authTerm.setNonPKFields(term);
        authTerm.set("authorizationTermKey", authorizationTermKey);
        authTerm.set("lastModifiedByUserLogin", userLogin.get("userLoginId"));
        authTerm.set("lastModifiedDate", UtilDateTime.nowTimestamp());
        return delegator.create(authTerm);
    }


    /**
     * Create new authorizationTermKey and check that not exist already.
     *
     * @param authorizationItemKey
     * @param oldAuthorizationTermKey
     * @return authorizationTermKey value
     * @throws GenericEntityException
     */
    private String createAuthorizationTermKey(String authorizationItemKey, String oldAuthorizationTermKey) throws GenericEntityException {
        String uuid = UUID.randomUUID().toString();
        if (UtilValidate.isNotEmpty(oldAuthorizationTermKey)) {
            uuid = oldAuthorizationTermKey.substring(oldAuthorizationTermKey.lastIndexOf('#') + 1);
        }
        String termKey = String.format("%s#%s", authorizationItemKey, uuid);
        if (delegator.findByAnd("GnAuthorizationTerm", UtilMisc.toMap("authorizationTermKey", termKey)).size() > 0) {
            Debug.logWarning("An authorizationTerm with key[" + termKey + "] already exist.", module);
            return createAuthorizationTermKey(authorizationItemKey, null);
        } else
            return termKey;
    }

    /**
     * @param agreementTermId
     * @param term
     * @throws GenericEntityException
     * @throws GenericServiceException
     */
    private void createAgreementTermAssoc(String agreementTermId, Map<String, Object> term) throws GenericEntityException, GenericServiceException {
        String termTypeId = (String) term.get("termTypeId");
        if (AgreementTermTypeOfbiz.GN_CNS_PACKAGING.is(termTypeId))
            termAssocHelper.createPackagingTerm(agreementTermId, UtilGenerics.<String>checkList(term.get("packagingIds")));
        else if (AgreementTermTypeOfbiz.GN_CNS_WASTE.is(termTypeId))
            termAssocHelper.createWasteTerm(agreementTermId, UtilGenerics.<String>checkList(term.get("wasteCerCodeIds")));
        else if (AgreementTermTypeOfbiz.GN_CNS_OPERATION.is(termTypeId))
            termAssocHelper.createOperationTerm(agreementTermId, UtilGenerics.<String>checkList(term.get("operationIds")));
        else if (AgreementTermTypeOfbiz.GN_CNS_VEHICLE_REG_N.is(termTypeId)) {
            Set<String> registrationNumberIdsTrimmed = new HashSet<String>();
            for (String registrationNumber : UtilGenerics.<String>checkList(term.get("registrationNumberIds"))) {
                String registrationNumberTrimmed = registrationNumber.replaceAll(" ", "");
                registrationNumberIdsTrimmed.add(registrationNumberTrimmed);
            }
            List<String> registrationNumberIdsTrimmedList = new ArrayList<String>(registrationNumberIdsTrimmed);
            termAssocHelper.createRegistrationLicenseTerm(agreementTermId, termTypeId, registrationNumberIdsTrimmedList);
        } else if (AgreementTermTypeOfbiz.GN_CNS_SUBJECT_KIND.is(termTypeId))
            termAssocHelper.createSubjectTypeAgreementTerm(agreementTermId, UtilGenerics.<String>checkList(term.get("subjectTypeIds")));
        else if (AgreementTermTypeOfbiz.GN_CNS_SUBJ_COMPANY.is(termTypeId))
            termAssocHelper.createSubjectCompanyAgreementTerm(agreementTermId, UtilGenerics.<String>checkList(term.get("partyNodeIds")), UtilGenerics.<String>checkList(term.get("partyNodeKeys")));
        else if (AgreementTermTypeOfbiz.GN_CNS_HOLDER.is(termTypeId)) {
            Set<String> registrationNumberIdsTrimmed = new HashSet<String>();
            for (String registrationNumber : UtilGenerics.<String>checkList(term.get("partyNodeTaxIdentificationNumbers"))) {
                String registrationNumberTrimmed = registrationNumber.replaceAll(" ", "");
                registrationNumberIdsTrimmed.add(registrationNumberTrimmed);
            }
            List<String> registrationNumberIdsTrimmedList = new ArrayList<String>(registrationNumberIdsTrimmed);
            termAssocHelper.createRegistrationLicenseTerm(agreementTermId, termTypeId, registrationNumberIdsTrimmedList);
            termAssocHelper.createGeoAgreementTerm(agreementTermId, UtilGenerics.<String>checkList(term.get("geoIds")));
        } else if (AgreementTermTypeOfbiz.GN_CNS_SUBJ_ROLE.is(termTypeId))
            termAssocHelper.createSubjectTypeAgreementTerm(agreementTermId, UtilGenerics.<String>checkList(term.get("subjectRoleIds")));
        else if (AgreementTermTypeOfbiz.GN_CNS_AUTH_TYPE.is(termTypeId))
            termAssocHelper.createSubjectTypeAgreementTerm(agreementTermId, UtilGenerics.<String>checkList(term.get("authorizationTypeIds")));
        else if (AgreementTermTypeOfbiz.GN_CNS_CAT_CLASS.is(termTypeId))
            termAssocHelper.createCategoryClassEnumTerm(agreementTermId, UtilGenerics.<String>checkList(term.get("categoryClassEnumIds")));


    }

    private void updateAgreementTerm(Map<String, Object> term, String agreementId, String agreementItemSeqId) throws GenericServiceException, GenericEntityException {
        term.put("agreementTermId", getAgreementTermId((String) term.get("agreementTermId"), (String) term.get("authorizationTermKey")));
        term.put("agreementId", agreementId);
        term.put("agreementItemSeqId", agreementItemSeqId);

        Map<String, Object> srvRequest = UtilMisc.toMap("userLogin", (Object) userLogin);
        srvRequest.putAll(dispatcher.getDispatchContext().makeValidContext("updateAgreementTerm", "IN", term));
        dispatcher.runSync("updateAgreementTerm", srvRequest);

        updateGnAuthorizationTerm(term);
        updateAgreementTermAssoc(term);
    }

    private void updateGnAuthorizationTerm(Map<String, Object> term) throws GenericEntityException {
        GenericValue gnAuthorizationTerm = findAuthorizationTermById((String) term.get("agreementTermId"));
        term.remove("lastModifiedDate");
        gnAuthorizationTerm.setNonPKFields(term, true);
        gnAuthorizationTerm.set("lastModifiedByUserLogin", userLogin.get("userLoginId"));
        gnAuthorizationTerm.set("lastModifiedDate", UtilDateTime.nowTimestamp());
        delegator.store(gnAuthorizationTerm);
    }


    /**
     * @param term
     * @throws GenericEntityException
     * @throws GenericServiceException
     */
    private void updateAgreementTermAssoc(Map<String, Object> term) throws GenericEntityException, GenericServiceException {
        String agreementTermId = (String) term.get("agreementTermId");
        String termTypeId = (String) term.get("termTypeId");
        if (AgreementTermTypeOfbiz.GN_CNS_PACKAGING.is(termTypeId))
            termAssocHelper.updatePackagingTerm(agreementTermId, UtilGenerics.<String>checkList(term.get("packagingIds")));
        else if (AgreementTermTypeOfbiz.GN_CNS_WASTE.is(termTypeId))
            termAssocHelper.updateWasteTerm(agreementTermId, UtilGenerics.<String>checkList(term.get("wasteCerCodeIds")));
        else if (AgreementTermTypeOfbiz.GN_CNS_OPERATION.is(termTypeId))
            termAssocHelper.updateOperationTerm(agreementTermId, UtilGenerics.<String>checkList(term.get("operationIds")));
        else if (AgreementTermTypeOfbiz.GN_CNS_VEHICLE_REG_N.is(termTypeId))
            termAssocHelper.updateRegistrationLicenseTerm(agreementTermId, termTypeId, UtilGenerics.<String>checkList(term.get("registrationNumberIds")));
        else if (AgreementTermTypeOfbiz.GN_CNS_SUBJECT_KIND.is(termTypeId))
            termAssocHelper.updateSubjectTypeAgreementTerm(agreementTermId, UtilGenerics.<String>checkList(term.get("subjectTypeIds")));
        else if (AgreementTermTypeOfbiz.GN_CNS_SUBJ_COMPANY.is(termTypeId))
            termAssocHelper.updateSubjectCompanyAgreementTerm(agreementTermId, UtilGenerics.<String>checkList(term.get("partyNodeIds")), UtilGenerics.<String>checkList(term.get("partyNodeKeys")));
            //filter
        else if (AgreementTermTypeOfbiz.GN_CNS_HOLDER.is(termTypeId)) {
            termAssocHelper.updateRegistrationLicenseTerm(agreementTermId, termTypeId, UtilGenerics.<String>checkList(term.get("partyNodeTaxIdentificationNumbers")));
            termAssocHelper.updateGeoAgreementTerm(agreementTermId, UtilGenerics.<String>checkList(term.get("geoIds")));
        } else if (AgreementTermTypeOfbiz.GN_CNS_SUBJ_ROLE.is(termTypeId))
            termAssocHelper.updateSubjectTypeAgreementTerm(agreementTermId, UtilGenerics.<String>checkList(term.get("subjectRoleIds")));
        else if (AgreementTermTypeOfbiz.GN_CNS_AUTH_TYPE.is(termTypeId))
            termAssocHelper.updateSubjectTypeAgreementTerm(agreementTermId, UtilGenerics.<String>checkList(term.get("authorizationTypeIds")));
        else if (AgreementTermTypeOfbiz.GN_CNS_CAT_CLASS.is(termTypeId))
            termAssocHelper.updateCategoryClassEnumAgreementTerm(agreementTermId, UtilGenerics.<String>checkList(term.get("categoryClassEnumIds")));

    }

    private void deleteGnAuthorizationTerm(String agreementTermId) throws GenericEntityException {
        if (UtilValidate.isNotEmpty(agreementTermId)) {
            GenericValue term = delegator.findOne("GnAuthorizationTerm", false, "agreementTermId", agreementTermId);
            delegator.removeValue(term);
        }
    }

    /**
     * @param term
     * @throws GenericEntityException
     * @throws GenericServiceException
     */
    private void deleteAgreementTermAssoc(Map<String, Object> term) throws GenericEntityException, GenericServiceException {
        String agreementTermId = (String) term.get("agreementTermId");
        String termTypeId = (String) term.get("termTypeId");
        if (AgreementTermTypeOfbiz.GN_CNS_PACKAGING.is(termTypeId))
            termAssocHelper.updatePackagingTerm(agreementTermId, null);
        else if (AgreementTermTypeOfbiz.GN_CNS_WASTE.is(termTypeId))
            termAssocHelper.updateWasteTerm(agreementTermId, null);
        else if (AgreementTermTypeOfbiz.GN_CNS_OPERATION.is(termTypeId))
            termAssocHelper.updateOperationTerm(agreementTermId, null);
        else if (AgreementTermTypeOfbiz.GN_CNS_VEHICLE_REG_N.is(termTypeId))
            termAssocHelper.updateRegistrationLicenseTerm(agreementTermId, termTypeId, null);
        else if (AgreementTermTypeOfbiz.GN_CNS_SUBJECT_KIND.is(termTypeId))
            termAssocHelper.updateSubjectTypeAgreementTerm(agreementTermId, null);
        else if (AgreementTermTypeOfbiz.GN_CNS_SUBJ_COMPANY.is(termTypeId))
            termAssocHelper.updateSubjectCompanyAgreementTerm(agreementTermId, null, null);
            //filter
        else if (AgreementTermTypeOfbiz.GN_CNS_HOLDER.is(termTypeId)) {
            termAssocHelper.updateRegistrationLicenseTerm(agreementTermId, termTypeId, null);
            termAssocHelper.updateGeoAgreementTerm(agreementTermId, null);
        } else if (AgreementTermTypeOfbiz.GN_CNS_SUBJ_ROLE.is(termTypeId))
            termAssocHelper.updateSubjectTypeAgreementTerm(agreementTermId, null);
        else if (AgreementTermTypeOfbiz.GN_CNS_AUTH_TYPE.is(termTypeId))
            termAssocHelper.updateSubjectTypeAgreementTerm(agreementTermId, null);
        else if (AgreementTermTypeOfbiz.GN_CNS_CAT_CLASS.is(termTypeId))
            termAssocHelper.updateCategoryClassEnumAgreementTerm(agreementTermId, null);
    }

    /**
     * Find terms by  {@code agreementId} and {@code agreementItemSeqId}
     * Method load collections of term additional infos also.
     *
     * @param agreementId
     * @param agreementItemSeqId
     * @return
     * @throws GenericEntityException
     */
    public List<Map<String, Object>> findAuthorizationAndAgreementTerms(String agreementId, String agreementItemSeqId, boolean loadInfoHolderConstraint) throws GenericEntityException, GenericServiceException {
        List<EntityCondition> conds = FastList.newInstance();
        if (UtilValidate.isNotEmpty(agreementId))
            conds.add(EntityCondition.makeCondition("agreementId", agreementId));
        if (UtilValidate.isNotEmpty(agreementItemSeqId))
            conds.add(EntityCondition.makeCondition("agreementItemSeqId", agreementItemSeqId));

        List<GenericValue> terms = delegator.findList("GnAuthorizationAndAgreementTerm", EntityCondition.makeCondition(conds), null, UtilMisc.toList("agreementId", "agreementItemSeqId", "agreementTermId"), null, false);
        List<Map<String, Object>> result = FastList.newInstance();
        for (GenericValue _term : terms) {
            Map<String, Object> term = UtilMisc.makeMapWritable(_term);
            result.add(term);
            String termTypeId = (String) term.get("termTypeId");
            if (AgreementTermTypeOfbiz.GN_CNS_PACKAGING.is(termTypeId))
                term.put("packagings", termAssocHelper.findPackagings(_term.getString("agreementTermId")));
            if (AgreementTermTypeOfbiz.GN_CNS_OPERATION.is(termTypeId))
                term.put("operations", termAssocHelper.findOperations(_term.getString("agreementTermId")));
            if (AgreementTermTypeOfbiz.GN_CNS_WASTE.is(termTypeId))
                term.put("wasteCerCodes", termAssocHelper.findWasteCerCodes(_term.getString("agreementTermId")));
            if (AgreementTermTypeOfbiz.GN_CNS_VEHICLE_REG_N.is(termTypeId))
                term.put("registrationNumbers", termAssocHelper.findRegistrationNumbers(_term.getString("agreementTermId"), termTypeId));
            if (AgreementTermTypeOfbiz.GN_CNS_SUBJECT_KIND.is(termTypeId))
                term.put("subjectTypes", termAssocHelper.findSubjectTypes(_term.getString("agreementTermId")));
            if (AgreementTermTypeOfbiz.GN_CNS_SUBJ_COMPANY.is(termTypeId))
                term.put("partyNodes", termAssocHelper.findSubjectPartyNodes(_term.getString("agreementTermId")));
            //filter
            if (AgreementTermTypeOfbiz.GN_CNS_HOLDER.is(termTypeId)) {
                term.put("partyNodes", termAssocHelper.findHolderPartyNodes(_term.getString("agreementTermId"), termTypeId, loadInfoHolderConstraint));
                term.put("geos", termAssocHelper.findGeos(_term.getString("agreementTermId")));
            }
            if (AgreementTermTypeOfbiz.GN_CNS_SUBJ_ROLE.is(termTypeId))
                term.put("subjectRoles", termAssocHelper.findSubjectTypes(_term.getString("agreementTermId")));
            if (AgreementTermTypeOfbiz.GN_CNS_AUTH_TYPE.is(termTypeId))
                term.put("authorizationTypes", termAssocHelper.findSubjectTypes(_term.getString("agreementTermId")));
            //slicingFilter
            if (AgreementTermTypeOfbiz.GN_CNS_CAT_CLASS.is(termTypeId))
                term.put("categoryClassEnums", termAssocHelper.findCategoryClassEnumIds(_term.getString("agreementTermId")));

            //  if (AgreementTermTypeOfbiz.GN_CNS_WASTE_ENTRY.is(term.get("termTypeId"))) ;
            // if (AgreementTermTypeOfbiz.GN_CNS_WASTE_STOCK.is(term.get("termTypeId"))) ;
            // if (AgreementTermTypeOfbiz.GN_CNS_SUBJECT_KIND.is(term.get("termTypeId"))) ;

        }
        return result;
    }

    private void checkTermTypeUnivocity(List<Map<String, Object>> agreementTerms) throws GnServiceException {
        if (agreementTerms != null) {
            Set<String> termTypes = FastSet.newInstance();
            for (Map<String, Object> term : agreementTerms) {
                String termTypeId = (String) term.get("termTypeId");
                if (AgreementTermTypeOfbiz.valueOf(termTypeId).isOneForItem() && termTypes.contains(termTypeId))
                    throw new GnServiceException("There are two or more terms with the same type");
                else termTypes.add(termTypeId);
            }
        }
    }

    /**
     * @param agreementTermId primary key
     * @return
     * @throws GenericEntityException
     */
    public GenericValue findAgreementTermById(String agreementTermId) throws GenericEntityException {
        return delegator.findOne("AgreementTerm", UtilMisc.toMap("agreementTermId", (Object) agreementTermId), false);
    }

    /**
     * @param agreementTermId primary key
     * @return
     * @throws GenericEntityException
     */
    public GenericValue findAuthorizationTermById(String agreementTermId) throws GenericEntityException {
        return delegator.findOne("GnAuthorizationTerm", false, "agreementTermId", agreementTermId);
    }

    private String getAgreementTermId(String agreementTermId, String authorizationTermKey) throws GenericEntityException, GnServiceException {
        if (UtilValidate.isEmpty(agreementTermId) && UtilValidate.isNotEmpty(authorizationTermKey)) {
            List<GenericValue> terms = delegator.findList("GnAuthorizationTerm",
                    EntityCondition.makeCondition("authorizationTermKey", authorizationTermKey), UtilMisc.toSet("agreementTermId"), null, null, false);
            if (terms.size() == 0)
                throw new GnServiceException(OfbizErrors.AUTHORIZATION_TERM_NOT_FOUND, "Term[" + authorizationTermKey + "] not found.");
            if (terms.size() > 1)
                throw new GnServiceException(OfbizErrors.ENTITY_EXCEPTION, "More than one Term[" + authorizationTermKey + "] found.");
            return terms.get(0).getString("agreementTermId");
        } else {
            if (UtilValidate.isEmpty(delegator.findOne("GnAuthorizationTerm", false, "agreementTermId", agreementTermId)))
                throw new GnServiceException(OfbizErrors.AUTHORIZATION_TERM_NOT_FOUND, "Term[" + agreementTermId + "] not found.");
            else return agreementTermId;
        }
    }

}
