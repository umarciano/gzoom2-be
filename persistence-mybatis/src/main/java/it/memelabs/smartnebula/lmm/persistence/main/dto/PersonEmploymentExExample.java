package it.memelabs.smartnebula.lmm.persistence.main.dto;

import java.util.Date;

public class PersonEmploymentExExample extends PersonEmploymentExample {

	private Date employmentStartDate;
	private Date employmentEndDate;

	public void setFilterEmploymentStartDate(Date employmentStartDate) {
		this.employmentStartDate = employmentStartDate;
	}

	public void setFilterEmploymentEndDate(Date employmentEndDate) {
		this.employmentEndDate = employmentEndDate;
	}

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
