package it.memelabs.gn.services.authorization;

import it.memelabs.gn.services.OfbizErrors;
import it.memelabs.gn.util.CollectionDiff;
import it.memelabs.gn.util.GnServiceException;
import it.memelabs.gn.util.SysUtil;
import javolution.util.FastList;
import javolution.util.FastMap;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilGenerics;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.service.DispatchContext;
import org.ofbiz.service.GenericServiceException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 21/05/13
 *
 * @author Andrea Fossi
 */
public class FilterHelper extends CommonAuthorizationHelper {
    public FilterHelper(DispatchContext dctx, Map<String, ? extends Object> context) {
        super(dctx, context);
        termHelper = new TermHelper(dctx, context);
    }

    private final TermHelper termHelper;
    public final static String module = TermAssocHelper.class.getName();

    public Map<String, Object> gnCreateUpdatedAuthorizationFilters(List<Map<String, Object>> inputFilters, Map<String, Object> relationshipKey, String agreementTypeId) throws GenericServiceException, GenericEntityException {
        FastMap<String, Object> result = FastMap.newInstance();
        result.put("filterIds", FastList.newInstance());

        Map<String, Object> fields = new HashMap<String, Object>();
        fields.putAll(relationshipKey);
        fields.put("agreementTypeId", agreementTypeId);

        List<GenericValue> currentFilters = delegator.findByAnd("GnAuthorizationFilterAndAgreement", fields);
        CollectionDiff<GenericValue, Map<String, Object>> diff = new CollectionDiff<GenericValue, Map<String, Object>>(currentFilters, inputFilters) {
            @Override
            protected boolean isItemEqual(GenericValue left, Map<String, Object> right) {
                if (UtilValidate.isEmpty(left.get("filterKey")) || UtilValidate.isEmpty(right.get("filterKey")))
                    return UtilValidate.areEqual(left.get("agreementId"), right.get("agreementId"));
                else
                    return UtilValidate.areEqual(left.get("filterKey"), right.get("filterKey"));
            }
        };
        diff.compare();
        for (Map<String, Object> agreement : diff.getRightOnly()) {
            UtilMisc.addToListInMap(gnCreateFilter(agreement, relationshipKey, agreementTypeId), result, "filterIds");
        }
        for (CollectionDiff.Pair<GenericValue, Map<String, Object>> termPair : diff.getBoth()) {
            UtilMisc.addToListInMap(gnUpdateFilter(termPair.getRight(), relationshipKey, agreementTypeId), result, "filterIds");
        }
        for (Map<String, Object> agreement : diff.getLeftOnly()) {
            gnRemoveFilter(agreement, relationshipKey);
        }
        return result;

    }

    public Map<String, Object> gnCreateFilter(Map<String, Object> filter, Map<String, Object> relationshipKey, String agreementTypeId) throws GenericEntityException, GenericServiceException {
        String filterKey = UUID.randomUUID().toString() + "@" + SysUtil.getInstanceId();
        String agreementId = delegator.getNextSeqId("Agreement");
        Debug.log("Creating filter[" + agreementId + "," + filterKey + "]");
        Map<String, Object> agreementMap = FastMap.newInstance();
        agreementMap.put("agreementTypeId", agreementTypeId);
        agreementMap.put("description", filter.get("description"));
        agreementMap.put("agreementId", agreementId);
        agreementMap.putAll(relationshipKey);
        GenericValue agr = delegator.makeValue("Agreement", agreementMap);
        delegator.create(agr);

        GenericValue authFilter = delegator.makeValue("GnAuthorizationFilter");
        authFilter.set("agreementId", agreementId);
        authFilter.set("filterKey", filterKey);

        authFilter.setNonPKFields(relationshipKey);
        delegator.create(authFilter);
        List<Map<String, Object>> terms = UtilGenerics.<Map<String, Object>>checkList(filter.get("agreementTerms"));
        List<String> response = termHelper.createAgreementTerms(terms, agr.getString("agreementId"), null, filterKey);
        //Debug.log(response.toString(),module);
        return UtilMisc.toMap("agreementId", agreementId, "filterKey", (Object) filterKey);
    }

