package it.memelabs.gn.services.authorization;

import it.intext.multifield.matcher.exception.ComparerException;
import it.intext.multifield.matcher.exception.TokenizerException;
import it.memelabs.gn.services.OfbizErrors;
import it.memelabs.gn.services.catalog.GeoTypeOfbiz;
import it.memelabs.gn.services.security.GnSecurity;
import it.memelabs.gn.services.security.PermissionsOfbiz;
import it.memelabs.gn.services.system.PropertyEnumOfbiz;
import it.memelabs.gn.util.GnServiceException;
import it.memelabs.gn.util.PropertyUtil;
import it.memelabs.gn.util.find.GnFindUtil;
import it.memelabs.gn.util.find.GnPaginatedFindParams;
import it.memelabs.greenebula.multifield.matcher.AddressMatcher;
import javolution.util.FastList;
import org.ofbiz.base.util.*;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.entity.condition.*;
import org.ofbiz.entity.model.DynamicViewEntity;
import org.ofbiz.entity.model.ModelEntity;
import org.ofbiz.entity.model.ModelKeyMap;
import org.ofbiz.entity.util.EntityFindOptions;
import org.ofbiz.entity.util.EntityListIterator;
import org.ofbiz.service.DispatchContext;
import org.ofbiz.service.GenericServiceException;
import org.ofbiz.service.ServiceUtil;

import java.sql.Timestamp;
import java.util.*;
import java.util.Map.Entry;

public class AuthorizationSearchHelper extends CommonAuthorizationHelper {
    public final static String module = AuthorizationSearchHelper.class.getName();

    private static final String ALIAS_AUTH = "AUTH";
    private static final String ALIAS_POSTAL_ADDR = "POSTAL_ADDR";
    private static final String ALIAS_PARTY_CONTACT = "PARTY_CONTACT";
    private static final String ALIAS_GEO = "GEO";
    private static final String ALIAS_OP_TERM_ASS = "OP_TRM_ASS";
    private static final String ALIAS_OP_TERM = "OP_TRM";
    private static final String ALIAS_WASTE_TERM_ASS = "WST_TRM_ASS";
    private static final String ALIAS_WASTE_TERM = "WST_TRM";
    private static final String ALIAS_PACK_TERM_ASS = "PACK_TRM_ASS";
    private static final String ALIAS_PACK_TERM = "PACK_TRM";
    private static final String ALIAS_REG_NUM = "REGN_TRM";
    private static final String ALIAS_REG_NUM_ASS = "REGN_TRM_ASS";
    private static final String ALIAS_ITEM = "ITEM";
    private static final String ALIAS_OTHER_PARTY_NODE = "OTHER_PARTY";

    private Integer vieIndex;
    private Integer viewSize;
    //TODO: see if in future can be replaced with another class
    private final GnPaginatedFindParams paginationParams;

    private static class ContextInfo {
        private Map<String, Object> partyNode;
        private List<Map<String, Object>> companyBases;
    }

    public AuthorizationSearchHelper(DispatchContext dctx, Map<String, ? extends Object> context) {
        super(dctx, context);
        paginationParams = new GnPaginatedFindParams(context);
        vieIndex = UtilMisc.toIntegerObject(context.get("viewIndex"));
        viewSize = UtilMisc.toIntegerObject(context.get("viewSize"));
        if (vieIndex == null)
            vieIndex = -1;
        if (viewSize == null)
            viewSize = -1;
    }


    public Map<String, Object> search(List<String> agreementIds, List<String> uuids, List<String> authorizationKeys,
                                      boolean onlyMine, String onlyPrivate, String statusId, String internalStatusId,
                                      String otherPartyNodeToDesc, String otherPartyNodeToTaxIdentificationNumber, Map<String, Object> originPartyNodeTo,
                                      Map<String, Object> partyIdToGeo, List<String> numbers, List<String> operationIds, CriteriaLogicOfbiz wasteLogic,
                                      List<String> wasteCerCodeIds, List<String> packagingIds, List<String> registrationNumberIds,
                                      List<String> categoryClassEnumIds, List<String> holderRoleIds, List<String> partyNodeToKeys, List<String> typeIds,
                                      List<String> issuerBaseKeys, Timestamp minimumPublishedDate, Timestamp validityDate,
                                      List<Map<String, String>> orderByList, boolean operationalDetail) throws GenericServiceException, GenericEntityException {
        return search(agreementIds, authorizationKeys, uuids, false, onlyMine, onlyPrivate, statusId, internalStatusId,
                otherPartyNodeToDesc, otherPartyNodeToTaxIdentificationNumber, originPartyNodeTo, partyIdToGeo, numbers, operationIds, wasteLogic, wasteCerCodeIds, packagingIds, registrationNumberIds,
                categoryClassEnumIds, holderRoleIds, partyNodeToKeys, typeIds, issuerBaseKeys, minimumPublishedDate, validityDate, orderByList, operationalDetail
        );
    }

    public Map<String, Object> searchAtRoot(List<String> agreementIds, List<String> uuids, List<String> authorizationKeys,
                                            boolean onlyMine, String onlyPrivate, String statusId, String internalStatusId,
                                            String otherPartyNodeToDesc, String otherPartyNodeToTaxIdentificationNumber, Map<String, Object> originPartyNodeTo,
                                            Map<String, Object> partyIdToGeo, List<String> numbers, List<String> operationIds,
                                            CriteriaLogicOfbiz wasteLogic, List<String> wasteCerCodeIds, List<String> packagingIds, List<String> registrationNumberIds,
                                            List<String> categoryClassEnumIds, List<String> holderRoleIds, List<String> partyNodeToKeys, List<String> typeIds,
                                            List<String> issuerBaseKeys, Timestamp minimumPublishedDate, Timestamp validityDate,
                                            List<Map<String, String>> orderByList, boolean operationalDetail) throws GenericServiceException, GenericEntityException {
        GnSecurity gnSecurity = new GnSecurity(delegator);
        if (!gnSecurity.hasPermission(PermissionsOfbiz.GN_AUTH_ROOT_VIEW, userLogin)) {
            throw new GnServiceException(OfbizErrors.ACCESS_DENIED, "User hasn't got GN_AUTH_ROOT_VIEW permission");
        } else {
            return search(agreementIds, authorizationKeys, uuids, true, onlyMine, onlyPrivate, statusId, internalStatusId,
                    otherPartyNodeToDesc, otherPartyNodeToTaxIdentificationNumber, originPartyNodeTo, partyIdToGeo, numbers, operationIds,
                    wasteLogic, wasteCerCodeIds, packagingIds, registrationNumberIds, categoryClassEnumIds,
                    holderRoleIds, partyNodeToKeys, typeIds, issuerBaseKeys, minimumPublishedDate, validityDate, orderByList, operationalDetail
            );
        }
    }

