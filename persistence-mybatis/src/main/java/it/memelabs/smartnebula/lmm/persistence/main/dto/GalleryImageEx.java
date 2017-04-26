package it.memelabs.smartnebula.lmm.persistence.main.dto;

public class GalleryImageEx extends GalleryImage implements AbstractIdentifiable, AbstractIdentity {
    private Company company;
    private ConstructionSite constructionSite;
    private Wbs wbs;

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public ConstructionSite getConstructionSite() {
        return constructionSite;
    }

    public void setConstructionSite(ConstructionSite constructionSite) {
        this.constructionSite = constructionSite;
    }

    public Wbs getWbs() {
        return wbs;
    }

    public void setWbs(Wbs wbs) {
        this.wbs = wbs;
    }
}