package it.memelabs.gn.services.authorization.filter;

import it.memelabs.gn.services.authorization.CommonAuthorizationHelper;
import javolution.util.FastList;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.service.DispatchContext;
import org.ofbiz.service.GenericServiceException;

import java.util.List;
import java.util.Map;

/**
 * 24/05/13
 *
 * @author Andrea Fossi
 */
public class GeoHelper extends CommonAuthorizationHelper {
    public GeoHelper(DispatchContext dctx, Map<String, ? extends Object> context) {
        super(dctx, context);
    }

    public List<String> findParents(String geoId) throws  GenericEntityException {
        List<String> result = FastList.newInstance();
        String geoIdTo = geoId;
        result.add(geoIdTo);
        while (UtilValidate.isNotEmpty(geoIdTo)) {
            GenericValue parent = getParent(geoIdTo);
            if (UtilValidate.isNotEmpty(parent)) {
                geoIdTo = parent.getString("geoId");
                result.add(geoIdTo);
            } else geoIdTo = null;
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> getRelatedGeos(String geoId, String geoAssocTypeId) throws GenericServiceException {
        Map<String, Object> result = dispatcher.runSync("getRelatedGeos", UtilMisc.toMap("geoId", geoId, "geoAssocTypeId", geoAssocTypeId));
        return (List<Map<String, Object>>) result.get("geoList");
    }

    public List<GenericValue> getParents(String geoIdTo) throws  GenericEntityException {
        return delegator.findByAnd("GeoAssoc", UtilMisc.toMap("geoIdTo", geoIdTo));
    }

    public GenericValue getParent(String geoIdTo) throws  GenericEntityException {
        List<GenericValue> relatedGeos = getParents(geoIdTo);
        if (relatedGeos.size() > 0)
            return relatedGeos.get(0);
        else return null;
    }
}
