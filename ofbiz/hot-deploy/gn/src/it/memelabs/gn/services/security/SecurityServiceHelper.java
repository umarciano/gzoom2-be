package it.memelabs.gn.services.security;

import it.memelabs.gn.services.AbstractServiceHelper;
import it.memelabs.gn.services.OfbizErrors;
import it.memelabs.gn.services.auditing.EntityTypeOfbiz;
import it.memelabs.gn.services.authorization.AgreementTypesOfbiz;
import it.memelabs.gn.services.authorization.AuthorizationStatusOfbiz;
import it.memelabs.gn.services.node.PartyRoleOfbiz;
import it.memelabs.gn.util.GnServiceException;
import it.memelabs.gn.util.find.GnFindUtil;
import javolution.util.FastList;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.entity.condition.EntityCondition;
import org.ofbiz.service.DispatchContext;
import org.ofbiz.service.GenericServiceException;

import java.util.List;
import java.util.Map;

import static it.memelabs.gn.services.auditing.EntityTypeOfbiz.valueOf;

/**
 * 05/08/13
 *
 * @author Andrea Fossi
 */
public class SecurityServiceHelper extends AbstractServiceHelper {
    private static final String module = SecurityServiceHelper.class.getName();

    /**
     * @param dctx    OFBiz DispatchContext
     * @param context Service Context
     */
    public SecurityServiceHelper(DispatchContext dctx, Map<String, ? extends Object> context) {
        super(dctx, context);
    }

    public String gnHasPermission(String entityId, String entityTypeId, String permissionId) throws GenericServiceException, GenericEntityException {
        Map<String, Object> gnContext = getCurrentContext();
        GnSecurity gnSecurity = new GnSecurity(delegator);
        @SuppressWarnings("unchecked")
        Map<String, Object> partyNode = (Map<String, Object>) gnContext.get("partyNode");
        String partyNodeId = (String) partyNode.get("partyId");
        String partyNodeKey = (String) partyNode.get("nodeKey");
        boolean hasPermission = false;
        if (UtilValidate.isEmpty(entityId) && UtilValidate.isEmpty(entityTypeId)) {
            hasPermission = gnSecurity.hasPermission(permissionId, getUserLogin());
        } else {
            EntityTypeOfbiz entityTypeEnum = valueOf(entityTypeId);
            switch (entityTypeEnum) {
                case AUTHORIZATION:
                    hasPermission = gnSecurity.hasPermission(permissionId, getUserLogin()) && searchAuthorization(entityId, partyNodeId);
                    break;
                case COMPANY:
                    hasPermission = gnSecurity.hasPermission(permissionId, getUserLogin()) && searchPartyNode(entityId, PartyRoleOfbiz.GN_COMPANY);
                    break;
                case COMPANY_BASE:
                    hasPermission = gnSecurity.hasPermission(permissionId, getUserLogin()) && searchPartyNode(entityId, PartyRoleOfbiz.GN_COMPANY_BASE);
                    break;
                case ORGANIZATION_NODE:
                    hasPermission = gnSecurity.hasPermission(permissionId, getUserLogin()) && searchPartyNode(entityId, PartyRoleOfbiz.GN_ORGANIZATION_NODE);
                    break;
                case PROPAGATION_NODE:
                    hasPermission = gnSecurity.hasPermission(permissionId, getUserLogin()) && searchPartyNode(entityId, PartyRoleOfbiz.GN_PROPAGATION_NODE);
                    break;
                case CONTEXT:
                    hasPermission = gnSecurity.hasPermission(permissionId, getUserLogin()) && searchPartyGroup(entityId, PartyRoleOfbiz.GN_CONTEXT);
                    break;
                case CONTACT_LIST:
                    hasPermission = gnSecurity.hasPermission(permissionId, getUserLogin()) && searchContactList(entityId);
                    break;
                case AUTH_REL_FILTER:
                    hasPermission = gnSecurity.hasPermission(permissionId, getUserLogin()) && searchAuthorizationFilter(entityId);
                    break;
                case AUTH_FILTER_CONSTRAINT:
                    hasPermission = gnSecurity.hasPermission(permissionId, getUserLogin()) && searchAuthorizationFilterTerm(entityId);
                    break;
                case REL_SLICE:
                    hasPermission = gnSecurity.hasPermission(permissionId, getUserLogin()) && searchRelSlice(entityId);
                    break;
                case REL_SLICE_CONSTRAINT:
                    hasPermission = gnSecurity.hasPermission(permissionId, getUserLogin()) && searchRelSliceTerm(entityId);
                    break;
                default:
                    hasPermission = false;
                    break;
            }


        }

        if (!hasPermission)
            throw new GnServiceException(OfbizErrors.ACCESS_DENIED, "You do not have permission[" + hasPermission + "] on entity[" + entityTypeId + "]");

        return partyNodeKey;
    }

