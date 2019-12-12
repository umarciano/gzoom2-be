package it.memelabs.gn.services.communication;

import it.memelabs.gn.services.OfbizErrors;
import it.memelabs.gn.util.GnServiceException;
import it.memelabs.gn.util.GnServiceUtil;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.service.DispatchContext;
import org.ofbiz.service.LocalDispatcher;

import java.util.List;
import java.util.Map;

/**
 * 29/05/13
 *
 * @author Andrea Fossi
 */
public class ContactListServices {
    private static final String module = ContactListServices.class.getName();

    /**
     * Proxy method to create/update authorization
     *
     * @param ctx
     * @param context
     * @return
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> gnCreateUpdateContactList(DispatchContext ctx, Map<String, Object> context) {
        try {
            Map<String, Object> result = GnServiceUtil.returnSuccess();
            LocalDispatcher dispatcher = ctx.getDispatcher();
            if (UtilValidate.isEmpty(context.get("contactListId"))) {
                result.putAll(dispatcher.runSync("gnCreateContactList", dispatcher.getDispatchContext().makeValidContext("gnCreateContactList", "IN", context)));
            } else {
                result.putAll(dispatcher.runSync("gnUpdateContactList", dispatcher.getDispatchContext().makeValidContext("gnUpdateContactList", "IN", context)));
            }
            return result;
        } catch (Throwable e) {
            Debug.logError(e, module);
            return GnServiceUtil.returnError(e);
        }
    }

    /**
     * Proxy method to create/update authorization
     *
     * @param ctx
     * @param context
     * @return
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> gnCreateContactList(DispatchContext ctx, Map<String, Object> context) {
        try {
            Map<String, Object> result = GnServiceUtil.returnSuccess();
            ContactListHelper contactListHelper = new ContactListHelper(ctx, context);
            List<String> userLoginIds = (List<String>) context.get("userLoginIds");
            List<String> communicationEventTypeIds = (List<String>) context.get("communicationEventTypeIds");//NotificationChannelKind
            List<String> eventTypeIds = (List<String>) context.get("eventTypeIds");//EventKind
            String nodeKey = (String) context.get("nodeKey");
            String partyId = (String) context.get("partyId");
            partyId = contactListHelper.getPartyId(partyId, nodeKey);
            String contactListName = (String) context.get("contactListName");
            checkParamsContactList(partyId, eventTypeIds);
            result.put("contactListId", contactListHelper.gnCreateContactList(contactListName, partyId, userLoginIds, communicationEventTypeIds, eventTypeIds));
            return result;
        } catch (Throwable e) {
            Debug.logError(e, module);
            return GnServiceUtil.returnError(e);
        }
    }

    /**
     * Proxy method to create/update authorization
     *
     * @param ctx
     * @param context
     * @return
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> gnUpdateContactList(DispatchContext ctx, Map<String, Object> context) {
        try {
            Map<String, Object> result = GnServiceUtil.returnSuccess();
            ContactListHelper contactListHelper = new ContactListHelper(ctx, context);
            List<String> userLoginIds = (List<String>) context.get("userLoginIds");
            List<String> communicationEventTypeIds = (List<String>) context.get("communicationEventTypeIds");//NotificationChannelKind
            List<String> eventTypeIds = (List<String>) context.get("eventTypeIds");//EventKind
            String nodeKey = (String) context.get("nodeKey");
            String partyId = (String) context.get("partyId");
            String contactListId = (String) context.get("contactListId");
            partyId = contactListHelper.getPartyId(partyId, nodeKey);
            String contactListName = (String) context.get("contactListName");
            checkParamsContactList(partyId, eventTypeIds);
            result.put("contactListId", contactListHelper.gnUpdateContactList(contactListId, contactListName, partyId, userLoginIds, communicationEventTypeIds, eventTypeIds));
            return result;
        } catch (Throwable e) {
            Debug.logError(e, module);
            return GnServiceUtil.returnError(e);
        }
    }

    private static void checkParamsContactList(String partyId, List<String> eventTypeIds) throws GnServiceException {
        if (UtilValidate.isEmpty(partyId))
            throw new GnServiceException(OfbizErrors.INVALID_PARAMETERS, "nodeKey and partyId are empty");
        for (String eventTypeId : eventTypeIds) {
            if (!EventTypeOfbiz.valueOf(eventTypeId).isSubscribable())
                throw new GnServiceException(OfbizErrors.NOT_ENOUGH_PERMISSIONS, "The '" + eventTypeId + "' event type can never be associated to a contact list!");
        }
    }

    /**
     * Proxy method to create/update authorization
     *
     * @param ctx
     * @param context
     * @return
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> gnFindContactListById(DispatchContext ctx, Map<String, Object> context) {
        try {
            Map<String, Object> result = GnServiceUtil.returnSuccess();
            ContactListHelper contactListHelper = new ContactListHelper(ctx, context);
            String contactListId = (String) context.get("contactListId");
            result.put("contactList", contactListHelper.findContactListById(contactListId));
            return result;
        } catch (Throwable e) {
            Debug.logError(e, module);
            return GnServiceUtil.returnError(e);
        }
    }

    /**
     * Proxy method to create/update authorization
     *
     * @param ctx
     * @param context
     * @return
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> gnFindContactList(DispatchContext ctx, Map<String, Object> context) {
        try {
            Map<String, Object> result = GnServiceUtil.returnSuccess();
            ContactListHelper contactListHelper = new ContactListHelper(ctx, context);
            List<String> ownerPartyIds = (List<String>) context.get("ownerPartyIds");
            List<String> eventTypeIds = (List<String>) context.get("eventTypeIds");
            // String ownerNodeKey = (String) context.get("ownerNodeKey");
            //ownerPartyId = contactListHelper.getPartyId(ownerPartyId, ownerNodeKey);
            result.put("contactLists", contactListHelper.findContactList(ownerPartyIds, eventTypeIds));
            return result;
        } catch (Throwable e) {
            Debug.logError(e, module);
            return GnServiceUtil.returnError(e);
        }
    }

    /**
     * Proxy method to create/update authorization
     *
     * @param ctx
     * @param context
     * @return
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> gnRemoveContactList(DispatchContext ctx, Map<String, Object> context) {
        try {
            Map<String, Object> result = GnServiceUtil.returnSuccess();
            ContactListHelper contactListHelper = new ContactListHelper(ctx, context);
            String contactListId = (String) context.get("contactListId");
            String nodeKey = (String) context.get("nodeKey");
            String partyId = (String) context.get("partyId");
            partyId = contactListHelper.getPartyId(partyId, nodeKey);
            contactListHelper.removeContactList(contactListId, partyId);
            return result;
        } catch (Throwable e) {
            Debug.logError(e, module);
            return GnServiceUtil.returnError(e);
        }
    }
}
