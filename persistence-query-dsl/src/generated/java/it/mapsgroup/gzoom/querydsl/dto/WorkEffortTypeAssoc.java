package it.mapsgroup.gzoom.querydsl.dto;

import javax.annotation.Generated;
import com.querydsl.sql.Column;
import it.mapsgroup.gzoom.querydsl.AbstractIdentity;

/**
 * WorkEffortTypeAssoc is a Querydsl bean type
 */
@Generated("com.querydsl.codegen.BeanSerializer")
public class WorkEffortTypeAssoc implements AbstractIdentity {

    @Column("COMMENTS")
    private String comments;

    @Column("COMMENTS_LANG")
    private String commentsLang;

    @Column("CONTENT_ID")
    private String contentId;

    @Column("CREATED_BY_USER_LOGIN")
    private String createdByUserLogin;

    @Column("CREATED_STAMP")
    private java.time.LocalDateTime createdStamp;

    @Column("CREATED_TX_STAMP")
    private java.time.LocalDateTime createdTxStamp;

    @Column("HAS_RESP")
    private Boolean hasResp;

    @Column("IS_MANDATORY")
    private Boolean isMandatory;

    @Column("IS_PARENT_REL")
    private Boolean isParentRel;

    @Column("IS_UNIQUE")
    private Boolean isUnique;

    @Column("LAST_MODIFIED_BY_USER_LOGIN")
    private String lastModifiedByUserLogin;

    @Column("LAST_UPDATED_STAMP")
    private java.time.LocalDateTime lastUpdatedStamp;

    @Column("LAST_UPDATED_TX_STAMP")
    private java.time.LocalDateTime lastUpdatedTxStamp;

    @Column("WEFROM_WETO_ENUM_ID")
    private String wefromWetoEnumId;

    @Column("WORK_EFFORT_ASSOC_TYPE_ID")
    private String workEffortAssocTypeId;

    @Column("WORK_EFFORT_TYPE_ID")
    private String workEffortTypeId;

    @Column("WORK_EFFORT_TYPE_ID_REF")
    private String workEffortTypeIdRef;

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getCommentsLang() {
        return commentsLang;
    }

    public void setCommentsLang(String commentsLang) {
        this.commentsLang = commentsLang;
    }

    public String getContentId() {
        return contentId;
    }

    public void setContentId(String contentId) {
        this.contentId = contentId;
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

    public Boolean getHasResp() {
        return hasResp;
    }

    public void setHasResp(Boolean hasResp) {
        this.hasResp = hasResp;
    }

    public Boolean getIsMandatory() {
        return isMandatory;
    }

    public void setIsMandatory(Boolean isMandatory) {
        this.isMandatory = isMandatory;
    }

    public Boolean getIsParentRel() {
        return isParentRel;
    }

    public void setIsParentRel(Boolean isParentRel) {
        this.isParentRel = isParentRel;
    }

    public Boolean getIsUnique() {
        return isUnique;
    }

    public void setIsUnique(Boolean isUnique) {
        this.isUnique = isUnique;
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

    public String getWefromWetoEnumId() {
        return wefromWetoEnumId;
    }

    public void setWefromWetoEnumId(String wefromWetoEnumId) {
        this.wefromWetoEnumId = wefromWetoEnumId;
    }

    public String getWorkEffortAssocTypeId() {
        return workEffortAssocTypeId;
    }

    public void setWorkEffortAssocTypeId(String workEffortAssocTypeId) {
        this.workEffortAssocTypeId = workEffortAssocTypeId;
    }

    public String getWorkEffortTypeId() {
        return workEffortTypeId;
    }

    public void setWorkEffortTypeId(String workEffortTypeId) {
        this.workEffortTypeId = workEffortTypeId;
    }

    public String getWorkEffortTypeIdRef() {
        return workEffortTypeIdRef;
    }

    public void setWorkEffortTypeIdRef(String workEffortTypeIdRef) {
        this.workEffortTypeIdRef = workEffortTypeIdRef;
    }

}

