package it.mapsgroup.gzoom.querydsl.dao;

import com.querydsl.core.Tuple;
import com.querydsl.core.group.GroupBy;
import com.querydsl.core.types.QBean;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberExpression;
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

import java.sql.ResultSet;
import java.sql.SQLException;
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
    public   TimesheetEx getTimesheet(String id) {
        if (TransactionSynchronizationManager.isActualTransactionActive()) {
            TransactionStatus status = TransactionAspectSupport.currentTransactionStatus();
            status.getClass();
        }

        QTimesheet TM = QTimesheet.timesheet;
        SQLQuery<Timesheet> tupleSQLQuery = queryFactory.select(TM).from(TM).where(TM.timesheetId.eq(id));

        QBean<TimesheetEx> qBean = bean(TimesheetEx.class, merge(TM.all()));

        SQLBindings bindings = tupleSQLQuery.getSQL();
        LOG.info("{}", bindings.getSQL());
        LOG.info("{}", bindings.getNullFriendlyBindings());
        List<TimesheetEx> ret = tupleSQLQuery.transform(GroupBy.groupBy(TM.timesheetId).list(qBean));

        return ret.isEmpty() ? null : ret.get(0);
    }


    @Transactional
    public  List<TimesheetEx> getTimesheets(String userLoginId, String context, String organization) throws SQLException {

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

        SQLQuery<Tuple> tupleSQLQuery = queryFactory.select(updatable.max().as("updatable"),TM, PV, PDO, PE, PEPR, UO, UOPR, ST, UM, WTC)
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
                .where(WT.parentTypeId.eq(context)
                        .and(PDO.organizationId.eq(organization))
                        .and((SGP.permissionId.like("%MGR_ADMIN")
                                .or(SGP.permissionId.like("%ORG_ADMIN")
                                        .and(RELR.partyIdTo.isNotNull()
                                                .or(RELD.partyIdTo.isNotNull()))))
                                .or(SGP.permissionId.like("%ROLE_ADMIN")
                                        .and(UL.partyId.eq(TM.partyId)))))
                .groupBy(TM, PV, PDO, PE, PEPR, UO, UOPR, ST, UM, WTC);


        SQLBindings bindings = tupleSQLQuery.getSQL();
        LOG.info("{}", bindings.getSQL());
        LOG.info("{}", bindings.getNullFriendlyBindings());
        QBean<TimesheetEx> qBean = bean(TimesheetEx.class, merge(TM.all(),
                bean(PartyHistoryView.class, PV.all()).as("partyHistoryView"),
                bean(Party.class, PE.all()).as("party"),
                bean(PartyParentRole.class, PEPR.all()).as("partyParentRole"),
                bean(Party.class, UO.all()).as("partyStructure"),
                bean(Uom.class, UM.all()).as("uom"),
                bean(PartyParentRole.class, UOPR.all()).as("partyParentRoleStructure"),
                bean(WorkEffortTypePeriod.class, PDO.all()).as("workEffortTypePeriod"),
                bean(StatusItem.class, ST.all()).as("statusItem"),
                bean(WorkEffortTypeContent.class, WTC.all()).as("workEffortTypeContent")));
        List<TimesheetEx> ret = tupleSQLQuery.transform(GroupBy.groupBy(updatable.max().as("updatable"),TM, PV, PDO, PE, PEPR, UO, UOPR, ST, UM, WTC).list(qBean));

        int result = 0;
        int cont = 0;
        ResultSet rs = tupleSQLQuery.getResults();
        while (rs.next()) {
            result = (rs.getInt(1));
            ret.get(cont).setUpdatable(result);
            cont = cont + 1;
        }
        LOG.info("size = {}", ret.size());
        return ret;
    }

    @Transactional
    public  List<WorkEffortTypeContentExt> getParamsTimesheets(String userLoginId, String context, String organization) {

        if (TransactionSynchronizationManager.isActualTransactionActive()) {
            TransactionStatus status = TransactionAspectSupport.currentTransactionStatus();
            status.getClass();
        }

        QTimesheet TM = QTimesheet.timesheet;
        QParty PE = new QParty("pe");;
        QParty UO = new QParty("uo");;
        QUom UM = QUom.uom;
        QWorkEffortTypePeriod PDO = QWorkEffortTypePeriod.workEffortTypePeriod;
        QStatusItem ST = QStatusItem.statusItem;
        QPartyRelationship UOPE = QPartyRelationship.partyRelationship;
        QPartyRelationship PR = QPartyRelationship.partyRelationship;
        QWorkEffortType WT = QWorkEffortType.workEffortType;
        QUserLoginSecurityGroup ULSG = QUserLoginSecurityGroup.userLoginSecurityGroup;
        QSecurityGroupPermission SP = QSecurityGroupPermission.securityGroupPermission;
        QWorkEffortTypeContent WTC = QWorkEffortTypeContent.workEffortTypeContent;
        QUserLoginPersistent UL = QUserLoginPersistent.userLogin;
        QPartyParentRole PEPR = new QPartyParentRole("pepr");
        QPartyParentRole UOPR = new QPartyParentRole("uopr");
        SQLQuery<Tuple> tupleSQLQuery = queryFactory.select(WTC,PDO)
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
                .innerJoin(WT).on(WT.workEffortTypeId.eq(PDO.workEffortTypeId))
                .innerJoin(WTC).on(WTC.workEffortTypeId.eq(WT.workEffortTypeId).and(WTC.contentId.eq("WEFLD_MAIN")))
                .where(WT.parentTypeId.eq(context)
                        .and(PDO.organizationId.eq(organization))
                        .and((queryFactory.selectOne().from(ULSG)
                                .innerJoin(SP).on(SP.groupId.eq(ULSG.groupId)
                                        .and(SP.permissionId.eq("ORGPERFMGR_ADMIN")))
                                .where(ULSG.userLoginId.eq(userLoginId))).exists()
                                .or((queryFactory.selectOne().from(ULSG)
                                        .innerJoin(SP).on(SP.groupId.eq(ULSG.groupId)
                                                .and(SP.permissionId.eq("ORGPERFORG_ADMIN")))
                                        .innerJoin(UL).on(UL.userLoginId.eq(ULSG.userLoginId))
                                        .innerJoin(PR).on(UOPE.partyIdTo.eq(UL.partyId)
                                                .and(PR.partyIdFrom.eq(UOPE.partyIdFrom).and(PR.partyRelationshipTypeId.in("ORG_RESPONSIBLE","ORG_DELEGATE")))
                                                .and(PR.fromDate.before(TM.thruDate).and(PR.thruDate.isNull().or(PR.thruDate.after(TM.thruDate)))))
                                        .where(ULSG.userLoginId.eq(userLoginId))).exists())));

        SQLBindings bindings = tupleSQLQuery.getSQL();
        LOG.info("{}", bindings.getSQL());
        LOG.info("{}", bindings.getNullFriendlyBindings());

        QBean<WorkEffortTypeContentExt> qBean = bean(WorkEffortTypeContentExt.class, merge(WTC.all(), bean(WorkEffortTypePeriod.class, PDO.all()).as("workEffortTypePeriod")));
        List<WorkEffortTypeContentExt> ret = tupleSQLQuery.transform(GroupBy.groupBy(WTC.workEffortTypeId).list(qBean));
        LOG.info("size = {}", ret.size());
        return ret;
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
    public boolean update(String id, Timesheet record) {
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
