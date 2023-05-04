package it.mapsgroup.gzoom.querydsl.dto;

import com.querydsl.core.types.Path;
import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.types.dsl.DateTimePath;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.StringPath;
import com.querydsl.sql.ColumnMetadata;

import javax.annotation.Generated;
import java.sql.Types;

import static com.querydsl.core.types.PathMetadataFactory.forVariable;




/**
 * QPartyHistoryView is a Querydsl query type for PartyHistoryView
 */
@Generated("com.querydsl.sql.codegen.MetaDataSerializer")
public class QPartyHistoryView extends com.querydsl.sql.RelationalPathBase<PartyHistoryView> {

    private static final long serialVersionUID = -346606370;

    public static final QPartyHistoryView partyHistoryView = new QPartyHistoryView("PARTY_HISTORY_VIEW");

    public final StringPath comments = createString("comments");

    public final StringPath description = createString("description");

    public final NumberPath<java.math.BigDecimal> employmentAmount = createNumber("employmentAmount", java.math.BigDecimal.class);

    public final StringPath emplPositionTypeId = createString("emplPositionTypeId");

    public final DateTimePath<java.time.LocalDateTime> fromDate = createDateTime("fromDate", java.time.LocalDateTime.class);

    public final StringPath partyId = createString("partyId");

    public final DateTimePath<java.time.LocalDateTime> thruDate = createDateTime("thruDate", java.time.LocalDateTime.class);

    public QPartyHistoryView(String variable) {
        super(PartyHistoryView.class, forVariable(variable), "null", "PARTY_HISTORY_VIEW");
        addMetadata();
    }

    public QPartyHistoryView(String variable, String schema, String table) {
        super(PartyHistoryView.class, forVariable(variable), schema, table);
        addMetadata();
    }

    public QPartyHistoryView(String variable, String schema) {
        super(PartyHistoryView.class, forVariable(variable), schema, "PARTY_HISTORY_VIEW");
        addMetadata();
    }

    public QPartyHistoryView(Path<? extends PartyHistoryView> path) {
        super(path.getType(), path.getMetadata(), "null", "PARTY_HISTORY_VIEW");
        addMetadata();
    }

    public QPartyHistoryView(PathMetadata metadata) {
        super(PartyHistoryView.class, metadata, "null", "PARTY_HISTORY_VIEW");
        addMetadata();
    }

    public void addMetadata() {
        addMetadata(comments, ColumnMetadata.named("COMMENTS").withIndex(7).ofType(Types.VARCHAR).withSize(255));
        addMetadata(description, ColumnMetadata.named("DESCRIPTION").withIndex(6).ofType(Types.LONGVARCHAR).withSize(65535));
        addMetadata(employmentAmount, ColumnMetadata.named("EMPLOYMENT_AMOUNT").withIndex(4).ofType(Types.DECIMAL).withSize(18).withDigits(2));
        addMetadata(emplPositionTypeId, ColumnMetadata.named("EMPL_POSITION_TYPE_ID").withIndex(5).ofType(Types.VARCHAR).withSize(20));
        addMetadata(fromDate, ColumnMetadata.named("FROM_DATE").withIndex(2).ofType(Types.VARCHAR).withSize(19));
        addMetadata(partyId, ColumnMetadata.named("PARTY_ID").withIndex(1).ofType(Types.VARCHAR).withSize(20).notNull());
        addMetadata(thruDate, ColumnMetadata.named("THRU_DATE").withIndex(3).ofType(Types.TIMESTAMP).withSize(26));
    }

}

