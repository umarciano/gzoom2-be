package it.mapsgroup.gzoom.querydsl.generator;

import com.querydsl.core.Tuple;
import com.querydsl.core.group.GroupBy;
import com.querydsl.core.types.QBean;
import com.querydsl.sql.*;
import it.mapsgroup.gzoom.querydsl.dao.AbstractDaoTest;
import it.mapsgroup.gzoom.querydsl.dto.*;
import org.junit.Test;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;

import java.util.ArrayList;
import java.util.List;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.support.TransactionTemplate;

import com.querydsl.core.group.GroupBy;
import com.querydsl.core.types.QBean;
import com.querydsl.sql.SQLQueryFactory;

import static com.querydsl.core.types.Projections.bean;
import static it.mapsgroup.gzoom.querydsl.QBeanUtils.merge;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author Andrea Fossi.
 */

public class ContentAndAttributesTest extends AbstractDaoTest {
    private static final Logger LOG = getLogger(ContentAndAttributesTest.class);

    @Autowired
    @Deprecated
    private DataSource mainDataSource;

    @Autowired
    private SQLQueryFactory queryFactory;

    @Autowired
    TransactionTemplate transactionTemplate;

    @Autowired
    PlatformTransactionManager txManager;

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
    public void getValidMenu() throws Exception {
        // like String startRegExp2 = "(/accountingext|/workeffort)";
        List<String> keys = new ArrayList<String>();
        keys.add("ACCOUNTING");
        keys.add("WORKEFFORT");
        QContent qContent = QContent.content;
        QContentAssoc qContentAssoc = QContentAssoc.contentAssoc;
        QContentAttribute qContentAttrTitle = new QContentAttribute("tit");
        QContentAttribute qContentAttrLink = new QContentAttribute("lin");
        
        QBean<ContentAndAttributes> contentAndAttributesExQBean = 
                bean(ContentAndAttributes.class,
                merge(qContent.all(),
                        bean(ContentAttribute.class, qContentAttrTitle.all()).as("title"),
                        bean(ContentAttribute.class, qContentAttrLink.all()).as("link")));
        String regExp = String.join("|/", keys);
        regExp = "(/" + regExp + ")";
        
        System.out.println(regExp);
        List<ContentAndAttributes> ret = queryFactory.select(qContent, qContentAttrTitle)
                .from(qContent)
                .innerJoin(qContent._contentasscTo, qContentAssoc)
                .innerJoin(qContentAttrTitle).on(qContentAttrTitle.contentId.eq(qContentAssoc.contentIdTo).and(qContentAttrTitle.attrName.eq("title")))
                .innerJoin(qContentAttrLink).on(qContentAttrLink.contentId.eq(qContentAssoc.contentIdTo).and(qContentAttrLink.attrName.eq("link")))
                .where(qContentAttrLink.attrValue.matches(regExp))
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
    public void getValidMenuPerm() throws Exception {
        // like String startRegExp2 = "(/accountingext|/workeffort)";
        List<String> keys = new ArrayList<String>();
        keys.add("ACCOUNTING");
        keys.add("WORKEFFORT");
        String userLoginId = "admin";
        QContent qContent = QContent.content;
        QContentAssoc qContentAssoc = QContentAssoc.contentAssoc;
        QContentAttribute qContentAttrTitle = new QContentAttribute("tit");
        QContentAttribute qContentAttrLink = new QContentAttribute("lin");
        QSecurityGroupContent qsgp = QSecurityGroupContent.securityGroupContent;
        QUserLoginSecurityGroup qulsg = QUserLoginSecurityGroup.userLoginSecurityGroup;
        
        QBean<ContentAndAttributes> contentAndAttributesExQBean = 
                bean(ContentAndAttributes.class,
                merge(qContent.all(),
                        bean(ContentAttribute.class, qContentAttrTitle.all()).as("title"),
                        bean(ContentAttribute.class, qContentAttrLink.all()).as("link")));
        String regExp = String.join("|/", keys);
        regExp = "(^(/" + regExp + "))";
        
        System.out.println(regExp);

        SQLQuery<Tuple> tupleSQLQuery = queryFactory.select(qContent, qContentAttrTitle)
                .from(qContent)
                .innerJoin(qContent._contentasscTo, qContentAssoc)
                .innerJoin(qContentAttrTitle).on(qContentAttrTitle.contentId.eq(qContentAssoc.contentIdTo).and(qContentAttrTitle.attrName.eq("title")))
                .innerJoin(qContentAttrLink).on(qContentAttrLink.contentId.eq(qContentAssoc.contentIdTo).and(qContentAttrLink.attrName.eq("link")))

                .where(
                        qContentAttrLink.attrValue.matches(regExp)
                                .and(
                                        queryFactory.from(qulsg)
                                                .leftJoin(qsgp).on(qulsg.groupId.eq(qsgp.groupId))
                                                .where(qulsg.userLoginId.eq(userLoginId), qsgp.contentId.eq(qContentAssoc.contentIdTo)).notExists())
                )
                // .orderBy(qContentAssoc.sequenceNum.asc())
                .orderBy(qContent.contentId.asc());
        SQLBindings bindings = tupleSQLQuery.getSQL();
        LOG.info("{}",bindings.getSQL());
        LOG.info("{}",bindings.getBindings());
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
