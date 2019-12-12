package it.memelabs.gn.services.event;

import it.memelabs.gn.util.GnServiceUtil;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.service.DispatchContext;

import java.util.Map;

/**
 * 10/01/14
 *
 * @author Andrea Fossi
 */
public class EventMessageService {
    private static final String module = EventMessageService.class.getName();

    /**
     * @param ctx
     * @param context
     * @return
     */
    public static Map<String, Object> gnProcessEvent(DispatchContext ctx, Map<String, Object> context) {

        try {
            Map<String, Object> result = GnServiceUtil.returnSuccess();

            Map<String, Object> eventMap = UtilMisc.<String, String, Object>getMapFromMap(context, "eventMessage");


            new EventMessageHelper(ctx, context).gnProcessEvent(eventMap);
            return result;
        } catch (Throwable e) {
            Debug.logError(e, module);
            return GnServiceUtil.returnError(e);
        }
    }


}
