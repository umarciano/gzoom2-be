package it.mapsgroup.gzoom.querydsl.dto;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;

import java.util.*;

import com.querydsl.sql.ColumnMetadata;
import java.sql.Types;




/**
 * QWorkEffortPartyAssignment is a Querydsl query type for WorkEffortPartyAssignment
 */
@Generated("com.querydsl.sql.codegen.MetaDataSerializer")
public class QWorkEffortPartyAssignment extends com.querydsl.sql.RelationalPathBase<WorkEffortPartyAssignment> {

    private static final long serialVersionUID = 1271627595;

    public static final QWorkEffortPartyAssignment workEffortPartyAssignment = new QWorkEffortPartyAssignment("WORK_EFFORT_PARTY_ASSIGNMENT");

    public final StringPath assignedByUserLoginId = createString("assignedByUserLoginId");

    public final StringPath availabilityStatusId = createString("availabilityStatusId");

    public final StringPath comments = createString("comments");

    public final StringPath createdByUserLogin = createString("createdByUserLogin");

    public final DateTimePath<java.time.LocalDateTime> createdStamp = createDateTime("createdStamp", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> createdTxStamp = createDateTime("createdTxStamp", java.time.LocalDateTime.class);

    public final StringPath delegateReasonEnumId = createString("delegateReasonEnumId");

    public final StringPath expectationEnumId = createString("expectationEnumId");

    public final StringPath facilityId = createString("facilityId");

    public final DateTimePath<java.time.LocalDateTime> fromDate = createDateTime("fromDate", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> fromDateFrom = createDateTime("fromDateFrom", java.time.LocalDateTime.class);

    public final BooleanPath isPosted = createBoolean("isPosted");

    public final StringPath lastModifiedByUserLogin = createString("lastModifiedByUserLogin");

    public final DateTimePath<java.time.LocalDateTime> lastUpdatedStamp = createDateTime("lastUpdatedStamp", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> lastUpdatedTxStamp = createDateTime("lastUpdatedTxStamp", java.time.LocalDateTime.class);

    public final BooleanPath mustRsvp = createBoolean("mustRsvp");

    public final StringPath partyId = createString("partyId");

    public final StringPath partyIdFrom = createString("partyIdFrom");

    public final NumberPath<java.math.BigDecimal> plannedHours = createNumber("plannedHours", java.math.BigDecimal.class);

    public final StringPath roleTypeId = createString("roleTypeId");

    public final StringPath roleTypeIdFrom = createString("roleTypeIdFrom");

    public final NumberPath<java.math.BigDecimal> roleTypeWeight = createNumber("roleTypeWeight", java.math.BigDecimal.class);

    public final NumberPath<java.math.BigDecimal> roleTypeWeightActual = createNumber("roleTypeWeightActual", java.math.BigDecimal.class);

    public final DateTimePath<java.time.LocalDateTime> statusDateTime = createDateTime("statusDateTime", java.time.LocalDateTime.class);

    public final StringPath statusId = createString("statusId");

    public final DateTimePath<java.time.LocalDateTime> thruDate = createDateTime("thruDate", java.time.LocalDateTime.class);

    public final StringPath workEffortId = createString("workEffortId");

    public final StringPath workEffortIdFrom = createString("workEffortIdFrom");

    public final StringPath workEffortMeasureId = createString("workEffortMeasureId");

    public final com.querydsl.sql.PrimaryKey<WorkEffortPartyAssignment> primary = createPrimaryKey(fromDate, partyId, roleTypeId, workEffortId);

    public final com.querydsl.sql.ForeignKey<StatusItem> wkeffPaStts = createForeignKey(statusId, "STATUS_ID");

    public final com.querydsl.sql.ForeignKey<UserLoginPersistent> wkeffPaAbusrlog = createForeignKey(assignedByUserLoginId, "USER_LOGIN_ID");

    public final com.querydsl.sql.ForeignKey<WorkEffort> wkeffPaWe = createForeignKey(workEffortId, "WORK_EFFORT_ID");

    public final com.querydsl.sql.ForeignKey<WorkEffortPartyAssignment> wepaFr = createForeignKey(Arrays.asList(workEffortIdFrom, partyIdFrom, roleTypeIdFrom, fromDateFrom), Arrays.asList("WORK_EFFORT_ID", "PARTY_ID", "ROLE_TYPE_ID", "FROM_DATE"));

    public final com.querydsl.sql.ForeignKey<StatusItem> wkeffPaAvstts = createForeignKey(availabilityStatusId, "STATUS_ID");

    public final com.querydsl.sql.ForeignKey<WorkEffortPartyAssignment> _wepaFr = createInvForeignKey(Arrays.asList(workEffortId, partyId, roleTypeId, fromDate), Arrays.asList("WORK_EFFORT_ID_FROM", "PARTY_ID_FROM", "ROLE_TYPE_ID_FROM", "FROM_DATE_FROM"));

    public QWorkEffortPartyAssignment(String variable) {
        super(WorkEffortPartyAssignment.class, forVariable(variable), "null", "WORK_EFFORT_PARTY_ASSIGNMENT");
        addMetadata();
    }

    public QWorkEffortPartyAssignment(String variable, String schema, String table) {
        super(WorkEffortPartyAssignment.class, forVariable(variable), schema, table);
        addMetadata();
    }

    public QWorkEffortPartyAssignment(String variable, String schema) {
        super(WorkEffortPartyAssignment.class, forVariable(variable), schema, "WORK_EFFORT_PARTY_ASSIGNMENT");
        addMetadata();
    }

    public QWorkEffortPartyAssignment(Path<? extends WorkEffortPartyAssignment> path) {
        super(path.getType(), path.getMetadata(), "null", "WORK_EFFORT_PARTY_ASSIGNMENT");
        addMetadata();
    }

    public QWorkEffortPartyAssignment(PathMetadata metadata) {
        super(WorkEffortPartyAssignment.class, metadata, "null", "WORK_EFFORT_PARTY_ASSIGNMENT");
        addMetadata();
    }

    public void addMetadata() {
        addMetadata(assignedByUserLoginId, ColumnMetadata.named("ASSIGNED_BY_USER_LOGIN_ID").withIndex(6).ofType(Types.VARCHAR).withSize(250));
        addMetadata(availabilityStatusId, ColumnMetadata.named("AVAILABILITY_STATUS_ID").withIndex(14).ofType(Types.VARCHAR).withSize(20));
        addMetadata(comments, ColumnMetadata.named("COMMENTS").withIndex(12).ofType(Types.VARCHAR).withSize(2000));
        addMetadata(createdByUserLogin, ColumnMetadata.named("CREATED_BY_USER_LOGIN").withIndex(28).ofType(Types.VARCHAR).withSize(250));
        addMetadata(createdStamp, ColumnMetadata.named("CREATED_STAMP").withIndex(17).ofType(Types.TIMESTAMP).withSize(19));
        addMetadata(createdTxStamp, ColumnMetadata.named("CREATED_TX_STAMP").withIndex(18).ofType(Types.TIMESTAMP).withSize(19));
        addMetadata(delegateReasonEnumId, ColumnMetadata.named("DELEGATE_REASON_ENUM_ID").withIndex(10).ofType(Types.VARCHAR).withSize(20));
        addMetadata(expectationEnumId, ColumnMetadata.named("EXPECTATION_ENUM_ID").withIndex(9).ofType(Types.VARCHAR).withSize(20));
        addMetadata(facilityId, ColumnMetadata.named("FACILITY_ID").withIndex(11).ofType(Types.VARCHAR).withSize(20));
        addMetadata(fromDate, ColumnMetadata.named("FROM_DATE").withIndex(4).ofType(Types.TIMESTAMP).withSize(19).notNull());
        addMetadata(fromDateFrom, ColumnMetadata.named("FROM_DATE_FROM").withIndex(23).ofType(Types.TIMESTAMP).withSize(19));
        addMetadata(isPosted, ColumnMetadata.named("IS_POSTED").withIndex(22).ofType(Types.CHAR).withSize(1));
        addMetadata(lastModifiedByUserLogin, ColumnMetadata.named("LAST_MODIFIED_BY_USER_LOGIN").withIndex(27).ofType(Types.VARCHAR).withSize(250));
        addMetadata(lastUpdatedStamp, ColumnMetadata.named("LAST_UPDATED_STAMP").withIndex(15).ofType(Types.TIMESTAMP).withSize(19));
        addMetadata(lastUpdatedTxStamp, ColumnMetadata.named("LAST_UPDATED_TX_STAMP").withIndex(16).ofType(Types.TIMESTAMP).withSize(19));
        addMetadata(mustRsvp, ColumnMetadata.named("MUST_RSVP").withIndex(13).ofType(Types.CHAR).withSize(1));
        addMetadata(partyId, ColumnMetadata.named("PARTY_ID").withIndex(2).ofType(Types.VARCHAR).withSize(20).notNull());
        addMetadata(partyIdFrom, ColumnMetadata.named("PARTY_ID_FROM").withIndex(25).ofType(Types.VARCHAR).withSize(20));
        addMetadata(plannedHours, ColumnMetadata.named("PLANNED_HOURS").withIndex(29).ofType(Types.DECIMAL).withSize(18).withDigits(6));
        addMetadata(roleTypeId, ColumnMetadata.named("ROLE_TYPE_ID").withIndex(3).ofType(Types.VARCHAR).withSize(20).notNull());
        addMetadata(roleTypeIdFrom, ColumnMetadata.named("ROLE_TYPE_ID_FROM").withIndex(26).ofType(Types.VARCHAR).withSize(20));
        addMetadata(roleTypeWeight, ColumnMetadata.named("ROLE_TYPE_WEIGHT").withIndex(19).ofType(Types.DECIMAL).withSize(18).withDigits(6));
        addMetadata(roleTypeWeightActual, ColumnMetadata.named("ROLE_TYPE_WEIGHT_ACTUAL").withIndex(20).ofType(Types.DECIMAL).withSize(18).withDigits(6));
        addMetadata(statusDateTime, ColumnMetadata.named("STATUS_DATE_TIME").withIndex(8).ofType(Types.TIMESTAMP).withSize(19));
        addMetadata(statusId, ColumnMetadata.named("STATUS_ID").withIndex(7).ofType(Types.VARCHAR).withSize(20));
        addMetadata(thruDate, ColumnMetadata.named("THRU_DATE").withIndex(5).ofType(Types.TIMESTAMP).withSize(19));
        addMetadata(workEffortId, ColumnMetadata.named("WORK_EFFORT_ID").withIndex(1).ofType(Types.VARCHAR).withSize(20).notNull());
        addMetadata(workEffortIdFrom, ColumnMetadata.named("WORK_EFFORT_ID_FROM").withIndex(24).ofType(Types.VARCHAR).withSize(20));
        addMetadata(workEffortMeasureId, ColumnMetadata.named("WORK_EFFORT_MEASURE_ID").withIndex(21).ofType(Types.VARCHAR).withSize(20));
    }

}

