package it.mapsgroup.gzoom.querydsl.dao;

import com.querydsl.sql.SQLQueryFactory;
import it.mapsgroup.gzoom.persistence.common.SequenceGenerator;
import it.mapsgroup.gzoom.querydsl.dto.Party;
import it.mapsgroup.gzoom.querydsl.dto.QParty;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public boolean create(Party record) {
        QParty party = QParty.party;
        String id = sequenceGenerator.getNextSeqId("Party");
        LOG.debug("PartyId[{}]", id);
        record.setPartyId(id);
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
    public boolean update(Party record) {
        QParty party = QParty.party;
        record.setDescription("Primo Party " + record.getPartyId());
        setUpdateTimestamp(record);
        long i = queryFactory.update(party).populate(record).execute();
        LOG.debug("updated records: {}", i);

        return i > 0;
    }
}
