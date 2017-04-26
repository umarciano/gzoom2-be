package it.memelabs.smartnebula.lmm.persistence.main.dto;

public class ConstructionSiteLogEx extends ConstructionSiteLog {
	
	private ConstructionSite constructionSite;
	private EntityState state;
    
    public ConstructionSite getConstructionSite() {
        return constructionSite;
    }
    public void setConstructionSite(ConstructionSite constructionSite) {
        this.constructionSite = constructionSite;
    }
    public EntityState getState() {
        return state;
    }
    public void setState(EntityState state) {
        this.state = state;
    }
}
