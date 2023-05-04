package it.mapsgroup.gzoom.querydsl.dao;

import com.querydsl.core.group.GroupBy;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.QBean;
import com.querydsl.sql.SQLBindings;
import com.querydsl.sql.SQLQuery;
import com.querydsl.sql.SQLQueryFactory;
import it.mapsgroup.gzoom.persistence.common.SequenceGenerator;
import it.mapsgroup.gzoom.querydsl.dto.CustomTimePeriod;
import it.mapsgroup.gzoom.querydsl.dto.QCustomTimePeriod;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

@Service
public class CustomTimePeriodDao extends AbstractDao {

    private static final Logger LOG = getLogger(PartyDao.class);

    private final SequenceGenerator sequenceGenerator;
    private final SQLQueryFactory queryFactory;
    private final FilterPermissionDao filterPermissionDao;

    @Autowired
    public CustomTimePeriodDao(SequenceGenerator sequenceGenerator, SQLQueryFactory queryFactory, FilterPermissionDao filterPermissionDao) {
        this.sequenceGenerator = sequenceGenerator;
        this.queryFactory = queryFactory;
        this.filterPermissionDao = filterPermissionDao;
    }

    @Transactional
    public List<CustomTimePeriod> getCustomTimePeriods(String periodTypeId) {
        if (TransactionSynchronizationManager.isActualTransactionActive()) {
            TransactionStatus status = TransactionAspectSupport.currentTransactionStatus();
            status.getClass();
        }

        QCustomTimePeriod qCustomTimePeriod = QCustomTimePeriod.customTimePeriod;

        SQLQuery<CustomTimePeriod> pSQLQuery = queryFactory.select(qCustomTimePeriod)
                .from(qCustomTimePeriod)
                .where(qCustomTimePeriod.periodTypeId.eq(periodTypeId))
                .orderBy(qCustomTimePeriod.thruDate.asc());

        SQLBindings bindings = pSQLQuery.getSQL();
        LOG.info("{}", bindings.getSQL());
        LOG.info("{}", bindings.getNullFriendlyBindings());
        QBean<CustomTimePeriod> periods = Projections.bean(CustomTimePeriod.class, qCustomTimePeriod.all());
        List<CustomTimePeriod> ret = pSQLQuery.transform(GroupBy.groupBy(qCustomTimePeriod.customTimePeriodId).list(periods));
        LOG.info("size = {}", ret.size());
        return ret;
    }
}
