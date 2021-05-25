package it.mapsgroup.gzoom.querydsl.dto;

import javax.annotation.Generated;
import com.querydsl.sql.Column;

/**
 * PersonInterface is a Querydsl bean type
 */
@Generated("com.querydsl.codegen.BeanSerializer")
public class PersonInterface {

    @Column("ALLOCATION_ORG_CODE")
    private String allocationOrgCode;

    @Column("ALLOCATION_ORG_COMMENTS")
    private String allocationOrgComments;

    @Column("ALLOCATION_ORG_DESCRIPTION")
    private String allocationOrgDescription;

    @Column("ALLOCATION_ORG_THRU_DATE")
    private java.time.LocalDateTime allocationOrgThruDate;

    @Column("ALLOCATION_ROLE_TYPE_ID")
    private String allocationRoleTypeId;

    @Column("APPROVER_CODE")
    private String approverCode;

    @Column("COMMENTS")
    private String comments;

    @Column("CONTACT_MAIL")
    private String contactMail;

    @Column("CONTACT_MOBILE")
    private String contactMobile;

    @Column("DATA_SOURCE")
    private String dataSource;

    @Column("DESCRIPTION")
    private String description;

    @Column("EMPLOYMENT_AMOUNT")
    private java.math.BigDecimal employmentAmount;

    @Column("EMPLOYMENT_ORG_CODE")
    private String employmentOrgCode;

    @Column("EMPLOYMENT_ORG_COMMENTS")
    private String employmentOrgComments;

    @Column("EMPLOYMENT_ORG_DESCRIPTION")
    private String employmentOrgDescription;

    @Column("EMPLOYMENT_ORG_FROM_DATE")
    private java.time.LocalDateTime employmentOrgFromDate;

    @Column("EMPLOYMENT_ORG_THRU_DATE")
    private java.time.LocalDateTime employmentOrgThruDate;

    @Column("EMPLOYMENT_ROLE_TYPE_ID")
    private String employmentRoleTypeId;

    @Column("EMPL_POSITION_TYPE_DATE")
    private java.time.LocalDateTime emplPositionTypeDate;

    @Column("EMPL_POSITION_TYPE_ID")
    private String emplPositionTypeId;

    @Column("EVALUATOR_CODE")
    private String evaluatorCode;

    @Column("EVALUATOR_FROM_DATE")
    private java.time.LocalDateTime evaluatorFromDate;

    @Column("FIRST_NAME")
    private String firstName;

    @Column("FISCAL_CODE")
    private String fiscalCode;

    @Column("FROM_DATE")
    private java.time.LocalDateTime fromDate;

    @Column("GROUP_ID")
    private String groupId;

    @Column("ID")
    private String id;

    @Column("IS_EVAL_MANAGER")
    private Boolean isEvalManager;

    @Column("LAST_NAME")
    private String lastName;

    @Column("PERSON_CODE")
    private String personCode;

    @Column("PERSON_ROLE_TYPE_ID")
    private String personRoleTypeId;

    @Column("QUALIF_CODE")
    private String qualifCode;

    @Column("QUALIF_FROM_DATE")
    private java.time.LocalDateTime qualifFromDate;

    @Column("REF_DATE")
    private java.time.LocalDateTime refDate;

    @Column("STATO")
    private String stato;

    @Column("THRU_DATE")
    private java.time.LocalDateTime thruDate;

    @Column("USER_LOGIN_ID")
    private String userLoginId;

    @Column("WORK_EFFORT_ASSIGNMENT_CODE")
    private String workEffortAssignmentCode;

    @Column("WORK_EFFORT_DATE")
    private java.time.LocalDateTime workEffortDate;

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

    public java.time.LocalDateTime getAllocationOrgThruDate() {
        return allocationOrgThruDate;
    }

    public void setAllocationOrgThruDate(java.time.LocalDateTime allocationOrgThruDate) {
        this.allocationOrgThruDate = allocationOrgThruDate;
    }

    public String getAllocationRoleTypeId() {
        return allocationRoleTypeId;
    }

    public void setAllocationRoleTypeId(String allocationRoleTypeId) {
        this.allocationRoleTypeId = allocationRoleTypeId;
    }

    public String getApproverCode() {
        return approverCode;
    }

