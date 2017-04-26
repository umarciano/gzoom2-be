package it.memelabs.gn.services.node;

import it.memelabs.gn.services.OfbizErrors;
import it.memelabs.gn.services.communication.CommunicationEventAttributeKeyOfbiz;
import it.memelabs.gn.services.communication.CommunicationEventTypeOfbiz;
import it.memelabs.gn.services.communication.EventTypeOfbiz;
import it.memelabs.gn.services.job.AbstractJobHelper;
import it.memelabs.gn.services.job.JobAttribute;
import it.memelabs.gn.services.job.JobStatusOfbiz;
import it.memelabs.gn.services.job.JobTypeOfbiz;
import it.memelabs.gn.services.system.PropertyEnumOfbiz;
import it.memelabs.gn.util.EntityConditionUtil;
import it.memelabs.gn.util.GnServiceException;
import it.memelabs.gn.util.PropertyUtil;
import javolution.util.FastMap;
import org.ofbiz.base.util.*;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.entity.condition.EntityComparisonOperator;
import org.ofbiz.entity.condition.EntityCondition;
import org.ofbiz.entity.model.DynamicViewEntity;
import org.ofbiz.entity.model.ModelKeyMap;
import org.ofbiz.entity.util.EntityListIterator;
import org.ofbiz.service.DispatchContext;
import org.ofbiz.service.GenericServiceException;

import java.sql.Timestamp;
import java.util.*;

/**
 * 21/05/2014
 *
 * @author Andrea Fossi
 */
public class InvitationJobHelper extends AbstractJobHelper {
    private static String module = InvitationJobHelper.class.getSimpleName();

    public InvitationJobHelper(DispatchContext dctx, Map<String, ? extends Object> context) {
        super(dctx, context);
    }

    /**
     * @param partyInvitation
     * @return
     * @throws GenericServiceException
     * @throws GenericEntityException
     */
    public int scheduleInvitationExpirationJobs(Map<String, Object> partyInvitation) throws GenericServiceException, GenericEntityException {
        int periodicNotification = Integer.parseInt(PropertyUtil.getProperty(PropertyEnumOfbiz.PARTY_INVITATION_EXPIRATION_NOTIFICATION_PERIODIC_INTERVAL, getDispatcher(), getContext()));
        int lastNotificationDays = Integer.parseInt(PropertyUtil.getProperty(PropertyEnumOfbiz.PARTY_INVITATION_EXPIRATION_NOTIFICATION_LAST_COMMUNICATION, getDispatcher(), getContext()));

        if (periodicNotification <= 0)
            throw new GnServiceException(OfbizErrors.INVALID_PARAMETERS, "periodicNotification cannot be <=0");
        if (lastNotificationDays <= 0)
            throw new GnServiceException(OfbizErrors.INVALID_PARAMETERS, "lastNotificationDays cannot be <=0");

        String inviterName = (String) UtilMisc.getMapFromMap(partyInvitation, "partyNodeFrom").get("name");
        String invitedName = (String) UtilMisc.getMapFromMap(partyInvitation, "partyNode").get("name");
        String uuid = (String) partyInvitation.get("uuid");
        String partyInvitationId = (String) partyInvitation.get("partyInvitationId");
        String userLoginIdFrom = (String) partyInvitation.get("userLoginIdFrom");
        Timestamp expirationDate = UtilDateTime.getDayStart((Timestamp) partyInvitation.get("expirationDate"));
        List<String> emails = UtilMisc.getListFromMap(partyInvitation, "emails");

        scheduleMarkExpiredJob(partyInvitationId, userLoginIdFrom, invitedName, expirationDate);
        scheduleNotificationExpirationJob(partyInvitationId, uuid, inviterName, 3, expirationDate, 0, emails);//same day of invite expiration
        scheduleNotificationExpirationJob(partyInvitationId, uuid, inviterName, 2, expirationDate, lastNotificationDays, emails);// n day before expiration

        int invValidityInterval = getInterval(expirationDate);
        //periodic
        int days = periodicNotification;
        //  do this before     notification with type 2
        while (days < (invValidityInterval - lastNotificationDays)) {
            scheduleNotificationExpirationJob(partyInvitationId, uuid, inviterName, 1, expirationDate, days, emails);//  day before expiration
            days += periodicNotification;
        }

        return 0;
    }

    private int getInterval(Timestamp expiration) {
        long diffMs = expiration.getTime() - UtilDateTime.getDayStart(UtilDateTime.nowTimestamp()).getTime();
        long diffDays = diffMs / (24 * 60 * 60 * 1000);
        return (int) diffDays;
    }


