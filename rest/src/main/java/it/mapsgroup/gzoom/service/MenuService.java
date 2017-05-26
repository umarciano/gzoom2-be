package it.mapsgroup.gzoom.service;

import static it.mapsgroup.gzoom.security.Principals.principal;

import java.util.*;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.mapsgroup.gzoom.model.*;
import it.mapsgroup.gzoom.querydsl.dao.ContentAndAttributesDao;
import it.mapsgroup.gzoom.querydsl.dao.PermissionDao;
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

    public AbstractMenu getMenu() {
        Permissions perms = profileService.getUserPermission();
        Map<String, List<String>> mappa = perms.getPermissions();
        List<String> keys = new ArrayList<String>(mappa.keySet());
        
        List<ContentAndAttributes> links = contentAndAttributeDao.getValidMenu(keys, principal().getUserLoginId());
        List<ContentAndAttributes> folders = contentAndAttributeDao.getFolderMenu();
        
        RootMenu root = new RootMenu();
        root.setId("GP_MENU");
        
        cercoFiglioDi(root, folders, links);
        return root;
    }
    
    private void cercoFiglioDi(AbstractMenu parent, List<ContentAndAttributes> folders, List<ContentAndAttributes> links) {
        String id = parent.getId();
        // try children
        boolean foundChild = false; // found almost 1 child
        
        Iterator<ContentAndAttributes> l = links.iterator();
        while(l.hasNext()) {
            ContentAndAttributes link = l.next();
            if (id.equals(link.getParent().getContentId())) {
                boolean addChild = true; // child to add
                LeafMenu leaf = new LeafMenu();
                leaf.setId(link.getContentId());
                leaf.setLabel(link.getTitle().getAttrValue());
                leaf.setParams(null);
                List<AbstractMenu> children = parent.getChildren();
                if (!children.isEmpty()) {
                    Iterator<AbstractMenu> p = children.iterator();
                    while(p.hasNext()) {
                        AbstractMenu pluto = p.next();
                        if (pluto.getId().equals(link.getContentId())) {
                            addChild = false;
                            foundChild = true;
                            break;
                        }
                    }
                }
                if (addChild) {
                    parent.addChild(leaf);
                    foundChild = true;
                }
            }
        }
        
        Iterator<ContentAndAttributes> iter = folders.iterator();
        while(iter.hasNext()) {
            ContentAndAttributes item = iter.next();
            if (id.equals(item.getParent().getContentId()) ) {
                boolean addFolder = true; // child to add
                FolderMenu folder = new FolderMenu();
                folder.setId(item.getContentId());
                List<AbstractMenu> children = parent.getChildren();
                if (!children.isEmpty()) {
                    Iterator<AbstractMenu> p = children.iterator();
                    while(p.hasNext()) {
                        AbstractMenu pluto = p.next();
                        if (pluto.getId().equals(item.getContentId())) {
                            addFolder = false;
                            foundChild = true;
                            break;
                        }
                    }
                }
                if(addFolder) {
                    parent.addChild(folder);
                    foundChild = true;
                }
                cercoFiglioDi(folder, folders, links);
            }
        }
        System.out.println("dopo iter foundChild " + foundChild + " per id "+ id);
        if (!foundChild) {
            
        }
    }
}
