package it.mapsgroup.gzoom.querydsl.dao;

import com.querydsl.core.group.GroupBy;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.QBean;
import com.querydsl.sql.SQLBindings;
import com.querydsl.sql.SQLQuery;
import com.querydsl.sql.SQLQueryFactory;
import it.mapsgroup.gzoom.querydsl.dto.PeriodType;
import it.mapsgroup.gzoom.querydsl.dto.QPeriodType;
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
public class PeriodTypeDao extends AbstractDao {

    private static final Logger LOG = getLogger(PeriodTypeDao.class);

    private final SQLQueryFactory queryFactory;

    @Autowired
    public PeriodTypeDao(SQLQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Transactional
    public List<PeriodType> getPeriodTypes() {
        if (TransactionSynchronizationManager.isActualTransactionActive()) {
            TransactionStatus status = TransactionAspectSupport.currentTransactionStatus();
            status.getClass();
        }

        QPeriodType qPeriodType = QPeriodType.periodType;
        SQLQuery<PeriodType> tupleSQLQuery = queryFactory.select(qPeriodType).from(qPeriodType);

        SQLBindings bindings = tupleSQLQuery.getSQL();
        LOG.info("{}", bindings.getSQL());
        LOG.info("{}", bindings.getNullFriendlyBindings());
        QBean<PeriodType> periodTypes = Projections.bean(PeriodType.class, qPeriodType.all());
        List<PeriodType> ret = tupleSQLQuery.transform(GroupBy.groupBy(qPeriodType.periodTypeId).list(periodTypes));
        return ret;
    }


    @Transactional
    public PeriodType getPeriodType(String periodTypeId) {
        if (TransactionSynchronizationManager.isActualTransactionActive()) {
            TransactionStatus status = TransactionAspectSupport.currentTransactionStatus();
            status.getClass();
        }
        QPeriodType qPeriodType = QPeriodType.periodType;
        SQLQuery<PeriodType> tSQLQuery = queryFactory.select(qPeriodType).from(qPeriodType).where(qPeriodType.periodTypeId.eq(periodTypeId));
        SQLBindings bindings = tSQLQuery.getSQL();
        LOG.info("{}", bindings.getSQL());
        LOG.info("{}", bindings.getNullFriendlyBindings());
        QBean<PeriodType> periodTypeQBean = Projections.bean(PeriodType.class, qPeriodType.all());
        List<PeriodType> ret = tSQLQuery.transform(GroupBy.groupBy(qPeriodType.periodTypeId).list(periodTypeQBean));

        return ret.isEmpty() ? null : ret.get(0);
    }

    @Transactional
    public boolean update(PeriodType periodType) {
        QPeriodType qPeriodType = QPeriodType.periodType;
        setUpdateTimestamp(periodType);
        long i = queryFactory.update(qPeriodType).set(qPeriodType.periodTypeId, periodType.getPeriodTypeId()  ).where(qPeriodType.periodTypeId.eq(periodType.getPeriodTypeId())).populate(periodType).execute();
        LOG.info("updated records: {}", i);
        return i > 0;
    }

    @Transactional
    public boolean delete(String id) {
        QPeriodType qPeriodType = QPeriodType.periodType;
        long i = queryFactory.delete(qPeriodType).where(qPeriodType.periodTypeId.eq(id)).execute();
        LOG.info("deleted records: {}", i);
        return i > 0;
    }

    @Transactional
    public boolean create(PeriodType periodType) {
        QPeriodType qPeriodType = QPeriodType.periodType;
        setCreatedTimestamp(periodType);
        long i = queryFactory.insert(qPeriodType).populate(periodType).execute();
        LOG.info("created records: {}", i);
        return i > 0;
    }
}
