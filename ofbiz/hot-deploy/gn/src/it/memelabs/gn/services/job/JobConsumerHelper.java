package it.memelabs.gn.services.job;

import it.memelabs.gn.services.AbstractServiceHelper;
import it.memelabs.gn.services.communication.CommunicationEventTypeOfbiz;
import it.memelabs.gn.services.communication.EventTypeOfbiz;
import it.memelabs.gn.services.event.EventMessageContainer;
import it.memelabs.gn.services.event.EventMessageFactory;
import it.memelabs.gn.services.event.type.EventMessage;
import it.memelabs.gn.services.node.PartyInvitationStateOfbiz;
import it.memelabs.gn.services.system.PropertyEnumOfbiz;
import it.memelabs.gn.util.PrettyPrintingMap;
import it.memelabs.gn.util.PropertyUtil;
import javolution.util.FastMap;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilDateTime;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.service.DispatchContext;
import org.ofbiz.service.GenericServiceException;

import java.sql.Timestamp;
import java.util.*;

/**
 * 06/05/2014
 *
 * @author Andrea Fossi
 */
public class JobConsumerHelper extends AbstractServiceHelper {
    private static final String module = JobConsumerHelper.class.getName();
    private JobHelper jobHelper;

    /**
     * @param dctx
     * @param context
     */
    public JobConsumerHelper(DispatchContext dctx, Map<String, ? extends Object> context) {
        super(dctx, context);
        this.jobHelper = new JobHelper(dctx, context);
    }

    public Map<String, Object> gnConsumeJob(String jobId) throws GenericEntityException, GenericServiceException {
        GenericValue job = jobHelper.findJobById(jobId);
        Map<String, Object> attributes = jobHelper.findAttributes(jobId);
        List<EventMessage> eventList = new ArrayList<EventMessage>();
        String jobTypeId = (String) job.get("jobTypeId");

        if (JobTypeOfbiz.GN_JOB_NOP.name().equals(jobTypeId)) {
            eventList.addAll(consumeNOP(job, attributes));
        } else if (JobTypeOfbiz.GN_ACT_JOB_AUTHORIZATION_EXPIRING.name().equals(jobTypeId)) {
            eventList.addAll(consumeAuthorizationExpirationJob(job, attributes));
        } else if (JobTypeOfbiz.GN_ACT_JOB_AUTHORIZATION_EXPIRING_MANAGE_OLD.name().equals(jobTypeId)) {
            eventList.addAll(scheduleJobsForPreviousAuthorization(job, attributes));
        } else if (JobTypeOfbiz.GN_ACT_JOB_PARTY_INVITATION_NOTIFICATION_PENDING.name().equals(jobTypeId)) {
            eventList.addAll(consumePartyInvitationPendingNotificationJob(job, attributes));
        } else if (JobTypeOfbiz.GN_ACT_JOB_PARTY_INVITATION_MARK_EXPIRED.name().equals(jobTypeId)) {
            eventList.addAll(consumePartyInvitationExpiredJob(job, attributes));
        } else if (JobTypeOfbiz.GN_ACT_JOB_PARTY_INVITATION_JOB_MANAGE_OLD.name().equals(jobTypeId)) {
            eventList.addAll(scheduleJobsForOldPartyInvitation(job, attributes));
        } else if (JobTypeOfbiz.GN_ACT_JOB_AUTHORIZATION_PUBLICATION_REMINDER.name().equals(jobTypeId)) {
            eventList.addAll(consumeAuthorizationPublicationReminder(job, attributes));
        } else if (JobTypeOfbiz.GN_ACT_JOB_AUTHORIZATION_PUBLICATION_REMINDER_MANAGE_OLD.name().equals(jobTypeId)) {
            eventList.addAll(scheduleJobsAuthorizationPublicationReminderForInvitedCompany(job, attributes));
        } else if (JobTypeOfbiz.GN_ACT_JOB_AUTHORIZATION_TO_BE_VALIDATED_REMINDER.name().equals(jobTypeId)) {
            eventList.addAll(consumeValidateAuthorizationRemindJob(job, attributes));
        } else if (JobTypeOfbiz.GN_ACT_JOB_AUTHORIZATION_TO_BE_VALIDATED_REMINDER_MANAGE_OLD.name().equals(jobTypeId)) {
            eventList.addAll(schedulePreviousAuthorizationValidateRemindJobs(job, attributes));
        } else {
            Debug.logError(String.format("Job[%s] Type[%s] not managed", jobId, job.getString("jobTypeId")), module);
            jobHelper.gnUpdateJobStatus(jobId, JobStatusOfbiz.GN_ACT_JOB_FAILED, "Job Type not managed");
        }
        Map<String, Object> ret = UtilMisc.makeMapWritable(job);
        ret.put("attributes", attributes);
        ret.put("eventList", eventList);
        return ret;
    }