    private Map<String, Object> search(List<String> agreementIds, List<String> authorizationKeys, List<String> uuids,
                                       boolean searchAtRoot, boolean onlyMine, String onlyPrivate, String statusId, String internalStatusId,
                                       String otherPartyNodeToDesc, String otherPartyNodeToTaxIdentificationNumber, Map<String, Object> originPartyNodeTo,
                                       Map<String, Object> partyIdToGeo, List<String> numbers,
                                       List<String> operationIds, CriteriaLogicOfbiz wasteLogic, List<String> wasteCerCodeIds, List<String> packagingIds,
                                       List<String> registrationNumberIds, List<String> categoryClassEnumIds,
                                       List<String> holderRoleIds, List<String> partyNodeToKeys, List<String> typeIds, List<String> issuerBaseKeys,
                                       Timestamp minimumPublishedDate, Timestamp validityDate,
                                       List<Map<String, String>> orderByList, boolean loadOperationalDetail) throws GenericEntityException, GenericServiceException {
        final ModelEntity mainEntityModel = delegator.getModelEntity("GnAuthorizationAndAgreement");
        final List<String> fieldsToSelect = mainEntityModel.getAllFieldNames();

        ContextInfo contextInfo = new ContextInfo();
        initContextInfo(userLogin.getString("activeContextId"), onlyMine, contextInfo);

        DynamicViewEntity entity = new DynamicViewEntity();
        entity.addMemberEntity(ALIAS_AUTH, mainEntityModel.getEntityName());
        entity.addAliasAll(ALIAS_AUTH, null);

        List<EntityCondition> conditions = new FastList<EntityCondition>();
        String ownerNodeId = (String) contextInfo.partyNode.get("partyId");

        if (searchAtRoot) {
            conditions.add(EntityCondition.makeCondition("ownerNodeId", "GN_ROOT"));
        } else {
            conditions.add(EntityCondition.makeCondition("ownerNodeId", ownerNodeId));
        }

        if (UtilValidate.isNotEmpty(statusId)) {
            conditions.add(EntityCondition.makeCondition("statusId", statusId));
        }

        if (UtilValidate.isEmpty(internalStatusId)) {
            conditions.add(GnFindUtil.makeIsEmptyCondition("internalStatusId"));
        } else {
            conditions.add(EntityCondition.makeCondition("internalStatusId", internalStatusId));
        }

        if (UtilValidate.isNotEmpty(partyIdToGeo)) {
            String partyIdToGeoId = (String) partyIdToGeo.get("geoId");
            String geoTypeId = (String) partyIdToGeo.get("geoTypeId");
            GeoTypeOfbiz partyIdToGeoType = null;
            if (partyIdToGeoId != null && (geoTypeId == null || GeoTypeOfbiz.valueOf(geoTypeId) == null)) {
                throw new GnServiceException(OfbizErrors.INVALID_PARAMETERS, "if geoId is not null, than geoTypeId is mandatory.");
            } else {
                partyIdToGeoType = GeoTypeOfbiz.valueOf(geoTypeId);
                entity.addMemberEntity(ALIAS_PARTY_CONTACT, "PartyContactMech");
                entity.addMemberEntity(ALIAS_POSTAL_ADDR, "PostalAddress");
                entity.addAliasAll(ALIAS_PARTY_CONTACT, "CONTACT");
                entity.addAliasAll(ALIAS_POSTAL_ADDR, "ADDR");
                entity.addViewLink(ALIAS_AUTH, ALIAS_PARTY_CONTACT, false, UtilMisc.toList(new ModelKeyMap("partyIdTo", "partyId")));
                entity.addViewLink(ALIAS_PARTY_CONTACT, ALIAS_POSTAL_ADDR, false, UtilMisc.toList(new ModelKeyMap("contactMechId", "contactMechId")));
                String idAlias;
                switch (partyIdToGeoType) {
                    case CITY://this should not be used
                        idAlias = GnFindUtil.addAliasUnique(entity, ALIAS_POSTAL_ADDR, "city");
                        break;
                    case COUNTRY://
                        idAlias = GnFindUtil.addAliasUnique(entity, ALIAS_POSTAL_ADDR, "countryGeoId");
                        break;
                    case MUNICIPALITY://
                        idAlias = GnFindUtil.addAliasUnique(entity, ALIAS_POSTAL_ADDR, "municipalityGeoId");
                        break;
                    case POSTAL_CODE://this should not be used
                        idAlias = GnFindUtil.addAliasUnique(entity, ALIAS_POSTAL_ADDR, "postalCodeGeoId");
                        break;
                    case PROVINCE://
                        idAlias = GnFindUtil.addAliasUnique(entity, ALIAS_POSTAL_ADDR, "stateProvinceGeoId");
                        break;
                    case REGION://
                        idAlias = GnFindUtil.addAliasUnique(entity, ALIAS_POSTAL_ADDR, "regionGeoId");
                        break;
                    default:
                        throw new GnServiceException(OfbizErrors.INVALID_PARAMETERS, "geoType not valid");
                }
                conditions.add(EntityCondition.makeCondition(idAlias, EntityJoinOperator.EQUALS, partyIdToGeoId));
            }
        }

        if (UtilValidate.isNotEmpty(typeIds)) {
            conditions.add(EntityCondition.makeCondition("typeId", EntityJoinOperator.IN, typeIds));
        }

        if (UtilValidate.isNotEmpty(numbers)) {
            conditions.add(EntityCondition.makeCondition("number", EntityJoinOperator.IN, numbers));
        }
        if (UtilValidate.isNotEmpty(uuids)) {
            conditions.add(EntityCondition.makeCondition("uuid", EntityJoinOperator.IN, uuids));
        }

        if (UtilValidate.isNotEmpty(issuerBaseKeys)) {
            conditions.add(EntityCondition.makeCondition("partyIdFrom", EntityJoinOperator.IN, convertNodeKeys(issuerBaseKeys)));
        }

        if (UtilValidate.isNotEmpty(otherPartyNodeToDesc) || UtilValidate.isNotEmpty(otherPartyNodeToTaxIdentificationNumber)) {
            entity.addMemberEntity(ALIAS_OTHER_PARTY_NODE, "GnPartyGroupPartyNode");
            entity.addViewLink(ALIAS_AUTH, ALIAS_OTHER_PARTY_NODE, false, ModelKeyMap.makeKeyMapList("otherPartyIdTo", "partyId"));

            if (UtilValidate.isNotEmpty(otherPartyNodeToDesc)) {
                String idAlias = GnFindUtil.addAliasUnique(entity, ALIAS_OTHER_PARTY_NODE, "name");
                conditions.add(EntityCondition.makeCondition(EntityFunction.UPPER_FIELD(idAlias), EntityOperator.LIKE, EntityFunction.UPPER("%" + otherPartyNodeToDesc + "%")));
            }

            if (UtilValidate.isNotEmpty(otherPartyNodeToTaxIdentificationNumber)) {
                String idAlias = GnFindUtil.addAliasUnique(entity, ALIAS_OTHER_PARTY_NODE, "taxIdentificationNumber");
                conditions.add(EntityCondition.makeCondition(EntityFunction.UPPER_FIELD(idAlias), EntityOperator.LIKE, EntityFunction.UPPER("%" + otherPartyNodeToTaxIdentificationNumber + "%")));
            }
        }

        if (UtilValidate.isNotEmpty(originPartyNodeTo)) {
            conditions.add(EntityCondition.makeCondition("originPartyIdTo", originPartyNodeTo.get("partyId")));
        }

        if (UtilValidate.isNotEmpty(partyNodeToKeys)) {
            conditions.add(EntityCondition.makeCondition("partyIdTo", EntityJoinOperator.IN, convertNodeKeys(partyNodeToKeys)));
        }

        if (UtilValidate.isNotEmpty(minimumPublishedDate)) {
            conditions.add(EntityCondition.makeCondition("publishedDate", EntityOperator.GREATER_THAN_EQUAL_TO, minimumPublishedDate));
        }

        if (UtilValidate.isNotEmpty(agreementIds)) {
            conditions.add(GnFindUtil.makeOrConditionById("agreementId", agreementIds));
        }

        if (UtilValidate.isNotEmpty(onlyPrivate)) {
            if ("Y".equals(onlyPrivate)) {
                conditions.add(EntityCondition.makeCondition("isPrivate", "Y"));
            } else {
                conditions.add(EntityCondition.makeCondition("isPrivate", "N"));
            }
        }

        if (UtilValidate.isNotEmpty(authorizationKeys)) {
            conditions.add(GnFindUtil.makeOrConditionById("authorizationKey", authorizationKeys));
        }

        //************************************************************************* conditions must be in AND in the same auth. item (start)


        //*******************************************************
        //**
        //** PAY ATTENTION PLEASE!!! If you change somethong from here to the end og this method, check also
        //** filterAndWasteAuthorization for similar modification to do.
        //**
        //*******************************************************

        boolean filterByOperation = UtilValidate.isNotEmpty(operationIds);
        boolean filterByWasteCerCode = UtilValidate.isNotEmpty(wasteCerCodeIds);
        boolean filterByPackaging = UtilValidate.isNotEmpty(packagingIds);
        boolean filterByRegistrationNumber = UtilValidate.isNotEmpty(registrationNumberIds);

        entity.addMemberEntity(ALIAS_ITEM, "GnAuthorizationItem");
        if (UtilValidate.isNotEmpty(categoryClassEnumIds) || UtilValidate.isNotEmpty(holderRoleIds) || filterByOperation || filterByWasteCerCode || filterByPackaging || filterByRegistrationNumber) {
            entity.addViewLink(ALIAS_AUTH, ALIAS_ITEM, false, ModelKeyMap.makeKeyMapList("agreementId"));
        } else {
            entity.addViewLink(ALIAS_AUTH, ALIAS_ITEM, true, ModelKeyMap.makeKeyMapList("agreementId"));
        }
        if (UtilValidate.isNotEmpty(categoryClassEnumIds)) {
            String idAlias = GnFindUtil.addAliasUnique(entity, ALIAS_ITEM, "categoryClassEnumId");
            conditions.add(GnFindUtil.makeOrConditionById(idAlias, categoryClassEnumIds));
        }
        if (UtilValidate.isNotEmpty(holderRoleIds)) {
            String idAlias = GnFindUtil.addAliasUnique(entity, ALIAS_ITEM, "holderRoleId");
            conditions.add(GnFindUtil.makeOrConditionById(idAlias, holderRoleIds));
        }

        if (UtilValidate.isNotEmpty(validityDate)) {
            String idAliasFrom = GnFindUtil.addAliasUnique(entity, ALIAS_ITEM, "validFromDate");
            String idAliasTo = GnFindUtil.addAliasUnique(entity, ALIAS_ITEM, "validThruDate");
            conditions.add(EntityCondition.makeCondition(idAliasFrom, EntityOperator.LESS_THAN_EQUAL_TO, validityDate));
            conditions.add(EntityCondition.makeCondition(idAliasTo, EntityOperator.GREATER_THAN_EQUAL_TO, validityDate));
        }

        if (filterByOperation) {
            entity.addMemberEntity(ALIAS_OP_TERM, "AgreementTerm");
            entity.addViewLink(ALIAS_ITEM, ALIAS_OP_TERM, false, ModelKeyMap.makeKeyMapList("agreementId", "agreementId", "agreementItemSeqId", "agreementItemSeqId"));
            conditions.add(getAgreementTermAssoc(entity, ALIAS_OP_TERM, ALIAS_OP_TERM_ASS, "GnOperationAgreementTermAssoc", "operationId", operationIds));
        }
        if (filterByWasteCerCode && !filterByRegistrationNumber) { // suspend the check for AuthorizationItemSearchHelper.findAuthorizationOperationalDetails()
            entity.addMemberEntity(ALIAS_WASTE_TERM, "AgreementTerm");
            entity.addViewLink(ALIAS_ITEM, ALIAS_WASTE_TERM, false, ModelKeyMap.makeKeyMapList("agreementId", "agreementId", "agreementItemSeqId", "agreementItemSeqId"));
            conditions.add(getAgreementTermAssoc(entity, ALIAS_WASTE_TERM, ALIAS_WASTE_TERM_ASS, "GnWasteCerCodeAgreementTermAssoc", "wasteCerCodeId", wasteCerCodeIds, wasteLogic));
        }
        if (filterByPackaging) {
            entity.addMemberEntity(ALIAS_PACK_TERM, "AgreementTerm");
            entity.addViewLink(ALIAS_ITEM, ALIAS_PACK_TERM, false, ModelKeyMap.makeKeyMapList("agreementId", "agreementId", "agreementItemSeqId", "agreementItemSeqId"));
            conditions.add(getAgreementTermAssoc(entity, ALIAS_PACK_TERM, ALIAS_PACK_TERM_ASS, "GnPackagingAgreementTermAssoc", "packagingId", packagingIds));
        }
        if (filterByRegistrationNumber) {
            entity.addMemberEntity(ALIAS_REG_NUM, "AgreementTerm");
            entity.addViewLink(ALIAS_ITEM, ALIAS_REG_NUM, false, ModelKeyMap.makeKeyMapList("agreementId", "agreementId", "agreementItemSeqId", "agreementItemSeqId"));
            String regNum = registrationNumberIds.get(0);
            regNum = regNum.replaceAll(" ", "");
            conditions.add(concatAgreementTermAssocInsensitive(entity, ALIAS_REG_NUM, ALIAS_REG_NUM_ASS, "GnRegistrationNumberAgreementTerm", "registrationNumber", Arrays.asList(regNum)));
        }

        //************************************************************************* conditions must be in AND in the same auth. item (end)

        if (onlyMine) {
            if (contextInfo.companyBases.size() > 0) {
                Debug.log("Context[" + userLogin.getString("activeContextId") + "] has got " + contextInfo.companyBases.size() + " companyBases", module);
                conditions.add(GnFindUtil.makeOrConditionById("originPartyIdTo", "partyId", contextInfo.companyBases));
                //todo: this is a workaround that create a faked company to activate filter when companyBases is empty and onlyMine flag is true
            } else {
                Debug.log("Context[" + userLogin.getString("activeContextId") + "] hasn't got companyBases", module);
                conditions.add(GnFindUtil.makeOrConditionById("originPartyIdTo", "partyId", UtilMisc.toList(UtilMisc.toMap("partyId", (Object) "!!!!!"))));
            }
        }

        List<String> orderBy = FastList.<String>newInstance();
        if (UtilValidate.isNotEmpty(orderByList)) for (Map<String, String> orderByMap : orderByList) {
            for (Entry<String, String> orderItem : orderByMap.entrySet()) {
                orderBy.add(orderItem.getKey() + " " + orderItem.getValue());
            }
        }
        if (UtilValidate.isEmpty(orderBy)) {
            // default sort by agreementId
            orderBy = UtilMisc.toList("agreementId ASC");
        }

        EntityFindOptions findOptions = GnFindUtil.getFindOptDistinct();
        findOptions.setResultSetType(EntityFindOptions.TYPE_SCROLL_INSENSITIVE);
        if (searchAtRoot) { //this avoid to consider authorizations which are present in private area
            List<EntityCondition> subSelectCondition = new ArrayList<EntityCondition>();
            subSelectCondition.add(EntityCondition.makeCondition("ownerNodeId", ownerNodeId));
            subSelectCondition.add(GnFindUtil.makeIsEmptyCondition("internalStatusId"));
            subSelectCondition.add(EntityCondition.makeCondition("statusId", AuthorizationStatusOfbiz.GN_AUTH_PUBLISHED.name()));
            EntityConditionSubSelect esb = new EntityConditionSubSelect("GnAuthorization", "uuid", EntityCondition.makeCondition(subSelectCondition), true, delegator);
            conditions.add(EntityCondition.makeCondition("uuid", EntityComparisonOperator.NOT_EQUAL, esb));
        }
        EntityListIterator it = delegator.findListIteratorByCondition(entity, EntityCondition.makeCondition(conditions), null, fieldsToSelect, orderBy, findOptions);

        final Map<String, Object> result = ServiceUtil.returnSuccess();
        if(loadOperationalDetail || (filterByWasteCerCode && filterByRegistrationNumber) || (CriteriaLogicOfbiz.AND.equals(wasteLogic) && wasteCerCodeIds.size() > 1)) {
            //niente paginazione per il momento, si scorrono tutti i risultati a mano!
            List<GenericValue> authList = new ArrayList<GenericValue>();
            try {
                authList = it.getCompleteList();
            } finally {
                it.close();
            }

            List<Map<String, Object>> authList2 = new ArrayList<Map<String, Object>>();
            if (authList.size() > 0 && (loadOperationalDetail || (filterByWasteCerCode && filterByRegistrationNumber) || (filterByRegistrationNumber && registrationNumberIds.size() > 1))) { //GREEN-1121
                for (Map<String, Object> anAuthList : authList) {
                    Map<String, Object> auth = UtilMisc.makeMapWritable(anAuthList);
                    AuthorizationItemSearchHelper itemHlp = new AuthorizationItemSearchHelper(dctx, context);
                    List<Map<String, Object>> items = itemHlp.findAuthorizationOperationalDetails((String) auth.get("agreementId"), validityDate, wasteCerCodeIds, operationIds, registrationNumberIds);
                    if (items.size() > 0) { //return auth in authList2 only if it's valid
                        if (loadOperationalDetail) { //return auth items in auth only if required
                            auth.put("authorizationItems", items);
                        }
                        authList2.add(auth);
                    }
                }
            } else {
                authList2.addAll(authList);
            }

            if (authList2.size() > 0 && CriteriaLogicOfbiz.AND.equals(wasteLogic) && wasteCerCodeIds.size() > 1) {
                authList2 = filterAndWasteAuthorization(authList2, categoryClassEnumIds, holderRoleIds, validityDate,
                        filterByOperation, operationIds,
                        filterByWasteCerCode, wasteCerCodeIds.subList(1, wasteCerCodeIds.size()),
                        filterByPackaging, packagingIds,
                        filterByRegistrationNumber, registrationNumberIds,
                        orderByList, loadOperationalDetail);
            }

            loadAuthPartyNodes(authList2);

            //TODO ELISA: niente paginazione!
            result.put("list", authList2);
            result.put("listSize", authList2.size());
            return result;

        } else { //ricerca legacy
            try {
                paginationParams.setResult(it);
            } finally {
                it.close();
            }
            paginationParams.putResult(result);

            List<Map<String, Object>> authList = UtilGenerics.checkList(result.get("list"));
            loadAuthPartyNodes(authList);
            return result;
        }
    }

