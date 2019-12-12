package it.memelabs.gn.services.authorization;

import it.intext.multifield.matcher.exception.ComparerException;
import it.intext.multifield.matcher.exception.TokenizerException;
import it.memelabs.gn.services.OfbizErrors;
import it.memelabs.gn.services.communication.CommunicationEventAttributeKeyOfbiz;
import it.memelabs.gn.services.communication.EventTypeOfbiz;
import it.memelabs.gn.services.event.EventMessageContainer;
import it.memelabs.gn.services.event.EventMessageFactory;
import it.memelabs.gn.services.event.type.*;
import it.memelabs.gn.services.login.LoginSourceOfbiz;
import it.memelabs.gn.services.node.NodeTreeHelper;
import it.memelabs.gn.services.node.PartyNodeTypeOfbiz;
import it.memelabs.gn.services.node.PartyRoleOfbiz;
import it.memelabs.gn.services.node.RelationshipTypeOfbiz;
import it.memelabs.gn.services.security.GnSecurity;
import it.memelabs.gn.services.security.PermissionsOfbiz;
import it.memelabs.gn.util.*;
import it.memelabs.gn.util.find.GnFindUtil;
import it.memelabs.greenebula.multifield.matcher.AddressMatcher;
import javolution.util.FastList;
import javolution.util.FastMap;
import org.ofbiz.base.util.*;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.entity.condition.EntityComparisonOperator;
import org.ofbiz.entity.condition.EntityCondition;
import org.ofbiz.entity.condition.EntityFieldMap;
import org.ofbiz.entity.condition.EntityOperator;
import org.ofbiz.entity.model.DynamicViewEntity;
import org.ofbiz.entity.model.ModelKeyMap;
import org.ofbiz.entity.util.EntityListIterator;
import org.ofbiz.service.DispatchContext;
import org.ofbiz.service.GenericServiceException;

import java.sql.Timestamp;
import java.util.*;

/**
 * 12/03/13
 *
 * @author Andrea Fossi
 */
public class AuthorizationHelper extends CommonAuthorizationHelper {

    public AuthorizationHelper(DispatchContext dctx, Map<String, ? extends Object> context) {
        super(dctx, context);
    }


    public final static List<String> keyFields = Arrays.asList("uuid", "version", "lastModifierNodeKey", "parentVersion");
    public final static String module = AuthorizationHelper.class.getName();

    /**
     * This method check the compatibility between the authorization and the profile of the company.
     *
     * @param userLogin
     * @param authorization
     * @param partyId       forced id of the company; it can be null and the default is the company where user is employed
     * @return profileResponse map in case of success
     * @throws GenericServiceException if there's no compatibility
     */
    public Map<String, Object> authorizationAndProfileMatchingCheck(Map<String, Object> userLogin, Map<String, Object> authorization, String partyId) throws GenericServiceException {
        String isPrivate = (String) authorization.get("isPrivate");
        Map<String, Object> profileResponse;
        if ("Y".equals(isPrivate)) {
            profileResponse = getDispatcher().runSync("gnPrivateAuthPublishProfileCheck",
                    UtilMisc.toMap("userLogin", userLogin, "forcedCompanyPartyId", partyId));
            if (!"Y".equals(profileResponse.get("hasPermission"))) {
                throw new GnServiceException(OfbizErrors.ACCESS_DENIED, "Company hasn't permission related to private publish.");
            }
        } else { //TODO: improvement Aggiungere controllo profilo cliente anche per autorizzazioni pubbliche
/*            profileResponse = getDispatcher().runSync("gnRootPublishProfileCheck",
//                    UtilMisc.toMap("userLogin", userLogin, "forcedCompanyPartyId", partyId));
            if (!"Y".equals(profileResponse.get("hasPermission"))) {
                throw new GnServiceException(OfbizErrors.ACCESS_DENIED, "Company hasn't permission related to root publish.");
            }*/
            profileResponse = new FastMap<String, Object>();
        }
        return profileResponse;
    }

    /**
     * @param authorizationKey
     * @param partyIdFrom
     * @param partyIdTo
     * @param number
     * @param holderTaxIdentificationNumber
     * @param holderCompanyBase
     * @param typeId
     * @param ownerNodeId
     * @return
     * @throws GenericServiceException
     * @throws GenericEntityException
     */
    public boolean existsAuthorizationByLogicalKey(String authorizationKey, String partyIdFrom, String partyIdTo,
                                                   String number, String holderTaxIdentificationNumber, String holderCompanyName,
                                                   Map<String, Object> holderCompanyBase, AuthorizationTypesOfbiz typeId,
                                                   String ownerNodeId, List<String> statusToExclude) throws GenericServiceException, GenericEntityException {

        if ((holderTaxIdentificationNumber == null || holderTaxIdentificationNumber.isEmpty()) &&
                UtilValidate.isNotEmpty(partyIdTo) && gnPartyRoleCheck(partyIdTo, PartyRoleOfbiz.GN_COMPANY_BASE.name())) {
            Debug.log(String.format("Overwrite incoming holder address and taxIdentificationNumber with data coming from companyBase[%s]", partyIdTo), module);

            Map<String, Object> holderCompany = PartyNodeUtil.getOwner(holderCompanyBase);
            //address = UtilGenerics.checkMap(holderCompanyBase.get("address"));
            holderTaxIdentificationNumber = (String) holderCompany.get("taxIdentificationNumber");
        }


        List<String> keys = findConflicting(ownerNodeId, authorizationKey, partyIdFrom, number, holderTaxIdentificationNumber, null, typeId, statusToExclude);

        if (keys.size() > 0) {
            StringBuilder error = new StringBuilder();
            error.append("Already exists an Authorization with the given logical key: [partyIdFrom=");
            error.append(partyIdFrom);
            //error.append(" partyNodeTo address=");
            //error.append(address.get("address1")).append(" ").append(address.get("streetNumber")).append(" ").append(address.get("village"));
            error.append(" partyNodeTo taxIdentificationNumber=");
            error.append(holderTaxIdentificationNumber);
            error.append(" (case insensitive)number=");
            error.append(number);
            error.append(" ownerNodeId=");
            error.append(ownerNodeId);
            throw new GnServiceException(OfbizErrors.DUPLICATED_AUTHORIZATION, error.toString());
        }


        if (UtilValidate.areEqual(typeId, AuthorizationTypesOfbiz.GN_AUTH_ALBO)) {
            List<String> result = findDuplicatedAlboAuthorization(typeId, holderTaxIdentificationNumber, ownerNodeId, partyIdFrom, statusToExclude);
            if (result.size() > 0) {
                String authorizationKeyFoundS = result.get(0);
                AuthorizationKey authorizationKeyFound = AuthorizationKey.fromString(authorizationKeyFoundS);
                AuthorizationKey authKey = authorizationKey == null ? null : AuthorizationKey.fromString(authorizationKey);
                if (authorizationKeyFound != null && (authKey == null || !authorizationKeyFound.getUuid().equals(authKey.getUuid()))) {
                    StringBuilder alboError = new StringBuilder();
                    alboError.append("Already exists an Albo Authorization on the same node with the given taxIdentificationNumber [");
                    alboError.append(holderTaxIdentificationNumber);
                    alboError.append("]");

                    List<EntityCondition> conds = FastList.newInstance();
                    conds.add(EntityCondition.makeCondition("taxIdentificationNumber", holderTaxIdentificationNumber));
                    conds.add(EntityCondition.makeCondition("nodeType", PartyNodeTypeOfbiz.PRIVATE_COMPANY.name()));
                    List<GenericValue> res = delegator.findList("GnPartyNode", EntityCondition.makeCondition(conds), UtilMisc.toSet("name"), null, null, false);
                    String companyName = null;
                    if (res.size() > 0) {
                        companyName = res.get(0).getString("name");
                    }
                    throw new GnServiceException(OfbizErrors.DUPLICATED_ALBO_AUTHORIZATION, alboError.toString(), companyName);
                }
            }

        } else if (UtilValidate.areEqual(typeId, AuthorizationTypesOfbiz.GN_AUTH_AIA)) {
            checkAiaAuaDuplicatedAuthorization(ownerNodeId, typeId, holderCompanyName, holderCompanyBase, authorizationKey == null ? null : AuthorizationKey.fromString(authorizationKey).getUuid(), OfbizErrors.DUPLICATED_AIA_AUTHORIZATION, false);

        } else if (UtilValidate.areEqual(typeId, AuthorizationTypesOfbiz.GN_AUTH_AUA)) {
            checkAiaAuaDuplicatedAuthorization(ownerNodeId, typeId, holderCompanyName, holderCompanyBase, authorizationKey == null ? null : AuthorizationKey.fromString(authorizationKey).getUuid(), OfbizErrors.DUPLICATED_AUA_AUTHORIZATION, false);
        }

        return false;
    }

    /**
     * GREEN-1002 : check DUPLICATED_ALBO_AUTHORIZATION
     *
     * @param type
     * @param holderTaxIdentificationNumber
     * @param ownerNodeId
     * @param partyIdFrom
     * @param statusToExclude
     * @return
     * @throws GnServiceException
     * @throws GenericEntityException
     */
    public List<String> findDuplicatedAlboAuthorization(AuthorizationTypesOfbiz type, String holderTaxIdentificationNumber, String ownerNodeId, String partyIdFrom, List<String> statusToExclude) throws GnServiceException, GenericEntityException {
        List<String> result = new ArrayList<String>();
        if (UtilValidate.isEmpty(type))
            throw new GnServiceException(OfbizErrors.INVALID_PARAMETERS, "typeId cannot be empty");

        if (AuthorizationTypesOfbiz.GN_AUTH_ALBO.equals(type)) {

            DynamicViewEntity view = new DynamicViewEntity();
            view.addMemberEntity("AU", "GnAuthorizationAndAgreement");
            view.addMemberEntity("PN", "GnPartyGroupPartyNode");
            view.addViewLink("AU", "PN", false, UtilMisc.toList(new ModelKeyMap("otherPartyIdTo", "partyId")));

            view.addAlias("AU", "agreementTypeId");
            view.addAlias("AU", "statusId");
            view.addAlias("AU", "internalStatusId");
            view.addAlias("AU", "authorizationKey");
            view.addAlias("AU", "uuid");
            view.addAlias("AU", "typeId");
            view.addAlias("AU", "ownerNodeId");
            view.addAlias("AU", "otherPartyIdTo");
            view.addAlias("AU", "partyIdTo");
            view.addAlias("AU", "partyIdFrom");
            view.addAlias("AU", "number");
            view.addAlias("PN", "nodeType");
            view.addAlias("PN", "taxIdentificationNumber");
            view.addAlias("PN", "partyId");

            List<EntityCondition> conds = new ArrayList<EntityCondition>();
            conds.add(EntityCondition.makeCondition("agreementTypeId", AgreementTypesOfbiz.GN_AUTHORIZATION.name()));
            conds.add(EntityCondition.makeCondition("nodeType", PartyNodeTypeOfbiz.PRIVATE_COMPANY.name()));
            conds.add(EntityCondition.makeCondition("taxIdentificationNumber", holderTaxIdentificationNumber));
            conds.add(EntityCondition.makeCondition("ownerNodeId", ownerNodeId));
            conds.add(EntityCondition.makeCondition("typeId", type.name()));
            conds.add(GnFindUtil.makeIsEmptyCondition("internalStatusId"));
            if (UtilValidate.isNotEmpty(statusToExclude)) {
                conds.add(EntityCondition.makeCondition("statusId", EntityComparisonOperator.NOT_IN, statusToExclude));
            }
            EntityListIterator iterator = delegator.findListIteratorByCondition(view, EntityCondition.makeCondition(conds), null, null, /*UtilMisc.toList("partyId ASC")*/null, GnFindUtil.getFindOptDistinct());
            List<GenericValue> gvs = iterator.getCompleteList();
            for (GenericValue auth : gvs) {
                result.add(auth.getString("authorizationKey"));
            }
            iterator.close();
        }
        return result;
    }

