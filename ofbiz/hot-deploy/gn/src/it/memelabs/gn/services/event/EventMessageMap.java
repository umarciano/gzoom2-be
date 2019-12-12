package it.memelabs.gn.services.event;

import it.memelabs.gn.services.OfbizErrors;
import it.memelabs.gn.util.GnServiceException;
import org.ofbiz.service.DispatchContext;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/**
 * 14/01/14
 *
 * @author Andrea Fossi
 */
public class EventMessageMap {
    private static Map<EventMessageTypeOfBiz, Class<? extends AbstractEventMessageHelper>> map = new HashMap<EventMessageTypeOfBiz, Class<? extends AbstractEventMessageHelper>>();

    static {
        map.put(EventMessageTypeOfBiz.PING, null);

    }

    public static Class<? extends AbstractEventMessageHelper> getClass(EventMessageTypeOfBiz eventTypeOfBiz) {
        return map.get(eventTypeOfBiz);
    }

    @SuppressWarnings("unchecked")
    public static <K extends AbstractEventMessageHelper> K newInstance(EventMessageTypeOfBiz eventTypeOfBiz, DispatchContext dctx, Map<String, ? extends Object> context) throws GnServiceException {
        try {
            Class<? extends AbstractEventMessageHelper> clazz = getClass(eventTypeOfBiz);
            if (clazz == null) throw new GnServiceException(OfbizErrors.GENERIC, "clazz is null");
            AbstractEventMessageHelper abstractServiceHelper = clazz.getDeclaredConstructor(DispatchContext.class, Map.class).newInstance(dctx, context);
            return (K) abstractServiceHelper;
        } catch (InstantiationException e) {
            throw new GnServiceException(OfbizErrors.GENERIC, e.getMessage());
        } catch (IllegalAccessException e) {
            throw new GnServiceException(OfbizErrors.GENERIC, e.getMessage());
        } catch (InvocationTargetException e) {
            throw new GnServiceException(OfbizErrors.GENERIC, e.getMessage());
        } catch (NoSuchMethodException e) {
            throw new GnServiceException(OfbizErrors.GENERIC, e.getMessage());
        }
    }

}
