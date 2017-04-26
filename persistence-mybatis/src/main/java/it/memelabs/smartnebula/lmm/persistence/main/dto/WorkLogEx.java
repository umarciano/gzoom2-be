package it.memelabs.smartnebula.lmm.persistence.main.dto;

public class WorkLogEx extends WorkLog {

    private EntityState state;
    private ConstructionSite constructionSite;
    private int personCount;
    private int equipmentCount;
    private int companyCount;

    private int personExternalEventCount;
    private int equipmentExternalEventCount;

    public EntityState getState() {
        return state;
    }

    public void setState(EntityState state) {
        this.state = state;
    }

    public ConstructionSite getConstructionSite() {
        return constructionSite;
    }

    public void setConstructionSite(ConstructionSite constructionSite) {
        this.constructionSite = constructionSite;
    }

    public int getPersonCount() {
        return personCount;
    }

    public void setPersonCount(int personCount) {
        this.personCount = personCount;
    }

    public int getEquipmentCount() {
        return equipmentCount;
    }

    public void setEquipmentCount(int equipmentCount) {
        this.equipmentCount = equipmentCount;
    }

    public int getCompanyCount() {
        return companyCount;
    }

    public void setCompanyCount(int companyCount) {
        this.companyCount = companyCount;
    }

    public int getPersonExternalEventCount() {
        return personExternalEventCount;
    }

    public void setPersonExternalEventCount(int personExternalEventCount) {
        this.personExternalEventCount = personExternalEventCount;
    }

    public int getEquipmentExternalEventCount() {
        return equipmentExternalEventCount;
    }

    public void setEquipmentExternalEventCount(int equipmentExternalEventCount) {
        this.equipmentExternalEventCount = equipmentExternalEventCount;
    }
}
