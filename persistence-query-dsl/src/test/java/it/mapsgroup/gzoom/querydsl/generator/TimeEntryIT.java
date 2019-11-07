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

        String timesheetId = "10020";

        QTimesheet ts = new QTimesheet("ts");
        QTimeEntry te = new QTimeEntry("te");
        QWorkEffort l1 = new QWorkEffort("l1");
        QWorkEffort l2 = new QWorkEffort("l2");
        QWorkEffort l3 = new QWorkEffort("l3");
        QWorkEffortAssoc l12 = new QWorkEffortAssoc("l12");
        QWorkEffortAssoc l23 = new QWorkEffortAssoc("l23");
        QWorkEffortType wt = new QWorkEffortType("wt");
        QWorkEffortTypeType t1 = new QWorkEffortTypeType("t1");
        QWorkEffortTypeType t2 = new QWorkEffortTypeType("t2");
        QWorkEffortTypeType t3 = new QWorkEffortTypeType("t3");
        QWorkEffortPartyAssignment wpa = new QWorkEffortPartyAssignment("wpa");

        SQLQuery<Tuple> tupleSQLQuery;
        tupleSQLQuery = queryFactory.
            select(l1.workEffortName.as("AttivitaLiv1"), l2.workEffortName.as("AttivitaLiv2"),
                    l3.workEffortName.as("AttivitaLiv3"), l3.workEffortId.as("IdLiv3"))
            .from(ts)
            .innerJoin(wt).on(wt.etch.eq("TIMESHEET"))
            .innerJoin(t1).on(t1.workEffortTypeIdRoot.eq(wt.workEffortTypeId).and(t1.sequenceNum.eq(new BigInteger("1"))))
            .innerJoin(l1).on(l1.workEffortTypeId.eq(t1.workEffortTypeIdTo)
                    .and(l1.estimatedStartDate.before(ts.thruDate))
                    .and(l1.estimatedCompletionDate.after(ts.fromDate))
                    .and(l1.workEffortRevisionId.isNull()))
            .innerJoin(wpa).on(wpa.workEffortId.eq(l1.workEffortId)
                    .and(wpa.partyId.eq(ts.partyId))
                    .and(wpa.fromDate.before(ts.thruDate))
                    .and(wpa.thruDate.after(ts.fromDate)))
            .innerJoin(t2).on(t2.workEffortTypeIdRoot.eq(wt.workEffortTypeId)
                    .and(t2.sequenceNum.eq(new BigInteger("2"))))
            .innerJoin(l12).on(l12.workEffortIdFrom.eq(l1.workEffortId))
            .innerJoin(l2).on(l2.workEffortId.eq(l12.workEffortIdTo)
                .and(l2.workEffortTypeId.eq(t2.workEffortTypeIdTo))
                .and(l2.estimatedStartDate.before(ts.thruDate))
                .and(l2.estimatedCompletionDate.after(ts.fromDate))
                .and(l1.workEffortRevisionId.isNull()))
            .innerJoin(t3).on(t3.workEffortTypeIdRoot.eq(wt.workEffortTypeId)
                .and(t3.sequenceNum.eq(new BigInteger("3"))))
            .innerJoin(l23).on(l23.workEffortIdFrom.eq(l2.workEffortId))
            .innerJoin(l3).on(l3.workEffortId.eq(l23.workEffortIdTo)
                .and(l3.workEffortTypeId.eq(t3.workEffortTypeIdTo))
                .and(l3.estimatedStartDate.before(ts.thruDate))
                .and(l3.estimatedCompletionDate.after(ts.fromDate))
                .and(l3.workEffortRevisionId.isNull()))
        .where(ts.timesheetId.eq(timesheetId));


        SQLBindings bindings = tupleSQLQuery.getSQL();
        LOG.info("{}", bindings.getSQL());
        LOG.info("{}", bindings.getNullFriendlyBindings());

    }

}
