package it.mapsgroup.gzoom.querydsl.dao;

import com.querydsl.core.Tuple;
import com.querydsl.core.group.GroupBy;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.QBean;
import com.querydsl.sql.SQLBindings;
import com.querydsl.sql.SQLQuery;
import com.querydsl.sql.SQLQueryFactory;
import it.mapsgroup.gzoom.persistence.common.SequenceGenerator;
import it.mapsgroup.gzoom.querydsl.dto.*;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.List;

import static com.querydsl.core.types.Projections.bean;
import static it.mapsgroup.gzoom.querydsl.QBeanUtils.merge;
import static org.slf4j.LoggerFactory.getLogger;

/**
 */
@Service
public class TimesheetDao extends AbstractDao {
    private static final Logger LOG = getLogger(TimesheetDao.class);

    private final SQLQueryFactory queryFactory;
    private final SequenceGenerator sequenceGenerator;
    private final TransactionTemplate transactionTemplate;

    @Autowired
    public TimesheetDao(SQLQueryFactory queryFactory, SequenceGenerator sequenceGenerator,
                        TransactionTemplate transactionTemplate) {
        this.queryFactory = queryFactory;
        this.sequenceGenerator = sequenceGenerator;
        this.transactionTemplate = transactionTemplate;
    }

    @Transactional
    public List<TimesheetEx> getTimesheets() {
        if (TransactionSynchronizationManager.isActualTransactionActive()) {
            TransactionStatus status = TransactionAspectSupport.currentTransactionStatus();
            status.getClass();
        }
        QTimesheet qTimesheet = QTimesheet.timesheet;
        QParty qParty = QParty.party;
        //SQLQuery<Timesheet> tSQLQuery = queryFactory.select(qTimesheet).from(qTimesheet);
        QBean<TimesheetEx> timesheetExQBean = bean(TimesheetEx.class, merge(qTimesheet.all(), bean(Party.class, qParty.all()).as("party")));
        SQLQuery<Tuple> tupleSQLQuery = queryFactory.select(qTimesheet, qParty).from(qTimesheet).innerJoin(qTimesheet.timesheetPrty, qParty);
        SQLBindings bindings = tupleSQLQuery.getSQL();
        LOG.info("{}", bindings.getSQL());
        LOG.info("{}", bindings.getBindings());
        //QBean<Timesheet> timesheets = Projections.bean(Timesheet.class, qTimesheet.all());
        List<TimesheetEx> ret = tupleSQLQuery.transform(GroupBy.groupBy(qTimesheet.timesheetId).list(timesheetExQBean));
        LOG.info("size = {}", ret.size());
        return ret;
    }

    @Transactional
    public boolean create(Timesheet record, String userLoginId) {
        QTimesheet qTimesheet = QTimesheet.timesheet;
        setCreatedTimestamp(record);
        //setCreatedByUserLogin(record, userLoginId);
        //transactionTemplate.execute(txStatus -> {
        Timesheet t = new Timesheet();
        String id = sequenceGenerator.getNextSeqId("Timesheet");
        LOG.debug("new timesheetId" + id);
        record.setTimesheetId(id);
        long i = queryFactory.insert(qTimesheet).populate(record).execute();
        LOG.info("created records: {}", i);

        return i > 0;
        //});
    }

    @Transactional
    public Timesheet getTimesheet(String timesheetId) {
        if (TransactionSynchronizationManager.isActualTransactionActive()) {
            TransactionStatus status = TransactionAspectSupport.currentTransactionStatus();
            status.getClass();
        }
        QTimesheet qTimesheet = QTimesheet.timesheet;
        SQLQuery<Timesheet> tSQLQuery = queryFactory.select(qTimesheet).from(qTimesheet).where(qTimesheet.timesheetId.eq(timesheetId));
        SQLBindings bindings = tSQLQuery.getSQL();
        LOG.info("{}", bindings.getSQL());
        LOG.info("{}", bindings.getBindings());
        QBean<Timesheet> timesheets = Projections.bean(Timesheet.class, qTimesheet.all());
        List<Timesheet> ret = tSQLQuery.transform(GroupBy.groupBy(qTimesheet.timesheetId).list(timesheets));

        return ret.isEmpty() ? null : ret.get(0);
    }

    @Transactional
    public boolean update(String id, Timesheet record, String userLoginId) {
        QTimesheet qTimesheet = QTimesheet.timesheet;
        setUpdateTimestamp(record);
        //setLastModifiedByUserLogin(record, userLoginId);
        // Using bean population
        long i = queryFactory.update(qTimesheet).set(qTimesheet.timesheetId, record.getTimesheetId()).where(qTimesheet.timesheetId.eq(id)).populate(record).execute();
        LOG.info("updated records: {}", i);
        return i > 0;
    }

    @Transactional
    public boolean delete(String id) {
        QTimesheet qTimesheet = QTimesheet.timesheet;
        long i = queryFactory.delete(qTimesheet).where(qTimesheet.timesheetId.eq(id)).execute();
        LOG.info("deleted records: {}", i);
        return i > 0;
    }

}
