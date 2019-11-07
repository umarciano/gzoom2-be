package it.mapsgroup.gzoom.querydsl.dao;

import static org.slf4j.LoggerFactory.getLogger;

import java.util.List;

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

import it.mapsgroup.gzoom.querydsl.dto.QWorkEffortAnalysis;
import it.mapsgroup.gzoom.querydsl.dto.WorkEffortAnalysis;

@Service
public class WorkEffortAnalysisDao {
	
	private static final Logger LOG = getLogger(WorkEffortAnalysisDao.class);
    
    private final SQLQueryFactory queryFactory;
    
    @Autowired
    public WorkEffortAnalysisDao(SQLQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }
    
    @Transactional
    public WorkEffortAnalysis getWorkEffortAnalysis(String workEffortAnalysisId) {
        if (TransactionSynchronizationManager.isActualTransactionActive()) {
            TransactionStatus status = TransactionAspectSupport.currentTransactionStatus();
            status.getClass();
        }
        QWorkEffortAnalysis qWa = QWorkEffortAnalysis.workEffortAnalysis;
        SQLQuery<WorkEffortAnalysis> tupleSQLQuery = queryFactory.select(qWa).from(qWa).where(qWa.workEffortAnalysisId.eq(workEffortAnalysisId));
        SQLBindings bindings = tupleSQLQuery.getSQL();
        LOG.info("{}", bindings.getSQL());
        LOG.info("{}", bindings.getNullFriendlyBindings());
        QBean<WorkEffortAnalysis> wa = Projections.bean(WorkEffortAnalysis.class, qWa.all());
        List<WorkEffortAnalysis> ret = tupleSQLQuery.transform(GroupBy.groupBy(qWa.workEffortAnalysisId).list(wa));
        return ret.isEmpty() ? null : ret.get(0);
    }
}
