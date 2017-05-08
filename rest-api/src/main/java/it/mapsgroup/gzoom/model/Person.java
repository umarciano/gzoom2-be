package it.mapsgroup.gzoom.model;

import java.util.Date;

public class Person extends Identity {

    private String taxIdentificationNumber;
    private String firstName;
    private String lastName;
    private PostalAddress address;
    private Date birthDate;
    private PostalAddress birthLocation;
    private String gender;
    private Boolean residencyPermit;
    private String note;
    //personEmployment.cardNumber
    private Long peCardNumber;
    //personEmployment.badge
    private String peBadge;
    //personEmployment.peStateTag
    private String peStateTag;
    private String peStateDescription;
    private String compDescription;
    private String secondmentCompanyDescription;
    private String role;
    private String job;
    private String jobDescription;
    private String level;
    private Date assumptionDate;
    private Date dismissalDate;
    private Date startDate;
    private Date endDate;
    private String companyStateTag;
    private String companyStateDescription;

    public String getTaxIdentificationNumber() {
        return taxIdentificationNumber;
    }

    public void setTaxIdentificationNumber(String taxIdentificationNumber) {
        this.taxIdentificationNumber = taxIdentificationNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public PostalAddress getAddress() {
        return address;
    }

    public void setAddress(PostalAddress address) {
        this.address = address;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Boolean getResidencyPermit() {
        return residencyPermit;
    }

    public void setResidencyPermit(Boolean residencyPermit) {
        this.residencyPermit = residencyPermit;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setPeCardNumber(Long peCardNumber) {
        this.peCardNumber = peCardNumber;
    }

    public Long getPeCardNumber() {
        return peCardNumber;
    }

    public void setPeBadge(String peBadgeNumber) {
        this.peBadge = peBadgeNumber;
    }

    public String getPeBadge() {
        return peBadge;
    }

    public void setPeStateDescription(String peStateDescription) {
        this.peStateDescription = peStateDescription;
    }

    public void setCompDescription(String compDescription) {
        this.compDescription = compDescription;
    }

    public String getPeStateDescription() {
        return peStateDescription;
    }

    public String getCompDescription() {
        return compDescription;
    }

    public PostalAddress getBirthLocation() {
        return birthLocation;
    }

    public void setBirthLocation(PostalAddress birthLocation) {
        this.birthLocation = birthLocation;
    }

    public void setSecondmentCompanyDescription(String secondmentCompanyDescription) {
        this.secondmentCompanyDescription = secondmentCompanyDescription;
    }

    public String getSecondmentCompanyDescription() {
        return secondmentCompanyDescription;
    }

    public Date getDismissalDate() {
        return dismissalDate;
    }

    public void setDismissalDate(Date dismissalDate) {
        this.dismissalDate = dismissalDate;
    }

    public Date getAssumptionDate() {
        return assumptionDate;
    }

    public void setAssumptionDate(Date assumptionDate) {
        this.assumptionDate = assumptionDate;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
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
