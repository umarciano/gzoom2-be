package it.mapsgroup.gzoom.querydsl.dao;

import static org.slf4j.LoggerFactory.getLogger;

import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import com.querydsl.core.group.GroupBy;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.QBean;
import com.querydsl.sql.SQLBindings;
import com.querydsl.sql.SQLQuery;
import com.querydsl.sql.SQLQueryFactory;

import it.mapsgroup.gzoom.querydsl.dto.ContactMech;
import it.mapsgroup.gzoom.querydsl.dto.QContactMech;
import it.mapsgroup.gzoom.querydsl.dto.QPartyContactMech;
import it.mapsgroup.gzoom.querydsl.dto.QPartyRole;

@Service
public class ContactMechDao extends AbstractDao {
	private static final Logger LOG = getLogger(ContactMechDao.class);

    private SQLQueryFactory queryFactory;
    
    @Autowired
    public ContactMechDao(SQLQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }
    
    @Transactional
    public List<ContactMech> getContactMechPartyRole(String roleTypeId) {
        if (TransactionSynchronizationManager.isActualTransactionActive()) {
            TransactionStatus status = TransactionAspectSupport.currentTransactionStatus();
            status.getClass();
        }
        
        QContactMech qContactMech = QContactMech.contactMech;
        QPartyRole qPartyRole = QPartyRole.partyRole;
        QPartyContactMech qPartyContactMech = QPartyContactMech.partyContactMech;

        SQLQuery<ContactMech> tupleSQLQuery = queryFactory.select(qContactMech)
        		.from(qPartyRole)
        		.innerJoin(qPartyContactMech).on(qPartyContactMech.partyId.eq(qPartyRole.partyId)
        				.and(filterByDate(qPartyContactMech.fromDate, qPartyContactMech.thruDate)))
        		.innerJoin(qContactMech).on(qContactMech.contactMechId.eq(qPartyContactMech.contactMechId))
        		.where(qPartyRole.roleTypeId.eq(roleTypeId)
        				.and(qContactMech.contactMechTypeId.eq("EMAIL_ADDRESS")));

        SQLBindings bindings = tupleSQLQuery.getSQL();
        LOG.info("{}", bindings.getSQL());
        LOG.info("{}", bindings.getBindings());
        QBean<ContactMech> list = Projections.bean(ContactMech.class, qContactMech.all());
        List<ContactMech> ret = tupleSQLQuery.transform(GroupBy.groupBy(qContactMech.contactMechId).list(list));
        LOG.info("size = {}", ret.size());
        return ret;
    }
}
