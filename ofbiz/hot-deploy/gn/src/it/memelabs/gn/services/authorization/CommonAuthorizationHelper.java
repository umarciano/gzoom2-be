package it.memelabs.gn.services.authorization;

import it.memelabs.gn.services.AbstractServiceHelper;
import it.memelabs.gn.services.OfbizErrors;
import it.memelabs.gn.services.node.PartyNodeTypeOfbiz;
import it.memelabs.gn.services.node.PartyRoleOfbiz;
import it.memelabs.gn.services.node.RelationshipTypeOfbiz;
import it.memelabs.gn.services.security.GnSecurity;
import it.memelabs.gn.services.security.PermissionsOfbiz;
import it.memelabs.gn.util.EntityConditionUtil;
import it.memelabs.gn.util.GnServiceException;
import it.memelabs.gn.util.MapUtil;
import org.ofbiz.base.util.UtilGenerics;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.entity.condition.EntityCondition;
import org.ofbiz.service.DispatchContext;
import org.ofbiz.service.GenericServiceException;

import java.util.List;
import java.util.Map;

/**
 * 04/10/13
 *
 * @author Andrea Fossi
 */
public abstract class CommonAuthorizationHelper extends AbstractServiceHelper {

    public CommonAuthorizationHelper(DispatchContext dctx, Map<String, ? extends Object> context) {
        super(dctx, context);
    }


    public static final String GN_SHARE_AUTHORIZATION = "GN_SHARE_AUTHORIZATION";


    /**
     * Finds authorization by id and it doesn't  load any associated entity
     *
     * @param agreementId
     * @param authorizationKey
     * @return authorization Map or null is not exist
     * @throws GenericEntityException
     */
    public GenericValue findAuthorizationById(String agreementId, String authorizationKey) throws GenericEntityException, GenericServiceException {
        GenericValue auth = null;
        if (UtilValidate.isEmpty(agreementId) && UtilValidate.isEmpty(authorizationKey))
            throw new GnServiceException(OfbizErrors.INVALID_PARAMETERS, "agreementId and authorizationKey cannot be empty both");

        if (UtilValidate.isNotEmpty(agreementId))
            auth = delegator.findOne("GnAuthorizationAndAgreement", UtilMisc.toMap("agreementId", agreementId), false);
        else {
            List<GenericValue> l = delegator.findByAnd("GnAuthorizationAndAgreement", UtilMisc.toMap("authorizationKey", authorizationKey));
            if (l.size() > 1)
                throw new GnServiceException(OfbizErrors.ENTITY_EXCEPTION, "more than one authorization found");
            if (l.size() == 1)
                auth = l.get(0);
        }
        return auth;
    }


    /**
     * @param partyIdTo
     * @param publicationNode
     * @param isPrivate
     * @throws GenericServiceException
     */
    public void canCreateAuthorization(String partyIdTo, Map<String, Object> publicationNode, String isPrivate) throws GenericServiceException, GenericEntityException {
        if ("Y".equals(isPrivate)) {
            canCreatePrivateAuthorization(partyIdTo, publicationNode);
        } else {
            canCreatePublicAuthorization(partyIdTo, publicationNode);
        }
    }

    /**
     * @param partyIdTo
     * @param publicationNode
     * @throws GenericServiceException
     */
    private void canCreatePublicAuthorization(String partyIdTo, Map<String, Object> publicationNode) throws GenericServiceException {
        GnSecurity gnSecurity = new GnSecurity(delegator);
        //String partyIdTo = (String) partyNodeTo.get("partyId");
        if (!gnSecurity.hasPermission(PermissionsOfbiz.GN_AUTH_DRAFT.name(), userLogin))
            throw new GnServiceException(OfbizErrors.ACCESS_DENIED, "User hasn't permission to create public authorization.");

        if (!PartyNodeTypeOfbiz.isACompany(publicationNode.get("nodeType"))) {
            throw new GnServiceException(OfbizErrors.INVALID_PARAMETERS, "partyNode of gnContext[" + getCurrentContextId() + "] must be a " + PartyNodeTypeOfbiz.COMPANY.name());
        }
        //control that user can insert authorization for partyIdTo companyBase
        Map<String, Object> gnContext = getCurrentContext();

        List<Map<String, Object>> companyBases = UtilGenerics.<Map<String, Object>>checkList(gnContext.get("companyBases"));
        if (!MapUtil.listToMap(companyBases, "partyId", String.class).containsKey(partyIdTo))
            throw new GnServiceException(OfbizErrors.INVALID_PARAMETERS, "user cannot create a public authorization for this companyBase[" + partyIdTo + "]");
    }

