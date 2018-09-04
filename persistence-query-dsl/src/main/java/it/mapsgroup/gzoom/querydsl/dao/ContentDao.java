package it.mapsgroup.gzoom.querydsl.dao;

import com.querydsl.sql.SQLQueryFactory;
import it.mapsgroup.gzoom.persistence.common.SequenceGenerator;
import it.mapsgroup.gzoom.querydsl.dto.Content;
import it.mapsgroup.gzoom.querydsl.dto.QContent;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author Andrea Fossi.
 */
@Service
public class ContentDao extends AbstractDao {
    private static final Logger LOG = getLogger(ContentDao.class);

    private final SequenceGenerator sequenceGenerator;
    private SQLQueryFactory queryFactory;

    @Autowired
    public ContentDao(SequenceGenerator sequenceGenerator, SQLQueryFactory queryFactory) {
        this.sequenceGenerator = sequenceGenerator;
        this.queryFactory = queryFactory;
    }





    @Transactional
    public boolean create(Content record) {
        QContent Content = QContent.content;
        String id = sequenceGenerator.getNextSeqId("Content");
        LOG.debug("PartyId[{}]", id);
        record.setContentId(id);
        setCreatedTimestamp(record);
        record.setCreatedDate(record.getCreatedStamp());
        record.setLastModifiedDate(record.getLastModifiedDate());
        long i = queryFactory.insert(Content).populate(record).execute();
        LOG.debug("created records: {}", i);
        return i > 0;
    }


}
