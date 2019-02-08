package it.mapsgroup.gzoom.querydsl.generator;

import static org.slf4j.LoggerFactory.getLogger;

import org.junit.Test;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.support.TransactionTemplate;

import com.querydsl.sql.SQLQueryFactory;

import it.mapsgroup.gzoom.querydsl.dao.AbstractDaoIT;
import it.mapsgroup.gzoom.querydsl.dto.CommunicationEvent;
import it.mapsgroup.gzoom.querydsl.dto.QCommunicationEvent;

public class CommunicationEventIT extends AbstractDaoIT {
	
	private static final Logger LOG = getLogger(CommunicationEventIT.class);
	
	@Autowired
    private SQLQueryFactory queryFactory;
	
	@Autowired
    TransactionTemplate transactionTemplate;
	
	@Test
    public void insert() throws Exception {
        transactionTemplate.execute(txStatus -> {
        	QCommunicationEvent commEvent = QCommunicationEvent.communicationEvent;        	
        	CommunicationEvent record = new CommunicationEvent();
            record.setCommunicationEventId("0001");
            record.setCommunicationEventTypeId("AUTO_EMAIL_COMM");
            long i = queryFactory.insert(commEvent).populate(record).execute();
            LOG.info("i " + i);

            return null;
        });
    }
	
	@Test
    public void delete() throws Exception {
        transactionTemplate.execute(txStatus -> {
        	QCommunicationEvent commEvent = QCommunicationEvent.communicationEvent;
            long i = queryFactory.delete(commEvent).where(commEvent.communicationEventId.eq("0001")).execute();

            LOG.info("i " + i);

            return null;
        });
    }

}