    /**
     * @param partyIdTo
     * @param publicationNode
     * @throws GenericServiceException
     */
    private void canCreatePrivateAuthorization(String partyIdTo, Map<String, Object> publicationNode) throws GenericServiceException, GenericEntityException {
        // String partyIdTo = (String) partyNodeTo.get("partyId");

        GnSecurity gnSecurity = new GnSecurity(delegator);
        if (!gnSecurity.hasPermission(PermissionsOfbiz.GN_AUTH_PVT_DRAFT.name(), userLogin)) {
            throw new GnServiceException(OfbizErrors.ACCESS_DENIED, "User hasn't permission to create private authorization.");
        }

        String publicationNodePartyId = getPartyId((String) publicationNode.get("partyId"), (String) publicationNode.get("nodeKey"));
        GenericValue rel = findReceiveFromRelationship(publicationNodePartyId);
        if ("N".equals(rel.getString("validationRequired")))
            throw new GnServiceException(OfbizErrors.ACCESS_DENIED, "only node with validation flag active can create or update private authorization");

        Map<String, Object> gnContext = getCurrentContext();
        //control that user doesn't insert authorization for one of his companyBases

        List<Map<String, Object>> companyBases = UtilGenerics.<Map<String, Object>>checkList(gnContext.get("companyBases"));

        if (MapUtil.listToMap(companyBases, "partyId", String.class).containsKey(partyIdTo))
            throw new GnServiceException(OfbizErrors.INVALID_PARAMETERS, "user cannot create private authorization for his companyBase[" + partyIdTo + "]");

    }


    /**
     * @param agreementId
     * @param authorizationKey
     * @param partyIdFrom
     * @param partyIdTo
     * @return authorization if exist otherwise an exception
     * @throws GenericEntityException
     * @throws GenericServiceException
     */
    public GenericValue canUpdateAuthorization(String agreementId, String authorizationKey, String partyIdFrom, String partyIdTo) throws GenericEntityException, GenericServiceException {
        GenericValue auth = findAuthorizationById(agreementId, authorizationKey);
        if (UtilValidate.isEmpty(auth))
            throw new GnServiceException(OfbizErrors.AUTHORIZATION_NOT_FOUND, "Authorization not found.");

        if (UtilValidate.isEmpty(partyIdFrom)) {
            partyIdFrom = auth.getString("partyIdFrom");
        }
        if (UtilValidate.isEmpty(partyIdTo)) {
            partyIdTo = auth.getString("partyIdTo");
        }

        if (!AuthUtil.isPrivate(auth)) {
            canUpdatePublicAuthorization(auth, partyIdTo);
        } else {
            canUpdatePrivateAuthorization(auth, partyIdTo);
        }
        if (!AuthUtil.checkState(auth, AuthorizationStatusOfbiz.GN_AUTH_DRAFT) || !AuthUtil.checkInternalState(auth, null))
            throw new GnServiceException(OfbizErrors.INVALID_AUTHORIZATION_STATUS, String.format("Authorization can be edited if it has status[%s] only.", AuthorizationStatusOfbiz.GN_AUTH_DRAFT.name()));
        return auth;
    }

