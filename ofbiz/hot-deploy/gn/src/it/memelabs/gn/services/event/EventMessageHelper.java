package it.memelabs.gn.services.event;

import it.memelabs.gn.services.AbstractServiceHelper;
import it.memelabs.gn.services.OfbizErrors;
import it.memelabs.gn.services.event.type.*;
import it.memelabs.gn.util.GnServiceException;
import it.memelabs.gn.util.GnServiceUtil;
import it.memelabs.gn.util.SysUtil;
import org.ofbiz.base.util.Debug;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.service.DispatchContext;
import org.ofbiz.service.GenericServiceException;
import org.ofbiz.service.ServiceUtil;

import java.util.Map;

/**
 * 13/01/14
 *
 * @author Andrea Fossi
 */
public class EventMessageHelper extends AbstractServiceHelper {
    public final static String module = EventMessageHelper.class.getName();
    public final static String sender = "OFBIZ@" + SysUtil.getInstanceId();

    public EventMessageHelper(DispatchContext dctx, Map<String, ? extends Object> context) {
        super(dctx, context);
    }

    /**
     * @param eventMap
     * @throws GenericEntityException
     */
    public void gnProcessEvent(Map<String, Object> eventMap) throws GenericEntityException, GenericServiceException {
        String eventTypeString = (String) eventMap.get("type");
        if (eventTypeString == null) throw new GnServiceException(OfbizErrors.GENERIC, "eventType is empty");
        EventMessageTypeOfBiz eventType = EventMessageTypeOfBiz.valueOf(eventTypeString);
        Debug.log(String.format("Processing event[%s][%s] ", eventType, eventMap.get("id")));
        switch (eventType) {
            case PING:
                ping(EventMessageFactory.make().makeEventMessagePing(eventMap));
                break;
            case AUTH_ARCHIVED_ACK:
                authArchivedAck(EventMessageFactory.make().makeEventMessageAuthArchivedAck(eventMap));
                break;
            case AUTH_SEND:
                authSend(EventMessageFactory.make().makeEventMessageAuthSend(eventMap));
                break;
            case AUTH_RECEIVED_ACK:
                authReceivedAck(EventMessageFactory.make().makeEventMessageAuthReceivedAck(eventMap));
                break;
            case AUTH_SEND_DELETE:
                authSendDelete(EventMessageFactory.make().makeEventMessageAuthSendDelete(eventMap));
                break;
            case COMMUNICATION_ACK:
                communicationAck(EventMessageFactory.make().makeEventMessageCommunicationAck(eventMap));
                break;
            case AUTH_MERGE_ACK:
                authMergeAck(EventMessageFactory.make().makeEventMessageAuthMergeAck(eventMap));
                break;
            case FILTER_APERTURE_SEND:
                filterApertureSend(EventMessageFactory.make().makeEventMessageFilterApertureSend(eventMap));
                break;
            case JOB_ACK:
                jobAck(EventMessageFactory.make().makeEventMessageJobAck(eventMap));
                break;
            default:
                throw new GnServiceException(OfbizErrors.GENERIC, "eventType[" + eventType.name() + "] unknown");
        }
    }

    private void ping(EventMessagePing event) {
        EventMessagePong ret = EventMessageFactory.make().makeEventMessagePong();
        ret.setMessage(event.getMessage());
        EventMessageContainer.add(ret);
    }

    private void authArchivedAck(EventMessageAuthArchivedAck event) throws GenericServiceException {
        runService("gnDeleteAuthorizationEM", event);
    }

    private void authSend(EventMessageAuthSend event) throws GenericServiceException {
        runService("gnPropagateAuthorizationToEM", event);
    }

    private void authReceivedAck(EventMessageAuthReceivedAck event) throws GenericServiceException {
        runService("gnReceivedAuthorizationAckEM", event);
    }

    private void authSendDelete(EventMessageAuthSendDelete event) throws GenericServiceException {
        runService("gnPropagateDeleteAuthorizationToEM", event);
    }

    private void communicationAck(EventMessageCommunicationAck event) throws GenericServiceException {
        runService("gnUpdateCommunicationEventStatusEM", event);
    }

    private void authMergeAck(EventMessageAuthMergeAck event) throws GenericServiceException {
        runService("gnAuthorizationMergeAckEM", event);
    }


    private void filterApertureSend(EventMessageFilterApertureSend event) throws GenericServiceException {
        runService("gnFilterApertureSendToEM", event);
    }

    private void jobAck(EventMessageJobAck event) throws GenericServiceException {
        runService("gnNotifyResult", event);
    }

    private Map<String, Object> runService(String serviceName, EventMessage event) throws GenericServiceException {
        Debug.log(String.format("Running service  event[%s][%s] ", event.getType(), event.getId()));
        Map<String, Object> srvRequest = dctx.makeValidContext(serviceName, "IN", event);
        srvRequest.put("userLogin", userLogin);
        Map<String, Object> result = dispatcher.runSync(serviceName, srvRequest);
        if (ServiceUtil.isError(result)) {
            throw new GnServiceException(GnServiceUtil.getErrorCode(result).ordinal(), ServiceUtil.getErrorMessage(result));
        } else return result;

    }
}
