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

import it.mapsgroup.gzoom.querydsl.dto.QWorkEffort;
import it.mapsgroup.gzoom.querydsl.dto.WorkEffort;

@Service
public class WorkEffortDao {
	private static final Logger LOG = getLogger(WorkEffortDao.class);

    private final SQLQueryFactory queryFactory;
    
    public WorkEffortDao(SQLQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }
    
    @Transactional
    public List<WorkEffort> getWorkEfforts() {
        if (TransactionSynchronizationManager.isActualTransactionActive()) {
            TransactionStatus status = TransactionAspectSupport.currentTransactionStatus();
            status.getClass();
        }

        QWorkEffort qWorkEffort = QWorkEffort.workEffort;

        SQLQuery<WorkEffort> tupleSQLQuery = queryFactory.select(qWorkEffort)
        												.from(qWorkEffort)        												
        												.orderBy(qWorkEffort.workEffortName.asc());

        SQLBindings bindings = tupleSQLQuery.getSQL();
        LOG.info("{}", bindings.getSQL());
        LOG.info("{}", bindings.getBindings());
        QBean<WorkEffort> workEfforts = Projections.bean(WorkEffort.class, qWorkEffort.all());
        List<WorkEffort> ret = tupleSQLQuery.transform(GroupBy.groupBy(qWorkEffort.workEffortId).list(workEfforts));
        LOG.info("size = {}", ret.size()); 
        return ret;
    }
    
    @Transactional
    public List<WorkEffort> getWorkEfforts(String workEffortTypeId) {
        if (TransactionSynchronizationManager.isActualTransactionActive()) {
            TransactionStatus status = TransactionAspectSupport.currentTransactionStatus();
            status.getClass();
        }

        QWorkEffort qWorkEffort = QWorkEffort.workEffort;

        SQLQuery<WorkEffort> tupleSQLQuery = queryFactory.select(qWorkEffort)
        												.from(qWorkEffort)
        												.where(qWorkEffort.workEffortTypeId.eq(workEffortTypeId))
        												.orderBy(qWorkEffort.workEffortName.asc());

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
        SQLQuery<WorkEffort> tupleSQLQuery = queryFactory.select(qWorkEffort)
        												.from(qWorkEffort)
        												.where(qWorkEffort.workEffortParentId.eq(workEffortParentId))
        												.orderBy(qWorkEffort.workEffortName.asc());

        SQLBindings bindings = tupleSQLQuery.getSQL();
        LOG.info("{}", bindings.getSQL());
        LOG.info("{}", bindings.getBindings());
        QBean<WorkEffort> workEfforts = Projections.bean(WorkEffort.class, qWorkEffort.all());
        List<WorkEffort> ret = tupleSQLQuery.transform(GroupBy.groupBy(qWorkEffort.workEffortId).list(workEfforts));
        LOG.info("size = {}", ret.size()); 
        return ret;
    }
    

}
