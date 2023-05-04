package it.mapsgroup.gzoom.querydsl.dto;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;

import com.querydsl.sql.ColumnMetadata;
import java.sql.Types;




/**
 * QPersonInterface is a Querydsl query type for PersonInterface
 */
@Generated("com.querydsl.sql.codegen.MetaDataSerializer")
public class QPersonInterface extends com.querydsl.sql.RelationalPathBase<PersonInterface> {

    private static final long serialVersionUID = 788382137;

    public static final QPersonInterface personInterface = new QPersonInterface("PERSON_INTERFACE");

    public final StringPath allocationOrgCode = createString("allocationOrgCode");

    public final StringPath allocationOrgComments = createString("allocationOrgComments");

    public final StringPath allocationOrgDescription = createString("allocationOrgDescription");

    public final DateTimePath<java.time.LocalDateTime> allocationOrgThruDate = createDateTime("allocationOrgThruDate", java.time.LocalDateTime.class);

    public final StringPath allocationRoleTypeId = createString("allocationRoleTypeId");

    public final StringPath approverCode = createString("approverCode");

    public final StringPath comments = createString("comments");

    public final StringPath contactMail = createString("contactMail");

    public final StringPath contactMobile = createString("contactMobile");

    public final DateTimePath<java.time.LocalDateTime> customDate01 = createDateTime("customDate01", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> customDate02 = createDateTime("customDate02", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> customDate03 = createDateTime("customDate03", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> customDate04 = createDateTime("customDate04", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> customDate05 = createDateTime("customDate05", java.time.LocalDateTime.class);

    public final StringPath customText01 = createString("customText01");

    public final StringPath customText02 = createString("customText02");

    public final StringPath customText03 = createString("customText03");

    public final StringPath customText04 = createString("customText04");

    public final StringPath customText05 = createString("customText05");

    public final NumberPath<java.math.BigDecimal> customValue01 = createNumber("customValue01", java.math.BigDecimal.class);

    public final NumberPath<java.math.BigDecimal> customValue02 = createNumber("customValue02", java.math.BigDecimal.class);

    public final NumberPath<java.math.BigDecimal> customValue03 = createNumber("customValue03", java.math.BigDecimal.class);

    public final NumberPath<java.math.BigDecimal> customValue04 = createNumber("customValue04", java.math.BigDecimal.class);

    public final NumberPath<java.math.BigDecimal> customValue05 = createNumber("customValue05", java.math.BigDecimal.class);

    public final StringPath dataSource = createString("dataSource");

    public final StringPath description = createString("description");

    public final NumberPath<java.math.BigDecimal> employmentAmount = createNumber("employmentAmount", java.math.BigDecimal.class);

    public final StringPath employmentOrgCode = createString("employmentOrgCode");

    public final StringPath employmentOrgComments = createString("employmentOrgComments");

    public final StringPath employmentOrgDescription = createString("employmentOrgDescription");

    public final DateTimePath<java.time.LocalDateTime> employmentOrgFromDate = createDateTime("employmentOrgFromDate", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> employmentOrgThruDate = createDateTime("employmentOrgThruDate", java.time.LocalDateTime.class);

    public final StringPath employmentRoleTypeId = createString("employmentRoleTypeId");

    public final DateTimePath<java.time.LocalDateTime> emplPositionTypeDate = createDateTime("emplPositionTypeDate", java.time.LocalDateTime.class);

    public final StringPath emplPositionTypeId = createString("emplPositionTypeId");

    public final StringPath evaluatorCode = createString("evaluatorCode");

    public final DateTimePath<java.time.LocalDateTime> evaluatorFromDate = createDateTime("evaluatorFromDate", java.time.LocalDateTime.class);

    public final StringPath firstName = createString("firstName");

    public final StringPath fiscalCode = createString("fiscalCode");

    public final DateTimePath<java.time.LocalDateTime> fromDate = createDateTime("fromDate", java.time.LocalDateTime.class);

    public final StringPath groupId = createString("groupId");

    public final StringPath id = createString("id");

    public final BooleanPath isEvalManager = createBoolean("isEvalManager");

    public final StringPath lastName = createString("lastName");

    public final StringPath personCode = createString("personCode");

    public final StringPath personRoleTypeId = createString("personRoleTypeId");

    public final StringPath qualifCode = createString("qualifCode");

    public final DateTimePath<java.time.LocalDateTime> qualifFromDate = createDateTime("qualifFromDate", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> refDate = createDateTime("refDate", java.time.LocalDateTime.class);

    public final NumberPath<java.math.BigInteger> seq = createNumber("seq", java.math.BigInteger.class);

    public final StringPath stato = createString("stato");

    public final DateTimePath<java.time.LocalDateTime> thruDate = createDateTime("thruDate", java.time.LocalDateTime.class);

    public final StringPath userLoginId = createString("userLoginId");

    public final StringPath workEffortAssignmentCode = createString("workEffortAssignmentCode");

    public final DateTimePath<java.time.LocalDateTime> workEffortDate = createDateTime("workEffortDate", java.time.LocalDateTime.class);

    public final com.querydsl.sql.PrimaryKey<PersonInterface> primary = createPrimaryKey(personCode);

    public QPersonInterface(String variable) {
        super(PersonInterface.class, forVariable(variable), "null", "PERSON_INTERFACE");
        addMetadata();
    }

    public QPersonInterface(String variable, String schema, String table) {
        super(PersonInterface.class, forVariable(variable), schema, table);
        addMetadata();
    }

    public QPersonInterface(String variable, String schema) {
        super(PersonInterface.class, forVariable(variable), schema, "PERSON_INTERFACE");
        addMetadata();
    }

    public QPersonInterface(Path<? extends PersonInterface> path) {
        super(path.getType(), path.getMetadata(), "null", "PERSON_INTERFACE");
        addMetadata();
    }

    public QPersonInterface(PathMetadata metadata) {
        super(PersonInterface.class, metadata, "null", "PERSON_INTERFACE");
        addMetadata();
    }

    public void addMetadata() {
        addMetadata(allocationOrgCode, ColumnMetadata.named("ALLOCATION_ORG_CODE").withIndex(20).ofType(Types.VARCHAR).withSize(20));
        addMetadata(allocationOrgComments, ColumnMetadata.named("ALLOCATION_ORG_COMMENTS").withIndex(22).ofType(Types.VARCHAR).withSize(255));
        addMetadata(allocationOrgDescription, ColumnMetadata.named("ALLOCATION_ORG_DESCRIPTION").withIndex(34).ofType(Types.VARCHAR).withSize(255));
        addMetadata(allocationOrgThruDate, ColumnMetadata.named("ALLOCATION_ORG_THRU_DATE").withIndex(35).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(allocationRoleTypeId, ColumnMetadata.named("ALLOCATION_ROLE_TYPE_ID").withIndex(21).ofType(Types.VARCHAR).withSize(20));
        addMetadata(approverCode, ColumnMetadata.named("APPROVER_CODE").withIndex(30).ofType(Types.VARCHAR).withSize(20));
        addMetadata(comments, ColumnMetadata.named("COMMENTS").withIndex(14).ofType(Types.VARCHAR).withSize(255));
        addMetadata(contactMail, ColumnMetadata.named("CONTACT_MAIL").withIndex(25).ofType(Types.VARCHAR).withSize(255));
        addMetadata(contactMobile, ColumnMetadata.named("CONTACT_MOBILE").withIndex(26).ofType(Types.VARCHAR).withSize(60));
        addMetadata(customDate01, ColumnMetadata.named("CUSTOM_DATE01").withIndex(45).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(customDate02, ColumnMetadata.named("CUSTOM_DATE02").withIndex(46).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(customDate03, ColumnMetadata.named("CUSTOM_DATE03").withIndex(47).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(customDate04, ColumnMetadata.named("CUSTOM_DATE04").withIndex(48).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(customDate05, ColumnMetadata.named("CUSTOM_DATE05").withIndex(49).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(customText01, ColumnMetadata.named("CUSTOM_TEXT01").withIndex(40).ofType(Types.VARCHAR).withSize(255));
        addMetadata(customText02, ColumnMetadata.named("CUSTOM_TEXT02").withIndex(41).ofType(Types.VARCHAR).withSize(255));
        addMetadata(customText03, ColumnMetadata.named("CUSTOM_TEXT03").withIndex(42).ofType(Types.VARCHAR).withSize(255));
        addMetadata(customText04, ColumnMetadata.named("CUSTOM_TEXT04").withIndex(43).ofType(Types.VARCHAR).withSize(255));
        addMetadata(customText05, ColumnMetadata.named("CUSTOM_TEXT05").withIndex(44).ofType(Types.VARCHAR).withSize(255));
        addMetadata(customValue01, ColumnMetadata.named("CUSTOM_VALUE01").withIndex(50).ofType(Types.DECIMAL).withSize(18).withDigits(6));
        addMetadata(customValue02, ColumnMetadata.named("CUSTOM_VALUE02").withIndex(51).ofType(Types.DECIMAL).withSize(18).withDigits(6));
        addMetadata(customValue03, ColumnMetadata.named("CUSTOM_VALUE03").withIndex(52).ofType(Types.DECIMAL).withSize(18).withDigits(6));
        addMetadata(customValue04, ColumnMetadata.named("CUSTOM_VALUE04").withIndex(53).ofType(Types.DECIMAL).withSize(18).withDigits(6));
        addMetadata(customValue05, ColumnMetadata.named("CUSTOM_VALUE05").withIndex(54).ofType(Types.DECIMAL).withSize(18).withDigits(6));
        addMetadata(dataSource, ColumnMetadata.named("DATA_SOURCE").withIndex(3).ofType(Types.VARCHAR).withSize(20));
        addMetadata(description, ColumnMetadata.named("DESCRIPTION").withIndex(15).ofType(Types.VARCHAR).withSize(255));
        addMetadata(employmentAmount, ColumnMetadata.named("EMPLOYMENT_AMOUNT").withIndex(8).ofType(Types.DECIMAL).withSize(18).withDigits(2));
        addMetadata(employmentOrgCode, ColumnMetadata.named("EMPLOYMENT_ORG_CODE").withIndex(12).ofType(Types.VARCHAR).withSize(20));
        addMetadata(employmentOrgComments, ColumnMetadata.named("EMPLOYMENT_ORG_COMMENTS").withIndex(19).ofType(Types.VARCHAR).withSize(255));
        addMetadata(employmentOrgDescription, ColumnMetadata.named("EMPLOYMENT_ORG_DESCRIPTION").withIndex(33).ofType(Types.VARCHAR).withSize(255));
        addMetadata(employmentOrgFromDate, ColumnMetadata.named("EMPLOYMENT_ORG_FROM_DATE").withIndex(37).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(employmentOrgThruDate, ColumnMetadata.named("EMPLOYMENT_ORG_THRU_DATE").withIndex(38).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(employmentRoleTypeId, ColumnMetadata.named("EMPLOYMENT_ROLE_TYPE_ID").withIndex(18).ofType(Types.VARCHAR).withSize(20));
        addMetadata(emplPositionTypeDate, ColumnMetadata.named("EMPL_POSITION_TYPE_DATE").withIndex(39).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(emplPositionTypeId, ColumnMetadata.named("EMPL_POSITION_TYPE_ID").withIndex(9).ofType(Types.VARCHAR).withSize(20));
        addMetadata(evaluatorCode, ColumnMetadata.named("EVALUATOR_CODE").withIndex(23).ofType(Types.VARCHAR).withSize(20));
        addMetadata(evaluatorFromDate, ColumnMetadata.named("EVALUATOR_FROM_DATE").withIndex(24).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(firstName, ColumnMetadata.named("FIRST_NAME").withIndex(6).ofType(Types.VARCHAR).withSize(100).notNull());
        addMetadata(fiscalCode, ColumnMetadata.named("FISCAL_CODE").withIndex(36).ofType(Types.VARCHAR).withSize(20));
        addMetadata(fromDate, ColumnMetadata.named("FROM_DATE").withIndex(16).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(groupId, ColumnMetadata.named("GROUP_ID").withIndex(28).ofType(Types.VARCHAR).withSize(20));
        addMetadata(id, ColumnMetadata.named("ID").withIndex(1).ofType(Types.VARCHAR).withSize(20));
        addMetadata(isEvalManager, ColumnMetadata.named("IS_EVAL_MANAGER").withIndex(29).ofType(Types.CHAR).withSize(1));
        addMetadata(lastName, ColumnMetadata.named("LAST_NAME").withIndex(7).ofType(Types.VARCHAR).withSize(100).notNull());
        addMetadata(personCode, ColumnMetadata.named("PERSON_CODE").withIndex(5).ofType(Types.VARCHAR).withSize(20).notNull());
        addMetadata(personRoleTypeId, ColumnMetadata.named("PERSON_ROLE_TYPE_ID").withIndex(13).ofType(Types.VARCHAR).withSize(20));
        addMetadata(qualifCode, ColumnMetadata.named("QUALIF_CODE").withIndex(10).ofType(Types.VARCHAR).withSize(20));
        addMetadata(qualifFromDate, ColumnMetadata.named("QUALIF_FROM_DATE").withIndex(11).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(refDate, ColumnMetadata.named("REF_DATE").withIndex(4).ofType(Types.TIMESTAMP).withSize(26).notNull());
        addMetadata(seq, ColumnMetadata.named("SEQ").withIndex(55).ofType(Types.DECIMAL).withSize(20));
        addMetadata(stato, ColumnMetadata.named("STATO").withIndex(2).ofType(Types.VARCHAR).withSize(20));
        addMetadata(thruDate, ColumnMetadata.named("THRU_DATE").withIndex(17).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(userLoginId, ColumnMetadata.named("USER_LOGIN_ID").withIndex(27).ofType(Types.VARCHAR).withSize(250));
        addMetadata(workEffortAssignmentCode, ColumnMetadata.named("WORK_EFFORT_ASSIGNMENT_CODE").withIndex(31).ofType(Types.VARCHAR).withSize(60));
        addMetadata(workEffortDate, ColumnMetadata.named("WORK_EFFORT_DATE").withIndex(32).ofType(Types.TIMESTAMP).withSize(26));
    }

}

