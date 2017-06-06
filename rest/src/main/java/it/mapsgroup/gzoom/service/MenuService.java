package it.mapsgroup.gzoom.service;

import static it.mapsgroup.gzoom.security.Principals.principal;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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

    public FolderMenu getMenu() {
        Permissions perms = profileService.getUserPermission();
        Map<String, List<String>> mappa = perms.getPermissions();
        List<String> keys = new ArrayList<String>(mappa.keySet());
        
        List<ContentAndAttributes> links = contentAndAttributeDao.getValidMenu(keys, principal().getUserLoginId());
        List<ContentAndAttributes> folders = contentAndAttributeDao.getFolderMenu();
        FolderMenu root = new FolderMenu();
        root.setId("GP_MENU");
        
        cercoFiglioDi(root, folders, links);
        removeEmptyFolder(root);
        
        return root;
    }
    
    private boolean removeEmptyFolder(FolderMenu parent) {
        List<FolderMenu> children = parent.getChildren();
        boolean found = false;
        for (int i = 0; i < children.size(); i++ ) {
            FolderMenu folder = (FolderMenu) children.get(i);
            removeEmptyFolder(folder);
            if (!(folder instanceof LeafMenu) && folder.getChildren().isEmpty()) {
                children.remove(i);
            }
        }
        
        return found;
    }

    private void cercoFiglioDi(FolderMenu parent, List<ContentAndAttributes> folders, List<ContentAndAttributes> links) {
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
                leaf.setLabel(link.getTitle().getAttrValue()); // TODO recuperare dal file
                if (link.getClasses() != null) {
                    leaf.setClasses(link.getClasses().getAttrValue()); // TODO array o string?
                }
                leaf.setParams(null);
                List<FolderMenu> children = parent.getChildren();
                if (!children.isEmpty()) {
                    Iterator<FolderMenu> p = children.iterator();
                    while(p.hasNext()) {
                        FolderMenu pluto = p.next();
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
                folder.setLabel(item.getTitle().getAttrValue()); // TODO recuperare dal file
                if (item.getClasses() != null) {
                    folder.setClasses(item.getClasses().getAttrValue()); // TODO array o string?
                }
                List<FolderMenu> children = parent.getChildren();
                if (!children.isEmpty()) {
                    Iterator<FolderMenu> p = children.iterator();
                    while(p.hasNext()) {
                        FolderMenu pluto = p.next();
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
    }
}
