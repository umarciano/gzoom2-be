package it.mapsgroup.gzoom.querydsl.persistence.service;

import com.querydsl.sql.PostgreSQLTemplates;
import com.querydsl.sql.SQLQueryFactory;
import com.querydsl.sql.SQLTemplates;
import com.querydsl.sql.spring.SpringConnectionProvider;
import com.querydsl.sql.spring.SpringExceptionTranslator;
import com.querydsl.sql.types.JSR310LocalDateTimeType;
import com.querydsl.sql.types.JSR310LocalDateType;
import com.zaxxer.hikari.HikariDataSource;
import it.mapsgroup.gzoom.persistence.common.CustomTxManager;
import it.mapsgroup.gzoom.querydsl.BooleanCharacterType;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;
import org.springframework.transaction.support.TransactionTemplate;

import javax.inject.Provider;
import javax.sql.DataSource;
import java.sql.Connection;

/**
 * @author Andrea Fossi.
 */
@Configuration
@EnableTransactionManagement
@ComponentScan("it.mapsgroup.gzoom.querydsl.dao")
//mybatis dependency
public class QueryDslPersistenceConfiguration implements TransactionManagementConfigurer {

    private PlatformTransactionManager txManager;

    @Bean(name = "mainDataSource")
    @Autowired
    public DataSource mainDataSource(Environment environment) {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setDriverClassName(getNotNullProperty(environment, "persistence.main.driver"));
        dataSource.setJdbcUrl(getNotNullProperty(environment, "persistence.main.url"));
        dataSource.setUsername(getNotNullProperty(environment, "persistence.main.user"));
        dataSource.setPassword(getNotNullProperty(environment, "persistence.main.password"));
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

    @Autowired
    @Bean(name = "idBankTransactionTemplate")
    public TransactionTemplate idBankTransactionTemplate(@Qualifier("txManager") PlatformTransactionManager txManager) {
        TransactionTemplate transactionTemplate = new TransactionTemplate(txManager);
        transactionTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        return transactionTemplate;
    }

    @Bean
    public com.querydsl.sql.Configuration querydslConfiguration() {
        SQLTemplates templates = PostgreSQLTemplates.builder().build(); //change to your Templates
        com.querydsl.sql.Configuration configuration = new com.querydsl.sql.Configuration(templates);
        configuration.setExceptionTranslator(new SpringExceptionTranslator());

        configuration.register(new JSR310LocalDateTimeType());
        configuration.register(new JSR310LocalDateType());
        configuration.register(new BooleanCharacterType());

        return configuration;
    }

    @Autowired
    @Bean(name = "mainQueryFactory")
    public SQLQueryFactory queryFactory(@Qualifier("mainDataSource") DataSource dataSource) {
        Provider<Connection> provider = new SpringConnectionProvider(dataSource);
        return new SQLQueryFactory(querydslConfiguration(), provider);
    }



    @Override
    public PlatformTransactionManager annotationDrivenTransactionManager() {
        return txManager;
    }

}
