package it.mapsgroup.gzoom.querydsl.dto;

import javax.annotation.processing.Generated;
import com.querydsl.sql.Column;
import it.mapsgroup.gzoom.querydsl.AbstractIdentity;

/**
 * Uom is a Querydsl bean type
 */
@Generated("com.querydsl.codegen.BeanSerializer")
public class Uom implements AbstractIdentity {

    @Column("ABBREVIATION")
    private String abbreviation;

    @Column("ABBREVIATION_LANG")
    private String abbreviationLang;

    @Column("CREATED_BY_USER_LOGIN")
    private String createdByUserLogin;

    @Column("CREATED_STAMP")
    private java.time.LocalDateTime createdStamp;

    @Column("CREATED_TX_STAMP")
    private java.time.LocalDateTime createdTxStamp;

    @Column("DECIMAL_SCALE")
    private java.math.BigInteger decimalScale;

    @Column("DESCRIPTION")
    private String description;

    @Column("DESCRIPTION_LANG")
    private String descriptionLang;

    @Column("LAST_MODIFIED_BY_USER_LOGIN")
    private String lastModifiedByUserLogin;

    @Column("LAST_UPDATED_STAMP")
    private java.time.LocalDateTime lastUpdatedStamp;

    @Column("LAST_UPDATED_TX_STAMP")
    private java.time.LocalDateTime lastUpdatedTxStamp;

    @Column("MAX_VALUE")
    private java.math.BigDecimal maxValue;

    @Column("MIN_VALUE")
    private java.math.BigDecimal minValue;

    @Column("UOM_ID")
    private String uomId;

    @Column("UOM_TYPE_ID")
    private String uomTypeId;

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public String getAbbreviationLang() {
        return abbreviationLang;
    }

    public void setAbbreviationLang(String abbreviationLang) {
        this.abbreviationLang = abbreviationLang;
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

    public java.math.BigInteger getDecimalScale() {
        return decimalScale;
    }

    public void setDecimalScale(java.math.BigInteger decimalScale) {
        this.decimalScale = decimalScale;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescriptionLang() {
        return descriptionLang;
    }

    public void setDescriptionLang(String descriptionLang) {
        this.descriptionLang = descriptionLang;
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

    public java.math.BigDecimal getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(java.math.BigDecimal maxValue) {
        this.maxValue = maxValue;
    }

    public java.math.BigDecimal getMinValue() {
        return minValue;
    }

    public void setMinValue(java.math.BigDecimal minValue) {
        this.minValue = minValue;
    }

    public String getUomId() {
        return uomId;
    }

    public void setUomId(String uomId) {
        this.uomId = uomId;
    }

    public String getUomTypeId() {
        return uomTypeId;
    }

    public void setUomTypeId(String uomTypeId) {
        this.uomTypeId = uomTypeId;
    }

}

