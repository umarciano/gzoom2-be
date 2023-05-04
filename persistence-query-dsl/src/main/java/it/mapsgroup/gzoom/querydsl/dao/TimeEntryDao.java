package it.mapsgroup.gzoom.querydsl.dao;

import com.querydsl.core.Tuple;
import com.querydsl.core.group.GroupBy;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.QBean;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberExpression;
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

import java.sql.ResultSet;
import java.sql.SQLException;
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
    private final SQLQueryFactory queryFactoryUnion;
    private final SequenceGenerator sequenceGenerator;
    private final TransactionTemplate transactionTemplate;

    @Autowired
    public TimeEntryDao(SQLQueryFactory queryFactory, SQLQueryFactory queryFactoryUnion, SequenceGenerator sequenceGenerator,
                        TransactionTemplate transactionTemplate) {
        this.queryFactory = queryFactory;
        this.queryFactoryUnion = queryFactoryUnion;
        this.sequenceGenerator = sequenceGenerator;
        this.transactionTemplate = transactionTemplate;
    }

    @Transactional
    public List<TimeEntryEx> getTimeEntries(String id) {
        if (TransactionSynchronizationManager.isActualTransactionActive()) {
            TransactionStatus status = TransactionAspectSupport.currentTransactionStatus();
            status.getClass();
        }
        QTimeEntry TE = QTimeEntry.timeEntry;
        QWorkEffort WE = new QWorkEffort("we");
        QRateType RT = QRateType.rateType;

        SQLQuery<Tuple> tSQLQuery = queryFactory.select(TE,WE,RT)
                .from(TE)
                .innerJoin(WE).on(WE.workEffortId.eq(TE.workEffortId))
                .innerJoin(RT).on(RT.rateTypeId.eq(TE.rateTypeId))
                .where(TE.timesheetId.eq(id));

        SQLBindings bindings = tSQLQuery.getSQL();
        LOG.info("{}", bindings.getSQL());
        LOG.info("{}", bindings.getNullFriendlyBindings());

        QBean<TimeEntryEx> timeEntries = bean(TimeEntryEx.class,
                merge(TE.all(),
                        bean(RateType.class, RT.all()).as("rateType"),
                        bean(WorkEffort.class, WE.all()).as("workEffort")
                ));

        List<TimeEntryEx> ret = tSQLQuery.transform(GroupBy.groupBy(TE.timeEntryId).list(timeEntries));
        LOG.info("size = {}", ret.size());
        return ret;
    }

    @Transactional
    public  List<TimesheetEx> getTimesheet(String timesheetId, String userLoginId) throws SQLException {

        if (TransactionSynchronizationManager.isActualTransactionActive()) {
            TransactionStatus status = TransactionAspectSupport.currentTransactionStatus();
            status.getClass();
        }

        QTimesheet TM = new QTimesheet("tm");
        QParty PE = new QParty("pe");;
        QParty UO = new QParty("uo");;
        QUom UM = new QUom("um");
        QWorkEffortTypePeriod PDO = new  QWorkEffortTypePeriod("pdo");
        QStatusItem ST = new  QStatusItem("st");
        QWorkEffortType WT = new QWorkEffortType("wt");
        QUserLoginSecurityGroup ULSG = new QUserLoginSecurityGroup("ulsg");
        QUserLoginSecurityGroup UGR = new QUserLoginSecurityGroup("ugr");
        QSecurityGroupPermission SP = new  QSecurityGroupPermission("sp");
        QWorkEffortTypeContent WTC = new QWorkEffortTypeContent("wtc");
        QUserLoginPersistent UL = new QUserLoginPersistent("ul");
        QPartyParentRole PEPR = new QPartyParentRole("pepr");
        QPartyParentRole UOPR = new QPartyParentRole("uopr");
        QSecurityGroupPermission SGP = new QSecurityGroupPermission("sgp");
        QWorkEffortTypeStatus WTS = new QWorkEffortTypeStatus("wts");
        QPartyRelationship UOPE = new QPartyRelationship("uope");
        QPartyRelationship PR = new QPartyRelationship("pr");
        QPartyRelationship RELR = new QPartyRelationship("relr");
        QPartyRelationship RELD = new QPartyRelationship("reld");
        QPartyHistoryView PV = new QPartyHistoryView("pv");
        QWorkEffortType WX = new QWorkEffortType("wx");

        NumberExpression<Integer> updatable = Expressions.cases().when(ST.actStEnumId.eq("ACTSTATUS_CLOSED")).then(0)
                .when(ST.actStEnumId.ne("ACTSTATUS_CLOSED").and(PDO.statusEnumId.eq("CLOSE")).and(SGP.permissionId.like("%MGR_ADMIN"))).then(1)
                .when(ST.actStEnumId.ne("ACTSTATUS_CLOSED").and(PDO.statusEnumId.eq("CLOSE")).and(SGP.permissionId.ne("%MGR_ADMIN"))).then(0)
                .when(ST.actStEnumId.ne("ACTSTATUS_CLOSED").and(PDO.statusEnumId.ne("CLOSE")).and(SGP.permissionId.like("%MGR_ADMIN"))).then(1)
                .when(ST.actStEnumId.ne("ACTSTATUS_CLOSED").and(PDO.statusEnumId.ne("CLOSE")).and(SGP.permissionId.like("%ORG_ADMIN")).and(WTS.managWeStatusEnumId.eq("ORGMANAGER")).and(RELR.partyIdTo.isNotNull().or(RELD.partyIdTo.isNotNull()))).then(1)
                .when(ST.actStEnumId.ne("ACTSTATUS_CLOSED").and(PDO.statusEnumId.ne("CLOSE")).and(SGP.permissionId.like("%ROLE_ADMIN")).and(WTS.managementRoleTypeId.eq("WEM_EVAL_IN_CHARGE")).and(UL.partyId.eq(TM.partyId))).then(1)
                .otherwise(0);

        SQLQuery<Tuple> tupleSQLQuery = queryFactory.select(updatable.max().as("updatable"),TM,PV, PDO, PE, ST, UM, WTC)
                .from(TM)
                .innerJoin(ST).on(ST.statusId.eq(TM.statusId))
                .innerJoin(PE).on(PE.partyId.eq(TM.partyId))
                .innerJoin(PEPR).on(PEPR.partyId.eq(PE.partyId).and(PEPR.roleTypeId.eq("EMPLOYEE")))
                .innerJoin(UOPE).on(UOPE.partyIdTo.eq(PE.partyId)
                        .and(UOPE.partyRelationshipTypeId.eq("ORG_EMPLOYMENT"))
                        .and(UOPE.fromDate.before(TM.thruDate).and(UOPE.thruDate.isNull().or(UOPE.thruDate.after(TM.thruDate)))))
                .innerJoin(UO).on(UO.partyId.eq(UOPE.partyIdFrom))
                .innerJoin(UOPR).on(UOPR.partyId.eq(UO.partyId).and(UOPR.roleTypeId.eq("ORGANIZATION_UNIT")))
                .innerJoin(UM).on(UM.uomId.eq(TM.effortUomId))
                .innerJoin(PDO).on(PDO.workEffortTypePeriodId.eq(TM.workEffortTypePeriodId))
                .innerJoin(PV).on(PV.partyId.eq(TM.partyId).and(PV.fromDate.before(TM.thruDate)).and(PV.thruDate.isNull().or(PV.thruDate.after(TM.thruDate))))
                .innerJoin(WT).on(WT.workEffortTypeId.eq(PDO.workEffortTypeId))
                .innerJoin(WTC).on(WTC.workEffortTypeId.eq(WT.workEffortTypeId).and(WTC.contentId.eq("WEFLD_MAIN")))
                .innerJoin(UL).on(UL.userLoginId.eq(userLoginId))
                .innerJoin(WTS).on(WTS.workEffortTypeRootId.eq(WT.workEffortTypeId).and(WTS.currentStatusId.eq(TM.statusId)))
                .leftJoin(RELR).on(RELR.partyIdFrom.eq(UOPE.partyIdFrom)
                        .and(RELR.partyIdTo.eq(UL.partyId))
                        .and(RELR.partyRelationshipTypeId.eq("ORG_RESPONSIBLE"))
                        .and(RELR.fromDate.before(TM.thruDate)).and(RELR.thruDate.isNull().or(RELR.thruDate.after(TM.thruDate))))
                .leftJoin(RELD).on(RELD.partyIdFrom.eq(UOPE.partyIdFrom)
                        .and(RELD.partyIdTo.eq(UL.partyId))
                        .and(RELD.partyRelationshipTypeId.eq("ORG_DELEGATE"))
                        .and(RELD.fromDate.before(TM.thruDate).and(RELD.thruDate.isNull().or(RELD.thruDate.after(TM.thruDate)))))
                .innerJoin(WX).on(WX.workEffortTypeId.eq(WT.parentTypeId))
                .innerJoin(UGR).on(UGR.userLoginId.eq(UL.userLoginId))
                .innerJoin(SGP).on(SGP.groupId.eq(UGR.groupId).and(SGP.permissionId.like( Expressions.stringTemplate("concat({0},'%ADMIN')", WX.codePrefix))))
                .where(TM.timesheetId.eq(timesheetId))
        .groupBy(TM, PV, PDO, PE, PEPR, UO, UOPR, ST, UM, WTC);

        SQLBindings bindings = tupleSQLQuery.getSQL();
        LOG.info("{}", bindings.getSQL());
        LOG.info("{}", bindings.getNullFriendlyBindings());

        QBean<TimesheetEx> qBean = bean(TimesheetEx.class, merge(TM.all(),
                bean(PartyHistoryView.class, PV.all()).as("partyHistoryView"),
                bean(Party.class, PE.all()).as("party"),
                bean(PartyParentRole.class, PEPR.all()).as("partyParentRole"),
                bean(Uom.class, UM.all()).as("uom"),
                bean(WorkEffortTypePeriod.class, PDO.all()).as("workEffortTypePeriod"),
                bean(StatusItem.class, ST.all()).as("statusItem"),
                bean(WorkEffortTypeContent.class, WTC.all()).as("workEffortTypeContent")));
        List<TimesheetEx> ret = tupleSQLQuery.transform(GroupBy.groupBy(updatable.max().as("updatable"),TM.timesheetId).list(qBean));
        LOG.info("size = {}", ret.size());

        int result = 0;
        int cont = 0;
        ResultSet rs = tupleSQLQuery.getResults();
        while (rs.next()) {
            result = (rs.getInt(1));
            ret.get(cont).setUpdatable(result);
            cont = cont + 1;
        }
        return ret;
    }

    @Transactional
    public List<WorkEffort> getWorkEfforts(String id) {
        if (TransactionSynchronizationManager.isActualTransactionActive()) {
            TransactionStatus status = TransactionAspectSupport.currentTransactionStatus();
            status.getClass();
        }

        QWorkEffort WE = QWorkEffort.workEffort;
        QTimesheet TM = QTimesheet.timesheet;
        QPartyRelationship UOPE = QPartyRelationship.partyRelationship;
        QWorkEffortTypePeriod PDO = QWorkEffortTypePeriod.workEffortTypePeriod;
        QWorkEffortTypeType WTT = QWorkEffortTypeType.workEffortTypeType;
        QStatusItem SI = QStatusItem.statusItem;
        QWorkEffortPartyAssignment WEPA = QWorkEffortPartyAssignment.workEffortPartyAssignment;

        SQLQuery<WorkEffort> tupleSQLQuery = queryFactory.select(WE)
                .from(TM)
                .innerJoin(UOPE).on(UOPE.partyIdTo.eq(TM.partyId)
                        .and(UOPE.partyRelationshipTypeId.eq("ORG_EMPLOYMENT"))
                        .and(UOPE.fromDate.before(TM.thruDate).and(UOPE.thruDate.isNull().or(UOPE.thruDate.after(TM.thruDate)))))
                .innerJoin(PDO).on(PDO.workEffortTypePeriodId.eq(TM.workEffortTypePeriodId))
                .innerJoin(WTT).on(WTT.workEffortTypeIdRoot.eq(PDO.workEffortTypeId))
                .innerJoin(WE).on(WE.workEffortTypeId.eq(WTT.workEffortTypeIdTo)
                        .and(WE.estimatedStartDate.before(TM.thruDate).and(WE.estimatedCompletionDate.after(TM.fromDate)))
                        .and(WE.orgUnitId.eq(UOPE.partyIdFrom))
                        .and(WE.workEffortRevisionId.isNull())
                        .and(WE.organizationId.eq(PDO.organizationId)))
                .innerJoin(SI).on(SI.statusId.eq(WE.currentStatusId).and(SI.actStEnumId.eq("ACTSTATUS_ACTIVE")))
                .where(TM.timesheetId.eq(id));

        SQLQuery<WorkEffort> sq = queryFactoryUnion.select(WE).from(TM)
                .innerJoin(PDO).on(PDO.workEffortTypePeriodId.eq(TM.workEffortTypePeriodId))
                .innerJoin(WTT).on(WTT.workEffortTypeIdRoot.eq(PDO.workEffortTypeId))
                .innerJoin(WE).on(WE.workEffortTypeId.eq(WTT.workEffortTypeIdTo)
                        .and(WE.estimatedStartDate.before(TM.thruDate).and(WE.estimatedCompletionDate.after(TM.fromDate))))
                .innerJoin(WEPA).on(WEPA.workEffortId.eq(WE.workEffortId)
                        .and(WEPA.fromDate.before(TM.thruDate)).and(WEPA.thruDate.after(TM.thruDate)).and(WEPA.roleTypeId.eq("WEM_DUMMY")))
                .where(TM.timesheetId.eq(id));

        SQLBindings bindings = tupleSQLQuery.getSQL();
        SQLBindings second_bindings = sq.getSQL();

        LOG.info("{}", bindings.getSQL());
        LOG.info("{}", bindings.getNullFriendlyBindings());

        LOG.info("{}", second_bindings.getSQL());
        LOG.info("{}", second_bindings.getNullFriendlyBindings());

        QBean<WorkEffort> qBean = bean(WorkEffort.class, merge(WE.all()));

        List<WorkEffort> ret = tupleSQLQuery.transform(GroupBy.groupBy(WE.workEffortId).list(qBean));
        List<WorkEffort> listTwo = sq.transform(GroupBy.groupBy(WE.workEffortId).list(qBean));

        ret.addAll(listTwo);
        LOG.info("size = {}", ret.size());
        return ret;
    }

    @Transactional
    public boolean create(TimeEntry record, String userLoginId) {
        QTimeEntry qTimeEntry = QTimeEntry.timeEntry;
        setCreatedTimestamp(record);
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
        LOG.info("{}", bindings.getNullFriendlyBindings());
        QBean<TimeEntry> timeEntry = Projections.bean(TimeEntry.class, qTimeEntry.all());
        List<TimeEntry> ret = tSQLQuery.transform(GroupBy.groupBy(qTimeEntry.timeEntryId).list(timeEntry));

        return ret.isEmpty() ? null : ret.get(0);
    }

}
