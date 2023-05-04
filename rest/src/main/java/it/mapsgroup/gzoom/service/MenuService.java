package it.mapsgroup.gzoom.service;

import static com.querydsl.core.types.Projections.bean;
import static it.mapsgroup.gzoom.querydsl.QBeanUtils.merge;
import static it.mapsgroup.gzoom.security.Principals.principal;
import static org.slf4j.LoggerFactory.getLogger;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import com.querydsl.core.Tuple;
import com.querydsl.core.group.GroupBy;
import com.querydsl.core.types.QBean;
import com.querydsl.core.types.dsl.DateExpression;
import com.querydsl.sql.SQLBindings;
import com.querydsl.sql.SQLQuery;
import com.querydsl.sql.SQLQueryFactory;
import it.mapsgroup.gzoom.querydsl.dto.*;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.mapsgroup.gzoom.model.FolderMenu;
import it.mapsgroup.gzoom.model.LeafMenu;
import it.mapsgroup.gzoom.model.Permissions;
import it.mapsgroup.gzoom.querydsl.dao.ContentAndAttributesDao;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.transaction.support.TransactionSynchronizationManager;

/**
 * Profile service.
 *
 */
@Service
public class MenuService {
    private static final Logger LOG = getLogger(MenuService.class);
    private static final String ROOT_MENU_ID = "GP_MENU";
    private static final String MENU_UI_LABELS = "MenuUiLabels";
    private static final String DOT = ".";
    
    private final ContentAndAttributesDao contentAndAttributeDao;
    private final ProfileService profileService;
    private final SQLQueryFactory queryFactory;
    
    @Autowired
    public MenuService(ContentAndAttributesDao contentAndAttributeDao, ProfileService profileService, SQLQueryFactory queryFactory) {
        this.contentAndAttributeDao = contentAndAttributeDao;
        this.profileService = profileService;
        this.queryFactory = queryFactory;
    }

    public FolderMenu getMenu() {
        Permissions perms = profileService.getUserPermission();

        //Get permissions and Filter only permission with ADMIN or VIEW
        Map<String, List<String>> mappa = perms.getPermissions().entrySet().stream().filter(e ->
                e.getValue().contains("VIEW") || e.getValue().contains("ADMIN")
        ).collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue()));

        LOG.info("mappa2 " + mappa);
        List<String> keys = new ArrayList<String>(mappa.keySet());
        
        FolderMenu root = new FolderMenu();
        root.setId(ROOT_MENU_ID);
        
        if (keys.size() != 0) {
            List<ContentAndAttributes> links = contentAndAttributeDao.getValidMenu(keys, principal().getUserLoginId());
            List<ContentAndAttributes> folders = contentAndAttributeDao.getFolderMenu();

        	createMenu(root, folders, links);
            removeEmptyMenu(root);
        }    
        
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
                String label = link.getTitle().getAttrValue();
                leaf.setLabel(label.substring(label.lastIndexOf(DOT)+1));
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
                String label = item.getTitle().getAttrValue();
                folder.setLabel(label.substring(label.lastIndexOf(DOT)+1));
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

    @Transactional
    public String getHelpId(String contentIdTo) {
        if (TransactionSynchronizationManager.isActualTransactionActive()) {
            TransactionStatus status = TransactionAspectSupport.currentTransactionStatus();
            status.getClass();
        }

        //DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        //LocalDateTime now = LocalDateTime.now();

        QContentAssoc qContentAssoc = QContentAssoc.contentAssoc;

        SQLQuery<ContentAssoc> pSQLQuery = queryFactory.select(qContentAssoc)
                .from(qContentAssoc)
                .where(qContentAssoc.contentIdTo.eq(contentIdTo)
                        .and(qContentAssoc.contentAssocTypeId.eq("HELP")));
               // .and(qContentAssoc.fromDate.before(LocalDateTime.now())));

        SQLBindings bindings = pSQLQuery.getSQL();
        LOG.info("{}", bindings.getSQL());
        LOG.info("{}", bindings.getNullFriendlyBindings());
        QBean<ContentAssoc> contentassocs = bean(ContentAssoc.class, qContentAssoc.all());
        List<ContentAssoc> ret = pSQLQuery.transform(GroupBy.groupBy(qContentAssoc.contentId).list(contentassocs));
        LOG.info("size = {}", ret.size());
        return !ret.isEmpty()?ret.get(0).getContentId():"HELP_GP_HOMEPAGE";

    }

    @Transactional
    public String getMenuPath(String contentIdTo) {
        if (TransactionSynchronizationManager.isActualTransactionActive()) {
            TransactionStatus status = TransactionAspectSupport.currentTransactionStatus();
            status.getClass();
        }

        QContentAssoc qContentAssoc = QContentAssoc.contentAssoc;
        QContentAssoc qParent = new QContentAssoc("parent");

        SQLQuery<Tuple> pSQLQuery = queryFactory.select(qContentAssoc,qParent)
                .from(qContentAssoc)
                .join(qParent).on(qParent.contentIdTo.eq(qContentAssoc.contentId))
                .where(qContentAssoc.contentIdTo.eq(contentIdTo)
                .and(qContentAssoc.contentAssocTypeId.eq("TREE_CHILD"))
                .and(qParent.fromDate.isNull().or(DateExpression.currentDate(LocalDateTime.class).goe(qParent.fromDate)))
                .and(qParent.thruDate.isNull().or(DateExpression.currentDate(LocalDateTime.class).lt(qParent.thruDate))));

        SQLBindings bindings = pSQLQuery.getSQL();
        LOG.info("{}", bindings.getSQL());
        LOG.info("{}", bindings.getNullFriendlyBindings());

        QBean<ContentAssocMenu> contentAssocMenuQBean = bean(ContentAssocMenu.class,
                merge(qContentAssoc.all(),
                        bean(ContentAssoc.class, qParent.all()).as("parent")));

        List<ContentAssocMenu> ret = pSQLQuery.transform(GroupBy.groupBy(qContentAssoc.contentIdTo).list(contentAssocMenuQBean));
        LOG.info("size = {}", ret.size());
        return !ret.isEmpty()? "/"+ret.get(0).getParent().getContentId()
                .concat("/"+ret.get(0).getContentId())
                .concat("/"+ret.get(0).getContentIdTo()):"";
    }
}
