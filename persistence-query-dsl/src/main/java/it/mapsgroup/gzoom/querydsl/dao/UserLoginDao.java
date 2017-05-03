package it.mapsgroup.gzoom.querydsl.dao;

import com.querydsl.core.group.GroupBy;
import com.querydsl.core.types.QBean;
import com.querydsl.sql.SQLQueryFactory;
import it.mapsgroup.gzoom.querydsl.dto.*;

import java.util.List;

import static com.querydsl.core.types.Projections.bean;

/**
 * @author Andrea Fossi.
 */
public class UserLoginDao {

    private SQLQueryFactory queryFactory;

    public SQLQueryFactory getQueryFactory() {
        return queryFactory;
    }

    public void setQueryFactory(SQLQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    public UserLoginEx findByUsername(String username) {
        QUserLogin qUserLogin = QUserLogin.userLogin;
        QParty qParty = QParty.party;
        QBean<UserLoginEx> userLoginExQBean = bean(UserLoginEx.class, bean(UserLogin.class, qUserLogin.all()).as("userLogin"), bean(Party.class, qParty.all()).as("party"));
        List<UserLoginEx> ret = queryFactory.select(qUserLogin, qParty).from(qUserLogin).innerJoin(qUserLogin.userParty, qParty)
                .where(qParty.partyId.isNotNull())
                .transform(GroupBy.groupBy(qUserLogin.userLoginId).list(userLoginExQBean));
        return ret.isEmpty() ? null : ret.get(0);
    }
}
