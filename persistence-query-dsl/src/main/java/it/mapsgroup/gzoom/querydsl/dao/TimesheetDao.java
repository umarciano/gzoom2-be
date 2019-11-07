package it.mapsgroup.gzoom.querydsl.dao;

import com.querydsl.core.Tuple;
import com.querydsl.core.group.GroupBy;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.QBean;
import com.querydsl.sql.SQLBindings;
import com.querydsl.sql.SQLQuery;
import com.querydsl.sql.SQLQueryFactory;

import it.mapsgroup.gzoom.persistence.common.SequenceGenerator;
import it.mapsgroup.gzoom.querydsl.dto.*;
import it.mapsgroup.gzoom.querydsl.service.PermissionService;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import static com.querydsl.core.types.Projections.bean;
import static it.mapsgroup.gzoom.querydsl.QBeanUtils.merge;

import static org.slf4j.LoggerFactory.getLogger;

import java.util.ArrayList;
import java.util.List;

/**
 */
@Service
public class TimesheetDao extends AbstractDao {
    private static final Logger LOG = getLogger(TimesheetDao.class);

    private final SQLQueryFactory queryFactory;
    private final SequenceGenerator sequenceGenerator;
    private final PermissionService permissionService;
    private final FilterPermissionDao filterPermissionDao;
    
    @Autowired
    public TimesheetDao(SQLQueryFactory queryFactory, SequenceGenerator sequenceGenerator, PermissionService permissionService, FilterPermissionDao filterPermissionDao) {
        this.queryFactory = queryFactory;
        this.sequenceGenerator = sequenceGenerator;
        this.permissionService = permissionService;
        this.filterPermissionDao = filterPermissionDao;
    }

    @Transactional
    public List<TimesheetEx> getTimesheets(String userLoginId) {
        if (TransactionSynchronizationManager.isActualTransactionActive()) {
            TransactionStatus status = TransactionAspectSupport.currentTransactionStatus();
            status.getClass();
        }
               
        QTimesheet qTimesheet = QTimesheet.timesheet;
        QParty qParty = QParty.party;
       
        SQLQuery<Tuple> tupleSQLQuery = queryFactory.select(qTimesheet, qParty)
				.from(qTimesheet)
				.innerJoin(qParty).on(qParty.partyId.eq(qTimesheet.partyId));
        
        
        tupleSQLQuery = (SQLQuery<Tuple>) filterPermissionDao.getFilterQueryPerson(tupleSQLQuery, qParty, userLoginId, "CTX_PR");
        
        SQLBindings bindings = tupleSQLQuery.getSQL();
        LOG.info("{}", bindings.getSQL());
        LOG.info("{}", bindings.getNullFriendlyBindings());
        //QBean<Timesheet> timesheets = Projections.bean(Timesheet.class, qTimesheet.all());
        QBean<TimesheetEx> timesheetExQBean = bean(TimesheetEx.class, merge(qTimesheet.all(), bean(Party.class, qParty.all()).as("party")));
        List<TimesheetEx> ret = tupleSQLQuery.transform(GroupBy.groupBy(qTimesheet.timesheetId).list(timesheetExQBean));
        LOG.info("size = {}", ret.size());
        return ret;
    }
    
 
    
    @Transactional
    public TimesheetEx getTimesheetExt(String timesheetId) {
        if (TransactionSynchronizationManager.isActualTransactionActive()) {
            TransactionStatus status = TransactionAspectSupport.currentTransactionStatus();
            status.getClass();
        }
        QTimesheet qTimesheet = QTimesheet.timesheet;
        QParty qParty = QParty.party;
        
        QBean<TimesheetEx> timesheetExQBean = bean(TimesheetEx.class, merge(qTimesheet.all(), bean(Party.class, qParty.all()).as("party")));
        SQLQuery<Tuple> tupleSQLQuery = queryFactory.select(qTimesheet, qParty).from(qTimesheet).innerJoin(qTimesheet.timesheetPrty, qParty).where(qTimesheet.timesheetId.eq(timesheetId));
        
        SQLBindings bindings = tupleSQLQuery.getSQL();
        LOG.info("{}", bindings.getSQL());
        LOG.info("{}", bindings.getNullFriendlyBindings());
        List<TimesheetEx> ret = tupleSQLQuery.transform(GroupBy.groupBy(qTimesheet.timesheetId).list(timesheetExQBean));

        return ret.isEmpty() ? null : ret.get(0);
    }

    @Transactional
    public boolean create(Timesheet record, String userLoginId) {
        QTimesheet qTimesheet = QTimesheet.timesheet;
        setCreatedTimestamp(record);
        //setCreatedByUserLogin(record, userLoginId);
        //transactionTemplate.execute(txStatus -> {
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
        LOG.info("{}", bindings.getNullFriendlyBindings());
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
