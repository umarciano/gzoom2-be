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
		param.put("isOrgMgr", permissionService.isFullAdmin(userLoginId, permission));
		param.put("isRole", permissionService.isFullAdmin(userLoginId, permission));
		param.put("isSup", permissionService.isFullAdmin(userLoginId, permission));
		//param.put("isTop", false); TODO
		param.put("userLoginId", userLoginId);
		
		return param;
	}
	
}
