package it.memelabs.smartnebula.lmm.persistence.main.dto;

public class WorkLogEquipmentEventEx extends WorkLogEquipmentEvent {
    private EquipmentEmploymentEx equipmentEmployment;
    private Wbs wbs;
    private EntityState state;
    private WorkLogEx workLog;


    public EquipmentEmploymentEx getEquipmentEmployment() {
        return equipmentEmployment;
    }

    public void setEquipmentEmployment(EquipmentEmploymentEx equipmentEmployment) {
        this.equipmentEmployment = equipmentEmployment;
    }

    public Wbs getWbs() {
        return wbs;
    }

    public void setWbs(Wbs wbs) {
        this.wbs = wbs;
    }

    public EntityState getState() {
        return state;
    }

    public void setState(EntityState state) {
        this.state = state;
    }

    public WorkLogEx getWorkLog() {
        return workLog;
    }

    public void setWorkLog(WorkLogEx workLog) {
        this.workLog = workLog;
    }
}