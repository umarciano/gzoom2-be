package it.mapsgroup.gzoom;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * @author Andrea Fossi.
 */
@Configuration
public class AppConfiguration {

    @Bean
    TaskExecutor taskExecutor(){
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(2);
        taskExecutor.setMaxPoolSize(5);
        taskExecutor.setQueueCapacity(2000);
        taskExecutor.afterPropertiesSet();
        return taskExecutor;
    }
}