    /**
     * GREEN-1076 : check DUPLICATED_AIA_AUTHORIZATION, DUPLICATED_AUA_AUTHORIZATION
     *
     * @param ownerNodeId
     * @param type
     * @param holderCompanyName
     * @param holderCompanyBase
     * @param uuid
     * @param errorKind
     * @return
     * @throws GenericEntityException
     * @throws GenericServiceException
     */
    public List<String> checkAiaAuaDuplicatedAuthorization(String ownerNodeId, AuthorizationTypesOfbiz type, String holderCompanyName, Map<String, Object> holderCompanyBase, String uuid, OfbizErrors errorKind, boolean draftTolerant) throws GenericEntityException, GenericServiceException {
        List<String> authIds = new ArrayList<String>();
        try {
            List<EntityCondition> conds = new ArrayList<EntityCondition>();
            conds.add(GnFindUtil.makeOrConditionById("statusId", UtilMisc.toList(AuthorizationStatusOfbiz.GN_AUTH_PUBLISHED.name(), AuthorizationStatusOfbiz.GN_AUTH_DRAFT.name())));
            conds.add(GnFindUtil.makeIsEmptyCondition("internalStatusId"));
            conds.add(EntityCondition.makeCondition("ownerNodeId", ownerNodeId));
            conds.add(EntityCondition.makeCondition("typeId", type.name()));
            conds.add(EntityCondition.makeCondition("parentVersion", "0"));
            if (UtilValidate.isNotEmpty(uuid))
                conds.add(EntityCondition.makeCondition("uuid", EntityOperator.NOT_EQUAL, uuid));
            List<GenericValue> result = delegator.findList("GnAuthorizationAndAgreement", EntityCondition.makeCondition(conds), UtilMisc.toSet("agreementId", "authorizationKey"), UtilMisc.toList("agreementId"), null, false);
            if (result.size() > 0) {
                Map<String, Object> companyBaseAddress = UtilGenerics.checkMap(holderCompanyBase.get("address"));
                AddressMatcher addressMatcher = new AddressMatcher((String) companyBaseAddress.get("address1"),
                        (String) companyBaseAddress.get("streetNumber"), (String) companyBaseAddress.get("village"));
                for (GenericValue gv : result) {
                    Map<String, Object> conflictingAuth = findAuthorizationById(gv.getString("agreementId"), null, true, false);
                    Map<String, Object> conflictingCompanyBase = UtilGenerics.checkMap(conflictingAuth.get("partyNodeTo"));
                    Map<String, Object> conflictingCompanyBaseAddress = UtilGenerics.checkMap(conflictingCompanyBase.get("address"));
                    //compare address with fuzzy logic
                    if (UtilValidate.areEqual(companyBaseAddress.get("postalCodeGeoId"), conflictingCompanyBaseAddress.get("postalCodeGeoId")) &&
                            addressMatcher.match((String) conflictingCompanyBaseAddress.get("address1"), (String) conflictingCompanyBaseAddress.get("streetNumber"), (String) conflictingCompanyBaseAddress.get("village"))
                            ) {
                        if (draftTolerant && AuthorizationStatusOfbiz.GN_AUTH_DRAFT.name().equals(gv.get("statusId"))) {
                            authIds.add(gv.getString("authorizationKey"));
                        } else {
                            throw new GnServiceException(errorKind, "Already exists an authorization with typeId " + type.name() +
                                    " on the same node with holder '" + holderCompanyName + " - " + holderCompanyBase.get("description") + "'",
                                    holderCompanyName + " - " + holderCompanyBase.get("description"));
                        }
                    }
                }
            }
        } catch (TokenizerException e) {
            throw new GnServiceException(OfbizErrors.GENERIC, "Unable to evaluate address", e);
        } catch (ComparerException e) {
            throw new GnServiceException(OfbizErrors.GENERIC, "Unable to evaluate address", e);
        }
        return authIds;
    }

    /**
     * @param authorizationKey
     * @param partyIdFrom
     * @param partyIdTo
     * @param number
     * @param holderTaxIdentificationNumber
     * @param address
     * @return authorization keys list
     * @throws GenericServiceException
     * @throws GenericEntityException
     */
    public List<String> findAuthorizationByLogicalKey(String authorizationKey, String partyIdFrom, String partyIdTo,
                                                      String number, String holderTaxIdentificationNumber,
                                                      Map<String, Object> address, AuthorizationTypesOfbiz typeId) throws GenericServiceException, GenericEntityException {
        Map<String, Object> gnContext = getCurrentContext();
        Map<String, Object> contextNode = UtilGenerics.checkMap(gnContext.get("partyNode"));
        String ownerNodeId = (String) contextNode.get("partyId");

        //if partyIdTo is a company base, next invoked services will copy data from it therefore should be used it to find conflict
        if (UtilValidate.isNotEmpty(partyIdTo) && gnPartyRoleCheck(partyIdTo, PartyRoleOfbiz.GN_COMPANY_BASE.name())) {
            Debug.log(String.format("Overwrite incoming holder address and taxIdentificationNumber with data coming from companyBase[%s]", partyIdTo), module);

            Map<String, Object> holderCompanyBase = findPartyNodeById(partyIdTo);
            Map<String, Object> holderCompany = PartyNodeUtil.getOwner(holderCompanyBase);
            address.clear();
            address.putAll(UtilGenerics.<String, Object>checkMap(holderCompanyBase.get("address")));
            holderTaxIdentificationNumber = (String) holderCompany.get("taxIdentificationNumber");
        }


        List<String> keys = findConflicting(ownerNodeId, authorizationKey, partyIdFrom, number, holderTaxIdentificationNumber, null, typeId, null);

        return keys;
    }

    /**
     * @param ownerNodeId
     * @param authorizationKey
     * @param partyIdFrom
     * @param number
     * @param holderTaxIdentificationNumber
     * @return
     * @throws GenericServiceException
     * @throws GenericEntityException
     */
    protected List<String> findConflicting(String ownerNodeId, String authorizationKey,
                                           String partyIdFrom, String number, String holderTaxIdentificationNumber,
                                           AuthorizationStatusOfbiz statusId, AuthorizationTypesOfbiz typeId, List<String> statusToExclude) throws GenericServiceException, GenericEntityException {
        //todo: pass auth status, allow key empty
        Debug.log("Find conflicted authorization", module);
        if (UtilValidate.isEmpty(holderTaxIdentificationNumber))
            throw new GnServiceException(OfbizErrors.INVALID_PARAMETERS, "taxIdentificationNumber cannot be empty");

        String uuid = null;
        if (UtilValidate.isNotEmpty(authorizationKey)) {
            AuthorizationKey authKey = AuthorizationKey.fromString(authorizationKey);
            uuid = authKey.getUuid();
        }

        DynamicViewEntity view = new DynamicViewEntity();
        view.addMemberEntity("AU", "GnAuthorizationAndAgreement");
        view.addMemberEntity("PN", "GnPartyGroupPartyNode");
        view.addViewLink("AU", "PN", false, UtilMisc.toList(new ModelKeyMap("otherPartyIdTo", "partyId")));

        view.addAlias("AU", "agreementTypeId");
        view.addAlias("AU", "statusId");
        view.addAlias("AU", "internalStatusId");
        view.addAlias("AU", "authorizationKey");
        view.addAlias("AU", "uuid");
        view.addAlias("AU", "typeId");
        view.addAlias("AU", "ownerNodeId");
        view.addAlias("AU", "otherPartyIdTo");
        view.addAlias("AU", "partyIdTo");
        view.addAlias("AU", "partyIdFrom");
        view.addAlias("AU", "number");
        view.addAlias("PN", "nodeType");
        view.addAlias("PN", "taxIdentificationNumber");
        view.addAlias("PN", "partyId");

        List<EntityCondition> conds = new ArrayList<EntityCondition>();
        conds.add(EntityCondition.makeCondition("agreementTypeId", AgreementTypesOfbiz.GN_AUTHORIZATION.name()));
        conds.add(EntityCondition.makeCondition("nodeType", PartyNodeTypeOfbiz.PRIVATE_COMPANY.name()));
        if (statusId != null)
            conds.add(EntityCondition.makeCondition("statusId", statusId.name()));
        conds.add(EntityCondition.makeCondition("taxIdentificationNumber", holderTaxIdentificationNumber));
        conds.add(EntityCondition.makeCondition("ownerNodeId", ownerNodeId));
        conds.add(EntityCondition.makeCondition("partyIdFrom", partyIdFrom));
        conds.add(EntityCondition.makeCondition("typeId", typeId.name()));
        conds.add(GnFindUtil.makeIsEmptyCondition("internalStatusId"));
        // sql number condition is removed and replaced by code check
        // conds.add(EntityCondition.makeCondition(EntityFunction.UPPER_FIELD("number"), EntityComparisonOperator.EQUALS, EntityFunction.UPPER(number)));
        if (UtilValidate.isNotEmpty(uuid)) {
            conds.add(EntityCondition.makeCondition("uuid", EntityComparisonOperator.NOT_EQUAL, uuid));
        }
        if (UtilValidate.isNotEmpty(statusToExclude)) {
            conds.add(EntityCondition.makeCondition("statusId", EntityComparisonOperator.NOT_IN, statusToExclude));
        }

        EntityListIterator iterator = delegator.findListIteratorByCondition(view, EntityCondition.makeCondition(conds), null, null, /*UtilMisc.toList("partyId ASC")*/null, GnFindUtil.getFindOptDistinct());
        List<GenericValue> gvs = iterator.getCompleteList();
        List<String> result = new ArrayList<String>();



        for (GenericValue auth : gvs) {
            //code check remove
            if (auth.getString("number").replaceAll("[\\W,_]", "").equalsIgnoreCase(number.replaceAll("[\\W,_]", "")))//todo remove not number e not string
            result.add(auth.getString("authorizationKey"));
        }
        iterator.close();
        /*for (GenericValue auth : gvs) {
            Map<String, Object> pvtCompanyBase = findPartyNodeById((String) auth.get("partyIdTo"));
            Map<String, Object> address = UtilGenerics.toMap(pvtCompanyBase.get("address"));

            AddressMatcher addressMatcher = new AddressMatcher(address1, streetNumber, village);

            try {
                //compare address with fuzzy logic
                if (UtilValidate.areEqual(postalCodeGeoId, address.get("postalCodeGeoId")) &&
                        addressMatcher.match((String) address.get("address1"), (String) address.get("streetNumber"), (String) address.get("village"))
                        ) {
                    Debug.log(String.format("Authorization %s conflict with %s", authorizationKey, auth.getString("authorizationKey")), module);
                    result.add(auth.getString("authorizationKey"));
                }
            } catch (TokenizerException e) {
                throw new GnServiceException(OfbizErrors.GENERIC, "Unable to evaluate address", e);
            } catch (ComparerException e) {
                throw new GnServiceException(OfbizErrors.GENERIC, "Unable to evaluate address", e);
            }
        }*/
        return result;
    }

