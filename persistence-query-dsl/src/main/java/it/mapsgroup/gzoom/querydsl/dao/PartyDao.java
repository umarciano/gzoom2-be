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
import it.mapsgroup.gzoom.querydsl.service.PermissionService;
import it.mapsgroup.gzoom.querydsl.util.ContextPermissionPrefixEnum;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.ArrayList;
import java.util.List;

import static com.querydsl.core.types.Projections.bean;
import static it.mapsgroup.gzoom.querydsl.QBeanUtils.merge;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author Andrea Fossi.
 */
@Service
public class PartyDao extends AbstractDao {
    private static final Logger LOG = getLogger(PartyDao.class);

    private final SequenceGenerator sequenceGenerator;
    private final SQLQueryFactory queryFactory;
    private final PermissionService permissionService;
    private final FilterPermissionDao filterPermissionDao;
    
    @Autowired
    public PartyDao(SequenceGenerator sequenceGenerator, SQLQueryFactory queryFactory, PermissionService permissionService, FilterPermissionDao filterPermissionDao) {
        this.sequenceGenerator = sequenceGenerator;
        this.queryFactory = queryFactory;
        this.permissionService = permissionService;
        this.filterPermissionDao = filterPermissionDao;
    }

    @Transactional
    public boolean create(Party record) {
        QParty party = QParty.party;
        String id = sequenceGenerator.getNextSeqId("Party");
        LOG.debug("PartyId[{}]", id);
        record.setPartyId(id);
        setCreatedTimestamp(record);
        long i = queryFactory.insert(party).populate(record).execute();
        LOG.debug("created records: {}", i);

        return i > 0;
    }

    /**
     * TODO implement
     *
     * @param record
     * @return
     */
    @Transactional
    public boolean update(Party record) {
        QParty party = QParty.party;
        record.setDescription("Primo Party " + record.getPartyId());
        setUpdateTimestamp(record);
        long i = queryFactory.update(party).populate(record).execute();
        LOG.debug("updated records: {}", i);

        return i > 0;
    }
    
    @Transactional
    public List<Party> getPartys(String userLoginId, String parentTypeId) {
        if (TransactionSynchronizationManager.isActualTransactionActive()) {
            TransactionStatus status = TransactionAspectSupport.currentTransactionStatus();
            status.getClass();
        }
        QParty qParty = QParty.party;
        
        SQLQuery<Party> pSQLQuery = queryFactory.select(qParty)
        		.from(qParty)        		
        		.orderBy(qParty.partyName.asc());
        
        pSQLQuery = (SQLQuery<Party>) filterPermissionDao.getFilterQueryPerson(pSQLQuery, qParty, userLoginId, parentTypeId);
        
        SQLBindings bindings = pSQLQuery.getSQL();
        LOG.info("{}", bindings.getSQL());
        LOG.info("{}", bindings.getBindings());
        QBean<Party> partys = Projections.bean(Party.class, qParty.all());
        List<Party> ret = pSQLQuery.transform(GroupBy.groupBy(qParty.partyId).list(partys));
        LOG.info("size = {}", ret.size());
        return ret;
    }

    @Transactional
    public List<PersonEx> getPartiesExposed() {
        if (TransactionSynchronizationManager.isActualTransactionActive()) {
            TransactionStatus status = TransactionAspectSupport.currentTransactionStatus();
            status.getClass();
        }
        QParty qParty = QParty.party;
        QPerson qPerson = QPerson.person;
        QPartyParentRole qPartyParentRole = QPartyParentRole.partyParentRole;

        SQLQuery<Tuple> pSQLQuery = queryFactory.select(qPerson, qParty, qPartyParentRole)
                .from(qPerson)
                .innerJoin(qParty).on(qParty.partyId.eq(qPerson.partyId))
                .innerJoin(qPartyParentRole).on(qPartyParentRole.partyId.eq(qPerson.partyId)).on(qPartyParentRole.roleTypeId.eq("EMPLOYEE"))
                .orderBy(qPerson.firstName.asc(), qPerson.lastName.asc());

        SQLBindings bindings = pSQLQuery.getSQL();
        LOG.info("{}", bindings.getSQL());
        LOG.info("{}", bindings.getBindings());
        QBean<PersonEx> partys = bean(PersonEx.class, merge(qPerson.all(), bean(Party.class, qParty.all()).as("party"), bean(PartyParentRole.class, qPartyParentRole.all()).as("partyParentRole")));
        List<PersonEx> ret = pSQLQuery.transform(GroupBy.groupBy(qParty.partyId).list(partys));
        LOG.info("size = {}", ret.size());
        return ret;
    }
    
