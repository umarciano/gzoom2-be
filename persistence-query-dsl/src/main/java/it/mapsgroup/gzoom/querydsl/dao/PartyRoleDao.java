package it.mapsgroup.gzoom.querydsl.dao;

import static org.slf4j.LoggerFactory.getLogger;

import java.util.List;

import org.slf4j.Logger;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import com.querydsl.core.group.GroupBy;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.QBean;
import com.querydsl.sql.SQLBindings;
import com.querydsl.sql.SQLQuery;
import com.querydsl.sql.SQLQueryFactory;

import it.mapsgroup.gzoom.querydsl.dto.PartyRole;
import it.mapsgroup.gzoom.querydsl.dto.QPartyRole;

public class PartyRoleDao extends AbstractDao {
	private static final Logger LOG = getLogger(PartyRoleDao.class);

    private final SQLQueryFactory queryFactory;
    
    public PartyRoleDao(SQLQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }
    
    @Transactional
    public PartyRole getPartyRole(String partyId, String roleTypeId) {
        if (TransactionSynchronizationManager.isActualTransactionActive()) {
            TransactionStatus status = TransactionAspectSupport.currentTransactionStatus();
            status.getClass();
        }

        QPartyRole qPartyRole = QPartyRole.partyRole;

        SQLQuery<PartyRole> tupleSQLQuery = queryFactory.select(qPartyRole).from(qPartyRole).where(qPartyRole.partyId.eq(partyId).and(qPartyRole.roleTypeId.eq(roleTypeId)));

        SQLBindings bindings = tupleSQLQuery.getSQL();
        LOG.info("{}", bindings.getSQL());
        LOG.info("{}", bindings.getBindings());
        QBean<PartyRole> partyRoles = Projections.bean(PartyRole.class, qPartyRole.all());
        List<PartyRole> ret = tupleSQLQuery.transform(GroupBy.groupBy(qPartyRole.roleTypeId).list(partyRoles));
        return ret.isEmpty() ? null : ret.get(0);
    }
}