    /**
     * This method do iterative search on selected authorization on order to find authorization with at least a detail
     * for every waste that satisfy all the other conditions
     *
     * @param authList
     * @param categoryClassEnumIds
     * @param holderRoleIds
     * @param validityDate
     * @param filterByOperation
     * @param operationIds
     * @param filterByWasteCerCode
     * @param wasteCerCodeIds
     * @param filterByPackaging
     * @param packagingIds
     * @param filterByRegistrationNumber
     * @param registrationNumberIds
     * @param orderByList
     * @return
     */
    private List<Map<String, Object>> filterAndWasteAuthorization(List<Map<String, Object>> authList,
                                                                  List<String> categoryClassEnumIds, List<String> holderRoleIds, Timestamp validityDate,
                                                                  boolean filterByOperation, List<String> operationIds, boolean filterByWasteCerCode, List<String> wasteCerCodeIds,
                                                                  boolean filterByPackaging, List<String> packagingIds, boolean filterByRegistrationNumber, List<String> registrationNumberIds,
                                                                  List<Map<String, String>> orderByList, boolean loadOperationalDetail) throws GenericEntityException, GenericServiceException {

        List<String> agreementIds = FastList.<String>newInstance();
        List<String> agreementIds50 = FastList.<String>newInstance();
        List<String> remaining = FastList.<String>newInstance();

        for (Map<String, Object> auth : authList) {
            remaining.add((String) auth.get("agreementId"));
        }
        authList.clear();

        while (remaining.size() > 0) { //verifica slot di 50 autorizzazioni alla volta
            agreementIds.clear();
            agreementIds.addAll(remaining); //agreementIds = autorizzazioni da controllare col primo wasteCerCodeId
            agreementIds50.clear();
            remaining.clear();
            if (agreementIds.size() >= 50) {
                agreementIds50.addAll(agreementIds.subList(0, 50)); //primo slot da controllare
                if (agreementIds.size() > 50)
                    remaining.addAll(agreementIds.subList(50, agreementIds.size() - 1)); //autorizzazioni da controllare al prossimo ciclo
            } else {
                agreementIds50.addAll(agreementIds);
            }

            //algoritmo di verifica tramite query con select in max 50 autorizzazioni

            final ModelEntity mainEntityModel = delegator.getModelEntity("GnAuthorizationAndAgreement");
            final List<String> fieldsToSelect = mainEntityModel.getAllFieldNames();

            DynamicViewEntity entity = new DynamicViewEntity();
            entity.addMemberEntity(ALIAS_AUTH, mainEntityModel.getEntityName());
            entity.addAliasAll(ALIAS_AUTH, null);

            List<EntityCondition> conditions = new FastList<EntityCondition>();

            if (UtilValidate.isNotEmpty(agreementIds)) {
                conditions.add(GnFindUtil.makeOrConditionById("agreementId", agreementIds50));
            }

            //************************************************************************* conditions must be in AND in the same auth. item (start)

            entity.addMemberEntity(ALIAS_ITEM, "GnAuthorizationItem");
            entity.addViewLink(ALIAS_AUTH, ALIAS_ITEM, false, ModelKeyMap.makeKeyMapList("agreementId"));

            if (UtilValidate.isNotEmpty(categoryClassEnumIds)) {
                String idAlias = GnFindUtil.addAliasUnique(entity, ALIAS_ITEM, "categoryClassEnumId");
                conditions.add(GnFindUtil.makeOrConditionById(idAlias, categoryClassEnumIds));
            }
            if (UtilValidate.isNotEmpty(holderRoleIds)) {
                String idAlias = GnFindUtil.addAliasUnique(entity, ALIAS_ITEM, "holderRoleId");
                conditions.add(GnFindUtil.makeOrConditionById(idAlias, holderRoleIds));
            }

            if (UtilValidate.isNotEmpty(validityDate)) {
                String idAliasFrom = GnFindUtil.addAliasUnique(entity, ALIAS_ITEM, "validFromDate");
                String idAliasTo = GnFindUtil.addAliasUnique(entity, ALIAS_ITEM, "validThruDate");
                conditions.add(EntityCondition.makeCondition(idAliasFrom, EntityOperator.LESS_THAN_EQUAL_TO, validityDate));
                conditions.add(EntityCondition.makeCondition(idAliasTo, EntityOperator.GREATER_THAN_EQUAL_TO, validityDate));
            }

            if (filterByOperation) {
                entity.addMemberEntity(ALIAS_OP_TERM, "AgreementTerm");
                entity.addViewLink(ALIAS_ITEM, ALIAS_OP_TERM, false, ModelKeyMap.makeKeyMapList("agreementId", "agreementId", "agreementItemSeqId", "agreementItemSeqId"));
                conditions.add(getAgreementTermAssoc(entity, ALIAS_OP_TERM, ALIAS_OP_TERM_ASS, "GnOperationAgreementTermAssoc", "operationId", operationIds));
            }

            filterByWasteCerCode = UtilValidate.isNotEmpty(wasteCerCodeIds);
            if (filterByWasteCerCode && !filterByRegistrationNumber) { // suspend the check for AuthorizationItemSearchHelper.findAuthorizationOperationalDetails()
                entity.addMemberEntity(ALIAS_WASTE_TERM, "AgreementTerm");
                entity.addViewLink(ALIAS_ITEM, ALIAS_WASTE_TERM, false, ModelKeyMap.makeKeyMapList("agreementId", "agreementId", "agreementItemSeqId", "agreementItemSeqId"));
                conditions.add(getAgreementTermAssoc(entity, ALIAS_WASTE_TERM, ALIAS_WASTE_TERM_ASS, "GnWasteCerCodeAgreementTermAssoc", "wasteCerCodeId", wasteCerCodeIds, CriteriaLogicOfbiz.AND)); //solo wasteCerCodeIds[0]
            }

            if (filterByPackaging) {
                entity.addMemberEntity(ALIAS_PACK_TERM, "AgreementTerm");
                entity.addViewLink(ALIAS_ITEM, ALIAS_PACK_TERM, false, ModelKeyMap.makeKeyMapList("agreementId", "agreementId", "agreementItemSeqId", "agreementItemSeqId"));
                conditions.add(getAgreementTermAssoc(entity, ALIAS_PACK_TERM, ALIAS_PACK_TERM_ASS, "GnPackagingAgreementTermAssoc", "packagingId", packagingIds));
            }
            if (filterByRegistrationNumber) {
                entity.addMemberEntity(ALIAS_REG_NUM, "AgreementTerm");
                entity.addViewLink(ALIAS_ITEM, ALIAS_REG_NUM, false, ModelKeyMap.makeKeyMapList("agreementId", "agreementId", "agreementItemSeqId", "agreementItemSeqId"));
                String regNum = registrationNumberIds.get(0);
                regNum = regNum.replaceAll(" ", "");
                conditions.add(concatAgreementTermAssocInsensitive(entity, ALIAS_REG_NUM, ALIAS_REG_NUM_ASS, "GnRegistrationNumberAgreementTerm", "registrationNumber", Arrays.asList(regNum)));
            }

            //************************************************************************* conditions must be in AND in the same auth. item (end)

            List<String> orderBy = FastList.<String>newInstance();
            if (UtilValidate.isNotEmpty(orderByList)) for (Map<String, String> orderByMap : orderByList) {
                for (Entry<String, String> orderItem : orderByMap.entrySet()) {
                    orderBy.add(orderItem.getKey() + " " + orderItem.getValue());
                }
            }
            if (UtilValidate.isEmpty(orderBy)) {
                // default sort by agreementId
                orderBy = UtilMisc.toList("agreementId ASC");
            }

            EntityFindOptions findOptions = GnFindUtil.getFindOptDistinct();
            findOptions.setResultSetType(EntityFindOptions.TYPE_SCROLL_INSENSITIVE);

            EntityListIterator it = delegator.findListIteratorByCondition(entity, EntityCondition.makeCondition(conditions), null, fieldsToSelect, orderBy, findOptions);
            try {
                List<GenericValue> completeList = it.getCompleteList();
                authList.addAll(UtilGenerics.<Map<String, Object>>checkList(completeList));
            } finally {
                it.close();
            }

        } // end while (remaining.size() > 0): con questo algoritmo vado a incrementare coi risultati della query la lista di autorizzazioni valide (max 50 per volta)

        // a questo punto in authList ci sono tutte le autorizzazioni valide secondo query considerando il wasteCerCodeIds[0] su cui effettuare ulteriori controlli manuali
        // (tipo targa-rifiuto) e su cui verificare ulteriori eventuali rifiuti
        if (authList.size() > 0) {

            //controllo manuale per vincoli con specificità (targa-rifiuto)
            if (loadOperationalDetail || (filterByWasteCerCode && filterByRegistrationNumber) || (filterByRegistrationNumber && registrationNumberIds.size() > 1)) { //GREEN-1121
                List<Map<String, Object>> authList2 = new ArrayList<Map<String, Object>>();
                for (Map<String, Object> anAuthList : authList) {
                    Map<String, Object> auth = UtilMisc.makeMapWritable(anAuthList);
                    AuthorizationItemSearchHelper itemHlp = new AuthorizationItemSearchHelper(dctx, context);
                    List<Map<String, Object>> items = itemHlp.findAuthorizationOperationalDetails((String) auth.get("agreementId"), validityDate, wasteCerCodeIds, operationIds, registrationNumberIds);
                    if (items.size() > 0) { //return auth in authList2 only if it's valid
                        if (loadOperationalDetail) { //return auth items in auth only if required
                            auth.put("authorizationItems", items);
                        }
                        authList2.add(auth);
                    }
                }
                authList.clear();
                authList.addAll(authList2);
            }

            //se sono presenti altri rifiuti in AND, si itera filterAndWasteAuthorization ed eventualmente authList verrà scremata ad ogni iterazione
            if (authList.size() > 0 && wasteCerCodeIds.size() > 1) { //CriteriaLogicOfbiz.AND obvious
                authList = filterAndWasteAuthorization(authList, categoryClassEnumIds, holderRoleIds, validityDate,
                        filterByOperation, operationIds,
                        filterByWasteCerCode, wasteCerCodeIds.subList(1, wasteCerCodeIds.size()),
                        filterByPackaging, packagingIds,
                        filterByRegistrationNumber, registrationNumberIds,
                        orderByList, loadOperationalDetail);
            }
        }

        return authList;
    }

