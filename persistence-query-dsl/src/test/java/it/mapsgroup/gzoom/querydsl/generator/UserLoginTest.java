package it.mapsgroup.gzoom.querydsl.generator;

import com.querydsl.core.group.GroupBy;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.QBean;
import com.querydsl.sql.SQLQueryFactory;
import it.mapsgroup.gzoom.persistence.common.CustomTxManager;
import it.mapsgroup.gzoom.querydsl.dto.*;
import it.mapsgroup.gzoom.querydsl.persistence.service.QueryDslPersistenceConfiguration;
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

import javax.sql.DataSource;
import java.util.List;

import static com.querydsl.core.types.Projections.bean;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author Andrea Fossi.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = QueryDslPersistenceConfiguration.class)
@TestPropertySource("/gzoom.properties")
public class UserLoginTest {
    private static final Logger LOG = getLogger(UserLoginTest.class);

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
                QUserLoginPersistent qUserLogin = QUserLoginPersistent.userLogin;
                Projections.bean(UserLogin.class);
                List<UserLoginPersistent> ret = queryFactory.select(qUserLogin).from(qUserLogin).where(qUserLogin.userLoginId.isNotNull().and(qUserLogin.enabled.isTrue()))
                        .transform(GroupBy.groupBy(qUserLogin.userLoginId).list(qUserLogin));

                ret.size();
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
        QUserLoginPersistent qUserLogin = QUserLoginPersistent.userLogin;
        QParty qParty = QParty.party;
        QBean<UserLogin> userLoginExQBean = bean(UserLogin.class, bean(UserLogin.class, qUserLogin.all()).as("userLogin"), bean(Party.class, qParty.all()).as("party"));
        List<UserLogin> ret = queryFactory.select(qUserLogin, qParty).from(qUserLogin).innerJoin(qUserLogin.userParty, qParty)
                .where(qParty.partyId.isNotNull())
                .transform(GroupBy.groupBy(qUserLogin.userLoginId).list(userLoginExQBean));
        ret.size();

      /*  Projections.bean(UserLogin.class);
        List<UserLogin> ret = queryFactory.select(qUserLogin).from(qUserLogin).where(qUserLogin.userLoginId.isNotNull().and(qUserLogin.enabled.isTrue()))
                .transform(GroupBy.groupBy(qUserLogin.userLoginId).list(qUserLogin));

        ret.size();*/

    }


}
