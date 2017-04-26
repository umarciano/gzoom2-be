package it.memelabs.smartnebula.lmm.persistence.main.dto;

public class LotExExample extends LotExample {
	
	private String filterText;

	public String getFilterText() {
		return filterText;
	}

	public void setFilterText(String filterText) {
		this.filterText = "%"+filterText+"%";
	}	
	
}
