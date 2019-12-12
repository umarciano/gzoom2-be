package it.memelabs.gn.services.communication;

import it.memelabs.gn.services.AbstractServiceHelper;
import it.memelabs.gn.services.AttributeTypeOfbiz;
import it.memelabs.gn.services.OfbizErrors;
import it.memelabs.gn.services.security.GnSecurity;
import it.memelabs.gn.services.security.PermissionsOfbiz;
import it.memelabs.gn.util.GnServiceException;
import it.memelabs.gn.util.XmlUtil;
import it.memelabs.gn.util.find.GnFindUtil;
import it.memelabs.gn.webapp.event.CommunicationEventSessionHelper;
import javolution.util.FastList;
import javolution.util.FastMap;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.GeneralException;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.entity.condition.EntityCondition;
import org.ofbiz.service.DispatchContext;
import org.ofbiz.service.GenericServiceException;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 29/05/13
 *
 * @author Andrea Fossi
 */
public class CommunicationEventHelper extends AbstractServiceHelper {
    private static final String module = CommunicationEventHelper.class.getName();

    /**
     * @param dctx    OFBiz DispatchContext
     * @param context Service Context
     */
    public CommunicationEventHelper(DispatchContext dctx, Map<String, ? extends Object> context) {
        super(dctx, context);
    }

    /**
     * @param params
     * @return
     * @throws GenericServiceException
     * @throws GenericEntityException
     */

    public String gnCreateCommunicationEvent(Map<String, Object> params) throws GeneralException {
        Map<String, Object> srvRequest = dispatcher.getDispatchContext().makeValidContext("createCommunicationEventWithoutPermission", "IN", params);
        if (UtilValidate.isEmpty(srvRequest.get("statusId")))
            srvRequest.put("statusId", CommunicationEventStatusOfbiz.COM_PENDING.name());
        //communicationEventType is passed
        String partyIdTo = (String) params.get("partyIdTo");
        String userLoginIdTo = (String) params.get("userLoginIdTo");

        if (UtilValidate.isEmpty(partyIdTo) && UtilValidate.isNotEmpty(userLoginIdTo))
            partyIdTo = getUserLoginPartyId(userLoginIdTo);
        srvRequest.put("partyIdTo", partyIdTo);
        Map<String, Object> result = dispatcher.runSync("createCommunicationEventWithoutPermission", srvRequest);
        String communicationEventId = (String) result.get("communicationEventId");
        Debug.log("Added CommunicationEvent[" + communicationEventId + "]", module);
        String eventTypeId = (String) params.get("eventTypeId");
        createCommunicationEventPurpose(communicationEventId, "GN_INTERNAL_COMM", eventTypeId);

        @SuppressWarnings("unchecked")
        Map<String, Object> attributes = (Map<String, Object>) context.get("attributes");
        saveCommunicationEventAttribute(communicationEventId, attributes);

        CommunicationEventSessionHelper.putCommunicationEventId(communicationEventId);

        return communicationEventId;
    }

    public boolean canUpdateCommunicationEventStatus(String communicationEventId) throws GenericEntityException, GnServiceException {
        GnSecurity gnSecurity = new GnSecurity(delegator);
        if (gnSecurity.hasPermission(PermissionsOfbiz.GN_COMM_ADMIN.name(), userLogin)) {
            return true;
        } else {
            GenericValue ev = delegator.findOne("CommunicationEvent", false, "communicationEventId", communicationEventId);
            return UtilValidate.isNotEmpty(userLogin.get("partyId")) && UtilValidate.areEqual(userLogin.get("partyId"), ev.getString("partyIdTo"));
        }
    }

    public void gnUpdateCommunicationEventStatus(String communicationEventId, String statusId) throws GenericEntityException, GnServiceException {
        GenericValue ev = delegator.findOne("CommunicationEvent", false, "communicationEventId", communicationEventId);
        if (ev == null)
            throw new GnServiceException(OfbizErrors.ENTITY_EXCEPTION, "CommunicationEvent[" + communicationEventId + "] not found.");
        ev.set("statusId", statusId);
        delegator.store(ev);
    }

