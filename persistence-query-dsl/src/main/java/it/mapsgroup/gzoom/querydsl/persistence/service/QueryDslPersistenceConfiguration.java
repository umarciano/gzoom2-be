package it.mapsgroup.gzoom.querydsl.persistence.service;

import com.querydsl.sql.SQLQueryFactory;
import com.querydsl.sql.SQLTemplates;
import com.querydsl.sql.spring.SpringConnectionProvider;
import com.querydsl.sql.spring.SpringExceptionTranslator;
import it.mapsgroup.gzoom.querydsl.BooleanCharacterType;
import it.mapsgroup.gzoom.querydsl.dao.PermissionDao;
import it.mapsgroup.gzoom.querydsl.service.PermissionService;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.util.StringUtils;

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
//@ComponentScan("it.mapsgroup.gzoom.querydsl")
@ComponentScan(basePackageClasses = {PermissionDao.class, PermissionService.class})
public class QueryDslPersistenceConfiguration {
    private static final Logger LOG = getLogger(QueryDslPersistenceConfiguration.class);


    public com.querydsl.sql.Configuration querydslConfiguration(Environment env) {
        LOG.info("querydslConfiguration" + env);
        String queryDslTemplate = env.getProperty("persistence.main.querydsl.templates");        
        
        if (StringUtils.isEmpty(queryDslTemplate)) {
            LOG.error("persistence.main.querydsl.templates cannot be empty");
            throw new RuntimeException("persistence.main.querydsl.templates cannot be empty");
        }
        SQLTemplates templates;
        try {
            LOG.info("Initializing template: '{}'", queryDslTemplate);
            Class<?> clazz = Class.forName(queryDslTemplate);
            Object builder = clazz.getMethod("builder").invoke(null);
            templates = (SQLTemplates) builder.getClass().getMethod("build").invoke(builder);
            templates.getEscapeChar();
        } catch (ClassNotFoundException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            LOG.error("Cannot initialize QueryDslTemplate[" + queryDslTemplate + "]", e);
            throw new RuntimeException(e);
        }

        com.querydsl.sql.Configuration configuration = new com.querydsl.sql.Configuration(templates);
        configuration.setExceptionTranslator(new SpringExceptionTranslator());

        //configuration.register(new LocalDateTimeType());
       // configuration.register(new LocalDateType());
       configuration.register(new JSR310LocalDateTimeType());
        configuration.register(new JSR310LocalDateType());
        configuration.register(new BooleanCharacterType());
        
      //  configuration.addListener(new LogListener());

        return configuration;
    }


    @Autowired
    @Bean(name = "mainQueryFactory")
    public SQLQueryFactory queryFactory(@Qualifier("mainDataSource") DataSource dataSource, Environment environment) {
        Provider<Connection> provider = new SpringConnectionProvider(dataSource);
        return new SQLQueryFactory(querydslConfiguration(environment), provider);
    }


}
