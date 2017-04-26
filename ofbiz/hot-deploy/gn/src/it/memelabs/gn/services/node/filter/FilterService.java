package it.memelabs.gn.services.node.filter;

import it.memelabs.gn.services.event.EventMessageDirectionOfbiz;
import it.memelabs.gn.util.GnServiceUtil;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.service.DispatchContext;

import java.util.Map;

/**
 * 03/04/14
 *
 * @author Elisa Spada
 */
public class FilterService {

    private static final String module = FilterService.class.getName();

    public static Map<String, Object> gnFilterApertureManage(DispatchContext ctx, Map<String, Object> context) {
        //context contains receive relationship and taxIdentificationNumber
        try {
            Map<String, Object> result = GnServiceUtil.returnSuccess();
            FilterHelper filterHelper = new FilterHelper(ctx, context);
            String taxIdentificationNumber = (String) context.get("taxIdentificationNumber");
            @SuppressWarnings("unchecked")
            Map<String, Object> partyRelationship = (Map<String, Object>) context.get("partyRelationship");
            Debug.log("FilterAperture starts on node " + partyRelationship.get("partyIdFrom") + " for taxIdentificationNumber " + taxIdentificationNumber);
            Map<String, Object> success = filterHelper.gnFilterApertureManage(partyRelationship, taxIdentificationNumber);
            Debug.log("FilterAperture done on node " + partyRelationship.get("partyIdFrom"));
            return result;
        } catch (Throwable e) {
            Debug.logError(e, module);
            return GnServiceUtil.returnError(e);
        }
    }

    public static Map<String, Object> gnFilterApertureSendToEM(DispatchContext ctx, Map<String, Object> context) {
        //context contains EventMessageFilterApertureSend
        try {
            Map<String, Object> result = GnServiceUtil.returnSuccess();
            FilterHelper filterHelper = new FilterHelper(ctx, context);
            String taxIdentificationNumber = (String) context.get("taxIdentificationNumber");
            String nodeKey = (String) context.get("nodeKey");
            String direction = (String) context.get("direction");
            String senderNodeKey = (String) context.get("senderNodeKey");
            String senderPartyId = (String) context.get("senderPartyId");
            String nodePartyIdFrom = (String) ctx.getDispatcher().runSync("gnGetPartyIdFromNodeKey", UtilMisc.toMap("userLogin", context.get("userLogin"), "nodeKey", nodeKey)).get("partyId");
            Map<String, Object> success = filterHelper.gnFilterApertureSendToEM(nodePartyIdFrom, taxIdentificationNumber, EventMessageDirectionOfbiz.valueOf(direction), senderNodeKey, senderPartyId);
            Debug.log("FilterAperture done on node " + nodePartyIdFrom);
            return result;
        } catch (Throwable e) {
            Debug.logError(e, module);
            return GnServiceUtil.returnError(e);
        }
    }

}
