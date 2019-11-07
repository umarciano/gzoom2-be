package it.mapsgroup.gzoom.querydsl.dto;

import javax.annotation.Generated;
import com.querydsl.sql.Column;
import it.mapsgroup.gzoom.querydsl.AbstractIdentity;

/**
 * Visit is a Querydsl bean type
 */
@Generated("com.querydsl.codegen.BeanSerializer")
public class Visit implements AbstractIdentity {

    @Column("CLIENT_HOST_NAME")
    private String clientHostName;

    @Column("CLIENT_IP_ADDRESS")
    private String clientIpAddress;

    @Column("CLIENT_IP_COUNTRY_GEO_ID")
    private String clientIpCountryGeoId;

    @Column("CLIENT_IP_ISP_NAME")
    private String clientIpIspName;

    @Column("CLIENT_IP_POSTAL_CODE")
    private String clientIpPostalCode;

    @Column("CLIENT_IP_STATE_PROV_GEO_ID")
    private String clientIpStateProvGeoId;

    @Column("CLIENT_USER")
    private String clientUser;

    @Column("CONTACT_MECH_ID")
    private String contactMechId;

    @Column("COOKIE")
    private String cookie;

    @Column("CREATED_STAMP")
    private java.time.LocalDateTime createdStamp;

    @Column("CREATED_TX_STAMP")
    private java.time.LocalDateTime createdTxStamp;

    @Column("FROM_DATE")
    private java.time.LocalDateTime fromDate;

    @Column("INITIAL_LOCALE")
    private String initialLocale;

    @Column("INITIAL_REFERRER")
    private String initialReferrer;

    @Column("INITIAL_REQUEST")
    private String initialRequest;

    @Column("INITIAL_USER_AGENT")
    private String initialUserAgent;

    @Column("LAST_UPDATED_STAMP")
    private java.time.LocalDateTime lastUpdatedStamp;

    @Column("LAST_UPDATED_TX_STAMP")
    private java.time.LocalDateTime lastUpdatedTxStamp;

    @Column("PARTY_ID")
    private String partyId;

    @Column("ROLE_TYPE_ID")
    private String roleTypeId;

    @Column("SERVER_HOST_NAME")
    private String serverHostName;

    @Column("SERVER_IP_ADDRESS")
    private String serverIpAddress;

    @Column("SESSION_ID")
    private String sessionId;

    @Column("THRU_DATE")
    private java.time.LocalDateTime thruDate;

    @Column("USER_AGENT_ID")
    private String userAgentId;

    @Column("USER_CREATED")
    private Boolean userCreated;

    @Column("USER_LOGIN_ID")
    private String userLoginId;

    @Column("VISIT_ID")
    private String visitId;

    @Column("VISITOR_ID")
    private String visitorId;

    @Column("WEBAPP_NAME")
    private String webappName;

    public String getClientHostName() {
        return clientHostName;
    }

    public void setClientHostName(String clientHostName) {
        this.clientHostName = clientHostName;
    }

    public String getClientIpAddress() {
        return clientIpAddress;
    }

    public void setClientIpAddress(String clientIpAddress) {
        this.clientIpAddress = clientIpAddress;
    }

    public String getClientIpCountryGeoId() {
        return clientIpCountryGeoId;
    }

    public void setClientIpCountryGeoId(String clientIpCountryGeoId) {
        this.clientIpCountryGeoId = clientIpCountryGeoId;
    }

    public String getClientIpIspName() {
        return clientIpIspName;
    }

    public void setClientIpIspName(String clientIpIspName) {
        this.clientIpIspName = clientIpIspName;
    }

    public String getClientIpPostalCode() {
        return clientIpPostalCode;
    }

    public void setClientIpPostalCode(String clientIpPostalCode) {
        this.clientIpPostalCode = clientIpPostalCode;
    }

    public String getClientIpStateProvGeoId() {
        return clientIpStateProvGeoId;
    }

    public void setClientIpStateProvGeoId(String clientIpStateProvGeoId) {
        this.clientIpStateProvGeoId = clientIpStateProvGeoId;
    }

    public String getClientUser() {
        return clientUser;
    }

    public void setClientUser(String clientUser) {
        this.clientUser = clientUser;
    }

    public String getContactMechId() {
        return contactMechId;
    }

    public void setContactMechId(String contactMechId) {
        this.contactMechId = contactMechId;
    }

    public String getCookie() {
        return cookie;
    }

    public void setCookie(String cookie) {
        this.cookie = cookie;
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

    public String getInitialLocale() {
        return initialLocale;
    }

    public void setInitialLocale(String initialLocale) {
        this.initialLocale = initialLocale;
    }

    public String getInitialReferrer() {
        return initialReferrer;
    }

    public void setInitialReferrer(String initialReferrer) {
        this.initialReferrer = initialReferrer;
    }

    public String getInitialRequest() {
        return initialRequest;
    }

    public void setInitialRequest(String initialRequest) {
        this.initialRequest = initialRequest;
    }

    public String getInitialUserAgent() {
        return initialUserAgent;
    }

    public void setInitialUserAgent(String initialUserAgent) {
        this.initialUserAgent = initialUserAgent;
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

    public String getRoleTypeId() {
        return roleTypeId;
    }

    public void setRoleTypeId(String roleTypeId) {
        this.roleTypeId = roleTypeId;
    }

    public String getServerHostName() {
        return serverHostName;
    }

    public void setServerHostName(String serverHostName) {
        this.serverHostName = serverHostName;
    }

    public String getServerIpAddress() {
        return serverIpAddress;
    }

    public void setServerIpAddress(String serverIpAddress) {
        this.serverIpAddress = serverIpAddress;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public java.time.LocalDateTime getThruDate() {
        return thruDate;
    }

    public void setThruDate(java.time.LocalDateTime thruDate) {
        this.thruDate = thruDate;
    }

    public String getUserAgentId() {
        return userAgentId;
    }

    public void setUserAgentId(String userAgentId) {
        this.userAgentId = userAgentId;
    }

    public Boolean getUserCreated() {
        return userCreated;
    }

    public void setUserCreated(Boolean userCreated) {
        this.userCreated = userCreated;
    }

    public String getUserLoginId() {
        return userLoginId;
    }

    public void setUserLoginId(String userLoginId) {
        this.userLoginId = userLoginId;
    }

    public String getVisitId() {
        return visitId;
    }

    public void setVisitId(String visitId) {
        this.visitId = visitId;
    }

    public String getVisitorId() {
        return visitorId;
    }

    public void setVisitorId(String visitorId) {
        this.visitorId = visitorId;
    }

    public String getWebappName() {
        return webappName;
    }

    public void setWebappName(String webappName) {
        this.webappName = webappName;
    }

}

