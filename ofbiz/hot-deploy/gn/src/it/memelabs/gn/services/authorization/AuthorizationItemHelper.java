package it.memelabs.gn.services.authorization;

import it.memelabs.gn.services.OfbizErrors;
import it.memelabs.gn.services.login.LoginSourceOfbiz;
import it.memelabs.gn.services.security.GnSecurity;
import it.memelabs.gn.services.security.PermissionsOfbiz;
import it.memelabs.gn.util.GnServiceException;
import it.memelabs.gn.util.validator.BulkCreateAuthorizationItemValidator;
import it.memelabs.gn.util.validator.BulkUpdateAuthorizationItemValidator;
import it.memelabs.gn.util.validator.CreateUpdateAuthorizationItemValidator;
import it.memelabs.gn.util.validator.Errors;
import javolution.util.FastList;
import javolution.util.FastMap;
import org.ofbiz.base.util.*;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.entity.condition.EntityCondition;
import org.ofbiz.service.DispatchContext;
import org.ofbiz.service.GenericServiceException;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 12/03/13
 *
 * @author Andrea Fossi
 */
public class AuthorizationItemHelper extends CommonAuthorizationHelper {

    private TermHelper termHelper;

    public AuthorizationItemHelper(DispatchContext dctx, Map<String, ? extends Object> context) {
        super(dctx, context);
        termHelper = new TermHelper(dctx, context);
    }

    public final static String module = AuthorizationItemHelper.class.getName();

    /**
     * Create an GnAuthorizationItem entity
     *
     * @return
     * @throws GenericEntityException
     * @throws GenericServiceException
     */
    public Map<String, Object> gnCreateAuthorizationItem(String agreementId, String authorizationKey, String oldAuthorizationItemKey,
                                                         String agreementText, String simplified, String holderRoleId, String categoryClassEnumId,
                                                         Timestamp validFromDate, Timestamp validThruDate, Timestamp suspendFromDate, Timestamp suspendThruDate, List<Map<String, Object>> agreementTerms) throws GenericEntityException, GenericServiceException {
        GenericValue auth = canUpdateAuthorization(agreementId, authorizationKey, null, null);
        String authorizationTypeId = auth.getString("typeId");

        Map<String, Object> authorizationItem = UtilMisc.toMap("agreementText", agreementText, "simplified", simplified, "holderRoleId", holderRoleId,
                "categoryClassEnumId", categoryClassEnumId, "validFromDate", validFromDate, "validThruDate", validThruDate, "suspendFromDate", suspendFromDate,
                "suspendThruDate", suspendThruDate, "agreementTerms", agreementTerms);
        Errors errors = new Errors("authorizationItem");
        new BulkCreateAuthorizationItemValidator().validate(errors, authorizationItem);
        Debug.log(errors.toString(), module);
        boolean isError = errors.size() > 0;
        if (isError) {
            Debug.log(errors.toString(), module);
            throw new GnServiceException(OfbizErrors.INVALID_PARAMETERS, errors.toString());
        }

        Map<String, Object> result = createAuthorizationItem(agreementId, authorizationKey, oldAuthorizationItemKey, agreementText,
                simplified, holderRoleId, categoryClassEnumId, validFromDate, validThruDate, suspendFromDate, suspendThruDate, agreementTerms, authorizationTypeId);
        return result;
    }

