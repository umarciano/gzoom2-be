package it.memelabs.gn.services.authorization.filter;

import it.memelabs.gn.services.authorization.AgreementTermTypeOfbiz;
import it.memelabs.gn.services.authorization.HazardousnessTypeOfbiz;
import it.memelabs.gn.util.CloneUtil;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilGenerics;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.service.GenericServiceException;

import java.util.*;

/**
 * 10/07/14
 *
 * @author Andrea Fossi
 */
public class SlicingFilterMatcher {
    private static final String module = SlicingFilterMatcher.class.getName();

    private Map<String, Object> authorization;
    private Map<String, Object> filter;
    private StringBuffer sb = new StringBuffer();
    private FilterMatcherHelper filterMatcherHelper;
    private GeoHelper geoHelper;
    private boolean sliceDone;

    public SlicingFilterMatcher(Map<String, Object> authorization, Map<String, Object> filter, FilterMatcherHelper filterMatcherHelper, GeoHelper geoHelper) {
        this.authorization = authorization;
        this.filter = filter;
        this.filterMatcherHelper = filterMatcherHelper;
        this.geoHelper = geoHelper;
        this.sliceDone = false;
    }

    public boolean isSliceDone() {
        return sliceDone;
    }

    public String getMessage() {
        return sb.toString();
    }

    private void append(String msg) {
        sb.append(msg.replaceAll("\\*", "   "));
        sb.append("\n");
    }

    /**
     * Return type:
     * null - matchHeadline headline doesn't math
     * auth without items - items don't match
     * auth with one or more items - at least one item match filter
     *
     * @return three state
     * @throws GenericEntityException
     * @throws GenericServiceException
     */
    public List<Map<String, Object>> matchSlicingFilters() throws GenericEntityException, GenericServiceException {
        Map<String, Object> slicedAuth = CloneUtil.deepClone(authorization);
        boolean matchHeadline = matchHeadline();
        if (matchHeadline) {
            List<Map<String, Object>> slicedItems = new LinkedList<Map<String, Object>>();
            List<Map<String, Object>> items = UtilGenerics.checkList(slicedAuth.get("authorizationItems"));
            for (Map<String, Object> item : items) {
                boolean matchItem = matchItem(item);
                if (matchItem) {
                    List<Map<String, Object>> itemTerms = UtilGenerics.checkList(item.get("agreementTerms"));
                    List<Map<String, Object>> slicedTerms = new LinkedList<Map<String, Object>>();
                    boolean matchTerms = true;
                    Map<String, Object> slicedTerm;
//                    String termTypeId;
//                    boolean foundOperationTerm = false;
//                    boolean foundWasteTerm = false;
                    for (Map<String, Object> itemTerm : itemTerms) {
//                        termTypeId = (String) itemTerm.get("termTypeId");
//                        if (AgreementTermTypeOfbiz.GN_CNS_OPERATION.is(termTypeId)) foundOperationTerm = true;
//                        else if (AgreementTermTypeOfbiz.GN_CNS_WASTE.is(termTypeId)) foundWasteTerm = true;
                        slicedTerm = sliceTerm(itemTerm);
                        if (slicedTerm != null) {
                            slicedTerms.add(slicedTerm);
                        } else {
                            sliceDone = true;
                            matchTerms = false;
                            break;
                        }
                    }
                    if (matchTerms) {
/*                        if (!foundOperationTerm) {
                            Map<String, Object> filterTerm = getTerm(AgreementTermTypeOfbiz.GN_CNS_OPERATION.name());
                            if (filterTerm != null) {
                                Map<String, Object> newSlicedTerm = cloneTerm((String) slicedAuth.get("agreementId"), filterTerm);
                                slicedTerms.add(newSlicedTerm);
                            }
                        }
                        if (!foundWasteTerm) {
                            Map<String, Object> filterTerm = getTerm(AgreementTermTypeOfbiz.GN_CNS_WASTE.name());
                            if (filterTerm != null) {
                                Map<String, Object> newSlicedTerm = cloneTerm((String) slicedAuth.get("agreementId"), filterTerm);
                                slicedTerms.add(newSlicedTerm);
                            }
                        }*/
                        item.put("agreementTerms", slicedTerms);
                        slicedItems.add(item);
                    }
                } else {
                    //slicedTerms no added to slicedItems
                    sliceDone = true;
                }
            }
            slicedAuth.put("authorizationItems", slicedItems);
            return slicedItems;
        } else {
            return Collections.emptyList();
        }
    }

