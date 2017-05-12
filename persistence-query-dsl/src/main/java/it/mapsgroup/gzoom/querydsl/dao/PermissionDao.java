package it.mapsgroup.gzoom.querydsl.dao;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.querydsl.core.group.GroupBy;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.QBean;
import com.querydsl.sql.SQLQueryFactory;

import it.mapsgroup.gzoom.querydsl.dto.QSecurityGroupPermission;
import it.mapsgroup.gzoom.querydsl.dto.QSecurityPermission;
import it.mapsgroup.gzoom.querydsl.dto.QUserLoginSecurityGroup;
import it.mapsgroup.gzoom.querydsl.dto.SecurityPermission;

/**
 * @author Andrea Fossi.
 */
@Service
public class PermissionDao {

    private final SQLQueryFactory queryFactory;

    public PermissionDao(SQLQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Transactional
    public List<SecurityPermission> getPermission(String userLoginId) {
        QUserLoginSecurityGroup qulsg = QUserLoginSecurityGroup.userLoginSecurityGroup;
        QSecurityGroupPermission qsgp = QSecurityGroupPermission.securityGroupPermission;
        QSecurityPermission qsp = QSecurityPermission.securityPermission;
        
        QBean<SecurityPermission> perm = Projections.bean(SecurityPermission.class, qsgp.permissionId);
        
        List<SecurityPermission> ret = queryFactory.select(qsgp.permissionId)
                .from(qsgp)
                .innerJoin(qulsg).on(qulsg.groupId.eq(qsgp.groupId))
                .innerJoin(qsp).on(qsgp.permissionId.eq(qsp.permissionId))
                .where(qulsg.userLoginId.eq(userLoginId).and(qsp.enabled.isTrue()))
                .transform(GroupBy.groupBy(qsgp.permissionId).list(perm));
        
        return ret.isEmpty() ? null : ret;
    }
}
