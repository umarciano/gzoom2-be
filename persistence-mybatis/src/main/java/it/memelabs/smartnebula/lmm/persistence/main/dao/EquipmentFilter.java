package it.memelabs.smartnebula.lmm.persistence.main.dao;

public class EquipmentFilter extends BaseFilter {

    private String registrationNumber;
    private String brand;
    private String model;
    private Long companyId;
    private Long filterStatusId;
    private String filterType;
    private Long filterTimeCardNumber;
    private String filterBadgeNumber;
    private Long filterCompanyStatusId;

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public void setFilterStatusId(Long filterStatusId) {
        this.filterStatusId = filterStatusId;
    }

    public Long getFilterStatusId() {
        return filterStatusId;
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

    public Long getFilterCompanyStatusId() {
        return filterCompanyStatusId;
    }

    public void setFilterCompanyStatusId(Long filterCompanyStatusId) {
        this.filterCompanyStatusId = filterCompanyStatusId;
    }
}
