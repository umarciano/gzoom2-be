package it.memelabs.gn.services.authorization;

import it.memelabs.gn.services.OfbizErrors;
import it.memelabs.gn.services.communication.EventTypeOfbiz;
import it.memelabs.gn.util.EnumLookupUtil;
import it.memelabs.gn.util.GnServiceException;
import it.memelabs.gn.util.GnServiceUtil;
import javolution.util.FastList;
import org.ofbiz.base.util.*;
import org.ofbiz.service.DispatchContext;
import org.ofbiz.service.ServiceUtil;

import java.sql.Timestamp;
import java.util.*;

/**
 * 04/12/12
 *
 * @author Andrea Fossi
 */
public class AuthorizationServices {
    private static final String module = AuthorizationServices.class.getName();
    private static final String resourceError = "AuthorizationErrorUiLabels";

    /**
     * Retrieves a list of companies identified by the input taxIdentificationNumber, candidated for
     * holder company in a draft authorization.
     *
     * @param ctx
     * @param context (taxIdentificationNumber)
     * @return partyNodes
     */
    public static Map<String, Object> gnPrivateFindCompaniesAuthorization(DispatchContext ctx, Map<String, Object> context) {
        try {
            @SuppressWarnings("unchecked")
            Map<String, Object> userLogin = (Map<String, Object>) context.get("userLogin");

            AuthorizationHelper authorizationHelper = new AuthorizationHelper(ctx, context);

            String taxIdentificationNumber = (String) context.get("taxIdentificationNumber");

            List<Map<String, Object>> partyNodeList = authorizationHelper.gnPrivateFindCompaniesAuthorization(taxIdentificationNumber);

            Map<String, Object> result = ServiceUtil.returnSuccess();
            result.put("partyNodes", partyNodeList);
            return result;
        } catch (Throwable e) {
            Debug.logError(e, module);
            return GnServiceUtil.returnError(e);
        }
    }

    /**
     * Retrieves a list of company bases identified by the input taxIdentificationNumber, candidated for
     * holder company base in a draft authorization.
     *
     * @param ctx
     * @param context (taxIdentificationNumber)
     * @return partyNodes
     */
    public static Map<String, Object> gnPrivateFindCompanyBasesAuthorization(DispatchContext ctx, Map<String, Object> context) {
        try {
            @SuppressWarnings("unchecked")
            Map<String, Object> userLogin = (Map<String, Object>) context.get("userLogin");

            AuthorizationHelper authorizationHelper = new AuthorizationHelper(ctx, context);

            String taxIdentificationNumber = (String) context.get("taxIdentificationNumber");

            List<Map<String, Object>> partyNodeList = authorizationHelper.gnPrivateFindCompanyBasesAuthorization(taxIdentificationNumber);

            Map<String, Object> result = ServiceUtil.returnSuccess();
            result.put("partyNodes", partyNodeList);
            return result;
        } catch (Throwable e) {
            Debug.logError(e, module);
            return GnServiceUtil.returnError(e);
        }
    }

    /**
     * Proxy method to create/update authorization
     *
     * @param ctx
     * @param context
     * @return
     */
    public static Map<String, Object> gnCreateUpdateAuthorization(DispatchContext ctx, Map<String, Object> context) {
        Map<String, Object> result = ServiceUtil.returnSuccess();
        try {
            AuthorizationHelper authorizationHelper = new AuthorizationHelper(ctx, context);

            Map<String, Object> userLogin = UtilGenerics.checkMap(context.get("userLogin"));
            authorizationHelper.authorizationAndProfileMatchingCheck(userLogin, context, null);

            AuthorizationPartyNodeHelper authorizationPartyNodeHelper = new AuthorizationPartyNodeHelper(ctx, context);
            String nodeKeyFrom = (String) context.get("nodeKeyFrom");
            String partyIdFrom = (String) context.get("partyIdFrom");
            partyIdFrom = authorizationPartyNodeHelper.getPartyId(partyIdFrom, nodeKeyFrom, OfbizErrors.ISSUER_NOT_FOUND, "PartyIdFrom and NodeKeyFrom cannot be empty both.");
            context.put("partyIdFrom", partyIdFrom);

            Map<String, Object> partyNodeTo = UtilGenerics.checkMap(context.get("partyNodeTo"));
            String partyIdTo = authorizationPartyNodeHelper.fixIncomingPartyId(partyNodeTo, "Y".equals(context.get("isPrivate")), OfbizErrors.HOLDER_BASE_NOT_FOUND);

            Map<String, Object> otherPartyNodeTo = UtilGenerics.checkMap(context.get("otherPartyNodeTo"));
            String taxIdentificationNumber = null;
            if (UtilValidate.isNotEmpty(otherPartyNodeTo)) {
                String otherPartyIdTo = authorizationPartyNodeHelper.fixIncomingPartyId(otherPartyNodeTo, true, OfbizErrors.HOLDER_BASE_NOT_FOUND);
                taxIdentificationNumber = (String) otherPartyNodeTo.get("taxIdentificationNumber");
            }

            String agreementId = (String) context.get("agreementId");
            String authorizationKey = (String) context.get("authorizationKey");
            String number = (String) context.get("number");
            String typeId = (String) context.get("typeId");

            List<String> statusToExclude = FastList.newInstance();
            if (!UtilValidate.isEmpty(authorizationKey)) {
                statusToExclude.add(AuthorizationStatusOfbiz.GN_AUTH_CONFLICTING.toString());
            }

            String contextId = (String) userLogin.get("activeContextId");
            Map<String, Object> gnContext = ctx.getDispatcher().runSync("gnFindContextById", UtilMisc.toMap("userLogin", userLogin, "partyId", contextId));
            Map<String, Object> contextNode = UtilGenerics.checkMap(gnContext.get("partyNode"));
            String ownerNodeId = (String) contextNode.get("partyId");
            if (!authorizationHelper.existsAuthorizationByLogicalKey(authorizationKey, partyIdFrom, partyIdTo, number,
                    taxIdentificationNumber, (String) otherPartyNodeTo.get("description"), partyNodeTo, AuthorizationTypesOfbiz.valueOf(typeId), ownerNodeId, statusToExclude)) {
                if (UtilValidate.isEmpty(agreementId) && UtilValidate.isEmpty(authorizationKey))
                    result = ctx.getDispatcher().runSync("gnCreateAuthorization", ctx.makeValidContext("gnCreateAuthorization", "IN", context));
                else
                    result = ctx.getDispatcher().runSync("gnUpdateAuthorization", ctx.makeValidContext("gnUpdateAuthorization", "IN", context));
            }
            return result;
        } catch (Throwable e) {
            Debug.logError(e, module);
            return GnServiceUtil.returnError(e);
        }
    }

