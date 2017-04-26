package it.memelabs.gn.services.node;

import it.memelabs.gn.services.AbstractServiceHelper;
import it.memelabs.gn.services.OfbizErrors;
import it.memelabs.gn.services.authorization.AuthorizationOriginOfbiz;
import it.memelabs.gn.services.authorization.AuthorizationStatusOfbiz;
import it.memelabs.gn.services.communication.CommunicationEventTypeOfbiz;
import it.memelabs.gn.services.communication.EventTypeOfbiz;
import it.memelabs.gn.services.event.EventMessageContainer;
import it.memelabs.gn.services.event.EventMessageDirectionOfbiz;
import it.memelabs.gn.services.event.EventMessageFactory;
import it.memelabs.gn.services.event.type.EventMessage;
import it.memelabs.gn.services.event.type.EventMessageFilterAperture;
import it.memelabs.gn.services.login.LoginSourceOfbiz;
import it.memelabs.gn.services.security.PermissionsOfbiz;
import it.memelabs.gn.services.system.GnAuthenticator;
import it.memelabs.gn.services.system.PropertyEnumOfbiz;
import it.memelabs.gn.util.EntityConditionUtil;
import it.memelabs.gn.util.GnServiceException;
import it.memelabs.gn.util.PropertyUtil;
import it.memelabs.gn.util.SysUtil;
import it.memelabs.gn.util.find.GnFindUtil;
import javolution.util.FastList;
import javolution.util.FastMap;
import org.ofbiz.base.util.*;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.entity.condition.EntityCondition;
import org.ofbiz.entity.condition.EntityFunction;
import org.ofbiz.entity.condition.EntityOperator;
import org.ofbiz.entity.model.DynamicViewEntity;
import org.ofbiz.entity.model.ModelKeyMap;
import org.ofbiz.service.DispatchContext;
import org.ofbiz.service.GenericServiceException;

import java.sql.Timestamp;
import java.util.*;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.compile;

/**
 * 08/05/13
 *
 * @author Andrea Fossi
 */
public class InvitationHelper extends AbstractServiceHelper {

    public InvitationHelper(DispatchContext dctx, Map<String, ? extends Object> context) {
        super(dctx, context);
    }

    private static final String module = InvitationHelper.class.getName();
    public static final String resource = "GnUiLabels.xml";
    public static final String EMAIL_REGEXP = "[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}";
    private static final Pattern EMAIL_PATTERN = compile(EMAIL_REGEXP);

    /**
     * Send an invitation
     * <p/>
     * company where user is employed
     *
     * @param partyInvitation map
     * @return partyInvitationId
     * @throws GenericServiceException
     */
    public Map<String, Object> gnCreatePartyInvitation(Map<String, Object> partyInvitation) throws GenericServiceException, GenericEntityException {
        Debug.log("Create PartyInvitation", module);
        int days = Integer.parseInt(PropertyUtil.getProperty(PropertyEnumOfbiz.PARTY_INVITATION_EXPIRATION, getDispatcher(), getContext()));
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, days);
        Timestamp expirationDate = UtilDateTime.getDayStart(new Timestamp(cal.getTimeInMillis()));
        partyInvitation.put("expirationDate", expirationDate);

        Map<String, Object> result = FastMap.newInstance();
        //manage invited partyNode
        @SuppressWarnings("unchecked")
        Map<String, Object> invited = (Map<String, Object>) partyInvitation.get("partyNode");
        if (UtilValidate.isEmpty(invited)) {
            throw new GnServiceException(OfbizErrors.INVALID_PARAMETERS, "invited PartyNode in mandatory.");
        }

