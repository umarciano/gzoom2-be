package it.mapsgroup.gzoom.querydsl.dao;

import static org.slf4j.LoggerFactory.getLogger;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.support.TransactionTemplate;

import it.mapsgroup.gzoom.querydsl.dao.AbstractDaoIT;
import it.mapsgroup.gzoom.querydsl.dao.PartyRoleDao;

public class PartyRoleDaoIT extends AbstractDaoIT {
	private static final Logger LOG = getLogger(PartyRoleDaoIT.class);

    @Autowired
    TransactionTemplate transactionTemplate;

    @Autowired
    PartyRoleDao  partyRoleDao;

}
