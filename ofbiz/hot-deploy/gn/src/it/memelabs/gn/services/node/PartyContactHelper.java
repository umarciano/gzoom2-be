package it.memelabs.gn.services.node;

import it.memelabs.gn.services.AbstractServiceHelper;
import it.memelabs.gn.services.OfbizErrors;
import it.memelabs.gn.util.GnServiceException;
import it.memelabs.gn.util.MapUtil;
import javolution.util.FastMap;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.entity.condition.EntityCondition;
import org.ofbiz.entity.condition.EntityConditionList;
import org.ofbiz.entity.condition.EntityFieldMap;
import org.ofbiz.entity.util.EntityUtil;
import org.ofbiz.service.DispatchContext;
import org.ofbiz.service.GenericServiceException;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 31/01/13
 *
 * @author Andrea Fossi
 */
public class PartyContactHelper extends AbstractServiceHelper {
    private static final String module = PartyContactHelper.class.getName();

    public PartyContactHelper(DispatchContext dctx, Map<String, ? extends Object> context) {
        super(dctx, context);
    }

    /**
     * @param partyId
     * @param contactMechTypeId
     * @param contactMechPurposeTypeId
     * @param value
     * @return
     * @throws org.ofbiz.entity.GenericEntityException
     * @throws org.ofbiz.service.GenericServiceException
     */

    protected String saveUpdatePartyContact(String partyId, String contactMechTypeId, String contactMechPurposeTypeId, String value) throws GenericEntityException, GenericServiceException {

        // String partyId = (String) context.get("partyId");

        if (UtilValidate.isEmpty(value)) {
            String contactMechId = (String) findContactMech(partyId, contactMechTypeId, contactMechPurposeTypeId).get("contactMechId");
            if (UtilValidate.isNotEmpty(contactMechId)) {
                //delete
                return deleteContactMech(partyId, contactMechPurposeTypeId, contactMechId);
            } else return null;
        } else {
            String contactMechId = (String) findContactMech(partyId, contactMechTypeId, contactMechPurposeTypeId).get("contactMechId");

            if (UtilValidate.isEmpty(contactMechId)) {
                //create
                return createContactMech(contactMechTypeId, contactMechPurposeTypeId, value);
            } else {
                //update
                return updateContactMech(contactMechTypeId, contactMechId, value);
            }
        }
    }

    /**
     * @param partyId
     * @param contactMechPurposeTypeId
     * @param contactMechId
     * @return
     * @throws org.ofbiz.entity.GenericEntityException
     * @throws org.ofbiz.service.GenericServiceException
     */
    private String deleteContactMech(String partyId, String contactMechPurposeTypeId, String contactMechId) throws GenericEntityException, GenericServiceException {
        // String partyId = (String) context.get("partyId");
        EntityFieldMap filter = EntityCondition.makeConditionMap("partyId", partyId,
                "contactMechPurposeTypeId", contactMechPurposeTypeId, "contactMechId", contactMechId);
        if (UtilValidate.isEmpty(contactMechPurposeTypeId))
            filter = EntityCondition.makeConditionMap("partyId", partyId,
                    "contactMechPurposeTypeId", contactMechPurposeTypeId, "contactMechId", contactMechId);
        EntityConditionList<EntityCondition> conditions = EntityCondition.makeCondition(filter, EntityUtil.getFilterByDateExpr());

        List<GenericValue> list = delegator.findList("PartyContactMechPurpose", conditions, null, null, null, false);
        if (list.size() > 0) {
            Map<String, Object> srvContext = dispatcher.getDispatchContext().makeValidContext("deletePartyContactMechPurpose", "IN", context);
            srvContext.put("contactMechId", contactMechId);
            if (UtilValidate.isNotEmpty(contactMechPurposeTypeId))
                srvContext.put("contactMechPurposeTypeId", contactMechPurposeTypeId);
            srvContext.put("fromDate", list.get(0).get("fromDate"));
            Map<String, Object> result = dispatcher.runSync("deletePartyContactMechPurpose", srvContext);
            Debug.log("Deleted PartyContactMechPurpose id[" + contactMechId + "]", module);

        }
        Map<String, Object> srvContext = dispatcher.getDispatchContext().makeValidContext("deletePartyContactMech", "IN", context);
        srvContext.put("contactMechId", contactMechId);
        Map<String, Object> result = dispatcher.runSync("deletePartyContactMech", srvContext);
        Debug.log("Deleted PartyContactMech id[" + contactMechId + "]", module);
        return contactMechId;
    }

