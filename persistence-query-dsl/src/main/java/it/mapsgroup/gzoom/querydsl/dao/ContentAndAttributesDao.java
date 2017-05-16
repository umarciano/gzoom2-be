package it.mapsgroup.gzoom.querydsl.dao;

import com.querydsl.core.group.GroupBy;
import com.querydsl.core.types.QBean;
import com.querydsl.sql.SQLQueryFactory;
import it.mapsgroup.gzoom.querydsl.dto.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.querydsl.core.types.Projections.bean;
import static it.mapsgroup.gzoom.querydsl.QBeanUtils.merge;

@Service
public class ContentAndAttributesDao {

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
                .where(qContentAssoc.contentId.eq(contentId))
                .orderBy(qContentAssoc.sequenceNum.asc())
                .transform(GroupBy.groupBy(qContent.contentId).list(contentAndAttributesExQBean));
        return ret;
    }

    @Transactional
    public List<ContentAndAttributes> getValidMenu(List<String> keys) {
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
        
        // .where(survey1.id.eq(employee.id)).exists());
        
        System.out.println("ret.size() " + ret.size());
        System.out.println("ret " + ret.get(0).getContentId());
        if (ret.get(0).getLink() != null) {
            System.out.println("ret " + ret.get(0).getLink().getAttrValue());
        }
        System.out.println("ret " + ret.get(0).getTitle().getAttrValue());
        return ret;
    }
}
