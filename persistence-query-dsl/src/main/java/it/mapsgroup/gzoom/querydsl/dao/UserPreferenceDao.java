package it.mapsgroup.gzoom.querydsl.dao;

import com.querydsl.sql.SQLQueryFactory;
import it.mapsgroup.gzoom.querydsl.dto.QUserPreference;
import it.mapsgroup.gzoom.querydsl.dto.UserPreference;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public boolean update(UserPreference record) {
        QUserPreference qUserPreference = QUserPreference.userPreference;
        setUpdateTimestamp(record);
        long i = queryFactory.update(qUserPreference).populate(record).execute();
        LOG.debug("updated records: {}", i);
        return i > 0;
    }
}
