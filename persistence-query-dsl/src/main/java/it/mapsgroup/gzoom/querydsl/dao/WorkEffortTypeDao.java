package it.mapsgroup.gzoom.querydsl.dao;

import static com.querydsl.core.types.Projections.bean;
import static it.mapsgroup.gzoom.querydsl.QBeanUtils.merge;
import static org.slf4j.LoggerFactory.getLogger;

import java.util.List;

import com.querydsl.core.BooleanBuilder;
import it.mapsgroup.gzoom.querydsl.dto.*;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import com.querydsl.core.Tuple;
import com.querydsl.core.group.GroupBy;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.QBean;
import com.querydsl.sql.SQLBindings;
import com.querydsl.sql.SQLQuery;
import com.querydsl.sql.SQLQueryFactory;

@Service
public class WorkEffortTypeDao extends AbstractDao {
	private static final Logger LOG = getLogger(WorkEffortTypeDao.class);

	private final SQLQueryFactory queryFactory;

	public WorkEffortTypeDao(SQLQueryFactory queryFactory) {
		this.queryFactory = queryFactory;
	}

	
	@Transactional
	public WorkEffortType getWorkEffortType(String workEffortTypeId) {
		if (TransactionSynchronizationManager.isActualTransactionActive()) {
			TransactionStatus status = TransactionAspectSupport.currentTransactionStatus();
			status.getClass();
		}
		QWorkEffortType qWorkEffortType = QWorkEffortType.workEffortType;
		SQLQuery<WorkEffortType> tupleSQLQuery = queryFactory.select(qWorkEffortType).from(qWorkEffortType).where(qWorkEffortType.workEffortTypeId.eq(workEffortTypeId));
		SQLBindings bindings = tupleSQLQuery.getSQL();
        LOG.info("{}", bindings.getSQL());
        LOG.info("{}", bindings.getNullFriendlyBindings());
        QBean<WorkEffortType> wa = Projections.bean(WorkEffortType.class, qWorkEffortType.all());
        List<WorkEffortType> ret = tupleSQLQuery.transform(GroupBy.groupBy(qWorkEffortType.workEffortTypeId).list(wa));
        return ret.isEmpty() ? null : ret.get(0);
	}

	@Transactional
	public List<WorkEffortType> getWorkEffortTypes(String workEffortTypeId) {
		if (TransactionSynchronizationManager.isActualTransactionActive()) {
			TransactionStatus status = TransactionAspectSupport.currentTransactionStatus();
			status.getClass();
		}
		QWorkEffortType qWorkEffortType = QWorkEffortType.workEffortType;

		BooleanBuilder builder = new BooleanBuilder();
		for(String workEffortTypeIdItem : workEffortTypeId.split(",")) {
			builder.or(qWorkEffortType.workEffortTypeId.like(workEffortTypeIdItem));
		}

		SQLQuery<WorkEffortType> tupleSQLQuery = queryFactory.select(qWorkEffortType).from(qWorkEffortType).where(builder);
		SQLBindings bindings = tupleSQLQuery.getSQL();
		LOG.info("{}", bindings.getSQL());
		LOG.info("{}", bindings.getNullFriendlyBindings());
		QBean<WorkEffortType> wa = Projections.bean(WorkEffortType.class, qWorkEffortType.all());
		List<WorkEffortType> ret = tupleSQLQuery.transform(GroupBy.groupBy(qWorkEffortType.workEffortTypeId).list(wa));
		return ret;
	}

	@Transactional
	public List<WorkEffortType> getWorkEffortTypesParametric(String workEffortTypeId) {
		if (TransactionSynchronizationManager.isActualTransactionActive()) {
			TransactionStatus status = TransactionAspectSupport.currentTransactionStatus();
			status.getClass();
		}
		QWorkEffortType qWorkEffortType = QWorkEffortType.workEffortType;
		QWorkEffortTypeType qWorkEffortTypeType = QWorkEffortTypeType.workEffortTypeType;

		SQLQuery<WorkEffortType> tupleSQLQuery = queryFactory.select(qWorkEffortType).from(qWorkEffortType).where(
				qWorkEffortType.workEffortTypeId.in(queryFactory.select(qWorkEffortTypeType.workEffortTypeIdFrom).from(qWorkEffortTypeType).where(qWorkEffortTypeType.workEffortTypeIdRoot.eq(workEffortTypeId))));
		SQLBindings bindings = tupleSQLQuery.getSQL();
		LOG.info("{}", bindings.getSQL());
		LOG.info("{}", bindings.getNullFriendlyBindings());
		QBean<WorkEffortType> wa = Projections.bean(WorkEffortType.class, qWorkEffortType.all());
		List<WorkEffortType> ret = tupleSQLQuery.transform(GroupBy.groupBy(qWorkEffortType.workEffortTypeId).list(wa));
		return ret;
	}

	@Transactional
	public List<WorkEffortTypeContentExt> getWorkEffortTypeReminderActive(String contentId) {
		if (TransactionSynchronizationManager.isActualTransactionActive()) {
			TransactionStatus status = TransactionAspectSupport.currentTransactionStatus();
			status.getClass();
		}
		QWorkEffortType qWorkEffortType = QWorkEffortType.workEffortType;
		QWorkEffortTypeContent qWTC = QWorkEffortTypeContent.workEffortTypeContent;
		QContent qContent = QContent.content;
		
		SQLQuery<Tuple> tupleSQLQuery = queryFactory.select(qWorkEffortType, qWTC, qContent)
				.from(qWorkEffortType)
				.innerJoin(qWTC).on(qWTC.workEffortTypeId.eq(qWorkEffortType.workEffortTypeId))
				.innerJoin(qContent).on(qWTC.contentId.eq(qContent.contentId))
				.where(qWTC.contentId.eq(contentId)
						.and(qWorkEffortType.reminderActive.eq(true)));
		
		SQLBindings bindings = tupleSQLQuery.getSQL();
        LOG.info("{}", bindings.getSQL());
        LOG.info("{}", bindings.getNullFriendlyBindings());
        
        QBean<WorkEffortTypeContentExt> qBean = bean(WorkEffortTypeContentExt.class,
                merge(qWTC.all(),
                        bean(WorkEffortType.class, qWorkEffortType.all()).as("workEffortType"),
                        bean(Content.class, qContent.all()).as("content")
                        ));
 
        List<WorkEffortTypeContentExt> ret = tupleSQLQuery.transform(GroupBy.groupBy(qWorkEffortType.workEffortTypeId).list(qBean));
        LOG.info("size = {}", ret.size());
        return ret;
	}
	

}
