package it.mapsgroup.gzoom.querydsl.dao;

import com.querydsl.core.group.GroupBy;
import com.querydsl.core.types.QBean;
import com.querydsl.sql.SQLQueryFactory;
import it.mapsgroup.gzoom.querydsl.dto.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.List;

import static com.querydsl.core.types.Projections.bean;
import static it.mapsgroup.gzoom.querydsl.QBeanUtils.merge;

/**
 * @author Andrea Fossi.
 */
@Service
public class UserLoginDao {

    private final SQLQueryFactory queryFactory;

    public UserLoginDao(SQLQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Transactional
    public UserLogin getUserLogin(String username) {
        if (TransactionSynchronizationManager.isActualTransactionActive()) {
            TransactionStatus status = TransactionAspectSupport.currentTransactionStatus();
            status.getClass();
        }

        QUserLoginPersistent qUserLogin = QUserLoginPersistent.userLogin;
        QParty qParty = QParty.party;
        QPerson qPerson = QPerson.person;
        QUserPreference qUserPreference = QUserPreference.userPreference;

        QBean<UserLogin> userLoginExQBean = bean(UserLogin.class,
                merge(qUserLogin.all(),
                        bean(Party.class, qParty.all()).as("party"),
                        bean(Person.class, qPerson.all()).as("person"),
                        bean(UserPreference.class, qUserPreference.all()).as("userPreference")));

        List<UserLogin> ret = queryFactory.select(qUserLogin, qParty, qUserPreference)
                .from(qUserLogin)
                .innerJoin(qUserLogin.userParty, qParty)
                .innerJoin(qParty._personParty, qPerson)
                .leftJoin(qUserPreference).on(qUserPreference.userLoginId.eq(qUserLogin.userLoginId)
                    .and(qUserPreference.userPrefTypeId.eq("VISUAL_THEME")))
                .where(qUserLogin.userLoginId.eq(username))
                .transform(GroupBy.groupBy(qUserLogin.userLoginId)
                        .list(userLoginExQBean));
        return ret.isEmpty() ? null : ret.get(0);
    }
}
