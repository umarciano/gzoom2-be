package it.memelabs.gn.services.event;

import org.ofbiz.entity.Delegator;

/**
 * 10/01/14
 *
 * @author Andrea Fossi
 */
public class EventMessageUtil {
    public static final String module = EventMessageUtil.class.getName();

    private Delegator delegator;
    private static EventMessageUtil eventMessageUtil;

    private EventMessageUtil(Delegator delegator) {
        this.delegator = delegator;
    }

    public static void init(Delegator delegator) {
        if (eventMessageUtil == null)
            eventMessageUtil = new EventMessageUtil(delegator);

    }

    public static EventMessageUtil getSingleton() {
        if (eventMessageUtil == null) throw new RuntimeException("EventUtil is not initialized");
        return eventMessageUtil;
    }

    public Long getNextId() {
        return delegator.getNextSeqIdLong("GnEventMessage");
    }


}
