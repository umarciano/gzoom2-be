package it.memelabs.smartnebula.lmm.persistence.main.dto;

public class WbsEx extends Wbs {
	
	private JobOrder jobOrder;
	private ConstructionSite constructionSite;
	
	public JobOrder getJobOrder() {
		return jobOrder;
	}
	public void setJobOrder(JobOrder jobOrder) {
		this.jobOrder = jobOrder;
	}
    public ConstructionSite getConstructionSite() {
        return constructionSite;
    }
    public void setConstructionSite(ConstructionSite constructionSite) {
        this.constructionSite = constructionSite;
    }
}
