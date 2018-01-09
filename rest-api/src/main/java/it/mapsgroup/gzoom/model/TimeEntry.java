package it.mapsgroup.gzoom.model;

import it.mapsgroup.gzoom.querydsl.dto.WorkEffort;

import java.math.BigDecimal;
import java.time.LocalDate;

public class TimeEntry extends Identifiable {
    private LocalDate fromDate;
    private LocalDate thruDate;
    private String partyId;
    private String timesheetId;
    private String workEffortId;
    private String comments;
    private String timeEntryId;
    private String rateTypeId;
    private BigDecimal percentage;
    private String effortUomId;
    private WorkEffort workEffort;
    private String attivitaLiv1;
    private String attivitaLiv2;
    private String attivitaLiv3;

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

    public String getWorkEffortId() {
        return workEffortId;
    }

    public void setWorkEffortId(String workEffortId) {
        this.workEffortId = workEffortId;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getTimeEntryId() {
        return timeEntryId;
    }

    public void setTimeEntryId(String timeEntryId) {
        this.timeEntryId = timeEntryId;
    }

    public String getRateTypeId() {
        return rateTypeId;
    }

    public void setRateTypeId(String rateTypeId) {
        this.rateTypeId = rateTypeId;
    }

    public BigDecimal getPercentage() {
        return percentage;
    }

    public void setPercentage(BigDecimal percentage) {
        this.percentage = percentage;
    }

    public String getEffortUomId() {
        return effortUomId;
    }

    public void setEffortUomId(String effortUomId) {
        this.effortUomId = effortUomId;
    }

    public WorkEffort getWorkEffort() {
        return workEffort;
    }

    public void setWorkEffort(WorkEffort workEffort) {
        this.workEffort = workEffort;
    }

    public String getAttivitaLiv1() {
        return attivitaLiv1;
    }

    public void setAttivitaLiv1(String attivitaLiv1) {
        this.attivitaLiv1 = attivitaLiv1;
    }

    public String getAttivitaLiv2() {
        return attivitaLiv2;
    }

    public void setAttivitaLiv2(String attivitaLiv2) {
        this.attivitaLiv2 = attivitaLiv2;
    }

    public String getAttivitaLiv3() {
        return attivitaLiv3;
    }

    public void setAttivitaLiv3(String attivitaLiv3) {
        this.attivitaLiv3 = attivitaLiv3;
    }
}
