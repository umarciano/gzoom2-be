package it.mapsgroup.gzoom.querydsl.generator;

import com.querydsl.core.group.GroupBy;
import com.querydsl.core.types.QBean;
import com.querydsl.sql.SQLQueryFactory;
import it.mapsgroup.gzoom.querydsl.dao.AbstractDaoTest;
import it.mapsgroup.gzoom.querydsl.dto.*;
import org.junit.Test;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
<<<<<<< Updated upstream
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;
import java.util.List;
=======
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
>>>>>>> Stashed changes

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
    public void name3() throws Exception {

        QContent qContent = QContent.content;
        QContentAssoc qContentAssoc = QContentAssoc.contentAssoc;
        QContentAttribute qContentAttrTitle = new QContentAttribute("tit");
        QContentAttribute qContentAttrLink = new QContentAttribute("lin");

        QBean<ContentAndAttributes> contentAndAttributesExQBean =
                bean(ContentAndAttributes.class,
                        merge(qContent.all(),
                                bean(ContentAttribute.class, qContentAttrTitle.all()).as("title"),
                                bean(ContentAttribute.class, qContentAttrLink.all()).as("link")));
<<<<<<< Updated upstream


        List<ContentAndAttributes> ret = queryFactory.select(qContent, qContentAttrTitle)
                .from(qContent)
                .innerJoin(qContentAssoc)
                .innerJoin(qContentAttrTitle).on(qContentAttrTitle.contentId.eq(qContentAssoc.contentIdTo).and(qContentAttrTitle.attrName.eq("title")))
                .leftJoin(qContentAttrLink).on(qContentAttrLink.contentId.eq(qContentAssoc.contentIdTo).and(qContentAttrLink.attrName.eq("link")))
                .where(qContentAssoc.contentId.eq("GP_MENU"))
                .transform(GroupBy.groupBy(qContent.contentId).list(contentAndAttributesExQBean));

        System.out.println("ret.size() " + ret.size());
        System.out.println("ret " + ret);
        System.out.println("ret " + ret.get(0).getContentId());
        if (ret.get(0).getLink() != null) {
            System.out.println("ret " + ret.get(0).getLink().getAttrValue());
        }
        System.out.println("ret " + ret.get(0).getTitle().getAttrValue());
=======
                
                
                List<ContentAndAttributes> ret = queryFactory.select(qContent, qContentAttrTitle)
                        .from(qContent)
                        // TODO .innerJoin(qContentAssoc)
                        .innerJoin(qContentAttrTitle).on(qContentAttrTitle.contentId.eq(qContentAssoc.contentIdTo).and(qContentAttrTitle.attrName.eq("title")))
                        .leftJoin(qContentAttrLink).on(qContentAttrLink.contentId.eq(qContentAssoc.contentIdTo).and(qContentAttrLink.attrName.eq("link")))
                        .where(qContentAssoc.contentId.eq("GP_MENU"))
                        .transform(GroupBy.groupBy(qContent.contentId).list(contentAndAttributesExQBean));
                
                System.out.println("ret.size() " + ret.size());
                System.out.println("TxManager.stamp2 " + ((CustomTxManager) txManager).getStamp());
                System.out.println("ret " + ret);
                System.out.println("ret " + ret.get(0).getContentId());
                if (ret.get(0).getLink() != null) {
                    System.out.println("ret " + ret.get(0).getLink().getAttrValue());
                }
                System.out.println("ret " + ret.get(0).getTitle().getAttrValue());
                return null;
            });
            System.out.println("TxManager.stamp1 " + ((CustomTxManager) txManager).getStamp());
            return null;
        });
>>>>>>> Stashed changes
    }

    @Test
    @Transactional
    public void name2() throws Exception {
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
                .transform(GroupBy.groupBy(qContent.contentId).list(contentAndAttributesExQBean));

        System.out.println("ret.size() " + ret.size());
        System.out.println("ret " + ret);
        System.out.println("ret " + ret.get(0).getContentId());
        if (ret.get(0).getLink() != null) {
            System.out.println("ret " + ret.get(0).getLink().getAttrValue());
        }

    }

    @Test
    @Transactional
    public void name() throws Exception {

        QContent qContent = QContent.content;
        QContentAssoc qContentAssoc = QContentAssoc.contentAssoc;
        QContentAttribute qContentAttribute = QContentAttribute.contentAttribute;

        QBean<ContentAndAttributes> contentAndAttributeExQBean =
                bean(ContentAndAttributes.class,
                        merge(qContent.all(),
                                bean(ContentAttribute.class, qContentAttribute.all()).as("contentAttribute")));
<<<<<<< Updated upstream


        List<ContentAndAttributes> ret = queryFactory.select(qContent, qContentAttribute)
                .from(qContent)
                .innerJoin(qContent._contentasscTo, qContentAssoc)
                .innerJoin(qContentAttribute).on(qContentAttribute.contentId.eq(qContentAssoc.contentIdTo).and(qContentAttribute.attrName.eq("title")))
                //.leftJoin(qContentAttribute).on((qContentAttribute.contentId.eq(qContentAssoc.contentIdTo)).and(qContentAttribute.attrName.eq("title")))

                // .innerJoin(department).on(employee.departmentId.eq(department.id))))
                // QCustomer customer = QCustomer.customer;
                // QCompany company = QCompany.company;

                // .innerJoin(customer.company, company)
                // .leftJoin(company).on(customer.company.eq(company))
                // .innerJoin(qContentAttribute.contentAttr, )


                .where(qContentAssoc.contentId.eq("GP_MENU")
                        // .and(qContentAttribute.attrName.eq("title"))
                )
                .transform(GroupBy.groupBy(qContent.contentId).list(contentAndAttributeExQBean));

        System.out.println("ret.size() " + ret.size());
        System.out.println("ret " + ret);
=======
                
                
                List<ContentAndAttributes> ret = queryFactory.select(qContent, qContentAttribute)
                        .from(qContent)
                        .innerJoin(qContent._contentasscTo, qContentAssoc)
                        .innerJoin(qContentAttribute).on(qContentAttribute.contentId.eq(qContentAssoc.contentIdTo).and(qContentAttribute.attrName.eq("title")))
                      //.leftJoin(qContentAttribute).on((qContentAttribute.contentId.eq(qContentAssoc.contentIdTo)).and(qContentAttribute.attrName.eq("title")))
                        
                        // .innerJoin(department).on(employee.departmentId.eq(department.id))))
                        // QCustomer customer = QCustomer.customer;
                        // QCompany company = QCompany.company;

                        // .innerJoin(customer.company, company)
                        // .leftJoin(company).on(customer.company.eq(company))
                        
                        .where(qContentAssoc.contentId.eq("GP_MENU")
                               // .and(qContentAttribute.attrName.eq("title"))
                        )
                        .transform(GroupBy.groupBy(qContent.contentId).list(contentAndAttributeExQBean));
                
                System.out.println("ret.size() " + ret.size());
                System.out.println("TxManager.stamp2 " + ((CustomTxManager) txManager).getStamp());
                System.out.println("ret " + ret);
                return null;
            });
            System.out.println("TxManager.stamp1 " + ((CustomTxManager) txManager).getStamp());
            return null;
        });
>>>>>>> Stashed changes
    }
}
