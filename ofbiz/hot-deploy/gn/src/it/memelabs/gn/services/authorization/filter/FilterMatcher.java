package it.memelabs.gn.services.authorization.filter;

import it.memelabs.gn.services.authorization.AgreementTermTypeOfbiz;
import it.memelabs.gn.services.authorization.HazardousnessTypeOfbiz;
import it.memelabs.gn.util.CollectionDiff;
import it.memelabs.gn.util.MapUtil;
import javolution.util.FastList;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilGenerics;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.service.GenericServiceException;

import java.util.*;

/**
 * 23/05/13
 *
 * @author Andrea Fossi
 */
public class FilterMatcher {
    private static final String module = FilterMatcher.class.getName();


    private Map<String, Object> authorization;
    private Map<String, Object> filter;
    private StringBuffer sb = new StringBuffer();
    private FilterMatcherHelper filterMatcherHelper;
    private GeoHelper geoHelper;

    public FilterMatcher(Map<String, Object> authorization, Map<String, Object> filter, FilterMatcherHelper filterMatcherHelper, GeoHelper geoHelper) {
        this.authorization = authorization;
        this.filter = filter;
        this.filterMatcherHelper = filterMatcherHelper;
        this.geoHelper = geoHelper;
    }

    public String getMessage() {
        return sb.toString();
    }

    private void append(String msg) {
        sb.append(msg.replaceAll("\\*", "   "));
        sb.append("\n");
    }

    public boolean match() throws GenericEntityException, GenericServiceException {

        boolean matchHeadline = matchHeadline();
        List<Map<String, Object>> items = UtilGenerics.checkList(authorization.get("authorizationItems"));
        boolean isInclusive = true;

        List<Map<String, Object>> filterTerms = UtilGenerics.checkList(filter.get("agreementTerms"));
        for (Map<String, Object> filterTerm : filterTerms) {
            isInclusive = isInclusive & isTermInclusive(filterTerm);
        }

        boolean matchItems = !isInclusive;//if filter isInclusive matchItems must be initialized as true

        for (Map<String, Object> item : items) {
            boolean matchItem = matchItem(item);

            List<Map<String, Object>> itemTerms = UtilGenerics.checkList(item.get("agreementTerms"));
            for (Map<String, Object> itemTerm : itemTerms) {
                matchItem = matchItem & matchTerm(itemTerm);
            }
            //one item match
            if (isInclusive) {//if all terms are inclusive
                matchItems = matchItems | matchItem;
            } else { //if at least one term is not inclusive
                matchItems = matchItems & matchItem;
            }
        }
        return matchItems & matchHeadline;
    }

    private boolean matchHeadline() throws GenericEntityException {
        append("Matching headline");
        boolean at = matchAuthorizationType();
        boolean h = matchHolder();
        return at & h;
    }


    private boolean matchItem(Map<String, Object> item) {
        append("*Matching item[" + item.get("agreementItemSeqId") + "]");
        boolean match = true;
        match = match & matchSubjectRole(item);

        return match;
    }

    private boolean matchTerm(Map<String, Object> term) throws GenericServiceException {
        append("**Matching term[" + term.get("agreementTermId") + "]");
        boolean match = true;
        String termTypeId = (String) term.get("termTypeId");
        if (AgreementTermTypeOfbiz.GN_CNS_OPERATION.is(termTypeId)) match = match & matchOperation(term);
        if (AgreementTermTypeOfbiz.GN_CNS_PACKAGING.is(termTypeId)) match = match & matchPackagings(term);
        if (AgreementTermTypeOfbiz.GN_CNS_VEHICLE_REG_N.is(termTypeId)) match = match & matchRegistrationNumbers(term);
        if (AgreementTermTypeOfbiz.GN_CNS_WASTE.is(termTypeId)) match = match & matchWastes(term);

        return match;
    }

