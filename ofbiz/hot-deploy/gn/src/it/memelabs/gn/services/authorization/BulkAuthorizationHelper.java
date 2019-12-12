package it.memelabs.gn.services.authorization;

import it.memelabs.gn.services.OfbizErrors;
import it.memelabs.gn.services.node.PartyNodeTypeOfbiz;
import it.memelabs.gn.services.node.PartyRoleOfbiz;
import it.memelabs.gn.services.node.RelationshipTypeOfbiz;
import it.memelabs.gn.services.security.GnSecurity;
import it.memelabs.gn.services.security.PermissionsOfbiz;
import it.memelabs.gn.util.CollectionDiff;
import it.memelabs.gn.util.GnServiceException;
import it.memelabs.gn.util.TreeUtil;
import it.memelabs.gn.util.validator.BulkCreateAuthorizationValidator;
import it.memelabs.gn.util.validator.BulkCreatePrivateAuthorizationValidator;
import it.memelabs.gn.util.validator.BulkUpdateAuthorizationValidator;
import it.memelabs.gn.util.validator.Errors;
import javolution.util.FastMap;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilGenerics;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.entity.condition.EntityCondition;
import org.ofbiz.entity.condition.EntityJoinOperator;
import org.ofbiz.entity.condition.EntityOperator;
import org.ofbiz.service.DispatchContext;
import org.ofbiz.service.GenericServiceException;
import org.ofbiz.service.ServiceUtil;

import java.sql.Timestamp;
import java.util.*;

/**
 * 29/10/13
 *
 * @author Andrea Fossi
 */
public class BulkAuthorizationHelper extends CommonAuthorizationHelper {
    private static final String module = BulkAuthorizationHelper.class.getName();

    AuthorizationHelper authorizationHelper;
    AuthorizationPartyNodeHelper authorizationPartyNodeHelper;
    AuthorizationItemHelper authorizationItemHelper;

    public BulkAuthorizationHelper(DispatchContext dctx, Map<String, ? extends Object> context) {
        super(dctx, context);
        authorizationHelper = new AuthorizationHelper(dctx, context);
        authorizationPartyNodeHelper = new AuthorizationPartyNodeHelper(dctx, context);
        authorizationItemHelper = new AuthorizationItemHelper(dctx, context);

    }

