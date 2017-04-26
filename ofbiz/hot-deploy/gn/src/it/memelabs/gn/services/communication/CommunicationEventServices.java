package it.memelabs.gn.services.communication;

import it.memelabs.gn.services.OfbizErrors;
import it.memelabs.gn.util.GnServiceException;
import it.memelabs.gn.util.GnServiceUtil;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilGenerics;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.service.DispatchContext;

import java.util.List;
import java.util.Map;

/**
 * 29/05/13
 *
 * @author Andrea Fossi
 */
public class CommunicationEventServices {
    private static final String module = CommunicationEventServices.class.getName();


    /**
     * Create CommunicationEvent
     *
     * @param ctx
     * @param context
     * @return
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> gnCreateCommunicationEvent(DispatchContext ctx, Map<String, Object> context) {
        try {
            Map<String, Object> result = GnServiceUtil.returnSuccess();
            CommunicationEventHelper contactListHelper = new CommunicationEventHelper(ctx, context);
            result.put("communicationEventId", contactListHelper.gnCreateCommunicationEvent(context));
            return result;
        } catch (Throwable e) {
            Debug.logError(e, module);
            return GnServiceUtil.returnError(e);
        }
    }

    /**
     * Find CommunicationEvent
     *
     * @param ctx
     * @param context
     * @return
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> gnFindCommunicationEventById(DispatchContext ctx, Map<String, Object> context) {
        try {
            Map<String, Object> result = GnServiceUtil.returnSuccess();
            String communicationEventId = (String) context.get("communicationEventId");
            CommunicationEventHelper contactListHelper = new CommunicationEventHelper(ctx, context);
            result.put("communicationEvent", contactListHelper.gnFindCommunicationEventById(communicationEventId));
            return result;
        } catch (Throwable e) {
            Debug.logError(e, module);
            return GnServiceUtil.returnError(e);
        }
    }

    /**
     * Find CommunicationEvent
     *
     * @param ctx
     * @param context
     * @return
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> gnFindCommunicationEvent(DispatchContext ctx, Map<String, Object> context) {
        try {
            Map<String, Object> result = GnServiceUtil.returnSuccess();
            List<String> statusIds = (List<String>) context.get("statusIds");
            List<String> eventTypeIds = (List<String>) context.get("eventTypeIds");
            List<String> communicationEventTypeIds = (List<String>) context.get("communicationEventTypeIds");
            List<String> communicationEventIds = (List<String>) context.get("communicationEventIds");
            List<String> partyIdsTo = (List<String>) context.get("partyIdsTo");
            List<Map<String, String>> orderByList = UtilGenerics.toList(context.get("orderBy"));
            Integer page = (Integer) context.get("page");
            Integer size = (Integer) context.get("size");

            CommunicationEventHelper communicationHelper = new CommunicationEventHelper(ctx, context);
            List<Map<String, Object>> events = communicationHelper.gnFindCommunicationEvent(communicationEventIds, statusIds, eventTypeIds, partyIdsTo, communicationEventTypeIds, orderByList, page, size);
            result.put("communicationEventList", events);
            result.put("communicationEventListSize", events.size());
            return result;
        } catch (Throwable e) {
            Debug.logError(e, module);
            return GnServiceUtil.returnError(e);
        }
    }

    /**
     * Find CommunicationEvent
     *
     * @param ctx
     * @param context
     * @return
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> gnFindCommunicationEventFilteredByUser(DispatchContext ctx, Map<String, Object> context) {
        try {
            Map<String, Object> result = GnServiceUtil.returnSuccess();
            List<String> statusIds = (List<String>) context.get("statusIds");
            List<String> eventTypeIds = (List<String>) context.get("eventTypeIds");
            List<String> partyIdsTo = UtilMisc.toList((String) ((Map) context.get("userLogin")).get("partyId"));
            List<String> communicationEventTypeIds = (List<String>) context.get("communicationEventTypeIds");
            List<String> communicationEventIds = (List<String>) context.get("communicationEventIds");
            List<Map<String, String>> orderByList = UtilGenerics.toList(context.get("orderBy"));
            Integer page = (Integer) context.get("page");
            Integer size = (Integer) context.get("size");

            CommunicationEventHelper communicationHelper = new CommunicationEventHelper(ctx, context);
            List<Map<String, Object>> events = communicationHelper.gnFindCommunicationEvent(communicationEventIds, statusIds, eventTypeIds, partyIdsTo, communicationEventTypeIds, orderByList, page, size);
            result.put("communicationEventList", events);
            result.put("communicationEventListSize", events.size());
            return result;
        } catch (Throwable e) {
            Debug.logError(e, module);
            return GnServiceUtil.returnError(e);
        }
    }

    /**
     * Update CommunicationEvent statusId attribute
     *
     * @param ctx
     * @param context
     * @return
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> gnUpdateCommunicationEventStatus(DispatchContext ctx, Map<String, Object> context) {
        try {
            Map<String, Object> result = GnServiceUtil.returnSuccess();
            String communicationEventId = (String) context.get("communicationEventId");
            String statusId = (String) context.get("statusId");
            CommunicationEventHelper contactListHelper = new CommunicationEventHelper(ctx, context);
            if (!contactListHelper.canUpdateCommunicationEventStatus(communicationEventId)) {
                throw new GnServiceException(OfbizErrors.ACCESS_DENIED,
                        "User cannot update status of communicationEvent[" + communicationEventId + "] ");
            }
            contactListHelper.gnUpdateCommunicationEventStatus(communicationEventId, statusId);
            return result;
        } catch (Throwable e) {
            Debug.logError(e, module);
            return GnServiceUtil.returnError(e);
        }
    }

    /**
     * Update CommunicationEvent statusId caused by delivered attribute
     *
     * @param ctx
     * @param context
     * @return
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> gnUpdateCommunicationEventStatusEM(DispatchContext ctx, Map<String, Object> context) {
        try {
            Map<String, Object> result = GnServiceUtil.returnSuccess();
            String communicationEventId = (String) context.get("communicationEventId");
            String delivered = (String) context.get("delivered");
            CommunicationEventHelper contactListHelper = new CommunicationEventHelper(ctx, context);
            if (!contactListHelper.canUpdateCommunicationEventStatus(communicationEventId)) {
                throw new GnServiceException(OfbizErrors.ACCESS_DENIED,
                        "User cannot update status of communicationEvent[" + communicationEventId + "] ");
            }
            if ("Y".equals(delivered)) {
                contactListHelper.gnUpdateCommunicationEventStatus(communicationEventId, CommunicationEventStatusOfbiz.COM_COMPLETE.name());
            } else {
                contactListHelper.gnUpdateCommunicationEventStatus(communicationEventId, CommunicationEventStatusOfbiz.COM_NOT_DELIVERED.name());
            }
            return result;
        } catch (Throwable e) {
            Debug.logError(e, module);
            return GnServiceUtil.returnError(e);
        }
    }

    /**
     * Find CommunicationEvent
     *
     * @param ctx
     * @param context
     * @return
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> gnRemoveCommunicationEvent(DispatchContext ctx, Map<String, Object> context) {
        try {
            Map<String, Object> result = GnServiceUtil.returnSuccess();
            String communicationEventId = (String) context.get("communicationEventId");
            CommunicationEventHelper contactListHelper = new CommunicationEventHelper(ctx, context);
            if (!contactListHelper.canUpdateCommunicationEventStatus(communicationEventId)) {
                throw new GnServiceException(OfbizErrors.ACCESS_DENIED,
                        "User cannot remove communicationEvent[" + communicationEventId + "] ");
            }
            contactListHelper.gnRemoveCommunicationEvent(communicationEventId);
            return result;
        } catch (Throwable e) {
            Debug.logError(e, module);
            return GnServiceUtil.returnError(e);
        }
    }

}
