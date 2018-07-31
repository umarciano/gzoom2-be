package it.mapsgroup.gzoom.querydsl.dao;

import com.querydsl.core.group.GroupBy;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.QBean;
import com.querydsl.sql.SQLBindings;
import com.querydsl.sql.SQLQuery;
import com.querydsl.sql.SQLQueryFactory;
import it.mapsgroup.gzoom.persistence.common.SequenceGenerator;
import it.mapsgroup.gzoom.querydsl.dto.Party;
import it.mapsgroup.gzoom.querydsl.dto.Person;
import it.mapsgroup.gzoom.querydsl.dto.QParty;
import it.mapsgroup.gzoom.querydsl.dto.QPerson;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author Andrea Fossi.
 */
@Service
public class PartyDao extends AbstractDao {
    private static final Logger LOG = getLogger(PartyDao.class);

    private final SequenceGenerator sequenceGenerator;
    private SQLQueryFactory queryFactory;

    @Autowired
    public PartyDao(SequenceGenerator sequenceGenerator, SQLQueryFactory queryFactory) {
        this.sequenceGenerator = sequenceGenerator;
        this.queryFactory = queryFactory;
    }

    @Transactional
    public boolean create(Party record) {
        QParty party = QParty.party;
        String id = sequenceGenerator.getNextSeqId("Party");
        LOG.debug("PartyId[{}]", id);
        record.setPartyId(id);
        setCreatedTimestamp(record);
        long i = queryFactory.insert(party).populate(record).execute();
        LOG.debug("created records: {}", i);

        return i > 0;
    }

    /**
     * TODO implement
     *
     * @param record
     * @return
     */
    @Transactional
    public boolean update(Party record) {
        QParty party = QParty.party;
        record.setDescription("Primo Party " + record.getPartyId());
        setUpdateTimestamp(record);
        long i = queryFactory.update(party).populate(record).execute();
        LOG.debug("updated records: {}", i);

        return i > 0;
    }
    
    @Transactional
    public List<Party> getPartys() {
        if (TransactionSynchronizationManager.isActualTransactionActive()) {
            TransactionStatus status = TransactionAspectSupport.currentTransactionStatus();
            status.getClass();
        }
        QParty qParty = QParty.party;
        SQLQuery<Party> pSQLQuery = queryFactory.select(qParty).from(qParty).where(qParty.partyTypeId.eq("PERSON")).orderBy(qParty.partyName.asc());
        SQLBindings bindings = pSQLQuery.getSQL();
        LOG.info("{}", bindings.getSQL());
        LOG.info("{}", bindings.getBindings());
        QBean<Party> partys = Projections.bean(Party.class, qParty.all());
        List<Party> ret = pSQLQuery.transform(GroupBy.groupBy(qParty.partyId).list(partys));
        LOG.info("size = {}", ret.size());
        return ret;
    }

}
