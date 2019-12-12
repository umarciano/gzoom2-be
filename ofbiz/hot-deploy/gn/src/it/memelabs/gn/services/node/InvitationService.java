package it.memelabs.gn.services.node;

import it.memelabs.gn.util.GnServiceUtil;
import org.ofbiz.base.util.Debug;
import org.ofbiz.service.DispatchContext;
import org.ofbiz.service.ServiceUtil;

import java.util.List;
import java.util.Map;

/**
 * 08/05/13
 *
 * @author Andrea Fossi
 */
public class InvitationService {
    private static final String module = InvitationService.class.getName();

    @SuppressWarnings("unchecked")
    public static Map<String, Object> gnCreatePartyInvitation(DispatchContext ctx, Map<String, Object> context) {
        try {
            Map<String, Object> result = ServiceUtil.returnSuccess();
            InvitationHelper invitationHelper = new InvitationHelper(ctx, context);
            context.put("partyIdFrom", invitationHelper.getPartyId((String) context.get("partyIdFrom"), (String) context.get("nodeKeyFrom")));
            result.putAll(invitationHelper.gnCreatePartyInvitation(context));
            return result;
        } catch (Throwable t) {
            Debug.logError(t, module);
            return GnServiceUtil.returnError(t);
        }
    }

    @SuppressWarnings("unchecked")
    public static Map<String, Object> gnFindPartyInvitationById(DispatchContext ctx, Map<String, Object> context) {
        try {
            Map<String, Object> result = ServiceUtil.returnSuccess();
            InvitationHelper invitationHelper = new InvitationHelper(ctx, context);
            String uuid = (String) context.get("uuid");
            String partyInvitationId = (String) context.get("partyInvitationId");
            result.put("partyInvitation", invitationHelper.gnFindInvitationById(partyInvitationId, uuid));

            return result;
        } catch (Throwable t) {
            Debug.logError(t, module);
            return GnServiceUtil.returnError(t);
        }
    }

    public static Map<String, Object> gnInternalFindPartyInvitationById(DispatchContext ctx, Map<String, Object> context) {
        try {
            Map<String, Object> result = ServiceUtil.returnSuccess();
            InvitationHelper invitationHelper = new InvitationHelper(ctx, context);
            String uuid = (String) context.get("uuid");
            String partyInvitationId = (String) context.get("partyInvitationId");
            result.put("partyInvitation", invitationHelper.findInvitationById(partyInvitationId, uuid));

            return result;
        } catch (Throwable t) {
            Debug.logError(t, module);
            return GnServiceUtil.returnError(t);
        }
    }

    @SuppressWarnings("unchecked")
    public static Map<String, Object> gnFindInvitation(DispatchContext ctx, Map<String, Object> context) {
        try {
            Map<String, Object> result = ServiceUtil.returnSuccess();
            InvitationHelper invitationHelper = new InvitationHelper(ctx, context);
            String statusId = (String) context.get("statusId");
            String invitedNameContent = (String) context.get("invitedNameContent");
            String companyInvitationState = (String) context.get("companyInvitationState");
            List<Map<String, Object>> invitations = invitationHelper.gnFindInvitation(statusId, invitedNameContent,companyInvitationState);
            result.put("partyInvitations", invitations);
            result.put("partyInvitationsSize", invitations.size());
            return result;
        } catch (Throwable t) {
            Debug.logError(t, module);
            return GnServiceUtil.returnError(t);
        }
    }

    @SuppressWarnings("unchecked")
    public static Map<String, Object> gnAcceptPartyInvitation(DispatchContext ctx, Map<String, Object> context) {
        try {
            Map<String, Object> result = ServiceUtil.returnSuccess();
            InvitationHelper invitationHelper = new InvitationHelper(ctx, context);
            String uuid = (String) context.get("uuid");
            Map<String, Object> companyBase = (Map<String, Object>) context.get("companyBase");
            Map<String, Object> userLogin = (Map<String, Object>) context.get("invitedUserLogin");
            String password = (String) context.get("password");
            String companyName = (String) context.get("companyName");

            result.putAll(invitationHelper.gnAcceptPartyInvitation(uuid,companyName, companyBase, userLogin, password));

            return result;
        } catch (Throwable t) {
            Debug.logError(t, module);
            return GnServiceUtil.returnError(t);
        }
    }

    @SuppressWarnings("unchecked")
    public static Map<String, Object> gnUpdateInvitationState(DispatchContext ctx, Map<String, Object> context) {
        try {
            Map<String, Object> result = ServiceUtil.returnSuccess();
            InvitationHelper invitationHelper = new InvitationHelper(ctx, context);
            String uuid = (String) context.get("uuid");
            String partyInvitationId = (String) context.get("partyInvitationId");
            String statusId = (String) context.get("statusId");

            invitationHelper.updateInvitationState(partyInvitationId, uuid, statusId);

            return result;
        } catch (Throwable t) {
            Debug.logError(t, module);
            return GnServiceUtil.returnError(t);
        }
    }

    @SuppressWarnings("unchecked")
    public static Map<String, Object> gnInvitationFindCountries(DispatchContext ctx, Map<String, Object> context) {
        try {
            Map<String, Object> result = ServiceUtil.returnSuccess();
            InvitationHelper invitationHelper = new InvitationHelper(ctx, context);
            String uuid = (String) context.get("uuid");
            result.put("countries", invitationHelper.gnInvitationFindCountries(uuid));
            return result;
        } catch (Throwable t) {
            Debug.logError(t, module);
            return GnServiceUtil.returnError(t);
        }
    }

    @SuppressWarnings("unchecked")
    public static Map<String, Object> gnManageOldPartyInvitation(DispatchContext ctx, Map<String, Object> context) {
        try {
            Map<String, Object> result = ServiceUtil.returnSuccess();
            new InvitationJobHelper(ctx, context).gnManageOldPartyInvitation();
            return result;
        } catch (Throwable t) {
            Debug.logError(t, module);
            return GnServiceUtil.returnError(t);
        }
    }

    @SuppressWarnings("unchecked")
    public static Map<String, Object> gnScheduleJobsAuthorizationPublicationReminderForInvitedCompany(DispatchContext ctx, Map<String, Object> context) {
        try {
            Map<String, Object> result = ServiceUtil.returnSuccess();
            new InvitationJobHelper(ctx, context).gnScheduleJobsAuthorizationPublicationReminderForInvitedCompany();
            return result;
        } catch (Throwable t) {
            Debug.logError(t, module);
            return GnServiceUtil.returnError(t);
        }
    }

}
