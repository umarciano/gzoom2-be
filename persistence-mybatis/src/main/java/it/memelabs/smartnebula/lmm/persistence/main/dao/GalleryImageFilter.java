package it.memelabs.smartnebula.lmm.persistence.main.dao;

import java.util.Date;

/**
 * @author Andrea Fossi.
 */
public class GalleryImageFilter extends BaseFilter {
    private Date from;
    private Date to;
    private Long csId;
    private Long wbsId;
    private Long companyId;
    private String text;

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

    public Long getCsId() {
        return csId;
    }

    public void setCsId(Long csId) {
        this.csId = csId;
    }

    public Long getWbsId() {
        return wbsId;
    }

    public void setWbsId(Long wbsId) {
        this.wbsId = wbsId;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
