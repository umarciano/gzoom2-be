package it.memelabs.smartnebula.lmm.persistence.main.dao;

import java.util.Date;

/**
 * @author Andrea Fossi.
 */
public class PersonEmploymentFilter extends BaseFilter {
    private Date filterDate;
    private String filterText;

    public Date getFilterDate() {
        return filterDate;
    }

    public void setFilterDate(Date filterDate) {
        this.filterDate = filterDate;
    }

    public String getFilterText() {
        return filterText;
    }

    public void setFilterText(String filterText) {
        this.filterText = filterText;
    }
}
