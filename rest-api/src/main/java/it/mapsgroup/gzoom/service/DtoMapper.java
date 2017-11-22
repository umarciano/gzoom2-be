package it.mapsgroup.gzoom.service;

import it.mapsgroup.gzoom.model.Timesheet;
import it.mapsgroup.gzoom.model.User;
import it.mapsgroup.gzoom.querydsl.dto.TimesheetEx;
import it.memelabs.smartnebula.commons.DateUtil;
import org.joda.time.format.DateTimeFormat;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

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

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

        if (from.getFromDate() != null) {
            String fromDate = from.getFromDate().toLocalDate().format(formatter);
            to.setFromDateAsString(fromDate);
            to.setFromDate(from.getFromDate().toLocalDate());
        }
        if (from.getThruDate() != null) {
            String thruDate = from.getThruDate().toLocalDate().format(formatter);
            to.setThruDateAsString(thruDate);
            to.setThruDate(from.getThruDate().toLocalDate());
        }
        to.setPartyId(from.getPartyId());
        to.setTimesheetId(from.getTimesheetId());
        to.setActualHours(from.getActualHours());
        to.setContractHours(from.getContractHours());
        to.setPartyName(from.getParty().getPartyName());
        return to;
    }

}
