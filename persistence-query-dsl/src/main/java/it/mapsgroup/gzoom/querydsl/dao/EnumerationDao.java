package it.mapsgroup.gzoom.querydsl.dao;

import com.querydsl.core.group.GroupBy;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.QBean;
import com.querydsl.sql.SQLBindings;
import com.querydsl.sql.SQLQuery;
import com.querydsl.sql.SQLQueryFactory;
import it.mapsgroup.gzoom.persistence.common.SequenceGenerator;
import it.mapsgroup.gzoom.querydsl.dto.Enumeration;
import it.mapsgroup.gzoom.querydsl.dto.QEnumeration;
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
public class EnumerationDao extends AbstractDao {

    private static final Logger LOG = getLogger(PartyDao.class);

    private final SequenceGenerator sequenceGenerator;
    private final SQLQueryFactory queryFactory;
    private final FilterPermissionDao filterPermissionDao;

    @Autowired
    public EnumerationDao(SequenceGenerator sequenceGenerator, SQLQueryFactory queryFactory, FilterPermissionDao filterPermissionDao) {
        this.sequenceGenerator = sequenceGenerator;
        this.queryFactory = queryFactory;
        this.filterPermissionDao = filterPermissionDao;
    }

    @Transactional
    public List<Enumeration> getEnumerations(String enumTypeId) {
        if (TransactionSynchronizationManager.isActualTransactionActive()) {
            TransactionStatus status = TransactionAspectSupport.currentTransactionStatus();
            status.getClass();
        }

        QEnumeration qEnumeration = QEnumeration.enumeration;

        SQLQuery<Enumeration> pSQLQuery = queryFactory.select(qEnumeration)
                .from(qEnumeration)
                .where(qEnumeration.enumTypeId.eq(enumTypeId))
                .orderBy(qEnumeration.sequenceId.asc());

        SQLBindings bindings = pSQLQuery.getSQL();
        LOG.info("{}", bindings.getSQL());
        LOG.info("{}", bindings.getNullFriendlyBindings());
        QBean<Enumeration> enumerations = Projections.bean(Enumeration.class, qEnumeration.all());
        List<Enumeration> ret = pSQLQuery.transform(GroupBy.groupBy(qEnumeration.enumId).list(enumerations));
        LOG.info("size = {}", ret.size());
        return ret;
    }
}
