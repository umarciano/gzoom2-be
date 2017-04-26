package it.memelabs.gn.services.node;

import it.memelabs.gn.services.OfbizErrors;
import it.memelabs.gn.services.authorization.AgreementTermTypeOfbiz;
import it.memelabs.gn.services.authorization.HazardousnessTypeOfbiz;
import it.memelabs.gn.util.GnServiceException;
import it.memelabs.gn.util.GnServiceUtil;
import it.memelabs.gn.util.PartyNodeUtil;
import it.memelabs.gn.util.PartyRelationshipIdUtil;
import javolution.util.FastList;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilDateTime;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.service.DispatchContext;
import org.ofbiz.service.ServiceUtil;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 02/04/13
 *
 * @author Andrea Fossi
 */
public class PartyRelationshipService {
    private static final String module = PartyRelationshipService.class.getName();

    /**
     * DELEGATES_TO,
     * BELONGS_TO,
     * OWNS,
     * GN_PROPAGATES_TO,
     * GN_RECEIVES_FROM;
     * PUBLISHES_AUTHORIZATION_TO,
     *
     * @param ctx
     * @param context
     * @return
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> gnCreatePartyNodeRelationship(DispatchContext ctx, Map<String, Object> context) {
        try {
            String partyRelationshipTypeId = (String) context.get("partyRelationshipTypeId");
            Timestamp fromDate = (Timestamp) context.get("fromDate");
            String partyIdFrom = (String) context.get("partyIdFrom");
            String partyIdTo = (String) context.get("partyIdTo");
            String nodeKeyFrom = (String) context.get("nodeKeyFrom");
            String nodeKeyTo = (String) context.get("nodeKeyTo");
            String roleTypeIdFrom = (String) context.get("roleTypeIdFrom");
            String roleTypeIdTo = (String) context.get("roleTypeIdTo");
            String validationRequired = (String) context.get("validationRequired");
            String synchronizationAllowed = (String) context.get("synchronizationAllowed");
            String acceptAllIfEmptyFilters = (String) context.get("acceptAllIfEmptyFilters");
            String relationshipName = (String) context.get("relationshipName");
            String createReverse = (String) context.get("createReverse");

            partyIdTo = PartyNodeUtil.getPartyId(ctx, context, partyIdTo, nodeKeyTo, "PartyIdTo and NodeKeyTo cannot be empty both.");
            partyIdFrom = PartyNodeUtil.getPartyId(ctx, context, partyIdFrom, nodeKeyFrom, "PartyIdFrom and NodeKeyFrom cannot be empty both.");

            Map<String, Object> result = ServiceUtil.returnSuccess();
            result.putAll(new PartyRelationshipHelper(ctx, context).gnCreatePartyNodeRelationship(partyIdFrom, roleTypeIdFrom, partyIdTo, roleTypeIdTo, partyRelationshipTypeId, fromDate, validationRequired, synchronizationAllowed, acceptAllIfEmptyFilters, relationshipName, createReverse));
            return result;
        } catch (Throwable t) {
            Debug.logError(t, module);
            return GnServiceUtil.returnError(t);
        }
    }

    /**
     * @param ctx
     * @param context
     * @return
     */
    public static Map<String, Object> gnUpdatePartyNodeRelationship(DispatchContext ctx, Map<String, Object> context) {
        try {
            String partyRelationshipTypeId = (String) context.get("partyRelationshipTypeId");
            String validationRequired = (String) context.get("validationRequired");
            String acceptAllIfEmptyFilters = (String) context.get("acceptAllIfEmptyFilters");
            String relationshipName = (String) context.get("relationshipName");
            String partyRelationshipId = (String) context.get("partyRelationshipId");
            List<Map<String, Object>> filters = UtilMisc.getListFromMap(context, "filters");
            List<Map<String, Object>> slicingFilters = UtilMisc.getListFromMap(context, "slicingFilters");

            Map<String, Object> key;
            if (UtilValidate.isNotEmpty(partyRelationshipId))
                key = PartyRelationshipIdUtil.keyToMap(partyRelationshipId);
            else key = PartyRelationshipIdUtil.getKeyMap(context);
            if (!PartyRelationshipIdUtil.isValid(key))
                throw new GnServiceException(OfbizErrors.INVALID_PARAMETERS, "PartyRelationship key is not valid");

            PartyRelationshipHelper partyRelationshipHelper = new PartyRelationshipHelper(ctx, context);

            Map<String, String> termTypeIds = new HashMap<String, String>();
            for (Map<String, Object> slice : slicingFilters) {
                List<Map<String, Object>> terms = (List<Map<String, Object>>) slice.get("agreementTerms");
                boolean typeFound = false;
                String termTypeId;
                for (Map<String, Object> term : terms) {
                    termTypeId = (String) term.get("termTypeId");
                    if (termTypeId.equals(AgreementTermTypeOfbiz.GN_CNS_AUTH_TYPE.name())) {
                        typeFound = true;
                        List<String> termTypeIdsFound = (List<String>) term.get("authorizationTypeIds");
                        for (String termTypeIdFound : termTypeIdsFound) {
                            if (termTypeIds.get(termTypeIdFound) != null) {
                                throw new GnServiceException(OfbizErrors.INVALID_PARAMETERS,
                                        "The term type " + termTypeIdFound + " is present in many slicing filters: a term type could be selected in only one slicing filter.");
                            } else {
                                termTypeIds.put(termTypeIdFound, (String) term.get("authorizationTermKey"));
                            }
                        }
                        break;
                    } else if (termTypeId.equals(AgreementTermTypeOfbiz.GN_CNS_WASTE.name())) {
                        if (!HazardousnessTypeOfbiz.GN_HAZARDOUS_ANY.is(term.get("hazardousnessTypeId"))) {
                            throw new GnServiceException(OfbizErrors.INVALID_PARAMETERS,
                                    "The hazardousness of the waste term of the filters is expected to be ANY.");
                        }
                    }
                }
                if (!typeFound) {
                    throw new GnServiceException(OfbizErrors.INVALID_PARAMETERS,
                            "Every slicing filter must have a term type in his agreement terms.");
                }
            }

            Map<String, Object> result = ServiceUtil.returnSuccess();
            partyRelationshipHelper.gnUpdatePartyNodeRelationship(key, partyRelationshipTypeId, validationRequired,
                    acceptAllIfEmptyFilters, relationshipName, filters, slicingFilters);
            result.put("partyRelationship", partyRelationshipHelper.gnFindPartyRelationshipById(key));
            return result;
        } catch (Throwable t) {
            Debug.logError(t, module);
            return GnServiceUtil.returnError(t);
        }
    }

