package it.mapsgroup.gzoom.querydsl.dto;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;

import com.querydsl.sql.ColumnMetadata;
import java.sql.Types;




/**
 * QTimeEntry is a Querydsl query type for TimeEntry
 */
@Generated("com.querydsl.sql.codegen.MetaDataSerializer")
public class QTimeEntry extends com.querydsl.sql.RelationalPathBase<TimeEntry> {

    private static final long serialVersionUID = 250053050;

    public static final QTimeEntry timeEntry = new QTimeEntry("TIME_ENTRY");

    public final StringPath comments = createString("comments");

    public final StringPath createdByUserLogin = createString("createdByUserLogin");

    public final DateTimePath<java.time.LocalDateTime> createdStamp = createDateTime("createdStamp", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> createdTxStamp = createDateTime("createdTxStamp", java.time.LocalDateTime.class);

    public final StringPath effortUomId = createString("effortUomId");

    public final DateTimePath<java.time.LocalDateTime> fromDate = createDateTime("fromDate", java.time.LocalDateTime.class);

    public final NumberPath<java.math.BigDecimal> hours = createNumber("hours", java.math.BigDecimal.class);

    public final StringPath invoiceId = createString("invoiceId");

    public final StringPath invoiceItemSeqId = createString("invoiceItemSeqId");

    public final StringPath jobId = createString("jobId");

    public final StringPath lastModifiedByUserLogin = createString("lastModifiedByUserLogin");

    public final DateTimePath<java.time.LocalDateTime> lastUpdatedStamp = createDateTime("lastUpdatedStamp", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> lastUpdatedTxStamp = createDateTime("lastUpdatedTxStamp", java.time.LocalDateTime.class);

    public final StringPath orderId = createString("orderId");

    public final StringPath partyId = createString("partyId");

    public final NumberPath<java.math.BigDecimal> percentage = createNumber("percentage", java.math.BigDecimal.class);

    public final NumberPath<java.math.BigDecimal> planHours = createNumber("planHours", java.math.BigDecimal.class);

    public final StringPath rateTypeId = createString("rateTypeId");

    public final DateTimePath<java.time.LocalDateTime> thruDate = createDateTime("thruDate", java.time.LocalDateTime.class);

    public final StringPath timeEntryId = createString("timeEntryId");

    public final StringPath timesheetId = createString("timesheetId");

    public final StringPath workEffortId = createString("workEffortId");

    public final com.querydsl.sql.PrimaryKey<TimeEntry> primary = createPrimaryKey(timeEntryId);

    public final com.querydsl.sql.ForeignKey<Timesheet> timeEntTsht = createForeignKey(timesheetId, "TIMESHEET_ID");

    public final com.querydsl.sql.ForeignKey<Party> timeEntPrty = createForeignKey(partyId, "PARTY_ID");

    public final com.querydsl.sql.ForeignKey<WorkEffort> timeEntWeff = createForeignKey(workEffortId, "WORK_EFFORT_ID");

    public QTimeEntry(String variable) {
        super(TimeEntry.class, forVariable(variable), "null", "TIME_ENTRY");
        addMetadata();
    }

    public QTimeEntry(String variable, String schema, String table) {
        super(TimeEntry.class, forVariable(variable), schema, table);
        addMetadata();
    }

    public QTimeEntry(String variable, String schema) {
        super(TimeEntry.class, forVariable(variable), schema, "TIME_ENTRY");
        addMetadata();
    }

    public QTimeEntry(Path<? extends TimeEntry> path) {
        super(path.getType(), path.getMetadata(), "null", "TIME_ENTRY");
        addMetadata();
    }

    public QTimeEntry(PathMetadata metadata) {
        super(TimeEntry.class, metadata, "null", "TIME_ENTRY");
        addMetadata();
    }

    public void addMetadata() {
        addMetadata(comments, ColumnMetadata.named("COMMENTS").withIndex(11).ofType(Types.VARCHAR).withSize(2000));
        addMetadata(createdByUserLogin, ColumnMetadata.named("CREATED_BY_USER_LOGIN").withIndex(18).ofType(Types.VARCHAR).withSize(250));
        addMetadata(createdStamp, ColumnMetadata.named("CREATED_STAMP").withIndex(14).ofType(Types.TIMESTAMP).withSize(19));
        addMetadata(createdTxStamp, ColumnMetadata.named("CREATED_TX_STAMP").withIndex(15).ofType(Types.TIMESTAMP).withSize(19));
        addMetadata(effortUomId, ColumnMetadata.named("EFFORT_UOM_ID").withIndex(16).ofType(Types.VARCHAR).withSize(20));
        addMetadata(fromDate, ColumnMetadata.named("FROM_DATE").withIndex(3).ofType(Types.TIMESTAMP).withSize(19));
        addMetadata(hours, ColumnMetadata.named("HOURS").withIndex(10).ofType(Types.DECIMAL).withSize(18).withDigits(6));
        addMetadata(invoiceId, ColumnMetadata.named("INVOICE_ID").withIndex(8).ofType(Types.VARCHAR).withSize(20));
        addMetadata(invoiceItemSeqId, ColumnMetadata.named("INVOICE_ITEM_SEQ_ID").withIndex(9).ofType(Types.VARCHAR).withSize(20));
        addMetadata(jobId, ColumnMetadata.named("JOB_ID").withIndex(19).ofType(Types.VARCHAR).withSize(20));
        addMetadata(lastModifiedByUserLogin, ColumnMetadata.named("LAST_MODIFIED_BY_USER_LOGIN").withIndex(17).ofType(Types.VARCHAR).withSize(250));
        addMetadata(lastUpdatedStamp, ColumnMetadata.named("LAST_UPDATED_STAMP").withIndex(12).ofType(Types.TIMESTAMP).withSize(19));
        addMetadata(lastUpdatedTxStamp, ColumnMetadata.named("LAST_UPDATED_TX_STAMP").withIndex(13).ofType(Types.TIMESTAMP).withSize(19));
        addMetadata(orderId, ColumnMetadata.named("ORDER_ID").withIndex(20).ofType(Types.VARCHAR).withSize(20));
        addMetadata(partyId, ColumnMetadata.named("PARTY_ID").withIndex(2).ofType(Types.VARCHAR).withSize(20));
        addMetadata(percentage, ColumnMetadata.named("PERCENTAGE").withIndex(22).ofType(Types.DECIMAL).withSize(18).withDigits(6));
        addMetadata(planHours, ColumnMetadata.named("PLAN_HOURS").withIndex(21).ofType(Types.DECIMAL).withSize(18).withDigits(6));
        addMetadata(rateTypeId, ColumnMetadata.named("RATE_TYPE_ID").withIndex(5).ofType(Types.VARCHAR).withSize(20));
        addMetadata(thruDate, ColumnMetadata.named("THRU_DATE").withIndex(4).ofType(Types.TIMESTAMP).withSize(19));
        addMetadata(timeEntryId, ColumnMetadata.named("TIME_ENTRY_ID").withIndex(1).ofType(Types.VARCHAR).withSize(20).notNull());
        addMetadata(timesheetId, ColumnMetadata.named("TIMESHEET_ID").withIndex(7).ofType(Types.VARCHAR).withSize(20));
        addMetadata(workEffortId, ColumnMetadata.named("WORK_EFFORT_ID").withIndex(6).ofType(Types.VARCHAR).withSize(20));
    }

}

