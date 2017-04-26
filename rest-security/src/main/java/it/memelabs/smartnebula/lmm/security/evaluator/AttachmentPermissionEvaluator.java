package it.memelabs.smartnebula.lmm.security.evaluator;

import com.google.common.collect.ImmutableMap;
import it.memelabs.smartnebula.lmm.persistence.main.dao.AttachmentDao;
import it.memelabs.smartnebula.lmm.persistence.main.dto.Attachment;
import it.memelabs.smartnebula.lmm.persistence.main.dto.UserLogin;
import it.memelabs.smartnebula.lmm.persistence.main.enumeration.AttachmentEntity;
import it.memelabs.smartnebula.lmm.security.PermissionEvaluator;
import it.memelabs.smartnebula.lmm.security.model.SecurityDomainObject;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;

import java.util.Objects;

import static it.memelabs.smartnebula.lmm.security.Principals.principal;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author Andrea Fossi.
 */

public class AttachmentPermissionEvaluator {
    private static final Logger LOG = getLogger(AttachmentPermissionEvaluator.class);

    private final AttachmentDao attachmentDao;

    @Autowired
    public AttachmentPermissionEvaluator(AttachmentDao attachmentDao) {
        this.attachmentDao = attachmentDao;
    }


    public boolean hasPermission(Authentication authentication, String targetId, String permission) {
        if (PermissionEvaluator.isAdmin((UserLogin) authentication.getPrincipal())) {
            return true;
        } else {
            SecurityDomainObject object = ENTITY2PERMISSION.get(targetId);
            LOG.debug("Evaluating permission for ATTACHMENT of {}", targetId);
            return PermissionEvaluator.hasPermission((UserLogin) authentication.getPrincipal(), object, permission);
        }
    }

    public boolean hasPermission(Authentication authentication, Long targetId, String permission) {
        if (PermissionEvaluator.isAdmin((UserLogin) authentication.getPrincipal())) {
            return true;
        } else {
            Attachment att = attachmentDao.findByPrimaryKey(targetId);
            if (att != null) {
                SecurityDomainObject object = ENTITY2PERMISSION.get(att.getEntity().name());
                if (Objects.equals(att.getOwnerNodeId(), principal().getActiveNode().getId())) {
                    LOG.debug("Evaluating permission for ATTACHMENT of {}", object.getId());
                    return PermissionEvaluator.hasPermission((UserLogin) authentication.getPrincipal(), object, permission);
                } else {
                    LOG.debug("Wrong ownerNodeId for ATTACHMENT of {}", object.getId());
                    return false;
                }
            } else
                return false;
        }
    }

    private static final ImmutableMap<String, SecurityDomainObject> ENTITY2PERMISSION = getMap();

    private static ImmutableMap<String, SecurityDomainObject> getMap() {
        ImmutableMap.Builder<String, SecurityDomainObject> builder = ImmutableMap.builder();
        builder.put(AttachmentEntity.COMPANY.name(), SecurityDomainObject.COMPANY);
        builder.put(AttachmentEntity.ATI.name(), SecurityDomainObject.COMPANY);
        builder.put(AttachmentEntity.COMPANY_SECURITY.name(), SecurityDomainObject.COMPANY);
        builder.put(AttachmentEntity.ATI_SECURITY.name(), SecurityDomainObject.COMPANY);
        builder.put(AttachmentEntity.ANTIMAFIA_PROCESS.name(), SecurityDomainObject.ANTIMAFIA_PROCESS);
        builder.put(AttachmentEntity.ANTIMAFIA_PHASE.name(), SecurityDomainObject.ANTIMAFIA_PROCESS);
        builder.put(AttachmentEntity.PERSON.name(), SecurityDomainObject.PERSON);
        builder.put(AttachmentEntity.PERSON_EMPLOYMENT.name(), SecurityDomainObject.PERSON_EMPLOYMENT);
        builder.put(AttachmentEntity.EQUIPMENT.name(), SecurityDomainObject.EQUIPMENT);
        builder.put(AttachmentEntity.EQUIPMENT_EMPLOYMENT.name(), SecurityDomainObject.EQUIPMENT_EMPLOYMENT);
        builder.put(AttachmentEntity.CONTRACT.name(), SecurityDomainObject.CONTRACT);
        builder.put(AttachmentEntity.CONTRACT_TRACEABILITY.name(), SecurityDomainObject.CONTRACT);
        builder.put(AttachmentEntity.CONTRACT_REGULARITY.name(), SecurityDomainObject.CONTRACT);
        return builder.build();
    }

}
