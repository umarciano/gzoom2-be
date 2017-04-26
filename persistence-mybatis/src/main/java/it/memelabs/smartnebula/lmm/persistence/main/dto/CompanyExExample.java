package it.memelabs.smartnebula.lmm.persistence.main.dto;

public class CompanyExExample extends CompanyExample {

    private String filterText;
    private boolean filterTaxOrVatNumber;
    private String taxIdentificationNumber;
    private String vatNumber;




    public String getFilterText() {
        return filterText;
    }

    public void setFilterText(String filterText) {
        this.filterText = filterText;
    }

    public boolean isFilterTaxOrVatNumber() {
        return filterTaxOrVatNumber;
    }

    public void setFilterTaxOrVatNumber(boolean filterTaxOrVatNumber) {
        this.filterTaxOrVatNumber = filterTaxOrVatNumber;
    }

    public String getTaxIdentificationNumber() {
        return taxIdentificationNumber;
    }

    public void setTaxIdentificationNumber(String taxIdentificationNumber) {
        this.taxIdentificationNumber = taxIdentificationNumber;
    }

    public String getVatNumber() {
        return vatNumber;
    }

    public void setVatNumber(String vatNumber) {
        this.vatNumber = vatNumber;
    }


}
