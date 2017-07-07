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

import it.mapsgroup.gzoom.querydsl.dto.*;

/**
 */
@Service
public class UomDao extends AbstractDao {
    private static final Logger LOG = getLogger(UomDao.class);

    private final SQLQueryFactory queryFactory;

    public UomDao(SQLQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Transactional
    public List<UomEx> getUoms() {
        if (TransactionSynchronizationManager.isActualTransactionActive()) {
            TransactionStatus status = TransactionAspectSupport.currentTransactionStatus();
            status.getClass();
        }

        QUom uom = QUom.uom;
        QUomType uomType = QUomType.uomType;

        QBean<UomEx> uomExQBean = bean(UomEx.class, merge(uom.all(), bean(UomType.class, uomType.all()).as("uomType")));

        SQLQuery<Tuple> tupleSQLQuery = queryFactory.select(uom, uomType).from(uom).innerJoin(uom.uomToType, uomType).orderBy(uomType.description.asc());

        SQLBindings bindings = tupleSQLQuery.getSQL();
        LOG.info("{}", bindings.getSQL());
        LOG.info("{}", bindings.getBindings()); // debug
        List<UomEx> ret = tupleSQLQuery.transform(GroupBy.groupBy(uom.uomId).list(uomExQBean));

        LOG.info("size = {}", ret.size());
        return ret;
    }

    @Transactional
    public UomEx getUom(String uomId) {
        if (TransactionSynchronizationManager.isActualTransactionActive()) {
            TransactionStatus status = TransactionAspectSupport.currentTransactionStatus();
            status.getClass();
        }

        QUom uom = QUom.uom;
        QUomType uomType = QUomType.uomType;

        QBean<UomEx> uomExQBean = bean(UomEx.class, merge(uom.all(), bean(UomType.class, uomType.all()).as("uomType")));

        SQLQuery<Tuple> tupleSQLQuery = queryFactory.select(uom, uomType).from(uom).innerJoin(uom.uomToType, uomType).where(uom.uomId.eq(uomId));

        SQLBindings bindings = tupleSQLQuery.getSQL();
        LOG.info("{}", bindings.getSQL());
        LOG.info("{}", bindings.getBindings()); // debug
        List<UomEx> ret = tupleSQLQuery.transform(GroupBy.groupBy(uom.uomId).list(uomExQBean));

        return ret.isEmpty() ? null : ret.get(0);
    }

    @Transactional
    public boolean create(Uom record, String userLoginId) {
        QUom uom = QUom.uom;
        setCreatedTimestamp(record);
        setCreatedByUserLogin(record, userLoginId);
        long i = queryFactory.insert(uom).populate(record).execute();
        LOG.info("created records: {}", i);

        return i > 0;
    }

    /**
     * Update record with uomId = id
     * 
     * @param id
     * @param record
     * @param userLoginId
     * @return
     */
    @Transactional
    public boolean update(String id, Uom record, String userLoginId) {
        QUom uom = QUom.uom;
        setUpdateTimestamp(record);
        setLastModifiedByUserLogin(record, userLoginId);
        // Using bean population
        long i = queryFactory.update(uom).set(uom.uomId, record.getUomId()).where(uom.uomId.eq(id)).populate(record).execute();

        LOG.info("updated records: {}", i);

        return i > 0;
    }

    /**
     * Delete record with uomId = id
     * 
     * @param id
     * @return
     */
    @Transactional
    public boolean delete(String id) {
        QUom uom = QUom.uom;
        long i = queryFactory.delete(uom).where(uom.uomId.eq(id)).execute();
        LOG.info("deleted records: {}", i);

        return i > 0;
    }

    private void setCreatedByUserLogin(Uom record, String userLoginId) {
        record.setCreatedByUserLogin(userLoginId);
    }

    private void setLastModifiedByUserLogin(Uom record, String userLoginId) {
        record.setLastModifiedByUserLogin(userLoginId);
    }
}