    @SuppressWarnings("unchecked")
    public static Map<String, Object> gnCreatePartyNodeRelationships(DispatchContext ctx, Map<String, Object> context) {
        try {
            Map<String, Object> result = ServiceUtil.returnSuccess();
            Timestamp fromDate = (Timestamp) context.get("fromDate");
            String createReverse = (String) context.get("createReverse");
            if (UtilValidate.isEmpty(fromDate)) fromDate = UtilDateTime.nowTimestamp();
            List<Map<String, Object>> relationships = (List<Map<String, Object>>) context.get("partyRelationships");
            PartyRelationshipHelper partyRelationshipHelper = new PartyRelationshipHelper(ctx, context);
            result.put("reversePartyRelationshipIds", FastList.<String>newInstance());
            result.put("partyRelationshipIds", FastList.<String>newInstance());
            for (Map<String, Object> rel : relationships) {
                String partyRelationshipTypeId = (String) rel.get("partyRelationshipTypeId");
                String partyIdFrom = (String) rel.get("partyIdFrom");
                String partyIdTo = (String) rel.get("partyIdTo");
                String nodeKeyFrom = (String) rel.get("nodeKeyFrom");
                String nodeKeyTo = (String) rel.get("nodeKeyTo");
                String roleTypeIdFrom = (String) rel.get("roleTypeIdFrom");
                String roleTypeIdTo = (String) rel.get("partyIdTo");
                String validationRequired = (String) rel.get("validationRequired");
                String synchronizationAllowed = (String) rel.get("synchronizationAllowed");
                String acceptAllIfEmptyFilters = (String) rel.get("acceptAllIfEmptyFilters");
                String relationshipName = (String) rel.get("relationshipName");
                partyIdTo = PartyNodeUtil.getPartyId(ctx, context, partyIdTo, nodeKeyTo, "PartyIdTo and NodeKeyTo cannot be empty both.");
                partyIdFrom = PartyNodeUtil.getPartyId(ctx, context, partyIdFrom, nodeKeyFrom, "PartyIdFrom and NodeKeyFrom cannot be empty both.");
                Map<String, Object> ret = partyRelationshipHelper.gnCreatePartyNodeRelationship(partyIdFrom,
                        roleTypeIdFrom, partyIdTo, roleTypeIdTo, partyRelationshipTypeId, fromDate, validationRequired,
                        synchronizationAllowed, acceptAllIfEmptyFilters, relationshipName, createReverse);
                String reversePartyRelationshipId = (String) ret.get("reversePartyRelationshipId");
                String partyRelationshipId = (String) ret.get("partyRelationshipId");
                if (UtilValidate.isNotEmpty(reversePartyRelationshipId))
                    UtilMisc.addToListInMap(reversePartyRelationshipId, result, "reversePartyRelationshipIds");
                if (UtilValidate.isNotEmpty(partyRelationshipId))
                    UtilMisc.addToListInMap(partyRelationshipId, result, "partyRelationshipIds");
            }
            return result;
        } catch (Throwable t) {
            Debug.logError(t, module);
            return GnServiceUtil.returnError(t);
        }
    }

