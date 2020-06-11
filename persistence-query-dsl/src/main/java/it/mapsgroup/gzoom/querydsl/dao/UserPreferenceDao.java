package it.mapsgroup.gzoom.querydsl.dao;

import com.querydsl.core.group.GroupBy;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.QBean;
import com.querydsl.sql.SQLBindings;
import com.querydsl.sql.SQLQuery;
import com.querydsl.sql.SQLQueryFactory;
import it.mapsgroup.gzoom.querydsl.dto.QTimeEntry;
import it.mapsgroup.gzoom.querydsl.dto.QUserPreference;
import it.mapsgroup.gzoom.querydsl.dto.TimeEntry;
import it.mapsgroup.gzoom.querydsl.dto.UserPreference;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

@Service
public class UserPreferenceDao extends AbstractDao {

    private static final Logger LOG = getLogger(UserPreferenceDao.class);

    private final SQLQueryFactory queryFactory;

    @Autowired
    public UserPreferenceDao(SQLQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Transactional
    public UserPreference getUserPreference(String userLoginId, String userPrefTypeId) {
        QUserPreference qUserPreference = QUserPreference.userPreference;
        SQLQuery<UserPreference> tSQLQuery = queryFactory.select(qUserPreference).from(qUserPreference)
                .where(qUserPreference.userLoginId.eq(userLoginId)
                        .and(qUserPreference.userPrefTypeId.eq(userPrefTypeId)));
        SQLBindings bindings = tSQLQuery.getSQL();
        LOG.info("{}", bindings.getSQL());
        LOG.info("{}", bindings.getNullFriendlyBindings());
        QBean<UserPreference> userPreference = Projections.bean(UserPreference.class, qUserPreference.all());
        List<UserPreference> ret = tSQLQuery.transform(GroupBy.groupBy(qUserPreference.userLoginId).list(userPreference));

        return ret.isEmpty() ? null : ret.get(0);
    }

    @Transactional
    public boolean create(String userLoginId, UserPreference record) {
        QUserPreference qUserPreference = QUserPreference.userPreference;
        setCreatedTimestamp(record);
        record.setUserLoginId(userLoginId);
        record.setUserPrefGroupTypeId("GLOBAL_PREFERENCES");
        long i = queryFactory.insert(qUserPreference).populate(record).execute();
        LOG.info("created records: {}", i);
        return i > 0;
    }

    @Transactional
    public boolean update(String loginId, UserPreference record) {
        QUserPreference qUserPreference = QUserPreference.userPreference;
        setUpdateTimestamp(record);
        long i = queryFactory.update(qUserPreference)
                .where(qUserPreference.userLoginId.eq(loginId)
                .and(qUserPreference.userPrefTypeId.eq(record.getUserPrefTypeId())))
                .populate(record).execute();
        LOG.debug("updated records: {}", i);
        return i > 0;
    }
}
