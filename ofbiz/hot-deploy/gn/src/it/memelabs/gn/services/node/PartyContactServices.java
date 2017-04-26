package it.memelabs.gn.services.node;

import it.memelabs.gn.util.CloneUtil;
import it.memelabs.gn.util.GnServiceUtil;
import javolution.util.FastMap;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.GeneralException;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.base.util.cache.UtilCache;
import org.ofbiz.entity.Delegator;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.entity.condition.EntityCondition;
import org.ofbiz.entity.condition.EntityConditionList;
import org.ofbiz.entity.condition.EntityFieldMap;
import org.ofbiz.entity.condition.EntityOperator;
import org.ofbiz.entity.util.EntityUtil;
import org.ofbiz.service.DispatchContext;
import org.ofbiz.service.GenericServiceException;
import org.ofbiz.service.ServiceUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 17/01/13
 *
 * @author Andrea Fossi
 */
public class PartyContactServices {
    private static final String module = PartyContactServices.class.getName();

    private static UtilCache<String, Map<String, Object>> partyContactCache = UtilCache.createUtilCache("gn.entitycache.gnPartyContact", 10000, 120 * 60 * 1000L, true);
    private static UtilCache<String, List<Map<String, Object>>> addressesCache = UtilCache.createUtilCache("gn.entitycache.gnAddressesCache", 10000, 120 * 60 * 1000L, true);

    /**
     * Find party contact info by party id
     *
     * @param ctx     (partyId)
     * @param context
     * @return
     */
    public static Map<String, Object> gnGetPartyContact(DispatchContext ctx, Map<String, ? extends Object> context) {
        //String description = (String) context.get("description");
        String partyId = (String) context.get("partyId");
        Map<String, Object> result = ServiceUtil.returnSuccess();
        Map<String, Object> contact = FastMap.newInstance();
        if (partyContactCache.containsKey(partyId)) {
            contact = partyContactCache.get(partyId);
        } else {
            try {
                PartyContactHelper partyContactHelper = new PartyContactHelper(ctx, context);
                String phoneNumber = (String) partyContactHelper.findContactMech(partyId, "TELECOM_NUMBER", "PRIMARY_PHONE").get("infoString");
                String mobileNumber = (String) partyContactHelper.findContactMech(partyId, "TELECOM_NUMBER", "PHONE_MOBILE").get("infoString");
                String faxNumber = (String) partyContactHelper.findContactMech(partyId, "TELECOM_NUMBER", "FAX_NUMBER").get("infoString");
                String emailAddress = (String) partyContactHelper.findContactMech(partyId, "EMAIL_ADDRESS", "PRIMARY_EMAIL").get("infoString");
                String webPage = (String) partyContactHelper.findContactMech(partyId, "WEB_ADDRESS", "PRIMARY_WEB_URL").get("infoString");
                String note = (String) partyContactHelper.findContactMech(partyId, "INTERNAL_PARTYID", "CONTACT_NOTE").get("infoString");
                String description = (String) partyContactHelper.findContactMech(partyId, "INTERNAL_PARTYID", "CONTACT_DESCRIPTION").get("infoString");

                //contactMechTypeId=TELECOM_NUMBER
                //contactMechPurposeTypeId=PRIMARY_PHONE
                contact.put("phoneNumber", phoneNumber);
                //contactMechTypeId=TELECOM_NUMBER
                //contactMechPurposeTypeId=PHONE_MOBILE
                contact.put("mobileNumber", mobileNumber);
                //contactMechTypeId=TELECOM_NUMBER
                //contactMechPurposeTypeId=FAX_NUMBER
                contact.put("faxNumber", faxNumber);
                //contactMechTypeId=EMAIL_ADDRESS
                //contactMechPurposeTypeId=PRIMARY_EMAIL
                contact.put("emailAddress", emailAddress);
                //contactMechTypeId=WEB_ADDRESS
                //contactMechPurposeTypeId=PRIMARY_WEB_URL
                contact.put("webPage", webPage);
                //contactMechTypeId=INTERNAL_PARTYID  Internal Note via partyId
                //contactMechPurposeTypeId= CONTACT_DESCRIPTION
                contact.put("note", note);
                //contactMechTypeId= INTERNAL_PARTYID
                //contactMechPurposeTypeId= CONTACT_DESCRIPTION
                contact.put("description", description);

                //add entry on cache
                partyContactCache.put(partyId, contact);
            } catch (Throwable e) {
                Debug.logError(e, module);
                return GnServiceUtil.returnError(e);
            }
        }
        result.put("contact", CloneUtil.deepClone(contact));
        return result;
    }

