package it.memelabs.gn.services.authorization;

import it.memelabs.gn.services.OfbizErrors;
import it.memelabs.gn.services.communication.EventTypeOfbiz;
import it.memelabs.gn.services.event.EventMessageContainer;
import it.memelabs.gn.services.event.EventMessageFactory;
import it.memelabs.gn.services.event.type.*;
import it.memelabs.gn.services.event.type.EventMessageAuthAudit.ActionTypeEnumOFBiz;
import it.memelabs.gn.services.node.NodeSearchHelper;
import it.memelabs.gn.services.node.PartyNodeTypeOfbiz;
import it.memelabs.gn.services.node.RelationshipTypeOfbiz;
import it.memelabs.gn.services.security.GnSecurity;
import it.memelabs.gn.services.security.PermissionsOfbiz;
import it.memelabs.gn.util.*;
import it.memelabs.gn.util.validator.CreateUpdateAuthorizationItemValidator;
import it.memelabs.gn.util.validator.Errors;
import it.memelabs.gn.util.validator.PublishAuthorizationValidator;
import javolution.util.FastList;
import javolution.util.FastMap;
import org.ofbiz.base.util.*;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.entity.condition.EntityCondition;
import org.ofbiz.service.DispatchContext;
import org.ofbiz.service.GenericServiceException;
import org.ofbiz.service.ServiceUtil;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * 28/03/13
 *
 * @author Andrea Fossi
 */
public class AuthorizationPublicationHelper extends CommonAuthorizationHelper {
    private final AuthorizationHelper authorizationHelper;
    private final AuthorizationItemHelper authorizationItemHelper;
    private final AuthorizationPartyNodeHelper authorizationPartyNodeHelper;
    private final AuthorizationAttachHelper authorizationAttachHelper;
    private AuthorizationJobHelper authorizationJobHelper;

    public AuthorizationPublicationHelper(DispatchContext dctx, Map<String, ? extends Object> context) {
        super(dctx, context);
        authorizationHelper = new AuthorizationHelper(dctx, context);
        authorizationItemHelper = new AuthorizationItemHelper(dctx, context);
        authorizationPartyNodeHelper = new AuthorizationPartyNodeHelper(dctx, context);
        authorizationAttachHelper = new AuthorizationAttachHelper(dctx, context);
        authorizationJobHelper = new AuthorizationJobHelper(dctx, context);
    }


    public final static String module = AuthorizationPublicationHelper.class.getName();

    /**
     * Publish a {@code GnAuthorization} by primaryKey
     * Check that authorization to publish belong to a companyBase in current user context
     *
     * @param agreementId      id of authorization to publish
     * @param authorizationKey key of authorization to publish
     * @param originId
     * @return
     * @throws GenericEntityException
     * @throws GenericServiceException
     */
    public Map<String, Object> gnPublishAuthorizationByIdEM(String agreementId, String authorizationKey, String originId) throws GenericEntityException, GenericServiceException {
        if (UtilValidate.isEmpty(agreementId) && UtilValidate.isEmpty(authorizationKey))
            throw new GnServiceException(OfbizErrors.INVALID_PARAMETERS, "service needs at least one of these parameters agreementId or authorizationKey");

        if (UtilValidate.isEmpty(agreementId)) agreementId = authorizationHelper.getAgreementId(authorizationKey);
        //check that user can publish this authorization
        Map<String, Object> gnContext = getCurrentContext();
        GnSecurity gnSecurity = new GnSecurity(delegator);

        GenericValue auth = delegator.findOne("GnAuthorizationAndAgreement", UtilMisc.toMap("agreementId", agreementId), false);

        if (UtilValidate.isEmpty(auth))
            throw new GnServiceException(OfbizErrors.AUTHORIZATION_NOT_FOUND, "Authorization not found");

        String partyIdTo = auth.getString("partyIdTo");

        if (UtilValidate.isNotEmpty(auth.get("originPartyIdTo")))
            partyIdTo = (String) auth.get("originPartyIdTo");
        Map<String, Object> company = UtilGenerics.checkMap(gnContext.get("partyNode"));

        String ownerNodeId = (String) company.get("partyId");
        // if is a private copy

        //check if user has permission to publish authorization
        if (AuthUtil.isPrivate(auth)) {
            //received copy
            if (AuthUtil.isOwner(auth, ownerNodeId) && !AuthUtil.isPublishedBy(auth, ownerNodeId)) {
                GenericValue rel = findReceiveFromRelationship(ownerNodeId);
                if ("N".equals(rel.getString("validationRequired")))
                    throw new GnServiceException(OfbizErrors.ACCESS_DENIED, "Only node with validation flag active can publish private authorization");

                if (!AuthUtil.checkState(auth, AuthorizationStatusOfbiz.GN_AUTH_TO_BE_VALID))
                    throw new GnServiceException(OfbizErrors.INVALID_AUTHORIZATION_STATUS, String.format("Authorization can be published if it has status[%s] only.", AuthorizationStatusOfbiz.GN_AUTH_TO_BE_VALID.name()));

                if (!gnSecurity.hasPermission(PermissionsOfbiz.GN_AUTH_VALIDATE, userLogin))
                    throw new GnServiceException(OfbizErrors.ACCESS_DENIED, "User hasn't got permission to validate authorization.");
            } else {//private copy
                if (AuthUtil.isPublishedBy(auth, ownerNodeId)) {
                    if (!AuthUtil.checkState(auth, AuthorizationStatusOfbiz.GN_AUTH_DRAFT))
                        throw new GnServiceException(OfbizErrors.INVALID_AUTHORIZATION_STATUS, String.format("Authorization can be edited if it has status[%s] only.", AuthorizationStatusOfbiz.GN_AUTH_DRAFT.name()));

                    GenericValue rel = findReceiveFromRelationship(ownerNodeId);
                    if ("N".equals(rel.getString("validationRequired")))
                        throw new GnServiceException(OfbizErrors.ACCESS_DENIED, "only node with validation flag active can publish private authorization");

                    List<Map<String, Object>> companyBases = UtilGenerics.toList(gnContext.get("companyBases"));
                    if (MapUtil.listToMap(companyBases, "partyId", String.class).containsKey(partyIdTo))
                        throw new GnServiceException(OfbizErrors.SERVICE_EXCEPTION, "user cannot publish authorization for this companyBase[" + partyIdTo + "]");
                    if (!gnSecurity.hasPermission(PermissionsOfbiz.GN_AUTH_PVT_PUBLISH, userLogin))
                        throw new GnServiceException(OfbizErrors.ACCESS_DENIED, "User hasn't permission to publish authorization.");
                } else
                    throw new GnServiceException(OfbizErrors.ACCESS_DENIED, "User hasn't permission to publish/validate authorization.");
            }

        } else {
            //received copy
            if (AuthUtil.isOwner(auth, ownerNodeId) && !AuthUtil.isPublishedBy(auth, ownerNodeId)) {
                GenericValue rel = findReceiveFromRelationship(ownerNodeId);
                if ("N".equals(rel.getString("validationRequired")))
                    throw new GnServiceException(OfbizErrors.ACCESS_DENIED, "only node with validation flag active can publish private authorization");

                if (!AuthUtil.checkState(auth, AuthorizationStatusOfbiz.GN_AUTH_TO_BE_VALID))
                    throw new GnServiceException(OfbizErrors.INVALID_AUTHORIZATION_STATUS, String.format("Authorization can be published if it has status[%s] only.", AuthorizationStatusOfbiz.GN_AUTH_TO_BE_VALID.name()));

                if (!gnSecurity.hasPermission(PermissionsOfbiz.GN_AUTH_VALIDATE, userLogin))
                    throw new GnServiceException(OfbizErrors.ACCESS_DENIED, "User hasn't got permission to validate authorization.");
            } else {//public copy
                if (AuthUtil.isPublishedBy(auth, ownerNodeId)) {
                    if (!AuthUtil.checkState(auth, AuthorizationStatusOfbiz.GN_AUTH_DRAFT))
                        throw new GnServiceException(OfbizErrors.INVALID_AUTHORIZATION_STATUS, String.format("Authorization can be published if it has status[%s] only.", AuthorizationStatusOfbiz.GN_AUTH_DRAFT.name()));
                    if (!PartyNodeTypeOfbiz.isACompany(company.get("nodeType"))) {
                        throw new GnServiceException(OfbizErrors.INVALID_PARAMETERS, "partyNode of gnContext[" + getCurrentContextId() + "] must be a " + PartyNodeTypeOfbiz.COMPANY.name());
                    }
                    List<Map<String, Object>> companyBases = UtilGenerics.toList(gnContext.get("companyBases"));
                    if (!MapUtil.listToMap(companyBases, "partyId", String.class).containsKey(partyIdTo))
                        throw new GnServiceException(OfbizErrors.SERVICE_EXCEPTION, "user cannot publish authorization for this companyBase[" + partyIdTo + "]");

                    if (!gnSecurity.hasPermission(PermissionsOfbiz.GN_AUTH_PUBLISH, userLogin))
                        throw new GnServiceException(OfbizErrors.ACCESS_DENIED, "User hasn't permission to publish authorization.");
                } else {
                    throw new GnServiceException(OfbizErrors.ACCESS_DENIED, "User hasn't permission to publish/validate authorization.");
                }
            }
        }
        Map<String, Object> result = publishAuthorizationByIdEM(agreementId, authorizationKey, originId, ActionTypeEnumOFBiz.PUBLISHED);

        refreshConflictingAuthorization((String) auth.get("ownerNodeId"), (String) auth.get("ownerNodeKey"));

        //if ownerNodeId is current context node, check if should be exported
        if (AuthUtil.isPublishedBy(auth, ownerNodeId) && !AuthUtil.isPrivate(auth))
            exportAuthorizationsEM();
        return result;
    }

    /**
     * Export authorization data is needed
     *
     * @return
     * @throws GenericServiceException
     * @throws GenericEntityException
     */
    public void exportAuthorizationsEM() throws GenericServiceException, GenericEntityException {
        Map<String, Object> sharerNode = authorizationHelper.gnFindCompanyWhereUserIsEmployed((String) userLogin.get("partyId"));
        if (UtilValidate.isNotEmpty(sharerNode)) {
            String shareEnable = authorizationHelper.gnFindPartyAttributeById((String) sharerNode.get("partyId"));
            if (UtilValidate.isEmpty(shareEnable)) shareEnable = "N";

            List<Map<String, Object>> auths;
            if ("Y".equals(shareEnable)) {
                Map<String, Object> result = authorizationHelper.gnAuthorizationManagePublicShare((String) sharerNode.get("partyId"));
                auths = UtilMisc.getListFromMap(result, "authorizationToShareList");
            } else auths = Collections.emptyList();
            EventMessagePublicSiteUpdate em = EventMessageFactory.make().makeEventMessagePublicSiteUpdate(shareEnable, sharerNode, auths);
            EventMessageContainer.add(em);
        }
    }

