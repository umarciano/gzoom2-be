package it.mapsgroup.gzoom.security.evaluator;

import it.mapsgroup.gzoom.security.model.SecurityPermissionMask;
import it.memelabs.smartnebula.lmm.persistence.security.dao.SecurityDao;
import it.memelabs.smartnebula.lmm.persistence.main.dto.UserLogin;
import it.mapsgroup.gzoom.security.PermissionEvaluator;
import it.mapsgroup.gzoom.security.Principals;
import it.mapsgroup.gzoom.security.model.SecurityObject;
import it.mapsgroup.gzoom.security.model.SecurityDomainObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.io.Serializable;

/**
 * @author Andrea Fossi.
 */
@Service
public class LmmPermissionEvaluator implements org.springframework.security.access.PermissionEvaluator {

    private final SecurityDao securityDao;

    /**
     * @param securityDao
     */
    @Autowired
    public LmmPermissionEvaluator(SecurityDao securityDao) {
        this.securityDao = securityDao;
    }

    /**
     * @param authentication
     * @param targetDomainObject {@link SecurityObject}
     * @param permission         {@link SecurityPermissionMask}
     * @return
     */
    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        if (targetDomainObject != null) {
            SecurityObject so = SecurityObject.fromString(String.valueOf(targetDomainObject));
            return hasPermission(authentication, so.getPermission(), permission);
        }
        return false;
    }

    /**
     * @param authentication
     * @param targetDomainObject {@link SecurityDomainObject}
     * @param permission         {@link SecurityPermissionMask}
     * @return
     */
    private boolean hasPermission(Authentication authentication, SecurityDomainObject targetDomainObject, Object permission) {
        UserLogin principal = ((UserLogin) authentication.getPrincipal());
        return PermissionEvaluator.isAdmin(principal) ||
                PermissionEvaluator.hasPermission(principal, targetDomainObject, (String) permission);
    }

    /**
     * @param authentication
     * @param targetId       {@link Long} object id
     * @param targetType     {@link SecurityObject}
     * @param permission     {@link SecurityPermissionMask}
     * @return
     */
    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        if (targetId != null) {
            SecurityObject so = SecurityObject.fromString(targetType);
            boolean hasPerm = hasPermission(authentication, so.getPermission(), permission);
            if (hasPerm) {
                return securityDao.check(so.getQuery(), (Long) targetId, Principals.principal().getActiveNode().getId());
            }
        }
        return false;
    }


}
