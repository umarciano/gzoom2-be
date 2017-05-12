package it.mapsgroup.gzoom.querydsl.dto;

import javax.annotation.Generated;
import com.querydsl.sql.Column;
import it.mapsgroup.gzoom.querydsl.AbstractIdentity;

/**
 * ContentAssoc is a Querydsl bean type
 */
@Generated("com.querydsl.codegen.BeanSerializer")
public class ContentAssoc implements AbstractIdentity {

    @Column("CONTENT_ASSOC_PREDICATE_ID")
    private String contentAssocPredicateId;

    @Column("CONTENT_ASSOC_TYPE_ID")
    private String contentAssocTypeId;

    @Column("CONTENT_ID")
    private String contentId;

    @Column("CONTENT_ID_TO")
    private String contentIdTo;

    @Column("CREATED_BY_USER_LOGIN")
    private String createdByUserLogin;

    @Column("CREATED_DATE")
    private java.time.LocalDateTime createdDate;

    @Column("CREATED_STAMP")
    private java.time.LocalDateTime createdStamp;

    @Column("CREATED_TX_STAMP")
    private java.time.LocalDateTime createdTxStamp;

    @Column("DATA_SOURCE_ID")
    private String dataSourceId;

    @Column("FROM_DATE")
    private java.time.LocalDateTime fromDate;

    @Column("LAST_MODIFIED_BY_USER_LOGIN")
    private String lastModifiedByUserLogin;

    @Column("LAST_MODIFIED_DATE")
    private java.time.LocalDateTime lastModifiedDate;

    @Column("LAST_UPDATED_STAMP")
    private java.time.LocalDateTime lastUpdatedStamp;

    @Column("LAST_UPDATED_TX_STAMP")
    private java.time.LocalDateTime lastUpdatedTxStamp;

    @Column("LEFT_COORDINATE")
    private java.math.BigInteger leftCoordinate;

    @Column("MAP_KEY")
    private String mapKey;

    @Column("SEQUENCE_NUM")
    private java.math.BigInteger sequenceNum;

    @Column("THRU_DATE")
    private java.time.LocalDateTime thruDate;

    @Column("UPPER_COORDINATE")
    private java.math.BigInteger upperCoordinate;

    public String getContentAssocPredicateId() {
        return contentAssocPredicateId;
    }

    public void setContentAssocPredicateId(String contentAssocPredicateId) {
        this.contentAssocPredicateId = contentAssocPredicateId;
    }

    public String getContentAssocTypeId() {
        return contentAssocTypeId;
    }

    public void setContentAssocTypeId(String contentAssocTypeId) {
        this.contentAssocTypeId = contentAssocTypeId;
    }

    public String getContentId() {
        return contentId;
    }

    public void setContentId(String contentId) {
        this.contentId = contentId;
    }

    public String getContentIdTo() {
        return contentIdTo;
    }

    public void setContentIdTo(String contentIdTo) {
        this.contentIdTo = contentIdTo;
    }

    public String getCreatedByUserLogin() {
        return createdByUserLogin;
    }

    public void setCreatedByUserLogin(String createdByUserLogin) {
        this.createdByUserLogin = createdByUserLogin;
    }

    public java.time.LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(java.time.LocalDateTime createdDate) {
        this.createdDate = createdDate;
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

    public String getDataSourceId() {
        return dataSourceId;
    }

    public void setDataSourceId(String dataSourceId) {
        this.dataSourceId = dataSourceId;
    }

    public java.time.LocalDateTime getFromDate() {
        return fromDate;
    }

    public void setFromDate(java.time.LocalDateTime fromDate) {
        this.fromDate = fromDate;
    }

    public String getLastModifiedByUserLogin() {
        return lastModifiedByUserLogin;
    }

    public void setLastModifiedByUserLogin(String lastModifiedByUserLogin) {
        this.lastModifiedByUserLogin = lastModifiedByUserLogin;
    }

    public java.time.LocalDateTime getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(java.time.LocalDateTime lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
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

    public java.math.BigInteger getLeftCoordinate() {
        return leftCoordinate;
    }

    public void setLeftCoordinate(java.math.BigInteger leftCoordinate) {
        this.leftCoordinate = leftCoordinate;
    }

    public String getMapKey() {
        return mapKey;
    }

    public void setMapKey(String mapKey) {
        this.mapKey = mapKey;
    }

    public java.math.BigInteger getSequenceNum() {
        return sequenceNum;
    }

    public void setSequenceNum(java.math.BigInteger sequenceNum) {
        this.sequenceNum = sequenceNum;
    }

    public java.time.LocalDateTime getThruDate() {
        return thruDate;
    }

    public void setThruDate(java.time.LocalDateTime thruDate) {
        this.thruDate = thruDate;
    }

    public java.math.BigInteger getUpperCoordinate() {
        return upperCoordinate;
    }

    public void setUpperCoordinate(java.math.BigInteger upperCoordinate) {
        this.upperCoordinate = upperCoordinate;
    }

}