    //-- job consumer

    private List<EventMessage> consumeNOP(GenericValue job, Map<String, Object> attributes) throws GenericEntityException {
        job.put("jobStatusId", JobStatusOfbiz.GN_ACT_JOB_MISFIRE.name());
        job.put("consumed", UtilDateTime.nowTimestamp());
        delegator.store(job);
        return Collections.emptyList();
    }

    private List<EventMessage> scheduleJobsForPreviousAuthorization(GenericValue job, Map<String, Object> attributes) throws GenericEntityException, GenericServiceException {
        dispatcher.runSync("gnScheduleAuthorizationExpirationRemindJobsForPreviousAuthorization", UtilMisc.toMap("userLogin", userLogin));
        job.put("jobStatusId", JobStatusOfbiz.GN_ACT_JOB_MISFIRE.name());
        job.put("consumed", UtilDateTime.nowTimestamp());
        delegator.store(job);
        return Collections.emptyList();
    }

    private List<EventMessage> schedulePreviousAuthorizationValidateRemindJobs(GenericValue job, Map<String, Object> attributes) throws GenericEntityException, GenericServiceException {
        dispatcher.runSync("gnSchedulePreviousAuthorizationValidateRemindJobs", UtilMisc.toMap("userLogin", userLogin));
        job.put("jobStatusId", JobStatusOfbiz.GN_ACT_JOB_MISFIRE.name());
        job.put("consumed", UtilDateTime.nowTimestamp());
        delegator.store(job);
        return Collections.emptyList();
    }

    private List<EventMessage> scheduleJobsAuthorizationPublicationReminderForInvitedCompany(GenericValue job, Map<String, Object> attributes) throws GenericEntityException, GenericServiceException {
        dispatcher.runSync("gnScheduleJobsAuthorizationPublicationReminderForInvitedCompany", UtilMisc.toMap("userLogin", userLogin));
        job.put("jobStatusId", JobStatusOfbiz.GN_ACT_JOB_MISFIRE.name());
        job.put("consumed", UtilDateTime.nowTimestamp());
        delegator.store(job);
        return Collections.emptyList();
    }

    private List<EventMessage> scheduleJobsForOldPartyInvitation(GenericValue job, Map<String, Object> attributes) throws GenericEntityException, GenericServiceException {
        dispatcher.runSync("gnManageOldPartyInvitation", UtilMisc.toMap("userLogin", userLogin));
        job.put("jobStatusId", JobStatusOfbiz.GN_ACT_JOB_MISFIRE.name());
        job.put("consumed", UtilDateTime.nowTimestamp());
        delegator.store(job);
        return Collections.emptyList();
    }

    private List<EventMessage> consumeAuthorizationExpirationJob(GenericValue job, Map<String, Object> attributes) throws GenericEntityException, GenericServiceException {
        String ownerNodeId = (String) attributes.get(JobAttribute.OWNER_NODE_ID.name());
        EventMessageContainer.clean();
        gnSendCommunicationEventToContactListEM(ownerNodeId, EventTypeOfbiz.GN_COM_EV_AUTH_EXPIR, attributes);
        List<EventMessage> events = EventMessageContainer.getEvents();
        if (events.size() > 0) {
            job.put("jobStatusId", JobStatusOfbiz.GN_ACT_JOB_CONSUMED.name());
        } else {
            job.put("jobStatusId", JobStatusOfbiz.GN_ACT_JOB_MISFIRE.name());
        }

        job.put("expectedAck", (long) events.size());
        job.put("consumed", UtilDateTime.nowTimestamp());
        delegator.store(job);
        return events;
    }