    /**
     * Creates an Authorization.
     *
     * @param ctx     The DispatchContext that this service is operating in.
     * @param context Map containing the input parameters.
     * @return Map with the result of the service, the output parameters.
     */
    public static Map<String, Object> gnCreateAuthorization(DispatchContext ctx, Map<String, Object> context) {
        try {
            Map<String, Object> result = ServiceUtil.returnSuccess();
            Map<String, Object> partyNodeTo = UtilGenerics.checkMap(context.get("partyNodeTo"));
            Map<String, Object> otherPartyNodeTo = UtilGenerics.checkMap(context.get("otherPartyNodeTo"));
            String partyIdFrom = (String) context.get("partyIdFrom");
            String typeId = (String) context.get("typeId");
            String number = (String) context.get("number");
            String description = (String) context.get("description");
            String textData = (String) context.get("textData");
            String originId = (String) (UtilGenerics.checkMap(context.get("userLogin"))).get("originId");
            String isPrivate = (String) context.get("isPrivate");
            if (!(isPrivate.length() == 1 && isPrivate.matches("[YN]")))
                throw new GnServiceException(OfbizErrors.INVALID_PARAMETERS, "isPrivate flag can have only of of follow value: 'Y', 'N'");


            //String authorizationKey = (String) context.get("authorizationKey");
            result.putAll(new AuthorizationHelper(ctx, context).gnCreateAuthorization(partyIdFrom, partyNodeTo, otherPartyNodeTo, typeId, number, textData, description, originId, isPrivate));
            return result;
        } catch (GeneralException e) {
            Debug.logError(e, module);
            return GnServiceUtil.returnError(e);
        }
    }

