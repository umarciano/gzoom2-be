package it.mapsgroup.gzoom.report.querydsl.dao;

import com.querydsl.sql.SQLBindings;
import com.querydsl.sql.SQLQuery;
import com.querydsl.sql.SQLQueryFactory;
import it.mapsgroup.gzoom.persistence.common.SequenceGenerator;
import it.mapsgroup.gzoom.persistence.common.dto.enumeration.ReportActivityStatus;
import it.mapsgroup.report.querydsl.dto.QReportActivity;
import it.mapsgroup.report.querydsl.dto.ReportActivity;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author Andrea Fossi.
 */
@Service
public class ReportActivityDao extends AbstractDao {
    private static final Logger LOG = getLogger(ReportActivityDao.class);

    private final SequenceGenerator sequenceGenerator;
    private SQLQueryFactory queryFactory;

    @Autowired
    public ReportActivityDao(SequenceGenerator sequenceGenerator, SQLQueryFactory queryFactory) {
        this.sequenceGenerator = sequenceGenerator;
        this.queryFactory = queryFactory;
    }

    @Transactional
    public boolean create(ReportActivity record) {
        QReportActivity reportActivity = QReportActivity.reportActivity;
        String id = sequenceGenerator.getNextSeqId(ReportActivity.class.getSimpleName());
        LOG.debug("ReportActivity[{}]", id);
        record.setActivityId(id);
        setCreatedTimestamp(record);
        long i = queryFactory.insert(reportActivity).populate(record).execute();
        LOG.debug("created records: {}", i);
        return i > 0;
    }

    @Transactional
    public ReportActivity get(String id) {
        QReportActivity qReportActivity = QReportActivity.reportActivity;
        return queryFactory.select(qReportActivity)
                .from(qReportActivity)
                .where(qReportActivity.activityId.eq(id))
                .fetchFirst();
    }

    @Transactional
    public Boolean updateState(String id,
                               ReportActivityStatus src,
                               ReportActivityStatus dest) {
        QReportActivity qReportActivity = QReportActivity.reportActivity;
        long result = queryFactory.update(qReportActivity)
                .set(qReportActivity.status, dest)
                .where(qReportActivity.activityId.eq(id)
                        .and(qReportActivity.status.eq(src)))
                .execute();
        return result > 0;

    }


    public List<ReportActivity> getActvities(ReportActivityStatus status) {
        QReportActivity qReportActivity = QReportActivity.reportActivity;
        SQLQuery<ReportActivity> pSQLQuery = queryFactory.select(qReportActivity).from(qReportActivity).orderBy(qReportActivity.activityId.asc());
        if (status != null)
            pSQLQuery.where(qReportActivity.status.eq(status));
        SQLBindings bindings = pSQLQuery.getSQL();
        LOG.info("{}", bindings.getSQL());
        LOG.info("{}", bindings.getBindings());
        //QBean<ReportActivity> partys = Projections.bean(ReportActivity.class, qReportActivity.all());
        //List<ReportActivity> ret = pSQLQuery.transform(GroupBy.groupBy(qReportActivity.activityId).list(partys));
        List<ReportActivity> ret = pSQLQuery.fetch();
        LOG.info("size = {}", ret.size());
        return ret;
    }
}