        NodeHelper nodeHelper = new NodeHelper(dctx, context);
        if (!(partyInvitation.get("geoCode").equals("IT")) || nodeHelper.syntacticVerifyCompanyTaxAndFiscalCode(invited)) {

            String taxIdentificationNumber = (String) invited.get("taxIdentificationNumber");
            String invitedCompanyPartyId = findCompany((String) invited.get("VATnumber"), taxIdentificationNumber);

            //*** CREATE INVITATION (start) ***///

            String nodePartyIdFrom = (String) (UtilGenerics.checkMap(getCurrentContext().get("partyNode"))).get("partyId");
            partyInvitation.put("nodePartyIdFrom", nodePartyIdFrom);

            String statusId;
            String companyStatusId;
            @SuppressWarnings("unchecked")
            List<String> emails = (List<String>) partyInvitation.get("emails");

            if (UtilValidate.isEmpty(invitedCompanyPartyId)) {
                statusId = PartyInvitationStateOfbiz.PARTYINV_PENDING.name();
                //invitedCompanyPartyId = getPartyId((String) invited.get("partyId"), (String) invited.get("nodeKey"));
                //if (UtilValidate.isEmpty(invitedCompanyPartyId)) {
                //Always create a new invited company
                if (!PartyNodeTypeOfbiz.isAInvitedCompany(invited.get("nodeType"))) {
                    throw new GnServiceException(OfbizErrors.INVALID_PARAMETERS, "partyNode.nodeType must be a " + PartyNodeTypeOfbiz.INVITED_COMPANY.name());
                }
                invited.put("nodeKey", null);
                invited.put("partyId", null);
                invitedCompanyPartyId = (String) createPartyNode(invited).get("partyId");
                // }
                /*invitedCompanyPartyId = getPartyId((String) invited.get("partyId"), (String) invited.get("nodeKey"));
                if (UtilValidate.isEmpty(invitedCompanyPartyId)) {
                    if (!PartyNodeTypeOfbiz.isAInvitedCompany(invited.get("nodeType"))) {
                        throw new GnServiceException(OfbizErrors.INVALID_PARAMETERS, "partyNode.nodeType must be a " + PartyNodeTypeOfbiz.INVITED_COMPANY.name());
                    }
                    invitedCompanyPartyId = (String) createPartyNode(invited).get("partyId");
                }*/
                companyStatusId = CompanyInvitationStateOfbiz.COMPANYINV_INVITED.name();
            } else {
                statusId = PartyInvitationStateOfbiz.PARTYINV_INVAGAIN.name();
                @SuppressWarnings("unchecked")
                List<Map<String, Object>> authList = (List<Map<String, Object>>) dctx.getDispatcher().runSync("gnInternalSearchAuthorizationAtRoot",
                        UtilMisc.toMap("userLogin", userLogin, "statusId", AuthorizationStatusOfbiz.GN_AUTH_PUBLISHED.name(),
                                "taxIdentificationNumbers", Arrays.asList(taxIdentificationNumber))
                ).get("list");

                if (authList.isEmpty()) {
                    companyStatusId = CompanyInvitationStateOfbiz.COMPANYINV_INACTIVE.name();
                } else {
                    companyStatusId = CompanyInvitationStateOfbiz.COMPANYINV_ACTIVE.name();
                }

                //try to get more accurated emails (users of invited company enabled to publish)
                Map<String, Object> srvRequest = FastMap.newInstance();
                srvRequest.put("userLogin", userLogin);
                srvRequest.put("partyNodeId", invitedCompanyPartyId);
                srvRequest.put("permissionId", PermissionsOfbiz.GN_AUTH_PUBLISH.name());
                @SuppressWarnings("unchecked")
                List<Map<String, Object>> ret = (List<Map<String, Object>>) dispatcher.runSync("gnFindContextWithUserLoginByPartyNodeAndPermission", srvRequest).get("contextList");
                if (ret.size() == 0) {
                    Debug.log("Has not been found any publication GnContext", module);
                } else {
                    for (Map<String, Object> gnContext : ret) {
                        String contextId = (String) gnContext.get("partyId");
                        Debug.log("Found publication GnContext[" + contextId + "]", module);
                        @SuppressWarnings("unchecked")
                        List<Map<String, Object>> users = (List<Map<String, Object>>) gnContext.get("users");

                        List<String> contextEmails = new ArrayList<String>(users.size());
                        for (Map<String, Object> user : users) {
                            @SuppressWarnings("unchecked")
                            Map<String, Object> contact = (Map<String, Object>) user.get("contact");
                            String emailAddress = (String) contact.get("emailAddress");
                            if (emailAddress != null && !emailAddress.isEmpty())
                                contextEmails.add(emailAddress);
                        }
                        if (!contextEmails.isEmpty()) {
                            emails.clear();
                            emails.addAll(contextEmails);
                        }
                    }
                }
                //invitedCompany that will be referenced by invitation, is created
                invited.put("nodeKey", null);
                invited.put("partyId", null);
                invitedCompanyPartyId = (String) createPartyNode(invited).get("partyId");
            }
            Debug.log("gnCreatePartyInvitation with status " + statusId);
            partyInvitation.put("statusId", statusId);

            //always override partyIdFrom
            @SuppressWarnings("unchecked")
            Map<String, Object> nodeMap = (Map<String, Object>) dctx.getDispatcher().runSync("gnFindCompanyWhereUserIsEmployed", UtilMisc.toMap("userLogin", userLogin, "userLoginPartyId", userLogin.get("partyId"))).get("partyNode");
            Object newPartyIdFrom = nodeMap.get("partyId");
            Debug.log("PartyIdFrom was [" + partyInvitation.get("partyIdFrom") + "], service used [" + newPartyIdFrom + "]");
            partyInvitation.put("partyIdFrom", newPartyIdFrom);
            String userLoginIdFrom = userLogin.getString("userLoginId");
            partyInvitation.put("userLoginIdFrom", userLoginIdFrom);
            result.put("inviterName", nodeMap.get("name"));
            result.put("userLoginIdFrom", userLoginIdFrom);

            //create party invitation
            Map<String, Object> srvContext = dispatcher.getDispatchContext().makeValidContext("createPartyInvitation", "IN", partyInvitation);

            if (UtilValidate.isEmpty(srvContext.get("lastInviteDate")))
                srvContext.put("lastInviteDate", UtilDateTime.nowTimestamp());

            srvContext.put("partyId", invitedCompanyPartyId);
            if (UtilValidate.isEmpty(srvContext.get("uuid"))) {
                String uuid = UUID.randomUUID().toString() + "@" + SysUtil.getInstanceId();
                srvContext.put("uuid", uuid);
                result.put("uuid", uuid);
            } else {
                result.put("uuid", context.get("uuid"));
            }
            Map<String, Object> srvResult = dispatcher.runSync("createPartyInvitation", srvContext);
            String partyInvitationId = (String) srvResult.get("partyInvitationId");

            //create email address
            if (UtilValidate.isNotEmpty(emails)) {
                for (String email : emails) {
                    Debug.log("invitation email: " + email);
                    if (!EMAIL_PATTERN.matcher(email).matches()) {
                        throw new GnServiceException(OfbizErrors.EMAIL_NOT_VALID, email + " isn't a valid email");
                    }
                    createPartyInvitationEmail(partyInvitationId, null, email);
                }
            }

            result.put("partyInvitationId", partyInvitationId);
            result.put("statusId", statusId);
            result.put("companyStatusId", companyStatusId);
            result.put("emails", emails);
            Debug.log("PartyInvitation[" + partyInvitationId + "] created.", module);

            //*** CREATE INVITATION (end) ***///
            makeFilterApertureAfterSendInvitation(taxIdentificationNumber, nodePartyIdFrom);

        }

