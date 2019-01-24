package it.mapsgroup.gzoom.querydsl.dto;

public class PartyEx extends Party {
	
	private PartyParentRole partyParentRole;

	/**
	 * @return the partyParentRole
	 */
	public PartyParentRole getPartyParentRole() {
		return partyParentRole;
	}

	/**
	 * @param partyParentRole the partyParentRole to set
	 */
	public void setPartyParentRole(PartyParentRole partyParentRole) {
		this.partyParentRole = partyParentRole;
	}
	
}
