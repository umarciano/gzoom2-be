package it.mapsgroup.gzoom.querydsl.dto;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;

import com.querydsl.sql.ColumnMetadata;
import java.sql.Types;




/**
 * QTimesheet is a Querydsl query type for Timesheet
 */
@Generated("com.querydsl.sql.codegen.MetaDataSerializer")
public class QTimesheet extends com.querydsl.sql.RelationalPathBase<Timesheet> {

    private static final long serialVersionUID = 292341447;

    public static final QTimesheet timesheet = new QTimesheet("TIMESHEET");

    public final NumberPath<java.math.BigDecimal> actualHours = createNumber("actualHours", java.math.BigDecimal.class);

    public final StringPath approvedByUserLoginId = createString("approvedByUserLoginId");

    public final StringPath clientPartyId = createString("clientPartyId");

    public final StringPath comments = createString("comments");

    public final NumberPath<java.math.BigDecimal> contractHours = createNumber("contractHours", java.math.BigDecimal.class);

    public final DateTimePath<java.time.LocalDateTime> createdStamp = createDateTime("createdStamp", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> createdTxStamp = createDateTime("createdTxStamp", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> fromDate = createDateTime("fromDate", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> lastUpdatedStamp = createDateTime("lastUpdatedStamp", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> lastUpdatedTxStamp = createDateTime("lastUpdatedTxStamp", java.time.LocalDateTime.class);

    public final StringPath partyId = createString("partyId");

    public final StringPath statusId = createString("statusId");

    public final DateTimePath<java.time.LocalDateTime> thruDate = createDateTime("thruDate", java.time.LocalDateTime.class);

    public final StringPath timesheetId = createString("timesheetId");

    public final BooleanPath transferFlag = createBoolean("transferFlag");

    public final com.querydsl.sql.PrimaryKey<Timesheet> primary = createPrimaryKey(timesheetId);

    public final com.querydsl.sql.ForeignKey<StatusItem> timesheetSts = createForeignKey(statusId, "STATUS_ID");

    public final com.querydsl.sql.ForeignKey<Party> timesheetPrty = createForeignKey(partyId, "PARTY_ID");

    public final com.querydsl.sql.ForeignKey<Party> timesheetCpty = createForeignKey(clientPartyId, "PARTY_ID");

    public final com.querydsl.sql.ForeignKey<UserLoginPersistent> timesheetAbUl = createForeignKey(approvedByUserLoginId, "USER_LOGIN_ID");

    public final com.querydsl.sql.ForeignKey<TimeEntry> _timeEntTsht = createInvForeignKey(timesheetId, "TIMESHEET_ID");

    public QTimesheet(String variable) {
        super(Timesheet.class, forVariable(variable), "null", "TIMESHEET");
        addMetadata();
    }

    public QTimesheet(String variable, String schema, String table) {
        super(Timesheet.class, forVariable(variable), schema, table);
        addMetadata();
    }

    public QTimesheet(String variable, String schema) {
        super(Timesheet.class, forVariable(variable), schema, "TIMESHEET");
        addMetadata();
    }

    public QTimesheet(Path<? extends Timesheet> path) {
        super(path.getType(), path.getMetadata(), "null", "TIMESHEET");
        addMetadata();
    }

    public QTimesheet(PathMetadata metadata) {
        super(Timesheet.class, metadata, "null", "TIMESHEET");
        addMetadata();
    }

    public void addMetadata() {
        addMetadata(actualHours, ColumnMetadata.named("ACTUAL_HOURS").withIndex(15).ofType(Types.DECIMAL).withSize(18).withDigits(6));
        addMetadata(approvedByUserLoginId, ColumnMetadata.named("APPROVED_BY_USER_LOGIN_ID").withIndex(7).ofType(Types.VARCHAR).withSize(250));
        addMetadata(clientPartyId, ColumnMetadata.named("CLIENT_PARTY_ID").withIndex(3).ofType(Types.VARCHAR).withSize(20));
        addMetadata(comments, ColumnMetadata.named("COMMENTS").withIndex(8).ofType(Types.VARCHAR).withSize(255));
        addMetadata(contractHours, ColumnMetadata.named("CONTRACT_HOURS").withIndex(14).ofType(Types.DECIMAL).withSize(18).withDigits(6));
        addMetadata(createdStamp, ColumnMetadata.named("CREATED_STAMP").withIndex(11).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(createdTxStamp, ColumnMetadata.named("CREATED_TX_STAMP").withIndex(12).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(fromDate, ColumnMetadata.named("FROM_DATE").withIndex(4).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(lastUpdatedStamp, ColumnMetadata.named("LAST_UPDATED_STAMP").withIndex(9).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(lastUpdatedTxStamp, ColumnMetadata.named("LAST_UPDATED_TX_STAMP").withIndex(10).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(partyId, ColumnMetadata.named("PARTY_ID").withIndex(2).ofType(Types.VARCHAR).withSize(20));
        addMetadata(statusId, ColumnMetadata.named("STATUS_ID").withIndex(6).ofType(Types.VARCHAR).withSize(20));
        addMetadata(thruDate, ColumnMetadata.named("THRU_DATE").withIndex(5).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(timesheetId, ColumnMetadata.named("TIMESHEET_ID").withIndex(1).ofType(Types.VARCHAR).withSize(20).notNull());
        addMetadata(transferFlag, ColumnMetadata.named("TRANSFER_FLAG").withIndex(13).ofType(Types.CHAR).withSize(1));
    }

}