    /**
     * Publish a {@code GnAuthorization} by primaryKey
     *
     * @param agreementId      id of authorization to publish
     * @param authorizationKey key of authorization to publish
     * @param originId
     * @param auditActionType
     * @return partyNodes to deliver (partyNodes), authorization to propagate (authorization), old authorization copy to save on audit trail (archived)
     */
    public Map<String, Object> publishAuthorizationByIdEM(String agreementId, String authorizationKey, String originId, ActionTypeEnumOFBiz auditActionType) throws GenericEntityException, GenericServiceException {

        Map<String, Object> result = ServiceUtil.returnSuccess();
        if (UtilValidate.isEmpty(agreementId) && UtilValidate.isEmpty(authorizationKey))
            throw new GnServiceException(OfbizErrors.GENERIC, "service needs at least one of these parameters agreementId or authorizationKey");

        if (UtilValidate.isEmpty(agreementId)) agreementId = authorizationHelper.getAgreementId(authorizationKey);

        if (UtilValidate.isEmpty(authorizationAttachHelper.find(agreementId, null, AgreementContentTypeOfbiz.GN_AUTH_DOC))) {
            throw new GnServiceException(OfbizErrors.MISSING_AUTHORIZATION_DOCUMENT, "authorization without GN_AUTH_DOC attachment cannot be published.");
        }

        List<EventMessage> messages = new ArrayList<EventMessage>();
        //***************
        AuthorizationKey authKey = AuthorizationKey.fromString(authorizationKey);
        List<GenericValue> publishedAuth = authorizationHelper.findByKey(null, authKey.getUuid(), null, authKey.getOwnerNodeKey(), null, null, AuthorizationStatusOfbiz.GN_AUTH_PUBLISHED, null);
        Map<String, Object> previousAuth = null;
        if (publishedAuth.size() > 0) {
            String previousAgreementId = publishedAuth.get(0).getString("agreementId");
            previousAuth = authorizationHelper.findAuthorizationById(previousAgreementId, null, true, true);

            Map<String, Object> ownerNode = authorizationHelper.findPartyNodeById((String) previousAuth.get("ownerNodeId"));
            EventMessageAuthArchived em = EventMessageFactory.make().makeEventMessageAuthArchived(
                    (String) previousAuth.get("ownerNodeKey"),
                    (String) previousAuth.get("ownerNodeId"),
                    (String) ownerNode.get("nodeType"),
                    removeOfBizIds(previousAuth, false)
            );
            messages.add(em);

            Debug.log("An authorization with key[" + authorizationKey + "] was already published. Status GN_AUTH_TO_BE_DELET assigned to " + previousAgreementId + ".", module);
            updateAuthorizationInternalState(previousAgreementId, AuthorizationInternalStatusOfbiz.GN_AUTH_TO_BE_DELET, originId);
        }

        AuthorizationStatusOfbiz previousAuthStatusOfbiz = updateAuthorizationState(agreementId, AuthorizationStatusOfbiz.GN_AUTH_PUBLISHED, originId);

        if (auditActionType == ActionTypeEnumOFBiz.PUBLISHED) {
            if (previousAuthStatusOfbiz == AuthorizationStatusOfbiz.GN_AUTH_TO_BE_VALID) {
                auditActionType = ActionTypeEnumOFBiz.VALIDATED;
            } else if (previousAuthStatusOfbiz != AuthorizationStatusOfbiz.GN_AUTH_DRAFT) {
                throw new GnServiceException(OfbizErrors.ENTITY_EXCEPTION, "Service error: ActionTypeEnumOFBiz " + auditActionType.name() + " not suitable with previous status of authorization " + previousAuthStatusOfbiz.name());
            }
        }

        Map<String, Object> authorization = authorizationHelper.findAuthorizationById(agreementId, authorizationKey, true, true);

        Errors errors = new Errors("authorization");
        new PublishAuthorizationValidator().validate(errors, authorization);

        /* If certain conditions are verified the publication is blocked and a specific exception are generated */
        new CreateUpdateAuthorizationItemValidator(delegator).validate(authorization);


        Debug.log(errors.toString(), module);
        boolean isError = errors.size() > 0;
        if (isError) {
            Debug.log(errors.toString(), module);
            throw new GnServiceException(OfbizErrors.INVALID_PARAMETERS, errors.toString());
        }

        //propagation root node
        final String ownerNodeId = (String) authorization.get("ownerNodeId");
        final String ownerNodeKey = (String) authorization.get("ownerNodeKey");

        //if I'm the holder authorization and it's public will propagate ROOT node
        if (AuthUtil.isPublishedBy(authorization, ownerNodeId) && !AuthUtil.isPrivate(authorization)) {
            EntityCondition condition = EntityConditionUtil.makeRelationshipCondition(ownerNodeId, null, null, null, RelationshipTypeOfbiz.GN_PUBLISHES_TO_ROOT.name(), true);
            List<GenericValue> rels = delegator.findList("GnPartyRelationship", condition, null, null, null, false);
            if (rels.size() > 1)
                throw new GnServiceException(OfbizErrors.ENTITY_EXCEPTION, "More than one partyRelationship[" + RelationshipTypeOfbiz.GN_PUBLISHES_TO_ROOT.name() + "] found from [" + ownerNodeId + "] to ROOT");
            else if (rels.size() == 1) {
                Debug.log("found partyRelationship from[" + ownerNodeId + "] to GN_ROOT");
                String partyNodeId = rels.get(0).getString("partyIdTo");
                Map<String, Object> partyNode = authorizationHelper.findPartyNodeById(partyNodeId);
                EventMessageAuthPropagate em = EventMessageFactory.make().makeEventMessageAuthPropagate(
                        (String) partyNode.get("nodeKey"),
                        (String) partyNode.get("partyId"),
                        (String) partyNode.get("nodeType"),
                        (String) authorization.get("authorizationKey"));
                messages.add(em);
            }
        }

        //propagate to child node
        EntityCondition condition = EntityConditionUtil.makeRelationshipCondition(ownerNodeId, null, null, null, RelationshipTypeOfbiz.GN_PROPAGATES_TO.name(), true);
        List<GenericValue> rels = delegator.findList("GnPartyRelationship", condition, null, null, null, false);
        for (GenericValue rel : rels) {
            String partyNodeId = rel.getString("partyIdTo");
            Map<String, Object> partyNode = authorizationHelper.findPartyNodeById(partyNodeId);

            EventMessageAuthPropagate em = EventMessageFactory.make().makeEventMessageAuthPropagate(
                    (String) partyNode.get("nodeKey"),
                    (String) partyNode.get("partyId"),
                    (String) partyNode.get("nodeType"),
                    (String) authorization.get("authorizationKey"));
            messages.add(em);
        }

        for (String key : AuthorizationHelper.keyFields) {
            authorization.remove(key);
        }
        authorizationHelper.gnSendCommunicationEventToContactListEM((String) authorization.get("ownerNodeId"), EventTypeOfbiz.GN_COM_EV_AUTH_PUB.name(), authorization);

        Map<String, Object> ownerNode = authorizationHelper.findPartyNodeById(ownerNodeId);

        if (auditActionType != null) {
            EventMessageAuthAudit em = EventMessageFactory.make().makeEventMessageAuthAudit(
                    auditActionType, getCurrentContextId(),
                    userLogin.getString("userLoginId"),
                    ownerNodeKey,
                    ownerNodeId,
                    (String) ownerNode.get("nodeType"),
                    removeOfBizIds(authorization, true));
            if (previousAuth != null) {
                Debug.log(String.format("Sending previous published authorization[%s] to audit", previousAuth.get("agreementId")));
                em.setPreviousAuthorization(previousAuth);
            }
            messages.add(em);
        }
        EventMessageAuthIndexing em2 = EventMessageFactory.make().makeEventMessageAuthIndexing(
                ownerNodeKey,
                ownerNodeId,
                (String) ownerNode.get("nodeType"),
                removeOfBizIds(authorization, true));
        messages.add(em2);

        EventMessageContainer.addAll(messages);

        new AuthorizationJobHelper(dctx, context).scheduleAuthorizationExpirationRemindJobs(authorization);

        result.put("authorization", removeOfBizIds(authorization, true));
        return result;
    }

    /**
     * remove lastModifier, publisher, uploader and other user data
     *
     * @param authorization
     */
    private void removeUserModifierData(Map<String, Object> authorization) {
        authorization.remove("publishedByUserLogin");
        authorization.remove("lastModifiedByUserLogin");

        List<Map<String, Object>> authorizationItems = UtilMisc.getListFromMap(authorization, "authorizationItems");
        for (Map<String, Object> item : authorizationItems) {
            item.remove("lastModifiedByUserLogin");
            List<Map<String, Object>> agreementTerms = UtilMisc.getListFromMap(item, "agreementTerms");
            for (Map<String, Object> term : agreementTerms) {
                term.remove("lastModifiedByUserLogin");
            }
        }
        List<Map<String, Object>> authorizationDocuments = UtilMisc.getListFromMap(authorization, "authorizationDocuments");
        for (Map<String, Object> doc : authorizationDocuments) {
            doc.remove("uploadedByUserLogin");
        }
    }

    /**
     * remove lastModifier, publisher, uploader and other user data
     *
     * @param authorization
     */
    private Map<String, Object> removeSystemUserData(Map<String, Object> authorization) {
        authorization = UtilGenerics.checkMap(CloneUtil.deepClone(authorization));
        UserUtils.updateSystemUserData(authorization, "publishedByUserLogin");
        UserUtils.updateSystemUserData(authorization, "lastModifiedByUserLogin");

        List<Map<String, Object>> authorizationItems = UtilMisc.getListFromMap(authorization, "authorizationItems");
        for (Map<String, Object> item : authorizationItems) {
            UserUtils.updateSystemUserData(item, "lastModifiedByUserLogin");
            List<Map<String, Object>> agreementTerms = UtilMisc.getListFromMap(item, "agreementTerms");
            for (Map<String, Object> term : agreementTerms) {
                UserUtils.updateSystemUserData(term, "lastModifiedByUserLogin");
            }
        }
        List<Map<String, Object>> authorizationDocuments = UtilMisc.getListFromMap(authorization, "authorizationDocuments");
        for (Map<String, Object> doc : authorizationDocuments) {
            UserUtils.updateSystemUserData(doc, "uploadedByUserLogin");
        }
        return authorization;
    }

