package it.memelabs.smartnebula.lmm.persistence.main.dto;

import java.util.Date;

/**
 * @author Andrea Fossi.
 */
public interface AbstractIdentity {

	public Long getId();

	public Date getModifiedStamp();

	public Date getCreatedStamp();

	public Long getCreatedByUserId();

	public Long getModifiedByUserId();
}
