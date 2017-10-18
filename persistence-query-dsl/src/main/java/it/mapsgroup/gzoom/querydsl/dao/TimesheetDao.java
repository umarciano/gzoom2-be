package it.mapsgroup.gzoom.querydsl.dao;

import com.querydsl.core.group.GroupBy;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.QBean;
import com.querydsl.sql.SQLBindings;
import com.querydsl.sql.SQLQuery;
import com.querydsl.sql.SQLQueryFactory;
import it.mapsgroup.gzoom.querydsl.dto.QTimesheet;
import it.mapsgroup.gzoom.querydsl.dto.Timesheet;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.sql.Time;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

/**
 */
@Service
public class TimesheetDao extends AbstractDao {
    private static final Logger LOG = getLogger(TimesheetDao.class);

    private final SQLQueryFactory queryFactory;

    public TimesheetDao(SQLQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Transactional
    public List<Timesheet> getTimesheets() {
        if (TransactionSynchronizationManager.isActualTransactionActive()) {
            TransactionStatus status = TransactionAspectSupport.currentTransactionStatus();
            status.getClass();
        }
        QTimesheet qTimesheet = QTimesheet.timesheet;
        SQLQuery<Timesheet> tSQLQuery = queryFactory.select(qTimesheet).from(qTimesheet);
        SQLBindings bindings = tSQLQuery.getSQL();
        LOG.info("{}", bindings.getSQL());
        LOG.info("{}", bindings.getBindings());
        QBean<Timesheet> timesheets = Projections.bean(Timesheet.class, qTimesheet.all());
        List<Timesheet> ret = tSQLQuery.transform(GroupBy.groupBy(qTimesheet.timesheetId).list(timesheets));
        LOG.info("size = {}", ret.size());
        return ret;
    }

    @Transactional
    public boolean create(Timesheet record, String userLoginId) {
        QTimesheet qTimesheet = QTimesheet.timesheet;
        setCreatedTimestamp(record);
        //setCreatedByUserLogin(record, userLoginId);
        long i = queryFactory.insert(qTimesheet).populate(record).execute();
        LOG.info("created records: {}", i);

        return i > 0;
    }

}