    /**
     * Create an GnAuthorizationItem entity
     * without permission checking
     *
     * @param agreementId
     * @param authorizationKey
     * @param oldAuthorizationItemKey
     * @param agreementText
     * @param simplified
     * @param holderRoleId
     * @param categoryClassEnumId
     * @param validFromDate
     * @param validThruDate
     * @param suspendFromDate
     * @param suspendThruDate
     * @param agreementTerms
     * @return
     * @throws GenericEntityException
     * @throws GenericServiceException
     */
    protected Map<String, Object> createAuthorizationItem(String agreementId, String authorizationKey, String oldAuthorizationItemKey, String agreementText, String simplified, String holderRoleId, String categoryClassEnumId, Timestamp validFromDate, Timestamp validThruDate, Timestamp suspendFromDate, Timestamp suspendThruDate, List<Map<String, Object>> agreementTerms, String authorizationTypeId) throws GenericEntityException, GenericServiceException {
        Map<String, Object> result = FastMap.newInstance();

        List<GenericValue> toStore = FastList.newInstance();
        String authorizationItemKey = createAuthorizationItemKey(authorizationKey, oldAuthorizationItemKey);

        // agreementText -->description
        // agreementId
        // agreementItemSeqId
        // agreementItemTypeId
        Map<String, Object> agrItem = FastMap.newInstance();
        agrItem.put("agreementItemTypeId", "GN_AUTH_ITEM");
        agrItem.put("agreementId", agreementId);
        agrItem.put("agreementText", agreementText);
        agrItem.put("agreementItemTypeId", "GN_AUTH_ITEM");
        agrItem.put("userLogin", userLogin);
        Map<String, Object> agrItemResult = dispatcher.runSync("createAgreementItem", agrItem);

        String agreementItemSeqId = (String) agrItemResult.get("agreementItemSeqId");

        GenericValue authItem = delegator.makeValue("GnAuthorizationItem");
        authItem.set("agreementId", agreementId);
        authItem.set("agreementItemSeqId", agreementItemSeqId);
        authItem.set("simplified", simplified);

        authItem.set("lastModifiedDate", UtilDateTime.nowTimestamp());
        authItem.set("lastModifiedByUserLogin", getUserLogin().get("userLoginId"));
        authItem.set("holderRoleId", holderRoleId);

        if (AuthorizationTypesOfbiz.GN_AUTH_ALBO.toString().equals(authorizationTypeId)) {
            authItem.set("categoryClassEnumId", categoryClassEnumId);
        } else {
            authItem.set("categoryClassEnumId", null);
        }

        //authItem.set("uuid", uuid);
        authItem.set("authorizationItemKey", authorizationItemKey);

        authItem.set("validFromDate", validFromDate);
        authItem.set("validThruDate", validThruDate);
        authItem.set("suspendFromDate", suspendFromDate);
        authItem.set("suspendThruDate", suspendThruDate);

        toStore.add(authItem);
        delegator.storeAll(toStore);

        Debug.log("Saving authorizationItem with authorizationItemKey[" + authorizationItemKey + "], agreementId[" + agreementId + "] and agreementItemSeqId " + agreementItemSeqId + "]", module);
        termHelper.createAgreementTerms(agreementTerms, agreementId, agreementItemSeqId, authorizationItemKey);
        result.put("authorizationItemKey", authorizationItemKey);
        result.put("agreementItemSeqId", agreementItemSeqId);
        result.put("agreementId", agreementId);
        return result;
    }

    /**
     * Create new authorizationItemKey and check that not exist already.
     *
     * @param authorizationKey
     * @param oldAuthorizationItemKey
     * @return authorizationItemKey value
     */
    private String createAuthorizationItemKey(String authorizationKey, String oldAuthorizationItemKey) throws GenericEntityException {
        String uuid = UUID.randomUUID().toString();
        if (UtilValidate.isNotEmpty(oldAuthorizationItemKey)) {
            uuid = oldAuthorizationItemKey.substring(oldAuthorizationItemKey.lastIndexOf('#') + 1);
        }
        String itemKey = String.format("%s#%s", authorizationKey, uuid); //random se in bozza (o da slicing), pieno se in propagazione
        if (delegator.findByAnd("GnAuthorizationItem", UtilMisc.toMap("authorizationItemKey", itemKey)).size() > 0) {
            Debug.logWarning("An authorizationItem with key[" + itemKey + "] already exist.", module);
            return createAuthorizationItemKey(authorizationKey, null);
        } else
            return itemKey;
    }

