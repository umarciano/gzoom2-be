package it.mapsgroup.gzoom.querydsl.dao;

import com.querydsl.sql.SQLQueryFactory;
import it.mapsgroup.gzoom.persistence.common.SequenceGenerator;
import it.mapsgroup.gzoom.querydsl.dto.*;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.slf4j.LoggerFactory.getLogger;

@Service
public class JobLogDao extends AbstractDao {

    private static final Logger LOG = getLogger(JobLogDao.class);
    private final SQLQueryFactory queryFactory;
    private final SequenceGenerator sequenceGenerator;

    @Autowired
    public JobLogDao(SQLQueryFactory queryFactory, SequenceGenerator sequenceGenerator) {
        this.queryFactory = queryFactory;
        this.sequenceGenerator = sequenceGenerator;
    }

    @Transactional
    public boolean addJobLog(JobLog record, String username) {

        setCreatedTimestamp(record);
        record.setUserLoginId(username);
        record.setCreatedByUserLogin(username);

        LOG.info("Add JobLog: {}", record);

        QJobLog qJobLog = QJobLog.jobLog;

        long i = queryFactory.insert(qJobLog).populate(record).execute();
        LOG.debug("created records: {}", i);

        return i > 0;
    }

    @Transactional
    public boolean updateJobLogEndDate(JobLog record) {

        setUpdateTimestamp(record);

        LOG.info("Update LongEndDate JobLog: {}", record);

        QJobLog qJobLog = QJobLog.jobLog;

        long i = queryFactory.update(qJobLog).set(qJobLog.logEndDate, record.getLogEndDate())
                .where(qJobLog.jobLogId.eq(record.getJobLogId())).populate(record).execute();

        LOG.info("updated records: {}", i);

        return i > 0;

    }

    @Transactional
    public boolean addJobLogLog(JobLogLog record) {
        record.setJobLogLogId(sequenceGenerator.getNextSeqId("JobLogLog"));
        setCreatedTimestamp(record);

        LOG.info("Add JobLogLog: {}", record);

        QJobLogLog qJobLogLog = QJobLogLog.jobLogLog;

        long i = queryFactory.insert(qJobLogLog).populate(record).execute();
        LOG.debug("created records: {}", i);

        return i > 0;
    }


    @Transactional
    public boolean addJobLogJobExecParams(JobLogJobExecParams record, String username) {

        record.setCreatedByUserLogin(username);
        setCreatedTimestamp(record);

        LOG.info("Add JobLogJobExecParams: {}", record);

        QJobLogJobExecParams qJobLogJobExecParams = QJobLogJobExecParams.jobLogJobExecParams;

        long i = queryFactory.insert(qJobLogJobExecParams).populate(record).execute();
        LOG.debug("created records: {}", i);

        return i > 0;
    }

    @Transactional
    public boolean addJobLogServiceType(JobLogServiceType record) {

        setCreatedTimestamp(record);

        LOG.info("Add JobLogServiceType: {}", record);

        QJobLogServiceType qJobLogServiceType = QJobLogServiceType.jobLogServiceType;

        long i = queryFactory.insert(qJobLogServiceType).populate(record).execute();
        LOG.debug("created records: {}", i);

        return i > 0;
    }


}