    /**
     * @param userLoginId
     * @param contextId
     * @param ownerNodeKey
     * @param ownerPartyId
     * @param authorization
     * @param isPrivate
     * @return
     * @throws org.ofbiz.entity.GenericEntityException
     * @throws org.ofbiz.service.GenericServiceException
     */
    public Map<String, Object> bulkCreateFromScraping(String userLoginId, String contextId, String ownerNodeKey, String ownerPartyId, Map<String, Object> authorization, boolean isPrivate) throws GenericEntityException, GenericServiceException {
        //******** check start ********
        GnSecurity gnSecurity = new GnSecurity(delegator);
        Map<String, Object> userCompany = null;
        if (UtilValidate.isEmpty("userLoginId"))
            throw new GnServiceException(OfbizErrors.INVALID_PARAMETERS, "The following required parameter is missing: [IN] [gnBulkImportAuthorization.userLoginId");

        ownerNodeKey = authorizationPartyNodeHelper.getNodeKey(ownerPartyId, ownerNodeKey, "PartyId and NodeKey cannot be empty both.");
        ownerPartyId = authorizationPartyNodeHelper.getPartyId(ownerPartyId, ownerNodeKey, "PartyId and NodeKey cannot be empty both.");


        if (isPrivate) {
            if (UtilValidate.isEmpty(contextId)) {
                throw new GnServiceException(OfbizErrors.INVALID_PARAMETERS,
                        "contextId must be specified in order to import a private authorization.");
            }
            GenericValue rel = authorizationHelper.findReceiveFromRelationship(ownerPartyId);
            if ("N".equals(rel.getString("validationRequired"))) {
                throw new GnServiceException(OfbizErrors.ACCESS_DENIED, "only node with validation flag active can import authorization");
            }

            if (authorizationHelper.gnPartyRoleCheck(ownerPartyId, PartyRoleOfbiz.GN_COMPANY.name())) {
                userCompany = authorizationHelper.findPartyNodeById(ownerPartyId);
            } else {
                Iterator<String> parentPartyId = TreeUtil.newBackwardNavigator(dctx, context,
                        ownerPartyId, PartyRoleOfbiz.GN_PROPAGATION_NODE.name(), PartyRoleOfbiz.GN_PROPAGATION_NODE.name(), RelationshipTypeOfbiz.GN_PROPAGATES_TO.name());
                while (parentPartyId.hasNext()) {
                    String parentPartyNodeId = parentPartyId.next();
                    if (authorizationHelper.gnPartyRoleCheck(parentPartyNodeId, PartyRoleOfbiz.GN_COMPANY.name())) {
                        userCompany = authorizationHelper.findPartyNodeById(parentPartyNodeId);
                        break;
                    }
                }
            }
            if (userCompany == null) {
                throw new GnServiceException(OfbizErrors.ENTITY_EXCEPTION, "No company where user is employed found.");
            }
            Map<String, Object> profileResponse = dispatcher.runSync("gnPrivateAuthPublishProfileCheck",
                    UtilMisc.toMap("userLogin", userLogin, "forcedCompanyPartyId", userCompany.get("partyId")));
            if (!"Y".equals(profileResponse.get("hasPermission"))) {
                throw new GnServiceException(OfbizErrors.ACCESS_DENIED, "Company hasn't permission related to private publish.");
            }
            if (!gnSecurity.hasPermissionByContext(PermissionsOfbiz.GN_AUTH_PVT_DRAFT.name(), contextId)) {
                throw new GnServiceException(OfbizErrors.ACCESS_DENIED, "Import private authorization not allowed.");
            }

        } else { //isPublic
            Map<String, Object> partyNode = authorizationHelper.findPartyNodeById(ownerPartyId);
            if (!PartyNodeTypeOfbiz.isACompany(partyNode.get("nodeType"))) {
                throw new GnServiceException(OfbizErrors.INVALID_PARAMETERS,
                        "nodeKey[" + ownerNodeKey + "] must be related to a " + PartyNodeTypeOfbiz.COMPANY.name() +
                                " node in order to import a public authorization.");
            }
            Map<String, Object> profileResponse = dispatcher.runSync("gnRootPublishProfileCheck",
                    UtilMisc.toMap("userLogin", userLogin, "forcedCompanyPartyId", ownerPartyId));
            if (!"Y".equals(profileResponse.get("hasPermission"))) {
                throw new GnServiceException(OfbizErrors.ACCESS_DENIED, "Company hasn't permission related to root publish.");
            }
            userCompany = partyNode;
            List<GenericValue> ret = delegator.findList("GnContextUser", EntityCondition.makeCondition("userLoginId", userLoginId), UtilMisc.toSet("contextPartyId"), null, null, false);
            if (ret.isEmpty()) {
                throw new GnServiceException(OfbizErrors.INVALID_PARAMETERS, "No context found for the userLoginId.");
            } else if (ret.size() > 1) {
                throw new GnServiceException(OfbizErrors.INVALID_PARAMETERS, "More than one context found for the userLoginId.");
            } else { //size==1
                contextId = (String) ((ret.get(0)).get("contextPartyId"));
            }
            if (!gnSecurity.hasPermissionByContext(PermissionsOfbiz.GN_AUTH_DRAFT.name(), contextId)) {
                throw new GnServiceException(OfbizErrors.ACCESS_DENIED, "Import public authorization not allowed.");
            }
        }

        GenericValue realUserLogin = delegator.findOne("UserLogin", UtilMisc.toMap("userLoginId", userLoginId), false);
        String userLoginPartyId = (String) realUserLogin.get("partyId");
        Map<String, Object> companyEmployed = authorizationHelper.gnFindCompanyWhereUserIsEmployed(userLoginPartyId);
        String companyEmployedPartyId = (companyEmployed != null) ? (String) companyEmployed.get("partyId") : null;
        String userCompanyPartyId = (userCompany != null) ? (String) userCompany.get("partyId") : null;
        if (companyEmployedPartyId == null || !companyEmployedPartyId.equalsIgnoreCase(userCompanyPartyId)) {
            throw new GnServiceException(OfbizErrors.NOT_ENOUGH_PERMISSIONS,
                    "The found Company in tree is different from the Company where the user is employed.");
        }

        String originId = (String) authorization.get("originId");
        if (UtilValidate.isEmpty(originId)) {
            throw new GnServiceException(OfbizErrors.ENTITY_EXCEPTION, "You cannot import an authorization without specifying origin.");
        }
        if (!AuthorizationOriginOfbiz.GN_AUTH_ORG_SCRAPING.name().equals(originId)) {
            throw new GnServiceException(OfbizErrors.ACCESS_DENIED, "You can import only authorizations with '"
                    + AuthorizationOriginOfbiz.GN_AUTH_ORG_SCRAPING.name() + " 'origin.");
        }
        //******** check end ********

        String nodeKeyFrom = (String) authorization.get("nodeKeyFrom");
        String partyIdFrom = (String) authorization.get("partyIdFrom");

        Map<String, Object> partyNodeTo = UtilGenerics.checkMap(authorization.get("partyNodeTo"));

        String partyIdTo = authorizationPartyNodeHelper.fixIncomingPartyId(partyNodeTo, isPrivate, OfbizErrors.HOLDER_BASE_NOT_FOUND);

        Map<String, Object> otherPartyNodeTo = UtilGenerics.checkMap(authorization.get("otherPartyNodeTo"));
        String taxIdentificationNumber = null;
        if (UtilValidate.isEmpty(otherPartyNodeTo)) {
            if ("N".equals(authorization.get("isPrivate")) && UtilValidate.isNotEmpty(partyIdTo)) {
                Map<String, Object> ret = dispatcher.runSync("gnFindOwnerCompanyByCompanyBaseId", UtilMisc.toMap("userLogin", context.get("userLogin"), "companyBasePartyId", partyIdTo));
                otherPartyNodeTo = UtilGenerics.checkMap(dispatcher.runSync("gnInternalFindPartyNodeById", UtilMisc.toMap("partyId", ret.get("companyPartyId"), "userLogin", userLogin, "findRelationships", "N")).get("partyNode"));
            }
        } else {
            String otherPartyIdTo = authorizationPartyNodeHelper.fixIncomingPartyId(otherPartyNodeTo, true, OfbizErrors.HOLDER_BASE_NOT_FOUND);
        }
        if (otherPartyNodeTo != null)
            taxIdentificationNumber = (String) otherPartyNodeTo.get("taxIdentificationNumber");

        partyIdFrom = authorizationPartyNodeHelper.getPartyId(partyIdFrom, nodeKeyFrom, OfbizErrors.ISSUER_NOT_FOUND, "PartyIdFrom and NodeKeyFrom cannot be empty both.");

        String authorizationKey = (String) authorization.get("authorizationKey");
        String number = (String) authorization.get("number");

        Map<String, Object> result = ServiceUtil.returnSuccess();
        String typeId = (String) authorization.get("typeId");

        if (!authorizationHelper.existsAuthorizationByLogicalKey(authorizationKey, partyIdFrom, partyIdTo, number, taxIdentificationNumber,
                (otherPartyNodeTo == null ? null : (String) otherPartyNodeTo.get("description")),
                partyNodeTo, AuthorizationTypesOfbiz.valueOf(typeId), ownerPartyId, null)) {
            String description = (String) authorization.get("description");
            String textData = (String) authorization.get("textData");

            List<Map<String, Object>> items = UtilMisc.getListFromMap(authorization, "authorizationItems");

            result.putAll(authorizationHelper.bulkImportAuthorizationWithItems(
                    ownerNodeKey, originId, ownerPartyId, partyNodeTo, otherPartyNodeTo, partyIdFrom, typeId, number,
                    textData, description, items, (String) authorization.get("isPrivate")));
        }
        return result;
    }

    /**
     * @param authorization
     * @return
     * @throws GenericEntityException
     * @throws GenericServiceException
     */
    public Map<String, Object> bulkCreateFromWebApi(Map<String, Object> authorization) throws GenericEntityException, GenericServiceException {
        if ("Y".equals(authorization.get("isPrivate"))) {
            return bulkCreateFromWebApiPrivate(authorization);
        } else {
            return bulkCreateFromWebApiPublic(authorization);
        }
    }

