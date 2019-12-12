package it.memelabs.gn.services.node;

import it.memelabs.gn.services.AbstractServiceHelper;
import it.memelabs.gn.services.OfbizErrors;
import it.memelabs.gn.util.GnServiceException;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.service.DispatchContext;
import org.ofbiz.service.GenericServiceException;

import java.util.List;
import java.util.Map;

/**
 * 25/06/2014
 *
 * @author Andrea Fossi
 */
public class MessageTemplateHelper extends AbstractServiceHelper {
    private static final String module = MessageTemplateHelper.class.getName();

    public MessageTemplateHelper(DispatchContext dctx, Map<String, ? extends Object> context) {
        super(dctx, context);
    }

    /**
     * This code is for saving PartyInvitation default text message, but in future can be expanded to manage other template.
     *
     * @param partyId
     * @param messageTemplateSettingId
     * @param description
     * @param fromAddress
     * @param ccAddress
     * @param bccAddress
     * @param subject
     * @param message
     * @param contentType
     * @return
     * @throws GenericEntityException
     * @throws GenericServiceException
     */
    public String gnCreateUpdateInvitationDefaultMessage(String partyId, String messageTemplateSettingId, String description, String fromAddress, String ccAddress, String bccAddress, String subject, String message, String contentType) throws GenericEntityException, GenericServiceException {
        if (UtilValidate.isEmpty(partyId)) {
            partyId = getCurrentContextId();
        } else {
            if (!UtilValidate.areEqual(partyId, getCurrentContextId()))
                throw new GnServiceException(OfbizErrors.INVALID_PARAMETERS, "You can create GnMessageTemplateSetting for current context only");
        }
        return createUpdatePartyDefaultMessage(partyId, messageTemplateSettingId, description,
                fromAddress, ccAddress, bccAddress, subject, message, contentType);
    }

    protected String createUpdatePartyDefaultMessage(String partyId, String messageTemplateSettingId, String description, String fromAddress, String ccAddress, String bccAddress, String subject, String message, String contentType) throws GenericEntityException, GenericServiceException {
        if (UtilValidate.isNotEmpty(messageTemplateSettingId)) {
            if (UtilValidate.isEmpty(findDefaultMessageAssoc(messageTemplateSettingId, partyId)))
                throw new GnServiceException(OfbizErrors.INVALID_PARAMETERS, "GnMessageTemplateSettingPartyAssoc not exist");
            updateDefaultMessage(messageTemplateSettingId, description, fromAddress, ccAddress, bccAddress, subject, message, contentType);
        } else {
            if (UtilValidate.isNotEmpty(gnFindInvitationMessageTemplateSetting(partyId)))
                throw new GnServiceException(OfbizErrors.BUSINESS_LOGIC_EXCEPTION, "You can add only one GnMessageTemplateSettingPartyAssoc to Party[" + partyId + "]");
            messageTemplateSettingId = createDefaultMessage(description, fromAddress, ccAddress, bccAddress, subject, message, contentType);
            createDefaultMessageAssoc(messageTemplateSettingId, partyId);
        }
        return messageTemplateSettingId;
    }

    public GenericValue gnFindInvitationMessageTemplateSetting(String partyId) throws GenericEntityException, GenericServiceException {
        if (UtilValidate.isEmpty(partyId)) {
            partyId = getCurrentContextId();
        } else {
            if (!UtilValidate.areEqual(partyId, getCurrentContextId()))
                throw new GnServiceException(OfbizErrors.INVALID_PARAMETERS, "You can create GnMessageTemplateSetting for current context only");
        }
        return findPartyMessageTemplateSetting(partyId);
    }

    public void gnDeleteInvitationMessageTemplateSetting(String messageTemplateSettingId, String partyId) throws GenericEntityException, GenericServiceException {
        if (UtilValidate.isEmpty(partyId)) {
            partyId = getCurrentContextId();
        } else {
            if (!UtilValidate.areEqual(partyId, getCurrentContextId()))
                throw new GnServiceException(OfbizErrors.INVALID_PARAMETERS, "You can delete GnMessageTemplateSetting for current context only");
        }
        deletePartyMessageTemplateSetting(messageTemplateSettingId, partyId);
    }

