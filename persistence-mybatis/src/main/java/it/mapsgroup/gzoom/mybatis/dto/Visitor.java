package it.mapsgroup.gzoom.mybatis.dto;

import java.time.LocalDateTime;

public class Visitor {

	private String visitorId;
	private String userLoginId;
	private String parentRoleCode;
	private String firstName;
	private String lastName;
	private LocalDateTime minFromDate;
	private LocalDateTime maxThruDate;

	public String getParentRoleCode() {
		return parentRoleCode;
	}

	public void setParentRoleCode(String parentRoleCode) {
		this.parentRoleCode = parentRoleCode;
	}

	public String getVisitorId() {
		return visitorId;
	}

	public void setVisitorId(String visitorId) {
		this.visitorId = visitorId;
	}

	public String getUserLoginId() {
		return userLoginId;
	}

	public void setUserLoginId(String userLoginId) {
		this.userLoginId = userLoginId;
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

	public LocalDateTime getMaxThruDate() {
		return  maxThruDate;
	}

	public void setMaxThruDate(LocalDateTime  maxThruDate) {
		this. maxThruDate =  maxThruDate;
	}

	public LocalDateTime getMinFromDate() {
		return minFromDate;
	}

	public void setMinFromDate(LocalDateTime minFromDate) {
		this.minFromDate = minFromDate;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Visitor [visitorId=" + visitorId + ", userLoginId=" + userLoginId + ", firstName=" + firstName + ", lastName=" + lastName + ", minFromDate=" + minFromDate
				+ ",  maxThruDate=" +  maxThruDate + "]";
	}
	
}
