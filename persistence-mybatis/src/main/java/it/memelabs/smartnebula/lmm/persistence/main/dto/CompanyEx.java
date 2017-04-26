package it.memelabs.smartnebula.lmm.persistence.main.dto;

/**
 * @author Andrea Fossi.
 */
public class CompanyEx extends Company {

    private String classificationDesc;
    private String categoryDesc;
    private String stateDesc;
    private PostalAddressEx address;
    private Company consortiumMembership;

    public String getClassificationDesc() {
        return classificationDesc;
    }

    public void setClassificationDesc(String classificationDesc) {
        this.classificationDesc = classificationDesc;
    }

    public String getCategoryDesc() {
        return categoryDesc;
    }

    public void setCategoryDesc(String categoryDesc) {
        this.categoryDesc = categoryDesc;
    }

    public String getStateDesc() {
        return stateDesc;
    }

    public void setStateDesc(String stateDesc) {
        this.stateDesc = stateDesc;
    }

    public PostalAddressEx getAddress() {
        return address;
    }

    public void setAddress(PostalAddressEx address) {
        this.address = address;
    }

    public Company getConsortiumMembership() {
        return consortiumMembership;
    }

    public void setConsortiumMembership(Company consortiumMembership) {
        this.consortiumMembership = consortiumMembership;
    }
}
