package it.mapsgroup.gzoom.querydsl.dto;

public class PersonEx extends Person{
    private Party party;
    private PartyParentRole partyParentRole;

    public PartyParentRole getPartyParentRole() {
        return partyParentRole;
    }

    public void setPartyParentRole(PartyParentRole partyParentRole) {
        this.partyParentRole = partyParentRole;
    }

    public Party getParty() {
        return party;
    }

    public void setParty(Party party) {
        this.party = party;
    }

}