    /**
     * Update an authorizationItem entity
     *
     * @param agreementId
     * @param agreementItemSeqId
     * @param authorizationKey
     * @param authorizationItemKey
     * @param agreementText
     * @param simplified
     * @param holderRoleId
     * @param categoryClassEnumId
     * @param validFromDate
     * @param validThruDate
     * @param suspendFromDate
     * @param suspendThruDate
     * @param agreementTerms
     * @return authorizationItemKey, agreementItemSeqId, agreementId
     * @throws GenericEntityException
     * @throws GenericServiceException
     */
    public Map<String, Object> gnUpdateAuthorizationItem(String agreementId, String agreementItemSeqId, String authorizationKey,
                                                         String authorizationItemKey, String agreementText, String simplified,
                                                         String holderRoleId, String categoryClassEnumId, Timestamp validFromDate,
                                                         Timestamp validThruDate, Timestamp suspendFromDate, Timestamp suspendThruDate,
                                                         List<Map<String, Object>> agreementTerms) throws GenericEntityException, GenericServiceException {
        canUpdateAuthorization(agreementId, authorizationKey, null, null);

        Map<String, Object> authorizationItem = UtilMisc.toMap("agreementText", agreementText, "simplified", simplified, "holderRoleId", holderRoleId,
                "categoryClassEnumId", categoryClassEnumId, "validFromDate", validFromDate, "validThruDate", validThruDate, "suspendFromDate", suspendFromDate,
                "suspendThruDate", suspendThruDate, "agreementTerms", agreementTerms);
        Errors errors = new Errors("authorization");
        new BulkUpdateAuthorizationItemValidator().validate(errors, authorizationItem);
        Debug.log(errors.toString(), module);
        boolean isError = errors.size() > 0;
        if (isError) {
            Debug.log(errors.toString(), module);
            throw new GnServiceException(OfbizErrors.INVALID_PARAMETERS, errors.toString());
        }

        List<Map<String, Object>> authItems = findAuthorizationItems(agreementId, agreementItemSeqId, authorizationKey, authorizationItemKey);
        if (authItems.size() == 0)
            throw new GnServiceException(OfbizErrors.AUTHORIZATION_ITEM_NOT_FOUND, String.format("AuthorizationItem not found"));
        if (authItems.size() > 1)
            throw new GnServiceException(OfbizErrors.ENTITY_EXCEPTION, String.format("More than one AuthorizationItem found with key[" + authorizationItemKey + "]"));
        agreementId = (String) authItems.get(0).get("agreementId");
        agreementItemSeqId = (String) authItems.get(0).get("agreementItemSeqId");
        authorizationItemKey = (String) authItems.get(0).get("authorizationItemKey");

        Map<String, Object> agrItem = FastMap.newInstance();
        agrItem.put("agreementItemTypeId", "GN_AUTH_ITEM");
        agrItem.put("agreementId", agreementId);
        agrItem.put("agreementItemSeqId", agreementItemSeqId);
        agrItem.put("agreementText", agreementText);
        agrItem.put("agreementItemTypeId", "GN_AUTH_ITEM");
        agrItem.put("userLogin", userLogin);
        Map<String, Object> agrItemResult = dispatcher.runSync("updateAgreementItem", agrItem);

        GenericValue authItem = delegator.findOne("GnAuthorizationItem", UtilMisc.toMap("agreementId", agreementId, "agreementItemSeqId", agreementItemSeqId), false);
        authItem.set("simplified", simplified);
        authItem.set("lastModifiedDate", UtilDateTime.nowTimestamp());
        authItem.set("lastModifiedByUserLogin", getUserLogin().get("userLoginId"));
        authItem.set("holderRoleId", holderRoleId);
        authItem.set("categoryClassEnumId", categoryClassEnumId);

        authItem.set("validFromDate", validFromDate);
        authItem.set("validThruDate", validThruDate);
        authItem.set("suspendFromDate", suspendFromDate);
        authItem.set("suspendThruDate", suspendThruDate);
        delegator.store(authItem);

        termHelper.updateAgreementTerms(agreementId, agreementItemSeqId, agreementTerms, authorizationItemKey);

        FastMap<String, Object> result = FastMap.newInstance();
        result.put("authorizationItemKey", authorizationItemKey);
        result.put("agreementItemSeqId", agreementItemSeqId);
        result.put("agreementId", agreementId);
        return result;
    }