    /**
     * Change status of authorization from <code>TO_BE_VALIDATE</code> to <code>INVALID</code>
     *
     * @param agreementId
     * @param authorizationKey
     * @throws GenericEntityException
     * @throws GnServiceException
     */
    protected Map<String, Object> gnSetNotValidatedAuthorizationByIdEM(String agreementId, String authorizationKey) throws GenericEntityException, GenericServiceException {
        Map<String, Object> result = ServiceUtil.returnSuccess();
        List<EventMessage> messages = new ArrayList<EventMessage>();

        if (UtilValidate.isNotEmpty(authorizationKey))
            agreementId = authorizationHelper.getAgreementId(authorizationKey);
        if (UtilValidate.isEmpty(agreementId))
            throw new GnServiceException(OfbizErrors.SERVICE_EXCEPTION, "AgreementId is is empty");
        GenericValue auth = delegator.findOne("GnAuthorization", UtilMisc.toMap("agreementId", agreementId), false);
        if (UtilValidate.isEmpty(auth))
            throw new GnServiceException(OfbizErrors.ENTITY_EXCEPTION, "Authorization not found with agreementId[" + agreementId + "]");

        Map<String, Object> contextNode = UtilGenerics.checkMap(getCurrentContext().get("partyNode"));
        String ownerNodeId = (String) contextNode.get("partyId");

        if (!"GN_ROOT".equals(auth.getString("ownerNodeId"))
                && AuthUtil.isOwner(auth, ownerNodeId) && !AuthUtil.isPublishedBy(auth, ownerNodeId)
                && AuthorizationStatusOfbiz.GN_AUTH_TO_BE_VALID.toString().equals(auth.getString("statusId"))) {
            GnSecurity gnSecurity = new GnSecurity(delegator);
            boolean hasPermission = gnSecurity.hasPermission(PermissionsOfbiz.GN_AUTH_VALIDATE.toString(), userLogin);
            if (!hasPermission)
                throw new GnServiceException(OfbizErrors.ACCESS_DENIED, "User hasn't got permission to validate authorization");
        } else
            throw new GnServiceException(OfbizErrors.ACCESS_DENIED, "User hasn't permission to validate authorization.");


        if (!AuthorizationStatusOfbiz.GN_AUTH_TO_BE_VALID.toString().equals(auth.getString("statusId")))
            throw new GnServiceException(OfbizErrors.SERVICE_EXCEPTION, "Authorization doesn't have status" + AuthorizationStatusOfbiz.GN_AUTH_TO_BE_VALID.toString());
        Map<String, Object> authorization = authorizationHelper.findAuthorizationById(agreementId, null, true, true);
        updateAuthorizationState(agreementId, AuthorizationStatusOfbiz.GN_AUTH_NOT_VALID, (String) authorization.get("originId"));
        updateAuthorizationInternalState(agreementId, AuthorizationInternalStatusOfbiz.GN_AUTH_TO_BE_DELET, (String) authorization.get("originId"));

        authorizationHelper.gnSendCommunicationEventToContactListEM((String) authorization.get("ownerNodeId"), EventTypeOfbiz.GN_COM_EV_AUTH_REJ.toString(), authorization);

        Map<String, Object> ownerNode = authorizationHelper.findPartyNodeById((String) authorization.get("ownerNodeId"));

        EventMessageAuthArchived em = EventMessageFactory.make().makeEventMessageAuthArchived(
                (String) authorization.get("ownerNodeKey"),
                (String) authorization.get("ownerNodeId"),
                (String) ownerNode.get("nodeType"),
                removeOfBizIds(authorization, false)
        );
        messages.add(em);
        EventMessageAuthAudit audit = EventMessageFactory.make.makeEventMessageAuthAudit(
                ActionTypeEnumOFBiz.REJECTED,
                getCurrentContextId(),
                userLogin.getString("userLoginId"),
                (String) authorization.get("ownerNodeKey"),
                (String) authorization.get("ownerNodeId"),
                (String) ownerNode.get("nodeType"),
                removeOfBizIds(authorization, true)
        );
        messages.add(audit);
        EventMessageContainer.addAll(messages);

        return result;
    }

    /**
     * Change authorization status
     * Load authorization from db, change status and save
     *
     * @param agreementId
     * @param statusId
     * @param originId
     * @return previous authorization state
     * @throws GenericEntityException
     */
    protected AuthorizationStatusOfbiz updateAuthorizationState(String agreementId, AuthorizationStatusOfbiz statusId, String originId) throws GenericEntityException, GnServiceException {
        if (UtilValidate.isEmpty(agreementId))
            throw new GnServiceException(OfbizErrors.SERVICE_EXCEPTION, "AgreementId is is empty");
        GenericValue auth = delegator.findOne("GnAuthorization", UtilMisc.toMap("agreementId", agreementId), false);
        if (UtilValidate.isEmpty(auth))
            throw new GnServiceException(OfbizErrors.ENTITY_EXCEPTION, "Authorization not found with agreementId[" + agreementId + "]");
        String previousState = auth.getString("statusId");
        auth.set("statusId", (statusId != null) ? statusId.name() : null);
        auth.set("originId", originId);
        auth.set("publishedByUserLogin", userLogin.get("userLoginId"));
        auth.set("publishedDate", UtilDateTime.nowTimestamp());
        delegator.store(auth);
        return AuthorizationStatusOfbiz.valueOf(previousState);
    }

    /**
     * Change authorization status
     * Load authorization from db, change status and save
     *
     * @param agreementId
     * @param statusId
     * @param originId
     * @throws GenericEntityException
     */
    protected void updateAuthorizationInternalState(String agreementId, AuthorizationInternalStatusOfbiz statusId, String originId) throws GenericEntityException, GnServiceException, GenericServiceException {
        if (UtilValidate.isEmpty(agreementId))
            throw new GnServiceException(OfbizErrors.SERVICE_EXCEPTION, "AgreementId is is empty");
        GenericValue auth = delegator.findOne("GnAuthorization", UtilMisc.toMap("agreementId", agreementId), false);
        if (UtilValidate.isEmpty(auth))
            throw new GnServiceException(OfbizErrors.ENTITY_EXCEPTION, "Authorization not found with agreementId[" + agreementId + "]");
        auth.set("internalStatusId", (statusId != null) ? statusId.name() : null);
        auth.set("originId", originId);
        auth.set("publishedByUserLogin", userLogin.get("userLoginId"));
        auth.set("publishedDate", UtilDateTime.nowTimestamp());
        delegator.store(auth);
        if (AuthorizationInternalStatusOfbiz.GN_AUTH_TO_BE_DELET == statusId) {
            new AuthorizationJobHelper(dctx, context).disableJob(auth.getString("authorizationKey"));
        }
    }

    /**
     * Remove ids field (technic key) from authorization and items
     *
     * @param authIn
     * @return
     */
    public Map<String, Object> removeOfBizIds(Map<String, Object> authIn, boolean removeAddressId) {
        authIn = UtilGenerics.checkMap(CloneUtil.deepClone(authIn));
        authIn.remove("agreementId");
        authIn.remove("partyIdTo");
        //partyNodeTo
        Map<String, Object> partyNodeTo = UtilGenerics.checkMap(authIn.get("partyNodeTo"));
        partyNodeTo.remove("partyId");
        partyNodeTo.remove("partyRoleId");
        Map<String, Object> partyNodeToAddress = UtilGenerics.checkMap(partyNodeTo.get("address"));
        if (removeAddressId && UtilValidate.isNotEmpty(partyNodeToAddress)) partyNodeToAddress.remove("contactMechId");
        //otherPartyNodeTo
        Map<String, Object> otherPartyNodeTo = UtilGenerics.checkMap(authIn.get("otherPartyNodeTo"));
        otherPartyNodeTo.remove("partyId");
        otherPartyNodeTo.remove("partyRoleId");
        //clean GN_CNS_SUBJ_COMPANY terms
        for (Map<String, Object> item : UtilGenerics.<Map<String, Object>>checkList(authIn.get("authorizationItems"))) {
            item.remove("agreementItemSeqId");
            item.remove("agreementId");
            for (Map<String, Object> term : UtilGenerics.<Map<String, Object>>checkList(item.get("agreementTerms"))) {
                term.remove("agreementTermId");
                term.remove("agreementItemSeqId");
                term.remove("agreementId");
                if (AgreementTermTypeOfbiz.GN_CNS_SUBJ_COMPANY.is(term.get("termTypeId"))) {
                    for (Map<String, Object> termNode : UtilGenerics.<Map<String, Object>>checkList(term.get("partyNodes"))) {
                        termNode.remove("partyId");
                        termNode.remove("partyRoleId");
                        termNode.put("partyRelationships", FastList.newInstance());
                    }
                }
            }
        }
        for (Map<String, Object> attach : UtilGenerics.<Map<String, Object>>checkList(authIn.get("authorizationDocuments"))) {
            attach.remove("agreementId");
            attach.remove("contentId");
        }
        return authIn;
    }

    /**
     * Receive authorization from publish.
     * If authorization have <code>validate</code> flag false, method republish it and return new key and node list to propagate
     *
     * @param nodeKey          to publish
     * @param partyNodeId      to publish
     * @param authorizationKey source authorization
     * @return
     * @throws GenericEntityException
     * @throws GnServiceException
     */
    public Map<String, Object> gnPropagateAuthorizationToEM(String partyNodeId, String nodeKey, String authorizationKey, boolean syncroPropagate) throws GenericEntityException, GenericServiceException {
        Debug.log("Received authorization[" + authorizationKey + "] on node[" + nodeKey + "]", module);
        Map<String, Object> authorization = authorizationHelper.findAuthorizationById(null, authorizationKey, true, true);
        //authorization = removeOfBizIds(authorization, true);
        if (!AuthUtil.checkState(authorization, AuthorizationStatusOfbiz.GN_AUTH_PUBLISHED) && !AuthUtil.checkInternalState(authorization, null))
            throw new GnServiceException(OfbizErrors.INVALID_AUTHORIZATION_STATUS, "can propagate only published authorization");

        // authorization and authorizationKey: related to parent by now
        // nodeKey, partyNodeId: related to the node candidated to receive a version of the authorization

        //if I'm the publisher, received authorization is ignored
        if (isPublishedByMe(nodeKey, authorizationKey)) return FastMap.newInstance();

        if (!syncroPropagate) { //it's the check in gnCreateAuthorizationFromReceiveEM()
            //if previously received on the same node, received authorization is ignored
            if (isPreviouslyReceived(nodeKey, authorizationKey)) return FastMap.newInstance();
        } //else update the authorizations (they could be modified for the slicing filters)

        //remove lastModifier,uploader and other user data
        removeUserModifierData(authorization);
        //propagate to ROOT
        if ("GN_ROOT".equals(partyNodeId)) {
            Map<String, Object> result = receiveAuthorizationOnRootEM(partyNodeId, nodeKey, authorization);
            return result;
        } else { // generic tree propagation
            Map<String, Object> result = receiveAuthorizationEM(partyNodeId, nodeKey, authorization);
            return result;
        }
    }

