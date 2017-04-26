package it.memelabs.smartnebula.lmm.persistence.main.enumeration;

import com.google.common.collect.ImmutableMap;
import it.mapsgroup.commons.Enums;

/**
 * @author Andrea Fossi.
 */
public enum AttachmentEntity {
    COMPANY,
    ATI,
    COMPANY_SECURITY,
    ATI_SECURITY,
    ANTIMAFIA_PROCESS, /* TODO @anfo implemento la gestione e le join sulle colonne attachment->mafia */
    ANTIMAFIA_PHASE, /* TODO @anfo implemento la gestione e le join sulle colonne attachment->mafia */
    PERSON,
    PERSON_IMAGE,
    PERSON_EMPLOYMENT,
    EQUIPMENT,
    EQUIPMENT_EMPLOYMENT,
    CONTRACT,
    CONTRACT_TRACEABILITY,
    CONTRACT_REGULARITY,
    ACCIDENT,//
    ;

    private static final ImmutableMap<String, AttachmentEntity> STR2PERM = Enums.parseMap(AttachmentEntity.class);

    public AttachmentEntity fromString(String value) {
        return STR2PERM.get(value);
    }
}
