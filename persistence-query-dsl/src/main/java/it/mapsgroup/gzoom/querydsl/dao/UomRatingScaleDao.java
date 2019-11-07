package it.mapsgroup.gzoom.querydsl.dao;

import static com.querydsl.core.types.Projections.bean;
import static it.mapsgroup.gzoom.querydsl.QBeanUtils.merge;
import static org.slf4j.LoggerFactory.getLogger;

import java.math.BigDecimal;
import java.util.List;

import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import com.querydsl.core.Tuple;
import com.querydsl.core.group.GroupBy;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.QBean;
import com.querydsl.sql.SQLBindings;
import com.querydsl.sql.SQLQuery;
import com.querydsl.sql.SQLQueryFactory;

import it.mapsgroup.gzoom.querydsl.dto.*;

/**
 */
@Service
public class UomRatingScaleDao extends AbstractDao {
    private static final Logger LOG = getLogger(UomRatingScaleDao.class);

    private final SQLQueryFactory queryFactory;

    public UomRatingScaleDao(SQLQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Transactional
    public List<UomRatingScaleEx> getUomRatingScales(String uomId) {
        if (TransactionSynchronizationManager.isActualTransactionActive()) {
            TransactionStatus status = TransactionAspectSupport.currentTransactionStatus();
            status.getClass();
        }

        QUom uom = QUom.uom;
        QUomRatingScale uomRatingScale = QUomRatingScale.uomRatingScale;

        QBean<UomRatingScaleEx> uomRatingScaleExQBean = bean(UomRatingScaleEx.class, merge(uomRatingScale.all(), bean(Uom.class, uom.all()).as("uom")));

        SQLQuery<Tuple> tupleSQLQuery = queryFactory.select(uomRatingScale, uom)
                .from(uomRatingScale)
                .innerJoin(uomRatingScale.ratingToUom, uom)
                .where(uomRatingScale.uomId.eq(uomId))
                .orderBy(uomRatingScale.uomRatingValue.asc());

        SQLBindings bindings = tupleSQLQuery.getSQL();
        LOG.info("{}", bindings.getSQL());
        LOG.info("{}", bindings.getNullFriendlyBindings());
        List<UomRatingScaleEx> ret = tupleSQLQuery.transform(GroupBy.groupBy(uomRatingScale.uomRatingValue).list(uomRatingScaleExQBean));

        LOG.info("size = {}", ret.size());
        return ret;
    }

    @Transactional
    public UomRatingScaleEx getUomRatingScale(String uomId, BigDecimal uomRatingValue) {
        if (TransactionSynchronizationManager.isActualTransactionActive()) {
            TransactionStatus status = TransactionAspectSupport.currentTransactionStatus();
            status.getClass();
        }

        QUomRatingScale uomRatingScale = QUomRatingScale.uomRatingScale;
        QUom uom = QUom.uom;

        QBean<UomRatingScaleEx> uomRatingScaleExQBean = bean(UomRatingScaleEx.class, merge(uomRatingScale.all(), bean(Uom.class, uom.all()).as("uom")));

        SQLQuery<Tuple> tupleSQLQuery = queryFactory
                .select(uomRatingScale, uom)
                .from(uomRatingScale)
                .innerJoin(uomRatingScale.ratingToUom, uom)
                .where(getPkCondition(uomRatingScale, uomId, uomRatingValue));

        SQLBindings bindings = tupleSQLQuery.getSQL();
        LOG.info("{}", bindings.getSQL());
        LOG.info("{}", bindings.getNullFriendlyBindings());
        List<UomRatingScaleEx> ret = tupleSQLQuery.transform(GroupBy.groupBy(uomRatingScale.uomRatingValue).list(uomRatingScaleExQBean));

        return ret.isEmpty() ? null : ret.get(0);
    }

    @Transactional
    public boolean create(UomRatingScale record, String userLoginId) {
        QUomRatingScale uomRatingScale = QUomRatingScale.uomRatingScale;
        setCreatedTimestamp(record);
        setCreatedByUserLogin(record, userLoginId);
        long i = queryFactory.insert(uomRatingScale).populate(record).execute();
        LOG.info("created records: {}", i);

        return i > 0;
    }

    /**
     * Update record with uomId = id and uomRatingValue = value
     * 
     * @param id
     * @param value
     * @param record
     * @param userLoginId 
     * @return
     */
    @Transactional
    public boolean update(String id, BigDecimal value, UomRatingScale record, String userLoginId) {
        QUomRatingScale uomRatingScale = QUomRatingScale.uomRatingScale;
        setUpdateTimestamp(record);
        setLastModifiedByUserLogin(record, userLoginId);
        // Using bean population
        long i = queryFactory
                .update(uomRatingScale)
                .set(uomRatingScale.uomId, record.getUomId())
                .set(uomRatingScale.uomRatingValue, record.getUomRatingValue())
                .where(getPkCondition(uomRatingScale, id, value))
                .populate(record)
                .execute();

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
    public boolean delete(String uomId, BigDecimal uomRatingValue) {
        QUomRatingScale uomRatingScale = QUomRatingScale.uomRatingScale;
        long i = queryFactory
                .delete(uomRatingScale)
                .where(getPkCondition(uomRatingScale, uomId, uomRatingValue)).execute();
        LOG.info("deleted records: {}", i);

        return i > 0;
    }

    private Predicate getPkCondition(QUomRatingScale uomRatingScale, String uomId, BigDecimal uomRatingValue) {
        return uomRatingScale.uomId.eq(uomId).and(uomRatingScale.uomRatingValue.eq(uomRatingValue));
    }

    private void setCreatedByUserLogin(UomRatingScale record, String userLoginId) {
        record.setCreatedByUserLogin(userLoginId);
    }

    private void setLastModifiedByUserLogin(UomRatingScale record, String userLoginId) {
        record.setLastModifiedByUserLogin(userLoginId);
    }
}
