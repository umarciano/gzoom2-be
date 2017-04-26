package it.memelabs.gn.services.authorization.filter;

import it.memelabs.gn.services.authorization.CommonAuthorizationHelper;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.service.DispatchContext;
import org.ofbiz.service.GenericServiceException;

import java.util.List;
import java.util.Map;

/**
 * 23/05/13
 *
 * @author Andrea Fossi
 */
public class FilterMatcherHelper extends CommonAuthorizationHelper {
    public FilterMatcherHelper(DispatchContext dctx, Map<String, ? extends Object> context) {
        super(dctx, context);
    }

    /**
     * Find operations
     *
     * @return
     * @throws org.ofbiz.service.GenericServiceException
     *
     */
    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> findOperations() throws GenericServiceException {
        Map<String, Object> ret = dispatcher.runSync("gnFindOperations", UtilMisc.toMap("userLogin", userLogin));
        return (List<Map<String, Object>>) ret.get("operations");
    }

    /**
     * Find operations
     *
     * @return
     * @throws org.ofbiz.service.GenericServiceException
     *
     */
    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> findPackagings() throws GenericServiceException {
        Map<String, Object> ret = dispatcher.runSync("gnFindPackaging", UtilMisc.toMap("userLogin", userLogin));
        return (List<Map<String, Object>>) ret.get("packagings");
    }

    /**
     * Find operations
     *
     * @return
     * @throws org.ofbiz.service.GenericServiceException
     *
     */
    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> findWasteCerCodes() throws GenericServiceException {
        Map<String, Object> ret = dispatcher.runSync("gnFindWasteCerCodes", UtilMisc.toMap("userLogin", userLogin));
        return (List<Map<String, Object>>) ret.get("wasteCerCodes");
    }

    /**
     * @param wasteCerCodeId
     * @return
     * @throws GenericEntityException
     */
    @SuppressWarnings("unchecked")
    public Map<String, Object> findWasteCerCode(String wasteCerCodeId) throws GenericEntityException {

        return delegator.findOne("GnWasteCerCode", UtilMisc.toMap("wasteCerCodeId", wasteCerCodeId), true);
    }

    /**
     * @param packagingId
     * @return
     * @throws GenericEntityException
     */
    @SuppressWarnings("unchecked")
    public Map<String, Object> findPackaging(String packagingId) throws GenericEntityException {

        return delegator.findOne("GnPackaging", UtilMisc.toMap("packagingId", packagingId), true);
    }

    /**
     * @param operationId
     * @return
     * @throws GenericEntityException
     */
    @SuppressWarnings("unchecked")
    public Map<String, Object> findOperation(String operationId) throws GenericEntityException {

        return delegator.findOne("GnOperation", UtilMisc.toMap("operationId", operationId), true);
    }

}
