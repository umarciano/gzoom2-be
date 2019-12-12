package it.memelabs.gn.services.authorization;

import it.memelabs.gn.services.OfbizErrors;
import it.memelabs.gn.services.node.PartyNodeTypeOfbiz;
import it.memelabs.gn.util.CollectionDiff;
import it.memelabs.gn.util.GnServiceException;
import javolution.util.FastList;
import org.ofbiz.base.util.UtilGenerics;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.service.DispatchContext;
import org.ofbiz.service.GenericServiceException;

import java.util.*;

/**
 * 25/03/13
 *
 * @author Andrea Fossi
 */
public class TermAssocHelper extends CommonAuthorizationHelper {
    public TermAssocHelper(DispatchContext dctx, Map<String, ? extends Object> context) {
        super(dctx, context);
        authorizationPartyNodeHelper = new AuthorizationPartyNodeHelper(dctx, context);
    }

    private final AuthorizationPartyNodeHelper authorizationPartyNodeHelper;
    public final static String module = TermAssocHelper.class.getName();

    /**
     * Create association entity between {@code AgreementTerm} and {@code GnPackaging} entity
     *
     * @param agreementTermId
     * @param packagingIds
     * @throws GenericEntityException
     */
    public void createPackagingTerm(String agreementTermId, List<String> packagingIds) throws GenericEntityException {
        if (UtilValidate.isNotEmpty(packagingIds)) {
            for (String packagingId : packagingIds) {
                GenericValue gv = delegator.makeValue("GnPackagingAgreementTermAssoc", UtilMisc.toMap("agreementTermId", agreementTermId, "packagingId", packagingId));
                delegator.create(gv);
            }
        }
    }

    /**
     * Create or delete association entity between {@code AgreementTerm} and {@code GnPackaging} entity
     *
     * @param agreementTermId
     * @param packagingIds
     * @throws GenericEntityException
     */
    public void updatePackagingTerm(String agreementTermId, List<String> packagingIds) throws GenericEntityException {
        List<GenericValue> listOld = delegator.findByAnd("GnPackagingAgreementTermAssoc", "agreementTermId", agreementTermId);
        List<String> listToCreate = deleteOldAndGetNew("packagingId", listOld, packagingIds);
        createPackagingTerm(agreementTermId, listToCreate);
    }

    /**
     * Create association entity between {@code AgreementTerm} and {@code GnWasteCerCode} entity
     *
     * @param agreementTermId
     * @param wasteCerCodeIds
     * @throws GenericEntityException
     */
    public void createWasteTerm(String agreementTermId, List<String> wasteCerCodeIds) throws GenericEntityException {
        if (UtilValidate.isNotEmpty(wasteCerCodeIds)) {
            for (String wasteCerCodeId : wasteCerCodeIds) {
                GenericValue gv = delegator.makeValue("GnWasteCerCodeAgreementTermAssoc", UtilMisc.toMap("agreementTermId", agreementTermId, "wasteCerCodeId", wasteCerCodeId));
                delegator.create(gv);
            }
        }
    }

    /**
     * Create or delete association entity between {@code AgreementTerm} and {@code GnWasteCerCode} entity
     *
     * @param agreementTermId
     * @param wasteCerCodeIds
     * @throws GenericEntityException
     */
    public void updateWasteTerm(String agreementTermId, List<String> wasteCerCodeIds) throws GenericEntityException {
        List<GenericValue> listOld = delegator.findByAnd("GnWasteCerCodeAgreementTermAssoc", "agreementTermId", agreementTermId);
        List<String> listToCreate = deleteOldAndGetNew("wasteCerCodeId", listOld, wasteCerCodeIds);
        createWasteTerm(agreementTermId, listToCreate);
    }

    /**
     * Create association entity between {@code AgreementTerm} and {@code GnOperation} entity
     *
     * @param agreementTermId
     * @param operationIds
     * @throws GenericEntityException
     */
    public void createOperationTerm(String agreementTermId, List<String> operationIds) throws GenericEntityException {
        if (UtilValidate.isNotEmpty(operationIds)) {
            for (String operationId : operationIds) {
                GenericValue gv = delegator.makeValue("GnOperationAgreementTermAssoc", UtilMisc.toMap("agreementTermId", agreementTermId, "operationId", operationId));
                delegator.create(gv);
            }
        }
    }