    /**
     * @param auth
     * @param partyIdTo
     * @return
     * @throws GenericEntityException
     * @throws GenericServiceException
     */
    private void canUpdatePublicAuthorization(GenericValue auth, String partyIdTo) throws GenericEntityException, GenericServiceException {
        GnSecurity gnSecurity = new GnSecurity(delegator);
        Map<String, Object> gnContext = getCurrentContext();

        Map<String, Object> company = UtilGenerics.checkMap(gnContext.get("partyNode"));

        String ownerNodeId = (String) company.get("partyId");
        //check if user has permission to update authorization
        //is a received copy
        if (AuthUtil.isOwner(auth, ownerNodeId) && !AuthUtil.isPublishedBy(auth, ownerNodeId) && AuthorizationStatusOfbiz.GN_AUTH_TO_BE_VALID.name().equals(auth.getString("statusId"))) {
            if (!gnSecurity.hasPermission(PermissionsOfbiz.GN_AUTH_VALIDATE.name(), userLogin))
                throw new GnServiceException(OfbizErrors.ACCESS_DENIED, "User hasn't permission to update authorization.");
        } else {//is a public copy
            if (AuthUtil.isOwner(auth, ownerNodeId) && AuthUtil.isPublishedBy(auth, ownerNodeId) && AuthorizationStatusOfbiz.GN_AUTH_DRAFT.name().equals(auth.getString("statusId"))) {
                if (!PartyNodeTypeOfbiz.isACompany(company.get("nodeType"))) {
                    throw new GnServiceException(OfbizErrors.INVALID_PARAMETERS, "partyNode of gnContext[" + getCurrentContextId() + "] must be a " + PartyNodeTypeOfbiz.COMPANY.name());
                }
                if (!gnSecurity.hasPermission(PermissionsOfbiz.GN_AUTH_DRAFT.name(), userLogin))
                    throw new GnServiceException(OfbizErrors.ACCESS_DENIED, "User hasn't permission to update authorization.");
            }
        }


        //control that user can update authorization for partyIdTo companyBase

        List<Map<String, Object>> companyBases = UtilGenerics.toList(gnContext.get("companyBases"));
        if (AuthorizationStatusOfbiz.GN_AUTH_DRAFT.name().equals(auth.getString("statusId")) &&
                !MapUtil.listToMap(companyBases, "partyId", String.class).containsKey(partyIdTo) &&
                gnPartyRoleCheck(partyIdTo, PartyRoleOfbiz.GN_COMPANY_BASE.name()) && !gnPartyRoleCheck(partyIdTo, PartyRoleOfbiz.GN_PVT_COMPANY_BASE.name()))
            throw new GnServiceException(OfbizErrors.ACCESS_DENIED, "user cannot update an authorization for this companyBase[" + partyIdTo + "]");
    }

    /**
     * @param auth
     * @param partyIdTo
     * @return
     * @throws GenericEntityException
     * @throws GenericServiceException
     */
    private void canUpdatePrivateAuthorization(GenericValue auth, String partyIdTo) throws GenericEntityException, GenericServiceException {
        GnSecurity gnSecurity = new GnSecurity(delegator);

        Map<String, Object> gnContext = getCurrentContext();


        Map<String, Object> company = UtilGenerics.checkMap(gnContext.get("partyNode"));

        GenericValue rel = findReceiveFromRelationship((String) company.get("partyId"));
        if ("N".equals(rel.getString("validationRequired")))
            throw new GnServiceException(OfbizErrors.ACCESS_DENIED, "only node with validation flag active can create or update private authorization");

        String ownerNodeId = (String) company.get("partyId");
        //check if user has permission to update authorization
        //is a received copy
        if (AuthUtil.isOwner(auth, ownerNodeId) && !AuthUtil.isPublishedBy(auth, ownerNodeId) && AuthorizationStatusOfbiz.GN_AUTH_TO_BE_VALID.name().equals(auth.getString("statusId"))) {
            if (!gnSecurity.hasPermission(PermissionsOfbiz.GN_AUTH_VALIDATE.name(), userLogin))
                throw new GnServiceException(OfbizErrors.ACCESS_DENIED, "User hasn't permission to update authorization.");
        } else {//is a public copy
            if (AuthUtil.isOwner(auth, ownerNodeId) && AuthUtil.isPublishedBy(auth, ownerNodeId) && AuthorizationStatusOfbiz.GN_AUTH_DRAFT.name().equals(auth.getString("statusId"))) {
                /*if (!PartyNodeTypeOfbiz.isACompany(company.get("nodeType"))) {
                    throw new GnServiceException(OfbizErrors.INVALID_PARAMETERS, "partyNode of gnContext[" + getCurrentContextId() + "] must be a " + PartyNodeTypeOfbiz.COMPANY.name());
                }*/
                if (!gnSecurity.hasPermission(PermissionsOfbiz.GN_AUTH_PVT_DRAFT.name(), userLogin))
                    throw new GnServiceException(OfbizErrors.ACCESS_DENIED, "User hasn't permission to update authorization.");
            }
        }

        //control that user can update authorization for partyIdTo companyBase

        List<Map<String, Object>> companyBases = UtilGenerics.toList(gnContext.get("companyBases"));
        if (AuthorizationStatusOfbiz.GN_AUTH_DRAFT.name().equals(auth.getString("statusId")) &&
                MapUtil.listToMap(companyBases, "partyId", String.class).containsKey(partyIdTo) &&
                (gnPartyRoleCheck(partyIdTo, PartyRoleOfbiz.GN_PVT_COMPANY_BASE.name()) || gnPartyRoleCheck(partyIdTo, PartyRoleOfbiz.GN_COMPANY_BASE.name()))
                )
            throw new GnServiceException(OfbizErrors.ACCESS_DENIED, "user cannot update an authorization for public companyBase[" + partyIdTo + "]");
    }