    /**
     * Updates an Authorization.
     *
     * @param ctx
     * @param context
     * @return
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> gnUpdateAuthorization(DispatchContext ctx, Map<String, ? extends Object> context) {
        try {
            Map<String, Object> result = ServiceUtil.returnSuccess();
            Map<String, Object> partyNodeTo = (Map<String, Object>) context.get("partyNodeTo");
            Map<String, Object> otherPartyNodeTo = (Map<String, Object>) context.get("otherPartyNodeTo");
            String partyIdFrom = (String) context.get("partyIdFrom");
            String agreementId = (String) context.get("agreementId");
            String number = (String) context.get("number");
            String description = (String) context.get("description");
            String textData = (String) context.get("textData");
            String authorizationKey = (String) context.get("authorizationKey");
            String typeId = (String) context.get("typeId");
            String originId = (String) ((Map<String, Object>) context.get("userLogin")).get("originId");
            String isPrivate = (String) context.get("isPrivate");

            if (!(isPrivate.length() == 1 && isPrivate.matches("[YN]")))
                throw new GnServiceException(OfbizErrors.INVALID_PARAMETERS, "isPrivate flag can have only of of follow value: 'Y', 'N'");

            result.putAll(new AuthorizationHelper(ctx, context).gnUpdateAuthorization(agreementId, authorizationKey,
                    partyIdFrom, partyNodeTo, otherPartyNodeTo, number, textData, description, typeId, originId, isPrivate));
            return result;
        } catch (GeneralException e) {
            Debug.logError(e, module);
            return GnServiceUtil.returnError(e);
        }
    }

    /**
     * Find a {@code GnAuthorization} by primaryKey
     *
     * @param ctx
     * @param context
     * @return
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> gnFindAuthorizationById(DispatchContext ctx, Map<String, ? extends Object> context) {
        try {
            Map<String, Object> result = ServiceUtil.returnSuccess();
            String agreementId = (String) context.get("agreementId");
            String authorizationKey = (String) context.get("authorizationKey");
            boolean loadNodes = "Y".equals(context.get("loadNodes"));
            boolean loadDetail = "Y".equals(context.get("loadDetails"));
            AuthorizationHelper authorizationHelper = new AuthorizationHelper(ctx, context);
            AuthorizationPartyNodeHelper authorizationPartyNodeHelper = new AuthorizationPartyNodeHelper(ctx, context);
            Map<String, Object> auth = authorizationHelper.gnFindAuthorizationById(agreementId, authorizationKey, loadNodes, loadDetail);

            for (String key : AuthorizationHelper.keyFields) {
                auth.remove(key);
            }

            authorizationPartyNodeHelper.replacePartyIdTo(context, auth);

            result.put("authorization", auth);
            return result;
        } catch (GeneralException e) {
            Debug.logError(e, module);
            return GnServiceUtil.returnError(e);
        }
    }

    /**
     * Find a {@code GnAuthorization} by primaryKey
     *
     * @param ctx
     * @param context
     * @return
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> gnFindAuthorizationByUuid(DispatchContext ctx, Map<String, ? extends Object> context) {
        try {
            Map<String, Object> result = ServiceUtil.returnSuccess();
            String uuid = (String) context.get("uuid");
            boolean loadNodes = "Y".equals(context.get("loadNodes"));
            boolean loadDetail = "Y".equals(context.get("loadDetails"));
            AuthorizationHelper authorizationHelper = new AuthorizationHelper(ctx, context);
            AuthorizationPartyNodeHelper authorizationPartyNodeHelper = new AuthorizationPartyNodeHelper(ctx, context);
            Map<String, Object> auth = authorizationHelper.gnFindAuthorizationByUuid(uuid, loadNodes, loadDetail);

            for (String key : AuthorizationHelper.keyFields) {
                auth.remove(key);
            }

            authorizationPartyNodeHelper.replacePartyIdTo(context, auth);

            result.put("authorization", auth);
            return result;
        } catch (GeneralException e) {
            Debug.logError(e, module);
            return GnServiceUtil.returnError(e);
        }
    }

    /**
     * Find a {@code GnAuthorization} by primaryKey
     *
     * @param ctx
     * @param context
     * @return
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> gnGetTaxIdeNumberPartyIdToByUuid(DispatchContext ctx, Map<String, ? extends Object> context) {
        try {
            Map<String, Object> result = ServiceUtil.returnSuccess();
            String uuid = (String) context.get("uuid");
            AuthorizationHelper authorizationHelper = new AuthorizationHelper(ctx, context);
            Map<String, Object> r = authorizationHelper.gnGetTaxIdeNumberPartyIdToByUuid(uuid);
            result.putAll(r);
            return result;
        } catch (GeneralException e) {
            Debug.logError(e, module);
            return GnServiceUtil.returnError(e);
        }
    }

    private static Map<String, Object> searchAuthorization(DispatchContext ctx, Map<String, ? extends Object> context, boolean root, boolean operationalDetail) {
        try {
            Map<String, Object> result = ServiceUtil.returnSuccess();

            List<String> agreementIds = UtilGenerics.toList(context.get("agreementIds"));
            List<String> authorizationKeys = UtilGenerics.toList(context.get("authorizationKeys"));
            List<String> uuids = UtilGenerics.toList(context.get("uuids"));

            boolean onlyMine = "Y".equals(context.get("onlyMine"));
            String onlyPrivate = (String) context.get("onlyPrivate");

            String statusId = (String) context.get("statusId");
            String internalStatusId = (String) context.get("internalStatusId");
            List<String> numbers = UtilGenerics.toList(context.get("numbers"));

            // holder company (=otherPartyNodeTo) e holder company base (=partyNodeTo) --> anagrafiche solidali con autorizzazione --> filtro non utile
            // holder company base ORIGIN (=originPartyNodeTo) --> sede di origine da cui deriva quella sull'autorizzazione (TODO: null su private al momento, GREEN-1105)
            Map<String, Object> originPartyNodeTo = UtilGenerics.toMap(context.get("originPartyNodeTo"));
            String otherPartyNodeToDesc = (String) context.get("otherPartyNodeToDesc"); // = holder company name
            String otherPartyNodeToTaxIdentificationNumber = (String) context.get("otherPartyNodeToTaxIdentificationNumber"); // = holder tax identification number
            Map<String, Object> partyIdToGeo = UtilGenerics.toMap(context.get("partyIdToGeo")); // = holder company base location

            String wasteLogicS = (String) context.get("wasteLogic");
            CriteriaLogicOfbiz wasteLogic = CriteriaLogicOfbiz.valueOf(wasteLogicS);
            if(wasteLogic==null) wasteLogic = CriteriaLogicOfbiz.OR;

            List<String> operationIds = UtilGenerics.toList(context.get("operationIds"));
            List<String> wasteCerCodeIds = UtilGenerics.toList(context.get("wasteCerCodeIds"));
            List<String> packagingIds = UtilGenerics.toList(context.get("packagingIds"));
            List<String> registrationNumberIds = UtilGenerics.toList(context.get("registrationNumberIds"));
            List<String> categoryClassEnumIds = UtilGenerics.toList(context.get("categoryClassEnumIds"));
            List<String> holderRoleIds = UtilGenerics.toList(context.get("holderRoleIds"));
            List<String> partyNodeToKeys = UtilGenerics.toList(context.get("partyNodeToKeys"));
            List<String> typeIds = UtilGenerics.toList(context.get("typeIds"));
            List<String> issuerBaseKeys = UtilGenerics.toList(context.get("issuerBaseKeys"));

            Timestamp minimumPublishedDate = (Timestamp) context.get("minimumPublishedDate");
            Timestamp validityDate = (Timestamp) context.get("validityDate");

            List<Map<String, String>> orderByList = UtilGenerics.toList(context.get("orderBy"));

            if (!(onlyPrivate == null || (onlyPrivate.length() == 1 && onlyPrivate.matches("[YN]"))))
                throw new GnServiceException(OfbizErrors.INVALID_PARAMETERS, "onlyPrivate flag can have only of of follow value: 'Y', 'N', null");

            Map<String, Object> searchResult;
            if (root) {
                searchResult = new AuthorizationSearchHelper(ctx, context).searchAtRoot(agreementIds, uuids, authorizationKeys,
                        onlyMine, onlyPrivate, statusId, internalStatusId, otherPartyNodeToDesc, otherPartyNodeToTaxIdentificationNumber,
                        originPartyNodeTo, partyIdToGeo, numbers, operationIds, wasteLogic,
                        wasteCerCodeIds, packagingIds, registrationNumberIds, categoryClassEnumIds, holderRoleIds, partyNodeToKeys, typeIds,
                        issuerBaseKeys, minimumPublishedDate, validityDate, orderByList, operationalDetail);
            } else {
                searchResult = new AuthorizationSearchHelper(ctx, context).search(agreementIds, uuids, authorizationKeys,
                        onlyMine, onlyPrivate, statusId, internalStatusId, otherPartyNodeToDesc, otherPartyNodeToTaxIdentificationNumber,
                        originPartyNodeTo, partyIdToGeo, numbers, operationIds, wasteLogic,
                        wasteCerCodeIds, packagingIds, registrationNumberIds, categoryClassEnumIds, holderRoleIds, partyNodeToKeys, typeIds,
                        issuerBaseKeys, minimumPublishedDate, validityDate, orderByList, operationalDetail);
            }
            List<Map<String, Object>> auths = UtilMisc.getListFromMap(searchResult, "list");
            AuthorizationPartyNodeHelper authorizationPartyNodeHelper = new AuthorizationPartyNodeHelper(ctx, context);
            if (UtilValidate.isNotEmpty(auths)) for (Map<String, Object> auth : auths) {
                authorizationPartyNodeHelper.replacePartyIdTo(context, auth);
            }
            result.putAll(searchResult);

            return result;
        } catch (Throwable e) {
            Debug.logError(e, module);
            return GnServiceUtil.returnError(e);
        }
    }

    public static Map<String, Object> gnSearchAuthorization(DispatchContext ctx, Map<String, ? extends Object> context) {
        boolean loadOperationalDetail = "Y".equals(context.get("loadOperationalDetail"));
        return searchAuthorization(ctx, context, false, loadOperationalDetail);
    }

    public static Map<String, Object> gnSearchAuthorizationAtRoot(DispatchContext ctx, Map<String, ? extends Object> context) {
        return searchAuthorization(ctx, context, true, false);
    }

    public static Map<String, Object> gnInternalSearchAuthorizationAtRoot(DispatchContext ctx, Map<String, ? extends Object> context) {
        try {
            Map<String, Object> result = ServiceUtil.returnSuccess();
            String statusId = (String) context.get("statusId");
            List<String> taxIdentificationNumbers = UtilGenerics.toList(context.get("taxIdentificationNumbers"));

            Map<String, Object> searchResult = new AuthorizationSearchHelper(ctx, context).internalSearchAtRoot(
                    statusId, taxIdentificationNumbers);

            List<Map<String, Object>> auths = UtilMisc.getListFromMap(searchResult, "list");
            AuthorizationPartyNodeHelper authorizationPartyNodeHelper = new AuthorizationPartyNodeHelper(ctx, context);
            if (UtilValidate.isNotEmpty(auths)) for (Map<String, Object> auth : auths) {
                authorizationPartyNodeHelper.replacePartyIdTo(context, auth);
            }
            result.putAll(searchResult);
            return result;
        } catch (Throwable e) {
            Debug.logError(e, module);
            return GnServiceUtil.returnError(e);
        }
    }

    public static Map<String, Object> gnSearchNewestAuthorization(DispatchContext ctx, Map<String, ? extends Object> context) {
        try {
            Map<String, Object> result = ServiceUtil.returnSuccess();


            List<Map<String, Object>> auths = new AuthorizationSearchHelper(ctx, context).gnSearchNewest();
            result.put("list", auths);
            result.put("listSize", auths.size());

            AuthorizationPartyNodeHelper authorizationPartyNodeHelper = new AuthorizationPartyNodeHelper(ctx, context);
            if (UtilValidate.isNotEmpty(auths))
                for (Map<String, Object> auth : auths) {
                    authorizationPartyNodeHelper.replacePartyIdTo(context, auth);
                }
            return result;
        } catch (Throwable e) {
            Debug.logError(e, module);
            return GnServiceUtil.returnError(e);
        }
    }


    /**
     * Publish a {@code GnAuthorization}, event driven
     *
     * @param ctx
     * @param context
     * @return
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> gnPublishAuthorizationByIdEM(DispatchContext ctx, Map<String, ? extends Object> context) {
        try {
            Map<String, Object> userLogin = (Map<String, Object>) context.get("userLogin");
            String authorizationKey = (String) context.get("authorizationKey");

            Map<String, Object> ret = ctx.getDispatcher().runSync("gnFindAuthorizationById", UtilMisc.toMap("userLogin", userLogin, "authorizationKey", authorizationKey));
            Map<String, Object> authorization = (Map<String, Object>) ret.get("authorization");
            if (UtilValidate.isEmpty(authorization))
                throw new GnServiceException(OfbizErrors.AUTHORIZATION_NOT_FOUND, String.format("Authorization[%s] not found", authorizationKey));
            new AuthorizationHelper(ctx, context).authorizationAndProfileMatchingCheck(userLogin, authorization, null);

            Map<String, Object> result = ServiceUtil.returnSuccess();
            String agreementId = (String) context.get("agreementId");
            String originId = (String) userLogin.get("originId");

            result.putAll(new AuthorizationPublicationHelper(ctx, context).gnPublishAuthorizationByIdEM(agreementId, authorizationKey, originId));

            return result;
        } catch (Throwable e) {
            Debug.logError(e, module);
            return GnServiceUtil.returnError(e);
        }
    }

    /**
     * Rejects an Authorization. event driven
     *
     * @param ctx
     * @param context
     * @return
     */
    public static Map<String, Object> gnSetNotValidatedAuthorizationByIdEM(DispatchContext ctx, Map<String, ? extends Object> context) {
        AuthorizationPublicationHelper authorizationPublicationHelper = new AuthorizationPublicationHelper(ctx, context);
        try {
            Map<String, Object> result = ServiceUtil.returnSuccess();
            String agreementId = (String) context.get("agreementId");
            String authorizationKey = (String) context.get("authorizationKey");
            result.putAll(authorizationPublicationHelper.gnSetNotValidatedAuthorizationByIdEM(agreementId, authorizationKey));
            return result;
        } catch (Exception t) {
            Debug.logError(t, module);
            return GnServiceUtil.returnError(t);
        }
    }

