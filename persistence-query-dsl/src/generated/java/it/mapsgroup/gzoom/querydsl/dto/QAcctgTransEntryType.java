package it.mapsgroup.gzoom.querydsl.dto;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;

import com.querydsl.sql.ColumnMetadata;
import java.sql.Types;




/**
 * QAcctgTransEntryType is a Querydsl query type for AcctgTransEntryType
 */
@Generated("com.querydsl.sql.codegen.MetaDataSerializer")
public class QAcctgTransEntryType extends com.querydsl.sql.RelationalPathBase<AcctgTransEntryType> {

    private static final long serialVersionUID = -921549139;

    public static final QAcctgTransEntryType acctgTransEntryType = new QAcctgTransEntryType("ACCTG_TRANS_ENTRY_TYPE");

    public final StringPath acctgTransEntryTypeId = createString("acctgTransEntryTypeId");

    public final DateTimePath<java.time.LocalDateTime> createdStamp = createDateTime("createdStamp", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> createdTxStamp = createDateTime("createdTxStamp", java.time.LocalDateTime.class);

    public final StringPath description = createString("description");

    public final BooleanPath hasTable = createBoolean("hasTable");

    public final DateTimePath<java.time.LocalDateTime> lastUpdatedStamp = createDateTime("lastUpdatedStamp", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> lastUpdatedTxStamp = createDateTime("lastUpdatedTxStamp", java.time.LocalDateTime.class);

    public final StringPath parentTypeId = createString("parentTypeId");

    public final com.querydsl.sql.PrimaryKey<AcctgTransEntryType> primary = createPrimaryKey(acctgTransEntryTypeId);

    public final com.querydsl.sql.ForeignKey<AcctgTransEntryType> accttxeTypePar = createForeignKey(parentTypeId, "ACCTG_TRANS_ENTRY_TYPE_ID");

    public final com.querydsl.sql.ForeignKey<AcctgTransEntry> _accttxentAtet = createInvForeignKey(acctgTransEntryTypeId, "ACCTG_TRANS_ENTRY_TYPE_ID");

    public final com.querydsl.sql.ForeignKey<AcctgTransEntryType> _accttxeTypePar = createInvForeignKey(acctgTransEntryTypeId, "PARENT_TYPE_ID");

    public QAcctgTransEntryType(String variable) {
        super(AcctgTransEntryType.class, forVariable(variable), "null", "ACCTG_TRANS_ENTRY_TYPE");
        addMetadata();
    }

    public QAcctgTransEntryType(String variable, String schema, String table) {
        super(AcctgTransEntryType.class, forVariable(variable), schema, table);
        addMetadata();
    }

    public QAcctgTransEntryType(String variable, String schema) {
        super(AcctgTransEntryType.class, forVariable(variable), schema, "ACCTG_TRANS_ENTRY_TYPE");
        addMetadata();
    }

    public QAcctgTransEntryType(Path<? extends AcctgTransEntryType> path) {
        super(path.getType(), path.getMetadata(), "null", "ACCTG_TRANS_ENTRY_TYPE");
        addMetadata();
    }

    public QAcctgTransEntryType(PathMetadata metadata) {
        super(AcctgTransEntryType.class, metadata, "null", "ACCTG_TRANS_ENTRY_TYPE");
        addMetadata();
    }

    public void addMetadata() {
        addMetadata(acctgTransEntryTypeId, ColumnMetadata.named("ACCTG_TRANS_ENTRY_TYPE_ID").withIndex(1).ofType(Types.VARCHAR).withSize(20).notNull());
        addMetadata(createdStamp, ColumnMetadata.named("CREATED_STAMP").withIndex(7).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(createdTxStamp, ColumnMetadata.named("CREATED_TX_STAMP").withIndex(8).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(description, ColumnMetadata.named("DESCRIPTION").withIndex(4).ofType(Types.VARCHAR).withSize(255));
        addMetadata(hasTable, ColumnMetadata.named("HAS_TABLE").withIndex(3).ofType(Types.CHAR).withSize(1));
        addMetadata(lastUpdatedStamp, ColumnMetadata.named("LAST_UPDATED_STAMP").withIndex(5).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(lastUpdatedTxStamp, ColumnMetadata.named("LAST_UPDATED_TX_STAMP").withIndex(6).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(parentTypeId, ColumnMetadata.named("PARENT_TYPE_ID").withIndex(2).ofType(Types.VARCHAR).withSize(20));
    }

}

