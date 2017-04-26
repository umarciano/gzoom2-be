package it.memelabs.smartnebula.lmm.persistence.main.dto;

public class WorkLogPersonEventEx extends WorkLogPersonEvent {
    private PersonEmploymentEx personEmployment;
    private Wbs wbs;
    private EntityState state;
    private WorkLogEx workLog;


    public Wbs getWbs() {
        return wbs;
    }

    public void setWbs(Wbs wbs) {
        this.wbs = wbs;
    }

    public PersonEmploymentEx getPersonEmployment() {
        return personEmployment;
    }

    public void setPersonEmployment(PersonEmploymentEx personEmployment) {
        this.personEmployment = personEmployment;
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