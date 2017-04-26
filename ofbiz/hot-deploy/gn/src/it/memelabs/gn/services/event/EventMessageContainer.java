package it.memelabs.gn.services.event;

import it.memelabs.gn.services.event.type.EventMessage;
import org.ofbiz.base.util.Debug;

import java.util.*;

/**
 * 13/01/14
 *
 * @author Andrea Fossi
 */
public class EventMessageContainer {
    private static ThreadLocal<List<EventMessage>> events = new ThreadLocal<List<EventMessage>>();

    /**
     * Clear current threadLocal events container
     */
    public static void clean() {
        events.remove();
        Debug.log("Clean up current thread communicationEventIds", EventMessageUtil.module);
    }

    /**
     * Add EventMessage to current threadLocal events container
     *
     * @param event
     */
    public static void add(EventMessage event) {
        if (events.get() == null)
            //removed synchronized list because container is threadLocal
            //events.set(Collections.synchronizedList(new ArrayList<EventMessage>()));
            events.set(new ArrayList<EventMessage>());
        events.get().add(event);
        Debug.log("added events[" + event.getId() + "]", EventMessageUtil.module);
    }

    /**
     * Add EventMessage to current threadLocal events container
     *
     * @param events
     */
    public static void addAll(Collection<EventMessage> events) {
        for (EventMessage em : events) {
            add(em);
        }
    }

    /**
     * @return events contained in current threadLocal events container
     */
    public static List<EventMessage> getEvents() {
        if (events.get() != null) return new ArrayList<EventMessage>(events.get());
        else return Collections.emptyList();
    }

    /**
     * @return events contained in current threadLocal events container as map
     */
    public static List<Map<String, Object>> getEventsAsMap() {
        if (events.get() != null) {
            ArrayList<Map<String, Object>> ret = new ArrayList<Map<String, Object>>(events.get().size());
            for (EventMessage em : events.get()) {
                ret.add(new HashMap<String, Object>(em));
            }
            return ret;
        } else return Collections.emptyList();
    }
}
