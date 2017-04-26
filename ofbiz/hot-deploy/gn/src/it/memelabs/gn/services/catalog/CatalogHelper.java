package it.memelabs.gn.services.catalog;

import it.memelabs.gn.services.AbstractServiceHelper;
import it.memelabs.gn.util.MapUtil;
import javolution.util.FastList;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.entity.condition.EntityCondition;
import org.ofbiz.entity.condition.EntityOperator;
import org.ofbiz.service.DispatchContext;
import org.ofbiz.service.GenericServiceException;

import java.util.List;
import java.util.Map;

/**
 * 11/03/13
 *
 * @author Andrea Fossi
 */
public class CatalogHelper extends AbstractServiceHelper {
    private static final String module = CatalogHelper.class.getName();

    /**
     * @param dctx    OFBiz DispatchContext
     * @param context Service Context
     */
    public CatalogHelper(DispatchContext dctx, Map<String, ? extends Object> context) {
        super(dctx, context);
    }


    protected List<Map<String, Object>> findCountries() throws GenericEntityException, GenericServiceException {
        long start = System.currentTimeMillis();
        List<Map<String, Object>> countries = FastList.newInstance();
        List<GenericValue> _countries = delegator.findByAnd("Geo", UtilMisc.toMap("geoTypeId", "COUNTRY"/*, "geoId", "ITA"*/));
        for (GenericValue _country : _countries) {
            Map<String, Object> country = UtilMisc.makeMapWritable(_country);
            String geoId = (String) country.get("geoId");
            countries.add(country);
            country.put("regions", findRegions(geoId));
        }
        long stop = System.currentTimeMillis();
        String msg = "findCountries executed in:" + (stop - start) / 1000d;
        Debug.log(msg, module);
        return countries;
    }

    private List<Map<String, Object>> findRegions(String geoIdFrom) throws GenericEntityException {
        List<Map<String, Object>> regions = FastList.newInstance();

        List<GenericValue> _regions = delegator.findByAnd("GeoAssocAndGeoTo", UtilMisc.toMap("geoTypeId", "REGION", "geoIdFrom", geoIdFrom, "geoAssocTypeId", "REGIONS"));
        List<Map<String, Object>> allMunicipalities = FastList.newInstance();

        for (Map<String, Object> _region : _regions) {
            Map<String, Object> region = MapUtil.copyKeys(_region, delegator.getModelEntity("Geo").getAllFieldNames());
            regions.add(region);
            region.put("provinces", findProvince((String) region.get("geoId"),allMunicipalities));
        }

        if ("ITA".equals(geoIdFrom)) {
            List<GenericValue> postalCodes = findAllPostalCodes();
            Map<String, Map<String, Object>> muni = MapUtil.listToMap(allMunicipalities, "geoId", String.class);
            for (GenericValue _pc : postalCodes) {
                Map<String, Object> pc = MapUtil.copyKeys(_pc, delegator.getModelEntity("Geo").getAllFieldNames());
                UtilMisc.addToListInMap(pc, muni.get(_pc.getString("geoIdFrom")), "postalCodes");
            }
        }
        return regions;
    }

    private List<Map<String, Object>> findProvince(String geoIdFrom, List<Map<String, Object>> allMunicipalities) throws GenericEntityException {
        List<Map<String, Object>> regions = FastList.newInstance();
        List<GenericValue> _regions = delegator.findByAnd("GeoAssocAndGeoTo", UtilMisc.toMap("geoTypeId", "PROVINCE", "geoIdFrom", geoIdFrom, "geoAssocTypeId", "REGIONS"));
        for (Map<String, Object> _region : _regions) {
            Map<String, Object> region = MapUtil.copyKeys(_region, delegator.getModelEntity("Geo").getAllFieldNames());
            regions.add(region);
            region.put("municipalities", findMunicipalities((String) region.get("geoId"),allMunicipalities));
        }
        return regions;
    }

    private List<Map<String, Object>> findMunicipalities(String geoIdFrom, List<Map<String, Object>> allMunicipalities) throws GenericEntityException {
        List<Map<String, Object>> municipalities = FastList.newInstance();
        List<GenericValue> _municipalities = delegator.findByAnd("GeoAssocAndGeoTo", UtilMisc.toMap("geoTypeId", "MUNICIPALITY", "geoIdFrom", geoIdFrom, "geoAssocTypeId", "REGIONS"));
        for (Map<String, Object> _municipality : _municipalities) {
            Map<String, Object> municipality = MapUtil.copyKeys(_municipality, delegator.getModelEntity("Geo").getAllFieldNames());
            municipalities.add(municipality);
           // municipality.put("postalCodes", findPostalCodes((String) municipality.get("geoId")));
            allMunicipalities.add(municipality);
        }
        return municipalities;
    }

    private List<Map<String, Object>> findPostalCodes(String geoIdFrom) throws GenericEntityException {
        List<Map<String, Object>> postalCodes = FastList.newInstance();
        List<GenericValue> _postalCodes = delegator.findByAnd("GeoAssocAndGeoTo", UtilMisc.toMap("geoTypeId", "POSTAL_CODE", "geoIdFrom", geoIdFrom, "geoAssocTypeId", "POSTAL_CODE"));
        for (Map<String, Object> _postalCode : _postalCodes) {
            Map<String, Object> region = MapUtil.copyKeys(_postalCode, delegator.getModelEntity("Geo").getAllFieldNames());
            postalCodes.add(region);
        }
        return postalCodes;
    }

    private List<GenericValue> findAllPostalCodes() throws GenericEntityException {
        // List<Map<String, Object>> postalCodes = FastList.newInstance();
        EntityCondition cond1 = EntityCondition.makeCondition(UtilMisc.toMap("geoTypeId", "POSTAL_CODE", "geoAssocTypeId", "POSTAL_CODE"));
        EntityCondition cond2 = EntityCondition.makeCondition("geoId", EntityOperator.LIKE, "IT-GN-CAP%");
        EntityCondition cond = EntityCondition.makeCondition(cond1, cond2);
        List<GenericValue> _postalCodes = delegator.findList("GeoAssocAndGeoTo", cond, null, null, null, false);
//        for (Map<String, Object> _postalCode : _postalCodes) {
//            Map<String, Object> region = MapUtil.copyKeys(_postalCode, delegator.getModelEntity("Geo").getAllFieldNames());
//            postalCodes.add(region);
//        }
        return _postalCodes;
    }

}
