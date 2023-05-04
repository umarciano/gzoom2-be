package it.mapsgroup.gzoom.querydsl.dto;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;

import com.querydsl.sql.ColumnMetadata;
import java.sql.Types;




/**
 * QOrganizationInterfaceHist is a Querydsl query type for OrganizationInterfaceHist
 */
@Generated("com.querydsl.sql.codegen.MetaDataSerializer")
public class QOrganizationInterfaceHist extends com.querydsl.sql.RelationalPathBase<OrganizationInterfaceHist> {

    private static final long serialVersionUID = -1962982499;

    public static final QOrganizationInterfaceHist organizationInterfaceHist = new QOrganizationInterfaceHist("ORGANIZATION_INTERFACE_HIST");

    public final DateTimePath<java.time.LocalDateTime> createdStamp = createDateTime("createdStamp", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> createdTxStamp = createDateTime("createdTxStamp", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> customDate01 = createDateTime("customDate01", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> customDate02 = createDateTime("customDate02", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> customDate03 = createDateTime("customDate03", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> customDate04 = createDateTime("customDate04", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> customDate05 = createDateTime("customDate05", java.time.LocalDateTime.class);

    public final StringPath customText01 = createString("customText01");

    public final StringPath customText02 = createString("customText02");

    public final StringPath customText03 = createString("customText03");

    public final StringPath customText04 = createString("customText04");

    public final StringPath customText05 = createString("customText05");

    public final NumberPath<java.math.BigDecimal> customValue01 = createNumber("customValue01", java.math.BigDecimal.class);

    public final NumberPath<java.math.BigDecimal> customValue02 = createNumber("customValue02", java.math.BigDecimal.class);

    public final NumberPath<java.math.BigDecimal> customValue03 = createNumber("customValue03", java.math.BigDecimal.class);

    public final NumberPath<java.math.BigDecimal> customValue04 = createNumber("customValue04", java.math.BigDecimal.class);

    public final NumberPath<java.math.BigDecimal> customValue05 = createNumber("customValue05", java.math.BigDecimal.class);

    public final StringPath dataSource = createString("dataSource");

    public final StringPath description = createString("description");

    public final StringPath descriptionLang = createString("descriptionLang");

    public final StringPath histJobLogId = createString("histJobLogId");

    public final StringPath id = createString("id");

    public final DateTimePath<java.time.LocalDateTime> lastUpdatedStamp = createDateTime("lastUpdatedStamp", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> lastUpdatedTxStamp = createDateTime("lastUpdatedTxStamp", java.time.LocalDateTime.class);

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

    public final NumberPath<java.math.BigInteger> seq = createNumber("seq", java.math.BigInteger.class);

    public final StringPath stato = createString("stato");

    public final DateTimePath<java.time.LocalDateTime> thruDate = createDateTime("thruDate", java.time.LocalDateTime.class);

    public final StringPath vatCode = createString("vatCode");

    public final com.querydsl.sql.PrimaryKey<OrganizationInterfaceHist> primary = createPrimaryKey(id);

    public QOrganizationInterfaceHist(String variable) {
        super(OrganizationInterfaceHist.class, forVariable(variable), "null", "ORGANIZATION_INTERFACE_HIST");
        addMetadata();
    }

    public QOrganizationInterfaceHist(String variable, String schema, String table) {
        super(OrganizationInterfaceHist.class, forVariable(variable), schema, table);
        addMetadata();
    }

    public QOrganizationInterfaceHist(String variable, String schema) {
        super(OrganizationInterfaceHist.class, forVariable(variable), schema, "ORGANIZATION_INTERFACE_HIST");
        addMetadata();
    }

    public QOrganizationInterfaceHist(Path<? extends OrganizationInterfaceHist> path) {
        super(path.getType(), path.getMetadata(), "null", "ORGANIZATION_INTERFACE_HIST");
        addMetadata();
    }

    public QOrganizationInterfaceHist(PathMetadata metadata) {
        super(OrganizationInterfaceHist.class, metadata, "null", "ORGANIZATION_INTERFACE_HIST");
        addMetadata();
    }

    public void addMetadata() {
        addMetadata(createdStamp, ColumnMetadata.named("CREATED_STAMP").withIndex(40).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(createdTxStamp, ColumnMetadata.named("CREATED_TX_STAMP").withIndex(41).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(customDate01, ColumnMetadata.named("CUSTOM_DATE01").withIndex(27).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(customDate02, ColumnMetadata.named("CUSTOM_DATE02").withIndex(28).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(customDate03, ColumnMetadata.named("CUSTOM_DATE03").withIndex(29).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(customDate04, ColumnMetadata.named("CUSTOM_DATE04").withIndex(30).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(customDate05, ColumnMetadata.named("CUSTOM_DATE05").withIndex(31).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(customText01, ColumnMetadata.named("CUSTOM_TEXT01").withIndex(22).ofType(Types.VARCHAR).withSize(255));
        addMetadata(customText02, ColumnMetadata.named("CUSTOM_TEXT02").withIndex(23).ofType(Types.VARCHAR).withSize(255));
        addMetadata(customText03, ColumnMetadata.named("CUSTOM_TEXT03").withIndex(24).ofType(Types.VARCHAR).withSize(255));
        addMetadata(customText04, ColumnMetadata.named("CUSTOM_TEXT04").withIndex(25).ofType(Types.VARCHAR).withSize(255));
        addMetadata(customText05, ColumnMetadata.named("CUSTOM_TEXT05").withIndex(26).ofType(Types.VARCHAR).withSize(255));
        addMetadata(customValue01, ColumnMetadata.named("CUSTOM_VALUE01").withIndex(32).ofType(Types.DECIMAL).withSize(18).withDigits(6));
        addMetadata(customValue02, ColumnMetadata.named("CUSTOM_VALUE02").withIndex(33).ofType(Types.DECIMAL).withSize(18).withDigits(6));
        addMetadata(customValue03, ColumnMetadata.named("CUSTOM_VALUE03").withIndex(34).ofType(Types.DECIMAL).withSize(18).withDigits(6));
        addMetadata(customValue04, ColumnMetadata.named("CUSTOM_VALUE04").withIndex(35).ofType(Types.DECIMAL).withSize(18).withDigits(6));
        addMetadata(customValue05, ColumnMetadata.named("CUSTOM_VALUE05").withIndex(36).ofType(Types.DECIMAL).withSize(18).withDigits(6));
        addMetadata(dataSource, ColumnMetadata.named("DATA_SOURCE").withIndex(3).ofType(Types.VARCHAR).withSize(20));
        addMetadata(description, ColumnMetadata.named("DESCRIPTION").withIndex(6).ofType(Types.VARCHAR).withSize(255).notNull());
        addMetadata(descriptionLang, ColumnMetadata.named("DESCRIPTION_LANG").withIndex(12).ofType(Types.VARCHAR).withSize(255));
        addMetadata(histJobLogId, ColumnMetadata.named("HIST_JOB_LOG_ID").withIndex(21).ofType(Types.VARCHAR).withSize(20));
        addMetadata(id, ColumnMetadata.named("ID").withIndex(1).ofType(Types.VARCHAR).withSize(20).notNull());
        addMetadata(lastUpdatedStamp, ColumnMetadata.named("LAST_UPDATED_STAMP").withIndex(38).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(lastUpdatedTxStamp, ColumnMetadata.named("LAST_UPDATED_TX_STAMP").withIndex(39).ofType(Types.TIMESTAMP).withSize(26));
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
        addMetadata(seq, ColumnMetadata.named("SEQ").withIndex(37).ofType(Types.DECIMAL).withSize(20));
        addMetadata(stato, ColumnMetadata.named("STATO").withIndex(2).ofType(Types.VARCHAR).withSize(20));
        addMetadata(thruDate, ColumnMetadata.named("THRU_DATE").withIndex(9).ofType(Types.TIMESTAMP).withSize(26));
        addMetadata(vatCode, ColumnMetadata.named("VAT_CODE").withIndex(15).ofType(Types.VARCHAR).withSize(20));
    }

}

