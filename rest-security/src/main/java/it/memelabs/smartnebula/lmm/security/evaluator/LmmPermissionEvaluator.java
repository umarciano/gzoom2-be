package it.memelabs.smartnebula.lmm.security.evaluator;

import it.memelabs.smartnebula.lmm.persistence.security.dao.SecurityDao;
import it.memelabs.smartnebula.lmm.persistence.main.dto.UserLogin;
import it.memelabs.smartnebula.lmm.security.PermissionEvaluator;
import it.memelabs.smartnebula.lmm.security.Principals;
import it.memelabs.smartnebula.lmm.security.model.SecurityObject;
import it.memelabs.smartnebula.lmm.security.model.SecurityDomainObject;
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
     * @param permission         {@link it.memelabs.smartnebula.lmm.security.model.SecurityPermissionMask}
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
     * @param permission         {@link it.memelabs.smartnebula.lmm.security.model.SecurityPermissionMask}
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
     * @param permission     {@link it.memelabs.smartnebula.lmm.security.model.SecurityPermissionMask}
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
