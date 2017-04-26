package it.memelabs.smartnebula.lmm.persistence.main.dto;

import java.util.Date;

public class EquipmentEmploymentExExample extends EquipmentEmploymentExample {

	private Date employmentStartDate;
	private Date employmentEndDate;

	public Date getEmploymentStartDate() {
		return employmentStartDate;
	}

	public void setEmploymentStartDate(Date employmentStartDate) {
		this.employmentStartDate = employmentStartDate;
	}

	public Date getEmploymentEndDate() {
		return employmentEndDate;
	}

	public void setEmploymentEndDate(Date employmentEndDate) {
		this.employmentEndDate = employmentEndDate;
	}

}
