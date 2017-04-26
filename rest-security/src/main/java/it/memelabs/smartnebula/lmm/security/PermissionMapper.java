package it.memelabs.smartnebula.lmm.security;

import it.memelabs.smartnebula.lmm.persistence.main.dto.SecurityRolePermission;
import it.memelabs.smartnebula.lmm.security.model.SecurityDomainObject;
import it.memelabs.smartnebula.lmm.security.model.SecurityPermissionMask;
import org.slf4j.Logger;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author Andrea Fossi.
 */
public class PermissionMapper {
    private static final Logger LOG = getLogger(PermissionMapper.class);

    /**
     * @param perms
     * @return Map<String,Integer>  (Map<entity,mask>)
     */
    public static Map<String, Integer> mapPermissions(List<SecurityRolePermission> perms) {
        if (perms == null || perms.size() == 0) return Collections.emptyMap();
        else {
            Map<String, Integer> map = new HashMap<>();
            //init permissions map
            for (SecurityDomainObject p : SecurityDomainObject.values()) {
                map.put(p.getId(), 0);
            }
            perms.forEach(p -> copy(p, map));
            return map;
        }
    }

    private static void copy(SecurityRolePermission p, Map<String, Integer> map) {
        Integer mask = map.get(p.getEntityId());
        if (mask != null) {
            mask = mask | getMask(p);
            map.put(p.getEntityId(), mask);
        } else {
            LOG.error("Entity " + p.getEntityId() + " not exist");
        }
    }

    private static int getMask(SecurityRolePermission perm) {
        SecurityDomainObject entity = SecurityDomainObject.fromString(perm.getEntityId());
        int mask = 0;
        mask |= perm.getBasic() ? SecurityPermissionMask.BASIC : 0;
        mask |= perm.getRead() ? SecurityPermissionMask.READ : 0;
        mask |= perm.getWrite() ? SecurityPermissionMask.WRITE : 0;
        mask |= perm.getDelete() ? SecurityPermissionMask.DELETE : 0;
        mask |= perm.getValidate() ? SecurityPermissionMask.VALIDATE : 0;
        mask &= entity.getMask();//db mask is filtered ny LmmPermissions#mask
        return mask;
    }
}
