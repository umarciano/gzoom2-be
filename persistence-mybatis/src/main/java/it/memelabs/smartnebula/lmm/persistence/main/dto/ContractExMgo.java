package it.memelabs.smartnebula.lmm.persistence.main.dto;

public class ContractExMgo extends ContractEx {
	private CompanyEx company;
	private CompanyEx performingCompany;

	public CompanyEx getCompany() {
		return company;
	}

	public void setCompany(CompanyEx company) {
		this.company = company;
	}

	public CompanyEx getPerformingCompany() {
		return performingCompany;
	}

	public void setPerformingCompany(CompanyEx performingCompany) {
		this.performingCompany = performingCompany;
	}
}
