package it.mapsgroup.gzoom.querydsl.dto;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;

import com.querydsl.sql.ColumnMetadata;
import java.sql.Types;




/**
 * QWorkEffortTypeStatus is a Querydsl query type for WorkEffortTypeStatus
 */
@Generated("com.querydsl.sql.codegen.MetaDataSerializer")
public class QWorkEffortTypeStatus extends com.querydsl.sql.RelationalPathBase<WorkEffortTypeStatus> {

    private static final long serialVersionUID = 957731796;

    public static final QWorkEffortTypeStatus workEffortTypeStatus = new QWorkEffortTypeStatus("WORK_EFFORT_TYPE_STATUS");

    public final BooleanPath checkIsMandatory = createBoolean("checkIsMandatory");

    public final StringPath createdByUserLogin = createString("createdByUserLogin");

    public final DateTimePath<java.time.LocalDateTime> createdStamp = createDateTime("createdStamp", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> createdTxStamp = createDateTime("createdTxStamp", java.time.LocalDateTime.class);

    public final StringPath ctrlScoreEnumId = createString("ctrlScoreEnumId");

    public final StringPath currentStatusId = createString("currentStatusId");

    public final NumberPath<java.math.BigInteger> freqSoll = createNumber("freqSoll", java.math.BigInteger.class);

    public final StringPath glFiscalTypeId = createString("glFiscalTypeId");

    public final BooleanPath hasMandatoryAttr = createBoolean("hasMandatoryAttr");

    public final StringPath lastModifiedByUserLogin = createString("lastModifiedByUserLogin");

    public final DateTimePath<java.time.LocalDateTime> lastUpdatedStamp = createDateTime("lastUpdatedStamp", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> lastUpdatedTxStamp = createDateTime("lastUpdatedTxStamp", java.time.LocalDateTime.class);

    public final NumberPath<java.math.BigInteger> latSoll = createNumber("latSoll", java.math.BigInteger.class);

    public final StringPath managementRoleTypeId = createString("managementRoleTypeId");

    public final StringPath managWeStatusEnumId = createString("managWeStatusEnumId");

    public final StringPath nextStatusId = createString("nextStatusId");

    public final BooleanPath onlyResponsible = createBoolean("onlyResponsible");

    public final NumberPath<java.math.BigInteger> startSoll = createNumber("startSoll", java.math.BigInteger.class);

    public final BooleanPath sumVerify = createBoolean("sumVerify");

    public final StringPath workEffortTypeRootId = createString("workEffortTypeRootId");

    public final com.querydsl.sql.PrimaryKey<WorkEffortTypeStatus> primary = createPrimaryKey(currentStatusId, workEffortTypeRootId);

    public final com.querydsl.sql.ForeignKey<RoleType> wetsMgmrt = createForeignKey(managementRoleTypeId, "ROLE_TYPE_ID");

    public final com.querydsl.sql.ForeignKey<StatusItem> wetsCurrsi = createForeignKey(currentStatusId, "STATUS_ID");

    public final com.querydsl.sql.ForeignKey<WorkEffortType> wetsRootwet = createForeignKey(workEffortTypeRootId, "WORK_EFFORT_TYPE_ID");

    public final com.querydsl.sql.ForeignKey<StatusItem> wetsNextsi = createForeignKey(nextStatusId, "STATUS_ID");

    public QWorkEffortTypeStatus(String variable) {
        super(WorkEffortTypeStatus.class, forVariable(variable), "null", "WORK_EFFORT_TYPE_STATUS");
        addMetadata();
    }

    public QWorkEffortTypeStatus(String variable, String schema, String table) {
        super(WorkEffortTypeStatus.class, forVariable(variable), schema, table);
        addMetadata();
    }

    public QWorkEffortTypeStatus(String variable, String schema) {
        super(WorkEffortTypeStatus.class, forVariable(variable), schema, "WORK_EFFORT_TYPE_STATUS");
        addMetadata();
    }

    public QWorkEffortTypeStatus(Path<? extends WorkEffortTypeStatus> path) {
        super(path.getType(), path.getMetadata(), "null", "WORK_EFFORT_TYPE_STATUS");
        addMetadata();
    }

    public QWorkEffortTypeStatus(PathMetadata metadata) {
        super(WorkEffortTypeStatus.class, metadata, "null", "WORK_EFFORT_TYPE_STATUS");
        addMetadata();
    }

    public void addMetadata() {
        addMetadata(checkIsMandatory, ColumnMetadata.named("CHECK_IS_MANDATORY").withIndex(16).ofType(Types.CHAR).withSize(1));
        addMetadata(createdByUserLogin, ColumnMetadata.named("CREATED_BY_USER_LOGIN").withIndex(11).ofType(Types.VARCHAR).withSize(250));
        addMetadata(createdStamp, ColumnMetadata.named("CREATED_STAMP").withIndex(14).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(createdTxStamp, ColumnMetadata.named("CREATED_TX_STAMP").withIndex(15).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(ctrlScoreEnumId, ColumnMetadata.named("CTRL_SCORE_ENUM_ID").withIndex(9).ofType(Types.VARCHAR).withSize(20));
        addMetadata(currentStatusId, ColumnMetadata.named("CURRENT_STATUS_ID").withIndex(2).ofType(Types.VARCHAR).withSize(20).notNull());
        addMetadata(freqSoll, ColumnMetadata.named("FREQ_SOLL").withIndex(18).ofType(Types.DECIMAL).withSize(20));
        addMetadata(glFiscalTypeId, ColumnMetadata.named("GL_FISCAL_TYPE_ID").withIndex(4).ofType(Types.VARCHAR).withSize(20));
        addMetadata(hasMandatoryAttr, ColumnMetadata.named("HAS_MANDATORY_ATTR").withIndex(7).ofType(Types.CHAR).withSize(1));
        addMetadata(lastModifiedByUserLogin, ColumnMetadata.named("LAST_MODIFIED_BY_USER_LOGIN").withIndex(10).ofType(Types.VARCHAR).withSize(250));
        addMetadata(lastUpdatedStamp, ColumnMetadata.named("LAST_UPDATED_STAMP").withIndex(12).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(lastUpdatedTxStamp, ColumnMetadata.named("LAST_UPDATED_TX_STAMP").withIndex(13).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(latSoll, ColumnMetadata.named("LAT_SOLL").withIndex(19).ofType(Types.DECIMAL).withSize(20));
        addMetadata(managementRoleTypeId, ColumnMetadata.named("MANAGEMENT_ROLE_TYPE_ID").withIndex(3).ofType(Types.VARCHAR).withSize(20));
        addMetadata(managWeStatusEnumId, ColumnMetadata.named("MANAG_WE_STATUS_ENUM_ID").withIndex(6).ofType(Types.VARCHAR).withSize(20));
        addMetadata(nextStatusId, ColumnMetadata.named("NEXT_STATUS_ID").withIndex(5).ofType(Types.VARCHAR).withSize(20));
        addMetadata(onlyResponsible, ColumnMetadata.named("ONLY_RESPONSIBLE").withIndex(20).ofType(Types.CHAR).withSize(1));
        addMetadata(startSoll, ColumnMetadata.named("START_SOLL").withIndex(17).ofType(Types.DECIMAL).withSize(20));
        addMetadata(sumVerify, ColumnMetadata.named("SUM_VERIFY").withIndex(8).ofType(Types.CHAR).withSize(1));
        addMetadata(workEffortTypeRootId, ColumnMetadata.named("WORK_EFFORT_TYPE_ROOT_ID").withIndex(1).ofType(Types.VARCHAR).withSize(20).notNull());
    }

}

