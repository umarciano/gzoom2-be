package it.memelabs.smartnebula.lmm.persistence.main.dao;

public class JobOrderFilter extends BaseFilter {

	private String code;
	private String freeText;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getFreeText() {
		return freeText;
	}

	public void setFreeText(String freeText) {
		this.freeText = freeText;
	}
}
