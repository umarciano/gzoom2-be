package it.memelabs.gn.services.event;

import it.memelabs.gn.services.event.type.EventMessage;
import it.memelabs.gn.util.SysUtil;

import java.util.Map;

/**
 * 17/01/14
 *
 * @author Andrea Fossi
 */
public class EventMessageFactory {
    public static final String sender = "OFBIZ@" + SysUtil.getInstanceId();
    public static final AbstractEventMessageFactory make = new AbstractEventMessageFactory() {
        @Override
        protected <T extends EventMessage> T init(T em) {
            return EventMessageFactory.init(em);
        }

        @Override
        protected <T extends EventMessage> T init(T em, Map<String, Object> attributes) {
            return EventMessageFactory.init(em, attributes);
        }
    };

    public static AbstractEventMessageFactory make() {
        return make;
    }

    @SuppressWarnings("unchecked")
    private static <T extends EventMessage> T init(T em) {
        em.setType(em.getType());
        em.setId(EventMessageUtil.getSingleton().getNextId());
        em.setTimestamp(System.currentTimeMillis());
        em.setSender(sender);
        return em;
    }

    @SuppressWarnings("unchecked")
    private static <T extends EventMessage> T init(T em, Map<String, Object> attributes) {
        em.clear();
        em.putAll(attributes);
        return em;
    }
}