    /**
     * @param authorization
     * @return
     * @throws GenericEntityException
     * @throws GenericServiceException
     */
    public Map<String, Object> bulkCreateFromWebApiPublic(Map<String, Object> authorization) throws GenericEntityException, GenericServiceException {
        String isPrivate = (String) authorization.get("isPrivate");
        //******** check start ********
        if ("Y".equals(isPrivate)) {
            throw new GnServiceException(OfbizErrors.INVALID_PARAMETERS, "You cannot import a private authorization.");
        } else {
            isPrivate = "N";
        }
        String originId = userLogin.getString("originId");
        if (UtilValidate.isEmpty(originId)) {
            throw new GnServiceException(OfbizErrors.INVALID_PARAMETERS, "You cannot import an authorization without specifying origin.");
        }
        if (!AuthorizationOriginOfbiz.GN_AUTH_ORG_APP.name().equals(originId)) {
            throw new GnServiceException(OfbizErrors.ACCESS_DENIED, "You can create only authorization with '"
                    + AuthorizationOriginOfbiz.GN_AUTH_ORG_APP.name() + " 'origin.");
        }
        //******** check end ********

        //from createUpdateAuth
        AuthorizationPartyNodeHelper authorizationPartyNodeHelper = new AuthorizationPartyNodeHelper(dctx, context);
        String nodeKeyFrom = (String) authorization.get("nodeKeyFrom");
        String partyIdFrom = (String) authorization.get("partyIdFrom");
        partyIdFrom = authorizationPartyNodeHelper.getPartyId(partyIdFrom, nodeKeyFrom, OfbizErrors.ISSUER_NOT_FOUND, "PartyIdFrom and NodeKeyFrom cannot be empty both.");
        authorization.put("partyIdFrom", partyIdFrom);
        Map<String, Object> partyNodeTo = UtilGenerics.checkMap(authorization.get("partyNodeTo"));
        String partyIdTo = authorizationPartyNodeHelper.fixIncomingPartyId(partyNodeTo, false, OfbizErrors.HOLDER_BASE_NOT_FOUND);
        //search for right values in partyNodeTo
        partyNodeTo = UtilGenerics.checkMap(dispatcher.runSync("gnInternalFindPartyNodeById", UtilMisc.toMap("partyId", partyIdTo, "userLogin", userLogin, "findRelationships", "N")).get("partyNode"));
        authorization.put("partyNodeTo", partyNodeTo);

        Errors errors = new Errors("authorization");
        new BulkCreateAuthorizationValidator().validate(errors, authorization);
        Debug.log(errors.toString(), module);
        boolean isError = errors.size() > 0;
        if (isError) {
            Debug.log(errors.toString(), module);
            throw new GnServiceException(OfbizErrors.INVALID_PARAMETERS, errors.toString());
        }

        ///from createUpdateAuth end
        String typeId = (String) authorization.get("typeId");
        AuthorizationTypesOfbiz type = AuthorizationTypesOfbiz.valueOf(typeId);
        String number = (String) authorization.get("number");
        String description = (String) authorization.get("description");
        String textData = (String) authorization.get("textData");

        Map<Object, Object> contextNode = UtilGenerics.checkMap(getCurrentContext().get("partyNode"));
        String ownerNodePartyId = (String) contextNode.get("partyId");

        //search for public holder company (never use that in the authorization passed!!!)
        Map<String, Object> ret = dispatcher.runSync("gnFindOwnerCompanyByCompanyBaseId", UtilMisc.toMap("userLogin", context.get("userLogin"), "companyBasePartyId", partyIdTo));
        Map<String, Object> otherPartyNodeToLoad = UtilGenerics.checkMap(dispatcher.runSync("gnInternalFindPartyNodeById", UtilMisc.toMap("partyId", ret.get("companyPartyId"), "userLogin", userLogin, "findRelationships", "N")).get("partyNode"));
        if (otherPartyNodeToLoad == null)
            throw new GnServiceException(OfbizErrors.GENERIC, "bulkCreateFromWebApi: could not load otherPartyNodeToLoad (that is holder company) from id of partyNodeTo (that is public holder company base)");
        String taxIdentificationNumber = (String) otherPartyNodeToLoad.get("taxIdentificationNumber");
        authorization.put("otherPartyNodeTo", otherPartyNodeToLoad);

        Map<String, Object> address = UtilGenerics.checkMap(partyNodeTo.get("address"));

        List<String> keys = authorizationHelper.findAuthorizationByLogicalKey(null, partyIdFrom, partyIdTo, number, taxIdentificationNumber, address, type);
        for (String key : keys) {
            //delete if similar and has draft status otherwise return an exception
            GenericValue auth = findAuthorizationById(null, key);
            if (AuthorizationStatusOfbiz.GN_AUTH_DRAFT.name().equals(auth.get("statusId")) &&
                    AuthorizationOriginOfbiz.GN_AUTH_ORG_APP.name().equals(auth.get("originId"))
                    ) {
                authorizationHelper.deleteAuthorization(null, key);
            } else {
                StringBuilder error = new StringBuilder();
                error.append("Already exists an Authorization with the given logical key: [partyIdFrom=");
                error.append(partyIdFrom);
                error.append(" (case insensitive)number=");
                error.append(number);
                error.append(" ownerNodeId=");
                error.append(ownerNodePartyId);
                error.append(" statusId=");
                error.append(auth.get("statusId"));
                error.append(" originId=");
                error.append(auth.get("originId"));
                throw new GnServiceException(OfbizErrors.DUPLICATED_AUTHORIZATION, error.toString());
            }
        }
        String currentAuthorizationKey = (String) authorization.get("authorizationKey");
        if (UtilValidate.areEqual(type, AuthorizationTypesOfbiz.GN_AUTH_ALBO)) {
            List<String> result = authorizationHelper.findDuplicatedAlboAuthorization(type, taxIdentificationNumber, ownerNodePartyId, partyIdFrom, null);
            for (String r : result) { //(CREATE)
                //delete if similar and has draft status otherwise return an exception
                GenericValue auth = findAuthorizationById(null, r);
                if (AuthorizationStatusOfbiz.GN_AUTH_DRAFT.name().equals(auth.get("statusId")) &&
                        AuthorizationOriginOfbiz.GN_AUTH_ORG_APP.name().equals(auth.get("originId"))
                        ) {
                    authorizationHelper.deleteAuthorization(null, r);
                } else {
                    StringBuilder alboError = new StringBuilder();
                    alboError.append("Already exists an Albo Authorization on the same node with the given taxIdentificationNumber [");
                    alboError.append(taxIdentificationNumber);
                    alboError.append("]");
                    throw new GnServiceException(OfbizErrors.DUPLICATED_ALBO_AUTHORIZATION, alboError.toString());
                }
            }
        } else if (UtilValidate.areEqual(type, AuthorizationTypesOfbiz.GN_AUTH_AIA)) {
            List<String> result = authorizationHelper.checkAiaAuaDuplicatedAuthorization(ownerNodePartyId, type, (String) otherPartyNodeToLoad.get("description"),
                    partyNodeTo, currentAuthorizationKey == null ? null : AuthorizationKey.fromString(currentAuthorizationKey).getUuid(), OfbizErrors.DUPLICATED_AIA_AUTHORIZATION, true);
            for (String r : result) { //(DRAFT)
                //delete if similar and has draft status otherwise return an exception
                GenericValue auth = findAuthorizationById(null, r);
                if (AuthorizationStatusOfbiz.GN_AUTH_DRAFT.name().equals(auth.get("statusId")) &&
                        AuthorizationOriginOfbiz.GN_AUTH_ORG_APP.name().equals(auth.get("originId"))
                        ) {
                    authorizationHelper.deleteAuthorization(null, r);
                }
            }
            authorizationHelper.checkAiaAuaDuplicatedAuthorization(ownerNodePartyId, type, (String) otherPartyNodeToLoad.get("description"),
                    partyNodeTo, currentAuthorizationKey == null ? null : AuthorizationKey.fromString(currentAuthorizationKey).getUuid(), OfbizErrors.DUPLICATED_AIA_AUTHORIZATION, false);
        } else if (UtilValidate.areEqual(type, AuthorizationTypesOfbiz.GN_AUTH_AUA)) {
            List<String> result = authorizationHelper.checkAiaAuaDuplicatedAuthorization(ownerNodePartyId, type, (String) otherPartyNodeToLoad.get("description"),
                    partyNodeTo, currentAuthorizationKey == null ? null : AuthorizationKey.fromString(currentAuthorizationKey).getUuid(), OfbizErrors.DUPLICATED_AUA_AUTHORIZATION, true);
            for (String r : result) { //(DRAFT)
                //delete if similar and has draft status otherwise return an exception
                GenericValue auth = findAuthorizationById(null, r);
                if (AuthorizationStatusOfbiz.GN_AUTH_DRAFT.name().equals(auth.get("statusId")) &&
                        AuthorizationOriginOfbiz.GN_AUTH_ORG_APP.name().equals(auth.get("originId"))
                        ) {
                    authorizationHelper.deleteAuthorization(null, r);
                }
                authorizationHelper.checkAiaAuaDuplicatedAuthorization(ownerNodePartyId, type, (String) otherPartyNodeToLoad.get("description"),
                        partyNodeTo, currentAuthorizationKey == null ? null : AuthorizationKey.fromString(currentAuthorizationKey).getUuid(), OfbizErrors.DUPLICATED_AUA_AUTHORIZATION, false);
            }
        }
        Map<String, Object> authCreateResult = authorizationHelper.gnCreateAuthorization(partyIdFrom, partyNodeTo, otherPartyNodeToLoad, typeId, number, textData, description, originId, isPrivate);
        String authorizationKey = (String) authCreateResult.get("authorizationKey");
        String agreementId = (String) authCreateResult.get("agreementId");

        List<Map<String, Object>> items = UtilMisc.getListFromMap(authorization, "authorizationItems");

        //create items' authorization imported
        if (UtilValidate.isNotEmpty(items)) {
            for (Map<String, Object> item : items) {
                String agreementText = (String) item.get("agreementText");
                String simplified = (String) item.get("simplified");
                String holderRoleId = (String) item.get("holderRoleId");
                String categoryClassEnumId = (String) item.get("categoryClassEnumId");

                @SuppressWarnings("unchecked")
                List<Map<String, Object>> agreementTerms = (List<Map<String, Object>>) item.get("agreementTerms");

                Timestamp validFromDate = getTimestamp(item.get("validFromDate"));
                Timestamp validThruDate = getTimestamp(item.get("validThruDate"));
                Timestamp suspendFromDate = getTimestamp(item.get("suspendFromDate"));
                Timestamp suspendThruDate = getTimestamp(item.get("suspendThruDate"));

                Map<String, Object> detailKeysMap = new AuthorizationItemHelper(dctx, context).createAuthorizationItem(agreementId, authorizationKey,
                        null, agreementText, simplified, holderRoleId, categoryClassEnumId, validFromDate, validThruDate, suspendFromDate, suspendThruDate, agreementTerms, typeId);
            }
        }

        return authCreateResult;
    }

