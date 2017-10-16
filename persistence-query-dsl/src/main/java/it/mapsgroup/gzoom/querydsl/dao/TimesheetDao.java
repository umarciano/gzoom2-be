package it.mapsgroup.gzoom.querydsl.dao;

import com.querydsl.core.Tuple;
import com.querydsl.core.group.GroupBy;
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

/**
 */
@Service
public class TimesheetDao extends AbstractDao {
    private static final Logger LOG = getLogger(TimesheetDao.class);

    private final SQLQueryFactory queryFactory;

    public TimesheetDao(SQLQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    /*
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
*/
}