    @SuppressWarnings("unchecked")
    public static Map<String, Object> gnFindPartyRelationships(DispatchContext ctx, Map<String, ?> context) {
        try {
            Map<String, Object> result = ServiceUtil.returnSuccess();

            boolean validOnly = "Y".equalsIgnoreCase((String) context.get("validOnly"));
            Collection<String> partyIdsFrom = (Collection<String>) context.get("partyIdsFrom");
            Collection<String> roleTypeIdsFrom = (Collection<String>) context.get("roleTypeIdsFrom");
            Collection<String> partyRelationshipTypeIds = (Collection<String>) context.get("partyRelationshipTypeIds");
            Collection<String> partyIdsTo = (Collection<String>) context.get("partyIdsTo");
            Collection<String> roleTypeIdsTo = (Collection<String>) context.get("roleTypeIdsTo");

            result.put("partyRelationships", new PartyRelationshipHelper(ctx, context).gnFindPartyRelationships(partyIdsFrom, roleTypeIdsFrom,
                    partyRelationshipTypeIds, partyIdsTo, roleTypeIdsTo, validOnly));
            return result;
        } catch (Throwable t) {
            Debug.logError(t, module);
            return GnServiceUtil.returnError(t);
        }
    }

    @SuppressWarnings("unchecked")
    public static Map<String, Object> gnFindPartyRelationshipById(DispatchContext ctx, Map<String, ?> context) {
        try {
            Map<String, Object> result = ServiceUtil.returnSuccess();
            String partyRelationshipId = (String) context.get("partyRelationshipId");
            Map<String, Object> key;
            if (UtilValidate.isEmpty(partyRelationshipId)) {
                key = PartyRelationshipIdUtil.getKeyMap((Map<String, Object>) context);
            } else {
                key = PartyRelationshipIdUtil.keyToMap(partyRelationshipId);
            }
            if (!PartyRelationshipIdUtil.isValid(key))
                throw new GnServiceException(OfbizErrors.INVALID_PARAMETERS, "PartyRelationship id is not valid");

            result.put("partyRelationship", new PartyRelationshipHelper(ctx, context).gnFindPartyRelationshipById(key));
            return result;
        } catch (Throwable t) {
            Debug.logError(t, module);
            return GnServiceUtil.returnError(t);
        }
    }

}
