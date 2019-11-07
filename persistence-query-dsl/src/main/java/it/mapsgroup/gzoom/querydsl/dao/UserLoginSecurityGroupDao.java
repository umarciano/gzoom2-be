package it.mapsgroup.gzoom.querydsl.dao;


import static org.slf4j.LoggerFactory.getLogger;

import java.util.List;

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

import it.mapsgroup.gzoom.querydsl.dto.QUserLoginSecurityGroup;
import it.mapsgroup.gzoom.querydsl.dto.UserLoginSecurityGroup;

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
