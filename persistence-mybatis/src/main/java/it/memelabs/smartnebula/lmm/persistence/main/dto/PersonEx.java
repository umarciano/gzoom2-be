package it.memelabs.smartnebula.lmm.persistence.main.dto;

import java.util.Date;

public class PersonEx extends Person {

	private PostalAddressEx address;
	private Long peCardNumber; 
	private String peBadgeNumber;
	private String peStateTag;
    private String peStateDescription;
	private String compDescription;
	private PostalAddressEx birthLocation;
	private String secondmentCompanyDescription;
    private Date assumptionDate;
    private Date dismissalDate;
    private Date startDate;
    private Date endDate;
    
    private Identifiable role;
    private Identifiable job;
    private String jobDescription;
    private String level;

    private String companyStateTag;
    private String companyStateDescription;

	public PostalAddressEx getAddress() {
		return address;
	}

	public void setAddress(PostalAddressEx address) {
		this.address = address;
	}

	public Long getPeCardNumber() {
		return peCardNumber;
	}

	public void setPeCardNumber(Long peCardNumber) {
		this.peCardNumber = peCardNumber;
	}

	public String getPeBadgeNumber() {
		return peBadgeNumber;
	}

	public void setPeBadgeNumber(String peBadgeNumber) {
		this.peBadgeNumber = peBadgeNumber;
	}

	public String getPeStateDescription() {
		return peStateDescription;
	}

	public void setPeStateDescription(String peStateDescription) {
		this.peStateDescription = peStateDescription;
	}

	public String getCompDescription() {
		return compDescription;
	}

	public void setCompDescription(String compDescription) {
		this.compDescription = compDescription;
	}

    public PostalAddressEx getBirthLocation() {
        return birthLocation;
    }

    public void setBirthLocation(PostalAddressEx birthLocation) {
        this.birthLocation = birthLocation;
    }

	public String getSecondmentCompanyDescription() {
		return secondmentCompanyDescription;
	}

	public void setSecondmentCompanyDescription(String secondmentCompanyDescription) {
		this.secondmentCompanyDescription = secondmentCompanyDescription;
	}

    public Date getAssumptionDate() {
        return assumptionDate;
    }

    public void setAssumptionDate(Date assumptionDate) {
        this.assumptionDate = assumptionDate;
    }

    public Date getDismissalDate() {
        return dismissalDate;
    }

    public void setDismissalDate(Date dismissalDate) {
        this.dismissalDate = dismissalDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Identifiable getRole() {
        return role;
    }

    public void setRole(Identifiable role) {
        this.role = role;
    }

    public Identifiable getJob() {
        return job;
    }

    public void setJob(Identifiable job) {
        this.job = job;
    }

    public String getJobDescription() {
        return jobDescription;
    }

    public void setJobDescription(String jobDescription) {
        this.jobDescription = jobDescription;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getPeStateTag() {
        return peStateTag;
    }

    public void setPeStateTag(String peStateTag) {
        this.peStateTag = peStateTag;
    }

    public String getCompanyStateTag() {
        return companyStateTag;
    }

    public void setCompanyStateTag(String companyStateTag) {
        this.companyStateTag = companyStateTag;
    }

    public String getCompanyStateDescription() {
        return companyStateDescription;
    }

    public void setCompanyStateDescription(String companyStateDescription) {
        this.companyStateDescription = companyStateDescription;
    }
}
