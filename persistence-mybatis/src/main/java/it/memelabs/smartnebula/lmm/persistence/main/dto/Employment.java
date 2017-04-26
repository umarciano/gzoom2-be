package it.memelabs.smartnebula.lmm.persistence.main.dto;

import it.memelabs.smartnebula.lmm.persistence.main.enumeration.EntityStateTag;

import java.util.Date;

/**
 * @author Andrea Fossi.
 */
public interface Employment {
   EntityState getState();

   Company getCompany();

   Date getStartDate();

   Date getEndDate();

   Long getStateId();

   EntityStateTag getStateTag();
}

