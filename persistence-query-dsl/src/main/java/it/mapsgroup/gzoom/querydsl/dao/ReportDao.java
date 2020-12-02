package it.mapsgroup.gzoom.querydsl.dao;

import com.querydsl.core.Tuple;
import com.querydsl.core.group.GroupBy;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.QBean;
import com.querydsl.sql.SQLBindings;
import com.querydsl.sql.SQLQuery;
import com.querydsl.sql.SQLQueryFactory;

import it.mapsgroup.gzoom.querydsl.dto.*;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import static com.querydsl.core.types.Projections.bean;
import static it.mapsgroup.gzoom.querydsl.QBeanUtils.merge;

import static org.slf4j.LoggerFactory.getLogger;

import java.util.List;

/**
 */
@Service
public class ReportDao extends AbstractDao {
    private static final Logger LOG = getLogger(ReportDao.class);

    private final SQLQueryFactory queryFactory;
    
    @Autowired
    public ReportDao(SQLQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
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
        LOG.info("{}", bindings.getNullFriendlyBindings());
        List<ReportType> ret = tupleSQLQuery.transform(GroupBy.groupBy(qContent.contentId).list(reportTypeQBean));
        LOG.info("size = {}", ret.size());
        return ret;
    }
    
    /**
     * Prendo la lista dei report per quel modulo
     * @param parentTypeId
     * @return
     */
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
        QDataResource qDataResource = QDataResource.dataResource;