    /**
     * Creates a new {@code GnAuthorization}
     * Possible AuthorizationItems will be ignored
     *
     * @param partyIdFrom
     * @param partyNodeTo      companyBase
     * @param otherPartyNodeTo company
     * @param typeId
     * @param number
     * @param textData
     * @param description
     * @param isPrivate
     * @return
     * @throws GenericEntityException
     * @throws GenericServiceException
     */
    public Map<String, Object> gnCreateAuthorization(String partyIdFrom, Map<String, Object> partyNodeTo, Map<String, Object> otherPartyNodeTo,
                                                     String typeId, String number, String textData, String description, String originId, String isPrivate) throws GenericEntityException, GenericServiceException {
        String partyIdTo = (String) partyNodeTo.get("partyId");

        Map<String, Object> publicationNode = UtilGenerics.checkMap(getCurrentContext().get("partyNode"));
        canCreateAuthorization(partyIdTo, publicationNode, isPrivate);

        //authorization key
        String statusId = AuthorizationStatusOfbiz.GN_AUTH_DRAFT.name();
        String ownerNodeId = (String) publicationNode.get("partyId");
        String ownerNodeKey = (String) publicationNode.get("nodeKey");
        AuthorizationKey authKey = generateAuthorizationKey(ownerNodeKey);

        ///managing company and base copy
        AuthorizationPartyNodeHelper authorizationPartyNodeHelper = new AuthorizationPartyNodeHelper(dctx, context);
        Map<String, Object> pvtCompanyBase = new HashMap<String, Object>(partyNodeTo);
        String pvtCompanyBaseId = getPartyId(pvtCompanyBase);
        String originPartyIdTo = null;

        Map<String, Object> pvtCompany = (otherPartyNodeTo != null) ? new HashMap<String, Object>(otherPartyNodeTo) : new HashMap<String, Object>();
        if (!"Y".equals(isPrivate)) {// if auth is public
            if (UtilValidate.isNotEmpty(pvtCompanyBaseId) && gnPartyRoleCheck(pvtCompanyBaseId, PartyRoleOfbiz.GN_COMPANY_BASE.name())) {
                //copy data from original companyBase
                originPartyIdTo = pvtCompanyBaseId;
                //copy data from original companyBase
                pvtCompanyBase = findPartyNodeById(pvtCompanyBaseId);

                pvtCompany = PartyNodeUtil.getOwner(pvtCompanyBase);
                String pvtCompanyId = getPartyId((String) pvtCompany.get("partyId"), (String) pvtCompany.get("nodeKey"));
                if (UtilValidate.isNotEmpty(pvtCompanyId)) {
                    //copy data from original company
                    pvtCompany = findPartyNodeById(pvtCompanyId);
                }
            } else {
                throw new GnServiceException(OfbizErrors.INVALID_PARAMETERS, "public authorization accept only CompanyBase and Company for holder");
            }
        }

        pvtCompany.put("partyId", null);
        pvtCompany.put("nodeKey", null);
        pvtCompanyBase.put("partyId", null);
        pvtCompanyBase.put("nodeKey", null);
        UtilGenerics.checkMap(pvtCompanyBase.get("address")).put("contactMechId", null);

        pvtCompanyBase.put("partyRelationships", new ArrayList<Map<String, Object>>());

        //partyIdTo is updated with id of privateCompany created
        Map<String, String> partyIdsTo = authorizationPartyNodeHelper.createUpdateReceivedCompanyAndCompanyBase(ownerNodeKey, pvtCompanyBase, pvtCompany);

        //----- managing company and base copy end
        Map<String, Object> authorization = createAuthorization(authKey, ownerNodeId, partyIdsTo.get("companyBasePartyId"), partyIdsTo.get("companyPartyId")
                , partyIdFrom, typeId, number, statusId, null, description, textData, originId, originPartyIdTo, isPrivate, UtilDateTime.nowTimestamp());

        Map<String, Object> savedAuthorization = findAuthorizationById((String) authorization.get("agreementId"), null, true, false);
        gnSendCommunicationEventToContactList((String) savedAuthorization.get("ownerNodeId"), EventTypeOfbiz.GN_COM_EV_AUTH_CRE.name(), savedAuthorization);
        return authorization;
    }


    /**
     * Creates a new {@code GnAuthorization} with related AuthorizationItems.
     *
     * @param ownerNodeKey
     * @param originId
     * @param ownerPartyId
     * @param partyNodeTo
     * @param partyIdFrom
     * @param typeId
     * @param number
     * @param textData
     * @param description
     * @param items
     * @return
     * @throws GenericEntityException
     * @throws GenericServiceException
     */
    public Map<String, Object> bulkImportAuthorizationWithItems(String ownerNodeKey, String originId, String ownerPartyId,
                                                                Map<String, Object> partyNodeTo, Map<String, Object> otherPartyNodeTo, String partyIdFrom,
                                                                String typeId, String number, String textData, String description,
                                                                List<Map<String, Object>> items, String isPrivate) throws GenericEntityException, GenericServiceException {

        //authorization key
        String statusId = AuthorizationStatusOfbiz.GN_AUTH_DRAFT.name();
        AuthorizationKey authKey = generateAuthorizationKey(ownerNodeKey);

        ///managing company and base copy
        AuthorizationPartyNodeHelper authorizationPartyNodeHelper = new AuthorizationPartyNodeHelper(dctx, context);
        Map<String, Object> pvtCompanyBase = new HashMap<String, Object>(partyNodeTo);
        String pvtCompanyBaseId = getPartyId((String) pvtCompanyBase.get("partyId"), (String) pvtCompanyBase.get("nodeKey"));
        String originPartyIdTo = null;
        Map<String, Object> pvtCompany = otherPartyNodeTo;

        if (UtilValidate.isNotEmpty(pvtCompanyBaseId) && gnPartyRoleCheck(pvtCompanyBaseId, PartyRoleOfbiz.GN_COMPANY_BASE.name())) {
            //copy data from original companyBase
            originPartyIdTo = pvtCompanyBaseId;
            pvtCompanyBase = findPartyNodeById(pvtCompanyBaseId);
            //copy data from original companyBase
            pvtCompany = PartyNodeUtil.getOwner(pvtCompanyBase);
        }

        pvtCompany.put("partyId", null);
        pvtCompany.put("nodeKey", null);
        pvtCompanyBase.put("partyId", null);
        pvtCompanyBase.put("nodeKey", null);
        (UtilGenerics.checkMap(pvtCompanyBase.get("address"))).put("contactMechId", null);
        pvtCompanyBase.put("partyRelationships", FastList.newInstance());
        Map<String, String> partyIdsTo = authorizationPartyNodeHelper.createUpdateReceivedCompanyAndCompanyBase(ownerNodeKey, pvtCompanyBase, pvtCompany);
        //-----------------

        //create authorization imported
        Map<String, Object> authorizationKeysMap = createAuthorization(authKey, ownerPartyId, partyIdsTo.get("companyBasePartyId"), partyIdsTo.get("companyPartyId"), partyIdFrom,
                typeId, number, statusId, null, description, textData, originId, originPartyIdTo, isPrivate, UtilDateTime.nowTimestamp());

        String agreementId = (String) authorizationKeysMap.get("agreementId");
        String authorizationKey = (String) authorizationKeysMap.get("authorizationKey");
        Map<String, Object> savedAuthorization = findAuthorizationById(agreementId, null, true, false);

        //create items' authorization imported
        if (UtilValidate.isNotEmpty(items)) {
            for (Map<String, Object> item : items) {
                String agreementText = (String) item.get("agreementText");
                String simplified = (String) item.get("simplified");
                String holderRoleId = (String) item.get("holderRoleId");
                String categoryClassEnumId = (String) item.get("categoryClassEnumId");

                List<Map<String, Object>> agreementTerms = UtilGenerics.checkList(item.get("agreementTerms"));

                Timestamp validFromDate = getTimestamp(item.get("validFromDate"));
                Timestamp validThruDate = getTimestamp(item.get("validThruDate"));
                Timestamp suspendFromDate = getTimestamp(item.get("suspendFromDate"));
                Timestamp suspendThruDate = getTimestamp(item.get("suspendThruDate"));

                Map<String, Object> detailKeysMap = new AuthorizationItemHelper(dctx, context).createAuthorizationItem(agreementId, authorizationKey, null, agreementText, simplified, holderRoleId, categoryClassEnumId, validFromDate, validThruDate, suspendFromDate, suspendThruDate, agreementTerms, typeId);
            }
        }

        gnSendCommunicationEventToContactList(ownerPartyId, EventTypeOfbiz.GN_COM_EV_AUTH_IMP.name(), savedAuthorization);

        return authorizationKeysMap;
    }


    private Timestamp getTimestamp(Object dateOrTimestamp) {
        if (dateOrTimestamp instanceof Date)
            return new Timestamp(((Date) dateOrTimestamp).getTime());
        else
            return (Timestamp) dateOrTimestamp;
    }

    /**
     * Generate an authorizationKey and check that doesn't exist already on db;
     *
     * @return authorizationKey generated
     * @throws GenericEntityException
     */
    protected AuthorizationKey generateAuthorizationKey(String ownerNodeKey) throws GenericEntityException {

        String uuid = UUID.randomUUID().toString() + "@" + SysUtil.getInstanceId();
        //String ownerNodeKey = "GN_ROOT@memelabs";
        String lastModifierNodeKey = ownerNodeKey;
        AuthorizationKey authKey = AuthorizationKey.newInstance(uuid, lastModifierNodeKey, ownerNodeKey, 1l, "0");
        if (delegator.findByAnd("GnAuthorization", UtilMisc.toMap("authorizationKey", authKey.toString())).size() > 0) {
            Debug.logWarning("An authorization with key[" + authKey.toString() + "] already exist.", module);
            return generateAuthorizationKey(ownerNodeKey);
        } else
            return authKey;
    }

    /**
     * @param agreementId
     * @param authorizationKey
     * @param partyIdFrom
     * @param partyNodeTo
     * @param otherPartyNodeTo
     * @param number
     * @param textData
     * @param description
     * @return
     * @throws GenericServiceException
     * @throws GenericEntityException
     */
    public Map<String, Object> gnUpdateAuthorization(String agreementId, String authorizationKey, String partyIdFrom,
                                                     Map<String, Object> partyNodeTo, Map<String, Object> otherPartyNodeTo,
                                                     String number, String textData, String description, String typeId,
                                                     String originId, String isPrivate) throws GenericServiceException, GenericEntityException {


        GenericValue auth = canUpdateAuthorization(agreementId, authorizationKey, partyIdFrom, (String) partyNodeTo.get("partyId"));


        agreementId = auth.getString("agreementId");
        String pvtCompanyBaseId = (String) auth.get("partyIdTo");
        String pvtCompanyId = (String) auth.get("otherPartyIdTo");

        ///managing company and base copy
        AuthorizationPartyNodeHelper authorizationPartyNodeHelper = new AuthorizationPartyNodeHelper(dctx, context);
        Map<String, Object> pvtCompanyBase = new HashMap<String, Object>(partyNodeTo);
        String inputCompanyBaseId = getPartyId((String) pvtCompanyBase.get("partyId"), (String) pvtCompanyBase.get("nodeKey"));

        Map<String, Object> pvtCompany = otherPartyNodeTo;
        //if party role id GN_COMPANY_BASE copy data
        String originPartyIdTo = null;

        if ("Y".equals(isPrivate)) {  //private auth
            pvtCompanyBase.put("partyId", pvtCompanyBaseId);
            pvtCompanyBase.put("nodeKey", getNodeKey(pvtCompanyBaseId, null));
            UtilGenerics.checkMap(pvtCompanyBase.get("address")).put("contactMechId", null);

            pvtCompany.put("partyId", pvtCompanyId);
            pvtCompany.put("nodeKey", getNodeKey(pvtCompanyId, null));
            originPartyIdTo = null;

        } else { //public auth
            if (UtilValidate.isNotEmpty(inputCompanyBaseId) && gnPartyRoleCheck(inputCompanyBaseId, PartyRoleOfbiz.GN_COMPANY_BASE.name())) {
                Debug.log(String.format("Coping Company and CompanyBase attributes from %s", inputCompanyBaseId), module);
                //copy data from original companyBase
                pvtCompanyBase = findPartyNodeById(inputCompanyBaseId);
                originPartyIdTo = inputCompanyBaseId;

                pvtCompanyBase.put("partyId", pvtCompanyBaseId);
                pvtCompanyBase.put("nodeKey", getNodeKey(pvtCompanyBaseId, null));
                UtilGenerics.checkMap(pvtCompanyBase.get("address")).put("contactMechId", null);
                pvtCompany = PartyNodeUtil.getOwner(pvtCompanyBase);
                pvtCompany.put("partyId", pvtCompanyId);
                pvtCompany.put("nodeKey", getNodeKey(pvtCompanyId, null));
                pvtCompanyBase.put("partyRelationships", FastList.newInstance());
            } else {
                throw new GnServiceException(OfbizErrors.INVALID_PARAMETERS,
                        "public authorization accept only CompanyBase and Company for holder");
            }
        }

        authorizationPartyNodeHelper.createUpdateReceivedCompanyAndCompanyBase((String) auth.get("ownerNodeKey"), pvtCompanyBase, pvtCompany);
        Map<String, Object> result = updateAuthorization(agreementId, partyIdFrom, pvtCompanyBaseId, pvtCompanyId,
                typeId, number, textData, description, originId, originPartyIdTo, isPrivate);

        Map<String, Object> savedAuthorization = findAuthorizationById((String) result.get("agreementId"), null, true, false);
        gnSendCommunicationEventToContactList((String) savedAuthorization.get("ownerNodeId"), EventTypeOfbiz.GN_COM_EV_AUTH_MOD.name(), savedAuthorization);
        return result;

    }