    /**
     * Receive authorization from publish.
     * If authorization have <code>validate</code> flag false, method republish it and return new key and node list to propagate
     *
     * @param nodeKey          to publish
     * @param partyNodeId      to publish
     * @param authorizationKey source authorization
     * @return
     * @throws GenericEntityException
     * @throws GnServiceException
     */
    public Map<String, Object> gnReceivedAuthorizationAckEM(String partyNodeId, String nodeKey, String authorizationKey) throws GenericEntityException, GenericServiceException {
        Debug.log("Received authorization[" + authorizationKey + "] on node[" + nodeKey + "]", module);
        Map<String, Object> authorization = authorizationHelper.findAuthorizationById(null, authorizationKey, false, false);
        String agreementId = (String) authorization.get("agreementId");
        String originId = (String) authorization.get("originId");

        //if I'm the publisher received authorization is dropped
        if (isPublishedByMe(nodeKey, authorizationKey)) return FastMap.newInstance();
        //todo check internal state????
        //ignore authorization received if previously received on the same node
        if (isPreviouslyReceived(nodeKey, authorizationKey)) return FastMap.newInstance();
        //remove lastModifier,uploader and other user data

        if (!AuthUtil.checkInternalState(authorization, AuthorizationInternalStatusOfbiz.GN_AUTH_TO_BE_RECEI))
            throw new GnServiceException(OfbizErrors.INVALID_AUTHORIZATION_STATUS, "authorization internalStatus is not: " + AuthorizationInternalStatusOfbiz.GN_AUTH_TO_BE_RECEI.name());

        //propagate to ROOT
        if ("GN_ROOT".equals(partyNodeId)) {
            Map<String, Object> result = receiveAuthorizationOnRootAckEM(partyNodeId, nodeKey, agreementId, authorizationKey, originId);
            return result;
        } else { // generic tree propagation
            Map<String, Object> result = receiveAuthorizationAckEM(partyNodeId, nodeKey, agreementId, authorizationKey, originId);
            return result;
        }
    }

    /**
     * Check if received authorization id published by me.
     *
     * @param nodeKey
     * @param authorizationKey
     * @return
     * @throws GenericEntityException
     */
    private boolean isPublishedByMe(String nodeKey, String authorizationKey) throws GenericEntityException {
        AuthorizationKey authKey = AuthorizationKey.fromString(authorizationKey);
        List<GenericValue> publishedByMe = authorizationHelper.findByKey(null, authKey.getUuid(), null, nodeKey, null, "0", AuthorizationStatusOfbiz.GN_AUTH_PUBLISHED, null);
        assert publishedByMe.size() <= 1;
        if (publishedByMe.size() == 1) {
            Debug.log("Authorization[" + publishedByMe.get(0).getString("agreementId") + "," + authKey.getUuid() + "] is published by me.", module);
            return true;
        } else
            return false;
    }

    private boolean isPreviouslyReceived(String nodeKey, String authorizationKeyS) throws GenericEntityException {
        AuthorizationKey authKey = AuthorizationKey.fromString(authorizationKeyS);
        Debug.log("Received authorization[" + authKey.toString() + "]", module);
        String parentVersion = authKey.getParentVersion() + "." + authKey.getVersion().toString();
        List<GenericValue> receivedList = authorizationHelper.findByKey(null, authKey.getUuid(), null, nodeKey, null, parentVersion, AuthorizationStatusOfbiz.GN_AUTH_PUBLISHED, null);
        assert receivedList.size() <= 1;
        if (receivedList.size() == 1) {
            Debug.log("Authorization[" + receivedList.get(0).getString("agreementId") + "," + authKey.getUuid() + "] is previously received on this node.", module);
            return true;
        } else
            return false;
    }

    /**
     * Receive authorization on non root node.
     *
     * @param nodeKey
     * @param partyNodeId
     * @param authorization
     * @return
     * @throws GenericEntityException
     * @throws GenericServiceException
     */
    private Map<String, Object> receiveAuthorizationEM(String partyNodeId, String nodeKey, Map<String, Object> authorization)
            throws GenericEntityException, GenericServiceException {

        // authorization and authorizationKey: related to parent by now
        // nodeKey, partyNodeId: related to the node candidated to receive a version of the authorization

        Map<String, Object> result = FastMap.newInstance();
        GenericValue relationship = authorizationHelper.findReceiveFromRelationship(partyNodeId);
        Map<String, Object> ownerNode = authorizationHelper.findPartyNodeById(partyNodeId);
        if (canBeReceived(relationship, authorization, nodeKey)) { // check filters
            Map<String, Object> slicedAuth = applySlicingFilter(relationship, authorization, nodeKey); //apply slicing filter
            List<Map<String, Object>> items = UtilGenerics.checkList(slicedAuth.get("authorizationItems"));
            if (items.size() > 0) {
                boolean sliceDone = "Y".equals(slicedAuth.get("sliceDone"));
                slicedAuth.remove("sliceDone");
                Map<String, Object> authIds = gnCreateAuthorizationFromReceiveEM(slicedAuth, partyNodeId, sliceDone);
                //Authorization is recived
                if (UtilValidate.isNotEmpty(authIds)) {
                    String agreementId = (String) authIds.get("agreementId");
                    String authorizationKey = (String) authIds.get("authorizationKey");
                    result.put("authorizationKey", authorizationKey);
                    result.put("agreementId", agreementId);
                    EventMessageAuthReceived em = EventMessageFactory.make().makeEventMessageAuthReceived(
                            nodeKey,
                            partyNodeId,
                            (String) ownerNode.get("nodeType"),
                            authorizationKey,
                            (String) authorization.get("authorizationKey"));
                    EventMessageContainer.add(em);
                } else {
                    Debug.log(String.format("Authorization[%s] is already received on node[%s-%s]", authorization.get("authorizationKey"), partyNodeId, nodeKey));
                }
            }
        }
        return result;
    }

    /**
     * @param partyNodeId
     * @param nodeKey
     * @param authorizationKey
     * @param agreementId
     * @return
     * @throws GenericEntityException
     * @throws GenericServiceException
     */
    private Map<String, Object> receiveAuthorizationAckEM(String partyNodeId, String nodeKey, String agreementId, String authorizationKey, String originId) throws GenericEntityException, GenericServiceException {
        //clear internalStatusId
        Map<String, Object> result = FastMap.newInstance();
        GenericValue relationship = authorizationHelper.findReceiveFromRelationship(partyNodeId);
        Map<String, Object> ownerNode = authorizationHelper.findPartyNodeById(partyNodeId);

        List<EventMessage> messages = new ArrayList<EventMessage>();

        boolean validationRequired = "Y".equals(relationship.getString("validationRequired"));
        Debug.log("Validation is required[" + relationship.getString("validationRequired") + "]", module);

        List<GenericValue> authToCheck = new ArrayList<GenericValue>();
        AuthorizationKey authKey = AuthorizationKey.fromString(authorizationKey);
        authToCheck.addAll(authorizationHelper.findByKey(null, authKey.getUuid(), null, nodeKey, null, null, AuthorizationStatusOfbiz.GN_AUTH_TO_BE_VALID, null));
        authToCheck.addAll(authorizationHelper.findByKey(null, authKey.getUuid(), null, nodeKey, null, null, AuthorizationStatusOfbiz.GN_AUTH_CONFLICTING, null));
        if (authToCheck.size() > 1) {
            StringBuilder error = new StringBuilder("Found ").append(authToCheck.size()).append("authorization on node[")
                    .append(nodeKey).append("] with uuid[").append(authKey.getUuid()).append("] in status ")
                    .append(AuthorizationStatusOfbiz.GN_AUTH_TO_BE_VALID).append(" or ").append(AuthorizationStatusOfbiz.GN_AUTH_CONFLICTING);
            throw new GnServiceException(OfbizErrors.ENTITY_EXCEPTION, error.toString());
        }
        if (authToCheck.size() == 1) {
            Debug.logWarning("Authorization[" + authKey.toString() + "] is found.", module);
            String previousAgreementId = authToCheck.get(0).getString("agreementId");

            Map<String, Object> previousAuthorization = authorizationHelper.findAuthorizationById(previousAgreementId, null, true, true);

            EventMessageAuthArchived em = EventMessageFactory.make().makeEventMessageAuthArchived(
                    (String) previousAuthorization.get("ownerNodeKey"),
                    (String) previousAuthorization.get("ownerNodeId"),
                    (String) ownerNode.get("nodeType"),
                    removeSystemUserData(removeOfBizIds(previousAuthorization, false)));
            messages.add(em);
            Debug.log("Remove authorization[" + authToCheck.get(0).getString("authorizationKey") + "],[" + previousAgreementId + "]", module);
            updateAuthorizationInternalState(previousAgreementId, AuthorizationInternalStatusOfbiz.GN_AUTH_TO_BE_DELET, originId);
        }
        updateAuthorizationInternalState(agreementId, null, originId);

        //used only for conflict searching
        Map<String, Object> authorization = authorizationHelper.findAuthorizationById(agreementId, authorizationKey, true, true);

        messages.add(EventMessageFactory.make().makeEventMessageAuthAudit(
                EventMessageAuthAudit.ActionTypeEnumOFBiz.RECEIVED,
                getCurrentContextId(),
                userLogin.getString("userLoginId"),
                nodeKey,
                partyNodeId,
                (String) ownerNode.get("nodeType"),
                removeOfBizIds(authorization, true)));

        List<String> conflictingAuthKeys = findConflicting(partyNodeId, authorization);

        boolean conflicting = conflictingAuthKeys.size() > 0;

        if (conflicting) {
            Debug.log(String.format("Received authorization[%s] conflict with [%s]", authorizationKey, conflictingAuthKeys.get(0)), module);
            updateAuthorizationState(agreementId, AuthorizationStatusOfbiz.GN_AUTH_CONFLICTING, originId);
        } else {
            updateAuthorizationState(agreementId, AuthorizationStatusOfbiz.GN_AUTH_TO_BE_VALID, originId);
        }
        if (!validationRequired) {
            Map<String, Object> publishingResult = publishAuthorizationByIdEM(agreementId, authorizationKey, originId, null);
            //published authorization
            result.put("authorization", publishingResult.get("authorization"));

            if (conflicting) {
                Debug.log(String.format("Remove old published conflicting authorization[%s]", conflictingAuthKeys.get(0)), module);
                Map<String, Object> toArchive = authorizationHelper.findAuthorizationById(null, conflictingAuthKeys.get(0), true, true);
                EventMessageAuthArchived em = EventMessageFactory.make().makeEventMessageAuthArchived(
                        (String) toArchive.get("ownerNodeKey"),
                        (String) toArchive.get("ownerNodeId"),
                        (String) ownerNode.get("nodeType"),
                        removeSystemUserData(removeOfBizIds(toArchive, false)));
                messages.add(em);
                updateAuthorizationInternalState((String) toArchive.get("agreementId"), AuthorizationInternalStatusOfbiz.GN_AUTH_TO_BE_DELET, originId);
            }
        } else {
            Map<String, Object> savedAuthorization = authorizationHelper.findAuthorizationById(agreementId, authorizationKey, true, false);
            authorizationHelper.gnSendCommunicationEventToContactListEM((String) savedAuthorization.get("ownerNodeId"), EventTypeOfbiz.GN_COM_EV_AUTH_TO_VA.name(), savedAuthorization);
            authorizationJobHelper.scheduleValidateAuthorizationRemindJobs((String) savedAuthorization.get("ownerNodeId"));
        }

        EventMessageContainer.addAll(messages);
        return result;
    }

