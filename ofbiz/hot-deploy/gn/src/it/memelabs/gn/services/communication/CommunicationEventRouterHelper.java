package it.memelabs.gn.services.communication;

import it.memelabs.gn.services.AbstractServiceHelper;
import it.memelabs.gn.services.event.EventMessageContainer;
import it.memelabs.gn.services.event.EventMessageFactory;
import it.memelabs.gn.services.event.type.EventMessage;
import it.memelabs.gn.services.event.type.EventMessageCommunication;
import javolution.util.FastList;
import javolution.util.FastMap;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilDateTime;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.service.DispatchContext;
import org.ofbiz.service.GenericServiceException;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 31/05/13
 *
 * @author Andrea Fossi
 */
public class CommunicationEventRouterHelper extends AbstractServiceHelper {
    private static final String module = CommunicationEventRouterHelper.class.getName();

    /**
     * @param dctx    OFBiz DispatchContext
     * @param context Service Context
     */
    public CommunicationEventRouterHelper(DispatchContext dctx, Map<String, ? extends Object> context) {
        super(dctx, context);
    }

    /**
     * @param ownerPartyId
     * @param attributes
     * @return communicationEventIds
     * @throws GenericServiceException
     * @throws GenericEntityException
     */
    @SuppressWarnings("unchecked")
    public List<String> gnSendCommunicationEventToContactList(String ownerPartyId, String eventTypeId, Map<String, Object> attributes) throws GenericServiceException, GenericEntityException {
        List<Map<String, Object>> contactLists = findContactList(ownerPartyId, eventTypeId);
        List<String> result = FastList.newInstance();
        for (Map<String, Object> contactList : contactLists) {
            String contactListName = (String) contactList.get("contactListName");
            String contactListId = (String) contactList.get("contactListId");
            Timestamp entryDate = UtilDateTime.nowTimestamp();
            List<Map<String, Object>> users = (List<Map<String, Object>>) contactList.get("users");
            List<Map<String, Object>> communicationEventTypes = (List<Map<String, Object>>) contactList.get("communicationEventTypes");
            for (Map<String, Object> user : users) {
                for (Map<String, Object> communicationEventType : communicationEventTypes) {
                    String communicationEventTypeId = (String) communicationEventType.get("communicationEventTypeId");
                    Map<String, Object> communicationEvent = FastMap.newInstance();
                    communicationEvent.put("userLoginIdTo", user.get("userLoginId"));
                    communicationEvent.put("entryDate", entryDate);
                    communicationEvent.put("communicationEventTypeId", communicationEventTypeId);
                    communicationEvent.put("eventTypeId", eventTypeId);
                    communicationEvent.put("attributes", attributes);
                    communicationEvent.put("note", "contactListId[" + contactListId + "," + contactListName + "]");
                    String communicationEventId = createCommunicationEvent(communicationEvent);
                    Debug.log("Created communicationEventId[" + communicationEventId + "], eventType[" + eventTypeId + "] and communicationEventType[" + communicationEventTypeId + "]  for contactList[" + contactListId + "," + contactListName + "]");
                    Debug.log("Created communicationEventId[" + communicationEventId + "] " + communicationEvent.toString());
                    result.add(communicationEventId);
                }
            }
        }

        return result;
    }

