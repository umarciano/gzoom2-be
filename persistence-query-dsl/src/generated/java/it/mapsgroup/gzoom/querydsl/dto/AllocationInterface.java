package it.mapsgroup.gzoom.querydsl.dto;

import javax.annotation.Generated;
import com.querydsl.sql.Column;

/**
 * AllocationInterface is a Querydsl bean type
 */
@Generated("com.querydsl.codegen.BeanSerializer")
public class AllocationInterface {

    @Column("ALLOCATION_FROM_DATE")
    private java.time.LocalDateTime allocationFromDate;

    @Column("ALLOCATION_ORG_CODE")
    private String allocationOrgCode;

    @Column("ALLOCATION_ORG_COMMENTS")
    private String allocationOrgComments;

    @Column("ALLOCATION_ORG_DESCRIPTION")
    private String allocationOrgDescription;

    @Column("ALLOCATION_ROLE_TYPE_ID")
    private String allocationRoleTypeId;

    @Column("ALLOCATION_THRU_DATE")
    private java.time.LocalDateTime allocationThruDate;

    @Column("ALLOCATION_VALUE")
    private java.math.BigInteger allocationValue;

    @Column("DATA_SOURCE")
    private String dataSource;

    @Column("ID")
    private String id;

    @Column("PERSON_CODE")
    private String personCode;

    @Column("PERSON_ROLE_TYPE_ID")
    private String personRoleTypeId;

    @Column("REF_DATE")
    private java.time.LocalDateTime refDate;

    @Column("STATO")
    private String stato;

    public java.time.LocalDateTime getAllocationFromDate() {
        return allocationFromDate;
    }

    public void setAllocationFromDate(java.time.LocalDateTime allocationFromDate) {
        this.allocationFromDate = allocationFromDate;
    }

    public String getAllocationOrgCode() {
        return allocationOrgCode;
    }

    public void setAllocationOrgCode(String allocationOrgCode) {
        this.allocationOrgCode = allocationOrgCode;
    }

    public String getAllocationOrgComments() {
        return allocationOrgComments;
    }

    public void setAllocationOrgComments(String allocationOrgComments) {
        this.allocationOrgComments = allocationOrgComments;
    }

    public String getAllocationOrgDescription() {
        return allocationOrgDescription;
    }

    public void setAllocationOrgDescription(String allocationOrgDescription) {
        this.allocationOrgDescription = allocationOrgDescription;
    }

    public String getAllocationRoleTypeId() {
        return allocationRoleTypeId;
    }

    public void setAllocationRoleTypeId(String allocationRoleTypeId) {
        this.allocationRoleTypeId = allocationRoleTypeId;
    }

    public java.time.LocalDateTime getAllocationThruDate() {
        return allocationThruDate;
    }

    public void setAllocationThruDate(java.time.LocalDateTime allocationThruDate) {
        this.allocationThruDate = allocationThruDate;
    }

    public java.math.BigInteger getAllocationValue() {
        return allocationValue;
    }

    public void setAllocationValue(java.math.BigInteger allocationValue) {
        this.allocationValue = allocationValue;
    }

    public String getDataSource() {
        return dataSource;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPersonCode() {
        return personCode;
    }

    public void setPersonCode(String personCode) {
        this.personCode = personCode;
    }

    public String getPersonRoleTypeId() {
        return personRoleTypeId;
    }

    public void setPersonRoleTypeId(String personRoleTypeId) {
        this.personRoleTypeId = personRoleTypeId;
    }

    public java.time.LocalDateTime getRefDate() {
        return refDate;
    }

    public void setRefDate(java.time.LocalDateTime refDate) {
        this.refDate = refDate;
    }

    public String getStato() {
        return stato;
    }

    public void setStato(String stato) {
        this.stato = stato;
    }

}

