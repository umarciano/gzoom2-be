package it.mapsgroup.gzoom.querydsl.dao;

import com.querydsl.sql.SQLQueryFactory;
import it.mapsgroup.gzoom.persistence.common.SequenceGenerator;
import it.mapsgroup.gzoom.querydsl.dto.*;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.math.BigDecimal;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

@Service
public class GlAccountInterfaceDao extends AbstractDao{

    private static final Logger LOG = getLogger(UomDao.class);

    private final SQLQueryFactory queryFactory;
    private final SequenceGenerator sequenceGenerator;

    public GlAccountInterfaceDao(SQLQueryFactory queryFactory, SequenceGenerator sequenceGenerator) {
        this.queryFactory = queryFactory;
        this.sequenceGenerator = sequenceGenerator;
    }

    @Transactional
    public boolean createEx(GlAccountInterfaceExt record, String userLoginId) {
        QGlAccountInterfaceExt gl = QGlAccountInterfaceExt.glAccountInterfaceExt;
        String id = sequenceGenerator.getNextSeqId("GlAccountInterfaceExt");
        record.setId(id);
        long i = queryFactory.insert(gl).populate(record).execute();
        LOG.info("created records: {}", i);
        return i > 0;
    }
}
