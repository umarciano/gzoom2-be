package it.mapsgroup.gzoom.querydsl.generator;

import static org.slf4j.LoggerFactory.getLogger;

import javax.sql.DataSource;

import org.junit.Test;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import com.querydsl.sql.SQLQueryFactory;

import it.mapsgroup.gzoom.persistence.common.SequenceGenerator;
import it.mapsgroup.gzoom.querydsl.dao.AbstractDaoTest;
import it.mapsgroup.gzoom.querydsl.dto.Party;
import it.mapsgroup.gzoom.querydsl.dto.QParty;

public class PartyTest extends AbstractDaoTest {
    private static final Logger LOG = getLogger(PartyTest.class);

    @Autowired
    @Deprecated
    private DataSource mainDataSource;

    @Autowired
    private SequenceGenerator sequenceGenerator;

    @Autowired
    private SQLQueryFactory queryFactory;

    @Autowired
    TransactionTemplate transactionTemplate;

    @Autowired
    PlatformTransactionManager txManager;

    @Test
    public void insert() throws Exception {
        transactionTemplate.execute(txStatus->{
            QParty party = QParty.party;
            
            Party record = new Party();
            String id = sequenceGenerator.getNextSeqId("Party");
            
            LOG.debug("id" + id);
            record.setPartyId(id);
            record.setDescription("Primo Party " + record.getPartyId());
            long i = queryFactory.insert(party).populate(record).execute();
            LOG.debug("i" + i);
            
            return null;
        });
        
    }
    
}
