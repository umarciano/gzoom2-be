package it.mapsgroup.gzoom.model;

import it.mapsgroup.gzoom.querydsl.dto.WorkEffort;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TimeEntry extends Identifiable {
    private LocalDateTime fromDate;
    private LocalDateTime thruDate;
    private String partyId;
    private String timesheetId;
    private String workEffortId;
    private String comments;
    private String timeEntryId;
    private String rateTypeId;
    private BigDecimal hours;
    private BigDecimal planHours;
    private BigDecimal percentage;
    private String effortUomId;
    private WorkEffort workEffort;
    private String description;

    public LocalDateTime getFromDate() {
        return fromDate;
    }

    public void setFromDate(LocalDateTime fromDate) {
        this.fromDate = fromDate;
    }

    public BigDecimal getHours() { return hours; }

    public void setHours(BigDecimal hours) {
        this.hours = hours;
    }

    public BigDecimal getPlanHours() { return planHours; }

    public void setPlanHours(BigDecimal planHours) {
        this.planHours = planHours;
    }

    public LocalDateTime getThruDate() {
        return thruDate;
    }

    public void setThruDate(LocalDateTime thruDate) {
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

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }
}
