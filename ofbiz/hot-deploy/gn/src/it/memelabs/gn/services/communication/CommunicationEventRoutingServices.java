package it.memelabs.gn.services.communication;

import it.memelabs.gn.util.GnServiceUtil;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.service.DispatchContext;

import java.util.Map;

/**
 * 31/05/13
 *
 * @author Andrea Fossi
 */
public class CommunicationEventRoutingServices {
    private static final String module = CommunicationEventRoutingServices.class.getName();

    /**
     * @param ctx
     * @param context
     * @return
     */
    public static Map<String, Object> gnSendCommunicationEventToContactList(DispatchContext ctx, Map<String, Object> context) {
        try {
            Map<String, Object> result = GnServiceUtil.returnSuccess();
            CommunicationEventRouterHelper communicationEventRouterHelper = new CommunicationEventRouterHelper(ctx, context);
            String ownerPartyId = (String)context.get("ownerPartyId");
            String eventTypeId = (String)context.get("eventTypeId");
            @SuppressWarnings("unchecked")
            Map<String, Object> attributes = (Map<String, Object>)context.get("attributes");
            result.put("communicationEventIds", communicationEventRouterHelper.gnSendCommunicationEventToContactList(ownerPartyId, eventTypeId, attributes));
            return result;
        } catch (Throwable e) {
            Debug.logError(e, module);
            return GnServiceUtil.returnError(e);
        }

    }
    /**
     * @param ctx
     * @param context
     * @return
     */
    public static Map<String, Object> gnSendCommunicationEventToContactListEM(DispatchContext ctx, Map<String, Object> context) {
        try {
            Map<String, Object> result = GnServiceUtil.returnSuccess();
            CommunicationEventRouterHelper communicationEventRouterHelper = new CommunicationEventRouterHelper(ctx, context);
            String ownerPartyId = (String)context.get("ownerPartyId");
            String eventTypeId = (String)context.get("eventTypeId");
            String overrideEventTypeId = (String)context.get("overrideEventTypeId");
            @SuppressWarnings("unchecked")
            Map<String, Object> attributes = (Map<String, Object>)context.get("attributes");
            result.put("communicationEvents", communicationEventRouterHelper.gnSendCommunicationEventToContactListEM(ownerPartyId, eventTypeId,overrideEventTypeId, attributes));
            return result;
        } catch (Throwable e) {
            Debug.logError(e, module);
            return GnServiceUtil.returnError(e);
        }
    }

    public static Map<String, Object> gnSendCommunicationEventToUser(DispatchContext ctx, Map<String, Object> context) {
        try {
            Map<String, Object> result = GnServiceUtil.returnSuccess();
            CommunicationEventRouterHelper communicationEventRouterHelper = new CommunicationEventRouterHelper(ctx, context);
            String userLoginId = (String)context.get("userLoginId");
            String communicationEventTypeId = (String)context.get("communicationEventTypeId");
            String eventTypeId = (String)context.get("eventTypeId");
            @SuppressWarnings("unchecked")
            Map<String, Object> attributes = (Map<String, Object>)context.get("attributes");
            result.put("communicationEventIds", UtilMisc.toList(communicationEventRouterHelper.gnSendCommunicationEventToUser(userLoginId, communicationEventTypeId, eventTypeId, attributes)));
            return result;
        } catch (Throwable e) {
            Debug.logError(e, module);
            return GnServiceUtil.returnError(e);
        }
    }
}