    private boolean matchHeadline() throws GenericEntityException {
        append("Matching headline");
        boolean authType = matchAuthorizationType();
        return authType;
    }


    private boolean matchItem(Map<String, Object> item) {
        append("*Matching item[" + item.get("agreementItemSeqId") + "]");
        boolean match = matchCategoryClass(item);
        return match;
    }

    private Map<String, Object> sliceTerm(Map<String, Object> term) throws GenericServiceException, GenericEntityException {
        append("**Matching term[" + term.get("agreementTermId") + "]");
        String termTypeId = (String) term.get("termTypeId");
        if (AgreementTermTypeOfbiz.GN_CNS_OPERATION.is(termTypeId)) term = sliceOperation(term);
        else if (AgreementTermTypeOfbiz.GN_CNS_WASTE.is(termTypeId)) term = sliceWastes(term);
        return term;
    }


    //-------------------------------------------- headlines

    /**
     * headline
     *
     * @return
     */
    private boolean matchAuthorizationType() {
        String type = (String) authorization.get("typeId");
        Map<String, Object> term = getTerm(AgreementTermTypeOfbiz.GN_CNS_AUTH_TYPE.name());
        if (term == null) {
            append("*[true] AuthorizationTypeTerm not found");
            return true;
        } else {
            boolean inclusive = "Y".equals(term.get("inclusive"));
            List<Map<String, Object>> types = UtilGenerics.<Map<String, Object>>checkList(term.get("authorizationTypes"));
            boolean result = contains(type, types, "enumId");
            if (!inclusive) result = !result;
            append("*[" + result + "] AuthorizationType match[" + type + "] inclusive[" + inclusive + "]");
            return result;
        }
    }

    public static Map<String, Object> findMatchingFilterType(Map<String, Object> auth, List<Map<String, Object>> slicingFilters) throws GenericEntityException {
        String type = (String) auth.get("typeId");
        List<Map<String, Object>> terms;
        for (Map<String, Object> slicingFilter : slicingFilters) {
            terms = (List<Map<String, Object>>) slicingFilter.get("agreementTerms");
            for (Map<String, Object> t : terms) {
                if (UtilValidate.areEqual(t.get("termTypeId"), AgreementTermTypeOfbiz.GN_CNS_AUTH_TYPE.name())) {
                    if (!"Y".equals(t.get("inclusive")))
                        throw new GenericEntityException("The term of the  expected to be ANY.");
                    List<Map<String, Object>> types = UtilGenerics.<Map<String, Object>>checkList(t.get("authorizationTypes"));
                    if (contains(type, types, "enumId"))
                        return slicingFilter;
                }
            }
        }
        return null;
    }

    //--------------------------------------------  items

    /**
     * if categoryClassEnumId is empty and filter term exist return false
     * if filter term is empty return true
     * if categoryClassEnumId is not empty and filter term exist return matching result
     *
     * @param item
     * @return
     */
    private boolean matchCategoryClass(Map<String, Object> item) {
        String categoryClassId = (String) item.get("categoryClassEnumId");
        Map<String, Object> term = getTerm(AgreementTermTypeOfbiz.GN_CNS_CAT_CLASS.name());
        if (term == null) {
            append("**[true] CategoryClassTerm not found");
            return true;
        } else {
            if (UtilValidate.isEmpty(categoryClassId)) {
                append("**[false] Authorization categoryClassEnumId is empty");
                return false;
            } else {
                boolean inclusive = "Y".equals(term.get("inclusive"));
                List<Map<String, Object>> categoryClassEnums = UtilMisc.getListFromMap(term, "categoryClassEnums");
                boolean result = contains(categoryClassId, categoryClassEnums, "categoryClassEnumId");
                if (!inclusive) result = !result;
                append("**[" + result + "] CategoryClass match[" + categoryClassId + "] inclusive[" + inclusive + "]");
                return result;
            }
        }
    }

