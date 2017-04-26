package it.memelabs.smartnebula.lmm.persistence.main.dao;

public class WbsFilter extends BaseFilter {
	private String code;
	private Long jobOrderId;
	private Long constructionSiteId;
	private Long id;
	private String text;
	
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

    public Long getConstructionSiteId() {
        return constructionSiteId;
    }

    public void setConstructionSiteId(Long constructionSiteId) {
        this.constructionSiteId = constructionSiteId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}
