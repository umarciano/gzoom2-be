package it.memelabs.gn.services.communication;

import it.memelabs.gn.services.AbstractServiceHelper;
import it.memelabs.gn.services.OfbizErrors;
import it.memelabs.gn.services.login.LoginSourceOfbiz;
import it.memelabs.gn.util.GnServiceException;
import javolution.util.FastList;
import org.ofbiz.base.util.*;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.entity.condition.EntityCondition;
import org.ofbiz.entity.model.DynamicViewEntity;
import org.ofbiz.entity.model.ModelKeyMap;
import org.ofbiz.entity.util.EntityListIterator;
import org.ofbiz.service.DispatchContext;
import org.ofbiz.service.GenericServiceException;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

/**
 * 29/05/13
 *
 * @author Andrea Fossi
 */
public class ContactListAssocHelper extends AbstractServiceHelper {
    private static final String module = ContactListAssocHelper.class.getName();

    /**
     * @param dctx    OFBiz DispatchContext
     * @param context Service Context
     */
    public ContactListAssocHelper(DispatchContext dctx, Map<String, ? extends Object> context) {
        super(dctx, context);
    }

    /**
     * @param contactListId
     * @return
     * @throws GenericEntityException
     * @throws GenericServiceException
     */
    public List<Map<String, Object>> findContactListUsers(String contactListId) throws GenericEntityException, GenericServiceException {
        DynamicViewEntity dv = new DynamicViewEntity();
        dv.addMemberEntity("CLP", "ContactListParty");
        dv.addMemberEntity("ULP", "PartyAndUserLoginAndPerson");
        dv.addAliasAll("ULP", null);
        dv.addAlias("CLP", "contactListId");
        dv.addViewLink("CLP", "ULP", false, UtilMisc.toList(new ModelKeyMap("partyId", "partyId")));
        EntityListIterator it = delegator.findListIteratorByCondition(dv, EntityCondition.makeCondition("contactListId", (Object) contactListId), null, null, UtilMisc.toList("partyId"), null);
        List<Map<String, Object>> result = FastList.newInstance();
        GenericValue next = it.next();
        while (next != null) {
            //Map<String, Object> ul = UtilMisc.makeMapWritable(next);
            //ul.remove("contactListId");
            result.add(getUserProfile(next.getString("userLoginId")));
            next = it.next();
        }
        it.close();
        return result;
    }

    /**
     * @param contactListId
     * @param userLoginIds
     * @throws GenericEntityException
     * @throws GenericServiceException
     */
    void createContactListUsers(String contactListId, List<String> userLoginIds) throws GenericEntityException, GenericServiceException {
        if (UtilValidate.isEmpty(userLoginIds)) return;
        Timestamp fromDate = UtilDateTime.nowTimestamp();
        for (String userLoginId : userLoginIds) {
            Map<String, Object> _userLogin = findUserLoginById(userLoginId);
            List<String> userLoginSourceIds = findLoginSourceIds(userLoginId);
            if (!userLoginSourceIds.contains(LoginSourceOfbiz.GN_LOG_SRC_WEB_UI.name()))
                throw new GnServiceException(OfbizErrors.INVALID_PARAMETERS, String.format("contactList can be associated to a userLogin with loginSourceId[%s]", LoginSourceOfbiz.GN_LOG_SRC_WEB_UI.name()));
            String userLoginPartyId = (String) _userLogin.get("partyId");
            GenericValue contactListParty = delegator.makeValue("ContactListParty");
            contactListParty.set("contactListId", contactListId);
            contactListParty.set("fromDate", fromDate);
            contactListParty.set("partyId", userLoginPartyId);
            contactListParty.set("statusId", "CLPT_IN_USE");
            delegator.create(contactListParty);
            Debug.log("Created ContactListParty contactListId[" + contactListId + "] partyId[" + userLoginPartyId + "]", module);
        }
    }

    void removeContactListUsers(String contactListId) throws GenericEntityException {
        delegator.removeByAnd("ContactListParty", UtilMisc.toMap("contactListId", contactListId));
    }

    /**
     * @param contactListId
     * @param communicationEventTypeIds
     * @throws GenericEntityException
     */
    public void createCommunicationEventTypeAss(String contactListId, List<String> communicationEventTypeIds) throws GenericEntityException {
        if (UtilValidate.isEmpty(communicationEventTypeIds)) return;
        for (String communicationEventTypeId : communicationEventTypeIds) {
            GenericValue contactListParty = delegator.makeValue("GnContactListCommunicationEventTypeAssoc");
            contactListParty.set("contactListId", contactListId);
            contactListParty.set("communicationEventTypeId", communicationEventTypeId);
            delegator.create(contactListParty);
            Debug.log("Created GnContactListCommunicationEventTypeAssoc contactListId[" + contactListId + "] CommunicationEventType[" + communicationEventTypeId + "]", module);
        }
    }

