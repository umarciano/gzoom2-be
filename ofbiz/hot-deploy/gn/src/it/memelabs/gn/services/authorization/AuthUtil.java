package it.memelabs.gn.services.authorization;

import it.memelabs.gn.services.OfbizErrors;
import it.memelabs.gn.util.GnServiceException;
import org.ofbiz.base.util.UtilValidate;

import java.util.Map;

/**
 * 04/07/13
 *
 * @author Andrea Fossi
 */
public class AuthUtil {
    /**
     * Check if ownerNodeId is the publisher node
     *
     * @param auth
     * @param ownerNodeId
     * @return
     */
    public static boolean isPublishedBy(Map<String, Object> auth, String ownerNodeId) {
        return isOwner(auth, ownerNodeId) && "0".equals(auth.get("parentVersion"));
    }

    /**
     * check if node is authorization owner
     *
     * @param auth
     * @param ownerNodeId
     * @return
     */
    public static boolean isOwner(Map<String, Object> auth, String ownerNodeId) {
        return UtilValidate.areEqual(ownerNodeId, auth.get("ownerNodeId"));
    }

    /**
     * check if authorization is private
     *
     * @param auth
     * @return isPrivate flag
     */
    public static boolean isPrivate(Map<String, Object> auth) {
        return "Y".equals(auth.get("isPrivate"));
    }

    /**
     * @param authorization
     * @param status
     * @return
     * @throws GnServiceException
     */
    public static boolean checkState(Map<String, Object> authorization, AuthorizationStatusOfbiz status) throws GnServiceException {
        if (status == null) throw new GnServiceException(OfbizErrors.GENERIC, "status cannot be empty");
        return status.name().equals(authorization.get("statusId"));
    }

    public static boolean checkInternalState(Map<String, Object> authorization, AuthorizationInternalStatusOfbiz status) throws GnServiceException {
        Object internalStatusId = authorization.get("internalStatusId");
        if (status == null) return internalStatusId == null || internalStatusId.toString().length() == 0;
        return status.name().equals(internalStatusId);
    }

    /**
     * @param authorization
     * @param status
     * @return
     * @throws GnServiceException
     */
    public static void checkStateWitError(Map<String, Object> authorization, AuthorizationStatusOfbiz status) throws GnServiceException {
        if (!checkState(authorization, status)) throw new GnServiceException(OfbizErrors.INVALID_PARAMETERS, "TODO");
    }
}
