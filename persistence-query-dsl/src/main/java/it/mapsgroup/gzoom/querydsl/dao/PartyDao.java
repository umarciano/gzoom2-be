package it.mapsgroup.gzoom.querydsl.dao;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.group.GroupBy;
import com.querydsl.core.types.OrderSpecifier;
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

import java.util.List;
import java.util.Locale;
import java.util.Map;

import static com.querydsl.core.types.Projections.bean;
import static com.querydsl.core.types.Projections.tuple;
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
    private final WorkEffortTypeContentDao workEffortTypeContentDao;
    private final UserLoginDao userLoginDao;
    
    @Autowired
    public PartyDao(SequenceGenerator sequenceGenerator, SQLQueryFactory queryFactory, FilterPermissionDao filterPermissionDao, WorkEffortTypeContentDao workEffortTypeContentDao, UserLoginDao userLoginDao) {
        this.sequenceGenerator = sequenceGenerator;
        this.queryFactory = queryFactory;
        this.filterPermissionDao = filterPermissionDao;
        this.workEffortTypeContentDao = workEffortTypeContentDao;
        this.userLoginDao = userLoginDao;
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

    @Transactional
    public Party findByPartyId(String id) {
        if (TransactionSynchronizationManager.isActualTransactionActive()) {
            TransactionStatus status = TransactionAspectSupport.currentTransactionStatus();
            status.getClass();
        }
        QParty qParty = QParty.party;

        SQLQuery<Party> pSQLQuery = queryFactory.select(qParty)
                .from(qParty)
                .where(qParty.partyId.eq(id));

        SQLBindings bindings = pSQLQuery.getSQL();
        LOG.info("{}", bindings.getSQL());
        LOG.info("{}", bindings.getNullFriendlyBindings());
        QBean<Party> party = Projections.bean(Party.class, qParty.all());
        List<Party> ret = pSQLQuery.transform(GroupBy.groupBy(qParty.partyId).list(party));

        return ret.isEmpty() ? null : ret.get(0);
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
    public List<Party> getRoleTypePartys(String roleTypeId, String roleTypeIdFrom, String workEffortTypeId) {
        if (TransactionSynchronizationManager.isActualTransactionActive()) {
            TransactionStatus status = TransactionAspectSupport.currentTransactionStatus();
            status.getClass();
        }

        String[] roleTypeIdFromArray = {"20DIR","30SETT"};
        if(roleTypeIdFrom!=null)
            roleTypeIdFromArray = roleTypeIdFrom.split(",");

        QParty qParty = QParty.party;
        QPartyRole qPartyRole = QPartyRole.partyRole;
        QPartyRole qUO = QPartyRole.partyRole;
        QPartyRelationship qPartyRelationship = QPartyRelationship.partyRelationship;
        QWorkEffortType qWorkEffortType = QWorkEffortType.workEffortType;

        BooleanBuilder builder = new BooleanBuilder();
        if(roleTypeIdFrom!=null) {
            builder.and(qPartyRelationship.roleTypeIdFrom.in(roleTypeIdFromArray));
        } else if (workEffortTypeId!=null && checkPartyRole(workEffortTypeId).size()>0) {
            builder.and(
                    qUO.roleTypeId.in(queryFactory.select(qWorkEffortType.orgUnitRoleTypeId).from(qWorkEffortType).where(qWorkEffortType.workEffortTypeId.eq(workEffortTypeId)))
                    .or(qPartyRole.roleTypeId.in(queryFactory.select(qWorkEffortType.orgUnitRoleTypeId2).from(qWorkEffortType).where(qWorkEffortType.workEffortTypeId.eq(workEffortTypeId)))
                    .or(qPartyRole.roleTypeId.in(queryFactory.select(qWorkEffortType.orgUnitRoleTypeId3).from(qWorkEffortType).where(qWorkEffortType.workEffortTypeId.eq(workEffortTypeId)))
                    )));
        }

        SQLQuery<Party> pSQLQuery = queryFactory.select(qParty)
        							.from(qParty)
        							.innerJoin(qPartyRole).on(qPartyRole.partyId.eq(qParty.partyId))
        							.where(qParty.partyTypeId.eq("PERSON")
                                        .and(qPartyRole.roleTypeId.eq(roleTypeId))

                                        .and(qParty.partyId.in(
                                                queryFactory.select(qPartyRelationship.partyIdTo)
                                                        .from(qPartyRelationship)
                                                        .innerJoin(qUO).on(qUO.partyId.eq(qPartyRelationship.partyIdFrom))
                                                        .where(builder.and(qPartyRelationship.partyRelationshipTypeId.eq("ORG_RESPONSIBLE")
                                                        )))))
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
    public List<PartyEx> getOrgUnits(String userLoginId, String parentTypeId, String roleTypeId, String workEffortTypeId, String company, List<String> languages) {
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
         QWorkEffortType qWorkEffortType = QWorkEffortType.workEffortType;

         BooleanBuilder builder = new BooleanBuilder();
         if(roleTypeId!=null) {
             builder.and(roleType!=null&&roleType.length>0?qPartyRole.roleTypeId.in(roleType):qPartyRole.roleTypeId.isNotNull());
         } else if(workEffortTypeId!=null && checkPartyRole(workEffortTypeId).size()>0){
             builder.and(
                     qPartyRole.roleTypeId.in(queryFactory.select(qWorkEffortType.orgUnitRoleTypeId).from(qWorkEffortType).where(qWorkEffortType.workEffortTypeId.eq(workEffortTypeId)))
                     .or(qPartyRole.roleTypeId.in(queryFactory.select(qWorkEffortType.orgUnitRoleTypeId2).from(qWorkEffortType).where(qWorkEffortType.workEffortTypeId.eq(workEffortTypeId)))
                             .or(qPartyRole.roleTypeId.in(queryFactory.select(qWorkEffortType.orgUnitRoleTypeId3).from(qWorkEffortType).where(qWorkEffortType.workEffortTypeId.eq(workEffortTypeId)))
                             )));
         }

         String orderUoBy = "MAINCODE";
         OrderSpecifier orderSpecifier = qPartyParentRole.parentRoleCode.asc();
         Map<String, String> paramsMap = workEffortTypeContentDao.getWorkEffortTypeContentParams(parentTypeId, "WEFLD_MAIN");
         if (paramsMap != null && paramsMap.size() > 0 && paramsMap.get("orderUoBy") != null) {
             orderUoBy = (String) paramsMap.get("orderUoBy");
         }
         if ("EXTCODE".equals(orderUoBy)) {
             orderSpecifier = qParty.externalId.asc();
         }
         if ("UONAME".equals(orderUoBy)) {
             orderSpecifier = isLanguageLang(userLoginId, languages) ? qParty.partyNameLang.asc() : qParty.partyName.asc();
         }

         SQLQuery<Tuple> tupleSQLQuery = queryFactory.select(qParty, qPartyParentRole)
  				.from(qPartyRole)
  				.innerJoin(qParty).on(qParty.partyId.eq(qPartyRole.partyId)) 
  				.innerJoin(qPartyParentRole).on(qPartyParentRole.partyId.eq(qParty.partyId)) 
  				.where(builder.and(qPartyRole.parentRoleTypeId.eq("ORGANIZATION_UNIT"))
  					.and(qParty.statusId.eq("PARTY_ENABLED"))
                    .and(qPartyParentRole.organizationId.eq(company)))

                 .orderBy(orderSpecifier);
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

    // Check if orgUnitRoleTypeId 2 3 fields are populated
    @Transactional
    public List<PartyRole> checkPartyRole(String workEffortTypeId) {
        if (TransactionSynchronizationManager.isActualTransactionActive()) {
            TransactionStatus status = TransactionAspectSupport.currentTransactionStatus();
            status.getClass();

        }

        QPartyRole qPartyRole = QPartyRole.partyRole;
        QWorkEffortType qWorkEffortType = QWorkEffortType.workEffortType;

        SQLQuery<PartyRole> pSQLQuery = queryFactory.select(qPartyRole).from(qPartyRole).where(
                qPartyRole.roleTypeId.in(queryFactory.select(qWorkEffortType.orgUnitRoleTypeId).from(qWorkEffortType).where(qWorkEffortType.workEffortTypeId.eq(workEffortTypeId)))
                        .or(qPartyRole.roleTypeId.in(queryFactory.select(qWorkEffortType.orgUnitRoleTypeId2).from(qWorkEffortType).where(qWorkEffortType.workEffortTypeId.eq(workEffortTypeId)))
                                .or(qPartyRole.roleTypeId.in(queryFactory.select(qWorkEffortType.orgUnitRoleTypeId3).from(qWorkEffortType).where(qWorkEffortType.workEffortTypeId.eq(workEffortTypeId)))
                                ))
        );

        SQLBindings bindings = pSQLQuery.getSQL();
        LOG.info("{}", bindings.getSQL());
        LOG.info("{}", bindings.getNullFriendlyBindings());
        QBean<PartyRole> partys = Projections.bean(PartyRole.class, qPartyRole.all());
        List<PartyRole> ret = pSQLQuery.transform(GroupBy.groupBy(qPartyRole.partyId).list(partys));
        LOG.info("size = {}", ret.size());
        return ret;
    }

    private boolean isLanguageLang(String userLoginId, List<String> languages) {
        boolean languageLang = false;
        if (languages != null && languages.size() > 1) {
            UserLogin profile = userLoginDao.getUserLogin(userLoginId);
            if (profile != null && profile.getLastLocale() != null && !profile.getLastLocale().equals("")){
                String [] localeStr = profile.getLastLocale().split("_");
                Locale locale = new Locale(localeStr[0],localeStr[1]);
                languageLang = locale != null && locale.toString().equals(languages.get(1));
            }
        }
        return languageLang;
    }

}