    /**
     * @param ctx
     * @param context
     * @return for contactMechTypeID and contactMechPurposeTypeId see OFBiz entity ContactMechTypePurpose
     */
    public static Map<String, Object> gnCreateUpdatePartyContact(DispatchContext ctx, Map<String, ? extends Object> context) {
        String partyId = (String) context.get("partyId");

        //invalidate cache
        if (partyContactCache.containsKey(partyId)) partyContactCache.remove(partyId);

        //contactMechTypeId=TELECOM_NUMBER
        //contactMechPurposeTypeId=PRIMARY_PHONE
        String phoneNumber = (String) context.get("phoneNumber");
        //contactMechTypeId=TELECOM_NUMBER
        //contactMechPurposeTypeId=PHONE_MOBILE
        String mobileNumber = (String) context.get("mobileNumber");
        //contactMechTypeId=TELECOM_NUMBER
        //contactMechPurposeTypeId=FAX_NUMBER
        String faxNumber = (String) context.get("faxNumber");
        //contactMechTypeId=EMAIL_ADDRESS
        //contactMechPurposeTypeId=PRIMARY_EMAIL
        String emailAddress = (String) context.get("emailAddress");
        //contactMechTypeId=WEB_ADDRESS
        //contactMechPurposeTypeId=PRIMARY_WEB_URL
        String webPage = (String) context.get("webPage");
        //contactMechTypeId=INTERNAL_PARTYID  Internal Note via partyId
        //contactMechPurposeTypeId=
        String note = (String) context.get("note");

        //contactMechTypeId=        pending
        //contactMechPurposeTypeId= pending

        String description = (String) context.get("description");


        try {
            PartyContactHelper partyContactHelper = new PartyContactHelper(ctx, context);
            partyContactHelper.saveUpdatePartyContact(partyId, "TELECOM_NUMBER", "PRIMARY_PHONE", phoneNumber);
            partyContactHelper.saveUpdatePartyContact(partyId, "TELECOM_NUMBER", "PHONE_MOBILE", mobileNumber);
            partyContactHelper.saveUpdatePartyContact(partyId, "TELECOM_NUMBER", "FAX_NUMBER", faxNumber);
            partyContactHelper.saveUpdatePartyContact(partyId, "EMAIL_ADDRESS", "PRIMARY_EMAIL", emailAddress);
            partyContactHelper.saveUpdatePartyContact(partyId, "WEB_ADDRESS", "PRIMARY_WEB_URL", webPage);
            partyContactHelper.saveUpdatePartyContact(partyId, "INTERNAL_PARTYID", "CONTACT_NOTE", note);
            partyContactHelper.saveUpdatePartyContact(partyId, "INTERNAL_PARTYID", "CONTACT_DESCRIPTION", description);
        } catch (GenericServiceException e1) {
            Debug.logError(e1, module);
            return GnServiceUtil.returnError(e1);
        } catch (GenericEntityException e1) {
            Debug.logError(e1, module);
            return GnServiceUtil.returnError(e1);
        }
        Map<String, Object> result = ServiceUtil.returnSuccess();
        return result;
    }

    public static Map<String, Object> gnCreateUpdatePartyPostalAddress(DispatchContext ctx, Map<String, ? extends Object> context) {
        try {
            addressesCache.remove(context.get("partyId"));
            return new PartyContactHelper(ctx, context).gnCreateUpdatePartyPostalAddress(UtilMisc.makeMapWritable(context));
        } catch (GeneralException e) {
            Debug.logError(e, module);
            return GnServiceUtil.returnError(e);
        }
    }

