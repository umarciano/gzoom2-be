package it.memelabs.gn.services.communication;

import it.memelabs.gn.services.AbstractServiceHelper;
import it.memelabs.gn.services.OfbizErrors;
import it.memelabs.gn.services.auditing.EntityTypeMap;
import it.memelabs.gn.services.auditing.EntityTypeOfbiz;
import it.memelabs.gn.util.GnServiceException;
import it.memelabs.gn.util.find.GnFindUtil;
import it.memelabs.gn.webapp.event.AuditEvent;
import it.memelabs.gn.webapp.event.AuditEventSessionHelper;
import javolution.util.FastList;
import org.ofbiz.base.util.Debug;
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
import java.util.Set;

/**
 * 29/05/13
 *
 * @author Andrea Fossi
 */
public class ContactListHelper extends AbstractServiceHelper {
    private static final String module = ContactListHelper.class.getName();
    private ContactListAssocHelper assocHelper;


    /**
     * @param dctx    OFBiz DispatchContext
     * @param context Service Context
     */
    public ContactListHelper(DispatchContext dctx, Map<String, ? extends Object> context) {
        super(dctx, context);
        assocHelper = new ContactListAssocHelper(dctx, context);
    }

    /**
     * @param contactListName
     * @param partyId
     * @param userLoginIds
     * @param communicationEventTypeIds
     * @param eventTypeIds
     * @return
     * @throws GenericServiceException
     * @throws GenericEntityException
     */
    public String gnCreateContactList(String contactListName, String partyId, List<String> userLoginIds,
                                      List<String> communicationEventTypeIds, List<String> eventTypeIds) throws GenericServiceException, GenericEntityException {
        Debug.log("Adding ContactList", module);
        AuditEvent event = new AuditEvent(EntityTypeOfbiz.CONTACT_LIST);
        Map<String, Object> srvCall = UtilMisc.toMap("userLogin", (Object) userLogin, "contactListName", contactListName, "contactListTypeId", "GN_NOTIFICATION_GRP", "ownerPartyId", partyId);
        Map<String, Object> result = dispatcher.runSync("createContactList", srvCall);
        String contactListId = (String) result.get("contactListId");
        Debug.log("ContactList[" + contactListId + "] added", module);

        assocHelper.createContactListUsers(contactListId, userLoginIds);
        assocHelper.createCommunicationEventTypeAss(contactListId, communicationEventTypeIds);
        assocHelper.createEventTypeAss(contactListId, eventTypeIds);
        Debug.log("Created ContactList[" + contactListId + "]", module);

        event.setNewValue(findContactListById(contactListId));
        event.addFallback(getNodeKey(partyId), getEntityType(partyId));
        AuditEventSessionHelper.putAuditEvent(event);
        return contactListId;
    }

    /**
     * @param contactListId
     * @param contactListName
     * @param partyId
     * @param userLoginIds
     * @param communicationEventTypeIds
     * @param eventTypeIds
     * @return
     * @throws GenericServiceException
     * @throws GenericEntityException
     */
    public String gnUpdateContactList(String contactListId, String contactListName, String partyId, List<String> userLoginIds,
                                      List<String> communicationEventTypeIds, List<String> eventTypeIds) throws GenericServiceException, GenericEntityException {
        Debug.log("Adding ContactList", module);
        AuditEvent event = new AuditEvent(EntityTypeOfbiz.CONTACT_LIST);
        event.setOldValue(findContactListById(contactListId));

        Map<String, Object> srvCall = UtilMisc.toMap("userLogin", (Object) userLogin, "contactListId", contactListId, "contactListName", contactListName, "contactListTypeId", "GN_NOTIFICATION_GRP", "ownerPartyId", partyId);
        Map<String, Object> result = dispatcher.runSync("updateContactList", srvCall);
        Debug.log("ContactList[" + contactListId + "] added", module);
        assocHelper.removeContactListUsers(contactListId);
        assocHelper.createContactListUsers(contactListId, userLoginIds);

        assocHelper.removeCommunicationEventTypeAss(contactListId);
        assocHelper.createCommunicationEventTypeAss(contactListId, communicationEventTypeIds);

        assocHelper.removeEventTypeAss(contactListId);
        assocHelper.createEventTypeAss(contactListId, eventTypeIds);
        Debug.log("Created ContactList[" + contactListId + "]", module);

        event.setNewValue(findContactListById(contactListId));
        event.addFallback(getNodeKey(partyId), getEntityType(partyId));
        AuditEventSessionHelper.putAuditEvent(event);
        return contactListId;
    }