    /**
     * Updates a an existent {@code GnAuthorization}
     * Possible AuthorizationItems will be ignored
     *
     * @param agreementId
     * @param partyIdFrom
     * @param partyIdTo
     * @param typeId
     * @param number
     * @param textData
     * @param description
     * @param originPartyIdTo
     * @param isPrivate
     * @return
     * @throws GenericEntityException
     * @throws GenericServiceException
     */
    public Map<String, Object> updateAuthorization(String agreementId, String partyIdFrom,
                                                   String partyIdTo, String otherPartyIdTo,
                                                   String typeId, String number, String textData, String description, String originId,
                                                   String originPartyIdTo, String isPrivate) throws GenericEntityException, GenericServiceException {
        List<GenericValue> toBeStored = FastList.newInstance();
        GenericValue agreement = delegator.findOne("Agreement", UtilMisc.toMap("agreementId", agreementId), false);
        //validation
        agreement.put("agreementId", agreementId);
        agreement.put("description", description);
        agreement.put("partyIdFrom", partyIdFrom);
        agreement.put("partyIdTo", partyIdTo);
        agreement.put("textData", textData);

        //check issuer and holder
        if (gnPartyRoleCheck(partyIdTo, PartyRoleOfbiz.GN_PVT_COMPANY_BASE.name())) {
            agreement.put("roleTypeIdTo", PartyRoleOfbiz.GN_PVT_COMPANY_BASE.name());
        } else
            throw new GnServiceException(OfbizErrors.INVALID_PARAMETERS, "partyIdTo is not a PrivateCompanyBase");

        if (gnPartyRoleCheck(partyIdFrom, PartyRoleOfbiz.GN_ISSUER_BASE.name())) {
            agreement.put("roleTypeIdFrom", PartyRoleOfbiz.GN_ISSUER_BASE.name());
        } else
            throw new GnServiceException(OfbizErrors.INVALID_PARAMETERS, "partyIdTo is not an IssuerBase");


        toBeStored.add(agreement);

        //Fill authorization
        GenericValue authorization = delegator.findOne("GnAuthorization", UtilMisc.toMap("agreementId", agreementId), false);
        //todo: GN_AUTH_TO_BE_VALID must be copied before update
        //manage update of authorization in state GN_AUTH_TO_BE_VALID
        /*AuthorizationKey key = AuthorizationKey.fromString(authorization.getString("authorizationKey"));
        if (AuthorizationStatusOfbiz.GN_AUTH_TO_BE_VALID.name().equals(authorization.getString("statusId"))
                //is not already modified from owner node
                && !UtilValidate.areEqual(key.getOwnerNodeKey(), key.getLastModifierNodeKey())) {
            //key.setParentVersion("" + key.getVersion());
            key.setVersion(1l);
            key.setLastModifierNodeKey(key.getOwnerNodeKey());
            authorization.setNonPKFields(key.toMap());
            authorization.set("authorizationKey", key.toString());
        }*/
        authorization.set("typeId", typeId);
        authorization.set("number", number);
        authorization.set("originId", originId);
        authorization.set("originPartyIdTo", originPartyIdTo);

        authorization.set("agreementId", agreementId);
        authorization.set("isPrivate", isPrivate);
        authorization.set("lastModifiedDate", UtilDateTime.nowTimestamp());
        authorization.set("lastModifiedByUserLogin", userLogin.getString("userLoginId"));
        authorization.set("otherPartyIdTo", otherPartyIdTo);

        if (UtilValidate.isNotEmpty(originPartyIdTo)) {
            if (gnPartyRoleCheck(originPartyIdTo, PartyRoleOfbiz.GN_COMPANY_BASE.name())) {
                authorization.set("originRoleTypeIdTo", PartyRoleOfbiz.GN_COMPANY_BASE.name());
            } else
                throw new GnServiceException(OfbizErrors.INVALID_PARAMETERS, "originPartyIdTo is not an CompanyBase");
        }

        if (gnPartyRoleCheck(otherPartyIdTo, PartyRoleOfbiz.GN_PVT_COMPANY.name())) {
            authorization.set("otherRoleTypeIdTo", PartyRoleOfbiz.GN_PVT_COMPANY.name());
        } else
            throw new GnServiceException(OfbizErrors.INVALID_PARAMETERS, "otherPartyIdTo is not a PrivateCompany");


        toBeStored.add(authorization);

        delegator.storeAll(toBeStored);
        Map<String, Object> result = FastMap.newInstance();
        result.put("agreementId", agreementId);
        result.put("authorizationKey", authorization.getString("authorizationKey"));
        return result;
    }

    /**
     * Find {@code GnAuthorization} by primary key
     *
     * @param agreementId      primary key
     * @param authorizationKey alternative global unique id
     * @param loadNodes        if true loads linked nodes
     * @param loadDetails      if true loads {@code GnAuthorizationItem} and {@code GnAuthorizationTerm} entities
     * @return an authorization
     * @throws GenericEntityException
     * @throws GenericServiceException
     */
    public Map<String, Object> gnFindAuthorizationById(String agreementId, String authorizationKey, boolean loadNodes, boolean loadDetails) throws GenericEntityException, GenericServiceException {
        Map<String, Object> auth = findAuthorizationById(agreementId, authorizationKey, loadNodes, loadDetails);
        if (UtilValidate.isEmpty(auth)) {
            AuthorizationKey key;
            try {
                key = AuthorizationKey.fromString(authorizationKey);
            } catch (Exception e) {
                key = null;
            }
            if (key != null) {
                Map<String, Object> currentAuth = gnFindAuthorizationByUuid(key.getUuid(), loadNodes, loadDetails);
                String currentAuthKey = (String) currentAuth.get("authorizationKey");
                if (currentAuthKey != null && !currentAuthKey.isEmpty())
                    throw new GnServiceException(OfbizErrors.AUTHORIZATION_OUTDATED,
                            "Authorization[" + authorizationKey + "] outdated. There's a more recent version with key [" + currentAuthKey + "]", currentAuthKey);
            }

            throw new GnServiceException(OfbizErrors.AUTHORIZATION_NOT_FOUND,
                    "Authorization[" + authorizationKey + "] not found.");
        }
        List<String> userLoginSourceIds = findLoginSourceIds(userLogin);
        if (UtilValidate.isNotEmpty(auth) && !userLoginSourceIds.contains(LoginSourceOfbiz.GN_LOG_SRC_INTERNAL.name())) {
            String contextPartyNodeId = (String) (UtilGenerics.checkMap(getCurrentContext().get("partyNode"))).get("partyId");
            GnSecurity gnSecurity = new GnSecurity(delegator);
            if (gnSecurity.hasPermission(PermissionsOfbiz.GN_AUTH_ROOT_VIEW, userLogin) && !gnSecurity.hasPermission(PermissionsOfbiz.GN_AUTH_VIEW, userLogin)) {
                if (!auth.get("ownerNodeId").equals("GN_ROOT"))
                    throw new GnServiceException(OfbizErrors.ACCESS_DENIED, "You cannot view this Authorization[" + authorizationKey + "].");
            } else if (gnSecurity.hasPermission(PermissionsOfbiz.GN_AUTH_VIEW, userLogin) && !gnSecurity.hasPermission(PermissionsOfbiz.GN_AUTH_ROOT_VIEW, userLogin)) {
                if (!auth.get("ownerNodeId").equals(contextPartyNodeId))
                    throw new GnServiceException(OfbizErrors.ACCESS_DENIED, "You cannot view this Authorization[" + authorizationKey + "].");
            } else {
                if (!auth.get("ownerNodeId").equals(contextPartyNodeId) && !auth.get("ownerNodeId").equals("GN_ROOT"))
                    throw new GnServiceException(OfbizErrors.ACCESS_DENIED, "You cannot view this Authorization[" + authorizationKey + "].");
            }
        }
        return auth;
    }

    /**
     * @param uuid
     * @param loadNodes
     * @param loadDetails
     * @return
     * @throws GenericEntityException
     * @throws GenericServiceException
     */
    public Map<String, Object> gnFindAuthorizationByUuid(String uuid, boolean loadNodes, boolean loadDetails) throws GenericEntityException, GenericServiceException {
        List<EntityCondition> conds = new ArrayList<EntityCondition>();
        conds.add(EntityCondition.makeCondition("uuid", uuid));
        conds.add(EntityCondition.makeCondition("statusId", AuthorizationStatusOfbiz.GN_AUTH_PUBLISHED.name()));
        conds.add(GnFindUtil.makeIsEmptyCondition("internalStatusId"));
        conds.add(EntityCondition.makeCondition("ownerNodeId", UtilGenerics.checkMap(getCurrentContext().get("partyNode")).get("partyId")));
        List<GenericValue> auths = delegator.findList("GnAuthorizationAndAgreement", EntityCondition.makeCondition(conds), UtilMisc.toSet("agreementId", "authorizationKey"), null, null, false);
        if (auths.size() > 1)
            throw new GnServiceException(OfbizErrors.ENTITY_EXCEPTION, "More than one authorization found with uuid[" + uuid + "]");
        else if (auths.size() == 1)
            return gnFindAuthorizationById(auths.get(0).getString("agreementId"), null, loadNodes, loadDetails);
        else
            throw new GnServiceException(OfbizErrors.AUTHORIZATION_NOT_FOUND, "Authorization[" + uuid + "] not found.");
    }

    /**
     * @param uuid
     * @param loadNodes
     * @param loadDetails
     * @return
     * @throws GenericEntityException
     * @throws GenericServiceException
     */
    public Map<String, Object> gnGetTaxIdeNumberPartyIdToByUuid(String uuid) throws GenericEntityException, GenericServiceException {
        List<EntityCondition> conds = new ArrayList<EntityCondition>();
        conds.add(EntityCondition.makeCondition("uuid", uuid));
        conds.add(EntityCondition.makeCondition("statusId", AuthorizationStatusOfbiz.GN_AUTH_PUBLISHED.name()));
        conds.add(GnFindUtil.makeIsEmptyCondition("internalStatusId"));
        conds.add(EntityCondition.makeCondition("ownerNodeId", UtilGenerics.checkMap(getCurrentContext().get("partyNode")).get("partyId")));
        List<GenericValue> auths = delegator.findList("GnAuthorizationAndAgreement", EntityCondition.makeCondition(conds), UtilMisc.toSet("agreementId", "authorizationKey"), null, null, false);
        if (auths.size() > 1)
            throw new GnServiceException(OfbizErrors.ENTITY_EXCEPTION, "More than one authorization found with uuid[" + uuid + "]");
        else if (auths.size() == 1) {
            String agreementId = auths.get(0).getString("agreementId");
            Map<String, Object> map = gnFindAuthorizationById(agreementId, null, true, false);
            Map<String, Object> otherPartyNodeTo = (Map<String, Object>) map.get("otherPartyNodeTo");
            String taxIdentificationNumber = (otherPartyNodeTo == null) ? null : (String) otherPartyNodeTo.get("taxIdentificationNumber");
            FastMap<String, Object> result = new FastMap<String, Object>();
            result.put("taxIdentificationNumber", taxIdentificationNumber);
            return result;
        } else
            throw new GnServiceException(OfbizErrors.AUTHORIZATION_NOT_FOUND, "Authorization[" + uuid + "] not found.");
    }

    /**
     * Find {@code GnAuthorization} by primary key
     *
     * @param agreementId      primary key
     * @param authorizationKey alternative global unique id
     * @param loadNodes        if true loads linked nodes
     * @param loadDetails      if true loads {@code GnAuthorizationItem} and {@code GnAuthorizationTerm} entities
     * @return an authorization
     * @throws GenericEntityException
     * @throws GenericServiceException
     */

    protected Map<String, Object> findAuthorizationById(String agreementId, String authorizationKey, boolean loadNodes, boolean loadDetails) throws GenericEntityException, GenericServiceException {
        // AuthFastHelper authFastHelper = new AuthFastHelper(dctx, context);
        long time2 = System.currentTimeMillis();
        //  authFastHelper.findAuthorizationByIdFast(agreementId, authorizationKey, true, true);
        time2 = System.currentTimeMillis() - time2;

        long time1 = System.currentTimeMillis();
        Map<String, Object> auth = UtilMisc.makeMapWritable(findAuthorizationById(agreementId, authorizationKey));

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
        time1 = System.currentTimeMillis() - time1;

        long time3 = System.currentTimeMillis();
        // authFastHelper.findAuthorizationByIdFast(agreementId, authorizationKey, true, true);
        time3 = System.currentTimeMillis() - time3;
        long time4 = System.currentTimeMillis();
        // authFastHelper.findAuthorizationByIdFast(agreementId, authorizationKey, true, true);
        time4 = System.currentTimeMillis() - time4;

       /* Debug.log(time2 + " new");
        Debug.log(time1 + " old");
        Debug.log(time3 + " new");
        Debug.log(time4 + " new");*/
        return auth;
    }


