package it.memelabs.smartnebula.lmm.persistence.main.enumeration;

import com.google.common.collect.ImmutableMap;
import it.mapsgroup.commons.Enums;

/**
 * @author Andrea Fossi.
 */
public enum CommentEntity {

    ANTIMAFIA_PROCESS_WORKFLOW,
    CONTRACT_WORKFLOW,
    CONSTRUCTION_SITE_LOG_ANNOTATION;

    private static final ImmutableMap<String, CommentEntity> STR2ENUM = Enums.parseMap(CommentEntity.class);

    public static CommentEntity fromString(String value) {
        return STR2ENUM.get(value);
    }
}
