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
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.transaction.support.TransactionTemplate;

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
    private final TransactionTemplate transactionTemplate;
    private final PermissionDao permissionDao;
    
    @Autowired
    public TimesheetDao(SQLQueryFactory queryFactory, SequenceGenerator sequenceGenerator,
                        TransactionTemplate transactionTemplate,  PermissionDao permissionDao) {
        this.queryFactory = queryFactory;
        this.sequenceGenerator = sequenceGenerator;
        this.transactionTemplate = transactionTemplate;
        this.permissionDao = permissionDao;
    }

    @Transactional
    public List<TimesheetEx> getTimesheets(String userLoginId) {
        if (TransactionSynchronizationManager.isActualTransactionActive()) {
            TransactionStatus status = TransactionAspectSupport.currentTransactionStatus();
            status.getClass();
        }
               
        QTimesheet qTimesheet = QTimesheet.timesheet;
        QParty qParty = QParty.party;
        QPartyRelationship qPartyRelationshipUO = new QPartyRelationship("UO");
        QPartyRelationship qPartyRelationshipE = new QPartyRelationship("E");
        QPartyRelationship qPartyRelationshipY = new QPartyRelationship("Y");
        QPartyRelationship qPartyRelationshipZ = new QPartyRelationship("Z");
        QPartyRelationship qPartyRelationshipZ2 = new QPartyRelationship("Z2");
        QPartyRelationship qPartyRelationshipY2 = new QPartyRelationship("Y2");        
        QUserLoginPersistent qUserLogin = QUserLoginPersistent.userLogin;
        
        
        
        List<Predicate> predicates = new ArrayList<>();
        predicates.add(qParty.statusId.eq("PARTY_ENABLED"));
        
        List<SecurityPermission> listSecurityPermission = permissionDao.getPermission(userLoginId, "PROCPERF");        
        if(listSecurityPermission != null) {        	
            listSecurityPermission.forEach(r -> {
                String permissionId = r.getPermissionId();
                
                if (permissionId.contains("ROLE_ADMIN")) {
                	predicates.add(qUserLogin.partyId.eq(qParty.partyId));
                }
                if (permissionId.contains("ORG_ADMIN")) {
                	predicates.add(qPartyRelationshipE.partyIdTo.isNotNull());
                }
                if (permissionId.contains("SUP_ADMIN")) {
                	predicates.add(qPartyRelationshipY.partyIdTo.isNotNull());
                }
                if (permissionId.contains("TOP_ADMIN")) {
                	predicates.add(qPartyRelationshipY2.partyIdTo.isNotNull());
                }
            });            
        }
        

        QBean<TimesheetEx> timesheetExQBean = bean(TimesheetEx.class, merge(qTimesheet.all(), bean(Party.class, qParty.all()).as("party")));
        SQLQuery<Tuple> tupleSQLQuery = queryFactory.select(qTimesheet, qParty)
        				.from(qTimesheet)
        				.innerJoin(qParty).on(qParty.partyId.eq(qTimesheet.partyId)) 
        				
        				.innerJoin(qPartyRelationshipUO).on(qPartyRelationshipUO.partyIdTo.eq(qParty.partyId)
        						.and(qPartyRelationshipUO.partyRelationshipTypeId.eq("ORG_EMPLOYMENT"))
        						.and(qPartyRelationshipUO.thruDate.isNull()))         				
        				.innerJoin(qUserLogin).on(qUserLogin.userLoginId.eq(userLoginId)) 
        				
        				.leftJoin(qPartyRelationshipE).on(qPartyRelationshipE.roleTypeIdFrom.eq(qPartyRelationshipUO.roleTypeIdFrom)
        						.and(qPartyRelationshipE.partyIdFrom.eq(qPartyRelationshipUO.partyIdFrom))
        						.and(qPartyRelationshipE.partyRelationshipTypeId.in("ORG_RESPONSIBLE", "ORG_DELEGATE"))
        						.and(qPartyRelationshipE.thruDate.isNull())
        						.and(qPartyRelationshipE.partyIdTo.eq(qUserLogin.partyId)))        				
        				.leftJoin(qPartyRelationshipZ).on(qPartyRelationshipZ.roleTypeIdTo.eq(qPartyRelationshipUO.roleTypeIdFrom)
        						.and(qPartyRelationshipZ.partyIdTo.eq(qPartyRelationshipUO.partyIdFrom))
        						.and(qPartyRelationshipZ.partyRelationshipTypeId.eq("GROUP_ROLLUP"))
        						.and(qPartyRelationshipZ.thruDate.isNull()))
        				.leftJoin(qPartyRelationshipY).on(qPartyRelationshipY.roleTypeIdFrom.eq(qPartyRelationshipZ.roleTypeIdFrom)
        						.and(qPartyRelationshipY.partyIdFrom.eq(qPartyRelationshipZ.partyIdFrom))
        						.and(qPartyRelationshipY.partyRelationshipTypeId.in("ORG_RESPONSIBLE", "ORG_DELEGATE"))
        						.and(qPartyRelationshipY.thruDate.isNull())
        						.and(qPartyRelationshipY.partyIdTo.eq(qUserLogin.partyId)))
        				.leftJoin(qPartyRelationshipZ2).on(qPartyRelationshipZ2.roleTypeIdTo.eq(qPartyRelationshipZ.roleTypeIdFrom)
        						.and(qPartyRelationshipZ2.partyIdTo.eq(qPartyRelationshipZ.partyIdFrom))
        						.and(qPartyRelationshipZ2.partyRelationshipTypeId.eq("GROUP_ROLLUP"))
        						.and(qPartyRelationshipZ2.thruDate.isNull()))
        				.leftJoin(qPartyRelationshipY2).on(qPartyRelationshipY2.roleTypeIdFrom.eq(qPartyRelationshipZ2.roleTypeIdFrom)
        						.and(qPartyRelationshipY2.partyIdFrom.eq(qPartyRelationshipZ2.partyIdFrom))
        						.and(qPartyRelationshipY2.partyRelationshipTypeId.in("ORG_RESPONSIBLE", "ORG_DELEGATE"))
        						.and(qPartyRelationshipY2.thruDate.isNull())
        						.and(qPartyRelationshipY2.partyIdTo.eq(qUserLogin.partyId)))
        				.where(predicates.toArray(new Predicate[0]))
        				.orderBy(qParty.partyName.asc());
        
        
        SQLBindings bindings = tupleSQLQuery.getSQL();
        LOG.info("{}", bindings.getSQL());
        LOG.info("{}", bindings.getBindings());
        //QBean<Timesheet> timesheets = Projections.bean(Timesheet.class, qTimesheet.all());
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
        LOG.info("{}", bindings.getBindings()); // debug
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
