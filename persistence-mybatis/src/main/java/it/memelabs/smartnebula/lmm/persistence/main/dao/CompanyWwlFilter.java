package it.memelabs.smartnebula.lmm.persistence.main.dao;

import java.util.Date;

/**
 * @author Andrea Fossi.
 */
public class CompanyWwlFilter {
    private Long constructionSiteId;
    private Date wwlStartDate;
    private Date wwlEndDate;
    private Long ownerNodeId;

    public Long getConstructionSiteId() {
        return constructionSiteId;
    }

    public void setConstructionSiteId(Long constructionSiteId) {
        this.constructionSiteId = constructionSiteId;
    }

    public Long getOwnerNodeId() {
        return ownerNodeId;
    }

    public void setOwnerNodeId(Long ownerNodeId) {
        this.ownerNodeId = ownerNodeId;
    }

    public Date getWwlEndDate() {
        return wwlEndDate;
    }

    public void setWwlEndDate(Date wwlEndDate) {
        this.wwlEndDate = wwlEndDate;
    }

    public Date getWwlStartDate() {
        return wwlStartDate;
    }

    public void setWwlStartDate(Date wwlStartDate) {
        this.wwlStartDate = wwlStartDate;
    }
}