    /**
     * Create or delete association entity between {@code AgreementTerm} and {@code GnOperation} entity
     *
     * @param agreementTermId
     * @param operationIds
     * @throws GenericEntityException
     */
    public void updateOperationTerm(String agreementTermId, List<String> operationIds) throws GenericEntityException {
        List<GenericValue> listOld = delegator.findByAnd("GnOperationAgreementTermAssoc", "agreementTermId", agreementTermId);
        List<String> listToCreate = deleteOldAndGetNew("operationId", listOld, operationIds);
        createOperationTerm(agreementTermId, listToCreate);
    }

    /**
     * Create association entity between {@code AgreementTerm} and {@code Enumeration} entity typed GN_SUBJECT_TYPE
     * Used in constraints:
     * <ul><li>{@link AgreementTermTypeOfbiz#GN_CNS_SUBJ_ROLE}</li></ul>
     * <ul><li>{@link AgreementTermTypeOfbiz#GN_CNS_SUBJECT_KIND}</li></ul>
     * <ul><li>{@link AgreementTermTypeOfbiz#GN_CNS_AUTH_TYPE}</li></ul>
     *
     * @param agreementTermId
     * @param subjectTypeIds
     * @throws GenericEntityException
     */
    public void createSubjectTypeAgreementTerm(String agreementTermId, List<String> subjectTypeIds) throws GenericEntityException {
        if (UtilValidate.isNotEmpty(subjectTypeIds)) {
            for (String subjectTypeId : subjectTypeIds) {
                GenericValue gv = delegator.makeValue("GnSubjectTypeAgreementTermAssoc", UtilMisc.toMap("agreementTermId", agreementTermId, "subjectTypeId", subjectTypeId));
                delegator.create(gv);
            }
        }
    }

    /**
     * Create or update association entity between {@code AgreementTerm} and {@code Enumeration} entity typed GN_SUBJECT_TYPE
     *
     * @param agreementTermId
     * @param subjectTypeIds
     * @throws GenericEntityException
     */
    public void updateSubjectTypeAgreementTerm(String agreementTermId, List<String> subjectTypeIds) throws GenericEntityException {
        List<GenericValue> listOld = delegator.findByAnd("GnSubjectTypeAgreementTermAssoc", "agreementTermId", agreementTermId);
        List<String> listToCreate = deleteOldAndGetNew("subjectTypeId", listOld, subjectTypeIds);
        createSubjectTypeAgreementTerm(agreementTermId, listToCreate);
    }


    public void createSubjectCompanyAgreementTerm(String agreementTermId, List<String> partyNodeIds, List<String> partyNodeKeys) throws GenericEntityException, GenericServiceException {
        //if partyNodeKeys is not isNotEmpty override partyIds
        if (UtilValidate.isNotEmpty(partyNodeKeys)) {
            partyNodeIds = FastList.newInstance();
            for (String partyKey : partyNodeKeys) {
                String partyId = getPartyId(null, partyKey);
                if (UtilValidate.isNotEmpty(partyId)) partyNodeIds.add(partyId);
            }
        }
        createSubjectCompanyAgreementTerm(agreementTermId, partyNodeIds);
    }

    /**
     * Create association entity between {@code AgreementTerm} and {@code GnPartyNode} entity
     *
     * @param agreementTermId
     * @param partyNodeIds
     * @throws GenericEntityException
     */
    private void createSubjectCompanyAgreementTerm(String agreementTermId, List<String> partyNodeIds) throws GenericEntityException, GenericServiceException {
        if (UtilValidate.isNotEmpty(partyNodeIds)) {
            for (String partyId : partyNodeIds) {
                if (!gnPartyRoleCheck(partyId, "GN_COMPANY") && !gnPartyRoleCheck(partyId, "GN_PVT_COMPANY"))
                    throw new GnServiceException(OfbizErrors.ENTITY_EXCEPTION, "partyNode[" + partyId + "] is not a Company or PrivateCompany");
                GenericValue gv = delegator.makeValue("GnSubjectPartyNodeAgreementTermAssoc", UtilMisc.toMap("agreementTermId", agreementTermId, "partyId", partyId));
                delegator.create(gv);
            }
        }
    }

