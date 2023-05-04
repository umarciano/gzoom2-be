package it.mapsgroup.gzoom.querydsl.dto;

import com.querydsl.sql.Column;

import javax.annotation.Generated;

/**
 * PartyHistoryView is a Querydsl bean type
 */
@Generated("com.querydsl.codegen.BeanSerializer")
public class PartyHistoryView {

    @Column("COMMENTS")
    private String comments;

    @Column("DESCRIPTION")
    private String description;

    @Column("EMPLOYMENT_AMOUNT")
    private java.math.BigDecimal employmentAmount;

    @Column("EMPL_POSITION_TYPE_ID")
    private String emplPositionTypeId;

    @Column("FROM_DATE")
    private java.time.LocalDateTime fromDate;

    @Column("PARTY_ID")
    private String partyId;

    @Column("THRU_DATE")
    private java.time.LocalDateTime thruDate;

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public java.math.BigDecimal getEmploymentAmount() {
        return employmentAmount;
    }

    public void setEmploymentAmount(java.math.BigDecimal employmentAmount) {
        this.employmentAmount = employmentAmount;
    }

    public String getEmplPositionTypeId() {
        return emplPositionTypeId;
    }

    public void setEmplPositionTypeId(String emplPositionTypeId) {
        this.emplPositionTypeId = emplPositionTypeId;
    }

    public java.time.LocalDateTime getFromDate() {
        return fromDate;
    }

    public void setFromDate(java.time.LocalDateTime fromDate) {
        this.fromDate = fromDate;
    }

    public String getPartyId() {
        return partyId;
    }

    public void setPartyId(String partyId) {
        this.partyId = partyId;
    }

    public java.time.LocalDateTime getThruDate() {
        return thruDate;
    }

    public void setThruDate(java.time.LocalDateTime thruDate) {
        this.thruDate = thruDate;
    }

}