    public Map<String, Object> internalSearchAtRoot(String statusId, List<String> taxIdentificationNumbers) throws GenericEntityException, GenericServiceException { // used for invitations !!!
        final Map<String, Object> result = ServiceUtil.returnSuccess();
        final ModelEntity mainEntityModel = delegator.getModelEntity("GnAuthorizationAndAgreement");
        final List<String> fieldsToSelect = mainEntityModel.getAllFieldNames();

        DynamicViewEntity entity = new DynamicViewEntity();
        entity.addMemberEntity(ALIAS_AUTH, mainEntityModel.getEntityName());
        entity.addAliasAll(ALIAS_AUTH, null);

        List<EntityCondition> conditions = new FastList<EntityCondition>();
        conditions.add(EntityCondition.makeCondition("ownerNodeId", "GN_ROOT"));

        if (UtilValidate.isNotEmpty(statusId)) {
            conditions.add(EntityCondition.makeCondition("statusId", statusId));
        }
        conditions.add(GnFindUtil.makeIsEmptyCondition("internalStatusId"));

        if (!UtilValidate.isEmpty(taxIdentificationNumbers)) {
            if (!UtilValidate.isEmpty(taxIdentificationNumbers)) {
                entity.addMemberEntity(ALIAS_OTHER_PARTY_NODE, "GnPartyGroupPartyNode");
                entity.addViewLink(ALIAS_AUTH, ALIAS_OTHER_PARTY_NODE, false, ModelKeyMap.makeKeyMapList("otherPartyIdTo", "partyId"));
                entity.addAlias(ALIAS_OTHER_PARTY_NODE, "taxIdentificationNumber");
                conditions.add(GnFindUtil.makeOrConditionById("taxIdentificationNumber", taxIdentificationNumbers));
            }
        }

        List<String> orderBy = UtilMisc.toList("agreementId ASC");

        EntityFindOptions findOptions = GnFindUtil.getFindOptDistinct();
        findOptions.setResultSetType(EntityFindOptions.TYPE_SCROLL_INSENSITIVE);
        EntityListIterator it = delegator.findListIteratorByCondition(entity, EntityCondition.makeCondition(conditions), null, fieldsToSelect, orderBy, findOptions);
        try {
            paginationParams.setResult(it);
        } finally {
            it.close();
        }

        paginationParams.putResult(result);

        List<Map<String, Object>> authList = UtilGenerics.checkList(result.get("list"));
        loadAuthPartyNodes(authList);
        return result;
    }