    /**
     * Create or delete association entity between {@code AgreementTerm} and {@code GnPartyNode} entity
     *
     * @param agreementTermId
     * @param partyNodeIds
     * @throws GenericEntityException
     */
    public void updateSubjectCompanyAgreementTerm(String agreementTermId, List<String> partyNodeIds, List<String> partyNodeKeys) throws GenericEntityException, GenericServiceException {
        //if partyNodeKeys is not isNotEmpty override partyIds
        if (UtilValidate.isNotEmpty(partyNodeKeys)) {
            partyNodeIds = FastList.newInstance();
            for (String partyKey : partyNodeKeys) {
                String partyId = getPartyId(null, partyKey);
                if (UtilValidate.isNotEmpty(partyId)) partyNodeIds.add(partyId);
            }
        }
        updateSubjectCompanyAgreementTerm(agreementTermId, partyNodeIds);
    }

    /**
     * Create or delete association entity between {@code AgreementTerm} and {@code GnPartyNode} entity
     * Used in {@link AgreementTermTypeOfbiz#GN_CNS_SUBJ_COMPANY} and {@link AgreementTermTypeOfbiz#GN_CNS_HOLDER}
     *
     * @param agreementTermId
     * @param partyNodeIds
     * @throws GenericEntityException
     */
    private void updateSubjectCompanyAgreementTerm(String agreementTermId, List<String> partyNodeIds) throws GenericEntityException, GenericServiceException {
        List<GenericValue> listOld = delegator.findByAnd("GnSubjectPartyNodeAgreementTermAssoc", "agreementTermId", agreementTermId);
        List<String> listToCreate = deleteOldAndGetNew("partyId", listOld, partyNodeIds);
        createSubjectCompanyAgreementTerm(agreementTermId, listToCreate);
    }

    /**
     * Create a GnStringValueAgreementTerm for each RegistrationNumber of AgreementTerm
     *
     * @param agreementTermId
     * @param registrationNumberIds
     * @throws GenericEntityException
     */
    public void createRegistrationLicenseTerm(String agreementTermId, String termTypeId, List<String> registrationNumberIds) throws GenericEntityException {
        if (UtilValidate.isNotEmpty(registrationNumberIds)) {
            Set<String> registrationNumberIdsTrimmed = new HashSet<String>();
            for (String registrationNumber : registrationNumberIds) {
                String registrationNumberTrimmed = registrationNumber.replaceAll(" ", "");
                registrationNumberIdsTrimmed.add(registrationNumberTrimmed);
            }
            for (String registrationNumberTrimmed : registrationNumberIdsTrimmed) {
                GenericValue gv = delegator.makeValue("GnRegistrationNumberAgreementTerm",
                        UtilMisc.toMap("agreementTermId", agreementTermId, "registrationNumber", registrationNumberTrimmed, "termTypeId", termTypeId));
                delegator.create(gv);
            }
        }
    }

    /**
     * Create association entity between {@code AgreementTerm} and {@code Geo} entity
     * Used in {@link AgreementTermTypeOfbiz#GN_CNS_HOLDER}
     *
     * @param agreementTermId
     * @param geoIds
     * @throws GenericEntityException
     */
    public void createGeoAgreementTerm(String agreementTermId, List<String> geoIds) throws GenericEntityException, GenericServiceException {
        if (UtilValidate.isNotEmpty(geoIds)) {
            for (String geoId : geoIds) {
                GenericValue gv = delegator.makeValue("GnGeoAgreementTermAssoc", UtilMisc.toMap("agreementTermId", agreementTermId,
                        "geoId", geoId));
                delegator.create(gv);
            }
        }
    }

    /**
     * Create or delete association entity between {@code AgreementTerm} and {@code Geo} entity
     * <p/>
     * Used in {@link AgreementTermTypeOfbiz#GN_CNS_SUBJ_COMPANY} and {@link AgreementTermTypeOfbiz#GN_CNS_HOLDER}
     *
     * @param agreementTermId
     * @param geoIds
     * @throws GenericEntityException
     */
    public void updateGeoAgreementTerm(String agreementTermId, List<String> geoIds) throws GenericEntityException, GenericServiceException {
        List<GenericValue> listOld = delegator.findByAnd("GnGeoAgreementTermAssoc", "agreementTermId", agreementTermId);
        List<String> listToCreate = deleteOldAndGetNew("geoId", listOld, geoIds);
        createGeoAgreementTerm(agreementTermId, listToCreate);
    }