    /**
     * @param authorization
     * @return
     * @throws GenericEntityException
     * @throws GenericServiceException
     */
    public Map<String, Object> bulkCreateFromWebApiPrivate(Map<String, Object> authorization) throws GenericEntityException, GenericServiceException {
        String isPrivateString = (String) authorization.get("isPrivate");
        boolean isPrivate = "Y".equals(isPrivateString);
        //******** check start ********
        if ("Y".equals(isPrivateString)) {
            //TODO removed
            //  throw new GnServiceException(OfbizErrors.INVALID_PARAMETERS, "You cannot import a private authorization.");
        } else {
            isPrivateString = "N";
        }

        String originId = userLogin.getString("originId");
        if (UtilValidate.isEmpty(originId)) {
            throw new GnServiceException(OfbizErrors.INVALID_PARAMETERS, "You cannot import an authorization without specifying origin.");
        }
        if (!AuthorizationOriginOfbiz.GN_AUTH_ORG_APP.name().equals(originId)) {
            throw new GnServiceException(OfbizErrors.ACCESS_DENIED, "You can create only authorization with '"
                    + AuthorizationOriginOfbiz.GN_AUTH_ORG_APP.name() + " 'origin.");
        }
        //******** check end ********

        //from createUpdateAuth
        AuthorizationPartyNodeHelper authorizationPartyNodeHelper = new AuthorizationPartyNodeHelper(dctx, context);
        String nodeKeyFrom = (String) authorization.get("nodeKeyFrom");
        String partyIdFrom = (String) authorization.get("partyIdFrom");
        partyIdFrom = authorizationPartyNodeHelper.getPartyId(partyIdFrom, nodeKeyFrom, OfbizErrors.ISSUER_NOT_FOUND, "PartyIdFrom and NodeKeyFrom cannot be empty both.");
        authorization.put("partyIdFrom", partyIdFrom);
        Map<String, Object> partyNodeTo = UtilGenerics.checkMap(authorization.get("partyNodeTo"));
        //String partyIdTo;
        /* if (isPrivate) {
        partyIdTo = authorizationPartyNodeHelper.fixIncomingPartyId(partyNodeTo, true, OfbizErrors.HOLDER_BASE_NOT_FOUND);
        if (UtilValidate.isNotEmpty(partyIdTo)) {
            partyNodeTo = UtilGenerics.checkMap(dispatcher.runSync("gnInternalFindPartyNodeById", UtilMisc.toMap("partyId", partyIdTo, "userLogin", userLogin, "findRelationships", "N")).get("partyNode"));
            authorization.put("partyNodeTo", partyNodeTo);
        }
        } else {
            partyIdTo = authorizationPartyNodeHelper.fixIncomingPartyId(partyNodeTo, false, OfbizErrors.HOLDER_BASE_NOT_FOUND);
            //search for right values in partyNodeTo
            partyNodeTo = UtilGenerics.checkMap(dispatcher.runSync("gnInternalFindPartyNodeById", UtilMisc.toMap("partyId", partyIdTo, "userLogin", userLogin, "findRelationships", "N")).get("partyNode"));
            authorization.put("partyNodeTo", partyNodeTo);
        }*/
        Errors errors = new Errors("authorization");
        //TODO validate public
        new BulkCreatePrivateAuthorizationValidator().validate(errors, authorization);
        Debug.log(errors.toString(), module);
        boolean isError = errors.size() > 0;
        if (isError) {
            Debug.log(errors.toString(), module);
            throw new GnServiceException(OfbizErrors.INVALID_PARAMETERS, errors.toString());
        }

        ///from createUpdateAuth end
        String typeId = (String) authorization.get("typeId");
        AuthorizationTypesOfbiz type = AuthorizationTypesOfbiz.valueOf(typeId);
        String number = (String) authorization.get("number");
        String description = (String) authorization.get("description");
        String textData = (String) authorization.get("textData");

        Map<Object, Object> contextNode = UtilGenerics.checkMap(getCurrentContext().get("partyNode"));
        String ownerNodePartyId = (String) contextNode.get("partyId");

        String partyIdTo = null;
        if (UtilValidate.isNotEmpty(partyNodeTo.get("nodeKey")) || UtilValidate.isNotEmpty(partyNodeTo.get("partyId"))) {
            partyIdTo = authorizationPartyNodeHelper.fixIncomingPartyId(partyNodeTo, true, OfbizErrors.HOLDER_BASE_NOT_FOUND);
            authorizationPartyNodeHelper.fixIncomingPartyId(partyNodeTo, false, OfbizErrors.HOLDER_BASE_NOT_FOUND);
            partyNodeTo = UtilGenerics.checkMap(dispatcher.runSync("gnInternalFindPartyNodeById", UtilMisc.toMap("partyId", partyIdTo, "userLogin", userLogin, "findRelationships", "N")).get("partyNode"));
            authorization.put("partyNodeTo", partyNodeTo);

            //search for public holder company (never use that in the authorization passed!!!)
            Map<String, Object> ret = dispatcher.runSync("gnFindOwnerCompanyByCompanyBaseId", UtilMisc.toMap("userLogin", context.get("userLogin"), "companyBasePartyId", partyIdTo));
            Map<String, Object> otherPartyNodeToLoad = UtilGenerics.checkMap(dispatcher.runSync("gnInternalFindPartyNodeById", UtilMisc.toMap("partyId", ret.get("companyPartyId"), "userLogin", userLogin, "findRelationships", "N")).get("partyNode"));
            if (otherPartyNodeToLoad == null)
                throw new GnServiceException(OfbizErrors.GENERIC, "bulkCreateFromWebApi: could not load otherPartyNodeToLoad (that is holder company) from id of partyNodeTo (that is public holder company base)");
            //String taxIdentificationNumber = (String) otherPartyNodeToLoad.get("taxIdentificationNumber");
            authorization.put("otherPartyNodeTo", otherPartyNodeToLoad);

            //Map<String, Object> address = UtilGenerics.checkMap(partyNodeTo.get("address"));
        } else {
            Map<String, Object> address = UtilGenerics.checkMap(partyNodeTo.get("address"));
            boolean foreign = !(UtilValidate.areEqual(address.get("countryGeoName"), "Italy") || UtilValidate.areEqual(address.get("countryGeoName"), "IT"));
            //validating postalCode
            GenericValue geo = delegator.findByPrimaryKey("Geo", UtilMisc.toMap("geoId", "IT-GN-CAP-" + address.get("postalCode")));
            if (!foreign && UtilValidate.isNotEmpty(geo)) address.put("postalCodeGeoId", geo.getString("geoId"));
            //validating country
            if (UtilValidate.isNotEmpty(address.get("countryGeoName"))) {
                EntityCondition cond = EntityCondition.makeCondition(
                        EntityCondition.makeCondition("geoName", EntityOperator.EQUALS, address.get("countryGeoName")),
                        EntityJoinOperator.OR,
                        EntityCondition.makeCondition("geoCode", EntityOperator.EQUALS, address.get("countryGeoName"))
                );
               cond= EntityCondition.makeCondition(cond,EntityJoinOperator.AND,EntityCondition.makeCondition("geoTypeId","COUNTRY"));
                List<GenericValue> cList = delegator.findList("Geo", cond, null, null, null, false);
                if (cList.size() != 1)
                    throw new GnServiceException(OfbizErrors.MISSING_DATA, "countryName not valid");
            }
        }

        Map<String, Object> otherPartyNodeTo = UtilGenerics.checkMap(authorization.get("otherPartyNodeTo"));
        Map<String, Object> address = UtilGenerics.checkMap(partyNodeTo.get("address"));
        String taxIdentificationNumber = (String) otherPartyNodeTo.get("taxIdentificationNumber");


        List<String> keys = authorizationHelper.findAuthorizationByLogicalKey(null, partyIdFrom, partyIdTo, number, taxIdentificationNumber, address, type);
        for (String key : keys) {
            //delete if similar and has draft status otherwise return an exception
            GenericValue auth = findAuthorizationById(null, key);
            if (AuthorizationStatusOfbiz.GN_AUTH_DRAFT.name().equals(auth.get("statusId")) &&
                    AuthorizationOriginOfbiz.GN_AUTH_ORG_APP.name().equals(auth.get("originId"))
                    ) {
                authorizationHelper.deleteAuthorization(null, key);
            } else {
                StringBuilder error = new StringBuilder();
                error.append("Already exists an Authorization with the given logical key: [partyIdFrom=");
                error.append(partyIdFrom);
                error.append(" (case insensitive)number=");
                error.append(number);
                error.append(" ownerNodeId=");
                error.append(ownerNodePartyId);
                error.append(" statusId=");
                error.append(auth.get("statusId"));
                error.append(" originId=");
                error.append(auth.get("originId"));
                throw new GnServiceException(OfbizErrors.DUPLICATED_AUTHORIZATION, error.toString());
            }
        }
        String currentAuthorizationKey = (String) authorization.get("authorizationKey");
        if (UtilValidate.areEqual(type, AuthorizationTypesOfbiz.GN_AUTH_ALBO)) {
            List<String> result = authorizationHelper.findDuplicatedAlboAuthorization(type, taxIdentificationNumber, ownerNodePartyId, partyIdFrom, null);
            for (String r : result) { //(CREATE)
                //delete if similar and has draft status otherwise return an exception
                GenericValue auth = findAuthorizationById(null, r);
                if (AuthorizationStatusOfbiz.GN_AUTH_DRAFT.name().equals(auth.get("statusId")) &&
                        AuthorizationOriginOfbiz.GN_AUTH_ORG_APP.name().equals(auth.get("originId"))
                        ) {
                    authorizationHelper.deleteAuthorization(null, r);
                } else {
                    StringBuilder alboError = new StringBuilder();
                    alboError.append("Already exists an Albo Authorization on the same node with the given taxIdentificationNumber [");
                    alboError.append(taxIdentificationNumber);
                    alboError.append("]");
                    throw new GnServiceException(OfbizErrors.DUPLICATED_ALBO_AUTHORIZATION, alboError.toString());
                }
            }
        } else if (UtilValidate.areEqual(type, AuthorizationTypesOfbiz.GN_AUTH_AIA)) {
            List<String> result = authorizationHelper.checkAiaAuaDuplicatedAuthorization(ownerNodePartyId, type, (String) otherPartyNodeTo.get("description"),
                    partyNodeTo, currentAuthorizationKey == null ? null : AuthorizationKey.fromString(currentAuthorizationKey).getUuid(), OfbizErrors.DUPLICATED_AIA_AUTHORIZATION, true);
            for (String r : result) { //(DRAFT)
                //delete if similar and has draft status otherwise return an exception
                GenericValue auth = findAuthorizationById(null, r);
                if (AuthorizationStatusOfbiz.GN_AUTH_DRAFT.name().equals(auth.get("statusId")) &&
                        AuthorizationOriginOfbiz.GN_AUTH_ORG_APP.name().equals(auth.get("originId"))
                        ) {
                    authorizationHelper.deleteAuthorization(null, r);
                }
            }
            authorizationHelper.checkAiaAuaDuplicatedAuthorization(ownerNodePartyId, type, (String) otherPartyNodeTo.get("description"),
                    partyNodeTo, currentAuthorizationKey == null ? null : AuthorizationKey.fromString(currentAuthorizationKey).getUuid(), OfbizErrors.DUPLICATED_AIA_AUTHORIZATION, false);
        } else if (UtilValidate.areEqual(type, AuthorizationTypesOfbiz.GN_AUTH_AUA)) {
            List<String> result = authorizationHelper.checkAiaAuaDuplicatedAuthorization(ownerNodePartyId, type, (String) otherPartyNodeTo.get("description"),
                    partyNodeTo, currentAuthorizationKey == null ? null : AuthorizationKey.fromString(currentAuthorizationKey).getUuid(), OfbizErrors.DUPLICATED_AUA_AUTHORIZATION, true);
            for (String r : result) { //(DRAFT)
                //delete if similar and has draft status otherwise return an exception
                GenericValue auth = findAuthorizationById(null, r);
                if (AuthorizationStatusOfbiz.GN_AUTH_DRAFT.name().equals(auth.get("statusId")) &&
                        AuthorizationOriginOfbiz.GN_AUTH_ORG_APP.name().equals(auth.get("originId"))
                        ) {
                    authorizationHelper.deleteAuthorization(null, r);
                }
                authorizationHelper.checkAiaAuaDuplicatedAuthorization(ownerNodePartyId, type, (String) otherPartyNodeTo.get("description"),
                        partyNodeTo, currentAuthorizationKey == null ? null : AuthorizationKey.fromString(currentAuthorizationKey).getUuid(), OfbizErrors.DUPLICATED_AUA_AUTHORIZATION, false);
            }
        }
        Map<String, Object> authCreateResult = authorizationHelper.gnCreateAuthorization(partyIdFrom, partyNodeTo, otherPartyNodeTo, typeId, number, textData, description, originId, isPrivateString);
        String authorizationKey = (String) authCreateResult.get("authorizationKey");
        String agreementId = (String) authCreateResult.get("agreementId");

        List<Map<String, Object>> items = UtilMisc.getListFromMap(authorization, "authorizationItems");

        //create items' authorization imported
        if (UtilValidate.isNotEmpty(items)) {
            for (Map<String, Object> item : items) {
                String agreementText = (String) item.get("agreementText");
                String simplified = (String) item.get("simplified");
                String holderRoleId = (String) item.get("holderRoleId");
                String categoryClassEnumId = (String) item.get("categoryClassEnumId");

                @SuppressWarnings("unchecked")
                List<Map<String, Object>> agreementTerms = (List<Map<String, Object>>) item.get("agreementTerms");

                Timestamp validFromDate = getTimestamp(item.get("validFromDate"));
                Timestamp validThruDate = getTimestamp(item.get("validThruDate"));
                Timestamp suspendFromDate = getTimestamp(item.get("suspendFromDate"));
                Timestamp suspendThruDate = getTimestamp(item.get("suspendThruDate"));

                Map<String, Object> detailKeysMap = new AuthorizationItemHelper(dctx, context).createAuthorizationItem(agreementId, authorizationKey,
                        null, agreementText, simplified, holderRoleId, categoryClassEnumId, validFromDate, validThruDate, suspendFromDate, suspendThruDate, agreementTerms, typeId);
            }
        }

        return authCreateResult;
    }

