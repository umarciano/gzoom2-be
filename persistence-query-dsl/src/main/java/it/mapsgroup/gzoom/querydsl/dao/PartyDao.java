package it.mapsgroup.gzoom.querydsl.dao;

import com.querydsl.core.BooleanBuilder;
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
    private final FilterPermissionDao filterPermissionDao;
    
    @Autowired
    public PartyDao(SequenceGenerator sequenceGenerator, SQLQueryFactory queryFactory, FilterPermissionDao filterPermissionDao) {
        this.sequenceGenerator = sequenceGenerator;
        this.queryFactory = queryFactory;
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
    public List<Party> getParties(String userLoginId, String parentTypeId) {
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
        LOG.info("{}", bindings.getNullFriendlyBindings());
        QBean<Party> parties = Projections.bean(Party.class, qParty.all());
        List<Party> ret = pSQLQuery.transform(GroupBy.groupBy(qParty.partyId).list(parties));
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
        QEmplPositionType qEmplPositionType = QEmplPositionType.emplPositionType;
        QStatusItem qStatusItem = QStatusItem.statusItem;
        QPartyContactMech qPartyContactMech = QPartyContactMech.partyContactMech;
        QContactMech qContactMech = QContactMech.contactMech;

        SQLQuery<Tuple> pSQLQuery = queryFactory.select(qPerson, qParty)
                .from(qPerson)
                .innerJoin(qParty).on(qParty.partyId.eq(qPerson.partyId))
                .innerJoin(qPartyParentRole).on(qPartyParentRole.partyId.eq(qPerson.partyId)).on(qPartyParentRole.roleTypeId.eq("EMPLOYEE"))
                .leftJoin(qEmplPositionType).on(qEmplPositionType.emplPositionTypeId.eq(qPerson.emplPositionTypeId))
                .innerJoin(qStatusItem).on(qStatusItem.statusId.eq(qParty.statusId))
                .leftJoin(qPartyContactMech).on(qPartyContactMech.partyId.eq(qParty.partyId)).on(qPartyContactMech.thruDate.isNull())
                .leftJoin(qContactMech).on(qContactMech.contactMechId.eq(qPartyContactMech.contactMechId))
                .where(qContactMech.contactMechTypeId.eq("EMAIL_ADDRESS").or(qContactMech.contactMechTypeId.isNull()))
                .orderBy(qPerson.lastName.asc(), qPerson.firstName.asc());

       SQLBindings bindings = pSQLQuery.getSQL();
        LOG.info("{}", bindings.getSQL());
        LOG.info("{}", bindings.getNullFriendlyBindings());
        QBean<PersonEx> parties = bean(PersonEx.class, merge(qPerson.all(),
                bean(Party.class, qParty.all()).as("party"),
                bean(PartyParentRole.class, qPartyParentRole.all()).as("partyParentRole"),
                bean(StatusItem.class, qStatusItem.all()).as("statusItem"),
                bean(EmplPositionType.class, qEmplPositionType.all()).as("emplPositionType"),
                bean(ContactMech.class, qContactMech.all()).as("contactMech")
        ));
        List<PersonEx> ret = pSQLQuery.transform(GroupBy.groupBy(qParty.partyId).list(parties));
        LOG.info("size = {}", ret.size());
        return ret;
    }
    
    @Transactional
    public List<Party> getRoleTypePartys(String roleTypeId, String roleTypeIdFrom) {
        if (TransactionSynchronizationManager.isActualTransactionActive()) {
            TransactionStatus status = TransactionAspectSupport.currentTransactionStatus();
            status.getClass();
        }

        String[] roleTypeIdFromArray = {"20DIR","30SETT"};
        if(roleTypeIdFrom!=null)
            roleTypeIdFromArray = roleTypeIdFrom.split(",");

        QParty qParty = QParty.party;
        QPartyRole qPartyRole = QPartyRole.partyRole;
        QPartyRelationship qPartyRelationship = QPartyRelationship.partyRelationship;
        
        SQLQuery<Party> pSQLQuery = queryFactory.select(qParty)
        							.from(qParty)
        							.innerJoin(qPartyRole).on(qPartyRole.partyId.eq(qParty.partyId))
        							.where(qParty.partyTypeId.eq("PERSON")
                                        .and(qPartyRole.roleTypeId.eq(roleTypeId))
                                        .and(qParty.partyId.in(
                                                queryFactory.select(qPartyRelationship.partyIdTo)
                                                        .from(qPartyRelationship)
                                                        .where(qPartyRelationship.partyRelationshipTypeId.eq("ORG_RESPONSIBLE")
                                                                .and(qPartyRelationship.roleTypeIdFrom.in(roleTypeIdFromArray))))))
        							.orderBy(qParty.partyName.asc());
        SQLBindings bindings = pSQLQuery.getSQL();
        LOG.info("{}", bindings.getSQL());
        LOG.info("{}", bindings.getNullFriendlyBindings());
        QBean<Party> partys = Projections.bean(Party.class, qParty.all());
        List<Party> ret = pSQLQuery.transform(GroupBy.groupBy(qParty.partyId).list(partys));
        LOG.info("size = {}", ret.size());
        return ret;
    }

    @Transactional
    public List<Party> getRoleTypePartysBetween(String roleTypeId) {
        if (TransactionSynchronizationManager.isActualTransactionActive()) {
            TransactionStatus status = TransactionAspectSupport.currentTransactionStatus();
            status.getClass();
        }

        QParty qParty = QParty.party;
        QPartyRole qPartyRole = QPartyRole.partyRole;

        SQLQuery<Party> pSQLQuery = queryFactory.select(qParty)
                .from(qParty)
                .innerJoin(qPartyRole).on(qPartyRole.partyId.eq(qParty.partyId))
                .where(qPartyRole.roleTypeId.between(roleTypeId.split(",")[0],roleTypeId.split(",")[1])
                        .and(qParty.partyTypeId.eq("PERSON")))
                .orderBy(qParty.partyName.asc());
        SQLBindings bindings = pSQLQuery.getSQL();
        LOG.info("{}", bindings.getSQL());
        LOG.info("{}", bindings.getNullFriendlyBindings());
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
    public List<PartyEx> getOrgUnits(String userLoginId, String parentTypeId, String roleTypeId) {
    	 if (TransactionSynchronizationManager.isActualTransactionActive()) {
             TransactionStatus status = TransactionAspectSupport.currentTransactionStatus();
             status.getClass();
         }

    	 String[] roleType = new String[]{};
    	 if(roleTypeId!=null)
    	    roleType = roleTypeId.split(",");


         QParty qParty = QParty.party;
         QPartyParentRole qPartyParentRole = QPartyParentRole.partyParentRole;
         QPartyRole qPartyRole = QPartyRole.partyRole;
         
         SQLQuery<Tuple> tupleSQLQuery = queryFactory.select(qParty, qPartyParentRole)
  				.from(qPartyRole)
  				.innerJoin(qParty).on(qParty.partyId.eq(qPartyRole.partyId)) 
  				.innerJoin(qPartyParentRole).on(qPartyParentRole.partyId.eq(qParty.partyId)) 
  				.where(qPartyRole.parentRoleTypeId.eq("ORGANIZATION_UNIT")
  					.and(qParty.statusId.eq("PARTY_ENABLED"))
                    .and(roleType!=null&&roleType.length>0?qPartyRole.roleTypeId.in(roleType):qPartyRole.roleTypeId.isNotNull()))
                 .orderBy(qPartyParentRole.parentRoleCode.asc());
                // .groupBy(qParty.partyId);

         
         
         tupleSQLQuery = (SQLQuery<Tuple>) filterPermissionDao.getFilterQuery(tupleSQLQuery, qPartyRole, userLoginId, parentTypeId);
         
		 SQLBindings bindings = tupleSQLQuery.getSQL();
		 LOG.info("{}", bindings.getSQL());
		 LOG.info("{}", bindings.getNullFriendlyBindings());
		 
         QBean<PartyEx> partyQBean = bean(PartyEx.class, 
        		merge(qParty.all(), 
        				bean(PartyParentRole.class, qPartyParentRole.all()).as("partyParentRole")));
	        
		 List<PartyEx> ret = tupleSQLQuery.transform(GroupBy.groupBy(qParty.partyId).list(partyQBean));
		 LOG.info("size = {}", ret.size()); 
		 return ret;    
    }
    
    
    

}
