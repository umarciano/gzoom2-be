package it.mapsgroup.gzoom.querydsl.dto;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;

import com.querydsl.sql.ColumnMetadata;
import java.sql.Types;




/**
 * QPersonInterfaceExt is a Querydsl query type for PersonInterfaceExt
 */
@Generated("com.querydsl.sql.codegen.MetaDataSerializer")
public class QPersonInterfaceExt extends com.querydsl.sql.RelationalPathBase<PersonInterfaceExt> {

    private static final long serialVersionUID = 1811138984;

    public static final QPersonInterfaceExt personInterfaceExt = new QPersonInterfaceExt("PERSON_INTERFACE_EXT");

    public final StringPath allocationOrgCode = createString("allocationOrgCode");

    public final StringPath allocationOrgComments = createString("allocationOrgComments");

    public final StringPath allocationOrgDescription = createString("allocationOrgDescription");

    public final DateTimePath<java.time.LocalDateTime> allocationOrgThruDate = createDateTime("allocationOrgThruDate", java.time.LocalDateTime.class);

    public final StringPath allocationRoleTypeId = createString("allocationRoleTypeId");

    public final StringPath approverCode = createString("approverCode");

    public final StringPath comments = createString("comments");

    public final StringPath contactMail = createString("contactMail");

    public final StringPath contactMobile = createString("contactMobile");

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

    public final BooleanPath isEvalManager = createBoolean("isEvalManager");

    public final StringPath lastName = createString("lastName");

    public final StringPath personCode = createString("personCode");

    public final StringPath personRoleTypeId = createString("personRoleTypeId");

    public final StringPath qualifCode = createString("qualifCode");