    private void loadAuthPartyNodes(List<Map<String, Object>> authList) throws GenericServiceException {
//        long start = System.currentTimeMillis();
        for (int i = 0; i < authList.size(); i++) {
            Map<String, Object> auth = UtilMisc.makeMapWritable(authList.get(i));
            auth.put("partyNodeTo", findPartyNodeById((String) auth.get("partyIdTo")));
            auth.put("otherPartyNodeTo", findPartyNodeById((String) auth.get("otherPartyIdTo")));
            auth.put("partyNodeFrom", findPartyNodeById((String) auth.get("partyIdFrom")));
            authList.set(i, auth);
        }
//        long stop = System.currentTimeMillis();
//        Debug.log("Time to load partyNodeTo and partyNodeFrom of " + authList.size() + " is " + ((stop - start) / 1000d) + "s", module);
    }

    private void initContextInfo(String contextId, boolean includeCompanyBases, ContextInfo contextInfo) throws GenericServiceException {
        Map<String, ? extends Object> serviceContext = UtilMisc.toMap("userLogin", userLogin, "partyId", contextId, "excludeCompanyBases", includeCompanyBases ? "N" : "Y", "excludeSecurityGroups", "Y", "excludeUsers", "Y");
        Map<String, Object> serviceResult = dispatcher.runSync("gnFindContextById", serviceContext);
        contextInfo.partyNode = UtilGenerics.toMap(serviceResult.get("partyNode"));
        contextInfo.companyBases = UtilGenerics.toList(serviceResult.get("companyBases"));
    }

