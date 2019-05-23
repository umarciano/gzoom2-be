package it.mapsgroup.gzoom.querydsl.dao;

import com.querydsl.core.group.GroupBy;
import com.querydsl.core.types.QBean;
import com.querydsl.sql.SQLBindings;
import com.querydsl.sql.SQLQuery;
import com.querydsl.sql.SQLQueryFactory;
import it.mapsgroup.gzoom.querydsl.dto.*;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.List;

import static com.querydsl.core.types.Projections.bean;
import static it.mapsgroup.gzoom.querydsl.QBeanUtils.merge;
import static org.slf4j.LoggerFactory.getLogger;

/**
 */
@Service
public class PartyNoteDao extends AbstractDao {
	private static final Logger LOG = getLogger(PartyNoteDao.class);

    private final SQLQueryFactory queryFactory;

    public PartyNoteDao(SQLQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }
    
    @Transactional
    public PartyNoteEx getPartyNote(String partyId, String noteName) {
        if (TransactionSynchronizationManager.isActualTransactionActive()) {
            TransactionStatus status = TransactionAspectSupport.currentTransactionStatus();
            status.getClass();
        }

        QPartyNote qPartyNote = QPartyNote.partyNote;
        QNoteData qNoteData = QNoteData.noteData;

        QBean<PartyNoteEx> tupleExQBean = bean(PartyNoteEx.class,
                merge(qPartyNote.all(), bean(NoteData.class, qNoteData.all()).as("noteData")));


        SQLQuery<PartyNote> tupleSQLQuery = queryFactory
                .select(qPartyNote)
                .from(qPartyNote)
                .innerJoin(qNoteData).on(qNoteData.noteId.eq(qPartyNote.noteId))
                .where(qPartyNote.partyId.eq(partyId)
                        .and(qNoteData.noteName.eq(noteName)));

        SQLBindings bindings = tupleSQLQuery.getSQL();
        LOG.info("{}", bindings.getSQL());
        LOG.info("{}", bindings.getNullFriendlyBindings());
        List<PartyNoteEx> ret = tupleSQLQuery.transform(GroupBy.groupBy(qNoteData.noteId).list(tupleExQBean));

        return ret.isEmpty() ? null : ret.get(0);
    }
}