    /**
     *
     * @param ownerPartyId
     * @param eventTypeId used for ContactList searching
     * @param overrideEventTypeId
     * @param attributes
     * @return
     * @throws GenericServiceException
     * @throws GenericEntityException
     */
    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> gnSendCommunicationEventToContactListEM(String ownerPartyId, String eventTypeId, String overrideEventTypeId, Map<String, Object> attributes) throws GenericServiceException, GenericEntityException {
        List<Map<String, Object>> contactLists = findContactList(ownerPartyId, eventTypeId);
        List<Map<String, Object>> result = FastList.newInstance();
        List<EventMessage> messages = new ArrayList<EventMessage>();
        for (Map<String, Object> contactList : contactLists) {
            String contactListName = (String) contactList.get("contactListName");
            String contactListId = (String) contactList.get("contactListId");
            Timestamp entryDate = UtilDateTime.nowTimestamp();
            List<Map<String, Object>> users = (List<Map<String, Object>>) contactList.get("users");
            List<Map<String, Object>> communicationEventTypes = (List<Map<String, Object>>) contactList.get("communicationEventTypes");
            for (Map<String, Object> user : users) {
                for (Map<String, Object> communicationEventType : communicationEventTypes) {
                    String communicationEventTypeId = (String) communicationEventType.get("communicationEventTypeId");
                    Map<String, Object> communicationEvent = FastMap.newInstance();
                    communicationEvent.put("userLoginIdTo", user.get("userLoginId"));
                    communicationEvent.put("entryDate", entryDate);
                    communicationEvent.put("communicationEventTypeId", communicationEventTypeId);
                    //if overrideEventTypeId is not empty, it replaces eventTypeId
                    communicationEvent.put("eventTypeId", (UtilValidate.isNotEmpty(overrideEventTypeId)) ? overrideEventTypeId : eventTypeId);
                    communicationEvent.put("attributes", attributes);
                    communicationEvent.put("note", "contactListId[" + contactListId + "," + contactListName + "]");
                    String communicationEventId = createCommunicationEvent(communicationEvent);
                    Debug.log("Created communicationEventId[" + communicationEventId + "], eventType[" + eventTypeId + "] and communicationEventType[" + communicationEventTypeId + "]  for contactList[" + contactListId + "," + contactListName + "]");
                    Debug.log("Created communicationEventId[" + communicationEventId + "] " + communicationEvent.toString());
                    Map<String, Object> ce = gnFindCommunicationEventById(communicationEventId);
                    result.add(ce);
                    if (!CommunicationEventTypeOfbiz.WEB_SITE_COMMUNICATI.name().equals(communicationEventTypeId)) {
                        EventMessageCommunication em = EventMessageFactory.make().makeEventMessageCommunicationFromCommunication(ce);
                        messages.add(em);
                    }
                }
            }
        }
        if (messages.size() > 0)
            EventMessageContainer.addAll(messages);
        return result;
    }

    public String gnSendCommunicationEventToUser(String userLoginId, String communicationEventTypeId, String eventTypeId, Map<String, Object> attributes) throws GenericServiceException {
        Map<String, Object> communicationEvent = FastMap.newInstance();
        communicationEvent.put("userLoginIdTo", userLoginId);
        communicationEvent.put("entryDate", UtilDateTime.nowTimestamp());
        communicationEvent.put("communicationEventTypeId", communicationEventTypeId);
        communicationEvent.put("eventTypeId", eventTypeId);
        communicationEvent.put("attributes", attributes);
        String communicationEventId = createCommunicationEvent(communicationEvent);
        Debug.log("Created communicationEventId[" + communicationEventId + "], eventType[" + eventTypeId + "] and communicationEventType[" + communicationEventTypeId + "]  for user[" + userLoginId + "]");
        Debug.log("Created communicationEventId[" + communicationEventId + "] " + communicationEvent.toString());
        return communicationEventId;
    }

    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> findContactList(String ownerPartyId, String eventTypeId) throws GenericServiceException {
        Map<String, Object> srvRequest = UtilMisc.toMap("userLogin", userLogin, "ownerPartyIds", UtilMisc.toList(ownerPartyId), "eventTypeIds", UtilMisc.toList(eventTypeId));
        return (List<Map<String, Object>>) dctx.getDispatcher().runSync("gnFindContactListWithoutPermission", srvRequest).get("contactLists");
    }

    @SuppressWarnings("unchecked")
    public String createCommunicationEvent(Map<String, Object> communicationEvent) throws GenericServiceException {
        Map<String, Object> srvRequest = UtilMisc.toMap("userLogin", context.get("userLogin"));
        srvRequest.putAll(communicationEvent);
        return (String) dctx.getDispatcher().runSync("gnCreateCommunicationEventWithoutPermission", srvRequest).get("communicationEventId");
    }

    @SuppressWarnings("unchecked")
    public Map<String, Object> gnFindCommunicationEventById(String communicationEventId) throws GenericServiceException {
        Map<String, Object> srvRequest = UtilMisc.toMap("userLogin", context.get("userLogin"), "communicationEventId", communicationEventId);
        Map<String, Object> srvResponse = dctx.getDispatcher().runSync("gnFindCommunicationEventByIdWithoutPermission", srvRequest);
        Map<String, Object> communicationEvent = UtilMisc.getMapFromMap(srvResponse, "communicationEvent");
        return communicationEvent;
    }
}