    /**
     * Receive authorization on root node
     *
     * @param partyNodeId
     * @param authorization
     * @return
     * @throws GenericEntityException
     * @throws GenericServiceException
     */
    private Map<String, Object> receiveAuthorizationOnRootEM(String partyNodeId, String nodeKey, Map<String, Object> authorization) throws GenericEntityException, GenericServiceException {
        Map<String, Object> result = FastMap.newInstance();
        if ("Y".equals(authorization.get("isPrivate")))
            throw new GnServiceException(OfbizErrors.INVALID_PARAMETERS, "ROOT node doesn't accept private authorization");
        Map<String, Object> authIds = gnCreateAuthorizationFromReceiveEM(authorization,
                partyNodeId, false);
        if (UtilValidate.isNotEmpty(authIds)) {
            String agreementId = (String) authIds.get("agreementId");
            String authorizationKey = (String) authIds.get("authorizationKey");
            result.put("authorizationKey", authorizationKey);
            result.put("agreementId", agreementId);
            EventMessageContainer.add(EventMessageFactory.make().makeEventMessageAuthReceived(
                    nodeKey,
                    partyNodeId,
                    PartyNodeTypeOfbiz.ROOT.name(),
                    authorizationKey,
                    (String) authorization.get("authorizationKey")));
        } else {
            Debug.log(String.format("Authorization[%s] is already received on node[%s-%s]", authorization.get("authorizationKey"), partyNodeId, nodeKey));
        }
        return result;
    }

    /**
     * @param partyNodeId
     * @param agreementId
     * @param authorizationKey
     * @param originId
     * @return
     * @throws GenericEntityException
     * @throws GenericServiceException
     */
    private Map<String, Object> receiveAuthorizationOnRootAckEM(String partyNodeId, String nodeKey, String agreementId, String authorizationKey, String originId) throws GenericEntityException, GenericServiceException {
        Map<String, Object> result = FastMap.newInstance();
        updateAuthorizationInternalState(agreementId, null, originId);
        result.putAll(publishAuthorizationByIdEM(agreementId, authorizationKey, originId, null));
        return result;
    }

    /**
     * Creates a new {@code GnAuthorization}, Items and Terms
     * This is invoked after receive (end eventually after slicing).
     *
     * @param authIn          related to parent authorization, eventually sliced
     * @param ownerNodeId     related to the node candidated to receive a version of the authorization
     * @param modifiedVersion true if slices have been done
     * @return
     * @throws GenericEntityException
     * @throws GenericServiceException
     */
    public Map<String, Object> gnCreateAuthorizationFromReceiveEM(Map<String, Object> authIn, String ownerNodeId, boolean modifiedVersion)
            throws GenericEntityException, GenericServiceException {

        Debug.log("Received authorization[" + authIn.get("authorizationKey") + "]", module);
        Map<String, Object> result = FastMap.newInstance();

        //Create a new authorization key for the authIn to be received from the parent key (actually from authIn)
        AuthorizationKey authKey = AuthorizationKey.fromString((String) authIn.get("authorizationKey"));
        String ownerNodeKey = getNodeKey(ownerNodeId);
        authKey.setParentVersion(authKey.getParentVersion() + "." + authKey.getVersion().toString());
        authKey.setOwnerNodeKey(ownerNodeKey);
        authKey.setVersion(0l); //later, eventually updated

        //cerco la lista delle autorizzazioni presenti sul nodo con la stesso uuid e la stessa parent version
        List<GenericValue> toAuthList = authorizationHelper.findByKey(null, authKey.getUuid(), null, authKey.getOwnerNodeKey(), null, authKey.getParentVersion(), null, null);
        boolean receive = true;
        if (UtilValidate.isEmpty(toAuthList) || UtilValidate.isEmpty(toAuthList.get(0))) {
            //se sul nodo non è presente un'aut. con stessa chiave - eccetto la version, che si trascura - --> authIn è da ricevere e propagare
            if (modifiedVersion)
                authKey.setVersion(1l);
        } else {
            // è da ricevere e propagare se:
            // - se si tratta di una versione tagliata - in tal caso si incrementa la version -
            // - se non si tratta di una versione tagliata, ma la version di quella che ho trovato sul nodo è >0 (quindi era stata tagliata in precedenza)
            // - altrimenti è una copia di una che ho già e va ignorata
            // NOTA: questo vuol dire che potrei avere due versioni successive tagliate ma effettivamente uguali - questo caso per il momento si trascura
            // (infatti potrebbe succedere solo in alcuni casi eseguendo la sincronizzazione
            // e per risolvere questo caso non ci si può basare soltanto sugli id, perchè potrebbe variare il contenuto dei vincoli)
            // Per evitare errori dovuti alla sequenza imprevedibile di messaggi ricevuti in modo asincrono, dalla receive si esclude solo il caso
            // in cui l'ultima versione è 0 e modifiedVersion è false.
            // In teoria in toAuthList dovrebbe esserci una sola autorizzazione, ma proprio per impreviste sequenze di messaggi asincroni,
            // si prende la max version tra tutte le possibili aut. trovate.
            Long oldVersion = 0l;
            AuthorizationKey oldAuthKey;
            for (GenericValue oldAuth : toAuthList) {
                oldAuthKey = AuthorizationKey.fromString((String) oldAuth.get("authorizationKey"));
                if (oldAuthKey.getVersion() > oldVersion)
                    oldVersion = oldAuthKey.getVersion();
            }
            if (!modifiedVersion && oldVersion.equals(0l)) {
                receive = false;
            }
            if (receive)
                authKey.setVersion(oldVersion + 1l);
        }

        if (receive) {
            long timeToSaveAuthorization = System.currentTimeMillis();

            Debug.log("Coping authorization with key[" + authKey.toString() + "]", module);

            Map<String, Object> pvtCompanyBase = authorizationHelper.findPartyNodeById((String) authIn.get("partyIdTo"));
            pvtCompanyBase.put("nodeKey", null);
            pvtCompanyBase.put("partyId", null);
            UtilGenerics.checkMap(pvtCompanyBase.get("address")).put("contactMechId", null);

            Map<String, Object> pvtCompany = authorizationHelper.findPartyNodeById((String) authIn.get("otherPartyIdTo"));
            pvtCompany.put("partyId", null);
            pvtCompany.put("nodeKey", null);

            Map<String, String> partyIdsTo = authorizationPartyNodeHelper.createUpdateReceivedCompanyAndCompanyBase(ownerNodeKey, pvtCompanyBase, pvtCompany);
            result = authorizationHelper.createAuthorization(authKey, ownerNodeId,
                    partyIdsTo.get("companyBasePartyId"), partyIdsTo.get("companyPartyId"), (String) authIn.get("partyIdFrom"),
                    (String) authIn.get("typeId"), (String) authIn.get("number"),
                    AuthorizationStatusOfbiz.GN_AUTH_TO_BE_VALID.name(), AuthorizationInternalStatusOfbiz.GN_AUTH_TO_BE_RECEI.name(),
                    (String) authIn.get("description"), (String) authIn.get("textData"),
                    (String) authIn.get("originId"), (String) authIn.get("originPartyIdTo"),
                    (String) authIn.get("isPrivate"), UtilDateTime.nowTimestamp());
            String authorizationKey = (String) result.get("authorizationKey");
            String agreementId = (String) result.get("agreementId");

            List<Map<String, Object>> items = UtilGenerics.toList(authIn.get("authorizationItems"));
            for (Map<String, Object> item : items) {
                List<Map<String, Object>> terms = UtilGenerics.toList(item.get("agreementTerms"));
                String oldAuthorizationItemKey = (String) item.get("authorizationItemKey");
                authorizationItemHelper.createAuthorizationItem(agreementId, authorizationKey, oldAuthorizationItemKey, (String) item.get("agreementText"),
                        (String) item.get("simplified"), (String) item.get("holderRoleId"), (String) item.get("categoryClassEnumId"),
                        (Timestamp) item.get("validFromDate"), (Timestamp) item.get("validThruDate"),
                        (Timestamp) item.get("suspendFromDate"), (Timestamp) item.get("suspendThruDate"), clearTerms(terms), (String) authIn.get("typeId"));
            }
            List<Map<String, Object>> authorizationDocuments = UtilGenerics.toList(authIn.get("authorizationDocuments"));
            for (Map<String, Object> authDoc : authorizationDocuments) {
                String contentName = (String) authDoc.get("contentName");
                String description = (String) authDoc.get("description");
                String mimeTypeId = (String) authDoc.get("mimeTypeId");
                String agreementContentTypeId = (String) authDoc.get("agreementContentTypeId");
                Long contentSize = (Long) authDoc.get("contentSize");
                Timestamp createdDate = (Timestamp) authDoc.get("createdDate");

                authorizationAttachHelper.upload(agreementId, contentName, description, mimeTypeId, contentSize, createdDate, agreementContentTypeId, false);
            }

            result.put("authorizationKey", authorizationKey);
            result.put("agreementId", agreementId);
            timeToSaveAuthorization = System.currentTimeMillis() - timeToSaveAuthorization;
            Debug.log(String.format("copyAuthorization: Authorization saved in[%s]ms", timeToSaveAuthorization), module);
        } else {
            Debug.log("Authorization with key[" + authKey.toString() + "] is already published.", module);
        }

        return result;
    }

    /**
     * Get nodeKey from partyId
     *
     * @param partyId
     * @return nodKey
     * @throws GenericServiceException
     */
    private String getNodeKey(String partyId) throws GenericServiceException {
        return (String) dispatcher.runSync("gnGetNodeKeyFromPartyId", UtilMisc.toMap("userLogin", userLogin, "partyId", partyId)).get("nodeKey");
    }


    /**
     * Create a draft authorization copy from a published.
     * If draft copy already exist, return key of it.
     * TODO: add EventMessage
     *
     * @param fromAgreementId
     * @param fromAuthorizationKey
     * @return map contains new agreementId and authorizationKey
     * @throws GenericServiceException
     * @throws GenericEntityException
     */

