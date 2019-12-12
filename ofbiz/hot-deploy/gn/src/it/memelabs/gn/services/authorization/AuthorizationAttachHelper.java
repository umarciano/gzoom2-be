package it.memelabs.gn.services.authorization;

import it.memelabs.gn.services.OfbizErrors;
import it.memelabs.gn.util.GnServiceException;
import it.memelabs.gn.util.find.GnFindUtil;
import javolution.util.FastList;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilDateTime;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.entity.condition.EntityCondition;
import org.ofbiz.entity.util.EntityUtil;
import org.ofbiz.service.DispatchContext;
import org.ofbiz.service.GenericServiceException;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

public class AuthorizationAttachHelper extends CommonAuthorizationHelper {

    private static final String module = AuthorizationAttachHelper.class.getName();

    private static enum E {
        GN_AUTH_ATTACH
    }

    public AuthorizationAttachHelper(DispatchContext dctx, Map<String, ? extends Object> context) {
        super(dctx, context);
    }

    /**
     * Make a new map with aliased field names.
     *
     * @param item
     * @return
     */
    public Map<String, Object> aliasFields(Map<String, Object> item) throws GenericServiceException {
        //return UtilMisc.makeMapWritable(item);
        return UtilMisc.toMap("contentId", item.get("contentId"), "contentName", item.get("contentName"),
                "description", item.get("description"), "mimeTypeId", item.get("mimeTypeId"),
                "contentSize", item.get("contentSize"), "createdDate", item.get("fromDate"),
                "uploadedByUserLogin", item.get("uploadedByUserLogin"), "agreementContentTypeId", item.get("agreementContentTypeId"));
        //return dctx.getModelService("gnDownloadAttachment").makeValid(item, "OUT");
    }

    /**
     * Make a new list with aliased field names.
     *
     * @param list
     * @return
     */
    public List<Map<String, Object>> aliasFields(List<GenericValue> list) throws GenericServiceException {
        List<Map<String, Object>> result = FastList.newInstance();
        for (Map<String, Object> item : list) {
            result.add(aliasFields(item));
        }
        return result;
    }

    /**
     * Find agreement attachments by type.
     *
     * @param agreementId
     * @param contentName (optional)
     * @param type        (optional, if null all types will be searched)
     * @return
     * @throws GenericEntityException
     */
    public List<GenericValue> find(String agreementId, String contentName, AgreementContentTypeOfbiz type) throws GenericEntityException {
        //Map<String, Object> filterMap = UtilMisc.toMap("agreementId", agreementId, "agreementContentTypeId", (Object) E.GN_AUTH_ATTACH.name());
        List<EntityCondition> conds = FastList.newInstance();
        conds.add(EntityCondition.makeCondition("agreementId", agreementId));
        if (type == null) {
            conds.add(GnFindUtil.makeOrConditionById("agreementContentTypeId",
                    UtilMisc.toList(AgreementContentTypeOfbiz.GN_AUTH_DOC.name(), AgreementContentTypeOfbiz.GN_AUTH_PAY_BRD_DOC.name(), AgreementContentTypeOfbiz.GN_AUTH_SUPPL_DOC.name())));
        } else {
            conds.add(EntityCondition.makeCondition("agreementContentTypeId", AgreementContentTypeOfbiz.GN_AUTH_DOC.name()));
        }
        conds.add(EntityCondition.makeCondition("agreementId", agreementId));
        if (UtilValidate.isNotEmpty(contentName))
            conds.add(EntityCondition.makeCondition("contentName", contentName));
        //filterMap.put("contentName", contentName);
        //return delegator.findByAnd("GnAgreementContentView", filterMap, UtilMisc.toList("fromDate ASC"));
        return delegator.findList("GnAgreementContentView", EntityCondition.makeCondition(conds), null, UtilMisc.toList("fromDate ASC"), null, false);
    }

    /**
     * Find agreement attachments.
     *
     * @param agreementId
     * @param contentName (optional)
     * @return
     * @throws GenericEntityException
     */
    public List<GenericValue> find(String agreementId, String contentName) throws GenericEntityException {
        return find(agreementId, contentName, null);
    }

    /**
     * Create/update attachment found by fileName.
     * Method are used bay service entry point
     *
     * @param agreementId
     * @param contentName
     * @param description
     * @param mimeTypeId
     * @param contentSize
     * @param overwrite
     * @param agreementContentTypeId
     * @return
     * @throws GenericEntityException
     * @throws GenericServiceException
     */
    public String gnUploadAttachment(String agreementId, String contentName, String description, String mimeTypeId, Long contentSize, String agreementContentTypeId, boolean overwrite) throws GenericEntityException, GenericServiceException {
        canUpdateAuthorization(agreementId, null, null, null);
        return upload(agreementId, contentName, description, mimeTypeId, contentSize, UtilDateTime.nowTimestamp(), agreementContentTypeId, overwrite);
    }