    /**
     * Find a {@code GnAuthorization} by primaryKey
     *
     * @param ctx
     * @param context
     * @return
     */
    public static Map<String, Object> gnPropagateAuthorizationToEM(DispatchContext ctx, Map<String, Object> context) {
        try {
            Map<String, Object> result = ServiceUtil.returnSuccess();
            String authorizationKey = (String) context.get("authorizationKey");
            String nodeKey = (String) context.get("nodeKey");
            String partyNodeId = (String) context.get("partyId");
            if (UtilValidate.isEmpty(nodeKey) && UtilValidate.isEmpty(partyNodeId))
                return GnServiceUtil.returnError(OfbizErrors.INVALID_PARAMETERS, "NodeKey and partyId cannot be null both.");
            if (UtilValidate.isNotEmpty(nodeKey)) {
                partyNodeId = new AuthorizationPartyNodeHelper(ctx, context).getPartyId(partyNodeId, nodeKey);
            }
            AuthorizationPublicationHelper authorizationPublicationHelper = new AuthorizationPublicationHelper(ctx, context);
            Map<String, Object> auto = authorizationPublicationHelper.gnPropagateAuthorizationToEM(partyNodeId, nodeKey, authorizationKey, false);
            result.putAll(auto);

            return result;
        } catch (Throwable e) {
            Debug.logError(e, module);
            return GnServiceUtil.returnError(e);
        }
    }


