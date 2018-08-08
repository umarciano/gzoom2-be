package it.mapsgroup.gzoom.report.querydsl.dao;

import it.mapsgroup.gzoom.persistence.common.CommonPersistenceConfiguration;
import it.mapsgroup.gzoom.report.querydsl.persistence.service.QueryDslPersistenceConfiguration;
import it.mapsgroup.report.querydsl.dto.ReportActivity;
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
//@ComponentScan("it.mapsgroup.gzoom.report.querydsl.dao")
@ComponentScan(basePackageClasses = {ReportActivityDao.class})
@TestPropertySource("/gzoom.properties")
public abstract class AbstractReportDaoIT {
}
