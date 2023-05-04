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
import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author Leonardo Minaudo.
 */
@Service
public class DataResourceTypeDao extends AbstractDao {
    private static final Logger LOG = getLogger(DataResourceTypeDao.class);

    private final SQLQueryFactory queryFactory;

    public DataResourceTypeDao(SQLQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    /**
     * This function gets a dataResourceType given its sequence name.
     *
     * @param id data resource type id of the dataResourceType
     * @return the corresponding dataResourceType record
     */
    @Transactional
    public DataResourceType get(String id) {
        if (TransactionSynchronizationManager.isActualTransactionActive()) {
            TransactionStatus status = TransactionAspectSupport.currentTransactionStatus();
            status.getClass();
        }

        QDataResourceType qDRT = QDataResourceType.dataResourceType;
        SQLQuery<DataResourceType> tSQLQuery = queryFactory.select(qDRT).from(qDRT).where(qDRT.dataResourceTypeId.eq(id));

        SQLBindings bindings = tSQLQuery.getSQL();
        LOG.info("{}", bindings.getSQL());
        LOG.info("{}", bindings.getNullFriendlyBindings());
        QBean<DataResourceType> dataResourceTypeQBeans = bean(DataResourceType.class, qDRT.all());
        List<DataResourceType> ret = tSQLQuery.transform(GroupBy.groupBy(qDRT.dataResourceTypeId).list(dataResourceTypeQBeans));

        return ret.isEmpty() ? null : ret.get(0);
    }

    /**
     * Gets a list of dataResourceType.
     *
     * @return
     */
    @Transactional
    public List<DataResourceType> getDataResourceTypeList() {
        if (TransactionSynchronizationManager.isActualTransactionActive()) {
            TransactionStatus status = TransactionAspectSupport.currentTransactionStatus();
            status.getClass();
        }

        QDataResourceType qDRT = QDataResourceType.dataResourceType;

        SQLQuery<DataResourceType> tupleSQLQuery = queryFactory.select(qDRT).from(qDRT);

        SQLBindings bindings = tupleSQLQuery.getSQL();

        LOG.info("{}", bindings.getSQL());
        LOG.info("{}", bindings.getNullFriendlyBindings());

        QBean<DataResourceType> qBean = bean(DataResourceType.class, qDRT.all());
        List<DataResourceType> ret = tupleSQLQuery.transform(GroupBy.groupBy(qDRT.dataResourceTypeId).list(qBean));

        LOG.info("size = {}", ret.size());
        return ret;
    }

    /**
     * This function creates a new record dataResourceType.
     *
     * @param record dataResourceType to add
     * @return true if the operation was successful
     */
    @Transactional
    public boolean create(DataResourceType record) {
        QDataResourceType qDRT = QDataResourceType.dataResourceType;
        setCreatedTimestamp(record);

        long i = queryFactory.insert(qDRT).populate(record).execute();
        LOG.info("created records: {}", i);

        return i > 0;
    }

    /**
     * Update of dataResourceType.
     *
     * @param record dataResourceType to update
     * @return true if the operation was successful
     */
    @Transactional
    public boolean update(DataResourceType record) {

        QDataResourceType qDRT = QDataResourceType.dataResourceType;
        setUpdateTimestamp(record);

        long i = queryFactory.update(qDRT).set(qDRT.dataResourceTypeId, record.getDataResourceTypeId())
                .where(qDRT.dataResourceTypeId.eq(record.getDataResourceTypeId())).populate(record).execute();

        LOG.info("updated records: {}", i);

        return i > 0;
    }

    /**
     *  Deletes a dataResourceType.
     *
     * @param id id of the dataResourceType to be deleted
     * @return true if the operation was successful
     */
    @Transactional
    public boolean delete(String id) {
        QDataResourceType qDRT = QDataResourceType.dataResourceType;

        long i = queryFactory.delete(qDRT).where(qDRT.dataResourceTypeId.eq(id)).execute();
        LOG.info("deleted records: {}", i);

        return i > 0;
    }


}