    /**
     * @param authorization
     * @return
     * @throws GenericEntityException
     * @throws GenericServiceException
     */
    public Map<String, Object> bulkUpdateFromWebApi(Map<String, Object> authorization) throws GenericEntityException, GenericServiceException {
        String isPrivate = (String) authorization.get("isPrivate");

        //******** check start ********
        if ("Y".equals(isPrivate)) {
            throw new GnServiceException(OfbizErrors.ACCESS_DENIED, "You cannot import a private authorization.");
        } else {
            isPrivate = "N";
        }
        String originId = userLogin.getString("originId");
        if (UtilValidate.isEmpty(originId)) {
            throw new GnServiceException(OfbizErrors.INVALID_PARAMETERS, "You cannot import an authorization without specifying origin.");
        }
        if (!AuthorizationOriginOfbiz.GN_AUTH_ORG_APP.name().equals(originId)) {
            throw new GnServiceException(OfbizErrors.ACCESS_DENIED, "You can create only authorization with '"
                    + AuthorizationOriginOfbiz.GN_AUTH_ORG_APP.name() + " 'origin.");
        }
        //******** check end ********

        Map<String, Object> authUpdateResult = new HashMap<String, Object>();
        Map<Object, Object> contextNode = UtilGenerics.checkMap(getCurrentContext().get("partyNode"));
        String ownerNodeId = (String) contextNode.get("partyId");

        String _authorizationKey = (String) authorization.get("authorizationKey");
        String _agreementId = (String) authorization.get("agreementId");

        //from createUpdateAuth
        AuthorizationPartyNodeHelper authorizationPartyNodeHelper = new AuthorizationPartyNodeHelper(dctx, context);
        String nodeKeyFrom = (String) authorization.get("nodeKeyFrom");
        String partyIdFrom = (String) authorization.get("partyIdFrom");
        partyIdFrom = authorizationPartyNodeHelper.getPartyId(partyIdFrom, nodeKeyFrom, OfbizErrors.ISSUER_NOT_FOUND, "PartyIdFrom and NodeKeyFrom cannot be empty both.");
        authorization.put("partyIdFrom", partyIdFrom);
        Map<String, Object> partyNodeTo = UtilGenerics.checkMap(authorization.get("partyNodeTo"));
        String partyIdTo = authorizationPartyNodeHelper.fixIncomingPartyId(partyNodeTo, false, OfbizErrors.HOLDER_BASE_NOT_FOUND);
        //search for right values in partyNodeTo
        partyNodeTo = UtilGenerics.checkMap(dispatcher.runSync("gnInternalFindPartyNodeById", UtilMisc.toMap("partyId", partyIdTo, "userLogin", userLogin, "findRelationships", "N")).get("partyNode"));
        authorization.put("partyNodeTo", partyNodeTo);

        Errors errors = new Errors("authorization");
        new BulkUpdateAuthorizationValidator().validate(errors, authorization);
        Debug.log(errors.toString(), module);
        boolean isError = errors.size() > 0;
        if (isError) {
            Debug.log(errors.toString(), module);
            throw new GnServiceException(OfbizErrors.INVALID_PARAMETERS, errors.toString());
        }

        Map<String, Object> copyResult = new AuthorizationPublicationHelper(dctx, context).gnCopyAuthorization(_agreementId, _authorizationKey);
        Map<String, Object> draftAuth = UtilGenerics.checkMap(copyResult.get("authorization"));
        String authorizationKey = (String) draftAuth.get("authorizationKey");
        String agreementId = (String) draftAuth.get("agreementId");
        String typeId = (String) authorization.get("typeId");
        AuthorizationTypesOfbiz type = AuthorizationTypesOfbiz.valueOf(typeId);
        String number = (String) authorization.get("number");
        String description = (String) authorization.get("description");
        String textData = (String) authorization.get("textData");

        //check logical key and other albo

        //search for public holder company (never use that in the authorization passed!!!)
        Map<String, Object> ret = dispatcher.runSync("gnFindOwnerCompanyByCompanyBaseId", UtilMisc.toMap("userLogin", context.get("userLogin"), "companyBasePartyId", partyIdTo));
        Map<String, Object> otherPartyNodeToLoad = UtilGenerics.checkMap(dispatcher.runSync("gnInternalFindPartyNodeById", UtilMisc.toMap("partyId", ret.get("companyPartyId"), "userLogin", userLogin, "findRelationships", "N")).get("partyNode"));
        if (otherPartyNodeToLoad == null)
            throw new GnServiceException(OfbizErrors.GENERIC, "bulkCreateFromWebApi: could not load otherPartyNodeToLoad (that is holder company) from id of partyNodeTo (that is public holder company base)");
        String taxIdentificationNumber = (String) otherPartyNodeToLoad.get("taxIdentificationNumber");
        authorization.put("otherPartyNodeTo", otherPartyNodeToLoad);

        Map<String, Object> address = UtilGenerics.checkMap(partyNodeTo.get("address"));
        if (address == null) address = FastMap.newInstance();
        List<String> result = authorizationHelper.findAuthorizationByLogicalKey(null, partyIdFrom, partyIdTo, number, taxIdentificationNumber, address, type);
        if (result.size() > 1 || (result.size() == 1 && !result.get(0).equals(authorizationKey))) {
            StringBuilder error = new StringBuilder();
            error.append("Already exists an Authorization with the given logical key: [partyIdFrom=");
            error.append(partyIdFrom);
            error.append(" partyNodeTo address=");
            error.append(address.get("address1")).append(" ").append(address.get("streetNumber")).append(" ").append(address.get("village"));
            error.append(" partyNodeTo taxIdentificationNumber=");
            error.append(taxIdentificationNumber);
            error.append(" (case insensitive)number=");
            error.append(number);
            error.append(" ownerNodeId=");
            error.append(ownerNodeId);
            error.append(" statusId=");
            error.append(authorization.get("statusId"));
            error.append(" originId=");
            error.append(authorization.get("originId"));
            throw new GnServiceException(OfbizErrors.DUPLICATED_AUTHORIZATION, error.toString());
        }
        result = authorizationHelper.findDuplicatedAlboAuthorization(type, taxIdentificationNumber, ownerNodeId, partyIdFrom, null);
        if (result.size() > 1 || (result.size() == 1 && !result.get(0).equals(authorizationKey))) {//MODIFY
            StringBuilder alboError = new StringBuilder();
            alboError.append("Already exists an Albo Authorization on the same node with the given taxIdentificationNumber [");
            alboError.append(taxIdentificationNumber);
            alboError.append("]");
            throw new GnServiceException(OfbizErrors.DUPLICATED_ALBO_AUTHORIZATION, alboError.toString());
        }
        //end check

        authorizationHelper.gnUpdateAuthorization(agreementId, authorizationKey, partyIdFrom, partyNodeTo, otherPartyNodeToLoad, number, textData, description, typeId, originId, isPrivate);


        List<Map<String, Object>> itemsOld = UtilMisc.getListFromMap(draftAuth, "authorizationItems");
        List<Map<String, Object>> itemsNew = UtilMisc.getListFromMap(authorization, "authorizationItems");

        for (Map<String, Object> item : itemsNew) {
            String itemKey = (String) item.get("authorizationItemKey");
            if (UtilValidate.isNotEmpty(itemKey)) {
                String itemUuid = itemKey.substring(itemKey.lastIndexOf('#') + 1);
                itemKey = String.format("%s#%s", authorizationKey, itemUuid);
                item.put("authorizationItemKey", itemKey);
                List<Map<String, Object>> terms = UtilGenerics.checkList(item.get("agreementTerms"));
                for (Map<String, Object> term : terms) {
                    String termKey = (String) term.get("authorizationTermKey");
                    if (UtilValidate.isNotEmpty(termKey)) {
                        String termUuid = termKey.substring(termKey.lastIndexOf('#') + 1);
                        termKey = String.format("%s#%s", itemKey, termUuid);
                        term.put("authorizationTermKey", termKey);
                        term.remove("agreementTermId");
                    }
                }
            }
        }


        CollectionDiff<Map<String, Object>, Map<String, Object>> diff = new CollectionDiff<Map<String, Object>, Map<String, Object>>(itemsOld, itemsNew) {
            @Override
            protected boolean isItemEqual(Map<String, Object> left, Map<String, Object> right) {
                String oldUuid = (String) left.get("authorizationItemKey");
                String newUuid = (String) right.get("authorizationItemKey");
                return UtilValidate.areEqual(oldUuid, newUuid);
            }
        };
        diff.compare();
        for (Map<String, Object> item : diff.getLeftOnly())
            deleteItem(item, agreementId, authorizationKey);
        for (CollectionDiff.Pair<Map<String, Object>, Map<String, Object>> itemPair : diff.getBoth())
            updateItem(itemPair.getRight(), agreementId, authorizationKey);
        for (Map<String, Object> item : diff.getRightOnly())
            createItem(item, agreementId, authorizationKey);

        authUpdateResult.put("agreementId", agreementId);
        authUpdateResult.put("authorizationKey", authorizationKey);
        return authUpdateResult;
    }


