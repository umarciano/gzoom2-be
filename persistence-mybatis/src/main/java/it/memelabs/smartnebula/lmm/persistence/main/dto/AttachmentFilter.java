package it.memelabs.smartnebula.lmm.persistence.main.dto;

import java.time.LocalDate;
import java.util.Date;

import it.memelabs.smartnebula.lmm.persistence.main.enumeration.AttachmentEntity;
import it.memelabs.smartnebula.lmm.persistence.main.enumeration.EntityStateTag;

/**
 * @author Andrea Fossi.
 */
public class AttachmentFilter {
    private AttachmentEntity entity;
    private Long companyId;
    private Long atiId;
    private Long antimafiaProcessId;
    private Long antimafiaProcessPhaseId;
    private Long personId;
    private Long personEmploymentId;
    private Long equipmentId;
    private Long equipmentEmploymentId;
    private Long accidentId;
    private Long contractId;
    private LocalDate validUntil;
    private Date validUntilBefore;
    private Long id;
    private EntityStateTag entityStateTag;

    public Long getContractId() {
        return contractId;
    }

    public void setContractId(Long contractId) {
        this.contractId = contractId;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Long getAtiId() {
        return atiId;
    }

    public void setAtiId(Long atiId) {
        this.atiId = atiId;
    }

    public Long getAntimafiaProcessId() {
        return antimafiaProcessId;
    }

    public void setAntimafiaProcessId(Long antimafiaProcessId) {
        this.antimafiaProcessId = antimafiaProcessId;
    }

    public Long getAntimafiaProcessPhaseId() {
        return antimafiaProcessPhaseId;
    }

    public void setAntimafiaProcessPhaseId(Long antimafiaProcessPhaseId) {
        this.antimafiaProcessPhaseId = antimafiaProcessPhaseId;
    }

    public Long getPersonId() {
        return personId;
    }

    public void setPersonId(Long personId) {
        this.personId = personId;
    }

    public Long getPersonEmploymentId() {
        return personEmploymentId;
    }

    public void setPersonEmploymentId(Long personEmploymentId) {
        this.personEmploymentId = personEmploymentId;
    }

    public Long getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(Long equipmentId) {
        this.equipmentId = equipmentId;
    }

    public Long getEquipmentEmploymentId() {
        return equipmentEmploymentId;
    }

    public void setEquipmentEmploymentId(Long equipmentEmploymentId) {
        this.equipmentEmploymentId = equipmentEmploymentId;
    }

    public AttachmentEntity getEntity() {
        return entity;
    }

    public void setEntity(AttachmentEntity entity) {
        this.entity = entity;
    }

    public LocalDate getValidUntil() {
        return validUntil;
    }

    public void setValidUntil(LocalDate validUntil) {
        this.validUntil = validUntil;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAccidentId() {
        return accidentId;
    }

    public void setAccidentId(Long accidentId) {
        this.accidentId = accidentId;
    }

    public EntityStateTag getEntityStateTag() {
        return entityStateTag;
    }

    public void setEntityStateTag(EntityStateTag entityStateTag) {
        this.entityStateTag = entityStateTag;
    }

    public Date getValidUntilBefore() {
        return validUntilBefore;
    }

    public void setValidUntilBefore(Date validUntilBefore) {
        this.validUntilBefore = validUntilBefore;
    }
}
