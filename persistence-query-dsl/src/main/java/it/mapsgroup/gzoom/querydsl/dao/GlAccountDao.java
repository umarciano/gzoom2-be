package it.mapsgroup.gzoom.querydsl.dao;

import com.querydsl.sql.SQLQueryFactory;
import it.mapsgroup.gzoom.querydsl.dto.QGlAccount;
import it.mapsgroup.gzoom.querydsl.dto.QUom;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.math.BigInteger;

import static org.slf4j.LoggerFactory.getLogger;

@Service
public class GlAccountDao extends AbstractDao {

    private static final Logger LOG = getLogger(GlAccountDao.class);
    private final SQLQueryFactory queryFactory;

    public GlAccountDao(SQLQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }


    /**
     * This function gets decimal precision.
     *
     * @return BigInteger.
     */
    @Transactional
    public BigInteger getDecimalPrecision() {
        if (TransactionSynchronizationManager.isActualTransactionActive()) {
            TransactionStatus status = TransactionAspectSupport.currentTransactionStatus();
            status.getClass();
        }

        QGlAccount qGL = QGlAccount.glAccount;
        QUom qUOM = QUom.uom;

        BigInteger tupleSQLQuery = queryFactory.select(qUOM.decimalScale)
                .from(qGL)
                .innerJoin(qUOM).on(qUOM.uomId.eq(qGL.defaultUomId))
                .where(qGL.glAccountId.eq("SCORE")).fetchOne();

        return tupleSQLQuery;
    }
}
