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
public class GlFiscalTypeDao extends AbstractDao {
    private static final Logger LOG = getLogger(GlFiscalTypeDao.class);

    private final SQLQueryFactory queryFactory;

    public GlFiscalTypeDao(SQLQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    /**
     * This function gets a glFiscalType given its sequence name.
     *
     * @param id sequence name of the glFiscalType
     * @return the corresponding glFiscalType record
     */
    @Transactional
    public GlFiscalType get(String id) {
        if (TransactionSynchronizationManager.isActualTransactionActive()) {
            TransactionStatus status = TransactionAspectSupport.currentTransactionStatus();
            status.getClass();
        }

        QGlFiscalType qGFT = QGlFiscalType.glFiscalType;
        SQLQuery<GlFiscalType> tSQLQuery = queryFactory.select(qGFT).from(qGFT).where(qGFT.glFiscalTypeId.eq(id));

        SQLBindings bindings = tSQLQuery.getSQL();
        LOG.info("{}", bindings.getSQL());
        LOG.info("{}", bindings.getNullFriendlyBindings());
        QBean<GlFiscalType> glFiscalTypeQBeans = bean(GlFiscalType.class, qGFT.all());
        List<GlFiscalType> ret = tSQLQuery.transform(GroupBy.groupBy(qGFT.glFiscalTypeId).list(glFiscalTypeQBeans));

        return ret.isEmpty() ? null : ret.get(0);
    }

    /**
     * Gets a list of glFiscalTypeExt with enumeration table.
     * @return
     */
    @Transactional
    public List<GlFiscalTypeEx> getDetectionType() {
        if (TransactionSynchronizationManager.isActualTransactionActive()) {
            TransactionStatus status = TransactionAspectSupport.currentTransactionStatus();
            status.getClass();
        }

        QGlFiscalType qGFT = QGlFiscalType.glFiscalType;
        QEnumeration qE = QEnumeration.enumeration;

        SQLQuery<Tuple> tupleSQLQuery = queryFactory.select(qGFT, qE).from(qGFT)
                .leftJoin(qE).on(qE.enumId.eq(qGFT.glFiscalTypeEnumId))
                .where(qE.enumTypeId.eq("GL_FISCAL_TYPE"));

        SQLBindings bindings = tupleSQLQuery.getSQL();

        LOG.info("{}", bindings.getSQL());
        LOG.info("{}", bindings.getNullFriendlyBindings());

        QBean<GlFiscalTypeEx> qBean = bean(GlFiscalTypeEx.class,
                merge(qGFT.all(), bean(Enumeration.class, qE.all()).as("enumeration")));
        List<GlFiscalTypeEx> ret = tupleSQLQuery.transform(GroupBy.groupBy(qGFT.glFiscalTypeId).list(qBean));

        LOG.info("size = {}", ret.size());
        return ret;
    }

    /**
     * Gets a list of glFiscalType.
     *
     * @return
     */
    @Transactional
    public List<GlFiscalType> getGlFiscalTypeList() {
        if (TransactionSynchronizationManager.isActualTransactionActive()) {
            TransactionStatus status = TransactionAspectSupport.currentTransactionStatus();
            status.getClass();
        }

        QGlFiscalType qGFT = QGlFiscalType.glFiscalType;

        SQLQuery<GlFiscalType> tupleSQLQuery = queryFactory.select(qGFT).from(qGFT);

        SQLBindings bindings = tupleSQLQuery.getSQL();

        LOG.info("{}", bindings.getSQL());
        LOG.info("{}", bindings.getNullFriendlyBindings());

        QBean<GlFiscalType> qBean = bean(GlFiscalType.class, qGFT.all());
        List<GlFiscalType> ret = tupleSQLQuery.transform(GroupBy.groupBy(qGFT.glFiscalTypeId).list(qBean));

        LOG.info("size = {}", ret.size());
        return ret;
    }

    /**
     * This function creates a new record glFiscalType.
     *
     * @param record glFiscalType to add
     * @param userLoginId User login id
     * @return true if the operation was successful
     */
    @Transactional
    public boolean create(GlFiscalType record, String userLoginId) {
        QGlFiscalType qGFT = QGlFiscalType.glFiscalType;
        setCreatedTimestamp(record);
        setCreatedByUserLogin(record, userLoginId);

        long i = queryFactory.insert(qGFT).populate(record).execute();
        LOG.info("created records: {}", i);

        return i > 0;
    }

    /**
     * Update of glFiscalType.
     *
     * @param record glFiscalType to update
     * @param userLoginId user login id
     * @return true if the operation was successful
     */
    @Transactional
    public boolean update(GlFiscalType record, String userLoginId) {

        QGlFiscalType qGFT = QGlFiscalType.glFiscalType;
        setUpdateTimestamp(record);
        setLastModifiedByUserLogin(record, userLoginId);

        long i = queryFactory.update(qGFT).set(qGFT.glFiscalTypeId, record.getGlFiscalTypeId())
                .set(qGFT.periodicalAbsoluteEnumId, record.getPeriodicalAbsoluteEnumId())
                .set(qGFT.glFiscalTypeEnumId, record.getGlFiscalTypeEnumId())
                .set(qGFT.isFinancialUsed, record.getIsFinancialUsed())
                .set(qGFT.isAccountUsed, record.getIsAccountUsed())
                .set(qGFT.isIndicatorUsed, record.getIsIndicatorUsed())
                .where(qGFT.glFiscalTypeId.eq(record.getGlFiscalTypeId())).populate(record).execute();

        LOG.info("updated records: {}", i);

        return i > 0;
    }

    /**
     *  Deletes a glFiscalType.
     *
     * @param id id of the glFiscalType to be deleted
     * @return true if the operation was successful
     */
    @Transactional
    public boolean delete(String id) {
        QGlFiscalType qGFT = QGlFiscalType.glFiscalType;

        long i = queryFactory.delete(qGFT).where(qGFT.glFiscalTypeId.eq(id)).execute();
        LOG.info("deleted records: {}", i);

        return i > 0;
    }

    private void setCreatedByUserLogin(GlFiscalType record, String userLoginId) {
        record.setCreatedByUserLogin(userLoginId);
    }

    private void setLastModifiedByUserLogin(GlFiscalType record, String userLoginId) {
        record.setLastModifiedByUserLogin(userLoginId);
    }

}
