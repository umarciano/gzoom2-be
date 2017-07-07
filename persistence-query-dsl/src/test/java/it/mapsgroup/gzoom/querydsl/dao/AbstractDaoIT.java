package it.mapsgroup.gzoom.querydsl.dao;

import it.mapsgroup.gzoom.persistence.common.CommonPersistenceConfiguration;
import it.mapsgroup.gzoom.querydsl.persistence.service.QueryDslPersistenceConfiguration;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

/**
 * @author Andrea Fossi.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = {CommonPersistenceConfiguration.class, QueryDslPersistenceConfiguration.class})
@ComponentScan("it.mapsgroup.gzoom.querydsl.dao")
@TestPropertySource("/gzoom.properties")
public abstract class AbstractDaoIT {
}
