package it.mapsgroup.gzoom.querydsl.generator;

import static org.slf4j.LoggerFactory.getLogger;

import java.util.List;

import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import com.querydsl.core.group.GroupBy;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.QBean;
import com.querydsl.sql.SQLQueryFactory;

import it.mapsgroup.gzoom.persistence.common.CustomTxManager;
import it.mapsgroup.gzoom.querydsl.dto.QSecurityGroupPermission;
import it.mapsgroup.gzoom.querydsl.dto.QUserLoginSecurityGroup;
import it.mapsgroup.gzoom.querydsl.dto.SecurityGroupPermission;
import it.mapsgroup.gzoom.querydsl.dto.SecurityPermission;
import it.mapsgroup.gzoom.querydsl.persistence.service.QueryDslPersistenceConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = QueryDslPersistenceConfiguration.class)
@TestPropertySource("/gzoom.properties")
public class PermissionsTest {
    private static final Logger LOG = getLogger(PermissionsTest.class);

    @Autowired
    @Deprecated
    private DataSource mainDataSource;

    @Autowired
    private SQLQueryFactory queryFactory;

    @Autowired
    TransactionTemplate transactionTemplate;

    @Autowired
    PlatformTransactionManager txManager;

    @Test
    public void name() throws Exception {
        transactionTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        transactionTemplate.execute(status -> {
            System.out.println("TxManager.stamp1 " + ((CustomTxManager) txManager).getStamp());
            transactionTemplate.execute(status2 -> {
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
                System.out.println("TxManager.stamp2 " + ((CustomTxManager) txManager).getStamp());
                return null;
            });
            System.out.println("TxManager.stamp1 " + ((CustomTxManager) txManager).getStamp());
            return null;
        });
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