    /**
     * @param contactListId
     * @throws GenericEntityException
     */
    void removeCommunicationEventTypeAss(String contactListId) throws GenericEntityException {
        delegator.removeByAnd("GnContactListCommunicationEventTypeAssoc", UtilMisc.toMap("contactListId", contactListId));
        Debug.log("Removed GnContactListCommunicationEventTypeAssoc contactListId[" + contactListId + "]", module);
    }

    /**
     * @param contactListId
     * @return
     * @throws GenericEntityException
     */
    public List<GenericValue> findCommunicationEventTypes(String contactListId) throws GenericEntityException {
        List<GenericValue> result = delegator.findByAnd("GnContactListCommunicationEventTypeAssoc", "contactListId", contactListId);
        List<GenericValue> types = FastList.newInstance();
        for (GenericValue type : result) {
            types.addAll(type.getRelated("CommunicationEventType", UtilMisc.toList("communicationEventTypeId")));
        }
        return types;
    }

    /**
     * @param contactListId
     * @param eventTypeIds
     * @throws GenericEntityException
     */
    public void createEventTypeAss(String contactListId, List<String> eventTypeIds) throws GenericEntityException {
        if (UtilValidate.isEmpty(eventTypeIds)) return;
        for (String eventTypeId : eventTypeIds) {
            GenericValue contactListParty = delegator.makeValue("GnContactListEventTypeAssoc");
            contactListParty.set("contactListId", contactListId);
            contactListParty.set("enumId", eventTypeId);
            delegator.create(contactListParty);
            Debug.log("Created GnContactListEventTypeAssoc contactListId[" + contactListId + "] CommunicationEventType[" + eventTypeId + "]", module);
        }
    }

    /**
     * @param contactListId
     * @throws GenericEntityException
     */
    void removeEventTypeAss(String contactListId) throws GenericEntityException {
        delegator.removeByAnd("GnContactListEventTypeAssoc", UtilMisc.toMap("contactListId", contactListId));
        Debug.log("Removed GnContactListEventTypeAssoc contactListId[" + contactListId + "]", module);
    }

    /**
     * @param contactListId
     * @return
     * @throws GenericEntityException
     */
    public List<GenericValue> findEventTypes(String contactListId) throws GenericEntityException {
        List<GenericValue> result = delegator.findByAnd("GnContactListEventTypeAssoc", "contactListId", contactListId);
        List<GenericValue> types = FastList.newInstance();
        for (GenericValue type : result) {
            types.addAll(type.getRelated("Enumeration", UtilMisc.toList("enumId")));
        }
        return types;
    }

    private String getUserLoginId(String partyId) throws GenericServiceException {
        Map<String, Object> result = dispatcher.runSync("gnFindUsers",
                UtilMisc.toMap("userLogin", userLogin, "partyId", partyId));
        List<Map<String, Object>> users = UtilGenerics.checkList(result.get("users"));
        return (String) users.get(0).get("userLoginId");
    }

    private Map<String, Object> findUserLoginById(String userLoginId) throws GenericServiceException {
        Map<String, Object> result = dispatcher.runSync("gnFindUsers",
                UtilMisc.toMap("userLogin", userLogin, "userLoginId", userLoginId));
        List<Map<String, Object>> users = UtilGenerics.checkList(result.get("users"));
        return users.get(0);
    }

    private Map<String, Object> getUserProfile(String userLoginId) throws GenericServiceException {
        Map<String, Object> userProfile = dispatcher.runSync("gnGetUserProfile",
                UtilMisc.toMap("userLogin", userLogin, "userLoginId", userLoginId));
        return userProfile;
    }

    /**
     * @param userLoginId
     * @return
     * @throws GenericServiceException
     */
    public List<String> findLoginSourceIds(String userLoginId) throws GenericServiceException {
        Map<String, Object> result = dispatcher.runSync("gnFindLoginSourceId", UtilMisc.toMap("userLogin", userLogin, "userLoginId", userLoginId));
        List<String> loginSourceIds = UtilMisc.getListFromMap(result, "loginSourceIds");
        return loginSourceIds;
    }
}
