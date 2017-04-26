package it.memelabs.smartnebula.lmm.persistence.main.dto;

import java.util.Date;

public class Identity implements AbstractIdentity {

	private Long id;
	private Date modifiedStamp;
	private Date createdStamp;
	private Long createdByUserId;
	private Long modifiedByUserId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getModifiedStamp() {
		return modifiedStamp;
	}

	public void setModifiedStamp(Date modifiedStamp) {
		this.modifiedStamp = modifiedStamp;
	}

	public Date getCreatedStamp() {
		return createdStamp;
	}

	public void setCreatedStamp(Date createdStamp) {
		this.createdStamp = createdStamp;
	}

	public Long getCreatedByUserId() {
		return createdByUserId;
	}

	public void setCreatedByUserId(Long createdByUserId) {
		this.createdByUserId = createdByUserId;
	}

	public Long getModifiedByUserId() {
		return modifiedByUserId;
	}

	public void setModifiedByUserId(Long modifiedByUserId) {
		this.modifiedByUserId = modifiedByUserId;
	}
}
