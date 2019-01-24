package it.mapsgroup.gzoom.querydsl.dao;

import static org.slf4j.LoggerFactory.getLogger;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import com.querydsl.core.group.GroupBy;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.QBean;
import com.querydsl.sql.SQLBindings;
import com.querydsl.sql.SQLQuery;
import com.querydsl.sql.SQLQueryFactory;

import it.mapsgroup.gzoom.querydsl.dto.QParty;
import it.mapsgroup.gzoom.querydsl.dto.QPartyParentRole;
import it.mapsgroup.gzoom.querydsl.dto.QPartyRelationship;
import it.mapsgroup.gzoom.querydsl.dto.QStatusItem;
import it.mapsgroup.gzoom.querydsl.dto.QUserLoginPersistent;
import it.mapsgroup.gzoom.querydsl.dto.QUserLoginValidPartyRole;
import it.mapsgroup.gzoom.querydsl.dto.QWorkEffort;
import it.mapsgroup.gzoom.querydsl.dto.QWorkEffortPartyAssignment;
import it.mapsgroup.gzoom.querydsl.dto.QWorkEffortType;
import it.mapsgroup.gzoom.querydsl.dto.QWorkEffortTypeStatus;
import it.mapsgroup.gzoom.querydsl.dto.WorkEffort;
import it.mapsgroup.gzoom.querydsl.service.PermissionService;
import it.mapsgroup.gzoom.querydsl.util.ContextPermissionPrefixEnum;

@Service
public class WorkEffortDao extends AbstractDao {
	private static final Logger LOG = getLogger(WorkEffortDao.class);

	private final SQLQueryFactory queryFactory;
	private PermissionService permissionService;

	public WorkEffortDao(SQLQueryFactory queryFactory, PermissionService permissionService) {
		this.queryFactory = queryFactory;
		this.permissionService = permissionService;
	}

	@Transactional
	public List<WorkEffort> getWorkEfforts() {
		if (TransactionSynchronizationManager.isActualTransactionActive()) {
			TransactionStatus status = TransactionAspectSupport.currentTransactionStatus();
			status.getClass();
		}

		QWorkEffort qWorkEffort = QWorkEffort.workEffort;

		SQLQuery<WorkEffort> tupleSQLQuery = queryFactory.select(qWorkEffort).from(qWorkEffort)
				.orderBy(qWorkEffort.workEffortName.asc());

		SQLBindings bindings = tupleSQLQuery.getSQL();
		LOG.info("{}", bindings.getSQL());
		LOG.info("{}", bindings.getBindings());
		QBean<WorkEffort> workEfforts = Projections.bean(WorkEffort.class, qWorkEffort.all());
		List<WorkEffort> ret = tupleSQLQuery.transform(GroupBy.groupBy(qWorkEffort.workEffortId).list(workEfforts));
		LOG.info("size = {}", ret.size());
		return ret;
	}

	/**
	 * TODO La query con i permessi è presa dal file queryWorkEffortRootInqy.sql.ftl
	 * @param userLoginId
	 * @param parentTypeId
	 * @param workEffortTypeId
	 * @param useFilter
	 * @return
	 */
	
