package it.mapsgroup.gzoom.querydsl.dto;


/**
 * @author Andrea Fossi.
 */
public class UserLoginEx extends UserLogin {
    private Party party;
    private Person person;

    public Party getParty() {
        return party;
    }

    public void setParty(Party party) {
        this.party = party;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
}
