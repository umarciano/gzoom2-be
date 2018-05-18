package it.mapsgroup.gzoom.querydsl.generator;

import static org.slf4j.LoggerFactory.getLogger;

import java.time.LocalDateTime;
import java.util.Calendar;
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
import it.mapsgroup.gzoom.querydsl.dto.PartyRelationship;
import it.mapsgroup.gzoom.querydsl.dto.QPartyRelationship;

public class PartyRelationshipIT extends AbstractDaoIT {
	private static final Logger LOG = getLogger(PartyRelationshipIT.class);

	@Autowired
	private SQLQueryFactory queryFactory;

	@Autowired
	TransactionTemplate transactionTemplate;

	@Autowired
	PlatformTransactionManager txManager;

	@Test
	@Transactional
	public void select() throws Exception {
		QPartyRelationship partyRelationship = QPartyRelationship.partyRelationship;

		List<PartyRelationship> ret = queryFactory.select(partyRelationship).from(partyRelationship)
				.transform(GroupBy.groupBy(partyRelationship.partyIdTo).list(partyRelationship));

		System.out.println("name ret.size() " + ret.size());
		ret.forEach(p -> System.out.println("p " + p.getPartyIdTo()));
	}

	@Test
	public void insert() throws Exception {
		transactionTemplate.execute(txStatus -> {
			QPartyRelationship partyRelationship = QPartyRelationship.partyRelationship;

			PartyRelationship record = new PartyRelationship();
			record.setPartyIdTo("Company");
			record.setPartyIdFrom("Company");
			record.setRoleTypeIdTo("EMPLOYEE");
			record.setRoleTypeIdFrom("EMPLOYEE");
			record.setPartyRelationshipTypeId("GROUP_ROLLUP");
			record.setFromDate(LocalDateTime.now());
			long i = queryFactory.insert(partyRelationship).populate(record).execute();
			LOG.info("i " + i);

			return null;
		});
	}

	@Test
	public void update() throws Exception {
		transactionTemplate.execute(txStatus -> {
			// setUpdateTimestamp(record);

			QPartyRelationship partyRelationship = QPartyRelationship.partyRelationship;
			long i = queryFactory.update(partyRelationship).set(partyRelationship.valueUomId, "OTH_100")
					.where(partyRelationship.partyIdTo.eq("Company").and(partyRelationship.partyIdFrom.eq("Company"))
							.and(partyRelationship.roleTypeIdTo.eq("DUMMY_ROLE"))
							.and(partyRelationship.roleTypeIdFrom.eq("DUMMY_ROLE"))).execute();

			LOG.info("i " + i);

			return null;
		});
	}

	@Test
	public void delete() throws Exception {
		transactionTemplate.execute(txStatus -> {
			QPartyRelationship partyRelationship = QPartyRelationship.partyRelationship;
			long i = queryFactory.delete(partyRelationship)
					.where(partyRelationship.partyIdTo.eq("Company").and(partyRelationship.partyIdFrom.eq("Company"))
							.and(partyRelationship.roleTypeIdTo.eq("DUMMY_ROLE"))
							.and(partyRelationship.roleTypeIdFrom.eq("DUMMY_ROLE"))
							.and(partyRelationship.partyRelationshipTypeId.eq("GROUP_ROLLUP")))
					.execute();

			LOG.info("i " + i);

			return null;
		});
	}
}
