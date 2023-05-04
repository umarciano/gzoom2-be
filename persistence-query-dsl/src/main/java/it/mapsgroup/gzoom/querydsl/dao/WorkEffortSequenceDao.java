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
 * @author Leonardo Minaudo.
 */
@Service
public class WorkEffortSequenceDao extends AbstractDao {
    private static final Logger LOG = getLogger(WorkEffortSequenceDao.class);

    private final SQLQueryFactory queryFactory;

    public WorkEffortSequenceDao(SQLQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    /**
     * This function gets a workEffortSequence given its sequence name.
     *
     * @param id sequence name of the workEffortSequence
     * @return the corresponding workEffortSequence record
     */
    @Transactional
    public WorkEffortSequence get(String id) {
        if (TransactionSynchronizationManager.isActualTransactionActive()) {
            TransactionStatus status = TransactionAspectSupport.currentTransactionStatus();
            status.getClass();
        }

        QWorkEffortSequence qWES = QWorkEffortSequence.workEffortSequence;
        SQLQuery<WorkEffortSequence> tSQLQuery = queryFactory.select(qWES).from(qWES).where(qWES.seqName.eq(id));

        SQLBindings bindings = tSQLQuery.getSQL();
        LOG.info("{}", bindings.getSQL());
        LOG.info("{}", bindings.getNullFriendlyBindings());
        QBean<WorkEffortSequence> workEffortSequenceQBeans = bean(WorkEffortSequence.class, qWES.all());
        List<WorkEffortSequence> ret = tSQLQuery.transform(GroupBy.groupBy(qWES.seqName).list(workEffortSequenceQBeans));

        return ret.isEmpty() ? null : ret.get(0);
    }

    /**
     * Gets a list of workEffortSequence.
     *
     * @return
     */
    @Transactional
    public List<WorkEffortSequence> getWorkEffortSequenceList() {
        if (TransactionSynchronizationManager.isActualTransactionActive()) {
            TransactionStatus status = TransactionAspectSupport.currentTransactionStatus();
            status.getClass();
        }

        QWorkEffortSequence qWES = QWorkEffortSequence.workEffortSequence;

        SQLQuery<WorkEffortSequence> tupleSQLQuery = queryFactory.select(qWES).from(qWES);

        SQLBindings bindings = tupleSQLQuery.getSQL();

        LOG.info("{}", bindings.getSQL());
        LOG.info("{}", bindings.getNullFriendlyBindings());

        QBean<WorkEffortSequence> qBean = bean(WorkEffortSequence.class, qWES.all());
        List<WorkEffortSequence> ret = tupleSQLQuery.transform(GroupBy.groupBy(qWES.seqName).list(qBean));

        LOG.info("size = {}", ret.size());
        return ret;
    }

    /**
     * This function creates a new record workEffortSequence.
     *
     * @param record workEffortSequence to add
     * @param userLoginId User login id
     * @return true if the operation was successful
     */
    @Transactional
    public boolean create(WorkEffortSequence record, String userLoginId) {
        QWorkEffortSequence qWES = QWorkEffortSequence.workEffortSequence;
        setCreatedTimestamp(record);
        setCreatedByUserLogin(record, userLoginId);

        long i = queryFactory.insert(qWES).populate(record).execute();
        LOG.info("created records: {}", i);

        return i > 0;
    }

    /**
     * Update of workEffortSequence.
     *
     * @param record workEffortSequence to update
     * @param userLoginId user login id
     * @return true if the operation was successful
     */
    @Transactional
    public boolean update(WorkEffortSequence record, String userLoginId) {

        QWorkEffortSequence qWES = QWorkEffortSequence.workEffortSequence;
        setUpdateTimestamp(record);
        setLastModifiedByUserLogin(record, userLoginId);

        long i = queryFactory.update(qWES).set(qWES.seqName, record.getSeqName())
                .where(qWES.seqName.eq(record.getSeqName())).populate(record).execute();

        LOG.info("updated records: {}", i);

        return i > 0;
    }

    /**
     *  Deletes a workEffortSequence.
     *
     * @param id id of the workEffortSequence to be deleted
     * @return true if the operation was successful
     */
    @Transactional
    public boolean delete(String id) {
        QWorkEffortSequence qWES = QWorkEffortSequence.workEffortSequence;

        long i = queryFactory.delete(qWES).where(qWES.seqName.eq(id)).execute();
        LOG.info("deleted records: {}", i);

        return i > 0;
    }

    private void setCreatedByUserLogin(WorkEffortSequence record, String userLoginId) {
        record.setCreatedByUserLogin(userLoginId);
    }

    private void setLastModifiedByUserLogin(WorkEffortSequence record, String userLoginId) {
        record.setLastModifiedByUserLogin(userLoginId);
    }

}