    public List<Map<String, Object>> gnFindAuthorizationItems(String agreementId, String agreementItemSeqId, String authorizationKey, String authorizationItemKey) throws GenericEntityException, GenericServiceException {
        List<Map<String, Object>> authorizationItems = findAuthorizationItems(agreementId, agreementItemSeqId, authorizationKey, authorizationItemKey);
        for (Map<String, Object> item : authorizationItems) {
            //check if current user can read authorization
            //  new AuthorizationHelper(dctx, context).gnFindAuthorizationById((String) item.get("agreementId"), (String) item.get("authorizationKey"), false, false);
            GenericValue auth = findAuthorizationById((String) item.get("agreementId"), (String) item.get("authorizationKey"));

            if (UtilValidate.isEmpty(auth)) {
                throw new GnServiceException(OfbizErrors.AUTHORIZATION_NOT_FOUND, "Authorization[" + authorizationKey + "] not found.");
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
        }
        return authorizationItems;
    }

    /**
     * Find authorizationItem and terms by agreementId/agreementItemSeqId
     *
     * @return
     */
    public List<Map<String, Object>> findAuthorizationItems(String agreementId, String agreementItemSeqId, String authorizationKey, String authorizationItemKey) throws GenericEntityException, GenericServiceException {
        List<EntityCondition> conds = FastList.newInstance();
        if (UtilValidate.isNotEmpty(agreementId))
            conds.add(EntityCondition.makeCondition("agreementId", agreementId));
        if (UtilValidate.isNotEmpty(agreementItemSeqId))
            conds.add(EntityCondition.makeCondition("agreementItemSeqId", agreementItemSeqId));
        if (UtilValidate.isNotEmpty(authorizationKey))
            conds.add(EntityCondition.makeCondition("authorizationKey", authorizationKey));
        if (UtilValidate.isNotEmpty(authorizationItemKey))
            conds.add(EntityCondition.makeCondition("authorizationItemKey", authorizationItemKey));

        List<GenericValue> _authItems = delegator.findList("GnAuthorizationAndAgreementItem", EntityCondition.makeCondition(conds), null, UtilMisc.toList("agreementId", "agreementItemSeqId"), null, false);
        List<Map<String, Object>> authorizationItems = FastList.newInstance();
        for (GenericValue _authItem : _authItems) {
            Map<String, Object> authItem = UtilMisc.makeMapWritable(_authItem);
            authorizationItems.add(authItem);
            List<Map<String, Object>> terms = termHelper.findAuthorizationAndAgreementTerms(_authItem.getString("agreementId"), _authItem.getString("agreementItemSeqId"), false);
            authItem.put("agreementTerms", terms);
        }
        return authorizationItems;
    }


    /**
     * Resolve agreementItemSeqId and agreementId from authorizationItemKey.
     * Result must be unique otherwise method return an exception.
     *
     * @param authorizationItemKey
     * @return agreementItemSeqId and agreementId
     * @throws GenericEntityException
     */
    public Map<String, Object> getAgreementItemId(String authorizationItemKey) throws GenericEntityException, GnServiceException {
        List<GenericValue> authItems = delegator.findList("GnAuthorizationAndAgreementItem", EntityCondition.makeCondition("authorizationItemKey", authorizationItemKey), null, null, null, false);
        if (authItems.size() == 0)
            throw new GnServiceException(OfbizErrors.ENTITY_EXCEPTION, "AuthorizationItem not found");
        if (authItems.size() > 1)
            throw new GnServiceException(OfbizErrors.ENTITY_EXCEPTION, "Found more than one AuthorizationItem with the same key");
        GenericValue genericValue = authItems.get(0);
        return UtilMisc.toMap("agreementId", genericValue.get("agreementId"), "agreementItemSeqId", genericValue.get("agreementItemSeqId"));
    }

    /**
     * Delete AuthorizationItem and Terms
     *
     * @param item
     * @return
     * @throws GenericServiceException
     * @throws GenericEntityException
     */
    public Map<String, Object> deleteAgreementItem(Map<String, Object> item) throws GenericServiceException, GenericEntityException {
        String agreementId = (String) item.get("agreementId");
        String agreementItemSeqId = (String) item.get("agreementItemSeqId");
        List<Map<String, Object>> terms = UtilGenerics.<Map<String, Object>>checkList(item.get("agreementTerms"));
        TermHelper termHelper = this.termHelper;
        for (Map<String, Object> term : terms) {
            termHelper.deleteAgreementTerm(term);
        }
        return deleteAgreementItem(agreementId, agreementItemSeqId);
    }

    /**
     * Delete <code>GnAuthorizationItem</code> and <code>AgreementItem</code>
     *
     * @param agreementId
     * @param agreementItemSeqId
     * @return
     * @throws GenericServiceException
     * @throws GenericEntityException
     */
    private Map<String, Object> deleteAgreementItem(String agreementId, String agreementItemSeqId) throws GenericServiceException, GenericEntityException {
        GenericValue item = delegator.findOne("GnAuthorizationItem", false, "agreementId", agreementId, "agreementItemSeqId", agreementItemSeqId);
        delegator.removeValue(item);

        final String srvName = "removeAgreementItem";
        Map<String, Object> srvRequest = UtilMisc.toMap("userLogin", (Object) userLogin, "agreementId", agreementId, "agreementItemSeqId", agreementItemSeqId);
        srvRequest = dispatcher.getDispatchContext().makeValidContext(srvName, "IN", srvRequest);
        return dispatcher.runSync(srvName, srvRequest);
    }

    /**
     * Delete <code>GnAuthorizationItem</code> and <code>AgreementItem</code>
     * Method used by external service
     *
     * @param authorizationItemKey
     * @throws GenericEntityException
     * @throws GenericServiceException
     */
    public void gnDeleteAuthorizationItem(String authorizationItemKey) throws GenericEntityException, GenericServiceException {
        List<Map<String, Object>> items = findAuthorizationItems(null, null, null, authorizationItemKey);
        if (items.size() == 0)
            throw new GnServiceException(OfbizErrors.AUTHORIZATION_ITEM_NOT_FOUND, String.format("AgreementItem[%s] not found", authorizationItemKey));
        if (items.size() > 1)
            throw new GnServiceException(OfbizErrors.ENTITY_EXCEPTION, String.format("More than one AgreementItems found with key[%s]", authorizationItemKey));
        Map<String, Object> item = items.get(0);
        canUpdateAuthorization((String) item.get("agreementId"), (String) item.get("authorizationKey"), null, null);

        deleteAgreementItem(item);
    }
}
