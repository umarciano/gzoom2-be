package it.mapsgroup.gzoom.querydsl.dto;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;

import com.querydsl.sql.ColumnMetadata;
import java.sql.Types;




/**
 * QAllocationInterfaceExt is a Querydsl query type for AllocationInterfaceExt
 */
@Generated("com.querydsl.sql.codegen.MetaDataSerializer")
public class QAllocationInterfaceExt extends com.querydsl.sql.RelationalPathBase<AllocationInterfaceExt> {

    private static final long serialVersionUID = -1747138253;

    public static final QAllocationInterfaceExt allocationInterfaceExt = new QAllocationInterfaceExt("ALLOCATION_INTERFACE_EXT");

    public final DateTimePath<java.time.LocalDateTime> allocationFromDate = createDateTime("allocationFromDate", java.time.LocalDateTime.class);

    public final StringPath allocationOrgCode = createString("allocationOrgCode");

    public final StringPath allocationOrgComments = createString("allocationOrgComments");

    public final StringPath allocationOrgDescription = createString("allocationOrgDescription");

    public final StringPath allocationRoleTypeId = createString("allocationRoleTypeId");

    public final DateTimePath<java.time.LocalDateTime> allocationThruDate = createDateTime("allocationThruDate", java.time.LocalDateTime.class);

    public final NumberPath<java.math.BigDecimal> allocationValue = createNumber("allocationValue", java.math.BigDecimal.class);

    public final StringPath dataSource = createString("dataSource");

    public final StringPath personCode = createString("personCode");

    public final StringPath personRoleTypeId = createString("personRoleTypeId");

    public final DateTimePath<java.time.LocalDateTime> refDate = createDateTime("refDate", java.time.LocalDateTime.class);

    public final com.querydsl.sql.PrimaryKey<AllocationInterfaceExt> primary = createPrimaryKey(allocationFromDate, allocationOrgCode, dataSource, personCode, refDate);

    public QAllocationInterfaceExt(String variable) {
        super(AllocationInterfaceExt.class, forVariable(variable), "null", "ALLOCATION_INTERFACE_EXT");
        addMetadata();
    }

    public QAllocationInterfaceExt(String variable, String schema, String table) {
        super(AllocationInterfaceExt.class, forVariable(variable), schema, table);
        addMetadata();
    }

    public QAllocationInterfaceExt(String variable, String schema) {
        super(AllocationInterfaceExt.class, forVariable(variable), schema, "ALLOCATION_INTERFACE_EXT");
        addMetadata();
    }

    public QAllocationInterfaceExt(Path<? extends AllocationInterfaceExt> path) {
        super(path.getType(), path.getMetadata(), "null", "ALLOCATION_INTERFACE_EXT");
        addMetadata();
    }

    public QAllocationInterfaceExt(PathMetadata metadata) {
        super(AllocationInterfaceExt.class, metadata, "null", "ALLOCATION_INTERFACE_EXT");
        addMetadata();
    }

    public void addMetadata() {
        addMetadata(allocationFromDate, ColumnMetadata.named("ALLOCATION_FROM_DATE").withIndex(7).ofType(Types.TIMESTAMP).withSize(26).notNull());
        addMetadata(allocationOrgCode, ColumnMetadata.named("ALLOCATION_ORG_CODE").withIndex(6).ofType(Types.VARCHAR).withSize(20).notNull());
        addMetadata(allocationOrgComments, ColumnMetadata.named("ALLOCATION_ORG_COMMENTS").withIndex(9).ofType(Types.VARCHAR).withSize(255));
        addMetadata(allocationOrgDescription, ColumnMetadata.named("ALLOCATION_ORG_DESCRIPTION").withIndex(10).ofType(Types.VARCHAR).withSize(255));
        addMetadata(allocationRoleTypeId, ColumnMetadata.named("ALLOCATION_ROLE_TYPE_ID").withIndex(5).ofType(Types.VARCHAR).withSize(20));
        addMetadata(allocationThruDate, ColumnMetadata.named("ALLOCATION_THRU_DATE").withIndex(8).ofType(Types.TIMESTAMP).withSize(26).notNull());
        addMetadata(allocationValue, ColumnMetadata.named("ALLOCATION_VALUE").withIndex(11).ofType(Types.DECIMAL).withSize(18).withDigits(2).notNull());
        addMetadata(dataSource, ColumnMetadata.named("DATA_SOURCE").withIndex(1).ofType(Types.VARCHAR).withSize(20).notNull());
        addMetadata(personCode, ColumnMetadata.named("PERSON_CODE").withIndex(4).ofType(Types.VARCHAR).withSize(20).notNull());
        addMetadata(personRoleTypeId, ColumnMetadata.named("PERSON_ROLE_TYPE_ID").withIndex(3).ofType(Types.VARCHAR).withSize(20));
        addMetadata(refDate, ColumnMetadata.named("REF_DATE").withIndex(2).ofType(Types.TIMESTAMP).withSize(26).notNull());
    }

}

