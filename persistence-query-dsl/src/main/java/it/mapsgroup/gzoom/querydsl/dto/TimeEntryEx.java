package it.mapsgroup.gzoom.querydsl.dto;

public class TimeEntryEx extends TimeEntry {
    private WorkEffort workEffort;
    private String attivitaLiv1;
    private String attivitaLiv2;
    private String attivitaLiv3;
    private String workEffortName;

    public WorkEffort getWorkEffort() {
        return workEffort;
    }

    public void setWorkEffort(WorkEffort workEffort) {
        this.workEffort = workEffort;
    }

    public String getAttivitaLiv1() {
        return attivitaLiv1;
    }

    public void setAttivitaLiv1(String attivitaLiv1) {
        this.attivitaLiv1 = attivitaLiv1;
    }

    public String getAttivitaLiv2() {
        return attivitaLiv2;
    }

    public void setAttivitaLiv2(String attivitaLiv2) {
        this.attivitaLiv2 = attivitaLiv2;
    }

    public String getAttivitaLiv3() {
        return attivitaLiv3;
    }

    public void setAttivitaLiv3(String attivitaLiv3) {
        this.attivitaLiv3 = attivitaLiv3;
    }

    public String getWorkEffortName() {
        return workEffortName;
    }

    public void setWorkEffortName(String workEffortName) {
        this.workEffortName = workEffortName;
    }
}