    /**
     * Find party by primary key
     *
     * @param partyNodeId
     * @return
     * @throws GenericServiceException
     */
    public Map<String, Object> findPartyNodeById(String partyNodeId) throws GenericServiceException {
        return UtilGenerics.checkMap(dispatcher.runSync("gnInternalFindPartyNodeById", UtilMisc.toMap("partyId", partyNodeId, "userLogin", userLogin, "findRelationships", "N")).get("partyNode"));
    }

    /**
     * resolve {@code agreementId} by passed {@code authorizationKey}
     * <p/>
     * if agreement now exist return a {@GnServiceException} with error code {@link OfbizErrors#AUTHORIZATION_NOT_FOUND}
     *
     * @param authorizationKey
     * @return agreementId
     * @throws GenericEntityException
     */
    public String getAgreementId(String authorizationKey) throws GnServiceException, GenericEntityException {
        EntityFieldMap cond = EntityCondition.makeCondition(UtilMisc.toMap("authorizationKey", authorizationKey));
        List<GenericValue> auth = delegator.findList("GnAuthorization", cond, UtilMisc.toSet("agreementId"), null, null, true);
        if (auth.size() == 0)
            throw new GnServiceException(OfbizErrors.AUTHORIZATION_NOT_FOUND, "Authorization with key[" + authorizationKey + "] not found.");
        if (auth.size() > 1)
            throw new GnServiceException(OfbizErrors.ENTITY_EXCEPTION, "Found more than one Authorization with the same key");
        return auth.get(0).getString("agreementId");
    }

    /**
     * get {@code agreementId} if not empty or resolve it by {@code authorizationKey}
     * <p/>
     * if agreement now exist return a {@GnServiceException} with error code {@link OfbizErrors#AUTHORIZATION_NOT_FOUND}
     *
     * @param authorizationKey
     * @param agreementId
     * @return
     * @throws GenericEntityException
     * @throws GenericServiceException
     */
    public String getAgreementId(String authorizationKey, String agreementId) throws GenericEntityException, GenericServiceException {
        if (UtilValidate.isEmpty(agreementId) && UtilValidate.isEmpty(authorizationKey))
            throw new GnServiceException(OfbizErrors.INVALID_PARAMETERS, "service needs at least one of these parameters agreementId or authorizationKey");
        if (UtilValidate.isEmpty(agreementId))
            return getAgreementId(authorizationKey);
        else {
            if (UtilValidate.isEmpty(delegator.findOne("GnAuthorization", false, "agreementId", agreementId)))
                throw new GnServiceException(OfbizErrors.AUTHORIZATION_NOT_FOUND, "Authorization with agreementId[" + agreementId + "]  not found.");
            else return agreementId;
        }
    }

    /**
     * resolve {@code authorizationKey} by passed {@code agreementId}
     *
     * @param agreementId
     * @return
     * @throws GenericEntityException
     */
    public String getAuthorizationKey(String agreementId) throws GenericEntityException, GnServiceException {
        GenericValue auth = delegator.findOne("GnAuthorization", UtilMisc.toMap("agreementId", agreementId), true);
        if (auth == null)
            throw new GnServiceException(OfbizErrors.AUTHORIZATION_NOT_FOUND, "Authorization not found");
        return auth.getString("authorizationKey");
    }

    /**
     * @param authKey
     * @param ownerNodeId
     * @param partyIdTo
     * @param otherPartyIdTo
     * @param partyIdFrom
     * @param typeId
     * @param number
     * @param statusId
     * @param internalStatusId
     * @param description
     * @param textData
     * @param originId
     * @param originPartyIdTo
     * @param isPrivate
     * @param lastModifiedDate
     * @return
     * @throws GenericEntityException
     * @throws GenericServiceException
     */
    protected Map<String, Object> createAuthorization(AuthorizationKey authKey, String ownerNodeId, String partyIdTo,
                                                      String otherPartyIdTo, String partyIdFrom, String typeId, String number,
                                                      String statusId, String internalStatusId, String description, String textData, String originId,
                                                      String originPartyIdTo, String isPrivate, Timestamp lastModifiedDate) throws GenericEntityException, GenericServiceException {
        if (lastModifiedDate == null) {
            Debug.logWarning("lastModifiedDate is empty", module);
            lastModifiedDate = UtilDateTime.nowTimestamp();
        }
        Map<String, Object> result = FastMap.newInstance();
        List<GenericValue> toBeStored = FastList.newInstance();
        Map<String, Object> agreementMap = FastMap.newInstance();
        // agreementId might be empty, so check it and get next seq party id if empty
        String agreementId = delegator.getNextSeqId("Agreement");

        //validation
        //check issuer and holder
        if (gnPartyRoleCheck(partyIdTo, PartyRoleOfbiz.GN_PVT_COMPANY_BASE.name())) {
            agreementMap.put("roleTypeIdTo", PartyRoleOfbiz.GN_PVT_COMPANY_BASE.name());
        } else
            throw new GnServiceException(OfbizErrors.INVALID_PARAMETERS, "partyIdTo is not a PrivateCompanyBase");

        if (gnPartyRoleCheck(partyIdFrom, PartyRoleOfbiz.GN_ISSUER_BASE.name())) {
            agreementMap.put("roleTypeIdFrom", PartyRoleOfbiz.GN_ISSUER_BASE.name());
        } else
            throw new GnServiceException(OfbizErrors.INVALID_PARAMETERS, "partyIdTo is not an IssuerBase");


        agreementMap.put("agreementId", agreementId);
        //Fill agreement
        agreementMap.put("agreementTypeId", AgreementTypesOfbiz.GN_AUTHORIZATION.name());
        agreementMap.put("description", description);
        agreementMap.put("partyIdFrom", partyIdFrom);
        agreementMap.put("partyIdTo", partyIdTo);
        agreementMap.put("textData", textData);
        GenericValue agreement = delegator.makeValue("Agreement", agreementMap);
        toBeStored.add(agreement);

        //Fill authorization
        GenericValue authorization = delegator.makeValue("GnAuthorization");

        authorization.set("typeId", typeId);
        authorization.set("number", number);
        authorization.set("statusId", statusId);
        authorization.set("internalStatusId", internalStatusId);
        authorization.set("originId", originId);
        authorization.set("originPartyIdTo", originPartyIdTo);

        if (UtilValidate.isNotEmpty(originPartyIdTo)) {
            if (gnPartyRoleCheck(originPartyIdTo, PartyRoleOfbiz.GN_COMPANY_BASE.name())) {
                authorization.set("originRoleTypeIdTo", PartyRoleOfbiz.GN_COMPANY_BASE.name());
            } else
                throw new GnServiceException(OfbizErrors.INVALID_PARAMETERS, "originPartyIdTo is not an CompanyBase");
        }

        if (gnPartyRoleCheck(otherPartyIdTo, PartyRoleOfbiz.GN_PVT_COMPANY.name())) {
            authorization.set("otherRoleTypeIdTo", PartyRoleOfbiz.GN_PVT_COMPANY.name());
        } else
            throw new GnServiceException(OfbizErrors.INVALID_PARAMETERS, "otherPartyIdTo is not a PrivateCompany");


        authorization.set("agreementId", agreementId);
        authorization.set("lastModifiedDate", lastModifiedDate);
        authorization.set("isPrivate", isPrivate);

        if (userLogin != null)
            authorization.set("lastModifiedByUserLogin", userLogin.getString("userLoginId"));
        else
            authorization.set("lastModifiedByUserLogin", null);//null because data come from system

        authorization.set("ownerNodeId", ownerNodeId);

        //Fill authorization key
        authorization.set("uuid", authKey.getUuid());
        authorization.set("version", authKey.getVersion());
        authorization.set("lastModifierNodeKey", authKey.getLastModifierNodeKey());
        authorization.set("parentVersion", authKey.getParentVersion());
        authorization.set("ownerNodeKey", authKey.getOwnerNodeKey());

        String authorizationKey = authKey.toString();

        authorization.set("authorizationKey", authorizationKey);
        authorization.set("otherPartyIdTo", otherPartyIdTo);

        if (gnPartyRoleCheck(otherPartyIdTo, PartyRoleOfbiz.GN_COMPANY.name())) {
            authorization.put("otherRoleTypeIdTo", PartyRoleOfbiz.GN_COMPANY.name());
        } else if (gnPartyRoleCheck(otherPartyIdTo, PartyRoleOfbiz.GN_PVT_COMPANY.name())) {
            authorization.put("otherRoleTypeIdTo", PartyRoleOfbiz.GN_PVT_COMPANY.name());
        } else
            throw new GnServiceException(OfbizErrors.INVALID_PARAMETERS, "otherPartyIdTo is not a Company or a PrivateCompany");


        toBeStored.add(authorization);

        delegator.storeAll(toBeStored);
        result.put("agreementId", agreementId);
        result.put("authorizationKey", authorizationKey);
        Debug.log("Saved Authorization agreementId[" + agreementId + "],authorizationKey[" + authorizationKey + "]");
        return result;
    }

    public Map<String, Object> gnDeleteAuthorizationEM(String agreementId, String authorizationKey) throws GenericServiceException, GenericEntityException {
        if (UtilValidate.isEmpty(authorizationKey) && UtilValidate.isEmpty(agreementId))
            throw new GnServiceException(OfbizErrors.INVALID_PARAMETERS, "authorizationKey and agreementId cannot be empty both");
        Map<String, Object> auth = findAuthorizationById(agreementId, authorizationKey, false, false);
        if (!AuthUtil.checkInternalState(auth, AuthorizationInternalStatusOfbiz.GN_AUTH_TO_BE_DELET))
            throw new GnServiceException(OfbizErrors.INVALID_AUTHORIZATION_STATUS, "internalStatus of authorization[" + authorizationKey + "] is not " + AuthorizationInternalStatusOfbiz.GN_AUTH_TO_BE_DELET.name());
        return deleteAuthorization(agreementId, authorizationKey);
    }

    protected Map<String, Object> gnDeleteAuthorization(String agreementId, String authorizationKey) throws GenericServiceException, GenericEntityException {
        if (UtilValidate.isEmpty(authorizationKey) && UtilValidate.isEmpty(agreementId))
            throw new GnServiceException(OfbizErrors.INVALID_PARAMETERS, "authorizationKey and agreementId cannot be empty both");
        Map<String, Object> auth = findAuthorizationById(agreementId, authorizationKey, false, false);
        if (UtilValidate.isEmpty(auth)) {
            throw new GnServiceException(OfbizErrors.AUTHORIZATION_NOT_FOUND, "authorization[" + authorizationKey + "] doesn't exist");
        }
        if (!AuthUtil.checkInternalState(auth, null) || !AuthUtil.checkState(auth, AuthorizationStatusOfbiz.GN_AUTH_DRAFT))
            throw new GnServiceException(OfbizErrors.INVALID_AUTHORIZATION_STATUS, "internalStatus of authorization[" + authorizationKey + "] is not " + AuthorizationInternalStatusOfbiz.GN_AUTH_TO_BE_DELET.name());
        canUpdateAuthorization(agreementId, authorizationKey, null, null);
        return deleteAuthorization(agreementId, authorizationKey);
    }

    /**
     * Delete an Authorization, items and terms.
     *
     * @param agreementId
     * @param authorizationKey
     * @return
     * @throws GenericServiceException
     * @throws GenericEntityException
     */
    public Map<String, Object> deleteAuthorization(String agreementId, String authorizationKey) throws GenericServiceException, GenericEntityException {
        Map<String, Object> auth = findAuthorizationById(agreementId, authorizationKey, true, true);
        agreementId = (String) auth.get("agreementId");
        String partyIdTo = (String) auth.get("partyIdTo");
        String otherPartyIdTo = (String) auth.get("otherPartyIdTo");

        AuthorizationItemHelper itemHlp = new AuthorizationItemHelper(dctx, context);
        List<Map<String, Object>> items = UtilGenerics.toList(auth.get("authorizationItems"));
        for (Map<String, Object> item : items) {
            itemHlp.deleteAgreementItem(item);
        }

        AuthorizationAttachHelper attachHlp = new AuthorizationAttachHelper(dctx, context);
        List<Map<String, Object>> authorizationDocuments = UtilGenerics.toList(auth.get("authorizationDocuments"));
        for (Map<String, Object> item : authorizationDocuments) {
            // GenericValue gv= EntityFindOptions
            item = UtilMisc.makeMapWritable(item);
            item.put("agreementId", agreementId);
            //item.put("agreementContentTypeId", "GN_AUTH_ATTACH");
            attachHlp.delete(item);
        }

        Map<String, Object> result = deleteAuthorization(agreementId);
        deletePrivateCompanyBaseAndOwnerCompany(partyIdTo, otherPartyIdTo);
        return result;
    }