    private Map<String, Object> createItem(Map<String, Object> item, String agreementId, String authorizationKey) throws GenericServiceException, GenericEntityException {
        String agreementText = (String) item.get("agreementText");
        String simplified = (String) item.get("simplified");
        String holderRoleId = (String) item.get("holderRoleId");
        //String agreementId = (String) item.get("agreementId");
        //String authorizationKey = (String) item.get("authorizationKey");
        String categoryClassEnumId = (String) item.get("categoryClassEnumId");
        @SuppressWarnings("unchecked")
        List<Map<String, Object>> agreementTerms = (List<Map<String, Object>>) item.get("agreementTerms");

        Timestamp validFromDate = getTimestamp(item.get("validFromDate"));
        Timestamp validThruDate = getTimestamp(item.get("validThruDate"));
        Timestamp suspendFromDate = getTimestamp(item.get("suspendFromDate"));
        Timestamp suspendThruDate = getTimestamp(item.get("suspendThruDate"));
        if (UtilValidate.isNotEmpty(agreementTerms))
            for (Map<String, Object> agreementTerm : agreementTerms) {
                agreementTerm.remove("agreementTermId");
                agreementTerm.remove("agreementItemSeqId");
                agreementTerm.remove("agreementId");
            }

        return authorizationItemHelper.gnCreateAuthorizationItem(agreementId, authorizationKey, null, agreementText,
                simplified, holderRoleId, categoryClassEnumId,
                validFromDate, validThruDate, suspendFromDate, suspendThruDate, agreementTerms);
    }