    /**
     * Create or delete a GnRegistrationNumberAgreementTerm for each RegistrationNumber of AgreementTerm
     *
     * @param agreementTermId
     * @param registrationNumberIds
     * @throws GenericEntityException
     */
    public void updateRegistrationLicenseTerm(String agreementTermId, String termTypeId, List<String> registrationNumberIds) throws GenericEntityException {
        List<GenericValue> listOld = delegator.findByAnd("GnRegistrationNumberAgreementTerm", "agreementTermId", agreementTermId, "termTypeId", termTypeId);
        Set<String> registrationNumberIdsTrimmed = new HashSet<String>();
        List<String> listToCreate;
        if (registrationNumberIds != null) {
            for (String registrationNumber : registrationNumberIds) {
                String registrationNumberTrimmed = registrationNumber.replaceAll(" ", "");
                registrationNumberIdsTrimmed.add(registrationNumberTrimmed);
            }
            List<String> registrationNumberIdsTrimmedList = new ArrayList<String>(registrationNumberIdsTrimmed);
            listToCreate = deleteOldAndGetNew("registrationNumber", listOld, registrationNumberIdsTrimmedList);
        } else { //delete
            listToCreate = deleteOldAndGetNew("registrationNumber", listOld, null);
        }
        createRegistrationLicenseTerm(agreementTermId, termTypeId, listToCreate);
    }

    /**
     * Create association entity between {@code AgreementTerm} and {@code Enumeration} entity typed GnCategoryClassEnum
     * Used in constraints:
     * <ul><li>{@link AgreementTermTypeOfbiz#GN_CNS_CAT_CLASS}</li></ul>
     *
     * @param agreementTermId
     * @param categoryClassEnumIds
     * @throws GenericEntityException
     */
    public void createCategoryClassEnumTerm(String agreementTermId, List<String> categoryClassEnumIds) throws GenericEntityException {
        if (UtilValidate.isNotEmpty(categoryClassEnumIds)) {
            for (String categoryClassEnumId : categoryClassEnumIds) {
                GenericValue gv = delegator.makeValue("GnCategoryClassEnumTermAssoc", UtilMisc.toMap("agreementTermId", agreementTermId, "categoryClassEnumId", categoryClassEnumId));
                delegator.create(gv);
            }
        }
    }

    /**
     * Create or delete association entity between {@code AgreementTerm} and {@code GnCategoryClassEnum} entity
     * <p/>
     * Used in {@link AgreementTermTypeOfbiz#GN_CNS_SUBJ_COMPANY} and {@link AgreementTermTypeOfbiz#GN_CNS_CAT_CLASS}
     *
     * @param agreementTermId
     * @param categoryClassEnumIds
     * @throws GenericEntityException
     */
    public void updateCategoryClassEnumAgreementTerm(String agreementTermId, List<String> categoryClassEnumIds) throws GenericEntityException, GenericServiceException {
        List<GenericValue> listOld = delegator.findByAnd("GnCategoryClassEnumTermAssoc", "agreementTermId", agreementTermId);
        List<String> listToCreate = deleteOldAndGetNew("categoryClassEnumId", listOld, categoryClassEnumIds);
        createCategoryClassEnumTerm(agreementTermId, listToCreate);
    }

    /**
     * Find {@code GnCategoryClassEnum} entities associates to the {@code AgreementTerm}
     *
     * @param agreementTermId
     * @return
     * @throws GenericEntityException
     */
    public List<Map<String, Object>> findCategoryClassEnumIds(String agreementTermId) throws GenericEntityException {
        List<GenericValue> categoryClassEnumIds = delegator.findByAnd("GnCategoryClassEnumTermAssoc", "agreementTermId", agreementTermId);
        ArrayList<Map<String, Object>> result = new ArrayList<Map<String, Object>>(categoryClassEnumIds.size());
        for (GenericValue _categoryClassEnumId : categoryClassEnumIds) {
            Map<String, Object> cat = UtilMisc.makeMapWritable(_categoryClassEnumId);
            cat.remove("agreementTermId");
            result.add(cat);
        }
        return result;
    }


