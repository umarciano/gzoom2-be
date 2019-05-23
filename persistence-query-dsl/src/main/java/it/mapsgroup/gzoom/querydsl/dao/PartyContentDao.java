package it.mapsgroup.gzoom.querydsl.dao;

import com.querydsl.core.Tuple;
import com.querydsl.core.group.GroupBy;
import com.querydsl.core.types.QBean;
import com.querydsl.sql.SQLBindings;
import com.querydsl.sql.SQLQuery;
import com.querydsl.sql.SQLQueryFactory;
import it.mapsgroup.gzoom.querydsl.dto.*;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.List;

import static com.querydsl.core.types.Projections.bean;
import static it.mapsgroup.gzoom.querydsl.QBeanUtils.merge;
import static org.slf4j.LoggerFactory.getLogger;

/**
 */
@Service
public class PartyContentDao extends AbstractDao {
	private static final Logger LOG = getLogger(PartyContentDao.class);

    private final SQLQueryFactory queryFactory;

    public PartyContentDao(SQLQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }
    
    @Transactional
    public PartyContentEx getPartyContent(String partyId, String partyContentTypeId) {
        if (TransactionSynchronizationManager.isActualTransactionActive()) {
            TransactionStatus status = TransactionAspectSupport.currentTransactionStatus();
            status.getClass();
        }

        QPartyContent qPartyContent = QPartyContent.partyContent;
        QContent qContent = QContent.content;
        QDataResource qDataResource = QDataResource.dataResource;

        QBean<PartyContentEx> tupleExQBean = bean(PartyContentEx.class,
                merge(qPartyContent.all(),
                        bean(Content.class, qContent.all()).as("content"),
                        bean(DataResource.class, qDataResource.all()).as("dataResource")
                ));

        SQLQuery<Tuple> tupleSQLQuery = queryFactory
                .select(qPartyContent, qContent, qDataResource)
                .from(qPartyContent)
                .innerJoin(qContent).on(qContent.contentId.eq(qPartyContent.contentId))
                .innerJoin(qDataResource).on(qDataResource.dataResourceId.eq(qContent.dataResourceId))
                .where(qPartyContent.partyId.eq(partyId)
                        .and(qPartyContent.thruDate.isNull())
                        .and(qPartyContent.partyContentTypeId.eq(partyContentTypeId)));

        SQLBindings bindings = tupleSQLQuery.getSQL();
        LOG.info("{}", bindings.getSQL());
        LOG.info("{}", bindings.getNullFriendlyBindings());
        List<PartyContentEx> ret = tupleSQLQuery.orderBy(qPartyContent.contentId.desc()).transform(GroupBy.groupBy(qPartyContent.contentId).list(tupleExQBean));

        return ret.isEmpty() ? null : ret.get(0);
    }
}
