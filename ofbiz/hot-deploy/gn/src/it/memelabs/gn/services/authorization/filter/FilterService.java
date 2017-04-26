package it.memelabs.gn.services.authorization.filter;

import it.memelabs.gn.util.GnServiceUtil;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.service.DispatchContext;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 23/05/13
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
    public static Map<String, Object> gnAuthorizationMatchFilter(DispatchContext ctx, Map<String, Object> context) {
        try {
            Map<String, Object> result = GnServiceUtil.returnSuccess();
            Map<String, Object> auth = (Map<String, Object>) context.get("authorization");
            Map<String, Object> filter = (Map<String, Object>) context.get("filter");
            String mode = (String) context.get("mode");
            boolean debug = "Y".equals(context.get("debug"));
            FilterMatcherHelper filterMatcherHelper = new FilterMatcherHelper(ctx, context);
            GeoHelper geoHelper = new GeoHelper(ctx, context);

            long filterMatchStartTime = System.currentTimeMillis();
            if ("OUT".equals(mode)) {
                Debug.log("Filter OUT", mode);
                FilterMatcher filterMatcher = new FilterMatcher(auth, filter, filterMatcherHelper, geoHelper);
                boolean match = filterMatcher.match();
                result.put("match", (match) ? "Y" : "N");
                result.put("message", filterMatcher.getMessage());
                Debug.log("\n=================\n" + filterMatcher.getMessage() + "\n=================\n", module);
            } else {//else in
                Debug.log("Filter IN", mode);
                FilterMatcher2 filterMatcher = new FilterMatcher2(auth, filter, filterMatcherHelper, geoHelper);
                boolean match = filterMatcher.match();
                result.put("match", (match) ? "Y" : "N");
                result.put("message", filterMatcher.getMessage());
                Debug.log("\n=================\n" + filterMatcher.getMessage() + "\n=================\n", module);
            }
            long filterMatchTime = System.currentTimeMillis() - filterMatchStartTime;
            if (Debug.timingOn() || filterMatchTime > 200) {
                Debug.logTiming("Authorization filter evaluated in [" + filterMatchTime + "] ms", module);
            }
            return result;
        } catch (Throwable e) {
            Debug.logError(e, module);
            return GnServiceUtil.returnError(e);
        }
    }

    /**
     * Proxy method
     *
     * @param ctx
     * @param context
     * @return
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> gnAuthorizationApplySlicingFilters(DispatchContext ctx, Map<String, Object> context) {
        try {
            Map<String, Object> result = GnServiceUtil.returnSuccess();
            Map<String, Object> auth = UtilMisc.getMapFromMap(context, "authorization");
            List<Map<String, Object>> filters = UtilMisc.getListFromMap(context, "filters");
            boolean debug = "Y".equals(context.get("debug"));
            FilterMatcherHelper filterMatcherHelper = new FilterMatcherHelper(ctx, context);
            GeoHelper geoHelper = new GeoHelper(ctx, context);
            result.put("message", "");
            Map<String, Object> matchingFilterType = SlicingFilterMatcher.findMatchingFilterType(auth, filters);
            if (matchingFilterType != null) {
                List<Map<String, Object>> items = new LinkedList<Map<String, Object>>();
                long filterMatchStartTime = System.currentTimeMillis();
                SlicingFilterMatcher filterMatcher = new SlicingFilterMatcher(auth, matchingFilterType, filterMatcherHelper, geoHelper);
                items.addAll(filterMatcher.matchSlicingFilters());
                if (filterMatcher.isSliceDone())
                    auth.put("sliceDone", "Y");
                result.put("message", result.get("message") + filterMatcher.getMessage() + "\n");
                Debug.log("\n=================\n" + filterMatcher.getMessage() + "\n=================\n", module);
                long filterMatchTime = System.currentTimeMillis() - filterMatchStartTime;
                if (Debug.timingOn() || filterMatchTime > 200) {
                    Debug.logTiming("Authorization filter evaluated in [" + filterMatchTime + "] ms", module);
                }
                auth.put("authorizationItems", items);
            } // else se non c'è nessun filtro corrispondente al tipo dell'autorizzazione, passa per intero
            result.put("authorization", auth);
            return result;
        } catch (Throwable e) {
            Debug.logError(e, module);
            return GnServiceUtil.returnError(e);
        }
    }

    @SuppressWarnings("unchecked")
    public static Map<String, Object> gnAuthorizationApplySlicingFiltersTest(DispatchContext ctx, Map<String, Object> context) {
        Map<String, Object> result = gnAuthorizationApplySlicingFilters(ctx, context);
        Map<String, Object> auth = (Map<String, Object>) result.get("authorization");
        if (auth != null) auth.remove("sliceDone");
        return result;
    }

}
