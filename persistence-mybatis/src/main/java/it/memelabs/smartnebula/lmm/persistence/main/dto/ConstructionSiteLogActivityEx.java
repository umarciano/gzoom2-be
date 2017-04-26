package it.memelabs.smartnebula.lmm.persistence.main.dto;

public class ConstructionSiteLogActivityEx extends ConstructionSiteLogActivity {
    private Company company;
    private Wbs wbs;
    private ConstructionSiteLogEx constructionSiteLog;

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Wbs getWbs() {
        return wbs;
    }

    public void setWbs(Wbs wbs) {
        this.wbs = wbs;
    }

    public ConstructionSiteLogEx getConstructionSiteLog() {
        return constructionSiteLog;
    }

    public void setConstructionSiteLog(ConstructionSiteLogEx constructionSiteLog) {
        this.constructionSiteLog = constructionSiteLog;
    }
}