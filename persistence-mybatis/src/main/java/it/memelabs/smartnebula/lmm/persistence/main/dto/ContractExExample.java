package it.memelabs.smartnebula.lmm.persistence.main.dto;

public class ContractExExample extends ContractExample {

    private String filterText;
    private Long performingCompanyId;
    private String cup;

    public String getFilterText() {
        return filterText;
    }

    public void setFilterText(String filterText) {
        this.filterText = filterText;
    }

    public Long getPerformingCompanyId() {
        return performingCompanyId;
    }

    public void setPerformingCompanyId(Long performingCompanyId) {
        this.performingCompanyId = performingCompanyId;
    }

    public String getCup() {
        return cup;
    }

    public void setCup(String cup) {
        this.cup = cup;
    }
}
