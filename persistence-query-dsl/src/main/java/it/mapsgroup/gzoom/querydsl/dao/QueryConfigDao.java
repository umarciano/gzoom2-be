package it.mapsgroup.gzoom.querydsl.dao;

import com.querydsl.core.group.GroupBy;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.QBean;
import com.querydsl.sql.SQLBindings;
import com.querydsl.sql.SQLQuery;
import com.querydsl.sql.SQLQueryFactory;
import it.mapsgroup.gzoom.persistence.common.SequenceGenerator;
import it.mapsgroup.gzoom.querydsl.dto.QQueryConfig;
import it.mapsgroup.gzoom.querydsl.dto.QueryConfig;
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
public class QueryConfigDao extends AbstractDao {

    private static final Logger LOG = getLogger(PartyDao.class);

    private final SequenceGenerator sequenceGenerator;
    private final SQLQueryFactory queryFactory;
    private final FilterPermissionDao filterPermissionDao;

    @Autowired
    public QueryConfigDao(SequenceGenerator sequenceGenerator, SQLQueryFactory queryFactory, FilterPermissionDao filterPermissionDao) {
        this.sequenceGenerator = sequenceGenerator;
        this.queryFactory = queryFactory;
        this.filterPermissionDao = filterPermissionDao;
    }

    @Transactional
    public List<QueryConfig> getAllQueryConfig() {
        if (TransactionSynchronizationManager.isActualTransactionActive()) {
            TransactionStatus status = TransactionAspectSupport.currentTransactionStatus();
            status.getClass();
        }

        QQueryConfig qQueryConfig = QQueryConfig.queryConfig;

        SQLQuery<QueryConfig> pSQLQuery = queryFactory.select(qQueryConfig)
                .from(qQueryConfig)
                //.where(qEnumeration.enumTypeId.eq(enumTypeId))
                .orderBy(qQueryConfig.queryId.asc());

        SQLBindings bindings = pSQLQuery.getSQL();
        LOG.info("{}", bindings.getSQL());
        LOG.info("{}", bindings.getNullFriendlyBindings());
        QBean<QueryConfig> queryconfigs = Projections.bean(QueryConfig.class, qQueryConfig.all());
        List<QueryConfig> ret = pSQLQuery.transform(GroupBy.groupBy(qQueryConfig.queryId).list(queryconfigs));
        LOG.info("size = {}", ret.size());
        return ret;
    }


    @Transactional
    public QueryConfig getQueryConfig(String id) {
        if (TransactionSynchronizationManager.isActualTransactionActive()) {
            TransactionStatus status = TransactionAspectSupport.currentTransactionStatus();
            status.getClass();
        }

        QQueryConfig qQueryConfig = QQueryConfig.queryConfig;

        SQLQuery<QueryConfig> pSQLQuery = queryFactory.select(qQueryConfig)
                .from(qQueryConfig)
                .where(qQueryConfig.queryId.eq(id))
                .orderBy(qQueryConfig.queryId.asc());

        SQLBindings bindings = pSQLQuery.getSQL();
        LOG.info("{}", bindings.getSQL());
        LOG.info("{}", bindings.getNullFriendlyBindings());
        QBean<QueryConfig> queryconfigs = Projections.bean(QueryConfig.class, qQueryConfig.all());
        List<QueryConfig> ret = pSQLQuery.transform(GroupBy.groupBy(qQueryConfig.queryId).list(queryconfigs));

        return ret.size()>0?ret.get(0):null;
    }
}
