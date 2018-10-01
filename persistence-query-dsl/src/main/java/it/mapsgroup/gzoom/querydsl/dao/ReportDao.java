package it.mapsgroup.gzoom.querydsl.dao;

import com.querydsl.core.Tuple;
import com.querydsl.core.group.GroupBy;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.QBean;
import com.querydsl.sql.SQLBindings;
import com.querydsl.sql.SQLQuery;
import com.querydsl.sql.SQLQueryFactory;

import it.mapsgroup.gzoom.persistence.common.SequenceGenerator;
import it.mapsgroup.gzoom.querydsl.dto.*;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.transaction.support.TransactionTemplate;

import static com.querydsl.core.types.Projections.bean;
import static it.mapsgroup.gzoom.querydsl.QBeanUtils.merge;

import static org.slf4j.LoggerFactory.getLogger;

import java.util.ArrayList;
import java.util.List;

/**
 */
@Service
public class ReportDao extends AbstractDao {
    private static final Logger LOG = getLogger(ReportDao.class);

    private final SQLQueryFactory queryFactory;
    private final SequenceGenerator sequenceGenerator;
    private final TransactionTemplate transactionTemplate;
    
    @Autowired
    public ReportDao(SQLQueryFactory queryFactory, SequenceGenerator sequenceGenerator,
                        TransactionTemplate transactionTemplate) {
        this.queryFactory = queryFactory;
        this.sequenceGenerator = sequenceGenerator;
        this.transactionTemplate = transactionTemplate;
    }

    @Transactional
    public List<ReportType> getReportTypes(String contentId) {
        if (TransactionSynchronizationManager.isActualTransactionActive()) {
            TransactionStatus status = TransactionAspectSupport.currentTransactionStatus();
            status.getClass();
        }
               
        QContent qContent = QContent.content;
        QContentAssoc qContentAssoc = QContentAssoc.contentAssoc;
        
        QBean<ReportType> reportTypeQBean = bean(ReportType.class, merge(qContent.all(), bean(ContentAttribute.class, qContentAssoc.all()).as("ass")));
        SQLQuery<Tuple> tupleSQLQuery = queryFactory.select(qContent, qContentAssoc)
        				.from(qContent)
        				.innerJoin(qContentAssoc).on(qContent.contentId.eq(qContentAssoc.contentId)) 
        				.where(qContent.contentTypeId.eq("TYPE_PRINT")
        				.and(qContentAssoc.contentIdTo.eq(contentId))
        				.and(qContentAssoc.contentAssocTypeId.eq("TYPE_PRINT")))
                        .orderBy(qContentAssoc.sequenceNum.asc());
        
        
        SQLBindings bindings = tupleSQLQuery.getSQL();
        LOG.info("{}", bindings.getSQL());
        LOG.info("{}", bindings.getBindings());
        List<ReportType> ret = tupleSQLQuery.transform(GroupBy.groupBy(qContent.contentId).list(reportTypeQBean));
        LOG.info("size = {}", ret.size());
        return ret;
    }
 }
