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
}
