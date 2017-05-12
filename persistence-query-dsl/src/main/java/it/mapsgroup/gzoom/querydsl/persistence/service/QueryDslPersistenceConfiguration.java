package it.mapsgroup.gzoom.querydsl.persistence.service;

import com.querydsl.sql.SQLQueryFactory;
import com.querydsl.sql.SQLTemplates;
import com.querydsl.sql.spring.SpringConnectionProvider;
import com.querydsl.sql.spring.SpringExceptionTranslator;
import com.querydsl.sql.types.JSR310LocalDateTimeType;
import com.querydsl.sql.types.JSR310LocalDateType;
import it.mapsgroup.gzoom.querydsl.BooleanCharacterType;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.inject.Provider;
import javax.sql.DataSource;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author Andrea Fossi.
 */
@Configuration
@EnableTransactionManagement
@ComponentScan("it.mapsgroup.gzoom.querydsl.dao")
public class QueryDslPersistenceConfiguration {
    private static final Logger LOG = getLogger(QueryDslPersistenceConfiguration.class);


    public com.querydsl.sql.Configuration querydslConfiguration() {
        String queryDslTemplate = "com.querydsl.sql.MySQLTemplates";
        SQLTemplates templates;
        try {
            Class<?> c;
            c = Class.forName(queryDslTemplate);
            Object builder = c.getMethod("builder").invoke(null);
            templates = (SQLTemplates) builder.getClass().getMethod("build").invoke(builder);
            templates.getEscapeChar();
        } catch (ClassNotFoundException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            LOG.error("Cannot initialize QueryDslTemplate[" + queryDslTemplate + "]", e);
            throw new RuntimeException(e);
        }

        //SQLTemplates templates = MySQLTemplates.builder().build(); //change to your Templates

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
