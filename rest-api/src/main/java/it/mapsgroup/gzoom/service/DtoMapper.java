package it.mapsgroup.gzoom.service;

import it.mapsgroup.gzoom.model.Timesheet;
import it.mapsgroup.gzoom.model.User;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

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

    public Timesheet copy(it.mapsgroup.gzoom.querydsl.dto.Timesheet from, Timesheet to) {
        if (from.getFromDate() != null)
            to.setFromDate(from.getFromDate().toLocalDate());
        if (from.getThruDate() != null)
            to.setThruDate(from.getThruDate().toLocalDate());
        to.setPartyId(from.getPartyId());
        to.setTimesheetId(from.getTimesheetId());
        //to.setActualHours(from.getActualHours());
        //to.setContractHours(from.getContractHours());
        return to;
    }

}
