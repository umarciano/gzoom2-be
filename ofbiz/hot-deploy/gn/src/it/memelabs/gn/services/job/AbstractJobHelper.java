package it.memelabs.gn.services.job;

import it.memelabs.gn.services.AbstractServiceHelper;
import it.memelabs.gn.services.auditing.EntityTypeOfbiz;
import it.memelabs.gn.services.communication.EventTypeOfbiz;
import it.memelabs.gn.util.PrettyPrintingMap;
import javolution.util.FastMap;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.service.DispatchContext;
import org.ofbiz.service.GenericServiceException;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 17/06/2014
 *
 * @author Andrea Fossi
 */
public class AbstractJobHelper extends AbstractServiceHelper {
    public AbstractJobHelper(DispatchContext dctx, Map<String, ? extends Object> context) {
        super(dctx, context);
    }


    /**
     * Proxy method to create/update authorization
     *
     * @param ownerPartyId
     * @param eventTypeId
     * @return
     */
    protected List<Map<String, Object>> gnFindContactList(String ownerPartyId, EventTypeOfbiz eventTypeId) throws GenericServiceException {
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
     * @param jobTypeId
     * @param commitRequired
     * @param referenceKey
     * @param expectedAck
     * @param scheduled
     * @return
     * @throws org.ofbiz.service.GenericServiceException
     */
    protected String gnCreateJob(JobTypeOfbiz jobTypeId, boolean commitRequired, String referenceKey, long expectedAck, Timestamp scheduled, Map<String, Object> attributes) throws GenericServiceException {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("jobTypeId", jobTypeId.name());
        params.put("commitRequired", (commitRequired) ? "Y" : "N");
        params.put("referenceKey", referenceKey);
        params.put("expectedAck", expectedAck);
        params.put("scheduled", scheduled);
        params.put("attributes", attributes);
        params.put("userLogin", userLogin);
        Map<String, Object> result = dispatcher.runSync("gnCreateJob", params);
        return (String) result.get("jobId");
    }

    protected void gnUpdateJobStatusByReferenceKey(String referenceKey, JobStatusOfbiz jobStatusId, String failMessage, boolean scheduledOnly) throws GenericServiceException {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("jobStatusId", jobStatusId.name());
        params.put("scheduledOnly", (scheduledOnly) ? "Y" : "N");
        params.put("referenceKey", referenceKey);
        params.put("failMessage", failMessage);
        params.put("userLogin", userLogin);
        Map<String, Object> result = dispatcher.runSync("gnUpdateJobStatusByReferenceKey", params);
        return;
    }

    protected List<Map<String, Object>> findJobs(String jobId, JobStatusOfbiz jobStatusId, JobTypeOfbiz jobTypeId, String referenceKey, boolean fetchJobAttributes) throws GenericServiceException {
        Map<String, Object> params = new HashMap<String, Object>(5);
        params.put("jobId", jobId);
        params.put("jobStatusId", (jobStatusId != null) ? jobStatusId.name() : null);
        params.put("jobTypeId", (jobTypeId != null) ? jobTypeId.name() : null);
        params.put("referenceKey", referenceKey);
        params.put("fetchJobAttributes", (fetchJobAttributes) ? "Y" : "N");
        params.put("userLogin", userLogin);
        Map<String, Object> result = dispatcher.runSync("gnFindJobs", params);
        return UtilMisc.getListFromMap(result, "jobList");
    }

    /**
     *
     * @param ownerPartyId
     * @param eventTypeId
     * @param overrideEventTypeId
     * @param jobAttributes
     * @return
     * @throws GenericServiceException
     */
    public Map<String, Object> gnSendCommunicationEventToContactListEM(String ownerPartyId, EventTypeOfbiz eventTypeId, EventTypeOfbiz overrideEventTypeId, Map<String, Object> jobAttributes) throws GenericServiceException {
        Map<String, Object> attributes = FastMap.newInstance();
        Map<String, Object> srvRequest = FastMap.newInstance();
        attributes.putAll(jobAttributes);
        attributes.remove(JobAttribute.OWNER_NODE_ID.name());
        srvRequest.put("userLogin", userLogin);
        srvRequest.put("ownerPartyId", ownerPartyId);
        srvRequest.put("eventTypeId", eventTypeId.name());
        srvRequest.put("overrideEventTypeId", (overrideEventTypeId != null) ? overrideEventTypeId.name() : eventTypeId);
        srvRequest.put("attributes", attributes);
        Debug.log("Sending message for event[" + eventTypeId + "] on node[" + ownerPartyId + "] with attributes " + PrettyPrintingMap.toString(attributes));
        return dispatcher.runSync("gnSendCommunicationEventToContactListEM", srvRequest);
    }
}
