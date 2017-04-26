package it.memelabs.smartnebula.lmm.persistence.main.dto;

import java.util.List;

public class ConstructionSiteEx extends ConstructionSite {
	
	private JobOrder jobOrder;
	private Lot lot;
	private Person weeklyReferent;
	private Person companyManager;
	private Person cse;
	private Company assignedCompany;
	private Company worksCompany;
	private PostalAddressEx address;
	private List<PostalAddressEx> locations;
	
	public JobOrder getJobOrder() {
		return jobOrder;
	}
	public void setJobOrder(JobOrder jobOrder) {
		this.jobOrder = jobOrder;
	}
	public Lot getLot() {
		return lot;
	}
	public void setLot(Lot lot) {
		this.lot = lot;
	}
	public Person getWeeklyReferent() {
		return weeklyReferent;
	}
	public void setWeeklyReferent(Person weeklyReferent) {
		this.weeklyReferent = weeklyReferent;
	}
	public Person getCompanyManager() {
		return companyManager;
	}
	public void setCompanyManager(Person companyManager) {
		this.companyManager = companyManager;
	}
	public Person getCse() {
		return cse;
	}
	public void setCse(Person cse) {
		this.cse = cse;
	}
	public Company getAssignedCompany() {
		return assignedCompany;
	}
	public void setAssignedCompany(Company assignedCompany) {
		this.assignedCompany = assignedCompany;
	}
	public PostalAddressEx getAddress() {
		return address;
	}
	public void setAddress(PostalAddressEx address) {
		this.address = address;
	}
	public List<PostalAddressEx> getLocations() {
		return locations;
	}
	public void setLocations(List<PostalAddressEx> locations) {
		this.locations = locations;
	}
    public Company getWorksCompany() {
        return worksCompany;
    }
    public void setWorksCompany(Company worksCompany) {
        this.worksCompany = worksCompany;
    }
	

}