    private void deletePartyMessageTemplateSetting(String messageTemplateSettingId, String partyId) throws GenericEntityException, GnServiceException {
        // GenericValue ret = findPartyMessageTemplateSetting(partyId);
        //   if (UtilValidate.isEmpty(ret))
        //      throw new GnServiceException(OfbizErrors.ENTITY_EXCEPTION, "GnMessageTemplateSettingPartyAssoc not found");
        GenericValue assoc = delegator.findByPrimaryKey("GnMessageTemplateSettingPartyAssoc",
                UtilMisc.toMap("messageTemplateSettingId", messageTemplateSettingId, "partyId", partyId));
          if (UtilValidate.isEmpty(assoc))
              throw new GnServiceException(OfbizErrors.ENTITY_EXCEPTION, "GnMessageTemplateSettingPartyAssoc not found");
        delegator.removeValue(assoc);
        GenericValue msg = delegator.findByPrimaryKey("GnMessageTemplateSetting", UtilMisc.toMap("messageTemplateSettingId", messageTemplateSettingId));
        if (UtilValidate.isEmpty(msg))
            throw new GnServiceException(OfbizErrors.ENTITY_EXCEPTION, "GnMessageTemplateSettingPartyAssoc not found");
        delegator.removeValue(msg);
    }

    private GenericValue findPartyMessageTemplateSetting(String partyId) throws GenericEntityException, GnServiceException {
        List<GenericValue> templates = delegator.findByAnd("GnPartyMessageTemplateSetting", UtilMisc.toMap("partyId", partyId));
        if (templates.size() == 0) return null;
        else if (templates.size() > 1)
            throw new GnServiceException(OfbizErrors.ENTITY_EXCEPTION, "You can associate one GnPartyMessageTemplateSetting to a Party only");
        else
            return templates.get(0);
    }


    private String createDefaultMessage(String description, String fromAddress, String ccAddress, String bccAddress, String subject, String message, String contentType) throws GenericEntityException {
        GenericValue gv = delegator.makeValue("GnMessageTemplateSetting");
        gv.setNextSeqId();
        gv.set("description", description);
        gv.set("fromAddress", fromAddress);
        gv.set("ccAddress", ccAddress);
        gv.set("bccAddress", bccAddress);
        gv.set("subject", subject);
        gv.set("message", message);
        gv.set("contentType", contentType);
        delegator.create(gv);
        String messageTemplateSettingId = gv.getString("messageTemplateSettingId");
        Debug.log("Created GnMessageTemplateSetting[" + messageTemplateSettingId + "]", module);
        return messageTemplateSettingId;
    }


    private String updateDefaultMessage(String messageTemplateSettingId, String description, String fromAddress, String ccAddress, String bccAddress, String subject, String message, String contentType) throws GenericEntityException, GnServiceException {
        GenericValue gv = delegator.findOne("GnMessageTemplateSetting", false, "messageTemplateSettingId", messageTemplateSettingId);
        if (UtilValidate.isEmpty(gv))
            throw new GnServiceException(OfbizErrors.INVALID_PARAMETERS, "GnMessageTemplateSetting not exist");

        gv.set("description", description);
        gv.set("fromAddress", fromAddress);
        gv.set("ccAddress", ccAddress);
        gv.set("bccAddress", bccAddress);
        gv.set("subject", subject);
        gv.set("message", message);
        gv.set("contentType", contentType);
        delegator.store(gv);
        Debug.log("Updated GnMessageTemplateSetting[" + messageTemplateSettingId + "]", module);
        return messageTemplateSettingId;
    }


    private void createDefaultMessageAssoc(String messageTemplateSettingId, String partyId) throws GenericEntityException {
        GenericValue gv = delegator.makeValue("GnMessageTemplateSettingPartyAssoc");
        gv.set("messageTemplateSettingId", messageTemplateSettingId);
        gv.set("partyId", partyId);
        delegator.create(gv);
        Debug.log("Created GnMessageTemplateSettingPartyAssoc[" + messageTemplateSettingId + "," + partyId + "]", module);
    }

    private GenericValue findDefaultMessageAssoc(String messageTemplateSettingId, String partyId) throws GenericEntityException {
        return delegator.findOne("GnMessageTemplateSettingPartyAssoc", false, "messageTemplateSettingId", messageTemplateSettingId, "partyId", partyId);
    }


}
