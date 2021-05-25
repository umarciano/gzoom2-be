package it.mapsgroup.gzoom.querydsl.dto;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;

import com.querydsl.sql.ColumnMetadata;
import java.sql.Types;




/**
 * QAllocationInterface is a Querydsl query type for AllocationInterface
 */
@Generated("com.querydsl.sql.codegen.MetaDataSerializer")
public class QAllocationInterface extends com.querydsl.sql.RelationalPathBase<AllocationInterface> {

    private static final long serialVersionUID = 1438180878;

    public static final QAllocationInterface allocationInterface = new QAllocationInterface("ALLOCATION_INTERFACE");

    public final DateTimePath<java.time.LocalDateTime> allocationFromDate = createDateTime("allocationFromDate", java.time.LocalDateTime.class);

    public final StringPath allocationOrgCode = createString("allocationOrgCode");

    public final StringPath allocationOrgComments = createString("allocationOrgComments");

    public final StringPath allocationOrgDescription = createString("allocationOrgDescription");

    public final StringPath allocationRoleTypeId = createString("allocationRoleTypeId");

    public final DateTimePath<java.time.LocalDateTime> allocationThruDate = createDateTime("allocationThruDate", java.time.LocalDateTime.class);

    public final NumberPath<java.math.BigInteger> allocationValue = createNumber("allocationValue", java.math.BigInteger.class);

    public final StringPath dataSource = createString("dataSource");

    public final StringPath id = createString("id");

    public final StringPath personCode = createString("personCode");

    public final StringPath personRoleTypeId = createString("personRoleTypeId");

    public final DateTimePath<java.time.LocalDateTime> refDate = createDateTime("refDate", java.time.LocalDateTime.class);

    public final StringPath stato = createString("stato");

    public final com.querydsl.sql.PrimaryKey<AllocationInterface> primary = createPrimaryKey(allocationFromDate, allocationOrgCode, dataSource, personCode, refDate);

    public QAllocationInterface(String variable) {
        super(AllocationInterface.class, forVariable(variable), "null", "ALLOCATION_INTERFACE");
        addMetadata();
    }

    public QAllocationInterface(String variable, String schema, String table) {
        super(AllocationInterface.class, forVariable(variable), schema, table);
        addMetadata();
    }

    public QAllocationInterface(String variable, String schema) {
        super(AllocationInterface.class, forVariable(variable), schema, "ALLOCATION_INTERFACE");
        addMetadata();
    }

    public QAllocationInterface(Path<? extends AllocationInterface> path) {
        super(path.getType(), path.getMetadata(), "null", "ALLOCATION_INTERFACE");
        addMetadata();
    }

    public QAllocationInterface(PathMetadata metadata) {
        super(AllocationInterface.class, metadata, "null", "ALLOCATION_INTERFACE");
        addMetadata();
    }

    public void addMetadata() {
        addMetadata(allocationFromDate, ColumnMetadata.named("ALLOCATION_FROM_DATE").withIndex(9).ofType(Types.TIMESTAMP).withSize(26).notNull());
        addMetadata(allocationOrgCode, ColumnMetadata.named("ALLOCATION_ORG_CODE").withIndex(8).ofType(Types.VARCHAR).withSize(20).notNull());
        addMetadata(allocationOrgComments, ColumnMetadata.named("ALLOCATION_ORG_COMMENTS").withIndex(11).ofType(Types.VARCHAR).withSize(255));
        addMetadata(allocationOrgDescription, ColumnMetadata.named("ALLOCATION_ORG_DESCRIPTION").withIndex(12).ofType(Types.VARCHAR).withSize(255));
        addMetadata(allocationRoleTypeId, ColumnMetadata.named("ALLOCATION_ROLE_TYPE_ID").withIndex(7).ofType(Types.VARCHAR).withSize(20));
        addMetadata(allocationThruDate, ColumnMetadata.named("ALLOCATION_THRU_DATE").withIndex(10).ofType(Types.TIMESTAMP).withSize(26).notNull());
        addMetadata(allocationValue, ColumnMetadata.named("ALLOCATION_VALUE").withIndex(13).ofType(Types.DECIMAL).withSize(20).notNull());
        addMetadata(dataSource, ColumnMetadata.named("DATA_SOURCE").withIndex(3).ofType(Types.VARCHAR).withSize(20).notNull());
        addMetadata(id, ColumnMetadata.named("ID").withIndex(1).ofType(Types.VARCHAR).withSize(20));
        addMetadata(personCode, ColumnMetadata.named("PERSON_CODE").withIndex(6).ofType(Types.VARCHAR).withSize(20).notNull());
        addMetadata(personRoleTypeId, ColumnMetadata.named("PERSON_ROLE_TYPE_ID").withIndex(5).ofType(Types.VARCHAR).withSize(20));
        addMetadata(refDate, ColumnMetadata.named("REF_DATE").withIndex(4).ofType(Types.TIMESTAMP).withSize(26).notNull());
        addMetadata(stato, ColumnMetadata.named("STATO").withIndex(2).ofType(Types.VARCHAR).withSize(20));
    }

}