    /**
     * @param contactMechTypeId
     * @param contactMechPurposeTypeId
     * @param value
     * @return
     * @throws org.ofbiz.service.GenericServiceException
     */
    private String createContactMech(String contactMechTypeId, String contactMechPurposeTypeId, String value) throws GenericServiceException {
        Map<String, Object> srvContext = dispatcher.getDispatchContext().makeValidContext("createPartyContactMech", "IN", context);
        srvContext.put("contactMechTypeId", contactMechTypeId);
        if (UtilValidate.isNotEmpty(contactMechPurposeTypeId))
            srvContext.put("contactMechPurposeTypeId", contactMechPurposeTypeId);
        srvContext.put("infoString", value);
        Map<String, Object> result = dispatcher.runSync("createPartyContactMech", srvContext);
        Debug.log("Created PartyContactMech id[" + result.get("contactMechId") + "] type[" + contactMechTypeId + "] purposeType[" + contactMechPurposeTypeId + "]", module);
        return (String) result.get("contactMechId");
    }

    /**
     * @param contactMechTypeId
     * @param contactMechId
     * @param value
     * @return
     * @throws org.ofbiz.service.GenericServiceException
     */
    private String updateContactMech(String contactMechTypeId, String contactMechId, String value) throws GenericServiceException {
        Map<String, Object> srvContext = dispatcher.getDispatchContext().makeValidContext("updatePartyContactMech", "IN", context);
        srvContext.put("contactMechId", contactMechId);
        srvContext.put("contactMechTypeId", contactMechTypeId);
        srvContext.put("infoString", value);
        Map<String, Object> result = dispatcher.runSync("updatePartyContactMech", srvContext);
        Debug.log("Updated PartyContactMech id[" + contactMechId + "] type[" + contactMechTypeId + "]", module);
        return (String) result.get("contactMechId");
    }

    /**
     * @param partyId
     * @param contactMechTypeId
     * @param contactMechPurposeTypeId
     * @return
     * @throws org.ofbiz.entity.GenericEntityException
     */

    public Map<String, Object> findContactMech(String partyId, String contactMechTypeId, String contactMechPurposeTypeId) throws GenericEntityException {
        Map<String, Object> result = FastMap.newInstance();
        if (UtilValidate.isNotEmpty(contactMechPurposeTypeId)) {
            EntityFieldMap filter = EntityCondition.makeConditionMap("partyId", partyId,
                    "contactMechTypeId", contactMechTypeId, "contactMechPurposeTypeId", contactMechPurposeTypeId);
            EntityConditionList<EntityCondition> conditions = EntityCondition.makeCondition(filter,
                    EntityUtil.getFilterByDateExpr("contactFromDate", "contactThruDate"),
                    EntityUtil.getFilterByDateExpr("purposeFromDate", "purposeThruDate"));
            List<GenericValue> view = delegator.findList("PartyContactWithPurpose", conditions, null, null, null, false);
            if (view.size() == 0) {
                return result;
            } else {
                if (view.size() > 1) {
                    Debug.logWarning("more than one PartyContactMech with contactMechTypeId[" + contactMechTypeId + "] and contactMechPurposeTypeId[" + contactMechPurposeTypeId + "]", module);
                }
                result.put("contactMechId", view.get(0).getString("contactMechId"));
                result.put("infoString", view.get(0).getString("infoString"));
                return result;
            }

        } else {
            EntityFieldMap filter = EntityCondition.makeConditionMap("partyId", partyId, "contactMechTypeId", contactMechTypeId);
            EntityConditionList<EntityCondition> conditions = EntityCondition.makeCondition(filter, EntityUtil.getFilterByDateExpr());
            List<GenericValue> view = delegator.findList("PartyAndContactMech", conditions, null, null, null, false);
            if (view.size() == 0) {
                return result;
            } else {
                if (view.size() > 1) {
                    Debug.logWarning("more than one PartyContactMech with contactMechTypeId[" + contactMechTypeId + "]", module);
                }
                result.put("contactMechId", view.get(0).getString("contactMechId"));
                result.put("infoString", view.get(0).getString("infoString"));
                return result;
            }
        }
    }

