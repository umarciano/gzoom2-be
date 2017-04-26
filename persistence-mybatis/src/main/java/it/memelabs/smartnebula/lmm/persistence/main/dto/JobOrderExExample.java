package it.memelabs.smartnebula.lmm.persistence.main.dto;


public class JobOrderExExample extends JobOrderExample {
	
	private String filterText; 
	
	public void setFilterText(String filterText) {
		this.filterText = "%"+filterText+"%";
	}

	public String getFilterText() {
		return filterText;
	}
	
	
}
