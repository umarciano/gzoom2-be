package it.memelabs.smartnebula.lmm.persistence.main.dao;

import java.util.Date;

import it.memelabs.smartnebula.commons.DateUtil;

public class WeeklyWorkLogFilter extends BaseFilter {

    private Long idToExclude;
    private Long constructionSiteId;
    private Date fromDate;
    private Date fromWeekDate;
    private Date toDate;
    private Date toWeekDate;

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
        this.fromWeekDate = DateUtil.getFirstDateOfCalendarWeek(this.fromDate);
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
        this.toWeekDate = DateUtil.getFirstDateOfCalendarWeek(this.toDate);
    }

    public Date getFromWeekDate() {
        return fromWeekDate;
    }

    public Date getToWeekDate() {
        return toWeekDate;
    }
}