    protected Map<String, Object> gnCreateUpdatePartyPostalAddress(Map<String, Object> address) throws GenericEntityException, GenericServiceException {
        Map<String, Object> srvParams;
        String countryGeoName = (String) address.get("countryGeoName");
        if (countryGeoName.equals("Italy") || countryGeoName.equals("IT")) {
            String postalCodeGeoId = (String) address.get("postalCodeGeoId");
            if (UtilValidate.isNotEmpty(postalCodeGeoId)) {
                GenericValue postalCodeGeo = delegator.findOne("Geo", true, "geoId", postalCodeGeoId);
                Map<String, Object> municipalityGeo = getGeoParent(postalCodeGeoId);
                Map<String, Object> provinceGeo = getGeoParent((String) municipalityGeo.get("geoId"));
                Map<String, Object> regionGeo = getGeoParent((String) provinceGeo.get("geoId"));
                Map<String, Object> countryGeo = getGeoParent((String) regionGeo.get("geoId"));

                address.put("postalCode", postalCodeGeo.get("geoName"));
                address.put("municipalityGeoId", municipalityGeo.get("geoId"));
                if (UtilValidate.isEmpty(address.get("city"))) address.put("city", municipalityGeo.get("geoName"));
                address.put("stateProvinceGeoId", provinceGeo.get("geoId"));
                address.put("regionGeoId", regionGeo.get("geoId"));
                address.put("countryGeoId", countryGeo.get("geoId"));
            }
            srvParams = dispatcher.getDispatchContext().makeValidContext("createUpdatePartyPostalAddress", "IN", address);
        } else {
            Map<String, Object> foreignAddress = MapUtil.copyKeys(address, address.keySet());
            Set<String> fieldsToSelect = new HashSet<String>(1);
            fieldsToSelect.add("geoId");
            List<GenericValue> cList = delegator.findList("Geo", EntityCondition.makeCondition("geoName", countryGeoName), fieldsToSelect, null, null, false);
            if (cList.size() == 0)
                cList = delegator.findList("Geo", EntityCondition.makeCondition("geoCode", countryGeoName), fieldsToSelect, null, null, false);
            if (cList.size() == 0)
                throw new GnServiceException(OfbizErrors.ENTITY_EXCEPTION, "Unable to find country geo of [" + countryGeoName + "]");
            GenericValue countryGeo = cList.get(0);
            foreignAddress.put("countryGeoId", countryGeo.get("geoId"));
            foreignAddress.put("foreignRegion", address.get("regionGeoName"));
            foreignAddress.put("foreignProvince", address.get("stateProvinceGeoName"));
            foreignAddress.put("city", address.get("municipalityGeoName"));
            srvParams = dispatcher.getDispatchContext().makeValidContext("createUpdatePartyPostalAddress", "IN", foreignAddress);
        }
        return dctx.getDispatcher().runSync("createUpdatePartyPostalAddress", srvParams);
    }

    private Map<String, Object> getGeoParent(String postalCodeGeoId) throws GenericEntityException, GnServiceException {
        List<GenericValue> geos = delegator.findByAnd("GeoAssocAndGeoFrom", "geoIdTo", postalCodeGeoId);
        if (geos.size() == 0)
            throw new GnServiceException(OfbizErrors.ENTITY_EXCEPTION, "Unable to find parent of Geo[" + postalCodeGeoId + "]");
        if (geos.size() > 1)
            throw new GnServiceException(OfbizErrors.ENTITY_EXCEPTION, "Found more than one parent of Geo[" + postalCodeGeoId + "]");
        GenericValue geo = geos.get(0);
        return geo.getFields(delegator.getModelEntity("Geo").getAllFieldNames());
    }


    public void gnDeleteAllPartyContactMechanisms(String partyId) throws GenericEntityException, GenericServiceException {
        EntityFieldMap filter = EntityCondition.makeConditionMap("partyId", partyId);
        List<GenericValue> list = delegator.findList("PartyContactMech", filter, null, null, null, false);
        for (GenericValue pcm : list) {
            String contactMechId = pcm.getString("contactMechId");
            int ret = delegator.removeByAnd("PartyContactMechPurpose", UtilMisc.toMap("partyId", partyId, "contactMechId", contactMechId), true);
            Debug.log("Deleted " + ret + " PartyContactMechPurpose");
            ret = delegator.removeByAnd("PostalAddress", UtilMisc.toMap("contactMechId", contactMechId), true);
            Debug.log("Deleted " + ret + " PostalAddress");
            ret = delegator.removeByAnd("ContactMechAttribute", UtilMisc.toMap("contactMechId", contactMechId), true);
            Debug.log("Deleted " + ret + " ContactMechAttribute");
            delegator.removeValue(pcm);
            Debug.log("Deleted  PartyContactMech[" + contactMechId + "]");
        }
    }
}