    /**
     * Find a {@code GnAuthorization} by primaryKey
     *
     * @param ctx
     * @param context
     * @return
     */
    public static Map<String, Object> gnReceivedAuthorizationAckEM(DispatchContext ctx, Map<String, Object> context) {
        try {
            Map<String, Object> result = ServiceUtil.returnSuccess();
            String authorizationKey = (String) context.get("authorizationKey");
            String nodeKey = (String) context.get("nodeKey");
            String partyNodeId = (String) context.get("partyId");
            if (UtilValidate.isEmpty(nodeKey) && UtilValidate.isEmpty(partyNodeId))
                return GnServiceUtil.returnError(OfbizErrors.INVALID_PARAMETERS, "NodeKey and partyId cannot be null both.");
            if (UtilValidate.isNotEmpty(nodeKey)) {
                partyNodeId = new AuthorizationPartyNodeHelper(ctx, context).getPartyId(partyNodeId, nodeKey);
            }
            AuthorizationPublicationHelper authorizationPublicationHelper = new AuthorizationPublicationHelper(ctx, context);
            Map<String, Object> auto = authorizationPublicationHelper.gnReceivedAuthorizationAckEM(partyNodeId, nodeKey, authorizationKey);
            result.putAll(auto);

            return result;
        } catch (Throwable e) {
            Debug.logError(e, module);
            return GnServiceUtil.returnError(e);
        }
    }

    /**
     * Copy a draft {@code GnAuthorization} copy from a published
     *
     * @param ctx
     * @param context
     * @return
     */
    public static Map<String, Object> gnCopyAuthorization(DispatchContext ctx, Map<String, Object> context) {
        try {
            Map<String, Object> result = ServiceUtil.returnSuccess();

            String agreementId = (String) context.get("agreementId");
            String authorizationKey = (String) context.get("authorizationKey");

            //Debug.log("copy authorization[" + authorization.get("authorizationKey") + "] to node[" + partyNode.get("nodeKey") + "][" + partyId + "]", module);
            result.putAll(new AuthorizationPublicationHelper(ctx, context).gnCopyAuthorization(agreementId, authorizationKey));
            Map<String, Object> authorization = UtilGenerics.checkMap(result.get("authorization"));
            new AuthorizationPartyNodeHelper(ctx, context).replacePartyIdTo(context, authorization);

            return result;
        } catch (Throwable e) {
            Debug.logError(e, module);
            return GnServiceUtil.returnError(e);
        }
    }

