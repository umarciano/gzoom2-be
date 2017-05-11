package it.mapsgroup.gzoom.querydsl.persistence.service;

import com.querydsl.sql.MySQLTemplates;
import com.querydsl.sql.SQLQueryFactory;
import com.querydsl.sql.SQLTemplates;
import com.querydsl.sql.spring.SpringConnectionProvider;
import com.querydsl.sql.spring.SpringExceptionTranslator;
import com.querydsl.sql.types.JSR310LocalDateTimeType;
import com.querydsl.sql.types.JSR310LocalDateType;
import it.mapsgroup.gzoom.querydsl.BooleanCharacterType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.inject.Provider;
import javax.sql.DataSource;
import java.sql.Connection;

/**
 * @author Andrea Fossi.
 */
@Configuration
@EnableTransactionManagement
@ComponentScan("it.mapsgroup.gzoom.querydsl.dao")
public class QueryDslPersistenceConfiguration {

    public com.querydsl.sql.Configuration querydslConfiguration() {
        SQLTemplates templates = MySQLTemplates.builder().build(); //change to your Templates
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


}