    /**
     * Create/update attachment found by fileName.
     *
     * @param agreementId
     * @param contentName
     * @param description
     * @param mimeTypeId
     * @param contentSize
     * @param createdDate
     * @param overwrite
     * @return
     * @throws GenericEntityException
     * @throws GenericServiceException
     */
    public String upload(String agreementId, String contentName, String description, String mimeTypeId, Long contentSize, Timestamp createdDate, String agreementContentTypeId, boolean overwrite) throws GenericEntityException, GenericServiceException {
        String result = "";
        Map<String, ? extends Object> serviceParam;
        Map<String, Object> serviceResult;


        GenericValue content = EntityUtil.getFirst(find(agreementId, contentName));
        if (content == null) {
            // create content
            serviceParam = UtilMisc.toMap("userLogin", userLogin, "contentName", contentName, "description", description, "mimeTypeId", mimeTypeId, "contentSize", contentSize, "createdDate", createdDate);
            serviceResult = dispatcher.runSync("createContent", serviceParam);
            final String contentId = (String) serviceResult.get("contentId");
            // create agreement content
            serviceParam = UtilMisc.toMap("userLogin", userLogin, "agreementId", agreementId, "contentId", contentId,
                    "agreementContentTypeId", agreementContentTypeId, "uploadedByUserLogin", userLogin.get("userLoginId"));
            serviceResult = dispatcher.runSync("gnCreateAgreementContent", serviceParam);
            result = contentId;
        } else {
            String contentId = content.getString("contentId");
            if (overwrite) {
                if (!agreementContentTypeId.equals(content.getString("agreementContentTypeId"))) {
                    //You are trying to upload the same file with another document type, update won't be possible
                    throw new GnServiceException(OfbizErrors.DUPLICATED_UPLOAD, "The upload file name already exists with a different document type");
                }
                // update content
                serviceParam = UtilMisc.toMap("userLogin", userLogin, "contentId", contentId, "description", description, "mimeTypeId", mimeTypeId, "contentSize", contentSize, "createdDate", createdDate);
                serviceResult = dispatcher.runSync("updateContent", serviceParam);
                result = (String) serviceResult.get("contentId");
                serviceParam = UtilMisc.toMap("userLogin", userLogin, "agreementId", agreementId, "contentId", contentId,
                        "agreementContentTypeId", agreementContentTypeId, "uploadedByUserLogin", userLogin.get("userLoginId"),
                        "fromDate", content.get("fromDate"));
                serviceResult = dispatcher.runSync("gnUpdateAgreementContent", serviceParam);
            } else {
                Debug.logWarning("Agreement attachment found but not updated: " + agreementId + ", " + contentId, module);
            }
        }

        return result;
    }

    /**
     * Get attachment details found by contentName.
     *
     * @param agreementId
     * @param contentName
     * @return
     * @throws GenericEntityException
     * @throws GnServiceException
     */
    public Map<String, Object> download(String agreementId, String contentName) throws GenericEntityException, GenericServiceException {
        GenericValue gv = EntityUtil.getFirst(find(agreementId, contentName));
        if (gv == null)
            throw new GnServiceException(OfbizErrors.ATTACHMENT_NOT_FOUND, "Agreement attachment not found: " + agreementId + ", " + contentName);
        return aliasFields(gv);
    }

    /**
     * Delete the attachment found by contentName.
     *
     * @param agreementId
     * @param contentName
     * @return
     * @throws GenericEntityException
     * @throws GenericServiceException
     */
    public void delete(String agreementId, String contentName) throws GenericEntityException, GenericServiceException {
        GenericValue attach = EntityUtil.getFirst(find(agreementId, contentName));
        if (attach == null)
            throw new GnServiceException(OfbizErrors.ATTACHMENT_NOT_FOUND, "Agreement attachment not found: " + agreementId + ", " + contentName);
        canUpdateAuthorization(agreementId, null, null, null);
        delete(attach);
    }

    /**
     * Delete an attachment.
     *
     * @param item
     * @throws GenericServiceException
     * @throws GenericEntityException
     */
    public void delete(Map<String, Object> item) throws GenericServiceException, GenericEntityException {
        Map<String, ? extends Object> serviceParam;
        final String agreementId = (String) item.get("agreementId");
        final String contentId = (String) item.get("contentId");
        final String agreementContentTypeId = (String) item.get("agreementContentTypeId");
        Timestamp fromDate = (Timestamp) item.get("fromDate");
        //workAround because sometimes fromDate is not passed
        if (UtilValidate.isEmpty(fromDate)) {
            Debug.log("workAround: find GnAgreementContentView to load fromDate field", module);
            fromDate = (Timestamp) EntityUtil.getFirst(delegator.findByAnd("GnAgreementContentView", UtilMisc.toMap("agreementId", (Object) agreementId, "contentId", contentId))).get("fromDate");
        }
        // delete agreement content
        serviceParam = UtilMisc.toMap("userLogin", userLogin, "agreementId", agreementId, "contentId", contentId, "agreementContentTypeId", agreementContentTypeId, "fromDate", fromDate);
        dispatcher.runSync("gnRemoveAgreementContent", serviceParam);
        deleteContent(contentId);
    }

    private void deleteContent(String contentId) throws GenericServiceException, GenericEntityException {
        String serviceName;
        Map<String, Object> serviceParam;
        List<GenericValue> list = delegator.findByAnd("ContentRole", "contentId", contentId);
        for (GenericValue item : list) {
            // delete content role
            serviceName = "removeContentRole";
            serviceParam = UtilMisc.toMap("userLogin", (Object) userLogin);
            serviceParam.putAll(item);
            serviceParam = dctx.makeValidContext(serviceName, "IN", serviceParam);
            dispatcher.runSync(serviceName, serviceParam);
        }
        // delete content
        serviceParam = UtilMisc.toMap("userLogin", (Object) userLogin, "contentId", contentId);
        dispatcher.runSync("removeContent", serviceParam);
    }
}