    /**
     * Proxy method to create/update authorization
     *
     * @param ownerPartyId
     * @param eventTypeId
     * @return
     */
    public List<Map<String, Object>> gnFindContactList(String ownerPartyId, EventTypeOfbiz eventTypeId) throws GenericServiceException {
        List<String> ownerPartyIds = new ArrayList<String>();
        ownerPartyIds.add(ownerPartyId);
        List<String> eventTypeIds = new ArrayList<String>();
        eventTypeIds.add(eventTypeId.name());
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("userLogin", userLogin);
        params.put("ownerPartyIds", ownerPartyIds);
        params.put("eventTypeIds", eventTypeIds);
        Map<String, Object> result = dispatcher.runSync("gnFindContactList", params);
        return UtilMisc.getListFromMap(result, "contactLists");
    }


    /**
     * @param partyInvitationId
     * @param uuid
     * @param inviterName
     * @param expirationType
     * @param expirationDate
     * @param leftDays
     * @param emails
     * @return
     * @throws GenericServiceException
     */
    private String scheduleNotificationExpirationJob(String partyInvitationId, String uuid, String inviterName, int expirationType, Timestamp expirationDate, int leftDays, List<String> emails) throws GenericServiceException {
        Map<String, Object> attributes = new HashMap<String, Object>();
        attributes.put(CommunicationEventAttributeKeyOfbiz.INVITATION_UUID.name(), uuid);
        attributes.put(CommunicationEventAttributeKeyOfbiz.INVITER_NAME.name(), inviterName);
        attributes.put(CommunicationEventAttributeKeyOfbiz.INV_EXPIRATION_TYPE.name(), expirationType);
        attributes.put(CommunicationEventAttributeKeyOfbiz.INV_EXPIRATION_DATE.name(), expirationDate);
        attributes.put(CommunicationEventAttributeKeyOfbiz.INV_EXPIRATION_LEFT_DAYS.name(), leftDays);
        attributes.put(JobAttribute.INVITATION_RECIPIENT_EMAILS.name(), emails);

        Calendar cal = Calendar.getInstance();
        cal.setTime(expirationDate);
        cal.add(Calendar.DAY_OF_MONTH, -leftDays);
        Timestamp scheduled = new Timestamp(cal.getTimeInMillis());
        if (scheduled.after(UtilDateTime.nowTimestamp()) && !scheduled.after(expirationDate)) {
            return gnCreateJob(JobTypeOfbiz.GN_ACT_JOB_PARTY_INVITATION_NOTIFICATION_PENDING, true, partyInvitationId, 0, scheduled, attributes);
        } else {
            return null;
        }
    }

    /**
     * @param partyInvitationId
     * @param userLoginIdFrom
     * @param invitedName
     * @param expirationDate    @return  @throws GenericServiceException
     */
    private String scheduleMarkExpiredJob(String partyInvitationId, String userLoginIdFrom, String invitedName, Timestamp expirationDate) throws GenericServiceException {
        Map<String, Object> attributes = new HashMap<String, Object>();
        if (UtilValidate.isNotEmpty(userLoginIdFrom)) {
            Map<String, Object> userProfile = dispatcher.runSync("gnGetUser",
                    UtilMisc.toMap("userLogin", userLogin, "userLoginId", userLoginIdFrom));
            String email = (String) UtilMisc.getMapFromMap(userProfile, "contact").get("emailAddress");
            List<String> emails = (UtilValidate.isNotEmpty(email)) ? Arrays.asList(email) : Collections.<String>emptyList();
            attributes.put(JobAttribute.INVITATION_RECIPIENT_EMAILS.name(), emails);
        } else {
            attributes.put(JobAttribute.INVITATION_RECIPIENT_EMAILS.name(), Collections.<String>emptyList());
        }

        attributes.put(CommunicationEventAttributeKeyOfbiz.INVITED_NAME.name(), invitedName);
        Calendar cal = Calendar.getInstance();
        cal.setTime(expirationDate);
        cal.add(Calendar.DAY_OF_MONTH, +1);
        Timestamp scheduled = new Timestamp(cal.getTimeInMillis());
        if (scheduled.after(UtilDateTime.nowTimestamp()) && scheduled.after(expirationDate)) {
            return gnCreateJob(JobTypeOfbiz.GN_ACT_JOB_PARTY_INVITATION_MARK_EXPIRED, true, partyInvitationId, 0, scheduled, attributes);
        } else {
            return null;
        }
    }


    public void disableJob(String referenceKey) throws GenericServiceException {
        gnUpdateJobStatusByReferenceKey(referenceKey, JobStatusOfbiz.GN_ACT_JOB_CANCELLED, null, true);
    }


