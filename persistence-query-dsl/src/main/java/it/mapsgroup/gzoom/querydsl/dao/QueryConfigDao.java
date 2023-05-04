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
import it.mapsgroup.gzoom.querydsl.service.PermissionService;

import it.mapsgroup.gzoom.querydsl.util.ContextPermissionPrefixEnum;
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
    private final PermissionService permissionService;


    @Autowired
    public QueryConfigDao(SequenceGenerator sequenceGenerator, SQLQueryFactory queryFactory,
                          FilterPermissionDao filterPermissionDao, PermissionService permissionService) {
        this.sequenceGenerator = sequenceGenerator;
        this.queryFactory = queryFactory;
        this.filterPermissionDao = filterPermissionDao;
        this.permissionService = permissionService;
    }

    @Transactional
    public List<QueryConfig> getAllQueryConfig(String parentTypeId,String queryType, String userLoginId) {
        if (TransactionSynchronizationManager.isActualTransactionActive()) {
            TransactionStatus status = TransactionAspectSupport.currentTransactionStatus();
            status.getClass();
        }

        QQueryConfig qQueryConfig = QQueryConfig.queryConfig;

        SQLQuery<QueryConfig> pSQLQuery = queryFactory.select(qQueryConfig)
                .from(qQueryConfig)
                //.where(qEnumeration.enumTypeId.eq(enumTypeId))
                .orderBy(qQueryConfig.queryId.asc());


        if(parentTypeId!=null && !parentTypeId.equals("")) {

            String permission = ContextPermissionPrefixEnum.getPermissionPrefix(parentTypeId);

            // se ho uno dei permessi uso la lista filtrata di elementi
            boolean isOrgMgr = permissionService.isOrgMgr(userLoginId, permission);
            boolean isSup = permissionService.isSup(userLoginId, permission);
            boolean isTop = permissionService.isTop(userLoginId, permission);
            boolean isRole = permissionService.isRole(userLoginId, permission);

            pSQLQuery.where(qQueryConfig.queryCtx.eq(parentTypeId));

            //Se non sono un admin/responsabile devo vedere solo le query pubbliche
            if (isOrgMgr || isSup || isTop) {
                pSQLQuery.where(qQueryConfig.queryPublic.eq(true));
            }

            if(queryType!=null) {
                pSQLQuery.where(qQueryConfig.queryType.eq(queryType));
            }
        }

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
