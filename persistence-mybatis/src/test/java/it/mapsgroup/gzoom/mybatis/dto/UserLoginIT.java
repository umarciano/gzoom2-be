package it.mapsgroup.gzoom.mybatis.dto;

import it.mapsgroup.gzoom.mybatis.MyBatisPersistenceConfiguration;
import it.mapsgroup.gzoom.mybatis.dao.UserLoginMyBatisDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author Andrea Fossi.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = MyBatisPersistenceConfiguration.class)
@TestPropertySource("/gzoom.properties")
public class UserLoginIT {
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
