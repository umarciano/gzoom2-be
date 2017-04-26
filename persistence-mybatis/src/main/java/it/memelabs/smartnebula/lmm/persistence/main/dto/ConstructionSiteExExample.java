package it.memelabs.smartnebula.lmm.persistence.main.dto;

public class ConstructionSiteExExample extends ConstructionSiteExample {
	
	private String filterText;

	public String getFilterText() {
		return filterText;
	}

	public void setFilterText(String filterText) {
		this.filterText = "%"+filterText+"%";
	} 
	
	
}
