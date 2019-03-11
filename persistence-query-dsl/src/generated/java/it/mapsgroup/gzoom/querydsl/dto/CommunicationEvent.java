package it.mapsgroup.gzoom.querydsl.dto;

import javax.annotation.processing.Generated;
import com.querydsl.sql.Column;
import it.mapsgroup.gzoom.querydsl.AbstractIdentity;

/**
 * CommunicationEvent is a Querydsl bean type
 */
@Generated("com.querydsl.codegen.BeanSerializer")
public class CommunicationEvent implements AbstractIdentity {

    @Column("BCC_STRING")
    private String bccString;

    @Column("CC_STRING")
    private String ccString;

    @Column("COMMUNICATION_EVENT_ID")
    private String communicationEventId;

    @Column("COMMUNICATION_EVENT_TYPE_ID")
    private String communicationEventTypeId;

    @Column("CONTACT_LIST_ID")
    private String contactListId;

    @Column("CONTACT_MECH_ID_FROM")
    private String contactMechIdFrom;

    @Column("CONTACT_MECH_ID_TO")
    private String contactMechIdTo;

    @Column("CONTACT_MECH_TYPE_ID")
    private String contactMechTypeId;

    @Column("CONTENT")
    private String content;

    @Column("CONTENT_MIME_TYPE_ID")
    private String contentMimeTypeId;

    @Column("CREATED_STAMP")
    private java.time.LocalDateTime createdStamp;

    @Column("CREATED_TX_STAMP")
    private java.time.LocalDateTime createdTxStamp;

    @Column("DATETIME_ENDED")
    private java.time.LocalDateTime datetimeEnded;

    @Column("DATETIME_STARTED")
    private java.time.LocalDateTime datetimeStarted;

    @Column("ENTRY_DATE")
    private java.time.LocalDateTime entryDate;

    @Column("FROM_STRING")
    private String fromString;

    @Column("HEADER_STRING")
    private String headerString;

    @Column("LAST_UPDATED_STAMP")
    private java.time.LocalDateTime lastUpdatedStamp;

    @Column("LAST_UPDATED_TX_STAMP")
    private java.time.LocalDateTime lastUpdatedTxStamp;

    @Column("MESSAGE_ID")
    private String messageId;

    @Column("NOTE")
    private String note;

    @Column("ORIG_COMM_EVENT_ID")
    private String origCommEventId;

    @Column("PARENT_COMM_EVENT_ID")
    private String parentCommEventId;

    @Column("PARTY_ID_FROM")
    private String partyIdFrom;

    @Column("PARTY_ID_TO")
    private String partyIdTo;

    @Column("REASON_ENUM_ID")
    private String reasonEnumId;

    @Column("ROLE_TYPE_ID_FROM")
    private String roleTypeIdFrom;

    @Column("ROLE_TYPE_ID_TO")
    private String roleTypeIdTo;

    @Column("STATUS_ID")
    private String statusId;

    @Column("SUBJECT")
    private String subject;

    @Column("TO_STRING")
    private String toString;

    public String getBccString() {
        return bccString;
    }

    public void setBccString(String bccString) {
        this.bccString = bccString;
    }

    public String getCcString() {
        return ccString;
    }

    public void setCcString(String ccString) {
        this.ccString = ccString;
    }

    public String getCommunicationEventId() {
        return communicationEventId;
    }

    public void setCommunicationEventId(String communicationEventId) {
        this.communicationEventId = communicationEventId;
    }

    public String getCommunicationEventTypeId() {
        return communicationEventTypeId;
    }

    public void setCommunicationEventTypeId(String communicationEventTypeId) {
        this.communicationEventTypeId = communicationEventTypeId;
    }

    public String getContactListId() {
        return contactListId;
    }

    public void setContactListId(String contactListId) {
        this.contactListId = contactListId;
    }

    public String getContactMechIdFrom() {
        return contactMechIdFrom;
    }

    public void setContactMechIdFrom(String contactMechIdFrom) {
        this.contactMechIdFrom = contactMechIdFrom;
    }

    public String getContactMechIdTo() {
        return contactMechIdTo;
    }

    public void setContactMechIdTo(String contactMechIdTo) {
        this.contactMechIdTo = contactMechIdTo;
    }

    public String getContactMechTypeId() {
        return contactMechTypeId;
    }

    public void setContactMechTypeId(String contactMechTypeId) {
        this.contactMechTypeId = contactMechTypeId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContentMimeTypeId() {
        return contentMimeTypeId;
    }

    public void setContentMimeTypeId(String contentMimeTypeId) {
        this.contentMimeTypeId = contentMimeTypeId;
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

    public java.time.LocalDateTime getDatetimeEnded() {
        return datetimeEnded;
    }

    public void setDatetimeEnded(java.time.LocalDateTime datetimeEnded) {
        this.datetimeEnded = datetimeEnded;
    }

    public java.time.LocalDateTime getDatetimeStarted() {
        return datetimeStarted;
    }

    public void setDatetimeStarted(java.time.LocalDateTime datetimeStarted) {
        this.datetimeStarted = datetimeStarted;
    }

    public java.time.LocalDateTime getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(java.time.LocalDateTime entryDate) {
        this.entryDate = entryDate;
    }

    public String getFromString() {
        return fromString;
    }

    public void setFromString(String fromString) {
        this.fromString = fromString;
    }

    public String getHeaderString() {
        return headerString;
    }

    public void setHeaderString(String headerString) {
        this.headerString = headerString;
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

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getOrigCommEventId() {
        return origCommEventId;
    }

    public void setOrigCommEventId(String origCommEventId) {
        this.origCommEventId = origCommEventId;
    }

    public String getParentCommEventId() {
        return parentCommEventId;
    }

    public void setParentCommEventId(String parentCommEventId) {
        this.parentCommEventId = parentCommEventId;
    }

    public String getPartyIdFrom() {
        return partyIdFrom;
    }

    public void setPartyIdFrom(String partyIdFrom) {
        this.partyIdFrom = partyIdFrom;
    }

    public String getPartyIdTo() {
        return partyIdTo;
    }

    public void setPartyIdTo(String partyIdTo) {
        this.partyIdTo = partyIdTo;
    }

    public String getReasonEnumId() {
        return reasonEnumId;
    }

    public void setReasonEnumId(String reasonEnumId) {
        this.reasonEnumId = reasonEnumId;
    }

    public String getRoleTypeIdFrom() {
        return roleTypeIdFrom;
    }

    public void setRoleTypeIdFrom(String roleTypeIdFrom) {
        this.roleTypeIdFrom = roleTypeIdFrom;
    }

    public String getRoleTypeIdTo() {
        return roleTypeIdTo;
    }

    public void setRoleTypeIdTo(String roleTypeIdTo) {
        this.roleTypeIdTo = roleTypeIdTo;
    }

    public String getStatusId() {
        return statusId;
    }

    public void setStatusId(String statusId) {
        this.statusId = statusId;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getToString() {
        return toString;
    }

    public void setToString(String toString) {
        this.toString = toString;
    }

}

