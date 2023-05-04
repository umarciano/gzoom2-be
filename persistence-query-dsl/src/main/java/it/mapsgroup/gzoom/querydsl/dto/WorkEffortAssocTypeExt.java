package it.mapsgroup.gzoom.querydsl.dto;

public class WorkEffortAssocTypeExt extends WorkEffortAssocType {
    private WorkEffortAssocType parentWorkEffortAssocType;

    public WorkEffortAssocType getParentWorkEffortAssocType() {
        return parentWorkEffortAssocType;
    }

    public void setParentWorkEffortAssocType(WorkEffortAssocType parentWorkEffortAssocType) {
        this.parentWorkEffortAssocType = parentWorkEffortAssocType;
    }


}
