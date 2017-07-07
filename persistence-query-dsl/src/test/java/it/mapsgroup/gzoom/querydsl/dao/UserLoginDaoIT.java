package it.mapsgroup.gzoom.querydsl.dao;

import com.querydsl.core.Tuple;
import com.querydsl.sql.SQLQueryFactory;
import it.mapsgroup.gzoom.querydsl.dto.QParty;
import it.mapsgroup.gzoom.querydsl.dto.QPerson;
import it.mapsgroup.gzoom.querydsl.dto.QUserLoginPersistent;
import it.mapsgroup.gzoom.querydsl.dto.UserLogin;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author Andrea Fossi.
 */

public class UserLoginDaoIT extends AbstractDaoIT {
    @Autowired
    UserLoginDao userLoginDao;

    @Test
    // @Transactional
    public void findByUsername() throws Exception {
        UserLogin admin = userLoginDao.getUserLogin("admin");
        assertNotNull(admin);
        assertNotNull(admin.getParty());
        assertNotNull(admin.getPerson());
        assertEquals("admin", admin.getUserLoginId());
        assertEquals("admin", admin.getParty().getPartyId());
        assertEquals("AMMINISTRATORE", admin.getPerson().getFirstName());
    }

    @Autowired
    SQLQueryFactory queryFactory;

    @Test
    @Transactional
    public void name() throws Exception {
        QUserLoginPersistent userLogin = new QUserLoginPersistent("ul");
        QParty party = new QParty("party_a");
        QParty party2 = new QParty("party_b");
        QPerson person = new QPerson("persons");
        List<Tuple> s = queryFactory.select(userLogin.userLoginId, party.partyName)
                .from(userLogin).join(userLogin.userParty, party)
                .join(party._personParty, person)
                .join(userLogin.userParty, party2)
                /*
                 * .where( userLogin.enabled.isTrue()
                 * .or( Expressions.allOf
                 * ( Expressions.allOf(party.isUnread,
                 * party.partyId.isNotEmpty()) , 
                 * Expressions.anyOf(party.partyId.between("ss", "rr"),
                 * userLogin.isSystem) ) ) 
                 * .and(userLogin.userLoginId.likeIgnoreCase("%ADMIN%"))
                 * .and(party.partyName.like("aaa")) ).fetch();
                 */
                .fetch();
        s.size();
    }
}
