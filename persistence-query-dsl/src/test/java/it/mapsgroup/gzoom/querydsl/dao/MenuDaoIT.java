package it.mapsgroup.gzoom.querydsl.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import it.mapsgroup.gzoom.querydsl.dto.ContentAndAttributes;
import it.mapsgroup.gzoom.querydsl.dto.SecurityPermission;

/**
 * @author Andrea Fossi.
 */

public class MenuDaoIT extends AbstractDaoTest {
    @Autowired
    PermissionDao permissionDao;
    @Autowired
    ContentAndAttributesDao contentAndAttributesDao;

    @Test
    @Transactional
    public void findByContentId() throws Exception {
        List<SecurityPermission> permissions2 = permissionDao.getPermission("admin");
        assertNotNull(permissions2);
        assertEquals(2, permissions2.size());
    }
    
    @Test
    @Transactional
    public void findByUserLoginId() throws Exception {
        List<SecurityPermission> permissions = permissionDao.getPermission("admin");
        assertNotNull(permissions);
        assertEquals(2, permissions.size());
        
        List<String> keys = new ArrayList<String>();
        
        String permRegExp = "(((?i)(MGR|ROLE|ORG)?)_)";
        Pattern permPattern = Pattern.compile(permRegExp);
        
        permissions.forEach(r -> {
            String permissionId = r.getPermissionId();
            
            String[] permArray = permPattern.split(permissionId);
            
            for(int i = 0; i < permArray.length; i++) {
                System.out.println("permission " + i + " : " + permArray[i]);
            }
            keys.add(permArray[0]);
        });
        
        List<ContentAndAttributes> menus = contentAndAttributesDao.getValidMenu(keys, "admin");
        menus.forEach(m -> System.out.println("menu = " + m.toString()));
    }
}