    /**
     * Append term assoc entity to current and create condition to filter
     *
     * @param entity      entity to search
     * @param entityAlias current entity alias
     * @param entityName  current entity name
     * @param idFieldName filter to filter
     * @param idList      ids to filter
     * @return updated condition
     */
    private EntityCondition getAgreementTermAssoc(DynamicViewEntity entity, String termAlias, String entityAlias, String entityName, String idFieldName, List<String> idList) {
        return getAgreementTermAssoc(entity, termAlias, entityAlias, entityName, idFieldName, idList, CriteriaLogicOfbiz.OR);
    }

    /**
     * Append term assoc entity to current and create condition to filter
     *
     * @param entity        entity to search
     * @param entityAlias   current entity alias
     * @param entityName    current entity name
     * @param idFieldName   filter to filter
     * @param idList        ids to filter
     * @param criteriaLogic param to determine if the filtered authorizations must have at least one id or all the ids selected
     * @return updated condition
     */
    private EntityCondition getAgreementTermAssoc(DynamicViewEntity entity, String termAlias, String entityAlias, String entityName, String idFieldName, List<String> idList, CriteriaLogicOfbiz criteriaLogic) {
        entity.addMemberEntity(entityAlias, entityName);
        entity.addViewLink(termAlias, entityAlias, false, ModelKeyMap.makeKeyMapList("agreementTermId"));
        String idAlias = GnFindUtil.addAliasUnique(entity, entityAlias, idFieldName);
        if (CriteriaLogicOfbiz.AND.equals(criteriaLogic)) {
            return GnFindUtil.makeOrConditionById(idAlias, idList.subList(0, 1)); //considero solo il primo, gli altri li verifico nelle successive iterazioni
        } else {
            return GnFindUtil.makeOrConditionById(idAlias, idList);
        }
    }

    private EntityCondition concatAgreementTermAssocInsensitive(DynamicViewEntity entity, String termAlias, String entityAlias, String entityName, String idFieldName, List<String> idList) throws GnServiceException {
        entity.addMemberEntity(entityAlias, entityName);
        entity.addViewLink(termAlias, entityAlias, false, ModelKeyMap.makeKeyMapList("agreementTermId"));
        String idAlias = GnFindUtil.addAliasUnique(entity, entityAlias, idFieldName);
        EntityCondition orCondition = null;
        for (String id : idList) {
            orCondition = GnFindUtil.concat(orCondition, EntityJoinOperator.OR, EntityCondition.makeCondition(EntityFunction.UPPER_FIELD(idAlias), EntityOperator.EQUALS, EntityFunction.UPPER(id)));
        }
        return orCondition;
    }

