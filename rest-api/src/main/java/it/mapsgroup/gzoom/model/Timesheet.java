package it.mapsgroup.gzoom.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Timesheet extends Identifiable {
    private LocalDate fromDate;
    private LocalDate thruDate;
    private String partyId;
    private String timesheetId;
    private float actualHours;
    private float contractHours;

    public LocalDate getFromDate() {
        return fromDate;
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    public LocalDate getThruDate() {
        return thruDate;
    }

    public void setThruDate(LocalDate thruDate) {
        this.thruDate = thruDate;
    }

    public String getPartyId() {
        return partyId;
    }

    public void setPartyId(String partyId) {
        this.partyId = partyId;
    }

    public String getTimesheetId() {
        return timesheetId;
    }

    public void setTimesheetId(String timesheetId) {
        this.timesheetId = timesheetId;
    }

    public float getActualHours() {
        return actualHours;
    }

    public void setActualHours(float actualHours) {
        this.actualHours = actualHours;
    }

    public float getContractHours() {
        return contractHours;
    }

    public void setContractHours(float contractHours) {
        this.contractHours = contractHours;
    }
}