    public static Map<String, Object> gnUploadAttachment(DispatchContext ctx, Map<String, Object> context) {
        try {
            Map<String, Object> result = ServiceUtil.returnSuccess();

            String authorizationKey = (String) context.get("authorizationKey");
            String agreementId = (String) context.get("agreementId");
            String contentName = (String) context.get("contentName");
            String description = (String) context.get("description");
            String mimeTypeId = (String) context.get("mimeTypeId");
            String agreementContentTypeId = (String) context.get("agreementContentTypeId");
            Long contentSize = (Long) context.get("contentSize");
            boolean overwrite = "Y".equals(context.get("overwrite"));

            AuthorizationHelper authorizationHelper = new AuthorizationHelper(ctx, context);
            agreementId = authorizationHelper.getAgreementId(authorizationKey, agreementId);
            String contentId = new AuthorizationAttachHelper(ctx, context).gnUploadAttachment(agreementId, contentName, description, mimeTypeId, contentSize, agreementContentTypeId, overwrite);
            result.put("contentId", contentId);
            Map<String, Object> savedAuthorization = authorizationHelper.findAuthorizationById(agreementId, authorizationKey, true, false);
            authorizationHelper.gnSendCommunicationEventToContactList((String) savedAuthorization.get("ownerNodeId"), EventTypeOfbiz.GN_COM_EV_AUTH_MOD.name(), savedAuthorization);
            return result;
        } catch (Throwable e) {
            Debug.logError(e, module);
            return GnServiceUtil.returnError(e);
        }
    }

    public static Map<String, Object> gnDownloadAttachment(DispatchContext ctx, Map<String, Object> context) {
        try {
            Map<String, Object> result = ServiceUtil.returnSuccess();

            String authorizationKey = (String) context.get("authorizationKey");
            String agreementId = (String) context.get("agreementId");
            String contentName = (String) context.get("contentName");

            AuthorizationHelper authorizationHelper = new AuthorizationHelper(ctx, context);
            agreementId = authorizationHelper.getAgreementId(authorizationKey, agreementId);
            Map<String, Object> attachMap = new AuthorizationAttachHelper(ctx, context).download(agreementId, contentName);
            result.putAll(attachMap);

            return result;
        } catch (Throwable e) {
            Debug.logError(e, module);
            return GnServiceUtil.returnError(e);
        }
    }

    public static Map<String, Object> gnDeleteAttachment(DispatchContext ctx, Map<String, Object> context) {
        try {
            Map<String, Object> result = ServiceUtil.returnSuccess();

            String authorizationKey = (String) context.get("authorizationKey");
            String agreementId = (String) context.get("agreementId");
            String contentName = (String) context.get("contentName");

            AuthorizationHelper authorizationHelper = new AuthorizationHelper(ctx, context);
            agreementId = authorizationHelper.getAgreementId(authorizationKey, agreementId);
            new AuthorizationAttachHelper(ctx, context).delete(agreementId, contentName);

            Map<String, Object> savedAuthorization = authorizationHelper.findAuthorizationById(agreementId, authorizationKey, true, false);
            authorizationHelper.gnSendCommunicationEventToContactList((String) savedAuthorization.get("ownerNodeId"), EventTypeOfbiz.GN_COM_EV_AUTH_MOD.name(), savedAuthorization);

            return result;
        } catch (Throwable e) {
            Debug.logError(e, module);
            return GnServiceUtil.returnError(e);
        }
    }

    /**
     * @param ctx
     * @param context
     * @return
     */
    public static Map<String, Object> gnAuthorizationGetPublicShare(DispatchContext ctx, Map<String, Object> context) {
        try {
            Map<String, Object> result = ServiceUtil.returnSuccess();
            AuthorizationHelper authorizationHelper = new AuthorizationHelper(ctx, context);
            String partyId = (String) context.get("partyId");
            Map<String, Object> sharerNode;
            if (UtilValidate.isNotEmpty(partyId)) {
                sharerNode = authorizationHelper.findPartyNodeById(partyId);
            } else {
                @SuppressWarnings("unchecked")
                Map<String, Object> userLogin = (Map<String, Object>) context.get("userLogin");
                sharerNode = authorizationHelper.gnFindCompanyWhereUserIsEmployed((String) userLogin.get("partyId"));
            }

            partyId = (String) sharerNode.get("partyId");
            result.put("sharerNode", sharerNode);
            String shareEnable = authorizationHelper.gnFindPartyAttributeById(partyId);
            if (UtilValidate.isEmpty(shareEnable))
                shareEnable = "N";
            result.put("shareEnable", shareEnable);

            //return authorizationToShareList
            //return authorizationToShareListSize
            //return sharerNode
            return result;
        } catch (Throwable e) {
            Debug.logError(e, module);
            return GnServiceUtil.returnError(e);
        }
    }

    /**
     * Find blamer authorization
     *
     * @param ctx
     * @param context
     * @return
     */
    public static Map<String, Object> gnFindBlamerAuthorization(DispatchContext ctx, Map<String, Object> context) {
        try {
            Map<String, Object> result = ServiceUtil.returnSuccess();
            String authorizationKey = (String) context.get("authorizationKey");
            String agreementId = (String) context.get("agreementId");
            String blamerAuthorizationKey = new AuthorizationPublicationHelper(ctx, context).gnFindBlamerAuthorization(authorizationKey, agreementId);
            result.put("authorizationKey", blamerAuthorizationKey);
            return result;
        } catch (Throwable e) {
            Debug.logError(e, module);
            return GnServiceUtil.returnError(e);
        }
    }

