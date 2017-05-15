package it.mapsgroup.gzoom.querydsl.generator;

import com.querydsl.core.group.GroupBy;
import com.querydsl.core.types.QBean;
import com.querydsl.sql.SQLQueryFactory;
import it.mapsgroup.gzoom.querydsl.dao.AbstractDaoTest;
import it.mapsgroup.gzoom.querydsl.dto.*;
import org.junit.Test;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;
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
    public void name() throws Exception {

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
            System.out.println("ret " + ret.get(0).getContentId());
            if (ret.get(0).getLink() != null) {
                System.out.println("ret " + ret.get(0).getLink().getAttrValue());
            }
            System.out.println("ret " + ret.get(0).getTitle().getAttrValue());
    }
}
