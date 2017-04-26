package it.memelabs.smartnebula.lmm.persistence.main.dao;

public class ConstructionSiteFilter extends BaseFilter {
	private String freeText;
	private String code;
	private Long jobOrder;
	private Long assignedCompany;
	private Long constructionSiteId;

	public String getFreeText() {
		return freeText;
	}

	public void setFreeText(String freeText) {
		this.freeText = freeText;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Long getJobOrder() {
		return jobOrder;
	}

	public void setJobOrder(Long jobOrder) {
		this.jobOrder = jobOrder;
	}

	public Long getAssignedCompany() {
		return assignedCompany;
	}

	public void setAssignedCompany(Long assignedCompany) {
		this.assignedCompany = assignedCompany;
	}

	public Long getConstructionSiteId() {
		return constructionSiteId;
	}

	public void setConstructionSiteId(Long constructionSiteId) {
		this.constructionSiteId = constructionSiteId;
	}
}
