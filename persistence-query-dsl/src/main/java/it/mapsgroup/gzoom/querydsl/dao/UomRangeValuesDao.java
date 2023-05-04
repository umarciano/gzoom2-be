package it.mapsgroup.gzoom.querydsl.dao;

import com.querydsl.core.Tuple;
import com.querydsl.core.group.GroupBy;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.QBean;
import com.querydsl.sql.SQLBindings;
import com.querydsl.sql.SQLQuery;
import com.querydsl.sql.SQLQueryFactory;
import it.mapsgroup.gzoom.querydsl.dto.*;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.math.BigDecimal;
import java.util.List;

import static com.querydsl.core.types.Projections.bean;
import static it.mapsgroup.gzoom.querydsl.QBeanUtils.merge;
import static org.slf4j.LoggerFactory.getLogger;

@Service
public class UomRangeValuesDao {

    private static final Logger LOG = getLogger(UomRangeValuesDao.class);

    private final SQLQueryFactory queryFactory;

    public UomRangeValuesDao(SQLQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }



    @Transactional
    public List<UomRangeValues> getUomRangeValues(String uomRangeId) {
        if (TransactionSynchronizationManager.isActualTransactionActive()) {
            TransactionStatus status = TransactionAspectSupport.currentTransactionStatus();
            status.getClass();
        }

        QUomRangeValues qUomRangeValues = QUomRangeValues.uomRangeValues;
        SQLQuery<UomRangeValues> tupleSQLQuery = queryFactory.select(qUomRangeValues)
                .from(qUomRangeValues)
                .where(qUomRangeValues.uomRangeId.eq(uomRangeId))
                .orderBy(qUomRangeValues.comments.asc());

        SQLBindings bindings = tupleSQLQuery.getSQL();
        LOG.info("{}", bindings.getSQL());
        LOG.info("{}", bindings.getNullFriendlyBindings());
        QBean<UomRangeValues> uoms = Projections.bean(UomRangeValues.class, qUomRangeValues.all());
        List<UomRangeValues> ret = tupleSQLQuery.transform(GroupBy.groupBy(qUomRangeValues.uomRangeValuesId).list(uoms));
        LOG.info("size = {}", ret.size());
        return ret;
    }

    /**
     * This function gets the maximum range value.
     *
     * @param uomRangeId rangeDefault from comments.
     * @return List of BigDecimal.
     */
    @Transactional
    public List<BigDecimal> getUomRangeValuesMax(String uomRangeId) {
        if (TransactionSynchronizationManager.isActualTransactionActive()) {
            TransactionStatus status = TransactionAspectSupport.currentTransactionStatus();
            status.getClass();
        }

        QUomRangeValues qUomRangeValues = QUomRangeValues.uomRangeValues;
        List<BigDecimal> tupleSQLQuery = queryFactory.select(qUomRangeValues.rangeValuesFactor.max().as("MAX_RANGE"))
                .from(qUomRangeValues)
                .where(qUomRangeValues.uomRangeId.eq(uomRangeId))
                .fetch();

        return tupleSQLQuery;
    }

    /**
     * This function gets the minimum range value.
     *
     * @param uomRangeId rangeDefault from comments.
     * @return A BigDecimal.
     */
    @Transactional
    public BigDecimal getUomRangeValuesMin(String uomRangeId) {
        if (TransactionSynchronizationManager.isActualTransactionActive()) {
            TransactionStatus status = TransactionAspectSupport.currentTransactionStatus();
            status.getClass();
        }

        QUomRangeValues qUomRangeValues = QUomRangeValues.uomRangeValues;
        List<BigDecimal> tupleSQLQuery = queryFactory.select(qUomRangeValues.rangeValuesFactor.min().as("MIN_RANGE"))
                .from(qUomRangeValues)
                .where(qUomRangeValues.uomRangeId.eq(uomRangeId))
                .fetch();

        return tupleSQLQuery.isEmpty()? null : tupleSQLQuery.get(0);
    }


    /**
     * This function gets the emoticon path based on amount.
     *
     * @param rangeDefault rangeDefault from comments
     * @param amount Amount value.
     * @return List of UomRangeValuesExt.
     */
    @Transactional
    public List<UomRangeValuesExt> getPathEmoticon(String rangeDefault, Float amount) {
        if (TransactionSynchronizationManager.isActualTransactionActive()) {
            TransactionStatus status = TransactionAspectSupport.currentTransactionStatus();
            status.getClass();
        }

        QUomRangeValues qRV = QUomRangeValues.uomRangeValues;
        QDataResource qDR = QDataResource.dataResource;

        SQLQuery<Tuple> tupleSQLQuery = queryFactory.select(qRV, qDR).from(qRV)
                .innerJoin(qDR).on(qDR.dataResourceId.eq(qRV.iconContentId))
                .where(qRV.uomRangeId.eq(rangeDefault).and(qRV.fromValue.loe(amount)).and(qRV.thruValue.goe(amount)));

        SQLBindings bindings = tupleSQLQuery.getSQL();
        LOG.info("{}", bindings.getSQL());
        LOG.info("{}", bindings.getNullFriendlyBindings());

        QBean<UomRangeValuesExt> uomRangeValuesQBean = bean(UomRangeValuesExt.class, merge(qRV.all(), bean(DataResource.class, qDR.all()).as("dataResource")));
        List<UomRangeValuesExt> ret = tupleSQLQuery.transform(GroupBy.groupBy(qRV.iconContentId).list(uomRangeValuesQBean));
        LOG.info("size = {}", ret.size());

        return ret;
    }
}
