package it.memelabs.smartnebula.lmm.persistence.main.dto;

public class PersonExExample extends PersonExample {

    private String filterText;
    private Long filterCompanyId;
    private Long filterPeStatusId;
    private String filterType;
    private Long filterTimeCardNumber;
    private String filterBadgeNumber;
    private Boolean filterSecondments;
    private Long filterCompanyStatusId;

    public String getFilterText() {
        return filterText;
    }

    public void setFilterText(String filterText) {
        this.filterText = filterText;
    }

    public void setFilterCompanyId(Long filterCompanyId) {
        this.filterCompanyId = filterCompanyId;
    }

    public Long getFilterCompanyId() {
        return filterCompanyId;
    }

    public Long getFilterPeStatusId() {
        return filterPeStatusId;
    }

    public void setFilterPeStatusId(Long filterPeStatusId) {
        this.filterPeStatusId = filterPeStatusId;
    }

    public void setFilterType(String filterType) {
        this.filterType = filterType;
    }

    public String getFilterType() {
        return filterType;
    }

    public void setFilterTimeCardNumber(Long filterTimeCardNumber) {
        this.filterTimeCardNumber = filterTimeCardNumber;
    }

    public Long getFilterTimeCardNumber() {
        return filterTimeCardNumber;
    }

    public void setFilterBadgeNumber(String filterBadgeNumber) {
        this.filterBadgeNumber = filterBadgeNumber;
    }

    public String getFilterBadgeNumber() {
        return filterBadgeNumber;
    }

    public void setFilterSecondments(Boolean filterSecondments) {
        this.filterSecondments = filterSecondments;
    }

    public Boolean getFilterSecondments() {
        return filterSecondments;
    }

    public Long getFilterCompanyStatusId() {
        return filterCompanyStatusId;
    }

    public void setFilterCompanyStatusId(Long filterCompanyStatusId) {
        this.filterCompanyStatusId = filterCompanyStatusId;
    }
}
