package it.mapsgroup.gzoom.mybatis.dto;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import it.mapsgroup.gzoom.mybatis.dao.UserLoginMyBatisDao;

/**
 * @author Andrea Fossi.
 */

public class UserLoginIT extends AbstractMyBatisTest {
    @Autowired
    private UserLoginMyBatisDao dao;


    @Test
    @Transactional
    public void name() throws Exception {
        UserLoginGZoom ret = dao.findByUserLoginId("admin");
        assertNotNull(ret);
        assertEquals("admin", ret.getUserLoginId());
    }
}
