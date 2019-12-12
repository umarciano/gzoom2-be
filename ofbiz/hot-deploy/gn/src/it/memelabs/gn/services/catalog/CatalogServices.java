package it.memelabs.gn.services.catalog;

import it.memelabs.gn.util.GnServiceUtil;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.GeneralException;
import org.ofbiz.service.DispatchContext;
import org.ofbiz.service.ServiceUtil;

import java.util.Map;

/**
 * 11/03/13
 *
 * @author Andrea Fossi
 */
public class CatalogServices {

    private static final String module = CatalogServices.class.getName();

    @SuppressWarnings("unchecked")
    public static Map<String, Object> gnFindCountries(DispatchContext ctx, Map<String, ?> context) {
        try {
            Map<String, Object> result = ServiceUtil.returnSuccess();
            result.put("countries", new CatalogHelper(ctx, context).findCountries());
            return result;
        } catch (GeneralException e) {
            Debug.logError(e, module);
            return GnServiceUtil.returnError(e);
        }
    }
}
