package it.mapsgroup.gzoom;

import it.mapsgroup.gzoom.birt.BirtService;
import it.mapsgroup.gzoom.persistence.common.CommonPersistenceConfiguration;
import it.mapsgroup.gzoom.report.querydsl.persistence.service.QueryDslPersistenceConfiguration;
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
@Import({QueryDslPersistenceConfiguration.class, CommonPersistenceConfiguration.class})
@ComponentScan(basePackageClasses = {ReportTaskService.class, BirtService.class})
public class ReportModuleConfiguration {
}