	@Transactional
	public List<WorkEffort> getWorkEfforts(String userLoginId, String parentTypeId, String workEffortTypeId, boolean useFilter) {
		if (TransactionSynchronizationManager.isActualTransactionActive()) {
			TransactionStatus status = TransactionAspectSupport.currentTransactionStatus();
			status.getClass();
		}
		String permission = ContextPermissionPrefixEnum.getPermissionPrefix(parentTypeId);

		QWorkEffort qWorkEffort = QWorkEffort.workEffort;
		QWorkEffortType qWorkEffortType = QWorkEffortType.workEffortType;
		
		SQLQuery<WorkEffort> tupleSQLQuery = queryFactory.select(qWorkEffort)
				.from(qWorkEffort)
				.innerJoin(qWorkEffortType).on(qWorkEffortType.workEffortTypeId.eq(qWorkEffort.workEffortTypeId))
				.where(qWorkEffort.workEffortTypeId.eq(workEffortTypeId)
						.and(qWorkEffortType.isRoot.eq(true))
						.and(qWorkEffortType.parentTypeId.like("CTX%"))
						.and(qWorkEffort.workEffortRevisionId.isNull()))  //TODO prendo solo quelli non storicizzati!!!!
				.groupBy(qWorkEffort.workEffortId)
				.orderBy(qWorkEffortType.seqEsp.asc(), qWorkEffort.sourceReferenceId.asc(), qWorkEffort.workEffortName.asc(), qWorkEffortType.description.asc());

		// se ho uno dei permessi uso la lista filtrata di elementi
		boolean isOrgMgr = permissionService.isOrgMgr(userLoginId, permission);
		boolean isSup = permissionService.isSup(userLoginId, permission);
		boolean isTop = permissionService.isTop(userLoginId, permission);
		boolean isRole = permissionService.isRole(userLoginId, permission);

		if ((isOrgMgr || isSup || isTop) && useFilter) {

			QUserLoginValidPartyRole qULVR = QUserLoginValidPartyRole.userLoginValidPartyRole;			
			QUserLoginPersistent qUserLogin = QUserLoginPersistent.userLogin;			
			QStatusItem qStatusItem = QStatusItem.statusItem;
			QWorkEffortTypeStatus qWorkEffortTypeStatus = QWorkEffortTypeStatus.workEffortTypeStatus;
			QWorkEffortPartyAssignment qWorkEffortPartyAssignment = QWorkEffortPartyAssignment.workEffortPartyAssignment;
			QParty qParty = QParty.party;
			QPartyParentRole qPartyParentRole = QPartyParentRole.partyParentRole;
			
			QPartyRelationship qPartyRelationshipE = new QPartyRelationship("E");
			QPartyRelationship qPartyRelationshipY = new QPartyRelationship("Y");
			QPartyRelationship qPartyRelationshipZ = new QPartyRelationship("Z");
			QPartyRelationship qPartyRelationshipZ2 = new QPartyRelationship("Z2");
			QPartyRelationship qPartyRelationshipY2 = new QPartyRelationship("Y2");
						
			tupleSQLQuery.innerJoin(qULVR).on(qULVR.partyId.eq(qWorkEffort.organizationId)
							.and(qULVR.roleTypeId.eq("INTERNAL_ORGANIZATIO"))
							.and(qULVR.userLoginId.eq(userLoginId)))
					.innerJoin(qUserLogin).on(qUserLogin.userLoginId.eq(qULVR.userLoginId))
					.innerJoin(qStatusItem).on(qStatusItem.statusId.eq(qWorkEffort.currentStatusId))
					.leftJoin(qWorkEffortTypeStatus).on(qWorkEffortTypeStatus.workEffortTypeRootId.eq(qWorkEffort.workEffortTypeId)
							.and(qWorkEffort.currentStatusId.eq(qWorkEffortTypeStatus.currentStatusId)))
					
					.leftJoin(qPartyRelationshipE).on(
							qPartyRelationshipE.roleTypeIdFrom.eq(qWorkEffort.orgUnitRoleTypeId)
								.and(qPartyRelationshipE.partyIdFrom.eq(qWorkEffort.orgUnitId))
								.and(qPartyRelationshipE.partyRelationshipTypeId.in("ORG_RESPONSIBLE", "ORG_DELEGATE"))
								.and(qPartyRelationshipE.fromDate.loe(qWorkEffort.estimatedCompletionDate))
								.and(qPartyRelationshipE.thruDate.isNull().or(qPartyRelationshipE.thruDate.goe(qWorkEffort.estimatedCompletionDate)))							
								.and(qPartyRelationshipE.partyIdTo.eq(qUserLogin.partyId))) 
					
					.leftJoin(qPartyRelationshipZ).on(
							qPartyRelationshipZ.roleTypeIdTo.eq(qWorkEffort.orgUnitRoleTypeId)
								.and(qPartyRelationshipZ.partyIdTo.eq(qWorkEffort.orgUnitId))
								.and(qPartyRelationshipZ.partyRelationshipTypeId.eq("GROUP_ROLLUP"))
								.and(qPartyRelationshipZ.fromDate.loe(qWorkEffort.estimatedCompletionDate))
								.and(qPartyRelationshipZ.thruDate.isNull().or(qPartyRelationshipZ.thruDate.goe(qWorkEffort.estimatedCompletionDate))))
					
					.leftJoin(qPartyRelationshipY).on(
							qPartyRelationshipY.roleTypeIdFrom.eq(qPartyRelationshipZ.roleTypeIdFrom)
								.and(qPartyRelationshipY.partyIdFrom.eq(qPartyRelationshipZ.partyIdFrom))
								.and(qPartyRelationshipY.partyRelationshipTypeId.in("ORG_RESPONSIBLE", "ORG_DELEGATE"))
								.and(qPartyRelationshipY.fromDate.loe(qWorkEffort.estimatedCompletionDate))
								.and(qPartyRelationshipY.thruDate.isNull().or(qPartyRelationshipY.thruDate.goe(qWorkEffort.estimatedCompletionDate)))
								.and(qPartyRelationshipY.partyIdTo.eq(qUserLogin.partyId))) 
				
					.leftJoin(qPartyRelationshipZ2).on(
							qPartyRelationshipZ2.roleTypeIdTo.eq(qPartyRelationshipZ.roleTypeIdFrom)
								.and(qPartyRelationshipZ2.partyIdTo.eq(qPartyRelationshipZ.partyIdFrom))
								.and(qPartyRelationshipZ2.partyRelationshipTypeId.eq("GROUP_ROLLUP"))
								.and(qPartyRelationshipZ2.fromDate.loe(qWorkEffort.estimatedCompletionDate))
								.and(qPartyRelationshipZ2.thruDate.isNull().or(qPartyRelationshipZ2.thruDate.goe(qWorkEffort.estimatedCompletionDate))))
					
					
					.leftJoin(qPartyRelationshipY2).on(
							qPartyRelationshipY2.roleTypeIdFrom.eq(qPartyRelationshipZ2.roleTypeIdFrom)
								.and(qPartyRelationshipY2.partyIdFrom.eq(qPartyRelationshipZ2.partyIdFrom))
								.and(qPartyRelationshipY2.partyRelationshipTypeId.in("ORG_RESPONSIBLE", "ORG_DELEGATE"))
								.and(qPartyRelationshipY2.fromDate.loe(qWorkEffort.estimatedCompletionDate))
								.and(qPartyRelationshipY2.thruDate.isNull().or(qPartyRelationshipY2.thruDate.goe(qWorkEffort.estimatedCompletionDate)))
								.and(qPartyRelationshipY2.partyIdTo.eq(qUserLogin.partyId)))
					
					.leftJoin(qWorkEffortPartyAssignment).on(
							qWorkEffortPartyAssignment.workEffortId.eq(qWorkEffort.workEffortId)
							.and(qWorkEffortPartyAssignment.thruDate.eq(qWorkEffort.estimatedCompletionDate))
							.and(qWorkEffortPartyAssignment.roleTypeId.like("WEM%"))
							.and(qWorkEffortPartyAssignment.partyId.eq(qUserLogin.partyId)))
					
					.innerJoin(qParty).on(qParty.partyId.eq(qWorkEffort.orgUnitId))
					.innerJoin(qPartyParentRole).on(qPartyParentRole.partyId.eq(qParty.partyId)
							.and(qPartyParentRole.roleTypeId.eq("ORGANIZATION_UNIT"))); 
					
			List<Predicate> predicates = new ArrayList<>();

			if (isOrgMgr) {
				predicates.add(qPartyRelationshipE.partyIdTo.isNotNull());
			}
			if (isSup) {
				predicates.add(qPartyRelationshipY.partyIdTo.isNotNull());
			}
			if (isRole) {
				predicates.add(qWorkEffortPartyAssignment.partyId.isNotNull());
			}
			if (isTop) {
				predicates.add(qPartyRelationshipY2.partyIdTo.isNotNull());
			}
			tupleSQLQuery.where(predicates.toArray(new Predicate[0]));
		}

		SQLBindings bindings = tupleSQLQuery.getSQL();
		LOG.info("{}", bindings.getSQL());
		LOG.info("{}", bindings.getBindings());
		QBean<WorkEffort> workEfforts = Projections.bean(WorkEffort.class, qWorkEffort.all());
		List<WorkEffort> ret = tupleSQLQuery.transform(GroupBy.groupBy(qWorkEffort.workEffortId).list(workEfforts));
		LOG.info("size = {}", ret.size());
		return ret;
	}

	@Transactional
	public List<WorkEffort> getWorkEffortParents(String workEffortParentId) {
		if (TransactionSynchronizationManager.isActualTransactionActive()) {
			TransactionStatus status = TransactionAspectSupport.currentTransactionStatus();
			status.getClass();
		}

		QWorkEffort qWorkEffort = QWorkEffort.workEffort;
		SQLQuery<WorkEffort> tupleSQLQuery = queryFactory.select(qWorkEffort).from(qWorkEffort)
				.where(qWorkEffort.workEffortParentId.eq(workEffortParentId)).orderBy(qWorkEffort.workEffortName.asc());

		SQLBindings bindings = tupleSQLQuery.getSQL();
		LOG.info("{}", bindings.getSQL());
		LOG.info("{}", bindings.getBindings());
		QBean<WorkEffort> workEfforts = Projections.bean(WorkEffort.class, qWorkEffort.all());
		List<WorkEffort> ret = tupleSQLQuery.transform(GroupBy.groupBy(qWorkEffort.workEffortId).list(workEfforts));
		LOG.info("size = {}", ret.size());
		return ret;
	}

}
