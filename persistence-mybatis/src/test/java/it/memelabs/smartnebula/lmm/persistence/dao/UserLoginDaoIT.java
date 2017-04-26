package it.memelabs.smartnebula.lmm.persistence.dao;

import it.mapsgroup.commons.collect.Tuple2;
import it.memelabs.smartnebula.lmm.persistence.AbstractDaoTestIT;
import it.memelabs.smartnebula.lmm.persistence.main.dao.UserLoginDao;
import it.memelabs.smartnebula.lmm.persistence.main.dao.UserLoginFilter;
import it.memelabs.smartnebula.lmm.persistence.main.dto.UserLogin;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @author Andrea Fossi
 */

@Transactional
public class UserLoginDaoIT extends AbstractDaoTestIT{
    @Autowired
    private UserLoginDao dao;

    @Test
    public void testName1() throws Exception {

        UserLoginFilter filter = new UserLoginFilter();
        filter.setSize(10);
        filter.setPage(1);
       // filter.setFilterText("AN");
        Tuple2<List<UserLogin>, Integer> result = dao.find(filter);
        result.second();

    }

    @Test
    public void testName() throws Exception {
        assertFalse(dao.userLoginExist("test"));
        UserLogin test = dao.getUserLogin("anfo");
        test.setId(55L);
        test.setUsername("test");
        dao.createUserLogin(test, null);
        test.setId(55L);
        dao.createUserLogin(test, null);
        assertTrue(dao.userLoginExist("test"));
        assertTrue(test.getId() > 0);
        UserLogin test3 = dao.getUserLogin("test");
        assertEquals(2, test3.getNodes().size());
        test3.getNodes().remove(55L);
        dao.updateUserUserLogin(test3, null);
        UserLogin test4 = dao.getUserLogin("test");
        assertEquals(1, test4.getNodes().size());


    }

    @Test
    public void name() throws Exception {
        UserLogin ret = dao.getUserLogin("anfo");
        assertTrue(ret!=null);

    }
}