    public void gnManageOldPartyInvitation() throws GenericEntityException, GenericServiceException {
        scheduleJobsForOldPartyInvitation();
        createNotificationGroupForInvitedCompanies();
    }

    /**
     * @throws GenericEntityException
     * @throws GenericServiceException
     */
    private void scheduleJobsForOldPartyInvitation() throws GenericEntityException, GenericServiceException {
        InvitationHelper invitationHelper = new InvitationHelper(dctx, context);
        List<EntityCondition> conds = new ArrayList<EntityCondition>();
        conds.add(EntityCondition.makeCondition("statusId", PartyInvitationStateOfbiz.PARTYINV_SENT.name()));
        conds.add(EntityCondition.makeCondition("expirationDate", EntityComparisonOperator.GREATER_THAN_EQUAL_TO, UtilDateTime.getDayEnd(UtilDateTime.nowTimestamp())));
        List<GenericValue> invitations = delegator.findList("PartyInvitation", EntityCondition.makeCondition(conds), null, UtilMisc.toList("lastInviteDate DESC"), null, false);

        List<Map<String, Object>> jobs = findJobs(null, JobStatusOfbiz.GN_ACT_JOB_SCHEDULED, JobTypeOfbiz.GN_ACT_JOB_PARTY_INVITATION_NOTIFICATION_PENDING, null, false);
        Set<String> scheduledKey = new HashSet<String>(jobs.size());
        for (Map<String, Object> job : jobs) {
            scheduledKey.add((String) job.get("referenceKey"));
        }

        for (GenericValue _partyInvitation : invitations) {
            if (!scheduledKey.contains(_partyInvitation.getString("partyInvitationId"))) {
                Map<String, Object> partyInvitation = UtilMisc.makeMapWritable(_partyInvitation);
                invitationHelper.loadRelatedEntity(partyInvitation);
                scheduleInvitationExpirationJobs(partyInvitation);
                Debug.log(String.format("Scheduled jobs for partyInvitation[%s]", partyInvitation.get("partyInvitationId")));
            }
        }

        createNotificationGroupForInvitedCompanies();

    }

    /**
     * Create notification group with events GN_COM_EV_AUTH_EXPIR for already invited companies
     *
     * @throws GenericEntityException
     * @throws GenericServiceException
     */
    private void createNotificationGroupForInvitedCompanies() throws GenericEntityException, GenericServiceException {
        List<GenericValue> invitedCompanyIds = delegator.findByAnd("GnInvitedCompany", UtilMisc.toMap("INVITE_StatusId", PartyInvitationStateOfbiz.PARTYINV_ACCEPTED.name()), UtilMisc.toList("partyId"));
        for (GenericValue invitedCompany : invitedCompanyIds) {
            String partyId = invitedCompany.getString("partyId");
            List<Map<String, Object>> contactLists = gnFindContactList(partyId, EventTypeOfbiz.GN_COM_EV_AUTH_EXPIR);
            if (contactLists.size() == 0) {
                List<Map<String, Object>> users = gnFindUsersByEmployingCompany(partyId);
                if (users.size() > 0) {
                    List<String> userLoginIds = new ArrayList<String>();
                    for (Map<String, Object> user : users)
                        userLoginIds.add((String) user.get("userLoginId"));

                    Map<String, Object> commEventParams2 = FastMap.newInstance();
                    commEventParams2.put("partyId", partyId);
                    commEventParams2.put("userLoginIds", userLoginIds);
                    commEventParams2.put("contactListName", "Gruppo di notifica scadenza autorizzazioni");
                    commEventParams2.put("communicationEventTypeIds", Arrays.asList(CommunicationEventTypeOfbiz.EMAIL_COMMUNICATION.name()));
                    commEventParams2.put("eventTypeIds", Arrays.asList(EventTypeOfbiz.GN_COM_EV_AUTH_EXPIR.name()));
                    commEventParams2.put("userLogin", userLogin);
                    Map<String, Object> contactList2 = dispatcher.runSync("gnCreateUpdateContactList", commEventParams2);
                    Debug.log("Created contact list with contactListId[" + contactList2.get("contactListId") + "]");
                }
            } else {
                Debug.log(String.format("Found contactList[%s] for company[%s]", contactLists.get(0).get("contactListId"), partyId), module);
            }
        }
    }

