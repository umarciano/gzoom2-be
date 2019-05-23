package it.mapsgroup.gzoom.querydsl.dto;

public class PartyContentEx extends Content {
	private PartyContent partyContent;

	public DataResource getDataResource() {
		return dataResource;
	}

	public void setDataResource(DataResource dataResource) {
		this.dataResource = dataResource;
	}

	private DataResource dataResource;
	
	/**
	 * @return the partyContent
	 */
	public PartyContent getPartyContent() {
		return partyContent;
	}
	/**
	 * @param partyContent the partyContent to set
	 */
	public void setPartyContent(PartyContent partyContent) {
		this.partyContent = partyContent;
	}
}
