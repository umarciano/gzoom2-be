package it.mapsgroup.gzoom.querydsl.dao;

import static com.querydsl.core.types.Projections.bean;
import static com.querydsl.core.types.dsl.Expressions.*;
import static it.mapsgroup.gzoom.querydsl.QBeanUtils.merge;
import static org.slf4j.LoggerFactory.getLogger;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.QTuple;
import com.querydsl.core.types.dsl.StringPath;
import it.mapsgroup.gzoom.querydsl.dto.*;
import it.mapsgroup.gzoom.querydsl.service.PermissionService;
import it.mapsgroup.gzoom.querydsl.util.ContextPermissionPrefixEnum;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import com.querydsl.core.group.GroupBy;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.QBean;
import com.querydsl.sql.SQLBindings;
import com.querydsl.sql.SQLQuery;
import com.querydsl.sql.SQLQueryFactory;

@Service
public class WorkEffortAnalysisDao {

	private static final Logger LOG = getLogger(WorkEffortAnalysisDao.class);

    private final SQLQueryFactory queryFactory;

   private final PermissionService permissionService;


    @Autowired
    public WorkEffortAnalysisDao(SQLQueryFactory queryFactory, PermissionService permissionService) {
        this.queryFactory = queryFactory;
        this.permissionService = permissionService;
    }

    /**
     * This function gets the work effort analyses with a context.
     *
     * @param context Context.
     * @param userLoginId User login id.
     * @return List of work effort analysis.
     */
    @Transactional
    public List<WorkEffortAnalysis> getWorkEffortAnalysesWithContext(String context, String userLoginId) {
        if (TransactionSynchronizationManager.isActualTransactionActive()) {
            TransactionStatus status = TransactionAspectSupport.currentTransactionStatus();
            status.getClass();
        }

        QWorkEffortAnalysis qWA = QWorkEffortAnalysis.workEffortAnalysis;
        QWorkEffortType qWAType = QWorkEffortType.workEffortType;

        QUserLoginSecurityGroup qULSG = QUserLoginSecurityGroup.userLoginSecurityGroup;
        QSecurityGroupPermission qSGP = QSecurityGroupPermission.securityGroupPermission;

        String permission = ContextPermissionPrefixEnum.getPermissionPrefix(context);

        SQLQuery<WorkEffortAnalysis> tupleSQLQuery = queryFactory.select(qWA).from(qWA)
                .innerJoin(qWAType).on(qWA.workEffortTypeId.eq(qWAType.workEffortTypeId))
                .where ((qWAType.parentTypeId.eq(context)) //"CTX_BS"
                        .and ((queryFactory.selectOne().from(qULSG)
                        .innerJoin(qSGP).on((qULSG.groupId.eq(qSGP.groupId)).and(qSGP.permissionId.eq(permission + "MGR_ADMIN")))
                        .where(qULSG.userLoginId.eq(userLoginId)
                        .and(qWA.availabilityId.eq("AVAIL_INTERNAL"))
                        )).exists().or(qWA.availabilityId.eq("AVAIL_PUBLIC"))))
                .orderBy(qWA.referenceDate.desc(), qWA.workEffortAnalysisId.asc());

        SQLBindings bindings = tupleSQLQuery.getSQL();
        LOG.info("{}", bindings.getSQL());
        LOG.info("{}", bindings.getNullFriendlyBindings());
        QBean<WorkEffortAnalysis> wa = bean(WorkEffortAnalysis.class, qWA.all());
        List<WorkEffortAnalysis> ret = tupleSQLQuery.transform(GroupBy.groupBy(qWA.workEffortAnalysisId).list(wa));

        LOG.info("size = {}", ret.size());
        return ret;
    }

