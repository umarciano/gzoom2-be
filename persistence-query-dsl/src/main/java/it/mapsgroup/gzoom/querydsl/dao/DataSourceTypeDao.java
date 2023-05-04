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
public class DataSourceTypeDao extends AbstractDao {
    private static final Logger LOG = getLogger(DataSourceTypeDao.class);

    private final SQLQueryFactory queryFactory;

    public DataSourceTypeDao(SQLQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    /**
     * This function gets a dataSourceType given its sequence name.
     *
     * @param id data resource type id of the dataSourceType
     * @return the corresponding dataSourceType record
     */
    @Transactional
    public DataSourceType get(String id) {
        if (TransactionSynchronizationManager.isActualTransactionActive()) {
            TransactionStatus status = TransactionAspectSupport.currentTransactionStatus();
            status.getClass();
        }

        QDataSourceType qDST = QDataSourceType.dataSourceType;
        SQLQuery<DataSourceType> tSQLQuery = queryFactory.select(qDST).from(qDST).where(qDST.dataSourceTypeId.eq(id));

        SQLBindings bindings = tSQLQuery.getSQL();
        LOG.info("{}", bindings.getSQL());
        LOG.info("{}", bindings.getNullFriendlyBindings());
        QBean<DataSourceType> dataSourceTypeQBeans = bean(DataSourceType.class, qDST.all());
        List<DataSourceType> ret = tSQLQuery.transform(GroupBy.groupBy(qDST.dataSourceTypeId).list(dataSourceTypeQBeans));

        return ret.isEmpty() ? null : ret.get(0);
    }

    /**
     * Gets a list of dataSourceType.
     *
     * @return
     */
    @Transactional
    public List<DataSourceType> getDataSourceTypeList() {
        if (TransactionSynchronizationManager.isActualTransactionActive()) {
            TransactionStatus status = TransactionAspectSupport.currentTransactionStatus();
            status.getClass();
        }

        QDataSourceType qDST = QDataSourceType.dataSourceType;

        SQLQuery<DataSourceType> tupleSQLQuery = queryFactory.select(qDST).from(qDST);

        SQLBindings bindings = tupleSQLQuery.getSQL();

        LOG.info("{}", bindings.getSQL());
        LOG.info("{}", bindings.getNullFriendlyBindings());

        QBean<DataSourceType> qBean = bean(DataSourceType.class, qDST.all());
        List<DataSourceType> ret = tupleSQLQuery.transform(GroupBy.groupBy(qDST.dataSourceTypeId).list(qBean));

        LOG.info("size = {}", ret.size());
        return ret;
    }

    /**
     * This function creates a new record dataSourceType.
     *
     * @param record dataSourceType to add
     * @param userLoginId User login id
     * @return true if the operation was successful
     */
    @Transactional
    public boolean create(DataSourceType record, String userLoginId) {
        QDataSourceType qDST = QDataSourceType.dataSourceType;
        setCreatedTimestamp(record);
        setCreatedByUserLogin(record, userLoginId);

        long i = queryFactory.insert(qDST).populate(record).execute();
        LOG.info("created records: {}", i);

        return i > 0;
    }

    /**
     * Update of dataSourceType.
     *
     * @param record dataSourceType to update
     * @param userLoginId user login id
     * @return true if the operation was successful
     */
    @Transactional
    public boolean update(DataSourceType record, String userLoginId) {

        QDataSourceType qDST = QDataSourceType.dataSourceType;
        setUpdateTimestamp(record);
        setLastModifiedByUserLogin(record, userLoginId);

        long i = queryFactory.update(qDST).set(qDST.dataSourceTypeId, record.getDataSourceTypeId())
                .where(qDST.dataSourceTypeId.eq(record.getDataSourceTypeId())).populate(record).execute();

        LOG.info("updated records: {}", i);

        return i > 0;
    }

    /**
     *  Deletes a dataSourceType.
     *
     * @param id id of the dataSourceType to be deleted
     * @return true if the operation was successful
     */
    @Transactional
    public boolean delete(String id) {
        QDataSourceType qDST = QDataSourceType.dataSourceType;

        long i = queryFactory.delete(qDST).where(qDST.dataSourceTypeId.eq(id)).execute();
        LOG.info("deleted records: {}", i);

        return i > 0;
    }

    private void setCreatedByUserLogin(DataSourceType record, String userLoginId) {
        record.setCreatedByUserLogin(userLoginId);
    }

    private void setLastModifiedByUserLogin(DataSourceType record, String userLoginId) {
        record.setLastModifiedByUserLogin(userLoginId);
    }


}
