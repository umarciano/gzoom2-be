package it.mapsgroup.gzoom.querydsl.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.mapsgroup.gzoom.querydsl.dao.SecurityGroupPermissionDao;
import it.mapsgroup.gzoom.querydsl.dao.UserLoginSecurityGroupDao;
import it.mapsgroup.gzoom.querydsl.dto.SecurityGroupPermission;
import it.mapsgroup.gzoom.querydsl.dto.UserLoginSecurityGroup;


@Service
public class PermissionService {

	private static String SUP_ADMIN = "SUP_ADMIN";
	private static String TOP_ADMIN = "TOP_ADMIN";
	private static String ORG_ADMIN = "ORG_ADMIN";
	private static String ROLE_ADMIN = "ROLE_ADMIN";

	private static String BSCPERF = "BSCPERF";
		
	private UserLoginSecurityGroupDao userLoginSecurityGroupDao;
	private SecurityGroupPermissionDao securityGroupPermissionDao;
	
		
	@Autowired
	public PermissionService(UserLoginSecurityGroupDao userLoginSecurityGroupDao, SecurityGroupPermissionDao securityGroupPermissionDao) {
		this.userLoginSecurityGroupDao = userLoginSecurityGroupDao;
		this.securityGroupPermissionDao = securityGroupPermissionDao;
	}
	/**
	 * Data il localDispatcherName, ritorno il nome del gruppo di sicurezza
	 * @param localDispatcherName
	 * @return
	 */
	public String permissionLocalDispatcherName(String localDispatcherName) {
	    String permission = localDispatcherName.toUpperCase();
	    if ("STRATPERF".equals(permission)) {
	        permission = BSCPERF;
	    }
	    
	    return permission;
	}
	
	
	public boolean hasSecurityGroup(String userLoginId, String gruopId) {
        List<UserLoginSecurityGroup> list = userLoginSecurityGroupDao.getUserLoginSecurityGroups(userLoginId, gruopId);        
        if (list != null && list.size() > 0) {
            return true;
        }
	    return false;
	}
	
	//TODO ricontrollare
	public boolean hasPermission(String permissionId, String userLoginId) { 
		//getPermission
		List<UserLoginSecurityGroup> list = userLoginSecurityGroupDao.getUserLoginSecurityGroups(userLoginId);        
		for(UserLoginSecurityGroup ele: list) {
			List<SecurityGroupPermission> sgpList = securityGroupPermissionDao.getSecurityGroupPermissions(ele.getGroupId(), permissionId);
            if (sgpList != null && sgpList.size() > 0 ) return true;
        }
		return false;
	}
	
	
	public boolean isOrgMgr(String userLoginId, String permission) {
	    return hasPermission(permission + ORG_ADMIN, userLoginId);	    
	}

	public boolean isRole(String userLoginId, String permission) {
	    return hasPermission(permission + ROLE_ADMIN, userLoginId);
	}

	public boolean isSup(String userLoginId, String permission) {
	    return hasPermission(permission + SUP_ADMIN, userLoginId);
	}

	public boolean isTop(String userLoginId, String permission) {
	    return hasPermission(permission + TOP_ADMIN, userLoginId);
	}

	
}
