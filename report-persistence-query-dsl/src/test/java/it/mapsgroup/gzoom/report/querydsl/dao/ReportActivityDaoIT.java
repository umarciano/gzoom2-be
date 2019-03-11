package it.mapsgroup.gzoom.report.querydsl.dao;

import it.mapsgroup.gzoom.persistence.common.dto.enumeration.ReportActivityStatus;
import it.mapsgroup.report.querydsl.dto.ReportActivity;
import org.junit.Test;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import java.time.LocalDateTime;

import static org.slf4j.LoggerFactory.getLogger;

public class ReportActivityDaoIT extends AbstractReportDaoIT {
    private static final Logger LOG = getLogger(ReportActivityDaoIT.class);

    @Autowired
    TransactionTemplate transactionTemplate;

    @Autowired
    PlatformTransactionManager txManager;

   // @Autowired
    //ReportActivityDao activityDao;

    @Test
    @Transactional
    public void daoInsert() throws Exception {
       /* transactionTemplate.execute(txStatus -> {
            ReportActivity record = new ReportActivity();
            record.setReportData("Primo ReportActivity " + System.currentTimeMillis());
            record.setStatus(ReportActivityStatus.QUEUED);
            record.setTemplateName("foo");
            record.setReportName("test");
            record.setCompletedStamp(LocalDateTime.now());
            activityDao.create(record);
            LOG.debug("i" + record.getActivityId());

            activityDao.getActivities(new ReportActvityFilter());
            return null;
        });*/

    }

}
