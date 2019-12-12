package it.memelabs.gn.services.node;

import it.memelabs.gn.util.GnServiceUtil;
import org.ofbiz.base.util.Debug;
import org.ofbiz.service.DispatchContext;
import org.ofbiz.service.ServiceUtil;

import java.util.Map;

/**
 * 25/06/2014
 *
 * @author Andrea Fossi
 */
public class MessageTemplateServices {
    private static final String module = MessageTemplateServices.class.getName();

    @SuppressWarnings("unchecked")
    public static Map<String, Object> gnCreateUpdateInvitationDefaultMessage(DispatchContext ctx, Map<String, Object> context) {
        try {
            Map<String, Object> result = ServiceUtil.returnSuccess();
            MessageTemplateHelper templateHelper = new MessageTemplateHelper(ctx, context);
            String partyId = (String) context.get("partyId");
            String messageTemplateSettingId = (String) context.get("messageTemplateSettingId");
            String description = (String) context.get("description");
            String fromAddress = (String) context.get("fromAddress");
            String ccAddress = (String) context.get("ccAddress");
            String bccAddress = (String) context.get("bccAddress");
            String subject = (String) context.get("subject");
            String message = (String) context.get("message");
            String contentType = (String) context.get("contentType");
            String ret = templateHelper.gnCreateUpdateInvitationDefaultMessage(partyId, messageTemplateSettingId, description,
                    fromAddress, ccAddress, bccAddress, subject, message, contentType);
            result.put("messageTemplateSettingId", ret);
            return result;
        } catch (Throwable t) {
            Debug.logError(t, module);
            return GnServiceUtil.returnError(t);
        }
    }

    @SuppressWarnings("unchecked")
    public static Map<String, Object> gnFindInvitationMessageTemplateSetting(DispatchContext ctx, Map<String, Object> context) {
        try {
            Map<String, Object> result = ServiceUtil.returnSuccess();
            MessageTemplateHelper templateHelper = new MessageTemplateHelper(ctx, context);
            String partyId = (String) context.get("partyId");
            result.put("messageTemplateSetting", templateHelper.gnFindInvitationMessageTemplateSetting(partyId));
            return result;
        } catch (Throwable t) {
            Debug.logError(t, module);
            return GnServiceUtil.returnError(t);
        }
    }

    @SuppressWarnings("unchecked")
    public static Map<String, Object> gnDeleteInvitationMessageTemplateSetting(DispatchContext ctx, Map<String, Object> context) {
        try {
            Map<String, Object> result = ServiceUtil.returnSuccess();
            MessageTemplateHelper templateHelper = new MessageTemplateHelper(ctx, context);
            String partyId = (String) context.get("partyId");
            String messageTemplateSettingId = (String) context.get("messageTemplateSettingId");
            templateHelper.gnDeleteInvitationMessageTemplateSetting(messageTemplateSettingId,partyId);
            return result;
        } catch (Throwable t) {
            Debug.logError(t, module);
            return GnServiceUtil.returnError(t);
        }
    }

}
