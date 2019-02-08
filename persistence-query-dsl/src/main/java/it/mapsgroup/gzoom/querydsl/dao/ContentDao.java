package it.mapsgroup.gzoom.querydsl.dao;

import com.querydsl.core.group.GroupBy;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.QBean;
import com.querydsl.sql.SQLBindings;
import com.querydsl.sql.SQLQuery;
import com.querydsl.sql.SQLQueryFactory;
import it.mapsgroup.gzoom.persistence.common.SequenceGenerator;
import it.mapsgroup.gzoom.querydsl.dto.Content;
import it.mapsgroup.gzoom.querydsl.dto.QContent;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import static org.slf4j.LoggerFactory.getLogger;

import java.util.List;

/**
 * @author Andrea Fossi.
 */
@Service
public class ContentDao extends AbstractDao {
    private static final Logger LOG = getLogger(ContentDao.class);

    private final SequenceGenerator sequenceGenerator;
    private SQLQueryFactory queryFactory;

    @Autowired
    public ContentDao(SequenceGenerator sequenceGenerator, SQLQueryFactory queryFactory) {
        this.sequenceGenerator = sequenceGenerator;
        this.queryFactory = queryFactory;
    }





    @Transactional
    public boolean create(Content record) {
        QContent Content = QContent.content;
        String id = sequenceGenerator.getNextSeqId("Content");
        LOG.debug("PartyId[{}]", id);
        record.setContentId(id);
        setCreatedTimestamp(record);
        record.setCreatedDate(record.getCreatedStamp());
        record.setLastModifiedDate(record.getLastModifiedDate());
        long i = queryFactory.insert(Content).populate(record).execute();
        LOG.debug("created records: {}", i);
        return i > 0;
    }



    
    @Transactional
    public Content getContentByActivityId(String activityId) {
        if (TransactionSynchronizationManager.isActualTransactionActive()) {
            TransactionStatus status = TransactionAspectSupport.currentTransactionStatus();
            status.getClass();
        }

        QContent qContent = QContent.content;

        SQLQuery<Content> tupleSQLQuery = queryFactory.select(qContent).from(qContent).where(qContent.serviceName.eq(activityId));

        SQLBindings bindings = tupleSQLQuery.getSQL();
        LOG.info("{}", bindings.getSQL());
        LOG.info("{}", bindings.getBindings());
        QBean<Content> list = Projections.bean(Content.class, qContent.all());
        List<Content> ret = tupleSQLQuery.transform(GroupBy.groupBy(qContent.contentId).list(list));
        return ret.isEmpty() ? null : ret.get(0);
    }
}