    /**
     * Schedule job that remind tu publish imported authorization
     *
     * @param nodeKey
     * @param email
     * @throws GenericServiceException
     */
    public void scheduleAuthorizationPublicationReminderJob(String nodeKey, String email) throws GenericServiceException, GenericEntityException {
        Map<String, Object> attributes = new HashMap<String, Object>();
        attributes.put(JobAttribute.INVITATION_RECIPIENT_EMAILS.name(), Arrays.asList(email));
        int days = Integer.parseInt(PropertyUtil.getProperty(PropertyEnumOfbiz.INVITED_COMPANY_PUBLISH_AUTHORIZATION_REMINDER, getDispatcher(), getContext()));
        Calendar cal = Calendar.getInstance();
        cal.setTime(UtilDateTime.getDayStart(UtilDateTime.nowTimestamp()));
        cal.add(Calendar.DAY_OF_MONTH, days);
        Timestamp scheduled = new Timestamp(cal.getTimeInMillis());
        String jobId = gnCreateJob(JobTypeOfbiz.GN_ACT_JOB_AUTHORIZATION_PUBLICATION_REMINDER, true, nodeKey, 0, scheduled, attributes);
        Debug.log(String.format("Scheduled job[%s]: send remainder to [%s] company[%s]", jobId, email, nodeKey));
    }

    /**
     * schedule jobs  GN_ACT_JOB_AUTHORIZATION_PUBLICATION_REMINDER for old PartyInvitation
     * {@link #scheduleAuthorizationPublicationReminderJob(String, String)}
     *
     * @throws GenericEntityException
     * @throws GenericServiceException
     */
    public void gnScheduleJobsAuthorizationPublicationReminderForInvitedCompany() throws GenericEntityException, GenericServiceException {
        DynamicViewEntity dv = new DynamicViewEntity();
        dv.addMemberEntity("COMPANY", "GnPartyNode");
        dv.addMemberEntity("PREL", "GnPartyRelationship");
        dv.addMemberEntity("INV", "GnPartyInvitationAndCompany");
        dv.addAliasAll("COMPANY", "COMPANY_");
        dv.addAliasAll("PREL", "PREL_");
        dv.addAliasAll("INV", "INV_");

        dv.addViewLink("COMPANY", "INV", false, ModelKeyMap.makeKeyMapList("taxIdentificationNumber", "INVITED_TaxIdentificationNumber"));
        dv.addViewLink("COMPANY", "PREL", false, ModelKeyMap.makeKeyMapList("partyId", "partyIdFrom"));
        List<EntityCondition> conds = new ArrayList<EntityCondition>();
        conds.add(EntityCondition.makeCondition("PREL_PartyRelationshipTypeId", RelationshipTypeOfbiz.GN_PUBLISHES_TO_ROOT.name()));
        conds.add(EntityCondition.makeCondition("COMPANY_NodeType", PartyNodeTypeOfbiz.COMPANY.name()));
        conds.add(EntityCondition.makeCondition("INV_StatusId", PartyInvitationStateOfbiz.PARTYINV_ACCEPTED.name()));

        EntityListIterator it = delegator.findListIteratorByCondition(dv, EntityCondition.makeCondition(conds), null, null, UtilMisc.toList("INV_PartyInvitationId"), null);
        List<GenericValue> list = it.getCompleteList();
        list.size();
        it.close();
        for (GenericValue gv : list) {

            List<GenericValue> ret = delegator.findList("PartyRelationship", EntityConditionUtil.makeRelationshipCondition(null, "GN_USER", (String) gv.get("COMPANY_PartyId"), "GN_COMPANY", RelationshipTypeOfbiz.EMPLOYMENT.name(), true), null, null, null, true);
            if (ret.size() != 1)
                Debug.logWarning("More than one employed found", module);

            Map<String, Object> srvContext = dispatcher.getDispatchContext().makeValidContext("gnGetPartyContact", "IN", context);
            srvContext.put("partyId", ret.get(0).get("partyIdFrom"));
            Map<String, Object> srvResult = dispatcher.runSync("gnGetPartyContact", srvContext);
            String emailAddress = (String) UtilGenerics.checkMap(srvResult.get("contact")).get("emailAddress");
            if (UtilValidate.isNotEmpty(emailAddress)) {
                String companyId = (String) gv.get("COMPANY_PartyId");
                if (findJobs(null, null, JobTypeOfbiz.GN_ACT_JOB_AUTHORIZATION_PUBLICATION_REMINDER, companyId, false).size() == 0)
                    scheduleAuthorizationPublicationReminderJob(companyId, emailAddress);
            }

        }
    }


    /**
     * Proxy method to create/update authorization
     *
     * @param partyId
     * @return
     */
    public List<Map<String, Object>> gnFindUsersByEmployingCompany(String partyId) throws GenericServiceException {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("userLogin", userLogin);
        params.put("partyId", partyId);
        Map<String, Object> result = dispatcher.runSync("gnFindUsersByEmployingCompany", params);
        return UtilMisc.getListFromMap(result, "users");
    }

}
