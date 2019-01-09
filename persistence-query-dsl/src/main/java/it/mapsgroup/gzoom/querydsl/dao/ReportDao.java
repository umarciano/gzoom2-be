package it.mapsgroup.gzoom.querydsl.dao;

import com.querydsl.core.Tuple;
import com.querydsl.core.group.GroupBy;
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

import java.util.HashMap;
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
    public ReportDao(SQLQueryFactory queryFactory, SequenceGenerator sequenceGenerator, TransactionTemplate transactionTemplate) {
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
    
    @Transactional
    public List<Report> getReports(String parentTypeId) {
        if (TransactionSynchronizationManager.isActualTransactionActive()) {
            TransactionStatus status = TransactionAspectSupport.currentTransactionStatus();
            status.getClass();
        }
   
        QContent qContent = QContent.content;
        QContentAssoc qContentAssoc = QContentAssoc.contentAssoc;
        QWorkEffortType qWorkEffortType = QWorkEffortType.workEffortType;
        QWorkEffortTypeContent qWorkEffortTypeContent = QWorkEffortTypeContent.workEffortTypeContent;
        
        QBean<Report> reportQBean = bean(Report.class,
                merge(qContent.all(),
                        bean(WorkEffortType.class, qWorkEffortType.all()).as("workEffortType"),
                        bean(WorkEffortTypeContent.class, qWorkEffortTypeContent.all()).as("workEffortTypeContent")
                        ));
        
        
        SQLQuery<Tuple> tupleSQLQuery = queryFactory.select(qContent, qWorkEffortType, qWorkEffortTypeContent)
        				.from(qWorkEffortType)
        				.innerJoin(qWorkEffortTypeContent).on(qWorkEffortType.workEffortTypeId.eq(qWorkEffortTypeContent.workEffortTypeId)) 
        				.innerJoin(qContent).on(qWorkEffortTypeContent.contentId.eq(qContent.contentId)) 
        				.innerJoin(qContentAssoc).on(qContentAssoc.contentIdTo.eq(qContent.contentId).
        						and(qContentAssoc.contentAssocTypeId.eq("REP_PERM")))         				
        				.where(qContentAssoc.contentId.eq("WE_PRINT")
        				.and(qWorkEffortType.parentTypeId.eq(parentTypeId))
        				.and(qWorkEffortTypeContent.isVisible.eq(true)))
                        .orderBy(qWorkEffortTypeContent.sequenceNum.asc());
        
        
        SQLBindings bindings = tupleSQLQuery.getSQL();
        LOG.info("{}", bindings.getSQL());
        LOG.info("{}", bindings.getBindings());
        List<Report> ret = tupleSQLQuery.transform(GroupBy.groupBy(qContent.contentId).list(reportQBean));
        LOG.info("size = {}", ret.size()); 
        return ret;
    }
    
    @Transactional
    public Report getReport(String reportContentId) {
        if (TransactionSynchronizationManager.isActualTransactionActive()) {
            TransactionStatus status = TransactionAspectSupport.currentTransactionStatus();
            status.getClass();
        }
   
        QContent qContent = QContent.content;
        QWorkEffortType qWorkEffortType = QWorkEffortType.workEffortType;
        QWorkEffortTypeContent qWorkEffortTypeContent = QWorkEffortTypeContent.workEffortTypeContent;
        
        QBean<Report> reportQBean = bean(Report.class,
                merge(qContent.all(),
                        bean(WorkEffortType.class, qWorkEffortType.all()).as("workEffortType"),
                        bean(WorkEffortTypeContent.class, qWorkEffortTypeContent.all()).as("workEffortTypeContent")
                        ));
        
        
        SQLQuery<Tuple> tupleSQLQuery = queryFactory.select(qContent, qWorkEffortType, qWorkEffortTypeContent)
        				.from(qWorkEffortType)
        				.innerJoin(qWorkEffortTypeContent).on(qWorkEffortType.workEffortTypeId.eq(qWorkEffortTypeContent.workEffortTypeId)) 
        				.innerJoin(qContent).on(qWorkEffortTypeContent.contentId.eq(qContent.contentId)) 
        				.where(qContent.contentId.eq(reportContentId))
                        .orderBy(qWorkEffortTypeContent.sequenceNum.asc());
        
        
        SQLBindings bindings = tupleSQLQuery.getSQL();
        LOG.info("{}", bindings.getSQL());
        LOG.info("{}", bindings.getBindings());
        List<Report> ret = tupleSQLQuery.transform(GroupBy.groupBy(qContent.contentId).list(reportQBean));
        return ret.isEmpty() ? null : ret.get(0);
    }
    
    @Transactional
    public List<ReportType> getReportType(String reportContentId) {
        if (TransactionSynchronizationManager.isActualTransactionActive()) {
            TransactionStatus status = TransactionAspectSupport.currentTransactionStatus();
            status.getClass();
        }
   
        QContentAssoc qContentAssoc = QContentAssoc.contentAssoc;
        QContent qContent = QContent.content;
        
        QBean<ReportType> reportQBean = bean(ReportType.class, merge(qContent.all()));        
        
        SQLQuery<Content> tupleSQLQuery = queryFactory.select(qContent)
        				.from(qContentAssoc)
        				.innerJoin(qContent).on(qContent.contentId.eq(qContentAssoc.contentId))
        				.where(qContentAssoc.contentIdTo.eq(reportContentId)
        				.and(qContentAssoc.contentAssocTypeId.eq("TYPE_PRINT")))
                        .orderBy(qContentAssoc.sequenceNum.asc());
        
        
        SQLBindings bindings = tupleSQLQuery.getSQL();
        LOG.info("{}", bindings.getSQL());
        LOG.info("{}", bindings.getBindings());
        List<ReportType> ret = tupleSQLQuery.transform(GroupBy.groupBy(qContentAssoc.contentId).list(reportQBean));
        LOG.info("size = {}", ret.size()); 
        return ret;
    }
    
    
    
    
    @Transactional
    public List<Report> getAnalysisReports(String parentTypeId) {
        if (TransactionSynchronizationManager.isActualTransactionActive()) {
            TransactionStatus status = TransactionAspectSupport.currentTransactionStatus();
            status.getClass();
        }
   
        QContent qContent = QContent.content;
        QWorkEffortType qWorkEffortType = QWorkEffortType.workEffortType;
        QWorkEffortAnalysis qWorkEffortAnalysis = QWorkEffortAnalysis.workEffortAnalysis;
        
        QBean<Report> reportQBean = bean(Report.class,
                merge(qContent.all(),
                        bean(WorkEffortType.class, qWorkEffortType.all()).as("workEffortType"),
                        bean(WorkEffortAnalysis.class, qWorkEffortAnalysis.all()).as("workEffortAnalysis")
                        ));        
        
        SQLQuery<Tuple> tupleSQLQuery = queryFactory.select(qContent, qWorkEffortType, qWorkEffortAnalysis)
        				.from(qWorkEffortType)
        				.innerJoin(qWorkEffortAnalysis).on(qWorkEffortType.workEffortTypeId.eq(qWorkEffortAnalysis.workEffortTypeId)) 
        				.innerJoin(qContent).on(qWorkEffortAnalysis.reportId.eq(qContent.contentId)) 
        				.where(qWorkEffortType.parentTypeId.eq(parentTypeId));
                      //  .orderBy(qWorkEffortTypeContent.sequenceNum.asc());
        
        
        SQLBindings bindings = tupleSQLQuery.getSQL();
        LOG.info("{}", bindings.getSQL());
        LOG.info("{}", bindings.getBindings());
        List<Report> ret = tupleSQLQuery.transform(GroupBy.groupBy(qContent.contentId).list(reportQBean));
        LOG.info("size = {}", ret.size()); 
        return ret;
    }
    
    
    //TODO devo fare la query in base al tipo di dati che debbo estrarre:
    //1. tutti gli elementi,
    //2. tutti glie elementi con il workEffortType scelto
    //3. per entrambe le scelte superiori -> scegliere se il filtro utente è attivo
    
    @Transactional
    public List<WorkEffort> getWorkEfforts(String workEffortTypeId) {
        if (TransactionSynchronizationManager.isActualTransactionActive()) {
            TransactionStatus status = TransactionAspectSupport.currentTransactionStatus();
            status.getClass();
        }
   
        QWorkEffort qWorkEffort = QWorkEffort.workEffort;
        
        SQLQuery<WorkEffort> tupleSQLQuery = queryFactory.select(qWorkEffort)
        				.from(qWorkEffort)
        				.where(qWorkEffort.workEffortTypeId.eq(workEffortTypeId));
                      //  .orderBy(qWorkEffortTypeContent.sequenceNum.asc());
        
        
        SQLBindings bindings = tupleSQLQuery.getSQL();
        LOG.info("{}", bindings.getSQL());
        LOG.info("{}", bindings.getBindings());
        QBean<WorkEffort> workEffortQBean = Projections.bean(WorkEffort.class, qWorkEffort.all());
        List<WorkEffort> ret = tupleSQLQuery.transform(GroupBy.groupBy(qWorkEffort.workEffortId).list(workEffortQBean));
        LOG.info("size = {}", ret.size()); 
        return ret;
    }
    
    
 }
