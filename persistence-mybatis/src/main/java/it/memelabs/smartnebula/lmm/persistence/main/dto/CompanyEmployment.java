package it.memelabs.smartnebula.lmm.persistence.main.dto;

public class CompanyEmployment {

    private long companyId;
    private long emplId;

    public long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(long companyId) {
        this.companyId = companyId;
    }

    public long getEmplId() {
        return emplId;
    }

    public void setEmplId(long emplId) {
        this.emplId = emplId;
    }
}
