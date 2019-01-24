package it.mapsgroup.gzoom.querydsl.dto;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;

import com.querydsl.sql.ColumnMetadata;
import java.sql.Types;




/**
 * QStatusItem is a Querydsl query type for StatusItem
 */
@Generated("com.querydsl.sql.codegen.MetaDataSerializer")
public class QStatusItem extends com.querydsl.sql.RelationalPathBase<StatusItem> {

    private static final long serialVersionUID = -977603408;

    public static final QStatusItem statusItem = new QStatusItem("STATUS_ITEM");

    public final StringPath actStEnumId = createString("actStEnumId");

    public final StringPath createdByUserLogin = createString("createdByUserLogin");

    public final DateTimePath<java.time.LocalDateTime> createdStamp = createDateTime("createdStamp", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> createdTxStamp = createDateTime("createdTxStamp", java.time.LocalDateTime.class);

    public final StringPath description = createString("description");

    public final StringPath descriptionLang = createString("descriptionLang");

    public final StringPath lastModifiedByUserLogin = createString("lastModifiedByUserLogin");

    public final DateTimePath<java.time.LocalDateTime> lastUpdatedStamp = createDateTime("lastUpdatedStamp", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> lastUpdatedTxStamp = createDateTime("lastUpdatedTxStamp", java.time.LocalDateTime.class);

    public final StringPath sequenceId = createString("sequenceId");

    public final StringPath statusCode = createString("statusCode");

    public final StringPath statusId = createString("statusId");

    public final StringPath statusTypeId = createString("statusTypeId");

    public final com.querydsl.sql.PrimaryKey<StatusItem> primary = createPrimaryKey(statusId);

    public final com.querydsl.sql.ForeignKey<WorkEffort> _wkEffrtCurstts = createInvForeignKey(statusId, "CURRENT_STATUS_ID");

    public final com.querydsl.sql.ForeignKey<Party> _partyStatusitm = createInvForeignKey(statusId, "STATUS_ID");

    public final com.querydsl.sql.ForeignKey<WorkEffortPartyAssignment> _wkeffPaAvstts = createInvForeignKey(statusId, "AVAILABILITY_STATUS_ID");

    public final com.querydsl.sql.ForeignKey<WorkEffortTypeStatus> _wetsCurrsi = createInvForeignKey(statusId, "CURRENT_STATUS_ID");

    public final com.querydsl.sql.ForeignKey<WorkEffortPartyAssignment> _wkeffPaStts = createInvForeignKey(statusId, "STATUS_ID");

    public final com.querydsl.sql.ForeignKey<Timesheet> _timesheetSts = createInvForeignKey(statusId, "STATUS_ID");

    public final com.querydsl.sql.ForeignKey<WorkEffortTypeStatus> _wetsNextsi = createInvForeignKey(statusId, "NEXT_STATUS_ID");

    public final com.querydsl.sql.ForeignKey<PartyRelationship> _partyRelStts = createInvForeignKey(statusId, "STATUS_ID");

    public final com.querydsl.sql.ForeignKey<DataResource> _dtrsrcStatus = createInvForeignKey(statusId, "STATUS_ID");

    public final com.querydsl.sql.ForeignKey<Content> _contentStatus = createInvForeignKey(statusId, "STATUS_ID");

    public QStatusItem(String variable) {
        super(StatusItem.class, forVariable(variable), "null", "STATUS_ITEM");
        addMetadata();
    }

    public QStatusItem(String variable, String schema, String table) {
        super(StatusItem.class, forVariable(variable), schema, table);
        addMetadata();
    }

    public QStatusItem(String variable, String schema) {
        super(StatusItem.class, forVariable(variable), schema, "STATUS_ITEM");
        addMetadata();
    }

    public QStatusItem(Path<? extends StatusItem> path) {
        super(path.getType(), path.getMetadata(), "null", "STATUS_ITEM");
        addMetadata();
    }

    public QStatusItem(PathMetadata metadata) {
        super(StatusItem.class, metadata, "null", "STATUS_ITEM");
        addMetadata();
    }

    public void addMetadata() {
        addMetadata(actStEnumId, ColumnMetadata.named("ACT_ST_ENUM_ID").withIndex(10).ofType(Types.VARCHAR).withSize(20));
        addMetadata(createdByUserLogin, ColumnMetadata.named("CREATED_BY_USER_LOGIN").withIndex(12).ofType(Types.VARCHAR).withSize(250));
        addMetadata(createdStamp, ColumnMetadata.named("CREATED_STAMP").withIndex(8).ofType(Types.TIMESTAMP).withSize(19));
        addMetadata(createdTxStamp, ColumnMetadata.named("CREATED_TX_STAMP").withIndex(9).ofType(Types.TIMESTAMP).withSize(19));
        addMetadata(description, ColumnMetadata.named("DESCRIPTION").withIndex(5).ofType(Types.VARCHAR).withSize(255));
        addMetadata(descriptionLang, ColumnMetadata.named("DESCRIPTION_LANG").withIndex(13).ofType(Types.VARCHAR).withSize(255));
        addMetadata(lastModifiedByUserLogin, ColumnMetadata.named("LAST_MODIFIED_BY_USER_LOGIN").withIndex(11).ofType(Types.VARCHAR).withSize(250));
        addMetadata(lastUpdatedStamp, ColumnMetadata.named("LAST_UPDATED_STAMP").withIndex(6).ofType(Types.TIMESTAMP).withSize(19));
        addMetadata(lastUpdatedTxStamp, ColumnMetadata.named("LAST_UPDATED_TX_STAMP").withIndex(7).ofType(Types.TIMESTAMP).withSize(19));
        addMetadata(sequenceId, ColumnMetadata.named("SEQUENCE_ID").withIndex(4).ofType(Types.VARCHAR).withSize(20));
        addMetadata(statusCode, ColumnMetadata.named("STATUS_CODE").withIndex(3).ofType(Types.VARCHAR).withSize(60));
        addMetadata(statusId, ColumnMetadata.named("STATUS_ID").withIndex(1).ofType(Types.VARCHAR).withSize(20).notNull());
        addMetadata(statusTypeId, ColumnMetadata.named("STATUS_TYPE_ID").withIndex(2).ofType(Types.VARCHAR).withSize(20));
    }

}

