package it.memelabs.smartnebula.lmm.security.evaluator;

import com.google.common.collect.ImmutableMap;
import it.memelabs.smartnebula.lmm.persistence.main.dao.CommentDao;
import it.memelabs.smartnebula.lmm.persistence.main.dto.Comment;
import it.memelabs.smartnebula.lmm.persistence.main.dto.UserLogin;
import it.memelabs.smartnebula.lmm.persistence.main.enumeration.CommentEntity;
import it.memelabs.smartnebula.lmm.security.PermissionEvaluator;
import it.memelabs.smartnebula.lmm.security.model.SecurityDomainObject;
import it.memelabs.smartnebula.lmm.security.model.SecurityObject;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author Andrea Fossi.
 */
public class CommentPermissionEvaluator {
    private static final Logger LOG = getLogger(CommentPermissionEvaluator.class);

    private final LmmPermissionEvaluator permissionEvaluator;
    private final CommentDao commentDao;

    @Autowired
    public CommentPermissionEvaluator(LmmPermissionEvaluator permissionEvaluator, CommentDao commentDao) {
        this.permissionEvaluator = permissionEvaluator;
        this.commentDao = commentDao;
    }

    /**
     * Evaluate permission for comment create request
     *
     * @param authentication
     * @param comment        create request body
     * @param permission
     * @return
     */
    public boolean hasPermission(Authentication authentication, it.memelabs.smartnebula.lmm.model.Comment comment, String permission) {
        if (comment.getConstructionSiteLog() != null) {
            return hasPermission(authentication, comment.getConstructionSiteLog().getId(), CommentEntity.CONSTRUCTION_SITE_LOG_ANNOTATION.name(), permission);
        } else {
            LOG.warn("Comment owner not found");
            return false;
        }
    }

    /**
     * check if user has passed 'permission' on owner comment entity
     *
     * @param authentication
     * @param targetId
     * @param permission
     * @return
     */
    public boolean hasPermission(Authentication authentication, Long targetId, String permission) {
        if (PermissionEvaluator.isAdmin((UserLogin) authentication.getPrincipal())) {
            return true;
        } else {
            Comment comment = commentDao.findById(targetId);
            if (comment == null) {
                LOG.warn("Comment[{}] not found", targetId);
                return false;
            }

            SecurityObject targetType = null;
            Long ownerId = null;
            if (comment.getAntimafiaProcessId() != null) {
                targetType = SecurityObject.ANTIMAFIA_PROCESS;
                ownerId = comment.getAntimafiaProcessId();
            } else if (comment.getContractId() != null) {
                targetType = SecurityObject.CONTRACT;
                ownerId = comment.getContractId();
            } else if (comment.getConstructionSiteLogId() != null) {
                targetType = SecurityObject.CONSTRUCTION_SITE_LOG;
                ownerId = comment.getConstructionSiteLogId();
            } else {
                LOG.warn("Comment[{}] owner not found", targetId);
                return false;
            }
            LOG.debug("Evaluating permission for COMMENT of {}", targetType);
            return permissionEvaluator.hasPermission(authentication, ownerId, targetType.name(), permission);
        }
    }

    public boolean hasPermission(Authentication authentication, Long targetId, String targetKind, String permission) {
        if (PermissionEvaluator.isAdmin((UserLogin) authentication.getPrincipal())) {
            return true;
        } else {
            LOG.debug("Evaluating permission for COMMENT of {}", targetKind);
            SecurityDomainObject targetType = ENTITY2SECURITY_OBJECT.get(targetKind);
            if (targetType != null) {
                return permissionEvaluator.hasPermission(authentication, targetId, targetType.name(), permission);
            } else {
                return false;
            }
        }
    }

    public boolean hasPermission(Authentication authentication, String targetKind, String permission) {
        if (PermissionEvaluator.isAdmin((UserLogin) authentication.getPrincipal())) {
            return true;
        } else {
            LOG.debug("Evaluating permission for COMMENT of {}", targetKind);
            SecurityDomainObject targetType = ENTITY2SECURITY_OBJECT.get(targetKind);
            if (targetType != null) {
                return permissionEvaluator.hasPermission(authentication, targetType.name(), permission);
            } else {
                return false;
            }
        }
    }


    private static final ImmutableMap<String, SecurityDomainObject> ENTITY2SECURITY_OBJECT = getMap();

    private static ImmutableMap<String, SecurityDomainObject> getMap() {
        ImmutableMap.Builder<String, SecurityDomainObject> builder = ImmutableMap.builder();
        builder.put(CommentEntity.CONTRACT_WORKFLOW.name(), SecurityDomainObject.CONTRACT);
        builder.put(CommentEntity.ANTIMAFIA_PROCESS_WORKFLOW.name(), SecurityDomainObject.ANTIMAFIA_PROCESS);
        builder.put(CommentEntity.CONSTRUCTION_SITE_LOG_ANNOTATION.name(), SecurityDomainObject.CONSTRUCTION_SITE_LOG);
        return builder.build();
    }

}