    public static Map<String, Object> gnResolveAuthorizationConflictEM(DispatchContext ctx, Map<String, Object> context) {
        try {
            Map<String, Object> result = ServiceUtil.returnSuccess();
            String authorizationKey = (String) context.get("authorizationKey");
            String agreementId = (String) context.get("agreementId");
            String _action = (String) context.get("action");
            ResolveAuthorizationConflictActionOfbiz action = EnumLookupUtil.lookup(ResolveAuthorizationConflictActionOfbiz.class, _action);
            result.putAll(new AuthorizationPublicationHelper(ctx, context).gnResolveAuthorizationConflictEM(authorizationKey, agreementId, action));
            return result;
        } catch (Exception e) {
            Debug.logError(e, module);
            return GnServiceUtil.returnError(e);
        }
    }

    /**
     * @param ctx
     * @param context
     * @return
     */
    public static Map<String, Object> gnAuthorizationManagePublicShare(DispatchContext ctx, Map<String, Object> context) {

        try {
            Map<String, Object> result = ServiceUtil.returnSuccess();
            AuthorizationHelper authorizationHelper = new AuthorizationHelper(ctx, context);
            String partyId = (String) context.get("partyId");
            String shareEnable = (String) context.get("shareEnable");
            Map<String, Object> sharerNode;
            if (UtilValidate.isNotEmpty(partyId)) {
                sharerNode = authorizationHelper.findPartyNodeById(partyId);
            } else {
                @SuppressWarnings("unchecked")
                Map<String, Object> userLogin = (Map<String, Object>) context.get("userLogin");
                sharerNode = authorizationHelper.gnFindCompanyWhereUserIsEmployed((String) userLogin.get("partyId"));
            }

            partyId = (String) sharerNode.get("partyId");
            authorizationHelper.gnCreateUpdatePartyAttribute(partyId, shareEnable);
            result.putAll(authorizationHelper.gnAuthorizationManagePublicShare(partyId));

            result.put("sharerNode", sharerNode);
            result.put("shareEnable", shareEnable);
            return result;
        } catch (Throwable e) {
            Debug.logError(e, module);
            return GnServiceUtil.returnError(e);
        }
    }

    /**
     * Delete an Authorization with status {@link it.memelabs.gn.services.authorization.AuthorizationStatusOfbiz#GN_AUTH_DRAFT}
     *
     * @param ctx
     * @param context authorizationItemKey
     * @return
     */
    public static Map<String, Object> gnDeleteAuthorization(DispatchContext ctx, Map<String, ? extends Object> context) {
        try {
            Map<String, Object> result = ServiceUtil.returnSuccess();

            String authorizationKey = (String) context.get("authorizationKey");
            String agreementId = (String) context.get("agreementId");
            new AuthorizationHelper(ctx, context).gnDeleteAuthorization(agreementId, authorizationKey);
            return result;
        } catch (GeneralException e) {
            Debug.logError(e, module);
            return GnServiceUtil.returnError(e);
        }
    }


    /**
     * Delete an Authorization
     *
     * @param ctx
     * @param context authorizationItemKey
     * @return
     */
    public static Map<String, Object> gnDeleteAuthorizationEM(DispatchContext ctx, Map<String, ? extends Object> context) {
        try {
            Map<String, Object> result = ServiceUtil.returnSuccess();

            String authorizationKey = (String) context.get("authorizationKey");
            String agreementId = (String) context.get("agreementId");
            new AuthorizationHelper(ctx, context).gnDeleteAuthorizationEM(agreementId, authorizationKey);
            return result;
        } catch (Exception e) {
            Debug.logError(e, module);
            return GnServiceUtil.returnError(e);
        }
    }

    public static Map<String, Object> gnPropagateDeleteAuthorizationToEM(DispatchContext ctx, Map<String, ? extends Object> context) {
        try {
            Map<String, Object> result = ServiceUtil.returnSuccess();
            String authorizationKey = (String) context.get("authorizationKey");
            String nodeKey = (String) context.get("nodeKey");
            String partyId = (String) context.get("partyId");
            new AuthorizationHelper(ctx, context).gnPropagateDeleteAuthorizationToEM(authorizationKey, nodeKey, partyId);
            return result;
        } catch (GeneralException e) {
            Debug.logError(e, module);
            return GnServiceUtil.returnError(e);
        }
    }

    public static Map<String, Object> gnAuthorizationMergeAckEM(DispatchContext ctx, Map<String, ? extends Object> context) {
        try {
            Map<String, Object> result = ServiceUtil.returnSuccess();
            String authorizationKey = (String) context.get("authorizationKey");
            String nodeKey = (String) context.get("nodeKey");
            String partyId = (String) context.get("partyId");
            new AuthorizationPublicationHelper(ctx, context).gnAuthorizationMergeAckEM(authorizationKey, nodeKey, partyId);
            return result;
        } catch (GeneralException e) {
            Debug.logError(e, module);
            return GnServiceUtil.returnError(e);
        }
    }

    public static Map<String, Object> gnHasToBeValidatedAuthorization(DispatchContext ctx, Map<String, ? extends Object> context) {
        try {
            Map<String, Object> result = ServiceUtil.returnSuccess();
            String nodeKey = (String) context.get("nodeKey");
            String partyId = (String) context.get("partyId");
            long count = new AuthorizationSearchHelper(ctx, context).getToBeValidatedAuthorizationCount(nodeKey, partyId);
            result.put("hasToBeValidatedAuthorization", (count > 0l) ? "Y" : "N");
            return result;
        } catch (GeneralException e) {
            Debug.logError(e, module);
            return GnServiceUtil.returnError(e);
        }
    }

