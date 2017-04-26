package it.memelabs.smartnebula.lmm.persistence.main.dto;

public class WeeklyWorkLogEquipmentExport extends WeeklyWorkLogEquipment {
    private String equipmentCategoryDescription;
    private String equipmentTypeDescription;
    private EquipmentEmploymentEx equipmentEmployment;

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

    public EquipmentEmploymentEx getEquipmentEmployment() {
        return equipmentEmployment;
    }

    public void setEquipmentEmployment(EquipmentEmploymentEx equipmentEmployment) {
        this.equipmentEmployment = equipmentEmployment;
    }
}
