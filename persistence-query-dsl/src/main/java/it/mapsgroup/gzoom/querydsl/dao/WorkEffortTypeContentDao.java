package it.mapsgroup.gzoom.querydsl.dao;

import static com.querydsl.core.types.Projections.bean;
import static it.mapsgroup.gzoom.querydsl.QBeanUtils.merge;
import static org.slf4j.LoggerFactory.getLogger;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.querydsl.core.types.Projections;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import com.querydsl.core.Tuple;
import com.querydsl.core.group.GroupBy;
import com.querydsl.core.types.QBean;
import com.querydsl.sql.SQLBindings;
import com.querydsl.sql.SQLQuery;
import com.querydsl.sql.SQLQueryFactory;

import it.mapsgroup.gzoom.querydsl.dto.QContent;
import it.mapsgroup.gzoom.querydsl.dto.QWorkEffortType;
import it.mapsgroup.gzoom.querydsl.dto.QWorkEffortTypeContent;
import it.mapsgroup.gzoom.querydsl.dto.WorkEffortTypeContent;
import it.mapsgroup.gzoom.querydsl.dto.WorkEffortTypeExt;

@Service
public class WorkEffortTypeContentDao extends AbstractDao {
	    
	    private static final Logger LOG = getLogger(WorkEffortTypeContentDao.class);

	    private final SQLQueryFactory queryFactory;

	    
	    @Autowired
	    public WorkEffortTypeContentDao(SQLQueryFactory queryFactory) {
	        this.queryFactory = queryFactory;

	    }

	    /**
	     * prendo la lista dei tipi selezionato un report 
	     * @param parentTypeId
	     * @param reportContentId
	     * @param reportName
	     * @return
	     */
	    
	    @Transactional
	    public List<WorkEffortTypeExt> getWorkEffortTypeContents(String parentTypeId, String reportContentId, String reportName, String workEffortTypeId) {
	        if (TransactionSynchronizationManager.isActualTransactionActive()) {
	            TransactionStatus status = TransactionAspectSupport.currentTransactionStatus();
	            status.getClass();
	        }

	        QWorkEffortTypeContent qWorkEffortTypeContent = QWorkEffortTypeContent.workEffortTypeContent;
	        QWorkEffortType qWorkEffortType = QWorkEffortType.workEffortType;
	        QContent qContent = QContent.content;

	        QBean<WorkEffortTypeExt> tupleExQBean = bean(WorkEffortTypeExt.class, merge(qWorkEffortType.all(), bean(WorkEffortTypeContent.class, qWorkEffortTypeContent.all()).as("workEffortTypeContent")));
	        
	        
	        SQLQuery<Tuple> tupleSQLQuery = queryFactory.select(qWorkEffortTypeContent, qWorkEffortType)
	        					.from(qWorkEffortTypeContent)
	        					.innerJoin(qWorkEffortType).on(qWorkEffortType.workEffortTypeId.eq(qWorkEffortTypeContent.workEffortTypeId)) 
	        					.innerJoin(qContent).on(qContent.contentId.eq(qWorkEffortTypeContent.contentId))
								.where((qWorkEffortTypeContent.weTypeContentTypeId.eq("REPORT").or(qWorkEffortTypeContent.weTypeContentTypeId.eq("JREPORT")))
										.and(qWorkEffortTypeContent.isVisible.isTrue())
										.and(workEffortTypeId!=null && !workEffortTypeId.equals("")? qWorkEffortTypeContent.workEffortTypeId.eq(workEffortTypeId): qWorkEffortTypeContent.workEffortTypeId.isNotNull())
	        							.and(qWorkEffortTypeContent.contentId.eq(reportContentId))
	        							.and(qWorkEffortType.parentTypeId.eq(parentTypeId)));


	        SQLBindings bindings = tupleSQLQuery.getSQL();
	        LOG.info("getWorkEffortTypeContents {}", bindings.getSQL());
	        LOG.info("getWorkEffortTypeContents {}", bindings.getNullFriendlyBindings());
	        List<WorkEffortTypeExt> ret = tupleSQLQuery.transform(GroupBy.groupBy(qWorkEffortType.workEffortTypeId).list(tupleExQBean));
	        LOG.info("getWorkEffortTypeContents size = {}", ret.size());
	        return ret;
	    }

	@Transactional
	public WorkEffortTypeContent getWorkEffortTypeContent(String workEffortTypeId, String contentId) {
		if (TransactionSynchronizationManager.isActualTransactionActive()) {
			TransactionStatus status = TransactionAspectSupport.currentTransactionStatus();
			status.getClass();
		}

		QWorkEffortTypeContent qWorkEffortTypeContent = QWorkEffortTypeContent.workEffortTypeContent;

		SQLQuery<WorkEffortTypeContent> tupleSQLQuery = queryFactory.select(qWorkEffortTypeContent)
				.from(qWorkEffortTypeContent)
				.where(qWorkEffortTypeContent.workEffortTypeId.eq(workEffortTypeId)
						.and(qWorkEffortTypeContent.contentId.eq(contentId)));

		SQLBindings bindings = tupleSQLQuery.getSQL();
		LOG.info("{}", bindings.getSQL());
		LOG.info("{}", bindings.getNullFriendlyBindings());

		QBean<WorkEffortTypeContent> wa = Projections.bean(WorkEffortTypeContent.class, qWorkEffortTypeContent.all());
		List<WorkEffortTypeContent> ret = tupleSQLQuery.transform(GroupBy.groupBy(qWorkEffortTypeContent.workEffortTypeId).list(wa));

		return ret.isEmpty() ? null : ret.get(0);
	}

	@Transactional
	public Map<String, String> getWorkEffortTypeContentParams(String workEffortTypeId, String contentId) {
		WorkEffortTypeContent workEffortTypeContent = getWorkEffortTypeContent(workEffortTypeId, contentId);
		if (workEffortTypeContent != null && workEffortTypeContent.getParams() != null && workEffortTypeContent.getParams().length() > 0) {
            return Arrays.asList(workEffortTypeContent.getParams().split(";")).stream().map(s -> s.split("=")).collect(Collectors.toMap(e -> e[0].trim(), e -> e[1].replaceAll("\"", "")));
		}
		return new HashMap<String, String>();
	}
}
