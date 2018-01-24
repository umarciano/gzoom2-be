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

import java.math.BigInteger;
import java.util.List;

import static com.querydsl.core.types.Projections.bean;
import static it.mapsgroup.gzoom.querydsl.QBeanUtils.merge;
import static org.slf4j.LoggerFactory.getLogger;

/**
 */
@Service
public class TimeEntryDao extends AbstractDao {
    private static final Logger LOG = getLogger(TimeEntryDao.class);

    private final SQLQueryFactory queryFactory;
    private final SequenceGenerator sequenceGenerator;
    private final TransactionTemplate transactionTemplate;

    @Autowired
    public TimeEntryDao(SQLQueryFactory queryFactory, SequenceGenerator sequenceGenerator,
                        TransactionTemplate transactionTemplate) {
        this.queryFactory = queryFactory;
        this.sequenceGenerator = sequenceGenerator;
        this.transactionTemplate = transactionTemplate;
    }

    @Transactional
    public List<TimeEntry> getTimeEntries(String id) {
        if (TransactionSynchronizationManager.isActualTransactionActive()) {
            TransactionStatus status = TransactionAspectSupport.currentTransactionStatus();
            status.getClass();
        }
        QTimeEntry qte = QTimeEntry.timeEntry;
        SQLQuery<TimeEntry> tSQLQuery = queryFactory.select(qte).from(qte).where(qte.timesheetId.eq(id));
        SQLBindings bindings = tSQLQuery.getSQL();
        LOG.info("{}", bindings.getSQL());
        LOG.info("{}", bindings.getBindings());
        QBean<TimeEntry> timeEntries = Projections.bean(TimeEntry.class, qte.all());
        List<TimeEntry> ret = tSQLQuery.transform(GroupBy.groupBy(qte.timeEntryId).list(timeEntries));
        LOG.info("size = {}", ret.size());
        return ret;
    }

    @Transactional
    public List<Activity> getWorkEfforts(String id) {
        if (TransactionSynchronizationManager.isActualTransactionActive()) {
            TransactionStatus status = TransactionAspectSupport.currentTransactionStatus();
            status.getClass();
        }

        QTimesheet ts = new QTimesheet("ts");
        QTimeEntry te = new QTimeEntry("te");
        QWorkEffort l1 = new QWorkEffort("l1");
        QWorkEffort l2 = new QWorkEffort("l2");
        QWorkEffort l3 = new QWorkEffort("l3");
        QWorkEffortAssoc l12 = new QWorkEffortAssoc("l12");
        QWorkEffortAssoc l23 = new QWorkEffortAssoc("l23");
        QWorkEffortType wt = new QWorkEffortType("wt");
        QWorkEffortTypeType t1 = new QWorkEffortTypeType("t1");
        QWorkEffortTypeType t2 = new QWorkEffortTypeType("t2");
        QWorkEffortTypeType t3 = new QWorkEffortTypeType("t3");
        QWorkEffortPartyAssignment wpa = new QWorkEffortPartyAssignment("wpa");

        QBean<Activity> teExQBean = bean(Activity.class,
                merge(ts.all(),
                        bean(WorkEffort.class, l1.all()).as("workEffort1"),
                        bean(WorkEffort.class, l2.all()).as("workEffort2"),
                        bean(WorkEffort.class, l3.all()).as("workEffort3")
                ));

        SQLQuery<Tuple> tupleSQLQuery = queryFactory.
                /*select(l1.workEffortName.as("attivitaLiv1"), l1.workEffortId.as("IdLiv1")
                        ,l2.workEffortName.as("attivitaLiv2"), l2.workEffortId.as("IdLiv2")
                        ,l3.workEffortName.as("attivitaLiv3"), l3.workEffortId.as("IdLiv3")
                )*/
                select(ts,l1,l2,l3)
                .from(ts)
                .innerJoin(wt).on(wt.etch.eq("TIMESHEET"))
                .innerJoin(t1).on(t1.workEffortTypeIdRoot.eq(wt.workEffortTypeId).and(t1.sequenceNum.eq(new BigInteger("1"))))
                .innerJoin(l1).on(l1.workEffortTypeId.eq(t1.workEffortTypeIdTo)
                        .and(l1.estimatedStartDate.before(ts.thruDate))
                        .and(l1.estimatedCompletionDate.after(ts.fromDate))
                        .and(l1.workEffortRevisionId.isNull()))
            .innerJoin(wpa).on(wpa.workEffortId.eq(l1.workEffortId)
                    .and(wpa.partyId.eq(ts.partyId))
                    .and(wpa.fromDate.before(ts.thruDate))
                    .and(wpa.thruDate.after(ts.fromDate)))
            .innerJoin(t2).on(t2.workEffortTypeIdRoot.eq(wt.workEffortTypeId)
                    .and(t2.sequenceNum.eq(new BigInteger("2"))))
            .innerJoin(l12).on(l12.workEffortIdFrom.eq(l1.workEffortId))
            .innerJoin(l2).on(l2.workEffortId.eq(l12.workEffortIdTo)
                    .and(l2.workEffortTypeId.eq(t2.workEffortTypeIdTo))
                    .and(l2.estimatedStartDate.before(ts.thruDate))
                    .and(l2.estimatedCompletionDate.after(ts.fromDate))
                    .and(l1.workEffortRevisionId.isNull()))
            .innerJoin(t3).on(t3.workEffortTypeIdRoot.eq(wt.workEffortTypeId)
                    .and(t3.sequenceNum.eq(new BigInteger("3"))))
            .innerJoin(l23).on(l23.workEffortIdFrom.eq(l2.workEffortId))
            .innerJoin(l3).on(l3.workEffortId.eq(l23.workEffortIdTo)
                    .and(l3.workEffortTypeId.eq(t3.workEffortTypeIdTo))
                    .and(l3.estimatedStartDate.before(ts.thruDate))
                    .and(l3.estimatedCompletionDate.after(ts.fromDate))
                    .and(l3.workEffortRevisionId.isNull()))

                .where(ts.timesheetId.eq(id));
        SQLBindings bindings = tupleSQLQuery.getSQL();
        LOG.info("{}", bindings.getSQL());
        LOG.info("{}", bindings.getBindings());

        List<Activity> ret = tupleSQLQuery.transform(GroupBy.groupBy(l3.workEffortId).list(teExQBean));
        LOG.info("size = {}", ret.size());
        return ret;
    }

