package it.memelabs.smartnebula.lmm.persistence.main.dao;

public class LotFilter extends BaseFilter {

	private String code;
	private Long jobOrderId;
	private Long companyAssignedId;
	private String filterText;
	private Long lotId;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Long getJobOrderId() {
		return jobOrderId;
	}

	public void setJobOrderId(Long jobOrderId) {
		this.jobOrderId = jobOrderId;
	}

	public Long getCompanyAssignedId() {
		return companyAssignedId;
	}

	public void setCompanyAssignedId(Long companyAssignedId) {
		this.companyAssignedId = companyAssignedId;
	}

	public String getFilterText() {
		return filterText;
	}

	public void setFilterText(String filterText) {
		this.filterText = filterText;
	}

	public Long getLotId() {
		return lotId;
	}

	public void setLotId(Long lotId) {
		this.lotId = lotId;
	}
}
