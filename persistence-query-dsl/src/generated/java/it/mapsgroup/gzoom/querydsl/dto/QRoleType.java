package it.mapsgroup.gzoom.querydsl.dto;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;

import com.querydsl.sql.ColumnMetadata;
import java.sql.Types;




/**
 * QRoleType is a Querydsl query type for RoleType
 */
@Generated("com.querydsl.sql.codegen.MetaDataSerializer")
public class QRoleType extends com.querydsl.sql.RelationalPathBase<RoleType> {

    private static final long serialVersionUID = 1265106459;

    public static final QRoleType roleType = new QRoleType("ROLE_TYPE");

    public final StringPath createdByUserLogin = createString("createdByUserLogin");

    public final DateTimePath<java.time.LocalDateTime> createdStamp = createDateTime("createdStamp", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> createdTxStamp = createDateTime("createdTxStamp", java.time.LocalDateTime.class);

    public final StringPath description = createString("description");

    public final StringPath descriptionLang = createString("descriptionLang");

    public final BooleanPath hasTable = createBoolean("hasTable");

    public final StringPath iconContentId = createString("iconContentId");

    public final StringPath lastModifiedByUserLogin = createString("lastModifiedByUserLogin");

    public final DateTimePath<java.time.LocalDateTime> lastUpdatedStamp = createDateTime("lastUpdatedStamp", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> lastUpdatedTxStamp = createDateTime("lastUpdatedTxStamp", java.time.LocalDateTime.class);

    public final StringPath parentTypeId = createString("parentTypeId");

    public final StringPath prevPartyTypeId = createString("prevPartyTypeId");

    public final StringPath roleTypeId = createString("roleTypeId");

    public final StringPath shortLabel = createString("shortLabel");

    public final StringPath shortLabelLang = createString("shortLabelLang");

    public final StringPath workEffortAssocTypeId = createString("workEffortAssocTypeId");

    public final StringPath workEffortPeriodId = createString("workEffortPeriodId");

    public final StringPath workEffortTypeId = createString("workEffortTypeId");

    public final com.querydsl.sql.PrimaryKey<RoleType> primary = createPrimaryKey(roleTypeId);

    public final com.querydsl.sql.ForeignKey<RoleType> roleTypePar = createForeignKey(parentTypeId, "ROLE_TYPE_ID");

    public final com.querydsl.sql.ForeignKey<PartyRole> _partyRleRole = createInvForeignKey(roleTypeId, "ROLE_TYPE_ID");

    public final com.querydsl.sql.ForeignKey<PartyContactMech> _partyCmechRole = createInvForeignKey(roleTypeId, "ROLE_TYPE_ID");

    public final com.querydsl.sql.ForeignKey<RoleType> _roleTypePar = createInvForeignKey(roleTypeId, "PARENT_TYPE_ID");

    public QRoleType(String variable) {
        super(RoleType.class, forVariable(variable), "null", "ROLE_TYPE");
        addMetadata();
    }

    public QRoleType(String variable, String schema, String table) {
        super(RoleType.class, forVariable(variable), schema, table);
        addMetadata();
    }

    public QRoleType(String variable, String schema) {
        super(RoleType.class, forVariable(variable), schema, "ROLE_TYPE");
        addMetadata();
    }

    public QRoleType(Path<? extends RoleType> path) {
        super(path.getType(), path.getMetadata(), "null", "ROLE_TYPE");
        addMetadata();
    }

    public QRoleType(PathMetadata metadata) {
        super(RoleType.class, metadata, "null", "ROLE_TYPE");
        addMetadata();
    }

    public void addMetadata() {
        addMetadata(createdByUserLogin, ColumnMetadata.named("CREATED_BY_USER_LOGIN").withIndex(13).ofType(Types.VARCHAR).withSize(250));
        addMetadata(createdStamp, ColumnMetadata.named("CREATED_STAMP").withIndex(7).ofType(Types.TIMESTAMP).withSize(19));
        addMetadata(createdTxStamp, ColumnMetadata.named("CREATED_TX_STAMP").withIndex(8).ofType(Types.TIMESTAMP).withSize(19));
        addMetadata(description, ColumnMetadata.named("DESCRIPTION").withIndex(4).ofType(Types.VARCHAR).withSize(255));
        addMetadata(descriptionLang, ColumnMetadata.named("DESCRIPTION_LANG").withIndex(17).ofType(Types.VARCHAR).withSize(255));
        addMetadata(hasTable, ColumnMetadata.named("HAS_TABLE").withIndex(3).ofType(Types.CHAR).withSize(1));
        addMetadata(iconContentId, ColumnMetadata.named("ICON_CONTENT_ID").withIndex(10).ofType(Types.VARCHAR).withSize(20));
        addMetadata(lastModifiedByUserLogin, ColumnMetadata.named("LAST_MODIFIED_BY_USER_LOGIN").withIndex(12).ofType(Types.VARCHAR).withSize(250));
        addMetadata(lastUpdatedStamp, ColumnMetadata.named("LAST_UPDATED_STAMP").withIndex(5).ofType(Types.TIMESTAMP).withSize(19));
        addMetadata(lastUpdatedTxStamp, ColumnMetadata.named("LAST_UPDATED_TX_STAMP").withIndex(6).ofType(Types.TIMESTAMP).withSize(19));
        addMetadata(parentTypeId, ColumnMetadata.named("PARENT_TYPE_ID").withIndex(2).ofType(Types.VARCHAR).withSize(20));
        addMetadata(prevPartyTypeId, ColumnMetadata.named("PREV_PARTY_TYPE_ID").withIndex(11).ofType(Types.VARCHAR).withSize(20));
        addMetadata(roleTypeId, ColumnMetadata.named("ROLE_TYPE_ID").withIndex(1).ofType(Types.VARCHAR).withSize(20).notNull());
        addMetadata(shortLabel, ColumnMetadata.named("SHORT_LABEL").withIndex(9).ofType(Types.VARCHAR).withSize(20));
        addMetadata(shortLabelLang, ColumnMetadata.named("SHORT_LABEL_LANG").withIndex(18).ofType(Types.VARCHAR).withSize(20));
        addMetadata(workEffortAssocTypeId, ColumnMetadata.named("WORK_EFFORT_ASSOC_TYPE_ID").withIndex(15).ofType(Types.VARCHAR).withSize(20));
        addMetadata(workEffortPeriodId, ColumnMetadata.named("WORK_EFFORT_PERIOD_ID").withIndex(16).ofType(Types.VARCHAR).withSize(20));
        addMetadata(workEffortTypeId, ColumnMetadata.named("WORK_EFFORT_TYPE_ID").withIndex(14).ofType(Types.VARCHAR).withSize(20));
    }

}

