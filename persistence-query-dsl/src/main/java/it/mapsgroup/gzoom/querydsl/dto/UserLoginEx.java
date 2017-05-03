package it.mapsgroup.gzoom.querydsl.dto;


/**
 * @author Andrea Fossi.
 */
public class UserLoginEx extends UserLogin {
    private Party party;

    public Party getParty() {
        return party;
    }

    public void setParty(Party party) {
        this.party = party;
    }
}
