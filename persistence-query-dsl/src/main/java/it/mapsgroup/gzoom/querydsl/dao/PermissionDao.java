package it.mapsgroup.gzoom.querydsl.dao;

import static org.slf4j.LoggerFactory.getLogger;

import java.util.List;

import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.querydsl.core.Tuple;
import com.querydsl.core.group.GroupBy;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.QBean;
import com.querydsl.sql.SQLBindings;
import com.querydsl.sql.SQLQuery;
import com.querydsl.sql.SQLQueryFactory;

import it.mapsgroup.gzoom.querydsl.dto.*;

/**
 * @author Andrea Fossi.
 */
@Service
public class PermissionDao {
    private static final Logger LOG = getLogger(PermissionDao.class);

    private final SQLQueryFactory queryFactory;

    public PermissionDao(SQLQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Transactional
    public List<SecurityPermission> getPermission(String userLoginId) {
        QUserLoginSecurityGroup qulsg = QUserLoginSecurityGroup.userLoginSecurityGroup;
        QSecurityGroupPermission qsgp = QSecurityGroupPermission.securityGroupPermission;
        QSecurityPermission qsp = QSecurityPermission.securityPermission;
        
        SQLQuery<String> tupleSQLQuery = queryFactory.select(qsgp.permissionId)
                .from(qsgp)
                .innerJoin(qulsg).on(qulsg.groupId.eq(qsgp.groupId))
                .innerJoin(qsp).on(qsgp.permissionId.eq(qsp.permissionId))
                .where(qulsg.userLoginId.eq(userLoginId).and(qsp.enabled.isTrue()));
                // .where(qulsg.userLoginId.eq(userLoginId).and(qsp.enabled.isTrue()))
        SQLBindings bindings = tupleSQLQuery.getSQL();
        LOG.info("{}", bindings.getSQL());
        LOG.info("{}", bindings.getBindings());
        QBean<SecurityPermission> perm = Projections.bean(SecurityPermission.class, qsgp.permissionId);
        
        List<SecurityPermission> ret = tupleSQLQuery.transform(GroupBy.groupBy(qsgp.permissionId).list(perm));
        
        
        return ret.isEmpty() ? null : ret;
    }
}