        QBean<Report> reportQBean = bean(Report.class,
                merge(qContent.all(),
                        bean(WorkEffortType.class, qWorkEffortType.all()).as("workEffortType"),
                        bean(WorkEffortTypeContent.class, qWorkEffortTypeContent.all()).as("workEffortTypeContent"),
                        bean(DataResource.class, qDataResource.all()).as("dataResource")
                        ));
        
        
        SQLQuery<Tuple> tupleSQLQuery = queryFactory.select(qContent, qWorkEffortType, qWorkEffortTypeContent, qDataResource)
        				.from(qWorkEffortType)
        				.innerJoin(qWorkEffortTypeContent).on(qWorkEffortType.workEffortTypeId.eq(qWorkEffortTypeContent.workEffortTypeId)) 
        				.innerJoin(qContent).on(qWorkEffortTypeContent.contentId.eq(qContent.contentId)) 
        				.innerJoin(qContentAssoc).on(qContentAssoc.contentIdTo.eq(qContent.contentId).
        						and(qContentAssoc.contentAssocTypeId.eq("REP_PERM")))
                        .innerJoin(qDataResource).on(qDataResource.dataResourceId.eq(qContent.dataResourceId))
        				.where(qContentAssoc.contentId.eq("WE_PRINT")
        				.and(qWorkEffortType.parentTypeId.eq(parentTypeId))
        				.and(qWorkEffortTypeContent.isVisible.eq(true)))
                        .orderBy(qWorkEffortTypeContent.sequenceNum.asc());
                      //  .groupBy(qContent.contentId, qWorkEffortTypeContent.etch, qContent.description);
        
        
        SQLBindings bindings = tupleSQLQuery.getSQL();
        LOG.info("{}", bindings.getSQL());
        LOG.info("{}", bindings.getNullFriendlyBindings());
        List<Report> ret = tupleSQLQuery.transform(GroupBy.groupBy(qContent.contentId, qWorkEffortTypeContent.etch, qContent.description).list(reportQBean));
        LOG.info("size = {}", ret.size()); 
        return ret;
    }
    
    /**
     * TODO aggiunege la lista di condizione per etichette
     * Seleziono il singolo report 
     * @param reportContentId
     * @return
     */
    @Transactional
    public Report getReport(String parentTypeId, String reportContentId, String resourceName, String workEffortTypeId) {
        if (TransactionSynchronizationManager.isActualTransactionActive()) {
            TransactionStatus status = TransactionAspectSupport.currentTransactionStatus();
            status.getClass();
        }
   
        QContent qContent = QContent.content;
        QWorkEffortType qWorkEffortType = QWorkEffortType.workEffortType;
        QWorkEffortTypeContent qWorkEffortTypeContent = QWorkEffortTypeContent.workEffortTypeContent;
        QDataResource qDataResource = QDataResource.dataResource;

        QBean<Report> reportQBean = bean(Report.class,
                merge(qContent.all(),
                        bean(WorkEffortType.class, qWorkEffortType.all()).as("workEffortType"),
                        bean(WorkEffortTypeContent.class, qWorkEffortTypeContent.all()).as("workEffortTypeContent"),
                        bean(DataResource.class, qDataResource.all()).as("dataResource")
                        ));
        
        
        SQLQuery<Tuple> tupleSQLQuery = queryFactory.select(qContent, qWorkEffortType, qWorkEffortTypeContent)
        				.from(qWorkEffortType)
        				.innerJoin(qWorkEffortTypeContent).on(qWorkEffortType.workEffortTypeId.eq(qWorkEffortTypeContent.workEffortTypeId)) 
        				.innerJoin(qContent).on(qWorkEffortTypeContent.contentId.eq(qContent.contentId))
                        .innerJoin(qDataResource).on(qDataResource.dataResourceId.eq(qContent.dataResourceId))
        				.where(qContent.contentId.eq(reportContentId)
        						.and(qWorkEffortType.parentTypeId.eq(parentTypeId))
                                .and(workEffortTypeId!=null && !workEffortTypeId.equals("")? qWorkEffortTypeContent.workEffortTypeId.eq(workEffortTypeId): qWorkEffortTypeContent.workEffortTypeId.isNotNull()))
                        .orderBy(qWorkEffortTypeContent.sequenceNum.asc());
        
        
        SQLBindings bindings = tupleSQLQuery.getSQL();
        LOG.info("{}", bindings.getSQL());
        LOG.info("{}", bindings.getNullFriendlyBindings());
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
        LOG.info("{}", bindings.getNullFriendlyBindings());
        List<ReportType> ret = tupleSQLQuery.transform(GroupBy.groupBy(qContentAssoc.contentId).list(reportQBean));
        LOG.info("size = {}", ret.size()); 
        return ret;
    }
    
    
    
    /**
     * Prendo la lista dei report collegati all'analisi
     * @param parentTypeId
     * @return
     */
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
        				//.groupBy(qContent.contentId, qWorkEffortAnalysis.description5, qContent.description);
                      //  .orderBy(qWorkEffortTypeContent.sequenceNum.asc());        
        	
        SQLBindings bindings = tupleSQLQuery.getSQL();
        LOG.info("{}", bindings.getSQL());
        LOG.info("{}", bindings.getNullFriendlyBindings());
        List<Report> ret = tupleSQLQuery.transform(GroupBy.groupBy(qContent.contentId, qWorkEffortAnalysis.description5, qContent.description).list(reportQBean));
        LOG.info("size = {}", ret.size()); 
        return ret;
    } 
    
    /**
     * Mostra la lista dei typr TODO manca reportName da cercare
     * @param parentTypeId
     * @return
     */
    @Transactional
    public List<WorkEffortTypeExt> getAnalysisWorkEffortTypeContents(String parentTypeId, String reportContentId, String reportName) {
        if (TransactionSynchronizationManager.isActualTransactionActive()) {
            TransactionStatus status = TransactionAspectSupport.currentTransactionStatus();
            status.getClass();
        }
   
        QContent qContent = QContent.content;
        QWorkEffortType qWorkEffortType = QWorkEffortType.workEffortType;
        QWorkEffortAnalysis qWorkEffortAnalysis = QWorkEffortAnalysis.workEffortAnalysis;
        
        QBean<WorkEffortTypeExt> tupleExQBean = bean(WorkEffortTypeExt.class, 
        		merge(qWorkEffortType.all(), 
        				bean(WorkEffortAnalysis.class, qWorkEffortAnalysis.all()).as("workEffortAnalysis")));
        
        SQLQuery<Tuple> tupleSQLQuery = queryFactory.select(qContent, qWorkEffortType, qWorkEffortAnalysis)
        				.from(qWorkEffortType)
        				.innerJoin(qWorkEffortAnalysis).on(qWorkEffortType.workEffortTypeId.eq(qWorkEffortAnalysis.workEffortTypeId)) 
        				.innerJoin(qContent).on(qWorkEffortAnalysis.reportId.eq(qContent.contentId)) 
        				.where(qWorkEffortType.parentTypeId.eq(parentTypeId)
        						.and(qContent.contentId.eq(reportContentId))
        						.and(qWorkEffortAnalysis.description5.eq(reportName).or(qContent.description.eq(reportName).and(qWorkEffortAnalysis.description5.isNull()))));
                      //  .orderBy(qWorkEffortTypeContent.sequenceNum.asc());        
        	
        SQLBindings bindings = tupleSQLQuery.getSQL();
        LOG.info("{}", bindings.getSQL());
        LOG.info("{}", bindings.getNullFriendlyBindings());
        List<WorkEffortTypeExt> ret = tupleSQLQuery.transform(GroupBy.groupBy(qWorkEffortAnalysis.description).list(tupleExQBean));
        LOG.info("size = {}", ret.size()); 
        return ret;
    }  
    
    
    /**
     * TODO aggiunege la lista di condizione per etichette
     * Prendo la lista dei report collegati all'analisi
     * @param parentTypeId
     * @return
     */
    @Transactional
    public Report getAnalysisReport(String parentTypeId, String reportContentId, String reportName) {
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
        				.where(qWorkEffortType.parentTypeId.eq(parentTypeId)
        						.and(qContent.contentId.eq(reportContentId)));
                      //  .orderBy(qWorkEffortTypeContent.sequenceNum.asc());        
        	
        SQLBindings bindings = tupleSQLQuery.getSQL();
        LOG.info("{}", bindings.getSQL());
        LOG.info("{}", bindings.getNullFriendlyBindings());
        List<Report> ret = tupleSQLQuery.transform(GroupBy.groupBy(qContent.contentId).list(reportQBean));        
        return ret.isEmpty() ? null : ret.get(0);
    }  
   
    @Transactional
    public List<WorkEffortAssoc> getChildRootEquality(String workEffortId) {
        if (TransactionSynchronizationManager.isActualTransactionActive()) {
            TransactionStatus status = TransactionAspectSupport.currentTransactionStatus();
            status.getClass();
        }
   
        QWorkEffortAssoc qWorkEffortAssoc = QWorkEffortAssoc.workEffortAssoc;
        QWorkEffort qWETO = QWorkEffort.workEffort;
        QWorkEffort qWEFROM = QWorkEffort.workEffort;
        QWorkEffortType qWETTO = QWorkEffortType.workEffortType;
        QWorkEffortType qWETFROM = QWorkEffortType.workEffortType;        
	
        
        SQLQuery<WorkEffortAssoc> tupleSQLQuery = queryFactory.select(qWorkEffortAssoc)
        				.from(qWorkEffortAssoc)
        				.innerJoin(qWETO).on(qWETO.workEffortId.eq(qWorkEffortAssoc.workEffortIdTo))
        				.innerJoin(qWETTO).on(qWETTO.workEffortTypeId.eq(qWETO.workEffortTypeId))
        				.innerJoin(qWEFROM).on(qWEFROM.workEffortId.eq(qWorkEffortAssoc.workEffortIdFrom))
        				.innerJoin(qWETFROM).on(qWETFROM.workEffortTypeId.eq(qWEFROM.workEffortTypeId))
        				.where(qWETFROM.parentTypeId.eq(qWETTO.parentTypeId)
        						.and(qWorkEffortAssoc.workEffortIdTo.eq(workEffortId))
        						.and(qWorkEffortAssoc.workEffortAssocTypeId.ne("SNAPSHOT"))
        						.and(qWorkEffortAssoc.workEffortAssocTypeId.ne("COPY"))
        						.and(qWorkEffortAssoc.workEffortAssocTypeId.ne("TEMPL")));
        				
                      //  .orderBy(qWorkEffortTypeContent.sequenceNum.asc());
        
        
        SQLBindings bindings = tupleSQLQuery.getSQL();
        LOG.info("{}", bindings.getSQL());
        LOG.info("{}", bindings.getNullFriendlyBindings());
        QBean<WorkEffortAssoc> workEffortAssocQBean = Projections.bean(WorkEffortAssoc.class, qWorkEffortAssoc.all());
        List<WorkEffortAssoc> ret = tupleSQLQuery.transform(GroupBy.groupBy(qWorkEffortAssoc.workEffortIdTo, qWorkEffortAssoc.workEffortIdFrom,
        		qWorkEffortAssoc.workEffortAssocTypeId, qWorkEffortAssoc.fromDate).list(workEffortAssocQBean));
        LOG.info("size = {}", ret.size()); 
        return ret;
    }
    
 }
