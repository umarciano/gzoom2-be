package it.mapsgroup.gzoom.querydsl.generator;

import com.querydsl.core.group.GroupBy;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.QBean;
import com.querydsl.sql.SQLQueryFactory;
import it.mapsgroup.gzoom.querydsl.dao.AbstractDaoTest;
import it.mapsgroup.gzoom.querydsl.dto.QSecurityGroupPermission;
import it.mapsgroup.gzoom.querydsl.dto.QUserLoginSecurityGroup;
import it.mapsgroup.gzoom.querydsl.dto.SecurityGroupPermission;
import it.mapsgroup.gzoom.querydsl.dto.SecurityPermission;
import it.mapsgroup.gzoom.querydsl.persistence.service.QueryDslPersistenceConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;


public class PermissionsTest extends AbstractDaoTest{
    private static final Logger LOG = getLogger(PermissionsTest.class);


    @Autowired
    private SQLQueryFactory queryFactory;


    @Test
    @Transactional
    public void name() throws Exception {
        QUserLoginSecurityGroup qulsg = QUserLoginSecurityGroup.userLoginSecurityGroup;
        QSecurityGroupPermission qsgp = QSecurityGroupPermission.securityGroupPermission;

        List<SecurityGroupPermission> ret = queryFactory.select(qsgp)
                .from(qsgp)
                .where(qsgp.groupId.isNotNull().and(qsgp.groupId.eq("FULLADMIN")))
                .transform(GroupBy.groupBy(qsgp.permissionId).list(qsgp));


        System.out.println("name ret.size() " + ret.size());
        ret.forEach(p ->
                System.out.println("p " + p.getPermissionId())
        );
    }

    @Test
    @Transactional
    public void name2() throws Exception {
        QUserLoginSecurityGroup qulsg = QUserLoginSecurityGroup.userLoginSecurityGroup;
        QSecurityGroupPermission qsgp = QSecurityGroupPermission.securityGroupPermission;

        QBean<SecurityPermission> perm = Projections.bean(SecurityPermission.class, qsgp.permissionId);

        List<SecurityPermission> ret = queryFactory.select(qsgp.permissionId)
                .from(qsgp)
                .innerJoin(qulsg).on(qulsg.groupId.eq(qsgp.groupId))
                .where(qulsg.userLoginId.isNotNull().and(qulsg.userLoginId.eq("admin")))
                .transform(GroupBy.groupBy(qsgp.permissionId).list(perm));

        ret.size();
        System.out.println("name2 ret.size() " + ret.size());
        ret.forEach(p ->
                System.out.println("p " + p.getPermissionId())
        );
    }

    @Test
    @Transactional
    public void name3() throws Exception {
        QSecurityGroupPermission qsgp = QSecurityGroupPermission.securityGroupPermission;

        QBean<SecurityPermission> perm = Projections.bean(SecurityPermission.class, qsgp.permissionId);

        List<SecurityPermission> ret = queryFactory.select(qsgp.permissionId)
                .from(qsgp)
                .where(qsgp.groupId.isNotNull().and(qsgp.groupId.eq("FULLADMIN")))
                .transform(GroupBy.groupBy(qsgp.permissionId).list(perm));

        ret.size();
        System.out.println("name3 ret.size() " + ret.size());
        ret.forEach(p ->
                System.out.println("p " + p.getPermissionId())
        );
    }


}
