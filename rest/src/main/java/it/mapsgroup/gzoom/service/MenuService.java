package it.mapsgroup.gzoom.service;

import static it.mapsgroup.gzoom.security.Principals.principal;

import java.util.*;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.mapsgroup.gzoom.model.AbstractMenu;
import it.mapsgroup.gzoom.model.Permissions;
import it.mapsgroup.gzoom.model.RootMenu;
import it.mapsgroup.gzoom.model.SecurityPermission;
import it.mapsgroup.gzoom.querydsl.dao.ContentAndAttributesDao;
import it.mapsgroup.gzoom.querydsl.dao.PermissionDao;
import it.mapsgroup.gzoom.querydsl.dao.UserLoginDao;
import it.mapsgroup.gzoom.querydsl.dto.ContentAndAttributes;

/**
 * Profile service.
 *
 */
@Service
public class MenuService {

    private final ContentAndAttributesDao contentAndAttributeDao;
    private final PermissionDao permissionDao;
    private final ProfileService profileService;

    @Autowired
    public MenuService(ContentAndAttributesDao contentAndAttributeDao, PermissionDao permissionDao, ProfileService profileService) {
        this.contentAndAttributeDao = contentAndAttributeDao;
        this.permissionDao = permissionDao;
        this.profileService = profileService;
    }

    public AbstractMenu getMenu(HttpServletRequest req) {
        Map<String, Map<String, Object>> map = new HashMap<>();
        Permissions perms = profileService.getUserPermission(req);
        Map<String, List<String>> mappa = perms.getPermissions();
        List<String> keys = new ArrayList<String>(mappa.keySet());
        List<ContentAndAttributes> contentAndAttribute = contentAndAttributeDao.getValidMenu(keys);
        // List<ContentAndAttributes> contentAndAttribute = contentAndAttributeDao.getMenu("GP_MENU");
        contentAndAttribute.forEach(m -> //
            System.out.println("link " + m.getContentId() + " " + m.getLink().getAttrValue())
        // map.put("key", (Map<String, Object>) contentAndAttributeDao.getMenu(m.getContentId()))
        );
        return null;
    }
}
