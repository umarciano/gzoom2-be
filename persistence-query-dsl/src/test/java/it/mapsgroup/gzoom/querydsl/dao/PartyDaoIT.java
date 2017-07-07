package it.mapsgroup.gzoom.querydsl.dao;

import static org.slf4j.LoggerFactory.getLogger;

import org.junit.Test;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import it.mapsgroup.gzoom.querydsl.dto.Party;

public class PartyDaoIT extends AbstractDaoIT {
    private static final Logger LOG = getLogger(PartyDaoIT.class);

    @Autowired
    TransactionTemplate transactionTemplate;

    @Autowired
    PlatformTransactionManager txManager;

    @Autowired
    PartyDao partyDao;

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
