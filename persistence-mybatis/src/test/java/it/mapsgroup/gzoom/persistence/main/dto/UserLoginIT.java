package it.mapsgroup.gzoom.persistence.main.dto;

import it.mapsgroup.gzoom.persistence.main.mapper.UserLoginGZoomMapper;
import it.memelabs.smartnebula.lmm.persistence.PersistenceConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.List;

/**
 * @author Andrea Fossi.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = PersistenceConfiguration.class)
@TestPropertySource("/gzoom.properties")
public class UserLoginIT {
    @Autowired
    private UserLoginGZoomMapper userLoginMapper;

    private TransactionTemplate txTemplate;

    @Test
    public void name() throws Exception {
        List<UserLoginGZoom> ret = userLoginMapper.selectByExample(new UserLoginGZoomExample());
        ret.size();
    }
}
