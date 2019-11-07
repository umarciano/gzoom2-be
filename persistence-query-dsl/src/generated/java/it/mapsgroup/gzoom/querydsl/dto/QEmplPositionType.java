package it.mapsgroup.gzoom.querydsl.dto;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;

import com.querydsl.sql.ColumnMetadata;
import java.sql.Types;




/**
 * QEmplPositionType is a Querydsl query type for EmplPositionType
 */
@Generated("com.querydsl.sql.codegen.MetaDataSerializer")
public class QEmplPositionType extends com.querydsl.sql.RelationalPathBase<EmplPositionType> {

    private static final long serialVersionUID = 687741202;

    public static final QEmplPositionType emplPositionType = new QEmplPositionType("EMPL_POSITION_TYPE");

    public final StringPath createdByUserLogin = createString("createdByUserLogin");

    public final DateTimePath<java.time.LocalDateTime> createdStamp = createDateTime("createdStamp", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> createdTxStamp = createDateTime("createdTxStamp", java.time.LocalDateTime.class);

    public final StringPath description = createString("description");

    public final StringPath emplPositionTypeId = createString("emplPositionTypeId");

    public final BooleanPath hasTable = createBoolean("hasTable");

    public final StringPath lastModifiedByUserLogin = createString("lastModifiedByUserLogin");

    public final DateTimePath<java.time.LocalDateTime> lastUpdatedStamp = createDateTime("lastUpdatedStamp", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> lastUpdatedTxStamp = createDateTime("lastUpdatedTxStamp", java.time.LocalDateTime.class);

    public final StringPath parentTypeId = createString("parentTypeId");

    public final StringPath templateId = createString("templateId");

    public final com.querydsl.sql.PrimaryKey<EmplPositionType> primary = createPrimaryKey(emplPositionTypeId);

    public final com.querydsl.sql.ForeignKey<EmplPositionType> emplPosiTypPar = createForeignKey(parentTypeId, "EMPL_POSITION_TYPE_ID");

    public final com.querydsl.sql.ForeignKey<WorkEffort> _weEpt = createInvForeignKey(emplPositionTypeId, "EMPL_POSITION_TYPE_ID");

    public final com.querydsl.sql.ForeignKey<EmplPositionType> _emplPosiTypPar = createInvForeignKey(emplPositionTypeId, "PARENT_TYPE_ID");

    public QEmplPositionType(String variable) {
        super(EmplPositionType.class, forVariable(variable), "null", "EMPL_POSITION_TYPE");
        addMetadata();
    }

    public QEmplPositionType(String variable, String schema, String table) {
        super(EmplPositionType.class, forVariable(variable), schema, table);
        addMetadata();
    }

    public QEmplPositionType(String variable, String schema) {
        super(EmplPositionType.class, forVariable(variable), schema, "EMPL_POSITION_TYPE");
        addMetadata();
    }

    public QEmplPositionType(Path<? extends EmplPositionType> path) {
        super(path.getType(), path.getMetadata(), "null", "EMPL_POSITION_TYPE");
        addMetadata();
    }

    public QEmplPositionType(PathMetadata metadata) {
        super(EmplPositionType.class, metadata, "null", "EMPL_POSITION_TYPE");
        addMetadata();
    }

    public void addMetadata() {
        addMetadata(createdByUserLogin, ColumnMetadata.named("CREATED_BY_USER_LOGIN").withIndex(11).ofType(Types.VARCHAR).withSize(250));
        addMetadata(createdStamp, ColumnMetadata.named("CREATED_STAMP").withIndex(7).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(createdTxStamp, ColumnMetadata.named("CREATED_TX_STAMP").withIndex(8).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(description, ColumnMetadata.named("DESCRIPTION").withIndex(4).ofType(Types.VARCHAR).withSize(255));
        addMetadata(emplPositionTypeId, ColumnMetadata.named("EMPL_POSITION_TYPE_ID").withIndex(1).ofType(Types.VARCHAR).withSize(20).notNull());
        addMetadata(hasTable, ColumnMetadata.named("HAS_TABLE").withIndex(3).ofType(Types.CHAR).withSize(1));
        addMetadata(lastModifiedByUserLogin, ColumnMetadata.named("LAST_MODIFIED_BY_USER_LOGIN").withIndex(10).ofType(Types.VARCHAR).withSize(250));
        addMetadata(lastUpdatedStamp, ColumnMetadata.named("LAST_UPDATED_STAMP").withIndex(5).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(lastUpdatedTxStamp, ColumnMetadata.named("LAST_UPDATED_TX_STAMP").withIndex(6).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(parentTypeId, ColumnMetadata.named("PARENT_TYPE_ID").withIndex(2).ofType(Types.VARCHAR).withSize(20));
        addMetadata(templateId, ColumnMetadata.named("TEMPLATE_ID").withIndex(9).ofType(Types.VARCHAR).withSize(20));
    }

}

