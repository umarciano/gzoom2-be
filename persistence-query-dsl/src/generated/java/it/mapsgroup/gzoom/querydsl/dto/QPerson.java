package it.mapsgroup.gzoom.querydsl.dto;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;

import com.querydsl.sql.ColumnMetadata;
import java.sql.Types;




/**
 * QPerson is a Querydsl query type for Person
 */
@Generated("com.querydsl.sql.codegen.MetaDataSerializer")
public class QPerson extends com.querydsl.sql.RelationalPathBase<Person> {

    private static final long serialVersionUID = 886970208;

    public static final QPerson person = new QPerson("PERSON");

    public final StringPath birthCountry = createString("birthCountry");

    public final DatePath<java.time.LocalDate> birthDate = createDate("birthDate", java.time.LocalDate.class);

    public final StringPath birthPlace = createString("birthPlace");

    public final StringPath cardId = createString("cardId");

    public final StringPath comments = createString("comments");

    public final StringPath createdByUserLogin = createString("createdByUserLogin");

    public final DateTimePath<java.time.LocalDateTime> createdStamp = createDateTime("createdStamp", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> createdTxStamp = createDateTime("createdTxStamp", java.time.LocalDateTime.class);

    public final DatePath<java.time.LocalDate> deceasedDate = createDate("deceasedDate", java.time.LocalDate.class);

    public final NumberPath<java.math.BigDecimal> employmentAmount = createNumber("employmentAmount", java.math.BigDecimal.class);

    public final StringPath employmentStatusEnumId = createString("employmentStatusEnumId");

    public final DateTimePath<java.time.LocalDateTime> emplPositionTypeDate = createDateTime("emplPositionTypeDate", java.time.LocalDateTime.class);

    public final StringPath emplPositionTypeId = createString("emplPositionTypeId");

    public final BooleanPath existingCustomer = createBoolean("existingCustomer");

    public final StringPath firstName = createString("firstName");

    public final StringPath firstNameLocal = createString("firstNameLocal");

    public final BooleanPath gender = createBoolean("gender");

    public final NumberPath<java.math.BigDecimal> height = createNumber("height", java.math.BigDecimal.class);

    public final StringPath lastModifiedByUserLogin = createString("lastModifiedByUserLogin");

    public final StringPath lastName = createString("lastName");

    public final StringPath lastNameLocal = createString("lastNameLocal");

    public final DateTimePath<java.time.LocalDateTime> lastUpdatedStamp = createDateTime("lastUpdatedStamp", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> lastUpdatedTxStamp = createDateTime("lastUpdatedTxStamp", java.time.LocalDateTime.class);

    public final BooleanPath maritalStatus = createBoolean("maritalStatus");

    public final StringPath memberId = createString("memberId");

    public final StringPath middleName = createString("middleName");

    public final StringPath middleNameLocal = createString("middleNameLocal");

    public final NumberPath<java.math.BigInteger> monthsWithEmployer = createNumber("monthsWithEmployer", java.math.BigInteger.class);

    public final StringPath mothersMaidenName = createString("mothersMaidenName");

    public final StringPath nickname = createString("nickname");

    public final NumberPath<java.math.BigInteger> numberOfChild = createNumber("numberOfChild", java.math.BigInteger.class);

    public final StringPath occupation = createString("occupation");

    public final StringPath otherLocal = createString("otherLocal");

    public final StringPath partyId = createString("partyId");

    public final DatePath<java.time.LocalDate> passportExpireDate = createDate("passportExpireDate", java.time.LocalDate.class);

    public final StringPath passportNumber = createString("passportNumber");

    public final StringPath personalTitle = createString("personalTitle");

    public final StringPath personPosition = createString("personPosition");

    public final StringPath residenceStatusEnumId = createString("residenceStatusEnumId");

    public final StringPath salutation = createString("salutation");

    public final StringPath socialSecurityNumber = createString("socialSecurityNumber");

    public final StringPath suffix = createString("suffix");

    public final NumberPath<java.math.BigDecimal> totalYearsWorkExperience = createNumber("totalYearsWorkExperience", java.math.BigDecimal.class);

    public final NumberPath<java.math.BigDecimal> weight = createNumber("weight", java.math.BigDecimal.class);

    public final NumberPath<java.math.BigInteger> yearsWithEmployer = createNumber("yearsWithEmployer", java.math.BigInteger.class);

    public final com.querydsl.sql.PrimaryKey<Person> primary = createPrimaryKey(partyId);

    public final com.querydsl.sql.ForeignKey<EmplPositionType> personEmplPositionType = createForeignKey(emplPositionTypeId, "EMPL_POSITION_TYPE_ID");

    public final com.querydsl.sql.ForeignKey<Party> personParty = createForeignKey(partyId, "PARTY_ID");

    public final com.querydsl.sql.ForeignKey<Enumeration> personEmpsEnum = createForeignKey(employmentStatusEnumId, "ENUM_ID");

    public final com.querydsl.sql.ForeignKey<Enumeration> personRessEnum = createForeignKey(residenceStatusEnumId, "ENUM_ID");

    public QPerson(String variable) {
        super(Person.class, forVariable(variable), "null", "PERSON");
        addMetadata();
    }

    public QPerson(String variable, String schema, String table) {
        super(Person.class, forVariable(variable), schema, table);
        addMetadata();
    }

    public QPerson(String variable, String schema) {
        super(Person.class, forVariable(variable), schema, "PERSON");
        addMetadata();
    }

    public QPerson(Path<? extends Person> path) {
        super(path.getType(), path.getMetadata(), "null", "PERSON");
        addMetadata();
    }

    public QPerson(PathMetadata metadata) {
        super(Person.class, metadata, "null", "PERSON");
        addMetadata();
    }

    public void addMetadata() {
        addMetadata(birthCountry, ColumnMetadata.named("BIRTH_COUNTRY").withIndex(38).ofType(Types.VARCHAR).withSize(20));
        addMetadata(birthDate, ColumnMetadata.named("BIRTH_DATE").withIndex(15).ofType(Types.DATE).withSize(10));
        addMetadata(birthPlace, ColumnMetadata.named("BIRTH_PLACE").withIndex(37).ofType(Types.VARCHAR).withSize(60));
        addMetadata(cardId, ColumnMetadata.named("CARD_ID").withIndex(32).ofType(Types.VARCHAR).withSize(60));
        addMetadata(comments, ColumnMetadata.named("COMMENTS").withIndex(25).ofType(Types.VARCHAR).withSize(255));
        addMetadata(createdByUserLogin, ColumnMetadata.named("CREATED_BY_USER_LOGIN").withIndex(44).ofType(Types.VARCHAR).withSize(250));
        addMetadata(createdStamp, ColumnMetadata.named("CREATED_STAMP").withIndex(35).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(createdTxStamp, ColumnMetadata.named("CREATED_TX_STAMP").withIndex(36).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(deceasedDate, ColumnMetadata.named("DECEASED_DATE").withIndex(16).ofType(Types.DATE).withSize(10));
        addMetadata(employmentAmount, ColumnMetadata.named("EMPLOYMENT_AMOUNT").withIndex(42).ofType(Types.DECIMAL).withSize(18).withDigits(2));
        addMetadata(employmentStatusEnumId, ColumnMetadata.named("EMPLOYMENT_STATUS_ENUM_ID").withIndex(26).ofType(Types.VARCHAR).withSize(20));
        addMetadata(emplPositionTypeDate, ColumnMetadata.named("EMPL_POSITION_TYPE_DATE").withIndex(41).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(emplPositionTypeId, ColumnMetadata.named("EMPL_POSITION_TYPE_ID").withIndex(40).ofType(Types.VARCHAR).withSize(20));
        addMetadata(existingCustomer, ColumnMetadata.named("EXISTING_CUSTOMER").withIndex(31).ofType(Types.CHAR).withSize(1));
        addMetadata(firstName, ColumnMetadata.named("FIRST_NAME").withIndex(3).ofType(Types.VARCHAR).withSize(100));
        addMetadata(firstNameLocal, ColumnMetadata.named("FIRST_NAME_LOCAL").withIndex(9).ofType(Types.VARCHAR).withSize(100));
        addMetadata(gender, ColumnMetadata.named("GENDER").withIndex(14).ofType(Types.CHAR).withSize(1));
        addMetadata(height, ColumnMetadata.named("HEIGHT").withIndex(17).ofType(Types.DECIMAL).withSize(18).withDigits(6));
        addMetadata(lastModifiedByUserLogin, ColumnMetadata.named("LAST_MODIFIED_BY_USER_LOGIN").withIndex(43).ofType(Types.VARCHAR).withSize(250));
        addMetadata(lastName, ColumnMetadata.named("LAST_NAME").withIndex(5).ofType(Types.VARCHAR).withSize(100));
        addMetadata(lastNameLocal, ColumnMetadata.named("LAST_NAME_LOCAL").withIndex(11).ofType(Types.VARCHAR).withSize(100));
        addMetadata(lastUpdatedStamp, ColumnMetadata.named("LAST_UPDATED_STAMP").withIndex(33).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(lastUpdatedTxStamp, ColumnMetadata.named("LAST_UPDATED_TX_STAMP").withIndex(34).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(maritalStatus, ColumnMetadata.named("MARITAL_STATUS").withIndex(20).ofType(Types.CHAR).withSize(1));
        addMetadata(memberId, ColumnMetadata.named("MEMBER_ID").withIndex(13).ofType(Types.VARCHAR).withSize(20));
        addMetadata(middleName, ColumnMetadata.named("MIDDLE_NAME").withIndex(4).ofType(Types.VARCHAR).withSize(100));
        addMetadata(middleNameLocal, ColumnMetadata.named("MIDDLE_NAME_LOCAL").withIndex(10).ofType(Types.VARCHAR).withSize(100));
        addMetadata(monthsWithEmployer, ColumnMetadata.named("MONTHS_WITH_EMPLOYER").withIndex(30).ofType(Types.DECIMAL).withSize(20));
        addMetadata(mothersMaidenName, ColumnMetadata.named("MOTHERS_MAIDEN_NAME").withIndex(19).ofType(Types.VARCHAR).withSize(255));
        addMetadata(nickname, ColumnMetadata.named("NICKNAME").withIndex(8).ofType(Types.VARCHAR).withSize(100));
        addMetadata(numberOfChild, ColumnMetadata.named("NUMBER_OF_CHILD").withIndex(39).ofType(Types.DECIMAL).withSize(20));
        addMetadata(occupation, ColumnMetadata.named("OCCUPATION").withIndex(28).ofType(Types.VARCHAR).withSize(100));
        addMetadata(otherLocal, ColumnMetadata.named("OTHER_LOCAL").withIndex(12).ofType(Types.VARCHAR).withSize(100));
        addMetadata(partyId, ColumnMetadata.named("PARTY_ID").withIndex(1).ofType(Types.VARCHAR).withSize(20).notNull());
        addMetadata(passportExpireDate, ColumnMetadata.named("PASSPORT_EXPIRE_DATE").withIndex(23).ofType(Types.DATE).withSize(10));
        addMetadata(passportNumber, ColumnMetadata.named("PASSPORT_NUMBER").withIndex(22).ofType(Types.VARCHAR).withSize(255));
        addMetadata(personalTitle, ColumnMetadata.named("PERSONAL_TITLE").withIndex(6).ofType(Types.VARCHAR).withSize(100));
        addMetadata(personPosition, ColumnMetadata.named("PERSON_POSITION").withIndex(45).ofType(Types.VARCHAR).withSize(100));
        addMetadata(residenceStatusEnumId, ColumnMetadata.named("RESIDENCE_STATUS_ENUM_ID").withIndex(27).ofType(Types.VARCHAR).withSize(20));
        addMetadata(salutation, ColumnMetadata.named("SALUTATION").withIndex(2).ofType(Types.VARCHAR).withSize(100));
        addMetadata(socialSecurityNumber, ColumnMetadata.named("SOCIAL_SECURITY_NUMBER").withIndex(21).ofType(Types.VARCHAR).withSize(255));
        addMetadata(suffix, ColumnMetadata.named("SUFFIX").withIndex(7).ofType(Types.VARCHAR).withSize(100));
        addMetadata(totalYearsWorkExperience, ColumnMetadata.named("TOTAL_YEARS_WORK_EXPERIENCE").withIndex(24).ofType(Types.DECIMAL).withSize(18).withDigits(6));
        addMetadata(weight, ColumnMetadata.named("WEIGHT").withIndex(18).ofType(Types.DECIMAL).withSize(18).withDigits(6));
        addMetadata(yearsWithEmployer, ColumnMetadata.named("YEARS_WITH_EMPLOYER").withIndex(29).ofType(Types.DECIMAL).withSize(20));
    }

}