    public static Map<String, Object> gnDeleteAllPartyContactMechanisms(DispatchContext ctx, Map<String, ? extends Object> context) {
        try {
            String partyId = (String) context.get("partyId");
            new PartyContactHelper(ctx, context).gnDeleteAllPartyContactMechanisms(partyId);
            return GnServiceUtil.returnSuccess();
        } catch (GeneralException e) {
            Debug.logError(e, module);
            return GnServiceUtil.returnError(e);
        }
    }

    public static Map<String, Object> gnGetPostalAddressFromPartyId(DispatchContext ctx, Map<String, ? extends Object> context) {
        Delegator delegator = ctx.getDelegator();
        List<Map<String, Object>> ret = new ArrayList<Map<String, Object>>();
        String partyId = (String) context.get("partyId");

        if (addressesCache.get(partyId) != null) {//cache hit
            ret = addressesCache.get(partyId);
        } else {//cache miss
            try {
                EntityFieldMap filter = EntityCondition.makeConditionMap("partyId", partyId, "countryGeoId", "ITA");
                EntityConditionList<EntityCondition> condition = EntityCondition.makeCondition(filter, EntityUtil.getFilterByDateExpr());
                List<GenericValue> view = delegator.findList("GnPartyAndPostalAddress", condition, null, null, null, false);
                for (GenericValue item : view) {
                    Map<String, Object> address = UtilMisc.toMap("contactMechId", item.get("contactMechId"),
                            "address1", item.get("address1"),
                            "streetNumber", item.get("streetNumber"),
                            "village", item.get("village"),
                            "postalCode", item.get("postalCode"),
                            "postalCodeGeoId", item.get("postalCodeGeoId"),
                            "city", item.get("city"),
                            "municipalityGeoName", item.get("municipalityGeoName"),
                            "municipalityGeoCode", item.get("municipalityGeoCode"),
                            "stateProvinceGeoId", item.get("stateProvinceGeoId"),
                            "stateProvinceGeoName", item.get("stateProvinceGeoName"),
                            "stateProvinceGeoAbbr", item.get("stateProvinceGeoAbbr"),
                            "regionGeoId", item.get("regionGeoId"),
                            "regionGeoName", item.get("regionGeoName"),
                            "countryGeoId", item.get("countryGeoId"),
                            "countryGeoName", item.get("countryGeoName")
                    );
                    ret.add(address);
                }

                List<EntityCondition> conds = new ArrayList<EntityCondition>();
                conds.add(EntityCondition.makeCondition("partyId", partyId));
                conds.add(EntityCondition.makeCondition("countryGeoId", EntityOperator.NOT_EQUAL, "ITA"));
                conds.add(EntityUtil.getFilterByDateExpr());
                EntityConditionList<EntityCondition> condition2 = EntityCondition.makeCondition(conds);
                List<GenericValue> view2 = delegator.findList("GnPartyAndForeignPostalAddress", condition2, null, null, null, false);
                for (GenericValue item2 : view2) {
                    Map<String, Object> address = UtilMisc.toMap("contactMechId", item2.get("contactMechId"),
                            "address1", item2.get("address1"),
                            "streetNumber", item2.get("streetNumber"),
                            "village", item2.get("village"),
                            "postalCode", item2.get("postalCode"),
                            "municipalityGeoName", item2.get("city"),
                            "stateProvinceGeoName", item2.get("stateProvinceGeoName"),
                            "regionGeoName", item2.get("regionGeoName"),
                            "countryGeoId", item2.get("countryGeoId"),
                            "countryGeoName", item2.get("countryGeoName")
                    );
                    ret.add(address);
                }
            } catch (GenericEntityException e) {
                Debug.logError(e, module);
                return GnServiceUtil.returnError(e);
            }
            addressesCache.put(partyId, ret);
        }
        Map<String, Object> result = ServiceUtil.returnSuccess();
        result.put("addresses", CloneUtil.deepClone(ret));
        return result;
    }
}
