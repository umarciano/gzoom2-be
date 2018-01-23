package it.mapsgroup.gzoom.querydsl.dto;

public class TimeEntryEx extends TimeEntry {
    private WorkEffort workEffort;
    private String attivitaLiv1;
    private String attivitaLiv2;
    private String attivitaLiv3;
    private String workEffortName;

    private WorkEffort workEffort1;
    private WorkEffort workEffort2;
    private WorkEffort workEffort3;

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

    public WorkEffort getWorkEffort1() {
        return workEffort1;
    }

    public void setWorkEffort1(WorkEffort workEffort1) {
        this.workEffort1 = workEffort1;
    }

    public WorkEffort getWorkEffort2() {
        return workEffort2;
    }

    public void setWorkEffort2(WorkEffort workEffort2) {
        this.workEffort2 = workEffort2;
    }

    public WorkEffort getWorkEffort3() {
        return workEffort3;
    }

    public void setWorkEffort3(WorkEffort workEffort3) {
        this.workEffort3 = workEffort3;
    }
}
