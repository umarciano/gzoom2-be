package it.mapsgroup.gzoom.querydsl.generator;

import static com.querydsl.core.types.Projections.bean;
import static it.mapsgroup.gzoom.querydsl.QBeanUtils.merge;
import static org.slf4j.LoggerFactory.getLogger;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.group.GroupBy;
import com.querydsl.core.types.QBean;
import com.querydsl.sql.SQLBindings;
import com.querydsl.sql.SQLQuery;
import com.querydsl.sql.SQLQueryFactory;

import it.mapsgroup.gzoom.querydsl.dao.AbstractDaoIT;
import it.mapsgroup.gzoom.querydsl.dto.*;

/**
 * @author Andrea Fossi.
 */

public class ContentAndAttributesIT extends AbstractDaoIT {
    private static final Logger LOG = getLogger(ContentAndAttributesIT.class);

    @Autowired
    private SQLQueryFactory queryFactory;

    @Test
    @Transactional
    public void select() throws Exception {
        QContent qContent = QContent.content;
        QContentAssoc qContentAssoc = QContentAssoc.contentAssoc;
        QContentAttribute qContentAttrTitle = new QContentAttribute("tit");
        QContentAttribute qContentAttrLink = new QContentAttribute("lin");

        QBean<ContentAndAttributes> contentAndAttributesExQBean =
                bean(ContentAndAttributes.class,
                        merge(qContent.all(),
                                bean(ContentAttribute.class, qContentAttrTitle.all()).as("title"),
                                bean(ContentAttribute.class, qContentAttrLink.all()).as("link")));
                
                
            List<ContentAndAttributes> ret = queryFactory.select(qContent, qContentAttrTitle)
                    .from(qContent)
                    .innerJoin(qContent._contentasscTo, qContentAssoc)
                    .innerJoin(qContentAttrTitle).on(qContentAttrTitle.contentId.eq(qContentAssoc.contentIdTo).and(qContentAttrTitle.attrName.eq("title")))
                    .leftJoin(qContentAttrLink).on(qContentAttrLink.contentId.eq(qContentAssoc.contentIdTo).and(qContentAttrLink.attrName.eq("link")))
                    .where(qContentAssoc.contentId.eq("GP_MENU"))
                    .orderBy(qContentAssoc.sequenceNum.asc())
                    .transform(GroupBy.groupBy(qContent.contentId).list(contentAndAttributesExQBean));
            
            System.out.println("ret.size() " + ret.size());
            System.out.println("ret " + ret.get(0).getContentId());
            if (ret.get(0).getLink() != null) {
                System.out.println("ret " + ret.get(0).getLink().getAttrValue());
            }
            System.out.println("ret " + ret.get(0).getTitle().getAttrValue());
    }
    
    @Test
    @Transactional
    public void selectWithPermission() throws Exception {
        List<String> keys = new ArrayList<String>();
        keys.add("ACCOUNTING");
        keys.add("WORKEFFORT");
        String userLoginId = "admin";
        QContent qContent = QContent.content;
        QContentAssoc qContentAssoc = QContentAssoc.contentAssoc;
        QContentAttribute qContentAttrTitle = new QContentAttribute("tit");
        QContentAttribute qContentAttrLink = new QContentAttribute("lin");
        QContentAttribute qContentAttrClasses = new QContentAttribute("cla");
        QSecurityGroupContent qsgp = QSecurityGroupContent.securityGroupContent;
        QUserLoginSecurityGroup qulsg = QUserLoginSecurityGroup.userLoginSecurityGroup;
        
        QBean<ContentAndAttributes> contentAndAttributesExQBean = 
                bean(ContentAndAttributes.class,
                merge(qContent.all(),
                        bean(ContentAttribute.class, qContentAttrTitle.all()).as("title"),
                        bean(ContentAttribute.class, qContentAttrLink.all()).as("link"),
                        bean(ContentAttribute.class, qContentAttrClasses.all()).as("classes")));
        
        BooleanBuilder builder = new BooleanBuilder();
        for (String key : keys) {
            builder.or(qContentAttrLink.attrValue.like("/" + key + "%"));
        }
        
        SQLQuery<Tuple> tupleSQLQuery = queryFactory.select(qContent, qContentAttrTitle)
                .from(qContent)
                .innerJoin(qContent._contentasscTo, qContentAssoc)
                .innerJoin(qContentAttrTitle).on(qContentAttrTitle.contentId.eq(qContentAssoc.contentIdTo).and(qContentAttrTitle.attrName.eq("title")))
                .innerJoin(qContentAttrLink).on(qContentAttrLink.contentId.eq(qContentAssoc.contentIdTo).and(qContentAttrLink.attrName.eq("link")))
                .where(builder
                            .and(qContentAssoc.contentAssocTypeId.eq("TREE_CHILD"))
                                .and(
                                        queryFactory.from(qulsg)
                                                .leftJoin(qsgp).on(qulsg.groupId.eq(qsgp.groupId))
                                                .where(qulsg.userLoginId.eq(userLoginId), qsgp.contentId.eq(qContentAssoc.contentIdTo)).notExists())
                )
                // .orderBy(qContentAssoc.sequenceNum.asc());
                .orderBy(qContent.contentId.asc());
        SQLBindings bindings = tupleSQLQuery.getSQL();
        LOG.info("{}",bindings.getSQL());
        LOG.info("{}",bindings.getNullFriendlyBindings());
        List<ContentAndAttributes> ret = tupleSQLQuery
                .transform(GroupBy.groupBy(qContent.contentId).list(contentAndAttributesExQBean));
        
        System.out.println("ret.size() " + ret.size()); // 48
        ret.forEach(c -> {
            final String contentId = c.getContentId();
            final String value = " - " + c.getTitle().getAttrValue();
            if (c.getLink() != null) {
                System.out.println("content = " + contentId + value + " - " + c.getLink().getAttrValue());
            }else {
                System.out.println("content " + contentId + value);
            }
        });
        
    }
}
