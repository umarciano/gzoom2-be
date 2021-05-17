package it.mapsgroup.gzoom.querydsl.dto;

import javax.annotation.Generated;
import com.querydsl.sql.Column;
import it.mapsgroup.gzoom.querydsl.AbstractIdentity;

/**
 * PartyGroup is a Querydsl bean type
 */
@Generated("com.querydsl.codegen.BeanSerializer")
public class PartyGroup implements AbstractIdentity {

    @Column("ANNUAL_REVENUE")
    private java.math.BigDecimal annualRevenue;

    @Column("ANNUAL_TURNOVER")
    private java.math.BigDecimal annualTurnover;

    @Column("COMMENTS")
    private String comments;

    @Column("CREATED_BY_USER_LOGIN")
    private String createdByUserLogin;

    @Column("CREATED_STAMP")
    private java.time.LocalDateTime createdStamp;

    @Column("CREATED_TX_STAMP")
    private java.time.LocalDateTime createdTxStamp;

    @Column("GROUP_NAME")
    private String groupName;

    @Column("GROUP_NAME_LANG")
    private String groupNameLang;

    @Column("GROUP_NAME_LOCAL")
    private String groupNameLocal;

    @Column("LAST_MODIFIED_BY_USER_LOGIN")
    private String lastModifiedByUserLogin;

    @Column("LAST_UPDATED_STAMP")
    private java.time.LocalDateTime lastUpdatedStamp;

    @Column("LAST_UPDATED_TX_STAMP")
    private java.time.LocalDateTime lastUpdatedTxStamp;

    @Column("LOGO_IMAGE_URL")
    private String logoImageUrl;

    @Column("NUM_EMPLOYEES")
    private java.math.BigInteger numEmployees;

    @Column("OFFICE_SITE_NAME")
    private String officeSiteName;

    @Column("PARTY_ID")
    private String partyId;

    @Column("TICKER_SYMBOL")
    private String tickerSymbol;

    @Column("YEAR_STATISTIC_DATA")
    private java.math.BigInteger yearStatisticData;

    public java.math.BigDecimal getAnnualRevenue() {
        return annualRevenue;
    }

    public void setAnnualRevenue(java.math.BigDecimal annualRevenue) {
        this.annualRevenue = annualRevenue;
    }

    public java.math.BigDecimal getAnnualTurnover() {
        return annualTurnover;
    }

    public void setAnnualTurnover(java.math.BigDecimal annualTurnover) {
        this.annualTurnover = annualTurnover;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getCreatedByUserLogin() {
        return createdByUserLogin;
    }

    public void setCreatedByUserLogin(String createdByUserLogin) {
        this.createdByUserLogin = createdByUserLogin;
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

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupNameLang() {
        return groupNameLang;
    }

    public void setGroupNameLang(String groupNameLang) {
        this.groupNameLang = groupNameLang;
    }

    public String getGroupNameLocal() {
        return groupNameLocal;
    }

    public void setGroupNameLocal(String groupNameLocal) {
        this.groupNameLocal = groupNameLocal;
    }

    public String getLastModifiedByUserLogin() {
        return lastModifiedByUserLogin;
    }

    public void setLastModifiedByUserLogin(String lastModifiedByUserLogin) {
        this.lastModifiedByUserLogin = lastModifiedByUserLogin;
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

    public String getLogoImageUrl() {
        return logoImageUrl;
    }

    public void setLogoImageUrl(String logoImageUrl) {
        this.logoImageUrl = logoImageUrl;
    }

    public java.math.BigInteger getNumEmployees() {
        return numEmployees;
    }

    public void setNumEmployees(java.math.BigInteger numEmployees) {
        this.numEmployees = numEmployees;
    }

    public String getOfficeSiteName() {
        return officeSiteName;
    }

    public void setOfficeSiteName(String officeSiteName) {
        this.officeSiteName = officeSiteName;
    }

    public String getPartyId() {
        return partyId;
    }

    public void setPartyId(String partyId) {
        this.partyId = partyId;
    }

    public String getTickerSymbol() {
        return tickerSymbol;
    }

    public void setTickerSymbol(String tickerSymbol) {
        this.tickerSymbol = tickerSymbol;
    }

    public java.math.BigInteger getYearStatisticData() {
        return yearStatisticData;
    }

    public void setYearStatisticData(java.math.BigInteger yearStatisticData) {
        this.yearStatisticData = yearStatisticData;
    }

}