    private List<EventMessage> consumePartyInvitationPendingNotificationJob(GenericValue job, Map<String, Object> attributes) throws GenericEntityException, GenericServiceException {
        Map<String, Object> params1 = UtilMisc.toMap("userLogin", userLogin, "partyInvitationId", job.get("referenceKey"));
        Map<String, Object> partyInvitation = UtilMisc.getMapFromMap(dispatcher.runSync("gnFindPartyInvitationById", params1), "partyInvitation");
        List<EventMessage> ret = new LinkedList<EventMessage>();
        if (UtilValidate.isNotEmpty(partyInvitation) && PartyInvitationStateOfbiz.PARTYINV_SENT.name().equals(partyInvitation.get("statusId"))) {
            List<String> emails = UtilMisc.getListFromMap(attributes, JobAttribute.INVITATION_RECIPIENT_EMAILS.name());
            Map<String, Object> eventAttributes = new HashMap<String, Object>(attributes);
            eventAttributes.remove(JobAttribute.INVITATION_RECIPIENT_EMAILS.name());
            EventMessageContainer.clean();
            for (String email : emails) {
                String communicationEventId = gnSendCommunicationEventToEmail(email, CommunicationEventTypeOfbiz.EMAIL_COMMUNICATION.name(),
                        EventTypeOfbiz.GN_COM_EV_INVI_EXP.name(), eventAttributes);
                Map<String, Object> result = dispatcher.runSync("gnFindCommunicationEventByIdWithoutPermission", UtilMisc.toMap("userLogin", userLogin, "communicationEventId", communicationEventId));
                Map<String, Object> event = UtilMisc.getMapFromMap(result, "communicationEvent");
                if (UtilValidate.isNotEmpty(event)) {
                    Map<String, Object> userMap = new HashMap<String, Object>();
                    userMap.put("firstName", null);
                    userMap.put("lastName", null);
                    userMap.put("contact", UtilMisc.toMap("emailAddress", email));
                    event.put("user", userMap);
                    ret.add(EventMessageFactory.make().makeEventMessageCommunicationFromCommunication(event));
                }
            }
        }

        if (ret.size() > 0) {
            job.put("jobStatusId", JobStatusOfbiz.GN_ACT_JOB_CONSUMED.name());
        } else {
            job.put("jobStatusId", JobStatusOfbiz.GN_ACT_JOB_MISFIRE.name());
        }
        job.put("expectedAck", (long) ret.size());
        job.put("consumed", UtilDateTime.nowTimestamp());
        delegator.store(job);
        return ret;
    }

    @Override
    public DispatchContext getDctx() {
        return super.getDctx();
    }