    public void setApproverCode(String approverCode) {
        this.approverCode = approverCode;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getContactMail() {
        return contactMail;
    }

    public void setContactMail(String contactMail) {
        this.contactMail = contactMail;
    }

    public String getContactMobile() {
        return contactMobile;
    }

    public void setContactMobile(String contactMobile) {
        this.contactMobile = contactMobile;
    }

    public String getDataSource() {
        return dataSource;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public java.math.BigDecimal getEmploymentAmount() {
        return employmentAmount;
    }

    public void setEmploymentAmount(java.math.BigDecimal employmentAmount) {
        this.employmentAmount = employmentAmount;
    }

    public String getEmploymentOrgCode() {
        return employmentOrgCode;
    }

    public void setEmploymentOrgCode(String employmentOrgCode) {
        this.employmentOrgCode = employmentOrgCode;
    }

    public String getEmploymentOrgComments() {
        return employmentOrgComments;
    }

    public void setEmploymentOrgComments(String employmentOrgComments) {
        this.employmentOrgComments = employmentOrgComments;
    }

    public String getEmploymentOrgDescription() {
        return employmentOrgDescription;
    }

    public void setEmploymentOrgDescription(String employmentOrgDescription) {
        this.employmentOrgDescription = employmentOrgDescription;
    }

    public java.time.LocalDateTime getEmploymentOrgFromDate() {
        return employmentOrgFromDate;
    }

    public void setEmploymentOrgFromDate(java.time.LocalDateTime employmentOrgFromDate) {
        this.employmentOrgFromDate = employmentOrgFromDate;
    }

    public java.time.LocalDateTime getEmploymentOrgThruDate() {
        return employmentOrgThruDate;
    }

    public void setEmploymentOrgThruDate(java.time.LocalDateTime employmentOrgThruDate) {
        this.employmentOrgThruDate = employmentOrgThruDate;
    }

    public String getEmploymentRoleTypeId() {
        return employmentRoleTypeId;
    }

    public void setEmploymentRoleTypeId(String employmentRoleTypeId) {
        this.employmentRoleTypeId = employmentRoleTypeId;
    }

    public java.time.LocalDateTime getEmplPositionTypeDate() {
        return emplPositionTypeDate;
    }

    public void setEmplPositionTypeDate(java.time.LocalDateTime emplPositionTypeDate) {
        this.emplPositionTypeDate = emplPositionTypeDate;
    }

    public String getEmplPositionTypeId() {
        return emplPositionTypeId;
    }

    public void setEmplPositionTypeId(String emplPositionTypeId) {
        this.emplPositionTypeId = emplPositionTypeId;
    }

    public String getEvaluatorCode() {
        return evaluatorCode;
    }

    public void setEvaluatorCode(String evaluatorCode) {
        this.evaluatorCode = evaluatorCode;
    }

    public java.time.LocalDateTime getEvaluatorFromDate() {
        return evaluatorFromDate;
    }

    public void setEvaluatorFromDate(java.time.LocalDateTime evaluatorFromDate) {
        this.evaluatorFromDate = evaluatorFromDate;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFiscalCode() {
        return fiscalCode;
    }

    public void setFiscalCode(String fiscalCode) {
        this.fiscalCode = fiscalCode;
    }

    public java.time.LocalDateTime getFromDate() {
        return fromDate;
    }

    public void setFromDate(java.time.LocalDateTime fromDate) {
        this.fromDate = fromDate;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Boolean getIsEvalManager() {
        return isEvalManager;
    }

    public void setIsEvalManager(Boolean isEvalManager) {
        this.isEvalManager = isEvalManager;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public String getQualifCode() {
        return qualifCode;
    }

    public void setQualifCode(String qualifCode) {
        this.qualifCode = qualifCode;
    }

    public java.time.LocalDateTime getQualifFromDate() {
        return qualifFromDate;
    }

    public void setQualifFromDate(java.time.LocalDateTime qualifFromDate) {
        this.qualifFromDate = qualifFromDate;
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

    public java.time.LocalDateTime getThruDate() {
        return thruDate;
    }

    public void setThruDate(java.time.LocalDateTime thruDate) {
        this.thruDate = thruDate;
    }

    public String getUserLoginId() {
        return userLoginId;
    }

    public void setUserLoginId(String userLoginId) {
        this.userLoginId = userLoginId;
    }

    public String getWorkEffortAssignmentCode() {
        return workEffortAssignmentCode;
    }

    public void setWorkEffortAssignmentCode(String workEffortAssignmentCode) {
        this.workEffortAssignmentCode = workEffortAssignmentCode;
    }

    public java.time.LocalDateTime getWorkEffortDate() {
        return workEffortDate;
    }

    public void setWorkEffortDate(java.time.LocalDateTime workEffortDate) {
        this.workEffortDate = workEffortDate;
    }

}

