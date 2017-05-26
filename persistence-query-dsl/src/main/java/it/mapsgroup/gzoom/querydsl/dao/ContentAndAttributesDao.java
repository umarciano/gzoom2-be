package it.mapsgroup.gzoom.querydsl.dao;

import static com.querydsl.core.types.Projections.bean;
import static it.mapsgroup.gzoom.querydsl.QBeanUtils.merge;
import static org.slf4j.LoggerFactory.getLogger;

import java.util.List;

import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.group.GroupBy;
import com.querydsl.core.types.QBean;
import com.querydsl.sql.SQLBindings;
import com.querydsl.sql.SQLQuery;
import com.querydsl.sql.SQLQueryFactory;

import it.mapsgroup.gzoom.querydsl.dto.*;

@Service
public class ContentAndAttributesDao {
    private static final Logger LOG = getLogger(ContentAndAttributesDao.class);

    private final SQLQueryFactory queryFactory;

    public ContentAndAttributesDao(SQLQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Transactional
    public List<ContentAndAttributes> getMenu(String contentId) {
        QContent qContent = QContent.content;
        QContentAssoc qContentAssoc = QContentAssoc.contentAssoc;
        QContentAttribute qContentAttrTitle = new QContentAttribute("tit");
        QContentAttribute qContentAttrLink = new QContentAttribute("lin");

        QBean<ContentAndAttributes> contentAndAttributesExQBean = bean(ContentAndAttributes.class,
                merge(qContent.all(), bean(ContentAttribute.class, qContentAttrTitle.all()).as("title"), bean(ContentAttribute.class, qContentAttrLink.all()).as("link")));

        List<ContentAndAttributes> ret = queryFactory.select(qContent, qContentAttrTitle)
                .from(qContent)
                .innerJoin(qContent._contentasscTo, qContentAssoc)
                .innerJoin(qContentAttrTitle).on(qContentAttrTitle.contentId.eq(qContentAssoc.contentIdTo).and(qContentAttrTitle.attrName.eq("title")))
                .leftJoin(qContentAttrLink).on(qContentAttrLink.contentId.eq(qContentAssoc.contentIdTo).and(qContentAttrLink.attrName.eq("link")))
                .where(qContentAssoc.contentId.eq(contentId))
                .orderBy(qContentAssoc.sequenceNum.asc())
                .transform(GroupBy.groupBy(qContent.contentId).list(contentAndAttributesExQBean));
        return ret;
    }

    @Transactional
    public List<ContentAndAttributes> getValidMenu(List<String> keys, String userLoginId) {
        QContent qContent = QContent.content;
        QContentAssoc qContentAssoc = QContentAssoc.contentAssoc;
        QContentAttribute qContentAttrTitle = new QContentAttribute("tit");
        QContentAttribute qContentAttrLink = new QContentAttribute("lin");
        QSecurityGroupContent qsgp = QSecurityGroupContent.securityGroupContent;
        QUserLoginSecurityGroup qulsg = QUserLoginSecurityGroup.userLoginSecurityGroup;

        QBean<ContentAndAttributes> contentAndAttributesExQBean = bean(ContentAndAttributes.class,
                merge(qContent.all(), bean(ContentAttribute.class, qContentAttrTitle.all()).as("title"), bean(ContentAttribute.class, qContentAttrLink.all()).as("link"), bean(ContentAssoc.class, qContentAssoc.all()).as("parent")));

        BooleanBuilder builder = new BooleanBuilder();
        for (String key : keys) {
            if ("COMMONEXT".equals(key)) {
                key = "COMMONDATAEXT";
            }
            builder.or(qContentAttrLink.attrValue.like("/" + key + "%"));
        }

        SQLQuery<Tuple> tupleSQLQuery = queryFactory.select(qContent, qContentAttrTitle)
                .from(qContent)
                .innerJoin(qContent._contentasscTo, qContentAssoc)
                .innerJoin(qContentAttrTitle).on(qContentAttrTitle.contentId.eq(qContentAssoc.contentIdTo).and(qContentAttrTitle.attrName.eq("title")))
                .innerJoin(qContentAttrLink).on(qContentAttrLink.contentId.eq(qContentAssoc.contentIdTo).and(qContentAttrLink.attrName.eq("link")))
                .where(builder
                        .and(qContentAssoc.contentAssocTypeId.eq("TREE_CHILD"))
                        .and(queryFactory
                                .from(qulsg)
                                .leftJoin(qsgp).on(qulsg.groupId.eq(qsgp.groupId))
                                .where(qulsg.userLoginId.eq(userLoginId), qsgp.contentId.eq(qContentAssoc.contentIdTo)).notExists()))
                // .orderBy(qContentAssoc.sequenceNum.asc());
                .orderBy(qContent.contentId.asc());
        SQLBindings bindings = tupleSQLQuery.getSQL();
        LOG.info("{}", bindings.getSQL());
        LOG.info("{}", bindings.getBindings());
        List<ContentAndAttributes> ret = tupleSQLQuery.transform(GroupBy.groupBy(qContent.contentId).list(contentAndAttributesExQBean));
        LOG.info("ret.size() ", ret.size());
        return ret;
    }

    @Transactional
    public List<ContentAndAttributes> getParentMenu(List<String> parentIdList) {
        QContent qContent = QContent.content;
        QContentAssoc qContentAssoc = QContentAssoc.contentAssoc;
        QContentAttribute qContentAttrTitle = new QContentAttribute("tit");

        QBean<ContentAndAttributes> contentAndAttributesExQBean = bean(ContentAndAttributes.class,
                merge(qContent.all(), bean(ContentAttribute.class, qContentAttrTitle.all()).as("title"), bean(ContentAssoc.class, qContentAssoc.all()).as("parent")));

        BooleanBuilder builder = new BooleanBuilder();
        for (String parentId : parentIdList) {
            builder.or(qContentAssoc.contentIdTo.eq(parentId));
        }

        SQLQuery<Tuple> tupleSQLQuery = queryFactory.select(qContent, qContentAttrTitle)
                .from(qContent)
                .innerJoin(qContent._contentasscTo, qContentAssoc)
                .innerJoin(qContentAttrTitle).on(qContentAttrTitle.contentId.eq(qContentAssoc.contentIdTo).and(qContentAttrTitle.attrName.eq("title")))
                .where(builder
                        .and(qContentAssoc.contentAssocTypeId.eq("TREE_CHILD")))
                // .orderBy(qContentAssoc.sequenceNum.asc());
                .orderBy(qContent.contentId.asc());
        SQLBindings bindings = tupleSQLQuery.getSQL();
        LOG.info("{}", bindings.getSQL());
        LOG.info("{}", bindings.getBindings());
        List<ContentAndAttributes> ret = tupleSQLQuery.transform(GroupBy.groupBy(qContent.contentId).list(contentAndAttributesExQBean));

        return ret; // TODO
    }

    @Transactional
    public List<ContentAndAttributes> getFolderMenu() {
        QContent qContent = QContent.content;
        QContentAssoc qContentAssoc = QContentAssoc.contentAssoc;
        QContentAttribute qContentAttrTitle = new QContentAttribute("tit");
        QContentAttribute qContentAttrLink = new QContentAttribute("lin");

        QBean<ContentAndAttributes> contentAndAttributesExQBean = bean(ContentAndAttributes.class,
                merge(qContent.all(), bean(ContentAttribute.class, qContentAttrTitle.all()).as("title"), bean(ContentAssoc.class, qContentAssoc.all()).as("parent")));

        SQLQuery<Tuple> tupleSQLQuery = queryFactory.select(qContent, qContentAttrTitle)
                .from(qContent)
                .innerJoin(qContent._contentasscTo, qContentAssoc)
                .innerJoin(qContentAttrTitle).on(qContentAttrTitle.contentId.eq(qContentAssoc.contentIdTo).and(qContentAttrTitle.attrName.eq("title")))
                .where(qContentAssoc.contentAssocTypeId.eq("TREE_CHILD")
                    .and(queryFactory
                            .from(qContentAttrLink)
                            .where(qContentAttrLink.attrName.eq("link"), qContentAttrLink.contentId.eq(qContentAssoc.contentIdTo)).notExists()))
                // .orderBy(qContentAssoc.sequenceNum.asc());
                .orderBy(qContent.contentId.asc());
        SQLBindings bindings = tupleSQLQuery.getSQL();
        LOG.info("{}", bindings.getSQL());
        LOG.info("{}", bindings.getBindings());
        List<ContentAndAttributes> ret = tupleSQLQuery.transform(GroupBy.groupBy(qContent.contentId).list(contentAndAttributesExQBean));
        LOG.info("ret.size() ", ret.size());
        return ret; // TODO
    }
}