    /**
     * Find {@code GnPackaging} entities associates to the {@code AgreementTerm}
     *
     * @param agreementTermId
     * @return
     * @throws GenericEntityException
     */
    public List<Map<String, Object>> findPackagings(String agreementTermId) throws GenericEntityException {
        List<GenericValue> pkgs = delegator.findByAnd("GnPackagingAgreementTerm", UtilMisc.toMap("agreementTermId", agreementTermId), UtilMisc.toList("packagingId"));
        List<Map<String, Object>> result = FastList.newInstance();
        for (GenericValue _pkg : pkgs) {
            Map<String, Object> pkg = UtilMisc.makeMapWritable(_pkg);
            pkg.remove("agreementTermId");
            result.add(pkg);
        }
        return result;
    }

    /**
     * * Find {@code GnOperation} entities associates to  the {@code AgreementTerm}
     *
     * @param agreementTermId
     * @return
     * @throws GenericEntityException
     */
    public List<Map<String, Object>> findOperations(String agreementTermId) throws GenericEntityException {
        List<GenericValue> ops = delegator.findByAnd("GnOperationAgreementTerm", UtilMisc.toMap("agreementTermId", agreementTermId), UtilMisc.toList("operationId"));
        List<Map<String, Object>> result = FastList.newInstance();
        for (GenericValue _op : ops) {
            Map<String, Object> op = UtilMisc.makeMapWritable(_op);
            op.remove("agreementTermId");
            result.add(op);
        }
        return result;
    }

    /**
     * Find {@code GnWasteCerCode} entities associates to the {@code AgreementTerm}
     *
     * @param agreementTermId
     * @return
     * @throws GenericEntityException
     */
    public List<Map<String, Object>> findWasteCerCodes(String agreementTermId) throws GenericEntityException {
        List<GenericValue> wastes = delegator.findByAnd("GnWasteCerCodeAgreementTerm", UtilMisc.toMap("agreementTermId", agreementTermId), UtilMisc.toList("wasteCerCodeId"));
        List<Map<String, Object>> result = FastList.newInstance();
        for (GenericValue _waste : wastes) {
            Map<String, Object> waste = UtilMisc.makeMapWritable(_waste);
            waste.remove("agreementTermId");
            result.add(waste);
        }
        return result;
    }

    /**
     * Find {@code SubjectType} (Enumeration) entities associates to the {@code AgreementTerm}
     *
     * @param agreementTermId
     * @return
     * @throws GenericEntityException
     */
    public List<Map<String, Object>> findSubjectTypes(String agreementTermId) throws GenericEntityException {
        List<GenericValue> subjetTypes = delegator.findByAnd("GnSubjectTypeAgreementTerm", UtilMisc.toMap("agreementTermId", agreementTermId), UtilMisc.toList("enumId"));
        List<Map<String, Object>> result = FastList.newInstance();
        for (GenericValue _waste : subjetTypes) {
            Map<String, Object> subjetType = UtilMisc.makeMapWritable(_waste);
            subjetType.remove("agreementTermId");
            result.add(subjetType);
        }
        return result;
    }


    /**
     * Find {@code SubjectType} (Enumeration) entities associates to the {@code AgreementTerm}
     *
     * @param agreementTermId
     * @return
     * @throws GenericEntityException
     */
    public List<Map<String, Object>> findSubjectPartyNodes(String agreementTermId) throws GenericEntityException, GenericServiceException {
        List<GenericValue> partyNodes = delegator.findByAnd("GnSubjectPartyNodeAgreementTerm", UtilMisc.toMap("agreementTermId", agreementTermId), UtilMisc.toList("partyId"));
        List<Map<String, Object>> result = FastList.newInstance();
        for (GenericValue partyNodeAssoc : partyNodes) {
            result.add(findPartyNodeById(partyNodeAssoc.getString("partyId")));
        }
        return result;
    }

