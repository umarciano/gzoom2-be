package it.memelabs.smartnebula.lmm.persistence.main.dto;

/**
 * @author Andrea Fossi.
 */
public class WorkLogTuple {
    private Long employmentId;
    private Long companyId;

    public Long getEmploymentId() {
        return employmentId;
    }

    public void setEmploymentId(Long employmentId) {
        this.employmentId = employmentId;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }
}
