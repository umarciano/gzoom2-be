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
import it.mapsgroup.gzoom.querydsl.dto.Party;
import it.mapsgroup.gzoom.querydsl.dto.PartyEx;
import it.mapsgroup.gzoom.querydsl.dto.PartyParentRole;
import it.mapsgroup.gzoom.querydsl.dto.QParty;
import it.mapsgroup.gzoom.querydsl.dto.QPartyParentRole;
import it.mapsgroup.gzoom.querydsl.dto.QPartyRelationship;
import it.mapsgroup.gzoom.querydsl.dto.QPartyRole;
import it.mapsgroup.gzoom.querydsl.dto.QUserLoginPersistent;
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
    private SQLQueryFactory queryFactory;
    private PermissionService permissionService;
    
    @Autowired
    public PartyDao(SequenceGenerator sequenceGenerator, SQLQueryFactory queryFactory, PermissionService permissionService) {
        this.sequenceGenerator = sequenceGenerator;
        this.queryFactory = queryFactory;
        this.permissionService = permissionService;
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
    public List<Party> getPartys() {
        if (TransactionSynchronizationManager.isActualTransactionActive()) {
            TransactionStatus status = TransactionAspectSupport.currentTransactionStatus();
            status.getClass();
        }
        QParty qParty = QParty.party;
        SQLQuery<Party> pSQLQuery = queryFactory.select(qParty).from(qParty).where(qParty.partyTypeId.eq("PERSON")).orderBy(qParty.partyName.asc());
        SQLBindings bindings = pSQLQuery.getSQL();
        LOG.info("{}", bindings.getSQL());
        LOG.info("{}", bindings.getBindings());
        QBean<Party> partys = Projections.bean(Party.class, qParty.all());
        List<Party> ret = pSQLQuery.transform(GroupBy.groupBy(qParty.partyId).list(partys));
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
    	 String permission = ContextPermissionPrefixEnum.getPermissionPrefix(parentTypeId);
    	 
         QParty qParty = QParty.party;
         QPartyParentRole qPartyParentRole = QPartyParentRole.partyParentRole;
         QPartyRole qPartyRole = QPartyRole.partyRole;
         
         SQLQuery<Tuple> tupleSQLQuery = queryFactory.select(qParty, qPartyParentRole)
  				.from(qPartyRole)
  				.innerJoin(qParty).on(qParty.partyId.eq(qPartyRole.partyId)) 
  				.innerJoin(qPartyParentRole).on(qPartyParentRole.partyId.eq(qParty.partyId)) 
  				.where(qPartyRole.parentRoleTypeId.eq("ORGANIZATION_UNIT")
  					.and(qParty.statusId.eq("PARTY_ENABLED")))
                 .orderBy(qPartyParentRole.parentRoleCode.asc(), qPartyParentRole.parentRoleCode.asc());
                // .groupBy(qParty.partyId);
         
         
         // se ho uno dei permessi uso la  lista filtrata di elementi
         boolean isOrgMgr = permissionService.isOrgMgr(userLoginId, permission);
         boolean isSup = permissionService.isSup(userLoginId, permission);
         boolean isTop = permissionService.isTop(userLoginId, permission);
         
         if (isOrgMgr || isSup || isTop) {
        	 
        	 QUserLoginPersistent qUserLogin = QUserLoginPersistent.userLogin;        	 
        	 QPartyRelationship qPartyRelationshipE = new QPartyRelationship("E");
             QPartyRelationship qPartyRelationshipY = new QPartyRelationship("Y");
             QPartyRelationship qPartyRelationshipZ = new QPartyRelationship("Z");
             QPartyRelationship qPartyRelationshipZ2 = new QPartyRelationship("Z2");
             QPartyRelationship qPartyRelationshipY2 = new QPartyRelationship("Y2");  
             
             
             tupleSQLQuery.innerJoin(qUserLogin).on(qUserLogin.userLoginId.eq(userLoginId)) 
             .leftJoin(qPartyRelationshipE).on(qPartyRelationshipE.roleTypeIdFrom.eq(qPartyRole.roleTypeId)
						.and(qPartyRelationshipE.partyIdFrom.eq(qPartyRole.partyId))
						.and(qPartyRelationshipE.partyRelationshipTypeId.in("ORG_RESPONSIBLE", "ORG_DELEGATE"))
						.and(qPartyRelationshipE.thruDate.isNull())
						.and(qPartyRelationshipE.partyIdTo.eq(qUserLogin.partyId)))             
             .leftJoin(qPartyRelationshipZ).on(qPartyRelationshipZ.roleTypeIdTo.eq(qPartyRole.roleTypeId)
						.and(qPartyRelationshipZ.partyIdTo.eq(qPartyRole.partyId))
						.and(qPartyRelationshipZ.partyRelationshipTypeId.eq("GROUP_ROLLUP"))
						.and(qPartyRelationshipZ.thruDate.isNull()))             
             .leftJoin(qPartyRelationshipY).on(qPartyRelationshipY.roleTypeIdFrom.eq(qPartyRelationshipZ.roleTypeIdFrom)
						.and(qPartyRelationshipY.partyIdFrom.eq(qPartyRelationshipZ.partyIdFrom))
						.and(qPartyRelationshipY.partyRelationshipTypeId.in("ORG_RESPONSIBLE", "ORG_DELEGATE"))
						.and(qPartyRelationshipY.thruDate.isNull())
						.and(qPartyRelationshipY.partyIdTo.eq(qUserLogin.partyId)))
             .leftJoin(qPartyRelationshipZ2).on(qPartyRelationshipZ2.roleTypeIdTo.eq(qPartyRelationshipZ.roleTypeIdFrom)
						.and(qPartyRelationshipZ2.partyIdTo.eq(qPartyRelationshipZ.partyIdFrom))
						.and(qPartyRelationshipZ2.partyRelationshipTypeId.eq("GROUP_ROLLUP"))
						.and(qPartyRelationshipZ2.thruDate.isNull()))
			.leftJoin(qPartyRelationshipY2).on(qPartyRelationshipY2.roleTypeIdFrom.eq(qPartyRelationshipZ2.roleTypeIdFrom)
						.and(qPartyRelationshipY2.partyIdFrom.eq(qPartyRelationshipZ2.partyIdFrom))
						.and(qPartyRelationshipY2.partyRelationshipTypeId.in("ORG_RESPONSIBLE", "ORG_DELEGATE"))
						.and(qPartyRelationshipY2.thruDate.isNull())
						.and(qPartyRelationshipY2.partyIdTo.eq(qUserLogin.partyId)));           
             
             List<Predicate> predicates = new ArrayList<>();
             
             if (isOrgMgr) {
            	 predicates.add(qPartyRelationshipE.partyIdTo.isNotNull());
             }
             if (isSup) {
            	 predicates.add(qPartyRelationshipY.partyIdTo.isNotNull());
             }
             if (isTop) {
            	 predicates.add(qPartyRelationshipY2.partyIdTo.isNotNull());
             }
             tupleSQLQuery.where(predicates.toArray(new Predicate[0]));
         }
         
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