        return result;
    }

    private void makeFilterApertureAfterSendInvitation(String taxIdentificationNumber, String nodePartyIdFrom) throws GenericEntityException, GenericServiceException {
        EntityCondition parentCondition = EntityConditionUtil.makeRelationshipCondition(nodePartyIdFrom, null, null, null, RelationshipTypeOfbiz.GN_RECEIVES_FROM.name(), true);
        List<GenericValue> parentRels = delegator.findList("GnPartyRelationship", parentCondition, null, null, null, false);
        GenericValue oldReceiveRel = parentRels.get(0);

        //*** manage filter aperture for this inviter node ***//
        if (oldReceiveRel != null) {
            Map<String, Object> params = new HashMap<String, Object>(3);
            params.put("partyRelationship", oldReceiveRel);
            params.put("taxIdentificationNumber", taxIdentificationNumber);
            params.put("userLogin", userLogin);
            Map<String, Object> srvRequest = dispatcher.runSync("gnFilterApertureManage", params);
        }

        List<EventMessage> messages = new ArrayList<EventMessage>();
        String senderPartyId = nodePartyIdFrom;
        String senderNodeKey = getNodeKey(senderPartyId, null);

        //*** propagate filter aperture to parent ***//
        if (oldReceiveRel != null) {
            String parentPartyId = (String) oldReceiveRel.get("partyIdTo");
            Map<String, Object> partyNode = UtilGenerics.checkMap(dispatcher.runSync("gnInternalFindPartyNodeById", UtilMisc.toMap("partyId", parentPartyId, "userLogin", userLogin, "findRelationships", "N")).get("partyNode"));
            EventMessageFilterAperture emParent = EventMessageFactory.make().makeEventMessageFilterAperture(
                    (String) partyNode.get("nodeKey"),
                    (String) partyNode.get("partyId"),
                    (String) partyNode.get("nodeType"),
                    EventMessageDirectionOfbiz.UPWARD,
                    senderNodeKey,
                    senderPartyId,
                    taxIdentificationNumber);
            messages.add(emParent);
        }

        //*** propagate filter aperture to children ***//
        EntityCondition childrenCondition = EntityConditionUtil.makeRelationshipCondition(nodePartyIdFrom, null, null, null, RelationshipTypeOfbiz.GN_PROPAGATES_TO.name(), true);
        List<GenericValue> childrenRels = delegator.findList("GnPartyRelationship", childrenCondition, null, null, null, false);
        for (GenericValue rel : childrenRels) {
            String childPartyId = rel.getString("partyIdTo");
            Map<String, Object> partyNode = UtilGenerics.checkMap(dispatcher.runSync("gnInternalFindPartyNodeById", UtilMisc.toMap("partyId", childPartyId, "userLogin", userLogin, "findRelationships", "N")).get("partyNode"));
            EventMessageFilterAperture emC = EventMessageFactory.make().makeEventMessageFilterAperture(
                    (String) partyNode.get("nodeKey"),
                    (String) partyNode.get("partyId"),
                    (String) partyNode.get("nodeType"),
                    EventMessageDirectionOfbiz.DOWNWARD,
                    senderNodeKey,
                    senderPartyId,
                    taxIdentificationNumber);
            messages.add(emC);
        }

        EventMessageContainer.addAll(messages);
    }

    /**
     * @param partyInvitationId
     * @param partyInvitationSeqId
     * @param emailAddress
     * @return
     * @throws GenericServiceException
     * @throws GenericEntityException
     */
    public Map<String, Object> createPartyInvitationEmail(String partyInvitationId, String partyInvitationSeqId, String emailAddress) throws GenericServiceException, GenericEntityException {
        GenericValue gv = delegator.makeValue("GnPartyInvitationEmail");
        gv.set("partyInvitationId", partyInvitationId);
        if (UtilValidate.isEmpty(partyInvitationSeqId)) {
            delegator.setNextSubSeqId(gv, "partyInvitationSeqId", 5, 1);
        } else {
            gv.set("partyInvitationSeqId", partyInvitationSeqId);
        }
        gv.set("emailAddress", emailAddress);
        delegator.create(gv);
        return UtilMisc.toMap("partyInvitationId", (Object) partyInvitationId, "partyInvitationSeqId", partyInvitationSeqId);
    }

    public List<GenericValue> findPartyInvitationEmails(String partyInvitationId) throws GenericEntityException {
        return delegator.findByAnd("GnPartyInvitationEmail", UtilMisc.toMap("partyInvitationId", partyInvitationId), UtilMisc.toList("partyInvitationSeqId"));
    }

    /**
     * Load invitation and related entity
     *
     * @param partyInvitationId
     * @param uuid
     * @return partyInvitation
     * @throws GenericServiceException
     * @throws GenericEntityException
     */
    public Map<String, Object> gnFindInvitationById(String partyInvitationId, String uuid) throws GenericServiceException, GenericEntityException {
        GenericValue invitation = findAndValidateInvitationById(partyInvitationId, uuid);
        Map<String, Object> result = UtilMisc.makeMapWritable(invitation);
        loadRelatedEntity(result);
        return result;
    }

    /**
     * Find invitation by id
     * and updates its state iof it is expired
     *
     * @param partyInvitationId
     * @param uuid
     * @return invitation GenericValue
     * @throws GenericEntityException
     * @throws GenericServiceException
     */
    private GenericValue findAndValidateInvitationById(String partyInvitationId, String uuid) throws GenericEntityException, GenericServiceException {
        GenericValue invitation = findInvitationById(partyInvitationId, uuid);

        Timestamp expirationDate = (Timestamp) invitation.get("expirationDate");
        if (!PartyInvitationStateOfbiz.PARTYINV_CANCELLED.toString().equals(invitation.get("statusId"))
                && UtilValidate.isNotEmpty(expirationDate) && expirationDate.before(UtilDateTime.nowTimestamp())) {
            invitation.set("statusId", PartyInvitationStateOfbiz.PARTYINV_CANCELLED.toString());
            Debug.log("PartyInvitation[" + invitation.get("partyInvitationId") + "] is expired.", module);
            //Debug.log("Updated PartyInvitation[" + invitation.get("partyInvitationId") + "] state[" + PartyInvitationStateOfbiz.PARTYINV_CANCELLED.toString() + "].", module);
            //delegator.store(invitation);
        }
        return invitation;
    }

    /**
     * Find invitation by id
     * and updates its state iof it is expired
     *
     * @param partyInvitationId
     * @param uuid
     * @return invitation GenericValue
     * @throws GenericEntityException
     * @throws GenericServiceException
     */
    public GenericValue findInvitationById(String partyInvitationId, String uuid) throws GenericEntityException, GenericServiceException {
        GenericValue invitation;
        if (UtilValidate.isEmpty(partyInvitationId) && UtilValidate.isEmpty(uuid))
            throw new GnServiceException(OfbizErrors.INVALID_PARAMETERS, "partyInvitationId and uuid cannot be null both.");
        if (UtilValidate.isEmpty(partyInvitationId)) {
            List<GenericValue> invitations = delegator.findByAnd("PartyInvitation", UtilMisc.toMap("uuid", uuid));
            if (invitations.size() == 0)
                throw new GnServiceException(OfbizErrors.INVITATION_NOT_FOUND, "PartyInvitation not found with uuid[" + uuid + "].");
            if (invitations.size() > 1)
                throw new GnServiceException(OfbizErrors.ENTITY_EXCEPTION, "More than one PartyInvitation found with uuid[" + uuid + "].");
            invitation = invitations.get(0);
        } else {
            invitation = delegator.findOne("PartyInvitation", UtilMisc.toMap("partyInvitationId", partyInvitationId), false);
        }
        return invitation;
    }

    protected void loadRelatedEntity(Map<String, Object> invitation) throws GenericServiceException, GenericEntityException {
        //load inviter
        String partyIdFrom = (String) invitation.get("partyIdFrom");
        invitation.put("partyNodeFrom", findPartyNodeById(partyIdFrom));
        //load invited
        String partyId = (String) invitation.get("partyId");
        invitation.put("partyNode", findPartyNodeById(partyId));
        //load email address
        String partyInvitationId = (String) invitation.get("partyInvitationId");
        List<GenericValue> emails = findPartyInvitationEmails(partyInvitationId);
        if (emails.isEmpty()) {
            invitation.put("emails", new ArrayList<String>());
        } else {
            for (GenericValue email : emails) {
                UtilMisc.addToListInMap(email.getString("emailAddress"), invitation, "emails");
            }
        }
    }

    /**
     * Modify invitation state
     *
     * @param partyInvitationId
     * @param uuid
     * @param statusId          {@link PartyInvitationStateOfbiz}
     * @throws GenericServiceException
     * @throws GenericEntityException
     */
    public void updateInvitationState(String partyInvitationId, String uuid, String statusId) throws GenericServiceException, GenericEntityException {
        updateInvitationState(partyInvitationId, uuid, statusId, null);
    }

    /**
     * @param partyInvitationId
     * @param uuid
     * @param statusId
     * @param expirationDate    is not mandatory
     * @throws GenericServiceException
     * @throws GenericEntityException
     */
    public void updateInvitationState(String partyInvitationId, String uuid, String statusId, Timestamp expirationDate) throws GenericServiceException, GenericEntityException {
        GenericValue invitation = findInvitationById(partyInvitationId, uuid);
        if (UtilValidate.isEmpty(invitation))
            throw new GnServiceException(OfbizErrors.ENTITY_EXCEPTION, "PartyInvitation not found. id[" + partyInvitationId + "],uuid[" + uuid + "]");
        invitation.set("statusId", statusId);
        if (UtilValidate.isNotEmpty(expirationDate)) {
            invitation.put("expirationDate", expirationDate);
        }
        Debug.log("Updated PartyInvitation[" + invitation.get("partyInvitationId") + "] state[" + statusId + "].", module);

        if (PartyInvitationStateOfbiz.PARTYINV_ACCEPTED.name().equals(statusId)) {
            invitation.put("acceptanceDate", UtilDateTime.nowTimestamp());
        }

        delegator.store(invitation);

        if (PartyInvitationStateOfbiz.PARTYINV_SENT.name().equals(statusId)) {
            Map<String, Object> _invitation = UtilMisc.makeMapWritable(invitation);
            loadRelatedEntity(_invitation);
            new InvitationJobHelper(getDctx(), getContext()).scheduleInvitationExpirationJobs(_invitation);
        } else {//disable every job
            new InvitationJobHelper(getDctx(), getContext()).disableJob(invitation.getString("partyInvitationId"));
        }

    }

    public List<Map<String, Object>> gnFindInvitation(String statusId, String invitedNameContent, String companyInvitationState) throws GenericServiceException, GenericEntityException {
        //Only accepted invitations can be filtered by InactiveCompany
        boolean filterInactive = CompanyInvitationStateOfbiz.COMPANYINV_INACTIVE.toString().equals(companyInvitationState);
        boolean filterActive = CompanyInvitationStateOfbiz.COMPANYINV_ACTIVE.toString().equals(companyInvitationState);
        if (!PartyInvitationStateOfbiz.PARTYINV_ACCEPTED.toString().equals(statusId) && (filterInactive || filterActive)) {
            return FastList.newInstance();
        }

        List<EntityCondition> conds = FastList.newInstance();
        if (UtilValidate.isNotEmpty(statusId)) {
            conds.add(EntityCondition.makeCondition("statusId", statusId));
        }
        String nodePartyIdFrom = (String) (UtilGenerics.checkMap(getCurrentContext().get("partyNode"))).get("partyId");
        conds.add(EntityCondition.makeCondition("nodePartyIdFrom", nodePartyIdFrom));

        List<GenericValue> invitations;
        if (UtilValidate.isNotEmpty(invitedNameContent)) {

            DynamicViewEntity view = new DynamicViewEntity();
            view.addMemberEntity("INV", "PartyInvitation");
            view.addMemberEntity("PA", "GnPartyNode");
            view.addViewLink("INV", "PA", false, UtilMisc.toList(new ModelKeyMap("partyId", "partyId")));
            view.addAliasAll("INV", null);
            view.addAlias("INV", "createdStamp");
            view.addAlias("INV", "createdTxStamp");
            view.addAlias("INV", "lastUpdatedStamp");
            view.addAlias("INV", "lastUpdatedTxStamp");
            view.addAlias("PA", "name");
            view.addAlias("PA", "taxIdentificationNumber");

            conds.add(EntityCondition.makeCondition(EntityFunction.UPPER_FIELD("name"), EntityOperator.LIKE, EntityFunction.UPPER("%" + invitedNameContent + "%")));

            EntityCondition cond = EntityCondition.makeCondition(conds);
            invitations = delegator.findListIteratorByCondition(view, cond, null, null, UtilMisc.toList("lastInviteDate DESC"), null).getCompleteList();

        } else {
            DynamicViewEntity view = new DynamicViewEntity();
            view.addMemberEntity("INV", "PartyInvitation");
            view.addMemberEntity("PA", "GnPartyNode");
            view.addViewLink("INV", "PA", false, UtilMisc.toList(new ModelKeyMap("partyId", "partyId")));
            view.addAliasAll("INV", null);
            view.addAlias("PA", "taxIdentificationNumber");
            EntityCondition cond = EntityCondition.makeCondition(conds);
            //invitations = delegator.findList("PartyInvitation", cond, null, UtilMisc.toList("lastInviteDate DESC"), null, false);
            invitations = delegator.findListIteratorByCondition(view, cond, null, null, UtilMisc.toList("lastInviteDate DESC"), null).getCompleteList();

        }


        //load linked entity
        List<Map<String, Object>> result = FastList.newInstance();
        Set<String> taxIdentificationsNumbers = new HashSet<String>();
        for (GenericValue gv : invitations) {
            Map<String, Object> invitation = UtilMisc.makeMapWritable(gv);

            if (PartyInvitationStateOfbiz.PARTYINV_ACCEPTED.toString().equals(statusId) &&
                    (filterInactive || filterActive)) {
                @SuppressWarnings("unchecked")
                List<Map<String, Object>> authList = (List<Map<String, Object>>) dctx.getDispatcher().runSync("gnInternalSearchAuthorizationAtRoot",
                        UtilMisc.toMap("userLogin", userLogin, "statusId", AuthorizationStatusOfbiz.GN_AUTH_PUBLISHED.name(),
                                "taxIdentificationNumbers", Arrays.asList(gv.getString("taxIdentificationNumber")))
                ).get("list");

                if ((authList.size() > 0 && filterInactive) || (authList.size() == 0 && filterActive)) {
                    //skip company: make sure that only active or inactive company are returned
                    continue;
                } else {
                    //skip company: make sure that one company for TaxIdentificationNumber is returned
                    //true if this set did not already contain the specified element
                    if (!taxIdentificationsNumbers.add(gv.getString("taxIdentificationNumber"))) {
                        continue;
                    }
                }
            }

            loadRelatedEntity(invitation);

            //Attention! This code is forced in order to give out only InvitedCompany as invited
            if (UtilGenerics.checkMap(invitation.get("partyNode")).get("nodeType").equals(PartyNodeTypeOfbiz.COMPANY.name()))
                UtilGenerics.checkMap(invitation.get("partyNode")).put("nodeType", PartyNodeTypeOfbiz.INVITED_COMPANY.name());

            result.add(invitation);
        }
        return result;
    }

    public Map<String, Object> gnAcceptPartyInvitation(String uuid, String companyName, Map<String, Object> companyBase, Map<String, Object> invitedUserLogin, String password) throws GenericServiceException, GenericEntityException {
        Debug.log("Accept PartyInvitation.", module);
        Map<String, Object> invitation = gnFindInvitationById(null, uuid);

        checkValidity(uuid, invitation);

        @SuppressWarnings("unchecked")
        Map<String, Object> invitedCompany = (Map<String, Object>) invitation.get("partyNode");
        String VATnumber = (String) invitedCompany.get("VATnumber");
        String taxIdentificationNumber = (String) invitedCompany.get("taxIdentificationNumber");
        if (UtilValidate.isEmpty(VATnumber) || UtilValidate.isEmpty(taxIdentificationNumber) || UtilValidate.isEmpty(companyName)) {
            throw new GnServiceException(OfbizErrors.MISSING_DATA, "Missing VATnumber, taxIdentificationNumber or companyName");
        }
        String companyId = findCompany(VATnumber, taxIdentificationNumber);
        if (UtilValidate.isEmpty(companyId)) {
            invitedCompany.remove("partyId");
            invitedCompany.remove("nodeKey");
            invitedCompany.put("groupName", companyName);
            invitedCompany.put("description", companyName);
            invitedCompany.put("name", companyName);
            invitedCompany.put("nodeType", PartyNodeTypeOfbiz.COMPANY.name());
            companyId = (String) createPartyNode(invitedCompany).get("partyId");
            Debug.log("Created company PartyNode[" + companyId + "]", module);
        } else {
            Debug.logError("Company found: PartyNode[" + companyId + "]", module);
            throw new GnServiceException(OfbizErrors.COMPANY_ALREADY_EXISTS, "The Company with VATnumber " + VATnumber + " and taxIdentificationNumber " + taxIdentificationNumber + " already exists");
        }

        //sreate partyAttribute (profile)
        Map<String, Object> crtPrtyAttr1 = dispatcher.runSync("createPartyAttribute", UtilMisc.toMap("userLogin", userLogin, "partyId", companyId, "attrName", CompanyAttributeOfbiz.ROOT_PUBLISH.name(), "attrValue", "Y"));
        Map<String, Object> crtPrtyAttr2 = dispatcher.runSync("createPartyAttribute", UtilMisc.toMap("userLogin", userLogin, "partyId", companyId, "attrName", CompanyAttributeOfbiz.PRIVATE_AUTH_PUBLISH.name(), "attrValue", "N"));
        List<GenericValue> partyAttribute = delegator.findByAnd("PartyAttribute", FastMap.newInstance());
        partyAttribute.size();

        //create relationship
        Map<String, Object> createRelatioshipParams = FastMap.newInstance();

        //createRelationshipParams.put("partyIdFrom", companyId);
        createRelatioshipParams.put("partyIdTo", companyId);
        createRelatioshipParams.put("partyRelationshipTypeId", RelationshipTypeOfbiz.GN_BELONGS_TO.name());
        createRelatioshipParams.put("relationshipName", "REL_" + System.currentTimeMillis());
        companyBase.put("partyRelationships", UtilMisc.toList(createRelatioshipParams));

        companyBase.put("registeredOffice", "Y");
        companyBase.put("authorizationOwner", "Y");

        String companyBaseId = null;
        try {
            companyBaseId = (String) createPartyNode(companyBase).get("partyId");
        } catch (GenericServiceException gse) {
            throw new GnServiceException(OfbizErrors.INVITATION_CREATE_CONTACT_ERROR.ordinal(), gse);
        }
        Debug.log("Created companyBase PartyNode[" + companyBaseId + "]", module);

        //create Person
        String userLoginId = (String) invitedUserLogin.get("userLoginId");

        //The invited userLoginId is the email of the user
        if (!EMAIL_PATTERN.matcher(userLoginId).matches()) {
            throw new GnServiceException(OfbizErrors.EMAIL_NOT_VALID, "Email not valid");
        }

        Map<String, Object> ul = createPersonAndUserLoginInvited(userLoginId, password, (String) invitedUserLogin.get("firstName"), (String) invitedUserLogin.get("lastName"), "N");
        String userLoginPartyId = (String) ul.get("partyId");
        Debug.log("Created Person[" + userLoginPartyId + "]", module);
        Debug.log("Created UserLogin[" + userLoginId + "]", module);
        createPartyRole(userLoginPartyId, "GN_USER");
        // BIZADMIN
        addUserLoginToSecurityGroup("BIZADMIN", userLoginId);

        //create relationship
        Map<String, Object> createEmplRelatioshipParams = FastMap.newInstance();

        //createRelationshipParams.put("partyIdFrom", companyId);
        createEmplRelatioshipParams.put("partyIdTo", companyId);
        createEmplRelatioshipParams.put("roleTypeIdTo", PartyRoleOfbiz.GN_COMPANY.name());
        createEmplRelatioshipParams.put("partyIdFrom", userLoginPartyId);
        createEmplRelatioshipParams.put("roleTypeIdFrom", PartyRoleOfbiz.GN_USER.name());
        createEmplRelatioshipParams.put("partyRelationshipTypeId", RelationshipTypeOfbiz.EMPLOYMENT.name());
        createEmplRelatioshipParams.put("relationshipName", "REL_" + System.currentTimeMillis());
        createEmplRelatioshipParams.put("createReverse", "N");
        createPartyRelationship(createEmplRelatioshipParams);

        //create contact
        @SuppressWarnings("unchecked")
        Map<String, Object> contact = (Map<String, Object>) invitedUserLogin.get("contact");
        contact.put("partyId", userLoginPartyId);
        gnCreateUpdatePartyContact(contact);

        String companyContextDescription = UtilProperties.getMessage(resource, "InvitationHelper.companyContextDescription", Locale.ITALY);
        Map<String, Object> companyContext = FastMap.newInstance();
        companyContext.put("description", companyContextDescription);
        companyContext.put("companyBaseIds", UtilMisc.toList(companyBaseId));
        companyContext.put("userLoginIds", UtilMisc.toList(userLoginId));
        companyContext.put("securityGroupIds", UtilMisc.toList("GN_AAZIEN" /*COMPANY_ADMIN*/, "GN_UOSEDE" /*PUBLISH,DRAFT,READ authorization*/, "GN_USTORI" /*ARCHIVE*/));
        companyContext.put("partyNodeId", companyId);

        String companyContextId = (String) gnCreateUpdateContext(companyContext).get("partyId");
        Debug.log("Created GnContext PartyNode[" + companyContextId + "]", module);

        //create relationship
        Map<String, Object> createPublishRelatioshipParams = FastMap.newInstance();

        //createRelationshipParams.put("partyIdFrom", companyId);
        createPublishRelatioshipParams.put("partyIdFrom", companyId);
        createPublishRelatioshipParams.put("roleTypeIdTo", PartyRoleOfbiz.GN_ROOT.name());
        createPublishRelatioshipParams.put("partyIdTo", "GN_ROOT");
        createPublishRelatioshipParams.put("roleTypeIdFrom", PartyRoleOfbiz.GN_COMPANY.name());
        createPublishRelatioshipParams.put("partyRelationshipTypeId", RelationshipTypeOfbiz.GN_PUBLISHES_TO_ROOT.name());
        createPublishRelatioshipParams.put("relationshipName", "PUBLISH_TO_ROOT_REL_" + System.currentTimeMillis());
        createPublishRelatioshipParams.put("createReverse", "N");
        createPartyRelationship(createPublishRelatioshipParams);

        updateInvitationState((String) invitation.get("partyInvitationId"), null, PartyInvitationStateOfbiz.PARTYINV_ACCEPTED.name());
        cancelPendingPartyInvitation(taxIdentificationNumber);

        Map<String, Object> result = FastMap.newInstance();
        result.put("companyContextId", companyContextId);
        result.put("companyId", companyId);

        Map<String, Object> companyBaseMap = dispatcher.runSync("gnInternalFindPartyNodeById", UtilMisc.toMap("userLogin", userLogin, "findRelationships", "N", "partyId", companyBaseId));
        result.put("companyBase", companyBaseMap.get("partyNode"));

        Map<String, Object> commEventParams = FastMap.newInstance();
        commEventParams.put("partyId", companyId);
        commEventParams.put("userLoginIds", Arrays.asList(userLoginId));
        commEventParams.put("contactListName", "Gruppo di notifica importazione autorizzazioni");
        commEventParams.put("communicationEventTypeIds", Arrays.asList(CommunicationEventTypeOfbiz.EMAIL_COMMUNICATION.name(), CommunicationEventTypeOfbiz.WEB_SITE_COMMUNICATI.name()));
        commEventParams.put("eventTypeIds", Arrays.asList(EventTypeOfbiz.GN_COM_EV_AUTH_IMP.name()));
        commEventParams.put("userLogin", userLogin);
        Map<String, Object> contactList = dispatcher.runSync("gnCreateUpdateContactList", commEventParams);
        Debug.log("Created contact list with contactListId[" + contactList.get("contactListId") + "]");

        Map<String, Object> commEventParams2 = FastMap.newInstance();
        commEventParams2.put("partyId", companyId);
        commEventParams2.put("userLoginIds", Arrays.asList(userLoginId));
        commEventParams2.put("contactListName", "Gruppo di notifica scadenza autorizzazioni");
        commEventParams2.put("communicationEventTypeIds", Arrays.asList(CommunicationEventTypeOfbiz.EMAIL_COMMUNICATION.name()));
        commEventParams2.put("eventTypeIds", Arrays.asList(EventTypeOfbiz.GN_COM_EV_AUTH_EXPIR.name()));
        commEventParams2.put("userLogin", userLogin);
        Map<String, Object> contactList2 = dispatcher.runSync("gnCreateUpdateContactList", commEventParams2);
        Debug.log("Created contact list with contactListId[" + contactList2.get("contactListId") + "]");


        new InvitationJobHelper(getDctx(), getContext()).scheduleAuthorizationPublicationReminderJob(companyId, userLoginId);
        return result;
    }

    /**
     * Cancel all pending partyInvitation to company with given taxIdentificationNumber
     *
     * @param taxIdentificationNumber of invited company
     */
    private void cancelPendingPartyInvitation(String taxIdentificationNumber) throws GenericEntityException, GenericServiceException {
        /*List<GenericValue> pendingPartyInvitations = delegator.findByAnd("GnPartyInvitationAndCompany",
                UtilMisc.toMap(, "statusId", PartyInvitationStateOfbiz.PARTYINV_PENDING.name()));*/
        int days = Integer.parseInt(PropertyUtil.getProperty(PropertyEnumOfbiz.PARTY_INVITATION_EXPIRATION, getDispatcher(), getContext()));


        List<EntityCondition> conds = new ArrayList<EntityCondition>();
        conds.add(EntityCondition.makeCondition("INVITED_TaxIdentificationNumber", taxIdentificationNumber));
        conds.add(GnFindUtil.makeOrConditionById("statusId", Arrays.asList(PartyInvitationStateOfbiz.PARTYINV_PENDING.name(), PartyInvitationStateOfbiz.PARTYINV_SENT.name())));
        List<GenericValue> pendingPartyInvitations = delegator.findList("GnPartyInvitationAndCompany",
                EntityCondition.makeCondition(conds), null, null, null, false);
        for (GenericValue inv : pendingPartyInvitations) {
            Date lastInviteDate = inv.getTimestamp("lastInviteDate");
            Calendar cal = Calendar.getInstance();
            cal.setTime(lastInviteDate);
            cal.add(Calendar.DAY_OF_MONTH, days);
            Timestamp expirationDate = UtilDateTime.getDayStart(new Timestamp(cal.getTimeInMillis()));
            //if calculated expiration date is after current, it doesn't update
            if (expirationDate.after(inv.getTimestamp("expirationDate")))
                expirationDate = inv.getTimestamp("expirationDate");
            updateInvitationState(inv.getString("partyInvitationId"), inv.getString("uuid"), PartyInvitationStateOfbiz.PARTYINV_ACCEPTED.name(), expirationDate);
        }
    }

    private void checkValidity(String uuid, Map<String, Object> invitation) throws GnServiceException {
        if (UtilValidate.isEmpty(invitation))
            throw new GnServiceException(OfbizErrors.INVITATION_NOT_FOUND, "PartyInvitation[" + uuid + "] not found.");
        if (PartyInvitationStateOfbiz.PARTYINV_ACCEPTED.name().equals(invitation.get("statusId")))
            throw new GnServiceException(OfbizErrors.INVITATION_ALREADY_ACCEPTED, "PartyInvitation[" + uuid + "] is already accepted.");
        Timestamp expirationDate = (Timestamp) invitation.get("expirationDate");
        if (PartyInvitationStateOfbiz.PARTYINV_CANCELLED.name().equals(invitation.get("statusId")) || UtilValidate.isNotEmpty(expirationDate) && expirationDate.before(UtilDateTime.nowTimestamp())) {
            throw new GnServiceException(OfbizErrors.INVITATION_CANCELLED, "Invitation[" + invitation.get("partyInvitationId") + "] is cancelled or expired.");
        }
    }

    public String findCompany(String VATnumber, String taxIdentificationNumber) throws GenericServiceException, GenericEntityException {
        List<EntityCondition> conds = FastList.newInstance();
        conds.add(EntityCondition.makeCondition(UtilMisc.toList(EntityCondition.makeCondition("VATnumber", VATnumber), EntityCondition.makeCondition("taxIdentificationNumber", taxIdentificationNumber)), EntityOperator.OR));
        conds.add(EntityCondition.makeCondition("nodeType", PartyNodeTypeOfbiz.COMPANY.name()));
        List<GenericValue> result = delegator.findList("GnCompany", EntityCondition.makeCondition(conds), UtilMisc.toSet("partyId"), null, null, false);

        if (result.size() == 1)
            return result.get(0).getString("partyId");
        else if (result.size() > 1)
            throw new GnServiceException(OfbizErrors.ENTITY_EXCEPTION, "More than one GnCompany found with VATnumber[" + VATnumber + "] or taxIdentificationNumber[" + taxIdentificationNumber + "]");
        return null;
    }

    /**
     * @param userLoginPartyId
     * @param roleId
     * @return
     * @throws GenericServiceException
     */
    private Map<String, Object> createPartyRole(String userLoginPartyId, String roleId) throws GenericServiceException {

        return dispatcher.runSync("createPartyRole", UtilMisc.toMap("userLogin", userLogin, "roleTypeId", roleId, "partyId", userLoginPartyId));
    }

    /**
     * Find party by primary key
     *
     * @param partyNodeId
     * @return
     * @throws GenericServiceException
     */
    @SuppressWarnings("unchecked")
    public Map<String, Object> findPartyNodeById(String partyNodeId) throws GenericServiceException {
        return (Map<String, Object>) dispatcher.runSync("gnInternalFindPartyNodeById", UtilMisc.toMap("partyId", partyNodeId, "userLogin", userLogin, "findRelationships", "N")).get("partyNode");
    }

    /**
     * @param partyNode
     * @return nodeKey and partyId
     * @throws GenericServiceException
     */
    public Map<String, Object> createPartyNode(Map<String, Object> partyNode) throws GenericServiceException {
        Map<String, Object> srvRequest = dispatcher.getDispatchContext().makeValidContext("gnCreatePartyNode", "IN", partyNode);
        srvRequest.put("userLogin", userLogin);
        return dispatcher.runSync("gnInternalCreatePartyNode", srvRequest);

    }

    /**
     * @param userLoginId
     * @param currentPassword
     * @param firstName
     * @param lastName
     * @return
     * @throws GenericServiceException
     */
    public Map<String, Object> createPersonAndUserLoginInvited(String userLoginId, String currentPassword, String firstName, String lastName, String enabled) throws GenericServiceException, GenericEntityException {
        Map<String, Object> srvRequest = UtilMisc.toMap("userLoginId", (Object) userLoginId, "currentPassword", currentPassword, "currentPasswordVerify", currentPassword, "firstName", firstName, "lastName", lastName, "enabled", enabled);
        srvRequest.put("userLogin", userLogin);

        GenericValue userLogin = null;
        try {
            userLogin = delegator.findOne("UserLogin", UtilMisc.toMap("userLoginId", userLoginId), false);
        } catch (GenericEntityException e) {
            throw new GnServiceException(OfbizErrors.ENTITY_EXCEPTION, e.getMessage());
        }
        if (userLogin != null) {
            throw new GnServiceException(OfbizErrors.USERNAME_NOT_UNIQUE, "Username '" + userLoginId + "' already exists.");
        }

        Map<String, Object> result = dispatcher.runSync("createPersonAndUserLogin", srvRequest);


        GenericValue userLogin2 = delegator.findOne("UserLogin", false, "userLoginId", userLoginId);
        GnAuthenticator.updateSaltedPassword(userLogin2, currentPassword, currentPassword, currentPassword, delegator);

        dispatcher.runSync("gnUpdateUserLoginOriginId", UtilMisc.toMap("userLogin", userLogin, "userLoginId", userLoginId, "originId", AuthorizationOriginOfbiz.GN_AUTH_ORG_HUMAN.name()));
        dispatcher.runSync("gnAddUserLoginLoginSourceId", UtilMisc.toMap("userLogin", userLogin, "userLoginId", userLoginId, "loginSourceId", LoginSourceOfbiz.GN_LOG_SRC_WEB_UI.name()));
        return result;
    }

    /**
     * private String description;
     * private String phoneNumber;
     * private String mobileNumber;
     * private String faxNumber;
     * private String email;
     * private String webPage;
     * private String note;
     * <p/>
     * contact.put("phoneNumber", phoneNumber);
     * contact.put("mobileNumber", mobileNumber);
     * contact.put("faxNumber", faxNumber);
     * contact.put("emailAddress", emailAddress);
     * contact.put("webPage", webPage);
     * contact.put("note", note);
     * contact.put("description", description);
     *
     * @return
     * @throws GenericServiceException
     */
    public Map<String, Object> gnCreateUpdatePartyContact(Map<String, Object> contact) throws GenericServiceException {
        Map<String, Object> srvRequest = dispatcher.getDispatchContext().makeValidContext("gnCreateUpdatePartyContact", "IN", contact);
        srvRequest.put("userLogin", userLogin);
        return getDispatcher().runSync("gnCreateUpdatePartyContact", srvRequest);
    }

    public Map<String, Object> gnCreateUpdateContext(Map<String, Object> gnContext) throws GenericServiceException {
        Map<String, Object> srvRequest = dispatcher.getDispatchContext().makeValidContext("gnCreateUpdateContext", "IN", gnContext);
        srvRequest.put("userLogin", userLogin);
        return dispatcher.runSync("gnCreateUpdateContext", srvRequest);
    }

    public Map<String, Object> createPartyRelationship(Map<String, Object> gnContext) throws GenericServiceException {
        Map<String, Object> srvRequest = dispatcher.getDispatchContext().makeValidContext("createPartyRelationship", "IN", gnContext);
        srvRequest.put("userLogin", userLogin);
        return dispatcher.runSync("createPartyRelationship", srvRequest);
    }

    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> gnInvitationFindCountries(String uuid) throws GenericEntityException, GenericServiceException {
        GenericValue invitation = findAndValidateInvitationById(null, uuid);
        checkValidity(uuid, invitation);
        Map<String, Object> result = dispatcher.runSync("gnFindCountries", UtilMisc.toMap("userLogin", userLogin));
        return (List<Map<String, Object>>) result.get("countries");
    }

    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> addUserLoginToSecurityGroup(String groupId, String userLoginId) throws GenericEntityException, GenericServiceException {
        Map<String, Object> result = dispatcher.runSync("gnAddUserLoginToSecurityGroup", UtilMisc.toMap("userLogin", userLogin, "groupId", groupId, "userLoginId", userLoginId));
        return (List<Map<String, Object>>) result.get("countries");
    }

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
            return (String) dctx.getDispatcher().runSync("gnGetPartyIdFromNodeKey", UtilMisc.toMap("userLogin", userLogin, "nodeKey", nodeKey)).get("partyId");
        else
            return partyId;
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
            return (String) dctx.getDispatcher().runSync("gnGetNodeKeyFromPartyId", UtilMisc.toMap("userLogin", userLogin, "partyId", partyId)).get("nodeKey");
        else return nodeKey;
    }


    public void createUpdateDefaultMessage(String description, String fromAddress, String ccAddress, String bccAddress, String subject, String contentType) throws GenericEntityException {
        GenericValue gv = delegator.makeValue("GnMessageTemplateSetting");
        gv.setNextSeqId();
        gv.set("description", description);
        gv.set("fromAddress", fromAddress);
        gv.set("ccAddress", ccAddress);
        gv.set("bccAddress", bccAddress);
        gv.set("subject", subject);
        gv.set("very-long", subject);
        gv.set("contentType", contentType);
        delegator.store(gv);
    }

}
