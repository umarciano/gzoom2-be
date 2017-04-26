package it.memelabs.smartnebula.lmm.persistence.main.dto;

public class EquipmentExExample extends EquipmentExample {

	private Long filterCompanyId;
	private Long filterStatusId;
	private String filterType;
	private String filterBadgeNumber;
	private Long filterTimeCardNumber;
	private Long filterCompanyStatusId;


	public Long getFilterCompanyId() {
		return filterCompanyId;
	}

	public void setFilterCompanyId(Long filterCompanyId) {
		this.filterCompanyId = filterCompanyId;
	}

	public void setFilterStatusId(Long filterStatusId) {
		this.filterStatusId = filterStatusId;
	}

	public Long getFilterStatusId() {
		return filterStatusId;
	}

	public void setFilterType(String filterType) {
		this.filterType = filterType;		
	}

	public String getFilterType() {
		return filterType;
	}

	public void setFilterBadgeNumber(String filterBadgeNumber) {
		this.filterBadgeNumber = filterBadgeNumber;		
	}

	public String getFilterBadgeNumber() {
		return filterBadgeNumber;
	}

	public void setFilterTimeCardNumber(Long filterTimeCardNumber) {
		this.filterTimeCardNumber = filterTimeCardNumber;		
	}

	public Long getFilterTimeCardNumber() {
		return filterTimeCardNumber;
	}

	public Long getFilterCompanyStatusId() {
		return filterCompanyStatusId;
	}

	public void setFilterCompanyStatusId(Long filterCompanyStatusId) {
		this.filterCompanyStatusId = filterCompanyStatusId;
	}
}