    /**
     * Check that all allowed term are inclusive.
     *
     * @param term
     * @return
     * @throws GenericServiceException
     */
    private boolean isTermInclusive(Map<String, Object> term) throws GenericServiceException {
        String termTypeId = (String) term.get("termTypeId");
        append("** term[" + term.get("agreementTermId") + "] is inclusive[" + term.get("inclusive") + "]");
        if (AgreementTermTypeOfbiz.GN_CNS_OPERATION.is(termTypeId) ||
                AgreementTermTypeOfbiz.GN_CNS_PACKAGING.is(termTypeId) ||
                AgreementTermTypeOfbiz.GN_CNS_VEHICLE_REG_N.is(termTypeId) ||
                AgreementTermTypeOfbiz.GN_CNS_WASTE.is(termTypeId) ||
                AgreementTermTypeOfbiz.GN_CNS_SUBJ_ROLE.is(termTypeId)
                ) return "Y".equals(term.get("inclusive"));
        // Debug.logWarning(String.format("Filter term[%s] not allowed", termTypeId), module);
        return true;
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

    /**
     * math holder
     *
     * @return
     */
    private boolean matchHolder() throws GenericEntityException {
        Map<String, Object> partyNodeTo = UtilGenerics.checkMap(authorization.get("partyNodeTo"));
        Map<String, Object> otherPartyNodeTo = UtilGenerics.checkMap(authorization.get("otherPartyNodeTo"));
        String taxIdentificationNumber = (String) otherPartyNodeTo.get("taxIdentificationNumber");

        String postalCodeGeoId = MapUtil.getValue(partyNodeTo, "address.postalCodeGeoId", String.class);

        Map<String, Object> term = getTerm(AgreementTermTypeOfbiz.GN_CNS_HOLDER.name());
        if (term == null) {
            append("*[true] HolderTerm not found");
            return true;
        } else {
            boolean inclusive = "Y".equals(term.get("inclusive"));
            @SuppressWarnings("unchecked")
            List<Map<String, Object>> partyNodes = (List<Map<String, Object>>) term.get("partyNodes");
            boolean result1 = true;
            if (UtilValidate.isNotEmpty(partyNodes)) {
                result1 = contains(taxIdentificationNumber, partyNodes, "taxIdentificationNumber");
                if (!inclusive) result1 = !result1;
                append("*[" + result1 + "] HolderTerm match[" + taxIdentificationNumber + "] inclusive[" + inclusive + "]");
            } else {
                result1 = true;
                append("*[" + result1 + "] HolderTerm  taxIdentificationNumbers collection is empty.");
            }
            /// geos check
            boolean result2;
            @SuppressWarnings("unchecked")
            List<Map<String, Object>> geos = (List<Map<String, Object>>) term.get("geos");
            if (UtilValidate.isNotEmpty(geos) && UtilValidate.isNotEmpty(postalCodeGeoId)) {
                List<String> authGeoIds = geoHelper.findParents(postalCodeGeoId);
                result2 = containValues(authGeoIds, geos, "geoId");
                if (!inclusive) result2 = !result2;
                append("*[" + result2 + "] HolderTerm match[" + postalCodeGeoId + "] inclusive[" + inclusive + "]");
            } else {
                result2 = true;
                append("*[" + result2 + "] HolderTerm  geos collection is empty.");
            }

            return result1 & result2;
        }
    }

    //--------------------------------------------  items


    /**
     * item
     *
     * @param item
     * @return
     */
    private boolean matchSubjectRole(Map<String, Object> item) {
        String roleId = (String) item.get("holderRoleId");
        Map<String, Object> term = getTerm(AgreementTermTypeOfbiz.GN_CNS_SUBJ_ROLE.name());
        if (term == null) {
            append("**[true] SubjectRoleTerm not found");
            return true;
        } else {
            boolean inclusive = "Y".equals(term.get("inclusive"));
            @SuppressWarnings("unchecked")
            List<Map<String, Object>> roles = (List<Map<String, Object>>) term.get("subjectRoles");
            boolean result = contains(roleId, roles, "enumId");
            if (!inclusive) result = !result;
            append("**[" + result + "] SubjectRole match[" + roleId + "] inclusive[" + inclusive + "]");
            return result;
        }
    }

    /**
     * item
     *
     * @param itemTerm
     * @return
     */
    @SuppressWarnings("unchecked")
    private boolean matchOperation(Map<String, Object> itemTerm) throws GenericServiceException {

        Map<String, Object> filterTerm = getTerm(AgreementTermTypeOfbiz.GN_CNS_OPERATION.name());
        CollectionMatcher m = new CollectionMatcher() {
            @Override
            public List<Map<String, Object>> getDomain() throws GenericServiceException {
                return filterMatcherHelper.findOperations();
            }
        };
        boolean result = m.match(itemTerm, filterTerm, "operations", "operationId");
        append("***" + m.getMessage() + " - check operationTerm");
        return result;
    }

    /**
     * item
     *
     * @param itemTerm
     * @return
     */
    @SuppressWarnings("unchecked")
    private boolean matchPackagings(Map<String, Object> itemTerm) throws GenericServiceException {

        Map<String, Object> filterTerm = getTerm(AgreementTermTypeOfbiz.GN_CNS_PACKAGING.name());
        CollectionMatcher m = new CollectionMatcher() {
            @Override
            public List<Map<String, Object>> getDomain() throws GenericServiceException {
                return filterMatcherHelper.findPackagings();
            }
        };
        boolean result = m.match(itemTerm, filterTerm, "packagings", "packagingId");
        append("***" + m.getMessage() + " - check packagingTerm");
        return result;
    }

    /**
     * item
     *
     * @param itemTerm
     * @return
     */
    @SuppressWarnings("unchecked")
    private boolean matchRegistrationNumbers(Map<String, Object> itemTerm) throws GenericServiceException {

        Map<String, Object> filterTerm = getTerm(AgreementTermTypeOfbiz.GN_CNS_VEHICLE_REG_N.name());
        CollectionMatcher m = new CollectionMatcher() {
            @Override
            public List<Map<String, Object>> getDomain() throws GenericServiceException {
                return FastList.newInstance();
            }
        };
        boolean result = m.match(itemTerm, filterTerm, "registrationNumbers", "registrationNumber");
        append("***" + m.getMessage() + " - check registrationNumbersTerm");
        return result;
    }

    /**
     * item
     *
     * @param itemTerm
     * @return
     */
    @SuppressWarnings("unchecked")
    private boolean matchWastes(Map<String, Object> itemTerm) throws GenericServiceException {
        Map<String, Object> filterTerm = getTerm(AgreementTermTypeOfbiz.GN_CNS_WASTE.name());
        if (filterTerm == null) {
            append("**[true] wasteTerm  not found");
            return true;
        }
        boolean result = false;
        //HazardousnessTypeOfbiz
        String itemHazardouness = (String) itemTerm.get("hazardousnessTypeId");
        String filterHazardouness = (String) filterTerm.get("hazardousnessTypeId");
        boolean itemInclusive = "Y".equals(itemTerm.get("inclusive"));
        boolean filterInclusive = "Y".equals(filterTerm.get("inclusive"));
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
        append("*** itemInclusive[" + itemInclusive + "]");
        append("*** filterInclusive[" + filterInclusive + "]");
        //  itemHazardouness and filterHazardouness ANY
        if (HazardousnessTypeOfbiz.GN_HAZARDOUS_ANY.is(itemHazardouness) &&
                HazardousnessTypeOfbiz.GN_HAZARDOUS_ANY.is(filterHazardouness)) {
            CollectionMatcher m = new CollectionMatcher() {
                @Override
                public List<Map<String, Object>> getDomain() throws GenericServiceException {
                    return filterMatcherHelper.findWasteCerCodes();
                }
            };
            result = m.match(itemTerm, filterTerm, "wasteCerCodes", "wasteCerCodeId");
            append("***" + m.getMessage() + " - check WasteCerCodeTerm (hazardous any flag on both)");
        } else // itemHazardouness ANY  filterHazardouness HAZARDOUS / NOT_HAZARDOUS
            if (HazardousnessTypeOfbiz.GN_HAZARDOUS_ANY.is(itemHazardouness)
                    && !HazardousnessTypeOfbiz.GN_HAZARDOUS_ANY.is(filterHazardouness)) {
                String hazardous = (HazardousnessTypeOfbiz.GN_HAZARDOUS.is(filterHazardouness)) ? "Y" : "N";
                List<Map<String, Object>> authWasteCerCodes = (List<Map<String, Object>>) itemTerm.get("wasteCerCodes");
                if (itemInclusive) {
                    result = contains(hazardous, authWasteCerCodes, "hazardous");
                    append("***[" + result + "] AuthorizationTerm contains almost one [" + filterHazardouness + "] wasteCerCode");
                } else {
                    CollectionDiff<Map<String, Object>, Map<String, Object>> diff =
                            new CollectionDiff<Map<String, Object>, Map<String, Object>>(authWasteCerCodes, filterMatcherHelper.findWasteCerCodes()) {
                                @Override
                                protected boolean isItemEqual(Map<String, Object> left, Map<String, Object> right) {
                                    String leftId = (String) left.get("wasteCerCodeId");
                                    String rightId = (String) right.get("wasteCerCodeId");
                                    return UtilValidate.areEqual(leftId, rightId);
                                }
                            };
                    diff.compare();
                    List<Map<String, Object>> wasteCerCodeNotInAuth = diff.getRightOnly();
                    result = contains(hazardous, wasteCerCodeNotInAuth, "hazardous");
                    append("***[" + result + "] AuthorizationTerm excluded wasteCerCode contain almost one [" + filterHazardouness + "] wasteCerCode");
                }
            } else  // itemHazardouness HAZARDOUS / NOT_HAZARDOUS filterHazardouness ANY
                if (!HazardousnessTypeOfbiz.GN_HAZARDOUS_ANY.is(itemHazardouness)
                        && HazardousnessTypeOfbiz.GN_HAZARDOUS_ANY.is(filterHazardouness)) {
                    String hazardous = (HazardousnessTypeOfbiz.GN_HAZARDOUS.is(itemHazardouness)) ? "Y" : "N";
                    List<Map<String, Object>> filterWasteCerCodes = (List<Map<String, Object>>) filterTerm.get("wasteCerCodes");
                    if (filterInclusive) {
                        result = contains(hazardous, filterWasteCerCodes, "hazardous");
                        append("***[" + result + "] FilterTerm contains almost one [" + itemHazardouness + "] wasteCerCode");
                    } else {
                        CollectionDiff<Map<String, Object>, Map<String, Object>> diff =
                                new CollectionDiff<Map<String, Object>, Map<String, Object>>(filterWasteCerCodes, filterMatcherHelper.findWasteCerCodes()) {
                                    @Override
                                    protected boolean isItemEqual(Map<String, Object> left, Map<String, Object> right) {
                                        String leftId = (String) left.get("wasteCerCodeId");
                                        String rightId = (String) right.get("wasteCerCodeId");
                                        boolean b = UtilValidate.areEqual(leftId, rightId);
                                        return b;
                                    }
                                };
                        //diff.setLeftOnlyEnabled(true);
                        diff.compare();
                        List<Map<String, Object>> wasteCerCodeNotInFilter = diff.getRightOnly();
                        result = contains(hazardous, wasteCerCodeNotInFilter, "hazardous");
                        append("***[" + result + "] FilterTerm excluded wasteCerCode contains almost one [" + itemHazardouness + "]");
                    }
                } else {
                    result = UtilValidate.areEqual(itemHazardouness, filterHazardouness);
                    append("***[" + result + "] AuthorizationTerm and FilterTerm have same dangerous type.");
                }

        return result;
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
    private boolean contains(Object valueToMatch, Collection<Map<String, Object>> entries, String key) {
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
