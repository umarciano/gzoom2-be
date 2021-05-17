package it.mapsgroup.gzoom.querydsl.dto;

import javax.annotation.Generated;
import com.querydsl.sql.Column;
import it.mapsgroup.gzoom.querydsl.AbstractIdentity;

/**
 * UomRangeValues is a Querydsl bean type
 */
@Generated("com.querydsl.codegen.BeanSerializer")
public class UomRangeValues implements AbstractIdentity {

    @Column("ALERT")
    private Boolean alert;

    @Column("COLOR_ENUM_ID")
    private String colorEnumId;

    @Column("COMMENTS")
    private String comments;

    @Column("COMMENTS_LANG")
    private String commentsLang;

    @Column("CREATED_BY_USER_LOGIN")
    private String createdByUserLogin;

    @Column("CREATED_STAMP")
    private java.time.LocalDateTime createdStamp;

    @Column("CREATED_TX_STAMP")
    private java.time.LocalDateTime createdTxStamp;

    @Column("FROM_VALUE")
    private java.math.BigDecimal fromValue;

    @Column("ICON_CONTENT_ID")
    private String iconContentId;

    @Column("IS_POSITIVE")
    private Boolean isPositive;

    @Column("LAST_MODIFIED_BY_USER_LOGIN")
    private String lastModifiedByUserLogin;

    @Column("LAST_UPDATED_STAMP")
    private java.time.LocalDateTime lastUpdatedStamp;

    @Column("LAST_UPDATED_TX_STAMP")
    private java.time.LocalDateTime lastUpdatedTxStamp;

    @Column("PRORATE_RANGE")
    private Boolean prorateRange;

    @Column("RANGE_VALUES_FACTOR")
    private java.math.BigDecimal rangeValuesFactor;

    @Column("RANGE_VALUES_FACTOR_MIN")
    private java.math.BigDecimal rangeValuesFactorMin;

    @Column("THRU_VALUE")
    private java.math.BigDecimal thruValue;

    @Column("UOM_RANGE_ID")
    private String uomRangeId;

    @Column("UOM_RANGE_VALUES_ID")
    private String uomRangeValuesId;

    public Boolean getAlert() {
        return alert;
    }

    public void setAlert(Boolean alert) {
        this.alert = alert;
    }

    public String getColorEnumId() {
        return colorEnumId;
    }

    public void setColorEnumId(String colorEnumId) {
        this.colorEnumId = colorEnumId;
    }

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

    public java.math.BigDecimal getFromValue() {
        return fromValue;
    }

    public void setFromValue(java.math.BigDecimal fromValue) {
        this.fromValue = fromValue;
    }

    public String getIconContentId() {
        return iconContentId;
    }

    public void setIconContentId(String iconContentId) {
        this.iconContentId = iconContentId;
    }

    public Boolean getIsPositive() {
        return isPositive;
    }

    public void setIsPositive(Boolean isPositive) {
        this.isPositive = isPositive;
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

    public Boolean getProrateRange() {
        return prorateRange;
    }

    public void setProrateRange(Boolean prorateRange) {
        this.prorateRange = prorateRange;
    }

    public java.math.BigDecimal getRangeValuesFactor() {
        return rangeValuesFactor;
    }

    public void setRangeValuesFactor(java.math.BigDecimal rangeValuesFactor) {
        this.rangeValuesFactor = rangeValuesFactor;
    }

    public java.math.BigDecimal getRangeValuesFactorMin() {
        return rangeValuesFactorMin;
    }

    public void setRangeValuesFactorMin(java.math.BigDecimal rangeValuesFactorMin) {
        this.rangeValuesFactorMin = rangeValuesFactorMin;
    }

    public java.math.BigDecimal getThruValue() {
        return thruValue;
    }

    public void setThruValue(java.math.BigDecimal thruValue) {
        this.thruValue = thruValue;
    }

    public String getUomRangeId() {
        return uomRangeId;
    }

    public void setUomRangeId(String uomRangeId) {
        this.uomRangeId = uomRangeId;
    }

    public String getUomRangeValuesId() {
        return uomRangeValuesId;
    }

    public void setUomRangeValuesId(String uomRangeValuesId) {
        this.uomRangeValuesId = uomRangeValuesId;
    }

}

