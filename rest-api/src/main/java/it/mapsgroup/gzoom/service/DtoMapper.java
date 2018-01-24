package it.mapsgroup.gzoom.service;

import it.mapsgroup.gzoom.model.TimeEntry;
import it.mapsgroup.gzoom.model.Timesheet;
import it.mapsgroup.gzoom.model.User;
import it.mapsgroup.gzoom.querydsl.dto.Activity;
import org.springframework.stereotype.Component;

/**
 * @author Fabio G. Strozzi
 */
@Component
public class DtoMapper {

    private static final String SEP = " ";

    /**
     * Simplified user mapper (copy basic field only)
     * for full copy {@link SecurityDtoMapper#copy(UserLogin, User)}
     *
     * @param userLogin
     * @param user
     * @return
     */

    public Timesheet copy(it.mapsgroup.gzoom.querydsl.dto.TimesheetEx from, Timesheet to) {

        if (from.getFromDate() != null)
            to.setFromDate(from.getFromDate().toLocalDate());
        if (from.getThruDate() != null)
            to.setThruDate(from.getThruDate().toLocalDate());
        to.setPartyId(from.getPartyId());
        to.setTimesheetId(from.getTimesheetId());
        to.setActualHours(from.getActualHours());
        to.setContractHours(from.getContractHours());
        to.setPartyName(from.getParty().getPartyName());
        return to;
    }

    public TimeEntry copy(it.mapsgroup.gzoom.querydsl.dto.TimeEntry from, TimeEntry to) {

        if (from.getFromDate() != null)
            to.setFromDate(from.getFromDate().toLocalDate());
        if (from.getThruDate() != null)
            to.setThruDate(from.getThruDate().toLocalDate());
        to.setTimesheetId(from.getTimesheetId());
        to.setWorkEffortId(from.getWorkEffortId());
        to.setPercentage(from.getPercentage());

        return to;
    }

    public TimeEntry copy(Activity from, TimeEntry to) {

        if (from.getFromDate() != null)
            to.setFromDate(from.getFromDate().toLocalDate());
        if (from.getThruDate() != null)
            to.setThruDate(from.getThruDate().toLocalDate());
        to.setPartyId(from.getPartyId());
        to.setTimesheetId(from.getTimesheetId());
        to.setWorkEffortId(from.getWorkEffortId());
        to.setTimeEntryId(from.getTimeEntryId());
        to.setAttivitaLiv1(from.getWorkEffort1().getWorkEffortName());
        to.setAttivitaLiv2(from.getWorkEffort2().getWorkEffortName());
        to.setAttivitaLiv3(from.getWorkEffort3().getWorkEffortName());
        return to;
    }

}
