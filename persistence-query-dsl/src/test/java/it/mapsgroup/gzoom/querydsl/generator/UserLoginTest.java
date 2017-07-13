package it.mapsgroup.gzoom.querydsl.generator;

import com.querydsl.core.group.GroupBy;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.QBean;
import com.querydsl.sql.SQLQueryFactory;
import it.mapsgroup.gzoom.querydsl.dao.AbstractDaoIT;
import it.mapsgroup.gzoom.querydsl.dto.*;
import org.junit.Test;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;
import java.util.List;

import static com.querydsl.core.types.Projections.bean;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author Andrea Fossi.
 */

public class UserLoginTest extends AbstractDaoIT {
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

        transactionTemplate.execute(status2 -> {
            QUserLoginPersistent qUserLogin = QUserLoginPersistent.userLogin;
            Projections.bean(UserLogin.class);
            List<UserLoginPersistent> ret = queryFactory.select(qUserLogin).from(qUserLogin).where(qUserLogin.userLoginId.isNotNull().and(qUserLogin.enabled.isTrue()))
                    .transform(GroupBy.groupBy(qUserLogin.userLoginId).list(qUserLogin));

            ret.size();
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
