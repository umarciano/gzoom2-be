package it.memelabs.gn.services.authorization;

import it.memelabs.gn.services.OfbizErrors;
import it.memelabs.gn.services.node.PartyNodeTypeOfbiz;
import it.memelabs.gn.services.node.PartyRoleOfbiz;
import it.memelabs.gn.util.GnServiceException;
import it.memelabs.gn.util.PartyNodeUtil;
import javolution.util.FastList;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilGenerics;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.service.DispatchContext;
import org.ofbiz.service.GenericServiceException;

import java.util.Map;

/**
 * 28/03/13
 *
 * @author Andrea Fossi
 */
public class AuthorizationPartyNodeHelper extends CommonAuthorizationHelper {
    public AuthorizationPartyNodeHelper(DispatchContext dctx, Map<String, ? extends Object> context) {
        super(dctx, context);

    }

    public final static String module = AuthorizationPartyNodeHelper.class.getName();

    /**
     * If one of fields partyId or nodeKey is valued end other doesn't, method find correct and set it.
     * If are empty both, method does noting
     *
     * @param partyNode
     * @param nullAllowed id partyId is empty method return null instead an exception
     * @return partyId of incoming party (if avalilable)
     * @throws org.ofbiz.service.GenericServiceException
     */
    protected String fixIncomingPartyId(Map<String, Object> partyNode, boolean nullAllowed) throws GenericServiceException {
        return fixIncomingPartyId(partyNode, nullAllowed, OfbizErrors.INVALID_PARAMETERS);
    }

    /**
     * If one of fields partyId or nodeKey is valued end other doesn't, method find correct and set it.
     * If are empty both, method does noting
     *
     * @param partyNode
     * @param nullAllowed id partyId is empty method return null instead an exception
     * @param code        The error code to use
     * @return partyId of incoming party (if avalilable)
     * @throws org.ofbiz.service.GenericServiceException
     */
    protected String fixIncomingPartyId(Map<String, Object> partyNode, boolean nullAllowed, OfbizErrors code) throws GenericServiceException {
        String nodeKey = (String) partyNode.get("nodeKey");
        String partyId = (String) partyNode.get("partyId");
        if (nullAllowed)
            partyId = getPartyId(partyId, nodeKey);
        else
            partyId = getPartyId(partyId, nodeKey, code, "PartyIdTo and NodeKeyTo cannot be empty both.");
        nodeKey = getNodeKey(partyId, nodeKey);
        partyNode.put("partyId", partyId);
        partyNode.put("nodeKey", nodeKey);
        return partyId;
    }

    /**
     * If authorization contain not empty field originPartyIdTo method replace companyBase partyId with origin companyBase.
     * Method also replace partyId of owner company.
     *
     * @param context
     * @param auth
     * @throws org.ofbiz.service.GenericServiceException
     */
    protected void replacePartyIdTo(Map<String, ? extends Object> context, Map<String, Object> auth) throws GenericServiceException {
        String originPartyIdTo = (String) auth.get("originPartyIdTo");

        if ("N".equals(auth.get("isPrivate")) && UtilValidate.isNotEmpty(originPartyIdTo)) {
            Map<String, Object> ret = dispatcher.runSync("gnFindOwnerCompanyByCompanyBaseId", UtilMisc.toMap("userLogin", context.get("userLogin"), "companyBasePartyId", originPartyIdTo));

            Debug.log(String.format("Replace partyIdTo[%s] with originPartyIdTo[%s]", auth.get("partyIdTo"), originPartyIdTo), module);
            Map<String, Object> companyBase = UtilGenerics.checkMap(auth.get("partyNodeTo"));
            companyBase.put("partyId", originPartyIdTo);
            companyBase.put("nodeKey", ret.get("companyBaseNodeKey"));
            Map<String, Object> company = UtilGenerics.checkMap(auth.get("otherPartyNodeTo"));
            Debug.log(String.format("Replace company partyIdTo[%s] with originPartyIdTo[%s]", company.get("partyId"), ret.get("companyPartyId")), module);
            company.put("partyId", ret.get("companyPartyId"));
            company.put("nodeKey", ret.get("companyNodeKey"));
        }
    }

