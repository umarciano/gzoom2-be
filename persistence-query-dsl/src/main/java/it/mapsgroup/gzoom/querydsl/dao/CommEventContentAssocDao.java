package it.mapsgroup.gzoom.querydsl.dao;

import static org.slf4j.LoggerFactory.getLogger;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.querydsl.sql.SQLQueryFactory;

import it.mapsgroup.gzoom.querydsl.dto.CommEventContentAssoc;
import it.mapsgroup.gzoom.querydsl.dto.QCommEventContentAssoc;

@Service
public class CommEventContentAssocDao extends AbstractDao {
	private static final Logger LOG = getLogger(CommEventContentAssocDao.class);

    private SQLQueryFactory queryFactory;
    
    @Autowired
    public CommEventContentAssocDao(SQLQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    /**
     * create element CommunicationEvent
     * @param record
     * @return
     */
    @Transactional
    public boolean create(CommEventContentAssoc record) {
        QCommEventContentAssoc commEvent = QCommEventContentAssoc.commEventContentAssoc;
        setCreatedTimestamp(record);
        if (record.getFromDate() == null) {
        	record.setFromDate(LocalDateTime.now());
        }
        long i = queryFactory.insert(commEvent).populate(record).execute();
        LOG.debug("created records: {}", i);
        return i > 0;
    }
}
