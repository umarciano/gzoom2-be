package it.mapsgroup.gzoom.querydsl.dto;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;

import java.util.*;

import com.querydsl.sql.ColumnMetadata;
import java.sql.Types;




/**
 * QPartyRelationship is a Querydsl query type for PartyRelationship
 */
@Generated("com.querydsl.sql.codegen.MetaDataSerializer")
public class QPartyRelationship extends com.querydsl.sql.RelationalPathBase<PartyRelationship> {

    private static final long serialVersionUID = -445981869;

    public static final QPartyRelationship partyRelationship = new QPartyRelationship("PARTY_RELATIONSHIP");

    public final StringPath comments = createString("comments");

    public final StringPath createdByUserLogin = createString("createdByUserLogin");

    public final DateTimePath<java.time.LocalDateTime> createdStamp = createDateTime("createdStamp", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> createdTxStamp = createDateTime("createdTxStamp", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> fromDate = createDateTime("fromDate", java.time.LocalDateTime.class);

    public final StringPath lastModifiedByUserLogin = createString("lastModifiedByUserLogin");

    public final DateTimePath<java.time.LocalDateTime> lastUpdatedStamp = createDateTime("lastUpdatedStamp", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> lastUpdatedTxStamp = createDateTime("lastUpdatedTxStamp", java.time.LocalDateTime.class);

    public final StringPath partyIdFrom = createString("partyIdFrom");

    public final StringPath partyIdTo = createString("partyIdTo");

    public final StringPath partyRelationshipTypeId = createString("partyRelationshipTypeId");

    public final StringPath permissionsEnumId = createString("permissionsEnumId");

    public final StringPath positionTitle = createString("positionTitle");

    public final StringPath priorityTypeId = createString("priorityTypeId");

    public final StringPath relationshipName = createString("relationshipName");

    public final NumberPath<java.math.BigDecimal> relationshipValue = createNumber("relationshipValue", java.math.BigDecimal.class);

    public final StringPath roleTypeIdFrom = createString("roleTypeIdFrom");

    public final StringPath roleTypeIdTo = createString("roleTypeIdTo");

    public final StringPath securityGroupId = createString("securityGroupId");

    public final StringPath statusId = createString("statusId");

    public final DateTimePath<java.time.LocalDateTime> thruDate = createDateTime("thruDate", java.time.LocalDateTime.class);

    public final StringPath valueUomId = createString("valueUomId");

    public final com.querydsl.sql.PrimaryKey<PartyRelationship> primary = createPrimaryKey(fromDate, partyIdFrom, partyIdTo, partyRelationshipTypeId, roleTypeIdFrom, roleTypeIdTo);

    public final com.querydsl.sql.ForeignKey<SecurityGroup> partyRelSecgrp = createForeignKey(securityGroupId, "GROUP_ID");

    public final com.querydsl.sql.ForeignKey<Uom> uomFk01 = createForeignKey(valueUomId, "UOM_ID");

    public final com.querydsl.sql.ForeignKey<PartyRole> partyRelFprole = createForeignKey(Arrays.asList(partyIdFrom, roleTypeIdFrom), Arrays.asList("PARTY_ID", "ROLE_TYPE_ID"));

    public final com.querydsl.sql.ForeignKey<StatusItem> partyRelStts = createForeignKey(statusId, "STATUS_ID");

    public final com.querydsl.sql.ForeignKey<PartyRole> partyRelTprole = createForeignKey(Arrays.asList(partyIdTo, roleTypeIdTo), Arrays.asList("PARTY_ID", "ROLE_TYPE_ID"));

    public QPartyRelationship(String variable) {
        super(PartyRelationship.class, forVariable(variable), "null", "PARTY_RELATIONSHIP");
        addMetadata();
    }

    public QPartyRelationship(String variable, String schema, String table) {
        super(PartyRelationship.class, forVariable(variable), schema, table);
        addMetadata();
    }

    public QPartyRelationship(String variable, String schema) {
        super(PartyRelationship.class, forVariable(variable), schema, "PARTY_RELATIONSHIP");
        addMetadata();
    }

    public QPartyRelationship(Path<? extends PartyRelationship> path) {
        super(path.getType(), path.getMetadata(), "null", "PARTY_RELATIONSHIP");
        addMetadata();
    }

    public QPartyRelationship(PathMetadata metadata) {
        super(PartyRelationship.class, metadata, "null", "PARTY_RELATIONSHIP");
        addMetadata();
    }

    public void addMetadata() {
        addMetadata(comments, ColumnMetadata.named("COMMENTS").withIndex(14).ofType(Types.VARCHAR).withSize(255));
        addMetadata(createdByUserLogin, ColumnMetadata.named("CREATED_BY_USER_LOGIN").withIndex(22).ofType(Types.VARCHAR).withSize(250));
        addMetadata(createdStamp, ColumnMetadata.named("CREATED_STAMP").withIndex(17).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(createdTxStamp, ColumnMetadata.named("CREATED_TX_STAMP").withIndex(18).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(fromDate, ColumnMetadata.named("FROM_DATE").withIndex(5).ofType(Types.TIMESTAMP).withSize(26).notNull());
        addMetadata(lastModifiedByUserLogin, ColumnMetadata.named("LAST_MODIFIED_BY_USER_LOGIN").withIndex(21).ofType(Types.VARCHAR).withSize(250));
        addMetadata(lastUpdatedStamp, ColumnMetadata.named("LAST_UPDATED_STAMP").withIndex(15).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(lastUpdatedTxStamp, ColumnMetadata.named("LAST_UPDATED_TX_STAMP").withIndex(16).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(partyIdFrom, ColumnMetadata.named("PARTY_ID_FROM").withIndex(1).ofType(Types.VARCHAR).withSize(20).notNull());
        addMetadata(partyIdTo, ColumnMetadata.named("PARTY_ID_TO").withIndex(2).ofType(Types.VARCHAR).withSize(20).notNull());
        addMetadata(partyRelationshipTypeId, ColumnMetadata.named("PARTY_RELATIONSHIP_TYPE_ID").withIndex(11).ofType(Types.VARCHAR).withSize(20).notNull());
        addMetadata(permissionsEnumId, ColumnMetadata.named("PERMISSIONS_ENUM_ID").withIndex(12).ofType(Types.VARCHAR).withSize(20));
        addMetadata(positionTitle, ColumnMetadata.named("POSITION_TITLE").withIndex(13).ofType(Types.VARCHAR).withSize(100));
        addMetadata(priorityTypeId, ColumnMetadata.named("PRIORITY_TYPE_ID").withIndex(10).ofType(Types.VARCHAR).withSize(20));
        addMetadata(relationshipName, ColumnMetadata.named("RELATIONSHIP_NAME").withIndex(8).ofType(Types.VARCHAR).withSize(255));
        addMetadata(relationshipValue, ColumnMetadata.named("RELATIONSHIP_VALUE").withIndex(20).ofType(Types.DECIMAL).withSize(18).withDigits(6));
        addMetadata(roleTypeIdFrom, ColumnMetadata.named("ROLE_TYPE_ID_FROM").withIndex(3).ofType(Types.VARCHAR).withSize(20).notNull());
        addMetadata(roleTypeIdTo, ColumnMetadata.named("ROLE_TYPE_ID_TO").withIndex(4).ofType(Types.VARCHAR).withSize(20).notNull());
        addMetadata(securityGroupId, ColumnMetadata.named("SECURITY_GROUP_ID").withIndex(9).ofType(Types.VARCHAR).withSize(20));
        addMetadata(statusId, ColumnMetadata.named("STATUS_ID").withIndex(7).ofType(Types.VARCHAR).withSize(20));
        addMetadata(thruDate, ColumnMetadata.named("THRU_DATE").withIndex(6).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(valueUomId, ColumnMetadata.named("VALUE_UOM_ID").withIndex(19).ofType(Types.VARCHAR).withSize(20));
    }

}

