package it.memelabs.gn.webapp.event;

import it.memelabs.gn.services.OfbizErrors;
import it.memelabs.gn.util.GnServiceException;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.service.GenericServiceException;
import org.ofbiz.service.LocalDispatcher;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 11/07/13
 *
 * @author Andrea Fossi
 */
public class AuditEventSessionHelper {
    private static ThreadLocal<AuditEventContainer> auditEvents = new ThreadLocal<AuditEventContainer>();
    private static final String module = AuditEventSessionHelper.class.getName();


    public static void clean() {
        auditEvents.remove();
        Debug.log("Clean up current thread auditEvents", module);
    }

    public static void putAuditEvent(AuditEvent auditEvent) throws GnServiceException {
        if (auditEvents.get() == null)
            throw new GnServiceException(OfbizErrors.GENERIC, "auditEvents must be initialized.");
        auditEvents.get().addEvent(auditEvent);
        Debug.log("added auditEvent[" + auditEvent + "]", module);
    }

    public static void putAuditEvent(String type, Map<String, Object> oldValue, Map<String, Object> newValue, List<Map<String, Object>> fallback) throws GnServiceException {
        if (auditEvents.get() == null)
            throw new GnServiceException(OfbizErrors.GENERIC, "auditEvents must be initialized.");
        AuditEvent auditEvent = new AuditEvent(type, oldValue, newValue, fallback);
        auditEvents.get().addEvent(auditEvent);
        Debug.log("added auditEvent[" + auditEvent + "]", module);
    }

    public static Map<String, Object> getAuditEvents() {
        AuditEventContainer auditEventContainer = auditEvents.get();
        if (auditEventContainer != null) {
            return auditEventContainer.toMap();
        } else return new HashMap<String, Object>();
    }

    public static void init(String userLoginId, String contextId, String contextDescription) {
        auditEvents.set(new AuditEventContainer(userLoginId, contextId, contextDescription));
        Debug.log("initialized audit event container");
    }

    public static void init(LocalDispatcher localDispatcher, Map<String, ? extends Object> context) throws GenericServiceException {
        GenericValue userLogin = (GenericValue) context.get("userLogin");
        String userLoginId = null;
        String contextId = null;
        String contextDescription = null;
        if (UtilValidate.isNotEmpty(userLogin)) {
            userLoginId = userLogin.getString("userLoginId");
            contextId = userLogin.getString("activeContextId");
            if (UtilValidate.isNotEmpty(contextId)) {
                Map<String, Object> gnContext = localDispatcher.runSync("gnFindContextById",
                        UtilMisc.toMap("partyId", contextId, "userLogin", userLogin,
                                "excludeCompanyBases", "Y",
                                "excludeSecurityGroups", "Y",
                                "excludePartyNode", "Y",
                                "excludeUsers", "Y"));
                contextDescription = (String) gnContext.get("description");
            }
        }
        auditEvents.set(new AuditEventContainer(userLoginId, contextId, contextDescription));
        Debug.log("initialized audit event container");
    }

    public static Map<String, Object> getAuditEventMap() {
        return auditEvents.get().toMap();
    }

}
