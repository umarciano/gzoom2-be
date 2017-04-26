package it.memelabs.smartnebula.lmm.persistence.main.dto;

import java.util.Date;

/**
 * @author Andrea Fossi.
 */
public class EquipmentEx extends Equipment {

	private String equipmentCategoryDesc;
	private String equipmentTypeDesc;
	private Long peCardNumber; 
	private String peBadgeNumber;
	private String eeStateDescription;
	private String eeStateTag;
    private String compDescription;
    private Date startDate;
    private Date endDate;

	private String companyStateDescription;
	private String companyStateTag;

	public String getEquipmentCategoryDesc() {
		return equipmentCategoryDesc;
	}

	public void setEquipmentCategoryDesc(String equipmentCategoryDesc) {
		this.equipmentCategoryDesc = equipmentCategoryDesc;
	}

	public String getEquipmentTypeDesc() {
		return equipmentTypeDesc;
	}

	public void setEquipmentTypeDesc(String equipmentTypeDesc) {
		this.equipmentTypeDesc = equipmentTypeDesc;
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

	public String getEeStateDescription() {
		return eeStateDescription;
	}

	public void setEeStateDescription(String eeStateDescription) {
		this.eeStateDescription = eeStateDescription;
	}

	public String getCompDescription() {
		return compDescription;
	}

	public void setCompDescription(String compDescription) {
		this.compDescription = compDescription;
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

    public String getEeStateTag() {
        return eeStateTag;
    }

    public void setEeStateTag(String eeStateTag) {
        this.eeStateTag = eeStateTag;
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
