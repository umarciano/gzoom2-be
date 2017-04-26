package it.memelabs.smartnebula.lmm.persistence.main.dao;

import it.memelabs.smartnebula.lmm.persistence.main.dto.UserLogin;

import java.util.Date;

/**
 * @author Andrea Fossi.
 */
public class AccidentFilter extends BaseFilter {
    /**
     * used in {@link AccidentDao#findOverlap(AccidentFilter, UserLogin)}
     */
    private Date since;
    /**
     * used in {@link AccidentDao#findOverlap(AccidentFilter, UserLogin)}
     */
    private Date until;

    private Date from;
    private Date to;
    private Long personId;
    private Long constructionSiteId;
    private Long id;

    public Date getSince() {
        return since;
    }

    public void setSince(Date since) {
        this.since = since;
    }

    public Date getUntil() {
        return until;
    }

    public void setUntil(Date until) {
        this.until = until;
    }

    public Long getPersonId() {
        return personId;
    }

    public void setPersonId(Long personId) {
        this.personId = personId;
    }

    public Date getFrom() {
        return from;
    }

    public void setFrom(Date from) {
        this.from = from;
    }

    public Date getTo() {
        return to;
    }

    public void setTo(Date to) {
        this.to = to;
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
}
