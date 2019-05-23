package it.mapsgroup.gzoom.querydsl.dto;

import javax.annotation.Generated;
import com.querydsl.sql.Column;
import it.mapsgroup.gzoom.querydsl.AbstractIdentity;

/**
 * NoteData is a Querydsl bean type
 */
@Generated("com.querydsl.codegen.BeanSerializer")
public class NoteData implements AbstractIdentity {

    @Column("CREATED_BY_USER_LOGIN")
    private String createdByUserLogin;

    @Column("CREATED_STAMP")
    private java.time.LocalDateTime createdStamp;

    @Column("CREATED_TX_STAMP")
    private java.time.LocalDateTime createdTxStamp;

    @Column("IS_PUBLIC")
    private Boolean isPublic;

    @Column("LAST_MODIFIED_BY_USER_LOGIN")
    private String lastModifiedByUserLogin;

    @Column("LAST_UPDATED_STAMP")
    private java.time.LocalDateTime lastUpdatedStamp;

    @Column("LAST_UPDATED_TX_STAMP")
    private java.time.LocalDateTime lastUpdatedTxStamp;

    @Column("MORE_INFO_ITEM_ID")
    private String moreInfoItemId;

    @Column("MORE_INFO_PORTLET_ID")
    private String moreInfoPortletId;

    @Column("NOTE_DATE_TIME")
    private java.time.LocalDateTime noteDateTime;

    @Column("NOTE_ID")
    private String noteId;

    @Column("NOTE_INFO")
    private String noteInfo;

    @Column("NOTE_INFO_LANG")
    private String noteInfoLang;

    @Column("NOTE_NAME")
    private String noteName;

    @Column("NOTE_NAME_LANG")
    private String noteNameLang;

    @Column("NOTE_PARTY")
    private String noteParty;

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

    public Boolean getIsPublic() {
        return isPublic;
    }

    public void setIsPublic(Boolean isPublic) {
        this.isPublic = isPublic;
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

    public String getMoreInfoItemId() {
        return moreInfoItemId;
    }

    public void setMoreInfoItemId(String moreInfoItemId) {
        this.moreInfoItemId = moreInfoItemId;
    }

    public String getMoreInfoPortletId() {
        return moreInfoPortletId;
    }

    public void setMoreInfoPortletId(String moreInfoPortletId) {
        this.moreInfoPortletId = moreInfoPortletId;
    }

    public java.time.LocalDateTime getNoteDateTime() {
        return noteDateTime;
    }

    public void setNoteDateTime(java.time.LocalDateTime noteDateTime) {
        this.noteDateTime = noteDateTime;
    }

    public String getNoteId() {
        return noteId;
    }

    public void setNoteId(String noteId) {
        this.noteId = noteId;
    }

    public String getNoteInfo() {
        return noteInfo;
    }

    public void setNoteInfo(String noteInfo) {
        this.noteInfo = noteInfo;
    }

    public String getNoteInfoLang() {
        return noteInfoLang;
    }

    public void setNoteInfoLang(String noteInfoLang) {
        this.noteInfoLang = noteInfoLang;
    }

    public String getNoteName() {
        return noteName;
    }

    public void setNoteName(String noteName) {
        this.noteName = noteName;
    }

    public String getNoteNameLang() {
        return noteNameLang;
    }

    public void setNoteNameLang(String noteNameLang) {
        this.noteNameLang = noteNameLang;
    }

    public String getNoteParty() {
        return noteParty;
    }

    public void setNoteParty(String noteParty) {
        this.noteParty = noteParty;
    }

}

