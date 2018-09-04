package it.mapsgroup.gzoom.querydsl.dao;

import com.querydsl.sql.SQLQueryFactory;
import it.mapsgroup.gzoom.persistence.common.SequenceGenerator;
import it.mapsgroup.gzoom.querydsl.dto.DataResource;
import it.mapsgroup.gzoom.querydsl.dto.QDataResource;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author Andrea Fossi.
 */
@Service
public class DataResourceDao extends AbstractDao {
    private static final Logger LOG = getLogger(DataResourceDao.class);

    private final SequenceGenerator sequenceGenerator;
    private SQLQueryFactory queryFactory;

    @Autowired
    public DataResourceDao(SequenceGenerator sequenceGenerator, SQLQueryFactory queryFactory) {
        this.sequenceGenerator = sequenceGenerator;
        this.queryFactory = queryFactory;
    }


    @Transactional
    public boolean create(DataResource record) {
        QDataResource dataResource = QDataResource.dataResource;
        String id = sequenceGenerator.getNextSeqId("DataResource");
        LOG.debug("PartyId[{}]", id);
        record.setDataResourceId(id);
        setCreatedTimestamp(record);
        record.setCreatedDate(record.getCreatedStamp());
        record.setLastModifiedDate(record.getLastUpdatedStamp());
        long i = queryFactory.insert(dataResource).populate(record).execute();
        LOG.debug("created records: {}", i);

        return i > 0;
    }

}
