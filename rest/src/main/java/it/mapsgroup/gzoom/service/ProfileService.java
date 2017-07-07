package it.mapsgroup.gzoom.service;

import static it.mapsgroup.gzoom.security.Principals.principal;

import java.util.List;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.mapsgroup.gzoom.model.Permissions;
import it.mapsgroup.gzoom.querydsl.dao.PermissionDao;
import it.mapsgroup.gzoom.querydsl.dto.SecurityPermission;

/**
 * Profile service.
 *
 */
@Service
public class ProfileService {

    private final PermissionDao permissionDao;

    @Autowired
    public ProfileService(PermissionDao permissionDao) {
        this.permissionDao = permissionDao;
    }

    public Permissions getUserPermission() {
        List<SecurityPermission> listSecurityPermission = permissionDao.getPermission(principal().getUserLoginId());
        Permissions permissions = new Permissions();
        
        String permRegExp = "(((?i)(MGR|ROLE|ORG)?)_)";
        Pattern permPattern = Pattern.compile(permRegExp);
        
        listSecurityPermission.forEach(r -> {
            String permissionId = r.getPermissionId();
            
            String[] permArray = permPattern.split(permissionId);
            permissions.addPermission(permArray[0], permArray[permArray.length-1]);
        });
        return permissions;
    }
}
