package it.mapsgroup.gzoom.querydsl.dao;

import com.querydsl.core.Tuple;
import com.querydsl.core.group.GroupBy;
import com.querydsl.core.types.QBean;
import com.querydsl.sql.SQLBindings;
import com.querydsl.sql.SQLQuery;
import com.querydsl.sql.SQLQueryFactory;
import it.mapsgroup.gzoom.querydsl.dto.*;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.List;

import static com.querydsl.core.types.Projections.bean;
import static it.mapsgroup.gzoom.querydsl.QBeanUtils.merge;
import static org.slf4j.LoggerFactory.getLogger;

@Service
public class UserLoginValidPartyRoleDao extends AbstractDao {

    private static final Logger LOG = getLogger(PartyDao.class);
    private final SQLQueryFactory queryFactory;

    @Autowired
    public UserLoginValidPartyRoleDao(SQLQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Transactional
    public List<UserLoginValidPartyRoleEx> getUserLoginValidPartyRoleList(String user) {
        if (TransactionSynchronizationManager.isActualTransactionActive()) {
            TransactionStatus status = TransactionAspectSupport.currentTransactionStatus();
            status.getClass();
        }

        QUserLoginValidPartyRole qUserLoginValidPartyRole = QUserLoginValidPartyRole.userLoginValidPartyRole;
        QPartyGroup qPartyGroup = QPartyGroup.partyGroup;

        QBean<UserLoginValidPartyRoleEx> tupleEx = bean(UserLoginValidPartyRoleEx.class,
                merge(qUserLoginValidPartyRole.all(),bean(PartyGroup.class,qPartyGroup.all()).as("partyGroup")));
        SQLQuery<Tuple> tupleSQLQuery = queryFactory.select(qUserLoginValidPartyRole, qPartyGroup)
                .from(qUserLoginValidPartyRole)
                .innerJoin(qPartyGroup).on(qPartyGroup.partyId.eq(qUserLoginValidPartyRole.partyId))
                .where(qUserLoginValidPartyRole.userLoginId.eq(user));

        SQLBindings bindings = tupleSQLQuery.getSQL();
        LOG.info("getUserLoginValidPartyRoleList {}", bindings.getSQL());
        LOG.info("getUserLoginValidPartyRoleList {}", bindings.getNullFriendlyBindings());
        List<UserLoginValidPartyRoleEx> ret = tupleSQLQuery.transform(GroupBy.groupBy(qUserLoginValidPartyRole.partyId).list(tupleEx));
        LOG.info("getUserLoginValidPartyRoleList size = {}", ret.size());
        return ret;
    }
}
