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

import it.mapsgroup.gzoom.querydsl.dto.QUomType;
import it.mapsgroup.gzoom.querydsl.dto.UomType;

/**
 */
@Service
public class UomTypeDao extends AbstractDao {
    private static final Logger LOG = getLogger(UomTypeDao.class);

    private final SQLQueryFactory queryFactory;

    public UomTypeDao(SQLQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Transactional
    public List<UomType> getUomTypes() {
        if (TransactionSynchronizationManager.isActualTransactionActive()) {
            TransactionStatus status = TransactionAspectSupport.currentTransactionStatus();
            status.getClass();
        }

        QUomType qUomType = QUomType.uomType;

        SQLQuery<UomType> tupleSQLQuery = queryFactory.select(qUomType).from(qUomType);

        SQLBindings bindings = tupleSQLQuery.getSQL();
        LOG.info("{}", bindings.getSQL());
        LOG.info("{}", bindings.getNullFriendlyBindings());
        QBean<UomType> uomTypes = Projections.bean(UomType.class, qUomType.all());

        List<UomType> ret = tupleSQLQuery.transform(GroupBy.groupBy(qUomType.uomTypeId).list(uomTypes));
        LOG.info("size = {}", ret.size());
        return ret;
    }

    @Transactional
    public UomType getUomType(String uomTypeId) {
        if (TransactionSynchronizationManager.isActualTransactionActive()) {
            TransactionStatus status = TransactionAspectSupport.currentTransactionStatus();
            status.getClass();
        }

        QUomType qUomType = QUomType.uomType;

        SQLQuery<UomType> tupleSQLQuery = queryFactory.select(qUomType).from(qUomType).where(qUomType.uomTypeId.eq(uomTypeId));

        SQLBindings bindings = tupleSQLQuery.getSQL();
        LOG.info("{}", bindings.getSQL());
        LOG.info("{}", bindings.getNullFriendlyBindings());
        QBean<UomType> uomTypes = Projections.bean(UomType.class, qUomType.all());
        List<UomType> ret = tupleSQLQuery.transform(GroupBy.groupBy(qUomType.uomTypeId).list(uomTypes));
        return ret.isEmpty() ? null : ret.get(0);
    }

    @Transactional
    // TODO per prova
    public UomType getUomType(String uomTypeId, long pageNumber, long pageSize) {
        if (TransactionSynchronizationManager.isActualTransactionActive()) {
            TransactionStatus status = TransactionAspectSupport.currentTransactionStatus();
            status.getClass();
        }

        QUomType qUomType = QUomType.uomType;

        SQLQuery<UomType> tupleSQLQuery = queryFactory.select(qUomType).from(qUomType).where(qUomType.uomTypeId.eq(uomTypeId));

        SQLBindings bindings = tupleSQLQuery.getSQL();
        LOG.info("{}", bindings.getSQL());
        LOG.info("{}", bindings.getNullFriendlyBindings());
        QBean<UomType> uomTypes = Projections.bean(UomType.class, qUomType.all());

        tupleSQLQuery.offset(pageNumber * pageSize).limit(pageSize);

        tupleSQLQuery.fetchCount();

        List<UomType> ret = tupleSQLQuery.transform(GroupBy.groupBy(qUomType.uomTypeId).list(uomTypes));
        return ret.isEmpty() ? null : ret.get(0);
    }

    @Transactional
    public boolean create(UomType record, String userLoginId) {
        QUomType uomType = QUomType.uomType;
        setCreatedTimestamp(record);
        setCreatedByUserLogin(record, userLoginId);
        long i = queryFactory.insert(uomType).populate(record).execute();
        LOG.info("created records: {}", i);

        return i > 0;
    }

    /**
     * Update record with uomTypeId = id
     * @param id
     * @param record
     * @param userLoginId
     * @return
     */
    @Transactional
    public boolean update(String id, UomType record, String userLoginId) {
        QUomType uomType = QUomType.uomType;
        setUpdateTimestamp(record);
        setLastModifiedByUserLogin(record, userLoginId);
        // Using bean population
        long i = queryFactory.update(uomType)
        .set(uomType.uomTypeId, record.getUomTypeId())
        .where(uomType.uomTypeId.eq(id))
        .populate(record)
        .execute();
        
        LOG.info("updated records: {}", i);

        return i > 0;
    }

    /**
     * Delete record with uomTypeId = id
     * @param id
     * @return
     */
    @Transactional
    public boolean delete(String id) {
        QUomType uomType = QUomType.uomType;
        long i = queryFactory
                .delete(uomType)
                .where(uomType.uomTypeId.eq(id))
                .execute();
        LOG.info("deleted records: {}", i);

        return i > 0;
    }

    private void setCreatedByUserLogin(UomType record, String userLoginId) {
        record.setCreatedByUserLogin(userLoginId);
    }

    private void setLastModifiedByUserLogin(UomType record, String userLoginId) {
        record.setLastModifiedByUserLogin(userLoginId);
    }

}