    private EntityCondition concatAgreementTermAssocWithLike(DynamicViewEntity entity, String termAlias, String entityAlias, String entityName, String idFieldName, List<String> idList) throws GnServiceException {
        entity.addMemberEntity(entityAlias, entityName);
        entity.addViewLink(termAlias, entityAlias, false, ModelKeyMap.makeKeyMapList("agreementTermId"));
        String idAlias = GnFindUtil.addAliasUnique(entity, entityAlias, idFieldName);
        EntityCondition orCondition = null;
        for (String id : idList) {
            if (id.contains("%"))
                throw new GnServiceException(OfbizErrors.INVALID_PARAMETERS, "parameter cannot contain %");
            orCondition = GnFindUtil.concat(orCondition, EntityJoinOperator.OR, EntityCondition.makeCondition(EntityFunction.UPPER_FIELD(idAlias), EntityOperator.LIKE, EntityFunction.UPPER("%" + id + "%")));
        }
        return orCondition;
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

    public List<Map<String, Object>> gnSearchNewest() throws GenericEntityException, GenericServiceException {
        List<Map<String, Object>> logs = gnFindServiceInvocationLog();
        boolean ok = false;
        Timestamp lastInvocationDate = null;
        if (logs.size() == 0) ok = true;
        else {
            lastInvocationDate = (Timestamp) logs.get(0).get("invocationDate");
            int property = Integer.parseInt(PropertyUtil.getProperty(PropertyEnumOfbiz.SEARCH_NEWEST_AUTH_DROP_CALL_TIME, getDispatcher(), getContext()));
            if (UtilDateTime.nowTimestamp().getTime() - lastInvocationDate.getTime() > property) {
                ok = true;
            }
        }
        if (ok) {
            List<GenericValue> auths = searchNewest(lastInvocationDate);
            gnCreateServiceInvocationLog();
            List<Map<String, Object>> ret = new ArrayList<Map<String, Object>>(auths.size());
            AuthorizationHelper authorizationHelper = new AuthorizationHelper(dctx, context);
            for (GenericValue auth : auths) {
                ret.add(authorizationHelper.gnFindAuthorizationById(auth.getString("agreementId"), auth.getString("authorizationKey"), true, true));

            }
            return ret;
        }
        return FastList.newInstance();
    }

    private List<GenericValue> searchNewest(Timestamp lastInvocationDate) throws GenericEntityException, GenericServiceException {
        String ownerNodeId = (String) UtilGenerics.checkMap(getCurrentContext().get("partyNode")).get("partyId");
        int tolerance = Integer.parseInt(PropertyUtil.getProperty(PropertyEnumOfbiz.SEARCH_NEWEST_AUTH_TOLERANCE_TIME, getDispatcher(), getContext()));

        List<EntityCondition> conds = new ArrayList<EntityCondition>();
        conds.add(EntityCondition.makeCondition("statusId", AuthorizationStatusOfbiz.GN_AUTH_PUBLISHED.name()));
        conds.add(EntityCondition.makeCondition("ownerNodeId", ownerNodeId));
        conds.add(GnFindUtil.makeIsEmptyCondition("internalStatusId"));
        if (lastInvocationDate != null) {
            Timestamp minimumPublishedDate = new Timestamp(lastInvocationDate.getTime() - tolerance);
            conds.add(EntityCondition.makeCondition("publishedDate", EntityOperator.GREATER_THAN_EQUAL_TO, minimumPublishedDate));
        }
        List<GenericValue> auths = delegator.findList("GnAuthorizationAndAgreement", EntityCondition.makeCondition(conds), null, UtilMisc.toList("publishedDate"), null, false);
        return auths;
    }

    private List<Map<String, Object>> gnFindServiceInvocationLog() throws GenericServiceException {
        Map<String, Object> srvContext = UtilMisc.toMap("userLogin", (Object) userLogin, "contextId", getCurrentContextId(),
                "serviceName", "gnSearchNewest", "userLoginId", userLogin.getString("userLoginId"));
        Map<String, Object> srvResponse = dispatcher.runSync("gnFindServiceInvocationLog", srvContext);
        return UtilGenerics.toList(srvResponse.get("list"));
    }

    private void gnCreateServiceInvocationLog() throws GenericServiceException {
        Map<String, Object> srvContext = UtilMisc.toMap("userLogin", (Object) userLogin, "contextId", getCurrentContextId(),
                "serviceName", "gnSearchNewest", "userLoginId", userLogin.getString("userLoginId"));
        Map<String, Object> srvResponse = dispatcher.runSync("gnCreateServiceInvocationLog", srvContext);
    }

    private List<String> convertNodeKeys(List<String> nodeKeys) throws GenericServiceException {
        List<String> result = FastList.newInstance();
        for (String nodeKey : nodeKeys) {
            result.add(getPartyId(null, nodeKey, String.format("Cannot convert nodeKey \"%s\" in a partyId", nodeKey)));
        }
        return result;
    }

    public long getToBeValidatedAuthorizationCount(String nodeKey, String partyId) throws GnServiceException, GenericEntityException {
        List<EntityCondition> conds = new ArrayList<EntityCondition>();
        if (UtilValidate.isNotEmpty(nodeKey)) {
            conds.add(EntityCondition.makeCondition("ownerNodeKey", nodeKey));
        } else {
            if (UtilValidate.isNotEmpty(partyId)) {
                conds.add(EntityCondition.makeCondition("ownerNodeId", partyId));
            } else {
                throw new GnServiceException(OfbizErrors.INVALID_PARAMETERS, "nodeKey and partyId cannot be empty both", module);
            }

        }
        conds.add(GnFindUtil.makeOrConditionById("statusId",
                Arrays.asList(AuthorizationStatusOfbiz.GN_AUTH_TO_BE_VALID.name(),
                        AuthorizationStatusOfbiz.GN_AUTH_CONFLICTING.name())));
        conds.add(GnFindUtil.makeIsEmptyCondition("internalStatusId"));
        long count = delegator.findCountByCondition("GnAuthorizationAndAgreement", EntityCondition.makeCondition(conds), null, null);
        return count;
    }

    public Map<String, Object> gnCompleteCompanyInfo(String nameOrTaxIdeNumberPart) throws GenericServiceException, GenericEntityException {
        final Map<String, Object> result = ServiceUtil.returnSuccess();

        final ModelEntity mainEntityModel = delegator.getModelEntity("GnAuthorizationAndAgreement");

        ContextInfo contextInfo = new ContextInfo();
        initContextInfo(userLogin.getString("activeContextId"), false, contextInfo);

        DynamicViewEntity entity = new DynamicViewEntity();
        entity.addMemberEntity(ALIAS_AUTH, mainEntityModel.getEntityName());
        entity.addAliasAll(ALIAS_AUTH, null);

        List<EntityCondition> conditions = new FastList<EntityCondition>();
        String ownerNodeId = (String) contextInfo.partyNode.get("partyId");

        conditions.add(EntityCondition.makeCondition("ownerNodeId", ownerNodeId));
        conditions.add(EntityCondition.makeCondition("statusId", AuthorizationStatusOfbiz.GN_AUTH_PUBLISHED.name()));
        conditions.add(GnFindUtil.makeIsEmptyCondition("internalStatusId"));

        entity.addMemberEntity(ALIAS_OTHER_PARTY_NODE, "GnPartyGroupPartyNode");
        entity.addViewLink(ALIAS_AUTH, ALIAS_OTHER_PARTY_NODE, false, ModelKeyMap.makeKeyMapList("otherPartyIdTo", "partyId"));

        String idAlias1 = GnFindUtil.addAliasUnique(entity, ALIAS_OTHER_PARTY_NODE, "name");
        EntityExpr entityExpr1 = EntityCondition.makeCondition(EntityFunction.UPPER_FIELD(idAlias1), EntityOperator.LIKE, EntityFunction.UPPER("%" + nameOrTaxIdeNumberPart + "%"));
        String idAlias2 = GnFindUtil.addAliasUnique(entity, ALIAS_OTHER_PARTY_NODE, "taxIdentificationNumber");
        EntityExpr entityExpr2 = EntityCondition.makeCondition(EntityFunction.UPPER_FIELD(idAlias2), EntityOperator.LIKE, EntityFunction.UPPER("%" + nameOrTaxIdeNumberPart + "%"));
        EntityCondition orCondition = GnFindUtil.concat(entityExpr1, EntityJoinOperator.OR, entityExpr2);
        conditions.add(orCondition);

        EntityFindOptions findOptions = GnFindUtil.getFindOptDistinct();
        findOptions.setResultSetType(EntityFindOptions.TYPE_SCROLL_INSENSITIVE);

        final List<String> fieldsToSelect = Arrays.asList(ALIAS_OTHER_PARTY_NODE.concat("_").concat("name"), ALIAS_OTHER_PARTY_NODE.concat("_").concat("taxIdentificationNumber"));
        List<String> orderBy = UtilMisc.toList(ALIAS_OTHER_PARTY_NODE.concat("_").concat("name").concat(" ASC"));
        EntityListIterator it = delegator.findListIteratorByCondition(entity, EntityCondition.makeCondition(conditions), null, fieldsToSelect, orderBy, findOptions);
        try {
            paginationParams.setResult(it);
        } finally {
            it.close();
        }

        paginationParams.putResult(result);

        return result;
    }

    public Map<String, Object> gnFindCompanyBasesPublisher(String taxIdentificationNumber) throws GenericServiceException, GenericEntityException {
        final Map<String, Object> result = ServiceUtil.returnSuccess();

        final ModelEntity mainEntityModel = delegator.getModelEntity("GnAuthorizationAndAgreement");

        ContextInfo contextInfo = new ContextInfo();
        initContextInfo(userLogin.getString("activeContextId"), false, contextInfo);

        DynamicViewEntity entity = new DynamicViewEntity();
        entity.addMemberEntity(ALIAS_AUTH, mainEntityModel.getEntityName());
        entity.addAliasAll(ALIAS_AUTH, null);

        List<EntityCondition> conditions = new FastList<EntityCondition>();
        String ownerNodeId = (String) contextInfo.partyNode.get("partyId");

        conditions.add(EntityCondition.makeCondition("ownerNodeId", ownerNodeId));
        conditions.add(EntityCondition.makeCondition("statusId", AuthorizationStatusOfbiz.GN_AUTH_PUBLISHED.name()));
        conditions.add(GnFindUtil.makeIsEmptyCondition("internalStatusId"));

        entity.addMemberEntity(ALIAS_OTHER_PARTY_NODE, "GnPartyGroupPartyNode");
        entity.addViewLink(ALIAS_AUTH, ALIAS_OTHER_PARTY_NODE, false, ModelKeyMap.makeKeyMapList("otherPartyIdTo", "partyId"));

        String idAlias = GnFindUtil.addAliasUnique(entity, ALIAS_OTHER_PARTY_NODE, "taxIdentificationNumber");
        EntityExpr entityExpr = EntityCondition.makeCondition(EntityFunction.UPPER_FIELD(idAlias), EntityOperator.EQUALS, EntityFunction.UPPER(taxIdentificationNumber));
        conditions.add(EntityCondition.makeCondition(entityExpr));

        EntityFindOptions findOptions = GnFindUtil.getFindOptDistinct();
        findOptions.setResultSetType(EntityFindOptions.TYPE_SCROLL_INSENSITIVE);

        List<String> orderBy = UtilMisc.toList("partyIdTo".concat(" ASC"));
        EntityListIterator iterator = delegator.findListIteratorByCondition(entity, EntityCondition.makeCondition(conditions), null, Arrays.asList("partyIdTo"), orderBy, findOptions);
        List<GenericValue> gvs = iterator.getCompleteList();
        iterator.close();
        List<PostalAddressEnhanced> postalAddressEnhancedList = new LinkedList<PostalAddressEnhanced>();
        List<PostalAddressEnhanced> distinctPostalAddressEnhancedList = new LinkedList<PostalAddressEnhanced>();
        for (GenericValue gv : gvs) {
            String partyIdTo = gv.getString("partyIdTo");
            Map<Object, Object> companyBase = UtilGenerics.checkMap(dispatcher.runSync("gnInternalFindPartyNodeById", UtilMisc.toMap("partyId", partyIdTo, "userLogin", userLogin, "findRelationships", "N")).get("partyNode"));
            Map<String, Object> address = UtilMisc.toMap(companyBase.get("address"));
            PostalAddressEnhanced postalAddressEnhanced = new PostalAddressEnhanced((String) address.get("contactMechId"), address, partyIdTo);
            postalAddressEnhancedList.add(postalAddressEnhanced);
        }
        for (PostalAddressEnhanced pae : postalAddressEnhancedList) {
            Map<String, Object> address = pae.postalAddress;
            boolean foundSameAddress = false;
            for (PostalAddressEnhanced distinctPae : distinctPostalAddressEnhancedList) {
                boolean match = false;
                try {
                    match = distinctPae.addressMatcher.match((String) address.get("address1"),
                            (String) address.get("streetNumber"), (String) address.get("village"));

                } catch (TokenizerException e) {
                    e.printStackTrace();
                } catch (ComparerException e) {
                    e.printStackTrace();
                }
                if (match) {
                    String postalGeoId = (String) address.get("postalCodeGeoId");
                    String distinctPostalGeoId = (String) distinctPae.postalAddress.get("postalCodeGeoId");
                    if (postalGeoId == null && distinctPostalGeoId == null) { //both foreign --> check all fields
                        String countryGeoId = (String) address.get("countryGeoId");
                        String distinctCountryGeoId = (String) distinctPae.postalAddress.get("countryGeoId");
                        if (countryGeoId.equals(distinctCountryGeoId)) { //country is the only mandatory field
                            String regionGeoName = (String) address.get("regionGeoName");
                            String distinctRegionGeoName = (String) distinctPae.postalAddress.get("regionGeoName");
                            if ((regionGeoName == null && distinctRegionGeoName == null) || (regionGeoName != null && regionGeoName.equals(distinctRegionGeoName))) {
                                String stateProvinceGeoName = (String) address.get("stateProvinceGeoName");
                                String distinctStateProvinceGeoName = (String) distinctPae.postalAddress.get("stateProvinceGeoName");
                                if ((stateProvinceGeoName == null && distinctStateProvinceGeoName == null) || (stateProvinceGeoName != null && stateProvinceGeoName.equals(distinctStateProvinceGeoName))) {
                                    String municipalityGeoName = (String) address.get("municipalityGeoName");
                                    String distinctMunicipalityGeoName = (String) distinctPae.postalAddress.get("municipalityGeoName");
                                    if ((municipalityGeoName == null && distinctMunicipalityGeoName == null) || (municipalityGeoName != null && municipalityGeoName.equals(distinctMunicipalityGeoName))) {
                                        foundSameAddress = true;
                                        distinctPae.companyBaseIds.addAll(pae.companyBaseIds);
                                        break;
                                    }
                                }
                            }
                        }
                    } else {
                        if (postalGeoId != null && postalGeoId.equals(distinctPostalGeoId)) { //equals if both italian and equals postalGeoId (mandatory for italians)
                            foundSameAddress = true;
                            distinctPae.companyBaseIds.addAll(pae.companyBaseIds);
                            break;
                        }
                    }
                }
            }//end for distinctPostalAddressEnhancedList
            if (!foundSameAddress) {
                distinctPostalAddressEnhancedList.add(pae);
            }
        }//end for postalAddressEnhancedList

        List<Map<String, Object>> postalAddressEnhancedListResult = new ArrayList<Map<String, Object>>(distinctPostalAddressEnhancedList.size());
        for (PostalAddressEnhanced postalAddressEnhanced : distinctPostalAddressEnhancedList) {
            Map<String, Object> postalAddressEnhancedMap = new HashMap<String, Object>();
            postalAddressEnhancedMap.put("address", postalAddressEnhanced.postalAddress);
            List<String> companyBaseIds = postalAddressEnhanced.companyBaseIds;
            List<String> companyBaseKeys = new ArrayList<String>(companyBaseIds.size());
            for (String cbId : companyBaseIds) {
                String cbKey = getNodeKey(cbId, null);
                if (cbKey != null) companyBaseKeys.add(cbKey);
            }
            postalAddressEnhancedMap.put("partyToKeys", companyBaseKeys);
            postalAddressEnhancedListResult.add(postalAddressEnhancedMap);
        }

        result.put("addressEnhancedList", postalAddressEnhancedListResult);
        return result;
    }

    private class PostalAddressEnhanced {
        private String contactMechId;
        private Map<String, Object> postalAddress;
        private List<String> companyBaseIds;
        private AddressMatcher addressMatcher;

        private PostalAddressEnhanced(String contactMechId, Map<String, Object> postalAddress, String companyBaseId) {
            this.contactMechId = contactMechId;
            this.postalAddress = postalAddress;
            this.companyBaseIds = new ArrayList<String>();
            this.companyBaseIds.add(companyBaseId);
            addressMatcher = new AddressMatcher((String) postalAddress.get("address1"),
                    (String) postalAddress.get("streetNumber"), (String) postalAddress.get("village"));
        }
    }

}