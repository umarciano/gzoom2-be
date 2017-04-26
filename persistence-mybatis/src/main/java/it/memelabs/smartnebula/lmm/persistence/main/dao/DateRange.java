package it.memelabs.smartnebula.lmm.persistence.main.dao;

import java.util.Date;

/**
 * @author Andrea Fossi.
 */
public class DateRange {
    private final Date from;
    private final Date to;

    public DateRange(Date from, Date to) {
        this.from = from;
        this.to = to;
    }

    public Date getFrom() {
        return from;
    }

    public Date getTo() {
        return to;
    }

}
