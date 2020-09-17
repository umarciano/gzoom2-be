package com.mapsengineering.base.birt.util;


import it.mapsgroup.gzoom.querydsl.service.PermissionService;
import it.memelabs.smartnebula.spring.boot.config.ApplicationContextProvider;

public class UtilUser {

    public static final String MODULE = UtilUser.class.getName();

    /**
     * Controlla il permesso di un utente
     * @return
     */
    public static boolean hasUserPermission(String userLoginId, String permission) {

        PermissionService permissionService = ApplicationContextProvider.getApplicationContext().getBean(PermissionService.class);
        return permissionService.hasPermission(permission, userLoginId);
    }
}
