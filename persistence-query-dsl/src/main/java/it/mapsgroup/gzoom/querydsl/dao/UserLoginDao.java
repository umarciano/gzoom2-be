package it.mapsgroup.gzoom.querydsl.dao;

import com.querydsl.core.group.GroupBy;
import com.querydsl.core.types.QBean;
import com.querydsl.sql.SQLQueryFactory;
import it.mapsgroup.gzoom.querydsl.dto.*;
import org.springframework.stereotype.Service;

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

    public UserLoginEx findByUsername(String username) {
        QUserLogin qUserLogin = QUserLogin.userLogin;
        QParty qParty = QParty.party;
        QPerson qPerson = QPerson.person;
        QBean<UserLoginEx> userLoginExQBean = bean(UserLoginEx.class,
                merge(qUserLogin.all(),
                        bean(Party.class, qParty.all()).as("party"),
                        bean(Person.class, qPerson.all()).as("person")));

        List<UserLoginEx> ret = queryFactory.select(qUserLogin, qParty)
                .from(qUserLogin)
                .innerJoin(qUserLogin.userParty, qParty)
                .innerJoin(qParty._personParty, qPerson)
                .where(qUserLogin.userLoginId.eq(username))
                .transform(GroupBy.groupBy(qUserLogin.userLoginId)
                        .list(userLoginExQBean));
        return ret.isEmpty() ? null : ret.get(0);
    }
}
