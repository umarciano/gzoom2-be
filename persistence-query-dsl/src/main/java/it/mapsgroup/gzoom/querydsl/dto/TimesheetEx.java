package it.mapsgroup.gzoom.querydsl.dto;

public class TimesheetEx extends Timesheet {
    private Party party;

    private Party partyStructure;

    private int updatable;

    private PartyHistoryView partyHistoryView;

    private WorkEffortTypeContent workEffortTypeContent;

    private StatusItem statusItem;

    private WorkEffortTypePeriod workEffortTypePeriod;

    private PartyParentRole partyParentRole;

    private PartyParentRole partyParentRoleStructure;

    private Uom uom;

    public int getUpdatable() {
        return updatable;
    }

    public void setUpdatable(int updatable) {
        this.updatable = updatable;
    }

    public Party getParty() {
        return party;
    }

    public void setParty(Party party) {
        this.party = party;
    }

    public PartyHistoryView getPartyHistoryView() {
        return partyHistoryView;
    }

    public void setPartyHistoryView(PartyHistoryView partyHistoryView) {
        this.partyHistoryView = partyHistoryView;
    }

    public PartyParentRole getPartyParentRole() {
        return partyParentRole;
    }

    public void setPartyParentRole(PartyParentRole partyParentRole) {
        this.partyParentRole = partyParentRole;
    }

    public PartyParentRole getPartyParentRoleStructure() { return partyParentRoleStructure; }

    public void setPartyParentRoleStructure(PartyParentRole partyParentRoleStructure) { this.partyParentRoleStructure = partyParentRoleStructure; }

    public Uom getUom() {
        return uom;
    }

    public void setUom(Uom uom) {
        this.uom = uom;
    }

    public Party getPartyStructure() {
        return partyStructure;
    }

    public void setPartyStructure(Party partyStructure) {
        this.partyStructure = partyStructure;
    }

    public WorkEffortTypeContent getWorkEffortTypeContent() {
        return workEffortTypeContent;
    }

    public void setWorkEffortTypeContent(WorkEffortTypeContent workEffortTypeContent) {
        this.workEffortTypeContent = workEffortTypeContent;
    }

    public StatusItem getStatusItem() {
        return statusItem;
    }

    public void setStatusItem(StatusItem statusItem) {
        this.statusItem = statusItem;
    }

    public WorkEffortTypePeriod getWorkEffortTypePeriod() {
        return workEffortTypePeriod;
    }

    public void setWorkEffortTypePeriod(WorkEffortTypePeriod workEffortTypePeriod) {
        this.workEffortTypePeriod = workEffortTypePeriod;
    }
}
