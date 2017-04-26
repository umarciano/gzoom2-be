package it.memelabs.smartnebula.lmm.persistence.main.dao;

import java.util.Date;

/**
 * Simple filter container used to generate {@link it.memelabs.smartnebula.lmm.persistence.main.dto.WorkLogExample}
 */
public class WorkLogFilter extends BaseFilter {

    private Long idToExclude;
    private Long constructionSiteId;
    private Boolean constructionSiteNull;
    private Date fromDate;
    private Date toDate;

    public Long getIdToExclude() {
        return idToExclude;
    }

    public void setIdToExclude(Long idToExclude) {
        this.idToExclude = idToExclude;
    }

    public Long getConstructionSiteId() {
        return constructionSiteId;
    }

    public void setConstructionSiteId(Long constructionSiteId) {
        this.constructionSiteId = constructionSiteId;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public Boolean getConstructionSiteNull() {
        return constructionSiteNull;
    }

    public void setConstructionSiteNull(Boolean constructionSiteNull) {
        this.constructionSiteNull = constructionSiteNull;
    }

}
