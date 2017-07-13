package it.mapsgroup.gzoom.querydsl.generator;

import com.querydsl.sql.SQLQueryFactory;
import it.mapsgroup.gzoom.persistence.common.SequenceGenerator;
import it.mapsgroup.gzoom.querydsl.dao.AbstractDaoIT;
import it.mapsgroup.gzoom.querydsl.dao.PartyDao;
import it.mapsgroup.gzoom.querydsl.dto.Party;
import it.mapsgroup.gzoom.querydsl.dto.QParty;
import org.junit.Test;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;

import static org.slf4j.LoggerFactory.getLogger;

public class PartyDaoIT extends AbstractDaoIT {
    private static final Logger LOG = getLogger(PartyDaoIT.class);

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

    @Autowired
    PartyDao partyDao;

    @Test
    public void insert() throws Exception {
        transactionTemplate.execute(txStatus -> {
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

    @Test
    public void daoInsert() throws Exception {
        transactionTemplate.execute(txStatus -> {

            Party record = new Party();
            record.setDescription("Primo Party " + System.currentTimeMillis());
            partyDao.create(record);
            LOG.debug("i" + record.getPartyId());

            return null;
        });

    }


}
