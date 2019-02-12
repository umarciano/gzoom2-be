package it.mapsgroup.gzoom.quartz;

import org.quartz.spi.TriggerFiredBundle;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;

/**
 * A factory for Quartz jobs that can autowire dependencies into jobs.
 *
 * @author Fabio G. Strozzi
 * @author Andrea Fossi
 */
public class AutowiringJobFactory extends SpringBeanJobFactory {
    private final transient AutowireCapableBeanFactory beanFactory;

    public AutowiringJobFactory(ApplicationContext appCtx) {
        beanFactory = appCtx.getAutowireCapableBeanFactory();
    }

    @Override
    protected Object createJobInstance(TriggerFiredBundle bundle) throws Exception {
        Object job = super.createJobInstance(bundle);
        beanFactory.autowireBean(job);
        job = beanFactory.initializeBean(job, null);
        return job;
    }
}
