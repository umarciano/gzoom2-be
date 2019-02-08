package it.mapsgroup.gzoom.querydsl.dao;

import static org.slf4j.LoggerFactory.getLogger;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.querydsl.sql.SQLQueryFactory;

import it.mapsgroup.gzoom.persistence.common.SequenceGenerator;
import it.mapsgroup.gzoom.querydsl.dto.CommunicationEvent;
import it.mapsgroup.gzoom.querydsl.dto.QCommunicationEvent;

@Service
public class CommunicationEventDao extends AbstractDao {
	private static final Logger LOG = getLogger(CommunicationEventDao.class);

    private final SequenceGenerator sequenceGenerator;
    private SQLQueryFactory queryFactory;
    
    @Autowired
    public CommunicationEventDao(SequenceGenerator sequenceGenerator, SQLQueryFactory queryFactory) {
        this.sequenceGenerator = sequenceGenerator;
        this.queryFactory = queryFactory;
    }

    /**
     * create element CommunicationEvent
     * @param record
     * @return
     */
    @Transactional
    public String create(CommunicationEvent record) {
        QCommunicationEvent commEvent = QCommunicationEvent.communicationEvent;
        String id = sequenceGenerator.getNextSeqId("CommunicationEvent");
        LOG.debug("communicationEventId[{}]", id);
        record.setCommunicationEventId(id);
        setCreatedTimestamp(record);
        long i = queryFactory.insert(commEvent).populate(record).execute();
        LOG.debug("created records: {}", i);
        return id;
    }
    
    /**
     * Update element CommunicationEvent
     * @param record
     * @return
     */
    @Transactional
    public boolean update(CommunicationEvent record) {
    	QCommunicationEvent commEvent = QCommunicationEvent.communicationEvent;
        setUpdateTimestamp(record);
        long i = queryFactory.update(commEvent).populate(record).execute();
        LOG.debug("updated records: {}", i);

        return i > 0;
    }
	
}