    @Transactional
    public WorkEffortAnalysis getWorkEffortAnalysis(String workEffortAnalysisId) {
        if (TransactionSynchronizationManager.isActualTransactionActive()) {
            TransactionStatus status = TransactionAspectSupport.currentTransactionStatus();
            status.getClass();
        }
        QWorkEffortAnalysis qWA = QWorkEffortAnalysis.workEffortAnalysis;
        SQLQuery<WorkEffortAnalysis> tupleSQLQuery = queryFactory.select(qWA).from(qWA).where(qWA.workEffortAnalysisId.eq(workEffortAnalysisId));
        SQLBindings bindings = tupleSQLQuery.getSQL();
        LOG.info("{}", bindings.getSQL());
        LOG.info("{}", bindings.getNullFriendlyBindings());
        QBean<WorkEffortAnalysis> wa = Projections.bean(WorkEffortAnalysis.class, qWA.all());
        List<WorkEffortAnalysis> ret = tupleSQLQuery.transform(GroupBy.groupBy(qWA.workEffortAnalysisId).list(wa));
        return ret.isEmpty() ? null : ret.get(0);
    }

    /**
     * This function gets the information for the header by having the workEffortId.
     *
     * @param analysisId Analysis id.
     * @param workEffortId Work effort id
     * @return List of WorkEffortAnalysisTypeTypeExt.
     */
    @Transactional
    public List<WorkEffortAnalysisTypeTypeExt> getWorkEffortAnalysisTargetHeader(String analysisId, String workEffortId) {

        if (TransactionSynchronizationManager.isActualTransactionActive()) {
            TransactionStatus status = TransactionAspectSupport.currentTransactionStatus();
            status.getClass();
        }

        QWorkEffortAnalysis qWA = QWorkEffortAnalysis.workEffortAnalysis;
        QWorkEffortTypeType qWTT = QWorkEffortTypeType.workEffortTypeType;
        QWorkEffort qWE = QWorkEffort.workEffort;

        SQLQuery<Tuple> tupleSQLQuery = queryFactory.select(qWA, qWE, qWTT).from(qWA)
                .innerJoin(qWTT).on(qWTT.workEffortTypeIdRoot.eq(qWA.workEffortTypeId))
                .innerJoin(qWE).on(qWE.workEffortTypeId.eq(qWTT.workEffortTypeIdFrom))
                .where(qWA.workEffortAnalysisId.eq(analysisId)
                        .and(qWE.workEffortId.eq(workEffortId)));

        SQLBindings bindings = tupleSQLQuery.getSQL();
        LOG.info("{}", bindings.getSQL());
        LOG.info("{}", bindings.getNullFriendlyBindings());

        QBean<WorkEffortAnalysisTypeTypeExt> qBean = bean(WorkEffortAnalysisTypeTypeExt.class,
                merge(qWTT.all(),
                        bean(WorkEffortAnalysis.class, qWA.all()).as("workEffortAnalysis"),
                        bean(WorkEffort.class, qWE.all()).as("workEffort")
                ));

        List<WorkEffortAnalysisTypeTypeExt> ret = tupleSQLQuery.transform(GroupBy.groupBy(qWA.workEffortAnalysisId).list(qBean));

        LOG.info("size = {}", ret.size());

        return ret;

    }


