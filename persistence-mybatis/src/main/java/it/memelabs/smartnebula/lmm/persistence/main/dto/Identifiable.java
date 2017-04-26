package it.memelabs.smartnebula.lmm.persistence.main.dto;

public class Identifiable extends Identity implements AbstractIdentifiable {

	private String description;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