    private List<EventMessage> consumePartyInvitationExpiredJob(GenericValue job, Map<String, Object> attributes) throws GenericEntityException, GenericServiceException {
        Map<String, Object> params1 = UtilMisc.toMap("userLogin", userLogin, "partyInvitationId", job.get("referenceKey"));
        Map<String, Object> partyInvitation = UtilMisc.getMapFromMap(dispatcher.runSync("gnInternalFindPartyInvitationById", params1), "partyInvitation");
        if (!PartyInvitationStateOfbiz.PARTYINV_ACCEPTED.name().equals(partyInvitation.get("statusId"))) {
            params1.put("statusId", PartyInvitationStateOfbiz.PARTYINV_CANCELLED.name());
            dispatcher.runSync("gnUpdateInvitationState", params1);
        }
        List<EventMessage> ret = new ArrayList<EventMessage>();
        if (UtilValidate.isNotEmpty(partyInvitation) && PartyInvitationStateOfbiz.PARTYINV_SENT.name().equals(partyInvitation.get("statusId"))) {
            List<String> emails = UtilMisc.getListFromMap(attributes, JobAttribute.INVITATION_RECIPIENT_EMAILS.name());
            Map<String, Object> eventAttributes = new HashMap<String, Object>(attributes);
            eventAttributes.remove(JobAttribute.INVITATION_RECIPIENT_EMAILS.name());
            EventMessageContainer.clean();
            for (String email : emails) {
                String communicationEventId = gnSendCommunicationEventToEmail(email, CommunicationEventTypeOfbiz.EMAIL_COMMUNICATION.name(),
                        EventTypeOfbiz.GN_COM_EV_INV_EXPD.name(), eventAttributes);
                Map<String, Object> result = dispatcher.runSync("gnFindCommunicationEventByIdWithoutPermission", UtilMisc.toMap("userLogin", userLogin, "communicationEventId", communicationEventId));
                Map<String, Object> event = UtilMisc.getMapFromMap(result, "communicationEvent");
                if (UtilValidate.isNotEmpty(event)) {
                    Map<String, Object> userMap = new HashMap<String, Object>();
                    userMap.put("firstName", null);
                    userMap.put("lastName", null);
                    userMap.put("contact", UtilMisc.toMap("emailAddress", email));
                    event.put("user", userMap);
                    ret.add(EventMessageFactory.make().makeEventMessageCommunicationFromCommunication(event));
                }
            }


            if (ret.size() > 0) {
                job.put("jobStatusId", JobStatusOfbiz.GN_ACT_JOB_CONSUMED.name());
            } else {
                job.put("jobStatusId", JobStatusOfbiz.GN_ACT_JOB_MISFIRE.name());
            }
            job.put("expectedAck", (long) ret.size());
            job.put("consumed", UtilDateTime.nowTimestamp());
            delegator.store(job);
            return ret;
        } else {
            job.put("jobStatusId", JobStatusOfbiz.GN_ACT_JOB_FAILED.name());
            job.put("expectedAck", 0l);
            job.put("failMessage", "partyInvitation not found");
            job.put("consumed", UtilDateTime.nowTimestamp());
            delegator.store(job);
            return Collections.emptyList();
        }
    }

    private List<EventMessage> consumeAuthorizationPublicationReminder(GenericValue job, Map<String, Object> attributes) throws GenericEntityException, GenericServiceException {

        String referenceKey = (String) job.get("referenceKey");
        EventMessageContainer.clean();

        List<String> emails = UtilMisc.getListFromMap(attributes, JobAttribute.INVITATION_RECIPIENT_EMAILS.name());

        Map<String, Object> hasPublishedResult = dispatcher.runSync("gnCheckInvitedCompanyHasPublishedAuthorization", UtilMisc.toMap("userLogin", userLogin, "ownerNodeId", referenceKey));
        boolean hasPublished = "Y".equals(hasPublishedResult.get("hasPublished"));
        List<EventMessage> ret = new LinkedList<EventMessage>();
        if (!hasPublished) {
            for (String email : emails) {
                String communicationEventId = gnSendCommunicationEventToEmail(email, CommunicationEventTypeOfbiz.EMAIL_COMMUNICATION.name(),
                        EventTypeOfbiz.GN_COM_EV_AUTH_PB_PN.name(), Collections.<String, Object>emptyMap());
                Map<String, Object> result = dispatcher.runSync("gnFindCommunicationEventByIdWithoutPermission", UtilMisc.toMap("userLogin", userLogin, "communicationEventId", communicationEventId));
                Map<String, Object> event = UtilMisc.getMapFromMap(result, "communicationEvent");
                if (UtilValidate.isNotEmpty(event)) {
                    Map<String, Object> userMap = new HashMap<String, Object>();
                    userMap.put("firstName", null);
                    userMap.put("lastName", null);
                    userMap.put("contact", UtilMisc.toMap("emailAddress", email));
                    event.put("user", userMap);
                    ret.add(EventMessageFactory.make().makeEventMessageCommunicationFromCommunication(event));
                }
            }
            int days = Integer.parseInt(PropertyUtil.getProperty(PropertyEnumOfbiz.INVITED_COMPANY_PUBLISH_AUTHORIZATION_REMINDER, getDispatcher(), getContext()));
            Calendar cal = Calendar.getInstance();
            cal.setTime((Timestamp) job.get("scheduled"));
            cal.add(Calendar.DAY_OF_MONTH, days);
            Timestamp scheduled = new Timestamp(cal.getTimeInMillis());
            jobHelper.gnCreateJob(JobStatusOfbiz.GN_ACT_JOB_SCHEDULED, JobTypeOfbiz.GN_ACT_JOB_AUTHORIZATION_PUBLICATION_REMINDER, "Y", referenceKey, 0l, scheduled, attributes);
        }

        if (ret.size() > 0) {
            job.put("jobStatusId", JobStatusOfbiz.GN_ACT_JOB_CONSUMED.name());
        } else {
            job.put("jobStatusId", JobStatusOfbiz.GN_ACT_JOB_MISFIRE.name());
        }
        job.put("expectedAck", (long) ret.size());
        job.put("consumed", UtilDateTime.nowTimestamp());
        delegator.store(job);
        return ret;
    }