    /**
     * item
     *
     * @param itemTerm
     * @return
     */
    @SuppressWarnings("unchecked")
    private Map<String, Object> sliceOperation(Map<String, Object> itemTerm) throws GenericServiceException {
        Map<String, Object> filterTerm = getTerm(AgreementTermTypeOfbiz.GN_CNS_OPERATION.name());
        if (filterTerm == null) {
            append("**[true] filter operationTerm  not found");
            return itemTerm;
        } else {
            CollectionMatcher m = new CollectionMatcher() {
                @Override
                public List<Map<String, Object>> getDomain() throws GenericServiceException {
                    return filterMatcherHelper.findOperations();
                }
            };
            boolean match = m.match(itemTerm, filterTerm, "operations", "operationId");
            append("***" + m.getMessage() + " - check operationTerm");
            if (match) {
                List<Map<String, Object>> authOperations = (List<Map<String, Object>>) itemTerm.get("operations");
                List<Map<String, Object>> filterOperations = (List<Map<String, Object>>) filterTerm.get("operations");
                List<Map<String, Object>> slicedOperations = new LinkedList<Map<String, Object>>();
                if (authOperations.isEmpty()) {
                    slicedOperations.addAll(filterOperations);
                } else {
                    for (Map<String, Object> opAuth : authOperations) {
                        for (Map<String, Object> opFilter : filterOperations) {
                            if (opAuth.get("operationId").equals(opFilter.get("operationId"))) {
                                slicedOperations.add(opAuth);
                                break;
                            }
                        }
                    }
                }
                if (slicedOperations.size() != authOperations.size())
                    sliceDone = true;
                itemTerm.put("operations", slicedOperations); // --> slicing
                return itemTerm;
            } else {
                sliceDone = true;
                return null;
            }
        }
    }


