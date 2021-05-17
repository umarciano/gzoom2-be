package it.mapsgroup.gzoom.querydsl.dto;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;

import com.querydsl.sql.ColumnMetadata;
import java.sql.Types;




/**
 * QEnumeration is a Querydsl query type for Enumeration
 */
@Generated("com.querydsl.sql.codegen.MetaDataSerializer")
public class QEnumeration extends com.querydsl.sql.RelationalPathBase<Enumeration> {

    private static final long serialVersionUID = -2100077284;

    public static final QEnumeration enumeration = new QEnumeration("ENUMERATION");

    public final DateTimePath<java.time.LocalDateTime> createdStamp = createDateTime("createdStamp", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> createdTxStamp = createDateTime("createdTxStamp", java.time.LocalDateTime.class);

    public final StringPath description = createString("description");

    public final StringPath enumCode = createString("enumCode");

    public final StringPath enumId = createString("enumId");

    public final StringPath enumTypeId = createString("enumTypeId");

    public final DateTimePath<java.time.LocalDateTime> lastUpdatedStamp = createDateTime("lastUpdatedStamp", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> lastUpdatedTxStamp = createDateTime("lastUpdatedTxStamp", java.time.LocalDateTime.class);

    public final StringPath sequenceId = createString("sequenceId");

    public final com.querydsl.sql.PrimaryKey<Enumeration> primary = createPrimaryKey(enumId);

    public final com.querydsl.sql.ForeignKey<EnumerationType> enumToType = createForeignKey(enumTypeId, "ENUM_TYPE_ID");

    public final com.querydsl.sql.ForeignKey<WorkEffortTypePeriod> _wtpStatus = createInvForeignKey(enumId, "STATUS_ENUM_ID");

    public final com.querydsl.sql.ForeignKey<WorkEffortType> _wetPeriodenumid = createInvForeignKey(enumId, "PERIOD_OPEN_ENUM_ID");

    public final com.querydsl.sql.ForeignKey<CommunicationEvent> _comEvntResenum = createInvForeignKey(enumId, "REASON_ENUM_ID");

    public final com.querydsl.sql.ForeignKey<WorkEffortType> _wetTotsonsenum = createInvForeignKey(enumId, "TOTAL_ENUM_ID_SONS");

    public final com.querydsl.sql.ForeignKey<WorkEffortAnalysis> _weaEnvFk = createInvForeignKey(enumId, "DATA_VISIBILITY");

    public final com.querydsl.sql.ForeignKey<WorkEffort> _weTotkpienum = createInvForeignKey(enumId, "TOTAL_ENUM_ID_KPI");

    public final com.querydsl.sql.ForeignKey<WorkEffortType> _wetEvalenumid = createInvForeignKey(enumId, "EVAL_ENUM_ID");

    public final com.querydsl.sql.ForeignKey<WorkEffortType> _wetFrameenumid = createInvForeignKey(enumId, "FRAME_ENUM_ID");

    public final com.querydsl.sql.ForeignKey<WorkEffortAnalysis> _weaEnaFk = createInvForeignKey(enumId, "AVAILABILITY_ID");

    public final com.querydsl.sql.ForeignKey<WorkEffort> _weTotsonsenum = createInvForeignKey(enumId, "TOTAL_ENUM_ID_SONS");

    public final com.querydsl.sql.ForeignKey<WorkEffort> _wkEffrtScEnum = createInvForeignKey(enumId, "SCOPE_ENUM_ID");

    public final com.querydsl.sql.ForeignKey<WorkEffortType> _wetTotassenum = createInvForeignKey(enumId, "TOTAL_ENUM_ID_ASSOC");

    public final com.querydsl.sql.ForeignKey<WorkEffort> _weTotassenum = createInvForeignKey(enumId, "TOTAL_ENUM_ID_ASSOC");

    public final com.querydsl.sql.ForeignKey<Person> _personEmpsEnum = createInvForeignKey(enumId, "EMPLOYMENT_STATUS_ENUM_ID");

    public final com.querydsl.sql.ForeignKey<Person> _personRessEnum = createInvForeignKey(enumId, "RESIDENCE_STATUS_ENUM_ID");

    public final com.querydsl.sql.ForeignKey<WorkEffortType> _wetLaytypenum = createInvForeignKey(enumId, "WE_LAYOUT_TYPE_ENUM_ID");

    public final com.querydsl.sql.ForeignKey<WorkEffortType> _wetTotkpienum = createInvForeignKey(enumId, "TOTAL_ENUM_ID_KPI");

    public final com.querydsl.sql.ForeignKey<StatusItem> _siActstsenum = createInvForeignKey(enumId, "ACT_ST_ENUM_ID");

    public final com.querydsl.sql.ForeignKey<WorkEffortPartyAssignment> _wkeffPaDelrEnm = createInvForeignKey(enumId, "DELEGATE_REASON_ENUM_ID");

    public final com.querydsl.sql.ForeignKey<Content> _contentPrivenm = createInvForeignKey(enumId, "PRIVILEGE_ENUM_ID");

    public final com.querydsl.sql.ForeignKey<WorkEffortTypeStatus> _wetsScoreenum = createInvForeignKey(enumId, "CTRL_SCORE_ENUM_ID");

    public final com.querydsl.sql.ForeignKey<WorkEffortPartyAssignment> _wkeffPaExpEnum = createInvForeignKey(enumId, "EXPECTATION_ENUM_ID");

    public final com.querydsl.sql.ForeignKey<WorkEffortTypeStatus> _wetsMantypenum = createInvForeignKey(enumId, "MANAG_WE_STATUS_ENUM_ID");

    public QEnumeration(String variable) {
        super(Enumeration.class, forVariable(variable), "null", "ENUMERATION");
        addMetadata();
    }

    public QEnumeration(String variable, String schema, String table) {
        super(Enumeration.class, forVariable(variable), schema, table);
        addMetadata();
    }

    public QEnumeration(String variable, String schema) {
        super(Enumeration.class, forVariable(variable), schema, "ENUMERATION");
        addMetadata();
    }

    public QEnumeration(Path<? extends Enumeration> path) {
        super(path.getType(), path.getMetadata(), "null", "ENUMERATION");
        addMetadata();
    }

    public QEnumeration(PathMetadata metadata) {
        super(Enumeration.class, metadata, "null", "ENUMERATION");
        addMetadata();
    }

    public void addMetadata() {
        addMetadata(createdStamp, ColumnMetadata.named("CREATED_STAMP").withIndex(8).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(createdTxStamp, ColumnMetadata.named("CREATED_TX_STAMP").withIndex(9).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(description, ColumnMetadata.named("DESCRIPTION").withIndex(5).ofType(Types.VARCHAR).withSize(255));
        addMetadata(enumCode, ColumnMetadata.named("ENUM_CODE").withIndex(3).ofType(Types.VARCHAR).withSize(60));
        addMetadata(enumId, ColumnMetadata.named("ENUM_ID").withIndex(1).ofType(Types.VARCHAR).withSize(20).notNull());
        addMetadata(enumTypeId, ColumnMetadata.named("ENUM_TYPE_ID").withIndex(2).ofType(Types.VARCHAR).withSize(20));
        addMetadata(lastUpdatedStamp, ColumnMetadata.named("LAST_UPDATED_STAMP").withIndex(6).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(lastUpdatedTxStamp, ColumnMetadata.named("LAST_UPDATED_TX_STAMP").withIndex(7).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(sequenceId, ColumnMetadata.named("SEQUENCE_ID").withIndex(4).ofType(Types.VARCHAR).withSize(20));
    }

}