    public Map<String, Object> gnCopyAuthorization(String fromAgreementId, String fromAuthorizationKey) throws GenericServiceException, GenericEntityException {
        Map<String, Object> result = ServiceUtil.returnSuccess();

        Map<String, Object> fromAuth = authorizationHelper.findAuthorizationById(fromAgreementId, fromAuthorizationKey, false, true);


        if (UtilValidate.isEmpty(fromAuth))
            throw new GnServiceException(OfbizErrors.ENTITY_EXCEPTION,
                    "authorization with id[" + fromAgreementId + "] or key[" + fromAuthorizationKey + "] not exist.");
        if (!AuthorizationStatusOfbiz.GN_AUTH_PUBLISHED.name().equals((String) fromAuth.get("statusId")))
            throw new GnServiceException(OfbizErrors.ENTITY_EXCEPTION,
                    "authorization with id[" + fromAgreementId + "] or key[" + fromAuthorizationKey + "] is not in PUBLISHED state.");


        Map<String, Object> contextNode = UtilGenerics.checkMap(getCurrentContext().get("partyNode"));
        String ownerNodeId = (String) contextNode.get("partyId");
        if (!AuthUtil.isOwner(fromAuth, ownerNodeId))
            throw new GnServiceException(OfbizErrors.ENTITY_EXCEPTION,
                    "authorization with id[" + fromAgreementId + "] or key[" + fromAuthorizationKey + "] is not in node[" + ownerNodeId + "]");

        NodeSearchHelper nodeSearchHelper = new NodeSearchHelper(dctx, context);
        Map<String, Object> holderCompany = UtilGenerics.checkMap(nodeSearchHelper.gnFindExtendedNodeById((String) fromAuth.get("otherPartyIdTo"), false).get("partyNode"));
        Map<String, Object> holderCompanyBase = UtilGenerics.checkMap(nodeSearchHelper.gnFindExtendedNodeById((String) fromAuth.get("partyIdTo"), false).get("partyNode"));
        if (!authorizationHelper.existsAuthorizationByLogicalKey(fromAuthorizationKey, (String) fromAuth.get("partyIdFrom"), (String) fromAuth.get("partyIdTo"), (String) fromAuth.get("number"),
                (String) holderCompany.get("taxIdentificationNumber"), (String) holderCompany.get("description"), holderCompanyBase, AuthorizationTypesOfbiz.valueOf((String) fromAuth.get("typeId")), ownerNodeId, null)) {

            String _partyIdTo = (String) fromAuth.get("originPartyIdTo");
            if (UtilValidate.isEmpty(_partyIdTo)) _partyIdTo = (String) fromAuth.get("partyIdTo");
            canCreateAuthorization(_partyIdTo, contextNode, (String) fromAuth.get("isPrivate"));

            result = copyAuthorization(fromAgreementId, fromAuthorizationKey);

            Map<String, Object> savedAuthorization = UtilGenerics.checkMap(result.get("authorization"));
            authorizationHelper.gnSendCommunicationEventToContactList(ownerNodeId, EventTypeOfbiz.GN_COM_EV_AUTH_CRE.name(), savedAuthorization);
        }

        return result;
    }

    /**
     * @param fromAgreementId
     * @param formAuthorizationKey
     * @return
     * @throws GenericServiceException
     * @throws GenericEntityException
     */
    private Map<String, Object> copyAuthorization(String fromAgreementId, String formAuthorizationKey) throws GenericServiceException, GenericEntityException {
        Map<String, Object> result = FastMap.newInstance();
        long timeToLoadAuthorization = System.currentTimeMillis();
        Map<String, Object> fromAuth = authorizationHelper.findAuthorizationById(fromAgreementId, formAuthorizationKey, false, true);
        timeToLoadAuthorization = System.currentTimeMillis() - timeToLoadAuthorization;
        Debug.log(String.format("copyAuthorization: Authorization loaded in[%s]ms", timeToLoadAuthorization), module);

        String originId = (String) getUserLogin().get("originId");

        if (UtilValidate.isEmpty(fromAuth))
            throw new GnServiceException(OfbizErrors.ENTITY_EXCEPTION,
                    "authorization with id[" + fromAgreementId + "] or key[" + formAuthorizationKey + "] not exist.");
        if (!AuthorizationStatusOfbiz.GN_AUTH_PUBLISHED.name().equals(fromAuth.get("statusId")))
            throw new GnServiceException(OfbizErrors.ENTITY_EXCEPTION,
                    "authorization with id[" + fromAgreementId + "] or key[" + formAuthorizationKey + "] is not in PUBLISHED state.");

        Map<String, Object> contextNode = UtilGenerics.checkMap(getCurrentContext().get("partyNode"));
        String ownerNodeId = (String) contextNode.get("partyId");
        if (!AuthUtil.isOwner(fromAuth, ownerNodeId))
            throw new GnServiceException(OfbizErrors.ENTITY_EXCEPTION,
                    "authorization with id[" + fromAgreementId + "] or key[" + formAuthorizationKey + "] is not in node[" + ownerNodeId + "]");


        AuthorizationKey key = AuthorizationKey.fromString((String) fromAuth.get("authorizationKey"));
        key.setVersion(key.getVersion() + 1);

        GenericValue toAuth = authorizationHelper.findAuthorizationById(null, key.toString());
        if (UtilValidate.isNotEmpty(toAuth)) {
            long timeToSaveAuthorization = System.currentTimeMillis();
            Debug.log("Authorization with key[" + key.toString() + "] is already copied.", module);
            result.put("authorizationKey", toAuth.getString("authorizationKey"));
            result.put("agreementId", toAuth.getString("agreementId"));
            result.put("authorization", authorizationHelper.findAuthorizationById(toAuth.getString("agreementId"), null, true, true));
            Debug.log(String.format("copyAuthorization: Authorization exist in[%s]ms", timeToSaveAuthorization), module);
        } else {
            long timeToSaveAuthorization = System.currentTimeMillis();
            Debug.log("Coping authorization with key[" + key.toString() + "]", module);

            Map<String, Object> pvtCompanyBase = authorizationHelper.findPartyNodeById((String) fromAuth.get("partyIdTo"));
            pvtCompanyBase.put("nodeKey", null);
            pvtCompanyBase.put("partyId", null);
            UtilGenerics.checkMap(pvtCompanyBase.get("address")).put("contactMechId", null);

            Map<String, Object> pvtCompany = authorizationHelper.findPartyNodeById((String) fromAuth.get("otherPartyIdTo"));
            pvtCompany.put("partyId", null);
            pvtCompany.put("nodeKey", null);

            Map<String, String> partyIdsTo = authorizationPartyNodeHelper.createUpdateReceivedCompanyAndCompanyBase((String) fromAuth.get("ownerNodeKey"), pvtCompanyBase, pvtCompany);
            result = authorizationHelper.createAuthorization(key, (String) fromAuth.get("ownerNodeId"),
                    partyIdsTo.get("companyBasePartyId"), partyIdsTo.get("companyPartyId"), (String) fromAuth.get("partyIdFrom"),
                    (String) fromAuth.get("typeId"), (String) fromAuth.get("number"), AuthorizationStatusOfbiz.GN_AUTH_DRAFT.name(), null,
                    (String) fromAuth.get("description"), (String) fromAuth.get("textData"), originId, (String) fromAuth.get("originPartyIdTo"),
                    (String) fromAuth.get("isPrivate"), UtilDateTime.nowTimestamp());
            String authorizationKey = (String) result.get("authorizationKey");
            String agreementId = (String) result.get("agreementId");

            List<Map<String, Object>> items = UtilGenerics.toList(fromAuth.get("authorizationItems"));
            for (Map<String, Object> item : items) {
                List<Map<String, Object>> terms = UtilGenerics.toList(item.get("agreementTerms"));
                String oldAuthorizationItemKey = (String) item.get("authorizationItemKey");
                authorizationItemHelper.createAuthorizationItem(agreementId, authorizationKey, oldAuthorizationItemKey, (String) item.get("agreementText"),
                        (String) item.get("simplified"), (String) item.get("holderRoleId"), (String) item.get("categoryClassEnumId"),
                        (Timestamp) item.get("validFromDate"), (Timestamp) item.get("validThruDate"),
                        (Timestamp) item.get("suspendFromDate"), (Timestamp) item.get("suspendThruDate"), clearTerms(terms), (String) fromAuth.get("typeId"));
            }
            List<Map<String, Object>> authorizationDocuments = UtilGenerics.toList(fromAuth.get("authorizationDocuments"));
            for (Map<String, Object> authDoc : authorizationDocuments) {
                String contentName = (String) authDoc.get("contentName");
                String description = (String) authDoc.get("description");
                String mimeTypeId = (String) authDoc.get("mimeTypeId");
                String agreementContentTypeId = (String) authDoc.get("agreementContentTypeId");
                Long contentSize = (Long) authDoc.get("contentSize");
                Timestamp createdDate = (Timestamp) authDoc.get("createdDate");

                authorizationAttachHelper.upload(agreementId, contentName, description, mimeTypeId, contentSize, createdDate, agreementContentTypeId, false);
            }

            result.put("authorizationKey", authorizationKey);
            result.put("agreementId", agreementId);
            timeToSaveAuthorization = System.currentTimeMillis() - timeToSaveAuthorization;
            Debug.log(String.format("copyAuthorization: Authorization saved in[%s]ms", timeToSaveAuthorization), module);

            long timeToReloadAuthorization = System.currentTimeMillis();
            Map<String, Object> savedAuthorization = authorizationHelper.findAuthorizationById(agreementId, null, true, true);
            timeToReloadAuthorization = System.currentTimeMillis() - timeToReloadAuthorization;
            Debug.log(String.format("copyAuthorization: Authorization reloaded in[%s]ms", timeToReloadAuthorization), module);
            result.put("authorization", savedAuthorization);
        }

        return result;
    }

    /**
     * Remove previous parent references (for example agreementId, waste...) from terms however service can
     * create new instance without problems.
     *
     * @param terms
     * @return processed list of terms
     */
    public List<Map<String, Object>> clearTerms(List<Map<String, Object>> terms) {
        List<Map<String, Object>> result = FastList.newInstance();
        for (Map<String, Object> term : terms) {
            Map<String, Object> term1 = FastMap.newInstance();
            term1.putAll(term);
            term1.remove("agreementId");
            term1.remove("agreementItemSeqId");
            term1.remove("agreementTermId");
            if (AgreementTermTypeOfbiz.GN_CNS_PACKAGING.is(term.get("termTypeId"))) {
                List<String> packagingIds = new ArrayList<String>();
                for (Map<String, Object> termNode : UtilGenerics.<Map<String, Object>>checkList(term.get("packagings"))) {
                    packagingIds.add((String) termNode.get("packagingId"));
                }
                term1.put("packagingIds", packagingIds);
                term1.remove("packagings");
            } else if (AgreementTermTypeOfbiz.GN_CNS_OPERATION.is(term.get("termTypeId"))) {
                List<String> operationIds = new ArrayList<String>();
                for (Map<String, Object> termNode : UtilGenerics.<Map<String, Object>>checkList(term.get("operations"))) {
                    operationIds.add((String) termNode.get("operationId"));
                }
                term1.put("operationIds", operationIds);
                term1.remove("operations");
            } else if (AgreementTermTypeOfbiz.GN_CNS_VEHICLE_REG_N.is(term.get("termTypeId"))) {
                List<String> registrationNumberIds = new ArrayList<String>();
                for (Map<String, Object> termNode : UtilGenerics.<Map<String, Object>>checkList(term.get("registrationNumbers"))) {
                    registrationNumberIds.add((String) termNode.get("registrationNumber"));
                }
                term1.put("registrationNumberIds", registrationNumberIds);
                term1.remove("registrationNumbers");
            } else if (AgreementTermTypeOfbiz.GN_CNS_SUBJECT_KIND.is(term.get("termTypeId"))) {
                List<String> subjectTypeIds = new ArrayList<String>();
                for (Map<String, Object> termNode : UtilGenerics.<Map<String, Object>>checkList(term.get("subjectTypes"))) {
                    subjectTypeIds.add((String) termNode.get("enumId"));
                }
                term1.put("subjectTypeIds", subjectTypeIds);
                term1.remove("subjectTypes");
            } else if (AgreementTermTypeOfbiz.GN_CNS_WASTE.is(term.get("termTypeId"))) {
                List<String> wasteCerCodeIds = new ArrayList<String>();
                for (Map<String, Object> termNode : UtilGenerics.<Map<String, Object>>checkList(term.get("wasteCerCodes"))) {
                    wasteCerCodeIds.add((String) termNode.get("wasteCerCodeId"));
                }
                term1.put("wasteCerCodeIds", wasteCerCodeIds);
                term1.remove("wasteCerCodes");
            } else if (AgreementTermTypeOfbiz.GN_CNS_SUBJ_COMPANY.is(term.get("termTypeId"))) {
                List<String> partyNodeKeys = new ArrayList<String>();
                for (Map<String, Object> termNode : UtilGenerics.<Map<String, Object>>checkList(term.get("partyNodes"))) {
                    partyNodeKeys.add((String) termNode.get("nodeKey"));
                }
                term1.put("partyNodeKeys", partyNodeKeys);
                term1.remove("partyNodes");
            }
            result.add(term1);

        }
        return result;
    }

