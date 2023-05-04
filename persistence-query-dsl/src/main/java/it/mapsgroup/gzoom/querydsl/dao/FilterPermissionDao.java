package it.mapsgroup.gzoom.querydsl.dao;

import static org.slf4j.LoggerFactory.getLogger;

import java.util.ArrayList;
import java.util.List;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.PredicateOperation;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.querydsl.core.types.Predicate;
import com.querydsl.sql.SQLQuery;

import it.mapsgroup.gzoom.querydsl.dto.QParty;
import it.mapsgroup.gzoom.querydsl.dto.QPartyRelationship;
import it.mapsgroup.gzoom.querydsl.dto.QPartyRole;
import it.mapsgroup.gzoom.querydsl.dto.QUserLoginPersistent;
import it.mapsgroup.gzoom.querydsl.service.PermissionService;
import it.mapsgroup.gzoom.querydsl.util.ContextPermissionPrefixEnum;

@Service
public class FilterPermissionDao {

	private static final Logger LOG = getLogger(FilterPermissionDao.class);


    private PermissionService permissionService;
    
    @Autowired
    public FilterPermissionDao(PermissionService permissionService) {
        this.permissionService = permissionService;
    }
    
	public SQLQuery<?> getFilterQueryPerson(SQLQuery<?> tupleSQLQuery, QParty qParty, String userLoginId, String parentTypeId) {
    	
    	String permission = ContextPermissionPrefixEnum.getPermissionPrefix(parentTypeId);
    	
    	// se ho uno dei permessi uso la  lista filtrata di elementi
        boolean isOrgMgr = permissionService.isOrgMgr(userLoginId, permission);
        boolean isSup = permissionService.isSup(userLoginId, permission);
        boolean isTop = permissionService.isTop(userLoginId, permission);
        boolean isRole = permissionService.isRole(userLoginId, permission);
        
        if (isOrgMgr || isSup || isTop || isRole) {
        	
        	QPartyRelationship qPartyRelationshipUO = new QPartyRelationship("UO");
            QPartyRelationship qPartyRelationshipE = new QPartyRelationship("E");
            QPartyRelationship qPartyRelationshipY = new QPartyRelationship("Y");
            QPartyRelationship qPartyRelationshipZ = new QPartyRelationship("Z");
            QPartyRelationship qPartyRelationshipZ2 = new QPartyRelationship("Z2");
            QPartyRelationship qPartyRelationshipY2 = new QPartyRelationship("Y2");        
            QUserLoginPersistent qUserLogin = QUserLoginPersistent.userLogin;
            
            
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(qParty.statusId.eq("PARTY_ENABLED"));
            if (isOrgMgr) {
    			predicates.add(qPartyRelationshipE.partyIdTo.isNotNull());
    		}
    		if (isSup) {
    			predicates.add(qPartyRelationshipY.partyIdTo.isNotNull());
    		}
    		if (isRole) {
    			predicates.add(qUserLogin.partyId.eq(qParty.partyId));
    		}
    		if (isTop) {
    			predicates.add(qPartyRelationshipY2.partyIdTo.isNotNull());
    		}
    		
    		
            tupleSQLQuery.innerJoin(qPartyRelationshipUO).on(qPartyRelationshipUO.partyIdTo.eq(qParty.partyId)
					.and(qPartyRelationshipUO.partyRelationshipTypeId.eq("ORG_EMPLOYMENT"))
					.and(qPartyRelationshipUO.thruDate.isNull()))  
			
			.innerJoin(qUserLogin).on(qUserLogin.userLoginId.eq(userLoginId)) 
			
			.leftJoin(qPartyRelationshipE).on(qPartyRelationshipE.roleTypeIdFrom.eq(qPartyRelationshipUO.roleTypeIdFrom)
					.and(qPartyRelationshipE.partyIdFrom.eq(qPartyRelationshipUO.partyIdFrom))
					.and(qPartyRelationshipE.partyRelationshipTypeId.eq("ORG_RESPONSIBLE")
							.or((qPartyRelationshipE.partyRelationshipTypeId.eq("ORG_DELEGATE").and(
									qPartyRelationshipE.ctxEnabled.isNull().or(qPartyRelationshipE.ctxEnabled.like('%' + parentTypeId + '%'))
							)))
					)
					.and(qPartyRelationshipE.thruDate.isNull())
					.and(qPartyRelationshipE.partyIdTo.eq(qUserLogin.partyId))) 
			
			.leftJoin(qPartyRelationshipZ).on(qPartyRelationshipZ.roleTypeIdTo.eq(qPartyRelationshipUO.roleTypeIdFrom)
					.and(qPartyRelationshipZ.partyIdTo.eq(qPartyRelationshipUO.partyIdFrom))
					.and(qPartyRelationshipZ.partyRelationshipTypeId.eq("GROUP_ROLLUP"))
					.and(qPartyRelationshipZ.thruDate.isNull()))
			
			.leftJoin(qPartyRelationshipY).on(qPartyRelationshipY.roleTypeIdFrom.eq(qPartyRelationshipZ.roleTypeIdFrom)
					.and(qPartyRelationshipY.partyIdFrom.eq(qPartyRelationshipZ.partyIdFrom))
					.and(qPartyRelationshipY.partyRelationshipTypeId.eq("ORG_RESPONSIBLE")
							.or((qPartyRelationshipY.partyRelationshipTypeId.eq("ORG_DELEGATE").and(
									qPartyRelationshipY.ctxEnabled.isNull().or(qPartyRelationshipY.ctxEnabled.like('%' + parentTypeId + '%'))
							)))
					)
					.and(qPartyRelationshipY.thruDate.isNull())
					.and(qPartyRelationshipY.partyIdTo.eq(qUserLogin.partyId)))
			
			.leftJoin(qPartyRelationshipZ2).on(qPartyRelationshipZ2.roleTypeIdTo.eq(qPartyRelationshipZ.roleTypeIdFrom)
					.and(qPartyRelationshipZ2.partyIdTo.eq(qPartyRelationshipZ.partyIdFrom))
					.and(qPartyRelationshipZ2.partyRelationshipTypeId.eq("GROUP_ROLLUP"))
					.and(qPartyRelationshipZ2.thruDate.isNull()))
			
			.leftJoin(qPartyRelationshipY2).on(qPartyRelationshipY2.roleTypeIdFrom.eq(qPartyRelationshipZ2.roleTypeIdFrom)
					.and(qPartyRelationshipY2.partyIdFrom.eq(qPartyRelationshipZ2.partyIdFrom))
					.and(qPartyRelationshipY2.partyRelationshipTypeId.eq("ORG_RESPONSIBLE")
							.or((qPartyRelationshipY2.partyRelationshipTypeId.eq("ORG_DELEGATE").and(
									qPartyRelationshipY2.ctxEnabled.isNull().or(qPartyRelationshipY2.ctxEnabled.like('%' + parentTypeId + '%'))
							)))
					)
					.and(qPartyRelationshipY2.thruDate.isNull())
					.and(qPartyRelationshipY2.partyIdTo.eq(qUserLogin.partyId)))
			
			.where(predicates.toArray(new Predicate[0]))
			.orderBy(qParty.partyName.asc());           
           
        }
        
        return tupleSQLQuery;
    }
	
	
	public SQLQuery<?> getFilterQuery(SQLQuery<?> tupleSQLQuery, QPartyRole qPartyRole, String userLoginId, String parentTypeId) {
    	
    	String permission = ContextPermissionPrefixEnum.getPermissionPrefix(parentTypeId);
    	
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
						.and(qPartyRelationshipE.partyRelationshipTypeId.eq("ORG_RESPONSIBLE")
								.or((qPartyRelationshipE.partyRelationshipTypeId.eq("ORG_DELEGATE").and(
										qPartyRelationshipE.ctxEnabled.isNull().or(qPartyRelationshipE.ctxEnabled.like('%' + parentTypeId + '%'))
								)))
						).and(qPartyRelationshipE.thruDate.isNull())
						.and(qPartyRelationshipE.partyIdTo.eq(qUserLogin.partyId)))   
            
