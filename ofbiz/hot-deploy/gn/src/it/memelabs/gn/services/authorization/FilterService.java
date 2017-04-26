package it.memelabs.gn.services.authorization;

import it.memelabs.gn.util.GnServiceUtil;
import it.memelabs.gn.util.PartyRelationshipIdUtil;
import org.ofbiz.base.util.Debug;
import org.ofbiz.service.DispatchContext;

import java.util.List;
import java.util.Map;

/**
 * 21/05/13
 *
 * @author Andrea Fossi
 */
public class FilterService {
    private static final String module = FilterService.class.getName();

    /**
     * Proxy method to create/update authorization
     *
     * @param ctx
     * @param context
     * @return
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> gnCreateUpdatedAuthorizationFilters(DispatchContext ctx, Map<String, Object> context) {
        try {
            Map<String, Object> result = GnServiceUtil.returnSuccess();
            FilterHelper filterHelper = new FilterHelper(ctx, context);
            List<Map<String, Object>> filters = (List<Map<String, Object>>) context.get("filters");
            Map<String, Object> relationshipKey = (Map<String, Object>) context.get("relationshipKey");
            String agreementTypeId = (String) context.get("agreementTypeId");
            result.putAll(filterHelper.gnCreateUpdatedAuthorizationFilters(filters, relationshipKey, agreementTypeId));
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
    @SuppressWarnings("unchecked")
    public static Map<String, Object> gnFindAuthorizationFilterByRelationship(DispatchContext ctx, Map<String, Object> context) {
        try {
            Map<String, Object> result = GnServiceUtil.returnSuccess();
            FilterHelper filterHelper = new FilterHelper(ctx, context);
            Map<String, Object> relationshipKey = PartyRelationshipIdUtil.getKeyMap(context);
            String loadInfoHolderConstraint = (String) context.get("loadInfoHolderConstraint");
            String agreementTypeId = (String) context.get("agreementTypeId");
            List<Map<String, Object>> filtersMap = filterHelper.gnFindFilterByRelationship(relationshipKey, agreementTypeId, "Y".equals(loadInfoHolderConstraint));
            result.put("filters", filtersMap);
            return result;
        } catch (Throwable e) {
            Debug.logError(e, module);
            return GnServiceUtil.returnError(e);
        }
    }


}