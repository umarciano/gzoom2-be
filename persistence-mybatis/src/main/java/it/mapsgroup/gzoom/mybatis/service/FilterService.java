package it.mapsgroup.gzoom.mybatis.service;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.mapsgroup.gzoom.querydsl.service.PermissionService;

@Service
public class FilterService {

	private final PermissionService permissionService;
	
	@Autowired
	public FilterService(PermissionService permissionService) {
		this.permissionService = permissionService;
	}
	
	public HashMap<String, Object> setMapFilter(String userLoginId, String permission) {
		HashMap<String, Object> param = new HashMap<>();
		param.put("isFullAdmin", permissionService.isFullAdmin(userLoginId, permission));
		param.put("isOrgMgr", permissionService.isOrgMgr(userLoginId, permission));
		param.put("isRole", permissionService.isRole(userLoginId, permission));
		param.put("isSup", permissionService.isSup(userLoginId, permission));
		param.put("isTop", permissionService.isTop(userLoginId, permission));  
		param.put("userLoginId", userLoginId);
		
		return param;
	}
	
}
