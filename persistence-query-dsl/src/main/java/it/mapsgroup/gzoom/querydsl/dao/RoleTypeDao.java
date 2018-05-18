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

import it.mapsgroup.gzoom.querydsl.dto.QRoleType;
import it.mapsgroup.gzoom.querydsl.dto.RoleType;

public class RoleTypeDao extends AbstractDao {
	private static final Logger LOG = getLogger(RoleTypeDao.class);

    private final SQLQueryFactory queryFactory;
    
    public RoleTypeDao(SQLQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }
    
    @Transactional
    public List<RoleType> getRoleTypes() {
        if (TransactionSynchronizationManager.isActualTransactionActive()) {
            TransactionStatus status = TransactionAspectSupport.currentTransactionStatus();
            status.getClass();
        }

        QRoleType qRoleType = QRoleType.roleType;

        SQLQuery<RoleType> tupleSQLQuery = queryFactory.select(qRoleType).from(qRoleType);

        SQLBindings bindings = tupleSQLQuery.getSQL();
        LOG.info("{}", bindings.getSQL());
        LOG.info("{}", bindings.getBindings());
        QBean<RoleType> roleTypes = Projections.bean(RoleType.class, qRoleType.all());

        List<RoleType> ret = tupleSQLQuery.transform(GroupBy.groupBy(qRoleType.roleTypeId).list(roleTypes));
        LOG.info("size = {}", ret.size());
        return ret;
    }

    @Transactional
    public RoleType getRoleType(String roleTypeId) {
        if (TransactionSynchronizationManager.isActualTransactionActive()) {
            TransactionStatus status = TransactionAspectSupport.currentTransactionStatus();
            status.getClass();
        }

        QRoleType qRoleType = QRoleType.roleType;

        SQLQuery<RoleType> tupleSQLQuery = queryFactory.select(qRoleType).from(qRoleType).where(qRoleType.roleTypeId.eq(roleTypeId));

        SQLBindings bindings = tupleSQLQuery.getSQL();
        LOG.info("{}", bindings.getSQL());
        LOG.info("{}", bindings.getBindings());
        QBean<RoleType> roleTypes = Projections.bean(RoleType.class, qRoleType.all());
        List<RoleType> ret = tupleSQLQuery.transform(GroupBy.groupBy(qRoleType.roleTypeId).list(roleTypes));
        return ret.isEmpty() ? null : ret.get(0);
    }
}
