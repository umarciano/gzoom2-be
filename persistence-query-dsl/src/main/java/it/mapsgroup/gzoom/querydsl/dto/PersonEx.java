package it.mapsgroup.gzoom.querydsl.dto;

public class PersonEx extends Person{
    private Party party;
    private PartyParentRole partyParentRole;
    private StatusItem statusItem;
    private EmplPositionType emplPositionType;
    private ContactMech contactMech;

    public void setParty(Party party) {
        this.party = party;
    }

    public void setPartyParentRole(PartyParentRole partyParentRole) {
        this.partyParentRole = partyParentRole;
    }

    public void setStatusItem(StatusItem statusItem) {
        this.statusItem = statusItem;
    }

    public void setEmplPositionType(EmplPositionType emplPositionType) {
        this.emplPositionType = emplPositionType;
    }

    public PartyParentRole getPartyParentRole() {
        return partyParentRole;
    }

    public Party getParty() {
        return party;
    }

    public StatusItem getStatusItem() {
        return statusItem;
    }

    public EmplPositionType getEmplPositionType() {
        return emplPositionType;
    }

    public ContactMech getContactMech() {
        return contactMech;
    }

    public void setContactMech(ContactMech contactMech) {
        this.contactMech = contactMech;
    }


}