    /**
     * item
     *
     * @param itemTerm
     * @return
     */
    @SuppressWarnings("unchecked")
    private Map<String, Object> sliceWastes(Map<String, Object> itemTerm) throws GenericServiceException, GenericEntityException {
        Map<String, Object> filterTerm = getTerm(AgreementTermTypeOfbiz.GN_CNS_WASTE.name());
        if (filterTerm == null) {
            append("**[true] filter wasteTerm  not found");
            return itemTerm;
        } else {
            String itemHazardouness = (String) itemTerm.get("hazardousnessTypeId");
            String filterHazardouness = (String) filterTerm.get("hazardousnessTypeId");
            append("***WasteTerm");
            if (UtilValidate.isEmpty(itemHazardouness)) {
                Debug.logWarning("itemHazardouness is null, setting value to[" + HazardousnessTypeOfbiz.GN_HAZARDOUS_ANY + "]", module);
                append("*** itemHazardouness is null, setting value to[" + HazardousnessTypeOfbiz.GN_HAZARDOUS_ANY + "]");
                itemHazardouness = HazardousnessTypeOfbiz.GN_HAZARDOUS_ANY.name();
            }
            if (UtilValidate.isEmpty(filterHazardouness)) {
                Debug.logWarning("filterHazardouness is null, setting value to[" + HazardousnessTypeOfbiz.GN_HAZARDOUS_ANY + "]", module);
                append("*** filterHazardouness is null, setting value to[" + HazardousnessTypeOfbiz.GN_HAZARDOUS_ANY + "]");
                filterHazardouness = HazardousnessTypeOfbiz.GN_HAZARDOUS_ANY.name();
            }
            append("*** itemHazardouness[" + itemHazardouness + "]");
            append("*** filterHazardouness[" + filterHazardouness + "]");

            if (!HazardousnessTypeOfbiz.GN_HAZARDOUS_ANY.is(filterHazardouness)) { // filterHazardouness ANY
                throw new GenericEntityException("The hazardousness of the  expected to be ANY.");
            } //Expected also itemInclusive=true, filterInclusive=true

            if (HazardousnessTypeOfbiz.GN_HAZARDOUS_ANY.is(itemHazardouness)) { //  itemHazardouness = ANY
                CollectionMatcher m = new CollectionMatcher() {
                    @Override
                    public List<Map<String, Object>> getDomain() throws GenericServiceException {
                        return filterMatcherHelper.findWasteCerCodes();
                    }
                };
                boolean match = m.match(itemTerm, filterTerm, "wasteCerCodes", "wasteCerCodeId");
                append("***" + m.getMessage() + " - check WasteCerCodeTerm (hazardous any flag on both)");

                if (match) {
                    List<Map<String, Object>> authWastes = (List<Map<String, Object>>) itemTerm.get("wasteCerCodes");
                    List<Map<String, Object>> filterWastes = (List<Map<String, Object>>) filterTerm.get("wasteCerCodes");
                    List<Map<String, Object>> slicedWastes = new LinkedList<Map<String, Object>>();
                    if (authWastes.isEmpty()) {
                        slicedWastes.addAll(filterWastes);
                    } else {
                        for (Map<String, Object> opAuth : authWastes) {
                            for (Map<String, Object> opFilter : filterWastes) {
                                if (opAuth.get("wasteCerCodeId").equals(opFilter.get("wasteCerCodeId"))) {
                                    slicedWastes.add(opAuth);
                                    break;
                                }
                            }
                        }
                    }
                    if (slicedWastes.size() != authWastes.size())
                        sliceDone = true;
                    itemTerm.put("wasteCerCodes", slicedWastes); // --> slicing
                    return itemTerm;
                } else {
                    sliceDone = true;
                    return null;
                }
            } else { // itemHazardouness HAZARDOUS / NOT_HAZARDOUS
                String hazardous = (HazardousnessTypeOfbiz.GN_HAZARDOUS.is(itemHazardouness)) ? "Y" : "N";
                List<Map<String, Object>> filterWasteCerCodes = (List<Map<String, Object>>) filterTerm.get("wasteCerCodes");
                List<Map<String, Object>> slicedWasteCerCodes = new LinkedList<Map<String, Object>>();
                for (Map<String, Object> filterWasteCerCode : filterWasteCerCodes) {
                    if (hazardous.equals(filterWasteCerCode.get("hazardous"))) {
                        slicedWasteCerCodes.add(filterWasteCerCode);
                    }
                }
                if (!slicedWasteCerCodes.isEmpty()) {
                    append("***[True] FilterTerm contains almost one [" + hazardous + "] wasteCerCode");
                    itemTerm.put("hazardousnessTypeId", HazardousnessTypeOfbiz.GN_HAZARDOUS_ANY.name());
                    itemTerm.put("wasteCerCodes", slicedWasteCerCodes); // --> slicing
                    return itemTerm;
                } else {
                    sliceDone = true;
                    return null;
                }
            }
        }
    }

    //-------------------------------------------- utilities


    private Map<String, Object> getTerm(String termTypeId) {
        @SuppressWarnings("unchecked")
        List<Map<String, Object>> terms = (List<Map<String, Object>>) filter.get("agreementTerms");
        for (Map<String, Object> term : terms)
            if (UtilValidate.areEqual(term.get("termTypeId"), termTypeId)) return term;
        return null;
    }

    /**
     * @param valueToMatch
     * @param entries
     * @param key
     * @return
     */
    private static boolean contains(Object valueToMatch, Collection<Map<String, Object>> entries, String key) {
        for (Map<String, Object> entry : entries) {
            Object value = entry.get(key);
            if (UtilValidate.areEqual(valueToMatch, value)) return true;
        }
        return false;
    }


    /**
     * @param values
     * @param entries
     * @param key
     * @return
     */
    private boolean containValues(List<? extends Object> values, List<Map<String, Object>> entries, String key) {
        for (Object value : values) {
            if (contains(value, entries, key)) return true;
        }
        return false;
    }

    private <T> Set<T> getValues(List<Map<String, Object>> entries, String key, Class<T> clazz) {
        Set<T> result = new HashSet<T>(entries.size());
        for (Map<String, Object> entry : entries) {
            @SuppressWarnings("unchecked")
            T value = (T) entry.get(key);
            result.add(value);
        }
        return result;
    }

}
