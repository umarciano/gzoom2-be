package it.mapsgroup.gzoom.querydsl.dao;

import com.querydsl.core.group.GroupBy;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.QBean;
import com.querydsl.sql.SQLBindings;
import com.querydsl.sql.SQLQuery;
import com.querydsl.sql.SQLQueryFactory;
import it.mapsgroup.gzoom.persistence.common.SequenceGenerator;
import it.mapsgroup.gzoom.querydsl.dto.*;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * AcctgTransInterfaceDao
 */
@Service
public class AcctgTransInterfaceDao extends AbstractDao {
    private static final Logger LOG = getLogger(UomDao.class);

    private final SQLQueryFactory queryFactory;
    private final SequenceGenerator sequenceGenerator;

    public AcctgTransInterfaceDao(SQLQueryFactory queryFactory, SequenceGenerator sequenceGenerator) {
        this.queryFactory = queryFactory;
        this.sequenceGenerator = sequenceGenerator;
    }

    @Transactional
    public List<AcctgTransInterface> getAll() {
        if (TransactionSynchronizationManager.isActualTransactionActive()) {
            TransactionStatus status = TransactionAspectSupport.currentTransactionStatus();
            status.getClass();
        }

        QAcctgTransInterface qAcctgTransInterface = QAcctgTransInterface.acctgTransInterface;
        SQLQuery<AcctgTransInterface> pSQLQuery = queryFactory.select(qAcctgTransInterface).from(qAcctgTransInterface);
        SQLBindings bindings = pSQLQuery.getSQL();
        LOG.info("{}", bindings.getSQL());
        LOG.info("{}", bindings.getNullFriendlyBindings());

        QBean<AcctgTransInterface> accTrans = Projections.bean(AcctgTransInterface.class, qAcctgTransInterface.all());
        List<AcctgTransInterface> ret = pSQLQuery.fetch();
        LOG.info("size = {}", ret.size());
        return ret;

    }

    /**
     * Create record
     * @param record
     * @param userLoginId
     * @return
     */
    @Transactional
    public boolean create(AcctgTransInterface record, String userLoginId) {
        QAcctgTransInterfaceExt acc = QAcctgTransInterfaceExt.acctgTransInterfaceExt;
        String id = sequenceGenerator.getNextSeqId("AcctgTransInterface");
        record.setId(id);
        long i = queryFactory.insert(acc).populate(record).execute();
        LOG.info("created records: {}", i);
        return i > 0;
    }

    /**
     * Create record for External table
     * @param record
     * @param userLoginId
     * @return
     */
    @Transactional
    public boolean createExt(AcctgTransInterfaceExt record, String userLoginId) {
        QAcctgTransInterfaceExt acc = QAcctgTransInterfaceExt.acctgTransInterfaceExt;
        String id = sequenceGenerator.getNextSeqId("AcctgTransInterfaceExt");
        record.setId(id);
        long i = queryFactory.insert(acc).populate(record).execute();
        LOG.info("created records: {}", i);
        return i > 0;
    }
}
