package it.memelabs.smartnebula.lmm.persistence.main.dto;

import java.util.ArrayList;
import java.util.List;

public class LotEx extends Lot {
    private JobOrder jobOrder;

    private Company assignedCompany;

    private Person worksManager;
    private Person dtl;

    private List<PostalAddressEx> locations;

    public LotEx() {
        this.locations=new ArrayList<>();
    }

    public JobOrder getJobOrder() {
        return jobOrder;
    }

    public void setJobOrder(JobOrder jobOrder) {
        this.jobOrder = jobOrder;
    }

    public Company getAssignedCompany() {
        return assignedCompany;
    }

    public void setAssignedCompany(Company assignedCompany) {
        this.assignedCompany = assignedCompany;
    }

    public Person getWorksManager() {
        return worksManager;
    }

    public void setWorksManager(Person worksManager) {
        this.worksManager = worksManager;
    }

    public Person getDtl() {
        return dtl;
    }

    public void setDtl(Person dtl) {
        this.dtl = dtl;
    }

    public List<PostalAddressEx> getLocations() {
        return locations;
    }

    public void setLocations(List<PostalAddressEx> locations) {
        this.locations = locations;
    }
}
