package it.mapsgroup.gzoom.querydsl.dto;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;

import com.querydsl.sql.ColumnMetadata;
import java.sql.Types;




/**
 * QOrganizationInterface is a Querydsl query type for OrganizationInterface
 */
@Generated("com.querydsl.sql.codegen.MetaDataSerializer")
public class QOrganizationInterface extends com.querydsl.sql.RelationalPathBase<OrganizationInterface> {

    private static final long serialVersionUID = 2038519259;

    public static final QOrganizationInterface organizationInterface = new QOrganizationInterface("ORGANIZATION_INTERFACE");

    public final StringPath dataSource = createString("dataSource");

    public final StringPath description = createString("description");

    public final StringPath descriptionLang = createString("descriptionLang");

    public final StringPath id = createString("id");

    public final StringPath longDescription = createString("longDescription");

    public final StringPath longDescriptionLang = createString("longDescriptionLang");

    public final StringPath orgCode = createString("orgCode");

    public final StringPath orgRoleTypeId = createString("orgRoleTypeId");

    public final DateTimePath<java.time.LocalDateTime> parentFromDate = createDateTime("parentFromDate", java.time.LocalDateTime.class);

    public final StringPath parentOrgCode = createString("parentOrgCode");

    public final StringPath parentRoleTypeId = createString("parentRoleTypeId");

    public final DateTimePath<java.time.LocalDateTime> refDate = createDateTime("refDate", java.time.LocalDateTime.class);

    public final StringPath responsibleCode = createString("responsibleCode");

    public final StringPath responsibleComments = createString("responsibleComments");

    public final DateTimePath<java.time.LocalDateTime> responsibleFromDate = createDateTime("responsibleFromDate", java.time.LocalDateTime.class);

    public final StringPath responsibleRoleTypeId = createString("responsibleRoleTypeId");

    public final DateTimePath<java.time.LocalDateTime> responsibleThruDate = createDateTime("responsibleThruDate", java.time.LocalDateTime.class);

    public final StringPath stato = createString("stato");

    public final DateTimePath<java.time.LocalDateTime> thruDate = createDateTime("thruDate", java.time.LocalDateTime.class);

    public final StringPath vatCode = createString("vatCode");

    public final com.querydsl.sql.PrimaryKey<OrganizationInterface> primary = createPrimaryKey(orgCode);

    public QOrganizationInterface(String variable) {
        super(OrganizationInterface.class, forVariable(variable), "null", "ORGANIZATION_INTERFACE");
        addMetadata();
    }

    public QOrganizationInterface(String variable, String schema, String table) {
        super(OrganizationInterface.class, forVariable(variable), schema, table);
        addMetadata();
    }

    public QOrganizationInterface(String variable, String schema) {
        super(OrganizationInterface.class, forVariable(variable), schema, "ORGANIZATION_INTERFACE");
        addMetadata();
    }

    public QOrganizationInterface(Path<? extends OrganizationInterface> path) {
        super(path.getType(), path.getMetadata(), "null", "ORGANIZATION_INTERFACE");
        addMetadata();
    }

    public QOrganizationInterface(PathMetadata metadata) {
        super(OrganizationInterface.class, metadata, "null", "ORGANIZATION_INTERFACE");
        addMetadata();
    }

    public void addMetadata() {
        addMetadata(dataSource, ColumnMetadata.named("DATA_SOURCE").withIndex(3).ofType(Types.VARCHAR).withSize(20));
        addMetadata(description, ColumnMetadata.named("DESCRIPTION").withIndex(6).ofType(Types.VARCHAR).withSize(255).notNull());
        addMetadata(descriptionLang, ColumnMetadata.named("DESCRIPTION_LANG").withIndex(12).ofType(Types.VARCHAR).withSize(255));
        addMetadata(id, ColumnMetadata.named("ID").withIndex(1).ofType(Types.VARCHAR).withSize(20));
        addMetadata(longDescription, ColumnMetadata.named("LONG_DESCRIPTION").withIndex(13).ofType(Types.VARCHAR).withSize(2000));
        addMetadata(longDescriptionLang, ColumnMetadata.named("LONG_DESCRIPTION_LANG").withIndex(14).ofType(Types.VARCHAR).withSize(2000));
        addMetadata(orgCode, ColumnMetadata.named("ORG_CODE").withIndex(5).ofType(Types.VARCHAR).withSize(20).notNull());
        addMetadata(orgRoleTypeId, ColumnMetadata.named("ORG_ROLE_TYPE_ID").withIndex(10).ofType(Types.VARCHAR).withSize(20));
        addMetadata(parentFromDate, ColumnMetadata.named("PARENT_FROM_DATE").withIndex(16).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(parentOrgCode, ColumnMetadata.named("PARENT_ORG_CODE").withIndex(7).ofType(Types.VARCHAR).withSize(20));
        addMetadata(parentRoleTypeId, ColumnMetadata.named("PARENT_ROLE_TYPE_ID").withIndex(11).ofType(Types.VARCHAR).withSize(20));
        addMetadata(refDate, ColumnMetadata.named("REF_DATE").withIndex(4).ofType(Types.TIMESTAMP).withSize(26).notNull());
        addMetadata(responsibleCode, ColumnMetadata.named("RESPONSIBLE_CODE").withIndex(8).ofType(Types.VARCHAR).withSize(20));
        addMetadata(responsibleComments, ColumnMetadata.named("RESPONSIBLE_COMMENTS").withIndex(19).ofType(Types.VARCHAR).withSize(255));
        addMetadata(responsibleFromDate, ColumnMetadata.named("RESPONSIBLE_FROM_DATE").withIndex(17).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(responsibleRoleTypeId, ColumnMetadata.named("RESPONSIBLE_ROLE_TYPE_ID").withIndex(20).ofType(Types.VARCHAR).withSize(20));
        addMetadata(responsibleThruDate, ColumnMetadata.named("RESPONSIBLE_THRU_DATE").withIndex(18).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(stato, ColumnMetadata.named("STATO").withIndex(2).ofType(Types.VARCHAR).withSize(20));
        addMetadata(thruDate, ColumnMetadata.named("THRU_DATE").withIndex(9).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(vatCode, ColumnMetadata.named("VAT_CODE").withIndex(15).ofType(Types.VARCHAR).withSize(20));
    }

}

