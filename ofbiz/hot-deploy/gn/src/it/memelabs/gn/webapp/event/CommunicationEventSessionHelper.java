package it.memelabs.gn.webapp.event;

import org.ofbiz.base.util.Debug;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 17/05/13
 *
 * @author Andrea Fossi
 */
public class CommunicationEventSessionHelper {
    private static ThreadLocal<List<String>> communicationEventIds = new ThreadLocal<List<String>>();
    private static final String module = CommunicationEventSessionHelper.class.getName();

    public static void clean() {
        communicationEventIds.remove();
        Debug.log("Clean up current thread communicationEventIds", module);
    }

    public static void putCommunicationEventId(String communicationEventId) {
        if (communicationEventIds.get() == null)
            //removed synchronized list because container is threadLocal
            //communicationEventIds.set(Collections.synchronizedList(new ArrayList<String>()));
            communicationEventIds.set(new ArrayList<String>());
        communicationEventIds.get().add(communicationEventId);
        Debug.log("added communicationEventId[" + communicationEventId + "]", module);
    }

    public static List<String> getCommunicationEventIds() {
        if (communicationEventIds.get() != null) return new ArrayList<String>(communicationEventIds.get());
        else return Collections.emptyList();
    }

    public static String getString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (String s : getCommunicationEventIds()) {
            sb.append(s).append(",");
        }
        sb.replace(0, sb.length() - 1, "]");
        return "current thread returned communicationEventIds" + sb.toString();
    }
}
