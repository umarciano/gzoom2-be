package it.mapsgroup.gzoom.quartz;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import javax.sql.DataSource;


/**
 * @author Andrea Fossi.
 */
@Configuration
@ComponentScan(basePackageClasses = QuartzConfiguration.class)
public class QuartzConfiguration {


    @Bean
    SchedulerConfig schedulerConfig() {
        return new SchedulerConfig() {
        };
    }


    @Bean
    public SchedulerFactoryBean schedulerFactory(ApplicationContext appCtx, @Qualifier("mainDataSource") DataSource mainDataSource) {
        SchedulerFactoryBean factory = new SchedulerFactoryBean();
        factory.setConfigLocation(new ClassPathResource("/report/quartz.properties"));
        factory.setDataSource(mainDataSource);
        factory.setJobFactory(new AutowiringJobFactory(appCtx));
        return factory;
    }

    /**
     * @author Andrea Fossi.
     */
    public static interface SchedulerConfig {
        /**
         * seconds
         *
         * @return
         */
        default int getReportProbeDelay() {
            return 10;
        }

        /**
         * numneber of retries
         *
         * @return
         */
        default int getReportProbeRetries() {
            return 10;
        }

    }
}
