package it.mapsgroup.gzoom.querydsl.generator;

import static org.slf4j.LoggerFactory.getLogger;

import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import com.querydsl.core.group.GroupBy;
import com.querydsl.sql.SQLQueryFactory;

import it.mapsgroup.gzoom.querydsl.dao.AbstractDaoIT;
import it.mapsgroup.gzoom.querydsl.dto.PartyRole;
import it.mapsgroup.gzoom.querydsl.dto.QPartyRole;



public class PartyRoleIT extends AbstractDaoIT {
	private static final Logger LOG = getLogger(PartyRoleIT.class);

    @Autowired
    private SQLQueryFactory queryFactory;

    @Autowired
    TransactionTemplate transactionTemplate;

    @Autowired
    PlatformTransactionManager txManager;
    
	@Test
    @Transactional
    public void select() throws Exception {
        QPartyRole partyRole = QPartyRole.partyRole;

        List<PartyRole> ret = queryFactory.select(partyRole).from(partyRole).transform(GroupBy.groupBy(partyRole.partyId).list(partyRole));

        System.out.println("name ret.size() " + ret.size());
        ret.forEach(p -> System.out.println("p " + p.getPartyId()));
    }
	
	@Test
    public void insert() throws Exception {
        transactionTemplate.execute(txStatus -> {
        	QPartyRole partyRole = QPartyRole.partyRole;
        	
        	PartyRole record = new PartyRole();
            record.setPartyId("Company");
            record.setRoleTypeId("EMPLOYEE");
            long i = queryFactory.insert(partyRole).populate(record).execute();
            LOG.info("i " + i);

            return null;
        });
    }	
	
    @Test
    public void delete() throws Exception {
        transactionTemplate.execute(txStatus -> {
        	QPartyRole partyRole = QPartyRole.partyRole;
            long i = queryFactory.delete(partyRole).where(partyRole.partyId.eq("Company").and(partyRole.roleTypeId.eq("EMPLOYEE"))).execute();

            LOG.info("i " + i);

            return null;
        });
    }
}
