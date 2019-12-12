package it.memelabs.gn.services.node;

import it.memelabs.gn.util.GnServiceUtil;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.service.DispatchContext;
import org.ofbiz.service.ServiceUtil;

import java.util.Map;

/**
 * 10/06/13
 *
 * @author Andrea Fossi
 */
public class PartyServices {
    private static final String module = PartyServices.class.getName();


    /**
     * Find a partyAttribute by primary key
     *
     * @param ctx
     * @param context
     * @return
     */
    public static Map<String, Object> gnFindPartyAttributeById(DispatchContext ctx, Map<String, Object> context) {
        try {
            Map<String, Object> result = ServiceUtil.returnSuccess();

            String partyId = (String) context.get("partyId");
            String attrName = (String) context.get("attrName");
            GenericValue value = ctx.getDelegator().findOne("PartyAttribute", false, "partyId", partyId, "attrName", attrName);
            result.put("attrName", attrName);
            if (value != null) {
                result.put("attrValue", value.getString("attrValue"));
                Debug.log("Found attribute partyId[" + partyId + "] attrName[" + attrName + "] attrValue[" + attrName + "]");
            } else {
                Debug.log("Attribute partyId[" + partyId + "] attrName[" + attrName + "] not found.");
            }
            return result;
        } catch (Throwable e) {
            Debug.logError(e, module);
            return GnServiceUtil.returnError(e);
        }
    }

    /**
     * Find a partyAttribute by primary key
     *
     * @param ctx
     * @param context
     * @return
     */
    public static Map<String, Object> gnCreateUpdatePartyAttribute(DispatchContext ctx, Map<String, Object> context) {
        try {
            Map<String, Object> result = ServiceUtil.returnSuccess();
            String partyId = (String) context.get("partyId");
            String attrName = (String) context.get("attrName");
            String attrValue = (String) context.get("attrValue");
            GenericValue value = ctx.getDelegator().findOne("PartyAttribute", false, "partyId", partyId, "attrName", attrName);
            Map<String, Object> srvRequest = UtilMisc.toMap("userLogin", context.get("userLogin"));
            srvRequest.put("partyId", partyId);
            srvRequest.put("attrName", attrName);
            srvRequest.put("attrValue", attrValue);

            if (UtilValidate.isEmpty(value)) {
                Debug.log("Attribute partyId[" + partyId + "] attrNme[" + attrName + "] not found.");
                ctx.getDispatcher().runSync("createPartyAttribute", srvRequest);
            } else {
                Debug.log("Found attribute partyId[" + partyId + "] attrNme[" + attrName + "].");
                ctx.getDispatcher().runSync("updatePartyAttribute", srvRequest);
            }
            result.put("partyId", partyId);
            result.put("attrName", attrName);
            return result;
        } catch (Throwable e) {
            Debug.logError(e, module);
            return GnServiceUtil.returnError(e);
        }
    }

    /*
      * @param ctx
      * @param context
      * @return
      */
    public static Map<String, Object> gnPartyRoleCheck(DispatchContext ctx, Map<String, Object> context) {
        try {
            Map<String, Object> result = GnServiceUtil.returnSuccess();
            String roleTypeId = (String) context.get("roleTypeId");
            String partyId = (String) context.get("partyId");
            String partyIdTo = (String) context.get("partyIdTo");
            String partyIdFrom = (String) context.get("partyIdFrom");
            Boolean hasRole = new PartyHelper(ctx, context).gnPartyRoleCheck(partyId, partyIdTo, partyIdFrom, roleTypeId);
            result.put("hasRole", hasRole);
            return result;
        } catch (Throwable e) {
            Debug.logError(e, module);
            return GnServiceUtil.returnError(e);
        }
    }

}
