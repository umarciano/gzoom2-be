package it.memelabs.smartnebula.lmm.persistence;

/**
 * @author Andrea Fossi.
 */

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = PersistenceConfiguration.class)
@TestPropertySource("/dev.properties")
public abstract class AbstractDaoTestIT {
}
