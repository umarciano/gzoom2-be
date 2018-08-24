package com.mapsengineering.base.birt.util;


import java.util.List;

import it.mapsgroup.gzoom.querydsl.dao.SecurityGroupPermissionDao;
import it.mapsgroup.gzoom.querydsl.dao.UserLoginSecurityGroupDao;
import it.mapsgroup.gzoom.querydsl.dto.SecurityGroupPermission;
import it.mapsgroup.gzoom.querydsl.dto.UserLoginSecurityGroup;
import it.memelabs.smartnebula.spring.boot.config.ApplicationContextProvider;



public class UtilPermission {

	private static String SUP_ADMIN = "SUP_ADMIN";
	private static String TOP_ADMIN = "TOP_ADMIN";
	private static String ORG_ADMIN = "ORG_ADMIN";
	private static String ROLE_ADMIN = "ROLE_ADMIN";

	private static String BSCPERF = "BSCPERF";
		
	/**
	 * Data il localDispatcherName, ritorno il nome del gruppo di sicurezza
	 * @param localDispatcherName
	 * @return
	 */
	public static String permissionLocalDispatcherName(String localDispatcherName) {
	    String permission = localDispatcherName.toUpperCase();
	    if ("STRATPERF".equals(permission)) {
	        permission = BSCPERF;
	    }
	    
	    return permission;
	}
	
	
	public static boolean hasSecurityGroup(String userLoginId, String gruopId) {
        UserLoginSecurityGroupDao dao = ApplicationContextProvider.getApplicationContext().getBean(UserLoginSecurityGroupDao.class);
        List<UserLoginSecurityGroup> list = dao.getUserLoginSecurityGroups(userLoginId, gruopId);
        
        if (list != null && list.size() > 0) {
            return true;
        }
	    return false;
	}
	
	public static boolean hasPermission(String userLoginId, String permissionId) {
		UserLoginSecurityGroupDao dao = ApplicationContextProvider.getApplicationContext().getBean(UserLoginSecurityGroupDao.class);
		SecurityGroupPermissionDao sgpdao = ApplicationContextProvider.getApplicationContext().getBean(SecurityGroupPermissionDao.class);
        
		List<UserLoginSecurityGroup> list = dao.getUserLoginSecurityGroups(userLoginId);
        
		for(UserLoginSecurityGroup ele: list) {
			List<SecurityGroupPermission> sgpList = sgpdao.getSecurityGroupPermissions(ele.getGroupId(), permissionId);
            if (sgpList != null && sgpList.size() > 0 ) return true;
        }
		return false;
	}
	
	
	public static boolean isOrgMgr(String userLoginId, String permission) {
	    return UtilPermission.hasPermission(permission + ORG_ADMIN, userLoginId);	    
	}

	public static boolean isRole(String userLoginId, String permission) {
	    return UtilPermission.hasPermission(permission + ROLE_ADMIN, userLoginId);
	}

	public static boolean isSup(String userLoginId, String permission) {
	    return UtilPermission.hasPermission(permission + SUP_ADMIN, userLoginId);
	}

	public static boolean isTop(String userLoginId, String permission) {
	    return UtilPermission.hasPermission(permission + TOP_ADMIN, userLoginId);
	}

	
}
