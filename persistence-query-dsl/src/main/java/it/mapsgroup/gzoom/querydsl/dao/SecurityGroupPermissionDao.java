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

import it.mapsgroup.gzoom.querydsl.dto.QSecurityGroupPermission;
import it.mapsgroup.gzoom.querydsl.dto.SecurityGroupPermission;

@Service
public class SecurityGroupPermissionDao extends AbstractDao {
private static final Logger LOG = getLogger(SecurityGroupPermissionDao.class);
	
	private final SQLQueryFactory queryFactory;

    public SecurityGroupPermissionDao(SQLQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }       
    
    @Transactional
    public List<SecurityGroupPermission> getSecurityGroupPermissions(String groupId, String permissionId) {
    	 
        if (TransactionSynchronizationManager.isActualTransactionActive()) {
            TransactionStatus status = TransactionAspectSupport.currentTransactionStatus();
            status.getClass();
        }

        QSecurityGroupPermission qsgp = QSecurityGroupPermission.securityGroupPermission;
        QBean<SecurityGroupPermission> qsgps = Projections.bean(SecurityGroupPermission.class, qsgp.all());
        
        SQLQuery<SecurityGroupPermission> tupleSQLQuery = queryFactory.select(qsgp)
        		.from(qsgp)
        		.where((qsgp.permissionId.eq(permissionId))
        				.and(qsgp.groupId.eq(groupId)));

        SQLBindings bindings = tupleSQLQuery.getSQL();
        LOG.info("{}", bindings.getSQL());
        LOG.info("{}", bindings.getNullFriendlyBindings());
        List<SecurityGroupPermission> ret = tupleSQLQuery.transform(GroupBy.groupBy(qsgp.groupId, qsgp.permissionId).list(qsgps));
        return ret;
    } 
}
