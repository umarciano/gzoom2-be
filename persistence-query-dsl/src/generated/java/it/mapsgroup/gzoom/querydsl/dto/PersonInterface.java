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

    @Column("CUSTOM_DATE01")
    private java.time.LocalDateTime customDate01;

    @Column("CUSTOM_DATE02")
    private java.time.LocalDateTime customDate02;

    @Column("CUSTOM_DATE03")
    private java.time.LocalDateTime customDate03;

    @Column("CUSTOM_DATE04")
    private java.time.LocalDateTime customDate04;

    @Column("CUSTOM_DATE05")
    private java.time.LocalDateTime customDate05;

    @Column("CUSTOM_TEXT01")
    private String customText01;

    @Column("CUSTOM_TEXT02")
    private String customText02;

    @Column("CUSTOM_TEXT03")
    private String customText03;

    @Column("CUSTOM_TEXT04")
    private String customText04;

    @Column("CUSTOM_TEXT05")
    private String customText05;

    @Column("CUSTOM_VALUE01")
    private java.math.BigDecimal customValue01;

    @Column("CUSTOM_VALUE02")
    private java.math.BigDecimal customValue02;

    @Column("CUSTOM_VALUE03")
    private java.math.BigDecimal customValue03;

    @Column("CUSTOM_VALUE04")
    private java.math.BigDecimal customValue04;

    @Column("CUSTOM_VALUE05")
    private java.math.BigDecimal customValue05;

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

    @Column("SEQ")
    private java.math.BigInteger seq;

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

    public java.time.LocalDateTime getCustomDate01() {
        return customDate01;
    }

    public void setCustomDate01(java.time.LocalDateTime customDate01) {
        this.customDate01 = customDate01;
    }

    public java.time.LocalDateTime getCustomDate02() {
        return customDate02;
    }

    public void setCustomDate02(java.time.LocalDateTime customDate02) {
        this.customDate02 = customDate02;
    }

    public java.time.LocalDateTime getCustomDate03() {
        return customDate03;
    }

    public void setCustomDate03(java.time.LocalDateTime customDate03) {
        this.customDate03 = customDate03;
    }

    public java.time.LocalDateTime getCustomDate04() {
        return customDate04;
    }

    public void setCustomDate04(java.time.LocalDateTime customDate04) {
        this.customDate04 = customDate04;
    }

    public java.time.LocalDateTime getCustomDate05() {
        return customDate05;
    }

    public void setCustomDate05(java.time.LocalDateTime customDate05) {
        this.customDate05 = customDate05;
    }

    public String getCustomText01() {
        return customText01;
    }

    public void setCustomText01(String customText01) {
        this.customText01 = customText01;
    }

    public String getCustomText02() {
        return customText02;
    }

    public void setCustomText02(String customText02) {
        this.customText02 = customText02;
    }

    public String getCustomText03() {
        return customText03;
    }

    public void setCustomText03(String customText03) {
        this.customText03 = customText03;
    }

    public String getCustomText04() {
        return customText04;
    }

    public void setCustomText04(String customText04) {
        this.customText04 = customText04;
    }

    public String getCustomText05() {
        return customText05;
    }

    public void setCustomText05(String customText05) {
        this.customText05 = customText05;
    }

    public java.math.BigDecimal getCustomValue01() {
        return customValue01;
    }

    public void setCustomValue01(java.math.BigDecimal customValue01) {
        this.customValue01 = customValue01;
    }

    public java.math.BigDecimal getCustomValue02() {
        return customValue02;
    }

    public void setCustomValue02(java.math.BigDecimal customValue02) {
        this.customValue02 = customValue02;
    }

    public java.math.BigDecimal getCustomValue03() {
        return customValue03;
    }

    public void setCustomValue03(java.math.BigDecimal customValue03) {
        this.customValue03 = customValue03;
    }

    public java.math.BigDecimal getCustomValue04() {
        return customValue04;
    }

    public void setCustomValue04(java.math.BigDecimal customValue04) {
        this.customValue04 = customValue04;
    }

    public java.math.BigDecimal getCustomValue05() {
        return customValue05;
    }

    public void setCustomValue05(java.math.BigDecimal customValue05) {
        this.customValue05 = customValue05;
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

    public java.math.BigInteger getSeq() {
        return seq;
    }

    public void setSeq(java.math.BigInteger seq) {
        this.seq = seq;
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

