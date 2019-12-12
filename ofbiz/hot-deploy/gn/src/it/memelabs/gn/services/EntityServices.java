package it.memelabs.gn.services;

import it.memelabs.gn.util.GnServiceUtil;
import org.ofbiz.base.util.Debug;
import org.ofbiz.entity.Delegator;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.entity.model.ModelEntity;
import org.ofbiz.service.DispatchContext;
import org.ofbiz.service.ServiceUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 11/01/13
 *
 * @author Andrea Fossi
 */
public class EntityServices {


    public static Map<String, Object> findEntity(DispatchContext ctx, Map<String, ? extends Object> context) {
        Delegator delegator = ctx.getDelegator();
        String entityName = (String) context.get("entityName");
        Object o = context.get("entityFilterFields");

        ModelEntity entity = delegator.getModelEntity(entityName);
        if (entity == null)
            return GnServiceUtil.returnError("Entity not exist");


        List<GenericValue> ret;

        try {
            ret = delegator.findByAnd(entityName);
        } catch (GenericEntityException e) {
            Debug.logError(e, "Error");
            return GnServiceUtil.returnError(e);
        }
        Map<String, Object> result = ServiceUtil.returnSuccess();
        ret = new ArrayList<GenericValue>(ret);
        ret.add(ret.get(0));
        result.put("entityList", ret);
        result.put("entityListSize", ret.size());
        return result;
    }
}