    public static Map<String, Object> gnAuthorizationOperationalCheck(DispatchContext ctx, Map<String, ? extends Object> context) {
        try {
            Map<String, Object> result = ServiceUtil.returnSuccess();

            Map<String, Object> userLogin = UtilGenerics.toMap(context.get("userLogin"));
            Map<String, Object> profileResponse = ctx.getDispatcher().runSync("gnPrivateAuthCheckProfileCheck", UtilMisc.toMap("userLogin", userLogin));
            if (!"Y".equals(profileResponse.get("hasPermission"))) {
                throw new GnServiceException(OfbizErrors.ACCESS_DENIED, "Company hasn't permission related to operational check.");
            }

            List<Map<String, Object>> operationalCriteriaList = UtilGenerics.toList(context.get("operationalCriteriaList"));
            context.remove("operationalCriteriaList");
            int size = operationalCriteriaList.size();
            Map<String, Object> checkResultMap = new HashMap<String, Object>(size);
            Map<String, Object> checkParam;
            List<Map<String, Object>> checkParams = new ArrayList<Map<String, Object>>(size);
            List<String> names = new ArrayList<String>(size);
            for (Map<String, Object> operationalCriteria : operationalCriteriaList) {
                checkParam = new HashMap<String, Object>();
                checkParam.putAll(context);
                String name = (String) operationalCriteria.get("name");
                names.add(name);
                Map<String, Object> criteria = UtilGenerics.toMap(operationalCriteria.get("criteria"));
                criteria.put("userLogin", userLogin);

                Timestamp validityDate;
                Object validityDateObj = criteria.get("validityDate");
                if (validityDateObj != null) {
                    if (validityDateObj instanceof Timestamp)
                        validityDate = (Timestamp) validityDateObj;
                    else
                        validityDate = new Timestamp(((Date) validityDateObj).getTime());
                    criteria.put("validityDate", validityDate);
                }
                checkParam.putAll(criteria);
                checkParams.add(checkParam);
                Map<String, Object> checkResult = searchAuthorization(ctx, checkParam, false, true);
                checkResultMap.put(name, checkResult);
            }
            result.put("checkResultMap", checkResultMap);

            new AuthorizationHelper(ctx, context).gnAuthorizationOperationalCheckEvents(names, checkParams, checkResultMap);

            return result;
        } catch (Exception e) {
            Debug.logError(e, module);
            return GnServiceUtil.returnError(e);
        }
    }

    public static Map<String, Object> gnCompleteCompanyInfo(DispatchContext ctx, Map<String, ? extends Object> context) {
        try {
            Map<String, Object> result = ServiceUtil.returnSuccess();

            Map<String, Object> userLogin = UtilGenerics.toMap(context.get("userLogin"));
            Map<String, Object> profileResponse = ctx.getDispatcher().runSync("gnPrivateAuthCheckProfileCheck", UtilMisc.toMap("userLogin", userLogin));
            if (!"Y".equals(profileResponse.get("hasPermission"))) {
                throw new GnServiceException(OfbizErrors.ACCESS_DENIED, "Company hasn't permission related to operational check.");
            }

            String nameOrTaxIdeNumberPart = (String) context.get("nameOrTaxIdeNumberPart");
            result.putAll(new AuthorizationSearchHelper(ctx, context).gnCompleteCompanyInfo(nameOrTaxIdeNumberPart));
            return result;
        } catch (Exception e) {
            Debug.logError(e, module);
            return GnServiceUtil.returnError(e);
        }
    }

    public static Map<String, Object> gnFindCompanyBasesPublisher(DispatchContext ctx, Map<String, ? extends Object> context) {
        try {
            Map<String, Object> result = ServiceUtil.returnSuccess();

            Map<String, Object> userLogin = UtilGenerics.toMap(context.get("userLogin"));
            Map<String, Object> profileResponse = ctx.getDispatcher().runSync("gnPrivateAuthCheckProfileCheck", UtilMisc.toMap("userLogin", userLogin));
            if (!"Y".equals(profileResponse.get("hasPermission"))) {
                throw new GnServiceException(OfbizErrors.ACCESS_DENIED, "Company hasn't permission related to operational check.");
            }

            String taxIdentificationNumber = (String) context.get("taxIdentificationNumber");
            result.putAll(new AuthorizationSearchHelper(ctx, context).gnFindCompanyBasesPublisher(taxIdentificationNumber));
            return result;
        } catch (Exception e) {
            Debug.logError(e, module);
            return GnServiceUtil.returnError(e);
        }
    }

    /*-----------------------------------------*/

   /* *//**
     * do not use...
     * @param ctx
     * @param context
     * @return
     *//*
    public static Map<String, Object> getAllAuthorization(DispatchContext ctx, Map<String, ? extends Object> context) {
        Map<String, Object> result = FastMap.newInstance();
        Delegator delegator = ctx.getDelegator();

        String number = (String) context.get("number");

        EntityConditionList<EntityExpr> baseExprs = null;
        if (!UtilValidate.isEmail(number)) {
            List<EntityExpr> conditions = UtilMisc.toList(EntityCondition.makeCondition("number", EntityOperator.LIKE, "%" + number + "%"));
            conditions.add(EntityCondition.makeCondition("number", EntityOperator.NOT_EQUAL, null));
            baseExprs = EntityCondition.makeCondition(conditions);
        }

        try {
            List<GenericValue> ret = delegator.findList("GnAuthorizationAndAgreement", baseExprs, null, null, null, true);
            result.put("authorizations", ret);
        } catch (GenericEntityException e) {
            Debug.logError(e, module);
            result.put(ModelService.ERROR_MESSAGE, e.getMessage());
        }

        return result;
    }*/

}
