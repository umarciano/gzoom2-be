package it.mapsgroup.gzoom.persistence.common;

import com.zaxxer.hikari.HikariDataSource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;

/**
 * @author Andrea Fossi.
 */
@Configuration
@EnableTransactionManagement
@ComponentScan("it.mapsgroup.gzoom.persistence.common")
//mybatis dependency
public class CommonPersistenceConfiguration implements TransactionManagementConfigurer {

    private PlatformTransactionManager txManager;

    @Bean(name = "mainDataSource")
    @Autowired
    public DataSource mainDataSource(Environment environment) {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setDriverClassName(getNotNullProperty(environment, "persistence.main.driver"));
        dataSource.setJdbcUrl(getNotNullProperty(environment, "persistence.main.url"));
        dataSource.setUsername(getNotNullProperty(environment, "persistence.main.user"));
        dataSource.setPassword(environment.getProperty("persistence.main.password"));
        //dataSource.setConnectionTestQuery("SELECT 1");
        dataSource.setMinimumIdle(10);
        dataSource.setMaximumPoolSize(50);
        return dataSource;
    }

    private String getNotNullProperty(Environment environment, String key) {
        String value = environment.getProperty(key);
        if (StringUtils.isNoneBlank(value))
            return value;
        else
            throw new RuntimeException(key + " is null");
    }

    @Autowired
    @Bean(name = "txManager")
    public PlatformTransactionManager txManager(@Qualifier("mainDataSource") DataSource dataSource) {
        txManager = new CustomTxManager(new DataSourceTransactionManager(dataSource));
        return txManager;
    }

    @Autowired
    @Bean(name = "transactionTemplate")
    public TransactionTemplate transactionTemplate(@Qualifier("txManager") PlatformTransactionManager txManager) {
        return new TransactionTemplate(txManager);
    }

    @Override
    public PlatformTransactionManager annotationDrivenTransactionManager() {
        return txManager;
    }

    @Autowired
    @Bean(name = "sequenceGenerator")
    public SequenceGenerator sequenceGenerator(@Qualifier("mainDataSource") DataSource dataSource) {
        return new SequenceGenerator(dataSource);
    }

}
