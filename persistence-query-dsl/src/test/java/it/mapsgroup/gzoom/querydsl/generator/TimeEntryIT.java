package it.mapsgroup.gzoom.querydsl.generator;

import com.querydsl.core.Tuple;
import com.querydsl.core.group.GroupBy;
import com.querydsl.core.types.QBean;
import com.querydsl.sql.SQLBindings;
import com.querydsl.sql.SQLQuery;
import com.querydsl.sql.SQLQueryFactory;
import it.mapsgroup.gzoom.querydsl.dao.AbstractDaoIT;
import it.mapsgroup.gzoom.querydsl.dto.*;
import org.junit.Test;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import static com.querydsl.core.types.Projections.bean;
import static it.mapsgroup.gzoom.querydsl.QBeanUtils.merge;
import static org.slf4j.LoggerFactory.getLogger;

public class TimeEntryIT extends AbstractDaoIT {
    private static final Logger LOG = getLogger(TimeEntryIT.class);

    @Autowired
    private SQLQueryFactory queryFactory;

    @Autowired
    TransactionTemplate transactionTemplate;

    @Autowired
    PlatformTransactionManager txManager;

    @Test
    @Transactional
    public void select() throws Exception {

        QTimesheet ts = QTimesheet.timesheet;
        QTimeEntry te = QTimeEntry.timeEntry;
        QWorkEffort l1 = QWorkEffort.workEffort;
        QWorkEffort l2 = QWorkEffort.workEffort;
        QWorkEffort l3 = QWorkEffort.workEffort;
        QWorkEffortAssoc wa = QWorkEffortAssoc.workEffortAssoc;
        QWorkEffortType wt = QWorkEffortType.workEffortType;
        QWorkEffortTypeType t1 = QWorkEffortTypeType.workEffortTypeType;
        QWorkEffortTypeType t2 = QWorkEffortTypeType.workEffortTypeType;
        QWorkEffortTypeType t3 = QWorkEffortTypeType.workEffortTypeType;
        QWorkEffortPartyAssignment pa = QWorkEffortPartyAssignment.workEffortPartyAssignment;

        SQLQuery<Tuple> tupleSQLQuery;
        tupleSQLQuery = queryFactory.
            select(l1.workEffortName, l2.workEffortName, l3.workEffortName, l3.workEffortId)
            .from(ts)
            .innerJoin(wt, wt).on(wt.etch.equalsIgnoreCase("TIMESHEET"))
            .innerJoin(t1,t1).on(t1.workEffortTypeIdRoot.equalsIgnoreCase(wt.workEffortTypeId).and(t1.sequenceNum.eq(new BigInteger("1"))))
            .innerJoin(l1,l1).on(l1.workEffortTypeId.equalsIgnoreCase(t1.workEffortTypeIdTo)
                    .and(l1.estimatedStartDate.before(ts.thruDate))
                    .and(l1.estimatedCompletionDate.after(ts.fromDate))
                    .and(l1.workEffortRevisionId.isNull()))

/*
        inner join work_effort_party_assignment pa
        on pa.WORK_EFFORT_ID = l1.WORK_EFFORT_ID
        and pa.PARTY_ID = ts.PARTY_ID
        and pa.FROM_DATE <= ts.THRU_DATE
        and pa.THRU_DATE >= ts.FROM_DATE
        inner join work_effort_type_type t2
        on t2.WORK_EFFORT_TYPE_ID_ROOT = wt.WORK_EFFORT_TYPE_ID
        and t2.SEQUENCE_NUM = 2
        inner join work_effort_assoc l12
        on l12.WORK_EFFORT_ID_FROM = l1.WORK_EFFORT_ID
        inner join work_effort l2
        on l2.WORK_EFFORT_ID = l12.WORK_EFFORT_ID_TO
        and l2.WORK_EFFORT_TYPE_ID = t2.WORK_EFFORT_TYPE_ID_TO
        and l2.ESTIMATED_START_DATE <= ts.THRU_DATE
        and l2.ESTIMATED_COMPLETION_DATE >= ts.FROM_DATE
        and l1.WORK_EFFORT_REVISION_ID is null
        inner join work_effort_type_type t3
        on t3.WORK_EFFORT_TYPE_ID_ROOT = wt.WORK_EFFORT_TYPE_ID
        and t3.SEQUENCE_NUM = 3
        inner join work_effort_assoc l23
        on l23.WORK_EFFORT_ID_FROM = l2.WORK_EFFORT_ID
        inner join work_effort l3
        on l3.WORK_EFFORT_ID = l23.WORK_EFFORT_ID_TO
        and l3.WORK_EFFORT_TYPE_ID = t3.WORK_EFFORT_TYPE_ID_TO
        and l3.ESTIMATED_START_DATE <= ts.THRU_DATE
        and l3.ESTIMATED_COMPLETION_DATE >= ts.FROM_DATE
        and l3.WORK_EFFORT_REVISION_ID is null

*/

        ;


        SQLBindings bindings = tupleSQLQuery.getSQL();
        LOG.info("{}", bindings.getSQL());
        LOG.info("{}", bindings.getBindings());

    }

}
