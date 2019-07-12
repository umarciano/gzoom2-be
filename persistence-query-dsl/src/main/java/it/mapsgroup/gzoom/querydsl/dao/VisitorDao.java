package it.mapsgroup.gzoom.querydsl.dao;

import com.querydsl.core.Tuple;
import com.querydsl.core.group.GroupBy;
import com.querydsl.core.types.Projections;
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
public class VisitorDao extends AbstractDao {
    private static final Logger LOG = getLogger(VisitorDao.class);

    private final SQLQueryFactory queryFactory;

    public VisitorDao(SQLQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Transactional
    public List<VisitorEx> getVisitors() {
        if (TransactionSynchronizationManager.isActualTransactionActive()) {
            TransactionStatus status = TransactionAspectSupport.currentTransactionStatus();
            status.getClass();
        }

        QVisitor qVisitor = QVisitor.visitor;
        QUserLoginPersistent qUserLoginPersistent = QUserLoginPersistent.userLogin;
        QParty qParty = QParty.party;
        QPerson qPerson = QPerson.person;
        QPartyParentRole qPartyParentRole = QPartyParentRole.partyParentRole;

        SQLQuery<Tuple> sqlQuery = queryFactory.select(qVisitor, qUserLoginPersistent, qParty, qPerson, qPartyParentRole)
                .from(qVisitor)
                .innerJoin(qUserLoginPersistent).on(qUserLoginPersistent.partyId.eq(qVisitor.partyId))
                .innerJoin(qParty).on(qParty.partyId.eq(qVisitor.partyId))
                .innerJoin(qPerson).on(qPerson.partyId.eq(qParty.partyId))
                .innerJoin(qPartyParentRole).on(qPerson.partyId.eq(qPartyParentRole.partyId)).on(qPartyParentRole.roleTypeId.eq("EMPLOYEE"))
                .orderBy(qVisitor.lastUpdatedStamp.asc());

        SQLBindings bindings = sqlQuery.getSQL();
        LOG.info("{}", bindings.getSQL());
        LOG.info("{}", bindings.getBindings());
        QBean<VisitorEx> visitors = bean(VisitorEx.class, merge(qVisitor.all(),
                bean(UserLoginPersistent.class, qUserLoginPersistent.all()),
                bean(Party.class, qParty.all()),
                bean(Person.class, qPerson.all()),
                bean(PartyParentRole.class, qPartyParentRole.all())));
        List<VisitorEx> ret = sqlQuery.transform(GroupBy.groupBy(qParty.partyId).list(visitors));
        LOG.info("size = {}", ret.size());

        return null;
    }

}
