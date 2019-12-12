package it.memelabs.gn.services.system;

import it.memelabs.gn.services.AbstractServiceHelper;
import it.memelabs.gn.services.OfbizErrors;
import it.memelabs.gn.services.user.GnContextHelper;
import it.memelabs.gn.util.GnServiceException;
import org.ofbiz.base.util.UtilDateTime;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.service.DispatchContext;
import org.ofbiz.service.GenericServiceException;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

/**
 * 22/10/13
 *
 * @author Andrea Fossi
 */
public class GnSystemHelper extends AbstractServiceHelper {
    private static final String module = GnContextHelper.class.getName();

    public GnSystemHelper(DispatchContext dctx, Map<String, ? extends Object> context) {
        super(dctx, context);
    }

    /**
     * Can use engine="entity-auto" but I don't know if cache are used
     *
     * @param name
     * @throws GenericEntityException
     */
    public Map<String, Object> gnFindApplicationProperty(String name) throws GenericEntityException, GenericServiceException {
        GenericValue property = delegator.findOne("GnApplicationProperty", true, "name", name);
        return dispatcher.getDispatchContext().makeValidContext("gnFindApplicationProperty", "OUT", property);
    }

    /**
     * @param name
     * @param value
     * @param comment
     * @throws GenericEntityException
     */
    public void gnCreateApplicationProperty(String name, String value, String comment) throws GenericEntityException {
        GenericValue gv = delegator.create("GnApplicationProperty");
        gv.set("name", name);
        gv.set("value", value);
        gv.set("comment", comment);
        delegator.create(gv);
    }

    /**
     * @param name
     * @param value
     * @param comment
     * @throws GenericEntityException
     * @throws GnServiceException
     */
    public void gnUpdateApplicationProperty(String name, String value, String comment) throws GenericEntityException, GnServiceException {
        GenericValue gv = delegator.findOne("GnApplicationProperty", true, "name", name);
        if (UtilValidate.isEmpty(gv))
            throw new GnServiceException(OfbizErrors.INVALID_PARAMETERS, "properties with name[" + name + "] not found");
        gv.set("name", name);
        gv.set("value", value);
        gv.set("comment", comment);
        delegator.store(gv);
    }

    /**
     * @param name
     * @return
     * @throws GenericEntityException
     */
    public Map<String, Object> gnRemoveApplicationProperty(String name) throws GenericEntityException, GenericServiceException {
        GenericValue gv = delegator.findOne("GnApplicationProperty", true, "name", name);
        delegator.removeValue(gv);
        return dispatcher.getDispatchContext().makeValidContext("gnRemoveApplicationProperty", "OUT", gv);
    }

    /**
     * @param serviceName
     * @param userLoginId
     * @param contextId
     * @return
     * @throws GenericEntityException
     */
    public List<GenericValue> gnFindServiceInvocationLog(String serviceName, String userLoginId, String contextId) throws GenericEntityException {
        List<GenericValue> logs = delegator.findByAnd("GnServiceInvocationLog", UtilMisc.toMap("serviceName", serviceName, "userLoginId", userLoginId, "contextId", contextId),
                UtilMisc.toList("invocationDate DESC"));
        return logs;
    }

    /**
     * @param serviceName
     * @param userLoginId
     * @param contextId
     * @param invocationDate
     * @return
     * @throws GenericEntityException
     */
    public GenericValue gnCreateServiceInvocationLog(String serviceName, String userLoginId, String contextId, Timestamp invocationDate) throws GenericEntityException, GenericServiceException {
        if (invocationDate == null) invocationDate = UtilDateTime.nowTimestamp();
        GenericValue log = delegator.create("GnServiceInvocationLog", "serviceName", serviceName, "userLoginId", userLoginId, "contextId", contextId, "invocationDate", invocationDate);
        String propMax = getProperty(PropertyEnumOfbiz.MAX_INVOCATION_LOG_ITEMS);
        if (UtilValidate.isNotEmpty(propMax)) {
            int max = Integer.parseInt(propMax);
            if (max > 0) {
                List<GenericValue> logs = gnFindServiceInvocationLog(serviceName, userLoginId, contextId);
                if (logs.size() > max) {
                    for (int i = max; i < logs.size(); i++) {
                        GenericValue value = logs.get(i);
                        delegator.removeValue(value);
                    }
                }
            }
        }
        return log;
    }

    /**
     * @param property
     * @return
     * @throws GenericEntityException
     */
    private String getProperty(PropertyEnumOfbiz property) throws GenericEntityException, GenericServiceException {
        Map<String, Object> prop = gnFindApplicationProperty(property.getName());
        if (UtilValidate.isEmpty(prop)) return null;
        else
            return (String) prop.get("value");
    }
}
