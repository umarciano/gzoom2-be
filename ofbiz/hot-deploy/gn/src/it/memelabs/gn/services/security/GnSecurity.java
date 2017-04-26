package it.memelabs.gn.services.security;

import javolution.util.FastList;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.entity.Delegator;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.entity.util.EntityUtil;

import java.util.Iterator;
import java.util.List;

/**
 * 15/02/13
 *
 * @author Andrea Fossi
 */
public class GnSecurity {
    public static final String module = GnSecurity.class.getName();

    protected Delegator delegator = null;

    public GnSecurity(Delegator delegator) {
        this.delegator = delegator;
    }

    public Delegator getDelegator() {
        return this.delegator;
    }

    public void setDelegator(Delegator delegator) {
        this.delegator = delegator;
    }

    /**
     * @see org.ofbiz.security.Security#findUserLoginSecurityGroupByUserLoginId(java.lang.String)
     */
    public Iterator<GenericValue> findPartySecurityGroupByPartyId(String partyId) {
        List<GenericValue> collection;
        try {
            collection = delegator.findByAnd("GnPartySecurityGroup", UtilMisc.toMap("partyId", partyId), null);
        } catch (GenericEntityException e) {
            // make an empty collection to speed up the case where a userLogin belongs to no security groups, only with no exception of course
            collection = FastList.newInstance();
            Debug.logWarning(e, module);
        }
        // filter each time after cache retreival, ie cache will contain entire list
        collection = EntityUtil.filterByDate(collection, true);
        return collection.iterator();
    }

    /**
     * @param permission
     * @param userLogin
     * @return
     * @see org.ofbiz.security.Security#hasPermission(java.lang.String, org.ofbiz.entity.GenericValue)
     */
    public boolean hasPermission(PermissionsOfbiz permission, GenericValue userLogin) {
        return hasPermission(permission.name(), userLogin);
    }

    /**
     * @param permission
     * @param userLogin
     * @return
     * @see org.ofbiz.security.Security#hasPermission(java.lang.String, org.ofbiz.entity.GenericValue)
     */
    public boolean hasPermission(String permission, GenericValue userLogin) {
        if (userLogin == null) return false;
        String partyId = userLogin.getString("activeContextId");
        return hasPermissionByContext(permission, partyId);
    }

    /**
     * @param permission
     * @param contextId
     * @return
     */
    public boolean hasPermissionByContext(String permission, String contextId) {
        if (UtilValidate.isEmpty(contextId)) return false;

        Iterator<GenericValue> iterator = findPartySecurityGroupByPartyId(contextId);
        GenericValue userLoginSecurityGroup = null;

        while (iterator.hasNext()) {
            userLoginSecurityGroup = iterator.next();
            if (securityGroupPermissionExists(userLoginSecurityGroup.getString("groupId"), permission)) return true;
        }
        return false;
    }

    /**
     * @see org.ofbiz.security.Security#securityGroupPermissionExists(java.lang.String, java.lang.String)
     */
    public boolean securityGroupPermissionExists(String groupId, String permission) {
        GenericValue securityGroupPermissionValue = delegator.makeValue("SecurityGroupPermission",
                UtilMisc.toMap("groupId", groupId, "permissionId", permission));
        try {
            return delegator.findOne(securityGroupPermissionValue.getEntityName(), securityGroupPermissionValue, false) != null;
        } catch (GenericEntityException e) {
            Debug.logWarning(e, module);
            return false;
        }
    }

    /**
     * @see org.ofbiz.security.Security#hasEntityPermission(java.lang.String, java.lang.String, org.ofbiz.entity.GenericValue)
     */
    public boolean hasEntityPermission(String entity, String action, GenericValue userLogin) {
        if (userLogin == null) return false;
        String partyId = userLogin.getString("activeContextId");
        if (UtilValidate.isEmpty(partyId)) return false;

        // if (Debug.infoOn()) Debug.logInfo("hasEntityPermission: entity=" + entity + ", action=" + action, module);
        Iterator<GenericValue> iterator = findPartySecurityGroupByPartyId(partyId);
        GenericValue userLoginSecurityGroup = null;

        while (iterator.hasNext()) {
            userLoginSecurityGroup = iterator.next();

            // if (Debug.infoOn()) Debug.logInfo("hasEntityPermission: userLoginSecurityGroup=" + userLoginSecurityGroup.toString(), module);

            // always try _ADMIN first so that it will cache first, keeping the cache smaller
            if (securityGroupPermissionExists(userLoginSecurityGroup.getString("groupId"), entity + "_ADMIN"))
                return true;
            if (securityGroupPermissionExists(userLoginSecurityGroup.getString("groupId"), entity + action))
                return true;
        }

        return false;
    }

}