    private Map<String, Object> updateItem(Map<String, Object> item, String agreementId, String authorizationKey) throws GenericServiceException, GenericEntityException {
        String agreementText = (String) item.get("agreementText");
        String simplified = (String) item.get("simplified");
        String holderRoleId = (String) item.get("holderRoleId");
        //String agreementId = (String) item.get("agreementId");
        //String authorizationKey = (String) item.get("authorizationKey");
        String agreementItemSeqId = (String) item.get("agreementItemSeqId");
        String oldAuthorizationItemKey = (String) item.get("authorizationItemKey");
        String authorizationItemKey = authorizationKey + "#" + oldAuthorizationItemKey.split("#")[5];

        String categoryClassEnumId = (String) item.get("categoryClassEnumId");
        @SuppressWarnings("unchecked")
        List<Map<String, Object>> agreementTerms = (List<Map<String, Object>>) item.get("agreementTerms");

        Timestamp validFromDate = getTimestamp(item.get("validFromDate"));
        Timestamp validThruDate = getTimestamp(item.get("validThruDate"));
        Timestamp suspendFromDate = getTimestamp(item.get("suspendFromDate"));
        Timestamp suspendThruDate = getTimestamp(item.get("suspendThruDate"));

        return authorizationItemHelper.gnUpdateAuthorizationItem(agreementId, agreementItemSeqId, authorizationKey,
                authorizationItemKey, agreementText, simplified, holderRoleId, categoryClassEnumId,
                validFromDate, validThruDate, suspendFromDate, suspendThruDate, agreementTerms);
    }

    private void deleteItem(Map<String, Object> item, String agreementId, String authorizationKey) throws GenericServiceException, GenericEntityException {
        String oldAuthorizationItemKey = (String) item.get("authorizationItemKey");
        String authorizationItemKey = authorizationKey + "#" + oldAuthorizationItemKey.split("#")[5];
        authorizationItemHelper.gnDeleteAuthorizationItem(authorizationItemKey);
    }

    private Timestamp getTimestamp(Object dateOrTimestamp) {
        if (dateOrTimestamp instanceof Date)
            return new Timestamp(((Date) dateOrTimestamp).getTime());
        else
            return (Timestamp) dateOrTimestamp;
    }

}