    public Map<String, Object> gnUpdateFilter(Map<String, Object> filter, Map<String, Object> relationshipKey, String agreementTypeId) throws GenericEntityException, GenericServiceException {
        GenericValue authFilter = findFilterById((String) filter.get("agreementId"), (String) filter.get("filterKey"));
        String agreementId = authFilter.getString("agreementId");
        String filterKey = authFilter.getString("filterKey");
        Debug.log("Updating filter[" + agreementId + "," + filterKey + "]");
        GenericValue agreement = delegator.findOne("Agreement", UtilMisc.toMap("agreementId", agreementId), false);
        agreement.put("description", filter.get("description"));
        delegator.store(agreement);


        List<Map<String, Object>> terms = UtilGenerics.<Map<String, Object>>checkList(filter.get("agreementTerms"));

        termHelper.updateAgreementTerms(agreementId, null, terms, filterKey);
        //Debug.log(response.toString(),module);
        return UtilMisc.toMap("agreementId", agreementId, "filterKey", (Object) filterKey);
    }

    public void gnRemoveFilter(Map<String, Object> filter, Map<String, Object> relationshipKey) throws GenericEntityException, GenericServiceException {
        GenericValue authFilter = findFilterById((String) filter.get("agreementId"), (String) filter.get("filterKey"));
        String agreementId = authFilter.getString("agreementId");
        String filterKey = authFilter.getString("filterKey");
        GenericValue agreement = delegator.findOne("Agreement", UtilMisc.toMap("agreementId", agreementId), false);
        Debug.log("Removing filter[" + agreementId + "," + filterKey + "]");
        List<Map<String, Object>> terms = termHelper.findAuthorizationAndAgreementTerms((String) filter.get("agreementId"), null, false);
        for (Map<String, Object> term : terms) {
            termHelper.deleteAgreementTerm(term);
        }
        delegator.removeValue(authFilter);
        delegator.removeValue(agreement);
    }

    public List<Map<String, Object>> gnFindFilterByRelationship(Map<String, Object> relationshipKey, String agreementTypeId, boolean loadInfoHolderConstraint) throws GenericEntityException, GenericServiceException {
        Map<String, Object> fields = new HashMap<String, Object>();
        fields.putAll(relationshipKey);
        fields.put("agreementTypeId", agreementTypeId);
        List<GenericValue> filters = delegator.findByAnd("GnAuthorizationFilterAndAgreement", fields, UtilMisc.toList("agreementId"));
        List<Map<String, Object>> result = FastList.newInstance();

        for (GenericValue _filter : filters) {
            Map<String, Object> filter = UtilMisc.makeMapWritable(_filter);
            List<Map<String, Object>> terms = termHelper.findAuthorizationAndAgreementTerms((String) filter.get("agreementId"), null, loadInfoHolderConstraint);
            filter.put("agreementTerms", terms);
            result.add(filter);
        }

        return result;
    }

    /**
     * Finds authorization by id and it doesn't  load any associated entity
     *
     * @param agreementId
     * @param filterKey
     * @return
     * @throws GenericEntityException
     */
    public GenericValue findFilterById(String agreementId, String filterKey) throws GenericEntityException, GenericServiceException {
        GenericValue filter = null;
        if (UtilValidate.isEmpty(filterKey))
            filter = delegator.findOne("GnAuthorizationFilter", UtilMisc.toMap("agreementId", agreementId), false);
        else {
            List<GenericValue> l = delegator.findByAnd("GnAuthorizationFilter", UtilMisc.toMap("filterKey", filterKey));
            if (l.size() > 1) throw new GnServiceException(OfbizErrors.ENTITY_EXCEPTION, "more than one filter found");
            if (l.size() == 1) filter = l.get(0);
        }
        return filter;
    }
}