    /**
     * @param relationship
     * @param authorization
     * @param nodeKey
     * @return
     * @throws GenericServiceException
     */
    public boolean canBeReceived(GenericValue relationship, Map<String, Object> authorization, String nodeKey) throws GenericServiceException {
        Debug.log("Applying filter Authorization[" + authorization.get("authorizationKey") + "] on incoming relationship of node [" + nodeKey + "]", module);
        Map<String, Object> srvRequest = PartyRelationshipIdUtil.getKeyMap(relationship);
        srvRequest.put("userLogin", userLogin);
        srvRequest.put("agreementTypeId", AgreementTypesOfbiz.GN_AUTH_FILTER.name());
        Map<String, Object> srvResult = dispatcher.runSync("gnFindAuthorizationFilterByRelationship", srvRequest);
        List<Map<String, Object>> filters = UtilMisc.getListFromMap(srvResult, "filters");
        if (filters.size() == 0) {
            Debug.log("No filter found", module);
            return ("Y".equals(relationship.get("acceptAllIfEmptyFilters")));
        } else {
            for (Map<String, Object> filter : filters) {
                Debug.log("Matching filter[" + filter.get("agreementId") + "," + filter.get("filterKey") + "] - " + filter.get("description"), module);
                Map<String, Object> result = dispatcher.runSync("gnAuthorizationMatchFilter", UtilMisc.toMap("authorization", authorization, "filter", filter, "mode", "IN", "userLogin", userLogin));
                if ("Y".equals(result.get("match"))) {
                    Debug.log("Filter match", module);
                    return true;
                } else Debug.log("Filter doesn't match", module);
            }
            return false;
        }
    }

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
     * @param conflictingAuthorizationKey
     * @return blamer authorization key
     * @throws GenericServiceException
     * @throws GenericEntityException
     */
    public String gnFindBlamerAuthorization(String conflictingAuthorizationKey, String conflictingAgreementId) throws GenericServiceException, GenericEntityException {
        if (UtilValidate.isNotEmpty(conflictingAuthorizationKey))
            conflictingAgreementId = authorizationHelper.getAgreementId(conflictingAuthorizationKey);
        if (UtilValidate.isEmpty(conflictingAgreementId))
            throw new GnServiceException(OfbizErrors.SERVICE_EXCEPTION, "AgreementId is is empty");
        Map<String, Object> conflictingAuth = authorizationHelper.gnFindAuthorizationById(conflictingAgreementId, conflictingAuthorizationKey, true, false);
        Map<String, Object> currentContext = getCurrentContext();
        Map<String, Object> contextNode = UtilGenerics.checkMap(currentContext.get("partyNode"));
        String ownerNodeId = (String) contextNode.get("partyId");

        if (!AuthUtil.isOwner(conflictingAuth, ownerNodeId)) { // || AuthUtil.isPublishedBy(conflictingAuth, ownerNodeId)
            throw new GnServiceException(OfbizErrors.INVALID_PARAMETERS, String.format("Authorization[%s] doesn't belong to contextNode[%s]", conflictingAuthorizationKey, ownerNodeId));
        }

        if (!AuthorizationStatusOfbiz.GN_AUTH_CONFLICTING.name().equals(conflictingAuth.get("statusId"))) {
            throw new GnServiceException(OfbizErrors.AUTHORIZATION_NOT_CONFLICTING,
                    String.format("Authorization[%s] is not in status %s", conflictingAuthorizationKey, AuthorizationStatusOfbiz.GN_AUTH_CONFLICTING));
        }
        List<String> keys = findConflicting(ownerNodeId, conflictingAuth);
        if (keys.size() > 0) return keys.get(0);
        return null;
    }

    /**
     * @param ownerNodeId
     * @param ownerNodeKey todo: remove ASAP
     * @throws GenericEntityException
     * @throws GenericServiceException
     */
    public void refreshConflictingAuthorization(String ownerNodeId, @Deprecated String ownerNodeKey) throws GenericEntityException, GenericServiceException {
        List<GenericValue> authToBeValidated = authorizationHelper.findByKey(null, null, null, ownerNodeKey, null, null, AuthorizationStatusOfbiz.GN_AUTH_TO_BE_VALID, null);
        List<GenericValue> authConflicting = authorizationHelper.findByKey(null, null, null, ownerNodeKey, null, null, AuthorizationStatusOfbiz.GN_AUTH_CONFLICTING, null);

        for (GenericValue auth : authToBeValidated) {
            //todo: remove authorizationHelper.findAuthorizationById and load nodes if really needed
            List<String> result = findConflicting(ownerNodeId, authorizationHelper.findAuthorizationById(auth.getString("agreementId"), auth.getString("authorizationKey"), true, false));
            if (result.size() > 0) {
                updateAuthorizationState(auth.getString("agreementId"), AuthorizationStatusOfbiz.GN_AUTH_CONFLICTING, auth.getString("originId"));
                Debug.log(String.format("Changed state of authorization[%s] form %s to %s", auth.getString("authorizationKey"),
                        AuthorizationStatusOfbiz.GN_AUTH_TO_BE_VALID.name(), AuthorizationStatusOfbiz.GN_AUTH_CONFLICTING.name()), module);
            }
        }
        for (GenericValue auth : authConflicting) {
            List<String> result = findConflicting(ownerNodeId, authorizationHelper.findAuthorizationById(auth.getString("agreementId"), auth.getString("authorizationKey"), true, false));
            if (result.size() == 0) {
                updateAuthorizationState(auth.getString("agreementId"), AuthorizationStatusOfbiz.GN_AUTH_TO_BE_VALID, auth.getString("originId"));
                Debug.log(String.format("Changed state of authorization[%s] form %s to %s", auth.getString("authorizationKey"),
                        AuthorizationStatusOfbiz.GN_AUTH_CONFLICTING.name(), AuthorizationStatusOfbiz.GN_AUTH_TO_BE_VALID.name()), module);
            }

        }
    }

    /**
     * @param ownerNodeId
     * @param authorization
     * @return conflicting authorization keys
     * @throws GenericServiceException
     * @throws GenericEntityException
     */
    protected List<String> findConflicting(String ownerNodeId, Map<String, Object> authorization) throws GenericServiceException, GenericEntityException {
        String partyIdFrom = (String) authorization.get("partyIdFrom");
        if (UtilValidate.isEmpty(partyIdFrom)) {
            Map<String, Object> partyNodeFrom = UtilGenerics.checkMap(authorization.get("partyNodeFrom"));
            partyIdFrom = getPartyId(null, (String) partyNodeFrom.get("nodeKey"));
        }
        List<String> keys = authorizationHelper.findConflicting(ownerNodeId,
                (String) authorization.get("authorizationKey"),
                partyIdFrom,
                (String) authorization.get("number"),
                MapUtil.getValue(authorization, "otherPartyNodeTo.taxIdentificationNumber", String.class),
                /*MapUtil.getValue(authorization, "partyNodeTo.address.address1", String.class),
                MapUtil.getValue(authorization, "partyNodeTo.address.streetNumber", String.class),
                MapUtil.getValue(authorization, "partyNodeTo.address.village", String.class),
                MapUtil.getValue(authorization, "partyNodeTo.address.postalCodeGeoId", String.class),*/
                AuthorizationStatusOfbiz.GN_AUTH_PUBLISHED, AuthorizationTypesOfbiz.valueOf((String) authorization.get("typeId")), null); //TODO verify
        if (keys.size() > 1) {
            throw new GnServiceException(OfbizErrors.INVALID_PARAMETERS,
                    String.format("Authorization[%s] conflicts  with %s other authorizations", authorization.get("authorizationKey"), StringUtil.join(keys, ",")));
        }
        return keys;
    }