    /**
     * @param partyNodeId
     * @return
     * @throws org.ofbiz.entity.GenericEntityException
     * @throws it.memelabs.gn.util.GnServiceException
     */

    public GenericValue findReceiveFromRelationship(String partyNodeId) throws GenericEntityException, GnServiceException {
        EntityCondition condition = EntityConditionUtil.makeRelationshipCondition(partyNodeId, PartyRoleOfbiz.GN_PROPAGATION_NODE, null, PartyRoleOfbiz.GN_PROPAGATION_NODE, RelationshipTypeOfbiz.GN_RECEIVES_FROM, true);
        List<GenericValue> rels = delegator.findList("GnPartyRelationship", condition, null, null, null, false);
        if (rels.size() != 1)
            throw new GnServiceException("bad number[" + rels.size() + "] of incoming propagation input relationship");
        return rels.get(0);
    }


    /**
     * @param partyId
     * @return
     * @throws org.ofbiz.service.GenericServiceException
     */
    protected void gnCreateUpdatePartyAttribute(String partyId, String attrValue) throws GenericServiceException {
        dispatcher.runSync("gnCreateUpdatePartyAttribute", UtilMisc.toMap("userLogin", userLogin, "attrName", GN_SHARE_AUTHORIZATION, "partyId", partyId, "attrValue", attrValue));
    }

    /**
     * @param partyId
     * @return
     * @throws GenericServiceException
     */
    protected String gnFindPartyAttributeById(String partyId) throws GenericServiceException {
        Map<String, Object> ret = dispatcher.runSync("gnFindPartyAttributeById", UtilMisc.toMap("userLogin", userLogin, "attrName", GN_SHARE_AUTHORIZATION, "partyId", partyId));
        return (String) ret.get("attrValue");
    }

    /**
     * @param userLoginPartyId
     * @return
     * @throws GenericServiceException
     */
    protected Map<String, Object> gnFindCompanyWhereUserIsEmployed(String userLoginPartyId) throws GenericServiceException {
        Map<String, Object> ret = dispatcher.runSync("gnFindCompanyWhereUserIsEmployed", UtilMisc.toMap("userLogin", userLogin, "userLoginPartyId", userLoginPartyId));
        return UtilGenerics.checkMap(ret.get("partyNode"));
    }

    /**
     * Check that the  party's has role.
     *
     * @param partyId
     * @param roleTypeId
     * @return
     * @throws GenericServiceException
     */
    protected boolean gnPartyRoleCheck(String partyId, String roleTypeId) throws GenericServiceException {
        Map<String, Object> ret = dispatcher.runSync("gnPartyRoleCheck", UtilMisc.toMap("userLogin", userLogin, "partyId", partyId, "roleTypeId", roleTypeId));
        return (Boolean) ret.get("hasRole");
    }

