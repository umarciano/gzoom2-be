package it.mapsgroup.gzoom;

import it.mapsgroup.gzoom.birt.BirtService;
import it.mapsgroup.gzoom.callback.ReportCallback;
import it.mapsgroup.gzoom.persistence.common.CommonPersistenceConfiguration;
import it.mapsgroup.gzoom.querydsl.persistence.service.QueryDslPersistenceConfiguration;
import it.mapsgroup.gzoom.report.querydsl.dao.ReportActivityDao;
import it.mapsgroup.gzoom.service.ReportTaskService;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;

/**
 * @author Andrea Fossi.
 */
@Configuration
@ImportResource("classpath:app-config.xml")
@Import({//ReportQueryDslPersistenceConfiguration.class,//report query dsl module configuration
        QueryDslPersistenceConfiguration.class,//gzoom query dsl module configuration
        CommonPersistenceConfiguration.class
})
@ComponentScan(basePackageClasses = {
        ReportTaskService.class,
        BirtService.class,
        ReportActivityDao.class,
        ReportCallback.class})
public class ReportModuleConfiguration {
}