    public void saveCommunicationEventAttribute(String communicationEventId, Map<String, Object> attributes) throws GeneralException {
        if (UtilValidate.isEmpty(attributes))
            return;
        for (String key : attributes.keySet()) {
            Object value = attributes.get(key);
            String attrValue;
            String attrTypeId;
            if (UtilValidate.isEmpty(value) || value instanceof String) {
                attrValue = (String) value;
                attrTypeId = AttributeTypeOfbiz.GN_ATTR_STRING.name();
            } else {
                attrValue = XmlUtil.toXml(attributes.get(key));
                attrTypeId = AttributeTypeOfbiz.GN_ATTR_XML.name();
            }
            GenericValue gv = delegator.makeValue("GnCommunicationEventAttribute");
            gv.set("communicationEventId", communicationEventId);
            gv.set("attrName", key);
            gv.set("attrValue", attrValue);
            gv.put("attrTypeId", attrTypeId);
            delegator.create(gv);
        }
        Debug.log("Added CommunicationEvent[" + communicationEventId + "] attributes.", module);

    }

    private void createCommunicationEventPurpose(String communicationEventId, String communicationEventPrpTypId, String eventTypeId) throws GeneralException {
        Map<String, Object> srvRequest = FastMap.newInstance();

        srvRequest.put("userLogin", userLogin);
        srvRequest.put("communicationEventId", communicationEventId);
        srvRequest.put("communicationEventPrpTypId", communicationEventPrpTypId);
        if (UtilValidate.isNotEmpty(eventTypeId))
            srvRequest.put("eventTypeId", eventTypeId);
        Map<String, Object> result = dispatcher.runSync("createCommunicationEventPurpose", srvRequest);
        Debug.log("Added CommunicationEventPurpose[" + communicationEventId + "," + communicationEventPrpTypId + "]", module);
    }

    public Map<String, Object> gnFindCommunicationEventById(String communicationEventId) throws GenericEntityException, GenericServiceException {
        GenericValue _ev = delegator.findOne("CommunicationEvent", false, "communicationEventId", communicationEventId);
        if (_ev == null)
            throw new GnServiceException(OfbizErrors.ENTITY_EXCEPTION, "CommunicationEvent[" + communicationEventId + "] not found.");
        Map<String, Object> ev = UtilMisc.makeMapWritable(_ev);
        String eventTypeId = findCommunicationEventPurposeType(communicationEventId, "GN_INTERNAL_COMM").getString("eventTypeId");
        ev.put("eventTypeId", eventTypeId);

        ev.put("user", getUserProfile(getUserLoginId(_ev.getString("partyIdTo"))));

        ev.put("attributes", findCommunicationEventAttribute(communicationEventId));

        return ev;
    }

    public List<Map<String, Object>> gnFindCommunicationEvent(List<String> communicationEventIds, List<String> statusIds, List<String> eventTypeIds, List<String> partyIdsTo, List<String> communicationEventTypeIds, List<Map<String, String>> orderByList, int page, int size) throws GenericEntityException, GenericServiceException {
        List<EntityCondition> conds = FastList.newInstance();

        if (UtilValidate.isNotEmpty(statusIds)) {
            conds.add(GnFindUtil.makeOrConditionById("statusId", statusIds));
        }

        if (UtilValidate.isNotEmpty(communicationEventTypeIds)) {
            conds.add(GnFindUtil.makeOrConditionById("communicationEventTypeId", communicationEventTypeIds));
        }

        if (UtilValidate.isNotEmpty(eventTypeIds)) {
            conds.add(GnFindUtil.makeOrConditionById("eventTypeId", eventTypeIds));
        }

        if (UtilValidate.isNotEmpty(communicationEventIds)) {
            conds.add(GnFindUtil.makeOrConditionById("communicationEventId", communicationEventIds));
        }

        if (UtilValidate.isNotEmpty(partyIdsTo)) {
            conds.add(GnFindUtil.makeOrConditionById("partyIdTo", partyIdsTo));
        }

        List<String> orderBy = FastList.<String>newInstance();
        if (UtilValidate.isNotEmpty(orderByList)) {
            for (Map<String, String> orderByMap : orderByList) {
                for (Entry<String, String> orderItem : orderByMap.entrySet()) {
                    orderBy.add(orderItem.getKey() + " " + orderItem.getValue());
                }
            }
        }

        if (UtilValidate.isEmpty(orderBy)) {
            // default sort by communicationEventId
            orderBy = UtilMisc.toList("communicationEventId DESC");
        }

        List<GenericValue> evs = GnFindUtil.find(delegator, "GnCommunicationEventAndPurpose", EntityCondition.makeCondition(conds), null, null, orderBy, null, page, size);
        List<Map<String, Object>> events = FastList.newInstance();

        for (GenericValue _ev : evs) {
            Map<String, Object> ev = UtilMisc.makeMapWritable(_ev);
            String communicationEventId = _ev.getString("communicationEventId");
            ev.put("user", getUserProfile(getUserLoginId(_ev.getString("partyIdTo"))));
            ev.put("attributes", findCommunicationEventAttribute(communicationEventId));
            events.add(ev);
        }

        return events;
    }

