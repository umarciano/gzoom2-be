package it.memelabs.smartnebula.lmm.persistence.main.dao;

import java.util.Date;

public class ConstructionSiteLogFilter extends BaseFilter {
	
    private Long stateId;
    private Long constructionSiteId;
	private Long id;
	private Date logDate;
	
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

    public Long getStateId() {
        return stateId;
    }

    public void setStateId(Long stateId) {
        this.stateId = stateId;
    }

    public Date getLogDate() {
        return logDate;
    }

    public void setLogDate(Date logDate) {
        this.logDate = logDate;
    }
}
