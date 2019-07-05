package it.mapsgroup.gzoom.querydsl.dto;

public class PersonEx extends Person{
    private Party party;

    public Party getParty() {
        return party;
    }

    public void setParty(Party party) {
        this.party = party;
    }

}