    public void deletePrivateCompanyBaseAndOwnerCompany(String privateCompanyBaseId, String privateCompanyId) throws GenericEntityException, GenericServiceException {
        //delete companyBase contactMechs
        dispatcher.runSync("gnDeletePrivateCompanyOrCompanyBase", UtilMisc.toMap("userLogin", userLogin, "partyId", privateCompanyBaseId));
        dispatcher.runSync("gnDeletePrivateCompanyOrCompanyBase", UtilMisc.toMap("userLogin", userLogin, "partyId", privateCompanyId));
    }

    /**
     * Delete <code>GnAuthorization</code> and cancel <code>Agreement</code>
     *
     * @param agreementId
     * @return
     * @throws GenericServiceException
     * @throws GenericEntityException
     */
    private Map<String, Object> deleteAuthorization(Object agreementId) throws GenericServiceException, GenericEntityException {
        delegator.removeByPrimaryKey(delegator.makePK("GnAuthorization", "agreementId", agreementId));

        GenericValue agreement = delegator.findOne("Agreement", false, "agreementId", agreementId);
        agreement.set("partyIdTo", null);
        agreement.set("roleTypeIdTo", null);
        delegator.store(agreement);

        String srvName = "cancelAgreement";
        Map<String, Object> srvRequest = UtilMisc.toMap("userLogin", (Object) userLogin, "agreementId", agreementId);
        srvRequest = dispatcher.getDispatchContext().makeValidContext(srvName, "IN", srvRequest);
        return dispatcher.runSync(srvName, srvRequest);
    }

    /**
     * @param authorizationKey
     * @param uuid
     * @param lastModifierNodeKey
     * @param ownerNodeKey
     * @param version
     * @param parentVersion
     * @param statusId
     * @param onlyPrivate         filter private="Y", public="N" all=null authorizations
     * @return
     * @throws GenericEntityException
     */
    public List<GenericValue> findByKey(String authorizationKey, String uuid, String lastModifierNodeKey, String ownerNodeKey,
                                        Long version, String parentVersion, AuthorizationStatusOfbiz statusId, String onlyPrivate) throws GenericEntityException {
        List<EntityCondition> conds = FastList.newInstance();
        if (UtilValidate.isNotEmpty(authorizationKey))
            conds.add(EntityCondition.makeCondition("authorizationKey", authorizationKey));
        if (UtilValidate.isNotEmpty(uuid))
            conds.add(EntityCondition.makeCondition("uuid", uuid));
        if (UtilValidate.isNotEmpty(lastModifierNodeKey))
            conds.add(EntityCondition.makeCondition("lastModifierNodeKey", lastModifierNodeKey));
        if (UtilValidate.isNotEmpty(parentVersion))
            conds.add(EntityCondition.makeCondition("parentVersion", parentVersion));
        if (UtilValidate.isNotEmpty(version))
            conds.add(EntityCondition.makeCondition("version", version));
        if (UtilValidate.isNotEmpty(ownerNodeKey))
            conds.add(EntityCondition.makeCondition("ownerNodeKey", ownerNodeKey));
        if (UtilValidate.isNotEmpty(statusId))
            conds.add(EntityCondition.makeCondition("statusId", statusId.name()));
        if (UtilValidate.isNotEmpty(onlyPrivate)) {
            if (!"Y".equals(onlyPrivate) && !"N".equals(onlyPrivate))
                throw new GenericEntityException("onlyPrivate can be 'N','Y' or null");
            conds.add(EntityCondition.makeCondition("isPrivate", onlyPrivate));
        }
        if (true)//todo add internalStatusId support
            conds.add(GnFindUtil.makeIsEmptyCondition("internalStatusId"));

        return delegator.findList("GnAuthorizationAndAgreement", EntityCondition.makeCondition(conds), null, null, null, false);
    }


    /**
     * @param ownerPartyId
     * @param eventTypeId
     * @param authorization
     * @return
     * @throws GenericServiceException
     */
    @Deprecated
    public Map<String, Object> gnSendCommunicationEventToContactList(String ownerPartyId, String eventTypeId, Map<String, Object> authorization) throws GenericServiceException {
        Map<String, Object> attributes = FastMap.newInstance();

        Map<String, Object> ownerNode = new AuthorizationSearchHelper(dctx, context).findPartyNodeById(ownerPartyId);

        attributes.put(CommunicationEventAttributeKeyOfbiz.AUTHORIZATION_KEY.name(), authorization.get("authorizationKey"));
        attributes.put(CommunicationEventAttributeKeyOfbiz.AUTHORIZATION_DESCRIPTION.name(), authorization.get("description"));
        attributes.put(CommunicationEventAttributeKeyOfbiz.AUTHORIZATION_NUMBER.name(), authorization.get("number"));
        Map<String, Object> partyNodeTo = UtilGenerics.checkMap(authorization.get("partyNodeTo"));
        Map<String, Object> otherPartyNodeTo = UtilGenerics.checkMap(authorization.get("otherPartyNodeTo"));
        attributes.put(CommunicationEventAttributeKeyOfbiz.AUTHORIZATION_HOLDER_BASE.name(), partyNodeTo.get("description"));
        attributes.put(CommunicationEventAttributeKeyOfbiz.AUTHORIZATION_HOLDER.name(), otherPartyNodeTo.get("description"));
        attributes.put(CommunicationEventAttributeKeyOfbiz.OWNER_NODE_DESCRIPTION.toString(), ownerNode.get("description"));

        Map<String, Object> srvRequest = FastMap.newInstance();
        srvRequest.put("userLogin", userLogin);
        srvRequest.put("ownerPartyId", ownerPartyId);
        srvRequest.put("eventTypeId", eventTypeId);
        srvRequest.put("attributes", attributes);
        Debug.log("Sending message for event[" + eventTypeId + "] on node[" + ownerPartyId + "] with attributes " + PrettyPrintingMap.toString(attributes));
        return dispatcher.runSync("gnSendCommunicationEventToContactList", srvRequest);
    }

    /**
     * @param ownerPartyId
     * @param eventTypeId
     * @param authorization
     * @return
     * @throws GenericServiceException
     */
    public Map<String, Object> gnSendCommunicationEventToContactListEM(String ownerPartyId, String eventTypeId, Map<String, Object> authorization) throws GenericServiceException {
        Map<String, Object> attributes = FastMap.newInstance();

        Map<String, Object> ownerNode = new AuthorizationSearchHelper(dctx, context).findPartyNodeById(ownerPartyId);

        attributes.put(CommunicationEventAttributeKeyOfbiz.AUTHORIZATION_KEY.name(), authorization.get("authorizationKey"));
        attributes.put(CommunicationEventAttributeKeyOfbiz.AUTHORIZATION_DESCRIPTION.name(), authorization.get("description"));
        attributes.put(CommunicationEventAttributeKeyOfbiz.AUTHORIZATION_NUMBER.name(), authorization.get("number"));
        Map<String, Object> partyNodeTo = UtilGenerics.checkMap(authorization.get("partyNodeTo"));
        Map<String, Object> otherPartyNodeTo = UtilGenerics.checkMap(authorization.get("otherPartyNodeTo"));
        attributes.put(CommunicationEventAttributeKeyOfbiz.AUTHORIZATION_HOLDER_BASE.name(), partyNodeTo.get("description"));
        attributes.put(CommunicationEventAttributeKeyOfbiz.AUTHORIZATION_HOLDER.name(), otherPartyNodeTo.get("description"));
        attributes.put(CommunicationEventAttributeKeyOfbiz.OWNER_NODE_DESCRIPTION.toString(), ownerNode.get("description"));

        Map<String, Object> srvRequest = FastMap.newInstance();
        srvRequest.put("userLogin", userLogin);
        srvRequest.put("ownerPartyId", ownerPartyId);
        srvRequest.put("eventTypeId", eventTypeId);
        srvRequest.put("attributes", attributes);
        Debug.log("Sending message for event[" + eventTypeId + "] on node[" + ownerPartyId + "] with attributes " + PrettyPrintingMap.toString(attributes));
        return dispatcher.runSync("gnSendCommunicationEventToContactListEM", srvRequest);
    }

    /**
     * @param partyId
     * @return
     * @throws GenericEntityException
     * @throws GenericServiceException
     */
    public Map<String, Object> gnAuthorizationManagePublicShare(String partyId) throws GenericEntityException, GenericServiceException {
        Map<String, Object> result = FastMap.newInstance();
        if ("Y".equals(gnFindPartyAttributeById(partyId))) {
            Debug.log("Party[" + partyId + "] shareEnable=Y", module);
            List<Map<String, Object>> shareList = findAuthorizationByHolderCompany(partyId);
            result.put("authorizationToShareList", shareList);
            result.put("authorizationToShareListSize", shareList.size());
        } else
            Debug.log("Party[" + partyId + "] shareEnable=N", module);
        return result;
    }

    private List<Map<String, Object>> findAuthorizationByHolderCompany(String holderCompanyId) throws GenericEntityException, GenericServiceException {
        DynamicViewEntity dv = new DynamicViewEntity();
        dv.addMemberEntity("AG", "GnAuthorizationAndAgreement");
        dv.addMemberEntity("PR", "PartyRelationship");
        dv.addAlias("AG", "statusId");
        dv.addAlias("AG", "internalStatusId");
        dv.addAlias("AG", "ownerNodeId");
        // dv.addAlias("AG", "AG_fromDate", "fromDate", null, null, null, null);
        //dv.addAlias("AG", "AG_thruDate", "AG_thruDate", null, null, null, null);
        dv.addAlias("AG", "agreementId");
        dv.addAlias("AG", "agreementTypeId");

        dv.addAliasAll("PR", null);
        dv.addViewLink("AG", "PR", false, UtilMisc.toList(new ModelKeyMap("originPartyIdTo", "partyIdFrom")));
        EntityCondition cond = EntityConditionUtil.makeRelationshipCondition(null, PartyRoleOfbiz.GN_COMPANY_BASE.name(), holderCompanyId, PartyRoleOfbiz.GN_COMPANY.name(), RelationshipTypeOfbiz.GN_BELONGS_TO.name(), true);
        cond = GnFindUtil.concat(cond, EntityCondition.makeCondition("agreementTypeId", AgreementTypesOfbiz.GN_AUTHORIZATION.name()));
        cond = GnFindUtil.concat(cond, EntityCondition.makeCondition("statusId", AuthorizationStatusOfbiz.GN_AUTH_PUBLISHED.name()));
        cond = GnFindUtil.concat(cond, EntityCondition.makeCondition("ownerNodeId", holderCompanyId));
        cond = GnFindUtil.concat(cond, GnFindUtil.makeIsEmptyCondition("internalStatusId"));

        // cond = GnFindUtil.concat(cond, EntityCondition.makeCondition("isPrivate", "N"));
        // cond = GnFindUtil.concat(cond, EntityUtil.getFilterByDateExpr("AG_fromDate", "AG_thruDate"));

        EntityListIterator iterator = delegator.findListIteratorByCondition(dv, cond, null, UtilMisc.toSet("agreementId"), UtilMisc.toList("agreementId ASC"), GnFindUtil.getFindOptDistinct());
        List<GenericValue> gvs = iterator.getCompleteList();
        iterator.close();
        FastList<Map<String, Object>> auths = FastList.newInstance();
        long start = System.currentTimeMillis();
        for (GenericValue gv : gvs) {
            auths.add(findAuthorizationById(gv.getString("agreementId"), null, true, true));
        }
        long stop = System.currentTimeMillis();
        Debug.log("Loaded " + gvs.size() + " authorizations in " + (stop - start) / 1000d + "s.", module);
        return auths;
    }

