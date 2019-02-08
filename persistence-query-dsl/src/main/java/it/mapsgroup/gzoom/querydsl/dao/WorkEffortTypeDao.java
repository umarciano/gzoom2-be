package it.mapsgroup.gzoom.querydsl.dao;

import static org.slf4j.LoggerFactory.getLogger;

import java.util.List;

import org.slf4j.Logger;
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

import it.mapsgroup.gzoom.querydsl.dto.QWorkEffortType;
import it.mapsgroup.gzoom.querydsl.dto.QWorkEffortTypeContent;
import it.mapsgroup.gzoom.querydsl.dto.WorkEffortType;

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
        LOG.info("{}", bindings.getBindings());
        QBean<WorkEffortType> wa = Projections.bean(WorkEffortType.class, qWorkEffortType.all());
        List<WorkEffortType> ret = tupleSQLQuery.transform(GroupBy.groupBy(qWorkEffortType.workEffortTypeId).list(wa));
        return ret.isEmpty() ? null : ret.get(0);
	}
	
	@Transactional
	public List<WorkEffortType> getWorkEffortTypeReminderActive(String contentId) {
		if (TransactionSynchronizationManager.isActualTransactionActive()) {
			TransactionStatus status = TransactionAspectSupport.currentTransactionStatus();
			status.getClass();
		}
		QWorkEffortType qWorkEffortType = QWorkEffortType.workEffortType;
		QWorkEffortTypeContent qWTC = QWorkEffortTypeContent.workEffortTypeContent;
		
		SQLQuery<WorkEffortType> tupleSQLQuery = queryFactory.select(qWorkEffortType)
				.from(qWorkEffortType)
				.innerJoin(qWTC).on(qWTC.workEffortTypeId.eq(qWorkEffortType.workEffortTypeId))
				.where(qWTC.contentId.eq(contentId)
						.and(qWorkEffortType.reminderActive.eq(true)));
		
		SQLBindings bindings = tupleSQLQuery.getSQL();
        LOG.info("{}", bindings.getSQL());
        LOG.info("{}", bindings.getBindings());
        QBean<WorkEffortType> wa = Projections.bean(WorkEffortType.class, qWorkEffortType.all());
        List<WorkEffortType> ret = tupleSQLQuery.transform(GroupBy.groupBy(qWorkEffortType.workEffortTypeId).list(wa));
        LOG.info("size = {}", ret.size());
        return ret;
	}
	

}
