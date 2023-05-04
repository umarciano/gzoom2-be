package it.mapsgroup.gzoom.querydsl.dao;


import static org.slf4j.LoggerFactory.getLogger;

import java.time.LocalDateTime;
import java.util.List;

import it.mapsgroup.gzoom.querydsl.dto.*;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
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


@Service
public class UserLoginSecurityGroupDao extends AbstractDao {
	
	private static final Logger LOG = getLogger(UserLoginSecurityGroupDao.class);
	
	private final SQLQueryFactory queryFactory;

    public UserLoginSecurityGroupDao(SQLQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }       
    
    @Transactional
    public List<UserLoginSecurityGroup> getUserLoginSecurityGroups(String userLoginId, String groupId) {
    	 
        if (TransactionSynchronizationManager.isActualTransactionActive()) {
            TransactionStatus status = TransactionAspectSupport.currentTransactionStatus();
            status.getClass();
        }

        QUserLoginSecurityGroup qUlsg = QUserLoginSecurityGroup.userLoginSecurityGroup;

        SQLQuery<UserLoginSecurityGroup> tupleSQLQuery = queryFactory.select(qUlsg)
        		.from(qUlsg)
        		.where((qUlsg.userLoginId.eq(userLoginId))
        				.and(qUlsg.groupId.eq(groupId))
        				.and(filterByDate(qUlsg.fromDate, qUlsg.thruDate)));

        SQLBindings bindings = tupleSQLQuery.getSQL();
        LOG.info("{}", bindings.getSQL());
        LOG.info("{}", bindings.getNullFriendlyBindings());
        QBean<UserLoginSecurityGroup> qUlsgs = Projections.bean(UserLoginSecurityGroup.class, qUlsg.all());
        List<UserLoginSecurityGroup> ret = tupleSQLQuery.transform(GroupBy.groupBy(qUlsg.userLoginId).list(qUlsgs));
        return ret;
    }

    @Transactional
    public String getDefaultPortalPage(String userLoginId) {
        if (TransactionSynchronizationManager.isActualTransactionActive()) {
            TransactionStatus status = TransactionAspectSupport.currentTransactionStatus();
            status.getClass();
        }

        QUserLoginSecurityGroup qUserLoginSecurityGroup = QUserLoginSecurityGroup.userLoginSecurityGroup;
        QSecurityGroup qSecurityGroup = QSecurityGroup.securityGroup;
        QPortalPage qPortalPage = QPortalPage.portalPage;

        SQLQuery<PortalPage> tupleSQLQuery = queryFactory.select(qPortalPage)
                .from(qUserLoginSecurityGroup)
                .join(qSecurityGroup).on(qUserLoginSecurityGroup.groupId.eq(qSecurityGroup.groupId))
                .join(qPortalPage).on(qSecurityGroup.defaultPortalPageId.eq(qPortalPage.portalPageId))
                .where((qUserLoginSecurityGroup.thruDate.isNull().or(qUserLoginSecurityGroup.thruDate.goe(LocalDateTime.now())))
                .and(qUserLoginSecurityGroup.userLoginId.eq(userLoginId)))
                .orderBy(qUserLoginSecurityGroup.fromDate.asc());

        SQLBindings bindings = tupleSQLQuery.getSQL();
        LOG.info("{}", bindings.getSQL());
        LOG.info("{}", bindings.getNullFriendlyBindings());
        QBean<PortalPage> portalPage = Projections.bean(PortalPage.class, qPortalPage.all());
        List<PortalPage> ret = tupleSQLQuery.transform(GroupBy.groupBy(qPortalPage.securityGroupId).list(portalPage));
        return ret.isEmpty() ? null : ret.get(0).getSecurityGroupId();
    }
    
    @Transactional
    public List<UserLoginSecurityGroup> getUserLoginSecurityGroups(String userLoginId) {
    	 
        if (TransactionSynchronizationManager.isActualTransactionActive()) {
            TransactionStatus status = TransactionAspectSupport.currentTransactionStatus();
            status.getClass();
        }

        QUserLoginSecurityGroup qUlsg = QUserLoginSecurityGroup.userLoginSecurityGroup;

        SQLQuery<UserLoginSecurityGroup> tupleSQLQuery = queryFactory.select(qUlsg)
        		.from(qUlsg)
        		.where((qUlsg.userLoginId.eq(userLoginId))
        				.and(filterByDate(qUlsg.fromDate, qUlsg.thruDate)));

        SQLBindings bindings = tupleSQLQuery.getSQL();
        LOG.info("{}", bindings.getSQL());
        LOG.info("{}", bindings.getNullFriendlyBindings());
        QBean<UserLoginSecurityGroup> qUlsgs = Projections.bean(UserLoginSecurityGroup.class, qUlsg.all());
        List<UserLoginSecurityGroup> ret = tupleSQLQuery.transform(GroupBy.groupBy(qUlsg.groupId).list(qUlsgs));
        return ret;
    }

}