    private boolean searchAuthorization(String authorizationKey, String ownerNodeId) throws GenericEntityException, GnServiceException {
        List<EntityCondition> conds = new FastList<EntityCondition>();
        conds.add(EntityCondition.makeCondition("authorizationKey", authorizationKey));
        conds.add(EntityCondition.makeCondition("ownerNodeId", ownerNodeId));
        conds.add(GnFindUtil.makeIsEmptyCondition("internalStatusId"));
        conds.add(GnFindUtil.makeOrConditionById("statusId", UtilMisc.toList(AuthorizationStatusOfbiz.GN_AUTH_PUBLISHED.name(), AuthorizationStatusOfbiz.GN_AUTH_NOT_VALID.name())));
        EntityCondition cond = EntityCondition.makeCondition(conds);
        List<GenericValue> result = delegator.findList("GnAuthorizationAndAgreement", cond, UtilMisc.toSet("agreementId", "authorizationKey"), null, null, false);
        if (result.size() == 1) return true;
        if (result.size() > 1)
            throw new GnServiceException(OfbizErrors.ENTITY_EXCEPTION, "More than one authorizations[" + authorizationKey + "] found");
        return false; // if (result.size() == 0) return false;
    }

    private boolean searchPartyNode(String nodeKey, PartyRoleOfbiz role) throws GenericEntityException, GenericServiceException {
        List<GenericValue> pg = delegator.findByAnd("GnPartyGroupPartyNode", "nodeKey", nodeKey);
        if (pg.size() > 0) {
            GenericValue party = pg.get(0);
            return gnPartyRoleCheck(party.getString("partyId"), role.name());
        } else return false;
    }

    private boolean searchPartyGroup(String partyId, PartyRoleOfbiz gnContext) throws GenericEntityException, GenericServiceException {
        return gnPartyRoleCheck(partyId, gnContext.name());
    }

    private boolean searchContactList(String contactListId) throws GenericEntityException, GenericServiceException {
        Map<String, Object> pg = delegator.findOne("ContactList", false, "contactListId", contactListId);
        return !UtilValidate.isEmpty(pg);
    }

    private boolean searchAuthorizationFilter(String filterKey) throws GenericEntityException, GenericServiceException {
        List<GenericValue> pg = delegator.findByAnd("GnAuthorizationFilterAndAgreement", UtilMisc.toMap("filterKey", filterKey, "agreementTypeId", AgreementTypesOfbiz.GN_AUTH_FILTER.name()));
        if (pg.size() > 0) {
            return true;
        } else return false;
    }

    private boolean searchRelSlice(String filterKey) throws GenericEntityException, GenericServiceException {
        List<GenericValue> pg = delegator.findByAnd("GnAuthorizationFilterAndAgreement", UtilMisc.toMap("filterKey", filterKey, "agreementTypeId", AgreementTypesOfbiz.GN_AUTH_SLI_FILTER.name()));
        if (pg.size() > 0) {
            return true;
        } else return false;
    }

    private boolean searchAuthorizationFilterTerm(String authorizationTermKey) throws GenericEntityException, GenericServiceException {
        List<GenericValue> pg = delegator.findByAnd("GnAuthorizationAndAgreementTerm", UtilMisc.toMap("authorizationTermKey", authorizationTermKey));
        if (pg.size() > 0) {
            return true;
        } else return false;
    }

    private boolean searchRelSliceTerm(String authorizationTermKey) throws GenericEntityException, GenericServiceException {
        List<GenericValue> pg = delegator.findByAnd("GnAuthorizationAndAgreementTerm", UtilMisc.toMap("authorizationTermKey", authorizationTermKey));
        if (pg.size() > 0) {
            return true;
        } else return false;
    }


    /**
     * Check that the  party's has role.
     *
     * @param partyId
     * @param roleTypeId
     * @return
     * @throws GenericServiceException
     */
    private boolean gnPartyRoleCheck(String partyId, String roleTypeId) throws GenericServiceException {
        Map<String, Object> ret = dispatcher.runSync("gnPartyRoleCheck", UtilMisc.toMap("userLogin", userLogin, "partyId", partyId, "roleTypeId", roleTypeId));
        return (Boolean) ret.get("hasRole");
    }


}

