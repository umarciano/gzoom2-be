package it.mapsgroup.gzoom.querydsl.dao;

import static org.slf4j.LoggerFactory.getLogger;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.support.TransactionTemplate;

public class PartyRelationshipDaoIT extends AbstractDao {
	 private static final Logger LOG = getLogger(PartyRelationshipDaoIT.class);

	    @Autowired
	    TransactionTemplate transactionTemplate;

	    @Autowired
	    PartyRelationshipDao partyRelationshipDao;
}
