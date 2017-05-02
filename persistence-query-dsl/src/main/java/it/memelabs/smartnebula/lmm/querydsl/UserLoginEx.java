package it.memelabs.smartnebula.lmm.querydsl;

import it.memelabs.smartnebula.lmm.querydsl.generated.Party;
import it.memelabs.smartnebula.lmm.querydsl.generated.UserLogin;

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