    @Transactional
    public boolean create(TimeEntry record, String userLoginId) {
        QTimeEntry qTimeEntry = QTimeEntry.timeEntry;
        setCreatedTimestamp(record);
        TimeEntry t = new TimeEntry();
        String id = sequenceGenerator.getNextSeqId("TimeEntry");
        LOG.debug("new timeEntryId" + id);
        record.setTimeEntryId(id);
        long i = queryFactory.insert(qTimeEntry).populate(record).execute();
        LOG.info("created records: {}", i);
        return i > 0;
    }

    @Transactional
    public boolean update(String id, TimeEntry record, String userLoginId) {
        QTimeEntry qTimeEntry = QTimeEntry.timeEntry;
        setUpdateTimestamp(record);
        long i = queryFactory.update(qTimeEntry).set(qTimeEntry.timeEntryId, record.getTimeEntryId()).where(qTimeEntry.timeEntryId.eq(id)).populate(record).execute();
        LOG.info("updated records: {}", i);
        return i > 0;
    }

    @Transactional
    public boolean delete(String id) {
        QTimeEntry qTimeEntry = QTimeEntry.timeEntry;
        long i = queryFactory.delete(qTimeEntry).where(qTimeEntry.timeEntryId.eq(id)).execute();
        LOG.info("deleted records: {}", i);
        return i > 0;
    }

    @Transactional
    public TimeEntry getTimeEntry(String timeEntryId) {
        if (TransactionSynchronizationManager.isActualTransactionActive()) {
            TransactionStatus status = TransactionAspectSupport.currentTransactionStatus();
            status.getClass();
        }
        QTimeEntry qTimeEntry = QTimeEntry.timeEntry;
        SQLQuery<TimeEntry> tSQLQuery = queryFactory.select(qTimeEntry).from(qTimeEntry).where(qTimeEntry.timeEntryId.eq(timeEntryId));
        SQLBindings bindings = tSQLQuery.getSQL();
        LOG.info("{}", bindings.getSQL());
        LOG.info("{}", bindings.getBindings());
        QBean<TimeEntry> timeEntry = Projections.bean(TimeEntry.class, qTimeEntry.all());
        List<TimeEntry> ret = tSQLQuery.transform(GroupBy.groupBy(qTimeEntry.timeEntryId).list(timeEntry));

        return ret.isEmpty() ? null : ret.get(0);
    }

}
