package it.mapsgroup.gzoom.querydsl;

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
