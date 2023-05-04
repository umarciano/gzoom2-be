package it.mapsgroup.gzoom.querydsl.dto;

public class WorkEffortAnalysisTypeTypeExt extends WorkEffortTypeType{

    private  WorkEffortTypeType workEffortTypeType;
    private  WorkEffortAnalysis workEffortAnalysis;
    private WorkEffort workEffort;

    public WorkEffortTypeType getWorkEffortTypeType() { return workEffortTypeType; }

    public void setWorkEffortTypeType(WorkEffortTypeType workEffortTypeType) {
        this.workEffortTypeType = workEffortTypeType;
    }

    public WorkEffortAnalysis getWorkEffortAnalysis() {
        return workEffortAnalysis;
    }

    public void setWorkEffortAnalysis(WorkEffortAnalysis workEffortAnalysis) {
        this.workEffortAnalysis = workEffortAnalysis;
    }

    public WorkEffort getWorkEffort() {
        return workEffort;
    }

    public void setWorkEffort(WorkEffort workEffort) {
        this.workEffort = workEffort;
    }
}