    /**
     * This function get the summary objectives of the analysis.
     *
     * @param context Context.
     * @param analysisId Analysis id.
     * @param userLoginId User login id.
     * @return List of WorkEffortAnalysisTypeTypeExt.
     */
    @Transactional
    public List<WorkEffortAnalysisTypeTypeExt> getWorkEffortAnalysisTargetSummary(String context, String analysisId, String userLoginId) {

        if (TransactionSynchronizationManager.isActualTransactionActive()) {
            TransactionStatus status = TransactionAspectSupport.currentTransactionStatus();
            status.getClass();
        }

        QWorkEffortAnalysis qWA = new QWorkEffortAnalysis("wa");
        QWorkEffortTypeType qWTT = new QWorkEffortTypeType("wtt");
        QWorkEffort qWE = new QWorkEffort("we");
        QWorkEffortType qWT = new QWorkEffortType("wt");
        QCustomTimePeriod qTP = new QCustomTimePeriod("tp");
        QUserLoginSecurityGroup qULSG = new QUserLoginSecurityGroup("ulsg");
        QSecurityGroupPermission qSP = new QSecurityGroupPermission("sp");
        QUserLoginPersistent qUL = new QUserLoginPersistent("ul");
        QPartyRelationship qPRO = new QPartyRelationship("pro");
        QWorkEffort qRA = new QWorkEffort("ra");
        QWorkEffortType qRAT = new QWorkEffortType("rat");

        String permission = ContextPermissionPrefixEnum.getPermissionPrefix(context);


        SQLQuery<Tuple> tupleSQLQuery = queryFactory.select(qWA, qWE, qWTT).from(qWA)
                .innerJoin(qWTT).on(qWTT.workEffortTypeIdRoot.eq(qWA.workEffortTypeId).and(qWTT.sequenceNum.ne(BigInteger.valueOf(1)).not()))
                .innerJoin(qWE).on(qWE.workEffortTypeId.eq(qWTT.workEffortTypeIdFrom).and(qWE.workEffortRevisionId.isNull()))
                .innerJoin(qWT).on(qWT.workEffortTypeId.eq(qWE.workEffortTypeId))
                .innerJoin(qRA).on(qRA.workEffortId.eq(qWE.workEffortParentId))
                .innerJoin(qRAT).on(qRAT.workEffortTypeId.eq(qRA.workEffortTypeId))
                .innerJoin(qTP).on(qTP.periodTypeId.eq(qRAT.periodTypeId)
                                    .and((qTP.fromDate.after(qWA.referenceDate)).not())
                                    .and((qTP.thruDate.before(qWA.referenceDate)).not()))
                .innerJoin(qUL).on(qUL.partyId.eq(qUL.partyId))
                .innerJoin(qULSG).on(qUL.userLoginId.eq(qULSG.userLoginId))
                .innerJoin(qSP).on(qSP.groupId.eq(qULSG.groupId))
                .leftJoin(qPRO).on(qPRO.partyIdFrom.eq(qWE.orgUnitId)
                                .and(qPRO.partyRelationshipTypeId.in("ORG_RESPONSIBLE","ORG_DELEGATE"))
                                .and(qPRO.thruDate.isNull().or(qPRO.thruDate.before(qWA.referenceDate).not()))
                                .and(qSP.permissionId.eq(permission + "ORG_ADMIN"))
                                .and(qPRO.partyIdTo.eq(qUL.partyId)))
                .where(qWA.workEffortAnalysisId.eq(analysisId)
                        .and(qWA.excludeValidity.eq("Y")
                                .or(qWA.excludeValidity.eq("N")
                                        .and(qWE.estimatedStartDate.after(qWA.referenceDate).not()
                                                .and(qWE.estimatedCompletionDate.before(qTP.fromDate).not())))
                                .or(qWA.excludeValidity.eq("A")
                                        .and(qWE.estimatedStartDate.after(qTP.thruDate).not().and(qWE.estimatedCompletionDate.before(qTP.fromDate).not()))))
                        .and(qULSG.userLoginId.eq(userLoginId))
                        .and(qSP.permissionId.eq(permission + "MGR_ADMIN")
                                .or(qSP.permissionId.eq(permission + "ORG_ADMIN")
                                        .and(qPRO.partyIdTo.isNotNull()).and(qPRO.partyIdTo.eq(qUL.partyId)))));



        SQLBindings bindings = tupleSQLQuery.getSQL();
        LOG.info("{}", bindings.getSQL());
        LOG.info("{}", bindings.getNullFriendlyBindings());

        QBean<WorkEffortAnalysisTypeTypeExt> qBean = bean(WorkEffortAnalysisTypeTypeExt.class,
                merge(qWTT.all(),
                        bean(WorkEffortAnalysis.class, qWA.all()).as("workEffortAnalysis"),
                        bean(WorkEffort.class, qWE.all()).as("workEffort")
                ));

        List<WorkEffortAnalysisTypeTypeExt> ret = tupleSQLQuery.transform(GroupBy.groupBy(qWE.workEffortId).list(qBean));

        LOG.info("size = {}", ret.size());

        return ret;

    }


}