    private List<EventMessage> consumeValidateAuthorizationRemindJob(GenericValue job, Map<String, Object> attributes) throws GenericEntityException, GenericServiceException {
        String jobId = job.getString("jobId");
        EventMessageContainer.clean();
        dispatcher.runSync("gnConsumeValidateAuthorizationRemindJob", UtilMisc.toMap("userLogin", userLogin, "jobId", jobId));
        List<EventMessage> events = EventMessageContainer.getEvents();
        if (events.size() > 0) {
            job.put("jobStatusId", JobStatusOfbiz.GN_ACT_JOB_CONSUMED.name());
        } else {
            job.put("jobStatusId", JobStatusOfbiz.GN_ACT_JOB_MISFIRE.name());
        }
        job.put("expectedAck", (long) events.size());
        job.put("consumed", UtilDateTime.nowTimestamp());
        delegator.store(job);
        return events;
    }


    //-- ----------------

    /**
     * @param toString
     * @param communicationEventTypeId
     * @param eventTypeId
     * @param attributes
     * @return
     * @throws GenericServiceException
     */
    public String gnSendCommunicationEventToEmail(String toString, String communicationEventTypeId, String eventTypeId, Map<String, Object> attributes) throws GenericServiceException {
        Map<String, Object> communicationEvent = FastMap.newInstance();
        communicationEvent.put("toString", toString);
        communicationEvent.put("entryDate", UtilDateTime.nowTimestamp());
        communicationEvent.put("communicationEventTypeId", communicationEventTypeId);
        communicationEvent.put("eventTypeId", eventTypeId);
        communicationEvent.put("attributes", attributes);
        String communicationEventId = createCommunicationEvent(communicationEvent);
        Debug.log("Created communicationEventId[" + communicationEventId + "], eventType[" + eventTypeId + "] and communicationEventType[" + communicationEventTypeId + "]  to email[" + toString + "]");
        Debug.log("Created communicationEventId[" + communicationEventId + "] " + communicationEvent.toString());
        return communicationEventId;
    }

    /**
     * @param ownerPartyId
     * @param eventTypeId
     * @return
     * @throws org.ofbiz.service.GenericServiceException
     */
    public Map<String, Object> gnSendCommunicationEventToContactListEM(String ownerPartyId, EventTypeOfbiz eventTypeId, Map<String, Object> jobAttributes) throws GenericServiceException {
        Map<String, Object> attributes = FastMap.newInstance();
        Map<String, Object> srvRequest = FastMap.newInstance();
        attributes.putAll(jobAttributes);
        attributes.remove(JobAttribute.OWNER_NODE_ID.name());
        srvRequest.put("userLogin", userLogin);
        srvRequest.put("ownerPartyId", ownerPartyId);
        srvRequest.put("eventTypeId", eventTypeId.name());
        srvRequest.put("attributes", attributes);
        Debug.log("Sending message for event[" + eventTypeId + "] on node[" + ownerPartyId + "] with attributes " + PrettyPrintingMap.toString(attributes));
        return dispatcher.runSync("gnSendCommunicationEventToContactListEM", srvRequest);
    }

    /**
     * @param communicationEvent
     * @return
     * @throws GenericServiceException
     */
    public String createCommunicationEvent(Map<String, Object> communicationEvent) throws GenericServiceException {
        Map<String, Object> srvRequest = UtilMisc.toMap("userLogin", context.get("userLogin"));
        srvRequest.putAll(communicationEvent);
        return (String) dctx.getDispatcher().runSync("gnCreateCommunicationEventWithoutPermission", srvRequest).get("communicationEventId");
    }


}
