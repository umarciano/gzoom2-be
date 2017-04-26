package it.memelabs.smartnebula.lmm.persistence.main.dto;

public class EquipmentEmploymentEx extends EquipmentEmployment implements Employment {

    private EntityState state;
    private Company company;
    private Equipment equipment;

    public EntityState getState() {
        return state;
    }

    public void setState(EntityState state) {
        this.state = state;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Equipment getEquipment() {
        return equipment;
    }

    public void setEquipment(Equipment equipment) {
        this.equipment = equipment;
    }
}