    /**
     * @param contactListId
     * @return
     * @throws GenericEntityException
     */
    public Map<String, Object> findContactListById(String contactListId) throws GenericEntityException, GenericServiceException {
        GenericValue _contactList = delegator.findOne("ContactList", UtilMisc.toMap("contactListId", contactListId), false);
        if (_contactList == null)
            throw new GnServiceException(OfbizErrors.ENTITY_EXCEPTION, "ContactList[" + contactListId + "] not found");
        Map<String, Object> contactList = UtilMisc.makeMapWritable(_contactList);
        contactList.put("users", assocHelper.findContactListUsers(contactListId));
        contactList.put("communicationEventTypes", assocHelper.findCommunicationEventTypes(contactListId));
        contactList.put("eventTypes", assocHelper.findEventTypes(contactListId));
        return contactList;
    }

    public List<Map<String, Object>> findContactList(List<String> ownerPartyIds, List<String> eventTypeIds) throws GenericEntityException, GenericServiceException {
        List<EntityCondition> conds = FastList.newInstance();

        if (UtilValidate.isNotEmpty(ownerPartyIds)) {
            conds.add(GnFindUtil.makeOrConditionById("ownerPartyId", ownerPartyIds));
        }

        if (UtilValidate.isNotEmpty(eventTypeIds)) {
            conds.add(GnFindUtil.makeOrConditionById("enumId", eventTypeIds));
        }

        Set<String> fieldToSelect = UtilMisc.toSet("contactListId");
        List<GenericValue> contactLists = delegator.findList("GnContactListEventType", EntityCondition.makeCondition(conds), fieldToSelect, UtilMisc.toList("contactListId"), GnFindUtil.getFindOptDistinct(), false);

        List<Map<String, Object>> result = FastList.newInstance();
        for (GenericValue _contactList : contactLists) {
            result.add(findContactListById(_contactList.getString("contactListId")));
        }

        return result;
    }

    /**
     * @param contactListId
     * @return
     * @throws GenericEntityException
     */
    public void removeContactList(String contactListId, String partyId) throws GenericEntityException, GenericServiceException {
        GenericValue contactList = delegator.findOne("ContactList", UtilMisc.toMap("contactListId", contactListId), false);
        if (UtilValidate.isNotEmpty(partyId) && !UtilValidate.areEqual(partyId, contactList.getString("ownerPartyId")))
            throw new GnServiceException(OfbizErrors.INVALID_PARAMETERS, "PartyId[" + partyId + "] is now owner of ContactList[" + contactListId + "]");

        AuditEvent event = new AuditEvent(EntityTypeOfbiz.CONTACT_LIST);
        event.setOldValue(findContactListById(contactListId));

        assocHelper.removeContactListUsers(contactListId);
        assocHelper.removeCommunicationEventTypeAss(contactListId);
        assocHelper.removeEventTypeAss(contactListId);

        delegator.removeValue(contactList);
        Debug.log("Removed ContactList[" + contactListId + "]", module);
        event.addFallback(getNodeKey(contactList.getString("ownerPartyId")), getEntityType(contactList.getString("ownerPartyId")));
        AuditEventSessionHelper.putAuditEvent(event);
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
            return (String) dctx.getDispatcher().runSync("gnGetPartyIdFromNodeKey", UtilMisc.toMap("userLogin", context.get("userLogin"), "nodeKey", nodeKey)).get("partyId");
        else return partyId;
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

    public EntityTypeOfbiz getEntityType(String partyId) throws GenericServiceException {
        Map<String, Object> result = dispatcher.runSync("gnInternalFindPartyNodeById", UtilMisc.toMap("userLogin", context.get("userLogin"), "findRelationships", "N", "partyId", partyId));
        String nodeType = (String) UtilGenerics.checkMap(result.get("partyNode")).get("nodeType");
        EntityTypeOfbiz ret = EntityTypeMap.getPartyNodeTypeFromEntityTypeId(nodeType);
        if (ret == null)
            throw new GnServiceException(OfbizErrors.INVALID_PARAMETERS, String.format("Node[%s] is not a propagation node.", partyId));
        return ret;


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

}