    /**
     * @param taxIdentificationNumber
     * @return
     * @throws GenericServiceException
     * @throws GenericEntityException
     */
    public List<Map<String, Object>> gnPrivateFindCompaniesAuthorization(String taxIdentificationNumber) throws GenericServiceException, GenericEntityException {
        List<Map<String, Object>> partyNodeList = FastList.newInstance();
        //find public company
        EntityCondition cond = EntityCondition.makeCondition(UtilMisc.toMap("taxIdentificationNumber", taxIdentificationNumber, "nodeType", "COMPANY"));
        List<GenericValue> publicCompanies = delegator.findList("GnPartyGroupPartyNode", cond, null, null, null, false);
        partyNodeList.addAll(publicCompanies);
        //find private company
        List<Map<String, Object>> privateCompany = privateFindCompaniesAuthorization(taxIdentificationNumber);
        partyNodeList.addAll(privateCompany);

        List<Map<String, Object>> recipientList = new ArrayList<Map<String, Object>>(partyNodeList.size());
        //find and remove similar company
        for (Map<String, Object> company : partyNodeList) {
            boolean exist = false;
            for (Map<String, Object> recipientCompany : recipientList) {
                //remove non word character and ignore case
                if (UtilValidate.areEqual(((String) recipientCompany.get("name")).replaceAll("[\\W_]", "").toLowerCase(),
                        ((String) company.get("name")).replaceAll("[\\W_]", "").toLowerCase()) &&
                        UtilValidate.areEqual(recipientCompany.get("taxIdentificationNumber"), company.get("taxIdentificationNumber")) &&
                        UtilValidate.areEqual(recipientCompany.get("VATnumber"), company.get("VATnumber"))
                        ) {
                    exist = true;
                    break;
                }


            }
            if (!exist) recipientList.add(company);

        }
        return recipientList;
    }

    /**
     * @param taxIdentificationNumber
     * @return
     * @throws GenericServiceException
     */

    private List<Map<String, Object>> privateFindCompaniesAuthorization(String taxIdentificationNumber) throws GenericServiceException, GenericEntityException {
        Map<String, Object> currentContext = getCurrentContext();
        Map<String, Object> company = UtilGenerics.checkMap(currentContext.get("partyNode"));
        String comanyId = (String) company.get("partyId");

        DynamicViewEntity view = new DynamicViewEntity();
        view.addMemberEntity("AU", "GnAuthorizationAndAgreement");
        view.addMemberEntity("PN", "GnPartyGroupPartyNode");
        view.addViewLink("AU", "PN", false, UtilMisc.toList(new ModelKeyMap("otherPartyIdTo", "partyId")));

        view.addAlias("AU", "agreementTypeId");
        view.addAlias("AU", "statusId");
        view.addAlias("AU", "internalStatusId");
        view.addAlias("AU", "ownerNodeId");
        view.addAlias("AU", "otherPartyIdTo");
        view.addAlias("PN", "nodeType");
        view.addAlias("PN", "taxIdentificationNumber");
        view.addAlias("PN", "partyId");
        view.addAliasAll("PN", "PN_");
        EntityCondition cond = EntityCondition.makeCondition("agreementTypeId", AgreementTypesOfbiz.GN_AUTHORIZATION.name());
        cond = GnFindUtil.concat(cond, EntityCondition.makeCondition("nodeType", "PRIVATE_COMPANY"));
        cond = GnFindUtil.concat(cond, GnFindUtil.makeOrConditionById("statusId", UtilMisc.toList(AuthorizationStatusOfbiz.GN_AUTH_DRAFT.name(), AuthorizationStatusOfbiz.GN_AUTH_PUBLISHED.name())));
        cond = GnFindUtil.concat(cond, EntityCondition.makeCondition("taxIdentificationNumber", taxIdentificationNumber));
        cond = GnFindUtil.concat(cond, EntityCondition.makeCondition("ownerNodeId", comanyId));
        cond = GnFindUtil.concat(cond, GnFindUtil.makeIsEmptyCondition("internalStatusId"));


        EntityListIterator iterator = delegator.findListIteratorByCondition(view, cond, null, null, UtilMisc.toList("partyId ASC"), GnFindUtil.getFindOptDistinct());
        List<GenericValue> gvs = iterator.getCompleteList();
        iterator.close();
        FastList<Map<String, Object>> parties = FastList.newInstance();
        Map<String, Object> party;
        for (GenericValue gn : gvs) {
            party = FastMap.newInstance();
            for (Map.Entry<String, Object> entry : gn.entrySet()) {
                if (entry.getKey().startsWith("PN_")) {
                    String field = entry.getKey().substring(3);
                    if ("VATnumber".equalsIgnoreCase(field)) {
                        party.put(field, entry.getValue());
                    } else {
                        String firstChar = field.substring(0, 1).toLowerCase();
                        party.put(firstChar.concat(field.substring(1)), entry.getValue());
                    }
                }
            }
            parties.add(party);
        }

        return parties;
    }

    /**
     * @param taxIdentificationNumber
     * @return
     * @throws GenericServiceException
     * @throws GenericEntityException
     */
    public List<Map<String, Object>> gnPrivateFindCompanyBasesAuthorization(String taxIdentificationNumber) throws GenericServiceException, GenericEntityException {
        try {
            List<Map<String, Object>> partyNodeList = FastList.newInstance();
            //public companyBase
            List<Map<String, Object>> publicCompanyBases = UtilGenerics.checkList(dispatcher.runSync("gnFindPublicCompanyBases", dctx.makeValidContext("gnFindPublicCompanyBases", "IN", context)).get("partyNodes"));
            partyNodeList.addAll(publicCompanyBases);
            //private companyBase
            List<Map<String, Object>> privateCompanyBases = privateFindCompanyBasesAuthorization(taxIdentificationNumber);
            partyNodeList.addAll(privateCompanyBases);

            List<Map<String, Object>> recipientList = new ArrayList<Map<String, Object>>(partyNodeList.size());
            //find and remove similar company
            for (Map<String, Object> companyBase : partyNodeList) {
                boolean exist = false;
                Map<String, Object> companyBaseAddress = UtilGenerics.checkMap(companyBase.get("address"));

                AddressMatcher addressMatcher = new AddressMatcher((String) companyBaseAddress.get("address1"),
                        (String) companyBaseAddress.get("streetNumber"), (String) companyBaseAddress.get("village"));

                for (Map<String, Object> recipientCompanyBase : recipientList) {
                    Map<String, Object> address = UtilGenerics.checkMap(recipientCompanyBase.get("address"));
                    //compare address with fuzzy logic
                    if (UtilValidate.areEqual(companyBaseAddress.get("postalCodeGeoId"), address.get("postalCodeGeoId")) &&
                            addressMatcher.match((String) address.get("address1"), (String) address.get("streetNumber"), (String) address.get("village"))
                            ) {
                        exist = true;
                        break;
                    }
                }
                if (!exist) {
                    recipientList.add(companyBase);
                }

            }
            return recipientList;
        } catch (TokenizerException e) {
            throw new GnServiceException(OfbizErrors.GENERIC, "Unable to evaluate address", e);
        } catch (ComparerException e) {
            throw new GnServiceException(OfbizErrors.GENERIC, "Unable to evaluate address", e);
        }
    }

    /**
     * @param taxIdentificationNumber
     * @return
     * @throws GenericServiceException
     */

    private List<Map<String, Object>> privateFindCompanyBasesAuthorization(String taxIdentificationNumber) throws
            GenericServiceException, GenericEntityException {
        Map<String, Object> currentContext = getCurrentContext();
        Map<String, Object> company = UtilGenerics.checkMap(currentContext.get("partyNode"));
        String comanyId = (String) company.get("partyId");

        DynamicViewEntity view = new DynamicViewEntity();
        view.addMemberEntity("AU", "GnAuthorizationAndAgreement");
        view.addMemberEntity("PN", "GnPartyGroupPartyNode");
        view.addViewLink("AU", "PN", false, UtilMisc.toList(new ModelKeyMap("otherPartyIdTo", "partyId")));

        view.addAlias("AU", "agreementTypeId");
        view.addAlias("AU", "statusId");
        view.addAlias("AU", "internalStatusId");
        view.addAlias("AU", "ownerNodeId");
        view.addAlias("AU", "otherPartyIdTo");
        view.addAlias("AU", "partyIdTo");
        view.addAlias("AU", "partyId");
        view.addAlias("PN", "nodeType");
        view.addAlias("PN", "taxIdentificationNumber");
        EntityCondition cond = EntityCondition.makeCondition("agreementTypeId", AgreementTypesOfbiz.GN_AUTHORIZATION.name());
        cond = GnFindUtil.concat(cond, EntityCondition.makeCondition("nodeType", "PRIVATE_COMPANY"));
        cond = GnFindUtil.concat(cond, GnFindUtil.makeOrConditionById("statusId", UtilMisc.toList(AuthorizationStatusOfbiz.GN_AUTH_DRAFT.name(), AuthorizationStatusOfbiz.GN_AUTH_PUBLISHED.name())));
        cond = GnFindUtil.concat(cond, EntityCondition.makeCondition("taxIdentificationNumber", taxIdentificationNumber));
        cond = GnFindUtil.concat(cond, EntityCondition.makeCondition("ownerNodeId", comanyId));
        cond = GnFindUtil.concat(cond, GnFindUtil.makeIsEmptyCondition("internalStatusId"));

        EntityListIterator iterator = delegator.findListIteratorByCondition(view, cond, null, Arrays.asList("partyIdTo"), null, GnFindUtil.getFindOptDistinct());
        List<GenericValue> gvs = iterator.getCompleteList();
        iterator.close();
        FastList<Map<String, Object>> parties = FastList.newInstance();
        Map<String, Object> companyBase;
        for (GenericValue gn : gvs) {
            companyBase = getDispatcher().runSync("gnInternalFindPartyNodeById", UtilMisc.toMap("userLogin", userLogin, "partyId", gn.get("partyIdTo")));
            Map<String, Object> partyNode = UtilGenerics.checkMap(companyBase.get("partyNode"));
            parties.add(partyNode);
        }

        return parties;
    }