    /**
     * Find {@code HolderCompany} (String) entities associates to the {@code AgreementTerm}
     *
     * @param agreementTermId
     * @param termTypeId
     * @return
     * @throws GenericEntityException
     */
    public List<Map<String, Object>> findHolderPartyNodes(String agreementTermId, String termTypeId, boolean loadInfoHolderConstraint) throws GenericEntityException, GenericServiceException {
        List<Map<String, Object>> taxIdentificationNumbers = findRegistrationNumbers(agreementTermId, termTypeId);
        List<Map<String, Object>> result = FastList.newInstance();
        for (Map<String, Object> partyNodeAssoc : taxIdentificationNumbers) {
            Object taxIdeNumber = partyNodeAssoc.get("registrationNumber");
            if (loadInfoHolderConstraint) {
                Map<String, Object> partyNode = UtilMisc.getMapFromMap(dispatcher.runSync("gnFindCompanyNodeByTaxIdentificationNumber", UtilMisc.toMap("userLogin", userLogin, "taxIdentificationNumber", taxIdeNumber)), "partyNode");
                if (UtilValidate.isNotEmpty(partyNode))
                    result.add(partyNode);
                else
                    result.add(UtilMisc.toMap("nodeType", PartyNodeTypeOfbiz.COMPANY.name(), "taxIdentificationNumber", taxIdeNumber));
            } else {
                result.add(UtilMisc.toMap("nodeType", PartyNodeTypeOfbiz.COMPANY.name(), "taxIdentificationNumber", taxIdeNumber));
            }
        }
        return result;
    }

    /**
     * Find {@code SubjectType} (Enumeration) entities associates to the {@code AgreementTerm}
     *
     * @param agreementTermId
     * @return
     * @throws GenericEntityException
     */
    public List<Map<String, Object>> findGeos(String agreementTermId) throws GenericEntityException, GenericServiceException {
        List<GenericValue> partyNodes = delegator.findByAnd("gnGeoAgreementTerm", UtilMisc.toMap("agreementTermId", agreementTermId), UtilMisc.toList("geoId"));
        List<Map<String, Object>> result = FastList.newInstance();
        for (GenericValue _partyNodeAssoc : partyNodes) {
            Map<String, Object> partyNodeAssoc = UtilMisc.makeMapWritable(_partyNodeAssoc);
            partyNodeAssoc.remove("agreementTermId");
            result.add(partyNodeAssoc);
        }
        return result;
    }

    /**
     * Find {@code GnRegistrationNumber} entities associates to the {@code AgreementTerm}
     *
     * @param agreementTermId
     * @return
     * @throws GenericEntityException
     */
    public List<Map<String, Object>> findRegistrationNumbers(String agreementTermId, String termTypeId) throws GenericEntityException {
        List<GenericValue> registrationNumbers = delegator.findByAnd("GnRegistrationNumberAgreementTerm", "agreementTermId", agreementTermId, "termTypeId", termTypeId);
        List<Map<String, Object>> result = FastList.newInstance();
        for (GenericValue _regNumber : registrationNumbers) {
            Map<String, Object> regNumber = UtilMisc.makeMapWritable(_regNumber);
            regNumber.remove("agreementTermId");
            result.add(regNumber);
        }
        return result;
    }

    /**
     * Find party by primary key
     *
     * @param partyNodeId
     * @return
     * @throws org.ofbiz.service.GenericServiceException
     */
    private Map<String, Object> findPartyNodeById(String partyNodeId) throws GenericServiceException {
        return UtilGenerics.checkMap(dispatcher.runSync("gnInternalFindPartyNodeById", UtilMisc.toMap("partyId", partyNodeId, "userLogin", userLogin, "findRelationships", "N")).get("partyNode"));
    }


    private List<String> deleteOldAndGetNew(final String pkFieldName, List<GenericValue> listOld, List<String> listNewIds) throws GenericEntityException {
        if (UtilValidate.isEmpty(listNewIds)) {
            delegator.removeAll(listOld);
            return null;
        }
        CollectionDiff<GenericValue, String> diff = new CollectionDiff<GenericValue, String>(listOld, listNewIds) {
            @Override
            protected boolean isItemEqual(GenericValue left, String rightId) {
                String leftId = left != null ? left.getString(pkFieldName) : null;
                return leftId != null ? leftId.equals(rightId) : rightId == null;
            }
        };
        diff.setBothEnabled(false);
        diff.compare();
        delegator.removeAll(diff.getLeftOnly());
        return diff.getRightOnly();
    }

}