            .leftJoin(qPartyRelationshipZ).on(qPartyRelationshipZ.roleTypeIdTo.eq(qPartyRole.roleTypeId)
						.and(qPartyRelationshipZ.partyIdTo.eq(qPartyRole.partyId))
						.and(qPartyRelationshipZ.partyRelationshipTypeId.eq("GROUP_ROLLUP"))
						.and(qPartyRelationshipZ.thruDate.isNull()))             
            .leftJoin(qPartyRelationshipY).on(qPartyRelationshipY.roleTypeIdFrom.eq(qPartyRelationshipZ.roleTypeIdFrom)
						.and(qPartyRelationshipY.partyIdFrom.eq(qPartyRelationshipZ.partyIdFrom))
						.and(qPartyRelationshipY.partyRelationshipTypeId.eq("ORG_RESPONSIBLE")
							.or((qPartyRelationshipY.partyRelationshipTypeId.eq("ORG_DELEGATE").and(
									qPartyRelationshipY.ctxEnabled.isNull().or(qPartyRelationshipY.ctxEnabled.like('%' + parentTypeId + '%'))
							)))
						)
						.and(qPartyRelationshipY.thruDate.isNull())
						.and(qPartyRelationshipY.partyIdTo.eq(qUserLogin.partyId)))
            .leftJoin(qPartyRelationshipZ2).on(qPartyRelationshipZ2.roleTypeIdTo.eq(qPartyRelationshipZ.roleTypeIdFrom)
						.and(qPartyRelationshipZ2.partyIdTo.eq(qPartyRelationshipZ.partyIdFrom))
						.and(qPartyRelationshipZ2.partyRelationshipTypeId.eq("GROUP_ROLLUP"))
						.and(qPartyRelationshipZ2.thruDate.isNull()))
			.leftJoin(qPartyRelationshipY2).on(qPartyRelationshipY2.roleTypeIdFrom.eq(qPartyRelationshipZ2.roleTypeIdFrom)
					.and(qPartyRelationshipY2.partyIdFrom.eq(qPartyRelationshipZ2.partyIdFrom))
					.and(qPartyRelationshipY2.partyRelationshipTypeId.eq("ORG_RESPONSIBLE")
							.or((qPartyRelationshipY2.partyRelationshipTypeId.eq("ORG_DELEGATE").and(
									qPartyRelationshipY2.ctxEnabled.isNull().or(qPartyRelationshipY2.ctxEnabled.like('%' + parentTypeId + '%'))
							)))
					)
					.and(qPartyRelationshipY2.thruDate.isNull())
					.and(qPartyRelationshipY2.partyIdTo.eq(qUserLogin.partyId)));
            //GN-4828
            //List<Predicate> predicates = new ArrayList<>();
			BooleanBuilder builder = new BooleanBuilder();
            if (isOrgMgr) {
           	 //predicates.add(qPartyRelationshipE.partyIdTo.isNotNull());
           	 builder.or(qPartyRelationshipE.partyIdTo.isNotNull());
            }
            if (isSup) {
           	 //predicates.add(qPartyRelationshipY.partyIdTo.isNotNull());
           	 builder.or(qPartyRelationshipY.partyIdTo.isNotNull());
            }
            if (isTop) {
           	 //predicates.add(qPartyRelationshipY2.partyIdTo.isNotNull());
           	 builder.or(qPartyRelationshipY2.partyIdTo.isNotNull());
            }
			tupleSQLQuery.where(builder);
            //tupleSQLQuery.where(predicates.toArray(new Predicate[0]));
        }
        return tupleSQLQuery;
    }
}
