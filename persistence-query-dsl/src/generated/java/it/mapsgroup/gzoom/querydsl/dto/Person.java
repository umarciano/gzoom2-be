package it.mapsgroup.gzoom.querydsl.dto;

import javax.annotation.Generated;
import com.querydsl.sql.Column;

/**
 * Person is a Querydsl bean type
 */
@Generated("com.querydsl.codegen.BeanSerializer")
public class Person {

    @Column("BIRTH_COUNTRY")
    private String birthCountry;

    @Column("BIRTH_DATE")
    private java.time.LocalDate birthDate;

    @Column("BIRTH_PLACE")
    private String birthPlace;

    @Column("CARD_ID")
    private String cardId;

    @Column("COMMENTS")
    private String comments;

    @Column("CREATED_BY_USER_LOGIN")
    private String createdByUserLogin;

    @Column("CREATED_STAMP")
    private java.time.LocalDateTime createdStamp;

    @Column("CREATED_TX_STAMP")
    private java.time.LocalDateTime createdTxStamp;

    @Column("DECEASED_DATE")
    private java.time.LocalDate deceasedDate;

    @Column("EMPLOYMENT_AMOUNT")
    private java.math.BigDecimal employmentAmount;

    @Column("EMPLOYMENT_STATUS_ENUM_ID")
    private String employmentStatusEnumId;

    @Column("EMPL_POSITION_TYPE_DATE")
    private java.time.LocalDateTime emplPositionTypeDate;

    @Column("EMPL_POSITION_TYPE_ID")
    private String emplPositionTypeId;

    @Column("EXISTING_CUSTOMER")
    private Boolean existingCustomer;

    @Column("FIRST_NAME")
    private String firstName;

    @Column("FIRST_NAME_LOCAL")
    private String firstNameLocal;

    @Column("GENDER")
    private Boolean gender;

    @Column("HEIGHT")
    private java.math.BigDecimal height;

    @Column("LAST_MODIFIED_BY_USER_LOGIN")
    private String lastModifiedByUserLogin;

    @Column("LAST_NAME")
    private String lastName;

    @Column("LAST_NAME_LOCAL")
    private String lastNameLocal;

    @Column("LAST_UPDATED_STAMP")
    private java.time.LocalDateTime lastUpdatedStamp;

    @Column("LAST_UPDATED_TX_STAMP")
    private java.time.LocalDateTime lastUpdatedTxStamp;

    @Column("MARITAL_STATUS")
    private Boolean maritalStatus;

    @Column("MEMBER_ID")
    private String memberId;

    @Column("MIDDLE_NAME")
    private String middleName;

    @Column("MIDDLE_NAME_LOCAL")
    private String middleNameLocal;

    @Column("MONTHS_WITH_EMPLOYER")
    private java.math.BigInteger monthsWithEmployer;

    @Column("MOTHERS_MAIDEN_NAME")
    private String mothersMaidenName;

    @Column("NICKNAME")
    private String nickname;

    @Column("NUMBER_OF_CHILD")
    private java.math.BigInteger numberOfChild;

    @Column("OCCUPATION")
    private String occupation;

    @Column("OTHER_LOCAL")
    private String otherLocal;

    @Column("PARTY_ID")
    private String partyId;

    @Column("PASSPORT_EXPIRE_DATE")
    private java.time.LocalDate passportExpireDate;

    @Column("PASSPORT_NUMBER")
    private String passportNumber;

    @Column("PERSONAL_TITLE")
    private String personalTitle;

    @Column("PERSON_POSITION")
    private String personPosition;

    @Column("RESIDENCE_STATUS_ENUM_ID")
    private String residenceStatusEnumId;

    @Column("SALUTATION")
    private String salutation;

    @Column("SOCIAL_SECURITY_NUMBER")
    private String socialSecurityNumber;

    @Column("SUFFIX")
    private String suffix;

    @Column("TOTAL_YEARS_WORK_EXPERIENCE")
    private java.math.BigDecimal totalYearsWorkExperience;

    @Column("WEIGHT")
    private java.math.BigDecimal weight;

    @Column("YEARS_WITH_EMPLOYER")
    private java.math.BigInteger yearsWithEmployer;

    public String getBirthCountry() {
        return birthCountry;
    }

    public void setBirthCountry(String birthCountry) {
        this.birthCountry = birthCountry;
    }

