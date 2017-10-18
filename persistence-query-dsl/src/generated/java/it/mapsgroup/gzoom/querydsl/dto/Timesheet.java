package it.mapsgroup.gzoom.querydsl.dto;

import javax.annotation.Generated;
import com.querydsl.sql.Column;
import it.mapsgroup.gzoom.querydsl.AbstractIdentity;

/**
 * Timesheet is a Querydsl bean type
 */
@Generated("com.querydsl.codegen.BeanSerializer")
public class Timesheet implements AbstractIdentity {

    @Column("ACTUAL_HOURS")
    private java.time.LocalDateTime actualHours;

    @Column("APPROVED_BY_USER_LOGIN_ID")
    private String approvedByUserLoginId;

    @Column("CLIENT_PARTY_ID")
    private String clientPartyId;

    @Column("COMMENTS")
    private String comments;

    @Column("CONTRACT_HOURS")
    private java.time.LocalDateTime contractHours;

    @Column("CREATED_STAMP")
    private java.time.LocalDateTime createdStamp;

    @Column("CREATED_TX_STAMP")
    private java.time.LocalDateTime createdTxStamp;

    @Column("FROM_DATE")
    private java.time.LocalDateTime fromDate;

    @Column("LAST_UPDATED_STAMP")
    private java.time.LocalDateTime lastUpdatedStamp;

    @Column("LAST_UPDATED_TX_STAMP")
    private java.time.LocalDateTime lastUpdatedTxStamp;

    @Column("PARTY_ID")
    private String partyId;

    @Column("STATUS_ID")
    private String statusId;

    @Column("THRU_DATE")
    private java.time.LocalDateTime thruDate;

    @Column("TIMESHEET_ID")
    private String timesheetId;

    @Column("TRANSFER_FLAG")
    private Boolean transferFlag;

    public java.time.LocalDateTime getActualHours() {
        return actualHours;
    }

    public void setActualHours(java.time.LocalDateTime actualHours) {
        this.actualHours = actualHours;
    }

    public String getApprovedByUserLoginId() {
        return approvedByUserLoginId;
    }

    public void setApprovedByUserLoginId(String approvedByUserLoginId) {
        this.approvedByUserLoginId = approvedByUserLoginId;
    }

    public String getClientPartyId() {
        return clientPartyId;
    }

    public void setClientPartyId(String clientPartyId) {
        this.clientPartyId = clientPartyId;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public java.time.LocalDateTime getContractHours() {
        return contractHours;
    }

    public void setContractHours(java.time.LocalDateTime contractHours) {
        this.contractHours = contractHours;
    }

    public java.time.LocalDateTime getCreatedStamp() {
        return createdStamp;
    }

    public void setCreatedStamp(java.time.LocalDateTime createdStamp) {
        this.createdStamp = createdStamp;
    }

    public java.time.LocalDateTime getCreatedTxStamp() {
        return createdTxStamp;
    }

    public void setCreatedTxStamp(java.time.LocalDateTime createdTxStamp) {
        this.createdTxStamp = createdTxStamp;
    }

    public java.time.LocalDateTime getFromDate() {
        return fromDate;
    }

    public void setFromDate(java.time.LocalDateTime fromDate) {
        this.fromDate = fromDate;
    }

    public java.time.LocalDateTime getLastUpdatedStamp() {
        return lastUpdatedStamp;
    }

    public void setLastUpdatedStamp(java.time.LocalDateTime lastUpdatedStamp) {
        this.lastUpdatedStamp = lastUpdatedStamp;
    }

    public java.time.LocalDateTime getLastUpdatedTxStamp() {
        return lastUpdatedTxStamp;
    }

    public void setLastUpdatedTxStamp(java.time.LocalDateTime lastUpdatedTxStamp) {
        this.lastUpdatedTxStamp = lastUpdatedTxStamp;
    }

    public String getPartyId() {
        return partyId;
    }

    public void setPartyId(String partyId) {
        this.partyId = partyId;
    }

    public String getStatusId() {
        return statusId;
    }

    public void setStatusId(String statusId) {
        this.statusId = statusId;
    }

    public java.time.LocalDateTime getThruDate() {
        return thruDate;
    }

    public void setThruDate(java.time.LocalDateTime thruDate) {
        this.thruDate = thruDate;
    }

    public String getTimesheetId() {
        return timesheetId;
    }

    public void setTimesheetId(String timesheetId) {
        this.timesheetId = timesheetId;
    }

    public Boolean getTransferFlag() {
        return transferFlag;
    }

    public void setTransferFlag(Boolean transferFlag) {
        this.transferFlag = transferFlag;
    }

}

