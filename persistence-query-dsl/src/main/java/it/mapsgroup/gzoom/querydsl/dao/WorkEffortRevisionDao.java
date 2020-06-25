package it.mapsgroup.gzoom.querydsl.dao;

import com.querydsl.core.group.GroupBy;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.QBean;
import com.querydsl.sql.SQLBindings;
import com.querydsl.sql.SQLQuery;
import com.querydsl.sql.SQLQueryFactory;
import it.mapsgroup.gzoom.querydsl.dto.QWorkEffortAnalysis;
import it.mapsgroup.gzoom.querydsl.dto.QWorkEffortRevision;
import it.mapsgroup.gzoom.querydsl.dto.WorkEffortAnalysis;
import it.mapsgroup.gzoom.querydsl.dto.WorkEffortRevision;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

@Service
public class WorkEffortRevisionDao {

	private static final Logger LOG = getLogger(WorkEffortRevisionDao.class);

    private final SQLQueryFactory queryFactory;

    @Autowired
    public WorkEffortRevisionDao(SQLQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }
    
    @Transactional
    public List<WorkEffortRevision> getWorkEffortRevisions(String workEffortTypeId) {
        if (TransactionSynchronizationManager.isActualTransactionActive()) {
            TransactionStatus status = TransactionAspectSupport.currentTransactionStatus();
            status.getClass();
        }

        QWorkEffortRevision qWorkEffortRevision = QWorkEffortRevision.workEffortRevision;
        SQLQuery<WorkEffortRevision> tupleSQLQuery = queryFactory.select(qWorkEffortRevision).from(qWorkEffortRevision)
                .where(qWorkEffortRevision.workEffortTypeIdCtx.eq(workEffortTypeId).and(qWorkEffortRevision.isAutomatic.eq(false)));

        SQLBindings bindings = tupleSQLQuery.getSQL();
        LOG.info("{}", bindings.getSQL());
        LOG.info("{}", bindings.getNullFriendlyBindings());
        QBean<WorkEffortRevision> wa = Projections.bean(WorkEffortRevision.class, qWorkEffortRevision.all());
        List<WorkEffortRevision> ret = tupleSQLQuery.transform(GroupBy.groupBy(qWorkEffortRevision.workEffortRevisionId).list(wa));
        return ret;
    }
}