    //-------------

    /**
     * if partyId is null return resolve it from nodeKey
     *
     * @param partyId
     * @param nodeKey
     * @return partyId
     * @throws GenericServiceException
     */
    public String getPartyId(String partyId, String nodeKey) throws GenericServiceException {
        if (UtilValidate.isEmpty(partyId) && UtilValidate.isNotEmpty(nodeKey))
            return (String) dctx.getDispatcher().runSync("gnGetPartyIdFromNodeKey", UtilMisc.toMap("userLogin", context.get("userLogin"), "nodeKey", nodeKey)).get("partyId");
        else return partyId;
    }

    /**
     * @param partyNode
     * @return
     * @throws GenericServiceException
     * @see AuthorizationPartyNodeHelper#getPartyId
     */
    public String getPartyId(Map<String, Object> partyNode) throws GenericServiceException {
        return getPartyId((String) partyNode.get("partyId"), (String) partyNode.get("nodeKey"));
    }

    /**
     * if partyId is null return resolve it from nodeKey
     *
     * @param partyId
     * @param nodeKey
     * @param code         The error code
     * @param errorMessage used in body exception generated when partyId is null;
     * @return partyId
     * @throws GenericServiceException if partyId is empty
     */
    public String getPartyId(String partyId, String nodeKey, OfbizErrors code, String errorMessage) throws GenericServiceException {
        partyId = getPartyId(partyId, nodeKey);
        if (UtilValidate.isEmpty(partyId)) throw new GnServiceException(code, errorMessage);
        else return partyId;
    }

    /**
     * if partyId is null return resolve it from nodeKey
     *
     * @param partyId
     * @param nodeKey
     * @param errorMessage used in body exception generated when partyId is null;
     * @return partyId
     * @throws GenericServiceException if partyId is empty
     */
    public String getPartyId(String partyId, String nodeKey, String errorMessage) throws GenericServiceException {
        return getPartyId(partyId, nodeKey, OfbizErrors.INVALID_PARAMETERS, errorMessage);
    }

    /**
     * if nodeKey is null return resolve it from partyId
     *
     * @param partyId
     * @param nodeKey
     * @return nodeKey
     * @throws GenericServiceException
     */
    public String getNodeKey(String partyId, String nodeKey) throws GenericServiceException {
        if (UtilValidate.isNotEmpty(partyId) && UtilValidate.isEmpty(nodeKey))
            return (String) dctx.getDispatcher().runSync("gnGetNodeKeyFromPartyId", UtilMisc.toMap("userLogin", context.get("userLogin"), "partyId", partyId)).get("nodeKey");
        else return nodeKey;
    }

    /**
     * if nodeKey is null return resolve it from partyId
     *
     * @param partyId
     * @param nodeKey
     * @param errorMessage used in body exception generated when nodeKey is null;
     * @return nodeKey
     * @throws GenericServiceException if nodeKey is empty
     */
    public String getNodeKey(String partyId, String nodeKey, String errorMessage) throws GenericServiceException {
        nodeKey = getNodeKey(partyId, nodeKey);
        if (UtilValidate.isEmpty(nodeKey)) throw new GnServiceException(errorMessage);
        else return nodeKey;
    }

    /**
     * Find loginSourceIds
     *
     * @param userLogin
     * @return
     * @throws GenericServiceException
     */
    public List<String> findLoginSourceIds(GenericValue userLogin) throws GenericServiceException {
        return findLoginSourceIds(userLogin.getString("userLoginId"));
    }

    /**
     * @param userLoginId
     * @return
     * @throws GenericServiceException
     */
    public List<String> findLoginSourceIds(String userLoginId) throws GenericServiceException {
        Map<String, Object> result = dispatcher.runSync("gnFindLoginSourceId", UtilMisc.toMap("userLogin", userLogin, "userLoginId", userLoginId));
        List<String> loginSourceIds = UtilMisc.getListFromMap(result, "loginSourceIds");
        return loginSourceIds;
    }
}
