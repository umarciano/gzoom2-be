package it.mapsgroup.gzoom.querydsl.dao;

import static com.querydsl.core.types.Projections.bean;
import static it.mapsgroup.gzoom.querydsl.QBeanUtils.merge;
import static org.slf4j.LoggerFactory.getLogger;

import java.util.List;

import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import com.querydsl.core.Tuple;
import com.querydsl.core.group.GroupBy;
import com.querydsl.core.types.QBean;
import com.querydsl.sql.SQLBindings;
import com.querydsl.sql.SQLQuery;
import com.querydsl.sql.SQLQueryFactory;

import it.mapsgroup.gzoom.querydsl.dto.QStatusItem;
import it.mapsgroup.gzoom.querydsl.dto.QWorkEffort;
import it.mapsgroup.gzoom.querydsl.dto.QWorkEffortType;
import it.mapsgroup.gzoom.querydsl.dto.StatusItemExt;
import it.mapsgroup.gzoom.querydsl.dto.WorkEffort;
import it.mapsgroup.gzoom.querydsl.dto.WorkEffortType;

@Service
public class StatusItemDao {
	private static final Logger LOG = getLogger(StatusItemDao.class);

    private final SQLQueryFactory queryFactory;
    

    public StatusItemDao(SQLQueryFactory queryFactory) {
        this.queryFactory = queryFactory;	        
    }

    
    @Transactional
    public List<StatusItemExt> getStatusItems(String parentTypeId) {
        if (TransactionSynchronizationManager.isActualTransactionActive()) {
            TransactionStatus status = TransactionAspectSupport.currentTransactionStatus();
            status.getClass();
        }

        QStatusItem qStatusItem = QStatusItem.statusItem;
        QWorkEffort qWorkEffort = QWorkEffort.workEffort;
        QWorkEffortType qWorkEffortType = QWorkEffortType.workEffortType;

        QBean<StatusItemExt> tupleExQBean = bean(StatusItemExt.class, 
        					merge(qStatusItem.all(), bean(WorkEffort.class, qWorkEffort.all()).as("workEffort"),
        							bean(WorkEffortType.class, qWorkEffortType.all()).as("workEffortType"))); 
            
        SQLQuery<Tuple> tupleSQLQuery = queryFactory.select(qStatusItem, qWorkEffort, qWorkEffortType)
        					.from(qStatusItem)
        					.innerJoin(qWorkEffort).on(qWorkEffort.currentStatusId.eq(qStatusItem.statusId)) 
        					.innerJoin(qWorkEffortType).on(qWorkEffortType.workEffortTypeId.eq(qWorkEffort.workEffortTypeId)) 
        					.where(qWorkEffort.workEffortRevisionId.isNull()
        							.and(qWorkEffortType.isTemplate.eq(false))
        							.and(qWorkEffortType.isRoot.eq(true))
        							.and(qWorkEffortType.parentTypeId.eq(parentTypeId))) 
            				.orderBy(qStatusItem.sequenceId.asc());

        SQLBindings bindings = tupleSQLQuery.getSQL();
        LOG.info("{}", bindings.getSQL());
        LOG.info("{}", bindings.getBindings());
        List<StatusItemExt> ret = tupleSQLQuery.transform(GroupBy.groupBy(qStatusItem.description).list(tupleExQBean));
        LOG.info("size = {}", ret.size());
        return ret;
    }
}