    public java.time.LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(java.time.LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getBirthPlace() {
        return birthPlace;
    }

    public void setBirthPlace(String birthPlace) {
        this.birthPlace = birthPlace;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
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

    public java.time.LocalDate getDeceasedDate() {
        return deceasedDate;
    }

    public void setDeceasedDate(java.time.LocalDate deceasedDate) {
        this.deceasedDate = deceasedDate;
    }

    public java.math.BigDecimal getEmploymentAmount() {
        return employmentAmount;
    }

    public void setEmploymentAmount(java.math.BigDecimal employmentAmount) {
        this.employmentAmount = employmentAmount;
    }

    public String getEmploymentStatusEnumId() {
        return employmentStatusEnumId;
    }

    public void setEmploymentStatusEnumId(String employmentStatusEnumId) {
        this.employmentStatusEnumId = employmentStatusEnumId;
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

    public Boolean getExistingCustomer() {
        return existingCustomer;
    }

    public void setExistingCustomer(Boolean existingCustomer) {
        this.existingCustomer = existingCustomer;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFirstNameLocal() {
        return firstNameLocal;
    }

    public void setFirstNameLocal(String firstNameLocal) {
        this.firstNameLocal = firstNameLocal;
    }

    public Boolean getGender() {
        return gender;
    }

    public void setGender(Boolean gender) {
        this.gender = gender;
    }

    public java.math.BigDecimal getHeight() {
        return height;
    }

    public void setHeight(java.math.BigDecimal height) {
        this.height = height;
    }

    public String getLastModifiedByUserLogin() {
        return lastModifiedByUserLogin;
    }

    public void setLastModifiedByUserLogin(String lastModifiedByUserLogin) {
        this.lastModifiedByUserLogin = lastModifiedByUserLogin;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLastNameLocal() {
        return lastNameLocal;
    }

    public void setLastNameLocal(String lastNameLocal) {
        this.lastNameLocal = lastNameLocal;
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

    public Boolean getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(Boolean maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getMiddleNameLocal() {
        return middleNameLocal;
    }

    public void setMiddleNameLocal(String middleNameLocal) {
        this.middleNameLocal = middleNameLocal;
    }

    public java.math.BigInteger getMonthsWithEmployer() {
        return monthsWithEmployer;
    }

    public void setMonthsWithEmployer(java.math.BigInteger monthsWithEmployer) {
        this.monthsWithEmployer = monthsWithEmployer;
    }

    public String getMothersMaidenName() {
        return mothersMaidenName;
    }

    public void setMothersMaidenName(String mothersMaidenName) {
        this.mothersMaidenName = mothersMaidenName;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public java.math.BigInteger getNumberOfChild() {
        return numberOfChild;
    }

    public void setNumberOfChild(java.math.BigInteger numberOfChild) {
        this.numberOfChild = numberOfChild;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getOtherLocal() {
        return otherLocal;
    }

    public void setOtherLocal(String otherLocal) {
        this.otherLocal = otherLocal;
    }

    public String getPartyId() {
        return partyId;
    }

    public void setPartyId(String partyId) {
        this.partyId = partyId;
    }

    public java.time.LocalDate getPassportExpireDate() {
        return passportExpireDate;
    }

    public void setPassportExpireDate(java.time.LocalDate passportExpireDate) {
        this.passportExpireDate = passportExpireDate;
    }

    public String getPassportNumber() {
        return passportNumber;
    }

    public void setPassportNumber(String passportNumber) {
        this.passportNumber = passportNumber;
    }

    public String getPersonalTitle() {
        return personalTitle;
    }

    public void setPersonalTitle(String personalTitle) {
        this.personalTitle = personalTitle;
    }

    public String getPersonPosition() {
        return personPosition;
    }

    public void setPersonPosition(String personPosition) {
        this.personPosition = personPosition;
    }

    public String getResidenceStatusEnumId() {
        return residenceStatusEnumId;
    }

    public void setResidenceStatusEnumId(String residenceStatusEnumId) {
        this.residenceStatusEnumId = residenceStatusEnumId;
    }

    public String getSalutation() {
        return salutation;
    }

    public void setSalutation(String salutation) {
        this.salutation = salutation;
    }

    public String getSocialSecurityNumber() {
        return socialSecurityNumber;
    }

    public void setSocialSecurityNumber(String socialSecurityNumber) {
        this.socialSecurityNumber = socialSecurityNumber;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public java.math.BigDecimal getTotalYearsWorkExperience() {
        return totalYearsWorkExperience;
    }

    public void setTotalYearsWorkExperience(java.math.BigDecimal totalYearsWorkExperience) {
        this.totalYearsWorkExperience = totalYearsWorkExperience;
    }

    public java.math.BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(java.math.BigDecimal weight) {
        this.weight = weight;
    }

    public java.math.BigInteger getYearsWithEmployer() {
        return yearsWithEmployer;
    }

    public void setYearsWithEmployer(java.math.BigInteger yearsWithEmployer) {
        this.yearsWithEmployer = yearsWithEmployer;
    }

}

