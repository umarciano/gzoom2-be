package it.memelabs.smartnebula.lmm.persistence.main.dto;

public class WorkLogEquipmentEventExport extends WorkLogEquipmentEventEx {
    private String equipmentCategoryDescription;
    private String equipmentTypeDescription;

    public String getEquipmentCategoryDescription() {
        return equipmentCategoryDescription;
    }

    public void setEquipmentCategoryDescription(String equipmentCategoryDescription) {
        this.equipmentCategoryDescription = equipmentCategoryDescription;
    }

    public String getEquipmentTypeDescription() {
        return equipmentTypeDescription;
    }

    public void setEquipmentTypeDescription(String equipmentTypeDescription) {
        this.equipmentTypeDescription = equipmentTypeDescription;
    }
}