    /**
     * @param conflictingAuthorizationKey
     * @param action
     * @return
     * @throws GenericServiceException
     * @throws GenericEntityException
     */
    public Map<String, Object> gnResolveAuthorizationConflictEM(final String conflictingAuthorizationKey, String conflictingAgreementId, ResolveAuthorizationConflictActionOfbiz action) throws GenericServiceException, GenericEntityException {
        List<EventMessage> messages = FastList.newInstance();

        GnSecurity gnSecurity = new GnSecurity(delegator);
        boolean hasPermission = gnSecurity.hasPermission(PermissionsOfbiz.GN_AUTH_VALIDATE.toString(), userLogin);
        if (!hasPermission)
            throw new GnServiceException(OfbizErrors.ACCESS_DENIED, "User hasn't got permission to validate authorization");

        if (UtilValidate.isNotEmpty(conflictingAuthorizationKey))
            conflictingAgreementId = authorizationHelper.getAgreementId(conflictingAuthorizationKey);
        if (UtilValidate.isEmpty(conflictingAgreementId))
            throw new GnServiceException(OfbizErrors.SERVICE_EXCEPTION, "AgreementId is is empty");

        Map<String, Object> currentContext = getCurrentContext();
        Map<String, Object> contextNode = UtilGenerics.checkMap(currentContext.get("partyNode"));
        String contextNodePartyId = (String) contextNode.get("partyId");

        Map<String, Object> conflictingAuthorization = authorizationHelper.findAuthorizationById(conflictingAgreementId, conflictingAuthorizationKey, true, false);
        String originId = (String) conflictingAuthorization.get("originId");


        if (!AuthUtil.isOwner(conflictingAuthorization, contextNodePartyId)) { // || AuthUtil.isPublishedBy(conflictingAuthorization, contextNodePartyId)
            throw new GnServiceException(OfbizErrors.INVALID_PARAMETERS, String.format("Authorization[%s] doesn't belong to contextNode[%s]", conflictingAuthorizationKey, contextNodePartyId));
        }
        if (!AuthorizationStatusOfbiz.GN_AUTH_CONFLICTING.toString().equals(conflictingAuthorization.get("statusId"))) {
            throw new GnServiceException(OfbizErrors.INVALID_PARAMETERS, String.format("Authorization[%s] is not in [%s] state.", conflictingAuthorizationKey, AuthorizationStatusOfbiz.GN_AUTH_CONFLICTING.toString()));
        }
        Debug.log(String.format("Resolving Authorization Conflict action[%s]", action), module);
        List<String> ret = findConflicting(contextNodePartyId, conflictingAuthorization);
        if (ret.size() == 0)
            throw new GnServiceException(OfbizErrors.INVALID_PARAMETERS, String.format("Authorization[%s] is not in conflict with another", conflictingAuthorizationKey));

        String blamerAuthorizationKey = ret.get(0);

        Map<String, Object> result = FastMap.newInstance();
        Map<String, Object> authRejected;
        Map<String, Object> ownerNodeRejected;

        if (action == ResolveAuthorizationConflictActionOfbiz.GN_ACCEPT) {
            //load published authorization
            Map<String, Object> blamerAuthorization = authorizationHelper.findAuthorizationById(null, blamerAuthorizationKey, true, true);
            Map<String, Object> ownerNode = authorizationHelper.findPartyNodeById((String) blamerAuthorization.get("ownerNodeId"));
            String blamerAuthorizationId = (String) blamerAuthorization.get("agreementId");
            String blamerAuthorizationOrigin = (String) blamerAuthorization.get("originId");

            updateAuthorizationInternalState(blamerAuthorizationId, AuthorizationInternalStatusOfbiz.GN_AUTH_TO_BE_DELET, blamerAuthorizationOrigin);

//TODO ********
            if (AuthUtil.checkState(blamerAuthorization, AuthorizationStatusOfbiz.GN_AUTH_PUBLISHED)) {
                EventMessageAuthIndexingDelete indDel = EventMessageFactory.make.makeEventMessageAuthIndexingDelete(
                        (String) blamerAuthorization.get("ownerNodeKey"),
                        (String) blamerAuthorization.get("ownerNodeId"),
                        (String) ownerNode.get("nodeType"),
                        (String) blamerAuthorization.get("authorizationKey"));
                messages.add(indDel);
            }


            EventMessageAuthArchived archiveMessage = EventMessageFactory.make().makeEventMessageAuthArchived(
                    (String) blamerAuthorization.get("ownerNodeKey"),
                    (String) blamerAuthorization.get("ownerNodeId"),
                    (String) ownerNode.get("nodeType"),
                    removeOfBizIds(blamerAuthorization, false)
            );
            messages.add(archiveMessage);
            //deleting authorization in state TO_BE_VALIDATE is exist
            deleteToBeValidateAuthorization((String) blamerAuthorization.get("uuid"), ownerNode, blamerAuthorizationOrigin);

            updateAuthorizationState(conflictingAgreementId, AuthorizationStatusOfbiz.GN_AUTH_TO_BE_VALID, originId);

            Map<String, Object> publishingResult = publishAuthorizationByIdEM(conflictingAgreementId, conflictingAuthorizationKey, originId, ActionTypeEnumOFBiz.CONFLICT_ACCEPTED);
            //published authorizationKey
            result.put("authorizationKey", conflictingAuthorizationKey);

            authRejected = blamerAuthorization;
            ownerNodeRejected = ownerNode;

        } else if (action == ResolveAuthorizationConflictActionOfbiz.GN_REJECT) {
            Map<String, Object> authorization = authorizationHelper.findAuthorizationById(conflictingAgreementId, null, true, true);
            updateAuthorizationState(conflictingAgreementId, AuthorizationStatusOfbiz.GN_AUTH_NOT_VALID, (String) authorization.get("originId"));
            updateAuthorizationInternalState(conflictingAgreementId, AuthorizationInternalStatusOfbiz.GN_AUTH_TO_BE_DELET, (String) authorization.get("originId"));

            authorizationHelper.gnSendCommunicationEventToContactListEM((String) authorization.get("ownerNodeId"), EventTypeOfbiz.GN_COM_EV_AUTH_REJ.toString(), authorization);

            Map<String, Object> ownerNode = authorizationHelper.findPartyNodeById((String) authorization.get("ownerNodeId"));
            EventMessageAuthArchived em = EventMessageFactory.make().makeEventMessageAuthArchived(
                    (String) authorization.get("ownerNodeKey"),
                    (String) authorization.get("ownerNodeId"),
                    (String) ownerNode.get("nodeType"),
                    removeOfBizIds(authorization, false)
            );
            messages.add(em);

            authRejected = authorization;
            ownerNodeRejected = ownerNode;
            result.put("authorizationKey", blamerAuthorizationKey);
        } else if (action == ResolveAuthorizationConflictActionOfbiz.GN_ACCEPT_DOCUMENTS) {
            Map<String, Object> copyResult = copyAuthorization(null, blamerAuthorizationKey);
            Map<String, Object> draftAuthorization = UtilGenerics.checkMap(copyResult.get("authorization"));
            String draftAgreementId = (String) copyResult.get("agreementId");
            String draftAuthorizationKey = (String) copyResult.get("authorizationKey");

            Map<String, Object> toBeDeleted = authorizationHelper.findAuthorizationById(conflictingAgreementId, conflictingAuthorizationKey, true, true);
            Map<String, Object> ownerNode = authorizationHelper.findPartyNodeById((String) toBeDeleted.get("ownerNodeId"));

            updateAuthorizationInternalState(conflictingAgreementId, AuthorizationInternalStatusOfbiz.GN_AUTH_TO_BE_DELET, (String) toBeDeleted.get("originId"));
            EventMessageAuthArchived archiveMessage = EventMessageFactory.make().makeEventMessageAuthArchived(
                    (String) toBeDeleted.get("ownerNodeKey"),
                    (String) toBeDeleted.get("ownerNodeId"),
                    (String) ownerNode.get("nodeType"),
                    removeOfBizIds(toBeDeleted, false)
            );
            messages.add(archiveMessage);

            EventMessageAuthMerge authMerge = EventMessageFactory.make.makeEventMessageAuthMerge(
                    (String) toBeDeleted.get("ownerNodeKey"),
                    (String) toBeDeleted.get("ownerNodeId"),
                    (String) ownerNode.get("nodeType"),
                    draftAuthorizationKey,
                    conflictingAuthorizationKey);
            messages.add(authMerge);

            //copy documents if needed
            originId = (String) draftAuthorization.get("originId");
            List<GenericValue> docs = authorizationAttachHelper.find(draftAgreementId, null);
            Debug.log(String.format("Copying Authorization Documents"), module);
            //delete old attachments
            for (GenericValue doc : docs) {
                authorizationAttachHelper.delete(doc);
            }
            //copying attachments from delete authorization
            List<GenericValue> docsToCopy = authorizationAttachHelper.find(conflictingAgreementId, null);
            for (Map<String, Object> doc : docsToCopy) {
                authorizationAttachHelper.upload(draftAgreementId, (String) doc.get("contentName"), (String) doc.get("description"), (String) doc.get("mimeTypeId"),
                        (Long) doc.get("contentSize"), (Timestamp) doc.get("createdDate"), (String) doc.get("agreementContentTypeId"), false);
            }
            // gnAuthorizationMergeAckEM will make the event with CONFLICT_MERGED

            authRejected = toBeDeleted;
            ownerNodeRejected = ownerNode;
            result.put("authorizationKey", draftAuthorizationKey);
        } else {
            throw new GnServiceException(OfbizErrors.SERVICE_EXCEPTION, "ResolveAuthorizationConflictActionOfbiz not expected: " + action.name());
        }

        EventMessageAuthAudit audit = EventMessageFactory.make.makeEventMessageAuthAudit( //all cases expects an auth. rejected
                ActionTypeEnumOFBiz.CONFLICT_REJECTED,
                getCurrentContextId(),
                userLogin.getString("userLoginId"),
                (String) authRejected.get("ownerNodeKey"),
                (String) authRejected.get("ownerNodeId"),
                (String) ownerNodeRejected.get("nodeType"),
                removeOfBizIds(authRejected, true)
        );
        messages.add(audit);

        EventMessageContainer.addAll(messages);
        return result;
    }

    /**
     * Delete authorization in state {@link it.memelabs.gn.services.authorization.AuthorizationStatusOfbiz#GN_AUTH_TO_BE_VALID}
     * with passed uuid on node ownerNode if exist
     *
     * @param uuid
     * @param ownerNode
     * @param authorizationOrigin
     * @throws GenericEntityException
     * @throws GnServiceException
     */
    private void deleteToBeValidateAuthorization(String uuid, Map<String, Object> ownerNode, String authorizationOrigin) throws GenericEntityException, GenericServiceException {
        List<GenericValue> toBeValAuths = authorizationHelper.findByKey(null, uuid, null, (String) ownerNode.get("nodeKey"),
                null, null, AuthorizationStatusOfbiz.GN_AUTH_TO_BE_VALID, null);
        if (toBeValAuths.size() > 1)
            throw new GnServiceException(OfbizErrors.GENERIC, String.format("Too many authorization with uuid[%s] in state[%s] on node [%s]",
                    uuid, AuthorizationStatusOfbiz.GN_AUTH_TO_BE_VALID, ownerNode.get("nodeKey")));
        if (toBeValAuths.size() == 1) {
            Map<String, Object> toBeValAuthorization = authorizationHelper.findAuthorizationById(null, toBeValAuths.get(0).getString("authorizationKey"), true, true);
            Debug.log(String.format("Deleting authorization with agreementId[%s] and authorizationKey[%s] in state[%s]",
                    toBeValAuthorization.get("agreementId"), toBeValAuthorization.get("authorizationKey"), AuthorizationStatusOfbiz.GN_AUTH_TO_BE_VALID), module);
            updateAuthorizationInternalState((String) toBeValAuthorization.get("agreementId"), AuthorizationInternalStatusOfbiz.GN_AUTH_TO_BE_DELET, authorizationOrigin);
            EventMessageAuthArchived archiveMessage2 = EventMessageFactory.make().makeEventMessageAuthArchived(
                    (String) toBeValAuthorization.get("ownerNodeKey"),
                    (String) toBeValAuthorization.get("ownerNodeId"),
                    (String) ownerNode.get("nodeType"),
                    removeOfBizIds(toBeValAuthorization, false)
            );
            EventMessageContainer.add(archiveMessage2);
        }
    }

    public void gnAuthorizationMergeAckEM(String authorizationKey, String nodeKey, String partyId) throws GenericServiceException, GenericEntityException {
        AuthorizationHelper authorizationHelper = new AuthorizationHelper(dctx, context);

        if (UtilValidate.isEmpty(authorizationKey)) {
            throw new GnServiceException(OfbizErrors.SERVICE_EXCEPTION, "The AuthorizationKey is empty");
        }
        String agreementId = authorizationHelper.getAgreementId(authorizationKey);

        Map<String, Object> authorization = authorizationHelper.findAuthorizationById(null, authorizationKey, true, true);
        String originId = (String) authorization.get("originId");

        Map<String, Object> publishingResult = publishAuthorizationByIdEM(agreementId, authorizationKey, originId, ActionTypeEnumOFBiz.CONFLICT_MERGED);
    }
}
