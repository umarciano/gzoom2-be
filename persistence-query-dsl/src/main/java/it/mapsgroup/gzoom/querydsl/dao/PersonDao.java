package it.mapsgroup.gzoom.querydsl.dao;

import com.querydsl.core.group.GroupBy;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.QBean;
import com.querydsl.sql.SQLBindings;
import com.querydsl.sql.SQLQuery;
import com.querydsl.sql.SQLQueryFactory;
import it.mapsgroup.gzoom.querydsl.dto.Person;
import it.mapsgroup.gzoom.querydsl.dto.QPerson;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

/**
 */
@Service
public class PersonDao extends AbstractDao {
    private static final Logger LOG = getLogger(PersonDao.class);

    private final SQLQueryFactory queryFactory;

    public PersonDao(SQLQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Transactional
    public List<Person> getPersons() {
        if (TransactionSynchronizationManager.isActualTransactionActive()) {
            TransactionStatus status = TransactionAspectSupport.currentTransactionStatus();
            status.getClass();
        }
        QPerson qPerson = QPerson.person;
        SQLQuery<Person> pSQLQuery = queryFactory.select(qPerson).from(qPerson);
        SQLBindings bindings = pSQLQuery.getSQL();
        LOG.info("{}", bindings.getSQL());
        LOG.info("{}", bindings.getNullFriendlyBindings());
        QBean<Person> timesheets = Projections.bean(Person.class, qPerson.all());
        List<Person> ret = pSQLQuery.transform(GroupBy.groupBy(qPerson.partyId).list(timesheets));
        LOG.info("size = {}", ret.size());
        return ret;
    }

}