    public final DateTimePath<java.time.LocalDateTime> qualifFromDate = createDateTime("qualifFromDate", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> refDate = createDateTime("refDate", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> thruDate = createDateTime("thruDate", java.time.LocalDateTime.class);

    public final StringPath userLoginId = createString("userLoginId");

    public final StringPath workEffortAssignmentCode = createString("workEffortAssignmentCode");

    public final DateTimePath<java.time.LocalDateTime> workEffortDate = createDateTime("workEffortDate", java.time.LocalDateTime.class);

    public final com.querydsl.sql.PrimaryKey<PersonInterfaceExt> primary = createPrimaryKey(dataSource, personCode, refDate);

    public QPersonInterfaceExt(String variable) {
        super(PersonInterfaceExt.class, forVariable(variable), "null", "PERSON_INTERFACE_EXT");
        addMetadata();
    }

    public QPersonInterfaceExt(String variable, String schema, String table) {
        super(PersonInterfaceExt.class, forVariable(variable), schema, table);
        addMetadata();
    }

    public QPersonInterfaceExt(String variable, String schema) {
        super(PersonInterfaceExt.class, forVariable(variable), schema, "PERSON_INTERFACE_EXT");
        addMetadata();
    }

    public QPersonInterfaceExt(Path<? extends PersonInterfaceExt> path) {
        super(path.getType(), path.getMetadata(), "null", "PERSON_INTERFACE_EXT");
        addMetadata();
    }

    public QPersonInterfaceExt(PathMetadata metadata) {
        super(PersonInterfaceExt.class, metadata, "null", "PERSON_INTERFACE_EXT");
        addMetadata();
    }

    public void addMetadata() {
        addMetadata(allocationOrgCode, ColumnMetadata.named("ALLOCATION_ORG_CODE").withIndex(18).ofType(Types.VARCHAR).withSize(20));
        addMetadata(allocationOrgComments, ColumnMetadata.named("ALLOCATION_ORG_COMMENTS").withIndex(20).ofType(Types.VARCHAR).withSize(255));
        addMetadata(allocationOrgDescription, ColumnMetadata.named("ALLOCATION_ORG_DESCRIPTION").withIndex(32).ofType(Types.VARCHAR).withSize(255));
        addMetadata(allocationOrgThruDate, ColumnMetadata.named("ALLOCATION_ORG_THRU_DATE").withIndex(33).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(allocationRoleTypeId, ColumnMetadata.named("ALLOCATION_ROLE_TYPE_ID").withIndex(19).ofType(Types.VARCHAR).withSize(20));
        addMetadata(approverCode, ColumnMetadata.named("APPROVER_CODE").withIndex(28).ofType(Types.VARCHAR).withSize(20));
        addMetadata(comments, ColumnMetadata.named("COMMENTS").withIndex(12).ofType(Types.VARCHAR).withSize(255));
        addMetadata(contactMail, ColumnMetadata.named("CONTACT_MAIL").withIndex(23).ofType(Types.VARCHAR).withSize(255));
        addMetadata(contactMobile, ColumnMetadata.named("CONTACT_MOBILE").withIndex(24).ofType(Types.VARCHAR).withSize(60));
        addMetadata(dataSource, ColumnMetadata.named("DATA_SOURCE").withIndex(1).ofType(Types.VARCHAR).withSize(20).notNull());
        addMetadata(description, ColumnMetadata.named("DESCRIPTION").withIndex(13).ofType(Types.VARCHAR).withSize(255));
        addMetadata(employmentAmount, ColumnMetadata.named("EMPLOYMENT_AMOUNT").withIndex(6).ofType(Types.DECIMAL).withSize(18).withDigits(2));
        addMetadata(employmentOrgCode, ColumnMetadata.named("EMPLOYMENT_ORG_CODE").withIndex(10).ofType(Types.VARCHAR).withSize(20));
        addMetadata(employmentOrgComments, ColumnMetadata.named("EMPLOYMENT_ORG_COMMENTS").withIndex(17).ofType(Types.VARCHAR).withSize(255));
        addMetadata(employmentOrgDescription, ColumnMetadata.named("EMPLOYMENT_ORG_DESCRIPTION").withIndex(31).ofType(Types.VARCHAR).withSize(255));
        addMetadata(employmentOrgFromDate, ColumnMetadata.named("EMPLOYMENT_ORG_FROM_DATE").withIndex(35).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(employmentOrgThruDate, ColumnMetadata.named("EMPLOYMENT_ORG_THRU_DATE").withIndex(36).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(employmentRoleTypeId, ColumnMetadata.named("EMPLOYMENT_ROLE_TYPE_ID").withIndex(16).ofType(Types.VARCHAR).withSize(20));
        addMetadata(emplPositionTypeDate, ColumnMetadata.named("EMPL_POSITION_TYPE_DATE").withIndex(37).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(emplPositionTypeId, ColumnMetadata.named("EMPL_POSITION_TYPE_ID").withIndex(7).ofType(Types.VARCHAR).withSize(20));
        addMetadata(evaluatorCode, ColumnMetadata.named("EVALUATOR_CODE").withIndex(21).ofType(Types.VARCHAR).withSize(20));
        addMetadata(evaluatorFromDate, ColumnMetadata.named("EVALUATOR_FROM_DATE").withIndex(22).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(firstName, ColumnMetadata.named("FIRST_NAME").withIndex(4).ofType(Types.VARCHAR).withSize(100).notNull());
        addMetadata(fiscalCode, ColumnMetadata.named("FISCAL_CODE").withIndex(34).ofType(Types.VARCHAR).withSize(20));
        addMetadata(fromDate, ColumnMetadata.named("FROM_DATE").withIndex(14).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(groupId, ColumnMetadata.named("GROUP_ID").withIndex(26).ofType(Types.VARCHAR).withSize(20));
        addMetadata(isEvalManager, ColumnMetadata.named("IS_EVAL_MANAGER").withIndex(27).ofType(Types.CHAR).withSize(1));
        addMetadata(lastName, ColumnMetadata.named("LAST_NAME").withIndex(5).ofType(Types.VARCHAR).withSize(100).notNull());
        addMetadata(personCode, ColumnMetadata.named("PERSON_CODE").withIndex(3).ofType(Types.VARCHAR).withSize(20).notNull());
        addMetadata(personRoleTypeId, ColumnMetadata.named("PERSON_ROLE_TYPE_ID").withIndex(11).ofType(Types.VARCHAR).withSize(20));
        addMetadata(qualifCode, ColumnMetadata.named("QUALIF_CODE").withIndex(8).ofType(Types.VARCHAR).withSize(20));
        addMetadata(qualifFromDate, ColumnMetadata.named("QUALIF_FROM_DATE").withIndex(9).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(refDate, ColumnMetadata.named("REF_DATE").withIndex(2).ofType(Types.TIMESTAMP).withSize(26).notNull());
        addMetadata(thruDate, ColumnMetadata.named("THRU_DATE").withIndex(15).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(userLoginId, ColumnMetadata.named("USER_LOGIN_ID").withIndex(25).ofType(Types.VARCHAR).withSize(250));
        addMetadata(workEffortAssignmentCode, ColumnMetadata.named("WORK_EFFORT_ASSIGNMENT_CODE").withIndex(29).ofType(Types.VARCHAR).withSize(60));
        addMetadata(workEffortDate, ColumnMetadata.named("WORK_EFFORT_DATE").withIndex(30).ofType(Types.TIMESTAMP).withSize(26));
    }

}

