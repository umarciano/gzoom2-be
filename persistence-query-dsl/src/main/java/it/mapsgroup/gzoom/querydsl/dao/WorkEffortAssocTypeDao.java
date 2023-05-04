package it.mapsgroup.gzoom.querydsl.dao;

import com.querydsl.core.Tuple;
import com.querydsl.core.group.GroupBy;
import com.querydsl.core.types.QBean;
import com.querydsl.sql.SQLBindings;
import com.querydsl.sql.SQLQuery;
import com.querydsl.sql.SQLQueryFactory;
import it.mapsgroup.gzoom.querydsl.dto.QWorkEffortAssocType;
import it.mapsgroup.gzoom.querydsl.dto.WorkEffortAssocType;
import it.mapsgroup.gzoom.querydsl.dto.WorkEffortAssocTypeExt;
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
public class WorkEffortAssocTypeDao extends AbstractDao{
    private static final Logger LOG = getLogger(WorkEffortAssocTypeDao.class);

    private final SQLQueryFactory queryFactory;

    public WorkEffortAssocTypeDao(SQLQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    /**
     * This function creates a new record workEffortAssocType.
     *
     * @param record workEffortAssocType to add
     * @param userLoginId User login id
     * @return true if the operation was successful
     */
    @Transactional
    public boolean create(WorkEffortAssocType record, String userLoginId) {
        QWorkEffortAssocType qWEAT = QWorkEffortAssocType.workEffortAssocType;
        setCreatedTimestamp(record);
        setCreatedByUserLogin(record, userLoginId);

        long i = queryFactory.insert(qWEAT).populate(record).execute();
        LOG.info("created records: {}", i);

        return i > 0;
    }

    /**
     * This function gets a workEffortAssocType given its id.
     *
     * @param id id of the workEffortAssocType
     * @return the corresponding workEffortAssocType record
     */
    @Transactional
    public WorkEffortAssocType get(String id) {
        if (TransactionSynchronizationManager.isActualTransactionActive()) {
            TransactionStatus status = TransactionAspectSupport.currentTransactionStatus();
            status.getClass();
        }

        QWorkEffortAssocType qWEAT = QWorkEffortAssocType.workEffortAssocType;
        SQLQuery<WorkEffortAssocType> tSQLQuery = queryFactory.select(qWEAT).from(qWEAT).where(qWEAT.workEffortAssocTypeId.eq(id));

        SQLBindings bindings = tSQLQuery.getSQL();
        LOG.info("{}", bindings.getSQL());
        LOG.info("{}", bindings.getNullFriendlyBindings());
        QBean<WorkEffortAssocType> workEffortAssocTypeQBeans = bean(WorkEffortAssocType.class, qWEAT.all());
        List<WorkEffortAssocType> ret = tSQLQuery.transform(GroupBy.groupBy(qWEAT.workEffortAssocTypeId).list(workEffortAssocTypeQBeans));

        return ret.isEmpty() ? null : ret.get(0);
    }

    /**
     * Gets a list of workEffortAssocTypeExt.
     *
     * @return
     */
    @Transactional
    public List<WorkEffortAssocTypeExt> getWorkEffortAssocTypeList() {
        if (TransactionSynchronizationManager.isActualTransactionActive()) {
            TransactionStatus status = TransactionAspectSupport.currentTransactionStatus();
            status.getClass();
        }

        QWorkEffortAssocType qWEAT = new QWorkEffortAssocType("weat");
        QWorkEffortAssocType qWEAT2 = new QWorkEffortAssocType("weat2");

        SQLQuery<Tuple> tupleSQLQuery = queryFactory.select(qWEAT, qWEAT2).from(qWEAT)
                .leftJoin(qWEAT2).on(qWEAT.parentTypeId.eq(qWEAT2.workEffortAssocTypeId))
                .orderBy(qWEAT.workEffortAssocTypeId.asc());

        SQLBindings bindings = tupleSQLQuery.getSQL();

        LOG.info("{}", bindings.getSQL());
        LOG.info("{}", bindings.getNullFriendlyBindings());

        QBean<WorkEffortAssocTypeExt> qBean = bean(WorkEffortAssocTypeExt.class,
                merge(qWEAT.all(), bean(WorkEffortAssocType.class, qWEAT2.all()).as("parentWorkEffortAssocType")));
        List<WorkEffortAssocTypeExt> ret = tupleSQLQuery.transform(GroupBy.groupBy(qWEAT.workEffortAssocTypeId).list(qBean));

        LOG.info("size = {}", ret.size());
        return ret;
    }

    /**
     * Updates the parentTypeId.
     *
     * @param idChild id of the workEffortAssocType to be updated
     * @param idParent id of the workEffortAssocType to be assigned
     * @return true if the operation was successful
     */
    @Transactional
    public boolean updateAssocWorkEffortAssocType (String idChild, String idParent) {
        QWorkEffortAssocType qWEAT = QWorkEffortAssocType.workEffortAssocType;

        long i = queryFactory.update(qWEAT).set(qWEAT.parentTypeId, idParent).where(qWEAT.workEffortAssocTypeId.eq(idChild)).execute();

        return i > 0;
    }

    /**
     * Update of workEffortAssocType.
     *
     * @param record workEffortAssocType to update
     * @param userLoginId user login id
     * @return true if the operation was successful
     */
    @Transactional
    public boolean update(WorkEffortAssocType record, String userLoginId) {

        QWorkEffortAssocType qWEAT = QWorkEffortAssocType.workEffortAssocType;
        setUpdateTimestamp(record);
        setLastModifiedByUserLogin(record, userLoginId);

        long i = queryFactory.update(qWEAT).set(qWEAT.workEffortAssocTypeId, record.getWorkEffortAssocTypeId()).set(qWEAT.parentTypeId, record.getParentTypeId())
               .where(qWEAT.workEffortAssocTypeId.eq(record.getWorkEffortAssocTypeId())).populate(record).execute();

        LOG.info("updated records: {}", i);

        return i > 0;
    }

    /**
     *  Deletes a workEffortAssocType.
     *
     * @param id id of the workEffortAssocType to be deleted
     * @return true if the operation was successful
     */
    @Transactional
    public boolean delete(String id) {
        QWorkEffortAssocType qWEAT = QWorkEffortAssocType.workEffortAssocType;

        long i = queryFactory.delete(qWEAT).where(qWEAT.workEffortAssocTypeId.eq(id)).execute();
        LOG.info("deleted records: {}", i);

        return i > 0;
    }

    private void setCreatedByUserLogin(WorkEffortAssocType record, String userLoginId) {
        record.setCreatedByUserLogin(userLoginId);
    }

    private void setLastModifiedByUserLogin(WorkEffortAssocType record, String userLoginId) {
        record.setLastModifiedByUserLogin(userLoginId);
    }


}