    /**
     * Create or update received authorization private holder companyBase and company without relationship.
     *
     * @param ownerNodeKey
     * @param pvtCompanyBase
     * @param pvtCompany
     * @return map with companyBasePartyId and companyPartyId
     * @throws GenericServiceException
     * @throws GenericEntityException
     */
    protected Map<String, String> createUpdateReceivedCompanyAndCompanyBase(String ownerNodeKey, Map<String, Object> pvtCompanyBase, Map<String, Object> pvtCompany) throws GenericServiceException, GenericEntityException {
        if (UtilValidate.isNotEmpty(pvtCompany.get("partyId")) || UtilValidate.isNotEmpty(pvtCompany.get("nodeKey"))) {
            String partyId = PartyNodeUtil.getPartyId(dctx, UtilGenerics.checkMap(context, String.class, Object.class), (String) pvtCompany.get("partyId"), (String) pvtCompany.get("nodeKey"));
            if (!gnPartyRoleCheck(partyId, PartyRoleOfbiz.GN_PVT_COMPANY.name()))
                throw new GnServiceException(OfbizErrors.ENTITY_EXCEPTION, String.format(
                        "Holder can be only a %s or %s", PartyRoleOfbiz.GN_COMPANY.name(), PartyRoleOfbiz.GN_PVT_COMPANY.name()));
        }
        if (UtilValidate.isNotEmpty(pvtCompanyBase.get("partyId")) || UtilValidate.isNotEmpty(pvtCompanyBase.get("nodeKey"))) {
            String partyId = PartyNodeUtil.getPartyId(dctx, UtilGenerics.checkMap(context, String.class, Object.class), (String) pvtCompanyBase.get("partyId"), (String) pvtCompanyBase.get("nodeKey"));
            if (!gnPartyRoleCheck(partyId, PartyRoleOfbiz.GN_PVT_COMPANY_BASE.name()))
                throw new GnServiceException(OfbizErrors.ENTITY_EXCEPTION, String.format(
                        "Holder Base can be only a %s or %s", PartyRoleOfbiz.GN_COMPANY_BASE.name(), PartyRoleOfbiz.GN_PVT_COMPANY_BASE.name()));
        }

        //create/update owner company
        pvtCompany.put("partyRelationships", FastList.newInstance());
        //pvtCompany.put("sourceNodeKey", pvtCompany.get("nodeKey"));
        pvtCompany.put("nodeType", PartyNodeTypeOfbiz.PRIVATE_COMPANY.name());
        pvtCompany.put("ownerNodeKey", ownerNodeKey);
        String pvtCompanyId = (String) createUpdatePartyNode(pvtCompany).get("partyId");

        //create/update companyBase
        pvtCompanyBase.put("partyRelationships", FastList.newInstance());
        pvtCompanyBase.put("nodeType", PartyNodeTypeOfbiz.PRIVATE_COMPANY_BASE.name());
        //pvtCompanyBase.put("sourceNodeKey", pvtCompany.get("nodeKey"));
        pvtCompanyBase.put("ownerNodeKey", ownerNodeKey);
        String pvtCompanyBaseId = (String) createUpdatePartyNode(pvtCompanyBase).get("partyId");
        return UtilMisc.toMap("companyPartyId", pvtCompanyId, "companyBasePartyId", pvtCompanyBaseId);
    }

    /**
     * Create a Party with given field; if another party with the same combination of key (nodeKey, sourceNodeKey and ownerNodeKey)
     * exist, method update it.
     *
     * @param partyNode Map
     * @return partyId
     * @throws GenericServiceException
     * @throws GenericEntityException
     */
    private Map<String, Object> createUpdatePartyNode(Map<String, Object> partyNode) throws GenericServiceException, GenericEntityException {
/*        List<GenericValue> result = delegator.findByAnd("GnPartyNode", UtilMisc.toMap("sourceNodeKey", partyNode.get("sourceNodeKey"),
                "nodeType", partyNode.get("nodeType"),
                "ownerNodeKey", partyNode.get("ownerNodeKey")
        ));
        if (result.size() > 1) throw new GnServiceException("Error to many party found");
 */
        if (UtilValidate.isEmpty(partyNode.get("partyId"))) {
            Map<String, Object> srvRequest = dispatcher.getDispatchContext().makeValidContext("gnInternalCreatePartyNode", "IN", partyNode);
            srvRequest.put("userLogin", userLogin);
            Map<String, Object> partyNodeToResponse = dispatcher.runSync("gnInternalCreatePartyNode", srvRequest);
            return partyNodeToResponse;
        } else {
            Map<String, Object> srvRequest = dispatcher.getDispatchContext().makeValidContext("gnInternalUpdatePartyNode", "IN", partyNode);
            srvRequest.put("userLogin", userLogin);
            Map<String, Object> partyNodeToResponse = dispatcher.runSync("gnInternalUpdatePartyNode", srvRequest);
            return partyNodeToResponse;
        }
    }


}