    private GenericValue findCommunicationEventPurposeType(String communicationEventId, String communicationEventPrpTypId) throws GenericEntityException {
        return delegator.findOne("CommunicationEventPurpose", false, "communicationEventId", communicationEventId, "communicationEventPrpTypId", communicationEventPrpTypId);
    }

    public Map<String, Object> findCommunicationEventAttribute(String communicationEventId) throws GenericEntityException, GnServiceException {
        Map<String, Object> attributes = FastMap.newInstance();
        List<GenericValue> result = delegator.findByAnd("GnCommunicationEventAttribute", "communicationEventId", communicationEventId);
        for (GenericValue attr : result) {
            String attrValue = attr.getString("attrValue");
            String attrTypeId = attr.getString("attrTypeId");
            Object value = null;
            if (AttributeTypeOfbiz.GN_ATTR_XML.name().equals(attrTypeId) && UtilValidate.isNotEmpty(attrValue)) {
                value = XmlUtil.fromXml(attrValue);
            } else {
                value = attrValue;
            }
            attributes.put(attr.getString("attrName"), value);
        }
        return attributes;
    }

    public void gnRemoveCommunicationEvent(String communicationEventId) throws GenericEntityException, GenericServiceException {
        removeCommunicationEventAttributes(communicationEventId);
        removeCommunicationEventPurpose(communicationEventId, "GN_INTERNAL_COMM");
        delegator.removeByAnd("CommunicationEventRole", "communicationEventId", communicationEventId);
        /*dispatcher.runSync("removeCommunicationEventRole",
                UtilMisc.toMap("userLogin", userLogin, "communicationEventId", communicationEventId, "partyId", ev.getString("partyIdTo")));*/
        GenericValue ev = delegator.findOne("CommunicationEvent", false, "communicationEventId", communicationEventId);
        delegator.removeValue(ev);
        Debug.log("Removed CommunicationEventPurpose[" + communicationEventId + "]", module);
    }

    public void removeCommunicationEventAttributes(String communicationEventId) throws GenericEntityException {
        delegator.removeByAnd("GnCommunicationEventAttribute", "communicationEventId", communicationEventId);

    }

    private void removeCommunicationEventPurpose(String communicationEventId, String communicationEventPrpTypId) throws GenericServiceException {
        Map<String, Object> srvRequest = FastMap.newInstance();

        srvRequest.put("userLogin", userLogin);
        srvRequest.put("communicationEventId", communicationEventId);
        srvRequest.put("communicationEventPrpTypId", communicationEventPrpTypId);

        Map<String, Object> result = dispatcher.runSync("removeCommunicationEventPurpose", srvRequest);
        Debug.log("Removed CommunicationEventPurpose[" + communicationEventId + "," + communicationEventPrpTypId + "]", module);
    }


    private String getUserLoginId(String partyId) throws GenericServiceException {
        Map<String, Object> result = dispatcher.runSync("gnFindUsers", UtilMisc.toMap("userLogin", userLogin, "partyId", partyId));
        @SuppressWarnings("unchecked")
        List<Map<String, Object>> users = (List<Map<String, Object>>) result.get("users");
        return (String) users.get(0).get("userLoginId");
    }

    private String getUserLoginPartyId(String userLoginId) throws GenericServiceException {
        Map<String, Object> result = dispatcher.runSync("gnFindUsers", UtilMisc.toMap("userLogin", userLogin, "userLoginId", userLoginId));
        @SuppressWarnings("unchecked")
        List<Map<String, Object>> users = (List<Map<String, Object>>) result.get("users");
        return (String) users.get(0).get("partyId");
    }

    private Map<String, Object> getUserProfile(String userLoginId) throws GenericServiceException {
        Map<String, Object> userProfile = dispatcher.runSync("gnGetUserProfile", UtilMisc.toMap("userLogin", userLogin, "userLoginId", userLoginId));
        return userProfile;
    }

}