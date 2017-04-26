package it.memelabs.smartnebula.lmm.persistence.main.dto;

public class CompanyCompositionEx extends CompanyComposition {

	private String businessName;
	private String taxIdentificationNumber;
	private String vatNumber;
	private String companyState;
	private String companyCategory;
	private String companyClassification;

	public String getBusinessName() {
		return businessName;
	}

	public void setBusinessName(String businessName) {
		this.businessName = businessName;
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

	public String getCompanyState() {
		return companyState;
	}

	public void setCompanyState(String companyState) {
		this.companyState = companyState;
	}

	public String getCompanyCategory() {
		return companyCategory;
	}

	public void setCompanyCategory(String companyCategory) {
		this.companyCategory = companyCategory;
	}

	public String getCompanyClassification() {
		return companyClassification;
	}

	public void setCompanyClassification(String companyClassification) {
		this.companyClassification = companyClassification;
	}
}
