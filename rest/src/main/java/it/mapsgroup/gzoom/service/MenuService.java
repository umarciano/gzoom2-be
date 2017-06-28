package it.mapsgroup.gzoom.service;

import static it.mapsgroup.gzoom.security.Principals.principal;
import static org.slf4j.LoggerFactory.getLogger;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.mapsgroup.gzoom.model.FolderMenu;
import it.mapsgroup.gzoom.model.LeafMenu;
import it.mapsgroup.gzoom.model.Permissions;
import it.mapsgroup.gzoom.querydsl.dao.ContentAndAttributesDao;
import it.mapsgroup.gzoom.querydsl.dto.ContentAndAttributes;

/**
 * Profile service.
 *
 */
@Service
public class MenuService {
    private static final Logger LOG = getLogger(MenuService.class);
    private static final String ROOT_MENU_ID = "GP_MENU";
    
    private final ContentAndAttributesDao contentAndAttributeDao;
    private final ProfileService profileService;
    
    @Autowired
    public MenuService(ContentAndAttributesDao contentAndAttributeDao, ProfileService profileService) {
        this.contentAndAttributeDao = contentAndAttributeDao;
        this.profileService = profileService;
    }

    public FolderMenu getMenu() {
        Permissions perms = profileService.getUserPermission();
        Map<String, List<String>> mappa = perms.getPermissions();
        List<String> keys = new ArrayList<String>(mappa.keySet());
        
        List<ContentAndAttributes> links = contentAndAttributeDao.getValidMenu(keys, principal().getUserLoginId());
        List<ContentAndAttributes> folders = contentAndAttributeDao.getFolderMenu();
        FolderMenu root = new FolderMenu();
        root.setId(ROOT_MENU_ID);
        
        createMenu(root, folders, links);
        removeEmptyMenu(root);
        
        return root;
    }
    
    private void createMenu(FolderMenu parent, List<ContentAndAttributes> folders, List<ContentAndAttributes> links) {
        String id = parent.getId();
        // first try link
        Iterator<ContentAndAttributes> l = links.iterator();
        while(l.hasNext()) {
            ContentAndAttributes link = l.next();
            if (id.equals(link.getParent().getContentId())) {
                LeafMenu leaf = new LeafMenu();
                leaf.setId(link.getContentId());
                leaf.setLabel(link.getTitle().getAttrValue()); // TODO recuperare dal file
                if (link.getClasses() != null) {
                    leaf.setClasses(link.getClasses().getAttrValue()); // TODO array o string?
                }
                leaf.setParams(null);
                
                parent.addChild(leaf, link.getContentId());
            }
        }
        
        // after try folder
        Iterator<ContentAndAttributes> iter = folders.iterator();
        while(iter.hasNext()) {
            ContentAndAttributes item = iter.next();
            if (id.equals(item.getParent().getContentId()) ) {
                FolderMenu folder = new FolderMenu();
                folder.setId(item.getContentId());
                folder.setLabel(item.getTitle().getAttrValue()); // TODO recuperare dal file
                if (item.getClasses() != null) {
                    folder.setClasses(item.getClasses().getAttrValue()); // TODO array o string?
                }
                parent.addChild(folder, item.getContentId());
                
                createMenu(folder, folders, links);
            }
        }
    }
    
    private void removeEmptyMenu(FolderMenu parent) {
        List<FolderMenu> children = parent.getChildren();
        for (int i = 0; i < children.size(); i++ ) {
            FolderMenu folder = (FolderMenu) children.get(i);
            removeEmptyMenu(folder);
            if (folder.getChildren().isEmpty()) {
                if (!(folder instanceof LeafMenu)) {
                    folder.setToRemove(true);
                }
            }
        }
        parent.setChildren(children.stream().filter(node -> !node.isToRemove()).collect(Collectors.toList()));
    }
    
}