    /**
     * @param authorizationKey
     * @param nodeKey
     * @param partyId
     * @throws GenericServiceException
     * @throws GenericEntityException
     */
    public void gnPropagateDeleteAuthorizationToEM(String authorizationKey, String nodeKey, String partyId) throws GenericServiceException, GenericEntityException {
        List<EventMessage> messages = new ArrayList<EventMessage>();
        Map<String, String> authorizationsByStatus = new HashMap<String, String>();
        AuthorizationKey key = AuthorizationKey.fromString(authorizationKey);
        AuthorizationPublicationHelper publicationHelper = new AuthorizationPublicationHelper(dctx, context);
        //NodeTreeHelper treeHelper = new NodeTreeHelper(dctx, context);//TODO remove
        partyId = getPartyId(partyId, nodeKey, OfbizErrors.INVALID_PARAMETERS, "PartyId and NodeKey cannot be empty both.");

        Map<String, Object> ownerNode = findPartyNodeById(partyId);

        List<EntityCondition> conds = new ArrayList<EntityCondition>();
        conds.add(EntityCondition.makeCondition("uuid", key.getUuid()));
        conds.add(EntityCondition.makeCondition("ownerNodeId", partyId));
        List<GenericValue> auths = delegator.findList("GnAuthorizationAndAgreement", EntityCondition.makeCondition(conds), UtilMisc.toSet("agreementId", "authorizationKey", "statusId"), null, null, false);
        for (GenericValue auth : auths) {
            authorizationsByStatus.put(auth.getString("statusId"), auth.getString("agreementId"));
        }

        //get node children
        EntityCondition condition = EntityConditionUtil.makeRelationshipCondition(partyId, PartyRoleOfbiz.GN_PROPAGATION_NODE, null, PartyRoleOfbiz.GN_PROPAGATION_NODE, RelationshipTypeOfbiz.GN_PROPAGATES_TO, true);
        List<GenericValue> rels = delegator.findList("GnPartyRelationship", condition, null, null, null, false);
        List<Map<String, Object>> childRelationships = new ArrayList<Map<String, Object>>(rels.size());
        for (GenericValue _rel : rels) {
            Map<String, Object> rel = UtilMisc.makeMapWritable(_rel);
            rel.put("target", findPartyNodeById(_rel.getString("partyIdTo")));
            childRelationships.add(rel);
        }

        //treeHelper.getChildren(partyId, RelationshipTypeOfbiz.GN_PROPAGATES_TO.toString(), ownerNode);
        //List<Map<String, Object>> childRelationships = (List<Map<String, Object>>) ownerNode.get("partyRelationships");

        //TO_BE_VALIDATED
        String toBeValidatedAgreementId = authorizationsByStatus.get(AuthorizationStatusOfbiz.GN_AUTH_TO_BE_VALID.toString());
        if (UtilValidate.isNotEmpty(toBeValidatedAgreementId)) {
            GenericValue authorization = delegator.findOne("GnAuthorization", UtilMisc.toMap("agreementId", toBeValidatedAgreementId), false);
            if (UtilValidate.isEmpty(authorization)) {
                throw new GnServiceException(OfbizErrors.ENTITY_EXCEPTION, "Authorization not found with agreementId[" + toBeValidatedAgreementId + "]");
            }
            publicationHelper.updateAuthorizationState(toBeValidatedAgreementId, AuthorizationStatusOfbiz.GN_AUTH_NOT_VALID, (String) authorization.get("originId"));
            publicationHelper.updateAuthorizationInternalState(toBeValidatedAgreementId, AuthorizationInternalStatusOfbiz.GN_AUTH_TO_BE_DELET, (String) authorization.get("originId"));

            Map<String, Object> toBeArchived = findAuthorizationById(toBeValidatedAgreementId, null, true, true);

            for (Map<String, Object> relation : childRelationships) {
                Map<String, Object> child = UtilMisc.getMapFromMap(relation, "target");
                EventMessageAuthPropagateDelete propDel = EventMessageFactory.make.makeEventMessageAuthPropagateDelete(
                        (String) child.get("nodeKey"),
                        (String) child.get("partyId"),
                        (String) child.get("nodeType"),
                        (String) toBeArchived.get("authorizationKey"));
                messages.add(propDel);
            }

            EventMessageAuthArchived em = EventMessageFactory.make.makeEventMessageAuthArchived(
                    nodeKey, partyId, (String) ownerNode.get("nodeType"), publicationHelper.removeOfBizIds(toBeArchived, false));
            messages.add(em);

            EventMessageAuthIndexingDelete indDel = EventMessageFactory.make.makeEventMessageAuthIndexingDelete(
                    nodeKey,
                    partyId,
                    (String) ownerNode.get("nodeType"),
                    (String) toBeArchived.get("authorizationKey"));
            messages.add(indDel);

            EventMessageAuthAudit ema = EventMessageFactory.make().makeEventMessageAuthAudit(
                    EventMessageAuthAudit.ActionTypeEnumOFBiz.DELETED, getCurrentContextId(),
                    userLogin.getString("userLoginId"),
                    nodeKey,
                    partyId,
                    (String) ownerNode.get("nodeType"),
                    publicationHelper.removeOfBizIds(toBeArchived, true));
            messages.add(ema);
        }

        //PUBLISHED
        String publishedAgreementId = authorizationsByStatus.get(AuthorizationStatusOfbiz.GN_AUTH_PUBLISHED.toString());
        if (UtilValidate.isNotEmpty(publishedAgreementId)) {
            Map<String, Object> authorization = findAuthorizationById(publishedAgreementId, null, true, true);
            if (UtilValidate.isEmpty(authorization)) {
                throw new GnServiceException(OfbizErrors.ENTITY_EXCEPTION, "Authorization not found with agreementId[" + publishedAgreementId + "]");
            }

            AuthorizationKey authKey = AuthorizationKey.fromString((String) authorization.get("authorizationKey"));
            if (authKey.getLastModifierNodeKey().equals(authKey.getOwnerNodeKey())
                    && authKey.getParentVersion().equals("0")) {
                //The authorization has been published by this node
                gnSendCommunicationEventToContactListEM((String) authorization.get("ownerNodeId"), EventTypeOfbiz.GN_COM_EV_AUTH_NDEL.toString(), authorization);
            } else {
                //It must be deleted
                publicationHelper.updateAuthorizationInternalState(publishedAgreementId, AuthorizationInternalStatusOfbiz.GN_AUTH_TO_BE_DELET, (String) authorization.get("originId"));

                Map<String, Object> toBeArchived = authorization;

                for (Map<String, Object> relation : childRelationships) {
                    Map<String, Object> child = UtilMisc.getMapFromMap(relation, "target");
                    EventMessageAuthPropagateDelete propDel = EventMessageFactory.make.makeEventMessageAuthPropagateDelete(
                            (String) child.get("nodeKey"),
                            (String) child.get("partyId"),
                            (String) child.get("nodeType"),
                            (String) toBeArchived.get("authorizationKey"));
                    messages.add(propDel);
                }

                EventMessageAuthIndexingDelete indDel = EventMessageFactory.make.makeEventMessageAuthIndexingDelete(
                        nodeKey,
                        partyId,
                        (String) ownerNode.get("nodeType"),
                        (String) toBeArchived.get("authorizationKey"));
                messages.add(indDel);

                EventMessageAuthArchived em = EventMessageFactory.make.makeEventMessageAuthArchived(
                        nodeKey,
                        partyId,
                        (String) ownerNode.get("nodeType"),
                        publicationHelper.removeOfBizIds(toBeArchived, false));
                messages.add(em);

                EventMessageAuthAudit ema = EventMessageFactory.make().makeEventMessageAuthAudit(
                        EventMessageAuthAudit.ActionTypeEnumOFBiz.DELETED, getCurrentContextId(),
                        userLogin.getString("userLoginId"),
                        nodeKey,
                        partyId,
                        (String) ownerNode.get("nodeType"),
                        publicationHelper.removeOfBizIds(toBeArchived, true));
                messages.add(ema);
            }
        }

        //CONFLICTING
        String conflictingAgreementId = authorizationsByStatus.get(AuthorizationStatusOfbiz.GN_AUTH_CONFLICTING.toString());
        if (UtilValidate.isNotEmpty(conflictingAgreementId)) {
            GenericValue authorization = delegator.findOne("GnAuthorization", UtilMisc.toMap("agreementId", conflictingAgreementId), false);
            if (UtilValidate.isEmpty(authorization)) {
                throw new GnServiceException(OfbizErrors.ENTITY_EXCEPTION, "Authorization not found with agreementId[" + conflictingAgreementId + "]");
            }
            publicationHelper.updateAuthorizationState(conflictingAgreementId, AuthorizationStatusOfbiz.GN_AUTH_NOT_VALID, (String) authorization.get("originId"));
            publicationHelper.updateAuthorizationInternalState(conflictingAgreementId, AuthorizationInternalStatusOfbiz.GN_AUTH_TO_BE_DELET, (String) authorization.get("originId"));

            //find the related one
            List<String> conflictingIds = publicationHelper.findConflicting(partyId, findAuthorizationById(conflictingAgreementId, null, true, false));
            if (UtilValidate.isNotEmpty(conflictingIds)) {
                Map<String, Object> other = findAuthorizationById(null, conflictingIds.get(0), true, true);
                AuthorizationKey authKey = AuthorizationKey.fromString((String) other.get("authorizationKey"));
                if (authKey.getLastModifierNodeKey().equals(authKey.getOwnerNodeKey())
                        && authKey.getParentVersion().equals("0")
                        && "Y".equalsIgnoreCase((String) other.get("isPrivate"))) {
                    //The authorization has been published by this node
                    gnSendCommunicationEventToContactListEM((String) other.get("ownerNodeId"), EventTypeOfbiz.GN_COM_EV_AUTH_NDEL.toString(), other);
                } else {
                    //It must be deleted
                    publicationHelper.updateAuthorizationInternalState((String) other.get("agreementId"), AuthorizationInternalStatusOfbiz.GN_AUTH_TO_BE_DELET, (String) other.get("originId"));

                    Map<String, Object> toBeArchived = other;

                    for (Map<String, Object> relation : childRelationships) {
                        Map<String, Object> child = UtilMisc.getMapFromMap(relation, "target");
                        EventMessageAuthPropagateDelete propDel = EventMessageFactory.make.makeEventMessageAuthPropagateDelete(
                                (String) child.get("nodeKey"),
                                (String) child.get("partyId"),
                                (String) child.get("nodeType"),
                                (String) toBeArchived.get("authorizationKey"));
                        messages.add(propDel);
                    }

                    EventMessageAuthIndexingDelete indDel = EventMessageFactory.make.makeEventMessageAuthIndexingDelete(
                            nodeKey,
                            partyId,
                            (String) ownerNode.get("nodeType"),
                            (String) toBeArchived.get("authorizationKey"));
                    messages.add(indDel);

                    EventMessageAuthArchived em = EventMessageFactory.make.makeEventMessageAuthArchived(
                            nodeKey,
                            partyId,
                            (String) ownerNode.get("nodeType"),
                            publicationHelper.removeOfBizIds(toBeArchived, false));
                    messages.add(em);

                    EventMessageAuthAudit ema = EventMessageFactory.make().makeEventMessageAuthAudit(
                            EventMessageAuthAudit.ActionTypeEnumOFBiz.DELETED, getCurrentContextId(),
                            userLogin.getString("userLoginId"),
                            nodeKey,
                            partyId,
                            (String) ownerNode.get("nodeType"),
                            publicationHelper.removeOfBizIds(toBeArchived, true));
                    messages.add(ema);
                }
            }

            Map<String, Object> toBeArchived = findAuthorizationById(conflictingAgreementId, null, true, true);

            for (Map<String, Object> relation : childRelationships) {
                Map<String, Object> child = UtilMisc.getMapFromMap(relation, "target");
                EventMessageAuthPropagateDelete propDel = EventMessageFactory.make.makeEventMessageAuthPropagateDelete(
                        (String) child.get("nodeKey"),
                        (String) child.get("partyId"),
                        (String) child.get("nodeType"),
                        (String) toBeArchived.get("authorizationKey"));
                messages.add(propDel);
            }

            EventMessageAuthIndexingDelete indDel = EventMessageFactory.make.makeEventMessageAuthIndexingDelete(
                    nodeKey,
                    partyId,
                    (String) ownerNode.get("nodeType"),
                    (String) toBeArchived.get("authorizationKey"));
            messages.add(indDel);

            EventMessageAuthArchived em = EventMessageFactory.make.makeEventMessageAuthArchived(
                    nodeKey,
                    partyId,
                    (String) ownerNode.get("nodeType"),
                    publicationHelper.removeOfBizIds(toBeArchived, false));
            messages.add(em);

            EventMessageAuthAudit ema = EventMessageFactory.make().makeEventMessageAuthAudit(
                    EventMessageAuthAudit.ActionTypeEnumOFBiz.DELETED, getCurrentContextId(),
                    userLogin.getString("userLoginId"),
                    nodeKey,
                    partyId,
                    (String) ownerNode.get("nodeType"),
                    publicationHelper.removeOfBizIds(toBeArchived, true));
            messages.add(ema);
        }

        EventMessageContainer.addAll(messages);
    }

    public void gnAuthorizationOperationalCheckEvents(List<String> names, List<Map<String, Object>> checkParams, Map<String, Object> checkResultMap) throws GenericServiceException {
        int size = checkParams.size();
        if (size != checkResultMap.size())
            throw new GnServiceException(OfbizErrors.GENERIC, "gnAuthorizationOperationalCheckEvents: checkParams.size != checkResultMap.size");
        if (size != names.size())
            throw new GnServiceException(OfbizErrors.GENERIC, "gnAuthorizationOperationalCheckEvents: checkParams.size != names.size");
        List<EventMessage> messages = new ArrayList<EventMessage>();
        List<Map<String, Object>> operationalCheckDataList = new ArrayList<Map<String, Object>>(size);
        Map<String, Object> currentContext = getCurrentContext();
        Map<String, Object> partyNode = (Map<String, Object>) currentContext.get("partyNode");
        int i = 0;
        for (Map<String, Object> checkParam : checkParams) {
            Map<String, Object> operationalCheckDataMap = new HashMap<String, Object>();
            String name = names.get(i);
            Map<String, Object> resultMap = (Map<String, Object>) checkResultMap.get(name);
            List<String> authorizationKeys = new ArrayList<String>(resultMap.size());
            List<Map<String, Object>> authorizations = (List<Map<String, Object>>) resultMap.get("list");
            for (Map<String, Object> auth : authorizations) {
                authorizationKeys.add((String) auth.get("authorizationKey"));
            }
            operationalCheckDataMap.put("name", name);
            operationalCheckDataMap.put("criteria", checkParam);
            operationalCheckDataMap.put("authorizationKeys", authorizationKeys);
            operationalCheckDataList.add(operationalCheckDataMap);
            i++;
        }
        EventMessageOperationalCheckAudit audit = EventMessageFactory.make.makeEventMessageOperationalCheckAudit(
                userLogin.getString("userLoginId"),
                (String) partyNode.get("nodeKey"),
                (String) partyNode.get("partyId"),
                (String) partyNode.get("nodeType"),
                operationalCheckDataList
        );
        messages.add(audit);
        EventMessageContainer.addAll(messages);
    }
}
