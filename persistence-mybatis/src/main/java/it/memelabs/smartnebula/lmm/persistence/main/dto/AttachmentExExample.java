package it.memelabs.smartnebula.lmm.persistence.main.dto;

import it.memelabs.smartnebula.lmm.persistence.main.enumeration.EntityStateTag;

public class AttachmentExExample extends AttachmentExample {

    private boolean filterEmploymentDate;
    private boolean filterAntimafiaPhase;
    private EntityStateTag entityStateTag;

    public boolean isFilterEmploymentDate() {
        return filterEmploymentDate;
    }

    public void setFilterEmploymentDate(boolean filterEmploymentDate) {
        this.filterEmploymentDate = filterEmploymentDate;
    }

    public boolean isFilterAntimafiaPhase() {
        return filterAntimafiaPhase;
    }

    public void setFilterAntimafiaPhase(boolean filterAntimafiaPhase) {
        this.filterAntimafiaPhase = filterAntimafiaPhase;
    }

    public EntityStateTag getEntityStateTag() {
        return entityStateTag;
    }

    public void setEntityStateTag(EntityStateTag entityStateTag) {
        this.entityStateTag = entityStateTag;
    }
}