    @Transactional
    public List<Party> getRoleTypePartys(String roleTypeId) {
        if (TransactionSynchronizationManager.isActualTransactionActive()) {
            TransactionStatus status = TransactionAspectSupport.currentTransactionStatus();
            status.getClass();
        }
        QParty qParty = QParty.party;
        QPartyRole qPartyRole = QPartyRole.partyRole;
        
        SQLQuery<Party> pSQLQuery = queryFactory.select(qParty)
        							.from(qParty)
        							.innerJoin(qPartyRole).on(qPartyRole.partyId.eq(qParty.partyId))
        							.where(qParty.partyTypeId.eq("PERSON")
        									.and(qPartyRole.roleTypeId.eq(roleTypeId)))        							
        							.orderBy(qParty.partyName.asc());
        SQLBindings bindings = pSQLQuery.getSQL();
        LOG.info("{}", bindings.getSQL());
        LOG.info("{}", bindings.getBindings());
        QBean<Party> partys = Projections.bean(Party.class, qParty.all());
        List<Party> ret = pSQLQuery.transform(GroupBy.groupBy(qParty.partyId).list(partys));
        LOG.info("size = {}", ret.size());
        return ret;
    }
    
    /**
     * Restituisce una lista di party filtrata in base al ruolo se non sono admin
     * @return
     */
    
    @Transactional
    public List<PartyEx> getOrgUnits(String userLoginId, String parentTypeId) {
    	 if (TransactionSynchronizationManager.isActualTransactionActive()) {
             TransactionStatus status = TransactionAspectSupport.currentTransactionStatus();
             status.getClass();
         }
    	 
    	 
         QParty qParty = QParty.party;
         QPartyParentRole qPartyParentRole = QPartyParentRole.partyParentRole;
         QPartyRole qPartyRole = QPartyRole.partyRole;
         
         SQLQuery<Tuple> tupleSQLQuery = queryFactory.select(qParty, qPartyParentRole)
  				.from(qPartyRole)
  				.innerJoin(qParty).on(qParty.partyId.eq(qPartyRole.partyId)) 
  				.innerJoin(qPartyParentRole).on(qPartyParentRole.partyId.eq(qParty.partyId)) 
  				.where(qPartyRole.parentRoleTypeId.eq("ORGANIZATION_UNIT")
  					.and(qParty.statusId.eq("PARTY_ENABLED")))
                 .orderBy(qPartyParentRole.parentRoleCode.asc());
                // .groupBy(qParty.partyId);
         
         
         tupleSQLQuery = (SQLQuery<Tuple>) filterPermissionDao.getFilterQuery(tupleSQLQuery, qPartyRole, userLoginId, parentTypeId);
         
		 SQLBindings bindings = tupleSQLQuery.getSQL();
		 LOG.info("{}", bindings.getSQL());
		 LOG.info("{}", bindings.getBindings());
		 
         QBean<PartyEx> partyQBean = bean(PartyEx.class, 
        		merge(qParty.all(), 
        				bean(PartyParentRole.class, qPartyParentRole.all()).as("partyParentRole")));
	        
		 List<PartyEx> ret = tupleSQLQuery.transform(GroupBy.groupBy(qParty.partyId).list(partyQBean));
		 LOG.info("size = {}", ret.size()); 
		 return ret;    
    }
    
    
    

}
